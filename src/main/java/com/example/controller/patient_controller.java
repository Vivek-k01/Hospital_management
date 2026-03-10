package com.example.controller;

import com.example.model.patient;
import com.example.repository.patient_repository;
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

@Controller
public class patient_controller {

    @Autowired
    private patient_repository repository;

    // 1. Dashboard View with Stats
    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("listPatients", repository.findAll());
        model.addAttribute("totalPatients", repository.count());
        model.addAttribute("activeAppointments", 38); 
        model.addAttribute("availableDoctors", 12);   
        return "index";
    }

    // 2. Save or Update Patient
    @PostMapping("/savePatient")
    public String savePatient(@ModelAttribute("patient") patient patient) {
        repository.save(patient);
        return "redirect:/";
    }

    // 3. Edit Form with Null Check
    @GetMapping("/editPatient/{id}")
    public String showEditForm(@PathVariable(value = "id") long id, Model model) {
        patient patient = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient Id:" + id));
        model.addAttribute("patient", patient);
        return "edit-patient";
    }

    // 4. Delete/Discharge Patient
    @GetMapping("/deletePatient/{id}")
    public String deletePatient(@PathVariable(value = "id") long id) {
        repository.deleteById(id);
        return "redirect:/";
    }

    // 5. PDF Prescription Generator (MCA Project Special)
    @GetMapping("/downloadPrescription/{id}")
    public void downloadPrescription(@PathVariable(value = "id") long id, HttpServletResponse response) throws Exception {
        patient patient = repository.findById(id).orElseThrow();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Prescription_" + patient.getName() + ".pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("LIFELINE HOSPITAL").setBold().setFontSize(22).setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("Patient Prescription").setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("------------------------------------------------------------------"));
        
        document.add(new Paragraph("Patient ID: " + patient.getId()));
        document.add(new Paragraph("Name: " + patient.getName()));
        document.add(new Paragraph("Age: " + patient.getAge()));
        document.add(new Paragraph("Diagnosis: " + patient.getDisease()));
        document.add(new Paragraph("Assigned Doctor: " + patient.getAssignedDoctor()));
        document.add(new Paragraph("\nStatus: " + patient.getStatus()));
        document.add(new Paragraph("\nRx (Medicines):").setBold());
        document.add(new Paragraph("- Paracetamol 500mg (1-0-1)\n- Antibiotic Course (3 days)"));
        
        document.add(new Paragraph("\n\n\nAuthorized Signature").setTextAlignment(TextAlignment.RIGHT));

        document.close();
    }
}