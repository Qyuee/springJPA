package com.qyuee.jpa2.springJPA.service;

import com.qyuee.jpa2.springJPA.domain.Member;
import com.qyuee.jpa2.springJPA.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *회원가입
 *회원 전체 조회
 */
@Service
@Transactional(readOnly = true) // 영속성 컨테스트를 플러시 하지 않으므로 약간의 성능 향상을 도모 할 수 있다.
@RequiredArgsConstructor        // final이 있는 필드만 가지고 생성자를 생성해줌. 해당 생성자를 통해서 해당 필드에 맞는 빈이 주입될 수 있다.
public class MemberService {
    //@Autowired (필드 주입 방식 - 권장X)
    private final MemberRepository memberRepository;

    /*
    생성자를 통해서 빈 주입(권장) -> 롬복과 final 키워드를 통해서 더 단순화 시킬 수 있다.
    @Autowired (생략가능)
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    */

    @Transactional  // 기본적으로 데이터에 대한 변경은 트랙잭션내에서 진행되어야 한다.
    // spring에서 제공하는 어노테이션, JPA에서 제공하는 어노테이션이 각각 있음, spring에서 제공하는 것이 더 많은 옵션을 제공한다.
    public Long join(Member member) {
        /*
        멀티쓰레드 환경(예를 들어서 was가 여러대)에서는 동시에 중복 검사 메소드를 통과 할 수도 있다.
        이러한 경우에는 DB쪽에 회원명컬럼을 유니크 제약조건을 추가하여 `중복검사` 메소드를 통과하여도 최종적으로 DB에서 저장되지 않도록 하는 것이 더 안전하다.
         */
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }

    private void validateDuplicateMember(Member member) {
        // 예외처리
        List<Member> members = memberRepository.findByName(member.getName());
        if (!members.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원 입니다.");
        }
    }
}
