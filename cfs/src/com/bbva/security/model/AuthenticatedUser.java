package com.bbva.security.model;

public class AuthenticatedUser {
	
	private String username;
	private String enabledUser;	
	private String status;
	private String lastAccess;
	private String accessHour;
	private String password1; 
	private String password2; 
	private String password3; 
	private String password4;
	private int    failedAttempts;
	private String password;
	
	private boolean active;  
	private int    lastPassword;

	public String getAccessHour() {
		return accessHour;
	}
	public void setAccessHour(String accessHour) {
		this.accessHour = accessHour;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getEnabledUser() {
		return enabledUser;
	}
	public void setEnabledUser(String enabledUser) {
		this.enabledUser = enabledUser;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getFailedAttempts() {
		return failedAttempts;
	}
	public void setFailedAttempts(int failedAttempts) {
		this.failedAttempts = failedAttempts;
	}
	public String getLastAccess() {
		return lastAccess;
	}
	public void setLastAccess(String lastAccess) {
		this.lastAccess = lastAccess;
	}
	public int getLastPassword() {
		return lastPassword;
	}
	public void setLastPassword(int lastPassword) {
		this.lastPassword = lastPassword;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword1() {
		return password1;
	}
	public void setPassword1(String password1) {
		this.password1 = password1;
	}
	public String getPassword2() {
		return password2;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	public String getPassword3() {
		return password3;
	}
	public void setPassword3(String password3) {
		this.password3 = password3;
	}
	public String getPassword4() {
		return password4;
	}
	public void setPassword4(String password4) {
		this.password4 = password4;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
