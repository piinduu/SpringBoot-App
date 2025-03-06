package com.secondmarkethand.controladores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.secondmarkethand.models.Compra;
import com.secondmarkethand.models.Producto;
import com.secondmarkethand.models.Usuario;
import com.secondmarkethand.servicios.CompraServicio;
import com.secondmarkethand.servicios.ProductoServicio;
import com.secondmarkethand.servicios.UsuarioServicio;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/app")
public class CompraController {
    @Autowired
    CompraServicio compraServicio;

    @Autowired
    ProductoServicio productoServicio;

    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    HttpSession httpSession;

    private Usuario usuario;

    @ModelAttribute("carrito")
    public List<Producto> productosCarrito() {
        @SuppressWarnings("unchecked")
        List<Long> contenido = (List<Long>) httpSession.getAttribute("carrito");
        return (contenido != null) ? null : productoServicio.variosPorId(contenido);
    }

    @ModelAttribute("total_carrito")
    public double totalCarrito() {
        List<Producto> carrito = productosCarrito();
        if (productosCarrito() != null) {
            return productosCarrito().stream().mapToDouble(Producto::getPrecio).sum();
        } else {
            return 0.0;
        }
    }

    @ModelAttribute("mis_compras")
    public List<Compra> misCompras() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioServicio.buscarPorEmail(email);
        return compraServicio.porPropietario(usuario);
    }

    public String verCarrito(Model model) {
        return "app/compra/carrito";
    }

    @GetMapping("/carrito/add/{id}")
    public String addCarrito(Model model, @PathVariable Long id) {
        @SuppressWarnings("unchecked")
        List<Long> contenido = (List<Long>) httpSession.getAttribute("carrito");
        if (contenido == null) {
            contenido = new ArrayList<>();
        }
        if (!contenido.contains(id)) {
            contenido.add(id);
        }
        httpSession.setAttribute("carrito", contenido);
        return "redirect:/app/carrito";
    }

    @GetMapping("/carrito/eliminar/{id}")
    public String eliminarCarrito(Model model, @PathVariable Long id) {
        @SuppressWarnings("unchecked")
        List<Long> contenido = (List<Long>) httpSession.getAttribute("carrito");
        if(contenido == null) {
            return "redirect:/public";
        }
        contenido.remove(id);
        if(contenido.isEmpty()) {
            httpSession.removeAttribute("carrito");
        } else {
            httpSession.setAttribute("carrito", contenido);
        }
        return "redirect:/app/carrito";
    }

    @GetMapping("/carrito/finalizar")
    public String checkOut(){
        List<Long> contenido = (List<Long>) httpSession.getAttribute("carrito");
        if(contenido == null) {
            return "redirect:/public";
        }
        List <Producto> productos = productosCarrito();

        Compra compra = compraServicio.insertar(new Compra(), usuario);

        productos.forEach(p -> compraServicio.addProductoCompra(p, compra));
        httpSession.removeAttribute("carrito");
        return "redirect:/app/compra/factura" + compra.getId();
    }

    @GetMapping("/compra/factura/{id}")
    public String factura(Model model, @PathVariable Long id) {
        Compra compra = compraServicio.buscarPorId(id);
        List <Producto> productos = productoServicio.productosDeUnaCompra(compra);
        model.addAttribute("productos", productos);
        model.addAttribute("compra", compra);
        model.addAttribute("total_compra", productos.stream().mapToDouble(Producto::getPrecio).sum());
        return "app/compra/factura";
    }

    @GetMapping("/mis_compras")
    public String misCompras(Model model) {
        return "app/compra/listado";
    }
}
