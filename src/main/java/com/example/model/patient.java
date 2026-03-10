package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*; // Validation ke liye

@Entity
@Table(name = "patients") // Table ka naam specify karna achha hota hai
public class patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50) // Database level security
    private String name;

    @NotBlank(message = "Disease cannot be empty")
    @Column(nullable = false)
    private String disease;

    @Min(0) @Max(120) // Age validation
    private int age;

    @Column(length = 20)
    private String status = "Admitted"; 

    @Column(length = 50)
    private String assignedDoctor = "Dr. Sharma";

    // 1. Mandatory No-args Constructor
    public patient() {}

    // 2. Parameterized Constructor (Optional but good for testing)
    public patient(String name, String disease, int age) {
        this.name = name;
        this.disease = disease;
        this.age = age;
    }

    // Getters and Setters (Same as yours)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDisease() { return disease; }
    public void setDisease(String disease) { this.disease = disease; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getAssignedDoctor() { return assignedDoctor; }
    public void setAssignedDoctor(String assignedDoctor) { this.assignedDoctor = assignedDoctor; }
}