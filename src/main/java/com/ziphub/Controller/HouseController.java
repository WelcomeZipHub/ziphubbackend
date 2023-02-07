package com.ziphub.Controller;

import com.ziphub.Entity.House;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/house")
public class HouseController {

    @PostMapping("/register")
    public ResponseEntity<String> addHouse(Authentication authentication) {
        return ResponseEntity.ok().body(authentication.getName() + " : your house added successfully");
    }
}
