package br.com.rocketseat.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class MinhaPrimeiraController {
    /**
     * GET - Buscar uma informação
     * POST - Adicionar um dado/informação
     * PUT - Alterar um dado/informação
     * PATCH - Alterar um dado/informação específica
     * DELETE - Remover um dado/informação
     */
    @GetMapping("/")
    public String primeiraMensagem() {
        return "Funcionou!";
    }
}
