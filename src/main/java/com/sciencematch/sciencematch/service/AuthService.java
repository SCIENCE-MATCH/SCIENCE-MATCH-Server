package com.sciencematch.sciencematch.service;

import com.sciencematch.sciencematch.controller.dto.request.MemberLoginRequestDto;
import com.sciencematch.sciencematch.domain.Member;
import com.sciencematch.sciencematch.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    public String login(MemberLoginRequestDto requestDto) {
        Member member = Member.builder()
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .build();
        memberRepository.save(member);
        return "아이디: " + requestDto.getEmail() + "${\n}" + "비밀번호: " + requestDto.getPassword();
    }
}
