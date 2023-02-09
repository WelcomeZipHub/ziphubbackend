package com.ziphub.Controller;

import com.ziphub.Entity.House;
import com.ziphub.Form.HouseForm;
import com.ziphub.Service.HouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/house")
public class HouseController {

    private final HouseService houseService;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addHouse(@RequestParam("imageFiles") List<MultipartFile> photos) throws IOException {
        HouseForm form = new HouseForm();
        form.setMemberId(1L);
        form.setImages(photos);
        Long houseId = houseService.addHouse(form);
        return ResponseEntity.ok().body("Successfully added" + String.valueOf(houseId));
    }
}
