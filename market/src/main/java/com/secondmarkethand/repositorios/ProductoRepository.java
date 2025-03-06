package com.secondmarkethand.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.secondmarkethand.models.Compra;
import com.secondmarkethand.models.Producto;
import com.secondmarkethand.models.Usuario;


public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByPropietario(Usuario propietario);
    
    List<Producto> findByCompra(Compra compra);
    
    List <Producto> findByCompraIsNull();
    
    List<Producto> findByNombreContainsIgnoreCaseAndCompraIsNull(String nombre);

    List <Producto> findByNombreContainsIgnoreCaseAndPropietario(String nombre, Usuario u);

}
