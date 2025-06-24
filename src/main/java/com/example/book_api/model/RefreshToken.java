package com.example.book_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="refresh_token")
@Data
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="contenu")
    private String contenu;

    @Column(name="revoked")
    private boolean revoked;
}
