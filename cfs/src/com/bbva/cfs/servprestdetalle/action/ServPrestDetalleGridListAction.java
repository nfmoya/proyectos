package com.bbva.cfs.servprestdetalle.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.itrsa.GeneralException;

import com.bbva.cfs.commons.action.CommonAction;
import com.bbva.cfs.commons.utils.AsciiConstants;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.servprestdetalle.form.ServPrestDetalleForm;
import com.bbva.cfs.servprestdetalle.model.ServPrestDetalle;
import com.bbva.cfs.servprestdetalle.service.GetServPrestDetalleService;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServPrestDetalleGridListAction extends CommonAction {
	private int cantPeriodos;
	ServPrestDetalleForm sfForm;
	// colnames
	String[] colNames;
	String[] colModels;
	String   expand;
	
	private String colModelsUnique;
	private ServPrestDetalleForm form;
	private String[] tot;
	// 
	private List<ServPrestDetalle> list;
	private List allRows;
	private List rowsToShow;
	private JSONObject jsonObctForm;
	private JSONArray jsonArray;

	private GetServPrestDetalleService service;

	public ServPrestDetalleGridListAction() {
		// TODO Auto-generated constructor stub
	}

	public ActionForward loadGridServPrestDetalle(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.tot =   new String[55];
		this.initialise(request.getSession());

		expand  = request.getParameter("expand");
		
		response.setContentType("application/json;charset=UTF-8");
		sfForm = (ServPrestDetalleForm) form;
		//String cantPeriodos = request.getParameter("cantPeriodos");
		//this.cantPeriodos = (new Integer(cantPeriodos)).intValue();
		list = new ArrayList<ServPrestDetalle>();

		allRows = fillRows();
		sortByProperty(allRows, request.getParameter("sidx"),
					request.getParameter("sord"));
		rowsToShow = paginate(request);
		jsonObctForm = convertToJSONGrid(request);
		jsonObctForm = loadJSONArray(rowsToShow, jsonObctForm);

		this.print(jsonObctForm, response);
		
		return null;
	}

	@SuppressWarnings("rawtypes")
	public Class getFilterClass() throws ClassNotFoundException {
		return null;
	}

	public void setParameters(HttpServletRequest request, ActionForm form)
			throws Exception {
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List fillRows() throws GeneralException {
		
		try {
            String consulta = "";
			service = new GetServPrestDetalleService(this.iWebClient);
                        
			result = service.execute(sfForm.getSelectedProveedor(), sfForm.getSelectedSector(),
                                                 sfForm.getSelectedGrupo(), sfForm.getSelectedProducto(),
                                                 sfForm.getFhRemito(), sfForm.getFhFinServ(), 
                                                 sfForm.getRemitoDesde(), sfForm.getRemitoHasta(),
                                                 sfForm.getCdLote(), sfForm.getStLoteDet());
            log.error(consulta);
			list = fillData(service.getSectoresList());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List fillData(List listSectores) {            
		List listaRet = new ArrayList();
		ServPrestDetalle tot = new ServPrestDetalle();
                
		tot.setCtServPrest(BigDecimal.ZERO);
		tot.setImPrecioTotal(BigDecimal.ZERO);
                
		for (int i = 0; i < listSectores.size(); i++) {
			Object object = listSectores.get(i);
			ServPrestDetalle frm = new ServPrestDetalle();
			ServPrestDetalle sector = (ServPrestDetalle) object;
                    
			tot.setCtServPrest(tot.getCtServPrest().add(sector.getCtServPrest() == null ? BigDecimal.ZERO : sector.getCtServPrest()));
			//tot.setCtServPrest(tot.getCtServPrest().add(sector.getCtServPrest() == null ? BigDecimal.ZERO : new BigDecimal ("1") ));
			tot.setImPrecioTotal(tot.getImPrecioTotal().add(sector.getImPrecioTotal() == null ? BigDecimal.ZERO : sector.getImPrecioTotal()));
                    
			frm.setCdSector(sector.getCdSector());
			frm.setCdGrupoProducto(sector.getCdGrupoProducto());
			frm.setCdProducto(sector.getCdProducto());
			frm.setNbDescripcion(sector.getNbDescripcion());
			frm.setCdLoteServ(sector.getCdLoteServ());
			frm.setCdRemito(sector.getCdRemito());
			frm.setFhRemito(sector.getFhRemito());
			frm.setFhFinServ(sector.getFhFinServ());
			frm.setCtServPrest(sector.getCtServPrest());
			frm.setImPrecioTotal(sector.getImPrecioTotal());
			frm.setNbObservaciones(sector.getNbObservaciones());
			frm.setStLoteDet(sector.getStLoteDet());
			frm.setCdConciliacion(sector.getCdConciliacion());
			frm.setNbAtributoRef1(sector.getNbAtributoRef1());
			frm.setNbAtributoRef2(sector.getNbAtributoRef2());
			frm.setNbTipVal(sector.getNbTipVal());

			listaRet.add(frm);
		}        
		tot.setCdSector("Total");
                
		armaStringTotales(tot);
		return listaRet;
	}

	private void armaStringTotales(ServPrestDetalle tot) {
		// TODO Auto-generated method stub
		this.tot[0] = "TOTAL";
		this.tot[1] = tot.getCtServPrest().toString();
		this.tot[2] = tot.getImPrecioTotal().toString();
	}

	public JSONObject convertToJSONeachAction(ActionForm form) {
		ServPrestDetalleForm frm = (ServPrestDetalleForm) form;

		JSONObject jsonObject = new JSONObject();

		return jsonObject;
	}

	// inventos de david

	/**
	 * Ordena la lista por las propiedades
	 * 
	 * @param rows
	 * @param property
	 * @param order
	 */
	private void sortByProperty(List rows, String property, String order) {
		try {
			BeanComparator comp = new BeanComparator(property);
			Collections.sort(rows, comp);
			if (order.equalsIgnoreCase("desc")) {
				Collections.reverse(rows);
			}
		} catch (Exception e) {
			log.error("Error sortByProperty", e);
		}
	}

	/**
	 * 
	 * @param request
	 * @return List
	 * @throws GeneralException
	 */
	protected List paginate(HttpServletRequest request) throws GeneralException {

		int page = Integer.parseInt(request.getParameter("page"));
		int rows = allRows.size(); //Integer.parseInt(request.getParameter("rows"));
		int top = 0;
		allRows = new ArrayList();
		try {
			allRows = list;

			top = page * rows;
			if (top > allRows.size()) {
				top = allRows.size();
			}

		} catch (Exception e) {
			log.error("Error paginate", e);
		}
		return allRows.subList((page - 1) * rows, top);
	}

	/**
	 * 
	 * 
	 * @param request
	 * @return JSONObject
	 * @throws ParseException
	 */
	private JSONObject convertToJSONGrid(HttpServletRequest request)
			throws ParseException {

		JSONObject jsonObjectForm = new JSONObject();
		// Calcular el total de p�ginas para el query
//			Integer total = new Integer(String.valueOf(Double.valueOf(String.valueOf(Math.ceil(allRows.size()/ Double.valueOf(request.getParameter("rows")).doubleValue()))).intValue()));
			jsonObjectForm.put("gridModelToShow", rowsToShow);
			jsonObjectForm.put("total", 1 );//total);
			jsonObjectForm.put("records", String.valueOf(allRows.size()));
			jsonObjectForm.put("rowsName", request.getParameter("rowsName"));
			jsonObjectForm.put("page","" + 1); //Integer.parseInt(request.getParameter("page")));
			jsonObjectForm.put("rows","" + allRows.size()); //Integer.parseInt(request.getParameter("rows")));

			jsonObjectForm.put("sidx", request.getParameter("sidx"));
			jsonObjectForm.put("sord", request.getParameter("sord"));
			jsonObjectForm.put(Constants.RESULT, super.result);
			jsonObjectForm.put("totales", tot);

		return jsonObjectForm;
	}

	/**
	 * 
	 * 
	 * @param rowsToShow
	 * @param jsonObject
	 * @return JSONObject
	 */
	private JSONObject loadJSONArray(List rowsToShow, JSONObject jsonObject) {
		jsonArray = JSONArray.fromObject(rowsToShow);
		jsonObject.put(Constants.GRID_MODEL_TO_SHOW, jsonArray);
		return jsonObject;
	}

	/**
	 * 
	 * @param jsonObject
	 * @param response
	 */
	private void print(JSONObject jsonObject, HttpServletResponse response) {
		response.setContentType(AsciiConstants.CONTENT_TYPE_JSON);
		PrintWriter out;
		try {
			out = response.getWriter();
			if (out != null) {
				out.print(jsonObject);
				out.flush();
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	@Override
//	public List fillRows(HttpSession session) throws GeneralException {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
