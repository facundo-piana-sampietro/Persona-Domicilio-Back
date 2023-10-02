package com.nttdata.ejercicio.equipo1.service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.nttdata.ejercicio.equipo1.domain.Domicilio;
import com.nttdata.ejercicio.equipo1.domain.Persona;
import com.nttdata.ejercicio.equipo1.model.DomicilioDTO;
import com.nttdata.ejercicio.equipo1.model.PersonaDTO;
import com.nttdata.ejercicio.equipo1.model.PersonaMostrarDTO;
import com.nttdata.ejercicio.equipo1.model.ResponseDTO;
import com.nttdata.ejercicio.equipo1.model.ResponseDomicilioDTO;
import com.nttdata.ejercicio.equipo1.model.ResponsePersonaDTO;
import com.nttdata.ejercicio.equipo1.repository.DomicilioRepository;
import com.nttdata.ejercicio.equipo1.repository.PersonaRepository;

@Service
public class PersonaServiceImpl implements IPersonaService{

	private static final String ID_INVALIDO = "Se ha ingresado una persona con id invalido";

	@Autowired
	private PersonaRepository personaRepository;
	
	@Autowired
	private DomicilioRepository domicilioRepository;

	@Override
	public ResponseDTO create(Persona persona) {
	
		if (persona.getDni()==null && (persona.getNombre() == null || persona.getNombre().isEmpty()) && (persona.getApellido() == null || persona.getApellido().isEmpty())){
        	return new ResponseDTO(true,"Debe completar todos los campos!", HttpStatus.BAD_REQUEST);
        }
		//Preguntamos si no se ingresó un DNI
		if (persona.getDni()==null) {
			return new ResponseDTO(true,"Debe ingresar un DNI", HttpStatus.BAD_REQUEST);
		}else if(persona.getNombre() == null || persona.getNombre().isEmpty()) {
			return new ResponseDTO(true,"Debe ingresar un Nombre", HttpStatus.BAD_REQUEST);
		}else if(persona.getApellido() == null || persona.getApellido().isEmpty()) {
			return new ResponseDTO(true,"Debe ingresar un apellido", HttpStatus.BAD_REQUEST);
		}
	
		personaRepository.save(persona);
		return new ResponseDTO(false, "Se ha creado la persona con éxito", HttpStatus.CREATED); 	
	}

	@Override
	public ResponseDTO asociar(Long idPersona, Long idDomicilio) {
		Domicilio domicilio = null;
		Persona persona = null;
		try {
		Optional<Domicilio> dOptional= domicilioRepository.findById(idDomicilio);
		domicilio = dOptional.get();
		}
		catch(Exception e) {
			return new ResponseDTO(false,"No se ha encontrado el domicilio", HttpStatus.OK);
		}
		
		try {
		Optional<Persona> pOptional= personaRepository.findById(idPersona);
		persona = pOptional.get();
		}
		catch(Exception e) {
			return new ResponseDTO(false,"No se ha encontrado la persona", HttpStatus.OK);
		}
		
		//Nos fijamos si la persona y el domicilio ya estan asociados
		boolean asociados=false;
		for (Domicilio dom : persona.getDomicilios()) {
			if (Objects.equals(dom.getId(), domicilio.getId())) {
				asociados=true;
			}
		}
		//Devolvemos el error de asosiación
		if (asociados) return new ResponseDTO(true,"La persona y el domicilio ya están asociados", HttpStatus.BAD_REQUEST);
		
		
		if (domicilio.limite()) {
			if (persona.limite()) {
				//Le agregamos el domicilio a la persona
				persona.agregarDomicilio(domicilio);
				//Le agregamos la persona al domicilio
				domicilio.agregarPersona(persona);
				
				//Realizamos la asociacion
				personaRepository.save(persona);
				domicilioRepository.save(domicilio);
				return new ResponseDTO(false,"Se ha asociado la persona con el domicilio correctamente", HttpStatus.OK);
			}
			else {
				//En caso de superar el limite de domicilios de la persona, se devuelve esta respuesta
				return new ResponseDTO(true,"La persona ya tiene 3 domicilios", HttpStatus.BAD_REQUEST);
			}
		}
		else {
			//En caso de superar el limite de personas en el domicilio, se devuelve esta respuesta
			return new ResponseDTO(true,"El domicilio ya posee 10 personas", HttpStatus.BAD_REQUEST);
		}
		
	}

	@Override
	public ResponseDTO desasociar(Long idPersona, Long idDomicilio) {
		Domicilio domicilio = null;
        Persona persona = null;
        try {
        Optional<Domicilio> dOptional= domicilioRepository.findById(idDomicilio);
        domicilio = dOptional.get();
        }
        catch(Exception e) {
            return new ResponseDTO(true,"No se ha encontrado el domicilio.", HttpStatus.OK);
        }
        
        try {
        Optional<Persona> pOptional= personaRepository.findById(idPersona);
        persona = pOptional.get();
        }
        catch(Exception e) {
            return new ResponseDTO(true,"No se ha encontrado la persona.", HttpStatus.OK);
        }
        
        boolean noExiste=true;
        for (Domicilio dom : persona.getDomicilios()) {
			if (Objects.equals(dom.getId(), domicilio.getId())) {
				noExiste=false;
			}
		}
		
		//Devolvemos el error de desasociación
		if (noExiste) { 
			return new ResponseDTO(true,"No existe una asociación entre la persona y el domicilio enviado.", HttpStatus.BAD_REQUEST);
		}

        persona.eliminarDomicilio(domicilio);
        
        domicilio.eliminarPersona(persona);
        
        personaRepository.save(persona);
        domicilioRepository.save(domicilio);
        return new ResponseDTO(false,"Se ha desasociado la persona con el domicilio correctamente", HttpStatus.OK);
	}
	
	@Override
	public ResponseDTO eliminar(Long idPersona) {
	     
		if(!personaRepository.existsById(idPersona))  {
			return new ResponseDTO(true,"No se encontró el id de la persona", HttpStatus.BAD_REQUEST);
		}
		
		personaRepository.deleteById(idPersona);
		return new ResponseDTO(false,"Eliminado correctamente", HttpStatus.OK);
	}

	@Override
	public ResponseDTO eliminarTodos() {
		personaRepository.deleteAll();
		return new ResponseDTO(false,"Eliminados correctamente", HttpStatus.OK);
	}

	@Override
	public ResponsePersonaDTO modificarPersona(Long idPersona, PersonaMostrarDTO personamostrarDTO) {
         
        if(!personaRepository.existsById(idPersona))
            return new ResponsePersonaDTO(true,ID_INVALIDO, HttpStatus.BAD_REQUEST);
        
        if (personamostrarDTO.getDni()==null && (personamostrarDTO.getNombre() == null || personamostrarDTO.getNombre().isEmpty()) && (personamostrarDTO.getApellido() == null || personamostrarDTO.getApellido().isEmpty())) {
        	return new ResponsePersonaDTO(true,"Debe completar todos los campos!", HttpStatus.BAD_REQUEST);
        }
        
        if (personamostrarDTO.getDni()==null) {
			return new ResponsePersonaDTO(true,"Debe ingresar un DNI", HttpStatus.BAD_REQUEST);
		}else if(personamostrarDTO.getNombre() == null || personamostrarDTO.getNombre().isEmpty()) {
			return new ResponsePersonaDTO(true,"Debe ingresar un Nombre", HttpStatus.BAD_REQUEST);
		}else if(personamostrarDTO.getApellido() == null || personamostrarDTO.getApellido().isEmpty()) {
			return new ResponsePersonaDTO(true,"Debe ingresar un apellido", HttpStatus.BAD_REQUEST);
		}
        
        Optional<Persona> pOptional= personaRepository.findById(idPersona);
        Persona persona = pOptional.get();
        
        persona.setDni(personamostrarDTO.getDni());
        persona.setApellido(personamostrarDTO.getApellido());
        persona.setNombre(personamostrarDTO.getNombre());
        
        
        personaRepository.save(persona);
        
        ModelMapper modelMapper = new ModelMapper();
		PersonaDTO personaDTO = modelMapper.map(persona,PersonaDTO.class);
        
        
        return new ResponsePersonaDTO(false,"Se ha modificado la persona correctamente", HttpStatus.OK, personaDTO, 1);
	}

	@Override
	public ResponsePersonaDTO filtrarTodos(int orden) {
		List <Persona> pe = new ArrayList<>();
		List<PersonaDTO> sortedList = new ArrayList<>();
		pe = personaRepository.findAll(); 
		ArrayList <PersonaDTO> peDTO = new ArrayList<>();
		if (pe.isEmpty()) {
			return new ResponsePersonaDTO(true,"No existen personas en la base de datos!", HttpStatus.BAD_REQUEST);
		}
		else {
			
			for (Persona p : pe) {
				ModelMapper modelMapper = new ModelMapper();
				PersonaDTO personaDTO = modelMapper.map(p,PersonaDTO.class);
				peDTO.add(personaDTO);
			}
			
			switch (orden) {
			case 1:
				 sortedList= peDTO.stream()
		        .sorted(Comparator.comparing(PersonaDTO::getId))
		        .collect(Collectors.toList());
				break;
			case 2:
				sortedList = peDTO.stream()
		        .sorted(Comparator.comparing(PersonaDTO::getDni))
		        .collect(Collectors.toList());
				break;
			case 3:
				sortedList = peDTO.stream()
		        .sorted(Comparator.comparing(PersonaDTO::getNombre))
		        .collect(Collectors.toList());
				break;
				
			case 4:
				sortedList = peDTO.stream()
		        .sorted(Comparator.comparing(PersonaDTO::getApellido))
		        .collect(Collectors.toList());
				break;

			}
		
			
			return new ResponsePersonaDTO(false,"Lista de personas: ", HttpStatus.OK, (ArrayList<PersonaDTO>)sortedList, peDTO.size());
		}
	}
	

	@Override
	public ResponsePersonaDTO filtrarPersonaPorId(Long idPersona) {
		if(!personaRepository.existsById(idPersona))
            return new ResponsePersonaDTO(false,ID_INVALIDO, HttpStatus.OK);
        
        Optional<Persona> pOptional= personaRepository.findById(idPersona);
        Persona persona = pOptional.get();
        
        ModelMapper modelMapper = new ModelMapper();
		PersonaDTO personaDTO = modelMapper.map(persona,PersonaDTO.class);
		
		
		return new ResponsePersonaDTO(false,"Se ha encontrado a la persona correctamente", HttpStatus.OK, personaDTO, 1);
	}

	@Override
	public ResponsePersonaDTO filtrarNombre(String nombre) {
		nombre = nombre.toUpperCase();
		ArrayList<Persona> p = new ArrayList<>();
		ArrayList<PersonaDTO> pdto = new ArrayList<>();
		p = personaRepository.findByNombreIgnoreCase(nombre);
    	if(p.isEmpty()) {
    		return new ResponsePersonaDTO(false,"No hay personas con ese nombre", HttpStatus.OK);
    	}
    	for (Persona persona : p) {
    		   
            ModelMapper modelMapper = new ModelMapper();
    		PersonaDTO personaDTO = modelMapper.map(persona,PersonaDTO.class);
    		pdto.add(personaDTO);
    		
		}
    	return new ResponsePersonaDTO(false,"Se han encontrado estas personas con ese nombre", HttpStatus.OK, pdto, pdto.size());
    }
	
	
	//private void guardarDomicilios(Persona persona, PersonaDTO personaDTO) {
	//	for (Domicilio dom : persona.getDomicilios()) {
	//		personaDTO.getDomicilios().add(new DomicilioMostrarDTO (dom.getCalle(),dom.getAltura()));
	//	}
	//}

	@Override
	public ResponseDomicilioDTO filtrarDomicilios(Long id, Integer orden) {
		Set<Domicilio> domicilios = new HashSet<>();
		ArrayList<DomicilioDTO> ddto = new ArrayList<>();
		List<DomicilioDTO> sortedList = new ArrayList<>();
		if(!personaRepository.existsById(id)) {
			 return new ResponseDomicilioDTO(false,ID_INVALIDO, HttpStatus.OK);
		}
		   Optional<Persona> pOptional= personaRepository.findById(id);
	        Persona persona = pOptional.get();
	        
	       domicilios =  persona.getDomicilios();
	    if (domicilios.isEmpty()) {
	    	return new ResponseDomicilioDTO(true,"No existen domicilios asociados a esta persona", HttpStatus.BAD_REQUEST);
	    }
	    
	   	for (Domicilio d : domicilios) {
 		   
            ModelMapper modelMapper = new ModelMapper();
    		DomicilioDTO dDTO = modelMapper.map(d,DomicilioDTO.class);
    		ddto.add(dDTO);
		}
	   	
	   	switch (orden) {
		case 1:
			 sortedList= ddto.stream()
	        .sorted(Comparator.comparing(DomicilioDTO::getId))
	        .collect(Collectors.toList());
			break;
		case 2:
			sortedList = ddto.stream()
	        .sorted(Comparator.comparing(DomicilioDTO::getCalle))
	        .collect(Collectors.toList());
			break;
		case 3:
			sortedList = ddto.stream()
	        .sorted(Comparator.comparing(DomicilioDTO::getAltura))
	        .collect(Collectors.toList());
			break;

		}
	   	
	   	return new ResponseDomicilioDTO(false,"Se han encontrado estos domicilios para la persona ingresada", HttpStatus.OK, (ArrayList<DomicilioDTO>)sortedList, ddto.size());
	}

	@Override
	public ResponsePersonaDTO filtrarPersonasSinDomicilios() {
		List<Persona> personas;
		List<Persona> personasSinDomicilios = new ArrayList<>();
		ArrayList<PersonaDTO> pdto = new ArrayList<>();
	
		personas = personaRepository.findAll();
		
		for (Persona persona : personas) {
			if (persona.getDomicilios().isEmpty()) {
				personasSinDomicilios.add(persona);
			}
		}
		
		for (Persona p : personasSinDomicilios) {
			 ModelMapper modelMapper = new ModelMapper();
	    		PersonaDTO personaDTO = modelMapper.map(p,PersonaDTO.class);
	    		pdto.add(personaDTO);
		}
		
		if (pdto.isEmpty()) {
			 return new ResponsePersonaDTO(true,"No existen personas sin domicilio", HttpStatus.BAD_REQUEST);
		}
		return new ResponsePersonaDTO(false,"Personas sin domicilio:", HttpStatus.OK, pdto, pdto.size());
	}



	

}

