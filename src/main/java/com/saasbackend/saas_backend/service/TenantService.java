package com.saasbackend.saas_backend.service;

import com.saasbackend.saas_backend.exception.DuplicateResourceException;
import com.saasbackend.saas_backend.exception.ResourceNotFoundException;
import com.saasbackend.saas_backend.model.Tenant;
import com.saasbackend.saas_backend.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for Tenant management.
 * Handles all business logic for creating and managing tenants.
 */
@Service
@RequiredArgsConstructor
public class TenantService {

    private final TenantRepository tenantRepository;

    /**
     * Creates a new tenant organization.
     * Validates that slug and email are unique before saving.
     *
     * @param tenant the tenant details to create
     * @return the created tenant
     * @throws DuplicateResourceException if slug or email already exists
     */
    public Tenant createTenant(Tenant tenant) {
        if (tenantRepository.existsBySlug(tenant.getSlug())) {
            throw new DuplicateResourceException("Tenant", "slug: " + tenant.getSlug());
        }
        if (tenantRepository.existsByEmail(tenant.getEmail())) {
            throw new DuplicateResourceException("Tenant", "email: " + tenant.getEmail());
        }
        return tenantRepository.save(tenant);
    }

    /**
     * Retrieves all tenants in the system.
     *
     * @return list of all tenants
     */
    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    /**
     * Retrieves a tenant by their unique slug.
     *
     * @param slug the tenant's unique slug identifier
     * @return the tenant if found
     * @throws ResourceNotFoundException if tenant does not exist
     */
    public Tenant getTenantBySlug(String slug) {
        return tenantRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "slug: " + slug));
    }

    /**
     * Retrieves a tenant by their ID.
     *
     * @param id the tenant ID
     * @return the tenant if found
     * @throws ResourceNotFoundException if tenant does not exist
     */
    public Tenant getTenantById(Long id) {
        return tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "id: " + id));
    }

    /**
     * Deactivates a tenant account without deleting their data.
     *
     * @param id the tenant ID to deactivate
     * @return the deactivated tenant
     * @throws ResourceNotFoundException if tenant does not exist
     */
    public Tenant deactivateTenant(Long id) {
        Tenant tenant = getTenantById(id);
        tenant.setActive(false);
        return tenantRepository.save(tenant);
    }
}