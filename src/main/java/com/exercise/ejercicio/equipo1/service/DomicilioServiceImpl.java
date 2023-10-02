package com.nttdata.ejercicio.equipo1.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
import com.nttdata.ejercicio.equipo1.model.DomicilioMostrarDTO;
import com.nttdata.ejercicio.equipo1.model.PersonaDTO;
import com.nttdata.ejercicio.equipo1.model.ResponseDTO;
import com.nttdata.ejercicio.equipo1.model.ResponseDomicilioDTO;
import com.nttdata.ejercicio.equipo1.model.ResponsePersonaDTO;
import com.nttdata.ejercicio.equipo1.repository.DomicilioRepository;

@Service
public class DomicilioServiceImpl implements IDomicilioService {

	static final String NO_COMPLETADO = "Uno de los campos no está completo";
	@Autowired
	private DomicilioRepository domicilioRepository;

	@Override
	public ResponseDTO create(Domicilio domicilio) {

		//Preguntamos si no se ingresó un DNI
		if (domicilio.getAltura() == null || domicilio.getCalle() == null || domicilio.getAltura().isEmpty() || domicilio.getCalle().isEmpty()) {
			return new ResponseDTO(true,NO_COMPLETADO, HttpStatus.BAD_REQUEST);
		}
	
		domicilioRepository.save(domicilio);
		return new ResponseDTO(false, "Se ha creado el domicilio con éxito", HttpStatus.CREATED); 
	}
	
	@Override
	public ResponseDTO eliminar(Long idDomicilio) {
		if(!domicilioRepository.existsById(idDomicilio))  {
			return new ResponseDTO(true,"No se encontro el id del domicilio", HttpStatus.BAD_REQUEST);
		}
		
		domicilioRepository.deleteById(idDomicilio);
      
		return new ResponseDTO(false,"Eliminado correctamente", HttpStatus.OK);
	}

	@Override
	public ResponseDTO eliminarTodos() {
		domicilioRepository.deleteAll();
		return new ResponseDTO(false,"Eliminados correctamente", HttpStatus.OK);
	}
	
	@Override
	public ResponseDomicilioDTO modificarDomicilio(Long idDomicilio, DomicilioMostrarDTO domicilioMostrarDTO) {
	     
	    if(!domicilioRepository.existsById(idDomicilio)) 
	    	return new ResponseDomicilioDTO(true,"Se ha ingresado un domicilio con id invalido", HttpStatus.BAD_REQUEST);
	    
	    if (domicilioMostrarDTO.getAltura() == null || domicilioMostrarDTO.getCalle() == null || domicilioMostrarDTO.getAltura().isEmpty() || domicilioMostrarDTO.getCalle().isEmpty()) {
			return new ResponseDomicilioDTO(true,NO_COMPLETADO, HttpStatus.BAD_REQUEST);
		}
	    
	    Domicilio domicilio = null;
	    Optional<Domicilio> dOptional= domicilioRepository.findById(idDomicilio);
	    if (dOptional.isPresent()) {
	    	domicilio = dOptional.get();
			
		    domicilio.setAltura(domicilioMostrarDTO.getAltura());
		    domicilio.setCalle(domicilioMostrarDTO.getCalle());
		    
		    domicilioRepository.save(domicilio);
		    
		    ModelMapper modelMapper = new ModelMapper();
			DomicilioDTO domicilioDTO = modelMapper.map(domicilio,DomicilioDTO.class);
			 
		    return new ResponseDomicilioDTO(false,"Se ha modificado el domicilio correctamente", HttpStatus.OK, domicilioDTO, 1);
	    }
	    
	    else return new ResponseDomicilioDTO(true,"El domicilio encontrado no tiene nada", HttpStatus.BAD_REQUEST);

		}

	
	
	//private void guardarPersonas(Domicilio domicilio, DomicilioDTO domicilioDTO) {
	//	for (Persona per : domicilio.getPersonas()) {
	//		domicilioDTO.getPersonas().add(new PersonaMostrarDTO (per.getDni(),per.getNombre(),per.getApellido()));
	//	}
	//}

	@Override
	public ResponseDomicilioDTO filtrarDomicilioPorId(Long id) {
		if(!domicilioRepository.existsById(id)) {
	    	return new ResponseDomicilioDTO(false,"Se ha ingresado un domicilio con id invalido", HttpStatus.OK);
		}
		
		 Optional<Domicilio> dOptional= domicilioRepository.findById(id);
		 Domicilio domicilio = dOptional.get();
		 
		 ModelMapper modelMapper = new ModelMapper();
		 DomicilioDTO domicilioDTO = modelMapper.map(domicilio,DomicilioDTO.class);
		 
		 return new ResponseDomicilioDTO(false,"Domicilio encontrado", HttpStatus.OK, domicilioDTO, 1);
	}

	@Override
	public ResponseDomicilioDTO filtrarDomicilioPorCalleYAltura(String calle, String altura) {
		
		if(domicilioRepository.findByCalleAndAlturaIgnoreCase(calle, altura) == null) {
		   return new ResponseDomicilioDTO(false,"No existe ese domicilio", HttpStatus.OK);
	    }
	
	    Domicilio domicilio = domicilioRepository.findByCalleAndAlturaIgnoreCase(calle,altura);
	 
	    ModelMapper modelMapper = new ModelMapper();
	    DomicilioDTO domicilioDTO = modelMapper.map(domicilio,DomicilioDTO.class);
		 
		return new ResponseDomicilioDTO(false,"Domicilio encontrado", HttpStatus.OK, domicilioDTO, 1);

	}

	@Override
	public ResponseDomicilioDTO filtrarDomiciliosSinPersonas() {
		List<Domicilio> domicilios;
		List<Domicilio> domiciliosSinPersonas = new ArrayList<>();
		ArrayList<DomicilioDTO> ddto = new ArrayList<>();
	
		domicilios= domicilioRepository.findAll();
		
		for (Domicilio domicilio : domicilios) {
			if (domicilio.getPersonas().isEmpty()) {
				domiciliosSinPersonas.add(domicilio);
			}
		}
		
		for (Domicilio d : domiciliosSinPersonas) {
			 ModelMapper modelMapper = new ModelMapper();
			 DomicilioDTO domicilioDTO = modelMapper.map(d,DomicilioDTO.class);
	    		ddto.add(domicilioDTO);
		}
		
		if (ddto.isEmpty()) {
			 return new ResponseDomicilioDTO(true,"No existen domicilios sin personas", HttpStatus.BAD_REQUEST);
		}
		return new ResponseDomicilioDTO(false,"Domicilios sin personas:", HttpStatus.OK, ddto, ddto.size());
	}

	@Override
	public ResponseDomicilioDTO filtrarTodos(Integer orden) {
		List <Domicilio> dom = new ArrayList<>();
		List<DomicilioDTO> sortedList = new ArrayList<>();
		dom = domicilioRepository.findAll(); 
		ArrayList <DomicilioDTO> domDTO = new ArrayList<>();
		if (dom.isEmpty()) {
			return new ResponseDomicilioDTO(true,"No existen domicilios en la base de datos!", HttpStatus.BAD_REQUEST);
		}
		else {
			
			for (Domicilio d : dom) {
				ModelMapper modelMapper = new ModelMapper();
				DomicilioDTO domicilioDTO = modelMapper.map(d,DomicilioDTO.class);
				domDTO.add(domicilioDTO);
			}
			
			switch (orden) {
			case 1:
				 sortedList= domDTO.stream()
		        .sorted(Comparator.comparing(DomicilioDTO::getId))
		        .collect(Collectors.toList());
				break;
			case 2:
				sortedList = domDTO.stream()
		        .sorted(Comparator.comparing(DomicilioDTO::getCalle))
		        .collect(Collectors.toList());
				break;
			case 3:
				sortedList = domDTO.stream()
		        .sorted(Comparator.comparing(DomicilioDTO::getAltura))
		        .collect(Collectors.toList());
				break;

			}
		
			
			return new ResponseDomicilioDTO(false,"Lista de domicilios: ", HttpStatus.OK, (ArrayList<DomicilioDTO>)sortedList, domDTO.size());
		}
	}

	@Override
	public ResponsePersonaDTO filtrarPersonas(Long id, Integer orden) {
		Set<Persona> personas;
		ArrayList<PersonaDTO> pdto = new ArrayList<>();
		List<PersonaDTO> sortedList = new ArrayList<>();
		if(!domicilioRepository.existsById(id)) {
			 return new ResponsePersonaDTO(true,"Se ha ingresado un domicilio con id invalido", HttpStatus.BAD_REQUEST);
		}
	   Optional<Domicilio> dOptional= domicilioRepository.findById(id);
       Domicilio domicilio = dOptional.get();
        
       personas =  domicilio.getPersonas();
      
       if (personas.isEmpty()) {
	    	return new ResponsePersonaDTO(true,"No existen domicilios asociados a esta persona", HttpStatus.BAD_REQUEST);
	    }
	       
	   	for (Persona p : personas) {
 		   
            ModelMapper modelMapper = new ModelMapper();
    		PersonaDTO pDTO = modelMapper.map(p,PersonaDTO.class);
    		pdto.add(pDTO);
		}
	   	
	   	switch (orden) {
		case 1:
			 sortedList= pdto.stream()
	        .sorted(Comparator.comparing(PersonaDTO::getId))
	        .collect(Collectors.toList());
			break;
			
		case 2:
			sortedList = pdto.stream()
	        .sorted(Comparator.comparing(PersonaDTO::getDni))
	        .collect(Collectors.toList());
			break;
		
		case 3:
			sortedList = pdto.stream()
	        .sorted(Comparator.comparing(PersonaDTO::getNombre))
	        .collect(Collectors.toList());
			break;
		case 4:
			sortedList = pdto.stream()
	        .sorted(Comparator.comparing(PersonaDTO::getApellido))
	        .collect(Collectors.toList());
			break;
		

		}
	   	
	   	return new ResponsePersonaDTO(false,"Se han encontrado estos domicilios para la persona ingresada", HttpStatus.OK, (ArrayList<PersonaDTO>)sortedList, pdto.size());
	}
    	
	
}

