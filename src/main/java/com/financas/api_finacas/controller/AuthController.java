package com.financas.api_finacas.controller;

import com.financas.api_finacas.model.Usuario;
import com.financas.api_finacas.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import com.financas.api_finacas.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map< String, String> dados){
        try{
            String nome = dados.get("nome");
            String email = dados.get("email");
            String senha = dados.get("senha");

            Usuario usuario = usuarioService.cadastrar(nome, email,senha);
            return ResponseEntity.ok(Map.of("message", "Usuário criado com sucesso!", "id", usuario.getId()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map< String, String> credenciais){
        String email = credenciais.get("email");
        String senha = credenciais.get("senha");

        Optional<Usuario> usuarioOpt = usuarioService.buscarPorEmail(email);
        if(usuarioOpt.isEmpty() || !usuarioService.validarSenha(senha,usuarioOpt.get().getSenha())){
            return ResponseEntity.status(401).body(Map.of("error", "Email ou Senha inválidos"));
        }

        Usuario usuario = usuarioOpt.get();
        String token = jwtUtil.gerarToken(usuario.getEmail());

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("token", token);
        resposta.put("usuario",Map.of("id", usuario.getId(), "email", usuario.getEmail()));

        return ResponseEntity.ok(resposta);

    }















}
