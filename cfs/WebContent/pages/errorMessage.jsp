<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>-1"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!--
TODO: FIJARME SI NO TRAE CONFLICTOS, ESTOS IMPORTS, PERO PARA LA PANTALLA INICIAL NO FUNCIONABA
-->
<link type="text/css" href="css/jquery/ui/redmond/jquery-ui-1.8.13.custom.css" rel="stylesheet" />	

<div id='genErrorMsg' class='ui-widget' style='max-width: 50%; float: left; padding: 5px;'> 
	<div style='padding: 0 .7em; background-color: #FEF1EC; border: 1px solid #CD0A0A;' class='ui-corner-all'>
		<p style='font-size: 10px;'><span style='float: left; margin-right: .3em;' class='ui-icon ui-icon-alert'></span>
			<c:out value="${message}" />
		</p>
	</div>
</div>

