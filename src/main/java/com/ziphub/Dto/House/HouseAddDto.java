package com.ziphub.Dto.House;

import com.ziphub.Entity.Embedded.Address;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class HouseAddDto {
    private Long memberId;
    private int price;
    private String description;
    private Address address;
    private boolean hide;
    List<MultipartFile> photos;

}
