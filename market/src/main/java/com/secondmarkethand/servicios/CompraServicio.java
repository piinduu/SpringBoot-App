package com.secondmarkethand.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.secondmarkethand.models.Compra;
import com.secondmarkethand.models.Producto;
import com.secondmarkethand.models.Usuario;
import com.secondmarkethand.repositorios.CompraRepository;

@Service
public class CompraServicio {

    @Autowired
    CompraRepository repositorio;

    @Autowired
    ProductoServicio productoServicio;

    public Compra insertar(Compra compra, Usuario u) {
        compra.setPropietario(u);
        return repositorio.save(compra);
    }

    public Compra insertar(Compra compra) {
        return repositorio.save(compra);
    }

    public Producto addProductoCompra(Producto p, Compra c) {
        p.setCompra(c);
        return productoServicio.editar(p);
    }

    public Compra findById(long id) {
        return repositorio.findById(id).orElse(null);
    }

    public List<Compra> todas() {
        return repositorio.findAll();
    }

    public List<Compra> porPropietario(Usuario u) {
        return repositorio.findByPropietario(u);
    }

    public Compra buscarPorId(Long id) {
        return repositorio.findById(id).orElse(null);
    }
}
