package com.bbva.cfs.proveedor.dao;

import java.util.List;
import java.util.Map;

import com.bbva.cfs.commons.model.Result;

/**
 * TODO:
 * @author xah1198
 *
 */
public interface ProveedorPeriodoDao {

	@SuppressWarnings("rawtypes")
	public Result getProveedoresPeriodos( Map parameters, List proveedoresPeriodosList ) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result saveProveedorPeriodo(Map parameters) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result deleteProveedorPeriodo( Map parameters ) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public Result getPeriodosDesdeHasta( Map parameters, List proveedoresPeriodosList ) throws Exception;

}
