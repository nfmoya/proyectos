package com.bbva.cfs.conciliacion.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ar.com.itrsa.GeneralException;

import com.bbva.cfs.conciliacion.form.ConciliacionForm;
import com.bbva.cfs.commons.action.GridActionWithoutPaginate;
import com.bbva.cfs.commons.model.Session;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.conciliacion.model.Conciliacion;
import com.bbva.cfs.conciliacion.service.GetConciliacionesService;
import com.bbva.cfs.producto.form.ProductoForm;

import java.util.Properties;

public class ConciliacionesGridListAction extends GridActionWithoutPaginate {

	private String cdProveedor;
	private String cdSector;
	private String cdProducto;
	private String cdPeriodo;
	private String fhRemitoDesde;
	private String fhRemitoHasta;
	private String fhFinServDesde;
	private String fhFinServHasta;
	private String stConciliacion;
	private Integer cdConciliacion;
	private Integer cantBilletes;
//	private String tipoCambio;
	
	public ConciliacionesGridListAction() {
		// TODO Auto-generated constructor stub
	}

	public ActionForward loadGridConciliaciones(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.initialise(request.getSession());
		
		String realpath = request.getSession().getServletContext().getRealPath("/configFolders.properties");
		File f = new File(realpath);
		File f2 = f.getParentFile().getParentFile();
		String pathfinal = f2.getAbsolutePath()	+ "/syscfg/configFolders.properties";
		File f3 = new File(pathfinal);

		FileInputStream input = new FileInputStream(f3);
		Properties config = new Properties();
		config.load(input);
		
		cantBilletes  =  Integer.parseInt(config.getProperty("concmedibles_cantbilletes"));
//		tipoCambio    = config.getProperty("concmedibles_tipocambio");
		
		ConciliacionForm frm = (ConciliacionForm) form;
		frm.setCantBilletes(cantBilletes);
//		frm.setTipoCambio(tipoCambio);
		
		cdProveedor    = request.getParameter("cdProveedor");
		cdSector       = request.getParameter("cdSector");
		cdProducto     = request.getParameter("cdProducto");
		cdPeriodo      = request.getParameter("cdPeriodoFact");
		fhRemitoDesde  = request.getParameter("fhRemitoDesde");
		fhRemitoHasta  = request.getParameter("fhRemitoHasta");
		fhFinServDesde = request.getParameter("fhFinServicioDesde");
		fhFinServHasta = request.getParameter("fhFinServicioHasta");
		stConciliacion = request.getParameter("stConciliacion");
		cdConciliacion = Integer.parseInt(request.getParameter("cdConciliacion"));

		return super.executeGrid(mapping, form, request, response);
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

	@SuppressWarnings("rawtypes")
	public List fillRows(HttpSession session) throws GeneralException {
		// Se obtiene Session del que esta realizando la consulta.
		@SuppressWarnings("unused")
		Session sessionApp = (Session) session.getAttribute(Constants.SESSION);
		GetConciliacionesService service = new GetConciliacionesService(iWebClient);
		
		List listaRet = new ArrayList();
		try {
			result = service.execute(cdProveedor, cdSector, cdProducto, cdPeriodo, fhRemitoDesde,
                                     fhRemitoHasta, fhFinServDesde, fhFinServHasta, stConciliacion,
                                     cdConciliacion, cantBilletes);
			listaRet = this.fillData(service.getConciliacionesList());
		} catch (Exception e) {
			log.error(e);
		}
		return listaRet;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List fillData(List listConciliaciones) {
		List listaRet = new ArrayList();
	    for (int i = 0; i < listConciliaciones.size(); i++) {  
	    	Object object = listConciliaciones.get(i);
	    	ConciliacionForm frm = new ConciliacionForm();
	    	Conciliacion conciliacion = (Conciliacion) object;

	        frm.setCdRemitoPres(conciliacion.getCdRemitoPres());
	        frm.setFhRemito(conciliacion.getFhRemito());
	        frm.setFhFinServ(conciliacion.getFhFinServ());
	        frm.setCtServPrest(conciliacion.getCtServPrest());
	        frm.setImPrecioTotalPres(conciliacion.getImPrecioTotalPres());
	        frm.setImPrecioTotalPresConv(conciliacion.getImPrecioTotalPresConv());
	        frm.setCdTipVal(conciliacion.getCdTipVal());
	        frm.setCdMoneda(conciliacion.getCdMoneda());
	        frm.setCdConciliacionPres(conciliacion.getCdConciliacionPres());
	        frm.setChkPres(conciliacion.getChkPres());
	        frm.setCdRemitoFact(conciliacion.getCdRemitoFact());
	        frm.setTpComprobante(conciliacion.getTpComprobante());
	        frm.setNuComprobante(conciliacion.getNuComprobante());
	        frm.setCtServFact(conciliacion.getCtServFact());
	        frm.setImPrecioTotalFact(conciliacion.getImPrecioTotalFact());
	        frm.setCdConciliacionFact(conciliacion.getCdConciliacionFact());
	        frm.setChkFact(conciliacion.getChkFact());
	        frm.setCtServFactDife(conciliacion.getCtServFactDife());
	        frm.setImServFactDife(conciliacion.getImServFactDife());
	        frm.setCdLoteServ(conciliacion.getCdLoteServ());
            frm.setCdOrdenServ(conciliacion.getCdOrdenServ());
	        frm.setCdSectorSol(conciliacion.getCdSectorSol()); 
	        frm.setCdSectorControl(conciliacion.getCdSectorControl()); 
	        frm.setCdProductoPres(conciliacion.getCdProductoPres()); 
	        frm.setCdUniValPres(conciliacion.getCdUniValPres()); 
            frm.setImPrecioUnitPres(conciliacion.getImPrecioUnitPres()); 
	        frm.setNbPiezaDesdePres(conciliacion.getNbPiezaDesdePres());
	        frm.setNbPiezaHastaPres(conciliacion.getNbPiezaHastaPres());
	        frm.setCdRemitoPadre(conciliacion.getCdRemitoPadre());
	        frm.setNbAtributoRef1(conciliacion.getNbAtributoRef1());
	        frm.setNbAtributoRef2(conciliacion.getNbAtributoRef2());
	        frm.setNbObservaciones(conciliacion.getNbObservaciones());
	        frm.setStLoteDet(conciliacion.getStLoteDet());
	        frm.setFhModificacion(conciliacion.getFhModificacion()); 
	        frm.setNbUsuarioModif(conciliacion.getNbUsuarioModif()); 
	        frm.setCdLoteFact(conciliacion.getCdLoteFact());
            frm.setCdOrdenFact(conciliacion.getCdOrdenFact());
	    	
			listaRet.add(frm);
		}
		return listaRet;
	}

	public JSONObject convertToJSONeachAction(ActionForm form) {
		ConciliacionForm frm = (ConciliacionForm) form;

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("cdRemitoPres", frm.getCdRemitoPres());
		jsonObject.put("fhRemito", frm.getFhRemito());
		jsonObject.put("fhFinServ", frm.getFhFinServ());
		jsonObject.put("ctServPrest", frm.getCtServPrest());
		jsonObject.put("imPrecioTotalPres", frm.getImPrecioTotalPres());
		jsonObject.put("imPrecioTotalPresConv", frm.getImPrecioTotalPresConv());
		jsonObject.put("cdTipVal", frm.getCdTipVal());
		jsonObject.put("cdMoneda", frm.getCdMoneda());
		jsonObject.put("cdConciliacionPres", frm.getCdConciliacionPres());
		jsonObject.put("chkPres", frm.getChkPres());
		jsonObject.put("cdRemitoFact", frm.getCdRemitoFact());
		jsonObject.put("tpComprobante", frm.getTpComprobante());
        jsonObject.put("nuComprobante", frm.getNuComprobante());
        jsonObject.put("ctServFact", frm.getCtServFact());
        jsonObject.put("imPrecioTotalFact", frm.getImPrecioTotalFact());
        jsonObject.put("cdConciliacionFact", frm.getCdConciliacionFact());
        jsonObject.put("chkFact", frm.getChkFact());
        jsonObject.put("ctServFactDife", frm.getCtServFactDife());
        jsonObject.put("imServFactDife", frm.getImServFactDife());
        jsonObject.put("cdUniValPres", frm.getCdUniValPres());
        jsonObject.put("cdLoteServ", frm.getCdLoteServ());
        jsonObject.put("cdOrdenServ", frm.getCdOrdenServ());
        jsonObject.put("cdSectorSol", frm.getCdSectorSol()); 
        jsonObject.put("cdSectorControl", frm.getCdSectorControl()); 
        jsonObject.put("cdProductoPres", frm.getCdProductoPres()); 
        jsonObject.put("cdUniValPres", frm.getCdUniValPres()); 
        jsonObject.put("imPrecioUnitPres", frm.getImPrecioUnitPres()); 
        jsonObject.put("nbPiezaDesdePres", frm.getNbPiezaDesdePres());
        jsonObject.put("nbPiezaHastaPres", frm.getNbPiezaHastaPres());
        jsonObject.put("cdRemitoPadre", frm.getCdRemitoPadre());
        jsonObject.put("nbAtributoRef1", frm.getNbAtributoRef1());
        jsonObject.put("nbAtributoRef2", frm.getNbAtributoRef2());
        jsonObject.put("nbObservaciones", frm.getNbObservaciones());
        jsonObject.put("stLoteDet", frm.getStLoteDet());
        jsonObject.put("fhModificacion", frm.getFhModificacion()); 
        jsonObject.put("nbUsuarioModif", frm.getNbUsuarioModif()); 
        jsonObject.put("cdLoteFact", frm.getCdLoteFact());
        jsonObject.put("cdOrdenFact", frm.getCdOrdenFact());
		
		return jsonObject;
	}
}
