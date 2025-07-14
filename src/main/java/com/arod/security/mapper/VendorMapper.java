package com.arod.security.mapper;

import com.arod.security.dto.request.VendorRequestDTO;
import com.arod.security.dto.response.VendorResponseDTO;
import com.arod.security.persistence.entity.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VendorMapper {

    @Mapping(source = "vendorTaxID", target = "taxID")
    @Mapping(source = "vendorName", target = "name")
    @Mapping(source = "vendorName2", target = "name2")
    @Mapping(source = "vendorLastName", target = "lastName")
    @Mapping(source = "vendorLastName2", target = "lastName2")
    Vendor toEntity(VendorRequestDTO request);

    @Mapping(source = "id", target = "vendorID")
    @Mapping(source = "taxID", target = "vendorTaxID")
    @Mapping(source = "name", target = "vendorName")
    @Mapping(source = "name2", target = "vendorName2")
    @Mapping(source = "lastName", target = "vendorLastName")
    @Mapping(source = "lastName2", target = "vendorLastName2")
    VendorResponseDTO toResponse(Vendor vendor);

    @Mapping(source = "vendorTaxID", target = "taxID")
    @Mapping(source = "vendorName", target = "name")
    @Mapping(source = "vendorName2", target = "name2")
    @Mapping(source = "vendorLastName", target = "lastName")
    @Mapping(source = "vendorLastName2", target = "lastName2")
    void update(VendorRequestDTO request, @MappingTarget Vendor vendor);

    List<VendorResponseDTO> toListResponse(List<Vendor> vendors);
}
