package com.drinklab.api.dto.distributor;

import com.drinklab.api.dto.address.AddressRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DistributorRequestDto {

    @NotBlank
    private String name;

    @Size(min = 11, max = 14)
    @NotBlank
    private String document;

    @Valid
    @NotNull
    private AddressRequestDto address;

    @Size(min= 10, max = 11)
    @NotBlank
    private String contact;

    @Email
    @NotBlank
    private String email;
}
