package com.bbva.ar.utils.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.model.Result;
import com.bbva.cfs.conciliacion.form.DiferenciaConciliacionForm;
import com.bbva.cfs.conciliacion.model.DiferenciaConciliacionModel;
import com.bbva.cfs.conciliacion.service.DifConciliacionService;

public class DiferenciaConciliaExcel extends Excel{
	
	private DifConciliacionService service;
	private List<DiferenciaConciliacionModel> dcList;
	private List<String> titulos;
	
	
	public DiferenciaConciliaExcel(IWebClient iWebClient, String realPath, DiferenciaConciliacionForm dcf, String nomFile) throws Exception{
		service = new DifConciliacionService(iWebClient);
		dcList = new ArrayList<DiferenciaConciliacionModel>();
		super.result = new Result();
		super.realPath = realPath;
		setFilename(generateNameFile(nomFile));
		titulos = this.getTitle();
		this.getDifConcilia(dcf);
	}
	
	@Override
	public List<String> getTitle(){
		List<String> titulos = new ArrayList<String>();
		
		titulos.add("Nro. Conciliacion");
		titulos.add("Nro. Diferenciacion");
		titulos.add("Sector Control");
		titulos.add("Periodo Fact.");
		titulos.add("Producto");
		titulos.add("Descripcion");
		titulos.add("Pza. Desde");
		titulos.add("Pza. Hasta");
		titulos.add("Cantidad");
		titulos.add("Unidad Valor");
		titulos.add("Valor");
		titulos.add("Tipo Valoracion");
		titulos.add("Estado");
		titulos.add("Fecha Alta");
		titulos.add("Usuario Alta");
		titulos.add("Observaciones");
		titulos.add("Situacion");			
		
		return titulos;
	}
	

	public void getDifConcilia(DiferenciaConciliacionForm dcf) throws Exception {
		service.getDifConciliacionList(dcList, dcf);
	}
	
	@Override
	protected void setFilename(String nomFile) {
		filename  = nomFile;
	}
		
	@Override
	public HSSFWorkbook doExcel() throws Exception{
		return doExcel(titulos, dcList, filename);
	}
	

	/**
	 * Metodo que graba en el array los datos del objeto 
	 * 
	 * @param dcm
	 * @return
	 */
	private List<String> convertObjectToString(DiferenciaConciliacionModel dcm){
		List<String> cellValues = new ArrayList<String>();
		
		cellValues.add(Long.toString(dcm.getCdConciliacion()));
		cellValues.add(dcm.getCdOrden());
		cellValues.add(dcm.getCdSector());
		cellValues.add(dcm.getCdPeriodoFact());
		cellValues.add(dcm.getCdProducto());
		cellValues.add(dcm.getDescProducto());
		cellValues.add(dcm.getPzaDesde());
		cellValues.add(dcm.getPzaHasta());
		cellValues.add(dcm.getCtDiferencia());
		cellValues.add(dcm.getCdUniVal());
		cellValues.add(dcm.getImPrecioTot());
		cellValues.add(dcm.getCdTipVal());
		cellValues.add(dcm.getStDiferencia());
		cellValues.add(dcm.getFhALta());
		cellValues.add(dcm.getDescUsuAlta());
		cellValues.add(dcm.getObservacion());
		cellValues.add(dcm.getTpSolucion());
				
		return cellValues;
	}
	
	/**
	 * Genera el archivo de excel. Con sus respectivos etilos en las celdas
	 * 
	 * @param titulos
	 * @param list
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public HSSFWorkbook doExcel(List<String> titulos, List<DiferenciaConciliacionModel> list, String filename) throws Exception{
		
		HSSFWorkbook wb = new HSSFWorkbook();
		CreationHelper createHelper = wb.getCreationHelper();
		Sheet sheet = wb.createSheet("informe");

		// se crean los estilos de las celdas
		CellStyle cellStyleHCentradoVCentrado = wb.createCellStyle();
		cellStyleHCentradoVCentrado.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyleHCentradoVCentrado
				.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		CellStyle cellStyleHIzquierdaVCentrado = wb.createCellStyle();
		cellStyleHIzquierdaVCentrado.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		cellStyleHIzquierdaVCentrado
				.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		CellStyle cellStyleFecha = wb.createCellStyle();
		cellStyleFecha.setDataFormat(createHelper.createDataFormat().getFormat(
				"dd/MM/yyyy"));
		cellStyleFecha.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyleFecha.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		// se crea la fila de los titulos

		Row rowTitulo = sheet.createRow((short) 0);

		for (int countTitulo = 0; countTitulo < this.getTitle().size(); countTitulo++) {
			rowTitulo.createCell(countTitulo).setCellValue(
					titulos.get(countTitulo));
		}

		int cantColumn = rowTitulo.getLastCellNum();

		for (int k = 0; k < cantColumn; k++) {
			sheet.setDefaultColumnStyle(k, cellStyleHCentradoVCentrado);
		}
		
		
		//Agrego de a uno los datos de la grilla
		
		for (int i = 0; i < list.size(); i++) {
			DiferenciaConciliacionModel dcm = list.get(i);
			List<String> cellValues = convertObjectToString(dcm);
			Row row = sheet.createRow((short) i + 1);
			
			for (int cantString = 0; cantString < cellValues.size() ; cantString ++){
				// se crean las celdas
				Cell cellValue = row.createCell(cantString);
				cellValue.setCellValue(cellValues.get(cantString));
			}		
		}
		//--------------------------
		
		for (int j = 0; j < cantColumn; j++) {
			sheet.autoSizeColumn(j);
		}
		
		return wb;
	}
	
	/**
	 * Metodo que genera el nombre del archivo con la fecha actual
	 * 
	 * @param nameFile
	 * @return String
	 */
	private String generateNameFile(String nameFile){
		
		Calendar c = Calendar.getInstance();
		
		String dia = Integer.toString(c.get(Calendar.DATE));
		String mes = Integer.toString(c.get(Calendar.MONTH));
		String anio = Integer.toString(c.get(Calendar.YEAR));
		
		nameFile += "_" + dia + "-" + mes + "-"+ anio;
		
		return nameFile;
	}

}