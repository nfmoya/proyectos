package com.bbva.cfs.producto.dao;

import java.util.List;
import java.util.Map;

import com.bbva.cfs.commons.model.Result;

/**
 * TODO:
 * @author xah1198
 *
 */
public interface ProductoPrecioDao {

	@SuppressWarnings("rawtypes")
	public Result getProductosPrecios( Map parameters, List productosPreciosList ) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result saveProductoPrecio(Map parameters) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result deleteProductoPrecio( Map parameters ) throws Exception;

}
