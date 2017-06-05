package com.bbva.cfs.parameters.dao;

import java.util.List;
import java.util.Map;

import com.bbva.cfs.commons.model.Result;



/**
 * TODO:
 * @author xah1198
 *
 */
public interface ParametersDao {
	
	/**
	 * TODO:
	 * @param parameterList
	 * @return result
	 */
	public Result getParameterTypeList(List parameterList) throws Exception;
	
	/**
	 * TODO:
	 * @param parameterList
	 * @return result
	 */
	public Result getParameterList(String parameterTypeId, String pi_filtro_1, String pi_filtro_2, 
								   List parameterListm) throws Exception;
	
	/**
	 * TODO:
	 * @param parameters
	 * @return result
	 */
	public Result addParameter(Map parameters) throws Exception;
	
	/**
	 * TODO:
	 * @param parameters
	 * @return result
	 */
	public Result modParameter(Map parameters) throws Exception;
	
	/**
	 * TODO:
	 * @param parameters
	 * @return result
	 */
	public Result delParameter(Map parameters) throws Exception;


}
