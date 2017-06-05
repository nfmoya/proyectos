<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  

<html:form action="/ABMCProductosAgrupados" styleId="ABMCProductosAgrupados">
	<html:hidden name="productoAgrupadoForm" property="addGrant"    styleId="addGrant" />
	<html:hidden name="productoAgrupadoForm" property="editGrant"   styleId="editGrant" />
	<html:hidden name="productoAgrupadoForm" property="deleteGrant" styleId="deleteGrant" />
	<input type="hidden" id="selectGrant" value="S"/>
	<input type="hidden" id="tipoModificacion" value=""/>	
	
	<!-- FILTRO de Sectores-->
	<fieldset id="productosFilter" class="ui-widget ui-widget-content ui-corner-all" style="${width_filter}">
		<legend id="productos_filter_legend" class="ui-widget ui-widget-header ui-corner-all">
			Administraci&oacute;n de Productos Agrupados
		</legend>
	
		<div class="fila" id="producto_responseMsgs" style="display: none; overflow: auto;"></div>
				
      <div id="dialogProductoFilter" title="Consulta" align="center">
         <div class="fila" style="height: 10px;">
            <div class="col_label" style="width: 130px;">Proveedor:</div>
            <div class="col_input" style="width: 300px;margin-left: 10px;">
               <html:select property="filtroProveedorList" name="productoAgrupadoForm" styleId="filtroProveedorList"  onchange="checkSession();search();limpiar(1);"> 
                  <html:option value="0"> Todos los Proveedores &nbsp;</html:option>
	              <html:optionsCollection name="productoAgrupadoForm" property="filtroProveedorList" label="desc" value="cod"/> 
               </html:select>
            </div>
         </div>
         <div class="fila" style="height: 10px;">
            <div class="col_label" style="width: 130px;">Producto Orig:</div>
            <div class="col_input" style="width: 300px;margin-left: 10px;">
               <html:select property="filtroProductoOrigList" name="productoAgrupadoForm" styleId="filtroProductoOrigList"  onchange="checkSession();search();limpiar(2);"> 
                  <html:option value="0"> Todos los Productos &nbsp;</html:option>
	              <html:optionsCollection name="productoAgrupadoForm" property="filtroProductoOrigList" label="desc" value="cod"/> 
               </html:select>
            </div>
         </div>
         <div class="fila" style="height: 10px;">
            <div class="col_label" style="width: 130px;">Producto:</div>
            <div class="col_input" style="width: 300px;margin-left: 10px;">
               <html:select property="filtroProductoList" name="productoAgrupadoForm" styleId="filtroProductoList"  onchange="checkSession();search();limpiar(2);"> 
                  <html:option value="0"> Todos los Productos &nbsp;</html:option> -->
	              <html:optionsCollection name="productoAgrupadoForm" property="filtroProductoList" label="desc" value="cod"/> 
               </html:select>
            </div>
         </div>
         <div class="fila" style="height: 10px;">
            <div class="col_label" style="width: 130px;">Habilitado:</div>
            <div class="col_input" style="width: 300px;margin-left: 10px;">
               <html:select property="filtroHabilitadoList" name="productoAgrupadoForm" styleId="filtroHabilitadoList"   onchange="checkSession();search();limpiar(4);"> 
                  <html:option value="0"> Todos los Estados &nbsp;</html:option> 
	              <html:optionsCollection name="productoAgrupadoForm" property="filtroHabilitadoList" label="desc" value="cod"/> 
               </html:select>
            </div>
         </div>
       </div>
	</fieldset>
			
		<!-- GRILLA de PRODUCTOS-->
		<div class="fila" id="msgEspera" style="display: none; overflow: auto;"></div>
		<div id="productosAgrupadosGrid" class="fila" style="display: none;">
			<div id="gridProductosAgrupados" align="left" style="padding-top: 10px;">
				<table id="gridProductosAgrupadosId"></table>
				<div id="gridProductosAgrupadosPager"></div>
			</div>
		</div>		
		
		<div id="dialogEditProductoAgrupado" title="Producto" align="center" style="display: none;">
		
			<div class="fila" id="msgConfirmacion" style="display: none; overflow: auto;"></div>
					
			<div class="fila" id="producto_diag_responseMsgs" style="display: none; overflow: auto;"></div>

			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 250px;">C&oacute;digo Proveedor: *</div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<html:select property="proveedorProductoList" name="productoAgrupadoForm" styleId="proveedorProductoList"> 
						<html:option value="0"> Sin Proveedor &nbsp;</html:option>
						<html:optionsCollection name="productoAgrupadoForm" property="proveedorProductoList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 250px;">C&oacute;digo Producto Orig: *</div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<html:select property="originalList" name="productoAgrupadoForm" styleId="originalList"> 
						<html:option value="0"> Sin Producto &nbsp;</html:option>
					</html:select>
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 250px;">C&oacute;digo Producto: *</div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<html:select property="productoList" name="productoAgrupadoForm" styleId="productoList"> 
						<html:option value="0"> Sin Producto &nbsp;</html:option>
					</html:select>
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 250px;">Descripci&oacute;n Item: *</div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<input type="text" id="desItem" class="text ui-widget-content ui-corner-all" size="50" maxlength="30" value="<c:out value="${productoAgrupadoForm.desItem}"/>" />
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 250px;">Descripci&oacute;n Grupo: *</div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<input type="text" id="desGrupo" class="text ui-widget-content ui-corner-all" size="50" maxlength="30" value="<c:out value="${productoAgrupadoForm.desGrupo}"/>" />
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 250px;">Habilitado: * </div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<html:select property="habilitadoProductoList" name="productoAgrupadoForm" styleId="habilitadoProductoList"> 
						<html:optionsCollection name="productoAgrupadoForm" property="habilitadoProductoList" label="desc" value="cod"/> 
					</html:select>					
				</div>
			</div>
		</div>

</html:form>

<script src="js/abmc/productosagrupados.js"></script>
