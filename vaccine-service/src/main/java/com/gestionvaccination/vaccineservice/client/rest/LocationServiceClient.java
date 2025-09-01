//package com.gestionvaccination.vaccineservice.client.rest;
//
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import com.gestionvaccination.vaccineservice.client.dto.LocationDTO;
//
///**
// * Client Feign pour communiquer avec le location-service
// */
//@FeignClient(name = "location-service", url = "${app.services.location-service.url}")
//public interface LocationServiceClient {
//
//    /**
//     * Récupérer une localité par son ID
//     */
//    @GetMapping("/api/localities/{id}")
//    LocationDTO obtenirLocaliteParId(@PathVariable("id") Long id);
//}
