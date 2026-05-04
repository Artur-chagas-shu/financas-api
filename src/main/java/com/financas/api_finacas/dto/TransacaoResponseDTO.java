package com.financas.api_finacas.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransacaoResponseDTO {
    private Long id;
    private String descricao;
    private BigDecimal valor;
    private String tipo;
    private String categoria;
    private LocalDateTime dataCriacao;

    public TransacaoResponseDTO(Long id, String descricao, BigDecimal valor, String tipo, String categoria, LocalDateTime dataCriacao) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.tipo = tipo;
        this.categoria = categoria;
        this.dataCriacao = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getTipo() {
        return tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
}
