package com.nttdata.ejercicio.equipo1.model;

import java.util.ArrayList;
import java.util.List;



public class DomicilioDTO {
	private Long id;
	private String calle;
	private String altura;
	private List<PersonaMostrarDTO> personas;
	
	public DomicilioDTO() {
		personas = new ArrayList<>();
	}
	
	public DomicilioDTO(Long id, String calle, String altura) {
		this.id = id;
		this.calle = calle;
		this.altura = altura;
		personas = new ArrayList<>();
	}
	
	
	public DomicilioDTO(String calle, String altura) {
		this.calle = calle;
		this.altura = altura;
		personas = new ArrayList<>();
	}
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCalle() {
		return calle;
	}


	public void setCalle(String calle) {
		this.calle = calle;
	}


	public String getAltura() {
		return altura;
	}


	public void setAltura(String altura) {
		this.altura = altura;
	}


	public List<PersonaMostrarDTO> getPersonas() {
		return personas;
	}


	public void setPersonas(List<PersonaMostrarDTO> personas) {
		this.personas = personas;
	}

	@Override
	public String toString() {
		return "DomicilioDTO [id=" + id + ", calle=" + calle + ", altura=" + altura + ", personas=" + personas + "]";
	}


	
	
	
}
