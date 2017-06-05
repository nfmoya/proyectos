package com.bbva.security.service;

import ar.com.bbva.web.IWebClient;

import com.bbva.security.model.AuthenticatedUser;
import com.bbva.security.utils.AuthenticationUtils;

/**
 * Crear o modificar passwords...
 * @author xah1198
 *
 */
public class EditPasswordService extends CommonAuthUserService {
	private String validateMesssage;
	

	public EditPasswordService(IWebClient iWebClient) {
		super(iWebClient);
	};
	
	/**
	 * TODO:
	 * @return
	 */
	public boolean update(AuthenticatedUser user, String newPassword) {
		try {					
			String encryptedPassword = AuthenticationUtils.encryptPassword(newPassword);
			if(isUsedPassword(user, encryptedPassword)){
				setValidateMesssage("ESTA CLAVE YA FUE UTILIZADA.");
				return false;
			} else {/*TODO: FIJARME DE CAMBIAR ESTO...*/
				user.setPassword(user.getPassword1());
			}
			if(!AuthenticationUtils.validatePassword(newPassword)){
				setValidateMesssage("COMPOSICION DE NUEVA CLAVE ERRONEA.");
				return false;
			}
			
			log.debug("Invocación al dao AuthenticateUserDao: editPassword()");
			getAuthenticateUserDao().editPassword(user);
			
			/*TODO: Agregar manejo de validaciones seguramente, revisar!!!!!!!*/
			
			setValidateMesssage("CLAVE ACTUALIZADA.");
			return true;
		} catch (Exception e) {
			log.error(e);
			setValidateMesssage("NO SE PUDO ACTUALIZAR LA CLAVE.");
			return false;
		}
	}
	
	/**
	 * En el caso de que no se usado pone al password nuevo como password1 y este pasa a ser el password actual
	 * @param user
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public boolean isUsedPassword(AuthenticatedUser user, String encryptedPassword) throws Exception {
		if(user.getPassword1().equals(encryptedPassword) ||
			user.getPassword2().equals(encryptedPassword) ||
			user.getPassword3().equals(encryptedPassword) ||
			user.getPassword4().equals(encryptedPassword)){
			return true;
		} else {
			user.setPassword4(user.getPassword3());
			user.setPassword3(user.getPassword2());
			user.setPassword2(user.getPassword1());
			user.setPassword1(encryptedPassword);
		}
		
		return false;
    }

	public String getValidateMesssage() {
		return validateMesssage;
	}
	
	public void setValidateMesssage(String validateMesssage) {
		this.validateMesssage = validateMesssage;
	}
}

