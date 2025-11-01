package co.edu.unicauca.distribuidos.core.capaAccesoADatos.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductoEntity {
    private Integer id;
    private String nombre;
    private String descripcion;
    private CategoriaEntity categoria;
    private Boolean estado; //Disponible, No Disponible true-false
    private Double precio;
    private String imagen;

    public ProductoEntity() {

    }
}
