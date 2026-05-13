package com.campaign.manager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "User's emerald balance response")
public class EmeraldBalanceDTO {

    @Schema(description = "Current emerald balance", example = "1250.50", minimum = "0.00")
    private BigDecimal balance;

}
