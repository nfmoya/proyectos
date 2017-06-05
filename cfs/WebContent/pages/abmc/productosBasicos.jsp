<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:form action="/ABMCProductosBasicos" styleId="ABMCProductosBasicos">
	<html:hidden name="productoForm" property="addGrant"    styleId="addGrant" />
	<html:hidden name="productoForm" property="editGrant"   styleId="editGrant" />
	<html:hidden name="productoForm" property="deleteGrant" styleId="deleteGrant" />
	<input type="hidden" id="selectGrant" value="S"/>
	<input type="hidden" id="tipoModificacion" value=""/>

		<!-- GRILLA de Productos-->
		<div class="fila" id="msgEspera" style="display: none; overflow: auto;"></div>
		<div id="productosBasicosGrid" class="fila" style="display: none;">
			<div id="gridProductosBasicos" align="left" style="padding-top: 10px;">
				<table id="gridProductosBasicosId"></table>
				<div id="gridProductosBasicosPager"></div>
			</div>
		</div>

		<div id="dialogEditProducto" title="Producto" align="center" style="display: none;">
			<div class="fila" id="msgConfirmacion" style="display: none; overflow: auto;"></div>
					
			<div class="fila" id="prod_basic_responseMsgs" style="display: none; overflow: auto;"></div>
	
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">C&oacute;digo Proveedor: *</div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<html:select property="proveedorProductoList" name="productoForm" styleId="proveedorProductoList"> 
						<c:if test="${empty productoForm.proveedorProductoList }" >
							<html:option value="0">&nbsp; Sin Proveedores &nbsp;</html:option>
						</c:if>								
					</html:select>
				</div>
			</div>
			
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">C&oacute;digo Producto: *</div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<input type="text" id="cdProducto" class="text ui-widget-content ui-corner-all" size="15" maxlength="12" value="<c:out value="${productoForm.cdProducto}"/>" />
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">Descripci&oacute;n larga: *</div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<input type="text" id="nbProducto" class="text ui-widget-content ui-corner-all" size="50" maxlength="30" value="<c:out value="${productoForm.nbProducto}"/>" />
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">Descripci&oacute;n Corta: *</div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<input type="text" id="nbProductoCorto" class="text ui-widget-content ui-corner-all" size="16" maxlength="15" value="<c:out value="${productoForm.nbProductoCorto}"/>" />
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">C&oacute;digo Unidad Valoraci&oacute;n: * </div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<html:select property="uniValList" name="productoForm" styleId="uniValList"> 
						<html:option value="0">  &nbsp; Sin Unidad &nbsp;</html:option>
					<c:forEach  items="${productoForm.uniValList}" var="item">
							<html:option  value="${item.cod}">
								${item.cod} - ${item.desc}
							</html:option>
						</c:forEach>
					</html:select>
				</div>
			</div>
 			
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">Tipo Valoraci&oacute;n: * </div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<html:select property="tipValList" name="productoForm" styleId="tipValList"> 
						<html:option value="0">  &nbsp; Sin Tip Val &nbsp;</html:option>
						<html:optionsCollection name="productoForm" property="tipValList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
 			
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">Moneda: *</div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<html:select property="monedaList" name="productoForm" styleId="monedaList">
						<html:option value="">&nbsp;</html:option>					 
						<html:optionsCollection name="productoForm" property="monedaList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">% Variaci&oacute;n: M&aacute;x Mensual*</div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<input type="text" id="nuPorcVarMax" class="text ui-widget-content ui-corner-all" onblur="extractNumber(this,0,true);" onkeyup="extractNumber(this,0,true);" size="10" maxlength="10" value="<c:out value="${productoForm.nuPorcVarMax}"/>" />
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">Grupo de Productos: * </div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<html:select property="grupoProductoList" name="productoForm" styleId="grupoProductoList"> 
						<html:option value="0"> &nbsp; Sin Grupo &nbsp;</html:option>
						<c:forEach  items="${productoForm.grupoProductoList}" var="item">
<%-- 						<logic:iterate id="item" name="productoForm" property="grupoProductoList" > --%>
							<html:option  value="${item.cod}">
								${item.cod} - ${item.desc}
							</html:option>
<%-- 						</logic:iterate> --%>
						</c:forEach>
<%-- 						<html:optionsCollection name="productoForm" property="grupoProductoList" label="desc" value="cod"/>  --%>
						
					</html:select>
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">Importar en Interfaz Serv Prestados: *</div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<html:select property="stProdImportPrestList" name="productoForm" styleId="stProdImportPrestList"> 
						<html:optionsCollection name="productoForm" property="stProdImportPrestList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">Importar en Interfaz Serv Facturados: * </div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<html:select property="stProdImportFactList" name="productoForm" styleId="stProdImportFactList"> 
						<html:optionsCollection name="productoForm" property="stProdImportFactList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">Remito Obligatorio en Serv Prestados: *</div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<html:select property="stRemServObligList" name="productoForm" styleId="stRemServObligList"> 
						<html:optionsCollection name="productoForm" property="stRemServObligList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>			
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">Remito Obligatorio en Serv Facturados: *</div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<html:select property="stRemFactObligList" name="productoForm" styleId="stRemFactObligList"> 
						<html:optionsCollection name="productoForm" property="stRemFactObligList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">Admite Remito ya Registrado en Serv Prestados:  *</div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<html:select property="stAdmiteRemServList" name="productoForm" styleId="stAdmiteRemServList"> 
						<html:optionsCollection name="productoForm" property="stAdmiteRemServList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">Admite Remito ya Registrado en Serv Facturados: *</div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<html:select property="stAdmiteRemFactList" name="productoForm" styleId="stAdmiteRemFactList"> 
						<html:optionsCollection name="productoForm" property="stAdmiteRemFactList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>			
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">Admite Conciliar ignorando Valores: *</div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<html:select property="stConcilSinValList" name="productoForm" styleId="stConcilSinValList"> 
						<html:optionsCollection name="productoForm" property="stConcilSinValList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">Permite dejar sin Conciliar Registros Serv Prestados: *</div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<html:select property="stServSinConcilList" name="productoForm" styleId="stServSinConcilList"> 
						<html:optionsCollection name="productoForm" property="stServSinConcilList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">Permite dejar sin Conciliar Registros Serv Facturados:  *</div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<html:select property="stFactSinConcilList" name="productoForm" styleId="stFactSinConcilList"> 
						<html:optionsCollection name="productoForm" property="stFactSinConcilList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">Sector Solicit por defecto en Serv Prestados:</div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<html:select property="cdSecSolServList" name="productoForm" styleId="cdSecSolServList"> 
						<html:option value="">  &nbsp;</html:option>
<!-- 					<html:optionsCollection name="productoForm" property="cdSecSolServList" label="desc" value="cod"/> -->
					</html:select>
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">Sector Control por defecto en Serv Prestados: </div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<html:select property="cdSecConServList" name="productoForm" styleId="cdSecConServList"> 
						<html:option value="">  &nbsp;</html:option>
<!-- 					<html:optionsCollection name="productoForm" property="cdSecConServList" label="desc" value="cod"/> -->
					</html:select>
				</div>
			</div>
<!-- 			
<logic:iterate id="sector" name="productoForm" property="cdSecConFactList">
     <tr>
        <td><html:text name="sector" property="cod" />
        <td><html:text name="sector" property="desc" />
     </tr>
</logic:iterate>
-->
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">Sector Control por defecto en Serv Facturados:</div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<html:select property="cdSecConFactList" name="productoForm" styleId="cdSecConFactList"> 
						<html:option value="">  &nbsp;</html:option>
<!-- 					<html:optionsCollection name="productoForm" property="cdSecConFactList" label="desc" value="cod"/> -->
					</html:select>
				</div>
			</div>			
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">Atributo Referencial 1 en Remitos valor por defecto:</div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<input type="text" id="nbAtributoRef1" class="text ui-widget-content ui-corner-all" size="30" maxlength="30" value="<c:out value="${productoForm.nbAtributoRef1}"/>" />
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">Atributo Referencial 2 en Remitos valor por defecto: </div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<input type="text" id="nbAtributoRef2" class="text ui-widget-content ui-corner-all" size="30" maxlength="30" value="<c:out value="${productoForm.nbAtributoRef2}"/>" />
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">D&iacute;as corrimiento Fecha Remito vs Inicio Per&iacute;odo Facturado:</div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<input type="text" id="nuDiaEmiFDesde" class="text ui-widget-content ui-corner-all" onblur="extractNumber(this,0,true);" onkeyup="extractNumber(this,0,true);" size="10" maxlength="10" value="<c:out value="${productoForm.nuDiaEmiFDesde}"/>" />
				</div>
			</div>			
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">D&iacute;as corrimiento Fecha Remito vs Fin Per&iacute;odo Facturado:</div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<input type="text" id="nuDiaEmiFHasta" class="text ui-widget-content ui-corner-all" onblur="extractNumber(this,0,true);" onkeyup="extractNumber(this,0,true);" size="10" maxlength="10" value="<c:out value="${productoForm.nuDiaEmiFHasta}"/>" />
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">D&iacute;as corrimiento Fecha Fin Servicio vs Ini Per&iacute;odo Facturado: </div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<input type="text" id="nuDiaCierreFDesde" class="text ui-widget-content ui-corner-all" onblur="extractNumber(this,0,true);" onkeyup="extractNumber(this,0,true);" size="10" maxlength="10" value="<c:out value="${productoForm.nuDiaCierreFDesde}"/>" />
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">D&iacute;as corrimiento Fecha Fin Servicio vs Fin Per&iacute;odo Facturado:</div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<input type="text" id="nuDiaCierreFHasta" class="text ui-widget-content ui-corner-all" onblur="extractNumber(this,0,true);" onkeyup="extractNumber(this,0,true);" size="10" maxlength="10" value="<c:out value="${productoForm.nuDiaCierreFHasta}"/>" />
				</div>
			</div>			
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">Productos que Agrupan otros Productos:</div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<html:select property="nbAtributoAdic1List" name="productoForm" styleId="nbAtributoAdic1List"> 
						<html:optionsCollection name="productoForm" property="nbAtributoAdic1List" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">Atributo Adicional 2: </div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<input type="text" id="nbAtributoAdic2" class="text ui-widget-content ui-corner-all" size="30" maxlength="30" value="<c:out value="${productoForm.nbAtributoAdic2}"/>" />
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">Atributo Adicional 3:</div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<input type="text" id="nbAtributoAdic3" class="text ui-widget-content ui-corner-all" size="30" maxlength="30" value="<c:out value="${productoForm.nbAtributoAdic3}"/>" />
				</div>
			</div>			
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">Atributo Adicional 4:</div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<input type="text" id="nbAtributoAdic4" class="text ui-widget-content ui-corner-all" size="30" maxlength="30" value="<c:out value="${productoForm.nbAtributoAdic4}"/>" />
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">Atributo Adicional 5: </div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<input type="text" id="nbAtributoAdic5" class="text ui-widget-content ui-corner-all" size="30" maxlength="30" value="<c:out value="${productoForm.nbAtributoAdic5}"/>" />
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 450px;">Habilitado: * </div>
				<div class="col_input" style="width: 300px;margin-left: 10px;">
					<html:select property="habilitadoProductoList" name="productoForm" styleId="habilitadoProductoList"> 
						<html:optionsCollection name="productoForm" property="habilitadoProductoList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
		</div>
</html:form>

<script src="js/abmc/productosBasicos.js"></script>
