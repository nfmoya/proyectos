package com.bbva.cfs.producto.dao;

import java.util.List;
import java.util.Map;

import com.bbva.cfs.commons.model.Result;

/**
 * TODO:
 * @author xah1198
 *
 */
public interface ProductoDao {

	@SuppressWarnings("rawtypes")
	public Result getProductos( Map parameters, List productosList ) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result saveProducto(Map parameters) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result deleteProducto( Map parameters ) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public Result verificaExistenciaAgrupados( Map parameters ) throws Exception;

}
