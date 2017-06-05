package com.bbva.cfs.servprestdetalle.model;

import java.math.BigDecimal;

public class ServPrestDetalle {
    
    private String cdSector;
    private String cdGrupoProducto;
    private String cdProducto;
    private String nbDescripcion;
    private Integer cdLoteServ;
    private String cdRemito;
    private String fhRemito;
    private String fhFinServ;
    private java.math.BigDecimal ctServPrest;
    private java.math.BigDecimal imPrecioTotal;
    private String nbObservaciones;
    private String stLoteDet;
    private Integer cdConciliacion;
    private String nbAtributoRef1;
    private String nbAtributoRef2;
    private String nbTipVal;

    public String getCdSector() {
            return cdSector;
    }

    public void setCdSector(String cdSector) {
            this.cdSector = cdSector;
    }
	
    public String getCdGrupoProducto() {
            return cdGrupoProducto;
    }

    public void setCdGrupoProducto(String cdGrupoProducto) {
            this.cdGrupoProducto = cdGrupoProducto;
    }

    public String getCdProducto() {
            return cdProducto;
    }

    public void setCdProducto(String cdProducto) {
            this.cdProducto = cdProducto;
    }

    public String getNbDescripcion() {
        
        return nbDescripcion;
    }
    
    public void setNbDescripcion(String nbDescripcion) {
        
        this.nbDescripcion = nbDescripcion;
    }
    
    public Integer getCdLoteServ() {
        
        return cdLoteServ;
    }
    
    public void setCdLoteServ(Integer cdLoteServ) {
        
        this.cdLoteServ = cdLoteServ;
    }
    
    public String getCdRemito() {
        
        return cdRemito;
    }
    
    public void setCdRemito(String cdRemito) {
        
        this.cdRemito = cdRemito;
    }
    
    public String getFhRemito() {
        
        return fhRemito;
    }
    
    public void setFhRemito(String fhRemito) {
        
        this.fhRemito = fhRemito;
    }
    
    public String getFhFinServ() {
        
        return fhFinServ;
    }
    
    public void setFhFinServ(String fhFinServ) {
        
        this.fhFinServ = fhFinServ;
    }
    
    public java.math.BigDecimal getCtServPrest() {
        
        return ctServPrest;
    }
    
    public void setCtServPrest(java.math.BigDecimal ctServPrest) {
        
        this.ctServPrest = ctServPrest;
    }
    
    public java.math.BigDecimal getImPrecioTotal() {
        
        return imPrecioTotal;
    }
    
    public void setImPrecioTotal(java.math.BigDecimal imPrecioTotal) {
        
        this.imPrecioTotal = imPrecioTotal;
    }

    public String getNbObservaciones() {
        
        return nbObservaciones;
    }
    
    public void setNbObservaciones(String nbObservaciones) {
        
        this.nbObservaciones = nbObservaciones;
    }
    
    public String getStLoteDet() {
        
        return stLoteDet;
    }
    
    public void setStLoteDet(String stLoteDet) {
        
        this.stLoteDet = stLoteDet;
    }

    public Integer getCdConciliacion() {
        
        return cdConciliacion;
    }
    
    public void setCdConciliacion(Integer cdConciliacion) {
        
        this.cdConciliacion = cdConciliacion;
    }

	public String getNbAtributoRef1() {
		return nbAtributoRef1;
	}

	public void setNbAtributoRef1(String nbAtributoRef1) {
		this.nbAtributoRef1 = nbAtributoRef1;
	}

	public String getNbAtributoRef2() {
		return nbAtributoRef2;
	}

	public void setNbAtributoRef2(String nbAtributoRef2) {
		this.nbAtributoRef2 = nbAtributoRef2;
	}

	public String getNbTipVal() {
		return nbTipVal;
	}

	public void setNbTipVal(String nbTipVal) {
		this.nbTipVal = nbTipVal;
	}
}
