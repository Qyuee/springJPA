package com.qyuee.jpa2.springJPA.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // 단방향 관계 설정
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;  // 연관관계의 주인

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    // 일대일의 관계에서는 비지니스 요구사항에 따라서 연관관계의 주인을 설정 할 수 있다. 현재 예제에서는 Order쪽에 FK를 두었으므로 Order가 주인이다.
    private Delivery delivery;

    // cascade: orderItem에 대한 persist를 전파한다.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    // java 8이상부터는 자동으로 하이버네이트가 자동으로 지원을 해준다.
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // 연관관계 편의 메소드
    public void setMember(Member member) {
        this.member = member;
        member.getOrder().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
