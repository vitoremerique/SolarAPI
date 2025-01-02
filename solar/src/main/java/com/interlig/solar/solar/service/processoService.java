package com.interlig.solar.solar.service;

import com.interlig.solar.solar.model.Processo;
import com.interlig.solar.solar.model.Usuario;
import com.interlig.solar.solar.repository.processoRepository;
import com.interlig.solar.solar.repository.usuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class processoService implements processoServiceInterface{

    @Autowired
    private processoRepository repository;

    @Autowired
    private usuarioRepository userRepo;

    public Optional<Processo> CriarProcesso(Processo proc) {
        // Busca o usuário no banco pelo ID
        if (proc.getUsuario() == null || proc.getUsuario().getUsuario_id() == null) {
            throw new IllegalArgumentException("ID do usuário é obrigatório.");
        }

        Usuario usuario = userRepo.findById(proc.getUsuario().getUsuario_id())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + proc.getUsuario().getUsuario_id()));

        // Associa o usuário ao processo
        proc.setUsuario(usuario);

        // Salva o processo no banco
        Processo processoSalvo = repository.save(proc);

        return Optional.of(processoSalvo);
    }


    @Override
    public void DeleteProcesso(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Processo com ID " + id + " não encontrado.");
        }
        repository.deleteById(id);
    }

    @Override
    public Optional<Processo> buscarPorId(Long id) {
        Optional<Processo> proc = repository.findById(id);
        if(proc.isEmpty()){
            return null;
        }
        return proc;
    }

    @Override
    public Processo atualizarProcesso(Long id, Processo proc) {
        // Verifica se o processo existe
        Processo processoExistente = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Processo com ID " + id + " não encontrado."));

        // Atualiza os campos
        processoExistente.setData_inicio(proc.getData_inicio() != null ? proc.getData_inicio() : processoExistente.getData_inicio());
        processoExistente.setData_final(proc.getData_final() != null ? proc.getData_final() : processoExistente.getData_final());
        processoExistente.setInstalacao(proc.getInstalacao() != null ? proc.getInstalacao() : processoExistente.getInstalacao());
        processoExistente.setCompra(proc.getCompra() != null ? proc.getCompra() : processoExistente.getCompra());
        processoExistente.setLiberacao(proc.getLiberacao() != null ? proc.getLiberacao() : processoExistente.getLiberacao());
        processoExistente.setFinalizacao(proc.getFinalizacao() != null ? proc.getFinalizacao() : processoExistente.getFinalizacao());

        // Atualiza o usuário, se fornecido
        if (proc.getUsuario() != null && proc.getUsuario().getUsuario_id() != null) {
            Optional<Usuario> usuarioExistente = userRepo.findById(proc.getUsuario().getUsuario_id());
            if (usuarioExistente.isEmpty()) {
                throw new IllegalArgumentException("Usuário com ID " + proc.getUsuario().getUsuario_id() + " não encontrado.");
            }
            processoExistente.setUsuario(usuarioExistente.get());
        }

        // Salva o processo atualizado
        return repository.save(processoExistente);
    }



    @Override
    public List<Processo> listarProcessoPorIdUsuario(Long id){
        List<Processo> lista = repository.findByUsuarioId(id);
        if(lista.isEmpty()){
            return null;
        }
        return lista;

    }

}
