package com.bbva.cfs.servprest.action;

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

import com.bbva.ar.utils.Validar;
import com.bbva.cfs.commons.action.CommonAction;
import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.commons.utils.AsciiConstants;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.proveedor.model.ProveedorPeriodo;
import com.bbva.cfs.proveedor.service.GetProveedoresPeriodosService;
import com.bbva.cfs.servprest.form.ServPrestForm;
import com.bbva.cfs.servprest.model.ServPrest;
import com.bbva.cfs.servprest.model.ServPrestShort;
import com.bbva.cfs.servprest.service.GetServPrestPeriodosService;
import com.bbva.cfs.servprest.service.GetServPrestService;

public class ServPrestGridListAction extends CommonAction {
	private int cantPeriodos;
	ServPrestForm sfForm;
	// colnames
	String[] colNames;
	String[] colModels;
	String   expand;
	
	private String colModelsUnique;
	private ServPrestForm form;
	private String[] tot;
	// 
	private List<ServPrest> list;
	String listaPeriodos[];
	private List allRows;
	private List rowsToShow;
	private JSONObject jsonObctForm;
	private JSONArray jsonArray;

	private GetServPrestService service;

	public ServPrestGridListAction() {
		// TODO Auto-generated constructor stub
	}

	public ActionForward loadGridServPrest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.tot =   new String[55];
		this.initialise(request.getSession());

		expand  = request.getParameter("expand");
		
		response.setContentType("application/json;charset=UTF-8");
		sfForm = (ServPrestForm) form;
		String cantPeriodos = request.getParameter("cantPeriodos");
		//String cantPeriodos = "12";
		this.cantPeriodos = (new Integer(cantPeriodos)).intValue();
		list = new ArrayList<ServPrest>();

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
	public List fillRowsShort() throws GeneralException {
		
		try {
			getPeriodos(sfForm.getSelectedProveedor(), sfForm.getSelectedSector(),
					sfForm.getSelectedProducto(), sfForm.getSelectedGrupo(),
					sfForm.getFhDesde(), sfForm.getFhHasta(),
					sfForm.getCdComprobante(), sfForm.getRemitoDesde(),
					sfForm.getRemitoHasta(), sfForm.getStLoteDet(), sfForm.getCdLote());
			service = new GetServPrestService(this.iWebClient);
			if(expand.equalsIgnoreCase("no"))
				result = service.execute(sfForm.getSelectedProveedor(), sfForm.getSelectedSector(),
						sfForm.getSelectedProducto(), sfForm.getSelectedGrupo(), null,
						sfForm.getFhDesde(), sfForm.getFhHasta(),null, sfForm.getCdComprobante(), 
						sfForm.getRemitoDesde(), sfForm.getRemitoHasta(),sfForm.getStLoteDet(), sfForm.getCdLote(), 0);
			else
				result = service.execute(sfForm.getSelectedProveedor(), sfForm.getSelectedSector(),
						sfForm.getSelectedProducto(), sfForm.getSelectedGrupo(), null,
						sfForm.getFhDesde(), sfForm.getFhHasta(),null, sfForm.getCdComprobante(), 
						sfForm.getRemitoDesde(), sfForm.getRemitoHasta(),sfForm.getStLoteDet(), sfForm.getCdLote(), 1);


			list = fillDataShort(service.getSectoresList());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
		return list;
	}



	private List fillDataShort(List servPrestList) {
		
		List lista = new ArrayList() ;		
		String claveAnt = new String();
		ServPrestShort sfs = servPrestList.size() == 0? null : (ServPrestShort) servPrestList.get(0);
		ServPrest linea = new ServPrest() ;
		ServPrest tot = new ServPrest() ;
		
		if(sfs != null){
			claveAnt = sfs.getCdProd()+sfs.getCdSector()+sfs.getCdGrupoProd();
			int i = 0;
			for (Object object : servPrestList) {
	//			System.out.println("Vuelta:"+ i++);
				sfs = (ServPrestShort) object;
				String clave =  sfs.getCdProd()+sfs.getCdSector()+sfs.getCdGrupoProd();
				if(clave.equalsIgnoreCase(claveAnt)){
					linea = validarYcargar(sfs, linea ) ;
					
				}else{
					lista.add(linea);
					tot = totales(tot , linea);
					linea = new ServPrest() ;
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
	private ServPrest validarYcargar(ServPrestShort sfs,ServPrest linea ){
		linea.setCdGrupoProd(sfs.getCdGrupoProd());
		linea.setCdProd(sfs.getCdProd());
		linea.setCdSector(sfs.getCdSector());
		if(listaPeriodos[0] != null)
			if(listaPeriodos[0].equalsIgnoreCase(sfs.getCdPeriodo())){
				linea.setCtRemitosMes1(linea.getCtRemitosMes1() + sfs.getCtRemitos());
				linea.setCtServPrestMes1(linea.getCtServPrestMes1() + sfs.getCtServPrest());
				linea.setValServPrestMes1(linea.getValServPrestMes1().add( sfs.getValServPrest()));
				linea.setCtRemitosAConcilMes1(linea.getCtRemitosAConcilMes1() + sfs.getCtRemitosAConci());
			}
		if(listaPeriodos[1] != null)
			if(listaPeriodos[1].equalsIgnoreCase(sfs.getCdPeriodo())){
				linea.setCtRemitosMes2(linea.getCtRemitosMes2() + sfs.getCtRemitos());
				linea.setCtServPrestMes2(linea.getCtServPrestMes2() + sfs.getCtServPrest());
				linea.setValServPrestMes2(linea.getValServPrestMes2().add( sfs.getValServPrest()));
				linea.setCtRemitosAConcilMes2(linea.getCtRemitosAConcilMes2() + sfs.getCtRemitosAConci());
			}
		if(listaPeriodos[2] != null)
			if(listaPeriodos[2].equalsIgnoreCase(sfs.getCdPeriodo())){
				linea.setCtRemitosMes3(linea.getCtRemitosMes3() + sfs.getCtRemitos());
				linea.setCtServPrestMes3(linea.getCtServPrestMes3() + sfs.getCtServPrest());
				linea.setValServPrestMes3(linea.getValServPrestMes3().add( sfs.getValServPrest()));
				linea.setCtRemitosAConcilMes3(linea.getCtRemitosAConcilMes3() + sfs.getCtRemitosAConci());
			}
		if(listaPeriodos[3] != null)
			if(listaPeriodos[3].equalsIgnoreCase(sfs.getCdPeriodo())){
				linea.setCtRemitosMes4(linea.getCtRemitosMes4() + sfs.getCtRemitos());
				linea.setCtServPrestMes4(linea.getCtServPrestMes4() + sfs.getCtServPrest());
				linea.setValServPrestMes4(linea.getValServPrestMes4().add( sfs.getValServPrest()));
				linea.setCtRemitosAConcilMes4(linea.getCtRemitosAConcilMes4() + sfs.getCtRemitosAConci());
			}
		if(listaPeriodos[4] != null)
			if(listaPeriodos[4].equalsIgnoreCase(sfs.getCdPeriodo())){
				linea.setCtRemitosMes5(linea.getCtRemitosMes5() + sfs.getCtRemitos());
				linea.setCtServPrestMes5(linea.getCtServPrestMes5() + sfs.getCtServPrest());
				linea.setValServPrestMes5(linea.getValServPrestMes5().add( sfs.getValServPrest()));
				linea.setCtRemitosAConcilMes5(linea.getCtRemitosAConcilMes5() + sfs.getCtRemitosAConci());
				}
		if(listaPeriodos[5] != null)
			if(listaPeriodos[5].equalsIgnoreCase(sfs.getCdPeriodo())){
				linea.setCtRemitosMes6(linea.getCtRemitosMes6() + sfs.getCtRemitos());
				linea.setCtServPrestMes6(linea.getCtServPrestMes6() + sfs.getCtServPrest());
				linea.setValServPrestMes6(linea.getValServPrestMes6().add( sfs.getValServPrest()));
				linea.setCtRemitosAConcilMes6(linea.getCtRemitosAConcilMes6() + sfs.getCtRemitosAConci());
			}
		if(listaPeriodos[6] != null)
			if(listaPeriodos[6].equalsIgnoreCase(sfs.getCdPeriodo())){
				linea.setCtRemitosMes7(linea.getCtRemitosMes7() + sfs.getCtRemitos());
				linea.setCtServPrestMes7(linea.getCtServPrestMes7() + sfs.getCtServPrest());
				linea.setValServPrestMes7(linea.getValServPrestMes7().add( sfs.getValServPrest()));
				linea.setCtRemitosAConcilMes7(linea.getCtRemitosAConcilMes7() + sfs.getCtRemitosAConci());
			}
		if(listaPeriodos[7] != null)
			if(listaPeriodos[7].equalsIgnoreCase(sfs.getCdPeriodo())){
				linea.setCtRemitosMes8(linea.getCtRemitosMes8() + sfs.getCtRemitos());
				linea.setCtServPrestMes8(linea.getCtServPrestMes8() + sfs.getCtServPrest());
				linea.setValServPrestMes8(linea.getValServPrestMes8().add( sfs.getValServPrest()));
				linea.setCtRemitosAConcilMes8(linea.getCtRemitosAConcilMes8() + sfs.getCtRemitosAConci());
			}
		if(listaPeriodos[8] != null)
			if(listaPeriodos[8].equalsIgnoreCase(sfs.getCdPeriodo())){
				linea.setCtRemitosMes9(linea.getCtRemitosMes9() + sfs.getCtRemitos());
				linea.setCtServPrestMes9(linea.getCtServPrestMes9() + sfs.getCtServPrest());
				linea.setValServPrestMes9(linea.getValServPrestMes9().add( sfs.getValServPrest()));
				linea.setCtRemitosAConcilMes9(linea.getCtRemitosAConcilMes9() + sfs.getCtRemitosAConci());
			}
		if(listaPeriodos[9] != null)
			if(listaPeriodos[9].equalsIgnoreCase(sfs.getCdPeriodo())){
				linea.setCtRemitosMes10(linea.getCtRemitosMes10() + sfs.getCtRemitos());
				linea.setCtServPrestMes10(linea.getCtServPrestMes10() + sfs.getCtServPrest());
				linea.setValServPrestMes10(linea.getValServPrestMes10().add( sfs.getValServPrest()));
				linea.setCtRemitosAConcilMes10(linea.getCtRemitosAConcilMes10() + sfs.getCtRemitosAConci());
			}
		if(listaPeriodos[10] != null)
			if(listaPeriodos[10].equalsIgnoreCase(sfs.getCdPeriodo())){
				linea.setCtRemitosMes11(linea.getCtRemitosMes11() + sfs.getCtRemitos());
				linea.setCtServPrestMes11(linea.getCtServPrestMes11() + sfs.getCtServPrest());
				linea.setValServPrestMes11(linea.getValServPrestMes11().add( sfs.getValServPrest()));
				linea.setCtRemitosAConcilMes11(linea.getCtRemitosAConcilMes11() + sfs.getCtRemitosAConci());
				}
		if(listaPeriodos[11] != null)
			if(listaPeriodos[11].equalsIgnoreCase(sfs.getCdPeriodo())){
				linea.setCtRemitosMes12(linea.getCtRemitosMes12() + sfs.getCtRemitos());
				linea.setCtServPrestMes12(linea.getCtServPrestMes12() + sfs.getCtServPrest());
				linea.setValServPrestMes12(linea.getValServPrestMes12().add( sfs.getValServPrest()));
				linea.setCtRemitosAConcilMes12(linea.getCtRemitosAConcilMes12() + sfs.getCtRemitosAConci());
		}

		linea.setCtServPrestTotal(linea.getCtServPrestTotal() + sfs.getCtServPrest());
		linea.setValServPrestTotal(linea.getValServPrestTotal().add(sfs.getValServPrest()));
		linea.setCtRemitosTotal(linea.getCtRemitosTotal() + sfs.getCtRemitos());
		linea.setCtRemitosAConcilTotal(linea.getCtRemitosAConcilTotal() + sfs.getCtRemitosAConci());
		linea.setValBrutaTotal(linea.getValBrutaTotal().add(sfs.getValBruto()));
		linea.setValNetoTotal(linea.getValNetoTotal().add(sfs.getValNeto()));
		
		
		
		return linea;
	}

	@SuppressWarnings("unchecked")
	private void getPeriodos(String prov, String sector, String prod,
			String grupo, String desde, String hasta, String comprobante,
			String remDesde, String remHasta, String stLoteDet, String lote) {
		GetServPrestPeriodosService gpps = new GetServPrestPeriodosService(this.iWebClient);
		try {
			Result resulto = gpps.execute(prov, sector, prod, grupo, desde, hasta,
					comprobante, remDesde, remHasta, stLoteDet, lote);
			listaPeriodos = new String[12];
			listaPeriodos = fillDataPeriodos(gpps.getServPrestPeriodosList());

		} catch (Exception e) {
			log.error(e);
		}

	}

	private String[] fillDataPeriodos(List listPP) {
		String lista[] = new String[12];
		for (int i = 0; i < listPP.size(); i++) {
			Object object = listPP.get(i);
			String added = new String();
			String pp = (String) object;
			System.out.println(pp.substring(6, pp.length()));
			lista[i] = pp.substring(6, pp.length());

		}
		return lista;
	}

	private void armaStringTotales(ServPrest tot2) {
		// TODO Auto-generated method stub
		this.tot[0]  = "TOTAL";
		this.tot[1]  = tot2.getCtServPrestMes1().toString();
		this.tot[2]  = tot2.getValServPrestMes1().toString(); 
		this.tot[3]  = tot2.getCtRemitosMes1().toString();
		this.tot[4]  = tot2.getCtRemitosAConcilMes1().toString();
		this.tot[5]  = tot2.getCtServPrestMes2().toString();
		this.tot[6]  = tot2.getValServPrestMes2().toString();
		this.tot[7]  = tot2.getCtRemitosMes2().toString();
		this.tot[8]  = tot2.getCtRemitosAConcilMes2().toString();
		this.tot[9]  = tot2.getCtServPrestMes3().toString();
		this.tot[10] = tot2.getValServPrestMes3().toString();
		this.tot[11] = tot2.getCtRemitosMes3().toString();
		this.tot[12] = tot2.getCtRemitosAConcilMes3().toString();
		this.tot[13] = tot2.getCtServPrestMes4().toString();
		this.tot[14] = tot2.getValServPrestMes4().toString();
		this.tot[15] = tot2.getCtRemitosMes4().toString();
		this.tot[16] = tot2.getCtRemitosAConcilMes4().toString();
		this.tot[17] = tot2.getCtServPrestMes5().toString();
		this.tot[18] = tot2.getValServPrestMes5().toString();
		this.tot[19] = tot2.getCtRemitosMes5().toString();
		this.tot[20] = tot2.getCtRemitosAConcilMes5().toString();
		this.tot[21] = tot2.getCtServPrestMes6().toString();
		this.tot[22] = tot2.getValServPrestMes6().toString();
		this.tot[23] = tot2.getCtRemitosMes6().toString();
		this.tot[24] = tot2.getCtRemitosAConcilMes6().toString();
		this.tot[25] = tot2.getCtServPrestMes7().toString();
		this.tot[26] = tot2.getValServPrestMes7().toString();
		this.tot[27] = tot2.getCtRemitosMes7().toString();
		this.tot[28] = tot2.getCtRemitosAConcilMes7().toString();
		this.tot[29] = tot2.getCtServPrestMes8().toString();
		this.tot[30] = tot2.getValServPrestMes8().toString();
		this.tot[31] = tot2.getCtRemitosMes8().toString();
		this.tot[32] = tot2.getCtRemitosAConcilMes8().toString();
		this.tot[33] = tot2.getCtServPrestMes9().toString();
		this.tot[34] = tot2.getValServPrestMes9().toString();
		this.tot[35] = tot2.getCtRemitosMes9().toString();
		this.tot[36] = tot2.getCtRemitosAConcilMes9().toString();
		this.tot[37] = tot2.getCtServPrestMes10().toString();
		this.tot[38] = tot2.getValServPrestMes10().toString();
		this.tot[39] = tot2.getCtRemitosMes10().toString();
		this.tot[40] = tot2.getCtRemitosAConcilMes10().toString();
		this.tot[41] = tot2.getCtServPrestMes11().toString();
		this.tot[42] = tot2.getValServPrestMes11().toString();
		this.tot[43] = tot2.getCtRemitosMes11().toString();
		this.tot[44] = tot2.getCtRemitosAConcilMes11().toString();
		this.tot[45] = tot2.getCtServPrestMes12().toString();
		this.tot[46] = tot2.getValServPrestMes12().toString();
		this.tot[47] = tot2.getCtRemitosMes12().toString();
		this.tot[48] = tot2.getCtRemitosAConcilMes12().toString();
		
		this.tot[49] = tot2.getCtServPrestTotal().toString();
		this.tot[50] = tot2.getValServPrestTotal().toString();
		this.tot[51] = tot2.getValBrutaTotal().toString();
		this.tot[52] = tot2.getValNetoTotal().toString();
		this.tot[53] = tot2.getCtRemitosTotal().toString();
		this.tot[54] = tot2.getCtRemitosAConcilTotal().toString();
		
	}

	private ServPrest totales(ServPrest frm,ServPrest sector ) {

        frm.setCtServPrestMes1(frm.getCtServPrestMes1() + (sector.getCtServPrestMes1()));
        frm.setCtRemitosMes1(frm.getCtRemitosMes1() + sector.getCtRemitosMes1());
        frm.setValServPrestMes1(frm.getValServPrestMes1().add(sector.getValServPrestMes1()) );
        frm.setCtRemitosAConcilMes1( frm.getCtRemitosAConcilMes1() + sector.getCtRemitosAConcilMes1() );
        
        frm.setCtServPrestMes2(frm.getCtServPrestMes2() + (sector.getCtServPrestMes2()));
        frm.setCtRemitosMes2(frm.getCtRemitosMes2() + sector.getCtRemitosMes2());
        frm.setValServPrestMes2(frm.getValServPrestMes2().add(sector.getValServPrestMes2()) );
        frm.setCtRemitosAConcilMes2( frm.getCtRemitosAConcilMes2() + sector.getCtRemitosAConcilMes2() );
        
        frm.setCtServPrestMes3(frm.getCtServPrestMes3() + (sector.getCtServPrestMes3()));
        frm.setCtRemitosMes3(frm.getCtRemitosMes3() + sector.getCtRemitosMes3());
        frm.setValServPrestMes3(frm.getValServPrestMes3().add(sector.getValServPrestMes3()) );
        frm.setCtRemitosAConcilMes3( frm.getCtRemitosAConcilMes3() + sector.getCtRemitosAConcilMes3() );
        
        frm.setCtServPrestMes4(frm.getCtServPrestMes4() + (sector.getCtServPrestMes4()));
        frm.setCtRemitosMes4(frm.getCtRemitosMes4() + sector.getCtRemitosMes4());
        frm.setValServPrestMes4(frm.getValServPrestMes4().add(sector.getValServPrestMes4()) );
        frm.setCtRemitosAConcilMes4( frm.getCtRemitosAConcilMes4() + sector.getCtRemitosAConcilMes4() );

        frm.setCtServPrestMes5(frm.getCtServPrestMes5() + (sector.getCtServPrestMes5()));
        frm.setCtRemitosMes5(frm.getCtRemitosMes5() + sector.getCtRemitosMes5());
        frm.setValServPrestMes5(frm.getValServPrestMes5().add(sector.getValServPrestMes5()) );
        frm.setCtRemitosAConcilMes5( frm.getCtRemitosAConcilMes5() + sector.getCtRemitosAConcilMes5() );
        
        frm.setCtServPrestMes6(frm.getCtServPrestMes6() + (sector.getCtServPrestMes6()));
        frm.setCtRemitosMes6(frm.getCtRemitosMes6() + sector.getCtRemitosMes6());
        frm.setValServPrestMes6(frm.getValServPrestMes6().add(sector.getValServPrestMes6()) );
        frm.setCtRemitosAConcilMes6( frm.getCtRemitosAConcilMes6() + sector.getCtRemitosAConcilMes6() );

        frm.setCtServPrestMes7(frm.getCtServPrestMes7() + (sector.getCtServPrestMes7()));
        frm.setCtRemitosMes7(frm.getCtRemitosMes7() + sector.getCtRemitosMes7());
        frm.setValServPrestMes7(frm.getValServPrestMes7().add(sector.getValServPrestMes7()) );
        frm.setCtRemitosAConcilMes7( frm.getCtRemitosAConcilMes7() + sector.getCtRemitosAConcilMes7() );

        frm.setCtServPrestMes8(frm.getCtServPrestMes8() + (sector.getCtServPrestMes8()));
        frm.setCtRemitosMes8(frm.getCtRemitosMes8() + sector.getCtRemitosMes8());
        frm.setValServPrestMes8(frm.getValServPrestMes8().add(sector.getValServPrestMes8()) );
        frm.setCtRemitosAConcilMes8( frm.getCtRemitosAConcilMes8() + sector.getCtRemitosAConcilMes8() );

        frm.setCtServPrestMes9(frm.getCtServPrestMes9() + (sector.getCtServPrestMes9()));
        frm.setCtRemitosMes9(frm.getCtRemitosMes9() + sector.getCtRemitosMes9());
        frm.setValServPrestMes9(frm.getValServPrestMes9().add(sector.getValServPrestMes9()) );
        frm.setCtRemitosAConcilMes9( frm.getCtRemitosAConcilMes9() + sector.getCtRemitosAConcilMes9() );

        frm.setCtServPrestMes10(frm.getCtServPrestMes10() + (sector.getCtServPrestMes10()));
        frm.setCtRemitosMes10(frm.getCtRemitosMes10() + sector.getCtRemitosMes10());
        frm.setValServPrestMes10(frm.getValServPrestMes10().add(sector.getValServPrestMes10()) );
        frm.setCtRemitosAConcilMes10( frm.getCtRemitosAConcilMes10() + sector.getCtRemitosAConcilMes10() );

        frm.setCtServPrestMes11(frm.getCtServPrestMes11() + (sector.getCtServPrestMes11()));
        frm.setCtRemitosMes11(frm.getCtRemitosMes11() + sector.getCtRemitosMes11());
        frm.setValServPrestMes11(frm.getValServPrestMes11().add(sector.getValServPrestMes11()) );
        frm.setCtRemitosAConcilMes11( frm.getCtRemitosAConcilMes11() + sector.getCtRemitosAConcilMes11() );

        frm.setCtServPrestMes12(frm.getCtServPrestMes12() + (sector.getCtServPrestMes12()));
        frm.setCtRemitosMes12(frm.getCtRemitosMes12() + sector.getCtRemitosMes12());
        frm.setValServPrestMes12(frm.getValServPrestMes12().add(sector.getValServPrestMes12()) );
        frm.setCtRemitosAConcilMes12( frm.getCtRemitosAConcilMes12() + sector.getCtRemitosAConcilMes12() );
        
        frm.setCtServPrestTotal(frm.getCtServPrestTotal() + (sector.getCtServPrestTotal()));
        frm.setCtRemitosTotal(frm.getCtRemitosTotal() + sector.getCtRemitosTotal());
        frm.setValServPrestTotal(frm.getValServPrestTotal().add(sector.getValServPrestTotal()));
        frm.setCtRemitosAConcilTotal(frm.getCtRemitosAConcilTotal() + sector.getCtRemitosAConcilTotal());
        frm.setValBrutaTotal(frm.getValBrutaTotal().add(sector.getValBrutaTotal()));
        frm.setValNetoTotal(frm.getValNetoTotal().add(sector.getValNetoTotal()));

        return frm;
	}

	public JSONObject convertToJSONeachAction(ActionForm form) {
		ServPrestForm frm = (ServPrestForm) form;

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
			Integer total = new Integer(String.valueOf(Double.valueOf(String.valueOf(Math.ceil(allRows.size()/ Double.valueOf(request.getParameter("rows")).doubleValue()))).intValue()));
			jsonObjectForm.put("gridModelToShow", rowsToShow);
			jsonObjectForm.put("total", 1);// total);
			jsonObjectForm.put("records", String.valueOf(allRows.size()));
			jsonObjectForm.put("rowsName", request.getParameter("rowsName"));
			jsonObjectForm.put("page","" + 1 ); //Integer.parseInt(request.getParameter("page")));
			jsonObjectForm.put("rows", "" + allRows.size()); //Integer.parseInt(request.getParameter("rows")));

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
