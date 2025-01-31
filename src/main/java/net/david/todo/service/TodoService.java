package net.david.todo.service;

import net.david.todo.dto.TodoDto;

import java.util.List;

public interface TodoService {
    public TodoDto addTodo(TodoDto todoDto);
    public TodoDto getTodo(Long todoId);
    public List<TodoDto> getAllTodos();
    public TodoDto updateTodo(Long todoId, TodoDto todoDto);
    public void deleteTodo(Long todoId);
    public TodoDto completeTodo(Long todoId);
    public TodoDto incompleteTodo(Long todoId);
}
