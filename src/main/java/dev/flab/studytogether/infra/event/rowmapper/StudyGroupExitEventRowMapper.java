package dev.flab.studytogether.infra.event.rowmapper;

import dev.flab.studytogether.core.domain.studygroup.event.StudyGroupExitEvent;

import java.time.LocalDateTime;

public class StudyGroupExitEventRowMapper implements DomainEventRowMapper<StudyGroupExitEvent> {
    private Long studyGroupId;
    private Long participantId;
    private LocalDateTime createdAt;

    @Override
    public StudyGroupExitEvent createDomainEvent() {
        return StudyGroupExitEvent.createFromExisting(studyGroupId, participantId, createdAt);
    }

    @Override
    public Class<StudyGroupExitEvent> eventType() {
        return StudyGroupExitEvent.class;
    }
}
