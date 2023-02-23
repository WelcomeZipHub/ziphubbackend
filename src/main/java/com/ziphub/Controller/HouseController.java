package com.ziphub.Controller;
import com.ziphub.Dto.House.HouseEditDto;
import com.ziphub.Dto.House.HouseGetDto;
import com.ziphub.Dto.House.HouseAddDto;
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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/house")
public class HouseController {

    private final HouseService houseService;
    private final HouseRepository houseRepository;

    @GetMapping("/all")
    public ResponseEntity<Result> getHouses(@RequestParam(value = "offset", defaultValue = "0") int offset, @RequestParam(value = "limit", defaultValue = "50") int limit) {
        List<HouseGetDto> data = houseService.getAll(offset, limit);
        return ResponseEntity.ok().body(new Result(data.size(), data));
    }

    @PostMapping(value = "/add", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addHouse(@RequestPart("imageFiles") List<MultipartFile> imageFiles, @RequestPart("house") String houseInfo) {
        HouseAddDto form = houseService.getJson(houseInfo, imageFiles);
        Long houseId = houseService.addHouse(form);
        return ResponseEntity.ok().body("Successfully added " + String.valueOf(houseId));
    }

    @PutMapping("/hide/{houseId}")
    public ResponseEntity<String> hideHouse(@PathVariable("houseId") Long houseId) {
        houseService.updateHouseHideStatus(houseId);
        return ResponseEntity.ok().body("Successfully update hide");
    }

    @PutMapping("/{houseId}")
    public ResponseEntity<HouseEditDto> editHouse(@RequestBody HouseEditDto houseEditDto, @PathVariable("houseId") Long houseId) {
        HouseEditDto data = houseService.editHouse(houseId, houseEditDto);
        return ResponseEntity.ok().body(data);
    }


    @Data
    @AllArgsConstructor
    static class Result {
        private int count;
        private List<HouseGetDto> data;
    }
}
