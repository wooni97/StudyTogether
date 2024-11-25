package dev.flab.studytogether.core.domain.studygroup.service;

import dev.flab.studytogether.api.dto.SchedulerTodoDto;
import dev.flab.studytogether.core.domain.schedule.entity.Scheduler;
import dev.flab.studytogether.core.domain.schedule.entity.Todo;
import dev.flab.studytogether.core.domain.schedule.repository.SchedulerRepository;
import dev.flab.studytogether.core.domain.schedule.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SchedulerTodoService {
    private final SchedulerRepository schedulerRepository;
    private final TodoRepository todoRepository;

    public SchedulerTodoService(SchedulerRepository schedulerRepository, TodoRepository todoRepository) {
        this.schedulerRepository = schedulerRepository;
        this.todoRepository = todoRepository;
    }


    public SchedulerTodoDto create(SchedulerTodoDto schedulerTodoDto) {

        //memberSequenceID, schedulerDate로 해당 스케줄러 존재하는지 확인
        Optional<Scheduler> result =
                schedulerRepository.findByMemberIdAndDate(
                        schedulerTodoDto.getMemberSequenceId(),
                        schedulerTodoDto.getSchedulerDate()
                );

        /*
        스케줄러 존재하지 않는다면 새로운 스케줄러 생성
        스케줄러 존재하면 기존 스케줄러에 투두 추가
        */
        Scheduler scheduler = result.orElseGet(() ->
                schedulerRepository.save(
                        schedulerTodoDto.getSchedulerDate(),
                        schedulerTodoDto.getMemberSequenceId()
                ));
        Todo todo = todoRepository.save(schedulerTodoDto.getTodoContent(), scheduler);

        return SchedulerTodoDto.builder()
                .schedulerSequenceID(scheduler.getSchedulerSeq())
                .todoSequenceID(todo.getTodoID())
                .todoContent(todo.getTodoContent())
                .build();
    }

    public SchedulerTodoDto updateTodoContent(SchedulerTodoDto schedulerTodoDto) {
        todoRepository.updateContent(
                schedulerTodoDto.getTodoContent(),
                schedulerTodoDto.getSchedulerSequenceID(),
                schedulerTodoDto.getTodoSequenceID());

        return schedulerTodoDto;
    }

    public SchedulerTodoDto deleteTodo(SchedulerTodoDto schedulerTodoDto) {
        todoRepository.delete(
                schedulerTodoDto.getSchedulerSequenceID(),
                schedulerTodoDto.getTodoSequenceID());

        return schedulerTodoDto;
    }

    public SchedulerTodoDto updateToCompleted(SchedulerTodoDto schedulerTodoDto){
        todoRepository.updateCheckStatusToCompleted(
                schedulerTodoDto.getSchedulerSequenceID(),
                schedulerTodoDto.getTodoSequenceID());

        return schedulerTodoDto;
    }

    public SchedulerTodoDto updateToUncompleted(SchedulerTodoDto schedulerTodoDto){
        todoRepository.updateCheckStatusToUncompleted(
                schedulerTodoDto.getSchedulerSequenceID(),
                schedulerTodoDto.getTodoSequenceID());

        return schedulerTodoDto;
    }
}

