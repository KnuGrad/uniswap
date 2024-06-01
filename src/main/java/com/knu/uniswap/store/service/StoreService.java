package com.knu.uniswap.store.service;

import com.knu.uniswap.common.constant.ErrorMessage;
import com.knu.uniswap.common.utils.ImageUploader;
import com.knu.uniswap.member.domain.Member;
import com.knu.uniswap.member.domain.MemberRepository;
import com.knu.uniswap.store.domain.Store;
import com.knu.uniswap.store.domain.StoreRepository;
import com.knu.uniswap.store.dto.StoreResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Transactional
@Service
public class StoreService {

    private final ImageUploader imageUploader;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public StoreResponse getStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.STORE_NOT_FOUND));

        return StoreResponse.of(store.getBio(), store.getImageUrl());
    }

    public void addStore(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.MEMBER_NOT_FOUND));

        storeRepository.save(Store.builder().member(member).build());
    }

    public void addBio(Long storeId, Map<String, String> bio) {
        Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.STORE_NOT_FOUND));

        store.changeOrAddBio(bio.get("bio"));
    }

    public void uploadProfileImage(Long storeId, MultipartFile image) {
        Store store = storeRepository.findById(storeId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.STORE_NOT_FOUND));

        String imageUrl = imageUploader.uploadImage(image);

        store.addProfileImage(imageUrl);
    }

}
