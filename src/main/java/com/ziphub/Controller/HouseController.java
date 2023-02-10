package com.ziphub.Controller;
import com.ziphub.Form.HouseForm;
import com.ziphub.Service.HouseService;
import com.ziphub.Service.PhotoService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/house")
public class HouseController {

    private final HouseService houseService;
    private final PhotoService photoService;

    @PostMapping(value = "/register", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addHouse(@RequestPart("files") List<MultipartFile> imageFiles, @RequestPart("house") String houseInfo) {
        HouseForm form = houseService.getJson(houseInfo, imageFiles);
        Long houseId = houseService.addHouse(form);
        return ResponseEntity.ok().body("Successfully added" + String.valueOf(houseId));
    }

    @GetMapping("/photos")
    public byte[] getHousePhotos(@RequestParam("houseId") String houseId, @RequestParam("email") String email) {
        return photoService.downloadPhotos(houseId, email);
    }
}
