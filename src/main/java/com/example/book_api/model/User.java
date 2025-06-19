package com.example.book_api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name="users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    public User(String userName, String password) {
        this.username = userName;
        this.password = password;
    }


    // --- Implémentations des méthodes de l'interface UserDetails ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Pour cet exemple, nous donnons le rôle "ADMIN" à tous les utilisateurs.
        // La convention de Spring Security est de préfixer les rôles avec "ROLE_".
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    // Les méthodes getUsername() et getPassword() sont générées par Lombok (@Data).
    // Tu n'as pas besoin de les écrire explicitement ici.

    @Override
    public boolean isAccountNonExpired() {
        // Retourne 'true' pour indiquer que le compte utilisateur n'est pas expiré.
        // Dans une application réelle, cela pourrait dépendre d'une date d'expiration.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Retourne 'true' pour indiquer que le compte utilisateur n'est pas verrouillé.
        // Cela peut devenir 'false' si un utilisateur échoue trop de tentatives de connexion.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Retourne 'true' pour indiquer que les informations d'authentification (mot de passe) ne sont pas expirées.
        // Peut être 'false' si tu forces les utilisateurs à changer leur mot de passe régulièrement.
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Retourne 'true' pour indiquer que l'utilisateur est activé.
        // Un utilisateur désactivé (false) ne peut pas se connecter.
        return true;
    }
}
