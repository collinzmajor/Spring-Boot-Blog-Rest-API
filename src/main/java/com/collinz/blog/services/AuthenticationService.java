package com.collinz.blog.services;

import com.collinz.blog.exceptions.ObjectAlreadyExistsException;
import com.collinz.blog.exceptions.ObjectNotFoundException;
import com.collinz.blog.models.User;
import com.collinz.blog.repositories.UserRepository;
import com.collinz.blog.requests.LoginRequest;
import com.collinz.blog.requests.RegisterRequest;
import com.collinz.blog.response.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(PasswordEncoder encoder, UserRepository repo, JwtService jwtService, AuthenticationManager authenticationManager){
        this.passwordEncoder = encoder;
        this.repository = repo;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegisterRequest request) throws ObjectAlreadyExistsException {
        User user = new User(
                null,
                request.getFirstName(),
                request.getLastName(),
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                LocalDateTime.now()
        );

        if (repository.existsByUsername(user.getUsername())){
            throw new ObjectAlreadyExistsException("Username already in use!");
        }

        repository.save(user);

        return getAuthenticationResponse(user);

    }

    private AuthenticationResponse getAuthenticationResponse(User user) {
        String jwt = jwtService.generateToken(user);

        return new AuthenticationResponse(jwt);
    }

    public AuthenticationResponse login(LoginRequest request) throws ObjectNotFoundException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var user = repository.findByUsername(request.getUsername()).orElseThrow(() -> new ObjectNotFoundException("User not found!"));

        return getAuthenticationResponse(user);
    }

}
