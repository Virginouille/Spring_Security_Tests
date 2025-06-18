package com.example.book_api.securite.filter;

import com.example.book_api.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter { //cet extend pour assurer que la logique de validation et d'authentiffication sera executée qu'une fois

    //Service chargé de générer et valider les token
    private final JwtService jwtService;

    // Service standard de Spring Security pour charger les informations d’un utilisateur (depuis une BDD ou autre).
    private final UserDetailsService userDetailsService;

    //Injection des dépendances via le constructeur
    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

/**
 * Méthode appelée automatiquement à chaque requête HTTP.
 * Ce filtre s'exécute une seule fois par requête, car il hérite de `OncePerRequestFilter`.*/

@Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Récupération du header "Authorization" qui contient potentiellement un token JWT.
        final String authHeader = request.getHeader("Authorization");

        // Si le header est absent ou ne commence pas par "Bearer ", on ne traite pas cette requête.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); //on passe au filtre suivant
            return;
        }

        // 2. Extraction du token JWT (on enlève le préfixe "Bearer ").
        final String jwt = authHeader.substring(7);

        // 3. Extraction du nom d’utilisateur depuis le token.
        final String username = jwtService.extractUsername(jwt);

        // Si on a un username ET qu’aucune authentification n’est encore active pour cette requête :
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 3.a Chargement de l'utilisateur depuis la base ou un autre provider.
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 3.b Vérification que le token est bien valide (signature, date, correspondance utilisateur).
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // 4. Création d’un objet Authentication pour informer Spring Security de l’utilisateur connecté.
                UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
                        userDetails, //L'utilisateur
                        null, //Pas de mot de passe nécessaire ici (déjà vérifié via le token)
                        userDetails.getAuthorities() //Les rôles ou permissions de l'utilisateur
                );
                // On attache des détails liés à la requête (comme l’adresse IP, etc.)
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // On enregistre l’authentification dans le contexte de sécurité de Spring
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // 5. On poursuit la chaîne de filtres (on laisse les autres s’exécuter normalement).
        filterChain.doFilter(request, response);
    }
}

