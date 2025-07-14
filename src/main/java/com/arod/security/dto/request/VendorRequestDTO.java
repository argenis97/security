package com.arod.security.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorRequestDTO {

    private String vendorName;
    private String vendorName2;

    private String vendorLastName;
    private String vendorLastName2;

    private String vendorTaxID;
}
