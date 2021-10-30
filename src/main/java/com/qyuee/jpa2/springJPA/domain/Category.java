package com.qyuee.jpa2.springJPA.domain;

import com.qyuee.jpa2.springJPA.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    // 다대일의 경우 일대다, 다대일로 풀어줄 수 있는 중간 테이블이 필요하다. 하지만,,, 단점이 있다.
    // 필드를 더 추가 할 수가 없다. 그냥 중간테이블 역활을 하는 엔티티를 추가하여 1:N, N:1로 풀어버리는게 낫다.
    // 여기서는 예제이므로 ManyToMany를 그대로 구현한다.
    private List<Item> items = new ArrayList<>();


    // 같은 엔티티내의 계층구조를 표현
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();
    
    // 연관관계 편의 메소드
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
