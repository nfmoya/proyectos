package com.bbva.cfs.conciliacion.form;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

/**
 * Clase que representa el formBean de la consulta de 
 * Diferencias de Conciliaciones
 * 
 * @author David
 *
 */
public class BusquedaConciliacionesForm extends  ValidatorForm {

	private static final long serialVersionUID = -6632053261087435669L;
	
	private String cdConciliacion;
	private String cdProveedor;
	private String cdSector;
	private String cdOrden;
	private String cdRemito;
	private String cdPeriodoFact;
	private String stPeriodoFact;
	private String cdProducto;
	private String descProducto;
	private String fhDesde;
	private String fhHasta;
	private String periodoFacturacion;
	private String estadoConciliacion;
	private BigDecimal imPrecioTot;
	private String estadoDif;
	private String stDiferencia; //Estado
	private BigDecimal ctDiferencia; //Cantidad
	private String tpSolucion;
	private String sector; //Sector del usuario para permisos
	private String observaciones;
	private String stIgnoraVal;
	private String fechaConciliacion;
	private String usuarioConciliacion;
	private String checkProdNoMedible;
	
	private List pFactDesdeList = new ArrayList();
	private List pFactHastaList = new ArrayList();
	private List productosList = new ArrayList();	
	private List proveedorList = new ArrayList();
	private List sectorList = new ArrayList();
	
	//Permisos para los usuarios
	private String editGrant = "N";
	private String anularConcil = "N";
	
	/**
	 * @return the cdConciliacion
	 */
	public String getCdConciliacion() {
		return cdConciliacion;
	}
	/**
	 * @param cdConciliacion the cdConciliacion to set
	 */
	public void setCdConciliacion(String cdConciliacion) {
		this.cdConciliacion = cdConciliacion;
	}
	/**
	 * @return the cdProveedor
	 */
	public String getCdProveedor() {
		return cdProveedor;
	}
	/**
	 * @param cdProveedor the cdProveedor to set
	 */
	public void setCdProveedor(String cdProveedor) {
		this.cdProveedor = cdProveedor;
	}
	/**
	 * @return the cdSector
	 */
	public String getCdSector() {
		return cdSector;
	}
	/**
	 * @param cdSector the cdSector to set
	 */
	public void setCdSector(String cdSector) {
		this.cdSector = cdSector;
	}
	/**
	 * @return the cdPeriodoFact
	 */
	public String getCdPeriodoFact() {
		return cdPeriodoFact;
	}
	/**
	 * @param cdPeriodoFact the cdPeriodoFact to set
	 */
	public void setCdPeriodoFact(String cdPeriodoFact) {
		this.cdPeriodoFact = cdPeriodoFact;
	}
	/**
	 * @return the cdProducto
	 */
	public String getCdProducto() {
		return cdProducto;
	}
	/**
	 * @param cdProducto the cdProducto to set
	 */
	public void setCdProducto(String cdProducto) {
		this.cdProducto = cdProducto;
	}
	/**
	 * @return the fhDesde
	 */
	public String getFhDesde() {
		return fhDesde;
	}
	/**
	 * @param fhDesde the fhDesde to set
	 */
	public void setFhDesde(String fhDesde) {
		this.fhDesde = fhDesde;
	}
	/**
	 * @return the fhHasta
	 */
	public String getFhHasta() {
		return fhHasta;
	}
	/**
	 * @param fhHasta the fhHasta to set
	 */
	public void setFhHasta(String fhHasta) {
		this.fhHasta = fhHasta;
	}
		/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @return the pFactDesdeList
	 */
	public List getpFactDesdeList() {
		return pFactDesdeList;
	}
	/**
	 * @param pFactDesdeList the pFactDesdeList to set
	 */
	public void setpFactDesdeList(List pFactDesdeList) {
		this.pFactDesdeList = pFactDesdeList;
	}
	/**
	 * @return the pFactHastaList
	 */
	public List getpFactHastaList() {
		return pFactHastaList;
	}
	/**
	 * @param pFactHastaList the pFactHastaList to set
	 */
	public void setpFactHastaList(List pFactHastaList) {
		this.pFactHastaList = pFactHastaList;
	}
	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}
	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	/**
	 * @return the cdOrden
	 */
	public String getCdOrden() {
		return cdOrden;
	}
	/**
	 * @param cdOrden the cdOrden to set
	 */
	public void setCdOrden(String cdOrden) {
		this.cdOrden = cdOrden;
	}
	/**
	 * @return the imPrecioTot
	 */
	public BigDecimal getImPrecioTot() {
		return imPrecioTot;
	}
	/**
	 * @param imPrecioTot the imPrecioTot to set
	 */
	public void setImPrecioTot(BigDecimal imPrecioTot) {
		this.imPrecioTot = imPrecioTot;
	}
	/**
	 * @return the stDiferencia
	 */
	public String getStDiferencia() {
		return stDiferencia;
	}
	/**
	 * @param stDiferencia the stDiferencia to set
	 */
	public void setStDiferencia(String stDiferencia) {
		this.stDiferencia = stDiferencia;
	}
	/**
	 * @return the sector
	 */
	public String getSector() {
		return sector;
	}
	/**
	 * @param sector the sector to set
	 */
	public void setSector(String sector) {
		this.sector = sector;
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
	 * @return the ctDiferencia
	 */
	public BigDecimal getCtDiferencia() {
		return ctDiferencia;
	}
	/**
	 * @param ctDiferencia the ctDiferencia to set
	 */
	public void setCtDiferencia(BigDecimal ctDiferencia) {
		this.ctDiferencia = ctDiferencia;
	}
	/**
	 * @return the tpSolucion
	 */
	public String getTpSolucion() {
		return tpSolucion;
	}
	/**
	 * @param tpSolucion the tpSolucion to set
	 */
	public void setTpSolucion(String tpSolucion) {
		this.tpSolucion = tpSolucion;
	}
	/**
	 * @return the cdRemito
	 */
	public String getCdRemito() {
		return cdRemito;
	}
	/**
	 * @param cdRemito the cdRemito to set
	 */
	public void setCdRemito(String cdRemito) {
		this.cdRemito = cdRemito;
	}
	public String getEstadoDif() {
		return estadoDif;
	}
	public void setEstadoDif(String estadoDif) {
		this.estadoDif = estadoDif;
	}
	public String getPeriodoFacturacion() {
		return periodoFacturacion;
	}
	public void setPeriodoFacturacion(String periodoFacturacion) {
		this.periodoFacturacion = periodoFacturacion;
	}
	public String getDescProducto() {
		return descProducto;
	}
	public void setDescProducto(String descProducto) {
		this.descProducto = descProducto;
	}
	public String getEstadoConciliacion() {
		return estadoConciliacion;
	}
	public void setEstadoConciliacion(String estadoConciliacion) {
		this.estadoConciliacion = estadoConciliacion;
	}
	public String getStIgnoraVal() {
		return stIgnoraVal;
	}
	public void setStIgnoraVal(String stIgnoraVal) {
		this.stIgnoraVal = stIgnoraVal;
	}
	public String getFechaConciliacion() {
		return fechaConciliacion;
	}
	public void setFechaConciliacion(String fechaConciliacion) {
		this.fechaConciliacion = fechaConciliacion;
	}
	public String getUsuarioConciliacion() {
		return usuarioConciliacion;
	}
	public void setUsuarioConciliacion(String usuarioConciliacion) {
		this.usuarioConciliacion = usuarioConciliacion;
	}
	public String getAnularConcil() {
		return anularConcil;
	}
	public void setAnularConcil(String anularConcil) {
		this.anularConcil = anularConcil;
	}
	/**
	 * @return the stPeriodoFact
	 */
	public String getStPeriodoFact() {
		return stPeriodoFact;
	}
	/**
	 * @param stPeriodoFact the estPeriodoFact to set
	 */
	public void setStPeriodoFact(String stPeriodoFact) {
		this.stPeriodoFact = stPeriodoFact;
	}
	public String getCheckProdNoMedible() {
		return checkProdNoMedible;
	}
	public void setCheckProdNoMedible(String checkProdNoMedible) {
		this.checkProdNoMedible = checkProdNoMedible;
	}
}
