package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String name;
    private String specialization;
    private int age;
    private String experience;
    private String contact;

    // Default Constructor (Zaroori hai)
    public Doctor() {}

    // Getters and Setters (Thymeleaf inhi se data uthata hai)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
}