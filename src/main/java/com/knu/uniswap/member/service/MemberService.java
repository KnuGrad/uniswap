package com.knu.uniswap.member.service;

import com.knu.uniswap.common.constant.ErrorMessage;
import com.knu.uniswap.common.exception.DuplicationException;
import com.knu.uniswap.common.exception.EmailNotCertifiedException;
import com.knu.uniswap.common.exception.ValidationException;
import com.knu.uniswap.member.domain.Member;
import com.knu.uniswap.member.domain.MemberRepository;
import com.knu.uniswap.member.dto.MemberCreateRequest;
import com.univcert.api.UnivCert;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        memberRepository.save(member);
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

//    private void validateDuplicateNickname(String nickname) {
//        Optional<Member> existingUsername = memberRepository.findMemberByNickname(nickname);
//
//        existingUsername.ifPresent(foundMember -> {
//            throw new DuplicationException(ErrorMessage.NICKNAME_DUPLICATED);
//        });
//    }
//
//    private void validateDuplicateEmail(String email) {
//        Optional<Member> existingEmail = memberRepository.findMemberByEmail(email);
//
//        existingEmail.ifPresent(foundMember -> {
//            throw new DuplicationException(ErrorMessage.EMAIL_DUPLICATED);
//        });
//    }

}
