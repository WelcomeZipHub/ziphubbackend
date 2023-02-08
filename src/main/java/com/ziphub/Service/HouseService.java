package com.ziphub.Service;


import com.ziphub.Entity.Embedded.Address;
import com.ziphub.Entity.House;
import com.ziphub.Entity.Member;
import com.ziphub.Entity.Photo;
import com.ziphub.Form.HouseForm;
import com.ziphub.Repository.HouseRepository;

import com.ziphub.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HouseService {

    private final MemberRepository memberRepository;
    private final HouseRepository houseRepository;
    private final PhotoService photoService;

    @Transactional
    public House addHouse(HouseForm form) throws IOException {
        List<Photo> photos = photoService.savePhotos(form.getImageFiles());
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

        House newHouse = House.builder()
                .member(member)
                .price(form.getPrice())
                .description(form.getDescription())
                .address(address)
                .photos(photos)
                .createdDate(LocalDateTime.now())
                .build();

        return houseRepository.save(newHouse);
    }
}
