<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  

<html:form action="/ABMCProductosSectores">
	<html:hidden name="productoSectorForm" property="addGrant"    styleId="addGrantPS" />
	<html:hidden name="productoSectorForm" property="editGrant"   styleId="editGrantPS" />
	<html:hidden name="productoSectorForm" property="deleteGrant" styleId="deleteGrantPS" />
	<input type="hidden" id="selectGrant" value="S"/>
	<input type="hidden" id="tipoModificacion" value=""/>
	<input type="hidden" id="producto" value=""/>

		<!-- GRILLA de Productos-->
		<div class="fila" id="msgEspera" style="display: none; overflow: auto;"></div>
		<div id="productosSectoresGrid" class="fila" style="display: none;">
			<div id="gridProductosSectores" align="left" style="padding-top: 10px;">
				<table id="gridProductosSectoresId"></table>
				<div id="gridProductosSectoresPager"></div>
			</div>
		</div>
		
		<div id="dialogEditProductoSector" title="Sector Producto" align="center" style="display: none;">
			<div class="fila" id="msgConfirmacion" style="display: none; overflow: auto;"></div>
			
			<div class="fila" id="prod_sector_responseMsgs" style="display: none; overflow: auto;"></div>
	
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 180px;">C&oacute;digo Proveedor: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<html:select property="proveedorProductoSectorList" name="productoSectorForm" styleId="proveedorProductoSectorList"> 
						<html:option value="0"> Sin Proveedor &nbsp;</html:option>
						<html:optionsCollection name="productoSectorForm" property="proveedorProductoSectorList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 180px;">C&oacute;digo Producto: * </div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<html:select property="productoSectorList" name="productoSectorForm" styleId="productoSectorList"> 
						<html:option value="0"> Sin Producto &nbsp;</html:option>
					</html:select>
				</div>
			</div>
			<input type="hidden" id="cdSectorOld" value=""/>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 180px;">C&oacute;digo Sector de Control: * </div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<html:select property="sectorList" name="productoSectorForm" styleId="sectorList"> 
						<html:option value="0"> Sin Sector &nbsp;</html:option>
						<html:optionsCollection name="productoSectorForm" property="sectorList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 180px;">Habilitado: * </div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<html:select property="habilitadoProductoSectorList" name="productoSectorForm" styleId="habilitadoProductoSectorList"> 
						<html:optionsCollection name="productoSectorForm" property="habilitadoProductoSectorList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
		</div>
</html:form>

<script src="js/abmc/productosSectores.js"></script>

