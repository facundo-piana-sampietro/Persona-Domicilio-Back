package com.nttdata.ejercicio.equipo1.model;

import org.springframework.http.HttpStatus;

public class ResponseDTO {
	
	private boolean isError;
	private String message;
	HttpStatus tipoError;
	
	public ResponseDTO() {}

	public ResponseDTO(boolean isError, String message, HttpStatus tipoError) {
		super();
		this.isError = isError;
		this.message = message;
		this.tipoError = tipoError;
	}

	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getTipoError() {
		return tipoError;
	}

	public void setTipoError(HttpStatus tipoError) {
		this.tipoError = tipoError;
	}

	@Override
	public String toString() {
		return "ResponseDTO [isError=" + isError + ", message=" + message + ", tipoError=" + tipoError + "]";
	}

	

	


}
