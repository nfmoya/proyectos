package com.bbva.cfs.proveedor.dao.impl;

//import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.bbva.common.config.SamConfig;
import com.bbva.cfs.proveedor.dao.ProveedorDao;
import com.bbva.cfs.proveedor.model.Proveedor;
//import com.bbva.cfs.users.dao.impl.UserDaoImpl;
import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.DaoUtils;
import com.bbva.cfs.commons.utils.DatabaseConstants;
//import com.bbva.cfs.commons.utils.DateFormatCustomize;
//import com.bbva.cfs.commons.utils.DateUtils;
import com.bbva.cfs.commons.utils.ResultDatabase;
//import com.bbva.cfs.parameters.model.Parameter;

/**
 * 
 * @author xah1198
 * 
 */
public class ProveedorDaoImpl implements ProveedorDao {

	static final Log log = LogFactory.getLog(ProveedorDaoImpl.class);
	private SAMWebClient samWebClient;

	private Result result;

	/* Getters & Setters */
	public SAMWebClient getSamWebClient() {
		return samWebClient;
	}

	public ProveedorDaoImpl(SAMWebClient samWebClient) {
		this.samWebClient = samWebClient;
		this.result = new Result();
	}

	/**
	 * M�todo que trae el grupo familiar de un candidato.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Result getProveedores(Map parameters, List proveedoresList) throws Exception {
		try {
			SamConfig.getSam().execute(DatabaseConstants.ABMC_PROVEEDOR_INVOKER, getSamWebClient().getSamContext(),
					parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
			ArrayList dynaListResult = (ArrayList) parameters.get(DatabaseConstants.PO_CURSOR);

			Proveedor proveedores;
			Iterator it = dynaListResult.iterator();
			while (it.hasNext()) {
				DynaBean dynaBean = (DynaBean) it.next();

				proveedores = new Proveedor();

				proveedores.setCdProveedor(
						dynaBean.get("CD_PROVEEDOR") != null ? dynaBean.get("CD_PROVEEDOR").toString() : "");

				proveedores.setNbProveedor(
						dynaBean.get("NB_PROVEEDOR") != null ? dynaBean.get("NB_PROVEEDOR").toString() : "");

				proveedores.setNbProveedorCorto(
						dynaBean.get("NB_PROVEEDORCORTO") != null ? dynaBean.get("NB_PROVEEDORCORTO").toString() : "");

				proveedores.setNuCuit(dynaBean.get("NU_CUIT") != null ? Long.valueOf(dynaBean.get("NU_CUIT").toString())
						: Long.valueOf("0"));

				proveedores.setNbAtributoProv1(
						dynaBean.get("NB_ATRIBUTOPROV1") != null ? dynaBean.get("NB_ATRIBUTOPROV1").toString() : "");

				proveedores.setNbAtributoProv2(
						dynaBean.get("NB_ATRIBUTOPROV2") != null ? dynaBean.get("NB_ATRIBUTOPROV2").toString() : "");

				if (dynaBean.get("NB_ATRIBUTOPROV3") != null) {
					String str = dynaBean.get("NB_ATRIBUTOPROV3").toString().substring(0, 4) + ","
							+ dynaBean.get("NB_ATRIBUTOPROV3").toString().substring(4, 6);
					proveedores.setNbAtributoProv3(str.replaceFirst("^0*", ""));
				}else{
					proveedores.setNbAtributoProv3("");
				}

				if (dynaBean.get("NB_ATRIBUTOPROV4") != null) {
					String str = dynaBean.get("NB_ATRIBUTOPROV4").toString().substring(0, 4) + ","
							+ dynaBean.get("NB_ATRIBUTOPROV4").toString().substring(4, 6);
					proveedores.setNbAtributoProv4(str.replaceFirst("^0*", ""));
				}else{
					proveedores.setNbAtributoProv4("");
				}

//				proveedores.setNbAtributoProv4(dynaBean.get("") != null
//						? dynaBean.get("NB_ATRIBUTOPROV4").toString().replaceFirst("^0*", "") : "");

				proveedores.setNbAtributoProv5(
						dynaBean.get("NB_ATRIBUTOPROV5") != null ? dynaBean.get("NB_ATRIBUTOPROV5").toString() : "");

				proveedores.setStHabilitado(
						dynaBean.get("ST_HABILITADO") != null ? dynaBean.get("ST_HABILITADO").toString() : "");

				proveedoresList.add(proveedores);
			}
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

	@SuppressWarnings("rawtypes")
	public Result saveProveedor(Map parameters) throws Exception {
		try {
			// Nombre de la constante del procedimiento de la base de datos
			SamConfig.getSam().execute(DatabaseConstants.ABMC_PROVEEDOR_INVOKER, getSamWebClient().getSamContext(),
					parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
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

	@SuppressWarnings("rawtypes")
	public Result deleteProveedor(Map parameters) throws Exception {
		try {
			SamConfig.getSam().execute(DatabaseConstants.ABMC_PROVEEDOR_INVOKER, getSamWebClient().getSamContext(),
					parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
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
}
