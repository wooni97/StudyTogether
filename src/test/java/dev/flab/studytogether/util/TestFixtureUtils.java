package dev.flab.studytogether.util;

import dev.flab.studytogether.domain.member.entity.MemberV2;
import dev.flab.studytogether.domain.room.entity.ActivateStatus;
import dev.flab.studytogether.domain.studygroup.entity.ParticipantV2;
import dev.flab.studytogether.domain.studygroup.entity.StudyGroup;
import dev.flab.studytogether.domain.studygroup.role.ParticipantRoleV2;

import java.time.LocalDateTime;

public class TestFixtureUtils {
    public static MemberV2 randomMember() {
        String email = "testEmail@gmail.com";
        String password = "password123";
        String nickname = "testNickName";

        return MemberV2.createNewMember(email, password, nickname);
    }

    public static StudyGroup randomStudyGroup() {
        String groupTitle = "Java Study Group";
        int maxParticipants = 10;
        ActivateStatus activateStatus = ActivateStatus.ACTIVATED;

        return new StudyGroup(groupTitle, maxParticipants, activateStatus);
    }

    public static StudyGroup randomStudyGroupWithParticipants() {
        String groupTitle = "Java Study Group";
        int maxParticipants = 10;
        ActivateStatus activateStatus = ActivateStatus.ACTIVATED;

        StudyGroup studyGroup = new StudyGroup(groupTitle, maxParticipants, activateStatus);

        ReflectionTestUtils.setField(studyGroup, "id", 1L);

        ParticipantV2 manager = new ParticipantV2(studyGroup,
                1L,
                ParticipantRoleV2.GROUP_MANAGER,
                LocalDateTime.of(2023, 1, 2, 4, 5));

        ReflectionTestUtils.setField(manager, "id", 1L);

        studyGroup.joinGroup(manager);

        ParticipantV2 participant = new ParticipantV2(studyGroup,
                2L,
                ParticipantRoleV2.ORDINARY_PARTICIPANT,
                LocalDateTime.of(2023, 2, 1, 2, 3, 55));

        ReflectionTestUtils.setField(participant, "id", 2L);

        studyGroup.joinGroup(participant);

        return studyGroup;
    }
}
