package net.david.todo.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.david.todo.dto.TodoDto;
import net.david.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class TodoController {
    private TodoService todoService;

    // Build add Todo REST API
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TodoDto> addTodo(@Valid @RequestBody TodoDto todoDto) {
        TodoDto savedTodoDto = todoService.addTodo(todoDto);
        return new ResponseEntity<>(savedTodoDto, HttpStatus.CREATED);
    }

    // Build get Todo REST API
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getTodo(@PathVariable("id") Long todoId) {
        TodoDto todoDto = todoService.getTodo(todoId);
        return ResponseEntity.ok(todoDto);
    }

    // Build get All Todos REST API
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<TodoDto>> GetAllTodo() {
        List<TodoDto> listTodoDto = todoService.getAllTodos();
        return ResponseEntity.ok(listTodoDto);
    }

    // Build update Todo REST API
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateTodo(@PathVariable("id") Long todoId, @Valid @RequestBody TodoDto todoDto) {
        TodoDto updatedTodoDto = todoService.updateTodo(todoId, todoDto);
        return ResponseEntity.ok(updatedTodoDto);
    }

    // Build delete Todo REST API
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable("id") Long todoId) {
        todoService.deleteTodo(todoId);
        return ResponseEntity.ok("Todo deleted successfully");
    }

    // Build complete Todo REST API
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PatchMapping("/{id}/complete")
    public ResponseEntity<TodoDto> completeTodo(@PathVariable("id") Long todoId) {
        TodoDto todoDto = todoService.completeTodo(todoId);
        return ResponseEntity.ok(todoDto);
    }

    // Build incomplete Todo REST API
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PatchMapping("/{id}/incomplete")
    public ResponseEntity<TodoDto> incompleteTodo(@PathVariable("id") Long todoId) {
        TodoDto todoDto = todoService.incompleteTodo(todoId);
        return ResponseEntity.ok(todoDto);
    }
}
