package com.bbva.cfs.producto.dao;

import java.util.List;
import java.util.Map;

import com.bbva.cfs.commons.model.Result;

/**
 * TODO:
 * @author xah1198
 *
 */
public interface ProductoSectorDao {

	@SuppressWarnings("rawtypes")
	public Result getProductosSectores( Map parameters, List productosSectoresList ) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result saveProductoSector(Map parameters) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result deleteProductoSector( Map parameters ) throws Exception;

}
