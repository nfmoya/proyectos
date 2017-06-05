package com.bbva.cfs.users.form;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

public class UsersForm extends ActionForm {
	private List userList = new ArrayList();
	
	private List receivers = new ArrayList();
	
	private List perfiles = new ArrayList();
	
	private List estadosList = new ArrayList();
	
	private String strPerfiles;
	private String strPerfilesDel;
	
	private Long userId;
	private String userName;
	
	private String editGrant = "N";
	private String createGrant = "N";
	private String deleteGrant = "N";
	private String resetUser = "N";
	
	/*TODO: LIMPIAR*/
	
	private String txtUsuario;
	private String txtPerfiles;
	private String txtEstadoUser;
	
	public List getUserList() {
		return userList;
	}

	public void setUserList(List userList) {
		this.userList = userList;
	}

	public UsersForm(){
		super();
	}

	public String getTxtPerfiles() {
		return txtPerfiles;
	}

	public void setTxtPerfiles(String txtPerfiles) {
		this.txtPerfiles = txtPerfiles;
	}

	public String getTxtUsuario() {
		return txtUsuario;
	}

	public void setTxtUsuario(String txtUsuario) {
		this.txtUsuario = txtUsuario;
	}

	public List getReceivers() {
		return receivers;
	}

	public void setReceivers(List recievers) {
		this.receivers = recievers;
	}

	public List getPerfiles() {
		return perfiles;
	}

	public void setPerfiles(List perfiles) {
		this.perfiles = perfiles;
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

	public String getStrPerfiles() {
		return strPerfiles;
	}

	public void setStrPerfiles(String strPerfiles) {
		this.strPerfiles = strPerfiles;
	}

	public String getStrPerfilesDel() {
		return strPerfilesDel;
	}

	public void setStrPerfilesDel(String strPerfilesDel) {
		this.strPerfilesDel = strPerfilesDel;
	}

	public String getCreateGrant() {
		return createGrant;
	}

	public void setCreateGrant(String createGrant) {
		this.createGrant = createGrant;
	}

	public String getDeleteGrant() {
		return deleteGrant;
	}

	public void setDeleteGrant(String deleteGrant) {
		this.deleteGrant = deleteGrant;
	}

	public String getEditGrant() {
		return editGrant;
	}

	public void setEditGrant(String editGrant) {
		this.editGrant = editGrant;
	}

	public String getResetUser() {
		return resetUser;
	}

	public void setResetUser(String resetUser) {
		this.resetUser = resetUser;
	}

	public List getEstadosList() {
		return estadosList;
	}

	public void setEstadosList(List estadosList) {
		this.estadosList = estadosList;
	}

	public String getTxtEstadoUser() {
		return txtEstadoUser;
	}

	public void setTxtEstadoUser(String txtEstadoUser) {
		this.txtEstadoUser = txtEstadoUser;
	}


}
