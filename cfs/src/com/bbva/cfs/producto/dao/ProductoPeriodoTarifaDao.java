package com.bbva.cfs.producto.dao;

import java.util.List;
import java.util.Map;

import com.bbva.cfs.commons.model.Result;

/**
 * TODO:
 * @author xah1198
 *
 */
public interface ProductoPeriodoTarifaDao {

	@SuppressWarnings("rawtypes")
	public Result getProductosPeriodosTarifas( Map parameters, List productosPeriodosTarifasList ) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result saveProductoPeriodoTarifa(Map parameters) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result deleteProductoPeriodoTarifa( Map parameters ) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public Result deleteProductoPeriodoTarifas( Map parameters ) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public Result getNuevoCodigoTarifa(Map parameters, List nuevoCodigoTarifaList) throws Exception;

}
