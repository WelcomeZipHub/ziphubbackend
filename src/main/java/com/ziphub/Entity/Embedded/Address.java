package com.ziphub.Entity.Embedded;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
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
