package com.example.model;


import jakarta.persistence.*;

@Entity
@Table(name = "staff")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String role;
    private String department;
    private String shift;
    private String contact;

    public Staff() {
    }
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
}
    

