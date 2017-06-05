
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html-el"%>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean-el"%>
<!doctype HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 																	
<html>
<head>
<title>Administraci&oacute;n de Cfs</title>
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link type="text/css" href="css/index.css" rel="stylesheet" />	
<link type="text/css" href="css/style.css" rel="stylesheet" />
<link type="text/css" href="css/form.css" rel="stylesheet" />
<link type="text/css" href="css/welcome.css" rel="stylesheet" />
<link type="text/css" href="css/messages.css" rel="stylesheet" />
<link type="text/css" href="css/jquery/ui/redmond/jquery-ui-1.8.13.custom.css" rel="stylesheet" />	
<link type="text/css" href="css/jquery/ui/jquery.ui.selectmenu.css" rel="stylesheet" />	 
<link type="text/css" media="screen" href="css/jquery/ui/grid/ui.jqgrid.css" rel="stylesheet" />
<link type="text/css" href="css/jquery/ezmark.css" rel="stylesheet" />	 

<script type="text/javascript" src="js/jquery/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="js/jquery/ui/jquery-ui-1.8.13.custom.min.js"></script>
<script type="text/javascript" src="js/jquery/ui/jquery.ui.selectmenu.js"></script>
<script type="text/javascript" src="js/jquery/ui/i18n/grid.locale-es.js"></script>
<script type="text/javascript" src="js/jquery/ui/i18n/jquery.ui.datepicker-es.js"></script>
<script type="text/javascript" src="js/jquery/ui/grid/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="js/jquery/jquery.tools.min.js"></script>
<script type="text/javascript" src="js/jquery/jquery.validate.js"></script>
<script type="text/javascript" src="js/jquery/ui/jquery-ui.dialogSabed.js"></script>
<script type="text/javascript" src="js/jquery/jquery.ezmark.min.js"></script>
<!-- <script type="text/javascript" src="js/jquery/jquery.meio.mask.min.js"></script> -->
<script type="text/javascript" src="js/decimalNumbers.js"></script>
<script type="text/javascript" src="js/general.js"></script>
<script type="text/javascript" src="js/loggin.js"></script>
<script type="text/javascript" src="js/generalGrilla.js"></script>
<script type="text/javascript" src="js/jquery/jquery.scrollablecombo.js"></script>
<script type="text/javascript" src="js/jquery/jquery-ui-tab-utils.js"></script>

<script type="text/javascript" src="js/pdf/jspdf.debug.js"></script>
<script type="text/javascript" src="js/jquery/jquery.maskedinput.js"></script>
<!-- NUEVO 11/01/13 JS PARA QUE TOME JSON EN IE6/7 --> 
<script type="text/javascript" src="js/json2.js"></script>
<script src="js/dateUtils.js"></script>

<script>
	$(document).ready(function () {

		/*
		** Con esta funcion obtengo la url para que 
		** cuando se desloguee el usuario se redirija
		** a la url de webseal de una
		**
		*/

		var href = $(location).attr('href');
		var myDir = href.substring( 0, href.lastIndexOf( "/" ) + 1);
		$('#hidUrlInit').val(myDir);
	});


	/*
		** Cuando hace click en el link de cerrar session 
		** lo mando a desloguearse de Webseal COMO UN CAMPEON
		** Pregunto si las cookie existen y si existen llama a la
		** URL de deslogueo sino solo da un alert para darli tiempo
		** al que se cierre el form websile
		*/
		function logOutLink(){

			allcookies = document.cookie;

			if ((allcookies.indexOf('PD-H-SESSION-ID=') != -1) || (allcookies.indexOf('PD-S-SESSION-ID=') != -1) ){
				myWindow =  window.open("/pkmslogout.form");
				alert("HA FINALIZADO LA SESION.");
	           	myWindow.close();
			}else{
				alert();
			}		
			
    		window.location.href = $('#hidUrlInit').val();
   		}
</script>


</head>
<body>
	
	<input type="hidden" name="hidUrlInit" id="hidUrlInit" value=""/>


	<div id="contenedor">
		<div id="cabecera">
			<div class="tituloApp">CFS</div>
			<div class="userNameLogged"></div>
		</div>
		<div id="barra-lateral">&nbsp;</div>
		<div id="contenido"></div>
		<div id="pie">Cfs b1.0</div>
	</div>
</body>
</html>