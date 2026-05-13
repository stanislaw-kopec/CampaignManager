package com.campaign.manager.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "towns")
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Town entity representing geographic location")
public class Town {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Town unique identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Schema(description = "Town name", example = "Warsaw", required = true)
    private String name;

    public Town(String name) {
        this.name = name;
    }

}
