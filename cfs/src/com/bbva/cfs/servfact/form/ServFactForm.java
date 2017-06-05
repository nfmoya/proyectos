package com.bbva.cfs.servfact.form;
 
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

public class ServFactForm extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cdSector;
	private String nbSector;
	private String nbSectorAbrev;
	private String cdSectorAlt;
	private String stHabilitado;
	private String nbUniVal;
	private String sector;	

	@SuppressWarnings("rawtypes")
	private List habilitadoList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List filtroSectorList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List filtroProveedorList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filtroProductoList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filterGroupList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filterCompList = new ArrayList();

	

	private String selectedComp = "";
	private String selectedProducto = "";
	private String selectedProveedor = "";
	private String selectedSector = "";
	private String selectedGrupo = "";
	private String habilitadoSel = "";
	
	private String cdComprobante = "";
	private String remitoDesde = "";
	private String remitoHasta = "";
	private String cdLote = "";
	private String fhDesde = "";
	private String fhHasta = "";

	@SuppressWarnings("rawtypes")
	private List pFactDesdeList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List pFactHastaList = new ArrayList();
	

	@SuppressWarnings("rawtypes")
	private List filtroGrupoList1 = new ArrayList();

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

	@SuppressWarnings("rawtypes")
	public List getHabilitadoList() {
		return habilitadoList;
	}

	@SuppressWarnings("rawtypes")
	public void setHabilitadoList(List habilitadoList) {
		this.habilitadoList = habilitadoList;
	}

	public List getFiltroSectorList() {
		return filtroSectorList;
	}

	public void setFiltroSectorList(List filtroSectorList) {
		this.filtroSectorList = filtroSectorList;
	}

	public void setFiltroProveedorList(List filtroProveedorList2) {
		this.filtroProveedorList = filtroProveedorList2;
		
	}
	public List getFiltroProveedorList() {
		return filtroProveedorList;
	}

	public List getFiltroProductoList() {
		return filtroProductoList;
	}

	public void setFiltroProductoList(List filtroProductoList) {
		this.filtroProductoList = filtroProductoList;
	}

	public List getFiltroGrupoList1() {
		return filtroGrupoList1;
	}

	public void setFiltroGrupoList1(List filtroGrupoList) {
		this.filtroGrupoList1 = filtroGrupoList;
	}
	
	public List getFilterGroupList() {
		return filterGroupList;
	}

	public void setFilterGroupList(List filterGroupList) {
		this.filterGroupList = filterGroupList;
	}

	public String getCdLote() {
		return cdLote;
	}

	public void setCdLote(String cdLote) {
		this.cdLote = cdLote;
	}

	public String getFhDesde() {
		return fhDesde;
	}

	public void setFhDesde(String fhDesde) {
		this.fhDesde = fhDesde;
	}

	public String getFhHasta() {
		return fhHasta;
	}

	public void setFhHasta(String fhHasta) {
		this.fhHasta = fhHasta;
	}

	public List getpFactDesdeList() {
		return pFactDesdeList;
	}

	public void setpFactDesdeList(List pFactDesdeList) {
		this.pFactDesdeList = pFactDesdeList;
	}

	public List getpFactHastaList() {
		return pFactHastaList;
	}

	public void setpFactHastaList(List pFactHastaList) {
		this.pFactHastaList = pFactHastaList;
	}

	public String getSelectedProducto() {
		return selectedProducto;
	}

	public void setSelectedProducto(String selectedProducto) {
		this.selectedProducto = selectedProducto;
	}

	public String getSelectedProveedor() {
		return selectedProveedor;
	}

	public void setSelectedProveedor(String selectedProveedor) {
		this.selectedProveedor = selectedProveedor;
	}

	public String getSelectedSector() {
		return selectedSector;
	}

	public void setSelectedSector(String selectedSector) {
		this.selectedSector = selectedSector;
	}

	public String getSelectedGrupo() {
		return selectedGrupo;
	}

	public void setSelectedGrupo(String selectedGrupo) {
		this.selectedGrupo = selectedGrupo;
	}

	public String getHabilitadoSel() {
		return habilitadoSel;
	}

	public void setHabilitadoSel(String habilitadoSel) {
		this.habilitadoSel = habilitadoSel;
	}

	public String getCdComprobante() {
		return cdComprobante;
	}

	public void setCdComprobante(String cdComprobante) {
		this.cdComprobante = cdComprobante;
	}

	public String getRemitoDesde() {
		return remitoDesde;
	}

	public void setRemitoDesde(String remitoDesde) {
		this.remitoDesde = remitoDesde;
	}

	public String getRemitoHasta() {
		return remitoHasta;
	}

	public void setRemitoHasta(String remitoHasta) {
		this.remitoHasta = remitoHasta;
	}
	
	public String getNbUniVal() {
		return nbUniVal;
	}

	public void setNbUniVal(String nbUniVal) {
		this.nbUniVal = nbUniVal;
	}
	
	public List getFilterCompList() {
		return filterCompList;
	}

	public void setFilterCompList(List filterCompList) {
		this.filterCompList = filterCompList;
	}

	public String getSelectedComp() {
		return selectedComp;
	}

	public void setSelectedComp(String selectedComp) {
		this.selectedComp = selectedComp;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	
}
