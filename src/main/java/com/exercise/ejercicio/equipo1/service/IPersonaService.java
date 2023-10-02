package com.nttdata.ejercicio.equipo1.service;


import com.nttdata.ejercicio.equipo1.domain.Persona;
import com.nttdata.ejercicio.equipo1.model.PersonaMostrarDTO;
import com.nttdata.ejercicio.equipo1.model.ResponseDTO;
import com.nttdata.ejercicio.equipo1.model.ResponseDomicilioDTO;
import com.nttdata.ejercicio.equipo1.model.ResponsePersonaDTO;

public interface IPersonaService {

	public ResponseDTO create(Persona persona);

	public ResponseDTO asociar(Long idPersona, Long idDomicilio);
	
    public ResponseDTO eliminar (Long idPersona);
	
    public ResponseDTO eliminarTodos ();

	public ResponseDTO desasociar(Long idPersona, Long idDomicilio);
	
	public ResponsePersonaDTO modificarPersona(Long idPersona, PersonaMostrarDTO personaDTO);
	
	public ResponsePersonaDTO filtrarPersonaPorId(Long idPersona);
	
	public ResponsePersonaDTO filtrarNombre(String nombre);

	public ResponseDomicilioDTO filtrarDomicilios(Long id, Integer orden);

	public ResponsePersonaDTO filtrarPersonasSinDomicilios();

	public ResponsePersonaDTO filtrarTodos(int orden);

}
