package com.saasbackend.saas_backend.controller;

import com.saasbackend.saas_backend.model.Tenant;
import com.saasbackend.saas_backend.service.TenantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing tenant organizations.
 * Base URL: /api/tenants
 */
@RestController
@RequestMapping("/api/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    /**
     * Creates a new tenant organization.
     *
     * @param tenant the tenant details from request body
     * @return the created tenant with HTTP 201
     */
    @PostMapping
    public ResponseEntity<Tenant> createTenant(@Valid @RequestBody Tenant tenant) {
        Tenant created = tenantService.createTenant(tenant);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Retrieves all tenant organizations.
     *
     * @return list of all tenants with HTTP 200
     */
    @GetMapping
    public ResponseEntity<List<Tenant>> getAllTenants() {
        return ResponseEntity.ok(tenantService.getAllTenants());
    }

    /**
     * Retrieves a tenant by their unique slug.
     *
     * @param slug the tenant slug from URL path
     * @return the tenant with HTTP 200
     */
    @GetMapping("/{slug}")
    public ResponseEntity<Tenant> getTenantBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(tenantService.getTenantBySlug(slug));
    }

    /**
     * Deactivates a tenant account.
     *
     * @param id the tenant ID from URL path
     * @return the deactivated tenant with HTTP 200
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Tenant> deactivateTenant(@PathVariable Long id) {
        return ResponseEntity.ok(tenantService.deactivateTenant(id));
    }
}