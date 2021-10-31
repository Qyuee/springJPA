package com.qyuee.jpa2.springJPA.service;

import com.qyuee.jpa2.springJPA.domain.Member;
import com.qyuee.jpa2.springJPA.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    /**
     * select 쿼리는 있지만, insert 쿼리가 없다. -> 트랜잭션 commit이 되지 않아서이다. -> why? @Transactional이 기본적으로 TC에 있으면 commit하지 않고 롤백을 한다.
     * 즉, 영속성 컨텍스트에 있는 값을 가지고 테스트를 한 것임
     *
     * DB에 쿼리가 전달되는 것을 보려면 @Rollback(false)를 설정해준다.
     * 아니면, 엔티티매니저를 통해서 flush()를 해준다.
     */
    @Test
    @Rollback(value = false)
    void join_회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("회원1");

        // when
        Long savedId = memberService.join(member);
        //em.flush(); @Rollback을 하거나 이 구문을 사용한다.

        // then
        assertEquals(member, memberRepository.findOne(savedId));
    }

    /**
     * @Reference - https://howtodoinjava.com/junit5/expected-exception-example/, https://jinioh88.tistory.com/57
     * JUnit4에서의 예외 테스트: @Test(expected = IllegalStateException.class)
     * JUnit5에서는 제공하지 않는다.
     */
    @Test
    public void 중복회원가입_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("회원1");

        Member member2 = new Member();
        member2.setName("회원1");

        // when
        memberService.join(member1);

        /*try {
            memberService.join(member2);
        } catch (IllegalStateException e) {
            return;
        }*/

        // then
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });
        assertTrue(exception.getMessage().contains("이미 존재하는 회원 입니다.")); // 성공
        // assertTrue(exception.getMessage().contains("이미 존재하는 회원 입니다.2")); -> 실패한다.
        // fail("예외가 발생해야 합니다.");  // 여기오면 잘못되었다는 의미의 테스트메소드
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}