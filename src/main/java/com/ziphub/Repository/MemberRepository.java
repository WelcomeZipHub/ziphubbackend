package com.ziphub.Repository;

import com.ziphub.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findOneByEmail(String email) {
        return em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList();
    }

    public List<Member> findOneByPhone(String phone) {
        return em.createQuery("select m from Member m where m.phone = :phone", Member.class)
                .setParameter("phone", phone)
                .getResultList();
    }

    public List<Member> findOneByUsername(String username) {
        return em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", username)
                .getResultList();
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findAllByFirstName(String firstname) {
        return em.createQuery("select m from Member m where m.firstname = :firstname", Member.class)
                .setParameter("firstname", firstname)
                .getResultList();
    }

    public List<Member> findAllByLastName(String lastname) {
        return em.createQuery("select m from Member m where m.lastname = :lastname", Member.class)
                .setParameter("lastname", lastname)
                .getResultList();
    }
}
