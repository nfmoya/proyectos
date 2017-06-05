package com.bbva.cfs.servfact.action;

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
import com.bbva.cfs.servfact.form.ServFactForm;

//public class ServFactGridListAction extends GridAction {
public class ServFactGridModelAction extends CommonAction {

	private GetProveedoresPeriodosService gpps;
	//
	int cantPeriodos;
	String expand;
	String[] colNames;
	String[] colModels;
	String[] colGroups;
	String[] colIndex;
	private List<ProveedorPeriodo> list;
	private String colModelsUnique;
	private JSONObject jsonObctForm;

	public ServFactGridModelAction() {
		// TODO Auto-generated constructor stub
	}

	public ActionForward loadModelServFact(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.initialise(request.getSession());

		ServFactForm sfForm = (ServFactForm) form;

//		System.out.println("prov:" + sfForm.getSelectedProveedor());
//		System.out.println("fhde:" + sfForm.getFhDesde());
//		System.out.println("fhha:" + sfForm.getFhHasta());

		expand  = request.getParameter("expand");
		
		getPeriodos(sfForm.getSelectedProveedor(), sfForm.getFhDesde(),
				    sfForm.getFhHasta(), sfForm.getCdLote());
		
		cantPeriodos = list.size();
		System.out.println(list.size());
		if (list.size() < 13) {
			colNames        = armaColNames(sfForm.getSelectedGrupo());
			colModelsUnique = armaColModel();
			colGroups       = armaColGroups();
			colIndex        = armaColIndex();
		}
	
		jsonObctForm = convertToJSONGrid(request);
		gpps.getProveedoresPeriodosList();
		this.print(jsonObctForm, response);

		return null;
	}

	@SuppressWarnings("unchecked")
	private void getPeriodos(String cdProveedor, String cdPerDesde,
			                 String cdPerHasta, String cdLote) {
		gpps = new GetProveedoresPeriodosService(this.iWebClient);
		try {
			result = gpps.execute(cdProveedor, cdPerDesde, cdPerHasta, cdLote);
			list = new ArrayList<ProveedorPeriodo>();
			list = fillData(gpps.getProveedoresPeriodosList());

		} catch (Exception e) {
			log.error(e);
		}
	}

	private String[] armaColNames(String grupo) {
		// TODO Auto-generated method stub
		int actCol = 0;
		String[] colnamesM;
		if (expand.equalsIgnoreCase("no")){
			colnamesM = new String[(cantPeriodos * 4) + 10];
			colnamesM[actCol] = "Producto";
			actCol++;
		} else {
			colnamesM = new String[(cantPeriodos * 4) + 9];
		}
		colnamesM[actCol] = "Sector";
		actCol++;
		colnamesM[actCol] = "Grupo";
		actCol++;
		for (int i = 1; i <= cantPeriodos; i++) {
			colnamesM[actCol] = "Cant. Facturada";
			actCol++;
			colnamesM[actCol] = "Valor Fact.";
			actCol++;
			if (grupo.equals("NO_CON")) {
				colnamesM[actCol] = "Cant. %";
				actCol++;
				colnamesM[actCol] = "Valor %" ;
				actCol++;
			} else {
				colnamesM[actCol] = "Cant. Rem.";
				actCol++;
				colnamesM[actCol] = "Rem. a Concil." ;
				actCol++;
			}
		}
		colnamesM[actCol] = "Cant. Facturada";
		actCol++;
		colnamesM[actCol] = "Val. Fact.(UV)";
		actCol++;
		colnamesM[actCol] = "Valor Fact. Bruto";
		actCol++;
		colnamesM[actCol] = "Valor Fact. Neto";
		actCol++;
		colnamesM[actCol] = "Cant. Rem.";
		actCol++;
		colnamesM[actCol] = "Cant. Rem. a Concil.";
		actCol++;
		colnamesM[actCol] = "Porc Var Max";
		actCol++;

		return colnamesM;
	}

	private String armaColModel() {
		// TODO Auto-generated method stub

		String colModelsM = new String();

		// "[{name : 'ctServFactMes1' , index: 'mes1', width : 100, align : 'left', hidden : false }, "+
		// "{name : 'ctServFactMes10', width : 100, align : 'center', hidden : false }, "+
		// "{name : 'ctServFactMes11', width : 100, align : 'center', hidden : false } ]";
		colModelsM = "[";
		if (expand.equalsIgnoreCase("no"))
			colModelsM += "{name : 'cdProd' , index: 'cdProd', width : 100, align : 'left', hidden : false, frozen:true, summaryTpl:'<b> Total</b>' }, ";
		
		colModelsM +=  "{name : 'cdSector' , index: 'cdSector', width : 100, align : 'left', hidden : false }, ";
		colModelsM +=  "{name : 'cdGrupoProd' , index: 'cdGrupoProd', width : 100, align : 'left', hidden : false }, ";
		// ctServFactMes
		// ctRemitosMes
		for (int i = 1; i <= cantPeriodos; i++) {
			colModelsM += "{name : 'ctServFactMes" + i
					+ "', index: 'ctServFactMes" + i
					+ "', width : 70, align : 'right', hidden : false  ,formatter: 'number', formatoptions: { decimalPlaces: 0}}, ";
			colModelsM += "{name : 'valServFactMes" + i
					+ "', index: 'valServFactMes" + i
					+ "', width : 70, align : 'right', hidden : false ,formatter: 'number', formatoptions: { decimalPlaces: 2 }},";
			colModelsM += "{name : 'ctRemitosMes" + i
					+ "', index: 'ctRemitosMes" + i
					+ "', width : 55, align : 'right', hidden : false ,formatter: 'number',summaryType:'sum', formatoptions: { decimalPlaces: 0} }, ";
			colModelsM += "{name : 'ctRemitosAConcilMes" + i
					+ "', index: 'ctRemitosAConcilMes" + i
					+ "', width : 70, align : 'right', hidden : false, classes:'grid-col' ,formatter: 'number', formatoptions: { decimalPlaces: 0}},";
		}
		colModelsM += "{name : 'ctServFactTotal', width : 100, align : 'right', hidden : false,formatter: 'number', formatoptions: { decimalPlaces: 0} }, ";
		colModelsM += "{name : 'valServFactTotal', width : 100, align : 'right', hidden : false ,formatter: 'number', formatoptions: { decimalPlaces: 2 }}, ";
		colModelsM += "{name : 'valBrutaTotal', width : 100, align : 'right', hidden : false ,formatter: 'number', formatoptions: { decimalPlaces: 2 }}, ";
		colModelsM += "{name : 'valNetoTotal', width : 100, align : 'right', hidden : false ,formatter: 'number', formatoptions: { decimalPlaces: 2 }}, ";
		colModelsM += "{name : 'ctRemitosTotal', width : 55, align : 'right', hidden : false,formatter: 'number', formatoptions: { decimalPlaces: 0}}, ";
		colModelsM += "{name : 'ctRemitosAConcilTotal', width : 100, align : 'right', hidden : false ,formatter: 'number', formatoptions: { decimalPlaces: 0}}, ";
		colModelsM += "{name : 'nuPorcVarMax', width : 100, align : 'right', hidden : true ,formatter: 'number', formatoptions: { decimalPlaces: 0}}";
		colModelsM += "]";
		
		return colModelsM;
	}


	private String[] armaColIndex() {
		// TODO Auto-generated method stub

		String[] colIndexM = new String[cantPeriodos];
//		String[] colIndexM = new String[(cantPeriodos) + 1];

		int act = 0;
		int i;
		for (i = 1; i <= cantPeriodos; i++) {
			colIndexM[act] = "ctServFactMes" + i;
			act++;
		}
//		for (i = i; i <= 12; i++) {
//			colIndexM[act] = "null";
//			act++;
//		}
//		colIndexM[act] = "ctServFactTotal";

		return colIndexM;
	}

	private String[] armaColGroups() {
		// TODO Auto-generated method stub

//		String[] colGroupsM = new String[(cantPeriodos) + 1];
		String[] colGroupsM = new String[13];

		int actCol = 0;
		// colGroupsM[actCol] = "Producto";
		// actCol++;

		// for(int i = 1; i <= cantPeriodos; i++) {
		for (ProveedorPeriodo pp : list) {
			colGroupsM[actCol] = pp.getCdPeriodoFact() + " - "
					+ pp.getNbPeriodoFact();
			actCol++;
		}
		int x = cantPeriodos;
//		System.out.println("change");
		for (int i = actCol; i < 12; i++) {
			colGroupsM[actCol] = "null";
			actCol++;
		}
		colGroupsM[actCol] = "Totales";
		actCol++;

		return colGroupsM;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
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
//		System.out.println(listaRet.size());
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

		jsonObjectForm.put("names" , colNames);
		jsonObjectForm.put("model" , colModelsUnique);
		jsonObjectForm.put("groups", colGroups);
		jsonObjectForm.put("index" , colIndex);
		jsonObjectForm.put("cant"  , list.size());

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
}
