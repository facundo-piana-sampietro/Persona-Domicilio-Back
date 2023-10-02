package com.nttdata.ejercicio.equipo1.model;

import java.util.ArrayList;
import java.util.List;




public class PersonaDTO {

	private Long id;
	private Integer dni;
	private String nombre;
	private String apellido;
	private List<DomicilioMostrarDTO> domicilios;
	
	public PersonaDTO() {
		domicilios = new ArrayList<>();
	}
	
	public PersonaDTO(Long id, Integer dni, String nombre, String apellido) {
		this.id = id;
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		domicilios = new ArrayList<>();
	}
	
	public PersonaDTO(Integer dni, String nombre, String apellido) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		domicilios = new ArrayList<>();
	}
	
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDni() {
		return dni;
	}

	public void setDni(Integer dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public List<DomicilioMostrarDTO> getDomicilios() {
		return domicilios;
	}

	public void setDomicilios(List<DomicilioMostrarDTO> set) {
		this.domicilios = set;
	}

	@Override
	public String toString() {
		return "PersonaDTO [id=" + id + ", dni=" + dni + ", nombre=" + nombre + ", apellido=" + apellido
				+ ", domicilios=" + domicilios + "]";
	}
	

	


	
	
}
