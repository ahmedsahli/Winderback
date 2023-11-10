package com.example.pidev.Controller;

import com.example.pidev.Service.JwtService;
import com.example.pidev.entity.JwtRequest;
import com.example.pidev.entity.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtController {
    @Autowired
    JwtService jwtService;

    @PostMapping({"/authenticate"})
    public JwtResponse createJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        System.out.println("request");
        System.out.println(jwtRequest.getUserName());
        System.out.println(jwtRequest.getPassword());
        return jwtService.createJwtToken(jwtRequest);
    }















}
