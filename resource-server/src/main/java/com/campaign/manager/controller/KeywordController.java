package com.campaign.manager.controller;

import com.campaign.manager.dto.KeywordRequestDTO;
import com.campaign.manager.model.Keyword;
import com.campaign.manager.service.KeywordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/keywords")
@CrossOrigin(origins = "*")
@Tag(name = "Keywords", description = "Manage campaign keywords")
public class KeywordController {

    private final KeywordService keywordService;

    public KeywordController(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    @GetMapping
    @Operation(summary = "Get all keywords", description = "Returns a list of all keywords, optionally filtered by name")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved keywords")
    public List<Keyword> getKeywords(
            @RequestParam(required = false) String search
    ) {
        if (search != null && !search.isBlank()) {
            return keywordService.searchKeywords(search);
        }
        return keywordService.getAllKeywords();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get keyword by ID", description = "Returns a single keyword by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Keyword found successfully"),
            @ApiResponse(responseCode = "404", description = "Keyword not found")
    })
    public Keyword getKeyword(@PathVariable Long id) {
        return keywordService.getKeywordById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new keyword", description = "Adds a new keyword to the database")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Keyword created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public Keyword createKeyword(@Valid @RequestBody KeywordRequestDTO request) {
        return keywordService.createKeyword(request.getName());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing keyword", description = "Updates the name of a specific keyword")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Keyword updated successfully"),
            @ApiResponse(responseCode = "404", description = "Keyword not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public Keyword updateKeyword(
            @PathVariable Long id,
            @Valid @RequestBody KeywordRequestDTO request
    ) {
        return keywordService.updateKeyword(id, request.getName());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a keyword", description = "Deletes a keyword by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Keyword deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Keyword not found")
    })
    public void deleteKeyword(@PathVariable Long id) {
        keywordService.deleteKeyword(id);
    }

}
