<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Este jsp es el contenedor de contenido estatico dinamico del alta de una aplicacion y las 
correspondientes asociaciones que se le tienen que hacer a dicha aplicaci&oacute;n.  -->
<html>
<head><!--
  <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
  <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
  
  -->
</head>
<html:form action="/ABMCProveedores">
   <html:hidden name="proveedorForm" property="addGrant"    styleId="addGrant" />
   <html:hidden name="proveedorForm" property="editGrant"   styleId="editGrant" />
   <html:hidden name="proveedorForm" property="deleteGrant" styleId="deleteGrant" />
   <input type="hidden" id="tipoModificacion" value=""/>

   <!-- FILTRO de Proveedores-->
   <fieldset id="proveedoresFilter" class="ui-widget ui-widget-content ui-corner-all">
      <legend id="proveedores_filter_legend" class="ui-widget ui-widget-header ui-corner-all">
			Administraci&oacute;n de Proveedores
      </legend>
	
      <div class="fila" id="proveedor_responseMsgs" style="display: none; overflow: auto;"></div>
		
      <div id="dialogProveedorFilter" title="Consulta" align="center">
         <div class="fila">
            <div class="col_label">Proveedor:</div>
            <div class="col_input">
               <html:select property="filtroProveedorList" name="proveedorForm" styleId="filtroProveedorList" onchange="search();limpiar();"> 
                  <html:option value="0"> Todos los Proveedores &nbsp;</html:option>
	              <html:optionsCollection name="proveedorForm" property="filtroProveedorList" label="desc" value="cod"/> 
               </html:select>
            </div>
         </div>
       </div>
	</fieldset>
    <br>
	<fieldset id="proveedoresSeleccionados" class="ui-widget ui-widget-content ui-corner-all">
      <legend id="proveedoresSeleccionados_filter_legend" class="ui-widget ui-widget-header ui-corner-all">
			Proveedor seleccionado
      </legend>
      <div id="dialogProveedorSeleccionado" title="Consulta" align="center">
         <div class="fila" style="height: 10px;">
            <div class="col_label" style="width: 120px;">C&oacute;digo Proveedor:</div>
            <div class="col_input" style="width: 300px;margin-left: 10px;">
               <input type="text" id="cdProveedorSeleccionado" class="text ui-widget-content ui-corner-all ui-state-disabled" value="" disabled=disabled />
               <input type="text" id="nbProveedorSeleccionado" class="text ui-widget-content ui-corner-all ui-state-disabled" value="" disabled=disabled />
            </div>
         </div>
         <div class="fila" style="height: 10px;">
            <div class="col_label" style="width: 120px;">C&oacute;digo Per&iacute;odo Facturaci&oacute;n:</div>
            <div class="col_input" style="width: 300px;margin-left: 10px;">
               <input type="text" id="cdPeriodoFactSeleccionado" class="text ui-widget-content ui-corner-all ui-state-disabled" value="" disabled=disabled />
               <input type="text" id="nbPeriodoFactSeleccionado" class="text ui-widget-content ui-corner-all ui-state-disabled" value="" disabled=disabled />
            </div>
         </div>
      </div>
	</fieldset>
    <br>
    <div id="tabsApplication"class="ui-widget ui-widget-content ui-corner-all">
       <ul>
          <li><a class = "checkLink" href="ABMCProveedoresBasicos.action">Datos B&aacute;sicos</a></li>
          <li><a class = "checkLink" href="ABMCProveedoresPeriodos.action">Per&iacute;odos de Facturaci&oacute;n</a></li>
          <li><a class = "checkLink" href="ABMCProveedoresValores.action">Valores Monetarios</a></li>
          <li><a class = "checkLink" href="ABMCProveedoresTipoCambio.action">Tipo de Cambio</a></li>
       </ul>
   </div>
</html:form>
<script type="text/javascript">
$(function(){ 
	$( ".checkLink" ).click(function(){
	         
       checkSession();
       
	   }); 
	$('#tabsApplication').tabs();
	$(':button').button();
});
</script>
<script src="js/abmc/proveedores.js"></script>
