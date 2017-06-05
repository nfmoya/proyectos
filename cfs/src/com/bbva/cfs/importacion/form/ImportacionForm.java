package com.bbva.cfs.importacion.form;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorForm;

public class ImportacionForm extends ValidatorForm {

//	private List filtroProveedorList = new ArrayList();

	private String observaciones;

	private FormFile lotesFile;
	
	private String proveedorSelected;
	
	private String lotesFilePath;
	
//	public List getFiltroProveedorList() {
//		return filtroProveedorList;
//	}
//
//	public void setFiltroProveedorList(List filtroProveedorList) {
//		this.filtroProveedorList = filtroProveedorList;
//	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public FormFile getLotesFile() {
		return lotesFile;
	}

	public void setLotesFile(FormFile lotesFile) {
		this.lotesFile = lotesFile;
	}

	public String getProveedorSelected() {
		return proveedorSelected;
	}

	public void setProveedorSelected(String proveedorSelected) {
		this.proveedorSelected = proveedorSelected;
	}

	public String getLotesFilePath() {
		return lotesFilePath;
	}

	public void setLotesFilePath(String lotesFilePath) {
		this.lotesFilePath = lotesFilePath;
	}

}
