package com.bbva.cfs.commons.model;

import java.util.Map;
import java.util.HashMap;

public class Session {
	
	private String userName;
	private Long userId;
	private String sepLista;
	private String sepSubLista;
	private Map privileges;
	private String fullname;
	private String sector;
	private Long idPerfil;
	
	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Session(){
		this.privileges = new HashMap();
		this.privileges.clear();
	}
	
	public String getSepLista() {
		return sepLista;
	}

	public void setSepLista(String sepLista) {
		this.sepLista = sepLista;
	}

	public Map getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Map privileges) {
		this.privileges = privileges;
	}

	public String getSepSubLista() {
		return sepSubLista;
	}

	public void setSepSubLista(String sepSubLista) {
		this.sepSubLista = sepSubLista;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public boolean checkPrivilege(String priv){
		boolean granted = false;
		if (this.privileges.get(priv) != null) {
			granted = true;
		}
		return granted;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	/**
	 * @return the idPerfil
	 */
	public Long getIdPerfil() {
		return idPerfil;
	}

	/**
	 * @param idPerfil the idPerfil to set
	 */
	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}
}
