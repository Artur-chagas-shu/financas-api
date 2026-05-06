package com.financas.api_finacas.service;


import com.financas.api_finacas.dto.ResumoFinanceiroDTO;
import com.financas.api_finacas.dto.TransacaoRequestDTO;
import com.financas.api_finacas.dto.TransacaoResponseDTO;
import com.financas.api_finacas.model.TipoTransacao;
import com.financas.api_finacas.model.Transacao;
import com.financas.api_finacas.model.Usuario;
import com.financas.api_finacas.repository.UsuarioRepository;
import com.financas.api_finacas.repository.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository repository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario getUsuarioLogado() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();
            return usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        }
        throw new RuntimeException("Usuário não autenticado");
    }

    public TransacaoResponseDTO criar(TransacaoRequestDTO dto) {
        Usuario usuarioLogado = getUsuarioLogado();
        TipoTransacao tipo = TipoTransacao.valueOf(dto.getTipo().toUpperCase());
        Transacao transacao = new Transacao(dto.getDescricao(), dto.getValor(), tipo, dto.getCategoria());
        transacao.setUsuario(usuarioLogado);
        Transacao salva = repository.save(transacao);
        return toResponseDTO(salva);
    }

    public List<TransacaoResponseDTO> listarTodas() {
        Usuario usuario = getUsuarioLogado();
        return repository.findByUsuario(usuario)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public TransacaoResponseDTO buscarPorId(Long id) {
        Usuario usuario = getUsuarioLogado();
        Transacao transacao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada com id " + id));
        if (!transacao.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Acesso negado a esta transação");
        }
        return toResponseDTO(transacao);
    }

    public TransacaoResponseDTO atualizar(Long id, TransacaoRequestDTO dto) {
        Usuario usuario = getUsuarioLogado();
        Transacao transacao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));
        if (!transacao.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Acesso negado a esta transação");
        }
        transacao.setDescricao(dto.getDescricao());
        transacao.setValor(dto.getValor());
        transacao.setTipo(TipoTransacao.valueOf(dto.getTipo().toUpperCase()));
        transacao.setCategoria(dto.getCategoria());
        Transacao atualizada = repository.save(transacao);
        return toResponseDTO(atualizada);
    }

    public void deletar(Long id) {
        Usuario usuario = getUsuarioLogado();
        Transacao transacao = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada"));
        if (!transacao.getUsuario().getId().equals(usuario.getId())) {
            throw new RuntimeException("Acesso negado a esta transação");
        }
        repository.deleteById(id);
    }

    public ResumoFinanceiroDTO obterResumo() {
        Usuario usuario = getUsuarioLogado();
        BigDecimal totalReceitas = repository.sumByTipoAndUsuario(TipoTransacao.RECEITA, usuario);
        BigDecimal totalDespesas = repository.sumByTipoAndUsuario(TipoTransacao.DESPESA, usuario);

        if (totalReceitas == null) totalReceitas = BigDecimal.ZERO;
        if (totalDespesas == null) totalDespesas = BigDecimal.ZERO;

        BigDecimal saldo = totalReceitas.subtract(totalDespesas);
        return new ResumoFinanceiroDTO(totalReceitas, totalDespesas, saldo);
    }

    private TransacaoResponseDTO toResponseDTO(Transacao transacao) {
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

