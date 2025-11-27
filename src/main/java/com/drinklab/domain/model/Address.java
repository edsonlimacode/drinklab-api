package com.drinklab.domain.model;


import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Address {

    private String street;
    private String number;
    private String neighborhood;
    private String complement;
    private String city;
    private String state;
    private String zipCode;

}
