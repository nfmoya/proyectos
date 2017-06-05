package com.bbva.cfs.users.model;

public class Perfil {
	
	private Long id;
	private String descripcion;
	
	public Perfil(){}
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	
}
