package com.ziphub.Form;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@Getter
@AllArgsConstructor
public class HouseForm {

    private Long memberId;
    private int price;
    private String description;
    private List<MultipartFile> imageFiles;

    private String city;
    private String street;
    private String suite;
    private String state;
    private String zipcode;
    private String longitude;
    private String latitude;

}
