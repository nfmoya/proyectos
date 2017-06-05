package com.bbva.cfs.producto.action;

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

import com.bbva.cfs.producto.form.ProductoForm;
import com.bbva.cfs.commons.action.GridAction;
import com.bbva.cfs.commons.model.Session;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.general.model.Tabla;
import com.bbva.cfs.general.service.GetTablasService;
import com.bbva.cfs.producto.model.Producto;
import com.bbva.cfs.producto.service.GetProductosService;

public class ProductosGridListAction extends GridAction {

	private String cdProveedor;
	private String cdProducto;
	private String cdGrupo;
	private String stHabilitado;
	
	public ProductosGridListAction() {
		// TODO Auto-generated constructor stub
	}

	public ActionForward loadGridProductos(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.initialise(request.getSession());
		
		cdProveedor  = request.getParameter("cdProveedor");
		cdProducto   = request.getParameter("cdProducto");
		cdGrupo      = request.getParameter("cdGrupo");
		stHabilitado = request.getParameter("stHabilitado");

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
		GetProductosService service = new GetProductosService(iWebClient);
		
		List listaRet = new ArrayList();
		try {
			if (cdProveedor.equals("0")) {
				result = service.execute();
			} else {
				result = service.execute(cdProveedor, cdProducto, cdGrupo, stHabilitado);
			}
			GetTablasService srvTablas = new GetTablasService(iWebClient);
			srvTablas.execute("TIPVAL");
			listaRet = this.fillData(service.getProductosList(), (List<Tabla>)srvTablas.getTablasList());
		} catch (Exception e) {
			log.error(e);
		}
		return listaRet;
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List fillData(List listProductos, List<Tabla> tiposVal) {
		List listaRet = new ArrayList();
	    for (int i = 0; i < listProductos.size(); i++) {  
	    	Object object = listProductos.get(i);
			ProductoForm frm = new ProductoForm();
			Producto producto = (Producto) object;

			frm.setCdProveedor(producto.getCdProveedor());
			frm.setCdProducto(producto.getCdProducto());
			frm.setNbProducto(producto.getNbProducto());
			frm.setNbProductoCorto(producto.getNbProductoCorto());
			frm.setCdGrupoProducto(producto.getCdGrupoProducto());
			frm.setCdUniVal(producto.getCdUniVal());
			frm.setCdSecSolServ(producto.getCdSecSolServ());
			frm.setCdSecConServ(producto.getCdSecConServ());
			frm.setCdSecConFact(producto.getCdSecConFact());
			frm.setStProdImportPrest(producto.getStProdImportPrest());
			frm.setStProdImportFact(producto.getStProdImportFact());
			frm.setStRemServOblig(producto.getStRemServOblig());
			frm.setStRemFactOblig(producto.getStRemFactOblig());
			frm.setStAdmiteRemServ(producto.getStAdmiteRemServ());
			frm.setStAdmiteRemFact(producto.getStAdmiteRemFact());
			frm.setNbAtributoRef1(producto.getNbAtributoRef1());
			frm.setNbAtributoRef2(producto.getNbAtributoRef2());
			frm.setStConcilSinVal(producto.getStConcilSinVal());
			frm.setStServSinConcil(producto.getStServSinConcil());
			frm.setStFactSinConcil(producto.getStFactSinConcil());
			frm.setNuDiaEmiFDesde(producto.getNuDiaEmiFDesde());
			frm.setNuDiaEmiFHasta(producto.getNuDiaEmiFHasta());
			frm.setNuDiaCierreFDesde(producto.getNuDiaCierreFDesde());
			frm.setNuDiaCierreFHasta(producto.getNuDiaCierreFHasta());
			frm.setNbAtributoAdic1(producto.getNbAtributoAdic1());
			frm.setNbAtributoAdic2(producto.getNbAtributoAdic2());
			frm.setNbAtributoAdic3(producto.getNbAtributoAdic3());
			frm.setNbAtributoAdic4(producto.getNbAtributoAdic4());
			frm.setNbAtributoAdic5(producto.getNbAtributoAdic5());
			frm.setStHabilitado(producto.getStHabilitado());
			frm.setCdTipVal(producto.getCdTipVal().trim());
			for (Tabla tipoVal : tiposVal) {
				if (producto.getCdTipVal().trim().equalsIgnoreCase(tipoVal.getCdCodTabla().trim())) {
					frm.setDescTipVal(producto.getCdTipVal().trim()+"-"+tipoVal.getNbCodTabla());
					break;
				}
			}
			
			frm.setCdMoneda(producto.getCdMoneda().trim());
			frm.setNuPorcVarMax(producto.getNuPorcVarMax());
			
			listaRet.add(frm);
		}
		return listaRet;
	}

	public JSONObject convertToJSONeachAction(ActionForm form) {
		ProductoForm frm = (ProductoForm) form;

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("cdProveedor", frm.getCdProveedor());
		jsonObject.put("cdProducto", frm.getCdProducto());
		jsonObject.put("nbProducto", frm.getNbProducto());
		jsonObject.put("nbProductoCorto", frm.getNbProductoCorto());
		jsonObject.put("cdGrupoProducto", frm.getCdGrupoProducto());
		jsonObject.put("cdUniVal", frm.getCdUniVal());
		jsonObject.put("cdSecSolServ", frm.getCdSecSolServ());
		jsonObject.put("cdSecConServ", frm.getCdSecConServ());
		jsonObject.put("cdSecConFact", frm.getCdSecConFact());
		jsonObject.put("stProdImportPrest", frm.getStProdImportPrest());
		jsonObject.put("stProdImportFact", frm.getStProdImportFact());
		jsonObject.put("stRemServOblig", frm.getStRemServOblig());
		jsonObject.put("stRemFactOblig", frm.getStRemFactOblig());
		jsonObject.put("stAdmiteRemServ", frm.getStAdmiteRemServ());
		jsonObject.put("stAdmiteRemFact", frm.getStAdmiteRemFact());
		jsonObject.put("nbAtributoRef1", frm.getNbAtributoRef1());
		jsonObject.put("nbAtributoRef2", frm.getNbAtributoRef2());
		jsonObject.put("stConcilSinVal", frm.getStConcilSinVal());
		jsonObject.put("stServSinConcil", frm.getStServSinConcil());
		jsonObject.put("stFactSinConcil", frm.getStFactSinConcil());
		jsonObject.put("nuDiaEmiFDesde", frm.getNuDiaEmiFDesde());
		jsonObject.put("nuDiaEmiFHasta", frm.getNuDiaEmiFHasta());
		jsonObject.put("nuDiaCierreFDesde", frm.getNuDiaCierreFDesde());
		jsonObject.put("nuDiaCierreFHasta", frm.getNuDiaCierreFHasta());
		jsonObject.put("nbAtributoAdic1", frm.getNbAtributoAdic1());
		jsonObject.put("nbAtributoAdic2", frm.getNbAtributoAdic2());
		jsonObject.put("nbAtributoAdic3", frm.getNbAtributoAdic3());
		jsonObject.put("nbAtributoAdic4", frm.getNbAtributoAdic4());
		jsonObject.put("nbAtributoAdic5", frm.getNbAtributoAdic5());
		jsonObject.put("stHabilitado", frm.getStHabilitado());
		jsonObject.put("cdTipVal", frm.getCdTipVal());
		jsonObject.put("cdMoneda", frm.getCdMoneda());
		jsonObject.put("nuPorcVarMax", frm.getNuPorcVarMax());
		
		return jsonObject;
	}
}
