package com.interlig.solar.solar.repository;

import com.interlig.solar.solar.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface usuarioRepository extends JpaRepository<Usuario,Long> {
    Usuario findByNomeIgnoreCase(String nome);
    UserDetails findByCpf(String cpf);

    @Query("SELECT u FROM Usuario u WHERE u.cpf = :cpf")
    Usuario buscarCPF(@Param("cpf") String cpf);
}
