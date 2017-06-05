package com.bbva.cfs.servprestdetalle.dao;

import java.util.List;
import java.util.Map;

import com.bbva.cfs.commons.model.Result;

public interface ServPrestDetalleDao {
	
	@SuppressWarnings("rawtypes")
	public Result getServPrestDetalle( Map parameters, List productosList) throws Exception;
}
