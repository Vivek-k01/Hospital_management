package com.example.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "emergency_contacts")
public class Emergency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String service_name;
    private String contact_number;
    private String availability;

    // Default Constructor
    public Emergency() {}

    // Getters and Setters (Inke bina error aayegi)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getService_name() { return service_name; }
    public void setService_name(String service_name) { this.service_name = service_name; }

    public String getContact_number() { return contact_number; }
    public void setContact_number(String contact_number) { this.contact_number = contact_number; }

    public String getAvailability() { return availability; }
    public void setAvailability(String availability) { this.availability = availability; }
}    

