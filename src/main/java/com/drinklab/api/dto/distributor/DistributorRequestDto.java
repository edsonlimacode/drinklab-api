package com.drinklab.api.dto.distributor;


import com.drinklab.api.dto.address.AddressRequestDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DistributorRequestDto {

    private String name;

    private String document;

    private AddressRequestDto address;

    private String contact;

    private String email;

    private Boolean active = true;

}
