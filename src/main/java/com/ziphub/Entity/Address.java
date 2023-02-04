package com.ziphub.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter
public class Address {
    private String city;
    private String street;
    private String suite;
    private String state;
    private String zipcode;
    private String longitude;
    private String latitude;
}
