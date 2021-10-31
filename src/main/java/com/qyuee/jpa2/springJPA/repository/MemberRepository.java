package com.qyuee.jpa2.springJPA.repository;

import com.qyuee.jpa2.springJPA.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

// 컴포넌트 스캔에 의해서 자동으로 스프링빈으로 등록
// JPA 예외를 스프링 기반 예외로 변환
@Repository
public class MemberRepository {
    // EntityManager가 PersistenceContextType.TRANSACTION을 사용하도록 지정해준다. (default)
    // Persistence Context는 크게 두 가지가 있다.
    // 1. 트랜잭션 범위
    // 2. 확장 범위 - 트랜잭션 없이 엔티티를 persist 할 수 있다. 각각의 persistence context는 서로를 인식하지 못한다.
    // A 컴포넌트에서 엔티티를 persist하고 B컴포넌트에서 A에서 persist한 엔티티를 찾으려고해도 찾을 수 없다.
    @PersistenceContext
    private EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
