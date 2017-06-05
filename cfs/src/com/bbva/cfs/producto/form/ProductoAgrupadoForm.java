package com.bbva.cfs.producto.form;
 
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

public class ProductoAgrupadoForm extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cdProveedor;
	private String cdProductoOrig;
	private String cdProducto;
	private String desItem;
	private String desGrupo;
	private String stHabilitado;
	
	@SuppressWarnings("rawtypes")
	private List proveedorProductoList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List originalList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List productoList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List habilitadoProductoList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filtroProveedorList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filtroProductoOrigList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filtroProductoList = new ArrayList();
	
	/** Grant => Permisos*/
	private String saveGrant = "N";	
	private String enableGrant = "N";	
	private String disableGrant = "N";	
	private String addGrant= "N";	
	private String editGrant = "N";	
	private String deleteGrant = "N";
	
	private String agregarGrant;
	private String modificarGrant;
	
	private String callBackFunction;
		
	@SuppressWarnings("rawtypes")
	private List filtroHabilitadoList = new ArrayList();
	
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

	public String getCdProductoOrig() {
		return cdProductoOrig;
	}

	public void setCdProductoOrig(String cdProductoOrig) {
		this.cdProductoOrig = cdProductoOrig;
	}

	public String getCdProducto() {
		return cdProducto;
	}

	public void setCdProducto(String cdProducto) {
		this.cdProducto = cdProducto;
	}

	public String getDesItem() {
		return desItem;
	}

	public void setDesItem(String desItem) {
		this.desItem = desItem;
	}

	public String getDesGrupo() {
		return desGrupo;
	}

	public void setDesGrupo(String desGrupo) {
		this.desGrupo = desGrupo;
	}
	
	public String getStHabilitado() {
		return stHabilitado;
	}
	
	public void setStHabilitado(String stHabilitado) {
		this.stHabilitado = stHabilitado;
	}

	@SuppressWarnings("rawtypes")
	public List getProveedorProductoList() {
		return proveedorProductoList;
	}

	@SuppressWarnings("rawtypes")
	public void setProveedorProductoList(List proveedorProductoList) {
		this.proveedorProductoList = proveedorProductoList;
	}

	@SuppressWarnings("rawtypes")
	public List getOriginalList() {
		return originalList;
	}

	@SuppressWarnings("rawtypes")
	public void setOriginalList(List originalList) {
		this.originalList = originalList;
	}

	@SuppressWarnings("rawtypes")
	public List getProductoList() {
		return productoList;
	}

	@SuppressWarnings("rawtypes")
	public void setProductoList(List productoList) {
		this.productoList = productoList;
	}

	@SuppressWarnings("rawtypes")
	public List getHabilitadoProductoList() {
		return habilitadoProductoList;
	}

	@SuppressWarnings("rawtypes")
	public void setHabilitadoProductoList(List habilitadoProductoList) {
		this.habilitadoProductoList = habilitadoProductoList;
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
	public List getFiltroProductoOrigList() {
		return filtroProductoOrigList;
	}

	@SuppressWarnings("rawtypes")
	public void setFiltroProductoOrigList(List filtroProductoOrigList) {
		this.filtroProductoOrigList = filtroProductoOrigList;
	}

	@SuppressWarnings("rawtypes")
	public List getFiltroHabilitadoList() {
		return filtroHabilitadoList;
	}

	@SuppressWarnings("rawtypes")
	public void setFiltroHabilitadoList(List filtroHabilitadoList) {
		this.filtroHabilitadoList = filtroHabilitadoList;
	}
	
}
