
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%-- <%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %> --%>
<jsp:useBean id="cons" class="com.gefa.utils.Constants" scope="session"/>
<%@ page import="com.gefa.utils.Constants" %>

<!DOCTYPE html>
<html lang="en">
 
<script type="text/javascript">
$().ready(function() {
	// Selecciona la pestaña activa
	console.log("Entra al llegar");
	$('#excelViewLink').addClass( "active" );
	});
</script>
  <body>

    
  

      <div style="padding: 10px 5px; text-align: center;" class="starter-template">
        <h1>Registro de Carga de Archivos de Facturaci&oacute;n</h1>
<!--         <p class="lead">Aca se va a cargar un archivo. -->
        <br> </p>
      </div>
           <div class="panel panel-default">
  <!-- Default panel contents -->
  <div class="panel-heading">Consulta</div>
  <div class="table-responsive">
           <display:table uid="rcList" class="table table-striped" name="registroList" requestURI="registroDeCarga.do" pagesize="7"
								 cellspacing="0" cellpadding="0" >
					
					<display:column style="width: 40%" property="nombreArchivo" title="Nombre Archivo" sortable="true" sortName="nombreArchivo" />
					<display:column style="width: 20%" property="fechaAlta" title="Fecha de Alta" sortable="true" sortName="fechaAlta" />
					<display:column style="width: 20%" property="cantidadRegistros" title="Cantidad de Registros" sortable="true" sortName="cantidadRegistros" />
					
<%-- 					<display:column style="width: 5%"> --%>
<%-- 						<a href="#" onclick="popUp(1, <bean:write name="wlReg" property="id" />);" id="linkAltaWhite"> --%>
<!-- 							<img src="images/edit.png" /> -->
<!-- 						</a> -->
<%-- 					</display:column> --%>
<%-- 					<display:column style="width: 5%"> --%>
<%-- 						<html:link action="/bajaWhiteList" paramId="id" paramName="wlReg" paramProperty="id" --%>
<%-- 									onclick="return deletes();" styleId="linkAltaWhite"> --%>
<!-- 							<img src="images/delete.png" /> -->
<%-- 						</html:link> --%>
<%-- 					</display:column> --%>
				</display:table>
           </div>
           </div>
            
           

  </body>
</html>
