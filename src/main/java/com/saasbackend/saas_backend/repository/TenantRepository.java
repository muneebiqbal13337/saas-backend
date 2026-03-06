package com.saasbackend.saas_backend.repository;

import com.saasbackend.saas_backend.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository for Tenant data access.
 * Provides standard CRUD operations plus custom tenant-specific queries.
 */
@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    /** Find a tenant by their unique slug identifier. */
    Optional<Tenant> findBySlug(String slug);

    /** Check if a tenant with the given slug already exists. */
    boolean existsBySlug(String slug);

    /** Check if a tenant with the given email already exists. */
    boolean existsByEmail(String email);
}
