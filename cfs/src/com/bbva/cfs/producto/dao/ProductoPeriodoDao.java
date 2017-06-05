package com.bbva.cfs.producto.dao;

import java.util.List;
import java.util.Map;

import com.bbva.cfs.commons.model.Result;

/**
 * TODO:
 * @author xah1198
 *
 */
public interface ProductoPeriodoDao {

	@SuppressWarnings("rawtypes")
	public Result getProductosPeriodos( Map parameters, List productosPeriodosList ) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result saveProductoPeriodo(Map parameters) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result deleteProductoPeriodo( Map parameters ) throws Exception;

}
