package br.com.rocketseat.todolist.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.rocketseat.todolist.model.User;
import br.com.rocketseat.todolist.repository.IUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * MODIFICADORES
 * public
 * private
 * protected
 */
@RestController
@RequestMapping("/users")
public class UserController {
    /**
     * TIPOS DE DADOS DO MÉTODO
     * String - Textos
     * Integer - (int) números inteiros
     * Double - (double) números 0.0000
     * Float - (float) números 0.000
     * char - (A, B, C) para caracter
     * Date - (data) para datas
     * void - quando não precisa de nenhum retorno
     */
    /**
     * Vem dentro do Body
     */

    private final IUserRepository userRepository;

    public UserController(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/")
    public ResponseEntity create(@RequestBody User user) {
        var newUser = this.userRepository.findByUsername(user.getUsername());

        if(newUser != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
        }

        var passwordHashred = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());

        user.setPassword(passwordHashred);

        var userCreated = this.userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
