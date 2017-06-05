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
  </style>  
</head>
<!-- Form de exportacion a pdf o xls-->
<html:form styleId="exportForm" action="/exportDataGrid">
	<html:hidden name="exportDataForm" property="nameFile" styleId="nameFile" />
	<html:hidden name="exportDataForm" property="html" styleId="html" />
	<html:hidden name="exportDataForm" property="fileType" styleId="fileType" />
	<html:hidden name="exportDataForm" property="filePath" styleId="filePath" />
</html:form>
<html:form styleId="conciliacionesForm" action="/ABMCConciliaciones">
   <html:hidden name="conciliacionForm" property="saveGrant"    styleId="saveGrant" />  
   <html:hidden name="conciliacionForm" property="approveGrant" styleId="approveGrant" />
   <html:hidden name="conciliacionForm" property="differGrant"  styleId="differGrant" />
   <html:hidden name="conciliacionForm" property="sector" styleId="sector" />
   <input type="hidden" id="nuDiaEmiFDesde"      value="999" />
   <input type="hidden" id="nuDiaEmiFHasta"      value="999" />
   <input type="hidden" id="nuDiaCierreFDesde"   value="999" />
   <input type="hidden" id="nuDiaCierreFHasta"   value="999" />
   <input type="hidden" id="stConcilSinVal"      value="N" />
   <input type="hidden" id="stServSinConcil"     value="S" />
   <input type="hidden" id="stFactSinConcil"     value="S" />
   <input type="hidden" id="fhDesde"             value="" />
   <input type="hidden" id="fhHasta"             value="" />
   <input type="hidden" id="stPeriodo"           value="" />
   <input type="hidden" id="cdUniVal"            value="" />
   <input type="hidden" id="stProducto"          value="" />
   <input type="hidden" id="stProductoSector"    value="" />
   <input type="hidden" id="cdGrupoProducto"     value="" />
   
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
   <!-- FILTRO de Productos-->
   <fieldset id="conciliacionesFilter" class="ui-widget ui-widget-content ui-corner-all">
      <legend id="conciliaciones_filter_legend" class="ui-widget ui-widget-header ui-corner-all">
			B&uacute;squeda Conciliaciones
      </legend>

      <div class="fila" id="conciliacion_responseMsgs" style="display: none; overflow: auto;"></div>
		
      <div id="dialogConciliacionFilter" title="Consulta" align="center" style="width:1052px;">
         <div class="fila" style="height: 10px;">
            <div class="col_label" style="width: 70px;font-weight:bold;">Proveedor:</div>
            <div class="col_input" style="width: 250px;">
               <html:select property="filtroProveedorList" name="conciliacionForm" styleId="filtroProveedorList"> 
<!--              <html:option value="0"> Sin Proveedor </html:option> -->
                  <html:optionsCollection name="conciliacionForm" property="filtroProveedorList" label="desc" value="cod"/> 
               </html:select>
            </div>
            <div class="col_label" style="width: 50px;font-weight:bold;">Sector:</div>
            <div class="col_input" style="width: 250px;">
               <html:select property="filtroSectorList" name="conciliacionForm" styleId="filtroSectorList"> 
                  <html:option value="0"> Sin Sector </html:option>
	              <html:optionsCollection name="conciliacionForm" property="filtroSectorList" label="desc" value="cod"/> 
               </html:select>
            </div>
            <div class="col_label" style="width: 50px;font-weight:bold;">Per&iacute;odo:</div>
            <div class="col_input" style="width: 250px;">
               <html:select property="filtroPeriodoList" name="conciliacionForm" styleId="filtroPeriodoList"> 
                  <html:option value="0"> Sin Per&iacute;odo </html:option>
	              <html:optionsCollection name="conciliacionForm" property="filtroPeriodoList" label="desc" value="cod"/> 
               </html:select>
            </div>
         </div>
		 <div class="fila" style="height: 10px;">
            <div class="col_label" style="width: 70px;font-weight:bold;">Producto:</div>
            <div class="col_input" style="width: 250px;">
               <html:select property="filtroProductoList" name="conciliacionForm" styleId="filtroProductoList"> 
                  <html:option value="0"> Sin Producto </html:option> -->
	              <html:optionsCollection name="conciliacionForm" property="filtroProductoList" label="desc" value="cod"/> 
               </html:select>
            </div>
			<div class="col_label" style="width: 120px;">Unidad Valoraci&oacute;n:</div>
			<div class="col_input" style="width: 180px;">
				<input type="text" id="nbUniVal" class="input_medium text ui-widget-content ui-corner-all ui-state-disabled" disabled="disabled" style="text-align:left;"/>
			</div>
			<div class="col_label" style="width: 140px;">Valor Monetario U.Val:</div>
			<div class="col_input" style="width: 200px;">
				<input type="text" id="nuValBrutoUniVal" class="input_medium text ui-widget-content ui-corner-all ui-state-disabled" disabled="disabled" style="text-align:center;"/>
			</div>
		 </div>
		 <div class="fila" style="height: 10px;">
            <div class="col_label" style="width: 130px;">Fecha Remito Desde:</div>
            <div class="col_input" style="width: 120px;">
            	<html:text property="fhRemitoDesde" name="conciliacionForm" styleId="fhRemitoDesde" alt="date" size="14" maxlength="10" styleClass="text ui-widget-content ui-corner-all" style="text-align:center"/>
            </div>
            <div class="col_label" style="width: 30px">Hasta:</div>
            <div class="col_input" style="width: 120px;margin-right: 10px;">
	           	<html:text property="fhRemitoHasta" name="conciliacionForm" styleId="fhRemitoHasta" alt="date" size="14" maxlength="10" styleClass="text ui-widget-content ui-corner-all" style="text-align:center"/>
            </div>
            <div class="col_label" style="width: 160px;">Fecha Fin Servicio Desde:</div>
            <div class="col_input" style="width: 120px;">
	           	<html:text property="fhFinServicioDesde" name="conciliacionForm" styleId="fhFinServicioDesde" alt="date" size="14" maxlength="10" styleClass="text ui-widget-content ui-corner-all" style="text-align:center"/>
            </div>
            <div class="col_label" style="width: 30px">Hasta:</div>
            <div class="col_input" style="width: 120px;">
	          	<html:text property="fhFinServicioHasta" name="conciliacionForm" styleId="fhFinServicioHasta" alt="date" size="14" maxlength="10" styleClass="text ui-widget-content ui-corner-all" style="text-align:center"/>
            </div>
         </div>
		 <div class="fila" style="height: 10px;">
           	<div class="col_label" style="width: 160px;">Situaci&oacute;n Conciliaci&oacute;n:</div>
           	<div class="col_input" style="width: 200px;margin-right:70px;">
               <html:select property="filtroSituacionConciliacionList" name="conciliacionForm" styleId="filtroSituacionConciliacionList"> 
                   <html:option value="PEN">Pendiente</html:option>
                   <html:option value="GRA">Grabada sin aprobar</html:option>	              
                   <html:option value="APR">Aprobada</html:option>      
                   <html:option value="ANU">Anulada</html:option>
               </html:select>
            </div>
            <div class="col_label" style="width: 160px;">Nro Conciliaci&oacute;n:</div>
            <div class="col_input" style="width: 200px;margin-left: 10px;">
	           <html:text property="cdConciliacion" name="conciliacionForm" styleId="cdConciliacion" size="70" maxlength="9" alt="999999999" styleClass="text ui-widget-content ui-corner-all" style="text-align:center" />
               <html:select property="filtroConciliacionList" name="conciliacionForm" styleId="filtroConciliacionList">
                   <html:option value="0"> Sin Conciliacion </html:option>
				   <html:optionsCollection name="conciliacionForm" property="filtroConciliacionList" label="desc" value="cod"/>                
			   </html:select>
            </div>
         </div>   
		 <div class="fila" style="height: 10px;">
            <div class="col_label" style="width: 250px;">Conciliar cantidades ignorando valores</div>
            <div class="col_input" style="width: 20px;margin-left: 10px;">
	           	<html:checkbox property="stIgnoraVal" name="conciliacionForm" styleId="stIgnoraVal" styleClass="text ui-widget-content ui-corner-all" style="text-align:right"/>
            </div>
         </div>
		 <div class="fila" id="divMontTot" style="height:10px;display:none;">
            <div class="col_label" style="width: 250px;">
            	El Producto elegido pertenece al grupo "CON_MONT_TOT"
            </div>
         </div>           
         <div class="fila" style="height: 10px;">
            <div class="col_label" style="width: 100px;"></div>
            <div class="col_input" style="width: 20px;margin-left: 10px;">
               <button id="btnBusCons" type="button">&nbsp;&nbsp;Buscar&nbsp;&nbsp;</button>
            </div>
         </div>   
       </div>

		<!-- GRILLA de Conciliaciones-->
		<div class="fila" id="msgEspera" style="display: none; overflow: auto;"></div>
		<div id="conciliacionesGrid" class="fila" style="display: none;">
			<div id="gridConciliaciones" align="left" style="padding-top: 10px;">
				<table id="gridConciliacionesId"></table>
                <div id="gridConciliacionesPager"></div>
			</div>
		</div>

        <p>
        
		<!-- GRILLA de Diferencias-->
		<div id="diferenciasGrid" class="fila" style="display: none;">
			<div id="gridDiferencias" align="left" style="padding-top: 10px;">
				<table id="gridDiferenciasId"></table>
 			    <div id="gridDiferenciasPager"></div>
			</div>
		</div>

        <p>

		<!-- TOTALES-->
		<div id="saldosGrid" class="fila" style="display: none;">
			<div id="gridSaldos" align="left" style="padding-top: 10px;">
				<table id="gridSaldosId"></table>
				<div id="gridSaldosPager"></div>				
			</div>
		</div>
		
        <p>
        
		<div id="divObservaciones" class="fila" style="display: none;">
	        <div class="fila" style="height: 10px;">
	           <div class="col_label" style="width: 160px;">Estado Conciliaci&oacute;n:</div>
	           <div class="col_input" style="width: 100px;">
	              <input type="text" id="nbConciliacion"   class="input_medium text ui-widget-content ui-corner-all ui-state-disabled" size="10" disabled="disabled" style="text-align:left;"/>
	           </div>
	        </div>   
	        <div class="fila" style="height: 10px;">
	           <div class="col_label" style="width: 160px;">Observaciones:</div>
	           <div class="col_input" style="width: 100px;">
	              <textarea cols="80" rows="5" id="observaciones"></textarea>
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
		
		<div id="dialogConsultaServicio" title="Detalle Servicio Prestado" align="center" style="display: none; width: 400">
	       <div class="fila" style="height: 10px;">
	       <div class="col_label" style="width: 250px;">Nro Lote Servicios Prestados:</div>
	          <div class="col_input" style="width: 300px;margin-left: 10px;">
	             <input type="text" id="cdLoteServ" class="input_medium text ui-widget-content ui-corner-all ui-state-disabled" disabled="disabled" style="text-align:center;"/>
	          </div>
	       </div>
	       <div class="fila" style="height: 10px;">
	       <div class="col_label" style="width: 250px;">Nro Orden:</div>
	          <div class="col_input" style="width: 300px;margin-left: 10px;">
	             <input type="text" id="cdOrdenServ" class="input_medium text ui-widget-content ui-corner-all ui-state-disabled" disabled="disabled" style="text-align:center;"/>
	          </div>
	       </div>
	       <div class="fila" style="height: 10px;">
	       <div class="col_label" style="width: 250px;">Sector Solicitante:</div>
	          <div class="col_input" style="width: 300px;margin-left: 10px;">
	             <input type="text" id="cdSectorSol" class="input_medium text ui-widget-content ui-corner-all ui-state-disabled" disabled="disabled" style="text-align:center;"/>
	          </div>
	       </div>
	       <div class="fila" style="height: 10px;">
	       <div class="col_label" style="width: 250px;">Sector de Control:</div>
	          <div class="col_input" style="width: 300px;margin-left: 10px;">
	             <input type="text" id="cdSectorControl" class="input_medium text ui-widget-content ui-corner-all ui-state-disabled" disabled="disabled" style="text-align:center;"/>
	          </div>
	       </div>
	       <div class="fila" style="height: 10px;">
	       <div class="col_label" style="width: 250px;">Remito:</div>
	          <div class="col_input" style="width: 300px;margin-left: 10px;">
	             <input type="text" id="cdRemitoPres" class="input_medium text ui-widget-content ui-corner-all ui-state-disabled" disabled="disabled" style="text-align:center;"/>
	          </div>
	       </div>
	       <div class="fila" style="height: 10px;">
	       <div class="col_label" style="width: 250px;">Fecha Remito:</div>
	          <div class="col_input" style="width: 300px;margin-left: 10px;">
	             <input type="text" id="fhRemito" class="input_medium text ui-widget-content ui-corner-all ui-state-disabled" disabled="disabled" style="text-align:center;"/>
	          </div>
	       </div>
	       <div class="fila" style="height: 10px;">
	       <div class="col_label" style="width: 250px;">Fecha Finalizaci&oacute;n Servicio:</div>
	          <div class="col_input" style="width: 300px;margin-left: 10px;">
	             <input type="text" id="fhFinServ" class="input_medium text ui-widget-content ui-corner-all ui-state-disabled" disabled="disabled" style="text-align:center;"/>
	          </div>
	       </div>
	       <div class="fila" style="height: 10px;">
	       <div class="col_label" style="width: 250px;">Producto:</div>
	          <div class="col_input" style="width: 300px;margin-left: 10px;">
	             <input type="text" id="cdProductoPres" class="input_medium text ui-widget-content ui-corner-all ui-state-disabled" disabled="disabled" style="text-align:center;"/>
	          </div>
	       </div>
	       <div class="fila" style="height: 10px;">
	       <div class="col_label" style="width: 250px;">Cantidad:</div>
	          <div class="col_input" style="width: 300px;margin-left: 10px;">
	             <input type="text" id="ctServPrest" class="input_medium text ui-widget-content ui-corner-all ui-state-disabled" disabled="disabled" style="text-align:center;"/>
	          </div>
	       </div>
	       <div class="fila" style="height: 10px;">
	       <div class="col_label" style="width: 250px;">Unidad Valoraci&oacute;n:</div>
	          <div class="col_input" style="width: 300px;margin-left: 10px;">
	             <input type="text" id="cdUniValPres" class="input_medium text ui-widget-content ui-corner-all ui-state-disabled" disabled="disabled" style="text-align:center;"/>
	          </div>
	       </div>
	       <div class="fila" style="height: 10px;">
	       <div class="col_label" style="width: 250px;">Precio Unitario en U. Val:</div>
	          <div class="col_input" style="width: 300px;margin-left: 10px;">
	             <input type="text" id="imPrecioUnitPres" class="input_medium text ui-widget-content ui-corner-all ui-state-disabled" disabled="disabled" style="text-align:center;"/>
	          </div>
	       </div>
	       <div class="fila" style="height: 10px;">
	       <div class="col_label" style="width: 250px;">Precio Total en U. Val:</div>
	          <div class="col_input" style="width: 300px;margin-left: 10px;">
	             <input type="text" id="imPrecioTotalPres" class="input_medium text ui-widget-content ui-corner-all ui-state-disabled" disabled="disabled" style="text-align:center;"/>
	          </div>
	       </div>
	       <div class="fila" style="height: 10px;">
	       <div class="col_label" style="width: 250px;">Pieza Desde:</div>
	          <div class="col_input" style="width: 300px;margin-left: 10px;">
	             <input type="text" id="nbPiezaDesdePres" class="input_medium text ui-widget-content ui-corner-all ui-state-disabled" disabled="disabled" style="text-align:center;"/>
	          </div>
	       </div>
	       <div class="fila" style="height: 10px;">
	       <div class="col_label" style="width: 250px;">Pieza Hasta:</div>
	          <div class="col_input" style="width: 300px;margin-left: 10px;">
	             <input type="text" id="nbPiezaHastaPres" class="input_medium text ui-widget-content ui-corner-all ui-state-disabled" disabled="disabled" style="text-align:center;"/>
	          </div>
	       </div>
	       <div class="fila" style="height: 10px;">
	       <div class="col_label" style="width: 250px;">Remito Padre:</div>
	          <div class="col_input" style="width: 300px;margin-left: 10px;">
	             <input type="text" id="cdRemitoPadre" class="input_medium text ui-widget-content ui-corner-all ui-state-disabled" disabled="disabled" style="text-align:center;"/>
	          </div>
	       </div>
	       <div class="fila" style="height: 10px;">
	       <div class="col_label" style="width: 250px;">Cotización :</div>
	          <div class="col_input" style="width: 300px;margin-left: 10px;">
	             <input type="text" id="nbAtributoRef1" class="input_medium text ui-widget-content ui-corner-all ui-state-disabled" disabled="disabled" style="text-align:center;"/>
	          </div>
	       </div>
	       <div class="fila" style="height: 10px;">
	       <div class="col_label" style="width: 250px;">Fecha Cotiz:</div>
	          <div class="col_input" style="width: 300px;margin-left: 10px;">
	             <input type="text" id="nbAtributoRef2" class="input_medium text ui-widget-content ui-corner-all ui-state-disabled" disabled="disabled" style="text-align:center;"/>
	          </div>
	       </div>
	       <div class="fila" style="height: 10px;">
	       <div class="col_label" style="width: 250px;">Observaciones:</div>
	          <div class="col_input" style="width: 300px;margin-left: 10px;">
	             <input type="text" id="nbObservaciones" class="input_medium text ui-widget-content ui-corner-all ui-state-disabled" disabled="disabled" style="text-align:center;"/>
	          </div>
	       </div>
	       <div class="fila" style="height: 10px;">
	       <div class="col_label" style="width: 250px;">Estado:</div>
	          <div class="col_input" style="width: 300px;margin-left: 10px;">
	             <input type="text" id="stLoteDet" class="input_medium text ui-widget-content ui-corner-all ui-state-disabled" disabled="disabled" style="text-align:center;"/>
	          </div>
	       </div>
	       <div class="fila" style="height: 10px;">
	       <div class="col_label" style="width: 250px;">Nro Conciliaci&oacute;n:</div>
	          <div class="col_input" style="width: 300px;margin-left: 10px;">
	             <input type="text" id="cdConciliacionPres" class="input_medium text ui-widget-content ui-corner-all ui-state-disabled" disabled="disabled" style="text-align:center;"/>
	          </div>
	       </div>
	       <div class="fila" style="height: 10px;">
	       <div class="col_label" style="width: 250px;">Fecha &uacute;ltima Modificaci&oacute;n:</div>
	          <div class="col_input" style="width: 300px;margin-left: 10px;">
	             <input type="text" id="fhModificacion" class="input_medium text ui-widget-content ui-corner-all ui-state-disabled" disabled="disabled" style="text-align:center;"/>
	          </div>
	       </div>
	       <div class="fila" style="height: 10px;">
	       <div class="col_label" style="width: 250px;">Usuario &uacute;ltima Modificaci&oacute;n:</div>
	          <div class="col_input" style="width: 300px;margin-left: 10px;">
	             <input type="text" id="nbUsuarioModif" class="input_medium text ui-widget-content ui-corner-all ui-state-disabled" disabled="disabled" style="text-align:center;"/>
	          </div>
	       </div>
		</div>
	
<!-- 	DIALOGO DE EDITAR -->
        <div id="dialogDiferenciaConciliacion" align="center" title="Editar Diferencia de Conciliaci&oacute;n" style="display: none;">
		   <div id="responseMessageToEdit" style="display:none;width: 300px;overflow:auto;"></div>
           <div class="fila" style="height: 10px;">
              <div class="col_label" style="width: 120px;">Sit. Soluci&oacute;n:</div>
              <div class="col_input" style="width: 100px;">
                 <select id="solucionDifListEdit"> 
                    <option value="0">&nbsp;</option>
                    <option value="PENSOL"> Pendiente Soluci&oacute;n</option>
                    <option value="SINAJU"> Sol. no genera Ajuste</option>	              
                    <option value="AJUSTE"> Sol. genera Ajuste</option>
                 </select>
              </div>
           </div>
	       <div class="fila" style="height: 10px;">
	       <div class="col_label" style="width: 120px;">Remito:</div>
	          <div class="col_input" style="width: 100px;">
	             <input type="text" id="cdRemitoEdit" class="input_medium text ui-widget-content ui-corner-all"  size="10" maxlength="14" style="text-align:center;"/>
	          </div>
	       </div>
	       <div class="fila" style="height: 10px;">
			  <div class="col_label" style="width: 120px;">Pieza Desde: </div>
			  <div class="col_input" style="width: 100px;">
				 <input type="text" id="nbPiezaDesdeEdit" class="text ui-widget-content ui-corner-all" size="25" maxlength="30" />
			  </div>
		   </div>
	       <div class="fila" style="height: 10px;">
			  <div class="col_label" style="width: 120px;">Pieza Hasta: </div>
			  <div class="col_input" style="width: 100px;">
				 <input type="text" id="nbPiezaHastaEdit" class="text ui-widget-content ui-corner-all" size="25" maxlength="30" />
			  </div>
		   </div>
	       <div class="fila" style="height: 10px;">
			  <div class="col_label" style="width: 120px;">Diferencia Cantidad: </div>
			  <div class="col_input" style="width: 100px;">
				 <input type="text" id="ctDiferenciaEdit" style="text-align:right;" class="text ui-widget-content ui-corner-all" size="15" maxlength="12" onblur="extractNumber(this,0,true);" onkeyup="extractNumber(this,0,true);" onkeypress="return blockNonNumbers(this, event, true, true);" />
			  </div>
		   </div>
	       <div class="fila" style="height: 10px;">
			  <div class="col_label" style="width: 120px;">Diferencia Valor: </div>
			  <div class="col_input" style="width: 100px;">
			  <!-- /// <input type="text" id="imDiferenciaEdit" style="text-align:right;" class="text ui-widget-content ui-corner-all" size="15" maxlength="12" onblur="extractNumber(this,2,true);" onkeyup="extractNumber(this,2,true);" onkeypress="return blockNonNumbers(this, event, true, true);" /> -->
				 <input type="text" id="imDiferenciaEdit" style="text-align:right;" class="text ui-widget-content ui-corner-all" size="15" maxlength="12" onblur="extractNumberComa(this,2,true);" onkeyup="extractNumberComa(this,2,true);" onkeypress="return blockNonNumbersComa(this, event, true, true);" />
			  </div>
		   </div>
           <div class="fila" style="height: 20px;">
              <div class="col_label" style="width: 120px;">Estado:</div>
              <div class="col_input" style="width: 100px;">
                 <select id="stDiferenciaEdit"> 
                  	<option value="ACT"> Activa </option>
                  	<option value="ANU"> Anulada </option>	              
               	 </select>
	          </div>
           </div>  
           <div class="fila" style="height: 20px;">
              <div class="col_label" style="text-align: left !important;width:120px;">Observaciones: </div>
              <div class="col_input" style="width: 300px;">
                 <textarea cols="45" rows="5" id="obsDifConciliaEdit" maxlength="180" class="input_large text ui-widget-content ui-corner-all;"></textarea>
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
<!--   	         <button id="btnRepetidos" type="button" title="Repetidos">Repetidos</button>  -->  
			     <button id="btnImprimir" type="button" title="Imprimir" form="exportDataForm">Imprimir</button>  
			     <button id="btnExportar" type="button" title="Exportar a Excel" form="exportDataForm">Exportar</button>
                 <button id="btnLimpiar" type="button" title="Limpiar">Limpiar</button> 
		</div>
	</div>
	
    <div id="dialogRemitosRepetidos" title="Consulta de Remitos Repetidos" align="center" style="display: none;">
		<!-- GRILLA de Repetidos-->
		<div class="fila" id="msgRepetidos" style="overflow: auto;">
		    Atenci&oacute;n: Se han detectado Remitos facturados m&aacute;s de una vez para el mismo producto
		</div>
		<div id="repetidosGrid" class="fila">
			<div id="gridRepetidos" align="left" style="padding-top: 10px;">
				<table id="gridRepetidosId"></table>
				<div id="gridRepetidosPager"></div>
			</div>
		</div>        
	</div>
	
    <br>
</html:form>
<script src="js/conciliacion/conciliacionesRemitosRepetidos.js"></script>
<script src="js/conciliacion/conciliacionesSaldosGrid.js"></script>
<script src="js/conciliacion/conciliacionesDiferenciasGrid.js"></script>
<script src="js/conciliacion/conciliacionesConciliacionesGrid.js"></script>
<script src="js/conciliacion/conciliacionesFunciones.js"></script>
<script src="js/conciliacion/conciliacionesExportacion.js"></script>
<script src="js/conciliacion/conciliacionesFiltros.js"></script>
<script src="js/conciliacion/conciliaciones.js"></script>

<script type="text/javascript" src="js/exports/numberConvert.js"></script>

<script src="js/exports/exportDataGrid.js"></script>