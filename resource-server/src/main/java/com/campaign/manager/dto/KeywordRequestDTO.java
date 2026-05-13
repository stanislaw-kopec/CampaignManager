package com.campaign.manager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request object for creating or updating a keyword")
public class KeywordRequestDTO {
    @NotBlank(message = "Keyword name must not be blank")
    @Schema(description = "Keyword name", example = "discount", required = true)
    private String name;
}
