package com.bbva.cfs.proveedor.model;

public class ProveedorTipoCambio {

	private String cdProveedor; 
	private String cdMoneda;
	private String nbMoneda;
	private String cdPeriodoFact; 
    private String nuDias;
    private String cdCotizacion;
    private String stHabilitado;
    
	public String getCdProveedor() {
		return cdProveedor;
	}
	
	public void setCdProveedor(String cdProveedor) {
		this.cdProveedor = cdProveedor;
	}
		
	public String getCdMoneda() {
		return cdMoneda;
	}

	public void setCdMoneda(String cdMoneda) {
		this.cdMoneda = cdMoneda;
	}

	public String getNbMoneda() {
		return nbMoneda;
	}

	public void setNbMoneda(String nbMoneda) {
		this.nbMoneda = nbMoneda;
	}

	public String getCdPeriodoFact() {
		return cdPeriodoFact;
	}

	public void setCdPeriodoFact(String cdPeriodoFact) {
		this.cdPeriodoFact = cdPeriodoFact;
	}

	public String getNuDias() {
		return nuDias;
	}

	public void setNuDias(String nuDias) {
		this.nuDias = nuDias;
	}

	public String getCdCotizacion() {
		return cdCotizacion;
	}

	public void setCdCotizacion(String cdCotizacion) {
		this.cdCotizacion = cdCotizacion;
	}

	public String getStHabilitado() {
		return stHabilitado;
	}
	
	public void setStHabilitado(String stHabilitado) {
		this.stHabilitado = stHabilitado;
	}
		
}
