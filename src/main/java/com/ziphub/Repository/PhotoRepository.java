package com.ziphub.Repository;

import com.ziphub.Entity.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PhotoRepository {

    private final EntityManager em;

    public Photo savePhoto(Photo photo) {
        em.persist(photo);
        return photo;
    }

    public List<Photo> getPhotos(String path) {
        return em.createQuery("select p from Photo p where p.storage_url = :path", Photo.class)
                .setParameter("path", path)
                .getResultList();
    }
}
