package com.bbva.cfs.lotes.model;

/**
 * Clase que representa la lista que se visualiza en pantalla
 * 
 * @author Alexis Comerci
 * 
 */

public class ConsActLotesModel {
	private Long cdLote;
	private String tpInterfaz;
	private String nbArchivo;
	private String cdProveedor;
	private String stLotecab;
	private String nbObservaciones;
	private String fhAlta;
	private String nbUsuarioAlta;
	private int anular;

	public Long getCdLote() {
		return cdLote;
	}

	public void setCdLote(Long cdLote) {
		this.cdLote = cdLote;
	}

	public String getTpInterfaz() {
		return tpInterfaz;
	}

	public void setTpInterfaz(String tpInterfaz) {
		this.tpInterfaz = tpInterfaz;
	}

	public String getNbArchivo() {
		return nbArchivo;
	}

	public void setNbArchivo(String nbArchivo) {
		this.nbArchivo = nbArchivo;
	}

	public String getCdProveedor() {
		return cdProveedor;
	}

	public void setCdProveedor(String cdProveedor) {
		this.cdProveedor = cdProveedor;
	}

	public String getStLotecab() {
		return stLotecab;
	}

	public void setStLotecab(String stLotecab) {
		this.stLotecab = stLotecab;
	}

	public String getNbObservaciones() {
		return nbObservaciones;
	}

	public void setNbObservaciones(String nbObservaciones) {
		this.nbObservaciones = nbObservaciones;
	}

	public String getFhAlta() {
		return fhAlta;
	}

	public void setFhAlta(String fhAlta) {
		this.fhAlta = fhAlta;
	}

	public String getNbUsuarioAlta() {
		return nbUsuarioAlta;
	}

	public void setNbUsuarioAlta(String nbUsuarioAlta) {
		this.nbUsuarioAlta = nbUsuarioAlta;
	}

	public int getAnular() {
		return anular;
	}

	public void setAnular(int anular) {
		this.anular = anular;
	}

}
