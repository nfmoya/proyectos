<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean2"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic2"%>


<html>

  <logic2:equal  name="deSumit" value="true" >
	<html:hidden  property=" " styleId="totalImportadosCorrec"  value='<%=request.getAttribute("totalImportadosCorrec").toString().toUpperCase() %>'></html:hidden>
	<html:hidden  property=" " styleId="codError"  value='<%=request.getAttribute("codError").toString().toUpperCase() %>'></html:hidden>
	<html:hidden  property=" " styleId="errors"  value='<%=request.getAttribute("errors").toString().toUpperCase() %>'></html:hidden>
  </logic2:equal> 

	<html:form styleId="importForm"   action="/importarServPrestTest" method="post" enctype="multipart/form-data">

		<fieldset id="conciliacionesFilter" class="ui-widget ui-widget-content ui-corner-all">

			<legend id="conciliaciones_filter_legend" class="ui-widget ui-widget-header ui-corner-all">
				Importaci&oacute;n de Servicios Prestados a conciliar </legend>
			
		    <div class="fila" id="conciliacion_responseMsgs" style="display: none; overflow: auto;"></div>

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
		<br>
		
		<fieldset id="conciliacionesFilter1" class="ui-widget ui-widget-content ui-corner-all">
					<legend class="ui-widget ui-widget-header ui-corner-all">
				Archivo&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </legend>
				
				<div  class="col_input" style="width: 300px; margin-left: 10px;">
	       			<html:file  styleId="urlArchivo" property="lotesFile" size="50" styleClass="input_medium text ui-widget-content ui-corner-all"/>
	        	</div>
				<br>
		</fieldset>

		<html:button property="button" styleId="aceptarButton" >Aceptar</html:button>
    </html:form>
</html>

<script src="js/importacion/importarServPrest.js"></script>
<script>
	function enviar(){
		alert($('#jjjj').val());
	}
</script>
