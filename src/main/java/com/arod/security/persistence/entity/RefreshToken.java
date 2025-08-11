package com.arod.security.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "refresh-token", schema = "application"
        , indexes = {@Index(name = "uq_token", unique = true, columnList = "token")})
public class RefreshToken {

    @Id
    @GeneratedValue(generator = "refresh_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "refresh_gen", schema = "application", sequenceName = "refresh_seq")
    private Long id;

    @Column(nullable = false)
    private LocalDateTime genDate;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private Long attempts;

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    @Column(nullable = false)
    private boolean isRevoked;

    @ManyToOne
    @JoinColumn(name = "userID", nullable = false)
    private AppUser user;
}
