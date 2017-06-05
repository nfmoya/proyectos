package com.bbva.security.action;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import net.sf.json.JSONObject;

import com.bbva.cfs.commons.action.CommonAction;
import com.bbva.cfs.commons.model.Session;
import com.bbva.cfs.commons.utils.Constants;

public class CheckSessionUserAction  extends CommonAction{
	
	
	private Session session;

//	private Long userId;
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		this.initialise(request.getSession());
		System.out.println("CheckSession "+new Date());
//		this.userId = this.getAutenticathedUserId(request);
		this.session = (Session) request.getSession().getAttribute(Constants.SESSION);
//		 verificarPermisos(request, importForm); 
//		this.obtenerCombos(request);
//		String realPath = (String) request.getSession().getServletContext().getRealPath("/");
//		log.info("realPath : "+ realPath);
//		iniciarConfig(realPath);
		
		int estadoS = 0;
		
		if((request.getSession().getAttribute(Constants.SESSION) == null)){
			System.out.println("Entro");
//			return mapping.findForward("success");
			estadoS = 1;	
			
		} ;
		
		PrintWriter out = null;

		Map resp = new HashMap();

		resp.put("estadoSesion", estadoS);
		

		JSONObject jsonObject = JSONObject.fromObject(resp);
		response.setContentType("text/json");
		out = response.getWriter();
		out.print(jsonObject);
		if (out != null) {
			out.flush();
			out.close();
		}

		return null;
			
		
	}
	




}
