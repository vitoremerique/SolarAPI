package com.interlig.solar.solar.controller;


import com.interlig.solar.solar.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.interlig.solar.solar.service.usuarioService;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class usuarioController {

    @Autowired
    private usuarioService usuarioService;


    @PostMapping
    public ResponseEntity<Optional<Usuario>> criarUsuario(@RequestBody Usuario usuario) {
        try {
            Optional<Usuario> usuarioCriado = usuarioService.CriarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
        } catch (Exception e) {
            // Logar o erro para depuração
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        try {
            Optional<Usuario> usuario = usuarioService.BuscarPorId(id);
            if (usuario.isPresent()) {
                usuarioService.DeletePorId(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            // Logar a exceção para depuração
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Usuario> buscarPorNome(@PathVariable String cpf){

            Usuario usuario = usuarioService.BuscarPorCPF(cpf);
            if (usuario !=null) {
                return ResponseEntity.status(HttpStatus.OK).body(usuario);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
    }


    @PatchMapping("/alterar/{id}")
    public ResponseEntity<Object> alterarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioAtualizado) {
        try {
            // Tentar alterar o usuário
            Usuario usuarioAlterado = usuarioService.AtualizarUsuario(id, usuarioAtualizado);
            return ResponseEntity.status(HttpStatus.OK).body(usuarioAlterado); // 200 OK
        } catch (RuntimeException e) {
            // Caso ocorra um erro no serviço
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar a solicitação: " + e.getMessage()); // 500 Internal Server Error
        }
    }




}
