//package com.pm.patient_service.service;
//
//import com.pm.authservice.dto.LoginRequestDTO;
//import com.pm.authservice.util.JwtUtil;
//import io.jsonwebtoken.JwtException;
//import java.util.Optional;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AuthService {
//
//  private final PatientService patientService;
//  private final PasswordEncoder passwordEncoder;
//  private final JwtUtil jwtUtil;
//
//  public AuthService(PatientService patientService, PasswordEncoder passwordEncoder,
//                     JwtUtil jwtUtil) {
//    this.patientService = patientService;
//    this.passwordEncoder = passwordEncoder;
//    this.jwtUtil = jwtUtil;
//  }
//
//  public Optional<String> authenticate(LoginRequestDTO loginRequestDTO) {
//    Optional<String> token = patientService.findByEmail(loginRequestDTO.getEmail())
//        .filter(u -> passwordEncoder.matches(loginRequestDTO.getPassword(),
//            u.getPassword()))
//        .map(u -> jwtUtil.generateToken(u.getEmail(), u.getRole()));
//
//    return token;
//  }
//
//  public boolean validateToken(String token) {
//    try {
//      jwtUtil.validateToken(token);
//      return true;
//    } catch (JwtException e){
//      return false;
//    }
//  }
//}
