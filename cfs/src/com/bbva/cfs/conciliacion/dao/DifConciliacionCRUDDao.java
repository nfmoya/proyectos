/**
 * Clase Interface para la pantalla de Diferencia de Conciliaciones
 * 
 * @author David
 */

package com.bbva.cfs.conciliacion.dao;

import java.util.List;

import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.conciliacion.form.DiferenciaConciliacionForm;
import com.bbva.cfs.conciliacion.model.DiferenciaConciliacionModel;

public interface DifConciliacionCRUDDao {
	
	public Result getDifConciliacion(List<DiferenciaConciliacionModel> dcList, DiferenciaConciliacionForm form ) throws Exception;

	public Result edit(DiferenciaConciliacionForm dcf, String user) throws Exception;
}
