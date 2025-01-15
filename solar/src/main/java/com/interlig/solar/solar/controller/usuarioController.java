package com.interlig.solar.solar.controller;


import com.interlig.solar.solar.model.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.interlig.solar.solar.service.usuarioService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class usuarioController {

    @Autowired
    private usuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos(){
        List<Usuario> lista = usuarioService.listarTodos();

        if(lista.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(lista);
    }


        @GetMapping("/me")
        public ResponseEntity<?> getCurrentUser(Authentication authentication) {
            // Recupera o usuário autenticado
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return ResponseEntity.ok(userDetails);
        }




    @PostMapping
    public ResponseEntity<Optional<Usuario>> criarUsuario(@RequestBody @Valid Usuario usuario) {
        if(this.usuarioService.BuscarPorCPF(usuario.getCpf())!= null) return ResponseEntity.badRequest().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(usuario.getPassword());
        usuario.setSenha(encryptedPassword);
        usuarioService.CriarUsuario(usuario);
        return ResponseEntity.ok().build();
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

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Usuario> buscarPorNome(@PathVariable String nome){

            Usuario usuario = usuarioService.BuscarPorCPF(nome);
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
