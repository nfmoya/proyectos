package com.bbva.cfs.proveedor.dao;

import java.util.List;
import java.util.Map;

import com.bbva.cfs.commons.model.Result;

/**
 * TODO:
 * @author xah1198
 *
 */
public interface ProveedorDao {

	@SuppressWarnings("rawtypes")
	public Result getProveedores( Map parameters, List proveedoresList ) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result saveProveedor(Map parameters) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result deleteProveedor( Map parameters ) throws Exception;

}
