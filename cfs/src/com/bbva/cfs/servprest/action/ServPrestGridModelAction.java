package com.bbva.cfs.servprest.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bbva.cfs.commons.action.CommonAction;
import com.bbva.cfs.commons.utils.AsciiConstants;
import com.bbva.cfs.proveedor.model.ProveedorPeriodo;
import com.bbva.cfs.proveedor.service.GetProveedoresPeriodosService;
import com.bbva.cfs.servprest.service.GetServPrestPeriodosService;
import com.bbva.cfs.servprest.form.ServPrestForm;

public class ServPrestGridModelAction extends CommonAction {
	private GetProveedoresPeriodosService gpps;
	private GetServPrestPeriodosService gspps;
	//
	int cantPeriodos;
	String expand;
	String[] colNames;
	String[] colModels;
	String[] colGroups;
	String[] colIndex;
	private List<ProveedorPeriodo> list;
	private List<String> listaPeriodos;
	private String colModelsUnique;
	private JSONObject jsonObctForm;

	public ServPrestGridModelAction() {
		// TODO Auto-generated constructor stub
	}

	// public ActionForward loadGridSectores(ActionMapping mapping,
	// ActionForm form, HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	public ActionForward loadModelServPrest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.initialise(request.getSession());

		ServPrestForm sfForm = (ServPrestForm) form;

//		System.out.println("prov:" + sfForm.getSelectedProveedor());
//		System.out.println("fhde:" + sfForm.getFhDesde());
//		System.out.println("fhha:" + sfForm.getFhHasta());

		expand = request.getParameter("expand");

		// getPeriodos(sfForm.getSelectedProveedor(), sfForm.getFhDesde(),
		// sfForm.getFhHasta(), sfForm.getCdLote());

		getPeriodos(sfForm.getSelectedProveedor(), sfForm.getSelectedSector(),
				sfForm.getSelectedProducto(), sfForm.getSelectedGrupo(),
				sfForm.getFhDesde(), sfForm.getFhHasta(),
				sfForm.getCdComprobante(), sfForm.getRemitoDesde(),
				sfForm.getRemitoHasta(), sfForm.getStLoteDet(), sfForm.getCdLote());

		cantPeriodos = listaPeriodos.size();
		//cantPeriodos = 12;

		colNames = armaColNames();

		colModelsUnique = armaColModel();

		colGroups = armaColGroups();
		colIndex = armaColIndex();

		jsonObctForm = convertToJSONGrid(request);
		gpps.getProveedoresPeriodosList();
		this.print(jsonObctForm, response);

		return null;
	}

//	private void getPeriodos(String cdProveedor, String cdPerDesde,
//			String cdPerHasta, String cdLote) {
//		gpps = new GetProveedoresPeriodosService(this.iWebClient);
//		try {
//			result = gpps.execute(cdProveedor, cdPerDesde, cdPerHasta, cdLote);
//			list = new ArrayList<ProveedorPeriodo>();
//			list = fillData(gpps.getProveedoresPeriodosList());
//
//		} catch (Exception e) {
//			log.error(e);
//		}
//
//	}

	private void getPeriodos(String prov, String sector, String prod,
			String grupo, String desde, String hasta, String comprobante,
			String remDesde, String remHasta, String stLoteDet, String lote) {
		gpps = new GetProveedoresPeriodosService(this.iWebClient);
		gspps = new GetServPrestPeriodosService(this.iWebClient);
		try {
			result = gspps.execute(prov, sector, prod, grupo, desde, hasta,
					comprobante, remDesde, remHasta, stLoteDet, lote);
			listaPeriodos = new ArrayList<String>();
			listaPeriodos = gspps.getServPrestPeriodosList();
			
			System.out.println("MAVER CANT" + listaPeriodos.size());

		} catch (Exception e) {
			log.error(e);
		}
	}

	private String[] armaColNames() {
		// TODO Auto-generated method stub

		int actCol = 0;
		String[] colnamesM;
		if (expand.equalsIgnoreCase("no")) {
			colnamesM = new String[(cantPeriodos * 4) + 9];
			colnamesM[actCol] = "Producto";
			actCol++;
		} else {
			colnamesM = new String[(cantPeriodos * 4) + 8];

		}

		colnamesM[actCol] = "Sector";
		actCol++;
		colnamesM[actCol] = "Grupo";
		actCol++;
		for (int i = 1; i <= cantPeriodos; i++) {
			colnamesM[actCol] = "Cant. Prest.";
			actCol++;
			colnamesM[actCol] = "Valor Prest.";
			actCol++;
			colnamesM[actCol] = "Cant. Rem.";
			actCol++;
			colnamesM[actCol] = "Rem. a Concil.";
			actCol++;
		}
		colnamesM[actCol] = "Cant. Prestada";
		actCol++;
		colnamesM[actCol] = "Val. Prest.(UV)";
		actCol++;
		colnamesM[actCol] = "Valor Prest. Bruto";
		actCol++;
		colnamesM[actCol] = "Valor Prest. Neto";
		actCol++;
		colnamesM[actCol] = "Cant. Rem.";
		actCol++;
		colnamesM[actCol] = "Cant. Rem. a Concil.";
		actCol++;

		return colnamesM;
	}

	private String armaColModel() {
		// TODO Auto-generated method stub

		String colModelsM = new String();

		// "[{name : 'ctServPrestMes1' , index: 'mes1', width : 100, align : 'left', hidden : false }, "+
		// "{name : 'ctServPrestMes10', width : 100, align : 'center', hidden : false }, "+
		// "{name : 'ctServPrestMes11', width : 100, align : 'center', hidden : false } ]";
		colModelsM = "[";
		if (expand.equalsIgnoreCase("no"))
		colModelsM += "{name : 'cdProd' , index: 'cdProd', width : 100, align : 'left', hidden : false, frozen:true, summaryTpl:'<b> Total</b>' }, ";
		colModelsM += "{name : 'cdSector' , index: 'cdSector', width : 100, align : 'left', hidden : false }, ";
		colModelsM += "{name : 'cdGrupoProd' , index: 'cdGrupoProd', width : 100, align : 'left', hidden : false }, ";
		// ctServPrestMes
		// ctRemitosMes
		for (int i = 1; i <= cantPeriodos; i++) {
			colModelsM += "{name : 'ctServPrestMes"	+ i
					+ "', index: 'ctServPrestMes" + i
					+ "', width : 70, align : 'right', hidden : false , 'summaryType': 'sum', 'formatter': 'integer'  }, ";
			colModelsM += "{name : 'valServPrestMes" + i
					+ "', index: 'valServPrestMes" + i
					+ "', width : 70, align : 'right', hidden : false, 'formatter': 'currency' },";
			colModelsM += "{name : 'ctRemitosMes"+ i
					+ "', index: 'ctRemitosMes"	+ i
					+ "', width : 55, align : 'right', hidden : false ,summaryType:'sum' }, ";
			colModelsM += "{name : 'ctRemitosAConcilMes" + i
					+ "', index: 'ctRemitosAConcilMes" + i	
					+ "', width : 70, align : 'right', hidden : false, classes:'grid-col'},";
		}
		colModelsM += "{name : 'ctServPrestTotal', width : 100, align : 'right', hidden : false, 'formatter': 'integer' }, ";
		colModelsM += "{name : 'valServPrestTotal', width : 100, align : 'right', hidden : false ,'formatter': 'currency'}, ";
		colModelsM += "{name : 'valBrutaTotal', width : 100, align : 'right', hidden : false ,'formatter': 'currency'}, ";
		colModelsM += "{name : 'valNetoTotal', width : 100, align : 'right', hidden : false ,'formatter': 'currency'}, ";
		colModelsM += "{name : 'ctRemitosTotal', width : 55, align : 'right', hidden : false }, ";
		colModelsM += "{name : 'ctRemitosAConcilTotal', width : 100, align : 'right', hidden : false }]";

		return colModelsM;
	}

	private String[] armaColIndex() {
		// TODO Auto-generated method stub

		String[] colIndexM = new String[cantPeriodos];
		// String[] colIndexM = new String[(cantPeriodos) + 1];

		int act = 0;
		int i;
		for (i = 1; i <= cantPeriodos; i++) {
			colIndexM[act] = "ctServPrestMes" + i;
			act++;
		}
		// for (i = i; i <= 12; i++) {
		// colIndexM[act] = "null";
		// act++;
		// }
		// colIndexM[act] = "ctServPrestTotal";

		return colIndexM;
	}

	private String[] armaColGroups() {
		// TODO Auto-generated method stub

		// String[] colGroupsM = new String[(cantPeriodos) + 1];
		String[] colGroupsM = new String[13];

		int actCol = 0;
		// colGroupsM[actCol] = "Producto";
		// actCol++;

		// for(int i = 1; i <= cantPeriodos; i++) {

		for (int i = 0; i < listaPeriodos.size(); i++) {
			colGroupsM[actCol] = listaPeriodos.get(i).substring(6, listaPeriodos.get(i).length()) ;
			actCol++;
		}

		for (int i = actCol; i < 12; i++) {
			colGroupsM[actCol] = "null";
			actCol++;
		}

		/*
		 * for (ProveedorPeriodo pp : list) { colGroupsM[actCol] =
		 * pp.getCdPeriodoFact() + " - " + pp.getNbPeriodoFact(); actCol++; }
		 * int x = cantPeriodos; System.out.println("change"); for (int i =
		 * actCol; i < 12; i++) { colGroupsM[actCol] = "null"; actCol++; }
		 */
		colGroupsM[actCol] = "Totales";
		actCol++;

		return colGroupsM;
	}

	private List fillData(List listPP) {
		List<ProveedorPeriodo> listaRet = new ArrayList();
		for (int i = 0; i < listPP.size(); i++) {
			Object object = listPP.get(i);
			ProveedorPeriodo added = new ProveedorPeriodo();
			ProveedorPeriodo pp = (ProveedorPeriodo) object;

			added.setCdPeriodoFact(pp.getCdPeriodoFact());
			added.setNbPeriodoFact(pp.getNbPeriodoFact());

			listaRet.add(added);
		}
		System.out.println(listaRet.size());
		return listaRet;
	}

	@SuppressWarnings("rawtypes")
	public Class getFilterClass() throws ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setParameters(HttpServletRequest request, ActionForm form)
			throws Exception {
		// TODO Auto-generated method stub

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

		jsonObjectForm.put("names", colNames);
		jsonObjectForm.put("model", colModelsUnique);
		jsonObjectForm.put("groups", colGroups);
		jsonObjectForm.put("index", colIndex);

		return jsonObjectForm;
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

	/**
	 * @return the list
	 */
	public List<ProveedorPeriodo> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<ProveedorPeriodo> list) {
		this.list = list;
	}
}
