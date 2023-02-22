package com.ziphub.Repository;

import com.ziphub.Entity.House;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class HouseRepository {

    private final EntityManager em;

    public House findOne(Long id) {
        return em.find(House.class, id);
    }

    public Long save(House newHouse) {
        em.persist(newHouse);
        return newHouse.getId();
    }

    public List<House> findAllWithMember(int offset, int limit) {
        return em.createQuery(
                "select h from House h" +
                        " where h.hide = false", House.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
