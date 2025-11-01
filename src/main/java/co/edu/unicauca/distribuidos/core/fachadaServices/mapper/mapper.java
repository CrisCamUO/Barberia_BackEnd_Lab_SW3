package co.edu.unicauca.distribuidos.core.fachadaServices.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.ProductoEntity;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ProductoDTORespuesta;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ProductoDTOPeticion;

@Configuration
public class mapper {
    @Bean 
    public ModelMapper crearMapper() {
        ModelMapper objMapeador= new ModelMapper();

        // Mapear ProductoEntity.categoria <-> ProductoDTORespuesta.objCategoria
        objMapeador.typeMap(ProductoEntity.class, ProductoDTORespuesta.class)
            .addMapping(src -> src.getCategoria(), ProductoDTORespuesta::setObjCategoria);

        // Mapear ProductoDTOPeticion.objCategoria <-> ProductoEntity.categoria
        objMapeador.typeMap(ProductoDTOPeticion.class, ProductoEntity.class)
            .addMapping(src -> src.getObjCategoria(), ProductoEntity::setCategoria);

        // También mapear CategoriaDTOPeticion -> CategoriaEntity por nombre del campo
        // (ModelMapper hace esto por convención pero lo dejamos explícito si se necesita)

        return objMapeador;//El objeto retornado se almacena en el contenedor de Spring
    }
}
