package com.example.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "billing")
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patientPhone;

    // 'double' ki jagah 'Double' use karo, ye NULL handle kar lega
    private Double doctorFee = 0.0;
    private Double roomCharges = 0.0;
    private Double pharmacyCharges = 0.0;
    private Double totalAmount = 0.0;

    private String paymentStatus;
    private LocalDate billingDate;

    public Billing() {}

    // Getters and Setters (Baki sab same rahega)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPatientPhone() { return patientPhone; }
    public void setPatientPhone(String patientPhone) { this.patientPhone = patientPhone; }
    public Double getDoctorFee() { return doctorFee; }
    public void setDoctorFee(Double doctorFee) { this.doctorFee = doctorFee; }
    public Double getRoomCharges() { return roomCharges; }
    public void setRoomCharges(Double roomCharges) { this.roomCharges = roomCharges; }
    public Double getPharmacyCharges() { return pharmacyCharges; }
    public void setPharmacyCharges(Double pharmacyCharges) { this.pharmacyCharges = pharmacyCharges; }
    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    public LocalDate getBillingDate() { return billingDate; }
    public void setBillingDate(LocalDate billingDate) { this.billingDate = billingDate; }
}