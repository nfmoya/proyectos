package com.bbva.cfs.servprest.form;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

public class ServPrestForm extends ValidatorForm {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cdSector;
	private String nbSector;
	private String nbSectorAbrev;
	private String cdSectorAlt;
	private String stLoteDet;
	private String sector;
	
	@SuppressWarnings("rawtypes")
	private List estadoList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filtroSectorList = new ArrayList();	
	@SuppressWarnings("rawtypes")
	private List filtroProveedorList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filtroProductoList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filterGroupList = new ArrayList();

	private String selectedProducto = "0";
	private String selectedProveedor = "0";
	private String selectedSector = "0";
	private String selectedGrupo = "0";
	private String selectedEstado = "";
	
	private String cdComprobante = "0";
	private String remitoDesde = "";
	private String remitoHasta = "";
	private String cdLote = "";
	private String fhDesde = "";
	private String fhHasta = "";

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

	private String cdGrupoProd;
	private String dsGrupoProd;
	private String cdProd;
	private String dsProd;
	
//	Mes1
	private Long ctServPrestMes1;
	private BigDecimal valServPrestMes1;
	private Long ctRemitosMes1;
	private Long ctRemitosAConcilMes1;
//	Mes2
	private Long ctServPrestMes2;
	private BigDecimal valServPrestMes2;
	private Long ctRemitosMes2;
	private Long ctRemitosAConcilMes2;
//	Mes3
	private Long ctServPrestMes3;
	private BigDecimal valServPrestMes3;
	private Long ctRemitosMes3;
	private Long ctRemitosAConcilMes3;
//	Mes4
	private Long ctServPrestMes4;
	private BigDecimal valServPrestMes4;
	private Long ctRemitosMes4;
	private Long ctRemitosAConcilMes4;
//	Mes5
	private Long ctServPrestMes5;
	private BigDecimal valServPrestMes5;
	private Long ctRemitosMes5;
	private Long ctRemitosAConcilMes5;
//	Mes6
	private Long ctServPrestMes6;
	private BigDecimal valServPrestMes6;
	private Long ctRemitosMes6;
	private Long ctRemitosAConcilMes6;
//	Mes7
	private Long ctServPrestMes7;
	private BigDecimal valServPrestMes7;
	private Long ctRemitosMes7;
	private Long ctRemitosAConcilMes7;
//	Mes8
	private Long ctServPrestMes8;
	private BigDecimal valServPrestMes8;
	private Long ctRemitosMes8;
	private Long ctRemitosAConcilMes8;
//	Mes9
	private Long ctServPrestMes9;
	private BigDecimal valServPrestMes9;
	private Long ctRemitosMes9;
	private Long ctRemitosAConcilMes9;
//	Mes10
	private Long ctServPrestMes10;
	private BigDecimal valServPrestMes10;
	private Long ctRemitosMes10;
	private Long ctRemitosAConcilMes10;
//	Mes11
	private Long ctServPrestMes11;
	private BigDecimal valServPrestMes11;
	private Long ctRemitosMes11;
	private Long ctRemitosAConcilMes11;
//	Mes12
	private Long ctServPrestMes12;
	private BigDecimal valServPrestMes12;
	private Long ctRemitosMes12;
	private Long ctRemitosAConcilMes12;
//TOTALES
	private Long ctServPrestTotal;
	private BigDecimal valServPrestTotal;
	private Long ctRemitosTotal;
	private Long ctRemitosAConcilTotal;
	private BigDecimal valBrutaTotal;
	private BigDecimal valNetoTotal;

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

	public Long getCtServPrestMes1() {
		return ctServPrestMes1;
	}

	public void setCtServPrestMes1(Long ctServPrestMes1) {
		this.ctServPrestMes1 = ctServPrestMes1;
	}

	public BigDecimal getValServPrestMes1() {
		return valServPrestMes1;
	}

	public void setValServPrestMes1(BigDecimal valServPrestMes1) {
		this.valServPrestMes1 = valServPrestMes1;
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

	public Long getCtServPrestMes2() {
		return ctServPrestMes2;
	}

	public void setCtServPrestMes2(Long ctServPrestMes2) {
		this.ctServPrestMes2 = ctServPrestMes2;
	}

	public BigDecimal getValServPrestMes2() {
		return valServPrestMes2;
	}

	public void setValServPrestMes2(BigDecimal valServPrestMes2) {
		this.valServPrestMes2 = valServPrestMes2;
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

	public Long getCtServPrestMes3() {
		return ctServPrestMes3;
	}

	public void setCtServPrestMes3(Long ctServPrestMes3) {
		this.ctServPrestMes3 = ctServPrestMes3;
	}

	public BigDecimal getValServPrestMes3() {
		return valServPrestMes3;
	}

	public void setValServPrestMes3(BigDecimal valServPrestMes3) {
		this.valServPrestMes3 = valServPrestMes3;
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

	public Long getCtServPrestMes4() {
		return ctServPrestMes4;
	}

	public void setCtServPrestMes4(Long ctServPrestMes4) {
		this.ctServPrestMes4 = ctServPrestMes4;
	}

	public BigDecimal getValServPrestMes4() {
		return valServPrestMes4;
	}

	public void setValServPrestMes4(BigDecimal valServPrestMes4) {
		this.valServPrestMes4 = valServPrestMes4;
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

	public Long getCtServPrestMes5() {
		return ctServPrestMes5;
	}

	public void setCtServPrestMes5(Long ctServPrestMes5) {
		this.ctServPrestMes5 = ctServPrestMes5;
	}

	public BigDecimal getValServPrestMes5() {
		return valServPrestMes5;
	}

	public void setValServPrestMes5(BigDecimal valServPrestMes5) {
		this.valServPrestMes5 = valServPrestMes5;
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

	public Long getCtServPrestMes6() {
		return ctServPrestMes6;
	}

	public void setCtServPrestMes6(Long ctServPrestMes6) {
		this.ctServPrestMes6 = ctServPrestMes6;
	}

	public BigDecimal getValServPrestMes6() {
		return valServPrestMes6;
	}

	public void setValServPrestMes6(BigDecimal valServPrestMes6) {
		this.valServPrestMes6 = valServPrestMes6;
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

	public Long getCtServPrestMes7() {
		return ctServPrestMes7;
	}

	public void setCtServPrestMes7(Long ctServPrestMes7) {
		this.ctServPrestMes7 = ctServPrestMes7;
	}

	public BigDecimal getValServPrestMes7() {
		return valServPrestMes7;
	}

	public void setValServPrestMes7(BigDecimal valServPrestMes7) {
		this.valServPrestMes7 = valServPrestMes7;
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

	public Long getCtServPrestMes8() {
		return ctServPrestMes8;
	}

	public void setCtServPrestMes8(Long ctServPrestMes8) {
		this.ctServPrestMes8 = ctServPrestMes8;
	}

	public BigDecimal getValServPrestMes8() {
		return valServPrestMes8;
	}

	public void setValServPrestMes8(BigDecimal valServPrestMes8) {
		this.valServPrestMes8 = valServPrestMes8;
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

	public Long getCtServPrestMes9() {
		return ctServPrestMes9;
	}

	public void setCtServPrestMes9(Long ctServPrestMes9) {
		this.ctServPrestMes9 = ctServPrestMes9;
	}

	public BigDecimal getValServPrestMes9() {
		return valServPrestMes9;
	}

	public void setValServPrestMes9(BigDecimal valServPrestMes9) {
		this.valServPrestMes9 = valServPrestMes9;
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

	public Long getCtServPrestMes10() {
		return ctServPrestMes10;
	}

	public void setCtServPrestMes10(Long ctServPrestMes10) {
		this.ctServPrestMes10 = ctServPrestMes10;
	}

	public BigDecimal getValServPrestMes10() {
		return valServPrestMes10;
	}

	public void setValServPrestMes10(BigDecimal valServPrestMes10) {
		this.valServPrestMes10 = valServPrestMes10;
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

	public Long getCtServPrestMes11() {
		return ctServPrestMes11;
	}

	public void setCtServPrestMes11(Long ctServPrestMes11) {
		this.ctServPrestMes11 = ctServPrestMes11;
	}

	public BigDecimal getValServPrestMes11() {
		return valServPrestMes11;
	}

	public void setValServPrestMes11(BigDecimal valServPrestMes11) {
		this.valServPrestMes11 = valServPrestMes11;
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

	public Long getCtServPrestMes12() {
		return ctServPrestMes12;
	}

	public void setCtServPrestMes12(Long ctServPrestMes12) {
		this.ctServPrestMes12 = ctServPrestMes12;
	}

	public BigDecimal getValServPrestMes12() {
		return valServPrestMes12;
	}

	public void setValServPrestMes12(BigDecimal valServPrestMes12) {
		this.valServPrestMes12 = valServPrestMes12;
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

	public Long getCtServPrestTotal() {
		return ctServPrestTotal;
	}

	public void setCtServPrestTotal(Long ctServPrestTotal) {
		this.ctServPrestTotal = ctServPrestTotal;
	}

	public BigDecimal getValServPrestTotal() {
		return valServPrestTotal;
	}

	public void setValServPrestTotal(BigDecimal valServPrestTotal) {
		this.valServPrestTotal = valServPrestTotal;
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

	public List getEstadoList() {
		return estadoList;
	}

	public void setEstadoList(List estadoList) {
		this.estadoList = estadoList;
	}

	public String getSelectedEstado() {
		return selectedEstado;
	}

	public void setSelectedEstado(String selectedEstado) {
		this.selectedEstado = selectedEstado;
	}

	public String getStLoteDet() {
		return stLoteDet;
	}

	public void setStLoteDet(String stLoteDet) {
		this.stLoteDet = stLoteDet;
	}
	
	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

}