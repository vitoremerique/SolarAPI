package com.interlig.solar.solar.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;


@Entity
@Table(name = "processo")
public class Processo  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @NotNull
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String data_inicio;

    private String data_final;

    @Enumerated(EnumType.STRING)
    private Status compra;

    @Enumerated(EnumType.STRING)
    private Status liberacao;

    @Enumerated(EnumType.STRING)
    private Status instalacao;

    @Enumerated(EnumType.STRING)
    private Status finalizacao;


    public Processo(){}

    public Processo(Long id, Usuario usuario, String data_inicio, String data_final, Status compra, Status liberacao, Status instalacao, Status finalizacao) {
        this.id = id;
        this.usuario = usuario;
        this.data_inicio = data_inicio;
        this.data_final = data_final;
        this.compra = compra;
        this.liberacao = liberacao;
        this.instalacao = instalacao;
        this.finalizacao = finalizacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario= usuario;
    }

    public String getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(String data_inicio) {
        this.data_inicio = data_inicio;
    }

    public String getData_final() {
        return data_final;
    }

    public void setData_final(String data_final) {
        this.data_final = data_final;
    }

    public Status getCompra() {
        return compra;
    }

    public void setCompra(Status compra) {
        this.compra = compra;
    }

    public Status getLiberacao() {
        return liberacao;
    }

    public void setLiberacao(Status liberacao) {
        this.liberacao = liberacao;
    }

    public Status getInstalacao() {
        return instalacao;
    }

    public void setInstalacao(Status instalacao) {
        this.instalacao = instalacao;
    }

    public Status getFinalizacao() {
        return finalizacao;
    }

    public void setFinalizacao(Status finalizacao) {
        this.finalizacao = finalizacao;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Processo{");
        sb.append("id=").append(id);
        sb.append(", usuario=").append(usuario);
        sb.append(", data_inicio='").append(data_inicio).append('\'');
        sb.append(", data_final='").append(data_final).append('\'');
        sb.append(", compra=").append(compra);
        sb.append(", liberacao=").append(liberacao);
        sb.append(", instalacao=").append(instalacao);
        sb.append(", finalizacao=").append(finalizacao);
        sb.append('}');
        return sb.toString();
    }
}
