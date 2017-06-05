package com.bbva.cfs.proveedor.dao;

import java.util.List;
import java.util.Map;

import com.bbva.cfs.commons.model.Result;

/**
 * TODO:
 * @author xah1198
 *
 */
public interface ProveedorTipoCambioDao {

	@SuppressWarnings("rawtypes")
	public Result getProveedoresTipoCambio(Map parameters, List proveedorTipoCambioList) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result saveProveedorTipoCambio(Map parameters) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result deleteProveedorTipoCambio(Map parameters) throws Exception;

}
