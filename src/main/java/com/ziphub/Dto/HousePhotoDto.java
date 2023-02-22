package com.ziphub.Dto;


import com.ziphub.Entity.House;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class HousePhotoDto {

    @NotEmpty
    private Long memberId;

    @NotEmpty
    private int price;
    private String description;

    @NotEmpty
    private boolean hide;

    @NotEmpty
    private String city;

    @NotEmpty
    private String street;

    @NotEmpty
    private String suite;

    @NotEmpty
    private String state;

    @NotEmpty
    private String zipcode;

    @NotEmpty
    private String longitude;

    @NotEmpty
    private String latitude;

    @NotEmpty
    List<MultipartFile> photos;

}
