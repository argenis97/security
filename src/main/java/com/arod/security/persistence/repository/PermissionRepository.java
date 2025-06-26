package com.arod.security.persistence.repository;

import com.arod.security.persistence.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<RolePermission, Long> {
}
