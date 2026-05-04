package com.financas.api_finacas.dto;

import java.math.BigDecimal;

public class ResumoFinanceiroDTO {

    private BigDecimal totalDespesas;
    private BigDecimal totalReceitas;
    private BigDecimal saldos;

    public ResumoFinanceiroDTO(BigDecimal totalDespesas, BigDecimal totalReceitas, BigDecimal saldos) {
        this.totalDespesas = totalDespesas;
        this.totalReceitas = totalReceitas;
        this.saldos = saldos;
    }

    public BigDecimal getTotalDespesas() {
        return totalDespesas;
    }

    public BigDecimal getTotalReceitas() {
        return totalReceitas;
    }

    public BigDecimal getSaldos() {
        return saldos;
    }
}
