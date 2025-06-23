package com.example.book_api.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccessTokenService extends JwtService {

    //Mise en place de l'expriation time
    private static final long EXPIRATION_TIME = 1000 * 60 * 15;

    /**Méthodee qui se charge de générer un acces Token*/
    public String generateAccessToken(String username) {
        return this.generateToken(username, EXPIRATION_TIME);
    }
}
