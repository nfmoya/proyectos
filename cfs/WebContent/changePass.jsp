<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>1"%>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link type="text/css" href="css/login.css" rel="stylesheet" />

<script type="text/javascript">
	function changePassword(){
		if (document.forms[0].currentPassword.value != '' &&
			document.forms[0].newPassword.value != '' &&
			document.forms[0].confirmPassword.value != '' ){
			document.getElementById('changePassMessage').innerHTML  = "";
			document.getElementById('changePassMessage').style.display='none';
			document.getElementById('changePassMessage').style.visibility='hidden';
			document.forms[0].submit();
		} else {
			document.getElementById('changePassMessage').innerHTML  = "Faltan datos.";
			document.getElementById('changePassMessage').style.display='block';
			document.getElementById('changePassMessage').style.visibility='visible';
		}
	}
	
	function cancelChangePassword(){
		document.getElementById('changePassMessage').innerHTML  = "";
		document.getElementById('changePassMessage').style.display='none';
		document.getElementById('changePassMessage').style.visibility='hidden';
		document.forms[0].action = 'index.action';
		document.forms[0].submit();
	}
	
	window.onload = function(){
		if(typeof String.prototype.trim !== 'function') {
		  String.prototype.trim = function() {
		    return this.replace(/^\s+|\s+$/g, ''); 
		  }
		}
		if (document.getElementById('changePassMessageP').innerHTML.trim() != ''){
			document.getElementById('changePassMessage').style.display='block';
			document.getElementById('changePassMessage').style.visibility='visible';
		}
	}

</script>

<html:form action="/ChangePassword">
<html:hidden property="username"/>

<div>
<!--<div class="mainMiddle">
	-->
	<div class="mainTop" style="width:600px; text-align: center;">Cambio de Password</div>
	
	<div class="fila" id="changePassMessage" style="visibility:hidden; display:none; overflow:auto; background-color: #FEF1EC; border: 1px solid #CD0A0A; width:600px; margin-top: 20px;">
		<p style='font-size: 10px;font-family: Verdana; color: red; text-align: center;' id="changePassMessageP">
		<c:out value="${errors}"/>
		</p>
	</div>
	
	<div class="fila" style=" margin-top: 20px;">
		<div class="col_label" style="width: 200px;"> Clave Actual : </div>
		<div class="col_input">
			<html:password property="currentPassword" styleClass="text ui-widget-content ui-corner-all" size="30"/>
		</div>
	</div>
	<div class="fila">
		<div class="col_label" style="width: 200px;"> Clave Nueva : </div>
		<div class="col_input">
			<html:password property="newPassword" styleClass="text ui-widget-content ui-corner-all" size="30"/>
		</div>
	</div>
	<div class="fila">
		<div class="col_label" style="width: 200px;"> Confirme Clave : </div>
		<div class="col_input">
			<html:password property="confirmPassword" styleClass="text ui-widget-content ui-corner-all" size="30"/>
		</div>
	</div>
	<div class="fila" style="margin-top: 10px;">
		<div class="col_label" style="width: 500px; text-align: center; font-size: 8pt;"> La clave debe contener una May&uacute;scula, letras, n&uacute;meros, caracteres especiales del tipo : ! # $ % & @ * ¡  y tener 8 posiciones  Ej: Macki01#  </div>
	</div>
	<div class="fila" style="width: 600px; margin-top: 20px;">
		<div class="col_input" style="margin-left: 40%;">
			<html:button property="botonEntrar" onclick="changePassword();"> Aceptar </html:button>
		</div>
		<div class="col_input" style="margin-left: 30px;">
			<html:button property="botonEntrar" onclick="cancelChangePassword();"> Cancelar </html:button>
		</div>
	</div>
</div>
</html:form>
