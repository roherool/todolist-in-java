package br.com.rocketseat.todolist.repository;

import br.com.rocketseat.todolist.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ITaskRepositoty extends JpaRepository<Task, UUID> {
    List<Task> findByIdUser(UUID idUser);
}
