package com.campaign.manager.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "campaigns")
@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Campaign entity representing an advertising campaign")
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    @Schema(description = "Campaign name", example = "Weekend Flash Sale", required = true)
    private String campaignName;

    @ManyToMany
    @JoinTable(
            name = "campaign_keywords",
            joinColumns = @JoinColumn(name = "campaign_id"),
            inverseJoinColumns = @JoinColumn(name = "keyword_id")
    )
    @Schema(description = "Set of keywords associated with the campaign")
    private Set<Keyword> keywords = new HashSet<>();

    @NotNull
    @DecimalMin(value = "0.01")
    @Column(nullable = false, precision = 12, scale = 2)
    @Schema(description = "Bid amount per click", example = "0.50", required = true, minimum = "0.01")
    private BigDecimal bidAmount;

    @NotNull
    @DecimalMin(value = "0.01")
    @Column(nullable = false, precision = 12, scale = 2)
    @Schema(description = "Total campaign budget", example = "1000.00", required = true, minimum = "0.01")
    private BigDecimal campaignFund;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Campaign status", example = "ON", required = true)
    private CampaignStatus status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "town_id")
    @Schema(description = "Town where campaign is active", required = true)
    private Town town;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    @Schema(description = "Targeting radius in kilometers", example = "10", required = true, minimum = "1")
    private Integer radius;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    @Schema(description = "User who owns the campaign", required = true)
    private User user;

    @Schema(description = "Creation timestamp", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(description = "Last update timestamp", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
