package com.bbva.cfs.importacion.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.io.BufferedInputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servprest.main.ProcessResult;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bbva.cfs.commons.action.CommonAction;
import com.bbva.cfs.commons.datasource.ConfigWeb;
import com.bbva.cfs.commons.exceptions.GlobalActionException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.model.Session;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.commons.utils.ParameterType;
import com.bbva.cfs.commons.utils.ResultDatabase;
import com.bbva.cfs.importacion.form.ImportacionForm;
import com.bbva.cfs.parameters.service.GetParameterListService;
import com.bbva.cfs.proveedor.model.Proveedor;

public class ImportarServPrestAction extends CommonAction {

	private Session session;

	private Long userId;

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		this.userId = this.getAutenticathedUserId(request);
		this.session = (Session) request.getSession().getAttribute(
				Constants.SESSION);
		// verificarPermisos(request, importForm);
		this.obtenerCombos(request);
		String realPath = (String) request.getSession().getServletContext()
				.getRealPath("/");

		iniciarConfig(realPath);
		return this.doFindSuccess(mapping);
	}

	public void iniciarConfig(String realPath) {

		File file = new File(realPath);
		ConfigWeb.setBasepath(file.getParent());

	}

	public ActionForward importar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// ////////////////////////////
		ImportacionForm importForm = (ImportacionForm) form;
		// Obtener contenido del archivo de lotes elegido por el usuario.
		InputStream is = importForm.getLotesFile().getInputStream();
		String project = ConfigWeb.get().getProjectFolder();
		String lotes = ConfigWeb.get().getFilesFolderProc();
		String logs = ConfigWeb.get().getFilesFolderLogs();
		// Copiar archivo en directorio local
		String lotesFileName = importForm.getLotesFile().getFileName();

		// this.createLocalFile(is, lotesFileName,lotes);

		log.info("Escribiendo Archivo");

		boolean exitoParseo = false;
		String error = "";

		try {

			File newFile = new File(lotes, lotesFileName);

			// Obtener contenido del archivo de lotes elegido por el usuario.
			if (newFile.exists()) {
				log.info("CFS - ImportarServPrestAction: Archivo Existe se borra");
				newFile.delete();
			}

			log.info("CFS - ImportarServPrestAction: Se copia el contenido del archivo");
			FileOutputStream fos = new FileOutputStream(newFile);
			fos.write(importForm.getLotesFile().getFileData());
			fos.flush();
			fos.close();

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date today = Calendar.getInstance().getTime();
			String fecha = df.format(today);

			InputStream input = new BufferedInputStream(new FileInputStream(
					newFile));

			// POIFSFileSystem fs = new POIFSFileSystem(input);
			Workbook wb = null; // Declare XSSF WorkBook

			String fileExtn = getFileExtension(newFile.getName());

			Sheet sheet = null;
			
			log.info("CFS - ImportarServPrestAction: Se carga la hoja de trabajo");
			if (fileExtn.equalsIgnoreCase("xlsx")) {
				wb = new XSSFWorkbook(input);
				sheet = wb.getSheetAt(0);
			}
			if (fileExtn.equalsIgnoreCase("xls")) {
				POIFSFileSystem fs = new POIFSFileSystem(input);
				wb = new HSSFWorkbook(fs);
				sheet = wb.getSheetAt(0);
			}

			// XSSFSheet sheet = wb.getSheetAt(0);

			FormulaEvaluator evaluator = wb.getCreationHelper()
					.createFormulaEvaluator();

			log.info("CFS - ImportarServPrestAction: Pasa el Excel a TXT");
			String linea = "";
			PrintWriter writer = new PrintWriter(lotes + lotesFileName, "UTF-8");
			Iterator rows = sheet.rowIterator();
			String a2 = null;
			/*
			 * if (fileExtn.equalsIgnoreCase("xlsx")) { XSSFCell cell =
			 * (XSSFCell) sheet.getRow(1).getCell(0); a2 =
			 * cell.getRawValue().trim(); } else {
			 */
			if (sheet.getRow(1).getCell(0).getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				double d = sheet.getRow(1).getCell(0).getNumericCellValue();
				a2 = "" + (int) d;
			} else {
				a2 = sheet.getRow(1).getCell(0).getStringCellValue();
			}
			// }


			if (a2.equals("1")) {

				int i = 0;

				while (rows.hasNext()) {
					Row row = (Row) rows.next();
					linea = "";
					if (i == 0) {
						String strFormula = "SUM(F2:F" + sheet.getLastRowNum()
								+ ")";
						log.info("CFS - ImportarServPrestAction: Formula " + strFormula);

						Cell cell = row.createCell(20);
						log.info("CFS - ImportarServPrestAction:obteniendo celda " + cell);
						cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);

						cell.setCellFormula(strFormula);
						log.info("CFS - ImportarServPrestAction: Formula " + cell.getCellFormula());

						Cell cellValue = evaluator.evaluateInCell(cell);

						log.info("CFS - ImportarServPrestAction: Valor "
								+ cellValue.getNumericCellValue());

						linea = i + "|" + importForm.getProveedorSelected()
								+ "|" + lotesFileName + "|" + fecha + "|"
								+ (sheet.getLastRowNum() - 1) + "|"
								+ cellValue.getNumericCellValue() + "|";

					} else {

//						log.info("\n");
						Iterator cells = row.cellIterator();
						linea = "";
						int columnas = 14;
						int j = 0;
						while (cells.hasNext()) {

							/*
							 * if (fileExtn.equalsIgnoreCase("xlsx")) { XSSFCell
							 * cell = (XSSFCell) cells.next();
							 * 
							 * if (cell.getColumnIndex()>j) { while
							 * (cell.getColumnIndex()>j) { linea +="|"; j++; } }
							 * 
							 * log.info("value " +
							 * cell.getRawValue().trim() +
							 * " - cell.getColumnIndex() " +
							 * cell.getColumnIndex() ); if (
							 * (cell.getColumnIndex()==6) ||
							 * (cell.getColumnIndex()==7) ) {
							 * log.info("numeric type -> " +
							 * (cell.getCellType() ==
							 * XSSFCell.CELL_TYPE_NUMERIC));
							 * 
							 * SimpleDateFormat sdf = new
							 * SimpleDateFormat("yyyy-MM-dd"); String date =
							 * sdf.format(cell.getDateCellValue());
							 * 
							 * linea += date; } else if (cell.getCellType() ==
							 * XSSFCell.CELL_TYPE_STRING){ linea +=
							 * cell.getStringCellValue().trim(); } else { linea
							 * += cell.getRawValue().trim(); }
							 * 
							 * } else {
							 */
							
							/*
							 * Para que cuando los campos del xls vienen sin datos los
							 * complete con vacio para que el batch complete con el sector default.
							 * 
							 * @autor David
							 */
							Cell cell = (Cell) cells.next();

							if (cell.getColumnIndex() > j) {
								while (cell.getColumnIndex() > j) {
//									if(j == 1 || j == 2 || j== 3)
//										linea += "  |";
//									else
									linea += "  |";
									j++;
								}
							}

							if ((cell.getColumnIndex() == 6)
									|| (cell.getColumnIndex() == 7)) {
								
								linea += getProcessFecha(cell);
								
							} else if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
								linea += "  ";								
							} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
								linea += cell.getStringCellValue().trim();							
							}else{
								linea += "" + (int) cell.getNumericCellValue();
							}
							
							if (cells.hasNext())
								linea += "|";
							j++;
						}
						while (j < columnas) {
							linea += "  |";
							j++;
						}
					}
					
					log.info(linea);
					
					System.out.println("Linea...> " + linea);
					
					if (!linea.equals(""))
						writer.println(linea);
					i++;
				}
				log.info("CFS - ImportarServPrestAction: Parseo Exitoso");
				exitoParseo = true;
			} else {
				error = "Formato incorrecto";
				exitoParseo = false;
				linea = "Sin linea";
				writer.println(linea);
			}
			writer.close();

			log.info("CFS - ImportarServPrestAction: Procesar");

		} catch (Exception ex) {
			error = ex.getMessage();
			log.error("CFS - ImportarServPrestAction: exception " + error);
			ex.printStackTrace();
		}

		// Procesar Archivo de Lotes.
		// ProcessResult processResult = main.Main.execute(LOTES_DIR, LOTES_DIR
		// + lotesFileName, LOGS_DIR, importForm.getProveedorSelected(),
		// importForm.getObservaciones());
		// log.info(processResult);

		// this.obtenerCombos(request);

		// //////////////////////////

		// //////*/////////////////////////////////
		// String ruta = request.getParameter("urlArch");
		String proveedor = importForm.getProveedorSelected();
		String observaciones = importForm.getObservaciones();

		// File rutaArchivo = new File(ruta);
		// String nombreArchivo = rutaArchivo.getName();

		// Obtener contenido del archivo de lotes elegido por el usuario.

		// FileInputStream inStream = new FileInputStream(ruta);
		//
		// InputStream is =inStream;
		//
		// // Copiar archivo en directorio local
		// String lotesFileName = nombreArchivo;
		//
		// String project = ConfigWeb.get().getProjectFolder();
		// String lotes = ConfigWeb.get().getFileConfig().getFolderProc();
		// String logs = ConfigWeb.get().getFileConfig().getFolderLogs();
		//
		// this.createLocalFile(is, lotesFileName,lotes);
		//
		// Procesar Archivo de Lotes.
		log.info("CFS - ImportarServPrestAction: project " + project);
		log.info("CFS - ImportarServPrestAction: lotes " + lotes);
		log.info("CFS - ImportarServPrestAction: lotesFileName " + lotesFileName);
		int totalImportadosCorrec = 0;
		int codError = 99;
		List<String> errors = null;
		log.info("CFS - ImportarServPrestAction: Exito Parseo" + exitoParseo);
		if (exitoParseo) {

			log.info("CFS - ImportarServPrestAction: Procesando ");

			ProcessResult processResult = servprest.main.Main.execute(project,
					lotes + lotesFileName, logs, proveedor, observaciones);

			totalImportadosCorrec = (processResult.getImportedRecords() == null ? 0
					: processResult.getImportedRecords());
			codError = processResult.getReturnCode() == null ? 90
					: processResult.getReturnCode();
			errors = processResult.getErrors();

			log.info("CFS - ImportarServPrestAction: Terminado  - totalImport "
					+ totalImportadosCorrec + " - CodError " + codError
					+ " - errors " + errors);
			
			
			File file = new File(lotes + lotesFileName);
			 
    		if(file.delete()){
    			log.info("CFS - ImportarServPrestAction: "+file.getName() + " is deleted!");
    		}else{
    			log.error("CFS - ImportarServPrestAction: Delete operation is failed.");
    		}			
		

		} else {
			codError = 90;
			errors = new ArrayList<String>();
			errors.add("Formato incorrecto de archivo. ");
		}

		this.obtenerCombos(request);
		/* /************************************** */

		// PrintWriter out = null;
		//
		// Map resp = new HashMap();
		//
		//
		// resp.put("numImportados", totalImportadosCorrec);
		// resp.put("codError", codError);
		// resp.put("errors", errors);
		//
		//
		// JSONObject jsonObject = JSONObject.fromObject(resp);
		// response.setContentType(AsciiConstants.CONTENT_TYPE_JSON);
		// out = response.getWriter();
		// out.print(jsonObject);
		// if (out != null) {
		// out.flush();
		// out.close();
		// }

		request.setAttribute("totalImportadosCorrec", totalImportadosCorrec);
		request.setAttribute("codError", codError);
		request.setAttribute("errors", errors);
		request.setAttribute("deSumit", true);

		return mapping.findForward("importServPrest");

	}

	private void obtenerCombos(HttpServletRequest request)
			throws GlobalActionException {
		GetParameterListService getParamListService = new GetParameterListService(
				this.iWebClient);
		try {
			result = getParamListService.execute(
					ParameterType.PROVEEDOR.getId(), "", "");
			List<Proveedor> proveedores = getParamListService
					.getParameterList();
			if (result.isSuccesfull()) {
				request.setAttribute("proveedores", proveedores);
				// importForm.setFiltroProveedorList(filtroProveedorList);
			} else {
				throw new GlobalActionException(result.getErrorCode(),
						result.getErrorDesc());
			}
		} catch (GlobalServiceException e) {
			throw new GlobalActionException(e);
		} catch (Exception e) {
			log.debug(e);
			throw new GlobalActionException(
					ResultDatabase.ACTION_ERROR.getCode(),
					ResultDatabase.ACTION_ERROR.getMessage());
		}
	}

	public void createLocalFile(InputStream inputStream,
			String uploadedFileName, String lotesUrl) {
		OutputStream outputStream = null;
		try {
			// transferir el contenido del inputStream a un FileOutputStream
			outputStream = new FileOutputStream(new File(lotesUrl
					+ uploadedFileName));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}

	private static String getFileExtension(String fname2) {
		String fileName = fname2;
		String fname = "";
		String ext = "";
		int mid = fileName.lastIndexOf(".");
		fname = fileName.substring(0, mid);
		ext = fileName.substring(mid + 1, fileName.length());
		return ext;
	}
	
	/**
	 * Metodo que procesa la celda de las fechas y devuelve el contenido
	 * con formato. Si es Fecha lo convierte al formato yyyy-MM-dd, sino pone vacio
	 * 
	 * @author David (XA50126)
	 * 
	 * @param cell
	 * @return String -> fecha o vacio
	 */
	
	private String getProcessFecha(Cell cell){
		
		String date= "";
		
		if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
			String dato = cell.getStringCellValue();
			//Considero que es media innecesaria la validacion pero la dejo por las dudas!
			if(dato == null || dato.trim().length()== 0)
				date = "  ";
			else
				date = dato.trim();									
		} else {
			if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK){
				date = "  ";
			}else{
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd");
				date = sdf.format(cell.getDateCellValue());
			}
		}
		
		return date;		
	}
	
}
