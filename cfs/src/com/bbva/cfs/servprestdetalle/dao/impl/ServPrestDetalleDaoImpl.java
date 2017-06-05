package com.bbva.cfs.servprestdetalle.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.bbva.web.impl.SAMWebClient;
import ar.com.itrsa.sam.TransactionException;

import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.DaoUtils;
import com.bbva.cfs.commons.utils.DatabaseConstants;
import com.bbva.cfs.commons.utils.ResultDatabase;
import com.bbva.cfs.servprestdetalle.model.ServPrestDetalle;
import com.bbva.cfs.servprestdetalle.dao.ServPrestDetalleDao;
import com.bbva.common.config.SamConfig;

public class ServPrestDetalleDaoImpl implements ServPrestDetalleDao {
	
	static final Log log = LogFactory.getLog(ServPrestDetalleDaoImpl.class);
	private SAMWebClient samWebClient;

	private Result result;

	/* Getters & Setters */
	public SAMWebClient getSamWebClient() {
		return samWebClient;
	}

	public ServPrestDetalleDaoImpl(SAMWebClient samWebClient) {
		this.samWebClient = samWebClient;
		this.result = new Result();
	}

	/**
	 * M�todo que trae el grupo familiar de un candidato.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Result getServPrestDetalle(Map parameters, List sectoresList)
			throws Exception {
		try {
			SamConfig.getSam().execute(
					DatabaseConstants.CONSULTA_SERV_PREST_DETALLE_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
			ArrayList dynaListResult = (ArrayList) parameters
					.get(DatabaseConstants.PO_CURSOR);
                        
			ServPrestDetalle servPrestDetalle;
                        
			Iterator it = dynaListResult.iterator();
                        
			while (it.hasNext()) {

				DynaBean dynaBean = (DynaBean) it.next();

				servPrestDetalle = new ServPrestDetalle();
                                
                servPrestDetalle.setCdSector(dynaBean.get("CD_SECTOR") != null ? dynaBean.get("CD_SECTOR").toString() : "");
                                
				servPrestDetalle.setCdGrupoProducto(dynaBean.get("CD_GRUPOPRODUCTO") != null ? dynaBean.get("CD_GRUPOPRODUCTO").toString() : "");
                                
				servPrestDetalle.setCdProducto(dynaBean.get("CD_PRODUCTO") != null ? dynaBean.get("CD_PRODUCTO").toString() : "");
                            
                servPrestDetalle.setNbDescripcion(dynaBean.get("NB_DESCRIPCION") != null ? dynaBean.get("NB_DESCRIPCION").toString() : "");

                servPrestDetalle.setCdLoteServ(Integer.valueOf(dynaBean.get("CD_LOTESERV") != null ? dynaBean.get("CD_LOTESERV").toString() : "0"));

                servPrestDetalle.setCdRemito(dynaBean.get("CD_REMITO") != null ? dynaBean.get("CD_REMITO").toString() : "");
                
                servPrestDetalle.setFhRemito(dynaBean.get("FH_REMITO") != null ? dynaBean.get("FH_REMITO").toString() : "");
                
                servPrestDetalle.setFhFinServ(dynaBean.get("FH_FIN_SERV") != null ? dynaBean.get("FH_FIN_SERV").toString() : "");

                servPrestDetalle.setCtServPrest(new BigDecimal(dynaBean.get("CT_SERVPREST") != null ? dynaBean.get("CT_SERVPREST").toString() : "0"));
                
                servPrestDetalle.setImPrecioTotal(new BigDecimal(dynaBean.get("IM_PRECIOTOTAL") != null ? dynaBean.get("IM_PRECIOTOTAL").toString() : "0"));

                servPrestDetalle.setNbObservaciones(dynaBean.get("NB_OBSERVACIONES") != null ? dynaBean.get("NB_OBSERVACIONES").toString() : "");

                servPrestDetalle.setStLoteDet(dynaBean.get("ST_LOTEDET") != null ? dynaBean.get("ST_LOTEDET").toString() : "");

                servPrestDetalle.setCdConciliacion(Integer.valueOf(dynaBean.get("CD_CONCILIACION") != null ? dynaBean.get("CD_CONCILIACION").toString() : "0"));
                
                servPrestDetalle.setNbAtributoRef1(dynaBean.get("NB_ATRIBUTOREF1") != null ? dynaBean.get("NB_ATRIBUTOREF1").toString() : "");

                servPrestDetalle.setNbAtributoRef2(dynaBean.get("NB_ATRIBUTOREF2") != null ? dynaBean.get("NB_ATRIBUTOREF2").toString() : "");

                servPrestDetalle.setNbTipVal(dynaBean.get("NB_TIPVAL") != null ? dynaBean.get("NB_TIPVAL").toString() : "");
                
				sectoresList.add(servPrestDetalle);
			}
		} catch (TransactionException e) {
			log.debug(e);
			e.printStackTrace();
			throw new DaoException(
					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		} catch (Exception e) {
			log.debug(e);
			e.printStackTrace();
			throw new DaoException(
					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}
		return result;
	}
}
