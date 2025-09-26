//package com.gestionvaccination.userservice.client.rest;
//
//import com.gestionvaccination.userservice.client.dto.UserDto;
//import com.gestionvaccination.userservice.dto.AuthRequestDTO;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//
///**
// * Client REST pour communiquer avec le Location Service
// */
//@FeignClient(
//        name = "AUTH-SERVICE1"
//)
//public interface AuthServiceClient1 {
//
//    /**
//     * Récupérer une localité par son ID
//     */
//    @PostMapping("/api/auth/v1/users")
//    UserDto SaveUser(@RequestBody AuthRequestDTO authRequestDTO);
//}
