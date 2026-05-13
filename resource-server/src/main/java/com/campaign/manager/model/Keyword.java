package com.campaign.manager.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "keywords")
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Keyword entity for campaign targeting")
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Keyword unique identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Schema(description = "Keyword name", example = "discount", required = true)
    private String name;

    public Keyword(String name) {
        this.name = name;
    }
}
