package com.bbva.cfs.producto.form;
 
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

public class ProductoForm extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cdProveedor;
	private String cdProducto;
	private String nbProducto;
	private String nbProductoCorto;
	private String cdGrupoProducto;
	private String cdUniVal;
	private String cdSecSolServ;
	private String cdSecConServ;
	private String cdSecConFact;
	private String stProdImportPrest;
	private String stProdImportFact;
	private String stRemServOblig;
	private String stRemFactOblig;
	private String stAdmiteRemServ;
	private String stAdmiteRemFact;
	private String nbAtributoRef1;
	private String nbAtributoRef2;
	private String stConcilSinVal;
	private String stServSinConcil;
	private String stFactSinConcil;
	private Long nuDiaEmiFDesde;
	private Long nuDiaEmiFHasta;
	private Long nuDiaCierreFDesde;
	private Long nuDiaCierreFHasta;
	private String nbAtributoAdic1;
	private String nbAtributoAdic2;
	private String nbAtributoAdic3;
	private String nbAtributoAdic4;
	private String nbAtributoAdic5;
	private String stHabilitado;
	@SuppressWarnings("rawtypes")
	private List proveedorProductoList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List nbAtributoAdic1List = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List habilitadoProductoList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List cdSecSolServList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List cdSecConServList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List cdSecConFactList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List stProdImportPrestList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List stProdImportFactList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List stRemServObligList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List stRemFactObligList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List stAdmiteRemServList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List stAdmiteRemFactList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List stConcilSinValList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List stServSinConcilList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List stFactSinConcilList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List grupoProductoList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List uniValList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filtroProveedorList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filtroProductoList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filtroGrupoList = new ArrayList();
	
	@SuppressWarnings("rawtypes")
	private List monedaList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List tipValList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List porcVarMaxList = new ArrayList();
	
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
	
	private String cdTipVal;
	private String descTipVal;
	private String cdMoneda;
	private Integer nuPorcVarMax;
	
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

	public String getCdProducto() {
		return cdProducto;
	}

	public void setCdProducto(String cdProducto) {
		this.cdProducto = cdProducto;
	}

	public String getNbProducto() {
		return nbProducto;
	}

	public void setNbProducto(String nbProducto) {
		this.nbProducto = nbProducto;
	}

	public String getNbProductoCorto() {
		return nbProductoCorto;
	}

	public void setNbProductoCorto(String nbProductoCorto) {
		this.nbProductoCorto = nbProductoCorto;
	}

	public String getCdGrupoProducto() {
		return cdGrupoProducto;
	}

	public void setCdGrupoProducto(String cdGrupoProducto) {
		this.cdGrupoProducto = cdGrupoProducto;
	}

	public String getCdUniVal() {
		return cdUniVal;
	}

	public void setCdUniVal(String cdUniVal) {
		this.cdUniVal = cdUniVal;
	}

	public String getCdSecSolServ() {
		return cdSecSolServ;
	}

	public void setCdSecSolServ(String cdSecSolServ) {
		this.cdSecSolServ = cdSecSolServ;
	}

	public String getCdSecConServ() {
		return cdSecConServ;
	}

	public void setCdSecConServ(String cdSecConServ) {
		this.cdSecConServ = cdSecConServ;
	}

	public String getCdSecConFact() {
		return cdSecConFact;
	}

	public void setCdSecConFact(String cdSecConFact) {
		this.cdSecConFact = cdSecConFact;
	}

	public String getStProdImportPrest() {
		return stProdImportPrest;
	}

	public void setStProdImportPrest(String stProdImportPrest) {
		this.stProdImportPrest = stProdImportPrest;
	}

	public String getStProdImportFact() {
		return stProdImportFact;
	}

	public void setStProdImportFact(String stProdImportFact) {
		this.stProdImportFact = stProdImportFact;
	}

	public String getStRemServOblig() {
		return stRemServOblig;
	}

	public void setStRemServOblig(String stRemServOblig) {
		this.stRemServOblig = stRemServOblig;
	}

	public String getStRemFactOblig() {
		return stRemFactOblig;
	}

	public void setStRemFactOblig(String stRemFactOblig) {
		this.stRemFactOblig = stRemFactOblig;
	}

	public String getStAdmiteRemServ() {
		return stAdmiteRemServ;
	}

	public void setStAdmiteRemServ(String stAdmiteRemServ) {
		this.stAdmiteRemServ = stAdmiteRemServ;
	}

	public String getStAdmiteRemFact() {
		return stAdmiteRemFact;
	}

	public void setStAdmiteRemFact(String stAdmiteRemFact) {
		this.stAdmiteRemFact = stAdmiteRemFact;
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

	public String getStConcilSinVal() {
		return stConcilSinVal;
	}

	public void setStConcilSinVal(String stConcilSinVal) {
		this.stConcilSinVal = stConcilSinVal;
	}

	public String getStServSinConcil() {
		return stServSinConcil;
	}

	public void setStServSinConcil(String stServSinConcil) {
		this.stServSinConcil = stServSinConcil;
	}

	public String getStFactSinConcil() {
		return stFactSinConcil;
	}

	public void setStFactSinConcil(String stFactSinConcil) {
		this.stFactSinConcil = stFactSinConcil;
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

	public String getNbAtributoAdic1() {
		return nbAtributoAdic1;
	}

	public void setNbAtributoAdic1(String nbAtributoAdic1) {
		this.nbAtributoAdic1 = nbAtributoAdic1;
	}

	public String getNbAtributoAdic2() {
		return nbAtributoAdic2;
	}

	public void setNbAtributoAdic2(String nbAtributoAdic2) {
		this.nbAtributoAdic2 = nbAtributoAdic2;
	}

	public String getNbAtributoAdic3() {
		return nbAtributoAdic3;
	}

	public void setNbAtributoAdic3(String nbAtributoAdic3) {
		this.nbAtributoAdic3 = nbAtributoAdic3;
	}

	public String getNbAtributoAdic4() {
		return nbAtributoAdic4;
	}

	public void setNbAtributoAdic4(String nbAtributoAdic4) {
		this.nbAtributoAdic4 = nbAtributoAdic4;
	}

	public String getNbAtributoAdic5() {
		return nbAtributoAdic5;
	}

	public void setNbAtributoAdic5(String nbAtributoAdic5) {
		this.nbAtributoAdic5 = nbAtributoAdic5;
	}

	public String getStHabilitado() {
		return stHabilitado;
	}

	public void setStHabilitado(String stHabilitado) {
		this.stHabilitado = stHabilitado;
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
	public List getProveedorProductoList() {
		return proveedorProductoList;
	}

	@SuppressWarnings("rawtypes")
	public void setProveedorProductoList(List proveedorProductoList) {
		this.proveedorProductoList = proveedorProductoList;
	}

	public List getNbAtributoAdic1List() {
		return nbAtributoAdic1List;
	}

	public void setNbAtributoAdic1List(List nbAtributoAdic1List) {
		this.nbAtributoAdic1List = nbAtributoAdic1List;
	}

	@SuppressWarnings("rawtypes")
	public List getStProdImportPrestList() {
		return stProdImportPrestList;
	}

	@SuppressWarnings("rawtypes")
	public void setStProdImportPrestList(List stProdImportPrestList) {
		this.stProdImportPrestList = stProdImportPrestList;
	}

	@SuppressWarnings("rawtypes")
	public List getStProdImportFactList() {
		return stProdImportFactList;
	}

	@SuppressWarnings("rawtypes")
	public void setStProdImportFactList(List stProdImportFactList) {
		this.stProdImportFactList = stProdImportFactList;
	}

	@SuppressWarnings("rawtypes")
	public List getStRemServObligList() {
		return stRemServObligList;
	}

	@SuppressWarnings("rawtypes")
	public void setStRemServObligList(List stRemServObligList) {
		this.stRemServObligList = stRemServObligList;
	}

	@SuppressWarnings("rawtypes")
	public List getStRemFactObligList() {
		return stRemFactObligList;
	}

	@SuppressWarnings("rawtypes")
	public void setStRemFactObligList(List stRemFactObligList) {
		this.stRemFactObligList = stRemFactObligList;
	}

	@SuppressWarnings("rawtypes")
	public List getStAdmiteRemServList() {
		return stAdmiteRemServList;
	}

	@SuppressWarnings("rawtypes")
	public void setStAdmiteRemServList(List stAdmiteRemServList) {
		this.stAdmiteRemServList = stAdmiteRemServList;
	}

	@SuppressWarnings("rawtypes")
	public List getStAdmiteRemFactList() {
		return stAdmiteRemFactList;
	}

	@SuppressWarnings("rawtypes")
	public void setStAdmiteRemFactList(List stAdmiteRemFactList) {
		this.stAdmiteRemFactList = stAdmiteRemFactList;
	}

	@SuppressWarnings("rawtypes")
	public List getStConcilSinValList() {
		return stConcilSinValList;
	}

	@SuppressWarnings("rawtypes")
	public void setStConcilSinValList(List stConcilSinValList) {
		this.stConcilSinValList = stConcilSinValList;
	}

	@SuppressWarnings("rawtypes")
	public List getStServSinConcilList() {
		return stServSinConcilList;
	}

	@SuppressWarnings("rawtypes")
	public void setStServSinConcilList(List stServSinConcilList) {
		this.stServSinConcilList = stServSinConcilList;
	}

	@SuppressWarnings("rawtypes")
	public List getStFactSinConcilList() {
		return stFactSinConcilList;
	}

	@SuppressWarnings("rawtypes")
	public void setStFactSinConcilList(List stFactSinConcilList) {
		this.stFactSinConcilList = stFactSinConcilList;
	}

	@SuppressWarnings("rawtypes")
	public List getCdSecSolServList() {
		return cdSecSolServList;
	}

	@SuppressWarnings("rawtypes")
	public void setCdSecSolServList(List cdSecSolServList) {
		this.cdSecSolServList = cdSecSolServList;
	}

	@SuppressWarnings("rawtypes")
	public List getCdSecConServList() {
		return cdSecConServList;
	}

	@SuppressWarnings("rawtypes")
	public void setCdSecConServList(List cdSecConServList) {
		this.cdSecConServList = cdSecConServList;
	}

	@SuppressWarnings("rawtypes")
	public List getCdSecConFactList() {
		return cdSecConFactList;
	}

	@SuppressWarnings("rawtypes")
	public void setCdSecConFactList(List cdSecConFactList) {
		this.cdSecConFactList = cdSecConFactList;
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
	public List getGrupoProductoList() {
		return grupoProductoList;
	}

	@SuppressWarnings("rawtypes")
	public void setGrupoProductoList(List grupoProductoList) {
		this.grupoProductoList = grupoProductoList;
	}

	@SuppressWarnings("rawtypes")
	public List getUniValList() {
		return uniValList;
	}

	@SuppressWarnings("rawtypes")
	public void setUniValList(List uniValList) {
		this.uniValList = uniValList;
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
	public List getFiltroGrupoList() {
		return filtroGrupoList;
	}

	@SuppressWarnings("rawtypes")
	public void setFiltroGrupoList(List filtroGrupoList) {
		this.filtroGrupoList = filtroGrupoList;
	}

	@SuppressWarnings("rawtypes")
	public List getFiltroHabilitadoList() {
		return filtroHabilitadoList;
	}

	@SuppressWarnings("rawtypes")
	public void setFiltroHabilitadoList(List filtroHabilitadoList) {
		this.filtroHabilitadoList = filtroHabilitadoList;
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

	public Integer getNuPorcVarMax() {
		return nuPorcVarMax;
	}

	public void setNuPorcVarMax(Integer nuPorcVarMax) {
		this.nuPorcVarMax = nuPorcVarMax;
	}

	@SuppressWarnings("rawtypes")
	public List getMonedaList() {
		return monedaList;
	}

	@SuppressWarnings("rawtypes")
	public void setMonedaList(List monedaList) {
		this.monedaList = monedaList;
	}

	public List getTipValList() {
		return tipValList;
	}

	public void setTipValList(List tipValList) {
		this.tipValList = tipValList;
	}

	public List getPorcVarMaxList() {
		return porcVarMaxList;
	}

	public void setPorcVarMaxList(List porcVarMaxList) {
		this.porcVarMaxList = porcVarMaxList;
	}

	public String getDescTipVal() {
		return descTipVal;
	}

	public void setDescTipVal(String descTipVal) {
		this.descTipVal = descTipVal;
	}

	

	
	/** ========================*/

	
}
