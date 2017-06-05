package com.bbva.cfs.conciliacion.model;

public class ProductoConciliacion {

	private String cdProveedor;
	private String cdProducto;
	private String nbProducto;
	private String cdUniVal;
	private String stConcilSinVal;
	private String stServSinConcil;
	private String stFactSinConcil;
	private Long nuDiaEmiFDesde;
	private Long nuDiaEmiFHasta;
	private Long nuDiaCierreFDesde;
	private Long nuDiaCierreFHasta;
	private String stHabilitado;
	private String cdGrupoProducto;
	private String stRelacionSector;
	
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
	
	public String getNbProducto() {
		return nbProducto;
	}
	
	public void setNbProducto(String nbProducto) {
		this.nbProducto = nbProducto;
	}
	
	public String getCdUniVal() {
		return cdUniVal;
	}
	
	public void setCdUniVal(String cdUniVal) {
		this.cdUniVal = cdUniVal;
	}
	
	public String getStConcilSinVal() {
		return stConcilSinVal;
	}
	
	public void setStConcilSinVal(String stConcilSinVal) {
		this.stConcilSinVal = stConcilSinVal;
	}
	
	public String getStServSinConcil() {
		return stServSinConcil;
	}
	
	public void setStServSinConcil(String stServSinConcil) {
		this.stServSinConcil = stServSinConcil;
	}
	
	public String getStFactSinConcil() {
		return stFactSinConcil;
	}
	
	public void setStFactSinConcil(String stFactSinConcil) {
		this.stFactSinConcil = stFactSinConcil;
	}
	
	public Long getNuDiaEmiFDesde() {
		return nuDiaEmiFDesde;
	}
	
	public void setNuDiaEmiFDesde(Long nuDiaEmiFDesde) {
		this.nuDiaEmiFDesde = nuDiaEmiFDesde;
	}
	
	public Long getNuDiaEmiFHasta() {
		return nuDiaEmiFHasta;
	}
	
	public void setNuDiaEmiFHasta(Long nuDiaEmiFHasta) {
		this.nuDiaEmiFHasta = nuDiaEmiFHasta;
	}
	
	public Long getNuDiaCierreFDesde() {
		return nuDiaCierreFDesde;
	}
	
	public void setNuDiaCierreFDesde(Long nuDiaCierreFDesde) {
		this.nuDiaCierreFDesde = nuDiaCierreFDesde;
	}
	
	public Long getNuDiaCierreFHasta() {
		return nuDiaCierreFHasta;
	}
	
	public void setNuDiaCierreFHasta(Long nuDiaCierreFHasta) {
		this.nuDiaCierreFHasta = nuDiaCierreFHasta;
	}
	
	public String getStHabilitado() {
		return stHabilitado;
	}
	
	public void setStHabilitado(String stHabilitado) {
		this.stHabilitado = stHabilitado;
	}

	public String getCdGrupoProducto() {
		return cdGrupoProducto;
	}

	public void setCdGrupoProducto(String cdGrupoProducto) {
		this.cdGrupoProducto = cdGrupoProducto;
	}

	public String getStRelacionSector() {
		return stRelacionSector;
	}

	public void setStRelacionSector(String stRelacionSector) {
		this.stRelacionSector = stRelacionSector;
	}

}
