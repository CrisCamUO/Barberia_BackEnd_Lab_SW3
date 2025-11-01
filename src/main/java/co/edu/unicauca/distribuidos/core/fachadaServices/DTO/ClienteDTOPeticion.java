package co.edu.unicauca.distribuidos.core.fachadaServices.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClienteDTOPeticion {
	
	private String nombre;
	private String apellido;
	private String email;	

	private CategoriaDTOPeticion objCategoria;

	public ClienteDTOPeticion() { 

	}
}