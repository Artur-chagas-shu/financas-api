package com.financas.api_finacas.controller;


import com.financas.api_finacas.dto.ResumoFinanceiroDTO;
import com.financas.api_finacas.dto.TransacaoRequestDTO;
import com.financas.api_finacas.dto.TransacaoResponseDTO;
import com.financas.api_finacas.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
@CrossOrigin(origins = "*")
public class TransacaoController {

    @Autowired
    private TransacaoService service;

    @GetMapping
    public ResponseEntity<List<TransacaoResponseDTO>> ListarTodas()  {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransacaoResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<TransacaoResponseDTO> criar(@Valid @RequestBody TransacaoRequestDTO dto){
        TransacaoResponseDTO nova = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nova);

    }

    @PutMapping("/{id}")
    public ResponseEntity<TransacaoResponseDTO> atualizar(@PathVariable Long id,
                                                          @Valid @RequestBody TransacaoRequestDTO dto){
        return ResponseEntity.ok(service.atualizar(id,dto));
    }

    @GetMapping("/resumo")
    public ResponseEntity<ResumoFinanceiroDTO> obterResumo(){
        return ResponseEntity.ok(service.obterResumo());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
