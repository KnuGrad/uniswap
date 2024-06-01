package com.knu.uniswap.member.service;

import com.knu.uniswap.common.constant.ErrorMessage;
import com.knu.uniswap.common.exception.DuplicationException;
import com.knu.uniswap.common.exception.EmailNotCertifiedException;
import com.knu.uniswap.common.exception.UnauthorizedAccessException;
import com.knu.uniswap.common.exception.ValidationException;
import com.knu.uniswap.member.domain.Member;
import com.knu.uniswap.member.domain.MemberRepository;
import com.knu.uniswap.member.dto.MemberCreateRequest;
import com.knu.uniswap.member.dto.MemberEditRequest;
import com.knu.uniswap.store.domain.Store;
import com.univcert.api.UnivCert;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RequiredArgsConstructor
@Service
public class MemberService {

    @Value("${univCert.apiKey}")
    private String apiKey;

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void addMember(MemberCreateRequest request) {
        checkCertified(request.getEmail());
        validateDuplicateMember(request.getNickname(), request.getEmail());

        if (!request.getPassword().equals(request.getCheckPassword())) {
            throw new ValidationException(ErrorMessage.PASSWORD_CONFIRMATION_FAILED);
        }

        Member member = Member.builder()
            .nickname(request.getNickname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .build();

        Store store = Store.builder().member(member).build();

        member.setStore(store);

        memberRepository.save(member);
    }

    @Transactional
    public void editMember(Long memberId, MemberEditRequest request, Long loginId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.MEMBER_NOT_FOUND));

        if (!member.getId().equals(loginId)) {
            throw new UnauthorizedAccessException(ErrorMessage.NO_ACCESS_PERMISSION);
        }

        if (!member.getNickname().equals(request.getNickname())) {
            validateDuplicateNickname(request.getNickname());
            member.changeNickname(request.getNickname());
        }

        if (!request.getPassword().equals(request.getCheckPassword())) {
            throw new ValidationException(ErrorMessage.PASSWORD_CONFIRMATION_FAILED);
        }

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            member.changePassword(passwordEncoder.encode(request.getPassword()));
        }
    }

    @Transactional
    public void removeMember(Long memberId, Long loginId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.MEMBER_NOT_FOUND));

        if (!member.getId().equals(loginId)) {
            throw new UnauthorizedAccessException(ErrorMessage.NO_ACCESS_PERMISSION);
        }

        memberRepository.delete(member);

        try {
            UnivCert.clear(apiKey, member.getEmail());
        } catch (IOException e) {
            throw new ValidationException(e.getMessage());
        }

        SecurityContextHolder.clearContext();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    private void checkCertified(String email) {
        try {
            boolean certified = (boolean) UnivCert.status(apiKey, email).get("success");

            if (!certified) {
                throw new EmailNotCertifiedException(ErrorMessage.EMAIL_NOT_CERTIFIED);
            }
        } catch (IOException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    private void validateDuplicateMember(String nickname, String email) {
        Optional<Member> existingUsername = memberRepository.findMemberByNickname(nickname);
        Optional<Member> existingEmail = memberRepository.findMemberByEmail(email);

        existingUsername.ifPresent(foundMember -> {
            throw new DuplicationException(ErrorMessage.NICKNAME_DUPLICATED);
        });
        existingEmail.ifPresent(foundMember -> {
            throw new DuplicationException(ErrorMessage.EMAIL_DUPLICATED);
        });
    }

    private void validateDuplicateNickname(String nickname) {
        Optional<Member> existingUsername = memberRepository.findMemberByNickname(nickname);

        existingUsername.ifPresent(foundMember -> {
            throw new DuplicationException(ErrorMessage.NICKNAME_DUPLICATED);
        });
    }

}
