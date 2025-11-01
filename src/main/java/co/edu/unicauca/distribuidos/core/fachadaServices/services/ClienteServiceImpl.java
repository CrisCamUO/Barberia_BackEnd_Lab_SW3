
package co.edu.unicauca.distribuidos.core.fachadaServices.services;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import co.edu.unicauca.distribuidos.core.capaAccesoADatos.models.ClienteEntity;
import co.edu.unicauca.distribuidos.core.capaAccesoADatos.repositories.UsuarioRepositoryBaseDatos;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ClienteDTOPeticion;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ClienteDTORespuesta;

@Service//El objeto creado se almacena en el contenedor de Spring
public class ClienteServiceImpl implements IClienteService {

	
	private UsuarioRepositoryBaseDatos servicioAccesoBaseDatos;	
	private ModelMapper modelMapper;

	//El contructor inyecta los objetos que se encuentran en el contenedor de Spring
	public ClienteServiceImpl(UsuarioRepositoryBaseDatos servicioAccesoBaseDatos, ModelMapper modelMapper) {
		this.servicioAccesoBaseDatos = servicioAccesoBaseDatos;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<ClienteDTORespuesta> findAll() {
		List<ClienteDTORespuesta> listaRetornar;
		Optional<Collection<ClienteEntity>> clientesEntityOpt = this.servicioAccesoBaseDatos.findAll();
		
		// Si el Optional está vacío, devolvemos una lista vacía
		if (clientesEntityOpt.isEmpty()) {
			listaRetornar=List.of(); // Retorna una lista inmutable vacía
		}
		else{
			// Convertimos la colección a una lista y la mapeamos a ClienteDTO
			Collection<ClienteEntity> clientesEntity = clientesEntityOpt.get();
			listaRetornar= this.modelMapper.map(clientesEntity, new TypeToken<List<ClienteDTORespuesta>>() {}.getType());
			
		}
	
		
		return listaRetornar;
	}
	
	@Override
	public ClienteDTORespuesta findById(Integer id) {
		ClienteDTORespuesta clienteRetornar=null;
		Optional<ClienteEntity> optionalCliente = this.servicioAccesoBaseDatos.findById(id);
		if(optionalCliente.isPresent())
		{
			ClienteEntity clienteEntity=optionalCliente.get();
			clienteRetornar= this.modelMapper.map(clienteEntity, ClienteDTORespuesta.class);
		}


		return clienteRetornar;
		
	}

	@Override
	public ClienteDTORespuesta save(ClienteDTOPeticion cliente) {
		ClienteEntity clienteEntity = this.modelMapper.map(cliente, ClienteEntity.class);
		clienteEntity.setEstado(true);
		clienteEntity.setCreateAt(new Date());
		ClienteEntity objCLienteEntity = this.servicioAccesoBaseDatos.save(clienteEntity);
		System.out.println(objCLienteEntity);
		ClienteDTORespuesta clienteDTO = this.modelMapper.map(objCLienteEntity, ClienteDTORespuesta.class);
		return clienteDTO;
	}

	@Override
	public ClienteDTORespuesta update(Integer id, ClienteDTOPeticion cliente) {
		ClienteEntity clienteActualizado=null;
		Optional<ClienteEntity> clienteEntityOp = this.servicioAccesoBaseDatos.findById(id);

		if(clienteEntityOp.isPresent())
		{
			ClienteEntity objCLienteDatosNuevos=clienteEntityOp.get();
			objCLienteDatosNuevos.setNombre(cliente.getNombre());
			objCLienteDatosNuevos.setApellido(cliente.getApellido());
			objCLienteDatosNuevos.setEmail(cliente.getEmail());
			objCLienteDatosNuevos.getObjCategoria().setId(cliente.getObjCategoria().getId());
			objCLienteDatosNuevos.getObjCategoria().setNombre("");

			Optional<ClienteEntity> optionalCliente = this.servicioAccesoBaseDatos.update(id, objCLienteDatosNuevos);
			clienteActualizado=optionalCliente.get(); 
		}

		return this.modelMapper.map(clienteActualizado, ClienteDTORespuesta.class);
	}

	@Override
	public boolean delete(Integer id) {
		return this.servicioAccesoBaseDatos.delete(id);
	}
}
