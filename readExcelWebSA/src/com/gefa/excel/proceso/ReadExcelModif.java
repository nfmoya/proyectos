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

import com.gefa.model.GefaModificaciones;

public class ReadExcelModif extends ReadExcel
{
	public ReadExcelModif() {
	}
	
	private List<GefaModificaciones> gmLista = new ArrayList<GefaModificaciones>();
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
    protected void rowProcessor(Row row, File nombreArchivo){
    	int i = 0;
        GefaModificaciones gfm = new GefaModificaciones
        		(getCellIntValue(row, i++), getCellStringValue(row, i++), 
        				getCellStringValue(row, i++), getCellStringValue(row, i++), 
        				getCellStringValue(row, i++), getCellStringValue(row, i++), getCellStringValue(row, i++), 
        				getCellStringValue(row, i++), getCellStringValue(row, i++),
        				getCellStringValue(row, i++), getCellStringValue(row, i++), 
        				getCellStringValue(row, i++), getCellStringValue(row, i++), 
        				getCellStringValue(row, i++), getCellDateValue(row, i++), getCellDateValue(row, i++), 
        				getCellDateValue(row, i++), getCellDateValue(row, i++), getCellStringValue(row, i++), 
        				getCellStringValue(row, i++), getCellStringValue(row, i++), getCellDoubleValue(row, i++), getCellDoubleValue(row, i++), getCellStringValue(row, i++), 
        				getCellDoubleValue(row, i++), getCellDoubleValue(row, i++),  getCellStringValue(row, i++),  getCellStringValue(row, i++), 
        				 getCellStringValue(row, i++),  getCellStringValue(row, i++),  getCellStringValue(row, i++), 
        				 getCellStringValue(row, i++),  getCellStringValue(row, i++),  getCellStringValue(row, i++),  getCellStringValue(row, i++), 
        				 getCellStringValue(row, i++),  getCellStringValue(row, i++), 
        				 getCellStringValue(row, i++),  getCellStringValue(row, i++),  getCellStringValue(row, i++), 
        				 getCellIntegerValue(row, i++),  getCellStringValue(row, i++), 
        				 getCellStringValue(row, i++),  
        				 getCellStringValue(row, i++),  
        				 getCellStringValue(row, i++), 
        				 null
        				,new Date()//fechaAlta
        				,null// fechaMod
        				,null// fechaBaja
        				,nombreArchivo.getName()//nombreArchivo
        				);
        gmLista.add(gfm);
    }

	/**
	 * @return the gmLista
	 */
	public List<GefaModificaciones> getGmLista() {
		return gmLista;
	}

	/**
	 * @param gmLista the gmLista to set
	 */
	public void setGmLista(List<GefaModificaciones> gmLista) {
		this.gmLista = gmLista;
	}
}