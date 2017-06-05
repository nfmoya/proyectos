<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  

<html:form action="/ABMCSectores">
	<html:hidden name="sectorForm" property="addGrant"    styleId="addGrant" />
	<html:hidden name="sectorForm" property="editGrant"   styleId="editGrant" />
	<html:hidden name="sectorForm" property="deleteGrant" styleId="deleteGrant" />
	<input type="hidden" id="selectGrant" value="S"/>	
	<input type="hidden" id="tipoModificacion" value=""/>
	
	<!-- FILTRO de Sectores-->
	<fieldset id="sectoresFilter" class="ui-widget ui-widget-content ui-corner-all" style="${width_filter}">
		<legend id="sectores_filter_legend" class="ui-widget ui-widget-header ui-corner-all">
			Administraci&oacute;n de Sectores
		</legend>
	
		<div class="fila" id="sector_responseMsgs" style="display: none; overflow: auto;"></div>
	
	    <div id="dialogSectorFilter" title="Consulta" align="center">
		   <div class="fila">
              <div class="col_label">Sector:</div>
              <div class="col_input">
					<html:select property="filtroSectorList" name="sectorForm" styleId="filtroSectorList" onchange="search();cleanMsgConfirmation();"> 
						<html:option value="0"> Todos los Sectores &nbsp;</html:option>
						<html:optionsCollection name="sectorForm" property="filtroSectorList" label="desc" value="cod"/> 
					</html:select>
              </div>
<!--          <button id="btnBusCons" type="button">Buscar</button>  -->
        	</div>
        </div>        
	
		<!-- GRILLA de Sectores-->
		<div class="fila" id="msgEspera" style="display: none; overflow: auto;"></div>
		<div id="sectoresGrid" class="fila" style="display: none;">
			<div id="gridSectores" align="left" style="padding-top: 10px;">
				<table id="gridSectoresId"></table>
				<div id="gridSectoresPager"></div>
			</div>
		</div>
		
		<div id="dialogEditSector" title="Sector" align="center" style="display: none; width: 400">
			<div class="fila" id="msgConfirmacion" style="display: none; overflow: auto;"></div>
	
			<div class="fila" id="sector_diag_responseMsgs" style="display: none; overflow: auto;"></div>
		
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;"><label for="cdSector">Sector: *</label></div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="cdSector" class="text ui-widget-content ui-corner-all" size="15" maxlength="15" value="<c:out value="${sectorForm.cdSector}"/>" required />
				</div>
			</div>
			
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">Nombre Sector: *</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="nbSector" class="text ui-widget-content ui-corner-all" size="50" maxlength="50" value="<c:out value="${sectorForm.nbSector}"/>" />
				</div>
			</div>

			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">Nombre Corto:</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="nbSectorAbrev" class="text ui-widget-content ui-corner-all" size="10" maxlength="10" value="<c:out value="${sectorForm.nbSectorAbrev}"/>" />
				</div>
			</div>
			
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">Sector Alternativo:</div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<input type="text" id="cdSectorAlt" class="text ui-widget-content ui-corner-all" size="15" maxlength="15" value="<c:out value="${sectorForm.cdSectorAlt}"/>" />
				</div>
			</div>
			<div class="fila" style="height: 10px;">
				<div class="col_label" style="width: 160px;">Habilitado: </div>
				<div class="col_input" style="width: 200px;margin-left: 10px;">
					<html:select property="habilitadoList" name="sectorForm" styleId="habilitadoListSect"> 
						<html:optionsCollection name="sectorForm" property="habilitadoList" label="desc" value="cod"/> 
					</html:select>
				</div>
			</div>
		</div>
	</fieldset>
</html:form>

<script src="js/abmc/sectores.js"></script>


