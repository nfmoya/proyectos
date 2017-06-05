<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  

<html:form action="/ABMCProductosPrecios">
	<html:hidden name="productoPrecioForm" property="addGrant"    styleId="addGrantPP" />
	<html:hidden name="productoPrecioForm" property="editGrant"   styleId="editGrantPP" />
	<html:hidden name="productoPrecioForm" property="deleteGrant" styleId="deleteGrantPP" />
	<input type="hidden" id="selectGrant" value="S"/>
	<input type="hidden" id="tipoModificacion" value=""/>
	<input type="hidden" id="producto" value=""/>

		<!-- GRILLA de Productos-->
		<div class="fila" id="msgEspera" style="display: none; overflow: auto;"></div>
		<div id="productosPreciosGrid" class="fila" style="display: none;">
			<div id="gridProductosPrecios" align="left" style="padding-top: 10px;">
				<table id="gridProductosPreciosId"></table>
				<div id="gridProductosPreciosPager"></div>
			</div>
		</div>
		
		<div id="dialogEditProductoPrecio" title="Precio Producto" align="center" style="display: none;">
			<div class="fila" id="msgConfirmacion" style="display: none; overflow: auto;"></div>
				
			<div class="fila" id="prod_precio_responseMsgs" style="display: none; overflow: auto;"></div>
	
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 260px;">C&oacute;digo Proveedor: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<html:select property="proveedorProductoPrecioList" name="productoPrecioForm" styleId="proveedorProductoPrecioList"> 
						<html:option value="0"> Sin Proveedor &nbsp;</html:option>
						<html:optionsCollection name="productoPrecioForm" property="proveedorProductoPrecioList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 260px;">C&oacute;digo Producto: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<html:select property="productoPrecioList" name="productoPrecioForm" styleId="productoPrecioList"> 
						<html:option value="0"> Sin Producto &nbsp;</html:option>
						<html:optionsCollection name="productoPrecioForm" property="productoPrecioList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
			<div id="disableFechas">
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 260px;">Fecha Desde: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="fhDesdeProdu" class="text ui-widget-content ui-corner-all" alt="date" size="12" maxlength="10" value="<c:out value="${productoPrecioForm.fhDesde}"/>" />
				</div>
			</div>
			<div class="fila" style="height: 10px;" >
				<div class="col_label" style="width: 260px;">Fecha Hasta: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="fhHastaProdu" class="text ui-widget-content ui-corner-all" alt="date" size="12" maxlength="10" value="<c:out value="${productoPrecioForm.fhHasta}"/>" />
				</div>
			</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 260px;">Tarifa en Unidades de Valoraci&oacute;n: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="nuPrecioUniVal" class="text ui-widget-content ui-corner-all" size="16" maxlength="16" onblur="extractNumberComa(this,4,true);" onkeyup="extractNumberComa(this,4,true);" onkeypress="return blockNonNumbersComa(this, event, true, true);" value="<c:out value="${productoPrecioForm.nuPrecioUniVal}"/>" />
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 260px;">Habilitado: </div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<html:select property="habilitadoProductoPrecioList" name="productoPrecioForm" styleId="habilitadoProductoPrecioList"> 
						<html:optionsCollection name="productoPrecioForm" property="habilitadoProductoPrecioList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
		</div>
</html:form>

<script src="js/abmc/productosPrecios.js"></script>

