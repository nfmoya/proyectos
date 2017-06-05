package com.bbva.cfs.commons.utils;

/**
 * Esta enumeracion representa los tipos de parametros existentes en la tabla
 * <code>INV_parametros</code> y el id se corresponde con el campo
 * <code>id_tabla</code> de la tabla parametros.
 * 
 * ie.: el "002" es el Id de tabla de provincias.
 * 
 */
public class ParameterType {
	
	private final String id;
	
	private ParameterType(String id) {
		this.id = id;
	}
	
	public static final ParameterType PROVEEDOR               = new ParameterType("001");
	public static final ParameterType PROVEEDOR_PERIODO       = new ParameterType("002");
	public static final ParameterType PROVEEDOR_VALOR         = new ParameterType("003");
	public static final ParameterType PRODUCTO                = new ParameterType("004");
	public static final ParameterType PRODUCTO_CONS_CONCIL    = new ParameterType("044");
	public static final ParameterType PRODUCTO_PRECIO         = new ParameterType("005");
	public static final ParameterType PRODUCTO_SECTOR         = new ParameterType("006");
	public static final ParameterType SECTOR                  = new ParameterType("007");
	public static final ParameterType TABLA                   = new ParameterType("008");
	public static final ParameterType TABLA_GRUPRO            = new ParameterType("009");
	public static final ParameterType TABLA_UNIVAL            = new ParameterType("010");
	public static final ParameterType TABLA_TIPCOM            = new ParameterType("011");
	public static final ParameterType TABLA_PARAME            = new ParameterType("012");
	public static final ParameterType CONC_PROVEEDOR_PERIODO  = new ParameterType("013");
	public static final ParameterType CONC_PROVEEDOR_PRODUCTO = new ParameterType("014");
	public static final ParameterType CONC_NRO_CONCILIACION   = new ParameterType("015");
	public static final ParameterType CONC_NRO_NOMEDIBLE      = new ParameterType("016");
	public static final ParameterType PRODUCTO_AGRUP_ORIG	  = new ParameterType("017");
	public static final ParameterType PRODUCTO_AGRUP	      = new ParameterType("018");
	public static final ParameterType PRODUCTO_AGRUP_ORIG_ALTA= new ParameterType("019");
	public static final ParameterType PRODUCTO_AGRUP_ALTA     = new ParameterType("020");

	public String getId() {
		return id;
	}
}
