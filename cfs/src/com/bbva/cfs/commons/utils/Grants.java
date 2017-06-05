package com.bbva.cfs.commons.utils;

/**
 * Est� enumeraci�n representa los d_acceso existentes en la tabla
 * <code>sast_accesos</code> y el id se corresponde con el campo
 * <code>id_acceso</code> de la tabla par�metros.
 * 
 * @author xah1198
 * 
 */
public class Grants {
	
	private final String id;
	
	private Grants(String id) {
		this.id = id;
	}
	
	public static final Grants WELCOME_VIEW                      = new Grants("BIENVENIDA");
	public static final Grants ABM_TABLES_CREATE                 = new Grants("ABMC_TAB_INS");
	public static final Grants ABM_TABLES_EDIT                   = new Grants("ABMC_TAB_UPD");
	public static final Grants ABM_TABLES_DELETE                 = new Grants("ABMC_TAB_DEL");
	public static final Grants ABM_PRODUCTOS_CREATE              = new Grants("ABMC_PRO_BAS_INS");
	public static final Grants ABM_PRODUCTOS_EDIT                = new Grants("ABMC_PRO_BAS_UPD");
	public static final Grants ABM_PRODUCTOS_DELETE              = new Grants("ABMC_PRO_BAS_DEL");
	public static final Grants ABM_PRODUCTOS_PRECIOS_CREATE      = new Grants("ABMC_PRO_PRE_INS");
	public static final Grants ABM_PRODUCTOS_PRECIOS_EDIT        = new Grants("ABMC_PRO_PRE_UPD");
	public static final Grants ABM_PRODUCTOS_PRECIOS_DELETE      = new Grants("ABMC_PRO_PRE_DEL");
	public static final Grants ABM_PRODUCTOS_SECTORES_CREATE     = new Grants("ABMC_PRO_SEC_INS");
	public static final Grants ABM_PRODUCTOS_SECTORES_EDIT       = new Grants("ABMC_PRO_SEC_UPD");
	public static final Grants ABM_PRODUCTOS_SECTORES_DELETE     = new Grants("ABMC_PRO_SEC_DEL");
	public static final Grants ABM_PRODUCTOS_PERIODOS_CREATE     = new Grants("ABMC_PRO_PER_INS");
	public static final Grants ABM_PRODUCTOS_PERIODOS_EDIT       = new Grants("ABMC_PRO_PER_UPD");
	public static final Grants ABM_PRODUCTOS_PERIODOS_DELETE     = new Grants("ABMC_PRO_PER_DEL");
	public static final Grants ABM_PRODUCTOS_TARIFAS_CREATE      = new Grants("ABMC_PRO_TAR_INS");
	public static final Grants ABM_PRODUCTOS_TARIFAS_EDIT        = new Grants("ABMC_PRO_TAR_UPD");
	public static final Grants ABM_PRODUCTOS_TARIFAS_DELETE      = new Grants("ABMC_PRO_TAR_DEL");
	public static final Grants ABM_SECTORES_CREATE               = new Grants("ABMC_SEC_INS");
	public static final Grants ABM_SECTORES_EDIT                 = new Grants("ABMC_SEC_UPD");
	public static final Grants ABM_SECTORES_DELETE               = new Grants("ABMC_SEC_DEL");
	public static final Grants ABM_PROVEEDORES_CREATE            = new Grants("ABMC_PRV_BAS_INS");
	public static final Grants ABM_PROVEEDORES_EDIT              = new Grants("ABMC_PRV_BAS_UPD");
	public static final Grants ABM_PROVEEDORES_DELETE            = new Grants("ABMC_PRV_BAS_DEL");
	public static final Grants ABM_PROVEEDORES_PERIODOS_CREATE   = new Grants("ABMC_PRV_PER_INS");
	public static final Grants ABM_PROVEEDORES_PERIODOS_EDIT     = new Grants("ABMC_PRV_PER_UPD");
	public static final Grants ABM_PROVEEDORES_PERIODOS_DELETE   = new Grants("ABMC_PRV_PER_DEL");
	public static final Grants ABM_PROVEEDORES_VALORES_CREATE    = new Grants("ABMC_PRV_VAL_INS");
	public static final Grants ABM_PROVEEDORES_VALORES_EDIT      = new Grants("ABMC_PRV_VAL_UPD");
	public static final Grants ABM_PROVEEDORES_VALORES_DELETE    = new Grants("ABMC_PRV_VAL_DEL");
	public static final Grants ABM_PROVEEDORES_TIPOCAMBIO_CREATE = new Grants("ABMC_PRV_TPC_INS");
	public static final Grants ABM_PROVEEDORES_TIPOCAMBIO_EDIT   = new Grants("ABMC_PRV_TPC_UPD");
	public static final Grants ABM_PROVEEDORES_TIPOCAMBIO_DELETE = new Grants("ABMC_PRV_TPC_DEL");
	public static final Grants CONCILIACION                    	 = new Grants("CONC_SRV");
	public static final Grants CONCILIACION_GRABAR_SIN_APROBAR   = new Grants("CONC_SRV_GRA");
	public static final Grants CONCILIACION_APROBAR              = new Grants("CONC_SRV_APR");
	public static final Grants CONCILIACION_DIFERENCIAS          = new Grants("CONC_SRV_DIF");
	public static final Grants DIFERENCIA_CONCILIACION           = new Grants("CONSULTAS_DIF_CONCIL");
	public static final Grants ANULAR_CONCILIACION               = new Grants("ANULAR_CONCIL");
	public static final Grants ANULAR_LOTE_PREST                 = new Grants("ANULAR_LOTE_PREST");
	public static final Grants ANULAR_LOTE_FACT                  = new Grants("ANULAR_LOTE_FACT");

	
	public String getId() {
		return id;
	}

}
