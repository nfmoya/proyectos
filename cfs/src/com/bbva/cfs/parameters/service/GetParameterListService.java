package com.bbva.cfs.parameters.service;

import java.util.ArrayList;
import java.util.List;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.ResultDatabase;

/**
 * TODO:
 * 
 * @author xah1198
 * 
 */
public class GetParameterListService extends CommonParamService {
	@SuppressWarnings("rawtypes")
	private List parameterList;

	public GetParameterListService(IWebClient iWebClient) {
		super(iWebClient);
	}

	/**
	 * TODO xah1257: Crear un Helper que retorne cada tipo de par�metro
	 * existente de est� evitariamos pasar el id del tipo de par�metro y se
	 * invocar�a a un servicio que retorne directamente cada de tipo de
	 * par�metro, otra de las cuestiones es evitar tener que crear varias
	 * instancias de este servicio cada vez que se precise
	 * 
	 * @param parameterTypeId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Result execute(String parameterTypeId, String pi_filtro_1, String pi_filtro_2) throws Exception {
		this.parameterList = new ArrayList();
		this.parameterList.clear();
		try {
			log.info("Invocaci�n al dao ParametersDAO: getParameterList("+parameterTypeId+","+ pi_filtro_1 +"," +pi_filtro_2 + "," +  this.parameterList+")" );
			result = getParamDao().getParameterList(parameterTypeId, pi_filtro_1, pi_filtro_2, this.parameterList);

		} catch (DaoException e) {
			result.setErrorCode(e.getCode());
			result.setErrorDesc(e.getMessage());

		} catch (Exception e) {
			log.error(e);
			throw new GlobalServiceException(ResultDatabase.SERVICE_ERROR
					.getCode(), ResultDatabase.SERVICE_ERROR.getMessage());
		}

		return result;
	}

	public List getParameterList() {
		return parameterList;
	}

	public void setParameterList(List parameterList) {
		this.parameterList = parameterList;
	}

}
