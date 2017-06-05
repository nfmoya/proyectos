package com.bbva.cfs.servfact.dao;

import java.util.List;
import java.util.Map;

import com.bbva.cfs.commons.model.Result;

/**
 * TODO:
 * @author xah1198
 *
 */
public interface ServFactDao {

	@SuppressWarnings("rawtypes")
	public Result getServFact( Map parameters, List servList, int cant ) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result getServFactReduc( Map parameters, List servList, int cant ) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public Result getServFactDet( Map parameters, List servList ) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public Result getServFact2( Map parameters, List servList ) throws Exception;



}
