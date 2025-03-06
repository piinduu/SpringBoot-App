package com.secondmarkethand.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.secondmarkethand.models.Compra;
import com.secondmarkethand.models.Usuario;

public interface CompraRepository extends JpaRepository<Compra, Long> {

    List<Compra> findByPropietario(Usuario propietario);
    
}
