package dev.flab.studytogether.domain.studygroup.infrastructure;

import dev.flab.studytogether.domain.room.entity.ActivateStatus;
import dev.flab.studytogether.domain.studygroup.entity.StudyGroup;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface StudyGroupJpaRepository extends JpaRepository<StudyGroup, Long> {
    @EntityGraph(attributePaths = {"participants.participants"})
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<StudyGroup> findById(long id);
    List<StudyGroup> findByActivateStatus(ActivateStatus status);
}
