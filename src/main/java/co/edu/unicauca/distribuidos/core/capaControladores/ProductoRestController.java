package co.edu.unicauca.distribuidos.core.capaControladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ProductoDTOPeticion;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ProductoDTORespuesta;
import co.edu.unicauca.distribuidos.core.fachadaServices.services.IProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})


public class ProductoRestController {
    @Autowired    //Otra forma de inyectar un objeto del contenedor de Spring
    private IProductoService productoService;

    @GetMapping("/productos")
    public List<ProductoDTORespuesta> listarProductos() {
        return productoService.findAll();
    }

    @GetMapping("/productos/{id}")
    public ProductoDTORespuesta consultarProducto(@PathVariable Integer id) {
        return productoService.findById(id);
    }
    /*
    // JSON-only endpoint (commentado). El cliente ahora siempre manda multipart/form-data
    @PostMapping("/productos")
    public ProductoDTORespuesta crearProducto(@RequestBody ProductoDTOPeticion producto) {
        ProductoDTORespuesta objProducto = null;
        objProducto = productoService.save(producto);
        return objProducto;
    }
    */

    /**
     * Endpoint para recibir multipart/form-data desde Angular o Postman.
     * Debe enviarse el campo 'producto' con el JSON del ProductoDTOPeticion
     * y opcionalmente 'imagen' con el archivo.
     * Content-Type: multipart/form-data
     */
    @PostMapping(value = "/productos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductoDTORespuesta crearProductoConImagen(@RequestPart("producto") String productoJson,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        ProductoDTOPeticion producto = mapper.readValue(productoJson, ProductoDTOPeticion.class);

        if (imagen != null && !imagen.isEmpty()) {
            // Carpeta uploads en la raíz del proyecto (puedes cambiarla a una ruta configurable)
            Path uploadDir = Paths.get("uploads").toAbsolutePath();
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            // Generar nombre único para evitar colisiones
            String original = imagen.getOriginalFilename();
            String ext = "";
            if (original != null && original.contains(".")) {
                ext = original.substring(original.lastIndexOf('.'));
            }
            String filename = UUID.randomUUID().toString() + ext;
            Path target = uploadDir.resolve(filename);

            // Guardar archivo
            imagen.transferTo(target.toFile());

            // Guardar la ruta relativa o absoluta según prefieras. Aquí guardamos ruta relativa.
            producto.setImagen("uploads/" + filename);
        }

        return productoService.save(producto);
    }
    
    @PutMapping("/productos/{id}")
    public ProductoDTORespuesta actualizarProducto(@PathVariable Integer id, @RequestBody ProductoDTOPeticion producto) {
        ProductoDTORespuesta objProducto = null;
        ProductoDTORespuesta productoActual = productoService.findById(id);
        // Solo actualizar si el producto existe
        if (productoActual != null) {
            objProducto = productoService.update(id, producto);
        }
        return objProducto;
    }
    @DeleteMapping("/productos/{id}")
    public boolean eliminarProducto(@PathVariable Integer id) {
        Boolean bandera = false;
        ProductoDTORespuesta productoActual = productoService.findById(id);
        if (productoActual != null) {
            bandera = productoService.delete(id);
        }
        return bandera;
    }
}
