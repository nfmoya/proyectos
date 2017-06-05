package com.gefa.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 * @author FGramajo
 * Clase de ejemplo que recibe un XML y lo parsea geteando el item que se quiere.
 *
 */
public class DOMProcessXML {
	
	public static void main(String args[]) {
		try {

			File stocks = new File("example.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(stocks);
		
			doc.getDocumentElement().normalize();

			System.out.println("root of xml file"
					+ doc.getDocumentElement().getNodeName());
			NodeList nodes = doc.getElementsByTagName("SOAP-ENV:Envelope");
			System.out.println("==========================");

			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					String string = getValue("item", element).toString();
					String[] array = string.split(";");
					String token = array[0];
					String aplicacion = array[1];
					System.out.println("token: "
							+ token);
					System.out.println("aplicacion: "
							+ aplicacion);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static Boolean exec(String arg) {
		String path= " ";
		
		try {
			 path = ConfigurationFiles.get().getPathProperties();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File file = new File(path +"xmlString.xml");
		Boolean retorno= new Boolean(Boolean.FALSE);
		try {

            // if file doesnt exists, then create it
            if (!file.exists()) {
            	file.delete();
                    file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(arg);
            bw.close();
            
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();

			System.out.println("root of xml file"
					+ doc.getDocumentElement().getNodeName());
			NodeList nodes = doc.getElementsByTagName("SOAP-ENV:Envelope");
			System.out.println("==========================");

			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					String result = getValue("item", element).toString();
					System.out.println("*******"+result +"***********");
//					String[] array = string.split(";");
//					String token = array[0];
//					String aplicacion = array[1];
//					System.out.println("token: "
//							+ token);
//					System.out.println("aplicacion: "
//							+ aplicacion);
					
					
					if(!result.contains("RR99")){
						
						retorno = Boolean.TRUE;
						
					}
					
					
				}
				
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally{
        	file.delete();
      
		}
	  	return retorno;
	}
	private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0)
				.getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}
}
