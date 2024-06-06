package com.knu.uniswap.goods.domain;

import com.knu.uniswap.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Goods extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private Long storeId;

    @Column(nullable = false)
    private Long categoryId;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, length = 50)
    private String location;

    @Column(nullable = false)
    private boolean isNew;

    @Builder
    public Goods(Long id, Long storeId, Long categoryId, String title, String content, int price, int quantity, String location, boolean isNew) {
        this.id = id;
        this.storeId = storeId;
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
        this.price = price;
        this.quantity = quantity;
        this.location = location;
        this.isNew = isNew;
    }

}
