package com.ziphub.Repository;

import com.ziphub.Entity.House;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class HouseRepository {

    private final EntityManager em;

    public Long save(House newHouse) {
        em.persist(newHouse);
        return newHouse.getId();
    }
}
