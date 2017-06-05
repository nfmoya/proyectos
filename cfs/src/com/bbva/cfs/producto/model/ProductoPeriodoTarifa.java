package com.bbva.cfs.producto.model;

import java.math.BigDecimal;
import java.util.Date;

public class ProductoPeriodoTarifa {

	private String cdProveedor; 
	private String cdProducto;
	private String fhDesde;
	private String fhHasta;
	private String nuCantDesde;
	private String nuCantHasta;
	private String nuPrecioUniVal;	
	private String nuPorcTarifa;
	private String stHabilitado;
    private String stPrecioFijo;
    
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
	
	public String getNuCantDesde() {
		return nuCantDesde;
	}

	public void setNuCantDesde(String nuCantDesde) {
		this.nuCantDesde = nuCantDesde;
	}

	public String getNuCantHasta() {
		return nuCantHasta;
	}

	public void setNuCantHasta(String nuCantHasta) {
		this.nuCantHasta = nuCantHasta;
	}

	public String getNuPrecioUniVal() {
		return nuPrecioUniVal;
	}

	public void setNuPrecioUniVal(String nuPrecioUniVal) {
		this.nuPrecioUniVal = nuPrecioUniVal;
	}

	public String getNuPorcTarifa() {
		return nuPorcTarifa;
	}

	public void setNuPorcTarifa(String nuPorcTarifa) {
		this.nuPorcTarifa = nuPorcTarifa;
	}

	public String getStHabilitado() {
		return stHabilitado;
	}
	
	public void setStHabilitado(String stHabilitado) {
		this.stHabilitado = stHabilitado;
	}

	public String getStPrecioFijo() {
		return stPrecioFijo;
	}

	public void setStPrecioFijo(String stPrecioFijo) {
		this.stPrecioFijo = stPrecioFijo;
	}

	public String getFhHasta() {
		return fhHasta;
	}

	public void setFhHasta(String fhHasta) {
		this.fhHasta = fhHasta;
	}	
}
