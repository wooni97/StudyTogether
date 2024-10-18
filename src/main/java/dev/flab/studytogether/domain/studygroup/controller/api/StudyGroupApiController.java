package dev.flab.studytogether.domain.studygroup.controller.api;

import dev.flab.studytogether.domain.studygroup.entity.StudyGroup;
import dev.flab.studytogether.domain.studygroup.service.StudyGroupJoinService;
import dev.flab.studytogether.utils.SessionUtilV2;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/study-groups")
@AllArgsConstructor
public class StudyGroupApiController {

    private final StudyGroupJoinService studyGroupJoinService;

    @PostMapping("/v1/join")
    @Operation(summary = "Create Study Group", description = "스터디 그룹 생성")
    @ResponseStatus(HttpStatus.OK)
    public void join(
            @RequestParam(name = "groupId") Long groupId,
            HttpSession httpSession) {
        Long memberId = SessionUtilV2.getLoginMemberId(httpSession);
        studyGroupJoinService.joinGroup(groupId, memberId);
    }


}
