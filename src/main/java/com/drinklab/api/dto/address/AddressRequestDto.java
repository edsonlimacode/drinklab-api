package com.drinklab.api.dto.address;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @Size(min=8, max = 8)
    @NotBlank
    private String zipCode;

}
