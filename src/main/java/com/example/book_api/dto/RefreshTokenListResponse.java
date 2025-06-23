package com.example.book_api.dto;

import com.example.book_api.model.RefreshToken;

import java.util.List;

public record RefreshTokenListResponse (List<String> refreshTokens) {
}
