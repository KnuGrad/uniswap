package com.knu.uniswap.member.controller;

import com.knu.uniswap.common.ApiResponse;
import com.knu.uniswap.member.dto.MemberCreateRequest;
import com.knu.uniswap.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<ApiResponse> register(@RequestBody @Valid MemberCreateRequest request, BindingResult bindingResult) {
        memberService.addMember(request);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(201));
    }

}
