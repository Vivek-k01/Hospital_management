package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.model.patient;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface patient_repository extends JpaRepository<patient, Long> {

    // Phone number se patient dhoondhne ke liye
    Optional<patient> findByPhone(String phone);

    // Naam se dhoondhne ke liye
    List<patient> findByNameContainingIgnoreCase(String name);
    
    // 1. Aaj kitne patients aaye
    long countByRegistrationDate(LocalDate date);

    // 2. Aaj ki highest Daily ID nikalne ke liye
    @Query("SELECT MAX(p.dailyId) FROM patient p WHERE p.registrationDate = :date")
    Optional<Long> findMaxDailyIdByRegistrationDate(@Param("date") LocalDate date);

    // --- NAYA METHOD: Dashboard par date-wise sorting ke liye ---
    // Isse naye din ke patient sabse upar dikhenge
    List<patient> findAllByOrderByRegistrationDateDescDailyIdAsc();
}