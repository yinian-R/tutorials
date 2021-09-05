package com.wymm.mapstruct.multiple;

import lombok.Data;

@Data
public class DeliveryAddress {
    private String forename;
    private String surname;
    private String street;
    private String postalcode;
    private String county;
}
