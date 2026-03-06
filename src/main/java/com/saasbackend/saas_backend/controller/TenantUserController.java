package com.saasbackend.saas_backend.controller;

import com.saasbackend.saas_backend.model.TenantUser;
import com.saasbackend.saas_backend.service.TenantUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing users within a tenant.
 * All endpoints are scoped to a specific tenant via the slug path variable.
 * Base URL: /api/tenants/{tenantSlug}/users
 */
@RestController
@RequestMapping("/api/tenants/{tenantSlug}/users")
@RequiredArgsConstructor
public class TenantUserController {

    private final TenantUserService tenantUserService;

    /**
     * Creates a new user within a specific tenant.
     *
     * @param tenantSlug the tenant slug from URL path
     * @param user the user details from request body
     * @return the created user with HTTP 201
     */
    @PostMapping
    public ResponseEntity<TenantUser> createUser(
            @PathVariable String tenantSlug,
            @Valid @RequestBody TenantUser user) {
        TenantUser created = tenantUserService.createUser(tenantSlug, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves all users belonging to a specific tenant.
     *
     * @param tenantSlug the tenant slug from URL path
     * @return list of tenant users with HTTP 200
     */
    @GetMapping
    public ResponseEntity<List<TenantUser>> getUsersByTenant(
            @PathVariable String tenantSlug) {
        return ResponseEntity.ok(tenantUserService.getUsersByTenant(tenantSlug));
    }

    /**
     * Retrieves a specific user by ID within a tenant.
     *
     * @param tenantSlug the tenant slug from URL path
     * @param userId the user ID from URL path
     * @return the user with HTTP 200
     */
    @GetMapping("/{userId}")
    public ResponseEntity<TenantUser> getUserById(
            @PathVariable String tenantSlug,
            @PathVariable Long userId) {
        return ResponseEntity.ok(tenantUserService.getUserById(tenantSlug, userId));
    }

    /**
     * Deactivates a user within a tenant.
     *
     * @param tenantSlug the tenant slug from URL path
     * @param userId the user ID from URL path
     * @return the deactivated user with HTTP 200
     */
    @PatchMapping("/{userId}/deactivate")
    public ResponseEntity<TenantUser> deactivateUser(
            @PathVariable String tenantSlug,
            @PathVariable Long userId) {
        return ResponseEntity.ok(tenantUserService.deactivateUser(tenantSlug, userId));
    }
}