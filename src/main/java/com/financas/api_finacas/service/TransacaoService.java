package com.financas.api_finacas.service;


import com.financas.api_finacas.dto.ResumoFinanceiroDTO;
import com.financas.api_finacas.dto.TransacaoRequestDTO;
import com.financas.api_finacas.dto.TransacaoResponseDTO;
import com.financas.api_finacas.model.TipoTransacao;
import com.financas.api_finacas.model.Transacao;
import com.financas.api_finacas.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository repository;


    public TransacaoResponseDTO criar(TransacaoRequestDTO dto) {
        TipoTransacao tipo = TipoTransacao.valueOf(dto.getTipo().toUpperCase());
        Transacao transacao = new Transacao(dto.getDescricao(), dto.getValor(), tipo, dto.getCategoria());
        Transacao salva = repository.save(transacao);
        return toResponseDTO(salva);
    }

    public List<TransacaoResponseDTO> listarTodos() {
        return repository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public TransacaoResponseDTO buscarPorId(Long id){
        Transacao transacao = repository.findById(id).orElseThrow(()-> new RuntimeException("Transação não encontrada com id " + id));
        return toResponseDTO(transacao);
    }

    public TransacaoResponseDTO atualizar(Long id, TransacaoRequestDTO dto){
        Transacao transacao = repository.findById(id).orElseThrow(()-> new RuntimeException("Transação não encotrada "));
        transacao.setDescricao(dto.getDescricao());
        transacao.setValor(dto.getValor());
        transacao.setTipo(TipoTransacao.valueOf(dto.getTipo().toUpperCase()));
        transacao.setCategoria(dto.getCategoria());
        Transacao atualizada = repository.save(transacao);
        return toResponseDTO(atualizada);
    }

    public void deletar(Long id){
        if(!repository.existsById(id)){
            throw new RuntimeException("Transação não encontrada");
        }
        repository.deleteById(id);
    }


    public ResumoFinanceiroDTO obterResumo(){
        BigDecimal totalReceitas = repository.sumByTipo(TipoTransacao.RECEITA);
        BigDecimal totalDespesas = repository.sumByTipo(TipoTransacao.DESPESA);

        if(totalReceitas == null) totalReceitas = BigDecimal.ZERO;
        if(totalDespesas == null) totalDespesas = BigDecimal.ZERO;

        BigDecimal saldo = totalReceitas.subtract(totalDespesas);
        return new ResumoFinanceiroDTO(totalReceitas, totalDespesas, saldo);
    }











    private TransacaoResponseDTO toResponseDTO(Transacao transacao){
        return new TransacaoResponseDTO(
                transacao.getId(),
                transacao.getDescricao(),
                transacao.getValor(),
                transacao.getTipo().toString(),
                transacao.getCategoria(),
                transacao.getDataCriacao()
        );
    }

}
