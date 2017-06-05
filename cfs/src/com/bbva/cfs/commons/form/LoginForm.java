package com.bbva.cfs.commons.form;

import java.util.List;

import org.apache.struts.action.ActionForm;

public class LoginForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 862224214829792123L;
	private String menuCompleto;
	private String username;
	private String selectedUserName;
	private String userFullname;
	private List userList;
	private Long userIdPerfil;

	public String getMenuCompleto() {
		return menuCompleto;
	}

	public void setMenuCompleto(String menuCompleto) {
		this.menuCompleto = menuCompleto;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List getUserList() {
		return userList;
	}

	public void setUserList(List userList) {
		this.userList = userList;
	}

	public String getSelectedUserName() {
		return selectedUserName;
	}

	public void setSelectedUserName(String selectedUserName) {
		this.selectedUserName = selectedUserName;
	}

	public String getUserFullname() {
		return userFullname;
	}

	public void setUserFullname(String userFullname) {
		this.userFullname = userFullname;
	}

	/**
	 * @return the userIdPerfil
	 */
	public Long getUserIdPerfil() {
		return userIdPerfil;
	}

	/**
	 * @param userIdPerfil the userIdPerfil to set
	 */
	public void setUserIdPerfil(Long userIdPerfil) {
		this.userIdPerfil = userIdPerfil;
	}
	
	

}
