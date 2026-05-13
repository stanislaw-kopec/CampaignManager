package com.campaign.manager.controller;

import com.campaign.manager.dto.CampaignDTO;
import com.campaign.manager.dto.CampaignRequestDTO;
import com.campaign.manager.service.CampaignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
@CrossOrigin(origins = "*")
@Tag(name = "Campaigns", description = "Manage advertising campaigns")
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping
    @Operation(summary = "Get all campaigns", description = "Returns a list of all campaigns")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all campaigns")
    public List<CampaignDTO> getAllCampaigns() {
        return campaignService.getAllCampaigns();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get campaign by ID", description = "Returns a single campaign by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Campaign found successfully"),
            @ApiResponse(responseCode = "404", description = "Campaign not found")
    })
    public CampaignDTO getCampaign(@PathVariable Long id) {
        return campaignService.getCampaignById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new campaign", description = "Creates a new campaign with the provided data")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Campaign created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public CampaignDTO createCampaign(
            @Valid @RequestBody CampaignRequestDTO request
    ) {
        return campaignService.createCampaign(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing campaign", description = "Updates a campaign by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Campaign updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Campaign not found")
    })
    public CampaignDTO updateCampaign(
            @PathVariable Long id,
            @Valid @RequestBody CampaignRequestDTO request
    ) {
        return campaignService.updateCampaign(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a campaign", description = "Deletes a campaign by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Campaign deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Campaign not found")
    })
    public void deleteCampaign(@PathVariable Long id) {
        campaignService.deleteCampaign(id);
    }
}
