package com.knu.uniswap.store.controller;

import com.knu.uniswap.common.ApiResponse;
import com.knu.uniswap.common.constant.ErrorMessage;
import com.knu.uniswap.common.exception.ValidationException;
import com.knu.uniswap.store.dto.StoreResponse;
import com.knu.uniswap.store.service.StoreService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api/members/{memberId}/stores")
@RestController
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/{storeId}")
    public ResponseEntity<ApiResponse> storeDetail(@PathVariable Long memberId, @PathVariable Long storeId) {
        StoreResponse store = storeService.getStore(storeId);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(201, store));
    }

    @PostMapping("/{storeId}/bio")
    public ResponseEntity<ApiResponse> bioAdd(@PathVariable Long memberId, @PathVariable Long storeId, @RequestBody Map<String, String> bio) {
        storeService.addBio(storeId, bio);

        return ResponseEntity.ok()
            .body(ApiResponse.success(200, null));
    }

    @PostMapping("/{storeId}/profile-image")
    public ResponseEntity<ApiResponse> profileImage(@PathVariable Long memberId, @PathVariable Long storeId, @ModelAttribute MultipartFile image) {
        if (image.isEmpty()) {
            throw new ValidationException(ErrorMessage.NOT_ATTACHED_IMAGE);
        }

        storeService.uploadProfileImage(storeId, image);

        return ResponseEntity.ok()
            .body(ApiResponse.success(200, null));
    }

}
