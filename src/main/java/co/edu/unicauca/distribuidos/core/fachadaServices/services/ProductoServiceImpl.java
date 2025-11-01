package co.edu.unicauca.distribuidos.core.fachadaServices.services;


import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.ProductoEntity;
import co.edu.unicauca.distribuidos.core.capaAccesoADatos.repositories.ProductoRepositoryBaseDatos;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ProductoDTOPeticion;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ProductoDTORespuesta;

@Service
public class ProductoServiceImpl implements IProductoService {
    //Para invocar los métodos de acceso a datos
    private ProductoRepositoryBaseDatos servicioAccesoBaseDatos;
    //Para mapear entre Entity y DTO
    private ModelMapper modelMapper;

    public ProductoServiceImpl(ProductoRepositoryBaseDatos servicioAccesoBaseDatos, ModelMapper modelMapper) {
        this.servicioAccesoBaseDatos = servicioAccesoBaseDatos;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductoDTORespuesta> findAll() {
        List<ProductoDTORespuesta> listaRetornar;
        Optional<Collection<ProductoEntity>> productosEntityOpt = this.servicioAccesoBaseDatos.findAll();
        
        if (productosEntityOpt.isEmpty()) {
            listaRetornar = List.of();
        } else {
            Collection<ProductoEntity> productosEntity = productosEntityOpt.get();
            listaRetornar = this.modelMapper.map(productosEntity, new TypeToken<List<ProductoDTORespuesta>>() {}.getType());
        }
    
        return listaRetornar;
    }

    @Override
    public ProductoDTORespuesta findById(Integer id) {
        ProductoDTORespuesta productoRetornar = null;
        Optional<ProductoEntity> optionalProducto = this.servicioAccesoBaseDatos.findById(id);
        if (optionalProducto.isPresent()) {
            ProductoEntity productoEntity = optionalProducto.get();
            productoRetornar = this.modelMapper.map(productoEntity, ProductoDTORespuesta.class);
        }
        return productoRetornar;
    }

    @Override
    public ProductoDTORespuesta save(ProductoDTOPeticion producto) {
        ProductoEntity productoEntity = this.modelMapper.map(producto, ProductoEntity.class);
        // Asegurar que la categoría se mapee correctamente: ProductoDTOPeticion tiene "objCategoria"
        // y ProductoEntity tiene "categoria". ModelMapper puede no mapear este campo por nombre,
        // así que lo configuramos explícitamente para evitar NPE al persistir.
        if (producto.getObjCategoria() != null) {
            productoEntity.setCategoria(new co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.CategoriaEntity(producto.getObjCategoria().getId(), null));
        }
        productoEntity.setEstado(true);
        ProductoEntity objProductoEntity = this.servicioAccesoBaseDatos.save(productoEntity);
        System.out.println(objProductoEntity);
        ProductoDTORespuesta productoDTO = this.modelMapper.map(objProductoEntity, ProductoDTORespuesta.class);
        return productoDTO;

    }

    @Override
    public ProductoDTORespuesta update(Integer id, ProductoDTOPeticion producto) {
        ProductoEntity productoActualizado = null;
        Optional<ProductoEntity> productoEntityOp = this.servicioAccesoBaseDatos.findById(id);
        if (productoEntityOp.isPresent()) {
            ProductoEntity objProductoDatosNuevos = productoEntityOp.get();
            objProductoDatosNuevos.setNombre(producto.getNombre());
            objProductoDatosNuevos.setPrecio(producto.getPrecio());
            objProductoDatosNuevos.setImagen(producto.getImagen());
            // Asegurar que la categoría exista antes de setear el id
            if (producto.getObjCategoria() != null) {
                if (objProductoDatosNuevos.getCategoria() == null) {
                    objProductoDatosNuevos.setCategoria(new co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.CategoriaEntity(producto.getObjCategoria().getId(), null));
                } else {
                    objProductoDatosNuevos.getCategoria().setId(producto.getObjCategoria().getId());
                }
            }

            Optional<ProductoEntity> optionalProducto = this.servicioAccesoBaseDatos.update(id, objProductoDatosNuevos);
            productoActualizado = optionalProducto.get();
        }
        return this.modelMapper.map(productoActualizado, ProductoDTORespuesta.class);
    }

    @Override
    public boolean delete(Integer id) {
        return this.servicioAccesoBaseDatos.delete(id);
    }
}
