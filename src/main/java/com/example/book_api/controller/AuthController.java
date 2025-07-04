package com.example.book_api.controller;

import com.example.book_api.dto.AuthRequest;
import com.example.book_api.service.AccessTokenService;
import com.example.book_api.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AccessTokenService accessTokenService;


    //Injection des dépendences
    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserDetailsService userDetailsService, AccessTokenService accessTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.accessTokenService = accessTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody AuthRequest request) {
        //On authentifie l'utilisateur avec le manager de Spring Security
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );

            // Si l'authentification réussit, on génère un acces token// Pas besoin de recharger l'utilisateur,
            final UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
            final String accessToken = accessTokenService.generateAccessToken(userDetails.getUsername());

            return ResponseEntity.ok(accessToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(request.username() + " " + request.password());
        }

    }
}
