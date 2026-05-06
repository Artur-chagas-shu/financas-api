package com.financas.api_finacas.repository;

import com.financas.api_finacas.model.TipoTransacao;
import com.financas.api_finacas.model.Transacao;
import com.financas.api_finacas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    List<Transacao> findByTipo(TipoTransacao tipo);

    List<Transacao> findByCategoria(String categoria);

    List<Transacao> findByDataCriacaoBetween(LocalDateTime inicio, LocalDateTime fim);

    List<Transacao> findByUsuario(Usuario usuario);

    @Query("SELECT SUM(t.valor) FROM Transacao t WHERE t.tipo = :tipo")
    BigDecimal sumByTipo(@Param("tipo") TipoTransacao tipo);

    @Query("SELECT SUM(t.valor) FROM Transacao t WHERE t.tipo = :tipo AND t.usuario = :usuario")
    BigDecimal sumByTipoAndUsuario(@Param("tipo") TipoTransacao tipo, @Param("usuario") Usuario usuario);
}