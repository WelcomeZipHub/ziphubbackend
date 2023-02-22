package com.ziphub.Dto;

import com.ziphub.Entity.Embedded.Address;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class HousePhotoDto {
    private Long memberId;
    private int price;
    private String description;
    private Address address;
    private boolean hide;
    List<MultipartFile> photos;

}
