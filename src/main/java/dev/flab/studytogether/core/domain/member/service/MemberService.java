package dev.flab.studytogether.core.domain.member.service;

import dev.flab.studytogether.core.domain.member.entity.Member;
import dev.flab.studytogether.core.domain.member.exception.MemberNotFoundException;
import dev.flab.studytogether.core.domain.member.exception.PasswordMismatchException;
import dev.flab.studytogether.core.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public Member create(String id, String password, String nickname){
        return memberRepository.save(Member.createWithoutSequenceId(id, password, nickname));
    }

    public Member login(String id, String password){
        Member member = memberRepository.findByID(id)
                        .orElseThrow(()-> new MemberNotFoundException("일치하는 ID가 없습니다."));

        if(member.isPasswordMatched(password)){
            return member;
        }

        throw new PasswordMismatchException("비밀번호가 일치하지 않습니다");
    }


    public boolean isIdExists(String id) {
        return memberRepository.isIdExists(id);
    }
}
