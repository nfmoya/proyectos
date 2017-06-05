/**
 * 
 */
package com.bbva.security.dao;

import ar.com.bbva.web.IWebClient;
import ar.com.bbva.web.impl.SAMWebClient;

import com.bbva.security.dao.AuthenticateUserDao;
import com.bbva.security.dao.impl.AuthenticateUserDaoImpl;

/**
 * @author xah1198
 * 
 *         Dao Factory crea varios DAOs
 */
public class DaoFactoryManager extends DaoFactory {
	
	

	private static DaoFactoryManager instance;

	private static SAMWebClient samWebClient;

	/**
	 * @param iWebClient
	 *            : obligamos a que reciba este parámetro para poder inicializar
	 *            sam.
	 * @return una instancia unica del objeto <code>DaoFactoryManager</code>
	 */
	public static DaoFactoryManager getInstance(IWebClient iWebClient) {
		if (instance == null) {
			instance = new DaoFactoryManager();
		}
		samWebClient = (SAMWebClient) iWebClient;
		return instance;
	}

	public AuthenticateUserDao getAuthenticateUserDao() {
		return new AuthenticateUserDaoImpl(samWebClient);
	}
}
