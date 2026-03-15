package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.Emergency;

public interface EmergencyRepository extends JpaRepository<Emergency, Integer> {

}
