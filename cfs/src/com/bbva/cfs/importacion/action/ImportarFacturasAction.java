package com.bbva.cfs.importacion.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.ProcessResult;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.bbva.cfs.commons.action.CommonAction;
import com.bbva.cfs.commons.exceptions.GlobalActionException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.model.Session;
import com.bbva.cfs.commons.utils.AsciiConstants;
import com.bbva.cfs.commons.utils.Constants;
import com.bbva.cfs.commons.utils.ParameterType;
import com.bbva.cfs.commons.utils.ResultDatabase;
import com.bbva.cfs.parameters.service.GetParameterListService;
import com.bbva.cfs.proveedor.model.Proveedor;
import com.bbva.cfs.commons.datasource.ConfigWeb;
import com.bbva.cfs.importacion.form.ImportacionForm;

public class ImportarFacturasAction extends CommonAction {

	

	private Session session;

	private Long userId;
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		this.initialise(request.getSession());
		this.userId = this.getAutenticathedUserId(request);
		this.session = (Session) request.getSession().getAttribute(Constants.SESSION);
//		 verificarPermisos(request, importForm); 
		this.obtenerCombos(request);
		String realPath = (String) request.getSession().getServletContext().getRealPath("/");
		log.info("realPath : "+ realPath);
		iniciarConfig(realPath);
		return this.doFindSuccess(mapping);
	}
	
	public void iniciarConfig(String realPath){
		
		
		File file = new File(realPath );
		ConfigWeb.setBasepath(file.getParent());
		
	}

	public ActionForward importar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//////////////////////////////
		ImportacionForm importForm = (ImportacionForm)form;
		// Obtener contenido del archivo de lotes elegido por el usuario.
		InputStream is = importForm.getLotesFile().getInputStream();
		String project = ConfigWeb.get().getProjectFolder();
		String lotes = ConfigWeb.get().getFilesFolderProc();
		String logs = ConfigWeb.get().getFilesFolderLogs();
		// Copiar archivo en directorio local
		String lotesFileName = importForm.getLotesFile().getFileName();
		this.createLocalFile(is, lotesFileName,lotes);
		
		// Procesar Archivo de Lotes.

		String proveedor = importForm.getProveedorSelected();
		String observaciones = importForm.getObservaciones();
	
		ProcessResult processResult = main.Main.execute(project, lotes + lotesFileName, logs, proveedor,
				observaciones);
	
		int totalImportadosCorrec =  (processResult.getImportedRecords() == null ? 0 : processResult.getImportedRecords()) ;
		int codError = processResult.getReturnCode() == null ? 90 : processResult.getReturnCode(); 
		List<String> errors = processResult.getErrors();
	

		this.obtenerCombos(request);
		if(processResult.getImportedRecords() != processResult.getTotalRecords()
		&& codError != 99){
			codError = 2;
		}
		if(codError == 90){
			deleteLocalFile(lotesFileName,lotes);
		}
//		Convierto de lista a array para mejor procesamiento de errores en javascript
		String concat = new String();
		for(int i = 0; i < errors.size(); i++){ 
//			System.out.println("ERROR");
			System.out.println(errors.get(i));
			concat =concat + errors.get(i) + "\n";
			System.out.println(concat);
			
		}
		
		
		request.setAttribute("totalImportadosCorrec", totalImportadosCorrec);
		request.setAttribute("codError", codError);
		request.setAttribute("errors", concat);
		request.setAttribute("deSumit", true);

		return mapping.findForward("importServFact");

	}


	private void obtenerCombos(HttpServletRequest request)
			throws GlobalActionException {
		GetParameterListService getParamListService = new GetParameterListService(
				this.iWebClient);
		try {
			result = getParamListService.execute(
					ParameterType.PROVEEDOR.getId(), "", "");
			List<Proveedor> proveedores = getParamListService.getParameterList();
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
	private void deleteLocalFile(String uploadedFileName,String lotesUrl){
		try{
			 
    		File file = new File(lotesUrl
					+ uploadedFileName);
 
    		if(file.delete()){
    			System.out.println(file.getName() + " is deleted!");
    		}else{
    			System.out.println("Delete operation is failed.");
    		}
 
    	}catch(Exception e){
 
    		e.printStackTrace();
 
    	}
	}
	public void createLocalFile(InputStream inputStream, String uploadedFileName,String lotesUrl) {
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
			log.error(e.getMessage());
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
					log.error(e.getMessage());
					e.printStackTrace();
					
				}

			}
		}
	}

}
