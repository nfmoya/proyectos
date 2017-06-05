package com.bbva.cfs.users.model;

import java.util.List;
import java.util.ArrayList;

public class User {

	private Long id;
	private String name;
	
	//Variable temporal
	private String userName;
	private Long idOng;
	private String strPerfiles;
	private List perfiles;
	private String estadoUser;
	
	
	//Estado de blanqueo de clave
	private String blaqueoState;

	public User() {
		this.perfiles = new ArrayList();
	}
	
	public User(Long id, String name){
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getIdOng() {
		return idOng;
	}

	public void setIdOng(Long idOng) {
		this.idOng = idOng;
	}

	public String getStrPerfiles() {
		return strPerfiles;
	}

	public void setStrPerfiles(String strPerfiles) {
		this.strPerfiles = strPerfiles;
	}

	public List getPerfiles() {
		return perfiles;
	}

	public void setPerfiles(List perfiles) {
		this.perfiles = perfiles;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBlaqueoState() {
		return blaqueoState;
	}

	public void setBlaqueoState(String blaqueoState) {
		this.blaqueoState = blaqueoState;
	}

	public String getEstadoUser() {
		return estadoUser;
	}

	public void setEstadoUser(String estadoUser) {
		this.estadoUser = estadoUser;
	}

}
