package com.example.book_api.controller;

import com.example.book_api.dto.RefreshTokenRequest;
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

    @PostMapping
    public ResponseEntity<String> generate(@RequestBody RefreshTokenRequest refresh) {
        String refreshToken = refreshTokenService.generateRefreshToken(refresh.username());
        return ResponseEntity.ok(refreshToken);
    }

}
