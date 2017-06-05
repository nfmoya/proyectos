package com.bbva.cfs.producto.dao.impl;

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
import com.bbva.cfs.producto.dao.ProductoDao;
import com.bbva.cfs.producto.model.Producto;
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
public class ProductoDaoImpl implements ProductoDao {

	static final Log log = LogFactory.getLog(ProductoDaoImpl.class);
	private SAMWebClient samWebClient;

	private Result result;
	
	/* Getters & Setters */
	public SAMWebClient getSamWebClient() {
		return samWebClient;
	}

	public ProductoDaoImpl(SAMWebClient samWebClient) {
		this.samWebClient = samWebClient;
		this.result = new Result();
	}

	/**
	 * Metodo que trae los productos.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Result getProductos(Map parameters, List productosList) throws Exception {
		try {
			SamConfig.getSam().execute(
					DatabaseConstants.ABMC_PRODUCTO_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
			ArrayList dynaListResult = (ArrayList) parameters
					.get(DatabaseConstants.PO_CURSOR);

			Producto productos;
			Iterator it = dynaListResult.iterator();
			while (it.hasNext()) {
				DynaBean dynaBean = (DynaBean) it.next();

				productos = new Producto();
				
				productos
					.setCdProveedor(dynaBean.get("CD_PROVEEDOR") != null 
					? dynaBean.get("CD_PROVEEDOR").toString()
					: "");

				productos
					.setCdProducto(dynaBean.get("CD_PRODUCTO") != null 
					? dynaBean.get("CD_PRODUCTO").toString()
					: "");

				productos
					.setNbProducto(dynaBean.get("NB_PRODUCTO") != null 
					? dynaBean.get("NB_PRODUCTO").toString()
					: "");

				productos
					.setNbProductoCorto(dynaBean.get("NB_PRODUCTOCORTO") != null 
					? dynaBean.get("NB_PRODUCTOCORTO").toString()
					: "");
				
				productos
					.setCdGrupoProducto(dynaBean.get("CD_GRUPOPRODUCTO") != null 
					? dynaBean.get("CD_GRUPOPRODUCTO").toString()
					: "");

				productos
					.setCdUniVal(dynaBean.get("CD_UNIVAL") != null 
					? dynaBean.get("CD_UNIVAL").toString()
					: "");

				productos
					.setCdSecSolServ(dynaBean.get("CD_SECSOLSERV") != null 
					? dynaBean.get("CD_SECSOLSERV").toString()
					: "");
				
				productos
					.setCdSecConServ(dynaBean.get("CD_SECCONSERV") != null 
					? dynaBean.get("CD_SECCONSERV").toString()
					: "");

				productos
					.setCdSecConFact(dynaBean.get("CD_SECCONFACT") != null 
					? dynaBean.get("CD_SECCONFACT").toString()
					: "");
				
				productos
					.setStProdImportPrest(dynaBean.get("ST_PRODIMPORTPREST") != null 
					? dynaBean.get("ST_PRODIMPORTPREST").toString()
					: "");

				productos
					.setStProdImportFact(dynaBean.get("ST_PRODIMPORTFACT") != null 
					? dynaBean.get("ST_PRODIMPORTFACT").toString()
					: "");

				productos
					.setStRemServOblig(dynaBean.get("ST_REMSERVOBLIG") != null 
					? dynaBean.get("ST_REMSERVOBLIG").toString()
					: "");
			
				productos
					.setStRemFactOblig(dynaBean.get("ST_REMFACTOBLIG") != null 
					? dynaBean.get("ST_REMFACTOBLIG").toString()
					: "");

				productos
					.setStAdmiteRemServ(dynaBean.get("ST_ADMITEREMSERV") != null 
					? dynaBean.get("ST_ADMITEREMSERV").toString()
					: "");

				productos
					.setStAdmiteRemFact(dynaBean.get("ST_ADMITEREMFACT") != null 
					? dynaBean.get("ST_ADMITEREMFACT").toString()
					: "");

				productos
					.setNbAtributoRef1(dynaBean.get("NB_ATRIBUTOREF1") != null 
					? dynaBean.get("NB_ATRIBUTOREF1").toString()
					: "");

				productos
					.setNbAtributoRef2(dynaBean.get("NB_ATRIBUTOREF2") != null 
					? dynaBean.get("NB_ATRIBUTOREF2").toString()
					: "");
			
				productos
					.setStConcilSinVal(dynaBean.get("ST_CONCILSINVAL") != null 
					? dynaBean.get("ST_CONCILSINVAL").toString()
					: "");

				productos
					.setStServSinConcil(dynaBean.get("ST_SERVSINCONCIL") != null 
					? dynaBean.get("ST_SERVSINCONCIL").toString()
					: "");

				productos
					.setStFactSinConcil(dynaBean.get("ST_FACTSINCONCIL") != null 
					? dynaBean.get("ST_FACTSINCONCIL").toString()
					: "");

				productos
					.setNuDiaEmiFDesde(dynaBean.get("NU_DIAEMIFDESDE") != null 
					? Long.valueOf(dynaBean.get("NU_DIAEMIFDESDE").toString())
					: Long.valueOf("0"));
				
				productos
					.setNuDiaEmiFHasta(dynaBean.get("NU_DIAEMIFHASTA") != null 
					? Long.valueOf(dynaBean.get("NU_DIAEMIFHASTA").toString())
					: Long.valueOf("0"));
			
				productos
					.setNuDiaCierreFDesde(dynaBean.get("NU_DIACIERREFDESDE") != null 
					? Long.valueOf(dynaBean.get("NU_DIACIERREFDESDE").toString())
					: Long.valueOf("0"));
			
				productos
					.setNuDiaCierreFHasta(dynaBean.get("NU_DIACIERREFHASTA") != null 
					? Long.valueOf(dynaBean.get("NU_DIACIERREFHASTA").toString())
					: Long.valueOf("0"));

				productos
					.setNbAtributoAdic1(dynaBean.get("NB_ATRIBUTOADIC1") != null 
					? dynaBean.get("NB_ATRIBUTOADIC1").toString()
					: "");

				productos
					.setNbAtributoAdic2(dynaBean.get("NB_ATRIBUTOADIC2") != null 
					? dynaBean.get("NB_ATRIBUTOADIC2").toString()
					: "");

				productos
					.setNbAtributoAdic3(dynaBean.get("NB_ATRIBUTOADIC3") != null 
					? dynaBean.get("NB_ATRIBUTOADIC3").toString()
					: "");
			
				productos
					.setNbAtributoAdic4(dynaBean.get("NB_ATRIBUTOADIC4") != null 
					? dynaBean.get("NB_ATRIBUTOADIC4").toString()
					: "");

				productos
					.setNbAtributoAdic5(dynaBean.get("NB_ATRIBUTOADIC5") != null 
					? dynaBean.get("NB_ATRIBUTOADIC5").toString()
					: "");

				productos
					.setStHabilitado(dynaBean.get("ST_HABILITADO") != null 
					? dynaBean.get("ST_HABILITADO").toString()
					: "");
				
				productos
					.setCdTipVal(dynaBean.get("CD_TIPVAL") != null 
					? dynaBean.get("CD_TIPVAL").toString()
					: "1");
				
				productos
					.setCdMoneda(dynaBean.get("CD_MONEDA") != null 
					? dynaBean.get("CD_MONEDA").toString()
					: "");
				
				productos
					.setNuPorcVarMax(dynaBean.get("NU_PORCVARMAX") != null 
					? Integer.valueOf(dynaBean.get("NU_PORCVARMAX").toString())
					: Integer.valueOf("0"));
				
				productosList.add(productos);
			}
		} catch (TransactionException e) {
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
	
	@SuppressWarnings("rawtypes")
	public Result saveProducto(Map parameters) throws Exception {
		try {
			// Nombre de la constante del procedimiento de la base de datos
			SamConfig.getSam().execute(
					DatabaseConstants.ABMC_PRODUCTO_INVOKER,
					getSamWebClient().getSamContext(), parameters);
			DaoUtils.proccessServiceResponse(parameters, this.result, log);
		} catch (TransactionException e) {
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
	
	@SuppressWarnings("rawtypes")
	public Result deleteProducto(Map parameters) throws Exception {
		try {
			SamConfig.getSam().execute(DatabaseConstants.ABMC_PRODUCTO_INVOKER,
			getSamWebClient().getSamContext(), parameters);        
			DaoUtils.proccessServiceResponse(parameters, this.result,log);
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
	public Result verificaExistenciaAgrupados(Map parameters) throws Exception {
		try {
			SamConfig.getSam().execute(DatabaseConstants.CONSULTA_PRODUCTO_AGRUP_INVOKER,
			getSamWebClient().getSamContext(), parameters);        
			DaoUtils.proccessServiceResponse(parameters, this.result,log);
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
