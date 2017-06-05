package com.bbva.cfs.commons.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import ar.com.bbva.web.IWebClient;

import com.bbva.cfs.commons.exceptions.DaoException;
import com.bbva.cfs.commons.exceptions.GlobalServiceException;
import com.bbva.cfs.commons.logging.Log;
import com.bbva.cfs.commons.model.MenuItem;
import com.bbva.cfs.commons.model.Result;

import com.bbva.cfs.commons.service.CommonLoginService;
import com.bbva.cfs.commons.utils.DatabaseConstants;
import com.bbva.cfs.commons.utils.ResultDatabase;

/**
 * TODO:
 * 
 * @author xah1198
 * 
 */
public class GetMenuService extends CommonLoginService {

	private Map menuCompleto;
	private Long userId;
	private Result result;

	public GetMenuService(IWebClient iWebClient) {
		super(iWebClient);
		this.menuCompleto = new HashMap();
	};

	/**
	 * TODO:
	 * 
	 * @return
	 */
	public Result execute(Long  userId) throws Exception {
		this.menuCompleto.clear();
		this.menuCompleto.put("pi_id_usuario",userId);
		
		try {
			log.debug("Invocación al dao LoginDAO: getMenu()");
			result = getLoginDao().getMenu(this.menuCompleto);
		} catch (DaoException e) {
			result = new Result();
			result.setErrorCode(e.getCode());
			result.setErrorDesc(e.getMessage());

		} catch (Exception e) {
			log.debug(e);
			throw new GlobalServiceException(ResultDatabase.SERVICE_ERROR.getCode(),
								ResultDatabase.SERVICE_ERROR.getMessage());
		}

		return result;
	}

	public Map getMenuCompleto() {
		return menuCompleto;
	}

	public void setMenuCompleto(Map menuCompleto) {
		this.menuCompleto = menuCompleto;
	}

	public String getMenuCompletoHtml() throws Exception {
		String menuCompletoHtml;
		try {
			menuCompletoHtml = "<ul class=\"modulos\">";
	
			Iterator it = ((ArrayList) this.menuCompleto.get("LISTAPADRES"))
					.iterator();
			while (it.hasNext()) {
				MenuItem itemPadre = (MenuItem) it.next();
	
				menuCompletoHtml += "<li><a href=\"" + itemPadre.getUrl() + "\">"
						+ itemPadre.getDescripcion() + "</a><ul class=\"menu\">";
	
				Iterator it2 = ((ArrayList) this.menuCompleto.get("LISTAHIJOS"))
						.iterator();
				while (it2.hasNext()) {
					MenuItem itemHijo = (MenuItem) it2.next();
	
					if (itemHijo.getIdPadre().equals(itemPadre.getId())) {
						menuCompletoHtml += "<li><a href=\"" + itemHijo.getUrl()
								+ "\">" + itemHijo.getDescripcion() + "</a><ul>";
	
						Iterator it3 = ((ArrayList) this.menuCompleto
								.get("LISTANIETOS")).iterator();
						while (it3.hasNext()) {
							MenuItem itemNieto = (MenuItem) it3.next();
	
							if (itemNieto.getIdPadre().equals(itemHijo.getId())) {
								menuCompletoHtml += "<li><a href=\""
										+ itemNieto.getUrl() + "\">"
										+ itemNieto.getDescripcion() + "</a></li>";
							}
						}
	
						if (menuCompletoHtml.endsWith("<ul>")) {
							menuCompletoHtml = menuCompletoHtml.substring(0,
									menuCompletoHtml.length() - 4)
									+ "</li>";
						} else {
							menuCompletoHtml += "</ul></li>";
						}
					}
				}
				if (menuCompletoHtml.endsWith("<ul class=\"menu\">")) {
					menuCompletoHtml = menuCompletoHtml.substring(0,
							//menuCompletoHtml.length() - 14)
							menuCompletoHtml.length() - 17)
							+ "</li>";
				} else {
					menuCompletoHtml += "</ul></li>";
				}
	
			}
			
			menuCompletoHtml += "</ul>";
			
		} catch (Exception e) {
			log.debug(e);
			throw new GlobalServiceException(ResultDatabase.SERVICE_ERROR.getCode(),
								ResultDatabase.SERVICE_ERROR.getMessage());
		}
		
		return menuCompletoHtml;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
