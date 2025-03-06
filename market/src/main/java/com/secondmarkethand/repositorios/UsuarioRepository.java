package com.secondmarkethand.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.secondmarkethand.models.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findFirstByEmail(String email);
}
