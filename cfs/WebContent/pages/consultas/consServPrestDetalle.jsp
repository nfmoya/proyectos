<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" import="java.sql.*"%>

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

<html:form action="/ConsServPrestDetalle">
    <html:hidden name="servPrestDetalleForm" property="sector" styleId="sector" />	
	<!-- FILTRO de servicios Prestados-->
	<fieldset id="conServPrestDetalleFilter" class="ui-widget ui-widget-content ui-corner-all" style="width:1052px;">
		<legend id="conServPrestDetalle_filter_legend"	class="ui-widget ui-widget-header ui-corner-all"> Consulta de Servicios Prestados - Detalle de Remitos</legend>

		<div class="fila" id="conServPrestDetalle_responseMsgs" style="display: none; overflow: auto;"> </div>

        <div id="dialogConServPrestDetalleFilter" title="Consulta" align="center">
			<input type="hidden" id="cdUniVal" value="" />
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">Proveedor:</div>
				<div class="col_input" style="width: 200px; margin-left: 10px;">
					<html:select property="selectedProveedor" name="servPrestDetalleForm" onchange="initialiseFiltroProducto();" styleId="selectedProveedor">
						<html:option value="0"> Sin Proveedor &nbsp;</html:option>
						<html:optionsCollection name="servPrestDetalleForm"
							property="filtroProveedorList" label="desc" value="cod" />
					</html:select>
				</div>
			</div>

			<div class="fila" style="height: 10px;">

				<div class="col_label" style="width: 160px;">Sector:</div>
				<div class="col_input" style="width: 200px; margin-left: 10px;">
					<html:select property="selectedSector" name="servPrestDetalleForm"	styleId="selectedSector">
						<html:option value="0"> Sin Sector &nbsp;</html:option>
						<html:optionsCollection name="servPrestDetalleForm" property="filtroSectorList" label="desc" value="cod" />
					</html:select>
				</div>
			</div>
			
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">Grupo de Productos:</div>
				<div class="col_input" style="width: 200px; margin-left: 10px;">
					<html:select property="selectedGrupo" name="servPrestDetalleForm"	styleId="selectedGrupo">
						<html:option value="0"> Sin Grupo &nbsp;</html:option>
						<html:optionsCollection name="servPrestDetalleForm" property="filterGroupList" label="desc" value="cod" />
					</html:select>
				</div>
			</div>


			<div class="fila" style="height: 10px;" id="filaProducto">
				<div class="col_label" style="width: 160px;">Producto:</div>
				<div class="col_input" style="width: 260px; margin-left: 10px;">
					<html:select property="selectedProducto" name="servPrestDetalleForm" styleId="selectedProducto">
						<html:option value="0"> Sin Producto &nbsp;</html:option>
						<html:optionsCollection name="servPrestDetalleForm" property="filtroProductoList" label="nbProducto" value="cdProducto" />
					</html:select>
				</div>
				<div class="col_label" style="width: 80px;">Unidad Val.:</div>
				<div class="col_input" style="width: 200px; margin-left: 05px;">
					<input type="text" id="nbUniVal" class="input_medium text ui-widget-content ui-corner-all"
						style="text-align: left;" disabled="disabled" />
				</div>
			</div>
                        
             <div class="fila" style="height: 10px;">
                 <div class="col_label" style="width: 160px;">Fecha Remito Desde:</div>
                 <div class="col_input" style="width: 160px;margin-left: 10px;text-align:left;">
                     <html:text property="fhRemito" name="servPrestDetalleForm" styleId="fhRemito" maxlength="10" styleClass="text ui-widget-content ui-corner-all" style="text-align:center"/>
                 </div>
                 <div class="col_input" >
               		<button id="btnLimpiarDesde" type="button">&nbsp;</button>
              	 </div>
                 <div class="col_label" style="width: 150px">Fecha Remito Hasta:</div>
                 <div class="col_input" style="width: 150px;margin-left: 10px;">
                     <html:text property="fhFinServ" name="servPrestDetalleForm" styleId="fhFinServ" maxlength="10" styleClass="text ui-widget-content ui-corner-all" style="text-align:center"/>
                 </div>
                 <div class="col_input" >
               		<button id="btnLimpiarHasta" type="button" >&nbsp; </button>
              	</div>
             </div>
                        
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">Remito Desde:</div>
				<div class="col_input" style="width: 150px; margin-left: 10px;">
					<html:text property="remitoDesde" name="servPrestDetalleForm"
						styleId="remitoDesde" maxlength="10" size="10"
						styleClass="input_medium text ui-widget-content ui-corner-all" />
				</div>
				<div class="col_label" style="width: 160px; margin-left: 10px">Remito Hasta:</div>
				<div class="col_input" style="width: 150px; margin-left: 10px;">
					<html:text property="remitoHasta" name="servPrestDetalleForm"
						styleId="remitoHasta" maxlength="10" size="10"
						styleClass="input_medium text ui-widget-content ui-corner-all" />
				</div>
			</div>
			<!-- 		Nro Lote  -->
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 140px;">Nro Lote:</div>
				<div class="col_input" style="width: 200px; margin-left: 05px;">
					<html:text property="cdLote" name="servPrestDetalleForm" styleId="cdLote" maxlength="10" size="10"
						styleClass="input_medium text ui-widget-content ui-corner-all" />
				</div>
			</div>
                               
                        <div class="fila" style="height: 10px;">
                            <div class="col_label" style="width: 160px;">Estado:</div>
                            <div class="col_input" style="width: 200px; margin-left: 10px;">
                                <html:select property="stLoteDet" name="servPrestDetalleForm" styleId="stLoteDet"> 
                                        <html:option value="A"> A. ABIERTO</html:option>
                                        <html:option value="C"> C. CERRADO</html:option>	              
                                        <html:option value="N"> N. ANULADO</html:option>
                                </html:select>
                            </div>
                        </div>
                                
			<button id="btnBusCons" type="button">Buscar</button>
			
			<!-- GRILLA de ServPrestDetalle-->
		<div class="fila" id="msgEspera" style="display: none; overflow: auto;"></div>
		<div id="conServPrestDetalleGrid" class="fila" style="display: none;">
			<div id="gridServPrestDetalle" align="left" style="padding-top: 10px;">
				<table id="gridServPrestDetalleId"></table>
				<div id="gridServPrestDetallePager"></div>
			</div>
		</div>
		<!--
		<div class="fila" style="height: 10px; display:none; text-align: center;" id="panelExport">
			<br> <button id="btnImprimir" type="button" title="Imprimir" style="display:none">Imprimir</button> <button id="btnExportar" type="button" title="Exportar a Excel" style="display:none">Exportar</button>
		</div>
		<br>-->
		<fieldset id="conServPrestDetalleOther" class="ui-widget ui-widget-content ui-corner-all" style="${width_filter};">
			<button id="btnContract" type="button" style="display:none">Contraer</button>
			<button id="btnExpand" type="button" style="display: none">Expandir</button>
			<button id="btnResumen" type="button" title="Resumen">Resumen</button>
			<button id="btnImprimir" type="button" title="Imprimir a PDF" form="exportDataForm">Imprimir</button>  
			<button id="btnExportar" type="button" title="Exportar a Excel" form="exportDataForm">Exportar</button>
	
		</fieldset>
		</div>

	</fieldset>
		
</html:form>

<script type="text/javascript" src="js/exports/exportDataGrid.js"></script>
<script type="text/javascript" src="js/consultas/consServPrestDetalle.js"></script>
<!-- <script type="text/javascript" src="js/jquery/jquery.maskedinput.js"></script> -->
<script type="text/javascript" src="js/exports/numberConvert.js"></script>