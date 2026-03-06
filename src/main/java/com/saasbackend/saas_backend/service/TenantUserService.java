package com.saasbackend.saas_backend.service;

import com.saasbackend.saas_backend.exception.DuplicateResourceException;
import com.saasbackend.saas_backend.exception.ResourceNotFoundException;
import com.saasbackend.saas_backend.model.Tenant;
import com.saasbackend.saas_backend.model.TenantUser;
import com.saasbackend.saas_backend.repository.TenantUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for TenantUser management.
 * All operations are scoped to a specific tenant — users from
 * one tenant can never access or interfere with another tenant's data.
 */
@Service
@RequiredArgsConstructor
public class TenantUserService {

    private final TenantUserRepository tenantUserRepository;
    private final TenantService tenantService;

    /**
     * Creates a new user within a specific tenant.
     * Validates that email is unique within the tenant.
     *
     * @param tenantSlug the slug of the tenant to create user in
     * @param user the user details to create
     * @return the created user
     * @throws DuplicateResourceException if email already exists in tenant
     * @throws ResourceNotFoundException if tenant does not exist
     */
    public TenantUser createUser(String tenantSlug, TenantUser user) {
        Tenant tenant = tenantService.getTenantBySlug(tenantSlug);

        if (tenantUserRepository.existsByEmailAndTenant(user.getEmail(), tenant)) {
            throw new DuplicateResourceException("User",
                    "email: " + user.getEmail() + " in tenant: " + tenantSlug);
        }

        user.setTenant(tenant);
        return tenantUserRepository.save(user);
    }

    /**
     * Retrieves all users belonging to a specific tenant.
     * Users from other tenants are never returned — full isolation.
     *
     * @param tenantSlug the slug of the tenant
     * @return list of users in the tenant
     * @throws ResourceNotFoundException if tenant does not exist
     */
    public List<TenantUser> getUsersByTenant(String tenantSlug) {
        Tenant tenant = tenantService.getTenantBySlug(tenantSlug);
        return tenantUserRepository.findAllByTenant(tenant);
    }

    /**
     * Retrieves a specific user by ID within a tenant.
     * Ensures the user actually belongs to the requested tenant.
     *
     * @param tenantSlug the slug of the tenant
     * @param userId the ID of the user
     * @return the user if found within the tenant
     * @throws ResourceNotFoundException if user not found in tenant
     */
    public TenantUser getUserById(String tenantSlug, Long userId) {
        Tenant tenant = tenantService.getTenantBySlug(tenantSlug);
        return tenantUserRepository.findById(userId)
                .filter(user -> user.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("User",
                        "id: " + userId + " in tenant: " + tenantSlug));
    }

    /**
     * Deactivates a user within a tenant without deleting their data.
     *
     * @param tenantSlug the slug of the tenant
     * @param userId the ID of the user to deactivate
     * @return the deactivated user
     * @throws ResourceNotFoundException if user not found in tenant
     */
    public TenantUser deactivateUser(String tenantSlug, Long userId) {
        TenantUser user = getUserById(tenantSlug, userId);
        user.setActive(false);
        return tenantUserRepository.save(user);
    }
}