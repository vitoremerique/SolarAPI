package com.interlig.solar.solar.controller;

import com.interlig.solar.solar.model.Processo;
import com.interlig.solar.solar.service.processoService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/processo")
public class processoController {

    @Autowired
    private processoService service;



    @PatchMapping("/alterar/{id}")
    public ResponseEntity<Object> alterarProcesso(@PathVariable Long id,@RequestBody Processo proc){
        try{
            Processo processo = service.atualizarProcesso(id,proc);
            return ResponseEntity.status(HttpStatus.OK).body(proc);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar a solicitação: " + e.getMessage()); // 500 Internal Server Error

        }

    }

    @PostMapping
    public ResponseEntity<Optional<Processo>> criarProcesso(@RequestBody Processo proc){
        try {
            Optional<Processo> processoCriado = service.CriarProcesso(proc);
            return ResponseEntity.status(HttpStatus.CREATED).body(processoCriado);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProcesso(@PathVariable Long id) {
        try {
            service.DeleteProcesso(id);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Retorna 404 Not Found
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 Internal Server Error
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Processo>> listarProcessosPorUsuario(@PathVariable Long usuarioId) {
        try {
            List<Processo> processos = service.listarProcessoPorIdUsuario(usuarioId);
            if (processos.isEmpty()) {
                return ResponseEntity.noContent().build(); // Retorna 204 No Content se não houver processos
            }
            return ResponseEntity.ok(processos); // Retorna 200 OK com a lista de processos
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 Internal Server Error
        }
    }

}
