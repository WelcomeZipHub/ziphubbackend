package com.ziphub.Entity.Embedded;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class Address {
    private String city;
    private String street;
    private String suite;
    private String state;
    private String zipcode;
    private String longitude;
    private String latitude;
}
