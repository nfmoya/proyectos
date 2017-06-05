package com.bbva.cfs.general.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
//import com.bbva.cfs.commons.logging.Log;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.ResultDatabase;


/**
 * TODO:
 * @author xah1198
 *
 */
public class GetTablasService extends CommoTablaService {
	private Result result;	
	@SuppressWarnings("rawtypes")
	private Map<String, Comparable> parameters;
	@SuppressWarnings("rawtypes")
	private List tablasList;
	
	@SuppressWarnings("rawtypes")
	public GetTablasService(IWebClient iWebClient){
		super(iWebClient);
		this.parameters = new HashMap<String, Comparable>();
		this.tablasList = new ArrayList();
	}
	
	/**
	 * TODO:
	 * @return
	 */
	public Result execute() throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion" , "3");
		this.tablasList.clear();
		try {
			log.debug("Invocaci�n al dao getTablaDao: getTablas()");
			result = getTablaDao().getTablas(this.parameters, this.tablasList);
		} catch (DaoException e) {
			result = new Result();
			result.setErrorCode(e.getCode());
			result.setErrorDesc(e.getMessage());

		} catch (Exception e) {
			log.debug(e);
			throw new GlobalServiceException(ResultDatabase.SERVICE_ERROR.getCode(),
								ResultDatabase.SERVICE_ERROR.getMessage());
		}
		return result;
	}

	/**
	 * TODO:
	 * @return
	 */
	public Result execute(String cdTabla) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion" , "3");
		this.parameters.put("pi_CD_TABLA"  , cdTabla);
		this.tablasList.clear();
		try {
			log.debug("Invocaci�n al dao getTablaDao: getTablas()");
			result = getTablaDao().getTablas(this.parameters, this.tablasList);
		} catch (DaoException e) {
			result = new Result();
			result.setErrorCode(e.getCode());
			result.setErrorDesc(e.getMessage());

		} catch (Exception e) {
			log.debug(e);
			throw new GlobalServiceException(ResultDatabase.SERVICE_ERROR.getCode(),
								ResultDatabase.SERVICE_ERROR.getMessage());
		}
		return result;
	}

	/**
	 * TODO:
	 * @return
	 */
	public Result execute(String cdTabla, String cdCodTabla) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"   , "3");
		this.parameters.put("pi_CD_TABLA"    , cdTabla);
		this.parameters.put("pi_CD_CODTABLA" , cdCodTabla);
		this.tablasList.clear();
		try {
			log.debug("Invocaci�n al dao getTablaDao: getTablas()");
			result = getTablaDao().getTablas(this.parameters, this.tablasList);
		} catch (DaoException e) {
			result = new Result();
			result.setErrorCode(e.getCode());
			result.setErrorDesc(e.getMessage());

		} catch (Exception e) {
			log.debug(e);
			throw new GlobalServiceException(ResultDatabase.SERVICE_ERROR.getCode(),
								ResultDatabase.SERVICE_ERROR.getMessage());
		}
		return result;
	}

	public Result saveTabla(String opcion, String cdTabla, String cdCodTabla, String nbCodTabla,  
				String nbCodTablaCorto, String nbAtributoTabla1, String nbAtributoTabla2, 
				String nbAtributoTabla3, String stHabilitado, String usuario) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"         , opcion);
		this.parameters.put("pi_CD_TABLA"          , cdTabla);
		this.parameters.put("pi_CD_CODTABLA"       , cdCodTabla);
		this.parameters.put("pi_NB_CODTABLA"       , nbCodTabla);
		this.parameters.put("pi_NB_CODTABLACORTO"  , nbCodTablaCorto);
		this.parameters.put("pi_NB_ATRIBUTOTABLA1" , nbAtributoTabla1);
		this.parameters.put("pi_NB_ATRIBUTOTABLA2" , nbAtributoTabla2);
		this.parameters.put("pi_NB_ATRIBUTOTABLA3" , nbAtributoTabla3);
		this.parameters.put("pi_ST_HABILITADO"     , stHabilitado);
		this.parameters.put("pi_USU_MODI"          , usuario);	
		
		try {
			log.debug("Invocaci�n al dao getTablaDao: saveTabla()");
			result = getTablaDao().saveTabla(this.parameters);
		} catch (DaoException e) {
			System.out.println("FAAAAAAAAA!");
			System.out.println("FAAAAAAAAA!");
			System.out.println("FAAAAAAAAA!");
			System.out.println("FAAAAAAAAA!");
			System.out.println("FAAAAAAAAA!");
			result = new Result();
			result.setErrorCode(e.getCode());
			result.setErrorDesc(e.getMessage());
		} catch (Exception e) {
			System.out.println("FEEEEEEEEEEE!");
			System.out.println("FEEEEEEEEEEE!");
			System.out.println("FEEEEEEEEEEE!");
			System.out.println("FEEEEEEEEEEE!");
			System.out.println("FEEEEEEEEEEE!");
			System.out.println("FEEEEEEEEEEE!");
			System.out.println("FEEEEEEEEEEE!");
			log.debug(e);
			throw new GlobalServiceException(ResultDatabase.SERVICE_ERROR.getCode(),
								ResultDatabase.SERVICE_ERROR.getMessage());
		}
		return result;
	}
	
	public Result deleteTabla(String cdTabla, String cdCodTabla) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"   , "4");
		this.parameters.put("pi_CD_TABLA"    , cdTabla);
		this.parameters.put("pi_CD_CODTABLA" , cdCodTabla);
		
		try {
			log.debug("Invocaci�n al dao getTablaDao: deleteTabla()");
			result = getTablaDao().deleteTabla(this.parameters);
		} catch (DaoException e) {
			result = new Result();
			result.setErrorCode(e.getCode());
			result.setErrorDesc(e.getMessage());
		} catch (Exception e) {
			log.debug(e);
			throw new GlobalServiceException(ResultDatabase.SERVICE_ERROR.getCode(),
								ResultDatabase.SERVICE_ERROR.getMessage());
		}
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public List getTablasList() {
		return tablasList;
	}

	@SuppressWarnings("rawtypes")
	public void setTablasList(List tablasList) {
		this.tablasList = tablasList;
	}
}
