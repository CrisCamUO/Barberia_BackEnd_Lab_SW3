
package co.edu.unicauca.distribuidos.core.fachadaServices.services;

import java.util.List;

import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ClienteDTOPeticion;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ClienteDTORespuesta;

public interface IClienteService {

	public List<ClienteDTORespuesta> findAll();

	public ClienteDTORespuesta findById(Integer id);

	public ClienteDTORespuesta save(ClienteDTOPeticion cliente);

	public ClienteDTORespuesta update(Integer id, ClienteDTOPeticion cliente);

	public boolean delete(Integer id);
}


