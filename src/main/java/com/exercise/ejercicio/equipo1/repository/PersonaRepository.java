package com.nttdata.ejercicio.equipo1.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.nttdata.ejercicio.equipo1.domain.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long>{


	   ArrayList<Persona> findByNombreIgnoreCase(String nombre);
	  

	
}
