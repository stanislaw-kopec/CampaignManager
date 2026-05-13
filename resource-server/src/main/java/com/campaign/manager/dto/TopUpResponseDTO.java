package com.campaign.manager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response after topping up emerald balance")
public class TopUpResponseDTO {

    @Schema(description = "User ID", example = "1")
    private Long userId;

    @Schema(description = "Username", example = "john_doe")
    private String username;

    @Schema(description = "Amount that was added", example = "500.00")
    private BigDecimal amountAdded;

    @Schema(description = "Balance before top-up", example = "100.00")
    private BigDecimal balanceBefore;

    @Schema(description = "Balance after top-up", example = "600.00")
    private BigDecimal balanceAfter;

    @Schema(description = "Transaction timestamp")
    private LocalDateTime transactionTime;

}
