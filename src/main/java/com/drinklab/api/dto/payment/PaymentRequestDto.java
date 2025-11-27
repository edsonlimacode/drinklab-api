package com.drinklab.api.dto.payment;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequestDto {

    @NotBlank
    private String name;

}
