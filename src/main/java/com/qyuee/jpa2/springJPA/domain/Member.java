package com.qyuee.jpa2.springJPA.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter // 새터는 필요한 경우에만 선택적으로 사용하자.
public class Member {
    @Column(name = "member_id")
    @Id @GeneratedValue
    private Long id;

    private String name;

    @Embedded
    private Address address;

    // 양방향 관계 설정
    @OneToMany(mappedBy = "member") // order쪽의 member와 객체와 매핑
    // 회원<->주문 관계 중에 주인은 어디? -> 외래키가 있는 쪽. ==> 주문쪽이다.
    private List<Order> order = new ArrayList<>();
}
