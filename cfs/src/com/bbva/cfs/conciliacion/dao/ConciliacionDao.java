package com.bbva.cfs.conciliacion.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.bbva.cfs.commons.model.Result;

/**
 * TODO:
 * @author xah1198
 *
 */
public interface ConciliacionDao {

	@SuppressWarnings("rawtypes")
	public Result getConciliaciones( Map parameters, List conciliacionesList ) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result getConciliacionCabecera( Map parameters, List conciliacionCabeceraList ) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result saveConciliacion( Map parameters, List conciliacionNro ) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result saveConciliacionPrestados( Map parameters ) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result saveConciliacionFacturados( Map parameters ) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result getRepetidos( Map parameters, List repetidosList ) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result getProductosConciliacion( Map parameters, List productosList ) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public Result buscarConciliaciones(Map parameters,
			List busqConciliacionesList) throws Exception;

	@SuppressWarnings("rawtypes")
	public Result anulacionConciliacion(Map parameters) throws Exception;
}
