package com.arod.security.persistence.repository;

import com.arod.security.persistence.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {

    Optional<Vendor> findByName(String name);

    Optional<Vendor> findByTaxID(String taxID);
}
