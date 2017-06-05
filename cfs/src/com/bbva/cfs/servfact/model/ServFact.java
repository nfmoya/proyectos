package com.bbva.cfs.servfact.model;

import java.math.BigDecimal;

public class ServFact {
	
	
	public ServFact() {
//		super();
		this.cdSector = "";
		this.nbSector = "";
		this.cdGrupoProd = "";
		this.dsGrupoProd = "";
		this.cdProd = "";
		this.dsProd = "";
		this.ctServFactMes1 = 0L;
		this.valServFactMes1 = BigDecimal.ZERO;
		this.ctRemitosMes1 =  0L;
		this.ctRemitosAConcilMes1 =  0L;
		this.ctServFactMes2 =  0L;
		this.valServFactMes2 = BigDecimal.ZERO;
		this.ctRemitosMes2 =  0L;
		this.ctRemitosAConcilMes2 = 0L;
		this.ctServFactMes3 =  0L;
		this.valServFactMes3 =  BigDecimal.ZERO;
		this.ctRemitosMes3 =  0L;
		this.ctRemitosAConcilMes3 =  0L;
		this.ctServFactMes4 =  0L;
		this.valServFactMes4 =  BigDecimal.ZERO;
		this.ctRemitosMes4 =  0L;
		this.ctRemitosAConcilMes4 =  0L;
		this.ctServFactMes5 =  0L;
		this.valServFactMes5 =  BigDecimal.ZERO;
		this.ctRemitosMes5 =  0L;
		this.ctRemitosAConcilMes5 =  0L;
		this.ctServFactMes6 =  0L;
		this.valServFactMes6 =  BigDecimal.ZERO;
		this.ctRemitosMes6 =  0L;
		this.ctRemitosAConcilMes6 =  0L;
		this.ctServFactMes7 =  0L;
		this.valServFactMes7 =  BigDecimal.ZERO;
		this.ctRemitosMes7 =  0L;
		this.ctRemitosAConcilMes7 =  0L;
		this.ctServFactMes8 =  0L;
		this.valServFactMes8 =  BigDecimal.ZERO;
		this.ctRemitosMes8 =  0L;
		this.ctRemitosAConcilMes8 =  0L;
		this.ctServFactMes9 =  0L;
		this.valServFactMes9 =  BigDecimal.ZERO;
		this.ctRemitosMes9 =  0L;
		this.ctRemitosAConcilMes9 =  0L;
		this.ctServFactMes10 =  0L;
		this.valServFactMes10 =  BigDecimal.ZERO;
		this.ctRemitosMes10 =  0L;
		this.ctRemitosAConcilMes10 =  0L;
		this.ctServFactMes11 =  0L;
		this.valServFactMes11 =  BigDecimal.ZERO;
		this.ctRemitosMes11 =  0L;
		this.ctRemitosAConcilMes11 =  0L;
		this.ctServFactMes12 =  0L;
		this.valServFactMes12 =  BigDecimal.ZERO;
		this.ctRemitosMes12 =  0L;
		this.ctRemitosAConcilMes12 =  0L;
		this.ctServFactTotal =  0L;
		this.valServFactTotal =  BigDecimal.ZERO;
		this.ctRemitosTotal =  0L;
		this.ctRemitosAConcilTotal =  0L;
		this.valBrutaTotal =  BigDecimal.ZERO;
		this.valNetoTotal =  BigDecimal.ZERO;
		this.nbSectorAbrev =  "";
		this.cdSectorAlt = "";
		this.stHabilitado =  "";
		this.setNuPorcVarMax(0);		
	}

	private String cdSector;
	private String nbSector;
	
	private String cdGrupoProd;
	private String dsGrupoProd;
	private String cdProd;
	private String dsProd;
	
	private Integer nuPorcVarMax = 0;
	
//	Mes1
	private Long ctServFactMes1 = 0L;
	private BigDecimal valServFactMes1 = BigDecimal.ZERO;
	private Long ctRemitosMes1 = 0L;
	private Long ctRemitosAConcilMes1 = 0L;
//	Mes2
	private Long ctServFactMes2 = 0L;
	private BigDecimal valServFactMes2 = BigDecimal.ZERO;
	private Long ctRemitosMes2 = 0L;
	private Long ctRemitosAConcilMes2 = 0L;
//	Mes3
	private Long ctServFactMes3 = 0L;
	private BigDecimal valServFactMes3 = BigDecimal.ZERO;
	private Long ctRemitosMes3 = 0L;
	private Long ctRemitosAConcilMes3 = 0L;
//	Mes4
	private Long ctServFactMes4 = 0L;
	private BigDecimal valServFactMes4 = BigDecimal.ZERO;
	private Long ctRemitosMes4 = 0L;
	private Long ctRemitosAConcilMes4 = 0L;
//	Mes5
	private Long ctServFactMes5 = 0L;
	private BigDecimal valServFactMes5 = BigDecimal.ZERO;
	private Long ctRemitosMes5 = 0L;
	private Long ctRemitosAConcilMes5 = 0L;
//	Mes6
	private Long ctServFactMes6 = 0L;
	private BigDecimal valServFactMes6 = BigDecimal.ZERO;
	private Long ctRemitosMes6 = 0L;
	private Long ctRemitosAConcilMes6 = 0L;
//	Mes7
	private Long ctServFactMes7 = 0L;
	private BigDecimal valServFactMes7 = BigDecimal.ZERO;
	private Long ctRemitosMes7 = 0L;
	private Long ctRemitosAConcilMes7 = 0L;
//	Mes8
	private Long ctServFactMes8 = 0L;
	private BigDecimal valServFactMes8 = BigDecimal.ZERO;
	private Long ctRemitosMes8 = 0L;
	private Long ctRemitosAConcilMes8 = 0L;
//	Mes9
	private Long ctServFactMes9 = 0L;
	private BigDecimal valServFactMes9 = BigDecimal.ZERO;
	private Long ctRemitosMes9 = 0L;
	private Long ctRemitosAConcilMes9 = 0L;
//	Mes10
	private Long ctServFactMes10 = 0L;
	private BigDecimal valServFactMes10 = BigDecimal.ZERO;
	private Long ctRemitosMes10 = 0L;
	private Long ctRemitosAConcilMes10 = 0L;
//	Mes11
	private Long ctServFactMes11 = 0L;
	private BigDecimal valServFactMes11 = BigDecimal.ZERO;
	private Long ctRemitosMes11 = 0L;
	private Long ctRemitosAConcilMes11 = 0L;
//	Mes12
	private Long ctServFactMes12 = 0L;
	private BigDecimal valServFactMes12 = BigDecimal.ZERO;
	private Long ctRemitosMes12 = 0L;
	private Long ctRemitosAConcilMes12 = 0L;
//TOTALES
	private Long ctServFactTotal = 0L;
	private BigDecimal valServFactTotal = BigDecimal.ZERO;
	private Long ctRemitosTotal = 0L;
	private Long ctRemitosAConcilTotal = 0L;
	private BigDecimal valBrutaTotal = BigDecimal.ZERO;
	private BigDecimal valNetoTotal = BigDecimal.ZERO;
	
	private String nbSectorAbrev;
	private String cdSectorAlt;
	private String stHabilitado;
	
	public String getCdSector() {
		return cdSector;
	}
	
	public void setCdSector(String cdSector) {
		this.cdSector = cdSector;
	}
	
	public String getNbSector() {
		return nbSector;
	}
	
	public void setNbSector(String nbSector) {
		this.nbSector = nbSector;
	}
	
	public String getNbSectorAbrev() {
		return nbSectorAbrev;
	}
	
	public void setNbSectorAbrev(String nbSectorAbrev) {
		this.nbSectorAbrev = nbSectorAbrev;
	}
	
	public String getCdSectorAlt() {
		return cdSectorAlt;
	}
	
	public void setCdSectorAlt(String cdSectorAlt) {
		this.cdSectorAlt = cdSectorAlt;
	}
	
	public String getStHabilitado() {
		return stHabilitado;
	}
	
	public void setStHabilitado(String stHabilitado) {
		this.stHabilitado = stHabilitado;
	}

//	TODO A PARTIR DE ACA NO SE BORRA
	
	public String getCdGrupoProd() {
		return cdGrupoProd;
	}

	public void setCdGrupoProd(String cdGrupoProd) {
		this.cdGrupoProd = cdGrupoProd;
	}

	public String getDsGrupoProd() {
		return dsGrupoProd;
	}

	public void setDsGrupoProd(String dsGrupoProd) {
		this.dsGrupoProd = dsGrupoProd;
	}

	public String getCdProd() {
		return cdProd;
	}

	public void setCdProd(String cdProd) {
		this.cdProd = cdProd;
	}

	public String getDsProd() {
		return dsProd;
	}

	public void setDsProd(String dsProd) {
		this.dsProd = dsProd;
	}

	public Long getCtServFactMes1() {
		return ctServFactMes1;
	}

	public void setCtServFactMes1(Long ctServFactMes1) {
		this.ctServFactMes1 = ctServFactMes1;
	}

	public BigDecimal getValServFactMes1() {
		return valServFactMes1;
	}

	public void setValServFactMes1(BigDecimal valServFactMes1) {
		this.valServFactMes1 = valServFactMes1;
	}

	public Long getCtRemitosMes1() {
		return ctRemitosMes1;
	}

	public void setCtRemitosMes1(Long ctRemitosMes1) {
		this.ctRemitosMes1 = ctRemitosMes1;
	}

	public Long getCtRemitosAConcilMes1() {
		return ctRemitosAConcilMes1;
	}

	public void setCtRemitosAConcilMes1(Long ctRemitosAConcilMes1) {
		this.ctRemitosAConcilMes1 = ctRemitosAConcilMes1;
	}

	public Long getCtServFactMes2() {
		return ctServFactMes2;
	}

	public void setCtServFactMes2(Long ctServFactMes2) {
		this.ctServFactMes2 = ctServFactMes2;
	}

	public BigDecimal getValServFactMes2() {
		return valServFactMes2;
	}

	public void setValServFactMes2(BigDecimal valServFactMes2) {
		this.valServFactMes2 = valServFactMes2;
	}

	public Long getCtRemitosMes2() {
		return ctRemitosMes2;
	}

	public void setCtRemitosMes2(Long ctRemitosMes2) {
		this.ctRemitosMes2 = ctRemitosMes2;
	}

	public Long getCtRemitosAConcilMes2() {
		return ctRemitosAConcilMes2;
	}

	public void setCtRemitosAConcilMes2(Long ctRemitosAConcilMes2) {
		this.ctRemitosAConcilMes2 = ctRemitosAConcilMes2;
	}

	public Long getCtServFactMes3() {
		return ctServFactMes3;
	}

	public void setCtServFactMes3(Long ctServFactMes3) {
		this.ctServFactMes3 = ctServFactMes3;
	}

	public BigDecimal getValServFactMes3() {
		return valServFactMes3;
	}

	public void setValServFactMes3(BigDecimal valServFactMes3) {
		this.valServFactMes3 = valServFactMes3;
	}

	public Long getCtRemitosMes3() {
		return ctRemitosMes3;
	}

	public void setCtRemitosMes3(Long ctRemitosMes3) {
		this.ctRemitosMes3 = ctRemitosMes3;
	}

	public Long getCtRemitosAConcilMes3() {
		return ctRemitosAConcilMes3;
	}

	public void setCtRemitosAConcilMes3(Long ctRemitosAConcilMes3) {
		this.ctRemitosAConcilMes3 = ctRemitosAConcilMes3;
	}

	public Long getCtServFactMes4() {
		return ctServFactMes4;
	}

	public void setCtServFactMes4(Long ctServFactMes4) {
		this.ctServFactMes4 = ctServFactMes4;
	}

	public BigDecimal getValServFactMes4() {
		return valServFactMes4;
	}

	public void setValServFactMes4(BigDecimal valServFactMes4) {
		this.valServFactMes4 = valServFactMes4;
	}

	public Long getCtRemitosMes4() {
		return ctRemitosMes4;
	}

	public void setCtRemitosMes4(Long ctRemitosMes4) {
		this.ctRemitosMes4 = ctRemitosMes4;
	}

	public Long getCtRemitosAConcilMes4() {
		return ctRemitosAConcilMes4;
	}

	public void setCtRemitosAConcilMes4(Long ctRemitosAConcilMes4) {
		this.ctRemitosAConcilMes4 = ctRemitosAConcilMes4;
	}

	public Long getCtServFactMes5() {
		return ctServFactMes5;
	}

	public void setCtServFactMes5(Long ctServFactMes5) {
		this.ctServFactMes5 = ctServFactMes5;
	}

	public BigDecimal getValServFactMes5() {
		return valServFactMes5;
	}

	public void setValServFactMes5(BigDecimal valServFactMes5) {
		this.valServFactMes5 = valServFactMes5;
	}

	public Long getCtRemitosMes5() {
		return ctRemitosMes5;
	}

	public void setCtRemitosMes5(Long ctRemitosMes5) {
		this.ctRemitosMes5 = ctRemitosMes5;
	}

	public Long getCtRemitosAConcilMes5() {
		return ctRemitosAConcilMes5;
	}

	public void setCtRemitosAConcilMes5(Long ctRemitosAConcilMes5) {
		this.ctRemitosAConcilMes5 = ctRemitosAConcilMes5;
	}

	public Long getCtServFactMes6() {
		return ctServFactMes6;
	}

	public void setCtServFactMes6(Long ctServFactMes6) {
		this.ctServFactMes6 = ctServFactMes6;
	}

	public BigDecimal getValServFactMes6() {
		return valServFactMes6;
	}

	public void setValServFactMes6(BigDecimal valServFactMes6) {
		this.valServFactMes6 = valServFactMes6;
	}

	public Long getCtRemitosMes6() {
		return ctRemitosMes6;
	}

	public void setCtRemitosMes6(Long ctRemitosMes6) {
		this.ctRemitosMes6 = ctRemitosMes6;
	}

	public Long getCtRemitosAConcilMes6() {
		return ctRemitosAConcilMes6;
	}

	public void setCtRemitosAConcilMes6(Long ctRemitosAConcilMes6) {
		this.ctRemitosAConcilMes6 = ctRemitosAConcilMes6;
	}

	public Long getCtServFactMes7() {
		return ctServFactMes7;
	}

	public void setCtServFactMes7(Long ctServFactMes7) {
		this.ctServFactMes7 = ctServFactMes7;
	}

	public BigDecimal getValServFactMes7() {
		return valServFactMes7;
	}

	public void setValServFactMes7(BigDecimal valServFactMes7) {
		this.valServFactMes7 = valServFactMes7;
	}

	public Long getCtRemitosMes7() {
		return ctRemitosMes7;
	}

	public void setCtRemitosMes7(Long ctRemitosMes7) {
		this.ctRemitosMes7 = ctRemitosMes7;
	}

	public Long getCtRemitosAConcilMes7() {
		return ctRemitosAConcilMes7;
	}

	public void setCtRemitosAConcilMes7(Long ctRemitosAConcilMes7) {
		this.ctRemitosAConcilMes7 = ctRemitosAConcilMes7;
	}

	public Long getCtServFactMes8() {
		return ctServFactMes8;
	}

	public void setCtServFactMes8(Long ctServFactMes8) {
		this.ctServFactMes8 = ctServFactMes8;
	}

	public BigDecimal getValServFactMes8() {
		return valServFactMes8;
	}

	public void setValServFactMes8(BigDecimal valServFactMes8) {
		this.valServFactMes8 = valServFactMes8;
	}

	public Long getCtRemitosMes8() {
		return ctRemitosMes8;
	}

	public void setCtRemitosMes8(Long ctRemitosMes8) {
		this.ctRemitosMes8 = ctRemitosMes8;
	}

	public Long getCtRemitosAConcilMes8() {
		return ctRemitosAConcilMes8;
	}

	public void setCtRemitosAConcilMes8(Long ctRemitosAConcilMes8) {
		this.ctRemitosAConcilMes8 = ctRemitosAConcilMes8;
	}

	public Long getCtServFactMes9() {
		return ctServFactMes9;
	}

	public void setCtServFactMes9(Long ctServFactMes9) {
		this.ctServFactMes9 = ctServFactMes9;
	}

	public BigDecimal getValServFactMes9() {
		return valServFactMes9;
	}

	public void setValServFactMes9(BigDecimal valServFactMes9) {
		this.valServFactMes9 = valServFactMes9;
	}

	public Long getCtRemitosMes9() {
		return ctRemitosMes9;
	}

	public void setCtRemitosMes9(Long ctRemitosMes9) {
		this.ctRemitosMes9 = ctRemitosMes9;
	}

	public Long getCtRemitosAConcilMes9() {
		return ctRemitosAConcilMes9;
	}

	public void setCtRemitosAConcilMes9(Long ctRemitosAConcilMes9) {
		this.ctRemitosAConcilMes9 = ctRemitosAConcilMes9;
	}

	public Long getCtServFactMes10() {
		return ctServFactMes10;
	}

	public void setCtServFactMes10(Long ctServFactMes10) {
		this.ctServFactMes10 = ctServFactMes10;
	}

	public BigDecimal getValServFactMes10() {
		return valServFactMes10;
	}

	public void setValServFactMes10(BigDecimal valServFactMes10) {
		this.valServFactMes10 = valServFactMes10;
	}

	public Long getCtRemitosMes10() {
		return ctRemitosMes10;
	}

	public void setCtRemitosMes10(Long ctRemitosMes10) {
		this.ctRemitosMes10 = ctRemitosMes10;
	}

	public Long getCtRemitosAConcilMes10() {
		return ctRemitosAConcilMes10;
	}

	public void setCtRemitosAConcilMes10(Long ctRemitosAConcilMes10) {
		this.ctRemitosAConcilMes10 = ctRemitosAConcilMes10;
	}

	public Long getCtServFactMes11() {
		return ctServFactMes11;
	}

	public void setCtServFactMes11(Long ctServFactMes11) {
		this.ctServFactMes11 = ctServFactMes11;
	}

	public BigDecimal getValServFactMes11() {
		return valServFactMes11;
	}

	public void setValServFactMes11(BigDecimal valServFactMes11) {
		this.valServFactMes11 = valServFactMes11;
	}

	public Long getCtRemitosMes11() {
		return ctRemitosMes11;
	}

	public void setCtRemitosMes11(Long ctRemitosMes11) {
		this.ctRemitosMes11 = ctRemitosMes11;
	}

	public Long getCtRemitosAConcilMes11() {
		return ctRemitosAConcilMes11;
	}

	public void setCtRemitosAConcilMes11(Long ctRemitosAConcilMes11) {
		this.ctRemitosAConcilMes11 = ctRemitosAConcilMes11;
	}

	public Long getCtServFactMes12() {
		return ctServFactMes12;
	}

	public void setCtServFactMes12(Long ctServFactMes12) {
		this.ctServFactMes12 = ctServFactMes12;
	}

	public BigDecimal getValServFactMes12() {
		return valServFactMes12;
	}

	public void setValServFactMes12(BigDecimal valServFactMes12) {
		this.valServFactMes12 = valServFactMes12;
	}

	public Long getCtRemitosMes12() {
		return ctRemitosMes12;
	}

	public void setCtRemitosMes12(Long ctRemitosMes12) {
		this.ctRemitosMes12 = ctRemitosMes12;
	}

	public Long getCtRemitosAConcilMes12() {
		return ctRemitosAConcilMes12;
	}

	public void setCtRemitosAConcilMes12(Long ctRemitosAConcilMes12) {
		this.ctRemitosAConcilMes12 = ctRemitosAConcilMes12;
	}

	public Long getCtServFactTotal() {
		return ctServFactTotal;
	}

	public void setCtServFactTotal(Long ctServFactTotal) {
		this.ctServFactTotal = ctServFactTotal;
	}

	public BigDecimal getValServFactTotal() {
		return valServFactTotal;
	}

	public void setValServFactTotal(BigDecimal valServFactTotal) {
		this.valServFactTotal = valServFactTotal;
	}

	public Long getCtRemitosTotal() {
		return ctRemitosTotal;
	}

	public void setCtRemitosTotal(Long ctRemitosTotal) {
		this.ctRemitosTotal = ctRemitosTotal;
	}

	public Long getCtRemitosAConcilTotal() {
		return ctRemitosAConcilTotal;
	}

	public void setCtRemitosAConcilTotal(Long ctRemitosAConcilTotal) {
		this.ctRemitosAConcilTotal = ctRemitosAConcilTotal;
	}

	public BigDecimal getValBrutaTotal() {
		return valBrutaTotal;
	}

	public void setValBrutaTotal(BigDecimal valBrutaTotal) {
		this.valBrutaTotal = valBrutaTotal;
	}

	public BigDecimal getValNetoTotal() {
		return valNetoTotal;
	}

	public void setValNetoTotal(BigDecimal valNetoTotal) {
		this.valNetoTotal = valNetoTotal;
	}

	public Integer getNuPorcVarMax() {
		return nuPorcVarMax;
	}

	public void setNuPorcVarMax(Integer nuPorcVarMax) {
		this.nuPorcVarMax = nuPorcVarMax;
	}
			
}
