package com.campaign.manager.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "emerald_accounts")
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Emerald account entity for storing user currency balance")
public class EmeraldAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Account unique identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Schema(description = "User associated with this account", required = true)
    private User user;

    @DecimalMin(value = "0.00")
    @Column(nullable = false, precision = 12, scale = 2)
    @Schema(description = "Current emerald balance", example = "2500.00", minimum = "0.00")
    private BigDecimal balance;

    public EmeraldAccount(User user, BigDecimal balance) {
        this.user = user;
        this.balance = balance;
    }
}
