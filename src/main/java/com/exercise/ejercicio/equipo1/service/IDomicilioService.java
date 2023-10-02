package com.nttdata.ejercicio.equipo1.service;

import com.nttdata.ejercicio.equipo1.domain.Domicilio;
import com.nttdata.ejercicio.equipo1.model.DomicilioMostrarDTO;
import com.nttdata.ejercicio.equipo1.model.ResponseDTO;
import com.nttdata.ejercicio.equipo1.model.ResponseDomicilioDTO;
import com.nttdata.ejercicio.equipo1.model.ResponsePersonaDTO;


public interface IDomicilioService {

	public ResponseDTO create(Domicilio domicilio);
	
    public ResponseDTO eliminar(Long idDomicilio);
	
	public ResponseDTO eliminarTodos();
	
	public ResponseDomicilioDTO modificarDomicilio(Long idDomicilio, DomicilioMostrarDTO domiciliomostrardto);

	public ResponseDomicilioDTO filtrarDomicilioPorId(Long id);

	public ResponseDomicilioDTO filtrarDomicilioPorCalleYAltura(String calle, String altura);

	public ResponseDomicilioDTO filtrarDomiciliosSinPersonas();

	public ResponseDomicilioDTO filtrarTodos(Integer orden);

	public ResponsePersonaDTO filtrarPersonas(Long id, Integer orden);
	
	

}
