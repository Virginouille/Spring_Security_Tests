package com.example.book_api.controller;

import com.example.book_api.model.RefreshToken;
import com.example.book_api.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/refresh_token")
@AllArgsConstructor
public class RefreshTokenController {

    //Injections de dépendances
    private final RefreshTokenService refreshTokenService;

    private record RefreshTokenRequest(String username) {}

    @PostMapping("/generate_refresh_token")
    public ResponseEntity<String> generate(@RequestBody RefreshTokenRequest refresh) {
        String refreshToken = refreshTokenService.generateRefreshToken(refresh.username());
        return ResponseEntity.ok(refreshToken);
    }

}
