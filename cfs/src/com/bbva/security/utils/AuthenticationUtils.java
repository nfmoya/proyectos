package com.bbva.security.utils;

import java.security.MessageDigest;

import org.apache.commons.lang.StringUtils;

public class AuthenticationUtils {
	
	public static boolean validatePassword(String reportedPassword){
		boolean hayNumero = false;
		boolean hayLetra  = false;
		boolean hayMayus  = false;
		boolean hayCuatroIg =false;
		boolean hayEspeciales =false;
		boolean haySecNumerica =false;
		
		try{
			//controla 8 de largo
			if (reportedPassword.length()!= 8){
				return false;// no es clave valida no tiene 8
			}
			// controla secuencia numerica
			if (reportedPassword.indexOf("12345") > 0){  
				haySecNumerica= true;
			}
			for (int i = 0; i < reportedPassword.length(); i++) {      		
				// Tiene por lo menos un mumero
				if (!hayNumero){
					if ("0123456789".indexOf(reportedPassword.substring(i,i+1))>=0){ 
						hayNumero=true;
					}
				}
				//  Tiene por lo menos una letra
				if (!hayLetra){
					if ("ABCDEFGHIJKLMÑNOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz".indexOf(reportedPassword.substring(i,i+1))>=0){
						hayLetra=true;
					}
				}
				//  Tiene por lo menos una letra mayuscula
				if (!hayMayus){
					if ("ABCDEFGHIJKLMÑNOPQRSTUVWXYZ".indexOf(reportedPassword.substring(i,i+1))>=0){
						hayMayus=true;
					}
				}
				//  Tiene por lo menos un caract especial
				if (!hayEspeciales){
					if ("!#$%&@*¡".indexOf(reportedPassword.substring(i,i+1))>=0){
						hayEspeciales=true;
					}
				}
				// valida que nos sean 4 caracteres iguales 
				String letra=String.valueOf(reportedPassword.charAt(i));
				String reportedPassword2 = letra.concat(letra).concat(letra).concat(letra);
				
				if (reportedPassword.indexOf(reportedPassword2)>=0){
					hayCuatroIg= true;
				}
				/*TODO: VERIFICAR SI ES REALMENTE ASI
				 * String reportedPassword2=StringUtils.fillChar(letra,4);
				if (StringUtils.inStr(reportedPassword2.toUpperCase(),reportedPassword.toUpperCase())>= 0){  
					hayCuatroIg= true;
				}*/
				
			}
			
			if (hayLetra && hayMayus && hayNumero && !hayCuatroIg && hayEspeciales && !haySecNumerica){
				return true; // es valida
			}
		} catch(Exception e){
		}
		
		return false;
	}
	
	public static String encryptPassword(String pass) throws Exception {
    	// Solo encripta cuando las passw tiene algo , sino devuelve blanco
    	String password = pass;
	    String algorithm = "SHA";
	    if (pass.trim().equals("")){
	    	return "";
	    }
	    byte[] plainText = password.getBytes();

	    MessageDigest md = MessageDigest.getInstance(algorithm);

	    md.reset();
	    md.update(plainText);
	    byte[] encodedPassword = md.digest();

	    //StringBuilder sb = new StringBuilder();
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < encodedPassword.length; i++) {
	      if ((encodedPassword[i] & 0xff) < 0x10) {
	        sb.append("0");
	      }
	      sb.append(Long.toString(encodedPassword[i] & 0xff, 16));
	    }
	    // return la nueva encriptada
	    return sb.toString().trim();	
    }

}
