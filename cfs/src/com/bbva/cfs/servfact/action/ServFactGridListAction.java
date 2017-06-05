package com.bbva.cfs.servfact.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.itrsa.GeneralException;

import com.bbva.ar.utils.Validar;
import com.bbva.cfs.commons.action.CommonAction;
import com.bbva.cfs.commons.action.GridAction;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.AsciiConstants;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.proveedor.model.ProveedorPeriodo;
import com.bbva.cfs.proveedor.service.GetProveedoresPeriodosService;
import com.bbva.cfs.servfact.form.ServFactForm;
import com.bbva.cfs.servfact.model.ServFact;
import com.bbva.cfs.servfact.model.ServFactShort;
import com.bbva.cfs.servfact.service.GetServFactService;

//public class ServFactGridListAction extends GridAction {
public class ServFactGridListAction extends CommonAction {

	private int cantPeriodos;
	ServFactForm sfForm;
	// colnames
	String listA[];
	String[] colNames;
	String[] colModels;
	String   expand;
	
	
	private String colModelsUnique;
	private ServFactForm form;
	private String[] tot;
	// 
	private List<ServFact> list;
	private List allRows;
	private List rowsToShow;
	private JSONObject jsonObctForm;
	private JSONArray jsonArray;

	private GetServFactService service;

	public ServFactGridListAction() {
		// TODO Auto-generated constructor stub
	}

	public ActionForward loadGridServFact(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.tot =   new String[55];
		this.initialise(request.getSession());

		expand  = request.getParameter("expand");
		
		response.setContentType("application/json;charset=UTF-8");
		sfForm = (ServFactForm) form;
		String cantPeriodos = request.getParameter("cantPeriodos");
		this.cantPeriodos = (new Integer(cantPeriodos)).intValue();
		list = new ArrayList<ServFact>();

//		allRows = fillRows();
		allRows = fillRowsShort();
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
			service = new GetServFactService(this.iWebClient);
			if(expand.equalsIgnoreCase("no"))
			result = service.execute(sfForm.getSelectedProveedor(), sfForm.getSelectedSector(),
					sfForm.getSelectedProducto(), sfForm.getSelectedGrupo(), sfForm.getHabilitadoSel(),
					sfForm.getFhDesde(), sfForm.getFhHasta(),sfForm.getSelectedComp(), sfForm.getCdComprobante(), 
					sfForm.getRemitoDesde(), sfForm.getRemitoHasta(), sfForm.getCdLote(), cantPeriodos);
			else
			result = service.execute(sfForm.getSelectedProveedor(), sfForm.getSelectedSector(),
					sfForm.getSelectedGrupo(), sfForm.getHabilitadoSel(),
					sfForm.getFhDesde(), sfForm.getFhHasta(),sfForm.getSelectedComp(), sfForm.getCdComprobante(), 
					sfForm.getRemitoDesde(), sfForm.getRemitoHasta(), sfForm.getCdLote(), cantPeriodos , sfForm.getSelectedProducto());


			list = fillData(service.getServFactList());
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List fillData(List listSectores) {
		List listaRet = new ArrayList();
		ServFact tot = new ServFact();
		for (int i = 0; i < listSectores.size(); i++) {
			Object object = listSectores.get(i);
			ServFact frm = new ServFact();
			ServFact sector = (ServFact) object;
			
			tot = totales(tot , sector);
			
			frm.setCdProd(sector.getCdProd());
			frm.setCdSector(sector.getCdSector());
			frm.setCdGrupoProd(sector.getCdGrupoProd());
			
			frm.setCtServFactMes1(sector.getCtServFactMes1());
			frm.setCtRemitosMes1(sector.getCtRemitosMes1());
			frm.setValServFactMes1(sector.getValServFactMes1());
			frm.setCtRemitosAConcilMes1(sector.getCtRemitosAConcilMes1());
            frm.setCtServFactMes2(sector.getCtServFactMes2());
            frm.setCtRemitosMes2(sector.getCtRemitosMes2());
            frm.setValServFactMes2(sector.getValServFactMes2());
            frm.setCtRemitosAConcilMes2(sector.getCtRemitosAConcilMes2());
            frm.setCtServFactMes3(sector.getCtServFactMes3());
            frm.setCtRemitosMes3(sector.getCtRemitosMes3());
            frm.setValServFactMes3(sector.getValServFactMes3());
            frm.setCtRemitosAConcilMes3(sector.getCtRemitosAConcilMes3());
            frm.setCtServFactMes4(sector.getCtServFactMes4());
            frm.setCtRemitosMes4(sector.getCtRemitosMes4());
            frm.setValServFactMes4(sector.getValServFactMes4());
            frm.setCtRemitosAConcilMes4(sector.getCtRemitosAConcilMes4());
            frm.setCtServFactMes5(sector.getCtServFactMes5());
            frm.setCtRemitosMes5(sector.getCtRemitosMes5());
            frm.setValServFactMes5(sector.getValServFactMes5());
            frm.setCtRemitosAConcilMes5(sector.getCtRemitosAConcilMes5());
            frm.setCtServFactMes6(sector.getCtServFactMes6());
            frm.setCtRemitosMes6(sector.getCtRemitosMes6());
            frm.setValServFactMes6(sector.getValServFactMes6());
            frm.setCtRemitosAConcilMes6(sector.getCtRemitosAConcilMes6());
            frm.setCtServFactMes7(sector.getCtServFactMes7());
            frm.setCtRemitosMes7(sector.getCtRemitosMes7());
            frm.setValServFactMes7(sector.getValServFactMes7());
            frm.setCtRemitosAConcilMes7(sector.getCtRemitosAConcilMes7());
            frm.setCtServFactMes8(sector.getCtServFactMes8());
            frm.setCtRemitosMes8(sector.getCtRemitosMes8());
            frm.setValServFactMes8(sector.getValServFactMes8());
            frm.setCtRemitosAConcilMes8(sector.getCtRemitosAConcilMes8());
            frm.setCtServFactMes9(sector.getCtServFactMes9());
            frm.setCtRemitosMes9(sector.getCtRemitosMes9());
            frm.setValServFactMes9(sector.getValServFactMes9());
            frm.setCtRemitosAConcilMes9(sector.getCtRemitosAConcilMes9());
            frm.setCtServFactMes10(sector.getCtServFactMes10());
            frm.setCtRemitosMes10(sector.getCtRemitosMes10());
            frm.setValServFactMes10(sector.getValServFactMes10());
            frm.setCtRemitosAConcilMes10(sector.getCtRemitosAConcilMes10());;
            frm.setCtServFactMes11(sector.getCtServFactMes11());
            frm.setCtRemitosMes11(sector.getCtRemitosMes11());
            frm.setValServFactMes11(sector.getValServFactMes11());
            frm.setCtRemitosAConcilMes11(sector.getCtRemitosAConcilMes11());
            frm.setCtServFactMes12(sector.getCtServFactMes12());
            frm.setCtRemitosMes12(sector.getCtRemitosMes12());
            frm.setValServFactMes12(sector.getValServFactMes12());
            frm.setCtRemitosAConcilMes12(sector.getCtRemitosAConcilMes12());

			frm.setCtServFactTotal(sector.getCtServFactTotal());
			frm.setCtRemitosTotal(sector.getCtRemitosTotal());
			frm.setValServFactTotal(sector.getValServFactTotal());
			frm.setCtRemitosAConcilTotal(sector.getCtRemitosAConcilTotal());
			frm.setValBrutaTotal(sector.getValBrutaTotal());
			frm.setValNetoTotal(sector.getValNetoTotal());
			frm.setNuPorcVarMax(sector.getNuPorcVarMax());
			
			
			listaRet.add(frm);
		}
		
		tot.setCdProd("Total");
		armaStringTotales(tot);
		return listaRet;
	}

	private void armaStringTotales(ServFact tot2) {
		// TODO Auto-generated method stub
		this.tot[0]  = "TOTAL";
		this.tot[1]  = tot2.getCtServFactMes1().toString();
		this.tot[2]  = tot2.getValServFactMes1().toString(); 
		this.tot[3]  = tot2.getCtRemitosMes1().toString();
		this.tot[4]  = tot2.getCtRemitosAConcilMes1().toString();
		this.tot[5]  = tot2.getCtServFactMes2().toString();
		this.tot[6]  = tot2.getValServFactMes2().toString();
		this.tot[7]  = tot2.getCtRemitosMes2().toString();
		this.tot[8]  = tot2.getCtRemitosAConcilMes2().toString();
		this.tot[9]  = tot2.getCtServFactMes3().toString();
		this.tot[10] = tot2.getValServFactMes3().toString();
		this.tot[11] = tot2.getCtRemitosMes3().toString();
		this.tot[12] = tot2.getCtRemitosAConcilMes3().toString();
		this.tot[13] = tot2.getCtServFactMes4().toString();
		this.tot[14] = tot2.getValServFactMes4().toString();
		this.tot[15] = tot2.getCtRemitosMes4().toString();
		this.tot[16] = tot2.getCtRemitosAConcilMes4().toString();
		this.tot[17] = tot2.getCtServFactMes5().toString();
		this.tot[18] = tot2.getValServFactMes5().toString();
		this.tot[19] = tot2.getCtRemitosMes5().toString();
		this.tot[20] = tot2.getCtRemitosAConcilMes5().toString();
		this.tot[21] = tot2.getCtServFactMes6().toString();
		this.tot[22] = tot2.getValServFactMes6().toString();
		this.tot[23] = tot2.getCtRemitosMes6().toString();
		this.tot[24] = tot2.getCtRemitosAConcilMes6().toString();
		this.tot[25] = tot2.getCtServFactMes7().toString();
		this.tot[26] = tot2.getValServFactMes7().toString();
		this.tot[27] = tot2.getCtRemitosMes7().toString();
		this.tot[28] = tot2.getCtRemitosAConcilMes7().toString();
		this.tot[29] = tot2.getCtServFactMes8().toString();
		this.tot[30] = tot2.getValServFactMes8().toString();
		this.tot[31] = tot2.getCtRemitosMes8().toString();
		this.tot[32] = tot2.getCtRemitosAConcilMes8().toString();
		this.tot[33] = tot2.getCtServFactMes9().toString();
		this.tot[34] = tot2.getValServFactMes9().toString();
		this.tot[35] = tot2.getCtRemitosMes9().toString();
		this.tot[36] = tot2.getCtRemitosAConcilMes9().toString();
		this.tot[37] = tot2.getCtServFactMes10().toString();
		this.tot[38] = tot2.getValServFactMes10().toString();
		this.tot[39] = tot2.getCtRemitosMes10().toString();
		this.tot[40] = tot2.getCtRemitosAConcilMes10().toString();
		this.tot[41] = tot2.getCtServFactMes11().toString();
		this.tot[42] = tot2.getValServFactMes11().toString();
		this.tot[43] = tot2.getCtRemitosMes11().toString();
		this.tot[44] = tot2.getCtRemitosAConcilMes11().toString();
		this.tot[45] = tot2.getCtServFactMes12().toString();
		this.tot[46] = tot2.getValServFactMes12().toString();
		this.tot[47] = tot2.getCtRemitosMes12().toString();
		this.tot[48] = tot2.getCtRemitosAConcilMes12().toString();
		
		this.tot[49] = tot2.getCtServFactTotal().toString();
		this.tot[50] = tot2.getValServFactTotal().toString();
		this.tot[51] = tot2.getValBrutaTotal().toString();
		this.tot[52] = tot2.getValNetoTotal().toString();
		this.tot[53] = tot2.getCtRemitosTotal().toString();
		this.tot[54] = tot2.getCtRemitosAConcilTotal().toString();
		
	}

	private ServFact totales(ServFact frm,ServFact sector ) {
		String grupo = sector.getCdGrupoProd().substring(0,6);
		frm.setCtServFactMes1(frm.getCtServFactMes1() + (sector.getCtServFactMes1()));
        frm.setValServFactMes1(frm.getValServFactMes1().add(sector.getValServFactMes1()) );
		if (!grupo.equals("NO_CON")) {
			frm.setCtRemitosMes1(frm.getCtRemitosMes1() + sector.getCtRemitosMes1());
			frm.setCtRemitosAConcilMes1( frm.getCtRemitosAConcilMes1() + sector.getCtRemitosAConcilMes1() );
		} else {
			frm.setCtRemitosMes1(0L);
			frm.setCtRemitosAConcilMes1(0L);
		}
        
        frm.setCtServFactMes2(frm.getCtServFactMes2() + (sector.getCtServFactMes2()));
        frm.setValServFactMes2(frm.getValServFactMes2().add(sector.getValServFactMes2()) );
		if (!grupo.equals("NO_CON")) {
			frm.setCtRemitosMes2(frm.getCtRemitosMes2() + sector.getCtRemitosMes2());
			frm.setCtRemitosAConcilMes2( frm.getCtRemitosAConcilMes2() + sector.getCtRemitosAConcilMes2() );
		} else {
			frm.setCtRemitosMes2(0L);
			frm.setCtRemitosAConcilMes2(0L);
		}
        
        frm.setCtServFactMes3(frm.getCtServFactMes3() + (sector.getCtServFactMes3()));
        frm.setValServFactMes3(frm.getValServFactMes3().add(sector.getValServFactMes3()) );
		if (!grupo.equals("NO_CON")) {
			frm.setCtRemitosMes3(frm.getCtRemitosMes3() + sector.getCtRemitosMes3());
      		frm.setCtRemitosAConcilMes3( frm.getCtRemitosAConcilMes3() + sector.getCtRemitosAConcilMes3() );
		} else {
			frm.setCtRemitosMes3(0L);
			frm.setCtRemitosAConcilMes3(0L);
		}
        
        frm.setCtServFactMes4(frm.getCtServFactMes4() + (sector.getCtServFactMes4()));
        frm.setValServFactMes4(frm.getValServFactMes4().add(sector.getValServFactMes4()) );
		if (!grupo.equals("NO_CON")) {
			frm.setCtRemitosMes4(frm.getCtRemitosMes4() + sector.getCtRemitosMes4());
      		frm.setCtRemitosAConcilMes4( frm.getCtRemitosAConcilMes4() + sector.getCtRemitosAConcilMes4() );
		} else {
			frm.setCtRemitosMes4(0L);
			frm.setCtRemitosAConcilMes4(0L);
		}

        frm.setCtServFactMes5(frm.getCtServFactMes5() + (sector.getCtServFactMes5()));
        frm.setValServFactMes5(frm.getValServFactMes5().add(sector.getValServFactMes5()) );
		if (!grupo.equals("NO_CON")) {
			frm.setCtRemitosMes5(frm.getCtRemitosMes5() + sector.getCtRemitosMes5());
			frm.setCtRemitosAConcilMes5( frm.getCtRemitosAConcilMes5() + sector.getCtRemitosAConcilMes5() );
		} else {
			frm.setCtRemitosMes5(0L);
			frm.setCtRemitosAConcilMes5(0L);
		}
        
        frm.setCtServFactMes6(frm.getCtServFactMes6() + (sector.getCtServFactMes6()));
        frm.setValServFactMes6(frm.getValServFactMes6().add(sector.getValServFactMes6()) );
		if (!grupo.equals("NO_CON")) {
			frm.setCtRemitosMes6(frm.getCtRemitosMes6() + sector.getCtRemitosMes6());
			frm.setCtRemitosAConcilMes6( frm.getCtRemitosAConcilMes6() + sector.getCtRemitosAConcilMes6() );
		} else {
			frm.setCtRemitosMes6(0L);
			frm.setCtRemitosAConcilMes6(0L);
		}

        frm.setCtServFactMes7(frm.getCtServFactMes7() + (sector.getCtServFactMes7()));
        frm.setValServFactMes7(frm.getValServFactMes7().add(sector.getValServFactMes7()) );
		if (!grupo.equals("NO_CON")) {
			frm.setCtRemitosMes7(frm.getCtRemitosMes7() + sector.getCtRemitosMes7());
			frm.setCtRemitosAConcilMes7( frm.getCtRemitosAConcilMes7() + sector.getCtRemitosAConcilMes7() );
		} else {
			frm.setCtRemitosMes7(0L);
			frm.setCtRemitosAConcilMes7(0L);
		}

        frm.setCtServFactMes8(frm.getCtServFactMes8() + (sector.getCtServFactMes8()));
        frm.setValServFactMes8(frm.getValServFactMes8().add(sector.getValServFactMes8()) );
		if (!grupo.equals("NO_CON")) {
			frm.setCtRemitosMes8(frm.getCtRemitosMes8() + sector.getCtRemitosMes8());
			frm.setCtRemitosAConcilMes8( frm.getCtRemitosAConcilMes8() + sector.getCtRemitosAConcilMes8() );
		} else {
			frm.setCtRemitosMes8(0L);
			frm.setCtRemitosAConcilMes8(0L);
		}

        frm.setCtServFactMes9(frm.getCtServFactMes9() + (sector.getCtServFactMes9()));
        frm.setValServFactMes9(frm.getValServFactMes9().add(sector.getValServFactMes9()) );
		if (!grupo.equals("NO_CON")) {
			frm.setCtRemitosMes9(frm.getCtRemitosMes9() + sector.getCtRemitosMes9());
			frm.setCtRemitosAConcilMes9( frm.getCtRemitosAConcilMes9() + sector.getCtRemitosAConcilMes9() );
		} else {
			frm.setCtRemitosMes9(0L);
			frm.setCtRemitosAConcilMes9(0L);
		}

        frm.setCtServFactMes10(frm.getCtServFactMes10() + (sector.getCtServFactMes10()));
        frm.setValServFactMes10(frm.getValServFactMes10().add(sector.getValServFactMes10()) );
		if (!grupo.equals("NO_CON")) {
			frm.setCtRemitosMes10(frm.getCtRemitosMes10() + sector.getCtRemitosMes10());
			frm.setCtRemitosAConcilMes10( frm.getCtRemitosAConcilMes10() + sector.getCtRemitosAConcilMes10() );
		} else {
			frm.setCtRemitosMes10(0L);
			frm.setCtRemitosAConcilMes10(0L);
		}

        frm.setCtServFactMes11(frm.getCtServFactMes11() + (sector.getCtServFactMes11()));
        frm.setValServFactMes11(frm.getValServFactMes11().add(sector.getValServFactMes11()) );
		if (!grupo.equals("NO_CON")) {
			frm.setCtRemitosMes11(frm.getCtRemitosMes11() + sector.getCtRemitosMes11());
			frm.setCtRemitosAConcilMes11( frm.getCtRemitosAConcilMes11() + sector.getCtRemitosAConcilMes11() );
		} else {
			frm.setCtRemitosMes11(0L);
			frm.setCtRemitosAConcilMes11(0L);
		}

        frm.setCtServFactMes12(frm.getCtServFactMes12() + (sector.getCtServFactMes12()));
        frm.setValServFactMes12(frm.getValServFactMes12().add(sector.getValServFactMes12()) );
		if (!grupo.equals("NO_CON")) {
			frm.setCtRemitosMes12(frm.getCtRemitosMes12() + sector.getCtRemitosMes12());
			frm.setCtRemitosAConcilMes12( frm.getCtRemitosAConcilMes12() + sector.getCtRemitosAConcilMes12() );
		} else {
        frm.setCtRemitosMes12(0L);
        frm.setCtRemitosAConcilMes12(0L);
		}
        
        frm.setCtServFactTotal(frm.getCtServFactTotal() + (sector.getCtServFactTotal()));
        frm.setValServFactTotal(frm.getValServFactTotal().add(sector.getValServFactTotal()));
		if (!grupo.equals("NO_CON")) {
			frm.setCtRemitosTotal(frm.getCtRemitosTotal() + sector.getCtRemitosTotal());
			frm.setCtRemitosAConcilTotal(frm.getCtRemitosAConcilTotal() + sector.getCtRemitosAConcilTotal());
		} else {
			frm.setValBrutaTotal(frm.getValBrutaTotal().add(sector.getValBrutaTotal()));
			frm.setValNetoTotal(frm.getValNetoTotal().add(sector.getValNetoTotal()));
		}
		return frm;
	}

	public JSONObject convertToJSONeachAction(ActionForm form) {
		ServFactForm frm = (ServFactForm) form;

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
			jsonObjectForm.put("total", 1); //total);
			jsonObjectForm.put("records", String.valueOf(allRows.size()));
			jsonObjectForm.put("rowsName", request.getParameter("rowsName"));
			jsonObjectForm.put("page","" + "" + 1); //Integer.parseInt(request.getParameter("page")));
			jsonObjectForm.put("rows","" + "" + allRows.size()); //Integer.parseInt(request.getParameter("rows")));
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

// PRUEBA PARA timeouts

	@SuppressWarnings("unchecked")
	private void getPeriodos(String cdProveedor, String cdPerDesde,
			String cdPerHasta, String cdLote) {
		GetProveedoresPeriodosService gpps = new GetProveedoresPeriodosService(this.iWebClient);
		try {
			Result resulto = gpps.execute(cdProveedor, cdPerDesde, cdPerHasta, cdLote);
			listA = new String[12];
			listA = fillData2(gpps.getProveedoresPeriodosList());

		} catch (Exception e) {
			log.error(e);
		}

	}

	private String[] fillData2(List listPP) {
		String lista[] = new String[12];
		for (int i = 0; i < listPP.size(); i++) {
			Object object = listPP.get(i);
			ProveedorPeriodo added = new ProveedorPeriodo();
			ProveedorPeriodo pp = (ProveedorPeriodo) object;
			lista[i] = pp.getCdPeriodoFact();

		}
		return lista;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List fillRowsShort() throws GeneralException {
		
		try {
			getPeriodos(sfForm.getSelectedProveedor(),sfForm.getFhDesde(), sfForm.getFhHasta(), sfForm.getCdLote());
			service = new GetServFactService(this.iWebClient);
			if(expand.equalsIgnoreCase("no"))
			result = service.execute(sfForm.getSelectedProveedor(), sfForm.getSelectedSector(),
					sfForm.getSelectedProducto(), sfForm.getSelectedGrupo(), sfForm.getHabilitadoSel(),
					sfForm.getFhDesde(), sfForm.getFhHasta(),sfForm.getSelectedComp(), sfForm.getCdComprobante(), 
					sfForm.getRemitoDesde(), sfForm.getRemitoHasta(), sfForm.getCdLote(), 0);
			else
			result = service.execute(sfForm.getSelectedProveedor(), sfForm.getSelectedSector(),
					sfForm.getSelectedProducto(), sfForm.getSelectedGrupo(), sfForm.getHabilitadoSel(),
					sfForm.getFhDesde(), sfForm.getFhHasta(),sfForm.getSelectedComp(), sfForm.getCdComprobante(), 
					sfForm.getRemitoDesde(), sfForm.getRemitoHasta(), sfForm.getCdLote(), 1);


			list = fillDataShort(service.getServFactList());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return list;
	}

	
	private List fillDataShort(List servFactList) {
		
		List lista = new ArrayList() ;		
		String claveAnt = new String();
		ServFactShort sfs = servFactList.size() == 0? null : (ServFactShort) servFactList.get(0);
		ServFact linea = new ServFact() ;
		ServFact tot = new ServFact() ;
		
		if(sfs != null){
			claveAnt = sfs.getCdProd()+sfs.getCdSector()+sfs.getCdGrupoProd();
			int i = 0;
			for (Object object : servFactList) {
	//			System.out.println("Vuelta:"+ i++);
				sfs = (ServFactShort) object;
				String clave =  sfs.getCdProd()+sfs.getCdSector()+sfs.getCdGrupoProd();
				if(clave.equalsIgnoreCase(claveAnt)){
					linea = validarYcargar(sfs, linea ) ;

				}else{
					lista.add(linea);
					tot = totales(tot , linea);
					linea = new ServFact() ;
					linea = validarYcargar(sfs, linea ) ;				
				}
				claveAnt = clave;		
				
			}
			if(!Validar.esVacio(claveAnt)){
				tot = totales(tot , linea);
				lista.add(linea);
			}
			tot.setCdProd("Total");
			armaStringTotales(tot);
		
		}
		
		return lista;
	}
	private ServFact validarYcargar(ServFactShort sfs,ServFact linea ){
		String grupo = sfs.getCdGrupoProd().substring(0,6);
		linea.setCdGrupoProd(sfs.getCdGrupoProd());
		linea.setCdProd(sfs.getCdProd());
		linea.setCdSector(sfs.getCdSector());
		if(listA[0] != null)
			if(listA[0].equalsIgnoreCase(sfs.getCdPeriodo())){
				linea.setCtServFactMes1(linea.getCtServFactMes1() + sfs.getCtServFact());
				linea.setValServFactMes1(linea.getValServFactMes1().add( sfs.getValServFact()));
				if (!grupo.equals("NO_CON")) {
					linea.setCtRemitosMes1(linea.getCtRemitosMes1() + sfs.getCtRemitos());
					linea.setCtRemitosAConcilMes1(linea.getCtRemitosAConcilMes1() + sfs.getCtRemitosAConci());
				} else {
					linea.setCtRemitosMes1(0L);
					linea.setCtRemitosAConcilMes1(0L);				
				}
			}
		if(listA[1] != null)
			if(listA[1].equalsIgnoreCase(sfs.getCdPeriodo())){
				linea.setCtServFactMes2(linea.getCtServFactMes2() + sfs.getCtServFact());
				linea.setValServFactMes2(linea.getValServFactMes2().add( sfs.getValServFact()));
				if (!grupo.equals("NO_CON")) {
					linea.setCtRemitosMes2(linea.getCtRemitosMes2() + sfs.getCtRemitos());
					linea.setCtRemitosAConcilMes2(linea.getCtRemitosAConcilMes2() + sfs.getCtRemitosAConci());
				} else {
					linea.setCtRemitosMes2(linea.getCtServFactMes1() != 0 ? (linea.getCtServFactMes2()/linea.getCtServFactMes1()*100) : 0L);
					linea.setCtRemitosAConcilMes2(linea.getValServFactMes1() != BigDecimal.ZERO ? (linea.getValServFactMes2().divide(linea.getValServFactMes1()).longValue()*100) : 0L);				
				}
			}
		if(listA[2] != null)
			if(listA[2].equalsIgnoreCase(sfs.getCdPeriodo())){
				linea.setCtServFactMes3(linea.getCtServFactMes3() + sfs.getCtServFact());
				linea.setValServFactMes3(linea.getValServFactMes3().add( sfs.getValServFact()));
				if (!grupo.equals("NO_CON")) {
					linea.setCtRemitosMes3(linea.getCtRemitosMes3() + sfs.getCtRemitos());
					linea.setCtRemitosAConcilMes3(linea.getCtRemitosAConcilMes3() + sfs.getCtRemitosAConci());
				} else {	
					linea.setCtRemitosMes3(linea.getCtServFactMes2() != 0 ? (linea.getCtServFactMes3()/linea.getCtServFactMes2()*100) : 0L);
					linea.setCtRemitosAConcilMes3(linea.getValServFactMes2() != BigDecimal.ZERO ? (linea.getValServFactMes3().divide(linea.getValServFactMes2()).longValue()*100) : 0L);				
				}
			}
		if(listA[3] != null)
			if(listA[3].equalsIgnoreCase(sfs.getCdPeriodo())){
				linea.setCtServFactMes4(linea.getCtServFactMes4() + sfs.getCtServFact());
				linea.setValServFactMes4(linea.getValServFactMes4().add( sfs.getValServFact()));
				if (!grupo.equals("NO_CON")) {
					linea.setCtRemitosMes4(linea.getCtRemitosMes4() + sfs.getCtRemitos());
					linea.setCtRemitosAConcilMes4(linea.getCtRemitosAConcilMes4() + sfs.getCtRemitosAConci());
				} else {	
					linea.setCtRemitosMes4(linea.getCtServFactMes3() != 0 ? (linea.getCtServFactMes4()/linea.getCtServFactMes3()*100) : 0L);
					linea.setCtRemitosAConcilMes4(linea.getValServFactMes3() != BigDecimal.ZERO ? (linea.getValServFactMes4().divide(linea.getValServFactMes3()).longValue()*100) : 0L);				
				}
			}
		if(listA[4] != null)
			if(listA[4].equalsIgnoreCase(sfs.getCdPeriodo())){
				linea.setCtServFactMes5(linea.getCtServFactMes5() + sfs.getCtServFact());
				linea.setValServFactMes5(linea.getValServFactMes5().add( sfs.getValServFact()));
				if (!grupo.equals("NO_CON")) {
					linea.setCtRemitosMes5(linea.getCtRemitosMes5() + sfs.getCtRemitos());
					linea.setCtRemitosAConcilMes5(linea.getCtRemitosAConcilMes5() + sfs.getCtRemitosAConci());
				} else {	
					linea.setCtRemitosMes5(linea.getCtServFactMes4() != 0 ? (linea.getCtServFactMes5()/linea.getCtServFactMes4()*100) : 0L);
					linea.setCtRemitosAConcilMes5(linea.getValServFactMes4() != BigDecimal.ZERO ? (linea.getValServFactMes5().divide(linea.getValServFactMes4()).longValue()*100) : 0L);				
				}
			}
		if(listA[5] != null)
			if(listA[5].equalsIgnoreCase(sfs.getCdPeriodo())){
				linea.setCtServFactMes6(linea.getCtServFactMes6() + sfs.getCtServFact());
				linea.setValServFactMes6(linea.getValServFactMes6().add( sfs.getValServFact()));
				if (!grupo.equals("NO_CON")) {
					linea.setCtRemitosMes6(linea.getCtRemitosMes6() + sfs.getCtRemitos());
					linea.setCtRemitosAConcilMes6(linea.getCtRemitosAConcilMes6() + sfs.getCtRemitosAConci());
				} else {	
					linea.setCtRemitosMes6(linea.getCtServFactMes5() != 0 ? (linea.getCtServFactMes6()/linea.getCtServFactMes5()*100) : 0L);
					linea.setCtRemitosAConcilMes6(linea.getValServFactMes5() != BigDecimal.ZERO ? (linea.getValServFactMes6().divide(linea.getValServFactMes5()).longValue()*100) : 0L);				
				}
			}
		if(listA[6] != null)
			if(listA[6].equalsIgnoreCase(sfs.getCdPeriodo())){
				linea.setCtServFactMes7(linea.getCtServFactMes7() + sfs.getCtServFact());
				linea.setValServFactMes7(linea.getValServFactMes7().add( sfs.getValServFact()));
				if (!grupo.equals("NO_CON")) {
					linea.setCtRemitosMes7(linea.getCtRemitosMes7() + sfs.getCtRemitos());
					linea.setCtRemitosAConcilMes7(linea.getCtRemitosAConcilMes7() + sfs.getCtRemitosAConci());
				} else {	
					linea.setCtRemitosMes7(linea.getCtServFactMes6() != 0 ? (linea.getCtServFactMes7()/linea.getCtServFactMes6()*100) : 0L);
					linea.setCtRemitosAConcilMes7(linea.getValServFactMes6() != BigDecimal.ZERO ? (linea.getValServFactMes7().divide(linea.getValServFactMes6()).longValue()*100) : 0L);				
				}
			}
		if(listA[7] != null)
			if(listA[7].equalsIgnoreCase(sfs.getCdPeriodo())){
				linea.setCtServFactMes8(linea.getCtServFactMes8() + sfs.getCtServFact());
				linea.setValServFactMes8(linea.getValServFactMes8().add( sfs.getValServFact()));
				if (!grupo.equals("NO_CON")) {
					linea.setCtRemitosMes8(linea.getCtRemitosMes8() + sfs.getCtRemitos());
					linea.setCtRemitosAConcilMes8(linea.getCtRemitosAConcilMes8() + sfs.getCtRemitosAConci());
				} else {	
					linea.setCtRemitosMes8(linea.getCtServFactMes7() != 0 ? (linea.getCtServFactMes8()/linea.getCtServFactMes7()*100) : 0L);
					linea.setCtRemitosAConcilMes8(linea.getValServFactMes7() != BigDecimal.ZERO ? (linea.getValServFactMes8().divide(linea.getValServFactMes7()).longValue()*100) : 0L);				
				}
			}
		if(listA[8] != null)
			if(listA[8].equalsIgnoreCase(sfs.getCdPeriodo())){
				linea.setCtServFactMes9(linea.getCtServFactMes9() + sfs.getCtServFact());
				linea.setValServFactMes9(linea.getValServFactMes9().add( sfs.getValServFact()));
				if (!grupo.equals("NO_CON")) {
					linea.setCtRemitosMes9(linea.getCtRemitosMes9() + sfs.getCtRemitos());
					linea.setCtRemitosAConcilMes9(linea.getCtRemitosAConcilMes9() + sfs.getCtRemitosAConci());
				} else {	
					linea.setCtRemitosMes9(linea.getCtServFactMes8() != 0 ? (linea.getCtServFactMes9()/linea.getCtServFactMes8()*100) : 0L);
					linea.setCtRemitosAConcilMes9(linea.getValServFactMes8() != BigDecimal.ZERO ? (linea.getValServFactMes9().divide(linea.getValServFactMes8()).longValue()*100) : 0L);				
				}
			}
		if(listA[9] != null)
			if(listA[9].equalsIgnoreCase(sfs.getCdPeriodo())){
				linea.setCtServFactMes10(linea.getCtServFactMes10() + sfs.getCtServFact());
				linea.setValServFactMes10(linea.getValServFactMes10().add( sfs.getValServFact()));
				if (!grupo.equals("NO_CON")) {
					linea.setCtRemitosMes10(linea.getCtRemitosMes10() + sfs.getCtRemitos());
					linea.setCtRemitosAConcilMes10(linea.getCtRemitosAConcilMes10() + sfs.getCtRemitosAConci());
				} else {	
					linea.setCtRemitosMes10(linea.getCtServFactMes9() != 0 ? (linea.getCtServFactMes10()/linea.getCtServFactMes9()*100) : 0L);
					linea.setCtRemitosAConcilMes10(linea.getValServFactMes9() != BigDecimal.ZERO ? (linea.getValServFactMes10().divide(linea.getValServFactMes9()).longValue()*100) : 0L);				
				}
			}
		if(listA[10] != null)
			if(listA[10].equalsIgnoreCase(sfs.getCdPeriodo())){
				linea.setCtServFactMes11(linea.getCtServFactMes11() + sfs.getCtServFact());
				linea.setValServFactMes11(linea.getValServFactMes11().add( sfs.getValServFact()));
				if (!grupo.equals("NO_CON")) {
					linea.setCtRemitosMes11(linea.getCtRemitosMes11() + sfs.getCtRemitos());
					linea.setCtRemitosAConcilMes11(linea.getCtRemitosAConcilMes11() + sfs.getCtRemitosAConci());
				} else {	
					linea.setCtRemitosMes11(linea.getCtServFactMes10() != 0 ? (linea.getCtServFactMes11()/linea.getCtServFactMes10()*100) : 0L);
					linea.setCtRemitosAConcilMes11(linea.getValServFactMes10() != BigDecimal.ZERO ? (linea.getValServFactMes11().divide(linea.getValServFactMes10()).longValue()*100) : 0L);				
				}
			}
		if(listA[11] != null)
			if(listA[11].equalsIgnoreCase(sfs.getCdPeriodo())){
				linea.setCtServFactMes12(linea.getCtServFactMes12() + sfs.getCtServFact());
				linea.setValServFactMes12(linea.getValServFactMes12().add( sfs.getValServFact()));
				if (!grupo.equals("NO_CON")) {
					linea.setCtRemitosMes12(linea.getCtRemitosMes12() + sfs.getCtRemitos());
					linea.setCtRemitosAConcilMes12(linea.getCtRemitosAConcilMes12() + sfs.getCtRemitosAConci());
				} else {	
					linea.setCtRemitosMes12(linea.getCtServFactMes11() != 0 ? (linea.getCtServFactMes12()/linea.getCtServFactMes11()*100) : 0L);
					linea.setCtRemitosAConcilMes12(linea.getValServFactMes11() != BigDecimal.ZERO ? (linea.getValServFactMes12().divide(linea.getValServFactMes11()).longValue()*100) : 0L);				
				}
			}

		linea.setCtServFactTotal(linea.getCtServFactTotal() + sfs.getCtServFact());
		linea.setValServFactTotal(linea.getValServFactTotal().add(sfs.getValServFact()));
		if (!grupo.equals("NO_CON")) {
			linea.setCtRemitosTotal(linea.getCtRemitosTotal() + sfs.getCtRemitos());
			linea.setCtRemitosAConcilTotal(linea.getCtRemitosAConcilTotal() + sfs.getCtRemitosAConci());
		} else {	
			linea.setCtRemitosTotal(0L);
			linea.setCtRemitosAConcilTotal(0L);
		}
		linea.setValBrutaTotal(linea.getValBrutaTotal().add(sfs.getValBruto()));
		linea.setValNetoTotal(linea.getValNetoTotal().add(sfs.getValNeto()));
		linea.setNuPorcVarMax(sfs.getNuPorcVarMax());
		
		return linea;
	}
}
