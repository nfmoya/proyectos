package com.bbva.cfs.proveedor.form;
 
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

public class ProveedorTipoCambioForm extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cdProveedor; 
	private String cdMoneda;
	private String nbMoneda;
	private String cdPeriodoFact; 
    private String nuDias;
    private String cdCotizacion;    
    private String stHabilitado;
	
	@SuppressWarnings("rawtypes")
	private List cotizacionTipoCambioList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List habilitadoTipoCambioList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List proveedorTipoCambioList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List periodoTipoCambioList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List monedaTipoCambioList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List filtroProveedorList = new ArrayList();	
	
	/** Grant => Permisos*/
	private String saveGrant = "N";	
	private String enableGrant = "N";	
	private String disableGrant = "N";	
	private String addGrant = "N";	
	private String editGrant = "N";	
	private String deleteGrant = "N";
	
	private String agregarGrant;
	private String modificarGrant;
	
	private String callBackFunction;
	
	/** ========================*/

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getSaveGrant() {
		return saveGrant;
	}

	public void setSaveGrant(String saveGrant) {
		this.saveGrant = saveGrant;
	}
	
	public String getEnableGrant() {
		return enableGrant;
	}

	public void setEnableGrant(String enableGrant) {
		this.enableGrant = enableGrant;
	}

	public String getDisableGrant() {
		return disableGrant;
	}

	public void setDisableGrant(String disableGrant) {
		this.disableGrant = disableGrant;
	}

	public String getAddGrant() {
		return addGrant;
	}

	public void setAddGrant(String addGrant) {
		this.addGrant = addGrant;
	}

	public String getEditGrant() {
		return editGrant;
	}

	public void setEditGrant(String editGrant) {
		this.editGrant = editGrant;
	}

	public String getDeleteGrant() {
		return deleteGrant;
	}

	public void setDeleteGrant(String deleteGrant) {
		this.deleteGrant = deleteGrant;
	}

	public String getCallBackFunction() {
		return callBackFunction;
	}

	public void setCallBackFunction(String callBackFunction) {
		this.callBackFunction = callBackFunction;
	}

	public String getAgregarGrant() {
		return agregarGrant;
	}

	public void setAgregarGrant(String agregarGrant) {
		this.agregarGrant = agregarGrant;
	}

	public String getModificarGrant() {
		return modificarGrant;
	}

	public void setModificarGrant(String modificarGrant) {
		this.modificarGrant = modificarGrant;
	}

	public String getCdProveedor() {
		return cdProveedor;
	}

	public void setCdProveedor(String cdProveedor) {
		this.cdProveedor = cdProveedor;
	}

	public String getCdMoneda() {
		return cdMoneda;
	}

	public void setCdMoneda(String cdMoneda) {
		this.cdMoneda = cdMoneda;
	}

	public String getNbMoneda() {
		return nbMoneda;
	}

	public void setNbMoneda(String nbMoneda) {
		this.nbMoneda = nbMoneda;
	}

	public String getCdPeriodoFact() {
		return cdPeriodoFact;
	}

	public void setCdPeriodoFact(String cdPeriodoFact) {
		this.cdPeriodoFact = cdPeriodoFact;
	}

	public String getNuDias() {
		return nuDias;
	}

	public void setNuDias(String nuDias) {
		this.nuDias = nuDias;
	}

	public String getCdCotizacion() {
		return cdCotizacion;
	}

	public void setCdCotizacion(String cdCotizacion) {
		this.cdCotizacion = cdCotizacion;
	}

	public String getStHabilitado() {
		return stHabilitado;
	}

	public void setStHabilitado(String stHabilitado) {
		this.stHabilitado = stHabilitado;
	}
	
	@SuppressWarnings("rawtypes")
	public List getCotizacionTipoCambioList() {
		return cotizacionTipoCambioList;
	}
	
	@SuppressWarnings("rawtypes")
	public void setCotizacionTipoCambioList(List cotizacionTipoCambioList) {
		this.cotizacionTipoCambioList = cotizacionTipoCambioList;
	}

	@SuppressWarnings("rawtypes")
	public List getHabilitadoTipoCambioList() {
		return habilitadoTipoCambioList;
	}

	@SuppressWarnings("rawtypes")
	public void setHabilitadoTipoCambioList(List habilitadoTipoCambioList) {
		this.habilitadoTipoCambioList = habilitadoTipoCambioList;
	}
	
	@SuppressWarnings("rawtypes")
	public List getProveedorTipoCambioList() {
		return proveedorTipoCambioList;
	}

	@SuppressWarnings("rawtypes")
	public void setProveedorTipoCambioList(List proveedorTipoCambioList) {
		this.proveedorTipoCambioList = proveedorTipoCambioList;
	}

	@SuppressWarnings("rawtypes")
	public List getPeriodoTipoCambioList() {
		return periodoTipoCambioList;
	}

	@SuppressWarnings("rawtypes")
	public void setPeriodoTipoCambioList(List periodoTipoCambioList) {
		this.periodoTipoCambioList = periodoTipoCambioList;
	}

	@SuppressWarnings("rawtypes")	
	public List getMonedaTipoCambioList() {
		return monedaTipoCambioList;
	}

	@SuppressWarnings("rawtypes")	
	public void setMonedaTipoCambioList(List monedaTipoCambioList) {
		this.monedaTipoCambioList = monedaTipoCambioList;
	}

	@SuppressWarnings("rawtypes")
	public List getFiltroProveedorList() {
		return filtroProveedorList;
	}

	@SuppressWarnings("rawtypes")
	public void setFiltroProveedorList(List filtroProveedorList) {
		this.filtroProveedorList = filtroProveedorList;
	}

	/** ========================*/
	
}
