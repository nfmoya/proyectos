package com.bbva.cfs.general.dao;

import java.util.List;
import java.util.Map;

import com.bbva.cfs.commons.model.Result;

/**
 * TODO:
 * @author xah1198
 *
 */
public interface TablaDao {

	@SuppressWarnings("rawtypes")
	public Result getTablas( Map parameters, List tablasList ) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result saveTabla(Map parameters) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result deleteTabla( Map parameters ) throws Exception;

}
