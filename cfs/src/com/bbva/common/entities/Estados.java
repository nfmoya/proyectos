package com.bbva.common.entities;

public class Estados {

	private String estado;
	private String desc;
	private String cod;

	public Estados(String estado, String cod, String desc) {
		this.estado = estado;
		this.cod = cod;
		this.desc = desc;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

}
