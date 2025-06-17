package com.example.book_api.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

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
            .signWith(getSigningKey(),SignatureAlgorithm.HS256)

            //Génération finale du token sous forme de chaine compacte

            .compact();
    }

    /**
     * Méthode utilitaire pour obtenir une clé utilisable par la bibliothèque JJWT.
     * Elle convertit la chaîne `SECRET_KEY` (en base64) en un objet `Key`.
     */

    private Key getSigningKey() {

        //Décodage de la clé base64
        byte [] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        //Construction de la key avec l'algo HMAC-SHA
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //A faire pour tester
    // D’autres méthodes peuvent être ajoutées ici, comme :
    // - validateToken(token)
    // - extractUsername(token)
    // - extractExpiration(token)


    /**Méthode pour extraire **tous les "claims"** (les données contenues dans le token).
     Les claims sont typiquement : username, date d’expiration, rôles, etc.*/
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                // On définit la clé de signature pour vérifier l’authenticité du token
                .setSigningKey(getSigningKey())
                .build()
                // On parse le token JWT et on récupère la partie "Claims" (le payload).
                .parseClaimsJwt(token)
                .getBody(); //ici on récup les données contenues dans le token
    }

    /**
    * Méthode générique pour extraire **un seul claim** depuis un token JWT.
    * Elle utilise une fonction (`claimsResolver`) pour dire **quel champ** on veut extraire.
    * Par exemple : `Claims::getSubject` pour le username, `Claims::getExpiration` pour la date.*/

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    //On application la fonction passée en paramètre à l'object claims
    return claimsResolver.apply(claims);
    }


    /**
    * Extrait le **username** contenu dans le token.
    * En JWT, il est stocké dans le champ `sub` (subject).
    */
    public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
    }

    /**Extraire la date d'expiration du token*/
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
    * Vérifie si le token est **expiré**.
    * On compare la date d’expiration du token à la date actuelle.
    */

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
    * Vérifie si un token est **valide** :
    * - Le nom d’utilisateur dans le token correspond à celui de l'utilisateur authentifié.
    * - Le token n’est pas expiré.
    */

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}