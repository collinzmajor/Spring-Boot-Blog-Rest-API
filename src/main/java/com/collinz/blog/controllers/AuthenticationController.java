package com.collinz.blog.controllers;

import com.collinz.blog.exceptions.ObjectAlreadyExistsException;
import com.collinz.blog.exceptions.ObjectNotFoundException;
import com.collinz.blog.requests.LoginRequest;
import com.collinz.blog.requests.RegisterRequest;
import com.collinz.blog.response.AuthenticationResponse;
import com.collinz.blog.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService service;

    @Autowired
    public AuthenticationController(AuthenticationService service){
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest request) throws ObjectAlreadyExistsException {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginRequest request) throws ObjectNotFoundException {
        return ResponseEntity.ok(service.login(request));
    }
}
