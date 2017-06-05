package com.bbva.cfs.commons.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.IWebConstants;
import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.bbva.cfs.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bbva.common.config.SamConfig;
import com.bbva.cfs.commons.dao.LoginDao;
import com.bbva.cfs.commons.exceptions.DaoException;

import com.bbva.cfs.commons.model.MenuItem;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.DaoUtils;
import com.bbva.cfs.commons.utils.DatabaseConstants;
import com.bbva.cfs.commons.utils.General;
import com.bbva.cfs.commons.utils.ResultDatabase;
import com.bbva.cfs.commons.model.Session;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.commons.beanutils.DynaBean;

/**
 * 
 * @author xah1198
 * 
 */
public class LoginDaoImpl implements LoginDao {
	
	static final org.apache.commons.logging.Log log = LogFactory.getLog(LoginDaoImpl.class);

	private SAMWebClient samWebClient;
	
	private Result result;
	
	public LoginDaoImpl() {
	}

	public LoginDaoImpl(SAMWebClient samWebClient) {
		this.samWebClient = samWebClient;
		this.result = new Result();
	}

	/**
	 * Metodo que obtiene los privilegios desde la base de datos.
	 * 
	 * @param Map
	 *            privileges - Privilegios
	 * @return Result r - resultado del metodo
	 */
	public Result getSession( Map parameters ) throws Exception {
		try {

			SamConfig.getSam().execute(DatabaseConstants.INIT_SESSION_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			
			DaoUtils.proccessServiceResponse(parameters, this.result,log);
			
			Session session = new Session();
			
			session.setUserName(parameters.get("d_user") != null ? parameters.get("d_user").toString() : "");
			session.setUserId(parameters.get("po_usuario_id") != null ? Long.valueOf(""+((BigDecimal)parameters.get("po_usuario_id")).toBigInteger().longValue()) : Long.valueOf("0"));
			session.setSepLista(parameters.get("po_sep_listas") != null ? parameters.get("po_sep_listas").toString() : "");
			session.setSepSubLista(parameters.get("po_sep_sublistas") != null ? parameters.get("po_sep_sublistas").toString() : "");
			session.setFullname(parameters.get("po_full_name") != null ? parameters.get("po_full_name").toString() : "");
			session.setSector(parameters.get("po_sector") != null ? parameters.get("po_sector").toString() : "");
			session.setIdPerfil(parameters.get("po_id_perfil") != null ? Long.valueOf(""+((BigDecimal)parameters.get("po_id_perfil")).toBigInteger().longValue()) : 0L );

			ArrayList dynaListResult = (ArrayList) parameters.get(DatabaseConstants.PO_CURSOR);
			DynaBean dynaBean;
			for (int i=0; i<dynaListResult.size(); i++) {
				dynaBean = (DynaBean)dynaListResult.get(i);
				session.getPrivileges().put((dynaBean.get("id_acceso") != null ? dynaBean.get("id_acceso").toString():""), "");
			}
			
			parameters.put("session", session);
			
		} catch (TransactionException e) {
			log.debug(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
						ResultDatabase.SAM_CONECTION_ERROR.getMessage());
	
		} catch (Exception e) {
			log.debug(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					    ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}

		return result;
	}

	/**
	 * Metodo que obtiene el menu desde la base de datos en funcion de los
	 * perfiles del usuario.
	 * 
	 * @param Map
	 *            menuCompleto - Map on los 3 niveles del menu
	 * @param Long
	 *            userId - id del usuario
	 * @return Result r - resultado del metodo
	 */
	public Result getMenu( Map parameters ) throws Exception {
		try {
			ArrayList padresList = new ArrayList();
			ArrayList hijosList = new ArrayList();
			ArrayList nietosList = new ArrayList();
			
			SamConfig.getSam().execute("TM_DBCFS.CONSULTAR_MENU",
					getSamWebClient().getSamContext(), parameters);
			
			DaoUtils.proccessServiceResponse(parameters, this.result,log);
			
			MenuItem menuItem; 
			Long orden;
			
			ArrayList dynaListResult = (ArrayList) parameters.get(DatabaseConstants.PO_CURSOR);
			DynaBean dynaBean;
			for (int i=0; i<dynaListResult.size(); i++) {
				dynaBean = (DynaBean)dynaListResult.get(i);
				orden = dynaBean.get("n_nivel") != null ? Long.valueOf(""+((BigDecimal) dynaBean.get("n_nivel")).toBigInteger().longValue()) : Long.valueOf("0");
				
				menuItem = new MenuItem();
				menuItem.setId(dynaBean.get("id_menu") != null ? ""+((BigDecimal) dynaBean.get("id_menu")).toBigInteger().longValue() : "");
				menuItem.setDescripcion(dynaBean.get("d_menu") != null ? dynaBean.get("d_menu").toString() : "");
				menuItem.setUrl(dynaBean.get("x_url_menu") != null ? dynaBean.get("x_url_menu").toString() : "");
				menuItem.setIdPadre(dynaBean.get("id_padre") != null ? ""+((BigDecimal) dynaBean.get("id_padre")).toBigInteger().longValue() : "");
				
				if (orden.equals(Long.valueOf("1"))){
					padresList.add(menuItem);
				} else if (orden.equals(Long.valueOf("2"))) {
					hijosList.add(menuItem);
				} else if (orden.equals(Long.valueOf("3"))) {
					nietosList.add(menuItem);
				}
				
			}
			
			parameters.put("LISTAPADRES", padresList);
			parameters.put("LISTAHIJOS", hijosList);
			parameters.put("LISTANIETOS", nietosList);
			
		} catch (TransactionException e) {
			log.debug(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
						ResultDatabase.SAM_CONECTION_ERROR.getMessage());
	
		} catch (Exception e) {
			log.debug(e);
			throw new DaoException(ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					    ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}

		return result;
	}
	
	/* Getters & Setters */
	public SAMWebClient getSamWebClient() {
		return samWebClient;
	}
	
}
