package com.bbva.cfs.proveedor.model;

import java.math.BigDecimal;

public class ProveedorValor {

	private String cdProveedor; 
	private String cdUniVal;
	private String cdPeriodoFact; 
    private String nuValBrutoUniVal;
    private String nuValNetoUniVal;
    private String stHabilitado;
    
	public String getCdProveedor() {
		return cdProveedor;
	}
	
	public void setCdProveedor(String cdProveedor) {
		this.cdProveedor = cdProveedor;
	}
		
	public String getCdUniVal() {
		return cdUniVal;
	}

	public void setCdUniVal(String cdUniVal) {
		this.cdUniVal = cdUniVal;
	}

	public String getCdPeriodoFact() {
		return cdPeriodoFact;
	}

	public void setCdPeriodoFact(String cdPeriodoFact) {
		this.cdPeriodoFact = cdPeriodoFact;
	}

	public String getNuValBrutoUniVal() {
		return nuValBrutoUniVal;
	}

	public void setNuValBrutoUniVal(String nuValBrutoUniVal) {
		this.nuValBrutoUniVal = nuValBrutoUniVal;
	}

	public String getNuValNetoUniVal() {
		return nuValNetoUniVal;
	}

	public void setNuValNetoUniVal(String nuValNetoUniVal) {
		this.nuValNetoUniVal = nuValNetoUniVal;
	}

	public String getStHabilitado() {
		return stHabilitado;
	}
	
	public void setStHabilitado(String stHabilitado) {
		this.stHabilitado = stHabilitado;
	}
		
}
