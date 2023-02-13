package com.ziphub.Dto;

import com.ziphub.Entity.Embedded.Address;
import com.ziphub.Entity.House;
import com.ziphub.Entity.Member;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Data
public class HouseDto {
    private Long houseId;
    private Address address;
    private int price;
    private String description;
    private String email;
    private String phone;
    private LocalDateTime createdDate;
    private List<PhotoDto> photos;

    public HouseDto(House house){
        houseId = house.getId();
        address = house.getAddress();
        price = house.getPrice();
        description = house.getDescription();
        email = house.getMember().getEmail();
        phone = house.getMember().getPhone();
        createdDate = house.getCreatedDate();
        photos = house.getPhotos().stream()
                .map(PhotoDto::new)
                .collect(toList());
    }
}
