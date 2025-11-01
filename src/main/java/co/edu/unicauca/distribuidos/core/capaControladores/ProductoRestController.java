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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ProductoDTOPeticion;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ProductoDTORespuesta;
import co.edu.unicauca.distribuidos.core.fachadaServices.services.IProductoService;

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
    @PostMapping("/productos")
    public ProductoDTORespuesta crearProducto(@RequestBody ProductoDTOPeticion producto) {
        ProductoDTORespuesta objProducto = null;
        objProducto = productoService.save(producto);
        return objProducto;
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
