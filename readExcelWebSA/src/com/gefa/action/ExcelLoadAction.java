package com.gefa.action;

import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.core.RestriccionAction;
import com.gefa.dao.GefaAltaDao;
import com.gefa.dao.GefaModificacionDao;
import com.gefa.excel.proceso.ReadExcelAlta;
import com.gefa.excel.proceso.ReadExcelModif;
import com.gefa.form.ExcelLoadForm;
import com.gefa.utils.Constants;

public class ExcelLoadAction extends RestriccionAction {

	ActionErrors errors;
	ActionMessages messages;
	ActionMessages warnings;
   @SuppressWarnings("deprecation")
public ActionForward executeAction(ActionMapping mapping, ActionForm form,
           HttpServletRequest request, HttpServletResponse response)
           throws Exception {
    
       String target = null;
       target = "failure";
       ExcelLoadForm importForm = (ExcelLoadForm)form;
       System.out.println("entra");
       try {
    	   
    	   String url = request.getSession().getServletContext().getRealPath("/");
    	   String name = importForm.getLotesFile().getFileName();
    	   if( importForm.getLotesFile().getFileName().isEmpty()){
    		   warnings = new ActionErrors();
    		   warnings.add("warnings", new ActionError("errorfilentf"));
    	       saveErrors(request, warnings);	
//    	       saveMessages(request, warnings);
			   return mapping.findForward(target);    		   
    	   }
			File newFile = new File(url, name);
			
			// Obtener contenido del archivo de lotes elegido por el usuario.
			if (newFile.exists()) {
				newFile.delete();
			}
			
			if(!validarName(newFile.getName(), importForm)){
				saveErrors(request, errors);	
			    return mapping.findForward(target);
			}
			FileOutputStream fos = new FileOutputStream(newFile);

			fos.write(importForm.getLotesFile().getFileData());

			fos.flush();

			fos.close();
				
			if(validarAltaModif(newFile.getName())){
				ReadExcelAlta excel = new ReadExcelAlta();
				excel.readExcel(url ,name );
				GefaAltaDao gad = new GefaAltaDao();
				gad.addList(excel.getGaLista());
				
			}else{
				ReadExcelModif excel = new ReadExcelModif();
				excel.readExcel(url ,name );
				GefaModificacionDao gmd = new GefaModificacionDao();
				gmd.addList(excel.getGmLista());
			}
	    target = "success";
       }catch (Exception e) {
		// TODO: handle exception
    	   e.printStackTrace();
    	   System.out.println("falla");
           target = "failure";
	       errors = new ActionErrors();
	       errors.add("errors", new ActionError("error"));
	       saveErrors(request, errors);	
		   return mapping.findForward(target);   
	    }
       messages = new ActionMessages();
       messages.add("success", new ActionMessage("successFile"));
       saveMessages(request, messages);
       request.setAttribute("messages", messages);
       return mapping.findForward(target);
   }
   @SuppressWarnings("deprecation")
private boolean validarName(String nombre,  ExcelLoadForm importForm){
	   boolean ok = true;
	   if(importForm.getAltaModif().equalsIgnoreCase(Constants.getFileTypeMod())
				&& nombre.toUpperCase().contains("MODIFICACI")){
					System.out.println("Contiene Modif");				
				}else {
					if(importForm.getAltaModif().equalsIgnoreCase(Constants.getFileTypeNew())
					&& nombre.toUpperCase().contains("ALTA")){
						System.out.println("Contiene Alta");
					}else{
						ok = false;
					       errors = new ActionErrors();
					       errors.add("errors", new ActionError("errorfile"));
					}
				}	
	   String extension = ReadExcelModif.getFileExtension(nombre);
	   if(!Constants.FILE_EXT_XLS.equalsIgnoreCase(extension)
	   && !Constants.FILE_EXT_XLSX.equalsIgnoreCase(extension)){
		ok = false;
	       errors.add("errors", new ActionError("errorext"));
	   }
	   
	return ok;
	   
   }
private boolean validarAltaModif(String nombre){
	   if(nombre.toUpperCase().contains("MODIFICACI"))
			return false;
	   
	return true;
	   
   }
}
