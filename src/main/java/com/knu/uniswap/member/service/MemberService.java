package com.knu.uniswap.member.service;

import com.knu.uniswap.common.constant.ErrorMessage;
import com.knu.uniswap.common.exception.DuplicationException;
import com.knu.uniswap.common.exception.ValidationException;
import com.knu.uniswap.member.domain.Member;
import com.knu.uniswap.member.domain.MemberRepository;
import com.knu.uniswap.member.dto.MemberCreateRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void addMember(MemberCreateRequest request) {
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
