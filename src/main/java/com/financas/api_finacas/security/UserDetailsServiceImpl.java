package com.financas.api_finacas.security;

import com.financas.api_finacas.model.Usuario;
import com.financas.api_finacas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("Usuario nào Encontrado:" + email));

        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .roles("USER")
                .build();

    }
}
