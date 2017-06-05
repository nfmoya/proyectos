package com.bbva.cfs.producto.model;

import java.math.BigDecimal;
import java.util.Date;

public class ProductoPrecio {

	private String cdProveedor; 
	private String cdProducto;
	private String fhDesde;
	private String fhHasta;
    private String nuPrecioUniVal;
    private String stHabilitado;
    
	public String getCdProveedor() {
		return cdProveedor;
	}
	
	public void setCdProveedor(String cdProveedor) {
		this.cdProveedor = cdProveedor;
	}
	
	public String getCdProducto() {
		return cdProducto;
	}
	
	public void setCdProducto(String cdProducto) {
		this.cdProducto = cdProducto;
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
	
	public String getNuPrecioUniVal() {
		return nuPrecioUniVal;
	}
	
	public void setNuPrecioUniVal(String nuPrecioUniVal) {
		this.nuPrecioUniVal = nuPrecioUniVal;
	}
	
	public String getStHabilitado() {
		return stHabilitado;
	}
	
	public void setStHabilitado(String stHabilitado) {
		this.stHabilitado = stHabilitado;
	}
    
	
}
