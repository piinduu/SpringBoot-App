package com.secondmarkethand.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.secondmarkethand.models.Usuario;
import com.secondmarkethand.repositorios.UsuarioRepository;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepository repositorio;

    @Autowired
    private PasswordEncoder encoder;

    public Usuario registrar(Usuario usuario) {
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        return repositorio.save(usuario);
    }

    public Usuario buscarPorEmail(String email) {
        return repositorio.findFirstByEmail(email);
    }

    public Usuario findById(long id) {
        return repositorio.findById(id).orElse(null);
    }
}
