package com.knu.uniswap.store.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class StoreResponse {

    private String bio;
    private String imageUrl;

    public static StoreResponse of(String bio, String imageUrl) {
        return new StoreResponse(bio, imageUrl);
    }

}
