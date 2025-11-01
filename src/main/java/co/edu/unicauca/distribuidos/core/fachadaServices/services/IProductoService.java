package co.edu.unicauca.distribuidos.core.fachadaServices.services;

import java.util.List;

import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ProductoDTOPeticion;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ProductoDTORespuesta;


public interface IProductoService {

    public List<ProductoDTORespuesta> findAll();

    public ProductoDTORespuesta findById(Integer id);

    public ProductoDTORespuesta save(ProductoDTOPeticion producto);

    public ProductoDTORespuesta update(Integer id, ProductoDTOPeticion producto);

    public boolean delete(Integer id);

}
