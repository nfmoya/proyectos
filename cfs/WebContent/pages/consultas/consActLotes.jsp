<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- <script type="text/javascript" src="js/jquery/jquery.maskedinput.js"></script> -->

<!-- Este jsp es el contenedor de contenido estatico dinamico del alta de una aplicacion y las 
correspondientes asociaciones que se le tienen que hacer a dicha aplicación.  -->
<html>
<head><!--
  <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
  <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
  
  -->
</head>
<html:form action="/consActLotes">
	<html:hidden name="consActLotesForm" property="editGrant" styleId="editGrant" />
	
	<html:hidden name="consActLotesForm" property="anularPrestGrant" styleId="anularPrestGrant" />	
	<html:hidden name="consActLotesForm" property="anularFactGrant" styleId="anularFactGrant" />
	
<!-- 	Hidden para chequear si puede anular  -->
	<input type="hidden" id="puedeAnular" />
	
	<div class="fila" id="consActLotes_responseMsgs" style="display: none; overflow: auto;"></div>
   
<!-- FILTRO -->
   	<fieldset id="consActLotesFilter" class="ui-widget ui-widget-content ui-corner-all">
    	<legend id="consActLotes_filter_legend" class="ui-widget ui-widget-header ui-corner-all">
			Consulta y Actualizaci&oacute;n de Lotes
      	</legend>
   	    	
      	<div id="dialogConsActLotesFilter" title="Filtros de Consulta" align="center">
      	
<!-- 		Combo Tipo Lote -->
			<div class="fila" style="height: 10px;">
	           	<div class="col_label" style="width: 150px;">Tipo Lote:</div>
	           	<div class="col_input" style="width: 100px;margin-left: 10px;">
	               	<html:select property="tipoLote" name="consActLotesForm" styleId="tipoLoteList"> 
	                 		<html:option value=""> &nbsp;</html:option>
	                 		<html:option value="SP"> Servicios Prestados &nbsp;</html:option>
	                 		<html:option value="SF"> Servicios Facturados &nbsp;</html:option>	              
	              		</html:select>
	            </div>
	   	    </div>
	   	    
<!-- 		Combo Proveedor -->	
         	<div class="fila" style="height: 10px;">
            	<div class="col_label" style="width: 150px;">Proveedor:</div>
            	<div class="col_input" style="width: 100px;margin-left: 10px;">
	               <html:select property="cdProveedor" name="consActLotesForm" styleId="proveedorList" onchange=""> 
	               		<html:option value=""> Todos los Proveedores &nbsp;</html:option>
		              	<html:optionsCollection name="consActLotesForm" property="proveedorList" label="desc" value="cod"/> 
	               </html:select>
            	</div>
         	</div>
         
<!--        Fecha Alta  -->
			<div class="fila" style="height: 10px;">
            	<div class="col_label" style="width: 150px;">Fecha Alta Desde:</div>
            	<div class="col_input" style="width: 150px;margin-left: 10px;text-align:left;">
            		<html:text property="fhAltaDesde" name="consActLotesForm" styleId="fhAltaDesde" size="12" maxlength="10" styleClass="text ui-widget-content ui-corner-all" style="text-align:center"/>
               	</div>
               	<div class="col_label" style="width: 30px">Hasta:</div>
            	<div class="col_input" style="width: 150px;margin-left: 10px;">
	           		<html:text property="fhAltaHasta" name="consActLotesForm" styleId="fhAltaHasta" size="12" maxlength="10" styleClass="text ui-widget-content ui-corner-all" style="text-align:center"/>
               	</div>
         	</div>

<!-- 		Combo Estado Lote -->
 			<div class="fila" style="height: 10px;">
            	<div class="col_label" style="width: 150px;">Estado Lote:</div>
            	<div class="col_input" style="width: 100px;margin-left: 10px;">
                	<html:select property="stLote" name="consActLotesForm" styleId="stLoteList"> 
                  		<html:option value=""> Todos los Estados &nbsp;</html:option>
                  		<html:option value="ACT"> Activo &nbsp;</html:option>
                  		<html:option value="ANU"> Anulado &nbsp;</html:option>	              
               		</html:select>
	            </div>
    	    </div>

<!-- 		Nro. Lote  -->
			<div class="fila" style="height: 10px;">
            	<div class="col_label" style="width: 150px;">Nro. Lote:</div>
            	<div class="col_input" style="width: 100px;margin-left: 10px;">
            		<html:text property="cdLote" name="consActLotesForm" styleId="cdLote" size="15" maxlength="10" styleClass="text ui-widget-content ui-corner-all" style="text-align:center"/>
            	</div>       
       		</div>         
         	<button id="btnBuscar" type="button" title="Buscar">Buscar</button>         
    	</div>
    	
<!-- GRILLA de resultados de la consulta -->
		<div id="consActLotesGrid" class="fila" style="display: none;">
			<div id="gridConsActLotes" align="left" style="padding-top: 10px;">
				<table id="gridConsActLotesId"></table>
				<div id="gridConsActLotesPager"></div>
			</div>
		</div>
	</fieldset>
	

		

<!-- DIALOGO DE CONFIRMAR ANULACION -->		
	<div id="dialogAnularLote" title="Confirmar anulaci&oacute;n" style="display: none;">
		<input type="hidden" id="tipoLoteAnular"/>
		<input type="hidden" id="nroLoteAnular"/>
		<div id="msgConfirm" class="col_label">Usted est&aacute; a punto de anular definitivamente este Lote.<br>
<!-- 												Todos los registros asociados a la misma dejar&aacute;n de estarlo.<br> -->
												&iquest;Confirma la anulaci&oacute;n?</div>
	</div>
</html:form>

<script>
	//Solo numeros!
	$('#cdLote').bind('keypress', function(e) {
	return ( e.which!=8 && e.which!=0 && (e.which<48 || e.which>57)) ? false : true ;
	});
</script>
<script src="js/consultas/consActLotes.js"></script>
