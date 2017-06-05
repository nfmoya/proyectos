package com.bbva.cfs.servprest.model;

import java.math.BigDecimal;

public class ServPrestShort {
	
	private String cdSector;
	
	private String cdGrupoProd;
	private String cdProd;
	private String cdPeriodo;

	private Long ctServPrest = 0L;
	private BigDecimal valServPrest = BigDecimal.ZERO;
	private Long ctRemitos = 0L;
	private Long ctRemitosAConci = 0L;
	private BigDecimal valBruto = BigDecimal.ZERO;
	private BigDecimal valNeto = BigDecimal.ZERO;
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
	public Long getCtServPrest() {
		return ctServPrest;
	}
	public void setCtServPrest(Long ctServPrest) {
		this.ctServPrest = ctServPrest;
	}
	public BigDecimal getValServPrest() {
		return valServPrest;
	}
	public void setValServPrest(BigDecimal valServPrest) {
		this.valServPrest = valServPrest;
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
			
}


