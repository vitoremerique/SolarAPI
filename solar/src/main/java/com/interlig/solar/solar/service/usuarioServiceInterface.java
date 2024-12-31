package com.interlig.solar.solar.service;

import com.interlig.solar.solar.model.Usuario;

import java.util.Optional;

public interface usuarioServiceInterface {
    Optional<Usuario> CriarUsuario(Usuario user);
    void DeletePorId(Long id);
    Usuario BuscarPorCPF(String CPF);
    Usuario AtualizarUsuario(Long id,Usuario user);
    Optional<Usuario> BuscarPorId(Long id);

}

