package com.interlig.solar.solar.service;

import com.interlig.solar.solar.model.Usuario;
import com.interlig.solar.solar.repository.usuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class usuarioService implements usuarioServiceInterface{
    @Autowired
    private usuarioRepository repository;

    @Override
    public Optional<Usuario> CriarUsuario(Usuario user) {

        repository.save(user);
        return repository.findById(user.getUsuario_id());
    }

    @Override
    public void DeletePorId(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Usuario BuscarPorCPF(String CPF) {
        return repository.buscarCPF(CPF);
    }

    @Override
    public Usuario AtualizarUsuario(Long id, Usuario usuarioAtualizado) {
        Usuario usuarioExistente = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario com ID " + id + " não encontrado."));

        // Atualiza os campos usando operador ternário
        usuarioExistente.setNome(usuarioAtualizado.getNome() != null ? usuarioAtualizado.getNome() : usuarioExistente.getNome());
        usuarioExistente.setCpf(usuarioAtualizado.getCpf() != null ? usuarioAtualizado.getCpf() : usuarioExistente.getCpf());
        usuarioExistente.setEmail(usuarioAtualizado.getEmail() != null ? usuarioAtualizado.getEmail() : usuarioExistente.getEmail());
        usuarioExistente.setSenha(usuarioAtualizado.getSenha() != null ? usuarioAtualizado.getSenha() : usuarioExistente.getSenha());
        usuarioExistente.setRole(usuarioAtualizado.getRole() != null ? usuarioAtualizado.getRole() : usuarioExistente.getRole());
        usuarioExistente.setTelefone(usuarioAtualizado.getTelefone() != null ? usuarioAtualizado.getTelefone() : usuarioExistente.getTelefone());

        // Salva as alterações no banco de dados
        return repository.save(usuarioExistente);

    }

    @Override
    public Optional<Usuario> BuscarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Usuario> listarTodos() {
        return repository.findAll();
    }
}
