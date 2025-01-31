package net.david.todo.service.impl;

import lombok.AllArgsConstructor;
import net.david.todo.dto.TodoDto;
import net.david.todo.entity.Todo;
import net.david.todo.exception.ResourceNotFoundException;
import net.david.todo.repository.TodoRepository;
import net.david.todo.service.TodoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository;
    private ModelMapper modelMapper;

    @Override
    public TodoDto addTodo(TodoDto todoDto) {
        Todo todo = modelMapper.map(todoDto, Todo.class);

        Todo savedTodo = todoRepository.save(todo);

        TodoDto savedTodoDto = modelMapper.map(savedTodo, TodoDto.class);
        return savedTodoDto;
    }

    @Override
    public TodoDto getTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new ResourceNotFoundException("Todo not found"));
        TodoDto todoDto = modelMapper.map(todo, TodoDto.class);
        return todoDto;
    }

    @Override
    public List<TodoDto> getAllTodos() {
        List<Todo> listTodo = todoRepository.findAll();
        List<TodoDto> listTodoDto = listTodo.stream().map(todo -> modelMapper.map(todo, TodoDto.class)).toList();
        return listTodoDto;
    }

    @Override
    public TodoDto updateTodo(Long todoId, TodoDto todoDto) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new ResourceNotFoundException("Todo not found"));
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.getCompleted());
        Todo updatedTodo = todoRepository.save(todo);
        TodoDto updatedTodoDto = modelMapper.map(updatedTodo, TodoDto.class);
        return updatedTodoDto;
    }

    @Override
    public void deleteTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new ResourceNotFoundException("Todo not found"));
        todoRepository.deleteById(todoId);
    }

    @Override
    public TodoDto completeTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new ResourceNotFoundException("Todo not found"));
        todo.setCompleted(true);
        Todo updatedTodo = todoRepository.save(todo);
        TodoDto todoDto = modelMapper.map(todo, TodoDto.class);
        return todoDto;
    }

    @Override
    public TodoDto incompleteTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new ResourceNotFoundException("Todo not found"));
        todo.setCompleted(false);
        Todo updatedTodo = todoRepository.save(todo);
        TodoDto todoDto = modelMapper.map(todo, TodoDto.class);
        return todoDto;
    }
}
