<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" import="java.sql.*"%>

<script type="text/javascript" src="js/consultas/consServFact.js"></script>
<script type="text/javascript" src="js/exports/exportConsServFact.js"></script>
<script type="text/javascript" src="js/exports/numberConvert.js"></script>

<style>
     .myclass td {
        font-weight : bold !important;
     }
     .grid-col {
        border-right-width: 2px   !important;
        border-right-style: solid !important;
        border-right-color: black !important;
     }     
  </style>

<html:form action="/exportDataGrid" styleId="exportForm">	
	<!-- Form de exportacion a pdf o xls-->
	<html:hidden name="exportDataForm" property="nameFile" styleId="nameFile" />
	<html:hidden name="exportDataForm" property="html" styleId="html" />
	<html:hidden name="exportDataForm" property="fileType" styleId="fileType" />	
</html:form>

<html:form action="/ConsServFact">
	<html:hidden name="servFactForm" property="addGrant"    styleId="addGrant" />
	<html:hidden name="servFactForm" property="editGrant" 	styleId="editGrant" />
	<html:hidden name="servFactForm" property="deleteGrant" styleId="deleteGrant" />
    <html:hidden name="servFactForm" property="sector"      styleId="sector" />		
	<input type="hidden" id="selectGrant" value="S" />
	<input type="hidden" id="tipoModificacion" value="" />
	<!-- FILTRO de servicios facturados-->
	<fieldset id="conServFactFilter" class="ui-widget ui-widget-content ui-corner-all" style="width:1052px;">
		<legend id="conServFact_filter_legend"
			class="ui-widget ui-widget-header ui-corner-all"> Consulta 	de Servicios Facturados </legend>

		<div class="fila" id="conServFact_responseMsgs"
			style="display: none; overflow: auto;"></div>

		<div id="dialogConServFactFilter" title="Consulta" align="center">
			<input type="hidden" id="cdUniVal" value="" />
			<div class="fila" style="height: 10px;">

				<div class="col_label" style="width: 160px;">Proveedor:</div>
				<div class="col_input" style="width: 200px; margin-left: 10px;">
					<html:select property="selectedProveedor" name="servFactForm"
						onchange="initialiseFiltroProducto();" styleId="selectedProveedor">
						<html:option value="0"> Sin Proveedor &nbsp;</html:option>
						<html:optionsCollection name="servFactForm"
							property="filtroProveedorList" label="desc" value="cod" />
					</html:select>
				</div>
			</div>
			<div class="fila" style="height: 10px;">

				<div class="col_label" style="width: 160px;">Sector:</div>
				<div class="col_input" style="width: 200px; margin-left: 10px;">
					<html:select property="selectedSector" name="servFactForm"
						styleId="selectedSector">
						<html:option value="0"> Sin Sector &nbsp;</html:option>
						<html:optionsCollection name="servFactForm"
							property="filtroSectorList" label="desc" value="cod" />
					</html:select>
				</div>
			</div>
			<div class="fila" style="height: 10px;" id="filaProducto">
				<div class="col_label" style="width: 160px;">Producto:</div>
				<div class="col_input" style="width: 320px; margin-left: 10px;">
					<html:select property="selectedProducto" name="servFactForm"
						styleId="selectedProducto">
						<html:option value="0"> Sin Producto &nbsp;</html:option>
						<html:optionsCollection name="servFactForm" property="filtroProductoList" label="desc" value="cod" />
					</html:select>
				</div>
				
				<div class="col_label" style="width: 160px;">Unidad Valoraci&oacute;n:</div>
				<div class="col_input" style="width: 200px; margin-left: 05px;">
					<html:text property="nbUniVal" name="servFactForm" styleId="nbUniVal"
						styleClass="input_medium text ui-widget-content ui-corner-all" readonly="true" />
				
				</div>
				
			</div>
			
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">Grupo Producto:</div>
				<div class="col_input" style="width: 200px; margin-left: 10px;">
					<html:select property="selectedGrupo" name="servFactForm"
						styleId="selectedGrupo">
						<html:option value="0"> Sin Grupo &nbsp;</html:option>
						<html:optionsCollection name="servFactForm"
							property="filterGroupList" label="desc" value="cod" />

					</html:select>
				</div>
			</div>
			<!--          Periodo Facturación  -->
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">Per&iacute;odo
					Fact.:</div>
				<div class="col_input" style="width: 150px; margin-left: 05px;">
					<html:select property="fhDesde" name="servFactForm"
						styleId="fhDesde">
						<html:option value="0"> Sin Per&iacute;odo &nbsp;</html:option>
						<html:optionsCollection name="servFactForm"
							property="pFactDesdeList" label="desc" value="cod" />
					</html:select>
				</div>
				<div class="col_label" style="width: 30px; margin-left: 115px">Hasta:</div>
				<div class="col_input" style="width: 110px; margin-left: 10px;">
					<html:select property="fhHasta" name="servFactForm"
						styleId="fhHasta">
						<html:option value="0"> Sin Per&iacute;odo &nbsp;</html:option>
						<html:optionsCollection name="servFactForm"
							property="pFactHastaList" label="desc" value="cod" />
					</html:select>
				</div>
			</div>
			<!-- 		Tipo Comprobante  -->
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">Tipo Comprobante:</div>
				<div class="col_input" style="width: 200px; margin-left: 05px;"><html:select property="selectedComp" name="servFactForm"
						styleId="selectedComp">
						<html:option value="0"> Sin Comprobante &nbsp;</html:option>
						<html:optionsCollection name="servFactForm"
							property="filterCompList" label="desc" value="cod" />
					</html:select>
				</div>
			</div>	
			<!-- 		Nro Comprobante  -->
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">Nro. Comprobante:</div>
				<div class="col_input" style="width: 150px; margin-left: 10px;">
					<html:text property="cdComprobante" name="servFactForm"
						styleId="cdComprobante" maxlength="13" size="13"
						styleClass="input_medium text ui-widget-content ui-corner-all" />
				</div>
			</div>
			<!-- 		Nro remito  -->
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">Remito Desde:</div>
				<div class="col_input" style="width: 150px; margin-left: 10px;">
					<html:text property="remitoDesde" name="servFactForm"
						styleId="remitoDesde" maxlength="13" size="13"
						styleClass="input_medium text ui-widget-content ui-corner-all" />
				</div>
				<div class="col_label" style="width: 30px; margin-left: 90px">Hasta:</div>
				<div class="col_input" style="width: 150px; margin-left: 10px;">
					<html:text property="remitoHasta" name="servFactForm"
						styleId="remitoHasta" maxlength="13" size="13"
						styleClass="input_medium text ui-widget-content ui-corner-all" />
				</div>
			</div>
			<!-- 		Nro Lote  -->
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">Nro Lote:</div>
				<div class="col_input" style="width: 200px; margin-left: 05px;">
					<html:text property="cdLote" name="servFactForm" styleId="cdLote"
						maxlength="10" size="10"
						styleClass="input_medium text ui-widget-content ui-corner-all" />
				</div>
				<div class="col_label" style="width: 160px;">Estado Detalle:</div>
				<div class="col_input" style="width: 200px; margin-left: 10px;">
					<html:select property="habilitadoSel" name="servFactForm"
						styleId="habilitadoSel">
						<html:optionsCollection name="servFactForm"
							property="habilitadoList" label="desc" value="cod" />
					</html:select>
				</div>
			</div>
			<button id="btnBusCons" type="button">Buscar</button>
		</div>

		<!-- GRILLA de ServFact-->
		<div class="fila" id="msgEspera"
			style="display: none; overflow: auto;"></div>
		<div id="conServFactGrid" class="fila" style="display: none;">
			<div id="gridServFact" align="left" style="padding-top: 10px;">
				<table id="gridServFactId"></table>
				<div id="gridServFactPager"></div>
			</div>
		</div>
		<br>
		<div id="botoneraServFact">
			<fieldset id="conServFactOther"
				class="ui-widget ui-widget-content ui-corner-all"
				style="${width_filter}">
				<!-- 		<br> -->
	
				<button id="btnContract" type="button" >Contraer</button>
				<button id="btnExpand" type="button" style="display: none">Expandir</button>
				<button id="btnDetail" type="button" >Detalle</button>
				<button id="btnImprimir" type="button" title="Imprimir a PDF" form="exportDataForm">Imprimir</button>  
				<button id="btnExportar" type="button" title="Exportar a Excel" form="exportDataForm">Exportar</button>
			</fieldset>
		</div>
	</fieldset>
</html:form>