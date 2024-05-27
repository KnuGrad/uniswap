package com.knu.uniswap.member.controller;

import static java.lang.Integer.parseInt;

import com.knu.uniswap.common.ApiResponse;
import com.knu.uniswap.member.dto.UnivEmailAuthCodeRequest;
import com.knu.uniswap.member.dto.UnivEmailCertificationRequest;
import com.knu.uniswap.member.service.UnivEmailService;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/emails")
@RestController
public class UnivEmailController {

    private final UnivEmailService univEmailService;

    @PostMapping("/certification-code")
    public ResponseEntity<ApiResponse> sendCode(@RequestBody @Valid UnivEmailAuthCodeRequest request, BindingResult bindingResult) {
        Map<String, Object> result = univEmailService.sendCode(request);

        if ((boolean) result.get("success")) {
            return ResponseEntity.ok()
                .body(ApiResponse.success((int) result.get("code"), null));
        }

        return ResponseEntity.badRequest()
            .body(ApiResponse.fail((int) result.get("code"), result.get("message").toString(), null));
    }

    @PostMapping("/certify")
    public ResponseEntity<ApiResponse> certify(@RequestBody UnivEmailCertificationRequest request, BindingResult bindingResult) {
        Map<String, Object> result = univEmailService.certifyCode(request);

        if ((boolean) result.get("success")) {
            return ResponseEntity.ok()
                .body(ApiResponse.success((int) result.get("code"), null));
        }

        return ResponseEntity.badRequest()
            .body(ApiResponse.fail(400, result.get("message").toString(), null));
    }

}
