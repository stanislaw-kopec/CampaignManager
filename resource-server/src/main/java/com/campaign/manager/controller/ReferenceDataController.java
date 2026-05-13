package com.campaign.manager.controller;

import com.campaign.manager.dto.EmeraldBalanceDTO;
import com.campaign.manager.model.Keyword;
import com.campaign.manager.model.Town;
import com.campaign.manager.model.User;
import com.campaign.manager.repository.UserRepository;
import com.campaign.manager.service.ReferenceDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reference-data")
@CrossOrigin(origins = "*")
@Tag(name = "Reference Data", description = "Manage reference data (towns, keywords, user balances)")
public class ReferenceDataController {

    private final ReferenceDataService referenceDataService;
    private final UserRepository userRepository;

    public ReferenceDataController(ReferenceDataService referenceDataService, UserRepository userRepository) {
        this.referenceDataService = referenceDataService;
        this.userRepository = userRepository;
    }

    @GetMapping("/towns")
    @Operation(summary = "Get all towns", description = "Returns a list of all available towns")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved towns list")
    public List<Town> getTowns() {
        return referenceDataService.getAllTowns();
    }

    @GetMapping("/keywords")
    @Operation(summary = "Search keywords", description = "Searches keywords by name (case-insensitive)")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved keywords")
    public List<Keyword> searchKeywords(
            @RequestParam(defaultValue = "") String search
    ) {
        return referenceDataService.searchKeywords(search);
    }

    @GetMapping("/users/{id}/balance")
    @Operation(summary = "Get user's emerald balance", description = "Returns the emerald balance for a specific user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Balance retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public EmeraldBalanceDTO getBalance(@PathVariable Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return new EmeraldBalanceDTO(
                user.getEmeraldAccount().getBalance()
        );
    }
}

