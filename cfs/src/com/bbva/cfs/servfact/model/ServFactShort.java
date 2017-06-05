package com.bbva.cfs.servfact.model;

import java.math.BigDecimal;

public class ServFactShort {
	
	private String cdSector;
	
	private String cdGrupoProd;
	private String cdProd;
	private String cdPeriodo;
//TOTALES
	private Long ctServFact = 0L;
	private BigDecimal valServFact = BigDecimal.ZERO;
	private Long ctRemitos = 0L;
	private Long ctRemitosAConci = 0L;
	private BigDecimal valBruto = BigDecimal.ZERO;
	private BigDecimal valNeto = BigDecimal.ZERO;
	private Integer nuPorcVarMax;
	
	public String getCdSector() {
		return cdSector;
	}
	public void setCdSector(String cdSector) {
		this.cdSector = cdSector;
	}
	public String getCdGrupoProd() {
		return cdGrupoProd;
	}
	public void setCdGrupoProd(String cdGrupoProd) {
		this.cdGrupoProd = cdGrupoProd;
	}
	public String getCdProd() {
		return cdProd;
	}
	public void setCdProd(String cdProd) {
		this.cdProd = cdProd;
	}
	public String getCdPeriodo() {
		return cdPeriodo;
	}
	public void setCdPeriodo(String cdPeriodo) {
		this.cdPeriodo = cdPeriodo;
	}
	public Long getCtServFact() {
		return ctServFact;
	}
	public void setCtServFact(Long ctServFact) {
		this.ctServFact = ctServFact;
	}
	public BigDecimal getValServFact() {
		return valServFact;
	}
	public void setValServFact(BigDecimal valServFact) {
		this.valServFact = valServFact;
	}
	public Long getCtRemitos() {
		return ctRemitos;
	}
	public void setCtRemitos(Long ctRemitos) {
		this.ctRemitos = ctRemitos;
	}
	public Long getCtRemitosAConci() {
		return ctRemitosAConci;
	}
	public void setCtRemitosAConci(Long ctRemitosAConci) {
		this.ctRemitosAConci = ctRemitosAConci;
	}
	public BigDecimal getValBruto() {
		return valBruto;
	}
	public void setValBruto(BigDecimal valBruto) {
		this.valBruto = valBruto;
	}
	public BigDecimal getValNeto() {
		return valNeto;
	}
	public void setValNeto(BigDecimal valNeto) {
		this.valNeto = valNeto;
	}
	public Integer getNuPorcVarMax() {
		return nuPorcVarMax;
	}
	public void setNuPorcVarMax(Integer nuPorcVarMax) {
		this.nuPorcVarMax = nuPorcVarMax;
	}
	
			
}
