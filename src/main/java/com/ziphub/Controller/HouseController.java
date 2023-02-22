package com.ziphub.Controller;
import com.ziphub.Dto.HouseDto;
import com.ziphub.Dto.HousePhotoDto;
import com.ziphub.Entity.House;
import com.ziphub.Repository.HouseRepository;
import com.ziphub.Service.HouseService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

import static java.util.stream.Collectors.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/house")
public class HouseController {

    private final HouseService houseService;
    private final HouseRepository houseRepository;

    @GetMapping("/all")
    public ResponseEntity<Result> getAllHouses(@RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "limit", defaultValue = "50") int limit) {
        List<House> houses = houseRepository.findAllWithMember(offset, limit);
        List<HouseDto> data = houses.stream()
                .map(HouseDto::new)
                .collect(toList());
        return ResponseEntity.ok().body(new Result(data.size(), data));
    }

    @PostMapping(value = "/register", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addHouse(@RequestPart("files") List<MultipartFile> imageFiles, @RequestPart("house") String houseInfo) {
        HousePhotoDto form = houseService.getJson(houseInfo, imageFiles);
        Long houseId = houseService.addHouse(form);
        return ResponseEntity.ok().body("Successfully added " + String.valueOf(houseId));
    }

    @PutMapping("/hide/{houseId}")
    public ResponseEntity<String> hideHouse(@PathVariable("houseId") Long houseId) {
        houseService.updateHouseHideStatus(houseId);
        return ResponseEntity.ok().body("Successfully update hide");
    }

    @PutMapping("/{houseId}")
    public ResponseEntity<HouseDto> editHouse(@RequestBody HouseDto houseReqDto, @PathVariable("houseId") Long houseId) {
        HouseDto houseDto = houseService.editHouse(houseId, houseReqDto);
        return ResponseEntity.ok().body(houseDto);
    }



    @Data
    @AllArgsConstructor
    static class Result {
        private int count;
        private List<HouseDto> data;
    }
}
