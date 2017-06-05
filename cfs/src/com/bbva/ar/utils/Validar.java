/*
 * Clase para validaciones genericas. 
 *
 */

package com.bbva.ar.utils;

public class Validar {

	/**
	 * @author SA
	 * Realiza validacion para saber si una cadena esta vacia o no y reemplazar el ".isEmpty()"
	 * debido a que al compilar con java y ant 1.5.X suele compilar con errores. 
	 * @param cadena
	 * @return
	 */
	
	public static boolean esVacio(String cadena){
		if(cadena == null)
			return true;
		else if(cadena.trim().equalsIgnoreCase(""))
			return true;
		
		return false;		
		
	}
	
	
}
