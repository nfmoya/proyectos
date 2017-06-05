package com.bbva.cfs.proveedor.model;

import java.util.Date;

public class ProveedorPeriodo {

	private String cdProveedor; 
	private String cdPeriodoFact;
	private String nbPeriodoFact; 
	private String cdPerFactAlt;
	private String fhDesde;
	private String fhHasta;
    private String stEstado;
    
	public String getCdProveedor() {
		return cdProveedor;
	}
	
	public void setCdProveedor(String cdProveedor) {
		this.cdProveedor = cdProveedor;
	}
	
	public String getCdPeriodoFact() {
		return cdPeriodoFact;
	}
	
	public void setCdPeriodoFact(String cdPeriodoFact) {
		this.cdPeriodoFact = cdPeriodoFact;
	}
	
	public String getNbPeriodoFact() {
		return nbPeriodoFact;
	}
	
	public void setNbPeriodoFact(String nbPeriodoFact) {
		this.nbPeriodoFact = nbPeriodoFact;
	}
	
	public String getCdPerFactAlt() {
		return cdPerFactAlt;
	}
	
	public void setCdPerFactAlt(String cdPerFactAlt) {
		this.cdPerFactAlt = cdPerFactAlt;
	}
	
	public String getFhDesde() {
		return fhDesde;
	}
	
	public void setFhDesde(String fhDesde) {
		this.fhDesde = fhDesde;
	}
	
	public String getFhHasta() {
		return fhHasta;
	}
	
	public void setFhHasta(String fhHasta) {
		this.fhHasta = fhHasta;
	}
	
	public String getStEstado() {
		return stEstado;
	}
	
	public void setStEstado(String stEstado) {
		this.stEstado = stEstado;
	}
	
}
