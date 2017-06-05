package com.bbva.cfs.sector.service;

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
import com.bbva.cfs.sector.model.Sector;
import com.bbva.cfs.sector.service.CommoSectorService;


/**
 * TODO:
 * @author xah1198
 *
 */
public class GetSectoresService extends CommoSectorService {
	private Result result;	
	@SuppressWarnings("rawtypes")
	private Map<String, Comparable> parameters;
	@SuppressWarnings("rawtypes")
	private List sectoresList;
	
	@SuppressWarnings("rawtypes")
	public GetSectoresService(IWebClient iWebClient){
		super(iWebClient);
		this.parameters = new HashMap<String, Comparable>();
		this.sectoresList = new ArrayList();
	}
	
	/**
	 * TODO:
	 * @return
	 */
	public Result execute() throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"  , "3");
		this.sectoresList.clear();
		try {
			log.debug("Invocación al dao getSectorDAO: GetSectoresService()");
			result = getSectorDao().getSectores(this.parameters, this.sectoresList);
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
	public Result execute(String cdSector) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"  , "3");
		this.parameters.put("pi_CD_SECTOR"  , cdSector);
		this.sectoresList.clear();
		try {
			log.debug("Invocación al dao getProductoDAO: GetProductosService()");
			result = getSectorDao().getSectores(this.parameters, this.sectoresList);
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

	public Result saveSector(String opcion, String cdSector, String nbSector, 
				String nbSectorAbrev, String cdSectorAlt, String stHabilitado, 
				String usuario) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"       , opcion);
		this.parameters.put("pi_CD_SECTOR"       , cdSector);
		this.parameters.put("pi_NB_SECTOR"       , nbSector);
		this.parameters.put("pi_NB_SECTORABREV"  , nbSectorAbrev);
		this.parameters.put("pi_CD_SECTOR_ALT"   , cdSectorAlt);
		this.parameters.put("pi_ST_HABILITADO"   , stHabilitado);
		this.parameters.put("pi_USU_MODI"        , usuario);	
		
		try {
			log.debug("Invocación al dao getSectorDao: saveSector()");
			result = getSectorDao().saveSector(this.parameters);
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
	
	public Result deleteSector(String cdSector) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion" , "4");
		this.parameters.put("pi_CD_SECTOR" , cdSector);
		
		try {
			log.debug("Invocación al dao getSectorDao: deleteSector()");
			result = getSectorDao().deleteSector(this.parameters);
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
	public List getSectoresList() {
		return sectoresList;
	}

	@SuppressWarnings("rawtypes")
	public void setSectoresList(List sectoresList) {
		this.sectoresList = sectoresList;
	}
}
