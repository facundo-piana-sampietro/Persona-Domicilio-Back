package com.nttdata.ejercicio.equipo1.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;


public class ResponsePersonaDTO extends ResponseDTO{

	ArrayList<PersonaDTO> personas;
	int cantTotal;
	
	
	public ResponsePersonaDTO() {
		
	}
	
	public ResponsePersonaDTO(boolean err, String msg,  HttpStatus tipoerror, PersonaDTO personadto, int cantTotal) {
		super(err, msg, tipoerror);
		this.personas = new ArrayList<>();
		this.personas.add(personadto);
		this.cantTotal = cantTotal;
	}
	
	public ResponsePersonaDTO(boolean isError, String message, HttpStatus tipoError) {
		super(isError, message, tipoError);
	}



	public ResponsePersonaDTO(boolean err, String msg,  HttpStatus tipoerror, ArrayList<PersonaDTO> personas, int cantTotal) {
		super(err, msg, tipoerror);
		this.personas = personas;
		this.cantTotal = cantTotal;
	}


	public List<PersonaDTO> getPersonas() {
		return personas;
	}


	public void setPersonas(ArrayList<PersonaDTO> personas) {
		this.personas = personas;
	}


	public int getCantTotal() {
		return cantTotal;
	}


	public void setCantTotal(int cantTotal) {
		this.cantTotal = cantTotal;
	}


	@Override
	public String toString() {
		return "ResponsePersonaDTO [" +super.toString() + "personas=" + personas + ", cantTotal=" + cantTotal + "]";
	}
	
}
