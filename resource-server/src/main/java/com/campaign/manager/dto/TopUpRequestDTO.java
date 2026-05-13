package com.campaign.manager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Schema(description = "Request object for topping up emerald balance")
public class TopUpRequestDTO {

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Minimum top-up amount is 0.01")
    @DecimalMax(value = "1000000.00", message = "Maximum top-up amount is 1,000,000.00")
    @Schema(description = "Amount to add to the account", example = "500.00", required = true, minimum = "0.01", maximum = "1000000.00")
    private BigDecimal amount;
}
