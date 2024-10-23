package dev.flab.studytogether.domain.studygroup.entity;

import dev.flab.studytogether.domain.room.entity.ActivateStatus;
import dev.flab.studytogether.domain.studygroup.exception.*;
import lombok.Getter;

import javax.persistence.*;
import java.util.Comparator;
import java.util.Optional;

@Entity
@Getter
public class StudyGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String groupTitle;
    private int maxParticipants;
    @Embedded
    private ParticipantsV2 participants;
    @OneToOne
    @JoinColumn(name = "manager_participant_id")
    private ParticipantV2 groupManager;
    @Enumerated(EnumType.STRING)
    private ActivateStatus activateStatus;

    protected StudyGroup() {}

    public StudyGroup(String groupTitle, int maxParticipants, ActivateStatus activateStatus) {
        this.groupTitle = groupTitle;
        this.maxParticipants = maxParticipants;
        this.activateStatus = activateStatus;
        this.participants = new ParticipantsV2();
    }

    public void joinGroup(ParticipantV2 participant) {
        if(this.activateStatus.equals(ActivateStatus.TERMINATED)) {
            throw new TerminatedGroupJoinException("종료된 Study Group에는 입장할 수 없습니다.");
        }

        if(isMemberExists(participant.getMemberId()))
            throw new MemberAlreadyExistsInGroupException("이미 StudyGroup에 존재하는 유저입니다.");

        if(isGroupFull())
            throw new GroupCapacityExceededException("Study Group ID : " + this.id +
                    " 정원이 다 찬 관계로 Member ID : " + participant.getMemberId() + " 입장 실패.");

        participants.addParticipant(participant);

        if(ParticipantV2.Role.GROUP_MANAGER.equals(participant.getParticipantRole())) {
            if(groupManager != null) throw new CannotAssignManagerException("Group Manager가 존재하는 그룹엔 매니저로 참여가 불가능합니다.");

            this.groupManager = participant;
        }
    }

    public void exitGroup(Long memberId, Long participantId) {
        if(!isMemberExists(memberId)) {
            throw new MemberNotFoundInGroupException(this.id, memberId);
        }

        if(isGroupManager(participantId)) {
            changeGroupManager();
        }

        participants.removeParticipant(participantId);
    }


    public boolean isGroupFull() {
        return this.maxParticipants == participants.getCurrentParticipantsCount();
    }

    public void changeGroupManager(){
        ParticipantV2 currentRoomManager = this.groupManager;
        ParticipantV2 nextRoomManager = findNextManager()
                .orElseThrow(() -> new NoParticipantForManagerDelegateException("방장 권한을 위임할 사용자가 존재하지 않습니다."));

        changeParticipantRole(currentRoomManager.getId(), ParticipantV2.Role.ORDINARY_PARTICIPANT);
        changeParticipantRole(nextRoomManager.getId(), ParticipantV2.Role.GROUP_MANAGER);

        this.groupManager = nextRoomManager;
    }

    private Optional<ParticipantV2> findNextManager() {
        return participants.getParticipants()
                .stream()
                .filter(participant ->
                        !participant.getParticipantRole().equals(ParticipantV2.Role.GROUP_MANAGER))
                .min(Comparator.comparing(ParticipantV2::getJoinedAt));
    }

    public boolean isGroupManager(Long participantId) {
        return this.groupManager.getId().equals(participantId);
    }

    public boolean isMemberExists(Long memberId) {
        return participants.hasParticipant(memberId);
    }

    private void changeParticipantRole(Long participantId, ParticipantV2.Role roleToChange) {
        participants.getParticipants().stream()
                .filter(p -> p.getId().equals(participantId))
                .findFirst()
                .ifPresent(p -> p.changeRole(roleToChange));
    }

}
