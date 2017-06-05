<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:hidden name="welcomeForm" property="grantWelcomeView" styleId="grantWelcomeView" />

<div class="mainTop">
	<div class="encabezado">
		<h2 class="titulo_bienvenida_1">Bienvenido <c:out value="${userFullname}" /></h2>
	</div>
</div>
<div class="fila" id="welcome_responseMsgs" style="display:none; overflow:auto;"></div>

