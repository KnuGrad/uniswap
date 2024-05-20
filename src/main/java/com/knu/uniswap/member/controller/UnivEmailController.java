package com.knu.uniswap.member.controller;

import com.knu.uniswap.member.dto.UnivEmailVerifyRequest;
import com.knu.uniswap.member.service.UnivEmailService;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/verification-code")
    public ResponseEntity<Map<String, Object>> sendCode(@RequestBody @Valid UnivEmailVerifyRequest request, BindingResult bindingResult) {
        Map<String, Object> result = univEmailService.sendCode(request);

        return ResponseEntity.ok()
            .body(result);
    }

}
