package com.example.book_api.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RefreshTokenService {

    //On récupère jwtService
    private final JwtService jwtService;

    //Expiration time 7 jours
    public static final long EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 7;

    /**Méthode generation refresh token qui récupère la méthode de jwtservice*/
    public String generateRefreshToken(String username) {
        return jwtService.generateToken(username, EXPIRATION_TIME);
    }
}
