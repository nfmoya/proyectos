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
<html:form action="/ABMCProductos">
   <html:hidden name="productoForm" property="addGrant"    styleId="addGrant" />
   <html:hidden name="productoForm" property="editGrant"   styleId="editGrant" />
   <html:hidden name="productoForm" property="deleteGrant" styleId="deleteGrant" />
   <input type="hidden" id="cdProveedorSeleccionado" value=""/>
   <input type="hidden" id="tipoModificacion" value=""/>
   <input type="hidden" id="productoPeriodoSeleccionado" value=""/>   
   <input type="hidden" id="productoTipVal" value=""/>   
   <input type="hidden" id="tarifaCantDesde" value=""/> 
   <input type="hidden" id="tarifaFhHasta" value=""/> 

   <!-- FILTRO de Productos-->
   <fieldset id="productosFilter" class="ui-widget ui-widget-content ui-corner-all">
      <legend id="productos_filter_legend" class="ui-widget ui-widget-header ui-corner-all">
			Administraci&oacute;n de Productos
      </legend>
	
      <div class="fila" id="producto_responseMsgs" style="display: none; overflow: auto;"></div>
		
      <div id="dialogProductoFilter" title="Consulta" align="center">
         <div class="fila" style="height: 10px;">
            <div class="col_label" style="width: 130px;">Proveedor:</div>
            <div class="col_input" style="width: 300px;margin-left: 10px;">
               <html:select property="filtroProveedorList" name="productoForm" styleId="filtroProveedorList"  onchange="checkSession();search();limpiar(1);"> 
                  <html:option value="0"> Todos los Proveedores &nbsp;</html:option>
	              <html:optionsCollection name="productoForm" property="filtroProveedorList" label="desc" value="cod"/> 
               </html:select>
            </div>
         </div>
         <div class="fila" style="height: 10px;">
            <div class="col_label" style="width: 130px;">Producto:</div>
            <div class="col_input" style="width: 300px;margin-left: 10px;">
               <html:select property="filtroProductoList" name="productoForm" styleId="filtroProductoList"  onchange="checkSession();search();limpiar(2);"> 
                  <html:option value="0"> Todos los Productos &nbsp;</html:option> -->
	              <html:optionsCollection name="productoForm" property="filtroProductoList" label="desc" value="cod"/> 
               </html:select>
            </div>
         </div>
         <div class="fila" style="height: 10px;">
            <div class="col_label" style="width: 130px;">Grupo Productos:</div>
            <div class="col_input" style="width: 300px;margin-left: 10px;">
               <html:select property="filtroGrupoList" name="productoForm" styleId="filtroGrupoList"  onchange="checkSession();search();limpiar(3);"> 
                  <html:option value="0"> Todos los Grupos &nbsp;</html:option>
<%-- 	              <html:optionsCollection name="productoForm" property="filtroGrupoList" label="desc" value="cod"/>  --%>

						<c:forEach  items="${productoForm.grupoProductoList}" var="item">
							<html:option  value="${item.cod}">
								${item.cod} - ${item.desc}
							</html:option>
						</c:forEach>
               </html:select>
            </div>
         </div>
         <div class="fila" style="height: 10px;">
            <div class="col_label" style="width: 130px;">Habilitado:</div>
            <div class="col_input" style="width: 300px;margin-left: 10px;">
               <html:select property="filtroHabilitadoList" name="productoForm" styleId="filtroHabilitadoList"   onchange="checkSession();search();limpiar(4);"> 
                  <html:option value="0"> Todos los Estados &nbsp;</html:option> 
	              <html:optionsCollection name="productoForm" property="filtroHabilitadoList" label="desc" value="cod"/> 
               </html:select>
            </div>
<!--        <button id="btnBusCons" type="button">Buscar</button>  -->
         </div>
       </div>
	</fieldset>
    <br>
	<fieldset id="productoSeleccionados" class="ui-widget ui-widget-content ui-corner-all">
      <legend id="productoSeleccionados_filter_legend" class="ui-widget ui-widget-header ui-corner-all">
			Producto seleccionado
      </legend>
      <div id="dialogProductoSeleccionado" title="Consulta" align="center">
         <div class="fila" style="height: 10px;">
            <div class="col_label" style="width: 120px;">C&oacute;digo Producto:</div>
            <div class="col_input" style="width: 350px;margin-left: 10px;">
               <input type="text" id="cdProductoSeleccionado" class="text ui-widget-content ui-corner-all ui-state-disabled" value="" disabled/>
               <input type="text" id="nbProductoSeleccionado" size="40" maxlength="40" class="text ui-widget-content ui-corner-all ui-state-disabled" value="" disabled />
            </div>
         </div>
      </div>
	</fieldset>
    <br>
    <div id="tabsApplication"class="ui-widget ui-widget-content ui-corner-all">
       <ul>
          <li><a class="checkLink" id="tab0" href="ABMCProductosBasicos.action">Datos B&aacute;sicos</a></li>
          <li><a class="checkLink" id="tab1" href="ABMCProductosPrecios.action">Tarifas en Unidad Val</a></li>
          
          <li><a class="checkLink" id="tab2" href="ABMCProductosPeriodos.action">Periodo de Valorizaci&oacute;n</a></li>
          <li><a class="checkLink" id="tab3" href="ABMCProductosSectores.action">Relaci&oacute;n Productos-Sectores</a></li>
          <li><a class="checkLink" id="tab4" href="ABMCProductosPeriodosTarifas.action">Tarifa en Unidades de Valoración</a></li>
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
<script src="js/abmc/productos.js"></script>
