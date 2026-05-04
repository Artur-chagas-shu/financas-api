package com.financas.api_finacas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class TrasacaoRequestDTO {


    @NotBlank(message="Descrição é obrigatoria")
    private String descricao;

    @NotNull(message = "Valor é obrigatori")
    @Positive(message = "Valor deve ser positivo")
    private BigDecimal valor;

    @NotBlank(message = "Tipo é obrigatorio (RECEITA ou DESPESA)")
    private string tipo ;

    @NotBlank(message = "Categoria é obrigatoria")
    private String categoria;

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

    public string getTipo() {
        return tipo;
    }

    public void setTipo(string tipo) {
        this.tipo = tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
