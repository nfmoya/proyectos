package com.bbva.cfs.commons.utils;

/**
 * Est� clase contiene constantes que se usan para invocar un SP.
 * 
 * @author xah1257
 * 
 */
public class DatabaseConstants {
	// Numero de error minimo que usan las validaciones como codigo en la base
	// de datos.
	public static final Long MIN_NUMBER_VALIDATION_ERROR = Long
			.valueOf("21000");

	// transaction manager name
	static final String TM_DBCFS = "TM_DBCFS";
	static final String DOT = ".";

	static final String INIT_SESSION = "INICIAR_SESION";


	static final String GET_USER  = "CONSULTA_USUARIO";
	static final String SAVE_USER = "ALTA_USUARIO";
	static final String EDIT_USER = "MODIF_USUARIO";
	
	static final String VALIDATE_PERIOD_FACT     = "VALIDATE_PERIOD_FACT";
	static final String VALIDATE_PERIOD_FACT2     = "VALIDATE_PERIOD_FACT2";

	static final String ABMC_PROVEEDOR           = "ABMC_PROVEEDOR";	
	static final String ABMC_PROVEEDOR_PERIODO   = "ABMC_PROVEEDOR_PERIODO";	
	static final String CONSULTA_PERIODO_DH      = "CONSULTA_PERIODO_DH";	;	
	static final String CONSULTA_SERV_FACT       = "CONSULTA_SERV_FACT";		
	static final String CONSULTA_SERV_FACT2      = "CONSULTA_SERV_FACT2";		
	static final String CONSULTA_SERV_FACT_RED   = "CONSULTA_SERV_FACT_RED";
	static final String CONSULTA_SERV_FACT_DET   = "CONSULTA_SERV_FACT_DET";		
	
	static final String CONSULTA_SERV_PREST_DETALLE  = "CONSULTA_SERV_PREST_DETALLE";
    static final String GET_SERV_PREST_PERIODOS      = "GET_SERV_PREST_PERIODOS";
	static final String CONSULTA_SERV_PREST2         = "CONSULTA_SERV_PREST2";
	static final String ABMC_PROVEEDOR_VALOR         = "ABMC_PROVEEDOR_VALOR";	
	static final String ABMC_PROVEEDOR_TIPOCAMBIO    = "ABMC_PROVEEDOR_TIPOCAMBIO";	
	static final String ABMC_PRODUCTO                = "ABMC_PRODUCTO";	
	static final String ABMC_PRODUCTO_PRECIO         = "ABMC_PRODUCTO_PRECIO";	
	static final String ABMC_PRODUCTO_SECTOR         = "ABMC_PRODUCTO_SECTOR";	
	static final String ABMC_PRODUCTO_PERIODO        = "ABMC_PRODUCTO_PERIODO";	
	static final String ABMC_PRODUCTO_PERIODO_TARIFA = "ABMC_PRODUCTO_PERIODO_TARIFA";	
	static final String ABMC_TARIFA_NUEVO_CODIGO     = "ABMC_TARIFA_NUEVO_CODIGO";
	static final String ABMC_SECTOR                  = "ABMC_SECTOR";	
	static final String ABMC_TABLA                   = "ABMC_TABLA";	
	static final String CONSULTA_PRODUCTO_AGRUP      = "CONSULTA_PRODUCTO_AGRUP";	
	static final String ABMC_PRODUCTO_AGRUPADO       = "ABMC_PRODUCTO_AGRUPADO";	
	
	static final String CONSULTA_SERV_PREST      = "CONSULTA_SERV_PREST";
	static final String CONSULTA_SERV_PREST_RED  = "CONSULTA_SERV_PREST_RED";
	
	static final String ABMC_USUARIO             = "ABMC_USUARIO";	
	
	static final String GET_PARAMETER_TYPE_LIST  = "LISTAR_TIPO_PARAMETRO";
	static final String GET_PARAMETER_LIST       = "LISTAR_PARAMETROS";
	static final String SAVE_PARAMETER           = "ALTA_PARAMETRO";
	static final String EDIT_PARAMETER           = "MODIF_PARAMETRO";
	static final String DELETE_PARAMETER         = "ELIMINA_PARAMETRO";
	
	static final String GET_DIF_CONCILIA_LIST    = "LISTAR_DIF_CONCILIACION";
	static final String EDIT_DIF_CONCILIA    	 = "EDITAR_DIF_CONCILIACION";
	
	static final String CONCILIACION_LISTA       = "CONCILIACION_LISTA";
	static final String CONCILIACION_CONSULTA    = "CONCILIACION_CONSULTA";
	static final String CONCILIACION_SAVE        = "CONCILIACION_SAVE";
	static final String CONCILIACION_REPETIDOS   = "CONCILIACION_REPETIDOS";
	static final String BUSQUEDA_CONCILIACIONES  = "BUSCAR_CONCILIACIONES";
	static final String BUSQUEDA_CONCIL_PROD_NOMED  = "BUSCAR_CONCIL_PROD_NOMED";
	static final String ANULAR_CONCILIACION      = "ANULAR_CONCILIACION";
	static final String ANULAR_CONCILIACION_NO_MED      = "ANULAR_CONCILIACION_NO_MED";
	static final String CONCILIACION_SAVE_PRES   = "CONCILIACION_SAVE_PRES";
	static final String CONCILIACION_SAVE_FACT   = "CONCILIACION_SAVE_FACT";
	static final String CONCILIACION_PRODUCTO    = "CONCILIACION_PRODUCTO";	
	static final String CONCILIACION_DIF_LIST    = "CONCILIACION_DIF_LIST";	
	
	static final String GET_CONS_ACT_LOTES_LIST  = "LISTAR_CONS_ACT_LOTES";
	static final String ANULAR_CONS_ACT_LOTES    = "ANULAR_CONS_ACT_LOTES";
	
	static final String NOMEDIBLES_LISTA         = "NOMEDIBLES_LISTA";
	static final String NOMEDIBLES_CONSULTA      = "NOMEDIBLES_CONSULTA";
	static final String NOMEDIBLES_SAVE          = "NOMEDIBLES_SAVE";
	static final String NOMEDIBLES_SAVE_DET      = "NOMEDIBLES_SAVE_DET";
	static final String BUSQUEDA_NOMEDIBLES      = "BUSCAR_NOMEDIBLES";
	static final String ANULAR_NOMEDIBLES        = "ANULAR_NOMEDIBLES";
	
    
	//==================================================
	
	// Parametro de salida codigo de error
	public static final String PO_C_ERROR = "po_c_error";
	// Parametro de salida descripcion de error
	public static final String PO_D_ERROR = "po_d_error";
	// Parametro de salida cursor
	public static final String PO_CURSOR = "cursor";
	
	public static final String PO_CD_CONCILIACION = "po_conciliacion";
	
	public static final String PO_NU_CANTDESDE_NUEVO = "po_NU_CANTDESDE_NUEVO";	

	public static String getTransactionName(final String name) {
		return TM_DBCFS + DOT + name;
	}

	public static final String INIT_SESSION_INVOKER = getTransactionName(INIT_SESSION);

	public static final String GET_USER_INVOKER  = getTransactionName(GET_USER);
	public static final String SAVE_USER_INVOKER = getTransactionName(SAVE_USER);
	public static final String EDIT_USER_INVOKER = getTransactionName(EDIT_USER);
	
	public static final String VALIDATE_PERIOD_FACT_INVOKER = getTransactionName(VALIDATE_PERIOD_FACT);
	public static final String VALIDATE_PERIOD_FACT2_INVOKER = getTransactionName(VALIDATE_PERIOD_FACT2);

	public static final String ABMC_PROVEEDOR_INVOKER = getTransactionName(ABMC_PROVEEDOR);	
	public static final String ABMC_PROVEEDOR_PERIODO_INVOKER = getTransactionName(ABMC_PROVEEDOR_PERIODO);	
	public static final String ABMC_PROVEEDOR_VALOR_INVOKER = getTransactionName(ABMC_PROVEEDOR_VALOR);	
	public static final String ABMC_PROVEEDOR_TIPOCAMBIO_INVOKER = getTransactionName(ABMC_PROVEEDOR_TIPOCAMBIO);	
	
	public static final String ABMC_PRODUCTO_INVOKER = getTransactionName(ABMC_PRODUCTO);	
	public static final String ABMC_PRODUCTO_PRECIO_INVOKER = getTransactionName(ABMC_PRODUCTO_PRECIO);	
	public static final String ABMC_PRODUCTO_SECTOR_INVOKER = getTransactionName(ABMC_PRODUCTO_SECTOR);	
	public static final String ABMC_PRODUCTO_PERIODO_INVOKER = getTransactionName(ABMC_PRODUCTO_PERIODO);	
	public static final String ABMC_PRODUCTO_PERIODO_TARIFA_INVOKER = getTransactionName(ABMC_PRODUCTO_PERIODO_TARIFA);	
	public static final String ABMC_TARIFA_NUEVO_CODIGO_INVOKER = getTransactionName(ABMC_TARIFA_NUEVO_CODIGO);	
	public static final String CONSULTA_PRODUCTO_AGRUP_INVOKER = getTransactionName(CONSULTA_PRODUCTO_AGRUP);	
	public static final String ABMC_PRODUCTO_AGRUPADO_INVOKER = getTransactionName(ABMC_PRODUCTO_AGRUPADO);	

	public static final String ABMC_SECTOR_INVOKER = getTransactionName(ABMC_SECTOR);	
	public static final String ABMC_TABLA_INVOKER = getTransactionName(ABMC_TABLA);	
	public static final String ABMC_USUARIO_INVOKER = getTransactionName(ABMC_USUARIO);
	
	public static final String GET_PARAMETER_TYPE_LIST_INVOKER = getTransactionName(GET_PARAMETER_TYPE_LIST);
	public static final String GET_PARAMETER_LIST_INVOKER = getTransactionName(GET_PARAMETER_LIST);
	public static final String SAVE_PARAMETER_INVOKER = getTransactionName(SAVE_PARAMETER);
	public static final String EDIT_PARAMETER_INVOKER = getTransactionName(EDIT_PARAMETER);
	public static final String DELETE_PARAMETER_INVOKER = getTransactionName(DELETE_PARAMETER);
	
	public static final String GET_DIF_CONCILIA_LIST_INVOKER = getTransactionName(GET_DIF_CONCILIA_LIST);
	public static final String EDIT_DIF_CONCILIA_INVOKER = getTransactionName(EDIT_DIF_CONCILIA);

	public static final String CONCILIACION_LISTA_INVOKER = getTransactionName(CONCILIACION_LISTA);
	public static final String CONCILIACION_CONSULTA_INVOKER = getTransactionName(CONCILIACION_CONSULTA);
	public static final String CONCILIACION_SAVE_INVOKER  = getTransactionName(CONCILIACION_SAVE);
	public static final String BUSQUEDA_CONCILIACIONES_INVOKER = getTransactionName(BUSQUEDA_CONCILIACIONES);
	public static final String BUSQUEDA_CONCIL_PROD_NOMED_INVOKER = getTransactionName(BUSQUEDA_CONCIL_PROD_NOMED);
	public static final String ANULAR_CONCILIACION_INVOKER = getTransactionName(ANULAR_CONCILIACION);
	public static final String ANULAR_CONCILIACION_NO_MED_INVOKER = getTransactionName(ANULAR_CONCILIACION_NO_MED);
	public static final String CONCILIACION_REPETIDOS_INVOKER = getTransactionName(CONCILIACION_REPETIDOS);
	public static final String CONCILIACION_SAVE_PRES_INVOKER  = getTransactionName(CONCILIACION_SAVE_PRES);
	public static final String CONCILIACION_SAVE_FACT_INVOKER  = getTransactionName(CONCILIACION_SAVE_FACT);
	public static final String CONCILIACION_PRODUCTO_INVOKER  = getTransactionName(CONCILIACION_PRODUCTO);
	public static final String CONCILIACION_DIF_LIST_INVOKER = getTransactionName(CONCILIACION_DIF_LIST);
			
	public static final String CONSULTA_PERIODO_DH_INVOKER = getTransactionName(CONSULTA_PERIODO_DH);
	public static final String CONSULTA_SERV_FACT_INVOKER = getTransactionName(CONSULTA_SERV_FACT);
	public static final String CONSULTA_SERV_FACT2_INVOKER = getTransactionName(CONSULTA_SERV_FACT2);
	public static final String CONSULTA_SERV_FACT_RED_INVOKER = getTransactionName(CONSULTA_SERV_FACT_RED);
	public static final String CONSULTA_SERV_FACT_DET_INVOKER = getTransactionName(CONSULTA_SERV_FACT_DET);
	
	public static final String CONSULTA_SERV_PREST_DETALLE_INVOKER = getTransactionName(CONSULTA_SERV_PREST_DETALLE);
	public static final String CONSULTA_SERV_PREST2_INVOKER = getTransactionName(CONSULTA_SERV_PREST2);
	
	public static final String GET_CONS_ACT_LOTES_INVOKER = getTransactionName(GET_CONS_ACT_LOTES_LIST);
	public static final String ANULAR_CONS_ACT_LOTES_INVOKER = getTransactionName(ANULAR_CONS_ACT_LOTES);

	public static final String CONSULTA_SERV_PREST_INVOKER = getTransactionName(CONSULTA_SERV_PREST);
	public static final String CONSULTA_SERV_PREST_RED_INVOKER = getTransactionName(CONSULTA_SERV_PREST_RED);
    public static final String GET_SERV_PREST_PERIODOS_INVOKER = getTransactionName(GET_SERV_PREST_PERIODOS);
    
	public static final String NOMEDIBLES_LISTA_INVOKER = getTransactionName(NOMEDIBLES_LISTA);
	public static final String NOMEDIBLES_CONSULTA_INVOKER = getTransactionName(NOMEDIBLES_CONSULTA);
	public static final String NOMEDIBLES_SAVE_INVOKER  = getTransactionName(NOMEDIBLES_SAVE);
	public static final String NOMEDIBLES_SAVE_DET_INVOKER  = getTransactionName(NOMEDIBLES_SAVE_DET);
	public static final String BUSQUEDA_NOMEDIBLES_INVOKER = getTransactionName(BUSQUEDA_NOMEDIBLES);
	public static final String ANULAR_NOMEDIBLES_INVOKER = getTransactionName(ANULAR_NOMEDIBLES);
}
