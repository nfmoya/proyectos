package com.bbva.cfs.sector.dao;

import java.util.List;
import java.util.Map;

import com.bbva.cfs.commons.model.Result;

/**
 * TODO:
 * @author xah1198
 *
 */
public interface SectorDao {

	@SuppressWarnings("rawtypes")
	public Result getSectores( Map parameters, List productosList ) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result saveSector(Map parameters) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result deleteSector( Map parameters ) throws Exception;

}
