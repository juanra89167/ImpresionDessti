package com.mave.ws.utils;

public class SoliciEstado {
	private String PKGNAME;
	private String CADENA;
	private Integer IDSOLICITUD;
	private Integer VERSION;
	private Integer CAMPOS;
	private String PLANTILLA;
	private Integer IDPLANTILLA;
	
	public Integer getIDPLANTILLA() {
		return IDPLANTILLA;
	}

	public void setIDPLANTILLA(Integer iDPLANTILLA) {
		IDPLANTILLA = iDPLANTILLA;
	}

	public Integer getVERSION() {
		return VERSION;
	}

	public void setVERSION(Integer vERSION) {
		VERSION = vERSION;
	}

	public Integer getCAMPOS() {
		return CAMPOS;
	}

	public void setCAMPOS(Integer cAMPOS) {
		CAMPOS = cAMPOS;
	}

	public String getPLANTILLA() {
		return PLANTILLA;
	}

	public void setPLANTILLA(String pLANTILLA) {
		PLANTILLA = pLANTILLA;
	}

	public Integer getIDSOLICITUD() {
		return IDSOLICITUD;
	}

	public void setIDSOLICITUD(Integer iDSOLICITUD) {
		IDSOLICITUD = iDSOLICITUD;
	}

	public String getPKGNAME() {
		return PKGNAME;
	}

	public void setPKGNAME(String pKGNAME) {
		PKGNAME = pKGNAME;
	}

	public String getCADENA() {
		return CADENA;
	}

	public void setCADENA(String cADENA) {
		CADENA = cADENA;
	}


	
	   public SoliciEstado(int IdSolicitud, String pkgname, String cadena, String Plantilla, Integer Version, Integer Campos, Integer idplantilla){
	        this.IDSOLICITUD =  IdSolicitud;
	        this.PKGNAME = pkgname;
	        this.CADENA = cadena;
	        this.PLANTILLA = Plantilla;
	        this.VERSION = Version;
	        this.CAMPOS = Campos;
	        this.IDPLANTILLA= idplantilla;
	    }
	
	
	
}
