package com.secondmarkethand;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.secondmarkethand.models.Producto;
import com.secondmarkethand.models.Usuario;
import com.secondmarkethand.servicios.ProductoServicio;
import com.secondmarkethand.servicios.UsuarioServicio;
import com.secondmarkethand.upload.StorageService;

@SpringBootApplication
public class SecondHandMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecondHandMarketApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(UsuarioServicio usuarioServicio, ProductoServicio productoServicio) {
		return args -> {

			Usuario usuario = new Usuario("Luis Miguel", "López Magaña", null, "luismi.lopez@openwebinars.net",
					"luismi");
			usuario = usuarioServicio.registrar(usuario);

			Usuario usuario2 = new Usuario("Antonio", "García Martín", null, "antonio.garcia@openwebinars.net",
					"antonio");
			usuario2 = usuarioServicio.registrar(usuario2);

			List<Producto> listado = Arrays.asList(new Producto("Bicicleta de montaña", 100.0f,
					"https://biocyclespain.com/wp-content/uploads/2021/06/CRONO-NEGRA.png", usuario),
					new Producto("Golf GTI Serie 2", 2500.0f,
							"https://www.minicar.es/large/Volkswagen-Golf-GTi-G60-Serie-II-%281990%29-Norev-1%3A18-i22889.jpg",
							usuario),
					new Producto("Raqueta de tenis", 10.5f,
							"https://media.atmosferasport.es/335203-large_default/raqueta-de-tenis-wilson-roland-garros-team-102-naranja.jpg",
							usuario),
					new Producto("Xbox One X", 425.0f, "https://m.media-amazon.com/images/I/51vgPrCmZrL.jpg",
							usuario2),
					new Producto("Trípode flexible", 10.0f, "https://m.media-amazon.com/images/I/61elJrIxkyL.jpg",
							usuario2),
					new Producto("Iphone 7 128 GB", 350.0f,
							"https://cdsassets.apple.com/live/SZLF0YNV/images/sp/111943_iphone7-rosegold.png",
							usuario2));

			listado.forEach(productoServicio::insertar);

		};
	}

	/**
	 * Este bean se inicia al lanzar la aplicación. Nos permite inicializar el
	 * almacenamiento
	 * secundario del proyecto
	 * 
	 * @param storageService Almacenamiento secundario del proyecto
	 * @return
	 */
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}
}