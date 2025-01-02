package com.interlig.solar.solar.repository;

import com.interlig.solar.solar.model.Processo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface processoRepository extends JpaRepository<Processo,Long> {



    @Query("SELECT p FROM Processo p WHERE p.usuario.id = :usuarioId")
    List<Processo> findByUsuarioId(@Param("usuarioId") Long usuarioId);

}
