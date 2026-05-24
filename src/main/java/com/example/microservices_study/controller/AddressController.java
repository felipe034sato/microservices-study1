package com.example.microservices_study.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class AddressController {

    @Autowired
    private RestTemplate restTemplate;

    //create a new varaible to store the data from the other service by hitting http://localhost:8080/address

    @GetMapping("/address")
    public String getFromAnotherServer(){
        String address = restTemplate.getForObject("http://localhost:8080/address", String.class);
        return "Adress" + address;
    }

//    @GetMapping("/address")
//    public String outcome(){
//        return "Rua visconde de Taunay";
//    }

}
