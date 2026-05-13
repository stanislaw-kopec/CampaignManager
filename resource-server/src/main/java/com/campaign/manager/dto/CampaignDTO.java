package com.campaign.manager.dto;

import com.campaign.manager.model.CampaignStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Schema(description = "Campaign data transfer object for responses")
public class CampaignDTO {

    @Schema(description = "Unique identifier of the campaign", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Name of the campaign", example = "Black Friday Sale", maxLength = 255)
    private String campaignName;

    @Schema(description = "Set of keywords associated with the campaign", example = "[\"discount\", \"sale\", \"promotion\"]")
    private Set<String> keywords;

    @Schema(description = "Bid amount per click", example = "0.50", minimum = "0.01")
    private BigDecimal bidAmount;

    @Schema(description = "Total budget allocated to the campaign", example = "1000.00", minimum = "0.01")
    private BigDecimal campaignFund;

    @Schema(description = "Current status of the campaign", example = "ON", allowableValues = {"ON", "OFF"})
    private CampaignStatus status;

    @Schema(description = "Town where the campaign is active", example = "Warsaw")
    private String town;

    @Schema(description = "Radius in kilometers for campaign targeting", example = "10", minimum = "1")
    private Integer radius;

    @Schema(description = "Username of the campaign owner", example = "john_doe")
    private String username;


}
