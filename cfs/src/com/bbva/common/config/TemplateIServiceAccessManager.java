package com.bbva.common.config;

import java.util.Iterator;
import java.util.Map;

import com.bbva.cfs.commons.logging.Log;
import com.bbva.cfs.commons.utils.CheckInvalidCharacter;

import ar.com.itrsa.sam.IContext;
import ar.com.itrsa.sam.IContextManager;
import ar.com.itrsa.sam.IDataSourceManager;
import ar.com.itrsa.sam.IServiceAccessManager;
import ar.com.itrsa.sam.ITransactionManager;
import ar.com.itrsa.sam.TransactionException;
import ar.com.itrsa.sam.ds.IDataSourceFactoryManager;

public class TemplateIServiceAccessManager implements IServiceAccessManager {

	private IServiceAccessManager sam;

	public IServiceAccessManager getSam() {
		return sam;
	}

	public void setSam(IServiceAccessManager sam) {
		this.sam = sam;
	}

	public TemplateIServiceAccessManager(IServiceAccessManager sam) {
		this.sam = sam;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void execute(String store_procedure_name, IContext iContext,
			Object parameters) throws TransactionException {
		// TODO Auto-generated method stub
		// CheckInvalidCharacter.checkMap(parameters,"Ñ","#");
//		try {
//			System.out.println(store_procedure_name);
//			if (store_procedure_name.contains("TM_DBCFS.INICIAR_SESION"))
				sam.execute(store_procedure_name, iContext, parameters);
//			else {
//				parameters = CheckInvalidCharacter.reCheckMap(parameters, "Ñ",
//						"#");
//				sam.execute(store_procedure_name, iContext, parameters);
//				parameters = CheckInvalidCharacter.reCheckMap(parameters, "#",
//						"Ñ");
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println("FUUUUUUUUUUUUUUUUUUUU!");
//			System.out.println("FUUUUUUUUUUUUUUUUUUUU!");
//			System.out.println("FUUUUUUUUUUUUUUUUUUUU!");
//			System.out.println("FUUUUUUUUUUUUUUUUUUUU!");
//			System.out.println("FUUUUUUUUUUUUUUUUUUUU!");
//			System.out.println("FUUUUUUUUUUUUUUUUUUUU!");
//			System.out.println("FUUUUUUUUUUUUUUUUUUUU!");
//			System.out.println("FUUUUUUUUUUUUUUUUUUUU!");
//			System.out.println("FUUUUUUUUUUUUUUUUUUUU!");
//			System.out.println("FUUUUUUUUUUUUUUUUUUUU!");
//			e.printStackTrace();
//		}
		// String pepe = ((String) parameters).replaceAll("Ñ","###") ;
		// System.out.println(pepe);
		// parameters = (Object) pepe;

		// pepe = ((String) parameters).replaceAll("###","Ñ") ;
		// System.out.println(pepe);
		// parameters = (Object) pepe;
		// CheckInvalidCharacter.checkMapCursor(parameters,"#","Ñ");
	}

	public void addTransactionManager(ITransactionManager arg0) {
		// TODO Auto-generated method stub

	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public IContextManager getContextManager() {
		// TODO Auto-generated method stub
		return null;
	}

	public IDataSourceFactoryManager getDataSourceFactoryManager() {
		// TODO Auto-generated method stub
		return null;
	}

	public IDataSourceManager getDataSourceManager() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getServiceAccessManagerKey() {
		// TODO Auto-generated method stub
		return null;
	}

	public ITransactionManager getTransactionManager()
			throws TransactionException {
		// TODO Auto-generated method stub
		return null;
	}

	public ITransactionManager getTransactionManager(String arg0)
			throws TransactionException {
		// TODO Auto-generated method stub
		return null;
	}

	public void init() throws TransactionException {
		// TODO Auto-generated method stub

	}

	public void setContextManager(IContextManager arg0) {
		// TODO Auto-generated method stub

	}

	public void setDataSourceFactoryManager(IDataSourceFactoryManager arg0) {
		// TODO Auto-generated method stub

	}

	public void setServiceAccessManagerKey(String arg0) {
		// TODO Auto-generated method stub

	}
}
