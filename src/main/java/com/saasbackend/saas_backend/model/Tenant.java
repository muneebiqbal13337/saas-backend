package com.saasbackend.saas_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Represents a tenant (organization) in the multi-tenant SaaS system.
 * Each tenant has isolated data and their own subscription plan.
 */
@Data
@Entity
@Table(name = "tenants")
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique identifier for the tenant used in API requests.
     * Example: "acme-corp", "globex-inc"
     */
    @NotBlank(message = "Tenant slug is required")
    @Size(min = 2, max = 50, message = "Slug must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "Slug must contain only lowercase letters, numbers, and hyphens")
    @Column(unique = true, nullable = false)
    private String slug;

    /** Display name of the tenant organization. */
    @NotBlank(message = "Company name is required")
    @Size(min = 2, max = 100, message = "Company name must be between 2 and 100 characters")
    @Column(nullable = false)
    private String companyName;

    /** Primary contact email for the tenant. */
    @NotBlank(message = "Email is required")
    @Email(message = "Must be a valid email address")
    @Column(unique = true, nullable = false)
    private String email;

    /** Subscription plan for the tenant. */
    @NotBlank(message = "Plan is required")
    @Column(nullable = false)
    private String plan;

    /** Whether the tenant account is currently active. */
    @Column(nullable = false)
    private boolean active = true;

    /** Timestamp when the tenant was created. Set automatically. */
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /** Timestamp when the tenant was last updated. */
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}