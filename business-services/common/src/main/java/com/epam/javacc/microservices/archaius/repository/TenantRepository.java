package com.epam.javacc.microservices.archaius.repository;

import com.epam.javacc.microservices.archaius.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

}
