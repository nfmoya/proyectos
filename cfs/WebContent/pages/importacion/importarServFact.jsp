<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean2"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic2"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<logic2:equal  name="deSumit" value="true" >
	<html:hidden  property=" " styleId="totalImportadosCorrec"  value='<%=request.getAttribute("totalImportadosCorrec").toString().toUpperCase() %>'></html:hidden>
	<html:hidden  property=" " styleId="codError"  value='<%=request.getAttribute("codError").toString().toUpperCase() %>'></html:hidden>
	<html:hidden  property=" " styleId="errors"  value='<%=request.getAttribute("errors").toString().toUpperCase() %>'></html:hidden>
  </logic2:equal> 
	<html:form styleId="importForm"   action="/importFacturasTest" method="post" enctype="multipart/form-data">

		<fieldset id="conciliacionesFilter" class="ui-widget ui-widget-content ui-corner-all">

			<legend id="conciliaciones_filter_legend" class="ui-widget ui-widget-header ui-corner-all">
				Importaci&oacute;n de Servicios Facturados a conciliar </legend>
			
		    <div class="fila" id="conciliacion_responseMsgs" style="display: none; overflow: auto;"></div>
<br>
			<div id="dialogConciliacionFilter" title="Consulta" align="center">
				<div class="fila" style="height: 15px;">
					<div class="col_label" style="width: 160px;">Proveedor:</div>
					<div class="col_input" style="width: 300px; margin-left: 10px;">
						<html:select property="proveedorSelected" name="importForm" styleId="filtroProveedorList">
							<html:option value="0"> Sin Proveedor&nbsp;</html:option>
							<html:optionsCollection name="proveedores" label="desc" value="cod" />
						</html:select>
					</div>
				</div> 
				
				<div class="fila" style="height: 15px;">
					<div class="col_label" style="width: 160px;">Observaciones:</div>
					<div class="col_input" style="width: 300px; margin-left: 10px;">
             
			
						<html:textarea property="observaciones" styleId="observaciones"  cols="60" rows="1" styleClass="input_medium text ui-widget-content ui-corner-all"   ></html:textarea>
						</div>
				</div>
			</div>
		</fieldset>
<%-- 	</html:form> --%>
		<br>
		
<%-- 	<html:form method="POST" enctype="multipart/form-data" action="/importFacturas"> --%>
		<fieldset id="conciliacionesFilter1" class="ui-widget ui-widget-content ui-corner-all">
					<legend class="ui-widget ui-widget-header ui-corner-all">
				Archivo&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </legend>
				<br>
				<div  class="col_input" style="width: 300px; margin-left: 10px;">
	       			<html:file  styleId="urlArchivo" property="lotesFile" size="50" styleClass="input_medium text ui-widget-content ui-corner-all"/>
	        	</div>
				<br>
				<br>
				<%--
				<div class="col_input" style="width: 300px; margin-left: 10px;">
	       			<html:file property="lotesFilePath" size="50" styleClass="input_medium text ui-widget-content ui-corner-all"/>
	        	</div>
				--%>
		</fieldset>
<br>
<%-- 			<html:submit>Aceptar</html:submit> --%>
		<html:button property="button" styleId="aceptarButton" >Aceptar</html:button>
    </html:form>
</html>

<script src="js/importacion/importarServFact.js"></script>
<script>
	function enviar(){
// 		$('#path').val($('#jjjj').val());
		alert($('#jjjj').val());
	}
</script>
