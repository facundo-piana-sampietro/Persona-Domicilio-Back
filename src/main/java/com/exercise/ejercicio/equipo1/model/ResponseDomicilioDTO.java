package com.nttdata.ejercicio.equipo1.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ResponseDomicilioDTO extends ResponseDTO{
	
	List<DomicilioDTO> domicilios;
	int cantTotal;
	
	
	public ResponseDomicilioDTO() {}


	public ResponseDomicilioDTO(boolean err, String msg,  HttpStatus tipoerror, DomicilioDTO domiciliodto, int cantTotal) {
		super(err, msg, tipoerror);
		this.domicilios = new ArrayList<>();
		this.domicilios.add(domiciliodto);
		this.cantTotal = cantTotal;
	}
	
	public ResponseDomicilioDTO(boolean isError, String message, HttpStatus tipoError) {
		super(isError, message, tipoError);
	}


	public ResponseDomicilioDTO(boolean isError, String message, HttpStatus tipoError, List<DomicilioDTO> personas, int cantTotal) {
		super(isError, message, tipoError);
		this.domicilios = personas;
		this.cantTotal = cantTotal;
	}


	public List<DomicilioDTO> getDomicilios() {
		return domicilios;
	}


	public void setPersonas(List<DomicilioDTO> domicilios) {
		this.domicilios = domicilios;
	}


	public int getCantTotal() {
		return cantTotal;
	}


	public void setCantTotal(int cantTotal) {
		this.cantTotal = cantTotal;
	}


	@Override
	public String toString() {
		return "ResponseDomicilioDTO [personas=" + domicilios + ", cantTotal=" + cantTotal + ", toString()="
				+ super.toString() + "]";
	}
	
	
	
	
}
