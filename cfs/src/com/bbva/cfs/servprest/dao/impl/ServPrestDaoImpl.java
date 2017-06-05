package com.bbva.cfs.servprest.dao.impl;

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
import com.bbva.cfs.servprest.dao.impl.ServPrestDaoImpl;
import com.bbva.cfs.servprest.model.ServPrest;
import com.bbva.cfs.servprest.model.ServPrestShort;
import com.bbva.cfs.servprest.dao.ServPrestDao;
import com.bbva.common.config.SamConfig;
import com.bbva.cfs.proveedor.model.ProveedorPeriodo;

public class ServPrestDaoImpl implements ServPrestDao {

	static final Log log = LogFactory.getLog(ServPrestDaoImpl.class);
	private SAMWebClient samWebClient;

	private Result result;

	/* Getters & Setters */
	public SAMWebClient getSamWebClient() {
		return samWebClient;
	}

	public ServPrestDaoImpl(SAMWebClient samWebClient) {
		this.samWebClient = samWebClient;
		this.result = new Result();
	}

	/**
	 * M�todo que trae el grupo familiar de un candidato.
	 */
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public Result getServPrest(Map parameters, List sectoresList, int cant)
//			throws Exception {
//		try {
//
//			System.out.println(parameters);
//			SamConfig.getSam().execute(
//					DatabaseConstants.CONSULTA_SERV_PREST_INVOKER,
//					getSamWebClient().getSamContext(), parameters);
//			DaoUtils.proccessServiceResponse(parameters, this.result, log);
//			ArrayList dynaListResult = (ArrayList) parameters
//					.get(DatabaseConstants.PO_CURSOR);
//			ServPrest servPrest;
//			Iterator it = dynaListResult.iterator();
//			while (it.hasNext()) {
//
//				DynaBean dynaBean = (DynaBean) it.next();
//
//				servPrest = new ServPrest();
//
//				servPrest.setCdSector(dynaBean.get("CD_SECTOR") != null ? dynaBean.get("CD_SECTOR").toString() : "");
//				servPrest.setCdGrupoProd(dynaBean.get("CD_GRUPOPRODUCTO") != null ? dynaBean.get("CD_GRUPOPRODUCTO").toString() : "");
//				servPrest.setCdProd(dynaBean.get("CD_PRODUCTO") != null ? dynaBean.get("CD_PRODUCTO").toString() : "");
//
//				servPrest.setCtServPrestMes1(new BigDecimal(dynaBean.get("MES1SERVPREST") != null ? dynaBean.get("MES1SERVPREST").toString() : "0"));
//				servPrest.setValServPrestMes1(new BigDecimal(dynaBean.get("MES1VALORPREST") != null ? dynaBean.get("MES1VALORPREST").toString() : "0"));
//				servPrest.setCtRemitosMes1(Long.valueOf(dynaBean.get("MES1REM") != null ? dynaBean.get("MES1REM").toString() : "0"));
//				System.out.println("DAO Mes1 "	+ servPrest.getValServPrestMes1());
//
//				servPrest.setCtRemitosAConcilMes1(Long.valueOf(dynaBean.get("MES1REMCON") != null ? dynaBean.get("MES1REMCON").toString() : "0"));
//
//				System.out.println("CANTIDAD ->" + cant);
//
//				if (cant >= 2) {
//
//					servPrest.setCtServPrestMes2(new BigDecimal(dynaBean
//							.get("MES2SERVPREST") != null ? dynaBean.get(
//							"MES2SERVPREST").toString() : "0"));
//					servPrest.setValServPrestMes2(new BigDecimal(dynaBean
//							.get("MES2VALORPREST") != null ? dynaBean.get(
//							"MES2VALORPREST").toString() : "0"));
//					servPrest.setCtRemitosMes2(Long.valueOf(dynaBean
//							.get("MES2REM") != null ? dynaBean.get("MES2REM")
//							.toString() : "0"));
//					servPrest.setCtRemitosAConcilMes2(Long.valueOf(dynaBean
//							.get("MES2REMCON") != null ? dynaBean.get(
//							"MES2REMCON").toString() : "0"));
//
//					System.out
//							.println("mes 2" + servPrest.getCtServPrestMes2());
//				}
//				if (cant >= 3) {
//					servPrest.setCtServPrestMes3(new BigDecimal(dynaBean
//							.get("MES3SERVPREST") != null ? dynaBean.get(
//							"MES3SERVPREST").toString() : "0"));
//					servPrest.setValServPrestMes3(new BigDecimal(dynaBean
//							.get("MES3VALORPREST") != null ? dynaBean.get(
//							"MES3VALORPREST").toString() : "0"));
//					servPrest.setCtRemitosMes3(Long.valueOf(dynaBean
//							.get("MES3REM") != null ? dynaBean.get("MES3REM")
//							.toString() : "0"));
//					servPrest.setCtRemitosAConcilMes3(Long.valueOf(dynaBean
//							.get("MES3REMCON") != null ? dynaBean.get(
//							"MES3REMCON").toString() : "0"));
//				}
//				if (cant >= 4) {
//					servPrest.setCtServPrestMes4(new BigDecimal(dynaBean
//							.get("MES4SERVPREST") != null ? dynaBean.get(
//							"MES4SERVPREST").toString() : "0"));
//					servPrest.setValServPrestMes4(new BigDecimal(dynaBean
//							.get("MES4VALORPREST") != null ? dynaBean.get(
//							"MES4VALORPREST").toString() : "0"));
//					servPrest.setCtRemitosMes4(Long.valueOf(dynaBean
//							.get("MES4REM") != null ? dynaBean.get("MES4REM")
//							.toString() : "0"));
//					servPrest.setCtRemitosAConcilMes4(Long.valueOf(dynaBean
//							.get("MES4REMCON") != null ? dynaBean.get(
//							"MES4REMCON").toString() : "0"));
//				}
//				if (cant >= 5) {
//					servPrest.setCtServPrestMes5(new BigDecimal(dynaBean
//							.get("MES5SERVPREST") != null ? dynaBean.get(
//							"MES5SERVPREST").toString() : "0"));
//					servPrest.setValServPrestMes5(new BigDecimal(dynaBean
//							.get("MES5VALORPREST") != null ? dynaBean.get(
//							"MES5VALORPREST").toString() : "0"));
//					servPrest.setCtRemitosMes5(Long.valueOf(dynaBean
//							.get("MES5REM") != null ? dynaBean.get("MES5REM")
//							.toString() : "0"));
//					servPrest.setCtRemitosAConcilMes5(Long.valueOf(dynaBean
//							.get("MES5REMCON") != null ? dynaBean.get(
//							"MES5REMCON").toString() : "0"));
//				}
//				if (cant >= 6) {
//					servPrest.setCtServPrestMes6(new BigDecimal(dynaBean
//							.get("MES6SERVPREST") != null ? dynaBean.get(
//							"MES6SERVPREST").toString() : "0"));
//					servPrest.setValServPrestMes6(new BigDecimal(dynaBean
//							.get("MES6VALORPREST") != null ? dynaBean.get(
//							"MES6VALORPREST").toString() : "0"));
//					servPrest.setCtRemitosMes6(Long.valueOf(dynaBean
//							.get("MES6REM") != null ? dynaBean.get("MES6REM")
//							.toString() : "0"));
//					servPrest.setCtRemitosAConcilMes6(Long.valueOf(dynaBean
//							.get("MES6REMCON") != null ? dynaBean.get(
//							"MES6REMCON").toString() : "0"));
//				}
//				if (cant >= 7) {
//					servPrest.setCtServPrestMes7(new BigDecimal(dynaBean
//							.get("MES7SERVPREST") != null ? dynaBean.get(
//							"MES7SERVPREST").toString() : "0"));
//					servPrest.setValServPrestMes7(new BigDecimal(dynaBean
//							.get("MES7VALORPREST") != null ? dynaBean.get(
//							"MES7VALORPREST").toString() : "0"));
//					servPrest.setCtRemitosMes7(Long.valueOf(dynaBean
//							.get("MES7REM") != null ? dynaBean.get("MES7REM")
//							.toString() : "0"));
//					servPrest.setCtRemitosAConcilMes7(Long.valueOf(dynaBean
//							.get("MES7REMCON") != null ? dynaBean.get(
//							"MES7REMCON").toString() : "0"));
//				}
//				if (cant >= 8) {
//					servPrest.setCtServPrestMes8(new BigDecimal(dynaBean
//							.get("MES8SERVPREST") != null ? dynaBean.get(
//							"MES8SERVPREST").toString() : "0"));
//					servPrest.setValServPrestMes8(new BigDecimal(dynaBean
//							.get("MES8VALORPREST") != null ? dynaBean.get(
//							"MES8VALORPREST").toString() : "0"));
//					servPrest.setCtRemitosMes8(Long.valueOf(dynaBean
//							.get("MES8REM") != null ? dynaBean.get("MES8REM")
//							.toString() : "0"));
//					servPrest.setCtRemitosAConcilMes8(Long.valueOf(dynaBean
//							.get("MES8REMCON") != null ? dynaBean.get(
//							"MES8REMCON").toString() : "0"));
//				}
//				if (cant >= 9) {
//					servPrest.setCtServPrestMes9(new BigDecimal(dynaBean
//							.get("MES9SERVPREST") != null ? dynaBean.get(
//							"MES9SERVPREST").toString() : "0"));
//					servPrest.setValServPrestMes9(new BigDecimal(dynaBean
//							.get("MES9VALORPREST") != null ? dynaBean.get(
//							"MES9VALORPREST").toString() : "0"));
//					servPrest.setCtRemitosMes9(Long.valueOf(dynaBean
//							.get("MES9REM") != null ? dynaBean.get("MES9REM")
//							.toString() : "0"));
//					servPrest.setCtRemitosAConcilMes9(Long.valueOf(dynaBean
//							.get("MES9REMCON") != null ? dynaBean.get(
//							"MES9REMCON").toString() : "0"));
//				}
//				if (cant >= 10) {
//					servPrest.setCtServPrestMes10(new BigDecimal(dynaBean
//							.get("MES10SERVPREST") != null ? dynaBean.get(
//							"MES10SERVPREST").toString() : "0"));
//					servPrest.setValServPrestMes10(new BigDecimal(dynaBean
//							.get("MES10VALORPREST") != null ? dynaBean.get(
//							"MES10VALORPREST").toString() : "0"));
//					servPrest.setCtRemitosMes10(Long.valueOf(dynaBean
//							.get("MES10REM") != null ? dynaBean.get("MES10REM")
//							.toString() : "0"));
//					servPrest.setCtRemitosAConcilMes10(Long.valueOf(dynaBean
//							.get("MES10REMCON") != null ? dynaBean.get(
//							"MES10REMCON").toString() : "0"));
//				}
//				if (cant >= 11) {
//					servPrest.setCtServPrestMes11(new BigDecimal(dynaBean
//							.get("MES11SERVPREST") != null ? dynaBean.get(
//							"MES11SERVPREST").toString() : "0"));
//					servPrest.setValServPrestMes11(new BigDecimal(dynaBean
//							.get("MES11VALORPREST") != null ? dynaBean.get(
//							"MES11VALORPREST").toString() : "0"));
//					servPrest.setCtRemitosMes11(Long.valueOf(dynaBean
//							.get("MES11REM") != null ? dynaBean.get("MES11REM")
//							.toString() : "0"));
//					servPrest.setCtRemitosAConcilMes11(Long.valueOf(dynaBean
//							.get("MES11REMCON") != null ? dynaBean.get(
//							"MES11REMCON").toString() : "0"));
//				}
//				if (cant >= 12) {
//					servPrest.setCtServPrestMes12(new BigDecimal(dynaBean
//							.get("MES12SERVPREST") != null ? dynaBean.get(
//							"MES12SERVPREST").toString() : "0"));
//					servPrest.setValServPrestMes12(new BigDecimal(dynaBean
//							.get("MES12VALORPREST") != null ? dynaBean.get(
//							"MES12VALORPREST").toString() : "0"));
//					servPrest.setCtRemitosMes12(Long.valueOf(dynaBean
//							.get("MES12REM") != null ? dynaBean.get("MES12REM")
//							.toString() : "0"));
//					servPrest.setCtRemitosAConcilMes12(Long.valueOf(dynaBean
//							.get("MES12REMCON") != null ? dynaBean.get(
//							"MES12REMCON").toString() : "0"));
//				}
//				servPrest.setCtServPrestTotal(new BigDecimal(dynaBean
//						.get("SERVPREST") != null ? dynaBean.get("SERVPREST")
//						.toString() : "0"));
//				servPrest.setValServPrestTotal(new BigDecimal(dynaBean
//						.get("VALORPREST") != null ? dynaBean.get("VALORPREST")
//						.toString() : "0"));
//				servPrest.setValBrutaTotal(new BigDecimal(dynaBean
//						.get("VALORPRESTBRUTO") != null ? dynaBean.get(
//						"VALORPRESTBRUTO").toString() : "0"));
//				servPrest.setValNetoTotal(new BigDecimal(dynaBean
//						.get("VALORPRESTNETO") != null ? dynaBean.get(
//						"VALORPRESTNETO").toString() : "0"));
//				servPrest.setCtRemitosTotal(new Long(dynaBean
//						.get("TOTALREMITOS") != null ? dynaBean.get(
//						"TOTALREMITOS").toString() : "0"));
//				servPrest.setCtRemitosAConcilTotal(new Long(dynaBean
//						.get("TOTALREMITOSACON") != null ? dynaBean.get(
//						"TOTALREMITOSACON").toString() : "0"));
//				sectoresList.add(servPrest);
//			}
//		} catch (TransactionException e) {
//			log.debug(e);
//			e.printStackTrace();
//			throw new DaoException(
//					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
//					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
//		} catch (Exception e) {
//			log.debug(e);
//			e.printStackTrace();
//			throw new DaoException(
//					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
//					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
//		}
//		return result;
//	}
//
//	public Result getServPrestReduc(Map parameters, List sectoresList, int cant)
//			throws Exception {
//		try {
//
//			System.out.println(parameters);
//			SamConfig.getSam().execute(
//					DatabaseConstants.CONSULTA_SERV_PREST_RED_INVOKER,
//					getSamWebClient().getSamContext(), parameters);
//			DaoUtils.proccessServiceResponse(parameters, this.result, log);
//			ArrayList dynaListResult = (ArrayList) parameters
//					.get(DatabaseConstants.PO_CURSOR);
//			ServPrest servPrest;
//			Iterator it = dynaListResult.iterator();
//			while (it.hasNext()) {
//
//				DynaBean dynaBean = (DynaBean) it.next();
//
//				servPrest = new ServPrest();
//
//				servPrest.setCdSector(dynaBean.get("CD_SECTOR") != null ? dynaBean.get("CD_SECTOR").toString() : "");
//				servPrest.setCdGrupoProd(dynaBean.get("CD_GRUPOPRODUCTO") != null ? dynaBean.get("CD_GRUPOPRODUCTO").toString() : "");
//				servPrest.setCdProd("");
//
//				servPrest.setCtServPrestMes1(new BigDecimal(dynaBean.get("MES1SERVPREST") != null ? dynaBean.get("MES1SERVPREST").toString() : "0"));
//				servPrest.setValServPrestMes1(new BigDecimal(dynaBean.get("MES1VALORPREST") != null ? dynaBean.get("MES1VALORPREST").toString() : "0"));
//				servPrest.setCtRemitosMes1(Long.valueOf(dynaBean.get("MES1REM") != null ? dynaBean.get("MES1REM").toString() : "0"));
//				System.out.println("DAO Mes1 "+ servPrest.getValServPrestMes1());
//
//				servPrest.setCtRemitosAConcilMes1(Long.valueOf(dynaBean.get("MES1REMCON") != null ? dynaBean.get("MES1REMCON").toString() : "0"));
//
//				System.out.println("CANTIDAD ->" + cant);
//
//				if (cant >= 2) {
//
//					servPrest.setCtServPrestMes2(new BigDecimal(dynaBean.get("MES2SERVPREST") != null ? dynaBean.get("MES2SERVPREST").toString() : "0"));
//					servPrest.setValServPrestMes2(new BigDecimal(dynaBean.get("MES2VALORPREST") != null ? dynaBean.get("MES2VALORPREST").toString() : "0"));
//					servPrest.setCtRemitosMes2(Long.valueOf(dynaBean.get("MES2REM") != null ? dynaBean.get("MES2REM").toString() : "0"));
//					servPrest.setCtRemitosAConcilMes2(Long.valueOf(dynaBean.get("MES2REMCON") != null ? dynaBean.get("MES2REMCON").toString() : "0"));
//
//					System.out.println("mes 2" + servPrest.getCtServPrestMes2());
//				}
//				if (cant >= 3) {
//					servPrest.setCtServPrestMes3(new BigDecimal(dynaBean.get("MES3SERVPREST") != null ? dynaBean.get("MES3SERVPREST").toString() : "0"));
//					servPrest.setValServPrestMes3(new BigDecimal(dynaBean.get("MES3VALORPREST") != null ? dynaBean.get("MES3VALORPREST").toString() : "0"));
//					servPrest.setCtRemitosMes3(Long.valueOf(dynaBean.get("MES3REM") != null ? dynaBean.get("MES3REM").toString() : "0"));
//					servPrest.setCtRemitosAConcilMes3(Long.valueOf(dynaBean.get("MES3REMCON") != null ? dynaBean.get("MES3REMCON").toString() : "0"));
//				}
//				if (cant >= 4) {
//					servPrest.setCtServPrestMes4(new BigDecimal(dynaBean.get("MES4SERVPREST") != null ? dynaBean.get("MES4SERVPREST").toString() : "0"));
//					servPrest.setValServPrestMes4(new BigDecimal(dynaBean.get("MES4VALORPREST") != null ? dynaBean.get("MES4VALORPREST").toString() : "0"));
//					servPrest.setCtRemitosMes4(Long.valueOf(dynaBean.get("MES4REM") != null ? dynaBean.get("MES4REM").toString() : "0"));
//					servPrest.setCtRemitosAConcilMes4(Long.valueOf(dynaBean.get("MES4REMCON") != null ? dynaBean.get("MES4REMCON").toString() : "0"));
//				}
//				if (cant >= 5) {
//					servPrest.setCtServPrestMes5(new BigDecimal(dynaBean.get("MES5SERVPREST") != null ? dynaBean.get("MES5SERVPREST").toString() : "0"));
//					servPrest.setValServPrestMes5(new BigDecimal(dynaBean.get("MES5VALORPREST") != null ? dynaBean.get("MES5VALORPREST").toString() : "0"));
//					servPrest.setCtRemitosMes5(Long.valueOf(dynaBean.get("MES5REM") != null ? dynaBean.get("MES5REM").toString() : "0"));
//					servPrest.setCtRemitosAConcilMes5(Long.valueOf(dynaBean.get("MES5REMCON") != null ? dynaBean.get("MES5REMCON").toString() : "0"));
//				}
//				if (cant >= 6) {
//					servPrest.setCtServPrestMes6(new BigDecimal(dynaBean.get("MES6SERVPREST") != null ? dynaBean.get("MES6SERVPREST").toString() : "0"));
//					servPrest.setValServPrestMes6(new BigDecimal(dynaBean.get("MES6VALORPREST") != null ? dynaBean.get("MES6VALORPREST").toString() : "0"));
//					servPrest.setCtRemitosMes6(Long.valueOf(dynaBean.get("MES6REM") != null ? dynaBean.get("MES6REM").toString() : "0"));
//					servPrest.setCtRemitosAConcilMes6(Long.valueOf(dynaBean.get("MES6REMCON") != null ? dynaBean.get("MES6REMCON").toString() : "0"));
//				}
//				if (cant >= 7) {
//					servPrest.setCtServPrestMes7(new BigDecimal(dynaBean.get("MES7SERVPREST") != null ? dynaBean.get("MES7SERVPREST").toString() : "0"));
//					servPrest.setValServPrestMes7(new BigDecimal(dynaBean.get("MES7VALORPREST") != null ? dynaBean.get("MES7VALORPREST").toString() : "0"));
//					servPrest.setCtRemitosMes7(Long.valueOf(dynaBean.get("MES7REM") != null ? dynaBean.get("MES7REM").toString() : "0"));
//					servPrest.setCtRemitosAConcilMes7(Long.valueOf(dynaBean.get("MES7REMCON") != null ? dynaBean.get("MES7REMCON").toString() : "0"));
//				}
//				if (cant >= 8) {
//					servPrest.setCtServPrestMes8(new BigDecimal(dynaBean.get("MES8SERVPREST") != null ? dynaBean.get("MES8SERVPREST").toString() : "0"));
//					servPrest.setValServPrestMes8(new BigDecimal(dynaBean.get("MES8VALORPREST") != null ? dynaBean.get("MES8VALORPREST").toString() : "0"));
//					servPrest.setCtRemitosMes8(Long.valueOf(dynaBean.get("MES8REM") != null ? dynaBean.get("MES8REM").toString() : "0"));
//					servPrest.setCtRemitosAConcilMes8(Long.valueOf(dynaBean.get("MES8REMCON") != null ? dynaBean.get("MES8REMCON").toString() : "0"));
//				}
//				if (cant >= 9) {
//					servPrest.setCtServPrestMes9(new BigDecimal(dynaBean.get("MES9SERVPREST") != null ? dynaBean.get("MES9SERVPREST").toString() : "0"));
//					servPrest.setValServPrestMes9(new BigDecimal(dynaBean.get("MES9VALORPREST") != null ? dynaBean.get("MES9VALORPREST").toString() : "0"));
//					servPrest.setCtRemitosMes9(Long.valueOf(dynaBean.get("MES9REM") != null ? dynaBean.get("MES9REM").toString() : "0"));
//					servPrest.setCtRemitosAConcilMes9(Long.valueOf(dynaBean.get("MES9REMCON") != null ? dynaBean.get("MES9REMCON").toString() : "0"));
//				}
//				if (cant >= 10) {
//					servPrest.setCtServPrestMes10(new BigDecimal(dynaBean.get("MES10SERVPREST") != null ? dynaBean.get("MES10SERVPREST").toString() : "0"));
//					servPrest.setValServPrestMes10(new BigDecimal(dynaBean.get("MES10VALORPREST") != null ? dynaBean.get("MES10VALORPREST").toString() : "0"));
//					servPrest.setCtRemitosMes10(Long.valueOf(dynaBean.get("MES10REM") != null ? dynaBean.get("MES10REM").toString() : "0"));
//					servPrest.setCtRemitosAConcilMes10(Long.valueOf(dynaBean.get("MES10REMCON") != null ? dynaBean.get("MES10REMCON").toString() : "0"));
//				}
//				if (cant >= 11) {
//					servPrest.setCtServPrestMes11(new BigDecimal(dynaBean.get("MES11SERVPREST") != null ? dynaBean.get("MES11SERVPREST").toString() : "0"));
//					servPrest.setValServPrestMes11(new BigDecimal(dynaBean.get("MES11VALORPREST") != null ? dynaBean.get("MES11VALORPREST").toString() : "0"));
//					servPrest.setCtRemitosMes11(Long.valueOf(dynaBean.get("MES11REM") != null ? dynaBean.get("MES11REM").toString() : "0"));
//					servPrest.setCtRemitosAConcilMes11(Long.valueOf(dynaBean.get("MES11REMCON") != null ? dynaBean.get("MES11REMCON").toString() : "0"));
//				}
//				if (cant >= 12) {
//					servPrest.setCtServPrestMes12(new BigDecimal(dynaBean.get("MES12SERVPREST") != null ? dynaBean.get("MES12SERVPREST").toString() : "0"));
//					servPrest.setValServPrestMes12(new BigDecimal(dynaBean.get("MES12VALORPREST") != null ? dynaBean.get("MES12VALORPREST").toString() : "0"));
//					servPrest.setCtRemitosMes12(Long.valueOf(dynaBean.get("MES12REM") != null ? dynaBean.get("MES12REM").toString() : "0"));
//					servPrest.setCtRemitosAConcilMes12(Long.valueOf(dynaBean.get("MES12REMCON") != null ? dynaBean.get("MES12REMCON").toString() : "0"));
//				}
//				
//				servPrest.setCtServPrestTotal(new BigDecimal(dynaBean.get("SERVPREST") != null ? dynaBean.get("SERVPREST").toString() : "0"));
//				servPrest.setValServPrestTotal(new BigDecimal(dynaBean.get("VALORPREST") != null ? dynaBean.get("VALORPREST").toString() : "0"));
//				servPrest.setValBrutaTotal(new BigDecimal(dynaBean.get("VALORPRESTBRUTO") != null ? dynaBean.get("VALORPRESTBRUTO").toString() : "0"));
//				servPrest.setValNetoTotal(new BigDecimal(dynaBean.get("VALORPRESTNETO") != null ? dynaBean.get("VALORPRESTNETO").toString() : "0"));
//				servPrest.setCtRemitosTotal(new Long(dynaBean.get("TOTALREMITOS") != null ? dynaBean.get("TOTALREMITOS").toString() : "0"));
//				servPrest.setCtRemitosAConcilTotal(new Long(dynaBean.get("TOTALREMITOSACON") != null ? dynaBean.get("TOTALREMITOSACON").toString() : "0"));
//				sectoresList.add(servPrest);
//			}
//		} catch (TransactionException e) {
//			log.debug(e);
//			e.printStackTrace();
//			throw new DaoException(
//					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
//					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
//		} catch (Exception e) {
//			log.debug(e);
//			e.printStackTrace();
//			throw new DaoException(
//					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
//					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
//		}
//		return result;
//	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Result getServPrestPeriodos(Map parameters,
			List servPrestPeriodosList) throws Exception {
		try {
			System.out.println(parameters);
			SamConfig.getSam().execute(
					DatabaseConstants.GET_SERV_PREST_PERIODOS_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
			ArrayList dynaListResult = (ArrayList) parameters
					.get(DatabaseConstants.PO_CURSOR);

			String periodo = "";
			Iterator it = dynaListResult.iterator();
			while (it.hasNext()) {
				DynaBean dynaBean = (DynaBean) it.next();

				periodo = dynaBean.get("PERIODO").toString();

				servPrestPeriodosList.add(periodo);
			}
		} catch (TransactionException e) {
			e.printStackTrace();
			log.debug(e);
			throw new DaoException(
					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		} catch (Exception e) {
			log.debug(e);
			throw new DaoException(
					ResultDatabase.SAM_CONECTION_ERROR.getCode(),
					ResultDatabase.SAM_CONECTION_ERROR.getMessage());
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	public Result getServPrest2(Map parameters, List servList)
			throws Exception {
		try {
			System.out.println(parameters);
			SamConfig.getSam().execute(
					DatabaseConstants.CONSULTA_SERV_PREST2_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
			ArrayList dynaListResult = (ArrayList) parameters
					.get(DatabaseConstants.PO_CURSOR);
			ServPrestShort servPrest;
			Iterator it = dynaListResult.iterator();
			int i = 0;
			while (it.hasNext()) {
				DynaBean dynaBean = (DynaBean) it.next();

				servPrest = new ServPrestShort();

				servPrest.setCdSector(dynaBean.get("CD_SECTORCONCIL") != null ? dynaBean
						.get("CD_SECTORCONCIL").toString() : "");
				servPrest.setCdGrupoProd(dynaBean.get("CD_GRUPOPRODUCTO") != null ? dynaBean
						.get("CD_GRUPOPRODUCTO").toString() : "");
				servPrest.setCdProd(dynaBean.get("CD_PRODUCTO") != null ? dynaBean
						.get("CD_PRODUCTO").toString() : "");
				servPrest.setCtServPrest(Long.valueOf(dynaBean
							.get("CANTFACT") != null ? dynaBean.get(
							"CANTFACT").toString() : "0"));
				System.out.println(servPrest.getCtServPrest());
				servPrest.setValServPrest(new BigDecimal(dynaBean
							.get("SERVFACT") != null ? dynaBean.get(
							"SERVFACT").toString() : "0"));
				servPrest.setCtRemitos(Long.valueOf(dynaBean
							.get("CANTREMITOS") != null ? dynaBean.get("CANTREMITOS")
							.toString() : "0"));
				servPrest.setCtRemitosAConci(Long.valueOf(dynaBean
							.get("CANTREMCON") != null ? dynaBean.get(
							"CANTREMCON").toString() : "0"));
				servPrest.setValNeto(new BigDecimal(dynaBean
						.get("CT_NETO") != null ? dynaBean.get(
						"CT_NETO").toString() : "0"));
				servPrest.setValBruto(new BigDecimal(dynaBean
						.get("CT_BRUTO") != null ? dynaBean.get("CT_BRUTO")
						.toString() : "0"));
				servPrest.setCdPeriodo(dynaBean.get("CD_PERIODOFACT") != null ? dynaBean
						.get("CD_PERIODOFACT").toString() : "");
				
				
				servList.add(servPrest);
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

	public Result getServPrest(Map parameters, List productosList, int cant)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Result getServPrestReduc(Map parameters, List productosList, int cant)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
