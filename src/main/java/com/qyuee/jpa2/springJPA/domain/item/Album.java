package com.qyuee.jpa2.springJPA.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter @Setter
@DiscriminatorValue("A")    // 싱글테이블 전략일 때, 각 컬럼이 어떤 속성인지 구별 할 수 있는 값 정의
public class Album extends Item {
    private String artist;
    private String etc;
}
