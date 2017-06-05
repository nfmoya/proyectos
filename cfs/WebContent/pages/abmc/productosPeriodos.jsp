<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  

<html:form action="/ABMCProductosPeriodos">
	<html:hidden name="productoPeriodoForm" property="addGrant"    styleId="addGrantPP" />
	<html:hidden name="productoPeriodoForm" property="editGrant"   styleId="editGrantPP" />
	<html:hidden name="productoPeriodoForm" property="deleteGrant" styleId="deleteGrantPP" />
	<input type="hidden" id="selectGrant" value="S"/>
	<input type="hidden" id="tipoModificacion" value=""/>
	<input type="hidden" id="producto" value=""/>

		<!-- GRILLA de Productos-->
		<div class="fila" id="msgEspera" style="display: none; overflow: auto;"></div>
		<div id="productosPeriodosGrid" class="fila" style="display: none;">
			<div id="gridProductosPeriodos" align="left" style="padding-top: 10px;">
				<table id="gridProductosPeriodosId"></table>
				<div id="gridProductosPeriodosPager"></div>
			</div>
		</div>
		
		<div id="dialogEditProductoPeriodo" title="Periodo Producto" align="center" style="display: none;">
			<div class="fila" id="msgConfirmacion" style="display: none; overflow: auto;"></div>
				
			<div class="fila" id="prod_Periodo_responseMsgs" style="display: none; overflow: auto;"></div>
	
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 260px;">C&oacute;digo Proveedor: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<html:select property="proveedorProductoPeriodoList" name="productoPeriodoForm" styleId="proveedorProductoPeriodoList"> 
						<html:option value="0"> Sin Proveedor &nbsp;</html:option>
						<html:optionsCollection name="productoPeriodoForm" property="proveedorProductoPeriodoList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 260px;">C&oacute;digo Producto: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<html:select property="productoPeriodoList" name="productoPeriodoForm" styleId="productoPeriodoList"> 
						<html:option value="0"> Sin Producto &nbsp;</html:option>
						<html:optionsCollection name="productoPeriodoForm" property="productoPeriodoList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
			<div id="disableFechas">
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 260px;">Fecha Desde: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="fhDesde" class="text ui-widget-content ui-corner-all" alt="date" size="12" maxlength="10" value="<c:out value="${productoPeriodoForm.fhDesde}"/>" />
				</div>
			</div>
			<div class="fila" style="height: 10px;" >
				<div class="col_label" style="width: 260px;">Fecha Hasta: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="fhHasta" class="text ui-widget-content ui-corner-all" alt="date" size="12" maxlength="10" value="<c:out value="${productoPeriodoForm.fhHasta}"/>" />
				</div>
			</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 260px;">Habilitado: </div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<html:select property="habilitadoProductoPeriodoList" name="productoPeriodoForm" styleId="habilitadoProductoPeriodoList"> 
						<html:optionsCollection name="productoPeriodoForm" property="habilitadoProductoPeriodoList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
		</div>
</html:form>

<script src="js/abmc/productosPeriodos.js"></script>

