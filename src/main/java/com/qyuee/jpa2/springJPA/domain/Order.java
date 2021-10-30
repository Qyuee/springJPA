package com.qyuee.jpa2.springJPA.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "orders")
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // 단방향 관계 설정
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;  // 연관관계의 주인

    @OneToOne
    @JoinColumn(name = "delivery_id")   // 일대일의 관계에서는 비지니스 요구사항에 따라서 연관관계의 주인을 설정 할 수 있다. 현재 예제에서는 Order쪽에 FK를 두었으므로 Order가 주인이다.
    private Delivery delivery;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    // java 8이상부터는 자동으로 하이버네이트가 자동으로 지원을 해준다.
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
