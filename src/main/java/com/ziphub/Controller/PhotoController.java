package com.ziphub.Controller;

import com.ziphub.Service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/photos")
public class PhotoController {

    private final PhotoService photoService;

    @GetMapping
    public byte[] getHousePhotos(@RequestParam("houseId") String houseId, @RequestParam("email") String email) {
        return photoService.downloadPhotos(houseId, email);
    }
}
