package com.bbva.cfs.nomedibles.model;

import java.math.BigDecimal;

public class Conciliacion {
	
	private String cdProducto;      
    private String cdPeriodoAnt;    
    private BigDecimal ctServFactAnt;    
    private String cdUniValAnt;
    private BigDecimal imPrecioTotalAnt; 
    private int cdConciliacionAnt; 
    private BigDecimal ctServFactAct;
    private String cdUniValAct;
    private BigDecimal imPrecioTotalAct; 
    private BigDecimal nuPorcVarMax; 
    private BigDecimal nuPorcVarVal;                 
    private int cdConciliacionAct; 
    private BigDecimal imDiferencia; 
    private String nbObservaciones;
    private String chkFact;
    private String tpSolucion;
    
	public String getCdProducto() {
		return cdProducto;
	}
	
	public void setCdProducto(String cdProducto) {
		this.cdProducto = cdProducto;
	}
	
	public String getCdPeriodoAnt() {
		return cdPeriodoAnt;
	}
	
	public void setCdPeriodoAnt(String cdPeriodoAnt) {
		this.cdPeriodoAnt = cdPeriodoAnt;
	}
	
	public BigDecimal getCtServFactAnt() {
		return ctServFactAnt;
	}
	
	public void setCtServFactAnt(BigDecimal ctServFactAnt) {
		this.ctServFactAnt = ctServFactAnt;
	}
	
	public String getCdUniValAnt() {
		return cdUniValAnt;
	}
	
	public void setCdUniValAnt(String cdUniValAnt) {
		this.cdUniValAnt = cdUniValAnt;
	}
	
	public BigDecimal getImPrecioTotalAnt() {
		return imPrecioTotalAnt;
	}
	
	public void setImPrecioTotalAnt(BigDecimal imPrecioTotalAnt) {
		this.imPrecioTotalAnt = imPrecioTotalAnt;
	}
	
	public int getCdConciliacionAnt() {
		return cdConciliacionAnt;
	}
	
	public void setCdConciliacionAnt(int cdConciliacionAnt) {
		this.cdConciliacionAnt = cdConciliacionAnt;
	}
	
	public BigDecimal getCtServFactAct() {
		return ctServFactAct;
	}
	
	public void setCtServFactAct(BigDecimal ctServFactAct) {
		this.ctServFactAct = ctServFactAct;
	}
	
	public String getCdUniValAct() {
		return cdUniValAct;
	}
	
	public void setCdUniValAct(String cdUniValAct) {
		this.cdUniValAct = cdUniValAct;
	}
	
	public BigDecimal getImPrecioTotalAct() {
		return imPrecioTotalAct;
	}
	
	public void setImPrecioTotalAct(BigDecimal imPrecioTotalAct) {
		this.imPrecioTotalAct = imPrecioTotalAct;
	}
	
	public BigDecimal getNuPorcVarMax() {
		return nuPorcVarMax;
	}
	
	public void setNuPorcVarMax(BigDecimal nuPorcVarMax) {
		this.nuPorcVarMax = nuPorcVarMax;
	}
	
	public BigDecimal getNuPorcVarVal() {
		return nuPorcVarVal;
	}
	
	public void setNuPorcVarVal(BigDecimal nuPorcVarVal) {
		this.nuPorcVarVal = nuPorcVarVal;
	}
	
	public int getCdConciliacionAct() {
		return cdConciliacionAct;
	}
	
	public void setCdConciliacionAct(int cdConciliacionAct) {
		this.cdConciliacionAct = cdConciliacionAct;
	}
	
	public BigDecimal getImDiferencia() {
		return imDiferencia;
	}
	
	public void setImDiferencia(BigDecimal imDiferencia) {
		this.imDiferencia = imDiferencia;
	}
	
	public String getNbObservaciones() {
		return nbObservaciones;
	}
	
	public void setNbObservaciones(String nbObservaciones) {
		this.nbObservaciones = nbObservaciones;
	}

	public String getChkFact() {
		return chkFact;
	}

	public void setChkFact(String chkFact) {
		this.chkFact = chkFact;
	}

	public String getTpSolucion() {
		return tpSolucion;
	}

	public void setTpSolucion(String tpSolucion) {
		this.tpSolucion = tpSolucion;
	} 
}
