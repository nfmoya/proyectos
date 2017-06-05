package com.bbva.cfs.servprest.dao;

import java.util.List;
import java.util.Map;

import com.bbva.cfs.commons.model.Result;

public interface ServPrestDao {
	
	@SuppressWarnings("rawtypes")
	public Result getServPrest( Map parameters, List productosList, int cant ) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result getServPrestReduc( Map parameters, List productosList, int cant ) throws Exception;

    @SuppressWarnings("rawtypes")
    public Result getServPrestPeriodos( Map parameters, List servPrestPeriodosList ) throws Exception;
    
	@SuppressWarnings("rawtypes")
	public Result getServPrest2( Map parameters, List productosList ) throws Exception;
}
