package com.secondmarkethand.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.secondmarkethand.models.Compra;
import com.secondmarkethand.models.Producto;
import com.secondmarkethand.models.Usuario;
import com.secondmarkethand.repositorios.ProductoRepository;
import com.secondmarkethand.upload.StorageService;

@Service
public class ProductoServicio {
    @Autowired
    ProductoRepository repositorio;

    @Autowired
    StorageService storageService;

    public Producto insertar(Producto producto) {
        return repositorio.save(producto);
    }

    public void borrar(long id) {
        if(repositorio.existsById(id)) {
            Producto producto = repositorio.findById(id).get();
            if(!producto.getImagen().isEmpty()) {
                storageService.delete(producto.getImagen());
            }
        }
        repositorio.deleteById(id);
    }

    public void borrar(Producto producto) {
        if(!producto.getImagen().isEmpty()) {
            storageService.delete(producto.getImagen());
        }
        repositorio.delete(producto);
    }

    public Producto editar(Producto producto) {
        return repositorio.save(producto);
    }

    public Producto findById(long id) {
        return repositorio.findById(id).orElse(null);
    }

    public Iterable<Producto> findAll() {
        return repositorio.findAll();
    }

    public List<Producto> productosDeUnPropietario(Usuario u) {
        return repositorio.findByPropietario(u);
    }

    public List<Producto> productosDeUnaCompra(Compra c) {
        return repositorio.findByCompra(c);
    }

    public List<Producto> productosSinVender() {
        return repositorio.findByCompraIsNull();
    }

    public List<Producto> buscar(String query) {
        return repositorio.findByNombreContainsIgnoreCaseAndCompraIsNull(query);
    }

    public List<Producto> buscarMisProductos(String query, Usuario u) {
        return repositorio.findByNombreContainsIgnoreCaseAndPropietario(query, u);
    }

    public List<Producto> variosPorId (List<Long> ids) {
        return repositorio.findAllById(ids);
    }
}
