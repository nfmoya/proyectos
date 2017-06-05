package com.gefa.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.soap.*;
import javax.xml.transform.stream.*;
/**
 * Clase de ejemplo de cliente SOAP para llamado a un webService por post. 
 * En este caso llama a un ws php. Deberia ser indistinto el codigo de ws. 
 * @author FSW-61
 *
 */
public class TokenSOAPClient {

//    public static void main(String args[]) throws Exception {
//        // Create SOAP Connection
//        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
//        SOAPConnection soapConnection = soapConnectionFactory.createConnection();
//
//        // Send SOAP Message to SOAP Server
//        String url = "http://cchhgefatesting.sistemasactivos.com/index.php/servidorToken_nusoap";
//        SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), url);
//
//        // print SOAP Response
//        System.out.print("Response SOAP Message:");
//        
//        soapResponse.writeTo(System.out);
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        
//		soapResponse.writeTo(out);
//		System.out.println();
//		System.out.println(out.toString());
//		DOMProcessXML.exec(out.toString().trim());
//        soapConnection.close();
//    }
    
    public static Boolean authenticate(String url , String serverURI,String token) throws Exception {
        // Create SOAP Connection
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();

        // Send SOAP Message to SOAP Server
//        String url = "http://cchhgefatesting.sistemasactivos.com/index.php/servidorToken_nusoap";
        System.out.println("********Token : "+token);
        SOAPMessage message = createSOAPRequest(serverURI,token);
        SOAPMessage soapResponse = soapConnection.call(message, url);

        // print SOAP Response
        System.out.print("Response SOAP Message:");
        
        soapResponse.writeTo(System.out);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
		soapResponse.writeTo(out);
		System.out.println();
		System.out.println(out.toString());
		Boolean boolean1 = DOMProcessXML.exec(out.toString().trim());
		System.out.println("------------------------------ "+ boolean1);
        soapConnection.close();
        return boolean1;
    }

    private static SOAPMessage createSOAPRequest(String serverURI,String tokenId) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

//        String serverURI = "http://cchhgefatesting.sistemasactivos.com";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
//        envelope.addNamespaceDeclaration("servicio", serverURI);

        /*
        SOAP Request Message:
       <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
			<SOAP-ENV:Header />
			<SOAP-ENV:Body>
				<servidorToken_nusoap..obtenerToken>
					<token>eed173608526c0119fca4c47d8505247</token>
					<aplicacion>gefa-java</aplicacion>
				</servidorToken_nusoap..obtenerToken>
			</SOAP-ENV:Body>
		</SOAP-ENV:Envelope>
         */

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
//        cargo el metodo a ejecutar
        SOAPElement obtenerToken = soapBody.addChildElement("servidorToken_nusoap..obtenerToken");
//        elementos del metodo
        SOAPElement token = obtenerToken.addChildElement("token");
        SOAPElement aplicacion = obtenerToken.addChildElement("aplicacion");
        token.addTextNode(tokenId);
        aplicacion.addTextNode("gefa-java");

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", serverURI  + "obtenerToken");

        soapMessage.saveChanges();

        /* Print the request message */
        System.out.print("Request SOAP Message:");
        OutputStream out;
        soapMessage.writeTo(System.out);
        System.out.println();

        return soapMessage;
    }

}