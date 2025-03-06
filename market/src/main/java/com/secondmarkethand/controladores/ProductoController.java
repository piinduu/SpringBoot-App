package com.secondmarkethand.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.secondmarkethand.models.Producto;
import com.secondmarkethand.models.Usuario;
import com.secondmarkethand.servicios.ProductoServicio;
import com.secondmarkethand.servicios.UsuarioServicio;
import com.secondmarkethand.upload.StorageService;

@Controller
@RequestMapping("/app")
public class ProductoController {

    @Autowired
    ProductoServicio productoServicio;

    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    StorageService storageService;

    private Usuario usuario;

    @ModelAttribute("misproductos")
    public List<Producto> misProductos() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        usuario = usuarioServicio.buscarPorEmail(email);
        return productoServicio.productosDeUnPropietario(usuario);
    }

    @ModelAttribute("mis_productos")
    public String list(Model model, @RequestParam(name = "q", required = false) String query) {
        if (query != null) {
            model.addAttribute("misproductos", productoServicio.buscarMisProductos(query, usuario));
        }
        return "app/producto/lista";
    }

    @GetMapping("/mis_productos/{id}/eliminar")
    public String eliminar(@PathVariable Long id) {
        Producto p = productoServicio.findById(id);
        if (p.getCompra() == null) {
            productoServicio.borrar(id);
        }
        return "redirect:/app/mis_productos";
    }

    @PostMapping("/producto/nuevo/submit")
    public String nuevoProductoSubmit(@ModelAttribute Producto producto, @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            String imagen = storageService.store(file);
            producto.setImagen(MvcUriComponentsBuilder
                    .fromMethodName(FilesController.class, "serveFile", imagen).build().toUriString());
        }
        producto.setPropietario(usuario);
        productoServicio.insertar(producto);
        return "redirect:/app/mis_productos";
    }
}
