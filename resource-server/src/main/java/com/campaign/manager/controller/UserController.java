package com.campaign.manager.controller;

import com.campaign.manager.dto.*;
import com.campaign.manager.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@Tag(name = "Users", description = "Manage users and their emerald accounts")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Returns a list of all users with their account status")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved users list")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Returns a single user with account information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User found successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public UserDTO getUser(@PathVariable Long id) {
        return userService.getUserById(id);
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided username")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Username already exists")
    })
    public UserDTO createUser(@Valid @RequestBody CreateUserRequestDTO request) {
        return userService.createUser(request.getUsername());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Updates the username of an existing user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "409", description = "Username already taken")
    })
    public UserDTO updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequestDTO request
    ) {
        return userService.updateUser(id, request.getUsername());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete user", description = "Deletes a user and all associated data")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/{id}/top-up")
    @Operation(summary = "Top up emerald balance", description = "Adds funds to user's emerald account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Balance topped up successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid amount or no emerald account"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public TopUpResponseDTO topUpBalance(
            @PathVariable Long id,
            @Valid @RequestBody TopUpRequestDTO request
    ) {
        return userService.topUpBalance(id, request.getAmount());
    }

    @PostMapping("/{id}/emerald-account")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create emerald account for user", description = "Creates a new emerald account for the specified user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Account created successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "409", description = "User already has an account")
    })
    public com.campaign.manager.dto.EmeraldAccountDTO createEmeraldAccount(
            @PathVariable Long id
    ) {
        return userService.createEmeraldAccount(id, java.math.BigDecimal.ZERO);
    }

}
