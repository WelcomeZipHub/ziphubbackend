package com.ziphub.Dto;

import com.ziphub.Entity.Embedded.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
public class HouseBasicInfoDto {
    private Address address;
    private int price;
    private String description;
    private List<MultipartFile> photos;
}
