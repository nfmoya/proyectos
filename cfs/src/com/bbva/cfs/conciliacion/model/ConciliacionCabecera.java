package com.bbva.cfs.conciliacion.model;

public class ConciliacionCabecera {

    private int cdConciliacion;
	private String cdProveedor;
	private String cdProducto;
	private String cdSector;
	private String cdPeriodoFact;
    private String stIgnoraVal;
    private String nbObservaciones;
    private String stConciliacion;
    private String fhRemitoDesde;
    private String fhRemitoHasta;
    private String fhFinSerDesde;
    private String fhFinSerHasta;
    
	public int getCdConciliacion() {
		return cdConciliacion;
	}
	
	public void setCdConciliacion(int cdConciliacion) {
		this.cdConciliacion = cdConciliacion;
	}
	
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
	
	public String getCdPeriodoFact() {
		return cdPeriodoFact;
	}
	
	public void setCdPeriodoFact(String cdPeriodoFact) {
		this.cdPeriodoFact = cdPeriodoFact;
	}
	
	public String getStIgnoraVal() {
		return stIgnoraVal;
	}
	
	public void setStIgnoraVal(String stIgnoraVal) {
		this.stIgnoraVal = stIgnoraVal;
	}
	
	public String getNbObservaciones() {
		return nbObservaciones;
	}
	
	public void setNbObservaciones(String nbObservaciones) {
		this.nbObservaciones = nbObservaciones;
	}
	
	public String getStConciliacion() {
		return stConciliacion;
	}
	
	public void setStConciliacion(String stConciliacion) {
		this.stConciliacion = stConciliacion;
	}

	public String getFhRemitoDesde() {
		return fhRemitoDesde;
	}

	public void setFhRemitoDesde(String fhRemitoDesde) {
		this.fhRemitoDesde = fhRemitoDesde;
	}

	public String getFhRemitoHasta() {
		return fhRemitoHasta;
	}

	public void setFhRemitoHasta(String fhRemitoHasta) {
		this.fhRemitoHasta = fhRemitoHasta;
	}

	public String getFhFinSerDesde() {
		return fhFinSerDesde;
	}

	public void setFhFinSerDesde(String fhFinSerDesde) {
		this.fhFinSerDesde = fhFinSerDesde;
	}

	public String getFhFinSerHasta() {
		return fhFinSerHasta;
	}

	public void setFhFinSerHasta(String fhFinSerHasta) {
		this.fhFinSerHasta = fhFinSerHasta;
	}
}
