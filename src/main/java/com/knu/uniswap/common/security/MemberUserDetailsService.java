package com.knu.uniswap.common.security;

import com.knu.uniswap.common.constant.ErrorMessage;
import com.knu.uniswap.member.domain.Member;
import com.knu.uniswap.member.domain.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MemberUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public MemberUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findMemberByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(ErrorMessage.LOGIN_FAILED));

        return new MemberPrincipal(member);
    }

}
