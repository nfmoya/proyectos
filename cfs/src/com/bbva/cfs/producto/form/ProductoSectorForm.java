package com.bbva.cfs.producto.form;
 
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

public class ProductoSectorForm extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cdProveedor; 
	private String cdProducto;
	private String cdSector;
	private String nbSector;
    private String stHabilitado;
	
	@SuppressWarnings("rawtypes")
	private List habilitadoProductoSectorList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List proveedorProductoSectorList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List productoSectorList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List sectorList = new ArrayList();	
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

	public String getStHabilitado() {
		return stHabilitado;
	}

	public void setStHabilitado(String stHabilitado) {
		this.stHabilitado = stHabilitado;
	}

	@SuppressWarnings("rawtypes")
	public List getHabilitadoProductoSectorList() {
		return habilitadoProductoSectorList;
	}

	@SuppressWarnings("rawtypes")
	public void setHabilitadoProductoSectorList(List habilitadoProductoSectorList) {
		this.habilitadoProductoSectorList = habilitadoProductoSectorList;
	}
	
	@SuppressWarnings("rawtypes")
	public List getProveedorProductoSectorList() {
		return proveedorProductoSectorList;
	}

	@SuppressWarnings("rawtypes")
	public void setProveedorProductoSectorList(List proveedorProductoSectorList) {
		this.proveedorProductoSectorList = proveedorProductoSectorList;
	}

	@SuppressWarnings("rawtypes")
	public List getProductoSectorList() {
		return productoSectorList;
	}

	@SuppressWarnings("rawtypes")
	public void setProductoSectorList(List productoSectorList) {
		this.productoSectorList = productoSectorList;
	}

	@SuppressWarnings("rawtypes")
	public List getSectorList() {
		return sectorList;
	}

	@SuppressWarnings("rawtypes")
	public void setSectorList(List sectorList) {
		this.sectorList = sectorList;
	}

	@SuppressWarnings("rawtypes")
	public List getFiltroProveedorList() {
		return filtroProveedorList;
	}

	@SuppressWarnings("rawtypes")
	public void setFiltroProveedorList(List filtroProveedorList) {
		this.filtroProveedorList = filtroProveedorList;
	}

	public String getNbSector() {
		return nbSector;
	}

	public void setNbSector(String nbSector) {
		this.nbSector = nbSector;
	}
	
	/** ========================*/
	
}
