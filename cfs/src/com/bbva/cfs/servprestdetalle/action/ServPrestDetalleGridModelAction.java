package com.bbva.cfs.servprestdetalle.action;

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
import com.bbva.cfs.servprestdetalle.form.ServPrestDetalleForm;

public class ServPrestDetalleGridModelAction extends CommonAction {
	private GetProveedoresPeriodosService gpps;
        
	String expand;
	String[] colNames;
	String[] colModels;
	String[] colGroups;
	String[] colIndex;
	private List<ProveedorPeriodo> list;
	private String colModelsUnique;
	private JSONObject jsonObctForm;

	public ServPrestDetalleGridModelAction() {
		// TODO Auto-generated constructor stub
	}

	// public ActionForward loadGridSectores(ActionMapping mapping,
	// ActionForm form, HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	public ActionForward loadModelServPrestDetalle(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.initialise(request.getSession());

		ServPrestDetalleForm sfForm = (ServPrestDetalleForm) form;

		System.out.println("prov:" + sfForm.getSelectedProveedor());

		expand  = request.getParameter("expand");
		
		colNames = armaColNames();

		colModelsUnique = armaColModel();
                
		colGroups = armaColGroups();
		colIndex = armaColIndex();

		jsonObctForm = convertToJSONGrid(request);
		//gpps.getProveedoresPeriodosList();
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

	private String[] armaColNames() {
		// TODO Auto-generated method stub

                String[] colnamesM = new String[16];
            
                colnamesM[0] = "Sector";
                colnamesM[1] = "Grupo Prod.";
                colnamesM[2] = "Cod. Prod.";
                colnamesM[3] = "Producto";
                colnamesM[4] = "Lote Prest.";
                colnamesM[5] = "Remito";
                colnamesM[6] = "Fecha Remito";
                colnamesM[7] = "Fecha Cierre";
                colnamesM[8] = "Cant. Prest.";
                colnamesM[9] = "Valor (en U. Val.)";
                colnamesM[10] = "Observaciones";
                colnamesM[11] = "Est.";
                colnamesM[12] = "Concil.";
                colnamesM[13] = "Cotización";
                colnamesM[14] = "Fecha Cotiz";
                colnamesM[15] = "Tipo Val.";
                
		return colnamesM;
	}

	private String armaColModel() {
		// TODO Auto-generated method stub

		String colModelsM = new String();

		colModelsM = "[";
                
                colModelsM += "{name : 'cdSector' , index: 'cdSector', width : 40, align : 'left', hidden : false, frozen:true, summaryTpl:'<b> Total</b>' }, ";
                colModelsM += "{name : 'cdGrupoProducto' , index: 'cdGrupoProducto', width : 65, align : 'left', hidden : false }, ";
                colModelsM += "{name : 'cdProducto' , index: 'cdProducto', width : 75, align : 'left', hidden : false }, ";
                colModelsM += "{name : 'nbDescripcion' , index: 'nbDescripcion', width : 125, align : 'left', hidden : false }, ";
                colModelsM += "{name : 'cdLoteServ' , index: 'cdLoteServ', width : 70, align : 'left', hidden : false }, ";
                colModelsM += "{name : 'cdRemito' , index: 'cdRemito', width : 85, align : 'left', hidden : false }, ";
                colModelsM += "{name : 'fhRemito' , index: 'fhRemito', width : 70, align : 'left', hidden : false }, ";
                colModelsM += "{name : 'fhFinServ' , index: 'fhFinServ', width : 70, align : 'left', hidden : false }, ";
                colModelsM += "{name : 'ctServPrest' , index: 'ctServPrest', width : 70, align : 'right', hidden : false, 'formatter': 'integer' }, ";
                colModelsM += "{name : 'imPrecioTotal' , index: 'imPrecioTotal', width : 85, align : 'right', hidden : false }, ";
                colModelsM += "{name : 'nbObservaciones' , index: 'nbObservaciones', width : 500, align : 'left', hidden : false }, ";
                colModelsM += "{name : 'stLoteDet' , index: 'stLoteDet', width : 25, align : 'left', hidden : false }, ";
                colModelsM += "{name : 'cdConciliacion' , index: 'cdConciliacion', width : 70, align : 'left', hidden : false }, ";
                colModelsM += "{name : 'nbAtributoRef1' , index: 'nbAtributoRef1', width : 70, align : 'right', hidden : false }, ";
                colModelsM += "{name : 'nbAtributoRef2' , index: 'nbAtributoRef2', width : 70, align : 'center', hidden : false }, ";
                colModelsM += "{name : 'nbTipVal' , index: 'nbTipVal', width : 70, align : 'left', hidden : false }]";
                
		return colModelsM;
	}


	private String[] armaColIndex() {
		// TODO Auto-generated method stub

		String[] colIndexM = new String[16];
                
                for (int i = 0; i <= 15; i++)
                    colIndexM[i] = "null";
                
		return colIndexM;
	}

	private String[] armaColGroups() {
		// TODO Auto-generated method stub

		String[] colGroupsM = new String[16];
                
                for (int i = 0; i <= 15; i++)
                    colGroupsM[i] = "null";
                
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
}
