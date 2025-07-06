package com.pm.patient_service.service;

import com.pm.patient_service.dto.PatientRequestDTO;
import com.pm.patient_service.dto.PatientResponseDTO;
import com.pm.patient_service.mapper.PatientMapper;
import com.pm.patient_service.model.Patient;
import com.pm.patient_service.repository.PatientRepository;

import java.util.List;
import java.util.Optional;

import com.pm.patient_service.repository.PatientRepository;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

  private final PatientRepository patientRepository;

  public PatientService(PatientRepository patientRepository) {
    this.patientRepository = patientRepository;
  }


  public List<PatientResponseDTO> getPatients() {
    List<Patient> patients = patientRepository.findAll();

      return patients.stream().map(PatientMapper::toDTO).toList();
  }

  public PatientResponseDTO createPatients(PatientRequestDTO patientRequestDTO) {
    Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));

    return PatientMapper.toDTO(newPatient);
  }
}
