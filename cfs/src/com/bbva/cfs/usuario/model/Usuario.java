package com.bbva.cfs.usuario.model;

public class Usuario {
	
	private Long usuarioId;
	private String userName;
	private Long perfil;
	private String perfilName;
	private String nombre;
	private String apellido;
	private String cdSector;
	private String nbSector;
	private String email;
	
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

	public String getCdSector() {
		return cdSector;
	}

	public void setCdSector(String cdSector) {
		this.cdSector = cdSector;
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
