package com.financas.api_finacas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacoes")
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message = "Descrição é obrigatória")
    @Size(min =3, max =100)
    @Column(nullable = false)
    private String descricao;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @NotNull(message = "Tipo é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTransacao tipo;

    @NotBlank(message = "Categoria é obrigatória")
    @Column(nullable = false)
    private String categoria;

    @Column(name="data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public Transacao() {
    }

    public Transacao( String descricao, BigDecimal valor,  TipoTransacao tipo, String categoria) {
        this.descricao = descricao;
        this.valor = valor;
        this.tipo = tipo;
        this.categoria = categoria;
        this.dataCriacao = LocalDateTime.now();
    }

    public Transacao(String descricao, BigDecimal valor, TipoTransacao tipo, String categoria, Usuario usuario) {
        this.descricao = descricao;
        this.valor = valor;
        this.tipo = tipo;
        this.categoria = categoria;
        this.usuario = usuario;
        this.dataCriacao = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
