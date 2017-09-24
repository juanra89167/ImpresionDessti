package com.mave.ws.rest.voweb;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "actor")
public class Actor {

	private String Sucursal;
	private String Endoso;
	private String Ramo;
	private String Poliza;
	private String Estatus;
	
	public Actor( String name, String birthName, String birthDate, String email, String image) {
		this.Sucursal = name;
		this.Endoso = birthName;
		this.Ramo = birthDate;
		this.Poliza = email;
		this.Estatus = image;
	}
	


	@XmlElement
	public String getSucursal() {
		return Sucursal;
	}

	public void setSucursal(String name) {
		this.Sucursal = name;
	}

	@XmlElement
	public String getEndoso() {
		return Endoso;
	}

	public void setEndoso(String birthName) {
		this.Endoso = birthName;
	}

	@XmlElement
	public String getRamo() {
		return Ramo;
	}

	public void setRamo(String birthDate) {
		this.Ramo = birthDate;
	}

	@XmlElement
	public String getPoliza() {
		return Poliza;
	}

	public void setPoliza(String email) {
		this.Poliza = email;
	}



	@XmlElement
	public String getEstatus() {
		return Estatus;
	}

	public void setEstatus(String image) {
		this.Estatus = image;
	}

	public Actor() {
	}

	@Override
	public String toString() {
		return "Actor [ sucursal=" + Sucursal + ", endoso="
				+ Endoso + ", ramo=" + Ramo + ", poliza=" + Poliza
				+ ", estatus=" + Estatus +  "]";
	}
}
