package com.campaign.manager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request object for creating or updating a town")
public class TownRequestDTO {

    @NotBlank(message = "Town name must not be blank")
    @Schema(description = "Town name", example = "Krakow", required = true)
    private String name;
}


