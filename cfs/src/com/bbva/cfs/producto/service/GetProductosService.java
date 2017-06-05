package com.bbva.cfs.producto.service;

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
//import com.bbva.cfs.producto.model.Producto;
import com.bbva.cfs.producto.service.CommoProductoService;

@SuppressWarnings("rawtypes")
public class GetProductosService extends CommoProductoService {
	private Result result;	
	private Map<String, Comparable> parameters;
	private List productosList;
	
	public GetProductosService(IWebClient iWebClient){
		super(iWebClient);
		this.parameters = new HashMap<String, Comparable>();
		this.productosList = new ArrayList();
	}
	
	/**
	 * TODO:
	 * @return
	 */
	public Result execute() throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"          , "3");
		this.parameters.put("pi_NU_DIAEMIFDESDE"    , 0);
		this.parameters.put("pi_NU_DIAEMIFHASTA"    , 0);
		this.parameters.put("pi_NU_DIACIERREFDESDE" , 0);
		this.parameters.put("pi_NU_DIACIERREFHASTA" , 0);
		this.parameters.put("pi_NU_PORCVARMAX"      , 0);
		
		this.productosList.clear();
		try {
			log.debug("Invocaci�n al dao getProductoDAO: GetProductosService()");
			result = getProductoDao().getProductos(this.parameters, this.productosList);
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
	public Result execute(String cdProveedor) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"          , "3");
		this.parameters.put("pi_CD_PROVEEDOR"       , cdProveedor);
		this.parameters.put("pi_NU_DIAEMIFDESDE"    , 0);
		this.parameters.put("pi_NU_DIAEMIFHASTA"    , 0);
		this.parameters.put("pi_NU_DIACIERREFDESDE" , 0);
		this.parameters.put("pi_NU_DIACIERREFHASTA" , 0);
		this.parameters.put("pi_NU_PORCVARMAX"      , 0);
		
		this.productosList.clear();
		try {
			log.debug("Invocaci�n al dao getProductoDAO: GetProductosService()");
			result = getProductoDao().getProductos(this.parameters, this.productosList);
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
	public Result execute(String cdProveedor, String cdProducto) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"          , "3");
		this.parameters.put("pi_CD_PROVEEDOR"       , cdProveedor);
		this.parameters.put("pi_CD_PRODUCTO"        , cdProducto);
		this.parameters.put("pi_NU_DIAEMIFDESDE"    , 0);
		this.parameters.put("pi_NU_DIAEMIFHASTA"    , 0);
		this.parameters.put("pi_NU_DIACIERREFDESDE" , 0);
		this.parameters.put("pi_NU_DIACIERREFHASTA" , 0);
		this.parameters.put("pi_NU_PORCVARMAX"      , 0);
		
		this.productosList.clear();
		try {
			log.debug("Invocaci�n al dao getProductoDAO: GetProductosService()");
			result = getProductoDao().getProductos(this.parameters, this.productosList);
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
	public Result execute(String cdProveedor, String cdProducto, String cdGrupo, String stHabilitado) throws Exception {
		this.parameters.clear();

        cdProducto   = (cdProducto.equals("0")   ? "" : cdProducto);
        cdGrupo      = (cdGrupo.equals("0")      ? "" : cdGrupo);
        stHabilitado = (stHabilitado.equals("0") ? "" : stHabilitado);
		this.parameters.put("pi_id_opcion"          , "3");
		this.parameters.put("pi_CD_PROVEEDOR"       , cdProveedor);
		this.parameters.put("pi_CD_PRODUCTO"        , cdProducto);
		this.parameters.put("pi_CD_GRUPOPRODUCTO"   , cdGrupo);
		this.parameters.put("pi_ST_HABILITADO"      , stHabilitado);
		this.parameters.put("pi_NU_DIAEMIFDESDE"    , 0);
		this.parameters.put("pi_NU_DIAEMIFHASTA"    , 0);
		this.parameters.put("pi_NU_DIACIERREFDESDE" , 0);
		this.parameters.put("pi_NU_DIACIERREFHASTA" , 0);
		this.parameters.put("pi_NU_PORCVARMAX"      , 0);
		
		this.productosList.clear();
		try {
			log.debug("Invocaci�n al dao getProductoDAO: GetProductosService()");
			result = getProductoDao().getProductos(this.parameters, this.productosList);
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
	

	public Result saveProducto(String opcion, String cdProveedor, String cdProducto, String nbProducto, 
							String nbProductoCorto, String cdGrupoProducto, String cdUniVal, 
							String cdSecSolServ, String cdSecConServ, String cdSecConFact,
							String stProdImportPrest, String stProdImportFact, String stRemServOblig,
							String stRemFactOblig, String stAdmiteRemServ, String stAdmiteRemFact,
							String nbAtributoRef1, String nbAtributoRef2, String stConcilSinVal, 
							String stServSinConcil, String stFactSinConcil, Integer nuDiaEmiFDesde, 
							Integer nuDiaEmiFHasta, Integer nuDiaCierreFDesde, Integer nuDiaCierreFHasta,
							String nbAtributoAdic1, String nbAtributoAdic2, String nbAtributoAdic3,
							String nbAtributoAdic4, String nbAtributoAdic5, String stHabilitado, 
							String cdTipVal, String cdMoneda, Integer nuPorcVarMax, String usuario) throws Exception {
		this.parameters.clear();
		
		this.parameters.put("pi_id_opcion"          , opcion);	
		this.parameters.put("pi_CD_PROVEEDOR"       , cdProveedor);
		this.parameters.put("pi_CD_PRODUCTO"        , cdProducto);
		this.parameters.put("pi_NB_PRODUCTO"        , nbProducto);
		this.parameters.put("pi_NB_PRODUCTOCORTO"   , nbProductoCorto);
		this.parameters.put("pi_CD_GRUPOPRODUCTO"   , cdGrupoProducto);
		this.parameters.put("pi_CD_UNIVAL"          , cdUniVal);
		this.parameters.put("pi_CD_SECSOLSERV"      , cdSecSolServ);
		this.parameters.put("pi_CD_SECCONSERV"      , cdSecConServ);
		this.parameters.put("pi_CD_SECCONFACT"      , cdSecConFact);
		this.parameters.put("pi_ST_PRODIMPORTPREST" , stProdImportPrest);
		this.parameters.put("pi_ST_PRODIMPORTFACT"  , stProdImportFact);
		this.parameters.put("pi_ST_REMSERVOBLIG"    , stRemServOblig);
		this.parameters.put("pi_ST_REMFACTOBLIG"    , stRemFactOblig);
		this.parameters.put("pi_ST_ADMITEREMSERV"   , stAdmiteRemServ);
		this.parameters.put("pi_ST_ADMITEREMFACT"   , stAdmiteRemFact);
		this.parameters.put("pi_NB_ATRIBUTOREF1"    , nbAtributoRef1);
		this.parameters.put("pi_NB_ATRIBUTOREF2"    , nbAtributoRef2);
		this.parameters.put("pi_ST_CONCILSINVAL"    , stConcilSinVal);		
		this.parameters.put("pi_ST_SERVSINCONCIL"   , stServSinConcil);
		this.parameters.put("pi_ST_FACTSINCONCIL"   , stFactSinConcil);
		this.parameters.put("pi_NU_DIAEMIFDESDE"    , nuDiaEmiFDesde);
		this.parameters.put("pi_NU_DIAEMIFHASTA"    , nuDiaEmiFHasta);
		this.parameters.put("pi_NU_DIACIERREFDESDE" , nuDiaCierreFDesde);
		this.parameters.put("pi_NU_DIACIERREFHASTA" , nuDiaCierreFHasta);
		this.parameters.put("pi_NB_ATRIBUTOADIC1"   , nbAtributoAdic1);
		this.parameters.put("pi_NB_ATRIBUTOADIC2"   , nbAtributoAdic2);
		this.parameters.put("pi_NB_ATRIBUTOADIC3"   , nbAtributoAdic3);
		this.parameters.put("pi_NB_ATRIBUTOADIC4"   , nbAtributoAdic4);
		this.parameters.put("pi_NB_ATRIBUTOADIC5"   , nbAtributoAdic5);
		this.parameters.put("pi_ST_HABILITADO"      , stHabilitado);
		this.parameters.put("pi_CD_TIPVAL"          , cdTipVal);
		this.parameters.put("pi_CD_MONEDA"          , cdMoneda);
		this.parameters.put("pi_NU_PORCVARMAX"      , nuPorcVarMax);
		this.parameters.put("pi_USU_MODI"           , usuario);	
		try {
			log.debug("Invocaci�n al dao getProductoDAO: saveProducto()");
			result = getProductoDao().saveProducto(this.parameters);
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

	public Result deleteProducto(String cdProveedor, String cdProducto) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_id_opcion"          , "4");
		this.parameters.put("pi_CD_PROVEEDOR"       , cdProveedor);
		this.parameters.put("pi_CD_PRODUCTO"        , cdProducto);
		this.parameters.put("pi_NU_DIAEMIFDESDE"    , 0);
		this.parameters.put("pi_NU_DIAEMIFHASTA"    , 0);
		this.parameters.put("pi_NU_DIACIERREFDESDE" , 0);
		this.parameters.put("pi_NU_DIACIERREFHASTA" , 0);
		this.parameters.put("pi_NU_PORCVARMAX"      , 0);
		
		try {
			log.debug("Invocaci�n al dao getProductoDAO: deleteProducto()");
			result = getProductoDao().deleteProducto(this.parameters);
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
	
	public List getProductosList() {
		return productosList;
	}

	public void setProductosList(List productosList) {
		this.productosList = productosList;
	}
	
	public Result VerificaExistenciaAgrupados(String cdProveedor, String cdProducto, String nbAtributoAdic1) throws Exception {
		this.parameters.clear();
		this.parameters.put("pi_CD_PROVEEDOR"       , cdProveedor);
		this.parameters.put("pi_CD_PRODUCTO"        , cdProducto);
		this.parameters.put("pi_NB_ATRIBUTOADIC1"   , nbAtributoAdic1);
		
		try {
			log.debug("Invocación al dao getProductoDAO: VerificaExistenciaAgrupados()");
			result = getProductoDao().verificaExistenciaAgrupados(this.parameters);
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
	
	
}
