package com.nttdata.ejercicio.equipo1.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.ejercicio.equipo1.domain.Domicilio;

@Repository
public interface DomicilioRepository extends JpaRepository<Domicilio, Long> {

	Domicilio findByCalleAndAlturaIgnoreCase(String calle, String altura);
}
