
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean2"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%-- <%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %> --%>
<jsp:useBean id="cons" class="com.gefa.utils.Constants" scope="session"/>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="template"%>
<%@ page import="com.gefa.utils.Constants" %>

<!DOCTYPE html>
<html lang="en">
    <script src="js/custom/mensajes.js"></script>
 
<script type="text/javascript">
$().ready(function() {
	// Selecciona la pestaña activa
	$('#excelLoadLink').addClass( "active" );
	$(":file").filestyle({buttonName: "btn-default"});
	$( ".alert" ).each(function( index ) {
		 $( this ).hide();
		});
	recorrerMensajes();
	});
</script>
  <body>

    
      <div  style="padding: 10px 5px; text-align: center;" class="starter-template">
        <h1>Carga de archivos de Facturaci&oacute;n</h1>
<!--         <p class="lead">Aca se va a cargar un archivo. -->
        <br> 
      </div>
           <div  style="padding: 10px 5px;padding-left: 20%" id="tablaCarga"> 
            <html:form action="excelLoad" styleId="excelLoad" enctype="multipart/form-data">
       
        	
			<div class="row">
        <div class="col-md-2">
          <div>
          </div>
        </div>
        
        <div class="col-md-8">
          <div>  
    		<div id="alertSuccess" class="alert alert-success alert-dismissible" role="alert"><strong>Éxito!</strong></div>
			<div id="alertInfo" class="alert alert-info alert-dismissible" role="alert"> <strong>Info</strong></div>
			<div id="alertWarning" class="alert alert-warning  alert-dismissible" role="alert"><strong>Advertencia</strong></div>
			<div id="alertDanger"class="alert alert-danger alert-dismissible" role="alert"><strong>Oh Rayos</strong></div> </div>           
          </div>
        
        <div class="col-md-2">
          <div>
            
          </div>
        </div>
        
      </div>  
     <div class="row">
        <div class="col-md-2">
          <div>
            
          </div>
        </div>
        
        <div class="col-md-8">
          <div>
          
        <div style="display: none;" >
        
        <logic:messagesPresent  property="warnings">
	        <ul id="warningsList" class="conjuntoError" >  
	            <html:messages id="warning" property="warnings">  
	                <li ><bean2:write name="warning"/></li>  
	            </html:messages>  
	        </ul>
	        
   	 </logic:messagesPresent>  
        <logic:messagesPresent property="errors">
	        <ul id="erroresList" class="conjuntoError" >  
	            <html:messages id="error" property="errors">  
	                <li ><bean2:write name="error"/></li>  
	            </html:messages>  
	        </ul>
	        
   	 </logic:messagesPresent>  
        <logic:messagesPresent message="true">
        
	    <html:messages id="aMsg" message="true" >
	        <logic:present name="aMsg">
	            <!-- Messages -->
	        <ul id="mensajesList" > 
	               <li > <bean2:write name="aMsg" filter="false" />
	            </li> 
	        </ul>
	        </logic:present>
	    </html:messages>
	    
	</logic:messagesPresent>
        </div>  
          <div style="width: 30%; float: left;" ><label for="tipoArchivo">Seleccione Tipo de Archivo </label> </div>
            	<div style="width: 70%; float: right;text-align: left;">
             	<html:select styleId="tipoArchivo"   property="altaModif" styleClass="form-control" style="width: 300px; margin-left: 10px;">
            	          	<option value="<%=Constants.getFileTypeNew()  %>" >Alta</option>
            	          	<option value="<%=Constants.getFileTypeMod()  %>" >Modificaci&oacute;n</option>
            	</html:select>
           		</div>
           </div>
        </div>
        <div class="col-md-2">
          <div>
          </div>
        </div>
      </div>
     <div class="row">
        <div class="col-md-2">
          <div>
          <br>
          </div>
        </div>
        
        <div class="col-md-8">
          <div>       
            	<br>
          <div style="width: 30%; float: left;" >
          	<label for="urlArchivo" style="text-align: left;">Seleccione Archivo</label> 
           </div>
            <div style="width: 70%; float: right;">
        		 <div  class="col_input" style="width: 300px; margin-left: 10px;">
	       			<html:file  
 					 styleId="urlArchivo"  property="lotesFile"  size="50"/>
	        	</div>           
          </div>
	        	           
          </div>
        </div>
        
        <div class="col-md-2">
          <div>
            
          <br>
          </div>
        </div>
        
      </div>
     <div class="row">
        <div class="col-md-2">
          <div>
            
          <br>
          </div>
        </div>
        
        <div class="col-md-8">
          <div>
            	<br>
				<html:submit style="float: center;" styleClass="btn btn-primary" styleId="aceptarButton" >Aceptar</html:submit>     
          </div>
        </div>
        
        <div class="col-md-2">
          <div>
            
          <br>
          </div>
        </div>
        
      </div>
          </html:form>
          </div>
   </body>
</html>

