package com.gefa.excel.proceso;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.format.CellDateFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gefa.model.GefaAlta;
import com.gefa.model.GefaModificaciones;

public class ReadExcelAlta extends ReadExcel
{
	public ReadExcelAlta() {
	}
	
	private List<GefaAlta> gaLista = new ArrayList<GefaAlta>();
	private Sheet sheet;
	private Workbook workbook;
    public void readExcel (String url,String name) throws Exception{
    	File newFile = new File(url , name);
        try
        {	
        	
            FileInputStream file = new FileInputStream(newFile);
            
            cargaWorkbook(newFile ,file);
       
            System.out.println("sheet.getLastRowNum()"+sheet.getLastRowNum());            
            Iterator<Row> rowIterator = sheet.iterator();
            Row row;
            int i = 1;
            if (rowIterator.hasNext()) {
            	i++;
				row = rowIterator.next();
			}
            while (rowIterator.hasNext()){
                 row = rowIterator.next();
                //For each row, iterate through all the columns
                 System.out.println("fila:"+i++);
                rowProcessor(row,newFile);
            }
            file.close();
        }
        catch (Exception e)
        {	
            e.printStackTrace();
            throw e;
        }finally{
            newFile.delete();
        	
        }
    }
    protected void cargaWorkbook(File newFile, FileInputStream file) throws IOException{

    		String fileExtn = getFileExtension(newFile.getName());

			if (fileExtn.equalsIgnoreCase("xlsx")) {
				System.out.println("xlsx");
				workbook = new XSSFWorkbook(file);
			}
			if (fileExtn.equalsIgnoreCase("xls")) {
				System.out.println("xls");
				POIFSFileSystem fs = new POIFSFileSystem(file);
				workbook = new HSSFWorkbook(fs);
			}
			sheet = workbook.getSheetAt(0);
    }
   
    protected void rowProcessor(Row row,File nombreArchivo){
    	int i = 0;
    	
        GefaAlta gfm = new GefaAlta(
        		getCellStringValue(row, i++) // tipologDificultadDistintos
        		,getCellStringValue(row, i++)// codInterno
        		,getCellStringValue(row, i++)// codPpm
        		,getCellStringValue(row, i++)// codAdicional
        		,getCellStringValue(row, i++)// nombreDeProyecto
        		,getCellStringValue(row, i++)// entrega
        		,getCellStringValue(row, i++)// codFactoria
        		,getCellStringValue(row, i++)// factoria
        		,getCellStringValue(row, i++)// tecnologia
        		,getCellStringValue(row, i++)// subproyecto
        		,getCellStringValue(row, i++)// nombreComponente
        		,getCellStringValue(row, i++)// programaNuevoModificado
        		,getCellStringValue(row, i++)// estado
        		,getCellDateValue(row, i++)// fechaRealCfgFactDiseño
        		,getCellDateValue(row, i++)// fechaPrevistaFactCgfsoftware
        		,getCellDateValue(row, i++)// fechaNegociadaFactCgfSoftware
        		,getCellDateValue(row, i++)// fechaRealFactCgfsoftware
        		,getCellStringValue(row, i++)// tipologiaInicial
        		,getCellStringValue(row, i++)// difIni
        		,getCellDoubleValue(row, i++)// costeIni
        		,getCellDoubleValue(row, i++)// horasIni
        		,getCellStringValue(row, i++)// tipologiaFactorias
        		,getCellStringValue(row, i++)// difFact
        		,getCellDoubleValue(row, i++)// costeFact
        		,getCellDoubleValue(row, i++)// horasFact
        		,getCellStringValue(row, i++)// divisa
        		,getCellStringValue(row, i++)// comentariosTipoficacionFactoria
        		,getCellStringValue(row, i++)// comentariosTipoficacionProyecto
        		,getCellStringValue(row, i++)// estadoTipificacion
        		,getCellStringValue(row, i++)// factSn
        		,getCellStringValue(row, i++)// mesFact
        		,getCellStringValue(row, i++)// añoFact
        		,getCellStringValue(row, i++)// observaciones
        		,getCellStringValue(row, i++)// metCal
        		,getCellStringValue(row, i++)// codPresupuestario
        		,getCellStringValue(row, i++)// responsableEjecucionPep
        		,getCellStringValue(row, i++)// sociedadDelPep
        		,null// fechaLibFact
        		,null//pmredmine
        		,null// f013
        		
        		,new Date()//fechaAlta
        		,null// fechaMod
        		,null// fechaBaja
        		,nombreArchivo.getName()//nombreArchivo
        		);
       gaLista.add(gfm);
    }

    /**
	 * @return the gaLista
	 */
	public List<GefaAlta> getGaLista() {
		return gaLista;
	}
	/**
	 * @param gaLista the gaLista to set
	 */
	public void setGaLista(List<GefaAlta> gaLista) {
		this.gaLista = gaLista;
	}
	
}