package com.ziphub.Service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ziphub.Entity.Embedded.Address;
import com.ziphub.Entity.House;
import com.ziphub.Entity.Member;
import com.ziphub.Entity.Photo;
import com.ziphub.Form.HouseForm;
import com.ziphub.Repository.HouseRepository;

import com.ziphub.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.channels.MulticastChannel;
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
    public Long addHouse(HouseForm form) {
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

        Long houseId = houseRepository.save(newHouse);

        String uniqueId = member.getUsername() + "-h" + houseId;
        List<Photo> photos = photoService.savePhotos(form.getPhotos(), uniqueId);
        for (Photo p : photos) {
            newHouse.addPhoto(p);
        }

        return houseId;
    }

    public HouseForm getJson(String houseInfo, List<MultipartFile> files) {
        HouseForm form = new HouseForm();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            form = objectMapper.readValue(houseInfo, HouseForm.class);
        } catch (IOException e) {
            log.info("Error on ObjectMapper (at HouseService) with --> {}", e);
        }
        form.setPhotos(files);
        return form;
    }
}
