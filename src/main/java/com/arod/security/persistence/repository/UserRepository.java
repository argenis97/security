package com.arod.security.persistence.repository;

import com.arod.security.persistence.entity.AppUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    @Query("select usr from AppUser usr inner join usr.role role where role.id = :roleID")
    List<AppUser> findByRole(@Param("roleID") Long roleID);

    @Query("select usr from AppUser usr" +
            " inner join usr.role role" +
            " inner join role.permissions permission" +
            " where permission.id = :permissionID")
    List<AppUser> findByPermission(@Param("permissionID") Long permID);

    Optional<AppUser> findByName(String name);

    @Query("select usr from AppUser usr where usr.id = :id")
    @EntityGraph(attributePaths = {})
    Optional<AppUser> findByIdLazy(@Param("id") Long id);
}
