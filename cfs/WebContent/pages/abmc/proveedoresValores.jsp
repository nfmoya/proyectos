<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  

<html:form action="/ABMCProveedoresValores">
	<html:hidden name="proveedorValorForm" property="addGrant"    styleId="addGrantPVAL" />
	<html:hidden name="proveedorValorForm" property="editGrant"   styleId="editGrantPVAL" />
	<html:hidden name="proveedorValorForm" property="deleteGrant" styleId="deleteGrantPVAL" />
	<input type="hidden" id="selectGrant" value="S"/>
	<input type="hidden" id="tipoModificacion" value=""/>	
	<input type="hidden" id="periodo" value=""/>	
	
		<!-- GRILLA de Proveedores-->
		<div class="fila" id="msgEspera" style="display: none; overflow: auto;"></div>
		<div id="proveedoresValoresGrid" class="fila" style="display: none;">
			<div id="gridProveedoresValores" align="left" style="padding-top: 10px;">
				<table id="gridProveedoresValoresId"></table>
				<div id="gridProveedoresValoresPager"></div>
			</div>
		</div>
		
		<div id="dialogEditProveedorValor" title="Valor Proveedor" align="center" style="display: none; width: 400">
			<div class="fila" id="msgConfirmacion" style="display: none; overflow: auto;"></div>
			
      		<div class="fila" id="prov_valor_responseMsgs" style="display: none; overflow: auto;"></div>
				
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 260px;">C&oacute;digo Proveedor: * </div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<html:select property="proveedorValorList" name="proveedorValorForm" styleId="proveedorValorList"> 
						<html:option value="0"> Sin Proveedor &nbsp;</html:option>
						<html:optionsCollection name="proveedorValorForm" property="proveedorValorList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 260px;">C&oacute;digo Per&iacute;odo Facturaci&oacute;n: * </div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<html:select property="periodoValorList" name="proveedorValorForm" styleId="periodoValorList"> 
						<html:option value="0"> Sin Per&iacute;odo &nbsp;</html:option>
						<html:optionsCollection name="proveedorValorForm" property="periodoValorList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 260px;">C&oacute;digo Unidad Valoraci&oacute;n: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<html:select property="uniValList" name="proveedorValorForm" styleId="uniValList"> 
						<html:option value="0">&nbsp;</html:option>
						<html:optionsCollection name="proveedorValorForm" property="uniValList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
			
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 260px;">Valor Bruto de la U.Val. en $: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="nuValBrutoUniVal" class="text ui-widget-content ui-corner-all" size="15" maxlength="15" onblur="extractNumberComa(this,4,true);" onkeyup="extractNumberComa(this,4,true);" onkeypress="return blockNonNumbersComa(this, event, true, true);" value="<c:out value="${proveedorValorForm.nuValBrutoUniVal}"/>" />
				</div>
			</div>

			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 260px;">Valor Neto de la U.Val. en $: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="nuValNetoUniVal" class="text ui-widget-content ui-corner-all" size="15" maxlength="15" onblur="extractNumberComa(this,4,true);" onkeyup="extractNumberComa(this,4,true);" onkeypress="return blockNonNumbersComa(this, event, true, true);" value="<c:out value="${proveedorValorForm.nuValNetoUniVal}"/>" />
				</div>
			</div>
			
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 260px;">Habilitado: * </div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<html:select property="habilitadoValorList" name="proveedorValorForm" styleId="habilitadoValorList"> 
						<html:optionsCollection name="proveedorValorForm" property="habilitadoValorList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
		</div>
</html:form>

<script src="js/abmc/proveedoresValores.js"></script>


