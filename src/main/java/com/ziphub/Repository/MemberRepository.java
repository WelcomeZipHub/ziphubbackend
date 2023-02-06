package com.ziphub.Repository;

import com.ziphub.Entity.Member;
import com.ziphub.Form.RegisterForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public Long save(RegisterForm form) {
        Member member = new Member();
        member.setUsername(form.getUsername());
        member.setPassword(form.getPassword());
        member.setEmail(form.getEmail());
        member.setPhone(form.getPhone());
        member.setFirstname(form.getFirstname());
        member.setLastname(form.getLastname());
        em.persist(member);
        return member.getId();
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public Optional<Member> findOneByEmail(String email) {
        return em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList()
                .stream()
                .findFirst();

    }

    public Optional<Member> findOneByPhone(String phone) {
        return em.createQuery("select m from Member m where m.phone = :phone", Member.class)
                .setParameter("phone", phone)
                .getResultList()
                .stream()
                .findFirst();

    }

    public Optional<Member> findOneByUsername(String username) {
        return em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", username)
                .getResultList()
                .stream()
                .findFirst();

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
