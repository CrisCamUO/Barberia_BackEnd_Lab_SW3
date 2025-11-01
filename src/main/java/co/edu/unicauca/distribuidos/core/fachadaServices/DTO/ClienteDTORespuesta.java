package co.edu.unicauca.distribuidos.core.fachadaServices.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTORespuesta {
    private Integer id;
	private String nombre;
	private String apellido;
	private String email;	
    private Date createAt;
	
	private CategoriaDTORespuesta objCategoria;
}
