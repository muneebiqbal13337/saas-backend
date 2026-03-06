package com.saasbackend.saas_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Represents a user within a tenant organization.
 * Every user belongs to exactly one tenant — this is the core
 * of the multi-tenancy data isolation model.
 */
@Data
@Entity
@Table(name = "tenant_users",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email", "tenant_id"})
    })
public class TenantUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** First name of the user. */
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    @Column(nullable = false)
    private String firstName;

    /** Last name of the user. */
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    @Column(nullable = false)
    private String lastName;

    /** Email address — unique within a tenant. */
    @NotBlank(message = "Email is required")
    @Email(message = "Must be a valid email address")
    @Column(nullable = false)
    private String email;

    /** Role of the user within the tenant organization. */
    @NotBlank(message = "Role is required")
    @Column(nullable = false)
    private String role;

    /** Whether the user account is currently active. */
    @Column(nullable = false)
    private boolean active = true;

    /**
     * The tenant this user belongs to.
     * ManyToOne — many users can belong to one tenant.
     * This foreign key enforces data isolation between tenants.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    /** Timestamp when the user was created. Set automatically. */
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}