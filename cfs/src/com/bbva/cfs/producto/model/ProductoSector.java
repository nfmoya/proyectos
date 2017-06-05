package com.bbva.cfs.producto.model;

public class ProductoSector {

	private String cdProveedor; 
	private String cdProducto;
	private String cdSector;
	private String nbSector;
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
	
	public String getCdSector() {
		return cdSector;
	}
	
	public void setCdSector(String cdSector) {
		this.cdSector = cdSector;
	}
	
	public String getStHabilitado() {
		return stHabilitado;
	}
	
	public void setStHabilitado(String stHabilitado) {
		this.stHabilitado = stHabilitado;
	}

	public String getNbSector() {
		return nbSector;
	}

	public void setNbSector(String nbSector) {
		this.nbSector = nbSector;
	}  
	
}
