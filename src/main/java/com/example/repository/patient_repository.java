package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.patient;

public interface patient_repository extends JpaRepository<patient, Long> {
}