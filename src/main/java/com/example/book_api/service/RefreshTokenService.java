package com.example.book_api.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RefreshTokenService {

    //On récupère jwtService et userDetailsService
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    //Expiration time 7 jours
    public static final long EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 7;



    /**Méthode generation refresh token qui récupère la méthode de jwtservice*/
    public String generateRefreshToken(String username) {
        return jwtService.generateToken(username, EXPIRATION_TIME);
    }

    /**Méthode qui vérifie si le refresh token est toujours valide
     * en reprenant la méthode dans jwtservice isTokenValid()
     */
    public boolean isvalidRefreshToken(String refreshToken, String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtService.isTokenValid(refreshToken, userDetails);
    }
}
