package com.bbva.cfs.nomedibles.form;
 
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
	@SuppressWarnings("rawtypes")
	private List filtroSectorList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filtroPeriodoList = new ArrayList();
	
	private String sector;
	private String cdPeriodoFact;
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
    private String chkDif;
    private String tpSolucion;
	
	private String situacionConciliacion;	
    private String cdConciliacion;
	@SuppressWarnings("rawtypes")
	private List filtroConciliacionList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filtroSituacionConciliacionList = new ArrayList();

	/** Grant => Permisos*/
	private String saveGrant = "N";	
	private String differGrant = "N";	
	private String disableGrant = "N";	
	private String approveGrant= "N";	
	private String exportGrant = "N";	
	private String printGrant = "N";
	
	private String callBackFunction;
	
	/** ========================*/

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCdProveedor() {
		return cdProveedor;
	}

	public void setCdProveedor(String cdProveedor) {
		this.cdProveedor = cdProveedor;
	}

	public List getFiltroProveedorList() {
		return filtroProveedorList;
	}

	public void setFiltroProveedorList(List filtroProveedorList) {
		this.filtroProveedorList = filtroProveedorList;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public List getFiltroSectorList() {
		return filtroSectorList;
	}

	public void setFiltroSectorList(List filtroSectorList) {
		this.filtroSectorList = filtroSectorList;
	}

	public String getCdPeriodoFact() {
		return cdPeriodoFact;
	}

	public void setCdPeriodoFact(String cdPeriodoFact) {
		this.cdPeriodoFact = cdPeriodoFact;
	}

	public List getFiltroPeriodoList() {
		return filtroPeriodoList;
	}

	public void setFiltroPeriodoList(List filtroPeriodoList) {
		this.filtroPeriodoList = filtroPeriodoList;
	}

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

	public List getFiltroConciliacionList() {
		return filtroConciliacionList;
	}

	public void setFiltroConciliacionList(List filtroConciliacionList) {
		this.filtroConciliacionList = filtroConciliacionList;
	}

	public List getFiltroSituacionConciliacionList() {
		return filtroSituacionConciliacionList;
	}

	public void setFiltroSituacionConciliacionList(
			List filtroSituacionConciliacionList) {
		this.filtroSituacionConciliacionList = filtroSituacionConciliacionList;
	}

	public String getSaveGrant() {
		return saveGrant;
	}

	public void setSaveGrant(String saveGrant) {
		this.saveGrant = saveGrant;
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

	public String getApproveGrant() {
		return approveGrant;
	}

	public void setApproveGrant(String approveGrant) {
		this.approveGrant = approveGrant;
	}

	public String getExportGrant() {
		return exportGrant;
	}

	public void setExportGrant(String exportGrant) {
		this.exportGrant = exportGrant;
	}

	public String getPrintGrant() {
		return printGrant;
	}

	public void setPrintGrant(String printGrant) {
		this.printGrant = printGrant;
	}

	public String getCallBackFunction() {
		return callBackFunction;
	}

	public void setCallBackFunction(String callBackFunction) {
		this.callBackFunction = callBackFunction;
	}

	public String getChkDif() {
		return chkDif;
	}

	public void setChkDif(String chkDif) {
		this.chkDif = chkDif;
	}

	public String getTpSolucion() {
		return tpSolucion;
	}

	public void setTpSolucion(String tpSolucion) {
		this.tpSolucion = tpSolucion;
	}
}
