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

import com.nttdata.ejercicio.equipo1.domain.Domicilio;
import com.nttdata.ejercicio.equipo1.model.DomicilioMostrarDTO;
import com.nttdata.ejercicio.equipo1.model.ResponseDTO;
import com.nttdata.ejercicio.equipo1.model.ResponseDomicilioDTO;
import com.nttdata.ejercicio.equipo1.model.ResponsePersonaDTO;
import com.nttdata.ejercicio.equipo1.service.IDomicilioService;

@RestController
public class DomicilioController {
	
	@Autowired
	private IDomicilioService domicilioService;
	
	@PostMapping("/domicilio/create")
	public ResponseEntity<ResponseDTO> create(@RequestBody DomicilioMostrarDTO domiciliomostrardto){
		ModelMapper modelMapper = new ModelMapper();
		Domicilio domicilio= modelMapper.map(domiciliomostrardto,Domicilio.class);
		ResponseDTO rp = domicilioService.create(domicilio);
		return new ResponseEntity<>(rp,rp.getTipoError());
		
	}
	
	@DeleteMapping("/domicilio/eliminar/{idDomicilio}")
	public ResponseEntity<ResponseDTO> eliminar(@PathVariable Long idDomicilio){
		ResponseDTO rp = domicilioService.eliminar(idDomicilio);
		return new ResponseEntity<>(rp,rp.getTipoError());
	
		
	}
	
	@DeleteMapping("/eliminarDomicilios")
	public ResponseEntity<ResponseDTO> eliminar(){
		ResponseDTO rp = domicilioService.eliminarTodos();
		return new ResponseEntity<>(rp,rp.getTipoError());
	
	
	}
	
	@PutMapping("/domicilio/modificar/{idDomicilio}")
	public ResponseEntity<ResponseDomicilioDTO> modificarDomicilio(@PathVariable Long idDomicilio,@RequestBody DomicilioMostrarDTO domiciliomostrardto){
		ResponseDomicilioDTO rp = domicilioService.modificarDomicilio(idDomicilio, domiciliomostrardto);
	
		return new ResponseEntity<>(rp,rp.getTipoError());
	}
	

	@GetMapping ("/domicilio/filtrarTodos/{orden}")
	public ResponseEntity<ResponseDomicilioDTO> filtrarTodos(@PathVariable Integer orden){
		ResponseDomicilioDTO rd = domicilioService.filtrarTodos(orden);
		return new ResponseEntity<>(rd,rd.getTipoError());
	}
	
	@GetMapping ("/domicilio/filtrarPorId/{id}")
	public ResponseEntity<ResponseDomicilioDTO> filtrarDomicilioPorId(@PathVariable Long id){
		ResponseDomicilioDTO rp = domicilioService.filtrarDomicilioPorId(id);
		return new ResponseEntity<>(rp,rp.getTipoError());
	}
	
	@GetMapping ("/domicilio/filtrarPorCalleYAltura/{calle}/{altura}")
	public ResponseEntity<ResponseDomicilioDTO> filtrarDomicilioPorCalleYAltura(@PathVariable String calle, @PathVariable String altura){
		ResponseDomicilioDTO rp = domicilioService.filtrarDomicilioPorCalleYAltura(calle,altura);
		return new ResponseEntity<>(rp,rp.getTipoError());
	}
	
	@GetMapping("/domicilio/obtenerPersonas/{id}/{orden}")
	public ResponseEntity<ResponsePersonaDTO> filtrarPersonasPorDomicilio(@PathVariable Long id, @PathVariable Integer orden){
		ResponsePersonaDTO rd = domicilioService.filtrarPersonas(id,orden);
		return new ResponseEntity<>(rd,rd.getTipoError());
	}
	
	
	@GetMapping ("/domicilio/sinPersonas")
	public ResponseEntity<ResponseDomicilioDTO> filtrarDomiciliosSinPersonas(@PathVariable Integer orden){
		ResponseDomicilioDTO rp = domicilioService.filtrarDomiciliosSinPersonas();
		return new ResponseEntity<>(rp,rp.getTipoError());
	}
	
}
