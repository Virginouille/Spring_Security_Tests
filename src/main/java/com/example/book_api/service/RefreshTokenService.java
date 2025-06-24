package com.example.book_api.service;

import com.example.book_api.model.RefreshToken;
import com.example.book_api.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RefreshTokenService extends JwtService {

    //On récupère userDetailsService et tokenRepository
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    //Expiration time 7 jours
    public static final long EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 7;

    /**Méthode generation refresh token qui récupère la méthode de jwtservice*/
    public String generateRefreshToken(String username) {
        return this.generateToken(username, EXPIRATION_TIME);
    }

    /**Méthode qui vérifie si le refresh token est toujours valide
     * en reprenant la méthode dans jwtservice isTokenValid()
     */
    public boolean isValidRefreshToken(String refreshToken, String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        boolean jwtStructureValid = this.isTokenValid(refreshToken, userDetails);

        Optional<RefreshToken> storedTokenOpt = tokenRepository.findByContenu(refreshToken);

        return jwtStructureValid
                && storedTokenOpt.isPresent();
    }

    /**Méthode suppression RefreshToken*/
    public void deleteRefreshToken(Long token) {
       tokenRepository.deleteById(token);
    }
    /**Méthode des tokens actifs par utilisateur*/
    public List<RefreshToken> getRefreshTokens(Long userId) {
        return tokenRepository.findByUserId_AndRevokedFalse(userId);
    }

}
