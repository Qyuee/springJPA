package com.qyuee.jpa2.springJPA.domain.item;

import com.qyuee.jpa2.springJPA.domain.Category;
import com.qyuee.jpa2.springJPA.execption.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
// 상속 전략을 설정해야한다.
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
    
    // 비즈니스 로직이 엔티티내에 있는 것이 도메인 주도 설계에 있어서 응집도를 높힐 수 있다.
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int updatedQuantity = this.stockQuantity - quantity;
        if (updatedQuantity < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = updatedQuantity;
    }
}
