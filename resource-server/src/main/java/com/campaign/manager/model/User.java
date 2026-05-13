package com.campaign.manager.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "User entity representing a system user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "User unique identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Schema(description = "Username", example = "john_doe", required = true)
    private String username;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @Schema(description = "User's emerald account")
    private EmeraldAccount emeraldAccount;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Schema(description = "List of user's campaigns")
    private List<Campaign> campaigns = new ArrayList<>();

    public User(String username) {
        this.username = username;
    }
}
