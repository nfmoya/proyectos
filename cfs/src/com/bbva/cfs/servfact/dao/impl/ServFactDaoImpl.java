package com.bbva.cfs.servfact.dao.impl;

//import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import com.bbva.cfs.servfact.dao.ServFactDao;
import com.bbva.cfs.servfact.model.ServFact;
import com.bbva.cfs.servfact.model.ServFactDet;
import com.bbva.cfs.servfact.model.ServFactShort;
//import com.bbva.cfs.users.dao.impl.UserDaoImpl;
import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.DaoUtils;
import com.bbva.cfs.commons.utils.DatabaseConstants;
//import com.bbva.cfs.commons.utils.DateFormatCustomize;
//import com.bbva.cfs.commons.utils.DateUtils;
import com.bbva.cfs.commons.utils.ResultDatabase;

//import com.bbva.cfs.parameters.model.Parameter;

public class ServFactDaoImpl implements ServFactDao {

	static final Log log = LogFactory.getLog(ServFactDaoImpl.class);
	private SAMWebClient samWebClient;

	private Result result;

	/* Getters & Setters */
	public SAMWebClient getSamWebClient() {
		return samWebClient;
	}

	public ServFactDaoImpl(SAMWebClient samWebClient) {
		this.samWebClient = samWebClient;
		this.result = new Result();
	}

	/**
	 * M�todo que trae el grupo familiar de un candidato.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Result getServFact(Map parameters, List sectoresList, int cant)
			throws Exception {
		try {
			SamConfig.getSam().execute(
					DatabaseConstants.CONSULTA_SERV_FACT_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
			ArrayList dynaListResult = (ArrayList) parameters
					.get(DatabaseConstants.PO_CURSOR);
//			String query  = (String) parameters
//					.get("po_d_query");
//			System.out.println("| " + query + " |");
			ServFact servFact;
			Iterator it = dynaListResult.iterator();
			while (it.hasNext()) {

				DynaBean dynaBean = (DynaBean) it.next();

				servFact = new ServFact();

				servFact.setCdSector(dynaBean.get("CD_SECTORCONCIL") != null ? dynaBean
						.get("CD_SECTORCONCIL").toString() : "");
				servFact.setCdGrupoProd(dynaBean.get("CD_GRUPOPRODUCTO") != null ? dynaBean
						.get("CD_GRUPOPRODUCTO").toString() : "");
				servFact.setCdProd(dynaBean.get("CD_PRODUCTO") != null ? dynaBean
						.get("CD_PRODUCTO").toString() : "");
//				System.out.println("prod  :"+servFact.getCdProd());
				servFact.setCtServFactMes1(Long.valueOf(dynaBean
						.get("MES1CANTFACT") != null ? dynaBean.get(
						"MES1CANTFACT").toString() : "0"));
				servFact.setValServFactMes1(new BigDecimal(dynaBean
						.get("MES1SERVFACT") != null ? dynaBean.get(
						"MES1SERVFACT").toString() : "0"));
				servFact.setCtRemitosMes1(Long
						.valueOf(dynaBean.get("MES1REM") != null ? dynaBean
								.get("MES1REM").toString() : "0"));
				servFact.setCtRemitosAConcilMes1(Long.valueOf(dynaBean
						.get("MES1REMCON") != null ? dynaBean.get("MES1REMCON")
						.toString() : "0"));
				if (servFact.getCdGrupoProd().equals("NO_CON")) {
					servFact.setCtRemitosMes1(Long.valueOf("0"));
					servFact.setCtRemitosAConcilMes1(Long.valueOf("0"));					
				}
				if (cant >= 2) {
					servFact.setCtServFactMes2(Long.valueOf(dynaBean
							.get("MES2CANTFACT") != null ? dynaBean.get(
							"MES2CANTFACT").toString() : "0"));
					servFact.setValServFactMes2(new BigDecimal(dynaBean
							.get("MES2SERVFACT") != null ? dynaBean.get(
							"MES2SERVFACT").toString() : "0"));
					servFact.setCtRemitosMes2(Long.valueOf(dynaBean
							.get("MES2REM") != null ? dynaBean.get("MES2REM")
							.toString() : "0"));
					servFact.setCtRemitosAConcilMes2(Long.valueOf(dynaBean
							.get("MES2REMCON") != null ? dynaBean.get(
							"MES2REMCON").toString() : "0"));
					if (servFact.getCdGrupoProd().equals("NO_CON")) {
						servFact.setCtRemitosMes2(Long.valueOf(servFact.getCtServFactMes2()/servFact.getCtServFactMes1()));
						servFact.setCtRemitosAConcilMes2((servFact.getValServFactMes2().divide(servFact.getValServFactMes1(),0, RoundingMode.CEILING)).longValue());					
					}
				}
				if (cant >= 3) {
					servFact.setCtServFactMes3(Long.valueOf(dynaBean
							.get("MES3CANTFACT") != null ? dynaBean.get(
							"MES3CANTFACT").toString() : "0"));
					servFact.setValServFactMes3(new BigDecimal(dynaBean
							.get("MES3SERVFACT") != null ? dynaBean.get(
							"MES3SERVFACT").toString() : "0"));
					servFact.setCtRemitosMes3(Long.valueOf(dynaBean
							.get("MES3REM") != null ? dynaBean.get("MES3REM")
							.toString() : "0"));
					servFact.setCtRemitosAConcilMes3(Long.valueOf(dynaBean
							.get("MES3REMCON") != null ? dynaBean.get(
							"MES3REMCON").toString() : "0"));
					if (servFact.getCdGrupoProd().equals("NO_CON")) {
						servFact.setCtRemitosMes3(Long.valueOf(servFact.getCtServFactMes3()/servFact.getCtServFactMes2()));
						servFact.setCtRemitosAConcilMes3((servFact.getValServFactMes3().divide(servFact.getValServFactMes2(),0, RoundingMode.CEILING)).longValue());					
					}
				}
				if (cant >= 4) {
					servFact.setCtServFactMes4(Long.valueOf(dynaBean
							.get("MES4CANTFACT") != null ? dynaBean.get(
							"MES4CANTFACT").toString() : "0"));
					servFact.setValServFactMes4(new BigDecimal(dynaBean
							.get("MES4SERVFACT") != null ? dynaBean.get(
							"MES4SERVFACT").toString() : "0"));
					servFact.setCtRemitosMes4(Long.valueOf(dynaBean
							.get("MES4REM") != null ? dynaBean.get("MES4REM")
							.toString() : "0"));
					servFact.setCtRemitosAConcilMes4(Long.valueOf(dynaBean
							.get("MES4REMCON") != null ? dynaBean.get(
							"MES4REMCON").toString() : "0"));
					if (servFact.getCdGrupoProd().equals("NO_CON")) {
						servFact.setCtRemitosMes4(Long.valueOf(servFact.getCtServFactMes4()/servFact.getCtServFactMes3()));
						servFact.setCtRemitosAConcilMes4((servFact.getValServFactMes4().divide(servFact.getValServFactMes3(),0, RoundingMode.CEILING)).longValue());					
					}
				}
				if (cant >= 5) {
					servFact.setCtServFactMes5(Long.valueOf(dynaBean
							.get("MES5CANTFACT") != null ? dynaBean.get(
							"MES5CANTFACT").toString() : "0"));
					servFact.setValServFactMes5(new BigDecimal(dynaBean
							.get("MES5SERVFACT") != null ? dynaBean.get(
							"MES5SERVFACT").toString() : "0"));
					servFact.setCtRemitosMes5(Long.valueOf(dynaBean
							.get("MES5REM") != null ? dynaBean.get("MES5REM")
							.toString() : "0"));
					servFact.setCtRemitosAConcilMes5(Long.valueOf(dynaBean
							.get("MES5REMCON") != null ? dynaBean.get(
							"MES5REMCON").toString() : "0"));
					if (servFact.getCdGrupoProd().equals("NO_CON")) {
						servFact.setCtRemitosMes5(Long.valueOf(servFact.getCtServFactMes5()/servFact.getCtServFactMes4()));
						servFact.setCtRemitosAConcilMes5((servFact.getValServFactMes5().divide(servFact.getValServFactMes4(),0, RoundingMode.CEILING)).longValue());					
					}
				}
				if (cant >= 6) {
					servFact.setCtServFactMes6(Long.valueOf(dynaBean
							.get("MES6CANTFACT") != null ? dynaBean.get(
							"MES6CANTFACT").toString() : "0"));
					servFact.setValServFactMes6(new BigDecimal(dynaBean
							.get("MES6SERVFACT") != null ? dynaBean.get(
							"MES6SERVFACT").toString() : "0"));
					servFact.setCtRemitosMes6(Long.valueOf(dynaBean
							.get("MES6REM") != null ? dynaBean.get("MES6REM")
							.toString() : "0"));
					servFact.setCtRemitosAConcilMes6(Long.valueOf(dynaBean
							.get("MES6REMCON") != null ? dynaBean.get(
							"MES6REMCON").toString() : "0"));
					if (servFact.getCdGrupoProd().equals("NO_CON")) {
						servFact.setCtRemitosMes6(Long.valueOf(servFact.getCtServFactMes6()/servFact.getCtServFactMes5()));
						servFact.setCtRemitosAConcilMes6((servFact.getValServFactMes6().divide(servFact.getValServFactMes5(),0, RoundingMode.CEILING)).longValue());					
					}
				}
				if (cant >= 7) {
					servFact.setCtServFactMes7(Long.valueOf(dynaBean
							.get("MES7CANTFACT") != null ? dynaBean.get(
							"MES7CANTFACT").toString() : "0"));
					servFact.setValServFactMes7(new BigDecimal(dynaBean
							.get("MES7SERVFACT") != null ? dynaBean.get(
							"MES7SERVFACT").toString() : "0"));
					servFact.setCtRemitosMes7(Long.valueOf(dynaBean
							.get("MES7REM") != null ? dynaBean.get("MES7REM")
							.toString() : "0"));
					servFact.setCtRemitosAConcilMes7(Long.valueOf(dynaBean
							.get("MES7REMCON") != null ? dynaBean.get(
							"MES7REMCON").toString() : "0"));
					if (servFact.getCdGrupoProd().equals("NO_CON")) {
						servFact.setCtRemitosMes7(Long.valueOf(servFact.getCtServFactMes7()/servFact.getCtServFactMes6()));
						servFact.setCtRemitosAConcilMes7((servFact.getValServFactMes7().divide(servFact.getValServFactMes6(),0, RoundingMode.CEILING)).longValue());					
					}
				}
				if (cant >= 8) {
					servFact.setCtServFactMes8(Long.valueOf(dynaBean
							.get("MES8CANTFACT") != null ? dynaBean.get(
							"MES8CANTFACT").toString() : "0"));
					servFact.setValServFactMes8(new BigDecimal(dynaBean
							.get("MES8SERVFACT") != null ? dynaBean.get(
							"MES8SERVFACT").toString() : "0"));
					servFact.setCtRemitosMes8(Long.valueOf(dynaBean
							.get("MES8REM") != null ? dynaBean.get("MES8REM")
							.toString() : "0"));
					servFact.setCtRemitosAConcilMes8(Long.valueOf(dynaBean
							.get("MES8REMCON") != null ? dynaBean.get(
							"MES8REMCON").toString() : "0"));
					if (servFact.getCdGrupoProd().equals("NO_CON")) {
						servFact.setCtRemitosMes8(Long.valueOf(servFact.getCtServFactMes8()/servFact.getCtServFactMes7()));
						servFact.setCtRemitosAConcilMes8((servFact.getValServFactMes8().divide(servFact.getValServFactMes7(),0, RoundingMode.CEILING)).longValue());					
					}
				}
				if (cant >= 9) {
					servFact.setCtServFactMes9(Long.valueOf(dynaBean
							.get("MES9CANTFACT") != null ? dynaBean.get(
							"MES9CANTFACT").toString() : "0"));
					servFact.setValServFactMes9(new BigDecimal(dynaBean
							.get("MES9SERVFACT") != null ? dynaBean.get(
							"MES9SERVFACT").toString() : "0"));
					servFact.setCtRemitosMes9(Long.valueOf(dynaBean
							.get("MES9REM") != null ? dynaBean.get("MES9REM")
							.toString() : "0"));
					servFact.setCtRemitosAConcilMes9(Long.valueOf(dynaBean
							.get("MES9REMCON") != null ? dynaBean.get(
							"MES9REMCON").toString() : "0"));
					if (servFact.getCdGrupoProd().equals("NO_CON")) {
						servFact.setCtRemitosMes9(Long.valueOf(servFact.getCtServFactMes9()/servFact.getCtServFactMes8()));
						servFact.setCtRemitosAConcilMes9((servFact.getValServFactMes9().divide(servFact.getValServFactMes8(),0, RoundingMode.CEILING)).longValue());					
					}
				}
				if (cant >= 10) {
					servFact.setCtServFactMes10(Long.valueOf(dynaBean
							.get("MES10CANTFACT") != null ? dynaBean.get(
							"MES10CANTFACT").toString() : "0"));
					servFact.setValServFactMes10(new BigDecimal(dynaBean
							.get("MES10SERVFACT") != null ? dynaBean.get(
							"MES10SERVFACT").toString() : "0"));
					servFact.setCtRemitosMes10(Long.valueOf(dynaBean
							.get("MES10REM") != null ? dynaBean.get("MES10REM")
							.toString() : "0"));
					servFact.setCtRemitosAConcilMes10(Long.valueOf(dynaBean
							.get("MES10REMCON") != null ? dynaBean.get(
							"MES10REMCON").toString() : "0"));
					if (servFact.getCdGrupoProd().equals("NO_CON")) {
						servFact.setCtRemitosMes10(Long.valueOf(servFact.getCtServFactMes10()/servFact.getCtServFactMes9()));
						servFact.setCtRemitosAConcilMes10((servFact.getValServFactMes10().divide(servFact.getValServFactMes9(),0, RoundingMode.CEILING)).longValue());					
					}
				}
				if (cant >= 11) {
					servFact.setCtServFactMes11(Long.valueOf(dynaBean
							.get("MES11CANTFACT") != null ? dynaBean.get(
							"MES11CANTFACT").toString() : "0"));
					servFact.setValServFactMes11(new BigDecimal(dynaBean
							.get("MES11SERVFACT") != null ? dynaBean.get(
							"MES11SERVFACT").toString() : "0"));
					servFact.setCtRemitosMes11(Long.valueOf(dynaBean
							.get("MES11REM") != null ? dynaBean.get("MES11REM")
							.toString() : "0"));
					servFact.setCtRemitosAConcilMes11(Long.valueOf(dynaBean
							.get("MES11REMCON") != null ? dynaBean.get(
							"MES11REMCON").toString() : "0"));
					if (servFact.getCdGrupoProd().equals("NO_CON")) {
						servFact.setCtRemitosMes11(Long.valueOf(servFact.getCtServFactMes11()/servFact.getCtServFactMes10()));
						servFact.setCtRemitosAConcilMes11((servFact.getValServFactMes11().divide(servFact.getValServFactMes10(),0, RoundingMode.CEILING)).longValue());					
					}
				}
				if (cant >= 12) {
					servFact.setCtServFactMes12(Long.valueOf(dynaBean
							.get("MES12CANTFACT") != null ? dynaBean.get(
							"MES12CANTFACT").toString() : "0"));
					servFact.setValServFactMes12(new BigDecimal(dynaBean
							.get("MES12SERVFACT") != null ? dynaBean.get(
							"MES12SERVFACT").toString() : "0"));
					servFact.setCtRemitosMes12(Long.valueOf(dynaBean
							.get("MES12REM") != null ? dynaBean.get("MES12REM")
							.toString() : "0"));
					servFact.setCtRemitosAConcilMes12(Long.valueOf(dynaBean
							.get("MES12REMCON") != null ? dynaBean.get(
							"MES12REMCON").toString() : "0"));
					if (servFact.getCdGrupoProd().equals("NO_CON")) {
						servFact.setCtRemitosMes12(Long.valueOf(servFact.getCtServFactMes12()/servFact.getCtServFactMes11()));
						servFact.setCtRemitosAConcilMes12((servFact.getValServFactMes12().divide(servFact.getValServFactMes11(),0, RoundingMode.CEILING)).longValue());					
					}
				}
				servFact.setCtServFactTotal(Long.valueOf(dynaBean
						.get("SERVFACT") != null ? dynaBean.get(
						"SERVFACT").toString() : "0"));
				servFact.setValServFactTotal(new BigDecimal(dynaBean
						.get("VALORFACT") != null ? dynaBean.get(
						"VALORFACT").toString() : "0"));
				servFact.setValBrutaTotal(new BigDecimal(dynaBean
						.get("VALORFACTBRUTO") != null ? dynaBean.get("VALORFACTBRUTO")
						.toString() : "0"));
				servFact.setValNetoTotal(new BigDecimal(dynaBean
						.get("VALORFACTNETO") != null ? dynaBean.get(
						"VALORFACTNETO").toString() : "0"));
				servFact.setCtRemitosTotal(Long.valueOf(dynaBean
						.get("TOTALREMITOS") != null ? dynaBean.get(
						"TOTALREMITOS").toString() : "0"));
				servFact.setCtRemitosAConcilTotal(Long.valueOf(dynaBean
						.get("TOTALREMITOSACON") != null ? dynaBean.get(
						"TOTALREMITOSACON").toString() : "0"));
				servFact.setNuPorcVarMax(Integer.valueOf(dynaBean
						.get("NU_PORCVARMAX") != null ? dynaBean.get(
						"NU_PORCVARMAX").toString() : "0"));
				sectoresList.add(servFact);
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Result getServFactReduc(Map parameters, List sectoresList, int cant)
			throws Exception {
		try {
			SamConfig.getSam().execute(
					DatabaseConstants.CONSULTA_SERV_FACT_RED_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
			ArrayList dynaListResult = (ArrayList) parameters.get(DatabaseConstants.PO_CURSOR);
//			String query  = (String) parameters
//					.get("po_d_query");
//			System.out.println("| " + query + " |");
			ServFact servFact;
			Iterator it = dynaListResult.iterator();
			while (it.hasNext()) {

				DynaBean dynaBean = (DynaBean) it.next();

				servFact = new ServFact();

				servFact.setCdSector(dynaBean.get("CD_SECTORCONCIL") != null ? dynaBean
						.get("CD_SECTORCONCIL").toString() : "");
				servFact.setCdGrupoProd(dynaBean.get("CD_GRUPOPRODUCTO") != null ? dynaBean
						.get("CD_GRUPOPRODUCTO").toString() : "");
				servFact.setCdProd("");
//				System.out.println("prod  :"+servFact.getCdProd());
				servFact.setCtServFactMes1(Long.valueOf(dynaBean
						.get("MES1CANTFACT") != null ? dynaBean.get(
						"MES1CANTFACT").toString() : "0"));
				servFact.setValServFactMes1(new BigDecimal(dynaBean
						.get("MES1SERVFACT") != null ? dynaBean.get(
						"MES1SERVFACT").toString() : "0"));
				servFact.setCtRemitosMes1(Long
						.valueOf(dynaBean.get("MES1REM") != null ? dynaBean
								.get("MES1REM").toString() : "0"));
				servFact.setCtRemitosAConcilMes1(Long.valueOf(dynaBean
						.get("MES1REMCON") != null ? dynaBean.get("MES1REMCON")
						.toString() : "0"));
				if (servFact.getCdGrupoProd().equals("NO_CON")) {
					servFact.setCtRemitosMes1(Long.valueOf("0"));
					servFact.setCtRemitosAConcilMes1(Long.valueOf("0"));					
				}				
				if (cant >= 2) {
					servFact.setCtServFactMes2(Long.valueOf(dynaBean
							.get("MES2CANTFACT") != null ? dynaBean.get(
							"MES2CANTFACT").toString() : "0"));
					servFact.setValServFactMes2(new BigDecimal(dynaBean
							.get("MES2SERVFACT") != null ? dynaBean.get(
							"MES2SERVFACT").toString() : "0"));
					servFact.setCtRemitosMes2(Long.valueOf(dynaBean
							.get("MES2REM") != null ? dynaBean.get("MES2REM")
							.toString() : "0"));
					servFact.setCtRemitosAConcilMes2(Long.valueOf(dynaBean
							.get("MES2REMCON") != null ? dynaBean.get(
							"MES2REMCON").toString() : "0"));
					if (servFact.getCdGrupoProd().equals("NO_CON")) {
						servFact.setCtRemitosMes2(Long.valueOf(servFact.getCtServFactMes2()/servFact.getCtServFactMes1()));
						servFact.setCtRemitosAConcilMes2((servFact.getValServFactMes2().divide(servFact.getValServFactMes1(),0, RoundingMode.CEILING)).longValue());					
					}					
				}
				if (cant >= 3) {
					servFact.setCtServFactMes3(Long.valueOf(dynaBean
							.get("MES3CANTFACT") != null ? dynaBean.get(
							"MES3CANTFACT").toString() : "0"));
					servFact.setValServFactMes3(new BigDecimal(dynaBean
							.get("MES3SERVFACT") != null ? dynaBean.get(
							"MES3SERVFACT").toString() : "0"));
					servFact.setCtRemitosMes3(Long.valueOf(dynaBean
							.get("MES3REM") != null ? dynaBean.get("MES3REM")
							.toString() : "0"));
					servFact.setCtRemitosAConcilMes3(Long.valueOf(dynaBean
							.get("MES3REMCON") != null ? dynaBean.get(
							"MES3REMCON").toString() : "0"));
					if (servFact.getCdGrupoProd().equals("NO_CON")) {
						servFact.setCtRemitosMes3(Long.valueOf(servFact.getCtServFactMes3()/servFact.getCtServFactMes2()));
						servFact.setCtRemitosAConcilMes3((servFact.getValServFactMes3().divide(servFact.getValServFactMes2(),0, RoundingMode.CEILING)).longValue());					
					}
				}
				if (cant >= 4) {
					servFact.setCtServFactMes4(Long.valueOf(dynaBean
							.get("MES4CANTFACT") != null ? dynaBean.get(
							"MES4CANTFACT").toString() : "0"));
					servFact.setValServFactMes4(new BigDecimal(dynaBean
							.get("MES4SERVFACT") != null ? dynaBean.get(
							"MES4SERVFACT").toString() : "0"));
					servFact.setCtRemitosMes4(Long.valueOf(dynaBean
							.get("MES4REM") != null ? dynaBean.get("MES4REM")
							.toString() : "0"));
					servFact.setCtRemitosAConcilMes4(Long.valueOf(dynaBean
							.get("MES4REMCON") != null ? dynaBean.get(
							"MES4REMCON").toString() : "0"));
					if (servFact.getCdGrupoProd().equals("NO_CON")) {
						servFact.setCtRemitosMes4(Long.valueOf(servFact.getCtServFactMes4()/servFact.getCtServFactMes3()));
						servFact.setCtRemitosAConcilMes4((servFact.getValServFactMes4().divide(servFact.getValServFactMes3(),0, RoundingMode.CEILING)).longValue());					
					}
				}
				if (cant >= 5) {
					servFact.setCtServFactMes5(Long.valueOf(dynaBean
							.get("MES5CANTFACT") != null ? dynaBean.get(
							"MES5CANTFACT").toString() : "0"));
					servFact.setValServFactMes5(new BigDecimal(dynaBean
							.get("MES5SERVFACT") != null ? dynaBean.get(
							"MES5SERVFACT").toString() : "0"));
					servFact.setCtRemitosMes5(Long.valueOf(dynaBean
							.get("MES5REM") != null ? dynaBean.get("MES5REM")
							.toString() : "0"));
					servFact.setCtRemitosAConcilMes5(Long.valueOf(dynaBean
							.get("MES5REMCON") != null ? dynaBean.get(
							"MES5REMCON").toString() : "0"));
					if (servFact.getCdGrupoProd().equals("NO_CON")) {
						servFact.setCtRemitosMes5(Long.valueOf(servFact.getCtServFactMes5()/servFact.getCtServFactMes4()));
						servFact.setCtRemitosAConcilMes5((servFact.getValServFactMes5().divide(servFact.getValServFactMes4(),0, RoundingMode.CEILING)).longValue());					
					}
				}
				if (cant >= 6) {
					servFact.setCtServFactMes6(Long.valueOf(dynaBean
							.get("MES6CANTFACT") != null ? dynaBean.get(
							"MES6CANTFACT").toString() : "0"));
					servFact.setValServFactMes6(new BigDecimal(dynaBean
							.get("MES6SERVFACT") != null ? dynaBean.get(
							"MES6SERVFACT").toString() : "0"));
					servFact.setCtRemitosMes6(Long.valueOf(dynaBean
							.get("MES6REM") != null ? dynaBean.get("MES6REM")
							.toString() : "0"));
					servFact.setCtRemitosAConcilMes6(Long.valueOf(dynaBean
							.get("MES6REMCON") != null ? dynaBean.get(
							"MES6REMCON").toString() : "0"));
					if (servFact.getCdGrupoProd().equals("NO_CON")) {
						servFact.setCtRemitosMes6(Long.valueOf(servFact.getCtServFactMes6()/servFact.getCtServFactMes5()));
						servFact.setCtRemitosAConcilMes6((servFact.getValServFactMes6().divide(servFact.getValServFactMes5(),0, RoundingMode.CEILING)).longValue());					
					}
				}
				if (cant >= 7) {
					servFact.setCtServFactMes7(Long.valueOf(dynaBean
							.get("MES7CANTFACT") != null ? dynaBean.get(
							"MES7CANTFACT").toString() : "0"));
					servFact.setValServFactMes7(new BigDecimal(dynaBean
							.get("MES7SERVFACT") != null ? dynaBean.get(
							"MES7SERVFACT").toString() : "0"));
					servFact.setCtRemitosMes7(Long.valueOf(dynaBean
							.get("MES7REM") != null ? dynaBean.get("MES7REM")
							.toString() : "0"));
					servFact.setCtRemitosAConcilMes7(Long.valueOf(dynaBean
							.get("MES7REMCON") != null ? dynaBean.get(
							"MES7REMCON").toString() : "0"));
					if (servFact.getCdGrupoProd().equals("NO_CON")) {
						servFact.setCtRemitosMes7(Long.valueOf(servFact.getCtServFactMes7()/servFact.getCtServFactMes6()));
						servFact.setCtRemitosAConcilMes7((servFact.getValServFactMes7().divide(servFact.getValServFactMes6(),0, RoundingMode.CEILING)).longValue());					
					}
				}
				if (cant >= 8) {
					servFact.setCtServFactMes8(Long.valueOf(dynaBean
							.get("MES8CANTFACT") != null ? dynaBean.get(
							"MES8CANTFACT").toString() : "0"));
					servFact.setValServFactMes8(new BigDecimal(dynaBean
							.get("MES8SERVFACT") != null ? dynaBean.get(
							"MES8SERVFACT").toString() : "0"));
					servFact.setCtRemitosMes8(Long.valueOf(dynaBean
							.get("MES8REM") != null ? dynaBean.get("MES8REM")
							.toString() : "0"));
					servFact.setCtRemitosAConcilMes8(Long.valueOf(dynaBean
							.get("MES8REMCON") != null ? dynaBean.get(
							"MES8REMCON").toString() : "0"));
					if (servFact.getCdGrupoProd().equals("NO_CON")) {
						servFact.setCtRemitosMes8(Long.valueOf(servFact.getCtServFactMes8()/servFact.getCtServFactMes7()));
						servFact.setCtRemitosAConcilMes8((servFact.getValServFactMes8().divide(servFact.getValServFactMes7(),0, RoundingMode.CEILING)).longValue());					
					}
				}
				if (cant >= 9) {
					servFact.setCtServFactMes9(Long.valueOf(dynaBean
							.get("MES9CANTFACT") != null ? dynaBean.get(
							"MES9CANTFACT").toString() : "0"));
					servFact.setValServFactMes9(new BigDecimal(dynaBean
							.get("MES9SERVFACT") != null ? dynaBean.get(
							"MES9SERVFACT").toString() : "0"));
					servFact.setCtRemitosMes9(Long.valueOf(dynaBean
							.get("MES9REM") != null ? dynaBean.get("MES9REM")
							.toString() : "0"));
					servFact.setCtRemitosAConcilMes9(Long.valueOf(dynaBean
							.get("MES9REMCON") != null ? dynaBean.get(
							"MES9REMCON").toString() : "0"));
					if (servFact.getCdGrupoProd().equals("NO_CON")) {
						servFact.setCtRemitosMes9(Long.valueOf(servFact.getCtServFactMes9()/servFact.getCtServFactMes8()));
						servFact.setCtRemitosAConcilMes9((servFact.getValServFactMes9().divide(servFact.getValServFactMes8(),0, RoundingMode.CEILING)).longValue());					
					}
				}
				if (cant >= 10) {
					servFact.setCtServFactMes10(Long.valueOf(dynaBean
							.get("MES10CANTFACT") != null ? dynaBean.get(
							"MES10CANTFACT").toString() : "0"));
					servFact.setValServFactMes10(new BigDecimal(dynaBean
							.get("MES10SERVFACT") != null ? dynaBean.get(
							"MES10SERVFACT").toString() : "0"));
					servFact.setCtRemitosMes10(Long.valueOf(dynaBean
							.get("MES10REM") != null ? dynaBean.get("MES10REM")
							.toString() : "0"));
					servFact.setCtRemitosAConcilMes10(Long.valueOf(dynaBean
							.get("MES10REMCON") != null ? dynaBean.get(
							"MES10REMCON").toString() : "0"));
					if (servFact.getCdGrupoProd().equals("NO_CON")) {
						servFact.setCtRemitosMes10(Long.valueOf(servFact.getCtServFactMes10()/servFact.getCtServFactMes9()));
						servFact.setCtRemitosAConcilMes10((servFact.getValServFactMes10().divide(servFact.getValServFactMes9(),0, RoundingMode.CEILING)).longValue());					
					}
				}
				if (cant >= 11) {
					servFact.setCtServFactMes11(Long.valueOf(dynaBean
							.get("MES11CANTFACT") != null ? dynaBean.get(
							"MES11CANTFACT").toString() : "0"));
					servFact.setValServFactMes11(new BigDecimal(dynaBean
							.get("MES11SERVFACT") != null ? dynaBean.get(
							"MES11SERVFACT").toString() : "0"));
					servFact.setCtRemitosMes11(Long.valueOf(dynaBean
							.get("MES11REM") != null ? dynaBean.get("MES11REM")
							.toString() : "0"));
					servFact.setCtRemitosAConcilMes11(Long.valueOf(dynaBean
							.get("MES11REMCON") != null ? dynaBean.get(
							"MES11REMCON").toString() : "0"));
					if (servFact.getCdGrupoProd().equals("NO_CON")) {
						servFact.setCtRemitosMes11(Long.valueOf(servFact.getCtServFactMes11()/servFact.getCtServFactMes10()));
						servFact.setCtRemitosAConcilMes11((servFact.getValServFactMes11().divide(servFact.getValServFactMes10(),0, RoundingMode.CEILING)).longValue());					
					}
				}
				if (cant >= 12) {
					servFact.setCtServFactMes12(Long.valueOf(dynaBean
							.get("MES12CANTFACT") != null ? dynaBean.get(
							"MES12CANTFACT").toString() : "0"));
					servFact.setValServFactMes12(new BigDecimal(dynaBean
							.get("MES12SERVFACT") != null ? dynaBean.get(
							"MES12SERVFACT").toString() : "0"));
					servFact.setCtRemitosMes12(Long.valueOf(dynaBean
							.get("MES12REM") != null ? dynaBean.get("MES12REM")
							.toString() : "0"));
					servFact.setCtRemitosAConcilMes12(Long.valueOf(dynaBean
							.get("MES12REMCON") != null ? dynaBean.get(
							"MES12REMCON").toString() : "0"));
					if (servFact.getCdGrupoProd().equals("NO_CON")) {
						servFact.setCtRemitosMes12(Long.valueOf(servFact.getCtServFactMes12()/servFact.getCtServFactMes11()));
						servFact.setCtRemitosAConcilMes12((servFact.getValServFactMes12().divide(servFact.getValServFactMes11(),0, RoundingMode.CEILING)).longValue());					
					}
				}
				servFact.setCtServFactTotal(Long.valueOf(dynaBean
						.get("SERVFACT") != null ? dynaBean.get(
						"SERVFACT").toString() : "0"));
				servFact.setValServFactTotal(new BigDecimal(dynaBean
						.get("VALORFACT") != null ? dynaBean.get(
						"VALORFACT").toString() : "0"));
				servFact.setValBrutaTotal(new BigDecimal(dynaBean
						.get("VALORFACTBRUTO") != null ? dynaBean.get("VALORFACTBRUTO")
						.toString() : "0"));
				servFact.setValNetoTotal(new BigDecimal(dynaBean
						.get("VALORFACTNETO") != null ? dynaBean.get(
						"VALORFACTNETO").toString() : "0"));
				servFact.setCtRemitosTotal(Long.valueOf(dynaBean
						.get("TOTALREMITOS") != null ? dynaBean.get(
						"TOTALREMITOS").toString() : "0"));
				servFact.setCtRemitosAConcilTotal(Long.valueOf(dynaBean
						.get("TOTALREMITOSACON") != null ? dynaBean.get(
						"TOTALREMITOSACON").toString() : "0"));
				servFact.setNuPorcVarMax(Integer.valueOf(dynaBean
						.get("NU_PORCVARMAX") != null ? dynaBean.get(
						"NU_PORCVARMAX").toString() : "0"));
				sectoresList.add(servFact);
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
/**
 * 
 */
	@SuppressWarnings("unchecked")
	public Result getServFactDet(Map parameters, List servList)
			throws Exception {
		try {
			SamConfig.getSam().execute(
					DatabaseConstants.CONSULTA_SERV_FACT_DET_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
			ArrayList dynaListResult = (ArrayList) parameters
					.get(DatabaseConstants.PO_CURSOR);
			ServFactDet servFact;
			Iterator it = dynaListResult.iterator();
			while (it.hasNext()) {

				DynaBean dynaBean = (DynaBean) it.next();

				servFact = new ServFactDet();

				servFact.setCdSector(dynaBean.get("CD_SECTORCONCIL") != null ? dynaBean
						.get("CD_SECTORCONCIL").toString() : "");
				servFact.setCdGrupoProd(dynaBean.get("CD_GRUPOPRODUCTO") != null ? dynaBean
						.get("CD_GRUPOPRODUCTO").toString() : "");
				servFact.setCdProd(dynaBean.get("CD_PRODUCTO") != null ? dynaBean
						.get("CD_PRODUCTO").toString() : "");
				servFact.setDsProd(dynaBean.get("NB_PRODUCTO") != null ? dynaBean
						.get("NB_PRODUCTO").toString() : "");
				servFact.setCdLoteFact(dynaBean.get("CD_LOTEFACT") != null ? dynaBean
						.get("CD_LOTEFACT").toString() : "");
				servFact.setCdPerFact(dynaBean.get("CD_PERIODOFACT") != null ? dynaBean
						.get("CD_PERIODOFACT").toString() : "");
				servFact.setCdTipComp(dynaBean.get("TP_COMPROBANTE") != null ? dynaBean
						.get("TP_COMPROBANTE").toString() : "");
				servFact.setNuComp(dynaBean.get("NU_COMPROBANTE") != null ? dynaBean
						.get("NU_COMPROBANTE").toString() : "");
				servFact.setCdRemito(dynaBean.get("CD_REMITO") != null ? dynaBean
						.get("CD_REMITO").toString() : "");
				servFact.setFechaRemito(dynaBean.get("FH_REMITO") != null ? dynaBean
						.get("FH_REMITO").toString() : "");
				servFact.setFechaCierre(dynaBean.get("FH_FIN_SERV") != null ? dynaBean
						.get("FH_FIN_SERV").toString() : "");
				servFact.setCtFact(new BigDecimal(dynaBean
						.get("CT_SERVFACT") != null ? dynaBean.get(
						"CT_SERVFACT").toString() : "0"));
				servFact.setNuValor(new BigDecimal(dynaBean
						.get("IM_PRECIOTOTAL") != null ? dynaBean.get(
						"IM_PRECIOTOTAL").toString() : "0"));
				servFact.setObserv(dynaBean.get("NB_OBSERVACIONES") != null ? dynaBean
						.get("NB_OBSERVACIONES").toString() : "");
				servFact.setStLote(dynaBean.get("ST_LOTEDET") != null ? dynaBean
						.get("ST_LOTEDET").toString() : "");
				servFact.setCdConciliacion(dynaBean.get("CD_CONCILIACION") != null ? dynaBean
						.get("CD_CONCILIACION").toString() : "");
				
				
				servList.add(servFact);
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


	@SuppressWarnings("unchecked")
	public Result getServFact2(Map parameters, List servList)
			throws Exception {
		try {
			System.out.println(parameters);
			SamConfig.getSam().execute(
					DatabaseConstants.CONSULTA_SERV_FACT2_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
			ArrayList dynaListResult = (ArrayList) parameters
					.get(DatabaseConstants.PO_CURSOR);
			ServFactShort servFact;
			Iterator it = dynaListResult.iterator();
			int i = 0;
			while (it.hasNext()) {
				DynaBean dynaBean = (DynaBean) it.next();

				servFact = new ServFactShort();

				servFact.setCdSector(dynaBean.get("CD_SECTORCONCIL") != null ? dynaBean
						.get("CD_SECTORCONCIL").toString() : "");
				servFact.setCdGrupoProd(dynaBean.get("CD_GRUPOPRODUCTO") != null ? dynaBean
						.get("CD_GRUPOPRODUCTO").toString() : "");
				servFact.setCdProd(dynaBean.get("CD_PRODUCTO") != null ? dynaBean
						.get("CD_PRODUCTO").toString() : "");
				servFact.setCtServFact(Long.valueOf(dynaBean
							.get("CANTFACT") != null ? dynaBean.get(
							"CANTFACT").toString() : "0"));
				servFact.setValServFact(new BigDecimal(dynaBean
							.get("SERVFACT") != null ? dynaBean.get(
							"SERVFACT").toString() : "0"));
				servFact.setCtRemitos(Long.valueOf(dynaBean
							.get("CANTREM") != null ? dynaBean.get("CANTREM")
							.toString() : "0"));
				servFact.setCtRemitosAConci(Long.valueOf(dynaBean
							.get("CANTREMCON") != null ? dynaBean.get(
							"CANTREMCON").toString() : "0"));
				servFact.setValNeto(new BigDecimal(dynaBean
						.get("CT_NETO") != null ? dynaBean.get(
						"CT_NETO").toString() : "0"));
				servFact.setValBruto(new BigDecimal(dynaBean
						.get("CT_BRUTO") != null ? dynaBean.get("CT_BRUTO")
						.toString() : "0"));
				servFact.setCdPeriodo(dynaBean.get("CD_PERIODOFACT") != null ? dynaBean
						.get("CD_PERIODOFACT").toString() : "");
				
				servFact.setNuPorcVarMax(Integer.valueOf(dynaBean.get("NU_PORCVARMAX") != null ? dynaBean
						.get("NU_PORCVARMAX").toString() : "0"));
				
				
				servList.add(servFact);
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
