package com.gefa.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
 
public class ExcelLoadForm extends ActionForm {
 
    private FormFile lotesFile;
    private String altaModif;
    
    @Override
    public ActionErrors validate(ActionMapping mapping,
    		HttpServletRequest request) {
    	// TODO Auto-generated method stub
    	return super.validate(mapping, request);
    }
     
    
    
    public FormFile getLotesFile() {
		return lotesFile;
	}
	public void setLotesFile(FormFile lotesFile) {
		this.lotesFile = lotesFile;
	}
	public String getAltaModif() {
		return altaModif;
	}
	public void setAltaModif(String altaModif) {
		this.altaModif = altaModif;
	}
}