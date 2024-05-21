package com.knu.uniswap.member.controller;

import com.knu.uniswap.common.ApiResponse;
import com.knu.uniswap.common.security.MemberPrincipal;
import com.knu.uniswap.member.dto.MemberCreateRequest;
import com.knu.uniswap.member.dto.MemberEditRequest;
import com.knu.uniswap.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
            .body(ApiResponse.success(201, null));
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<ApiResponse> memberEdit(@PathVariable Long memberId, @RequestBody @Valid MemberEditRequest memberEditRequest, BindingResult bindingResult, @AuthenticationPrincipal MemberPrincipal userDetails) {
        Long loginId = userDetails.getMember().getId();
        memberService.editMember(memberId, memberEditRequest, loginId);

        return ResponseEntity.ok()
            .body(ApiResponse.success(200, null));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<ApiResponse> withdrawal(@PathVariable Long memberId, @AuthenticationPrincipal MemberPrincipal userDetails) {
        Long loginId = userDetails.getMember().getId();
        memberService.removeMember(memberId, loginId);

        return ResponseEntity.ok()
            .body(ApiResponse.success(200, null));
    }

}
