package com.bbva.cfs.usuario.form;
 
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorForm;

public class UsuarioForm extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long usuarioId;
	private String userName;
	private Long perfil;
	private String perfilName;
	private String nombre;
	private String apellido;
	private String cdSector;
	private String nbSector;
	private String email;
	
	
	@SuppressWarnings("rawtypes")
	private List sectorList = new ArrayList();	

	@SuppressWarnings("rawtypes")
	private List perfilList = new ArrayList();	

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

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getPerfil() {
		return perfil;
	}

	public void setPerfil(Long perfil) {
		this.perfil = perfil;
	}

	public String getPerfilName() {
		return perfilName;
	}

	public void setPerfilName(String perfilName) {
		this.perfilName = perfilName;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	@SuppressWarnings("rawtypes")
	public List getPerfilList() {
		return perfilList;
	}

	@SuppressWarnings("rawtypes")
	public void setPerfilList(List perfilList) {
		this.perfilList = perfilList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public List getSectorList() {
		return sectorList;
	}

	public void setSectorList(List sectorList) {
		this.sectorList = sectorList;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the nbSector
	 */
	public String getNbSector() {
		return nbSector;
	}

	/**
	 * @param nbSector the nbSector to set
	 */
	public void setNbSector(String nbSector) {
		this.nbSector = nbSector;
	}




}
