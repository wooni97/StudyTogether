package dev.flab.studytogether.domain.member.controller.api;

import dev.flab.studytogether.domain.member.dto.MemberLoginRequestDto;
import dev.flab.studytogether.domain.member.dto.MemberV2Response;
import dev.flab.studytogether.domain.member.entity.MemberV2;
import dev.flab.studytogether.domain.member.service.MemberV2Service;
import dev.flab.studytogether.utils.SessionUtil;
import dev.flab.studytogether.utils.SessionUtilV2;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/membersV2")
@AllArgsConstructor
public class MemberV2ApiController {
    private final MemberV2Service memberService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public MemberV2Response login(@RequestBody MemberLoginRequestDto memberLoginRequestDto,
                                  HttpSession httpSession) {
        MemberV2 member = memberService.login(memberLoginRequestDto.getEmail(), memberLoginRequestDto.getPassword());
        SessionUtilV2.setLoginMemberSession(httpSession, member.getId());

        return new MemberV2Response(member.getId(), member.getEmail(), member.getNickname());
    }

    @GetMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpSession httpSession) {
        SessionUtilV2.logout(httpSession);
    }
}
