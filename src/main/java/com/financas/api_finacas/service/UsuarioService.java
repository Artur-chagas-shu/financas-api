package com.financas.api_finacas.service;


import com.financas.api_finacas.model.Usuario;
import com.financas.api_finacas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Usuario cadastrar(String nome, String email, String senha){
        if (usuarioRepository.existsByEmail(email)){
            throw  new RuntimeException("Email já cadastrado");
        }
        String senhaCriptografada = encoder.encode(senha);
        Usuario novoUsuario = new Usuario(nome,email,senhaCriptografada);
        return usuarioRepository.save(novoUsuario);
    }

    public Optional<Usuario> buscarPorEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

    public boolean validarSenha(String senhaRaw, String senhaCriptografada){
        return encoder.matches(senhaRaw,senhaCriptografada);
    }








}
