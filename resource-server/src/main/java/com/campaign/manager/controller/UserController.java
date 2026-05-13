package com.campaign.manager.controller;

import com.campaign.manager.dto.EmeraldBalanceDTO;
import com.campaign.manager.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@Tag(name = "Users", description = "Manage user data and balances")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}/balance")
    @Operation(summary = "Get user's emerald balance", description = "Returns the emerald balance for a specific user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Balance retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public EmeraldBalanceDTO getBalance(@PathVariable Long id) {
        return userService.getUserBalance(id);
    }

}
