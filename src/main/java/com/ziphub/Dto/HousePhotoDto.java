package com.ziphub.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
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
