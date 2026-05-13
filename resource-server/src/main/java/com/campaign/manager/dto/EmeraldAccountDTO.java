package com.campaign.manager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Emerald account information")
public class EmeraldAccountDTO {

    @Schema(description = "Account ID", example = "1")
    private Long id;

    @Schema(description = "Owner's user ID", example = "1")
    private Long userId;

    @Schema(description = "Owner's username", example = "john_doe")
    private String username;

    @Schema(description = "Current balance", example = "2500.00")
    private BigDecimal balance;
}
