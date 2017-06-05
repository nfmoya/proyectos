package com.bbva.cfs.nomedibles.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.bbva.cfs.commons.model.Result;

/**
 * TODO:
 * @author xah1198
 *
 */
public interface NoMediblesDao {

	@SuppressWarnings("rawtypes")
	public Result getConciliaciones( Map parameters, List conciliacionesList ) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result getConciliacionCabecera( Map parameters, List conciliacionCabeceraList ) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result saveConciliacion( Map parameters, List conciliacionNro ) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result saveConciliacionDetalle( Map parameters ) throws Exception;

}
