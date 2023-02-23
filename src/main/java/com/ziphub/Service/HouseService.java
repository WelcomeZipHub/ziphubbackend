package com.ziphub.Service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ziphub.Dto.House.HouseEditDto;
import com.ziphub.Dto.House.HouseGetDto;
import com.ziphub.Entity.House;
import com.ziphub.Entity.Member;
import com.ziphub.Entity.Photo;
import com.ziphub.Dto.House.HouseAddDto;
import com.ziphub.Repository.HouseRepository;

import com.ziphub.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class HouseService {

    private final MemberRepository memberRepository;
    private final HouseRepository houseRepository;
    private final PhotoService photoService;

    @Transactional
    public List<HouseGetDto> getAll(int offset, int limit) {
        List<House> houses = houseRepository.findAll(offset, limit);
        return houses.stream()
                .map(HouseGetDto::new)
                .collect(toList());
    }

    @Transactional
    public Long addHouse(HouseAddDto form) {
        Member member = memberRepository.findOne(form.getMemberId());
        House newHouse = new House();
        newHouse.setMember(member);
        newHouse.setAddress(form.getAddress());
        newHouse.setDescription(form.getDescription());
        newHouse.setPrice(form.getPrice());
        newHouse.setCreatedDate(LocalDateTime.now());
        newHouse.setHide(form.isHide());

        Long houseId = houseRepository.save(newHouse);

        String uniqueId = member.getEmail() + "-h" + houseId;
        List<Photo> photos = photoService.savePhotos(form.getPhotos(), uniqueId);
        photos.stream().forEach(p -> newHouse.addPhoto(p));

        return houseId;
    }

    @Transactional
    public HouseEditDto editHouse(Long houseId, HouseEditDto houseEditDto) {
        House findHouse = houseRepository.findOne(houseId);
        log.info("address INFO: {}", houseEditDto);
        findHouse.setAddress(houseEditDto.getAddress());
        if(houseEditDto.getPrice() != findHouse.getPrice()) {
            findHouse.setPrice(houseEditDto.getPrice());
        }
        if(!houseEditDto.getDescription().equals(findHouse.getDescription())) {
            findHouse.setDescription(houseEditDto.getDescription());
        }
        return houseEditDto;
    }

    @Transactional
    public void updateHouseHideStatus(Long houseId) {
        House house = houseRepository.findOne(houseId);
        house.setHide(!house.isHide());
    }

    public HouseAddDto getJson(String houseInfo, List<MultipartFile> files) {
        HouseAddDto form = new HouseAddDto();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            form = objectMapper.readValue(houseInfo, HouseAddDto.class);
        } catch (IOException e) {
            log.info("Error on ObjectMapper (at HouseService) with --> {}", e);
        }
        form.setPhotos(files);
        return form;
    }

}
