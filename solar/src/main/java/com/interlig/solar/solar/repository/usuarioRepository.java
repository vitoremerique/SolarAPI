package com.interlig.solar.solar.repository;

import com.interlig.solar.solar.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface usuarioRepository extends JpaRepository<Usuario,Long> {
    Usuario findByNomeIgnoreCase(String nome);
    Usuario findByCPF(String CPF);
}
