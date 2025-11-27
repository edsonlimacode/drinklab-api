package com.drinklab.api.dto.address;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequestDto {

    @NotBlank
    private String street;

    @NotBlank
    private String number;

    @NotBlank
    private String neighborhood;

    private String complement;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    private String zipCode;

}
