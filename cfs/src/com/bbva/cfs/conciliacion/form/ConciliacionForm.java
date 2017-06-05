package com.bbva.cfs.conciliacion.form;
 
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

public class ConciliacionForm extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cdProveedor;
	@SuppressWarnings("rawtypes")
	private List filtroProveedorList = new ArrayList();
	private String cdProducto;
	@SuppressWarnings("rawtypes")
	private List filtroProductoList = new ArrayList();
	private String cdSector;
	@SuppressWarnings("rawtypes")
	private List filtroSectorList = new ArrayList();
	private String cdPeriodoFact;
	@SuppressWarnings("rawtypes")
	private List filtroPeriodoList = new ArrayList();
	private String cdUniVal;
	@SuppressWarnings("rawtypes")
	private List filtroUniValList = new ArrayList();
	private String nuValBrutoUniVal;
	private String fhRemitoDesde;
	private String fhRemitoHasta;
	private String fhFinServicioDesde;
	private String fhFinServicioHasta;
	private String situacionConciliacion;	
    private String cdConciliacion;
	@SuppressWarnings("rawtypes")
	private List filtroConciliacionList = new ArrayList();
	private Long nuDiaEmiFDesde;
	private Long nuDiaEmiFHasta;
	private Long nuDiaCierreFDesde;
	private Long nuDiaCierreFHasta;
	private String stConcilSinVal;
	@SuppressWarnings("rawtypes")
	private List filtroSituacionConciliacionList = new ArrayList();
	
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
    private String stIgnoraVal;
    private String cdTipVal;
    private String cdMoneda;
    
	private String sector;

	/** Grant => Permisos*/
	private String saveGrant = "N";	
	private String differGrant = "N";	
	private String disableGrant = "N";	
	private String approveGrant= "N";	
	private String exportGrant = "N";	
	private String printGrant = "N";
	
	private String callBackFunction;
	private Integer cantBilletes;
	private String tipoCambio;
	
	/** ========================*/

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getDifferGrant() {
		return differGrant;
	}

	public void setDifferGrant(String differGrant) {
		this.differGrant = differGrant;
	}

	public String getDisableGrant() {
		return disableGrant;
	}

	public void setDisableGrant(String disableGrant) {
		this.disableGrant = disableGrant;
	}

	public String getSaveGrant() {
		return saveGrant;
	}

	public void setSaveGrant(String saveGrant) {
		this.saveGrant = saveGrant;
	}

	public String getApproveGrant() {
		return approveGrant;
	}

	public void setApproveGrant(String approveGrant) {
		this.approveGrant = approveGrant;
	}

	public String getExportGrant() {
		return exportGrant;
	}

	public String getPrintGrant() {
		return printGrant;
	}

	public void setPrintGrant(String printGrant) {
		this.printGrant = printGrant;
	}
	
	public void setExportGrant(String exportGrant) {
		this.exportGrant = exportGrant;
	}

	public String getCallBackFunction() {
		return callBackFunction;
	}

	public void setCallBackFunction(String callBackFunction) {
		this.callBackFunction = callBackFunction;
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

	@SuppressWarnings("rawtypes")
	public List getFiltroProveedorList() {
		return filtroProveedorList;
	}

	@SuppressWarnings("rawtypes")
	public void setFiltroProveedorList(List filtroProveedorList) {
		this.filtroProveedorList = filtroProveedorList;
	}

	@SuppressWarnings("rawtypes")
	public List getFiltroProductoList() {
		return filtroProductoList;
	}

	@SuppressWarnings("rawtypes")
	public void setFiltroProductoList(List filtroProductoList) {
		this.filtroProductoList = filtroProductoList;
	}

	@SuppressWarnings("rawtypes")
	public List getFiltroSectorList() {
		return filtroSectorList;
	}

	@SuppressWarnings("rawtypes")
	public void setFiltroSectorList(List filtroSectorList) {
		this.filtroSectorList = filtroSectorList;
	}

	@SuppressWarnings("rawtypes")
	public List getFiltroPeriodoList() {
		return filtroPeriodoList;
	}

	@SuppressWarnings("rawtypes")
	public void setFiltroPeriodoList(List filtroPeriodoList) {
		this.filtroPeriodoList = filtroPeriodoList;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
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

	@SuppressWarnings("rawtypes")
	public List getFiltroUniValList() {
		return filtroUniValList;
	}

	@SuppressWarnings("rawtypes")
	public void setFiltroUniValList(List filtroUniValList) {
		this.filtroUniValList = filtroUniValList;
	}

	public String getNuValBrutoUniVal() {
		return nuValBrutoUniVal;
	}

	public void setNuValBrutoUniVal(String nuValBrutoUniVal) {
		this.nuValBrutoUniVal = nuValBrutoUniVal;
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

	public String getFhFinServicioDesde() {
		return fhFinServicioDesde;
	}

	public void setFhFinServicioDesde(String fhFinServicioDesde) {
		this.fhFinServicioDesde = fhFinServicioDesde;
	}

	public String getFhFinServicioHasta() {
		return fhFinServicioHasta;
	}

	public void setFhFinServicioHasta(String fhFinServicioHasta) {
		this.fhFinServicioHasta = fhFinServicioHasta;
	}

	public String getSituacionConciliacion() {
		return situacionConciliacion;
	}

	public void setSituacionConciliacion(String situacionConciliacion) {
		this.situacionConciliacion = situacionConciliacion;
	}

	public String getCdConciliacion() {
		return cdConciliacion;
	}

	public void setCdConciliacion(String cdConciliacion) {
		this.cdConciliacion = cdConciliacion;
	}

	@SuppressWarnings("rawtypes")
	public List getFiltroConciliacionList() {
		return filtroConciliacionList;
	}

	@SuppressWarnings("rawtypes")
	public void setFiltroConciliacionList(List filtroConciliacionList) {
		this.filtroConciliacionList = filtroConciliacionList;
	}

	@SuppressWarnings("rawtypes")
	public List getFiltroSituacionConciliacionList() {
		return filtroSituacionConciliacionList;
	}

	@SuppressWarnings("rawtypes")
	public void setFiltroSituacionConciliacionList(List filtroSituacionConciliacionList) {
		this.filtroSituacionConciliacionList = filtroSituacionConciliacionList;
	}

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
	
	public BigDecimal getImPrecioUnitPres() {
		return imPrecioUnitPres;
	}

	public void setImPrecioUnitPres(BigDecimal imPrecioUnitPres) {
		this.imPrecioUnitPres = imPrecioUnitPres;
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

	public Integer getCantBilletes() {
		return cantBilletes;
	}

	public void setCantBilletes(Integer cantBilletes) {
		this.cantBilletes = cantBilletes;
	}

	public String getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(String tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

}
