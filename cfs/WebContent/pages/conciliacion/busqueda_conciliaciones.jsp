<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Este jsp es el contenedor de contenido estatico dinamico del alta de una aplicacion y las 
correspondientes asociaciones que se le tienen que hacer a dicha aplicación.  -->
<html>
<head>
<!--
  <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
  <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
  
  -->
</head>

<html:form action="/exportDataGrid" styleId="exportForm">

	<html:hidden name="busqConciliacionForm" property="anularConcil" styleId="anularConcil" />

	<div class="fila" id="difConciliacion_responseMsgs"
		style="display: none; overflow: auto;"></div>
		
	<html:hidden name="busqConciliacionForm" property="sector" styleId="userSector" />	
		
	<!-- Form de exportacion a pdf o xls-->
	<html:hidden name="exportDataForm" property="nameFile" styleId="nameFile" />
	<html:hidden name="exportDataForm" property="html" styleId="html" />
	<html:hidden name="exportDataForm" property="fileType" styleId="fileType" />	
	
	<input type="hidden" id="globalParams" />
	<!-- FILTRO de Conciliacion-->
	<div class="fila" id="busqConciliacion_responseMsgs"
		style="display: none; overflow: auto;"></div>
	<fieldset id="difConciliacionFilter"
		class="ui-widget ui-widget-content ui-corner-all">
		<legend id="difConciliacion_filter_legend"
			class="ui-widget ui-widget-header ui-corner-all">
			B&uacute;queda de Conciliaciones: </legend>

		<div class="fila" id="difConciliacion_responseMsgs"
			style="display: none; overflow: auto;"></div>

		<!-- 		Combo Proveedor -->
		<div id="dialogDifConciliacionFilter" title="Filtros de Consulta"
			align="center">
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 100px;">Proveedor:</div>
				<div class="col_input" style="width: 100px; margin-left: 10px;">
					<html:select property="cdProveedor" name="busqConciliacionForm" styleId="proveedorList" onchange="initialiseFiltroProducto()">
					 	<html:option value="0"> Todos los Proveedores &nbsp;</html:option>
						<html:optionsCollection name="busqConciliacionForm"
							property="proveedorList" label="desc" value="cod" />
					</html:select>
				</div>
			</div>

			<!--          Combo Sector -->
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 100px;">Sector Control:</div>
				<div class="col_input" style="width: 100px; margin-left: 10px;">
					<html:select property="cdSector" name="busqConciliacionForm"
						styleId="sectorList" onchange="">
						<html:option value="0"> Todos los Sectores &nbsp;</html:option>
						<html:optionsCollection name="busqConciliacionForm"
							property="sectorList" label="desc" value="cod" />
					</html:select>
				</div>
			</div>

			<!--          Periodo Facturacion  -->
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 100px;">Periodo Fact.:</div>
				<div class="col_input" style="width: 50px; margin-left: 10px;">
					<html:select property="fhDesde" name="busqConciliacionForm" styleId="selectFDesdeList">
						<html:option value="0"> Seleccione &nbsp;</html:option>
<%-- 						<html:optionsCollection name="busqConciliacionForm" property="pFactDesdeList" label="desc" value="cod" /> --%>
					</html:select>
				</div>
				<div class="col_label" style="width: 60px; margin-left: 90px">Hasta:</div>
				<div class="col_input" style="width: 50px; margin-left: 10px;">
					<html:select property="fhHasta" name="busqConciliacionForm" styleId="selectFHastaList">
						<html:option value="0"> Seleccione &nbsp;</html:option>
<%-- 						<html:optionsCollection name="busqConciliacionForm" property="pFactHastaList" label="desc" value="cod" /> --%>
					</html:select>
				</div>
			</div>

			<!--          Combo Producto -->
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 100px;">Producto:</div>
				<div class="col_input" style="width: 100px; margin-left: 10px;">
					<html:select property="cdProducto" name="busqConciliacionForm"
						styleId="productosList">
						<html:option value="0"> Todos los Productos &nbsp;</html:option>
						<html:optionsCollection name="busqConciliacionForm"
							property="productosList" label="nbProducto" value="cdProducto" />
					</html:select>
				</div>
				
				
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">
					Producto No Medibles
					<html:checkbox property="checkProdNoMedible" name="busqConciliacionForm" styleId="checkNoMedible"></html:checkbox></div>
			</div>
			<!-- 		Combo estado -->
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 100px;">Estado:</div>
				<div class="col_input" style="width: 100px; margin-left: 10px;">
					<html:select property="estadoDif" name="busqConciliacionForm"
						styleId="estadoDifList">
						<html:option value="0"> Todos los Estados &nbsp;</html:option>
						<html:option value="GRA"> Grabada Sin Aprobaci&oacute;n &nbsp;</html:option>
						<html:option value="APR"> Aprobada &nbsp;</html:option>
						<html:option value="ANU"> Anulada &nbsp;</html:option>
					</html:select>
				</div>
			</div>

			<!-- 		Combo  	CON/SIN diferencias -->
			<div class="fila" style="height: 10px;" id="comboDiferencias">
				<div class="col_label" style="width: 100px;">Con/Sin Dif.:</div>
				<div class="col_input" style="width: 100px; margin-left: 10px;">
					<html:select property="stDiferencia" name="busqConciliacionForm"
						styleId="stDiferencia">
						<html:option value="0"> Todas &nbsp;</html:option>
						<html:option value="true">CON DIFERENCIAS &nbsp;</html:option>
						<html:option value="false">SIN DIFERENCIAS &nbsp;</html:option>
					</html:select>
				</div>
			</div>

			<!-- 		Nro Conciliacion  -->
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 100px;">Nro Concilia:</div>
				<div class="col_input" style="width: 100px; margin-left: 10px;">
					<html:text property="cdConciliacion" name="busqConciliacionForm"
						styleId="cdConciliacion" maxlength="10"
						styleClass="text ui-widget-content ui-corner-all"
						style="text-align:center" onkeypress="return isNumber(event,this);"/>
				</div>
				<button id="btnBuscar" type="button" title="Buscar">Buscar</button>
			</div>

		</div>
		<br>
		<!-- GRILLA de Conciliaciones-->
		<div id="busqConciliacionesGrid" class="fila">
			<div id="gridBusqConciliaciones" align="left"
				style="padding-top: 10px;">
				<table id="gridBusqConciliacionesId"></table>
				<div id="gridBusqConciliacionesPager"></div>
			</div>
		</div>
		<!-- 		BOTONERA -->
		<div class="fila" style="height: 10px; display: none;"
			id="panelExport">
			<br>
			<button id="btnImprimir" type="button" title="Imprimir">Imprimir</button>
			<button id="btnExportar" type="button" title="Exportar a Excel" form="exportDataForm">Exportar</button>
		</div>

		<br>
		<!-- 	DIALOGO DE CONFIRMAR ANULAR -->
		<div id="confirmAnularConcilia" title="Atenci&oacute;n!!!"
			style="display: none;">
			<div id="msgConforim" class="col_label"></div>
		</div>
		<!-- 	DIALOGO DE ANULAR -->
		<div id="dialogAnularConcilia"
			title="Anulaci&oacute;n de Conciliaci&oacute;n"
			style="display: none;">
			<div id="responseMessageToEdit"
				style="display: none; width: 300px; overflow: auto;"></div>

			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 100px;">Observaciones:</div>
				<div class="col_input" style="width: 100px; margin-left: 10px;">
					<textarea rows="5" cols="80" name="observaciones"
						form="busqConciliacionForm" id="observaciones"
						class="text ui-widget-content ui-corner-all"
						style="text-align: center"></textarea>

				</div>

			</div>

		</div>

	</fieldset>
</html:form>
<script type="text/javascript">
	$(function() {
		$('#tabsApplication').tabs();
		$(':button').button();
	});
</script>
<script src="js/conciliacion/busquedaConciliaciones.js"></script>
<script src="js/exports/exportDataGrid.js"></script>
<script type="text/javascript" src="js/exports/numberConvert.js"></script>