package com.ziphub.Dto;

import com.ziphub.Entity.Embedded.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HouseBasicInfoDto {
    private Address address;
    private int price;
    private String description;
}
