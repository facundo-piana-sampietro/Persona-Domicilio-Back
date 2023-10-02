package com.nttdata.ejercicio.equipo1.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.ejercicio.equipo1.domain.Persona;
import com.nttdata.ejercicio.equipo1.model.PersonaMostrarDTO;
import com.nttdata.ejercicio.equipo1.model.ResponseDTO;
import com.nttdata.ejercicio.equipo1.model.ResponseDomicilioDTO;
import com.nttdata.ejercicio.equipo1.model.ResponsePersonaDTO;
import com.nttdata.ejercicio.equipo1.service.IPersonaService;

@RestController
public class PersonaController {

	@Autowired
	private IPersonaService personaService;	
	
	@PostMapping("/persona/create")
	public ResponseEntity<ResponseDTO> create(@RequestBody PersonaMostrarDTO personamostrardto){
		ModelMapper modelMapper = new ModelMapper();
		Persona persona = modelMapper.map(personamostrardto,Persona.class);
		
		ResponseDTO rp = personaService.create(persona);
		return new ResponseEntity<>(rp,rp.getTipoError());
		
	}
	
	@GetMapping("asociacion/{idPersona}/{idDomicilio}")
	public ResponseEntity<ResponseDTO> asociarDomicilio(@PathVariable Long idPersona, @PathVariable Long idDomicilio){
		ResponseDTO rp = personaService.asociar(idPersona,idDomicilio);
		return new ResponseEntity<>(rp,rp.getTipoError());

	}
	
	@GetMapping("/desasociacion/{idPersona}/{idDomicilio}")
	public ResponseEntity<ResponseDTO> desasociarDomicilio(@PathVariable Long idPersona, @PathVariable Long idDomicilio){
		ResponseDTO rp = personaService.desasociar(idPersona,idDomicilio);
		return new ResponseEntity<>(rp,rp.getTipoError());
	}

	@DeleteMapping("/persona/eliminar/{idPersona}")
	public ResponseEntity<ResponseDTO> eliminar(@PathVariable Long idPersona){
		ResponseDTO rp = personaService.eliminar(idPersona);
		return new ResponseEntity<>(rp,rp.getTipoError());
	
		
	}

	@DeleteMapping("/eliminarPersonas")
	public ResponseEntity<ResponseDTO> eliminar(){
		ResponseDTO rp = personaService.eliminarTodos();
		return new ResponseEntity<>(rp,rp.getTipoError());
	}
	

	@PutMapping("/persona/modificar/{idPersona}")
	public ResponseEntity<ResponsePersonaDTO> modificarPersona(@PathVariable Long idPersona, @RequestBody PersonaMostrarDTO personamostrarDTO){
		ResponsePersonaDTO rp = personaService.modificarPersona(idPersona, personamostrarDTO);
		return new ResponseEntity<>(rp,rp.getTipoError());
	}
	
	
	@GetMapping ("/persona/filtrarTodos/{orden}")
	public ResponseEntity<ResponsePersonaDTO> filtrarTodos(@PathVariable Integer orden){
		ResponsePersonaDTO rp = personaService.filtrarTodos(orden);
		return new ResponseEntity<>(rp,rp.getTipoError());
	}
	
	@GetMapping ("/persona/filtrarPorId/{id}")
	public ResponseEntity<ResponsePersonaDTO> filtrarPersonaPorId(@PathVariable Long id){
		ResponsePersonaDTO rp = personaService.filtrarPersonaPorId(id);
		return new ResponseEntity<>(rp,rp.getTipoError());
	}
	
	@GetMapping ("/persona/filtrarPorNombre/{nombre}")
	public ResponseEntity<ResponsePersonaDTO> filtrarPersonaPorNombre(@PathVariable String nombre){
		ResponsePersonaDTO rp = personaService.filtrarNombre(nombre);
		return new ResponseEntity<>(rp,rp.getTipoError());
	}
	
	@GetMapping ("/persona/obtenerDomicilios/{id}/{orden}")
	public ResponseEntity<ResponseDomicilioDTO> filtrarDomicilioPorPersona(@PathVariable Long id, @PathVariable Integer orden){
		ResponseDomicilioDTO rp = personaService.filtrarDomicilios(id,orden);
		return new ResponseEntity<>(rp,rp.getTipoError());
	}
	
	@GetMapping ("/persona/sinDomicilios")
	public ResponseEntity<ResponsePersonaDTO> filtrarPersonasSinDomicilios(){
		ResponsePersonaDTO rp = personaService.filtrarPersonasSinDomicilios();
		return new ResponseEntity<>(rp,rp.getTipoError());
	}
	
	
	
	
	
}
