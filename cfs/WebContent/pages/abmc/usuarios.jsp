<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  

<html:form action="/ABMCUsuarios">
	<input type="hidden" id="tipoModificacion" value=""/>
	<input type="hidden" id="usuarioId" value=""/>
		
	<!-- FILTRO de Usuarios-->
	<fieldset id="usuariosFilter" class="ui-widget ui-widget-content ui-corner-all" style="${width_filter}">
		<legend id="usuarios_filter_legend" class="ui-widget ui-widget-header ui-corner-all">
			Administraci&oacute;n de Usuarios
		</legend>
	
		<div class="fila" id="usuario_responseMsgs" style="display: none; overflow: auto;"></div>
	
		<!-- GRILLA de Usuarios-->
		<div class="fila" id="msgEspera" style="display: none; overflow: auto;"></div>
		<div id="usuariosGrid" class="fila" style="display: none;">
			<div id="gridUsuarios" align="left" style="padding-top: 10px;">
				<table id="gridUsuariosId"></table>
				<div id="gridUsuariosPager"></div>
			</div>
		</div>
		
		<!-- TextArea de motivo para la pantalla de eliminar -->	
		<div id="dialogDeleteUsuario" title="Confirmacion" align="center" style="display: none;">	
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">User Name:</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="bajaUserName" class="text ui-widget-content ui-corner-all ui-state-disabled" value="" disabled=disabled />
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">Nombre:</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="bajaNombre" class="text ui-widget-content ui-corner-all ui-state-disabled" value="" disabled=disabled />
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">Apellido:</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="bajaApellido" class="text ui-widget-content ui-corner-all ui-state-disabled" value="" disabled=disabled />
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">Perfil: </div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="bajaPerfilName" class="text ui-widget-content ui-corner-all ui-state-disabled" value="" disabled=disabled />
				</div>
			</div>
		</div>
		
		<div id="dialogEditUsuario" title="Usuario" align="center" style="display: none; width: 400">
			<div class="fila" id="msgConfirmacion" style="display: none; overflow: auto;"></div>
	
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">User Name: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="userName" class="text ui-widget-content ui-corner-all" size="15" maxlength="8" value="<c:out value="${usuarioForm.userName}"/>" />
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">Nombre: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="nombre" class="text ui-widget-content ui-corner-all" size="15" maxlength="15" value="<c:out value="${usuarioForm.nombre}"/>" />
				</div>
			</div>
			<div class="fila" style="height: 50px;">
				<div class="col_label" style="width: 160px;">Apellido: * </div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="apellido" class="text ui-widget-content ui-corner-all" size="30" maxlength="30" value="<c:out value="${usuarioForm.apellido}"/>" />
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">Perfil: * </div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<html:select property="perfilList" name="usuarioForm" styleId="perfilList"> 
						<html:option value="0"> SIN PERFIL ASIGNADO &nbsp;</html:option>
						<html:optionsCollection name="usuarioForm" property="perfilList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">Sector: * </div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<html:select property="sectorList" name="usuarioForm" styleId="sectorList"> 
						<html:option value="0"> SIN SECTOR ASIGNADO &nbsp;</html:option>
						<html:optionsCollection name="usuarioForm" property="sectorList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>		
		</div>
	</fieldset>
</html:form>

<script type="text/javascript" src="js/abmc/usuarios.js" ></script>