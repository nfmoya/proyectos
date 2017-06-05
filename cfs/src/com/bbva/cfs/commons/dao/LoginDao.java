package com.bbva.cfs.commons.dao;

import java.util.ArrayList;
import java.util.Map;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.model.Result;





/**
 * TODO:
 * @author xah1198
 *
 */
public interface LoginDao {
	
	/**
	 * TODO:
	 */
	public Result getSession( Map parameters ) throws Exception; 
	
	/**
	 * TODO:
	 * @param parameters
	 * @param oWebClient
	 * @return
	 * @throws Exception
	 */
	public Result getMenu ( Map parameters ) throws Exception; 
	
}
