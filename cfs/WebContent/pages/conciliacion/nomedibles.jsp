<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- <script type="text/javascript" src="js/jquery/jquery.maskedinput.js"></script> -->

<!-- Este jsp es el contenedor de contenido estatico dinamico del alta de una aplicacion y las 
correspondientes asociaciones que se le tienen que hacer a dicha aplicacion.  -->
<html>
<head>
  <style>
     .myclass td {
        font-weight : bold !important;
/*
        background-color: red !important;
        color: white !important;
*/
     }
     .grid-col {
        border-right-width: 1px   !important;
        border-right-style: solid !important;
        border-right-color: blue !important;
     }     
    th.ui-th-column div{
        white-space:normal !important;
        height:auto !important;
        padding:2px;
    }
  </style>  
</head>
<!-- Form de exportacion a pdf o xls-->
<html:form styleId="exportForm" action="/exportDataGrid">
	<html:hidden name="exportDataForm" property="nameFile" styleId="nameFile" />
	<html:hidden name="exportDataForm" property="html" styleId="html" />
	<html:hidden name="exportDataForm" property="fileType" styleId="fileType" />
	<html:hidden name="exportDataForm" property="filePath" styleId="filePath" />
</html:form>
<html:form styleId="noMediblesForm" action="/ABMCNoMedibles">
   <html:hidden name="noMediblesForm" property="saveGrant"    styleId="saveGrant" />  
   <html:hidden name="noMediblesForm" property="approveGrant" styleId="approveGrant" />
   <html:hidden name="noMediblesForm" property="differGrant"  styleId="differGrant" />
   <html:hidden name="noMediblesForm" property="sector" styleId="sector" />
   <input type="hidden" id="stConcilSinVal"      value="N" />
   <input type="hidden" id="stServSinConcil"     value="S" />
   <input type="hidden" id="stFactSinConcil"     value="S" />
   <input type="hidden" id="fhDesde"             value="" />
   <input type="hidden" id="fhHasta"             value="" />
   <input type="hidden" id="stPeriodo"           value="" />
   <input type="hidden" id="stProducto"          value="" />
   <input type="hidden" id="stProductoSector"    value="" />
   
   <input type="hidden" id="cdProveedor"         value="" />
   <input type="hidden" id="cdSector"            value="" />
   <input type="hidden" id="cdProducto"          value="" />
   <input type="hidden" id="cdPeriodo"           value="" />
   <input type="hidden" id="tipoModificacion"    value=""/>   
   <input type="hidden" id="grabada"             value="N"/>   
   <input type="hidden" id="conciliacion"        value=""/>
   <input type="hidden" id="modificada"          value="N"/>
   <input type="hidden" id="stConciliacion"      value="PEN"/>
   <input type="hidden" id="anConciliacion"      value="PEN"/>
   <input type="hidden" id="recargaConciliacion" value="N"/>
   <input type="hidden" id="rowIdDiferencia"     value=""/>
   <input type="hidden" id="nbObservacGeneral"   value=""/>
   <input type="hidden" id="tpSolucionGeneral"   value=""/>
   
   <!-- FILTRO de Productos-->
   <fieldset id="conciliacionesFilter" class="ui-widget ui-widget-content ui-corner-all">
      <legend id="conciliaciones_filter_legend" class="ui-widget ui-widget-header ui-corner-all">
			B&uacute;squeda No Medibles
      </legend>

      <div class="fila" id="conciliacion_responseMsgs" style="display: none; overflow: auto;"></div>
		
      <div id="dialogConciliacionFilter" title="Consulta" align="center" style="width:1052px;">
         <div class="fila" style="height: 10px;">
            <div class="col_label" style="width: 70px;font-weight:bold;">Proveedor:</div>
            <div class="col_input" style="width: 250px;">
               <html:select property="filtroProveedorList" name="noMediblesForm" styleId="filtroProveedorList"> 
<!--              <html:option value="0"> Sin Proveedor </html:option> -->
                  <html:optionsCollection name="noMediblesForm" property="filtroProveedorList" label="desc" value="cod"/> 
               </html:select>
            </div>
            <div class="col_label" style="width: 50px;font-weight:bold;">Sector:</div>
            <div class="col_input" style="width: 250px;">
               <html:select property="filtroSectorList" name="noMediblesForm" styleId="filtroSectorList"> 
                  <html:option value="0"> Sin Sector </html:option>
	              <html:optionsCollection name="noMediblesForm" property="filtroSectorList" label="desc" value="cod"/> 
               </html:select>
            </div>
            <div class="col_label" style="width: 50px;font-weight:bold;">Per&iacute;odo:</div>
            <div class="col_input" style="width: 250px;">
               <html:select property="filtroPeriodoList" name="noMediblesForm" styleId="filtroPeriodoList"> 
                  <html:option value="0"> Sin Per&iacute;odo </html:option>
	              <html:optionsCollection name="noMediblesForm" property="filtroPeriodoList" label="desc" value="cod"/> 
               </html:select>
            </div>
         </div>
		 <div class="fila" style="height: 10px;">
            <div class="col_label" style="width: 70px;font-weight:bold;">Grupo:</div>
            <div class="col_label" style="width: 230px;">
               NO-CON – NO CONCILIADOS
            </div>   
           	<div class="col_label" style="width: 160px;">Situaci&oacute;n Conciliaci&oacute;n:</div>
           	<div class="col_input" style="width: 200px;margin-right:70px;">
               <html:select property="filtroSituacionConciliacionList" name="noMediblesForm" styleId="filtroSituacionConciliacionList"> 
                   <html:option value="PEN">Pendiente</html:option>
                   <html:option value="GRA">Grabada sin aprobar</html:option>	              
                   <html:option value="APR">Aprobada</html:option>      
                   <html:option value="ANU">Anulada</html:option>
               </html:select>
            </div>
		 </div>
		 <div class="fila" style="height: 10px;">
            <div class="col_label" style="width: 100px;">Nro Conciliaci&oacute;n:</div>
            <div class="col_input" style="width: 300px;margin-left: 10px;">
	           <html:text property="cdConciliacion" name="noMediblesForm" styleId="cdConciliacion" size="70" maxlength="9" alt="999999999" styleClass="text ui-widget-content ui-corner-all" style="text-align:center" />
               <html:select property="filtroConciliacionList" name="noMediblesForm" styleId="filtroConciliacionList">
                   <html:option value="0"> Sin Conciliacion </html:option>
				   <html:optionsCollection name="noMediblesForm" property="filtroConciliacionList" label="desc" value="cod"/>                
			   </html:select>
            </div>
         </div>   
         <div class="fila" style="height: 20px;">
            <div class="col_label" style="width: 100px;"></div>
            <div class="col_input" style="width: 20px;margin-left: 10px;">
               <button id="btnBusCons" type="button">&nbsp;&nbsp;Buscar&nbsp;&nbsp;</button>
            </div>
         </div>   
       </div>

		<!-- GRILLA de Conciliaciones-->
		<div class="fila" id="msgEspera" style="display: none; overflow: auto;"></div>
		<div id="conciliacionesGrid" class="fila" style="display: none;">
			<div class="fila" style="height:10px;text-align:right;padding-bottom:0px;">
	            <div class="col_label" style="width:800px;text-align:right;">Seleccionar Todo</div> 
	            <div class="col_input" style="width: 100px;margin-left: 10px;">
	            	<input type="checkbox" name="checkAll" id="checkAll" class="text ui-widget-content ui-corner-all" />
	                <button id="btnObsAll" type="button">Observ</button>
	            </div>
	        </div>		
			<div id="gridConciliaciones" align="left" style="padding-top: 10px;">
				<table id="gridConciliacionesId"></table>
                <div id="gridConciliacionesPager"></div>
			</div>
			<br/>
			<div class="fila" style="height:20px;text-align:right;padding-bottom:0px;">
	            <div class="col_label" style="width:800px;text-align:right;">Dif. fuera del desvio</div> 
	            <div class="col_input" style="width: 100px;margin-left: 10px;">
	            	<input type="text" name="difDesvio" id="difDesvio" size="20" maxlength="20" class="text ui-widget-content ui-corner-all"  style="text-align:right" disabled="disabled" />
	            </div>
	        </div>			
		</div>

        <p>
             
		<div id="divParametros" class="fila" style="display: none;">
		   <div class="fila" style="height: 10px">
              <div class="fila" style="height: 40px;">
                 <div class="col_label" style="text-align: left !important;width:120px;">Parametros: </div>
                 <div class="col_input" style="width: 100px;">
                    <textarea cols="120" rows="5" id="parametrosGrabar"></textarea>
                 </div>
              </div>
           </div>
		</div>
		
        <p>
		
<!-- 	DIALOGO DE EDITAR -->
        <div id="dialogDiferenciaConciliacion" align="center" title="Editar Diferencia de Conciliaci&oacute;n" style="display: none;">
		   <div id="responseMessageToEdit" style="display:none;width: 300px;overflow:auto;"></div>
<!-- 
	       <div class="fila" style="height: 10px;">
			  <div class="col_label" style="width: 120px;">Diferencia Valor: </div>
			  <div class="col_input" style="width: 100px;">
				 <input type="text" id="imDiferencia" style="text-align:right;" class="text ui-widget-content ui-corner-all" size="15" maxlength="12" onblur="extractNumberComa(this,2,true);" onkeyup="extractNumberComa(this,2,true);" onkeypress="return blockNonNumbersComa(this, event, true, true);" />
			  </div>
		   </div>
-->		   
           <div class="fila" style="height: 20px;">
              <div class="col_label" style="text-align: left !important;width:120px;">Observaciones: </div>
              <div class="col_input" style="width: 300px;">
                 <textarea cols="45" rows="10" id="nbObservaciones" maxlength="180" class="input_large text ui-widget-content ui-corner-all;"></textarea>
              </div>
           </div>
           <div class="fila" style="height: 10px;">
              <div class="col_label" style="width: 120px;">Sit. Soluci&oacute;n:</div>
              <div class="col_input" style="width: 100px;">
                 <select id="solucionDifListEdit"> 
                    <option value="">Seleccionar...</option>
                    <option value="PENSOL"> Pendiente Soluci&oacute;n</option>
                    <option value="SINAJU"> Sol. no genera Ajuste</option>	              
                    <option value="AJUSTE"> Sol. genera Ajuste</option>
                 </select>
              </div>
           </div>           
        </div>        
        <p>    
	</fieldset>
	<!-- BOTONERA -->
	<div id="botonera" class="fila">
		<div class="fila" style="height: 10px; display: none;" id="panelExport">
			<br> <button id="btnGrabar" type="button" title="Grabar">Grabar</button>  
			     <button id="btnAprobar" type="button" title="Aprobar">Aprobar</button>  
			     <button id="btnImprimir" type="button" title="Imprimir" form="exportDataForm">Imprimir</button>  
			     <button id="btnExportar" type="button" title="Exportar a Excel" form="exportDataForm">Exportar</button>
                 <button id="btnLimpiar" type="button" title="Limpiar">Limpiar</button> 
		</div>
	</div>
    <br>
</html:form>

<script src="js/nomedibles/nomediblesConciliacionesGrid.js"></script>
<script src="js/nomedibles/nomediblesFunciones.js"></script>
<script src="js/nomedibles/nomediblesExportacion.js"></script>
<script src="js/nomedibles/nomediblesFiltros.js"></script>
<script src="js/nomedibles/nomedibles.js"></script>

<script type="text/javascript" src="js/exports/numberConvert.js"></script>

<script src="js/exports/exportDataGrid.js"></script>
