package com.campaign.manager.dto;

import com.campaign.manager.model.CampaignStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Schema(description = "Request object for creating or updating a campaign")
public class CampaignRequestDTO {

    @NotBlank
    @Schema(description = "Name of the campaign", example = "Summer Sale 2024", required = true, maxLength = 255)
    private String campaignName;

    @NotNull
    @Schema(description = "List of keyword IDs to associate with the campaign", example = "[1, 2, 3]", required = true)
    private Set<Long> keywordIds;

    @NotNull
    @DecimalMin("0.01")
    @Schema(description = "Bid amount per click", example = "0.75", required = true, minimum = "0.01")
    private BigDecimal bidAmount;

    @NotNull
    @DecimalMin("0.01")
    @Schema(description = "Total budget for the campaign", example = "5000.00", required = true, minimum = "0.01")
    private BigDecimal campaignFund;

    @NotNull
    @Schema(description = "Campaign status", example = "ON", required = true, allowableValues = {"ON", "OFF"})
    private CampaignStatus status;

    @NotNull
    @Schema(description = "ID of the town where campaign runs", example = "1", required = true)
    private Long townId;

    @NotNull
    @Min(1)
    @Schema(description = "Targeting radius in kilometers", example = "15", required = true, minimum = "1")
    private Integer radius;

    @NotNull
    @Schema(description = "ID of the user owning the campaign", example = "1", required = true)
    private Long userId;

}
