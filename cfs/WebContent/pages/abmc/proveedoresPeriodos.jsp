<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  

<html:form action="/ABMCProveedoresPeriodos">
	<html:hidden name="proveedorPeriodoForm" property="addGrant"    styleId="addGrantPVP" />
	<html:hidden name="proveedorPeriodoForm" property="editGrant"   styleId="editGrantPVP" />
	<html:hidden name="proveedorPeriodoForm" property="deleteGrant" styleId="deleteGrantPVP" />
	<input type="hidden" id="selectGrant" value="S"/>
	<input type="hidden" id="tipoModificacion" value=""/>	
		
		<!-- GRILLA de Proveedores-->
		<div class="fila" id="msgEspera" style="display: none; overflow: auto;"></div>
		<div id="proveedoresPeriodosGrid" class="fila" style="display: none;">
			<div id="gridProveedoresPeriodos" align="left" style="padding-top: 10px;">
				<table id="gridProveedoresPeriodosId"></table>
				<div id="gridProveedoresPeriodosPager"></div>
			</div>
		</div>
		
		<div id="dialogEditProveedorPeriodo" title="Per&iacute;odo" align="center" style="display: none; width: 400">
			<div class="fila" id="msgConfirmacion" style="display: none; overflow: auto;"></div>
     		
     		<div class="fila" id="prov_per_responseMsgs" style="display: none; overflow: auto;"></div>

			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 260px;">C&oacute;digo Proveedor: * </div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<html:select property="proveedorPeriodoList" name="proveedorPeriodoForm" styleId="proveedorPeriodoList"> 
						<html:optionsCollection name="proveedorPeriodoForm" property="proveedorPeriodoList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 260px;">C&oacute;digo Per&iacute;odo Facturaci&oacute;n: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="cdPeriodoFact" class="text ui-widget-content ui-corner-all" size="6" maxlength="6" value="<c:out value="${proveedorPeriodoForm.cdPeriodoFact}"/>" />
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 260px;">Descripci&oacute;n: * </div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="nbPeriodoFact" class="text ui-widget-content ui-corner-all" size="20" maxlength="20" value="<c:out value="${proveedorPeriodoForm.nbPeriodoFact}"/>" />
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 260px;">C&oacute;digo Alternativo:</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="cdPerFactAlt" class="text ui-widget-content ui-corner-all" size="20" maxlength="20" value="<c:out value="${proveedorPeriodoForm.cdPerFactAlt}"/>" />
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 260px;">Fecha Desde:</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="fhDesde" class="text ui-widget-content ui-corner-all" alt="date"  size="12" maxlength="10" value="<c:out value="${proveedorPeriodoForm.fhDesde}"/>" />
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 260px;">Fecha Hasta:</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="fhHasta" class="text ui-widget-content ui-corner-all" alt="date" size="12" maxlength="10" value="<c:out value="${proveedorPeriodoForm.fhHasta}"/>" />
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 260px;">Estado: * </div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<html:select property="estadoList" name="proveedorPeriodoForm" styleId="estadoList"> 
						<html:optionsCollection name="proveedorPeriodoForm" property="estadoList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
		</div>
</html:form>

<script src="js/abmc/proveedoresPeriodos.js"></script>


