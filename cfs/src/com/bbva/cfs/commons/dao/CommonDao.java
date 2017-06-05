package com.bbva.cfs.commons.dao;

import java.util.List;
import java.util.Map;

import com.bbva.cfs.commons.model.Result;

/**
 * TODO:
 * 
 * @author xah1198
 * 
 */
public interface CommonDao {

	/**
	 * @author xah1257: Dao que carga la lista de usuario la cual es temporal,
	 *         es solo a efectos de acceder a la aplicaci�n con usuarios que
	 *         tienen distintos perfiles
	 * 
	 * @param users
	 * @return
	 * @throws Exception
	 */
	public Result getUsersAvailable(List users) throws Exception;

}
