package com.arod.security.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorResponseDTO {
    private Long vendorID;

    private String vendorName;
    private String vendorName2;

    private String vendorLastName;
    private String vendorLastName2;

    private String vendorTaxID;
}
