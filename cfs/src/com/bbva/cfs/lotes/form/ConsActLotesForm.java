package com.bbva.cfs.lotes.form;

import com.bbva.cfs.general.form.GeneralForm;

/**
 * Clase que representa el formBean de Consulta y Actualizaci�n de Lotes
 * 
 * @author Alexis Comerci
 * 
 */
public class ConsActLotesForm extends GeneralForm {

	private static final long serialVersionUID = -6632053261087435669L;

	private String tipoLote;
	private String cdProveedor;
	private String fhAltaDesde;
	private String fhAltaHasta;
	private String stLote;
	private String cdLote;
	
	/** Grant => Permisos*/
	private String anularPrestGrant = "N";	
	private String anularFactGrant = "N";

	public String getTipoLote() {
		return tipoLote;
	}

	public void setTipoLote(String tipoLote) {
		this.tipoLote = tipoLote;
	}

	public String getCdProveedor() {
		return cdProveedor;
	}

	public void setCdProveedor(String cdProveedor) {
		this.cdProveedor = cdProveedor;
	}

	public String getFhAltaDesde() {
		return fhAltaDesde;
	}

	public void setFhAltaDesde(String fhAltaDesde) {
		this.fhAltaDesde = fhAltaDesde;
	}

	public String getFhAltaHasta() {
		return fhAltaHasta;
	}

	public void setFhAltaHasta(String fhAltaHasta) {
		this.fhAltaHasta = fhAltaHasta;
	}

	public String getStLote() {
		return stLote;
	}

	public void setStLote(String stLote) {
		this.stLote = stLote;
	}

	public String getCdLote() {
		return cdLote;
	}

	public void setCdLote(String cdLote) {
		this.cdLote = cdLote;
	}

	/**
	 * @return the anularPrestGrant
	 */
	public String getAnularPrestGrant() {
		return anularPrestGrant;
	}

	/**
	 * @param anularPrestGrant the anularPrestGrant to set
	 */
	public void setAnularPrestGrant(String anularPrestGrant) {
		this.anularPrestGrant = anularPrestGrant;
	}

	/**
	 * @return the anularFatGrant
	 */
	public String getAnularFactGrant() {
		return anularFactGrant;
	}

	/**
	 * @param anularFatGrant the anularFatGrant to set
	 */
	public void setAnularFactGrant(String anularFactGrant) {
		this.anularFactGrant = anularFactGrant;
	}
}
