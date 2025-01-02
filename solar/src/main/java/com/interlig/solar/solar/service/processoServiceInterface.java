package com.interlig.solar.solar.service;

import com.interlig.solar.solar.model.Processo;

import java.util.List;
import java.util.Optional;

public interface processoServiceInterface {
    Optional<Processo> CriarProcesso(Processo proc);
    void DeleteProcesso(Long id);
    Optional<Processo> buscarPorId(Long id);
    Processo atualizarProcesso(Long id, Processo proc);
    List<Processo> listarProcessoPorIdUsuario(Long id);
}
