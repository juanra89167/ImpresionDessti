package com.mave.ws.rest.vo;

public class Usuario {
	private String token;
	private String cadena;
	private Boolean bandera;
	private String respuesta;
	
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	public String getCadena() {
		return cadena;
	}
	public void setCadena(String cadena) {
		this.cadena = cadena;
	}
	public Boolean getBandera() {
		return bandera;
	}
	public void setBandera(Boolean bandera) {
		this.bandera = bandera;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
