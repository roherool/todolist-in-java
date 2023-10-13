package br.com.rocketseat.todolist.repository;

import br.com.rocketseat.todolist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);
}
