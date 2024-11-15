package dev.flab.studytogether.domain.studygroup.service;

import dev.flab.studytogether.domain.event.EventRepository;
import dev.flab.studytogether.domain.studygroup.event.StudyGroupJoinEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class StudyGroupJoinProcessStartService {

    private final EventRepository eventRepository;

    @Transactional
    public void studyGroupJoinProcessStart(Long studyGroupId, Long memberId) {
        StudyGroupJoinEvent studyGroupJoinEvent =
                StudyGroupJoinEvent.createNewEvent(studyGroupId, memberId);

        eventRepository.save(studyGroupJoinEvent);
    }
}