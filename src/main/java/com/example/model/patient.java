package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "patients")
public class patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Ye database ki primary key hai (Sequential: 1, 2, 3...)

    // --- Naya Column: Jo har din 1, 2, 3 se shuru hoga ---
    @Column(name = "daily_id")
    private Long dailyId;

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 50)
    @Column(nullable = false, length = 50)
    private String name;

    @NotBlank(message = "Disease cannot be empty")
    @Column(nullable = false)
    private String disease;

    @Min(0) @Max(120)
    private int age;

    @Column(length = 20)
    private String status = "Admitted"; 

    @Column(length = 50)
    private String assignedDoctor = "Dr. Sharma";

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate = LocalDate.now(); 

    // 1. Fields mein add karein
@NotBlank(message = "Phone number is required")
@Column(nullable = false, length = 20, unique = true)
private String phone;

// 2. Getters and Setters mein ye niche add karein
public String getPhone() { return phone; }
public void setPhone(String phone) { this.phone = phone; }

    // Mandatory No-args Constructor
    public patient() {}

    // Parameterized Constructor
    public patient(String name, String disease, int age) {
        this.name = name;
        this.disease = disease;
        this.age = age;
        this.registrationDate = LocalDate.now();
    }

    // --- Getters and Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getDailyId() { return dailyId; }
    public void setDailyId(Long dailyId) { this.dailyId = dailyId; }

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

    public LocalDate getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }
}