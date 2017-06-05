<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- Este jsp es el contenedor de contenido estatico dinamico del alta de una aplicacion y las 
correspondientes asociaciones que se le tienen que hacer a dicha aplicación.  -->
<html>
<head><!--
  <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
  <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>  
  -->
</head>


<%-- <html:form action="/searchDifConciliacion"> --%>
<html:form action="/exportDataGrid" styleId="exportForm">
	<html:hidden name="difConciliacionForm" property="editGrant" styleId="editGrant" />
	<html:hidden name="difConciliacionForm" property="sectorUser" styleId="userSector" />	
	
	<!-- Form de exportacion a pdf o xls-->
	<html:hidden name="exportDataForm" property="nameFile" styleId="nameFile" />
	<html:hidden name="exportDataForm" property="html" styleId="html" />
	<html:hidden name="exportDataForm" property="fileType" styleId="fileType" />	
	
<!-- 	Datos origeinales que se van editar para comparar si se modificaron  -->
	<input type="hidden" id="oldTpSolucion" />
	<input type="hidden" id="oldObservaciones" />
<!-- 	Variable global para los parametros de exportar -->
	<input type="hidden" id="globalParams" />
	
	<div class="fila" id="difConciliacion_responseMsgs" style="display: none; overflow: auto;"></div>
	
   
   <!-- FILTRO de Conciliacion-->
   	<fieldset id="difConciliacionFilter" class="ui-widget ui-widget-content ui-corner-all">
    	<legend id="difConciliacion_filter_legend" class="ui-widget ui-widget-header ui-corner-all">
			Diferencias en Conciliaciones
      	</legend>
	
      	
<!-- 		Combo Proveedor -->		
      	<div id="dialogDifConciliacionFilter" title="Filtros de Consulta" align="center" style="width:1052px;">
         	<div class="fila" style="height: 10px;">
            	<div class="col_label" style="width: 100px;">Proveedor:</div>
            	<div class="col_input" style="width: 100px;margin-left: 10px;">
	               <html:select property="cdProveedor" name="difConciliacionForm" styleId="selectProveedorList" onchange="changeComboProveedor();"> 
	               		<html:option value="0">&nbsp; Todos los Proveedores &nbsp;</html:option>
		              	<html:optionsCollection name="difConciliacionForm" property="proveedorList" label="desc" value="cod"/> 
	               </html:select>
            	</div>
         	</div>
         
<!--          Combo Sector -->         
         	<div class="fila" style="height: 10px;">
            <div class="col_label" style="width: 100px;">Sector Control:</div>
	            <div class="col_input" style="width: 100px;margin-left: 10px;">
	            	<html:select property="cdSector" name="difConciliacionForm" styleId="selectSectorList" onchange="changeComboProveedor();"> 
	                	<html:option value="0">&nbsp; Todos los Sectores &nbsp;</html:option>
		             	<html:optionsCollection name="difConciliacionForm" property="sectorList" label="desc" value="cod"/> 
	               	</html:select>
	            </div>
         	</div>
   
<!--          Periodo Facturacion  -->
<%-- 	              		<html:optionsCollection name="difConciliacionForm" property="pFactDesdeList" label="cod" value="cod"/>  --%>
			<div class="fila" style="height: 10px;">
            	<div class="col_label" style="width: 100px;">Per&iacute;odo Fact.:</div>
            	<div class="col_input" style="width: 50px;margin-left: 10px;">
            		<html:select property="pfDesde" name="difConciliacionForm" styleId="selectFDesdeList"> 
	              		<html:option value="0">&nbsp; Todos los Periodos &nbsp;</html:option>
               		</html:select>            	
               	</div>
               	
<%-- 	              		<html:optionsCollection name="difConciliacionForm" property="pFactHastaList" label="cod" value="cod"/> --%>               	
               	<div class="col_label" style="width: 60px;margin-left: 160px">Hasta:</div>
            	<div class="col_input" style="width: 50px;margin-left: 10px;">
            		<html:select property="pfHasta" name="difConciliacionForm" styleId="selectFHastaList"> 
            			<html:option value="0">&nbsp; Todos los Periodos &nbsp;</html:option>
               		</html:select>            	
               	</div>
         	</div>

<!--          Combo Producto -->         
         	<div class="fila" style="height: 10px;">
            	<div class="col_label" style="width: 100px;">Producto:</div>
            	<div class="col_input" style="width: 100px;margin-left: 10px;" >
               		<html:select property="cdProducto" name="difConciliacionForm" styleId="selectProductoList"> 
                  		<html:option value="0">&nbsp; Todos los Productos &nbsp;</html:option>
	              		<html:optionsCollection name="difConciliacionForm" property="productosList" label="nbProducto" value="cdProducto"/> 
               		</html:select>
            	</div>
         	</div>   
         
<!-- 		Combo estado -->
 			<div class="fila" style="height: 10px;">
            	<div class="col_label" style="width: 100px;">Estado:</div>
            	<div class="col_input" style="width: 100px;margin-left: 10px;">
                	<html:select property="stDiferencia" name="difConciliacionForm" styleId="selectEstadoDifList"> 
                  		<html:option value="0"> Todos los Estados &nbsp;</html:option>
                  		<html:option value="ACT"> Activa &nbsp;</html:option>
                  		<html:option value="ANU"> Anulada &nbsp;</html:option>	              
               		</html:select>
	            </div>
    	    </div>   

<!-- 		Combo Situacion -->
 			<div class="fila" style="height: 10px;">
            	<div class="col_label" style="width: 100px;">Sit. Soluci&oacute;n:</div>
            	<div class="col_input" style="width: 100px;margin-left: 10px;">
                	<html:select property="tpSolucion" name="difConciliacionForm" styleId="selectSolucionDifList"> 
	              		<html:option value="0"> Todas las Soluciones &nbsp;</html:option>
                  		<html:option value="PENSOL"> Pendiente Soluci&oacute;n &nbsp;</html:option>
                  		<html:option value="SINAJU"> Sol. no genera Ajuste &nbsp;</html:option>	              
                  		<html:option value="AJUSTE"> Sol. genera Ajuste &nbsp;</html:option>
               		</html:select>
            	</div>
         	</div>   

<!-- 		Nro Conciliacion  -->
			<div class="fila" style="height: 10px;">
            	<div class="col_label" style="width: 100px;">Nro Concilia:</div>
            	<div class="col_input" style="width: 100px;margin-left: 10px;">
            		<html:text property="cdConciliacion" name="difConciliacionForm" styleId="cdConciliacion" size="17" maxlength="10" styleClass="text ui-widget-content ui-corner-all" style="text-align:center"/>
            	</div>       
       		</div>         
         	<button id="btnBuscar" type="button" title="Buscar">Buscar</button>
         	
         	<br><br>         	
         	
	        <!-- GRILLA de Diferencias de Conciliaciones -->
			<div id="difConciliaGrid" class="fila" style="display: none;">
				<div id="gridDifConcilia" align="left" style="padding-top: 10px;">
					<table id="gridDifConciliaId"></table>
					<div id="gridDifConciliaPager"></div>
				</div>
			</div>
			
			<!-- 		BOTONERA -->		
			<div class="fila" style="height: 10px; display: none;" id="panelExport" align="left">
				<br> <button id="btnImprimir" type="button" title="Imprimir" form="exportDataForm">Imprimir</button>
				<button id="btnExportar" type="button" title="Exportar a Excel" form="exportDataForm">Exportar</button>
			</div><br><br> 
    	</div>
	</fieldset>	

<!-- 	DIALOGO DE CONFIRMAR EDITAR -->		
	<div id="confirmUpdateDifConcilia" title="Confirmar Actualizar" style="display: none;">
		<div id="msgConforim" class="col_label"></div>
	</div>	
	
<!-- 	DIALOGO DE EDITAR -->
	<div id="dialogUpdateDifConcilia" title="Modificar Diferencia de Conciliaci&oacute;n" style="display: none;">
		<div id="responseMessageToEdit" style="display:none;width: 300px;overflow:auto;"></div>
		
<!-- 			Nro de conciliacion			 -->		
		<div class="fila">
			<div class="fila">
				<div class="col_label" style="text-align: left !important;width:120px;">Nro. Conciliaci&oacute;n: </div>
				<div class="col_input">
					<input type="text" id="cdConciliacionEdit" class="input_medium text ui-widget-content ui-corner-all ui-state-disabled" disabled="disabled" style="text-align:center;"/>
				</div>
			</div>
			 
			<!-- 		Combo Situacion -->
 			<div class="fila" style="height: 10px;">
            	<div class="col_label" style="width: 120px;">Sit. Soluci&oacute;n:</div>
            	<div class="col_input" style="width: 100px;">
                	<select id="solucionDifListEdit" style="width:150px"> 
	              		<option value="PENSOL"> Pendiente Soluci&oacute;n</option>
                  		<option value="SINAJU"> Sol. no genera Ajuste</option>	              
                  		<option value="AJUSTE"> Sol. genera Ajuste</option>
               		</select>
            	</div>
         	</div> 
         				
<!-- 			Text Area de Observaciones -->
			<div class="fila">
			    <div class="col_label" style="text-align: left !important;width:120px;">Observaciones: </div>
				<div class="col_input">
					<textarea cols="30" rows="4" id="obsDifConciliaEdit" class="input_large text ui-widget-content ui-corner-all;"></textarea>
				</div>
			</div>
		</div> 		
	</div>
	
</html:form>

<script>
	//Solo numeros!
	$('#cdConciliacion').bind('keypress', function(e) {
		return ( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57)) ? false : true ;
	});
</script>

<script src="js/consultas/searchDifConciliacion.js"></script>
<script src="js/exports/exportDataGrid.js"></script>
<script type="text/javascript" src="js/exports/numberConvert.js"></script>