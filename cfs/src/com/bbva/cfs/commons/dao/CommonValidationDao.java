package com.bbva.cfs.commons.dao;

import com.bbva.cfs.commons.model.Result;

public interface CommonValidationDao {
	
	public Result validatePeriodoFact(String periodD, String periodH, String cdProveedor,Boolean busquedaConcil) throws Exception;

}
