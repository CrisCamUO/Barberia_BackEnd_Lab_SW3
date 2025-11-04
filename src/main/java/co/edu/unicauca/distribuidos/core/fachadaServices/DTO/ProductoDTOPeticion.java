package co.edu.unicauca.distribuidos.core.fachadaServices.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductoDTOPeticion { //Recibe los datos necesarios para crear, actualizar o buscar un producto un producto
    private String nombre;
    private String descripcion;
    private CategoriaDTOPeticion objCategoria;
    private Double precio;
    private String imagen;
    private Boolean estado;

    public ProductoDTOPeticion() {

    }
}
