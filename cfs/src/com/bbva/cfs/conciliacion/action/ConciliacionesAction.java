package com.bbva.cfs.conciliacion.action;

import java.io.PrintWriter;
import java.util.ArrayList;
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
import com.bbva.cfs.conciliacion.form.ConciliacionForm;
import com.bbva.cfs.conciliacion.service.GetConciliacionConsultaService;
import com.bbva.cfs.conciliacion.service.GetConciliacionesService;
import com.bbva.cfs.producto.form.ProductoForm;
import com.bbva.cfs.producto.service.GetProductosService;
import com.bbva.cfs.proveedor.service.GetProveedoresPeriodosService;
import com.bbva.cfs.proveedor.service.GetProveedoresValoresService;
import com.bbva.cfs.usuario.form.UsuarioForm;
import com.bbva.cfs.commons.action.CommonAction;
import com.bbva.cfs.commons.exceptions.GlobalActionException;
import com.bbva.cfs.commons.exceptions.GlobalGrantException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.model.Session;
import com.bbva.cfs.commons.utils.AsciiConstants;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.commons.utils.Grants;
import com.bbva.cfs.commons.utils.ParameterType;
import com.bbva.cfs.commons.utils.ResultDatabase;
import com.bbva.cfs.general.service.GetTablasService;

/**
 * @author jmanrique
 * 
 */
@SuppressWarnings("unused")
public class ConciliacionesAction extends CommonAction {
	// private GetParameterListService getParamListService;

	private GetConciliacionesService getConciliacionesService;

	private Result result;
	private Session session;
	private Long userId;
	@SuppressWarnings("rawtypes")
	private List filtroProveedorList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filtroProductoList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filtroSectorList = new ArrayList();
	@SuppressWarnings("rawtypes")
	private List filtroPeriodoList = new ArrayList();
	private String sector;
	@SuppressWarnings("rawtypes")
	private List filtroConciliacionList = new ArrayList();

	private String cdProveedor;
	private String cdSector;
	private String cdProducto;
	private String cdPeriodo;
	private String stIgnoraval;
	private String nbObservaciones;
	private String stConciliacion;
    private String fhRemitoDesde;
    private String fhRemitoHasta;
    private String fhFinSerDesde;
    private String fhFinSerHasta;	
	private String listaServPres;
	private String listaServFact;
	private String listaServDife;

	private Integer conciliacion;

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		this.initialise(request.getSession());
		ConciliacionForm conciliacionForm = (ConciliacionForm) form;
		userId = this.getAutenticathedUserId(request);
		sector = this.getSector(request);

		conciliacionForm.setSector(sector);
		obtenerInformacionDeUsuario(request.getSession());
		verificarPermisos(request, conciliacionForm);
		obtenerCombos(conciliacionForm);

		// Verifica si viene desde la busqueda de conciliaciones para ver el
		// detalle.
		String nroConDetalle = request.getParameter("cdConciliacionDetalle");
		if (nroConDetalle != null) {
			conciliacionForm.setCdConciliacion(nroConDetalle);
		} else {
			conciliacionForm.setCdConciliacion("0");
		}

		return doFindSuccess(mapping);
	}

	private void obtenerInformacionDeUsuario(HttpSession httpSession) {
		session = (Session) httpSession.getAttribute(Constants.SESSION);
	}

	/* Verifico los permisos */
	private void verificarPermisos(HttpServletRequest request,
			ConciliacionForm conciliacionForm) throws GlobalGrantException {

		// Otorgo si a todo hasta nueva definicion
		if (this.checkPrivilege(request,
				Grants.CONCILIACION_GRABAR_SIN_APROBAR.getId())) {
			log.info("Grabar Conciliacion sin aprobar");
			conciliacionForm.setSaveGrant("S");
		}
		if (this.checkPrivilege(request, Grants.CONCILIACION_APROBAR.getId())) {
			log.info("Aprobar Conciliacion");
			conciliacionForm.setApproveGrant("S");
		}
		if (this.checkPrivilege(request,
				Grants.CONCILIACION_DIFERENCIAS.getId())) {
			log.info("Agregar Diferencias Conciliacion");
			conciliacionForm.setDifferGrant("S");
		}
	}

	@SuppressWarnings("unchecked")
	private void obtenerCombos(ConciliacionForm conciliacionForm)
			throws GlobalActionException {
		GetParameterListService getParamListService = new GetParameterListService(
				this.iWebClient);
		try {
			result = getParamListService.execute(
					ParameterType.PROVEEDOR.getId(), "", "");
			filtroProveedorList = getParamListService.getParameterList();
			if (result.isSuccesfull()) {		
				if (filtroProveedorList.size() == 1) {
					conciliacionForm.setCdProveedor(((Parameter)filtroProveedorList.get(0)).getCod());
				} else {
                    Parameter param = new Parameter(ParameterType.PROVEEDOR.getId(), null, null, null);
                    param.setCod("0");
                    param.setDesc("Sin Proveedor");					
                    filtroProveedorList.add(0, param);
				}
				conciliacionForm.setFiltroProveedorList(filtroProveedorList);
				
				result = getParamListService.execute(
						ParameterType.SECTOR.getId(), "", "");
				filtroSectorList = getParamListService.getParameterList();

				if (result.isSuccesfull()) {
					conciliacionForm.setFiltroSectorList(filtroSectorList);
				} else {
					throw new GlobalActionException(result.getErrorCode(),
							result.getErrorDesc());
				}
			} else {
				throw new GlobalActionException(result.getErrorCode(),
						result.getErrorDesc());
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

	@SuppressWarnings("unchecked")
	public ActionForward getCombosProveedores(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.initialise(request.getSession());
		String opcion = request.getParameter("opcion");
		String cdProveedor = request.getParameter("cdProveedor");
		String cdSector = request.getParameter("cdSector");

		GetParameterListService getParamListService = new GetParameterListService(
				this.iWebClient);
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			if (opcion.equals("1")) {
				cdSector = (cdSector.equals("0") ? "" : cdSector);
				result = getParamListService.execute(
						ParameterType.CONC_PROVEEDOR_PRODUCTO.getId(),
						cdProveedor, cdSector);
				filtroProductoList = getParamListService.getParameterList();
				resp.put("ProductoPrecioList", filtroProductoList);
				resp.put("result", result);
				JSONObject jsonObject = JSONObject.fromObject(resp);
				response.setContentType(AsciiConstants.CONTENT_TYPE_JSON);
				out = response.getWriter();
				out.print(jsonObject);
				if (out != null) {
					out.flush();
					out.close();
				}
			} else {
				result = getParamListService.execute(
						ParameterType.CONC_PROVEEDOR_PERIODO.getId(),
						cdProveedor, "");
				filtroPeriodoList = getParamListService.getParameterList();
				resp.put("ProveedorPeriodoList", filtroPeriodoList);
				resp.put("result", result);
				JSONObject jsonObject = JSONObject.fromObject(resp);
				response.setContentType(AsciiConstants.CONTENT_TYPE_JSON);
				out = response.getWriter();
				out.print(jsonObject);
				if (out != null) {
					out.flush();
					out.close();
				}
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
	public ActionForward getDatosPeriodo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.initialise(request.getSession());
		String cdProveedor = request.getParameter("cdProveedor");
		String cdPeriodo = request.getParameter("cdPeriodo");
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			GetProveedoresPeriodosService getProveedoresPeriodosService = new GetProveedoresPeriodosService(
					this.iWebClient);
			result = getProveedoresPeriodosService.execute(cdProveedor, cdPeriodo);
			resp.put("DetallePeriodo", getProveedoresPeriodosService.getProveedoresPeriodosList());
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
	public ActionForward getDatosProducto(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.initialise(request.getSession());
		String cdProveedor = request.getParameter("cdProveedor");
		String cdSector    = request.getParameter("cdSector");
		String cdProducto  = request.getParameter("cdProducto");
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			GetConciliacionesService getConciliacionesService = new GetConciliacionesService(
					this.iWebClient);
			result = getConciliacionesService.getDatosProducto(cdProveedor, cdSector, cdProducto);
			resp.put("DetalleProducto", getConciliacionesService.getProductosList());
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
	public ActionForward getDatosUniVal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		String cdUniVal = request.getParameter("cdUniVal");
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			GetTablasService getTablasService = new GetTablasService(
					this.iWebClient);
			result = getTablasService.execute("UNIVAL", cdUniVal);
			resp.put("DetalleUniVal", getTablasService.getTablasList());
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
	public ActionForward getDatosProveedorValor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.initialise(request.getSession());
		String cdProveedor = request.getParameter("cdProveedor");
		String cdPeriodo = request.getParameter("cdPeriodo");
		String cdUniVal = request.getParameter("cdUniVal");
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			GetProveedoresValoresService getProveedoresValoresService = new GetProveedoresValoresService(
					this.iWebClient);
			result = getProveedoresValoresService.execute(cdProveedor,
					cdPeriodo, cdUniVal);
			resp.put("DetalleProveedorValor",
					getProveedoresValoresService.getProveedoresValoresList());
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
	public ActionForward getComboConciliacion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.initialise(request.getSession());
		String filtro = request.getParameter("filtro");

		GetParameterListService getParamListService = new GetParameterListService(
				this.iWebClient);
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			result = getParamListService.execute(
					ParameterType.CONC_NRO_CONCILIACION.getId(), filtro, "");
			filtroConciliacionList = getParamListService.getParameterList();
			resp.put("ConciliacionList", filtroConciliacionList);
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
	public ActionForward saveConciliacion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.initialise(request.getSession());

		Integer cdConciliacion = Integer.parseInt(request
				.getParameter("cdConciliacion"));
		String cdProveedor     = request.getParameter("cdProveedor");
		String cdSector        = request.getParameter("cdSector");
		String cdProducto      = request.getParameter("cdProducto");
		String cdPeriodo       = request.getParameter("cdPeriodo");
		String stIgnoraval     = request.getParameter("stIgnoraval");
		String nbObservaciones = request.getParameter("nbObservaciones");
		String stConciliacion  = request.getParameter("stConciliacion");
	    String fhRemitoDesde   = request.getParameter("fhRemitoDesde");
	    String fhRemitoHasta   = request.getParameter("fhRemitoHasta");
	    String fhFinSerDesde   = request.getParameter("fhFinServicioDesde");
	    String fhFinSerHasta   = request.getParameter("fhFinServicioHasta");
	    
		String listaServPres   = request.getParameter("listaServPres");
		String listaServFact   = request.getParameter("listaServFact");
		String listaServDife   = request.getParameter("listaServDife");

		Integer cdConciliacionCab = 0;
		
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			GetConciliacionesService getConciliacionesService = new GetConciliacionesService(
					this.iWebClient);
			
			List<String> aServPres = new ArrayList<String>(); 
			List<String> aServFact = new ArrayList<String>(); 

			String servPres = listaServPres;
			String servFact = listaServFact;
			int index;
            String serv;
            
			while (true) {
               if (servPres.length()>=1500) {
                  index    = servPres.indexOf("|", 1450);
                  serv     = servPres.substring(0, index-1);
                  servPres = servPres.substring(index+1);
                  aServPres.add(serv);
               } else {
                  aServPres.add(servPres);
                  break;
               }
			}

			while (true) {
               if (servFact.length()>=1500) {
                  index    = servFact.indexOf("|", 1450);
                  serv     = servFact.substring(0, index-1);
                  servFact = servFact.substring(index+1);
                  aServFact.add(serv);
               } else {
                  aServFact.add(servFact);
                  break;
               }
			}
			
			result = getConciliacionesService.saveConciliacion(cdConciliacion,
					cdProveedor, cdSector, cdProducto, cdPeriodo, stIgnoraval,
					nbObservaciones, stConciliacion, fhRemitoDesde, fhRemitoHasta,
				    fhFinSerDesde, fhFinSerHasta, aServPres, aServFact, 
					listaServDife, this.getAutenticathedUserName(request));

//			cdConciliacionCab = Integer.valueOf((String)getConciliacionesService.getConciliacionList().get(0));
			cdConciliacionCab = Integer.valueOf(getConciliacionesService.getConciliacionList().get(0).toString());
						
			resp.put("Cd_Conciliacion", getConciliacionesService
					.getConciliacionList().get(0));
			
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
	public ActionForward consultaConciliacion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.initialise(request.getSession());

		Integer cdConciliacion = Integer.parseInt(request
				.getParameter("cdConciliacion"));
		String cdProveedor = request.getParameter("cdProveedor");
		String cdSector = request.getParameter("cdSector");
		String cdProducto = request.getParameter("cdProducto");
		String cdPeriodo = request.getParameter("cdPeriodo");

		GetConciliacionConsultaService getConciliacionConsultaService = new GetConciliacionConsultaService(
				this.iWebClient);
		PrintWriter out = null;
		@SuppressWarnings("rawtypes")
		Map resp = new HashMap();
		try {
			result = getConciliacionConsultaService.getConsultaConciliacion(
					cdConciliacion, cdProveedor, cdSector, cdProducto,
					cdPeriodo);
			resp.put("ConciliacionCabeceraList", getConciliacionConsultaService
					.getConciliacionCabeceraList());
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

}
