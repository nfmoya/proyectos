<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" import="java.sql.*"%>
<!-- <script type="text/javascript" src="js/jquery/jquery.maskedinput.js"></script> -->
<html>
<head>
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
</head>

<html:form action="/exportDataGrid" styleId="exportForm">	
	<!-- Form de exportacion a pdf o xls-->
	<html:hidden name="exportDataForm" property="nameFile" styleId="nameFile" />
	<html:hidden name="exportDataForm" property="html" styleId="html" />
	<html:hidden name="exportDataForm" property="fileType" styleId="fileType" />	
</html:form>

<html:form action="/ConsServPrest">
   <html:hidden name="servPrestForm" property="sector" styleId="sector" />
   
	<!-- FILTRO de servicios Prestados-->
	<fieldset id="conServPrestFilter" class="ui-widget ui-widget-content ui-corner-all" style="width:1052px;">
		<legend id="conServPrest_filter_legend"	class="ui-widget ui-widget-header ui-corner-all"> Consulta de Servicios Prestados</legend>

		<div class="fila" id="conServPrest_responseMsgs" style="display: none; overflow: auto;"> </div>

		<div id="dialogConServPrestFilter" title="Consulta" align="center" >
			<input type="hidden" id="cdUniVal" value="" />
			<input type="hidden" id="productoContraido" value="" />
			
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">Proveedor:</div>
				<div class="col_input" style="width: 200px; margin-left: 10px;">
					<html:select property="selectedProveedor" name="servPrestForm" onchange="initialiseFiltroProducto();" styleId="selectedProveedor">
						<html:option value="0"> Sin Proveedor &nbsp;</html:option>
						<html:optionsCollection name="servPrestForm" property="filtroProveedorList" label="desc" value="cod" />
					</html:select>
				</div>
			</div>
						
			<div class="fila" style="height: 10px;">

				<div class="col_label" style="width: 160px;">Sector:</div>
				<div class="col_input" style="width: 200px; margin-left: 10px;">
					<html:select property="selectedSector" name="servPrestForm"	styleId="selectedSector">
						<html:option value="0"> Sin Sector &nbsp;</html:option>
						<html:optionsCollection name="servPrestForm" property="filtroSectorList" label="desc" value="cod" />
					</html:select>
				</div>
			</div>
			
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">Grupo de Productos:</div>
				<div class="col_input" style="width: 200px; margin-left: 10px;">
					<html:select property="selectedGrupo" name="servPrestForm"	styleId="selectedGrupo">
						<html:option value="0"> Sin Grupo &nbsp;</html:option>
						<html:optionsCollection name="servPrestForm" property="filterGroupList" label="desc" value="cod" />
					</html:select>
				</div>
			</div>


			<div class="fila" style="height: 10px;" id="filaProducto">
				<div class="col_label" style="width: 160px;">Producto:</div>
				<div class="col_input" style="width: 320px; margin-left: 10px;">
					<html:select property="selectedProducto" name="servPrestForm" styleId="selectedProducto">
						<html:option value="0"> Sin Producto &nbsp;</html:option>
						<html:optionsCollection name="servPrestForm" property="filtroProductoList" label="nbProducto" value="cdProducto" />
					</html:select>
				</div>
				<div class="col_label" style="width: 150px;">Unidad Val.:</div>
				<div class="col_input" style="width: 150px; margin-left: 05px;">
					<input type="text" id="nbUniVal" class="input_medium text ui-widget-content ui-corner-all"
						style="text-align: left;" disabled="disabled" />
				</div>
			</div>
			
			<div class="fila" style="height: 10px;">
            	<div class="col_label" style="width: 160px;">Fecha Remito Desde:</div>
            	<div class="col_input" style="width: 160px;margin-left: 10px;text-align:left;">
            		<html:text property="fhDesde" name="servPrestForm" styleId="fhDesde" maxlength="10"  styleClass="text ui-widget-content ui-corner-all" style="text-align:center"/>
               	</div>
               	<div class="col_input" style="width: 156px;text-align:left;" >
               		<button id="btnLimpiarDesde" type="button">&nbsp;</button>
              	</div>
               	<div class="col_label" style="width: 150px">Fecha Remito Hasta:</div>
            	<div class="col_input" style="width: 150px;margin-left: 05px;">
	           		<html:text property="fhHasta" name="servPrestForm" styleId="fhHasta" maxlength="10"  styleClass="text ui-widget-content ui-corner-all" style="text-align:center"/>
	           		
               	</div>
               	<div class="col_input" >
               		<button id="btnLimpiarHasta" type="button" >&nbsp; </button>
              	</div>
         	</div>
		
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">Remito Desde:</div>
				<div class="col_input" style="width: 320px; margin-left: 10px;">
					<html:text property="remitoDesde" name="servPrestForm"
						styleId="remitoDesde" maxlength="6" size="10"
						styleClass="input_medium text ui-widget-content ui-corner-all" />
				</div>
				<div class="col_label" style="width: 150px;">Remito Hasta:</div>
				<div class="col_input" style="width: 150px; margin-left: 05px;">
					<html:text property="remitoHasta" name="servPrestForm"
						styleId="remitoHasta" maxlength="6" size="10"
						styleClass="input_medium text ui-widget-content ui-corner-all" />
				</div>
			</div>
			
			<div class="fila" style="height: 10px;">
                 <div class="col_label" style="width: 160px;">Estado Detalle:</div>
                 <div class="col_input" style="width: 200px; margin-left: 10px;">
                     <html:select property="stLoteDet" name="servPrestForm" styleId="stLoteDet" > 
                             <html:option value="A" > A. ABIERTO</html:option>
                             <html:option value="C" > C. CERRADO</html:option>	              
                             <html:option value="N"> N. ANULADO</html:option>
                     </html:select>
                 </div>
             </div>
			
			<!-- 		Nro Lote  -->
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 140px;">Nro Lote:</div>
				<div class="col_input" style="width: 200px; margin-left: 05px;">
					<html:text property="cdLote" name="servPrestForm" styleId="cdLote" maxlength="10" size="10"
						styleClass="input_medium text ui-widget-content ui-corner-all" />
				</div>
			</div>
			<button id="btnBusCons" type="button">Buscar</button>
			
				<!-- GRILLA de ServPrest-->
		<div class="fila" id="msgEspera" style="display: none; overflow: auto;"></div>
		<div id="conServPrestGrid" class="fila" style="display: none;width:1050px;"  >
			<div id="gridServPrest" align="left" style="padding-top: 10px; " >
				<table id="gridServPrestId"></table>
				<div id="gridServPrestPager"></div>
			</div>
		</div>
		<br>
			<div id="botoneraServPrest">
				<fieldset id="conServPrestOther"
					class="ui-widget ui-widget-content ui-corner-all"
					style="${width_filter}">
					<!-- 		<br> -->
		
					<button id="btnContract" type="button" style="width:100px;">Contraer</button>
					<button id="btnExpand" type="button" style="display: none; width:100px;">Expandir</button>
					<button id="btnDetalle" type="button" style="width:100px;">Detalle</button>
					<button id="btnImprimir" type="button" title="Imprimir a PDF" form="exportDataForm">Imprimir</button>  
					<button id="btnExportar" type="button" title="Exportar a Excel" form="exportDataForm">Exportar</button>
				</fieldset>
			</div>				
		</div>

	</fieldset>
		
</html:form>

<script type="text/javascript" src="js/consultas/consServPrest.js"></script>
<script type="text/javascript" src="js/exports/exportConsServPrest.js"></script>
<script type="text/javascript" src="js/exports/numberConvert.js"></script>