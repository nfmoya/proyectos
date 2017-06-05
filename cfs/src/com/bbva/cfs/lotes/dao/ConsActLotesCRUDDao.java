/**
 * Clase Interface para la pantalla de Consulta y Actualización de Lotes
 * 
 * @author Alexis Comerci
 */

package com.bbva.cfs.lotes.dao;

import java.util.List;

import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.lotes.form.ConsActLotesForm;
import com.bbva.cfs.lotes.model.ConsActLotesModel;

public interface ConsActLotesCRUDDao {
	
	public Result getConsActLotes(List<ConsActLotesModel> dcList, ConsActLotesForm form ) throws Exception;

	public Result anular(String tipoLote, String nroLote, String user) throws Exception;
}
