<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  

<style type="text/css" media="screen">
    th.ui-th-column div{
        white-space:normal !important;
        height:auto !important;
        padding:2px;
    }
</style>
<html:form action="/ABMCProveedoresBasicos">
	<html:hidden name="proveedorForm" property="addGrant"    styleId="addGrant" />
	<html:hidden name="proveedorForm" property="editGrant"   styleId="editGrant" />
	<html:hidden name="proveedorForm" property="deleteGrant" styleId="deleteGrant" />
	<input type="hidden" id="selectGrant" value="S"/>
	<input type="hidden" id="tipoModificacion" value=""/>	

    <br>	
		<!-- GRILLA de Proveedores-->
		<div class="fila" id="msgEspera" style="display: none; overflow: auto;"></div>
		<div id="proveedoresBasicosGrid" class="fila" style="display: none;">
			<div id="gridProveedoresBasicos" align="left" style="padding-top: 10px;">
				<table id="gridProveedoresBasicosId"></table>
				<div id="gridProveedoresBasicosPager"></div>
			</div>
		</div>
		
		<div id="dialogEditProveedor" title="Proveedor" align="center" style="display:none;width:500">
			<div class="fila" id="msgConfirmacion" style="display: none; overflow: auto;"></div>
			
			<div class="fila" id="prov_basic_responseMsgs" style="display: none; overflow: auto;"></div>
				
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 200px;">C&oacute;digo Proveedor: *</div>
				<div class="col_input" style="width: 160px;margin-left: 10px;">
					<input type="text" id="cdProveedor" class="text ui-widget-content ui-corner-all" size="7" maxlength="6" value="<c:out value="${proveedorForm.cdProveedor}"/>" />
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 200px;">Nombre o Raz&oacute;n Social: *</div>
				<div class="col_input" style="width: 160px;margin-left: 10px;">
					<input type="text" id="nbProveedor" class="text ui-widget-content ui-corner-all" size="31" maxlength="30" value="<c:out value="${proveedorForm.nbProveedor}"/>" />
				</div>
			</div>

			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 200px;">Descripci&oacute;n Corta: *</div>
				<div class="col_input" style="width: 160px;margin-left: 10px;">
					<input type="text" id="nbProveedorCorto" class="text ui-widget-content ui-corner-all" size="13" maxlength="12" value="<c:out value="${proveedorForm.nbProveedorCorto}"/>" />
				</div>
			</div>
			
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 200px;">CUIT: *</div>
				<div class="col_input" style="width: 160px;margin-left: 10px;">
					<input type="text" id="nuCuit" class="text ui-widget-content ui-corner-all" size="13" maxlength="11" onkeypress="return isNumber(event)" value="<c:out value="${proveedorForm.nuCuit}"/>" />
				</div>
			</div>

			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 200px;">Informa Precio Unitario? (S/N)</div>
				<div class="col_input" style="width: 160px;margin-left: 10px;">
					<html:select property="nbAtributoProv1" name="proveedorForm" styleId="nbAtributoProv1"> 
						<html:optionsCollection name="proveedorForm" property="nbAtributoProv1List" label="desc" value="cod"/> 
					</html:select>
					
				</div>
			</div>
			
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 200px;">Suma Cant/importe? (S/N)</div>
				<div class="col_input" style="width: 160px;margin-left: 10px;">
					<html:select property="nbAtributoProv2" name="proveedorForm" styleId="nbAtributoProv2"> 
						<html:optionsCollection name="proveedorForm" property="nbAtributoProv2List" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
			
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 200px;">Redondeo P/Medibles:</div>
				<div class="col_input" style="width: 160px;margin-left: 10px;">####,##
					<input type="text" id="nbAtributoProv3" onkeyup="extractNumberComa(this,'2','false')" onchange="validateAttr('nbAtributoProv3')" class="text ui-widget-content ui-corner-all" size="13" maxlength="7" value="<c:out value="${proveedorForm.nbAtributoProv3}"/>" /> 
				</div>
			</div>
			
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 200px;">Redondeo P/No Medibles:</div>
				<div class="col_input" style="width: 160px;margin-left: 10px;">####,##
					<input type="text" id="nbAtributoProv4" onkeyup="extractNumberComa(this,'2','false')" onchange="validateAttr('nbAtributoProv4')" class="text ui-widget-content ui-corner-all" size="13" maxlength="7" value="<c:out value="${proveedorForm.nbAtributoProv4}"/>" />
				</div>
			</div>
			
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 200px;">Atributo Adicional 5:</div>
				<div class="col_input" style="width: 160px;margin-left: 10px;">
					<input type="text" id="nbAtributoProv5" class="text ui-widget-content ui-corner-all" size="30" maxlength="30" value="<c:out value="${proveedorForm.nbAtributoProv5}"/>" />
				</div>
			</div>
			
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 200px;">Habilitado: * </div>
				<div class="col_input" style="width: 160px;margin-left: 10px;">
					<html:select property="habilitadoList" name="proveedorForm" styleId="habilitadoListProv"> 
						<html:optionsCollection name="proveedorForm" property="habilitadoList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
			
		</div>
</html:form>

<script src="js/abmc/proveedoresBasicos.js"></script>
<script src="js/decimalNumbers.js"></script>
<script type="text/javascript">
function validateAttr(idInput){
	var campo = $('#'+idInput).val();
	var arr = campo.indexOf(",");
	if(arr == -1 ){
		if(campo.length > 3){
			var parteDec = campo.substring((campo.length -2), campo.length);
			var parteEntera = campo.substring(0, (campo.length -2));
			$('#'+idInput).val(parteEntera+","+parteDec);
		}else{
			var parteDec = campo.substring((campo.length -2), campo.length);
			var parteEntera = campo.substring(0, (campo.length -2));
			$('#'+idInput).val("0,"+parteDec);
		}
	}
}

</script>

