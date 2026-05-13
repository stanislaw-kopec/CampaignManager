package com.campaign.manager.controller;

import com.campaign.manager.dto.TownRequestDTO;
import com.campaign.manager.model.Town;
import com.campaign.manager.service.TownService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/towns")
@CrossOrigin(origins = "*")
@Tag(name = "Towns", description = "Manage campaign towns/cities")
public class TownController {

    private final TownService townService;

    public TownController(TownService townService) {
        this.townService = townService;
    }

    @GetMapping
    @Operation(summary = "Get all towns", description = "Returns a list of all available towns")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved towns list")
    public List<Town> getAllTowns() {
        return townService.getAllTowns();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get town by ID", description = "Returns a single town by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Town found successfully"),
            @ApiResponse(responseCode = "404", description = "Town not found")
    })
    public Town getTown(@PathVariable Long id) {
        return townService.getTownById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new town", description = "Adds a new town to the database")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Town created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public Town createTown(@Valid @RequestBody TownRequestDTO request) {
        return townService.createTown(request.getName());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing town", description = "Updates the name of a specific town")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Town updated successfully"),
            @ApiResponse(responseCode = "404", description = "Town not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public Town updateTown(
            @PathVariable Long id,
            @Valid @RequestBody TownRequestDTO request
    ) {
        return townService.updateTown(id, request.getName());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a town", description = "Deletes a town by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Town deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Town not found")
    })
    public void deleteTown(@PathVariable Long id) {
        townService.deleteTown(id);
    }

    @GetMapping("/{id}/usage")
    @Operation(summary = "Check town usage", description = "Returns list of campaign IDs using this town")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usage information retrieved"),
            @ApiResponse(responseCode = "404", description = "Town not found")
    })
    public Map<String, Object> getTownUsage(@PathVariable Long id) {
        townService.getTownById(id);

        boolean inUse = townService.isTownInUse(id);
        List<Long> campaignIds = townService.getCampaignsUsingTown(id);

        return Map.of(
                "townId", id,
                "inUse", inUse,
                "campaignCount", campaignIds.size(),
                "campaignIds", campaignIds
        );
    }

}
