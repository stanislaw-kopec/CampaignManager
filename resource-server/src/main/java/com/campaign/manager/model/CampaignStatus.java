package com.campaign.manager.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Campaign status: ON - active campaign, OFF - suspended campaign")
public enum CampaignStatus {
    @Schema(description = "Campaign is active and running")
    ON,

    @Schema(description = "Campaign is suspended/inactive")
    OFF
}
