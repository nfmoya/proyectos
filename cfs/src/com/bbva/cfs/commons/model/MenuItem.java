package com.bbva.cfs.commons.model;
import java.util.ArrayList;

public class MenuItem {

	private String id;
	private String descripcion;
	private String url;
	private String orden;
	private String idPadre;
	private ArrayList child;
	
	public MenuItem() {
		this.child = new ArrayList();
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdPadre() {
		return idPadre;
	}
	public void setIdPadre(String idPadre) {
		this.idPadre = idPadre;
	}
	public String getOrden() {
		return orden;
	}
	public void setOrden(String orden) {
		this.orden = orden;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void addChild(MenuItem item) {
		this.child.add(item);
	}
	
	public void clearChild(MenuItem item) {
		this.child.clear();
	}
}
