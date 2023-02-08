package com.ziphub.Controller;

import com.ziphub.Entity.House;
import com.ziphub.Entity.Photo;
import com.ziphub.Form.HouseForm;
import com.ziphub.Service.HouseService;
import com.ziphub.Service.PhotoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/house")
public class HouseController {

    private final HouseService houseService;

    @PostMapping("/register")
    public ResponseEntity<House> addHouse(@RequestBody HouseForm form) throws IOException {
        House newHouse = houseService.addHouse(form);
        return ResponseEntity.ok().body(newHouse);
    }
}
