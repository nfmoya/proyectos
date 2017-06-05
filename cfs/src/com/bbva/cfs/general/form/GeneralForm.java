package com.bbva.cfs.general.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

/**
 * Clase que contiene las listas que son genericas 
 * a todos los formularios de la aplicacion.
 * 
 * @author David
 */
public class GeneralForm extends  ValidatorForm {
	
	private static final long serialVersionUID = -5360531539196773190L;

	private List proveedorProductoList = new ArrayList();	
	
	private List habilitadoProductoList = new ArrayList();	
	
	private List cdSecSolServList = new ArrayList();	
	
	private List cdSecConServList = new ArrayList();
	
	private List cdSecConFactList = new ArrayList();	
	
	private List stProdImportPrestList = new ArrayList();
	
	private List stProdImportFactList = new ArrayList();
	
	private List stRemServObligList = new ArrayList();
	
	private List stRemFactObligList = new ArrayList();
	
	private List stAdmiteRemServList = new ArrayList();
	
	private List stAdmiteRemFactList = new ArrayList();
	
	private List stConcilSinValList = new ArrayList();
	
	private List stServSinConcilList = new ArrayList();
	
	private List stFactSinConcilList = new ArrayList();
	
	private List grupoProductoList = new ArrayList();
	
	private List uniValList = new ArrayList();
	
	private List filtroProveedorList = new ArrayList();
	
	private List filtroProductoList = new ArrayList();
	
	private List filtroGrupoList = new ArrayList();
	
	private List filtroHabilitadoList = new ArrayList();
	
	private List productosList = new ArrayList();
	
	private List proveedorList = new ArrayList();
	
	private List sectorList = new ArrayList();
	
	/** Grant => Permisos*/
	private String saveGrant = "N";	
	private String enableGrant = "N";	
	private String disableGrant = "N";	
	private String addGrant= "N";	
	private String editGrant = "N";	
	private String deleteGrant = "N";
	
	private String agregarGrant;
	private String modificarGrant;

	/**
	 * @return the proveedorProductoList
	 */
	public List getProveedorProductoList() {
		return proveedorProductoList;
	}

	/**
	 * @param proveedorProductoList the proveedorProductoList to set
	 */
	public void setProveedorProductoList(List proveedorProductoList) {
		this.proveedorProductoList = proveedorProductoList;
	}

	/**
	 * @return the habilitadoProductoList
	 */
	public List getHabilitadoProductoList() {
		return habilitadoProductoList;
	}

	/**
	 * @param habilitadoProductoList the habilitadoProductoList to set
	 */
	public void setHabilitadoProductoList(List habilitadoProductoList) {
		this.habilitadoProductoList = habilitadoProductoList;
	}

	/**
	 * @return the cdSecSolServList
	 */
	public List getCdSecSolServList() {
		return cdSecSolServList;
	}

	/**
	 * @param cdSecSolServList the cdSecSolServList to set
	 */
	public void setCdSecSolServList(List cdSecSolServList) {
		this.cdSecSolServList = cdSecSolServList;
	}

	/**
	 * @return the cdSecConServList
	 */
	public List getCdSecConServList() {
		return cdSecConServList;
	}

	/**
	 * @param cdSecConServList the cdSecConServList to set
	 */
	public void setCdSecConServList(List cdSecConServList) {
		this.cdSecConServList = cdSecConServList;
	}

	/**
	 * @return the cdSecConFactList
	 */
	public List getCdSecConFactList() {
		return cdSecConFactList;
	}

	/**
	 * @param cdSecConFactList the cdSecConFactList to set
	 */
	public void setCdSecConFactList(List cdSecConFactList) {
		this.cdSecConFactList = cdSecConFactList;
	}

	/**
	 * @return the stProdImportPrestList
	 */
	public List getStProdImportPrestList() {
		return stProdImportPrestList;
	}

	/**
	 * @param stProdImportPrestList the stProdImportPrestList to set
	 */
	public void setStProdImportPrestList(List stProdImportPrestList) {
		this.stProdImportPrestList = stProdImportPrestList;
	}

	/**
	 * @return the stProdImportFactList
	 */
	public List getStProdImportFactList() {
		return stProdImportFactList;
	}

	/**
	 * @param stProdImportFactList the stProdImportFactList to set
	 */
	public void setStProdImportFactList(List stProdImportFactList) {
		this.stProdImportFactList = stProdImportFactList;
	}

	/**
	 * @return the stRemServObligList
	 */
	public List getStRemServObligList() {
		return stRemServObligList;
	}

	/**
	 * @param stRemServObligList the stRemServObligList to set
	 */
	public void setStRemServObligList(List stRemServObligList) {
		this.stRemServObligList = stRemServObligList;
	}

	/**
	 * @return the stRemFactObligList
	 */
	public List getStRemFactObligList() {
		return stRemFactObligList;
	}

	/**
	 * @param stRemFactObligList the stRemFactObligList to set
	 */
	public void setStRemFactObligList(List stRemFactObligList) {
		this.stRemFactObligList = stRemFactObligList;
	}

	/**
	 * @return the stAdmiteRemServList
	 */
	public List getStAdmiteRemServList() {
		return stAdmiteRemServList;
	}

	/**
	 * @param stAdmiteRemServList the stAdmiteRemServList to set
	 */
	public void setStAdmiteRemServList(List stAdmiteRemServList) {
		this.stAdmiteRemServList = stAdmiteRemServList;
	}

	/**
	 * @return the stAdmiteRemFactList
	 */
	public List getStAdmiteRemFactList() {
		return stAdmiteRemFactList;
	}

	/**
	 * @param stAdmiteRemFactList the stAdmiteRemFactList to set
	 */
	public void setStAdmiteRemFactList(List stAdmiteRemFactList) {
		this.stAdmiteRemFactList = stAdmiteRemFactList;
	}

	/**
	 * @return the stConcilSinValList
	 */
	public List getStConcilSinValList() {
		return stConcilSinValList;
	}

	/**
	 * @param stConcilSinValList the stConcilSinValList to set
	 */
	public void setStConcilSinValList(List stConcilSinValList) {
		this.stConcilSinValList = stConcilSinValList;
	}

	/**
	 * @return the stServSinConcilList
	 */
	public List getStServSinConcilList() {
		return stServSinConcilList;
	}

	/**
	 * @param stServSinConcilList the stServSinConcilList to set
	 */
	public void setStServSinConcilList(List stServSinConcilList) {
		this.stServSinConcilList = stServSinConcilList;
	}

	/**
	 * @return the stFactSinConcilList
	 */
	public List getStFactSinConcilList() {
		return stFactSinConcilList;
	}

	/**
	 * @param stFactSinConcilList the stFactSinConcilList to set
	 */
	public void setStFactSinConcilList(List stFactSinConcilList) {
		this.stFactSinConcilList = stFactSinConcilList;
	}

	/**
	 * @return the grupoProductoList
	 */
	public List getGrupoProductoList() {
		return grupoProductoList;
	}

	/**
	 * @param grupoProductoList the grupoProductoList to set
	 */
	public void setGrupoProductoList(List grupoProductoList) {
		this.grupoProductoList = grupoProductoList;
	}

	/**
	 * @return the uniValList
	 */
	public List getUniValList() {
		return uniValList;
	}

	/**
	 * @param uniValList the uniValList to set
	 */
	public void setUniValList(List uniValList) {
		this.uniValList = uniValList;
	}

	/**
	 * @return the filtroProveedorList
	 */
	public List getFiltroProveedorList() {
		return filtroProveedorList;
	}

	/**
	 * @param filtroProveedorList the filtroProveedorList to set
	 */
	public void setFiltroProveedorList(List filtroProveedorList) {
		this.filtroProveedorList = filtroProveedorList;
	}

	/**
	 * @return the filtroProductoList
	 */
	public List getFiltroProductoList() {
		return filtroProductoList;
	}

	/**
	 * @param filtroProductoList the filtroProductoList to set
	 */
	public void setFiltroProductoList(List filtroProductoList) {
		this.filtroProductoList = filtroProductoList;
	}

	/**
	 * @return the filtroGrupoList
	 */
	public List getFiltroGrupoList() {
		return filtroGrupoList;
	}

	/**
	 * @param filtroGrupoList the filtroGrupoList to set
	 */
	public void setFiltroGrupoList(List filtroGrupoList) {
		this.filtroGrupoList = filtroGrupoList;
	}

	/**
	 * @return the filtroHabilitadoList
	 */
	public List getFiltroHabilitadoList() {
		return filtroHabilitadoList;
	}

	/**
	 * @param filtroHabilitadoList the filtroHabilitadoList to set
	 */
	public void setFiltroHabilitadoList(List filtroHabilitadoList) {
		this.filtroHabilitadoList = filtroHabilitadoList;
	}

	/**
	 * @return the saveGrant
	 */
	public String getSaveGrant() {
		return saveGrant;
	}

	/**
	 * @param saveGrant the saveGrant to set
	 */
	public void setSaveGrant(String saveGrant) {
		this.saveGrant = saveGrant;
	}

	/**
	 * @return the enableGrant
	 */
	public String getEnableGrant() {
		return enableGrant;
	}

	/**
	 * @param enableGrant the enableGrant to set
	 */
	public void setEnableGrant(String enableGrant) {
		this.enableGrant = enableGrant;
	}

	/**
	 * @return the disableGrant
	 */
	public String getDisableGrant() {
		return disableGrant;
	}

	/**
	 * @param disableGrant the disableGrant to set
	 */
	public void setDisableGrant(String disableGrant) {
		this.disableGrant = disableGrant;
	}

	/**
	 * @return the addGrant
	 */
	public String getAddGrant() {
		return addGrant;
	}

	/**
	 * @param addGrant the addGrant to set
	 */
	public void setAddGrant(String addGrant) {
		this.addGrant = addGrant;
	}

	/**
	 * @return the editGrant
	 */
	public String getEditGrant() {
		return editGrant;
	}

	/**
	 * @param editGrant the editGrant to set
	 */
	public void setEditGrant(String editGrant) {
		this.editGrant = editGrant;
	}

	/**
	 * @return the deleteGrant
	 */
	public String getDeleteGrant() {
		return deleteGrant;
	}

	/**
	 * @param deleteGrant the deleteGrant to set
	 */
	public void setDeleteGrant(String deleteGrant) {
		this.deleteGrant = deleteGrant;
	}

	/**
	 * @return the agregarGrant
	 */
	public String getAgregarGrant() {
		return agregarGrant;
	}

	/**
	 * @param agregarGrant the agregarGrant to set
	 */
	public void setAgregarGrant(String agregarGrant) {
		this.agregarGrant = agregarGrant;
	}

	/**
	 * @return the modificarGrant
	 */
	public String getModificarGrant() {
		return modificarGrant;
	}

	/**
	 * @param modificarGrant the modificarGrant to set
	 */
	public void setModificarGrant(String modificarGrant) {
		this.modificarGrant = modificarGrant;
	}

	/**
	 * @return the productosList
	 */
	public List getProductosList() {
		return productosList;
	}

	/**
	 * @param productosList the productosList to set
	 */
	public void setProductosList(List productosList) {
		this.productosList = productosList;
	}

	/**
	 * @return the proveedorList
	 */
	public List getProveedorList() {
		return proveedorList;
	}

	/**
	 * @param proveedorList the proveedorList to set
	 */
	public void setProveedorList(List proveedorList) {
		this.proveedorList = proveedorList;
	}

	/**
	 * @return the sectorList
	 */
	public List getSectorList() {
		return sectorList;
	}

	/**
	 * @param sectorList the sectorList to set
	 */
	public void setSectorList(List sectorList) {
		this.sectorList = sectorList;
	}
}
