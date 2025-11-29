package com.drinklab.api.dto.distributor;


import com.drinklab.api.dto.address.AddressRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DistributorRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String document;

    @Valid
    @NotNull
    private AddressRequestDto address;

    @NotBlank
    private String contact;

    @NotBlank
    private String email;
}
