package com.example.demo.controller;

import com.example.demo.entity.UserEntity;
import com.example.demo.model.AuthenticationRequest;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "http://localhost:63342", allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) {
        return userService.createAuthenticationToken(authenticationRequest, response);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserEntity userEntity){
        return userService.save(userEntity);
    }

    @PostMapping("/logouto")
    public ResponseEntity<?> logout(HttpServletResponse response){
        return userService.logout(response);
    }


    @GetMapping("/connected")
    public ResponseEntity<Boolean> checkConnection(){
        return ResponseEntity.ok(true);
    }

}
