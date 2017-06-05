package com.bbva.security.service;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ar.com.bbva.web.IWebClient;

import com.bbva.security.model.AuthenticatedUser;
import com.bbva.security.utils.AuthenticationUtils;

/**
 * TODO:
 * @author xah1198
 *
 */
public class GetAuthenticatedUserService extends CommonAuthUserService {
	
	private AuthenticatedUser user;
	private static final Long EXPIRED_TIME = Long.valueOf("180");
	private IWebClient iWebClient;
	
	SaveAuthenticatedUserService updateAuthUserService;
	
	public GetAuthenticatedUserService(IWebClient iWebClient) {
		super(iWebClient);
		this.iWebClient = iWebClient;
		user = new AuthenticatedUser();
	};
	
	/**
	 * TODO:
	 * @return
	 */
	public void init(String username) throws Exception {
		user.setUsername(username);
		try {					
			log.debug("Invocaci�n al dao UserDAO: getUserData()");
			getAuthenticateUserDao().getAuthenticatedUserData(user);
			
		} catch (Exception e) {
			user.setPassword(null);
			log.error(e);
			throw new Exception();
		}
	}
	
	private void updateAuthenticateUser() throws Exception {
		try {					
			log.debug("Invocaci�n al dao UserDAO: getUserData()");
			getAuthenticateUserDao().updateAuthenticatedUserData(this.user);
		} catch (Exception e) {
			/*TODO: ESTA REPITIENDOSE SIN SENTIDO... REVISAR*/
			log.error(e);
			throw new Exception();
		}
	}

	public AuthenticatedUser getUser() {
		return user;
	}

	public void setUser(AuthenticatedUser user) {
		this.user = user;
	}
	
	public boolean existUser(){
		if (user.getPassword() != null){
			return true;
		}
		return false;
	}
	
	public boolean isFirstTime(){
		if (user.getStatus().equals("I")){
			return true;
		}
		return false;
	}
	
	/**
	 * TODO: Aqui mismo se podria agregar algun otro tipo de expiracion.
	 * @return
	 */
	public boolean isExpiredPassword(){
		try{
			//DateFormat df = new SimpleDateFormat("yyyymmdd");
			//Date dateLastAccess = df.parse(user.getLastAccess());
			Long lastAccess = Long.valueOf(user.getLastAccess());
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, EXPIRED_TIME.intValue() * -1);
			String fecha = cal.get(Calendar.YEAR) +
					   ((cal.get(Calendar.MONTH) + 1)>=10?""+(cal.get(Calendar.MONTH) + 1):"0"+(cal.get(Calendar.MONTH) + 1)) +
					   ((cal.get(Calendar.DAY_OF_MONTH))>=10?""+(cal.get(Calendar.DAY_OF_MONTH)):"0"+(cal.get(Calendar.DAY_OF_MONTH)));
			
	    	/*cal.add(Calendar.DATE, EXPIRED_TIME.intValue() * -1);
	    	String cadena ="00"+(cal.get(Calendar.MONTH)+1);
	    	String mes =cadena.substring(cadena.length()-2,3);
	    	       cadena ="00"+(cal.get(Calendar.DAY_OF_MONTH)+1);
	    	String dia =cadena.substring(cadena.length()-2,3);
	    	String fecha =(""+ cal.get(Calendar.YEAR))+mes +dia;*/
			
			//DateFormat df1 = new SimpleDateFormat("yyyymmdd");
	    	//Date today = df1.parse(fecha);
			
			Long today = Long.valueOf(fecha);
	    	
	    	//if (!dateLastAccess.before(today)){
			if(today.compareTo(lastAccess) > 0){
		      return true;
			}
			
		} catch(Exception e){
		}

		return false;
	}
	
	/**
	 * Si el usuario esta habilitado y su estado no es el inicial, sino normal.
	 * @return
	 */
	public boolean isEnabledUser(){
		if(user.getEnabledUser().trim().equals("S")   ){
//				&& user.getStatus().equals("N")){
				
			return true;
		}		
		return false;
	}
	
	public boolean isValidPassword(String reportedPassword) throws Exception {
    	// Compara la grabada con la nueva encriptada
	    if(user.getPassword().trim().equals(AuthenticationUtils.encryptPassword(reportedPassword))){
	    	user.setFailedAttempts(0);
	    	this.updateAuthenticateUser();//this.modificar();
	    	return true;
	    }
	    
	    // Si llego hasta aca es porque fallo la comparacion de arriba
	    // le suma un intento cuando falla
	    user.setFailedAttempts(user.getFailedAttempts()+1);
	    if (user.getFailedAttempts() > 2){
	    	user.setStatus("B"); // bloqueado por 3 intentos
	    }
	    this.updateAuthenticateUser();
	    	
	    return false ;
    }
	
}

