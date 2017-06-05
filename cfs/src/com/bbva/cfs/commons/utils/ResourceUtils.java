package com.bbva.cfs.commons.utils;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Properties;

import ar.com.itrsa.GeneralRuntimeException;

import com.bbva.cfs.commons.exceptions.GeneralException;

public class ResourceUtils {

	public static final String CFG_DIRECTORY = "cfg";
	
	public static final String SYS_CFG_DIRECTORY = "syscfg";

	public static final String DIR_RELATIVO = "/..";

	private static String realPath;

	public static void setRealPath(String path) {
		realPath = path;
	} 
 
	public static URL getRealPathResourceURL(String resource, String confDirectory) throws GeneralException {

		if (!resource.startsWith("/")) {
			resource = "/" + resource;
		}

		if (!confDirectory.startsWith("/")) {
			confDirectory = "/" + confDirectory;			
		}

		if (confDirectory.endsWith("/")) {
			confDirectory = confDirectory.substring(0, confDirectory.length() - 1);
		}

		URL url = null;
		try {
			url = new URL("file", "", -1, realPath + DIR_RELATIVO + confDirectory + resource);
		} catch (MalformedURLException exc) {
			throw new GeneralException("El recurso " + confDirectory + resource + " no existe.", exc);
		}
		
		return url;		
	}

	public static InputStream getRealPathResourceStream(String resource, String confDirectory) throws GeneralException {

		if (!resource.startsWith("/")) {
			resource = "/" + resource;
		}

		if (!confDirectory.startsWith("/")) {
			confDirectory = "/" + confDirectory;			
		}

		if (confDirectory.endsWith("/")) {
			confDirectory = confDirectory.substring(0, confDirectory.length() - 1);
		}

		try {
	        return new FileInputStream(realPath + DIR_RELATIVO + confDirectory + resource);
		} catch (IOException exc) {
			throw new GeneralException("El recurso : " + confDirectory + resource + " no existe y no puede ser leido.", exc);
		}
	}


    public static URL getResourceURL(String resource) throws GeneralException {

        ClassLoader contextClassLoader =
            (
                ClassLoader) AccessController
                    .doPrivileged(new PrivilegedAction() {
            public Object run() {
                return getContextClassLoader();
            }
        });

        URL url =
            (contextClassLoader == null
                ? ClassLoader.getSystemResource(resource)
                : contextClassLoader.getResource(resource));

        return url;
    }

    /**
     * Obtiene un recurso como un InputStream.
     * 
     * @param resource el nombre del recurso.
     */
    public static InputStream getConfigResource(String resource)
        throws GeneralException {

        ClassLoader contextClassLoader =
            (
                ClassLoader) AccessController
                    .doPrivileged(new PrivilegedAction() {
            public Object run() {
                return getContextClassLoader();
            }
        });


        InputStream stream =
            (contextClassLoader == null
                ? ClassLoader.getSystemResourceAsStream(resource)
                : contextClassLoader.getResourceAsStream(resource));

        return stream;
    }

    /**
     * Retorna el context class loader asociado con el thread
     * si el mismo esta disponible.
     * 
     * A los threads se les puede asignar context class loaders
     * a partir de la versión 1.2 de JDK, si es que se cumplen
     * ciertos requisitos de serguridad.
     * 
     * @exception GeneralRuntimeException si no se puede obtener
     *             el class loader..
     */
    protected static ClassLoader getContextClassLoader()
        throws GeneralRuntimeException {

        ClassLoader classLoader = null;

        try {
            // Estamos corriendo en JDK1.2???
            Method method =
                Thread.class.getMethod("getContextClassLoader", null);

            // Obtengo el context class loader del thread.
            try {
                classLoader =
                    (ClassLoader) method.invoke(Thread.currentThread(), null);
            } catch (IllegalAccessException e) {
                throw new GeneralRuntimeException(
                    "Unexpected IllegalAccessException",
                    e);
            } catch (InvocationTargetException e) {

                /**
                 * Una excepcion de tipo InvocationTargetException es 
                 * arrojada por el método 'invoke' cuando el método 
                 * invocado arroja una excepcion.
                 * 
                 * getContextClassLoader() arroja una SecurityException
                 * cuando el context class loader no es un ancestro
                 * del class loader de la clase que la llama, o si
                 * no hay permisos para ejecuta la operación.
                 */
                if (e.getTargetException() instanceof SecurityException) {
                    ; // ignorar
                } else {
                    // Capturo 'e.getTargetException()' para detalles.
                    throw new GeneralRuntimeException(
                        "Unexpected InvocationTargetException",
                        e.getTargetException());
                }
            }
        } catch (NoSuchMethodException e) {
            // Asumo que estoy corriendo JDK 1.1
            classLoader = ResourceUtils.class.getClassLoader();
        }

        // Retorno el class loader seleccionado.
        return classLoader;
    }


	public static String formatNumberStr(String str, int dec) {
		if (str != null) {

			int aux = (int)Math.pow(10.0, dec);
			
			double value = 0.0;
			
			try {
				value = Double.parseDouble(str);
			} catch (Exception e) {
				return str;
			}

			value = Math.round(value * aux);
			
			str = String.valueOf(value / aux);
		}
		return str;
	}

	public static String eliminarCerosPrefijo(String str) {

		boolean completo = false;
		
		String aux = str;
		
		if (aux != null) {
		
			for (int i = 0; i < str.length() && !completo; i++) {
				if (str.charAt(i) == '0') {
					if (i == str.length() - 1) {
						aux = "";
					} else {
						aux = aux.substring(1); 
					}
				} else {
					completo = true;
				}
			}

		}
				
		return aux;
	}

	public static String clearInvalidCharacters(String str) {

		char[] invalidChars = {0x0009, 0x000A, 0x000C, 0x000D, 0x0020};

		String aux = "";

		boolean found;
		
		for (int i = 0 ; i < str.length(); i++) {
			char cc = str.charAt(i);
		
			found = false;
			
			for (int j = 0; j < invalidChars.length && !found; j++) {
				if (cc == invalidChars[j]) {
					found = true;
				}
			}
			if (!found) {
				aux = aux + cc;
			} else {
				aux = aux + ' ';
			}
		}
		
		return aux;
	}


	public static String formatNumericValue(String num, int dec) {
		if ((num != null) && (num.length() > 0)) {

			String strConverter = "";
	
			int indexPoint = num.indexOf('.');

			String decimalStr = "";
			
			if (indexPoint == -1) {
				
				strConverter = insertarSeparadorMiles(num);
				
			} else {
				
				strConverter = insertarSeparadorMiles(num.substring(0, indexPoint));
				
				decimalStr = num.substring(indexPoint + 1, num.length());
				
			}
			while (decimalStr.length() < dec) {
				decimalStr += "0";
			}
			strConverter += "," + decimalStr;
			
			return strConverter;
		}
		
		return num;
	}


	private static String insertarSeparadorMiles(String str) {

		if (str.length() <= 3) {
			return str;
		} else {
			return insertarSeparadorMiles(str.substring(0, str.length() - 3)) + "." + str.substring(str.length() - 3);
		}
	}

	public static String obtenerDirectorioTMP(String str) 
	{
		String retorno = "";
		Properties prop = new Properties();
		
		
		try 
		{
			prop.load(
					ResourceUtils.getRealPathResourceStream("pool-config/entorno.properties",
					ResourceUtils.SYS_CFG_DIRECTORY));
			
			retorno = prop.getProperty(str).trim();
			
			if (!"/".equals("" + retorno.charAt(retorno.length()-1)) )
				retorno += "/";		
		}
		catch (FileNotFoundException e) 
		{
			retorno = "/tmp";
		} catch (IOException e) {
			retorno = "/tmp";
		} catch (Exception e) {
			retorno = "tmp";			
		}
		finally 
		{
			return retorno;
		}		
	}


}
