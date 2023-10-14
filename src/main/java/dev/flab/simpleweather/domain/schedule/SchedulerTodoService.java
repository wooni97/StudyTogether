package dev.flab.simpleweather.domain.schedule;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SchedulerTodoService {
    private final SchedulerRepository schedulerRepository;
    private final TodoRepository todoRepository;

    public SchedulerTodoService(SchedulerRepository schedulerRepository, TodoRepository todoRepository) {
        this.schedulerRepository = schedulerRepository;
        this.todoRepository = todoRepository;
    }


    public SchedulerApiResponse create(int seqId, String id, LocalDate date, List<String> todos) {


        Optional<Scheduler> result = schedulerRepository.find(seqId, date);
        SchedulerApiResponse schedulerApiResponse = new SchedulerApiResponse();
        //이쪽 코드가 중복되는것 같아..좀 이상하다고 생각 드는데
        if(result.isEmpty()){
            Scheduler scheduler = schedulerRepository.createScheduler(date, seqId, id);
            todoRepository.createTodo(todos, scheduler);
            schedulerApiResponse.setSchedulerSeq(scheduler.getSchedulerSeq());
            schedulerApiResponse.setTodos(todos);

            return schedulerApiResponse;
        }else{
            Scheduler scheduler = result.get();
            todoRepository.createTodo(todos, scheduler);
            schedulerApiResponse.setSchedulerSeq(scheduler.getSchedulerSeq());
            schedulerApiResponse.setTodos(todos);

            return schedulerApiResponse;
        }



    }
}