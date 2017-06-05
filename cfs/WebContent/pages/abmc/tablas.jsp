<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  

<html:form action="/ABMCTablasGenerales">
	<html:hidden name="tablaForm" property="addGrant"    styleId="addGrant" />
	<html:hidden name="tablaForm" property="editGrant"   styleId="editGrant" />
	<html:hidden name="tablaForm" property="deleteGrant" styleId="deleteGrant" />
	<input type="hidden" id="selectGrant" value="S"/>	
	<input type="hidden" id="tipoModificacion" value=""/>
	
	<!-- FILTRO de Tablas-->
	<fieldset id="tablasFilter" class="ui-widget ui-widget-content ui-corner-all" style="${width_filter}">
		<legend id="tablas_filter_legend" class="ui-widget ui-widget-header ui-corner-all">
			Administraci&oacute;n de Tablas
		</legend>
	
		<div class="fila" id="tabla_responseMsgs" style="display: none; overflow: auto;"></div>
		
	    <div id="dialogTablaFilter" title="Consulta" align="center">
		   <div class="fila">
              <div class="col_label">Tabla:</div>
              <div class="col_input">
					<html:select property="filtroTablaList" name="tablaForm" styleId="filtroTablaList" onchange="search();cleanMsgConfirmation();"> 
						<html:option value="0"> &nbsp; Sin Tabla Seleccionada &nbsp;</html:option>  
						<html:optionsCollection name="tablaForm" property="filtroTablaList" label="desc" value="cod"/> 
					</html:select>
              </div>
<!--          <button id="btnBusCons" type="button">Buscar</button>  -->
        </div>
	
		<!-- GRILLA de Tablas-->
		<div class="fila" id="msgEspera" style="display: none; overflow: auto;"></div>
		<div id="tablasGrid" class="fila" style="display: none;">
			<div id="gridTablas" align="left" style="padding-top: 10px;">
				<table id="gridTablasId"></table>
				<div id="gridTablasPager"></div>
			</div>
		</div>
		
		<div id="dialogEditTabla" title="Tabla" align="center" style="display: none; width: 400">
			<div class="fila" id="msgConfirmacion" style="display: none; overflow: auto;"></div>
				
			<div class="fila" id="tabla_diag_responseMsgs" style="display: none; overflow: auto;"></div>
		
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">Tabla: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
<!-- 				<input type="text" id="cdTabla" class="text ui-widget-content ui-corner-all" value="<c:out value="${tablaForm.cdTabla}"/>" />  -->
					<html:select property="tablaList" name="tablaForm" styleId="tablaList"> 
						<html:option value="">  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</html:option>
						<html:optionsCollection name="tablaForm" property="tablaList" label="desc" value="cod"/> 
					</html:select>
					
				</div>
			</div>
			
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">C&oacute;digo: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="cdCodTabla" class="text ui-widget-content ui-corner-all" size="6" maxlength="6" value="<c:out value="${tablaForm.cdCodTabla}"/>" />
				</div>
			</div>
			
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">Descripci&oacute;n larga: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="nbCodTabla" class="text ui-widget-content ui-corner-all" size="30" maxlength="30" value="<c:out value="${tablaForm.nbCodTabla}"/>" />
				</div>
			</div>

			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">Descr. Corta: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="nbCodTablaCorto" class="text ui-widget-content ui-corner-all" size="12" maxlength="12" value="<c:out value="${tablaForm.nbCodTablaCorto}"/>" />
				</div>
			</div>
			
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">Atributo 1:</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="nbAtributoTabla1" class="text ui-widget-content ui-corner-all" size="30" maxlength="30" value="<c:out value="${tablaForm.nbAtributoTabla1}"/>" />
				</div>
			</div>
			
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">Atributo 2:</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="nbAtributoTabla2" class="text ui-widget-content ui-corner-all" size="30" maxlength="30" value="<c:out value="${tablaForm.nbAtributoTabla2}"/>" />
				</div>
			</div>
			
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">Atributo 3:</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="nbAtributoTabla3" class="text ui-widget-content ui-corner-all" size="20" maxlength="30" value="<c:out value="${tablaForm.nbAtributoTabla3}"/>" />
				</div>
			</div>
			
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">Habilitado: * </div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<html:select property="habilitadoList" name="tablaForm" styleId="habilitadoList"> 
						<html:optionsCollection name="tablaForm" property="habilitadoList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
		</div>
	</fieldset>
</html:form>

<script src="js/abmc/tablas.js"></script>


