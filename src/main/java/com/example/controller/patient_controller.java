package com.example.controller;

import com.example.model.Billing;
import com.example.model.patient;
import com.example.repository.patient_repository;
import com.example.repository.room_repository;
import com.example.repository.DoctorRepository;
import com.example.repository.EmergencyRepository;
import com.example.repository.PharmacyRepository;
import com.example.repository.BillingRepository;
import com.example.repository.StaffRepository;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Controller
public class patient_controller {

    @Autowired
    private patient_repository repository;

    @Autowired
    private room_repository roomRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Autowired
    private BillingRepository billingRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private EmergencyRepository emergencyRepository;

    // ---------------- EMERGENCY ----------------
    @GetMapping("/emergency")
    public String viewEmergency(Model model) {
        model.addAttribute("listEmergency", emergencyRepository.findAll());
        return "emergency"; 
    }

    // ---------------- STAFF ----------------
    @GetMapping("/staff")
    public String viewStaff(Model model) {
        model.addAttribute("listStaff", staffRepository.findAll());
        return "staff"; 
    }
  
    // ---------------- BILLING DASHBOARD ----------------
    @GetMapping("/billing")
    public String viewBilling(Model model) {
        List<Billing> bills = billingRepository.findAll();
        model.addAttribute("listBills", bills);
        double totalRev = bills.stream().mapToDouble(Billing::getTotalAmount).sum();
        model.addAttribute("totalRevenue", totalRev);
        return "billing"; 
    }

    // ---------------- NEW: GENERATE BILL BY PHONE ----------------
    @GetMapping("/billing/search")
    public String searchAndGenerateBill(@RequestParam("phone") String phone, Model model) {
        Optional<patient> patientOpt = repository.findByPhone(phone);
        
        if (patientOpt.isPresent()) {
            patient p = patientOpt.get();
            long days = ChronoUnit.DAYS.between(p.getRegistrationDate(), LocalDate.now());
            if (days <= 0) days = 1; 

            double docFee = 500.0; 
            double roomRate = 1200.0;
            double pharmacyCharge = 1500.0; 
            double totalRoomCharge = days * roomRate;
            double finalTotal = docFee + totalRoomCharge + pharmacyCharge;

            Billing bill = billingRepository.findByPatientPhone(phone).orElse(new Billing());
            bill.setPatientPhone(phone);
            bill.setDoctorFee(docFee);
            bill.setRoomCharges(totalRoomCharge);
            bill.setPharmacyCharges(pharmacyCharge);
            bill.setTotalAmount(finalTotal);
            bill.setPaymentStatus("Pending");
            bill.setBillingDate(LocalDate.now());
            
            billingRepository.save(bill);
            return "redirect:/billing";
        } else {
            model.addAttribute("error", "Error: No patient found with mobile number: " + phone);
            model.addAttribute("listBills", billingRepository.findAll());
            return "billing";
        }
    }

    // ---------------- PHARMACY ----------------
    @GetMapping("/pharmacy")
    public String viewPharmacy(Model model) {
        model.addAttribute("listMedicines", pharmacyRepository.findAll());
        return "pharmacy";
    }

    // ---------------- DOCTORS ----------------
    @GetMapping("/doctors")
    public String viewDoctors(Model model) {
        model.addAttribute("listDoctors", doctorRepository.findAll());
        return "doctors";
    }

    // ---------------- ROOMS ----------------
    @GetMapping("/rooms")
    public String getAllRooms(Model model) {
        model.addAttribute("listRooms", roomRepository.findAll());
        return "rooms";
    }

    // ---------------- LOGIN PAGE ----------------
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // ---------------- DASHBOARD (UPDATED FOR SORTING) ----------------
    @GetMapping("/")
    public String viewHomePage(@RequestParam(value = "search", required = false) String search, Model model) {
        List<patient> list;
        LocalDate today = LocalDate.now();

        if (search != null && !search.isEmpty()) {
            if (search.matches("\\d+")) {
                list = repository.findByPhone(search).map(List::of).orElse(List.of());
            } else {
                list = repository.findByNameContainingIgnoreCase(search);
            }
        } else {
            // Naya method use kiya sorting ke liye
            list = repository.findAllByOrderByRegistrationDateDescDailyIdAsc();
        }

        long todayPatientsCount = repository.countByRegistrationDate(today);
        long availableSlots = 50 - todayPatientsCount;
        if (availableSlots < 0) availableSlots = 0;

        model.addAttribute("listPatients", list);
        model.addAttribute("totalPatients", repository.count());
        model.addAttribute("activeAppointments", availableSlots); 
        model.addAttribute("availableDoctors", 15);
        
        return "index";
    }

    // ---------------- SAVE PATIENT (UPDATED) ----------------
    @PostMapping("/savePatient")
    public String savePatient(@ModelAttribute("patient") patient patientEntity, Model model) {
        try {
            if (patientEntity.getId() != null) {
                patient existing = repository.findById(patientEntity.getId()).orElse(null);
                if (existing != null) {
                    if (patientEntity.getPhone() == null || patientEntity.getPhone().isEmpty()) {
                        patientEntity.setPhone(existing.getPhone());
                    }
                    patientEntity.setRegistrationDate(existing.getRegistrationDate());
                    patientEntity.setDailyId(existing.getDailyId());
                }
            } else {
                LocalDate today = LocalDate.now();
                patientEntity.setRegistrationDate(today);
                Optional<Long> maxDailyIdOpt = repository.findMaxDailyIdByRegistrationDate(today);
                patientEntity.setDailyId(maxDailyIdOpt.isPresent() ? maxDailyIdOpt.get() + 1 : 1L);
            }

            repository.save(patientEntity);
            return "redirect:/";

        } catch (Exception e) {
            model.addAttribute("error", "Bhai, update fail ho gaya: " + e.getMessage());
            model.addAttribute("patient", patientEntity);
            return "edit-patient"; 
        }
    }

    // ---------------- EDIT & DELETE ----------------
    @GetMapping("/editPatient/{id}")
    public String showEditForm(@PathVariable("id") long id, Model model) {
        patient patient = repository.findById(id).orElseThrow();
        model.addAttribute("patient", patient);
        return "edit-patient";
    }

    @GetMapping("/deletePatient/{id}")
    public String deletePatient(@PathVariable("id") long id) {
        repository.deleteById(id);
        return "redirect:/";
    }

    // ---------------- PDF PRESCRIPTION ----------------
    @GetMapping("/downloadPrescription/{id}")
    public void downloadPrescription(@PathVariable("id") long id, HttpServletResponse response) throws Exception {
        patient patient = repository.findById(id).orElseThrow();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Prescription_" + patient.getName() + ".pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("LIFELINE HOSPITAL").setBold().setFontSize(22).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("Patient Prescription").setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("---------------------------------------------------"));
        document.add(new Paragraph("Today's Patient No: " + patient.getDailyId()));
        document.add(new Paragraph("Name: " + patient.getName()));
        document.add(new Paragraph("Phone: " + patient.getPhone()));
        document.add(new Paragraph("Age: " + patient.getAge()));
        document.add(new Paragraph("Diagnosis: " + patient.getDisease()));
        document.add(new Paragraph("Assigned Doctor: " + patient.getAssignedDoctor()));
        document.close();
    }
}