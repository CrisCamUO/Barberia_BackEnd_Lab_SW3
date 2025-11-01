
package co.edu.unicauca.distribuidos.core.capaControladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ClienteDTOPeticion;
import co.edu.unicauca.distribuidos.core.fachadaServices.DTO.ClienteDTORespuesta;
import co.edu.unicauca.distribuidos.core.fachadaServices.services.IClienteService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ClienteRestController {

	@Autowired	//Otra forma de inyectar un objeto del contenedor de Spring
	private IClienteService clienteService;

	@GetMapping("/clientes")
	public List<ClienteDTORespuesta> listarClientes() {			
		return clienteService.findAll();
	}

	@GetMapping("/clientes/{id}")
	public ClienteDTORespuesta consultarCliente(@PathVariable Integer id) {
		ClienteDTORespuesta objCliente = null;
		objCliente = clienteService.findById(id);
		return objCliente;
	}
	
	@PostMapping("/clientes")
	public ClienteDTORespuesta crearCliente(@RequestBody ClienteDTOPeticion cliente) {
		ClienteDTORespuesta objCliente = null;
		objCliente = clienteService.save(cliente);
		return objCliente;
	}

	@PutMapping("/clientes/{id}")
	public ClienteDTORespuesta actualizarCliente(@RequestBody ClienteDTOPeticion cliente, @PathVariable Integer id) {
		ClienteDTORespuesta objCliente = null;
		ClienteDTORespuesta clienteActual = clienteService.findById(id);
		if (clienteActual != null) {
			objCliente = clienteService.update(id, cliente);
		}
		return objCliente;
	}

	@DeleteMapping("/clientes/{id}")
	public Boolean eliminarCliente(@PathVariable Integer id) {
		Boolean bandera = false;
		ClienteDTORespuesta clienteActual = clienteService.findById(id);
		if (clienteActual != null) {
			bandera = clienteService.delete(id);
		}
		return bandera;
	}
	
}
