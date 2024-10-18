package dev.flab.studytogether.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberLoginRequestDto {
    private String email;
    private String password;

    private MemberLoginRequestDto() {}
}
