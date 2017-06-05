package com.gefa.excel.proceso;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
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

public abstract class ReadExcel
{
	public ReadExcel() {
	}
	
	protected Sheet sheet;
	protected Workbook workbook;
	protected void readExcel (String url,String name) throws Exception {
        try
        {	
        	File newFile = new File(url , name);
            FileInputStream file = new FileInputStream(newFile);
            
            cargaWorkbook(newFile ,file);
       
            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            Row row;
            if (rowIterator.hasNext()) {
				row = rowIterator.next();
			}
            while (rowIterator.hasNext()){
                 row = rowIterator.next();
                //For each row, iterate through all the columns
                rowProcessor(row);
            }
            file.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
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
    protected void rowProcessor(Row row){
    	
    }
    protected Date getCellDateValue(Row row,int index){

    	Date date = HSSFDateUtil.getJavaDate(row.getCell(index).getNumericCellValue());
    	return date;
    }
    protected Double getCellDoubleValue(Row row,int index){

    	Double doble = row.getCell(index).getNumericCellValue();
    	return doble;
    }
    protected int getCellIntValue(Row row,int index){

    	int entero = (int) row.getCell(index).getNumericCellValue();
    	return entero;
    }
    protected Integer getCellIntegerValue(Row row,int index){

    	Integer entero = Integer.valueOf((int) row.getCell(index).getNumericCellValue());
    	return entero;
    }
    protected String getCellStringValue(Row row,int index){

    	String string = row.getCell(index).getStringCellValue();
    	return string;
    }
    protected BigDecimal getCellBigDecimalValue(Row row,int index){

    	BigDecimal doble = BigDecimal.valueOf(row.getCell(index).getNumericCellValue());
    	return doble;
    }
    
    public static String getFileExtension(String fname2) {
		String fileName = fname2;
		String fname = "";
		String ext = "";
		int mid = fileName.lastIndexOf(".");
		fname = fileName.substring(0, mid);
		ext = fileName.substring(mid + 1, fileName.length());
		return ext;
	}

}