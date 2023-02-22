package com.ziphub.Service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ziphub.Dto.HouseDto;
import com.ziphub.Entity.Embedded.Address;
import com.ziphub.Entity.House;
import com.ziphub.Entity.Member;
import com.ziphub.Entity.Photo;
import com.ziphub.Dto.HousePhotoDto;
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


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class HouseService {

    private final MemberRepository memberRepository;
    private final HouseRepository houseRepository;
    private final PhotoService photoService;

    @Transactional
    public Long addHouse(HousePhotoDto form) {
        Member member = memberRepository.findOne(form.getMemberId());
        Address address = new Address(
                form.getCity(),
                form.getStreet(),
                form.getSuite(),
                form.getState(),
                form.getZipcode(),
                form.getLongitude(),
                form.getLatitude()
        );

        House newHouse = new House();
        newHouse.setMember(member);
        newHouse.setAddress(address);
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
    public HouseDto editHouse(Long houseId, HouseDto houseDto) {
        House findHouse = houseRepository.findOne(houseId);
        findHouse.setAddress(houseDto.getAddress());
        if(houseDto.getPrice() != findHouse.getPrice()) {
            findHouse.setPrice(houseDto.getPrice());
        }
        if(!houseDto.getDescription().equals(findHouse.getDescription())) {
            findHouse.setDescription(houseDto.getDescription());
        }
        return houseDto;
    }

    @Transactional
    public void updateHouseHideStatus(Long houseId) {
        House house = houseRepository.findOne(houseId);
        house.setHide(!house.isHide());
    }

    public HousePhotoDto getJson(String houseInfo, List<MultipartFile> files) {
        HousePhotoDto form = new HousePhotoDto();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            form = objectMapper.readValue(houseInfo, HousePhotoDto.class);
        } catch (IOException e) {
            log.info("Error on ObjectMapper (at HouseService) with --> {}", e);
        }
        form.setPhotos(files);
        return form;
    }
}
