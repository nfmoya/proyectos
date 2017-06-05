<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  

<html:form action="/ABMUsers">
	<input id="usersPersonaId" type="hidden" value=""/> 
	<input id="usersPersonaNroDoc" type="hidden" value=""/> 
	
	<input type="hidden" id="usersCreateGrant" value="<c:out value="${usersForm.createGrant}"/>"/>
	<input type="hidden" id="usersEditGrant" value="<c:out value="${usersForm.editGrant}"/>"/>
	<input type="hidden" id="usersDeleteGrant" value="<c:out value="${usersForm.deleteGrant}"/>"/>
	
	<div class="col_titulo">
		Gesti&oacute;n Usuarios
	</div>
		
</html:form>

<script src="js/users.js"></script>
