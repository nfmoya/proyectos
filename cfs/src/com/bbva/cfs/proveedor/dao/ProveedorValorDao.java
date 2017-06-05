package com.bbva.cfs.proveedor.dao;

import java.util.List;
import java.util.Map;

import com.bbva.cfs.commons.model.Result;

/**
 * TODO:
 * @author xah1198
 *
 */
public interface ProveedorValorDao {

	@SuppressWarnings("rawtypes")
	public Result getProveedoresValores( Map parameters, List proveedoresValoresList ) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result saveProveedorValor(Map parameters) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result deleteProveedorValor( Map parameters ) throws Exception;

}
