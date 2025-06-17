package com.example.book_api.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

import static JwtService.EXPIRATION_TIME;

//Service de gestion de JWTs
@Service
public class JwtService {

    //Clé secrete utilisée pour signer le JWT attention ne pas faire ça en prod
    //il faut utiliser un fichier de configuration sécurisé (.properties ou .yml) et une clé bi en plus robuste
    private static final String SECRET_KEY = "une_super_cle_secrete_tres_longue_et_complexe";

    //Ici, durée validité token exprimé en ms
    private static final long EXPIRATION_TIME = 1000 * 60 * 10;


/**
 * Méthode principale pour générer un JWT.
 * On reçoit un nom d'utilisateur (généralement après authentification réussie),
 * et on retourne un token signé.
 */

public String genrateToken(String username) {
    return Jwts.builder()
            //Le subject = identifiant principal à savoir ici le username
            .setSubject(username)

            //Date génération du token
            .setIssuedAt(new Date(System.currentTimeMillis()))

            //Date d'expiration du token (ici ajout 10 min à la date)
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))

            //Signature du token
            .signWith(getSigningKey().SignatureAlgorithm.HS256, SECRET_KEY)

            //Génération finale du token sous forme de chaine compacte

            .compact();
}

}