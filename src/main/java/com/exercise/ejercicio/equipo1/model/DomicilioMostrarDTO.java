package com.nttdata.ejercicio.equipo1.model;

public class DomicilioMostrarDTO {
	private String calle;
	private String altura;
	
	public DomicilioMostrarDTO() {}
	
	public DomicilioMostrarDTO(String calle, String altura) {
		this.calle = calle;
		this.altura = altura;
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
	
	
	@Override
	public String toString() {
		return "DomicilioMostrarDTO [calle=" + calle + ", altura=" + altura + "]";
	}
	
	
}
