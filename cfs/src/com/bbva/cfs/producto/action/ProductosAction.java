package com.bbva.cfs.producto.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



//import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bbva.cfs.parameters.model.Parameter;
import com.bbva.cfs.parameters.service.GetParameterListService;
import com.bbva.cfs.producto.form.ProductoForm;
import com.bbva.cfs.producto.service.GetProductosPreciosService;
//import com.bbva.cfs.producto.model.Producto;
import com.bbva.cfs.producto.service.GetProductosService;
import com.bbva.cfs.usuario.form.UsuarioForm;
import com.bbva.cfs.commons.action.CommonAction;
import com.bbva.cfs.commons.exceptions.GlobalActionException;
import com.bbva.cfs.commons.exceptions.GlobalGrantException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.model.Session;
import com.bbva.cfs.commons.utils.AsciiConstants;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.commons.utils.DateFormatCustomize;
import com.bbva.cfs.commons.utils.Grants;
import com.bbva.cfs.commons.utils.ParameterType;
import com.bbva.cfs.commons.utils.ResultDatabase;
import com.bbva.cfs.general.model.Tabla;
import com.bbva.cfs.general.service.GetTablasService;

/**
 * @author jmanrique
 * 
 */
@SuppressWarnings("unused")
public class ProductosAction extends CommonAction {
//	private GetParameterListService getParamListService;

	private GetProductosService getProductosService;

	private Result result;
	private Session session;
	private Long userId;
	@SuppressWarnings("rawtypes")
	private List lista;
	@SuppressWarnings("rawtypes")
	private List proveedorProductoList;	
	@SuppressWarnings("rawtypes")
	private List sectorList;
	@SuppressWarnings("rawtypes")
	private List grupoProductoList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List uniValList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filtroProveedorList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filtroProductoList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filtroGrupoList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filtroHabilitadoList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filtroSectorList = new ArrayList();

	
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		this.initialise(request.getSession());
		ProductoForm productoForm = (ProductoForm) form;
		userId = this.getAutenticathedUserId(request);
		obtenerInformacionDeUsuario(request.getSession());
		verificarPermisos(request, productoForm);
  		obtenerCombos(productoForm);
		
		return doFindSuccess(mapping);
	}


	private void obtenerInformacionDeUsuario(HttpSession httpSession) {
		session = (Session) httpSession.getAttribute(Constants.SESSION);
	}
	
	/* Verifico los permisos */
	private void verificarPermisos(HttpServletRequest request,
			ProductoForm productoForm) throws GlobalGrantException {

		// Otorgo si a todo hasta nueva definicion
		if (this.checkPrivilege(request, Grants.ABM_PRODUCTOS_CREATE.getId())){
			log.info("Insertar Producto");
			productoForm.setAddGrant("S");
		}
		if (this.checkPrivilege(request, Grants.ABM_PRODUCTOS_EDIT.getId())){
			log.info("Edit Producto");
			productoForm.setEditGrant("S");
		} 
		if (this.checkPrivilege(request, Grants.ABM_PRODUCTOS_DELETE.getId())){
			log.info("Delete Producto");
			productoForm.setDeleteGrant("S");
		} 
	}
	
	/* TODO: CAMBIAR */
	@SuppressWarnings("unchecked")
	public ActionForward getProductos(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		ProductoForm productoForm = (ProductoForm) form;
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			getProductosService = new GetProductosService(this.iWebClient);
			result = getProductosService.execute();
			resp.put("ProductosList", getProductosService.getProductosList());
			resp.put("result", result);
			JSONObject jsonObject = JSONObject.fromObject(resp);
			response.setContentType(AsciiConstants.CONTENT_TYPE_JSON);
			out = response.getWriter();
			out.print(jsonObject);
			if (out != null) {
				out.flush();
				out.close();
			}
		} catch (JSONException e) {
			log.error("Error execute Json: ", e);
			throw new GlobalActionException(ResultDatabase.ACTION_ERROR
					.getCode(), ResultDatabase.ACTION_ERROR.getMessage());
		} catch (GlobalServiceException e) {
			throw new GlobalActionException(e);
		} catch (Exception e) {
			log.error(e);
			throw new GlobalActionException(ResultDatabase.ACTION_ERROR
					.getCode(), ResultDatabase.ACTION_ERROR.getMessage());
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void obtenerCombos(ProductoForm productoForm) throws GlobalActionException {
		GetParameterListService getParamListService = new GetParameterListService(this.iWebClient);
		GetTablasService tablasService = new GetTablasService(iWebClient);
		try {
			List lista = new ArrayList();
			
			Parameter param = new Parameter("S_N", null, null, null);
			param.setCod("S");
			param.setDesc("S&iacute;");					
			lista.add(param);

			param = new Parameter("S_N", null, null, null);
			param.setCod("N");
			param.setDesc("No");
			lista.add(param);
				
			productoForm.setHabilitadoProductoList(lista);
			productoForm.setStProdImportPrestList(lista);
			productoForm.setStProdImportFactList(lista);
			productoForm.setStRemServObligList(lista);
			productoForm.setStRemFactObligList(lista);
			productoForm.setStAdmiteRemServList(lista);
			productoForm.setStAdmiteRemFactList(lista);
			productoForm.setStConcilSinValList(lista);
			productoForm.setStServSinConcilList(lista);
			productoForm.setStFactSinConcilList(lista);
			productoForm.setFiltroHabilitadoList(lista);
			productoForm.setNbAtributoAdic1List(lista);
			
			lista = new ArrayList();
			tablasService.execute("TIPVAL");
			
			for (Tabla	tipoVal : (List<Tabla>)tablasService.getTablasList()) {
				param = new Parameter("tipval", null, null, null);
				param.setCod(tipoVal.getCdCodTabla().trim());
				param.setDesc(tipoVal.getCdCodTabla().trim()+"-"+tipoVal.getNbCodTabla());
				lista.add(param);
			}
//			param = new Parameter("tipval", null, null, null);
//			param.setCod("1");
//			param.setDesc("Valorizaci\u00F3n Normal");
//			lista.add(param);
//			
//			param = new Parameter("tipval", null, null, null);
//			param.setCod("2");
//			param.setDesc("Rangos Cantidades");
//			lista.add(param);
//
//			param = new Parameter("tipval", null, null, null);
//			param.setCod("3");
//			param.setDesc("Rangos Importes y % Comisi\u00F3n");
//			lista.add(param);
			productoForm.setTipValList(lista);			
			lista = new ArrayList();
			tablasService.execute("MONEDA");
			
			lista = new ArrayList();
			for (Tabla	tipoVal : (List<Tabla>)tablasService.getTablasList()) {
				param = new Parameter("moneda", null, null, null);
				param.setCod(tipoVal.getCdCodTabla().trim());
				param.setDesc(tipoVal.getNbCodTablaCorto());
				lista.add(param);
			}
//			
//			param = new Parameter("moneda", null, null, null);
//			param.setCod("PES");
//			param.setDesc("$");
//			lista.add(param);
//			
//			param = new Parameter("moneda", null, null, null);
//			param.setCod("USD");
//			param.setDesc("u$s BNA");
//			lista.add(param);
//			
//			param = new Parameter("moneda", null, null, null);
//			param.setCod("EUR");
//			param.setDesc("eur BNA");
//			lista.add(param);			
			productoForm.setMonedaList(lista);

			
			lista = new ArrayList();
			param = new Parameter("porcvarmax", null, null, null);
			param.setCod("1");
			param.setDesc("10%");
			lista.add(param);
			
			param = new Parameter("porcvarmax", null, null, null);
			param.setCod("2");
			param.setDesc("20%");
			lista.add(param);

			param = new Parameter("porcvarmax", null, null, null);
			param.setCod("3");
			param.setDesc("50%");
			lista.add(param);			
			productoForm.setPorcVarMaxList(lista);
			
			
			result = getParamListService.execute(ParameterType.PROVEEDOR.getId(), "", "");			
			proveedorProductoList = getParamListService.getParameterList();
			if (result.isSuccesfull()) {
				productoForm.setProveedorProductoList(proveedorProductoList);
				result = getParamListService.execute(ParameterType.SECTOR.getId(), "", "");			
				sectorList = getParamListService.getParameterList();
//				productoForm.setCdSecSolServList(null);
//              productoForm.setCdSecConServList(null);
//				productoForm.setCdSecConFactList(null);
				
				if (result.isSuccesfull()) {
//					productoForm.setCdSecConFactList(sectorList);
//					productoForm.setCdSecSolServList(sectorList);
//					productoForm.setCdSecConServList(sectorList);
					
					result = getParamListService.execute(ParameterType.PROVEEDOR.getId(), "", "");			
					filtroProveedorList = getParamListService.getParameterList();
					if (result.isSuccesfull()) {
						productoForm.setFiltroProveedorList(filtroProveedorList);

						result = getParamListService.execute(ParameterType.TABLA_GRUPRO.getId(), "", "");			
						grupoProductoList = getParamListService.getParameterList();
						if (result.isSuccesfull()) {
							productoForm.setGrupoProductoList(grupoProductoList);
							productoForm.setFiltroGrupoList(grupoProductoList);

							result = getParamListService.execute(ParameterType.TABLA_UNIVAL.getId(), "", "");			
							uniValList = getParamListService.getParameterList();
							if (result.isSuccesfull()) {
								productoForm.setUniValList(uniValList);
							
							} else {
								throw new GlobalActionException(result.getErrorCode(), result.getErrorDesc());
							}
						} else {
							throw new GlobalActionException(result.getErrorCode(), result.getErrorDesc());
						}
					} else {
						throw new GlobalActionException(result.getErrorCode(), result.getErrorDesc());
					}
				} else {
					throw new GlobalActionException(result.getErrorCode(), result.getErrorDesc());
				}
			} else {
				throw new GlobalActionException(result.getErrorCode(), result.getErrorDesc());
			}
		} catch (GlobalServiceException e) {
			throw new GlobalActionException(e);
		} catch (Exception e) {
			log.debug(e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		}
	}


	/**
	 * Metodo que se encarga de editar el producto seleccionado
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward saveProducto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		String opcion           = request.getParameter("opcion");
		
		String cdProveedor        = request.getParameter("cdProveedor");
		String cdProducto         = request.getParameter("cdProducto");
		String nbProducto         = request.getParameter("nbProducto");
		String nbProductoCorto    = request.getParameter("nbProductoCorto");
		String cdGrupoProducto    = request.getParameter("cdGrupoProducto");
		String cdUniVal           = request.getParameter("cdUniVal");
		String cdSecSolServ       = request.getParameter("cdSecSolServ");
		String cdSecConServ       = request.getParameter("cdSecConServ");
		String cdSecConFact       = request.getParameter("cdSecConFact");
		String stProdImportPrest  = request.getParameter("stProdImportPrest");
		String stProdImportFact   = request.getParameter("stProdImportFact");
		String stRemServOblig     = request.getParameter("stRemServOblig");
		String stRemFactOblig     = request.getParameter("stRemFactOblig");
		String stAdmiteRemServ    = request.getParameter("stAdmiteRemServ");
		String stAdmiteRemFact    = request.getParameter("stAdmiteRemFact");
		String nbAtributoRef1     = request.getParameter("nbAtributoRef1");
		String nbAtributoRef2     = request.getParameter("nbAtributoRef2");
		String stConcilSinVal     = request.getParameter("stConcilSinVal");
		String stServSinConcil    = request.getParameter("stServSinConcil");
		String stFactSinConcil    = request.getParameter("stFactSinConcil");
		Integer nuDiaEmiFDesde    = Integer.parseInt(request.getParameter("nuDiaEmiFDesde"));
		Integer nuDiaEmiFHasta    = Integer.parseInt(request.getParameter("nuDiaEmiFHasta"));
		Integer nuDiaCierreFDesde = Integer.parseInt(request.getParameter("nuDiaCierreFDesde")); 
		Integer nuDiaCierreFHasta = Integer.parseInt(request.getParameter("nuDiaCierreFHasta"));
		String nbAtributoAdic1    = request.getParameter("nbAtributoAdic1");
		String nbAtributoAdic2    = request.getParameter("nbAtributoAdic2");
		String nbAtributoAdic3    = request.getParameter("nbAtributoAdic3");
		String nbAtributoAdic4    = request.getParameter("nbAtributoAdic4");
		String nbAtributoAdic5    = request.getParameter("nbAtributoAdic5");
		String stHabilitado       = request.getParameter("stHabilitado");
		String cdTipVal           = request.getParameter("cdTipVal");		
		String cdMoneda           = request.getParameter("cdMoneda");	
		Integer nuPorcVarMax      = Integer.parseInt(request.getParameter("nuPorcVarMax"));
			
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {			
			GetProductosService getProductosService = new GetProductosService(this.iWebClient); 
			
			result = getProductosService.saveProducto(opcion, cdProveedor, cdProducto, nbProducto, 
 							nbProductoCorto, cdGrupoProducto, cdUniVal, cdSecSolServ, cdSecConServ, cdSecConFact,
 							stProdImportPrest, stProdImportFact, stRemServOblig, stRemFactOblig, stAdmiteRemServ, 
 							stAdmiteRemFact, nbAtributoRef1, nbAtributoRef2, stConcilSinVal, stServSinConcil, 
 							stFactSinConcil, nuDiaEmiFDesde, nuDiaEmiFHasta, nuDiaCierreFDesde, nuDiaCierreFHasta,
 							nbAtributoAdic1, nbAtributoAdic2, nbAtributoAdic3, nbAtributoAdic4, nbAtributoAdic5, 
 							stHabilitado, cdTipVal, cdMoneda, nuPorcVarMax, this.getAutenticathedUserName(request));
			
			resp.put("result", result);
			JSONObject json = JSONObject.fromObject(resp);
			response.setContentType(AsciiConstants.CONTENT_TYPE_JSON);
			out = response.getWriter();
			out.print(json);
			if (out != null) {
				out.flush();
				out.close();
			}
		} catch (JSONException e) {
			log.error("Error execute Json: ", e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		} catch (GlobalServiceException e) {
			throw new GlobalActionException(e);
		} catch (Exception e) {
			log.error(e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public ActionForward deleteProducto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		String cdProveedor   = request.getParameter("cdProveedor");
		String cdProducto    = request.getParameter("cdProducto");
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			GetProductosService getProductosService = new GetProductosService(this.iWebClient); 
			result = getProductosService.deleteProducto(cdProveedor, cdProducto);
			resp.put("result", result);
			JSONObject json = JSONObject.fromObject(resp);
			response.setContentType(AsciiConstants.CONTENT_TYPE_JSON);
			out = response.getWriter();
			out.print(json);
			if (out != null) {
				out.flush();
				out.close();
			}
		} catch (JSONException e) {
			log.error("Error execute Json: ", e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		} catch (GlobalServiceException e) {
			throw new GlobalActionException(e);
		} catch (Exception e) {
			log.error(e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward getCombosSectores(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.initialise(request.getSession());

		GetParameterListService getParamListService = new GetParameterListService(
				this.iWebClient);
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			result = getParamListService.execute(ParameterType.SECTOR.getId(), "", "");
			filtroSectorList = getParamListService.getParameterList();
			resp.put("SectorList", filtroSectorList);
			resp.put("result", result);
			JSONObject jsonObject = JSONObject.fromObject(resp);
			response.setContentType(AsciiConstants.CONTENT_TYPE_JSON);
			out = response.getWriter();
			out.print(jsonObject);
			if (out != null) {
				out.flush();
				out.close();
			}
		} catch (JSONException e) {
			log.error("Error execute Json: ", e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		} catch (GlobalServiceException e) {
			throw new GlobalActionException(e);
		} catch (Exception e) {
			log.error(e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public ActionForward getCombosProveedores(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.initialise(request.getSession());

		GetParameterListService getParamListService = new GetParameterListService(
				this.iWebClient);
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			result = getParamListService.execute(ParameterType.PROVEEDOR.getId(), "", "");
			resp.put("ProveedorList", getParamListService.getParameterList());
			resp.put("result", result);
			JSONObject jsonObject = JSONObject.fromObject(resp);
			response.setContentType(AsciiConstants.CONTENT_TYPE_JSON);
			out = response.getWriter();
			out.print(jsonObject);
			if (out != null) {
				out.flush();
				out.close();
			}
		} catch (JSONException e) {
			log.error("Error execute Json: ", e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		} catch (GlobalServiceException e) {
			throw new GlobalActionException(e);
		} catch (Exception e) {
			log.error(e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward getVerificaExistenciaAgrupados(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.initialise(request.getSession());
		
		String cdProveedor     = request.getParameter("cdProveedor");
		String cdProducto      = request.getParameter("cdProducto");
		String nbAtributoAdic1 = request.getParameter("nbAtributoAdic1");
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			GetProductosService getProductosService = new GetProductosService(this.iWebClient); 
			result = getProductosService.VerificaExistenciaAgrupados(cdProveedor, cdProducto, nbAtributoAdic1);
			resp.put("result", result);
			JSONObject json = JSONObject.fromObject(resp);
			response.setContentType(AsciiConstants.CONTENT_TYPE_JSON);
			out = response.getWriter();
			out.print(json);
			if (out != null) {
				out.flush();
				out.close();
			}
		} catch (JSONException e) {
			log.error("Error execute Json: ", e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		} catch (GlobalServiceException e) {
			throw new GlobalActionException(e);
		} catch (Exception e) {
			log.error(e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		}
		return null;
	}
}
