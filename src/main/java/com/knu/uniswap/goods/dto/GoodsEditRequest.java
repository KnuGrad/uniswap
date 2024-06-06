package com.knu.uniswap.goods.dto;

import jakarta.validation.constraints.Size;

public class GoodsEditRequest {
    private Long id;
    private Long storeId;
    private String imageUrl;
    private Long categoryId;
    private boolean isNew;
    @Size(max=40)
    private String title;
    @Size(max=2000)
    private String content;
    private int price;
    private int quantity;
    private String location;
}
