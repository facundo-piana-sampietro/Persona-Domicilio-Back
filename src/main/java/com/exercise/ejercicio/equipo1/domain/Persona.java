package com.nttdata.ejercicio.equipo1.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name="Personas")
public class Persona {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer dni;
	private String nombre;
	private String apellido;
	@ManyToMany
	@JoinTable(name = "Persona_Domicilio", joinColumns = @JoinColumn(name = "persona_id"), 
			inverseJoinColumns = @JoinColumn(name = "domicilio_id"))
	private Set<Domicilio> domicilios;
	
	public Persona() {
		domicilios = new HashSet<>();
	}
	
	public Persona(Integer dni, String nombre, String apellido) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		domicilios = new HashSet<>();
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

	public Set<Domicilio> getDomicilios() {
		return domicilios;
	}

	public void setDomicilios(Set<Domicilio> domicilios) {
		this.domicilios = domicilios;
	}

	@Override
	public String toString() {
		return "Persona [dni=" + dni + ", nombre=" + nombre + ", apellido=" + apellido;
	}

	public boolean limite() {
		return domicilios.size()<3;
	}

	public void agregarDomicilio(Domicilio domicilio) {
		domicilios.add(domicilio);		
	}

	public void eliminarDomicilio(Domicilio domicilio) {
		domicilios.remove(domicilio);
		
	}
}
