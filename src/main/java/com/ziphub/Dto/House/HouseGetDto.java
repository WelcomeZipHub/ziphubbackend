package com.ziphub.Dto.House;

import com.ziphub.Dto.PhotoDto;
import com.ziphub.Entity.Embedded.Address;
import com.ziphub.Entity.House;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.*;

@Data
public class HouseGetDto {
    private Long houseId;
    private Address address;
    private int price;
    private String description;
    private String email;
    private String phone;
    private LocalDateTime createdDate;
    private List<PhotoDto> photos;

    public HouseGetDto(House house){
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
