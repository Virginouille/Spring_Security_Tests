package com.example.book_api.controller;

import com.example.book_api.dto.RefreshTokenListResponse;
import com.example.book_api.dto.RefreshTokenRequest;
import com.example.book_api.dto.RefreshTokenResponse;
import com.example.book_api.model.RefreshToken;
import com.example.book_api.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/refresh_token")
@AllArgsConstructor
public class RefreshTokenController {

    //Injections de dépendances
    private final RefreshTokenService refreshTokenService;

    @PostMapping
    public ResponseEntity<RefreshTokenResponse> generate(@RequestBody RefreshTokenRequest refresh) {
        String refreshToken = refreshTokenService.generateRefreshToken(refresh.username());
        return ResponseEntity.ok(new RefreshTokenResponse(refreshToken));
    }

    @GetMapping
    public ResponseEntity<RefreshTokenListResponse> getRefreshToken(@RequestParam Long token) {
        List<String> refreshTokens = refreshTokenService.getRefreshTokens(token)
                .stream()
                .map(RefreshToken::getContenu)
                .toList();
        return ResponseEntity.ok(new RefreshTokenListResponse(refreshTokens));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long tokenId) {
        refreshTokenService.deleteRefreshToken(tokenId);
        return ResponseEntity.ok().build();
    }

}
