package com.arod.security.persistence.repository;

import com.arod.security.persistence.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
    
    @Query("select role from Role role" +
            " inner join role.permissions permission" +
            " where permission.id = :permissionID")
    List<Role> findByPermissionID(@Param("permissionID") Long id);
}
