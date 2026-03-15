package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class room {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "room_no")
    private String roomNo;
    
    private String availability; // Available or Occupied
    private Double price;
    
    @Column(name = "bed_type")
    private String bedType; // ICU, General, Private

    // Default Constructor
    public room() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRoomNo() { return roomNo; }
    public void setRoomNo(String roomNo) { this.roomNo = roomNo; }
    public String getAvailability() { return availability; }
    public void setAvailability(String availability) { this.availability = availability; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public String getBedType() { return bedType; }
    public void setBedType(String bedType) { this.bedType = bedType; }
}