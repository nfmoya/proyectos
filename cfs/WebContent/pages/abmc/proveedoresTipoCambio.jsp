<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  

<html:form action="/ABMCProveedoresTipoCambio">
	<html:hidden name="proveedorTipoCambioForm" property="addGrant"    styleId="addGrantTC" />
	<html:hidden name="proveedorTipoCambioForm" property="editGrant"   styleId="editGrantTC" />
	<html:hidden name="proveedorTipoCambioForm" property="deleteGrant" styleId="deleteGrantTC" />
	<input type="hidden" id="selectGrant" value="S"/>
	<input type="hidden" id="tipoModificacion" value=""/>	
	<input type="hidden" id="periodo" value=""/>	
	
	<!-- GRILLA de Proveedores-->
	<div class="fila" id="msgEspera" style="display: none; overflow: auto;"></div>
	<div id="proveedoresTipoCambioGrid" class="fila" style="display: none;">
		<div id="gridProveedoresTipoCambio" align="left" style="padding-top: 10px;">
			<table id="gridProveedoresTipoCambioId"></table>
			<div id="gridProveedoresTipoCambioPager"></div>
		</div>
	</div>
		
	<div id="dialogEditProveedorTipoCambio" title="TipoCambio Periodo" align="center" style="display: none; width: 400">
		<div class="fila" id="msgConfirmacion" style="display: none; overflow: auto;"></div>
		
    	<div class="fila" id="prov_tipocambio_responseMsgs" style="display: none; overflow: auto;"></div>
				
		<div class="fila" style="height: 10px;">
			<div class="col_label" style="width: 260px;">C&oacute;digo Proveedor: * </div>
			<div class="col_input" style="width: 200px;margin-left: 10px;">
				<html:select property="proveedorTipoCambioList" name="proveedorTipoCambioForm" styleId="proveedorTipoCambioList"> 
					<html:option value="0"> Sin Proveedor &nbsp;</html:option>
					<html:optionsCollection name="proveedorTipoCambioForm" property="proveedorTipoCambioList" label="desc" value="cod"/> 
				</html:select>
			</div>
		</div>
		<div class="fila" style="height: 10px;">
			<div class="col_label" style="width: 260px;">C&oacute;digo Per&iacute;odo Facturaci&oacute;n: * </div>
			<div class="col_input" style="width: 200px;margin-left: 10px;">
				<html:select property="periodoTipoCambioList" name="proveedorTipoCambioForm" styleId="periodoTipoCambioList"> 
					<html:option value="0"> Sin Per&iacute;odo &nbsp;</html:option>
					<html:optionsCollection name="proveedorTipoCambioForm" property="periodoTipoCambioList" label="desc" value="cod"/> 
				</html:select>
			</div>
		</div>
		<div class="fila" style="height: 10px;">
			<div class="col_label" style="width: 260px;">Moneda: *</div>
			<div class="col_input" style="width: 200px;margin-left: 10px;">
				<html:select property="monedaTipoCambioList" name="proveedorTipoCambioForm" styleId="monedaTipoCambioList">
					<html:option value="0">&nbsp;</html:option>					 
					<html:optionsCollection name="proveedorTipoCambioForm" property="monedaTipoCambioList" label="desc" value="cod"/> 
				</html:select>
			</div>
		</div>
		<div class="fila" style="height: 10px;">
			<div class="col_label" style="width: 260px;">+- Días de la fecha de Referencia: *</div>
			<div class="col_input" style="width: 200px;margin-left: 10px;">
				<input type="text" id="nuDias" class="text ui-widget-content ui-corner-all" size="10" maxlength="10" onblur="extractNumberComa(this,0,true);" onkeyup="extractNumberComa(this,0,true);" onkeypress="return blockNonNumbersComa(this, event, true, true);" value="<c:out value="${proveedorTipoCambioForm.nuDias}"/>" />
			</div>
		</div>
		<div class="fila" style="height: 10px;">
			<div class="col_label" style="width: 260px;">Cotización: * </div>
			<div class="col_input" style="width: 200px;margin-left: 10px;">
				<html:select property="cotizacionTipoCambioList" name="proveedorTipoCambioForm" styleId="cotizacionTipoCambioList"> 
					<html:optionsCollection name="proveedorTipoCambioForm" property="cotizacionTipoCambioList" label="desc" value="cod"/> 
				</html:select>
			</div>
		</div>
		<div class="fila" style="height: 10px;">
			<div class="col_label" style="width: 260px;">Habilitado: * </div>
			<div class="col_input" style="width: 200px;margin-left: 10px;">
				<html:select property="habilitadoTipoCambioList" name="proveedorTipoCambioForm" styleId="habilitadoTipoCambioList"> 
					<html:optionsCollection name="proveedorTipoCambioForm" property="habilitadoTipoCambioList" label="desc" value="cod"/> 
				</html:select>
			</div>
		</div>
	</div>
</html:form>

<script src="js/abmc/proveedoresTipoCambio.js"></script>

