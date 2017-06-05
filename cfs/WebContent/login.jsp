<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link type="text/css" href="css/login.css" rel="stylesheet" />
<script type="text/javascript">
	function login(){
		if (document.forms[0].username.value != '' &&
			document.forms[0].password.value != '' ){
			document.getElementById('loginMessage').innerHTML  = "";
			document.getElementById('loginMessage').style.display='none';
			document.getElementById('loginMessage').style.visibility='hidden';
			document.forms[0].submit();
		} else {
			document.getElementById('loginMessage').innerHTML  = "Informe Nombre de Usuario y Password";
			document.getElementById('loginMessage').style.display='block';
			document.getElementById('loginMessage').style.visibility='visible';
		}
	}
	window.onload = function(){
		if(typeof String.prototype.trim !== 'function') {
		  String.prototype.trim = function() {
		    return this.replace(/^\s+|\s+$/g, ''); 
		  }
		}
		if (document.getElementById('loginMessageP').innerHTML.trim() != ''){
			document.getElementById('loginMessage').style.display='block';
			document.getElementById('loginMessage').style.visibility='visible';
		}
	}
</script>

<html:form action="/AuthenticateUser">
<div>
	<div class="mainTop" style="width:600px; text-align: center;">CFS</div>
	
	<div class="fila" id="loginMessage" style="visibility:hidden; display:none; overflow:auto; background-color: #FEF1EC; border: 1px solid #CD0A0A; width:600px; margin-top: 20px;">
		<p style='font-size: 10px;font-family: Verdana; color: red; text-align: center;' id="loginMessageP">
		<c:out value="${errors}" />
		</p>
	</div>
	
	<div class="fila" style="width:600px; margin-top: 20px;">
		<div class="col_label" style="width: 200px;"> Nombre de Usuario : </div>
		<div class="col_input">
			<html:text name="authenticateForm" property="username" styleClass="text ui-widget-content ui-corner-all" size="30"/>
		</div>
	</div>
	<div class="fila" style="width:600px;">
		<div class="col_label" style="width: 200px;"> Contrase&ntilde;a : </div>
		<div class="col_input">
			<html:password name="authenticateForm" property="password" styleClass="text ui-widget-content ui-corner-all" size="30"/>
		</div>
	</div>
	<div class="fila" style="width:600px;">
		<div class="col_input" style="margin-left: 45%">
			<html:button property="botonEntrar" onclick="login();" > Entrar </html:button>
		</div>
	</div>
</div>
</html:form>
