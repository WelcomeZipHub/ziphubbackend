package com.ziphub.Repository;

import com.ziphub.Entity.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class PhotoRepository {

    private final EntityManager em;

    public Photo savePhoto(Photo photo) {
        em.persist(photo);
        return photo;
    }
}
