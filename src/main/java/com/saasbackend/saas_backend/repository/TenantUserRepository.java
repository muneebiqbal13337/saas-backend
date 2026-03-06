package com.saasbackend.saas_backend.repository;

import com.saasbackend.saas_backend.model.Tenant;
import com.saasbackend.saas_backend.model.TenantUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Repository for TenantUser data access.
 * All queries are scoped to a specific tenant — this is what
 * enforces data isolation between tenants at the database level.
 */
@Repository
public interface TenantUserRepository extends JpaRepository<TenantUser, Long> {

    /** Get all users belonging to a specific tenant. */
    List<TenantUser> findAllByTenant(Tenant tenant);

    /** Find a specific user by email within a tenant. */
    Optional<TenantUser> findByEmailAndTenant(String email, Tenant tenant);

    /** Check if email already exists within a tenant. */
    boolean existsByEmailAndTenant(String email, Tenant tenant);
}