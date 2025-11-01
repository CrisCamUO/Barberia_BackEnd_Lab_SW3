package co.edu.unicauca.distribuidos.core.fachadaServices.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTORespuesta { //Devuelve los datos de la base de datos
    private Integer id;
    private String nombre;
    private String descripcion;
    private Boolean estado;
    private Double precio;
    private CategoriaDTORespuesta objCategoria;
    private String imagen;
}
