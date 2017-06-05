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
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>

</head>
<body>

<input type="hidden" id="path" value="<%= request.getContextPath()%>" />



</body>
</html>
<html:form action="/AuthenticateUser">

<div>
	<div class="mainTop" style="width:600px; text-align: center;">CFS</div>
	
	<div class="fila" id="loginMessage" style="visibility:hidden; display:none; overflow:auto; background-color: #FEF1EC; border: 1px solid #CD0A0A; width:600px; margin-top: 20px;">
		<p style='font-size: 10px;font-family: Verdana; color: red; text-align: center;' id="loginMessageP">
		<c:out value="${errors}" />
		</p>
	</div>
	<br/>
	<div class="fila" style="width:600px;">
		<div class="col_input" style="margin-left: 45%">
			<html:button property="botonEntrar" onclick="volver()" > volver </html:button>
		</div>
	</div>
	
</div>
</html:form>

<script type="text/javascript">
	function volver() {

	     var path = document.getElementById("path").value;
	   
	     myWindow =  window.open("/pkmslogout.form");
	     
			 alert("CERRANDO SESION.");		 
		 
	     
	     myWindow.close();
	     window.location.href = path;
	};
		</script>
