package br.com.rocketseat.todolist.controller;

import br.com.rocketseat.todolist.model.Task;
import br.com.rocketseat.todolist.repository.ITaskRepositoty;
import br.com.rocketseat.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final ITaskRepositoty taskRepositoty;

    public TaskController(ITaskRepositoty taskRepositoty) {
        this.taskRepositoty = taskRepositoty;
    }

    @PostMapping("/")
    public ResponseEntity create(@RequestBody Task task, HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");
        task.setIdUser((UUID) idUser);

        var currentDate = LocalDateTime.now();

        if (currentDate.isAfter(task.getStartAt()) || currentDate.isAfter(task.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data inícial deve ser maior que a data atual.");
        }

        if (task.getStartAt().isAfter(task.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data inícial deve ser menor que a data final.");
        }

        var newTask = this.taskRepositoty.save(task);
        return ResponseEntity.status(HttpStatus.OK).body(newTask);
    }

    @GetMapping("/")
    public List<Task> list(HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");
        var tasks = this.taskRepositoty.findByIdUser((UUID) idUser);
        return tasks;
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody Task task, @PathVariable UUID id, HttpServletRequest request) {
        var updateTask = this.taskRepositoty.findById(id).orElse(null);
        if (updateTask == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa não encontrada.");
        }

        var idUser = request.getAttribute("idUser");
        if (!updateTask.getIdUser().equals(idUser)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não tem permissão paa alterar essa tarefa.");
        }

        Utils.copyNonNullProperties(task, updateTask);

        var taskUpdated = this.taskRepositoty.save(updateTask);
        return ResponseEntity.ok().body(taskUpdated);
    }
}
