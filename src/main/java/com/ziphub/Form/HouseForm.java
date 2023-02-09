package com.ziphub.Form;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class HouseForm {

    private Long memberId;
    private int price;
    private List<MultipartFile> images;
    private String description;
    private String city;
    private String street;
    private String suite;
    private String state;
    private String zipcode;
    private String longitude;
    private String latitude;

}
