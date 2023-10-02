package com.nttdata.ejercicio.equipo1.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name="Domicilios")
public class Domicilio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String calle;
	private String altura;
	@ManyToMany(mappedBy = "domicilios")
	private Set<Persona> personas;
	
	
	public Domicilio() {
		personas = new HashSet<>();
	}
	
	public Domicilio(Long id, String calle, String altura) {
		this.id = id;
		this.calle = calle;
		this.altura = altura;
		personas = new HashSet<>();
	}
	
	public Domicilio( String calle, String altura) {
		this.calle = calle;
		this.altura = altura;
		personas = new HashSet<>();
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

	public Set<Persona> getPersonas() {
		return personas;
	}

	public void setPersonas(Set<Persona> personas) {
		this.personas = personas;
	}

	@Override
	public String toString() {
		return "Domicilio [id=" + id + ", calle=" + calle + ", altura=" + altura;
	}

	public boolean limite() {
		return personas.size()<10;
	}

	public void agregarPersona(Persona persona) {
		personas.add(persona);
		
	}

	public void eliminarPersona(Persona persona) {
		personas.remove(persona);
		
	}
	
	
	
}
