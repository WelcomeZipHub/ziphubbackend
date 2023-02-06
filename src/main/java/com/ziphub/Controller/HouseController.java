package com.ziphub.Controller;

import com.ziphub.Entity.House;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/house")
public class HouseController {

    @PostMapping
    public ResponseEntity<House> addHouse() {
        House house = new House();
        return ResponseEntity.ok().body(house);
    }
}
