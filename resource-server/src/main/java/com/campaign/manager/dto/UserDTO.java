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
@Schema(description = "User data transfer object with account information")
public class UserDTO {

    @Schema(description = "User ID", example = "1")
    private Long id;

    @Schema(description = "Username", example = "john_doe")
    private String username;

    @Schema(description = "Whether user has an emerald account", example = "true")
    private boolean hasEmeraldAccount;

    @Schema(description = "Current emerald balance (null if no account)", example = "1500.00")
    private BigDecimal emeraldBalance;

    @Schema(description = "Number of campaigns owned by user", example = "5")
    private int campaignCount;

}
