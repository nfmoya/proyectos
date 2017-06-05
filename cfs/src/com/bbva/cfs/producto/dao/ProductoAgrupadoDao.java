package com.bbva.cfs.producto.dao;

import java.util.List;
import java.util.Map;

import com.bbva.cfs.commons.model.Result;

/**
 * TODO:
 * @author xah1198
 *
 */
public interface ProductoAgrupadoDao {

	@SuppressWarnings("rawtypes")
	public Result getProductosAgrupados( Map parameters, List productosAgrupadosList ) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result saveProductoAgrupado(Map parameters) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result deleteProductoAgrupado( Map parameters ) throws Exception;
	
}
