package dev.flab.studytogether.fakerepositories;

import dev.flab.studytogether.core.domain.schedule.entity.Scheduler;
import dev.flab.studytogether.core.domain.schedule.entity.Todo;
import dev.flab.studytogether.core.domain.schedule.repository.TodoRepository;
import dev.flab.studytogether.core.domain.schedule.CompleteStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

public class FakeTodoRepository implements TodoRepository {
    private final Collection<Todo> fakeTodos = new ArrayList<>();

    @Override
    public Todo save(String todoContent, Scheduler scheduler) {
        Todo newTodo = new Todo(
                getMaxTodoSequenceId() + 1,
                scheduler.getSchedulerSeq(),
                todoContent,
                CompleteStatus.UNCOMPLETED);

        fakeTodos.add(newTodo);
        return newTodo;
    }

    @Override
    public Optional<Todo> find(int schedulerSeq, long todoID) {
        return fakeTodos.stream()
                .filter(x -> x.getSchedulerSeq() == schedulerSeq && x.getTodoID() == todoID)
                .findFirst();
    }

    @Override
    public void updateContent(String todoContent, int schedulerSequenceId, long todoID) {
        fakeTodos.stream()
                .filter(todo -> todo.getTodoID() == todoID)
                .findFirst()
                .ifPresent(todo -> {
                    Todo updateTodo = new Todo(todo.getTodoID(), todo.getSchedulerSeq(), todoContent, todo.getCompleteStatus());
                    fakeTodos.remove(todo);
                    fakeTodos.add(updateTodo);
                });

    }

    @Override
    public void delete(int schedulerSeq, long todoID) {
        fakeTodos.stream()
                .filter(todo -> todo.getTodoID() == todoID)
                .findFirst()
                .ifPresent(fakeTodos::remove);
    }

    @Override
    public void updateCheckStatusToCompleted(int schedulerSeq, long todoID) {
        fakeTodos.stream()
                .filter(todo -> todo.getTodoID() == todoID)
                .findFirst()
                .ifPresent(todo -> {
                    Todo updateTodo = new Todo(todo.getTodoID(), todo.getSchedulerSeq(), todo.getTodoContent(), CompleteStatus.COMPLETED);
                    fakeTodos.remove(todo);
                    fakeTodos.add(updateTodo);
                });
    }

    @Override
    public void updateCheckStatusToUncompleted(int schedulerSeq, long todoID) {
        fakeTodos.stream()
                .filter(todo -> todo.getTodoID() == todoID)
                .findFirst()
                .ifPresent(todo -> {
                    Todo updateTodo = new Todo(todo.getTodoID(), todo.getSchedulerSeq(), todo.getTodoContent(), CompleteStatus.UNCOMPLETED);
                    fakeTodos.remove(todo);
                    fakeTodos.add(updateTodo);
                });
    }

    public long getMaxTodoSequenceId() {
        Optional<Todo> todoWithMaxSequenceId = fakeTodos.stream()
                .max(Comparator.comparing(Todo::getTodoID));

        return todoWithMaxSequenceId
                .map(Todo::getTodoID)
                .orElse(0L);
    }

}
