package com.bbva.cfs.conciliacion.model;

import java.math.BigDecimal;

public class Conciliacion {

    private String cdRemitoPres;
    private String fhRemito;
    private String fhFinServ;
    private BigDecimal ctServPrest;
    private BigDecimal imPrecioUnitPres;
    private BigDecimal imPrecioTotalPres;
    private BigDecimal imPrecioTotalPresConv;
    private String cdUniValPres;
    private String cdConciliacionPres;
    private String chkPres;
    private String cdRemitoFact;
    private String tpComprobante;
    private String nuComprobante;
    private BigDecimal ctServFact;
    private BigDecimal imPrecioTotalFact;
    private String cdConciliacionFact;
    private String chkFact;
    private BigDecimal ctServFactDife;
    private BigDecimal imServFactDife;
    private int cdLoteServ;
    private int cdOrdenServ;
    private String cdSectorSol;
    private String cdSectorControl;
    private String cdProductoPres;
    private String nbPiezaDesdePres;
    private String nbPiezaHastaPres;
    private String cdRemitoPadre;
    private String nbAtributoRef1;
    private String nbAtributoRef2;
    private String nbObservaciones;
    private String stLoteDet;
    private String fhModificacion;
    private String nbUsuarioModif;
    private int cdLoteFact;
    private int cdOrdenFact;
	private String cdProveedor;
	private String cdProducto;
	private String cdSector;
	private String cdPeriodoFact;
    private String stIgnoraVal;
    private String cdTipVal;
    private String cdMoneda;

	public String getCdRemitoPres() {
		return cdRemitoPres;
	}

	public void setCdRemitoPres(String cdRemitoPres) {
		this.cdRemitoPres = cdRemitoPres;
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

	public BigDecimal getCtServPrest() {
		return ctServPrest;
	}

	public void setCtServPrest(BigDecimal ctServPrest) {
		this.ctServPrest = ctServPrest;
	}

	public BigDecimal getImPrecioTotalPres() {
		return imPrecioTotalPres;
	}

	public void setImPrecioTotalPres(BigDecimal imPrecioTotalPres) {
		this.imPrecioTotalPres = imPrecioTotalPres;
	}

	public BigDecimal getImPrecioTotalPresConv() {
		return imPrecioTotalPresConv;
	}

	public void setImPrecioTotalPresConv(BigDecimal imPrecioTotalPresConv) {
		this.imPrecioTotalPresConv = imPrecioTotalPresConv;
	}

	public String getCdUniValPres() {
		return cdUniValPres;
	}

	public void setCdUniValPres(String cdUniValPres) {
		this.cdUniValPres = cdUniValPres;
	}

	public String getCdConciliacionPres() {
		return cdConciliacionPres;
	}

	public void setCdConciliacionPres(String cdConciliacionPres) {
		this.cdConciliacionPres = cdConciliacionPres;
	}

	public String getChkPres() {
		return chkPres;
	}

	public void setChkPres(String chkPres) {
		this.chkPres = chkPres;
	}

	public String getCdRemitoFact() {
		return cdRemitoFact;
	}

	public void setCdRemitoFact(String cdRemitoFact) {
		this.cdRemitoFact = cdRemitoFact;
	}

	public String getTpComprobante() {
		return tpComprobante;
	}

	public void setTpComprobante(String tpComprobante) {
		this.tpComprobante = tpComprobante;
	}

	public String getNuComprobante() {
		return nuComprobante;
	}

	public void setNuComprobante(String nuComprobante) {
		this.nuComprobante = nuComprobante;
	}

	public BigDecimal getCtServFact() {
		return ctServFact;
	}

	public void setCtServFact(BigDecimal ctServFact) {
		this.ctServFact = ctServFact;
	}

	public BigDecimal getImPrecioTotalFact() {
		return imPrecioTotalFact;
	}

	public void setImPrecioTotalFact(BigDecimal imPrecioTotalFact) {
		this.imPrecioTotalFact = imPrecioTotalFact;
	}

	public String getCdConciliacionFact() {
		return cdConciliacionFact;
	}

	public void setCdConciliacionFact(String cdConciliacionFact) {
		this.cdConciliacionFact = cdConciliacionFact;
	}

	public String getChkFact() {
		return chkFact;
	}

	public void setChkFact(String chkFact) {
		this.chkFact = chkFact;
	}

	public BigDecimal getCtServFactDife() {
		return ctServFactDife;
	}

	public void setCtServFactDife(BigDecimal ctServFactDife) {
		this.ctServFactDife = ctServFactDife;
	}

	public BigDecimal getImServFactDife() {
		return imServFactDife;
	}

	public void setImServFactDife(BigDecimal imServFactDife) {
		this.imServFactDife = imServFactDife;
	}
	
	public int getCdLoteServ() {
		return cdLoteServ;
	}

	public void setCdLoteServ(int cdLoteServ) {
		this.cdLoteServ = cdLoteServ;
	}

	public int getCdOrdenServ() {
		return cdOrdenServ;
	}

	public void setCdOrdenServ(int cdOrdenServ) {
		this.cdOrdenServ = cdOrdenServ;
	}

	public String getCdSectorSol() {
		return cdSectorSol;
	}

	public void setCdSectorSol(String cdSectorSol) {
		this.cdSectorSol = cdSectorSol;
	}

	public String getCdSectorControl() {
		return cdSectorControl;
	}

	public void setCdSectorControl(String cdSectorControl) {
		this.cdSectorControl = cdSectorControl;
	}

	public String getCdProductoPres() {
		return cdProductoPres;
	}

	public void setCdProductoPres(String cdProductoPres) {
		this.cdProductoPres = cdProductoPres;
	}

	public String getNbPiezaDesdePres() {
		return nbPiezaDesdePres;
	}

	public void setNbPiezaDesdePres(String nbPiezaDesdePres) {
		this.nbPiezaDesdePres = nbPiezaDesdePres;
	}

	public String getNbPiezaHastaPres() {
		return nbPiezaHastaPres;
	}

	public void setNbPiezaHastaPres(String nbPiezaHastaPres) {
		this.nbPiezaHastaPres = nbPiezaHastaPres;
	}

	public String getCdRemitoPadre() {
		return cdRemitoPadre;
	}

	public void setCdRemitoPadre(String cdRemitoPadre) {
		this.cdRemitoPadre = cdRemitoPadre;
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

	public String getFhModificacion() {
		return fhModificacion;
	}

	public void setFhModificacion(String fhModificacion) {
		this.fhModificacion = fhModificacion;
	}

	public String getNbUsuarioModif() {
		return nbUsuarioModif;
	}

	public void setNbUsuarioModif(String nbUsuarioModif) {
		this.nbUsuarioModif = nbUsuarioModif;
	}

	public BigDecimal getImPrecioUnitPres() {
		return imPrecioUnitPres;
	}

	public void setImPrecioUnitPres(BigDecimal imPrecioUnitPres) {
		this.imPrecioUnitPres = imPrecioUnitPres;
	}

	public int getCdLoteFact() {
		return cdLoteFact;
	}

	public void setCdLoteFact(int cdLoteFact) {
		this.cdLoteFact = cdLoteFact;
	}

	public int getCdOrdenFact() {
		return cdOrdenFact;
	}

	public void setCdOrdenFact(int cdOrdenFact) {
		this.cdOrdenFact = cdOrdenFact;
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

	public String getCdTipVal() {
		return cdTipVal;
	}

	public void setCdTipVal(String cdTipVal) {
		this.cdTipVal = cdTipVal;
	}

	public String getCdMoneda() {
		return cdMoneda;
	}

	public void setCdMoneda(String cdMoneda) {
		this.cdMoneda = cdMoneda;
	}

}
