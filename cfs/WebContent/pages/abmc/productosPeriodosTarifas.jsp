<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  

<html:form action="/ABMCProductosPeriodosTarifas">
	<html:hidden name="productoPeriodoTarifaForm" property="addGrant"    styleId="addGrantTAR" />
	<html:hidden name="productoPeriodoTarifaForm" property="editGrant"   styleId="editGrantTAR" />
	<html:hidden name="productoPeriodoTarifaForm" property="deleteGrant" styleId="deleteGrantTAR" />
	<input type="hidden" id="selectGrant" value="S"/>
	<input type="hidden" id="tipoModificacion" value=""/>
	<input type="hidden" id="producto" value=""/>

		<!-- GRILLA de Productos-->
		<div class="fila" id="msgEspera" style="display: none; overflow: auto;"></div>
		<div id="productosPeriodosTarifasGrid" class="fila" style="display: none;">
			<div id="gridProductosPeriodosTarifas" align="left" style="padding-top: 10px;">
				<table id="gridProductosPeriodosTarifasId"></table>
				<div id="gridProductosPeriodosTarifasPager"></div>
			</div>
		</div>
		
		<div id="dialogEditProductoTarifa" title="Periodo Producto" align="center" style="display: none;">
			<div class="fila" id="msgConfirmacion" style="display: none; overflow: auto;"></div>
				
			<div class="fila" id="prod_Periodo_responseMsgs" style="display: none; overflow: auto;"></div>
	
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 260px;">C&oacute;digo Proveedor: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<html:select property="proveedorProductoTarifaList" name="productoPeriodoTarifaForm" styleId="proveedorProductoTarifaList" > 
						<html:option value="0"> Sin Proveedor &nbsp;</html:option>
						<html:optionsCollection name="productoPeriodoTarifaForm" property="proveedorProductoTarifaList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 260px;">C&oacute;digo Producto: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<html:select property="productoTarifaList" name="productoPeriodoTarifaForm" styleId="productoTarifaList" > 
						<html:option value="0"> Sin Producto &nbsp;</html:option>
						<html:optionsCollection name="productoPeriodoTarifaForm" property="productoTarifaList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 260px;">Tipo de Valorizaci&oacute;n: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<html:select property="productoTipValList" name="productoPeriodoTarifaForm" styleId="productoTipValList"> 
						<html:option value="0">  &nbsp; Sin Tip Val &nbsp;</html:option>
						<html:optionsCollection name="productoPeriodoTarifaForm" property="productoTipValList" label="desc" value="cod"/> 
					</html:select>					
				</div>
			</div>
			<div id="disableFechas">
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 260px;">Fecha Desde: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="fhDesdeTarifa" class="text ui-widget-content ui-corner-all" alt="date" size="12" maxlength="10" value="<c:out value="${productoPeriodoTarifaForm.fhDesde}"/>"  />
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 260px;">Fecha Hasta: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="fhHastaTarifa" class="text ui-widget-content ui-corner-all" alt="date" size="12" maxlength="10" value="" >
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 260px;">Cantidad Desde: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="nuCantDesde" class="text ui-widget-content ui-corner-all" size="12" maxlength="10" value="<c:out value="${productoPeriodoTarifaForm.nuCantDesde}"/>"  />
				</div>
			</div>
			<div class="fila" style="height: 10px;" >
				<div class="col_label" style="width: 260px;">Cantidad Hasta: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="nuCantHasta" class="text ui-widget-content ui-corner-all" size="12" maxlength="10" onblur="extractNumberComa(this,0,true);" onkeyup="extractNumberComa(this,0,true);" onkeypress="return blockNonNumbersComa(this, event, true, true);" value="<c:out value="${productoPeriodoTarifaForm.nuCantHasta}"/>" />
				</div>
			</div>
			<div class="fila" style="height: 10px;" id="divPrecioUniVal">
				<div class="col_label" style="width: 260px;">Tarifa en Unidades de Valoraci&oacute;n: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="nuPrecioUniVal" class="text ui-widget-content ui-corner-all" size="16" maxlength="16" onblur="extractNumberComa(this,4,true);" onkeyup="extractNumberComa(this,4,true);" onkeypress="return blockNonNumbersComa(this, event, true, true);" value="<c:out value="${productoPeriodoTarifaForm.nuPrecioUniVal}"/>" />
				</div>
			</div>
			<div class="fila" style="height: 10px;" id="divPorcTarifa">
				<div class="col_label" style="width: 260px;">Tarifa en % a aplicar: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="nuPorcTarifa" class="text ui-widget-content ui-corner-all" size="10" maxlength="10" onblur="extractNumberComa(this,2,true);" onkeyup="extractNumberComa(this,2,true);" onkeypress="return blockNonNumbersComa(this, event, true, true);" value="<c:out value="${productoPeriodoTarifaForm.nuPorcTarifa}"/>" />
				</div>
			</div>
			<div class="fila" style="height: 10px;" id="divHabilitado">
				<div class="col_label" style="width: 260px;">Habilitado: </div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<html:select property="habilitadoProductoTarifaList" name="productoPeriodoTarifaForm" styleId="habilitadoProductoTarifaList"> 
						<html:optionsCollection name="productoPeriodoTarifaForm" property="habilitadoProductoTarifaList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
		    <div class="fila" style="height: 10px;" id="divPrecioFijo">
	            <div class="col_label" style="width: 250px;">Precio Fijo</div>
	            <div class="col_input" style="width: 20px;margin-left: 10px;">
		           	<html:checkbox property="stPrecioFijo" name="productoPeriodoTarifaForm" styleId="stPrecioFijo" styleClass="text ui-widget-content ui-corner-all" style="text-align:right"/>
	            </div>
	        </div>			
		</div>
</html:form>

<script src="js/abmc/productosPeriodosTarifas.js"></script>

