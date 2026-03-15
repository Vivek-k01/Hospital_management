package com.example.repository;

import com.example.model.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {
    
    // Mobile number se patient ka bill dhoondhne ke liye custom method
    Optional<Billing> findByPatientPhone(String patientPhone);
}