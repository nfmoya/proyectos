package com.bbva.cfs.conciliacion.model;

import java.math.BigDecimal;

public class Repetido {

    private String cdRemito;
    private String cdPeriodoFact;
    private String tpComprobante;
    private String nuComprobante;
    private BigDecimal ctServFact;
    private BigDecimal imPrecioUnit;
    private BigDecimal imPrecioTotal;
    
	public String getCdRemito() {
		return cdRemito;
	}
	
	public void setCdRemito(String cdRemito) {
		this.cdRemito = cdRemito;
	}
	
	public String getTpComprobante() {
		return tpComprobante;
	}
	
	public void setTpComprobante(String tpComprobante) {
		this.tpComprobante = tpComprobante;
	}
	
	public String getNuComprobante() {
		return nuComprobante;
	}
	
	public void setNuComprobante(String nuComprobante) {
		this.nuComprobante = nuComprobante;
	}
	
	public BigDecimal getCtServFact() {
		return ctServFact;
	}
	
	public void setCtServFact(BigDecimal ctServFact) {
		this.ctServFact = ctServFact;
	}
	
	public BigDecimal getImPrecioUnit() {
		return imPrecioUnit;
	}
	
	public void setImPrecioUnit(BigDecimal imPrecioUnit) {
		this.imPrecioUnit = imPrecioUnit;
	}
	
	public BigDecimal getImPrecioTotal() {
		return imPrecioTotal;
	}
	
	public void setImPrecioTotal(BigDecimal imPrecioTotal) {
		this.imPrecioTotal = imPrecioTotal;
	}

	public String getCdPeriodoFact() {
		return cdPeriodoFact;
	}

	public void setCdPeriodoFact(String cdPeriodoFact) {
		this.cdPeriodoFact = cdPeriodoFact;
	}
	    
}
