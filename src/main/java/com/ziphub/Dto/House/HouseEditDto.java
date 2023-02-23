package com.ziphub.Dto.House;

import com.ziphub.Entity.Embedded.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
public class HouseEditDto {
    private Address address;
    private int price;
    private String description;
    private List<MultipartFile> photos;
}
