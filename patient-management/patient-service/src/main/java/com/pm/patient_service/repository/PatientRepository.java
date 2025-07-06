package com.pm.patient_service.repository;

import com.pm.patient_service.model.Patient;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, UUID> {
//  Optional<User> findByEmail(String email);
}
