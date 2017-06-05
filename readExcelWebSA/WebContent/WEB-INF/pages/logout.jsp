<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>



<!DOCTYPE html>
<html lang="en">
<head>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="js/jquery.js"></script>
<!--     <script src="js/jquery-ui.js"></script> -->
<!--     <script src="js/bootstrap.min.js"></script> -->
<!--     <script type="text/javascript" src="js/bootstrap-filestyle.min.js"> </script> -->
    
    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.original.css" rel="stylesheet">
<!--     <link href="css/style.css" rel="stylesheet"> -->
<!--     <link href="css/mensajes.css" rel="stylesheet"> -->
<!--     <link href="css/sticky-footer.css" rel="stylesheet"> -->


  </head>
   
 

  <body background="./images/fondo3.png">

    
<!--     <div class="container"> -->

      <div   class="starter-template">
       <br>
       <br>
       <br>
       <br>
       <br>
       <br>
       
       
       <h1  align="center">La sesion ha caducado</h1>
		<p align="center" class="lead">
			redireccionando a Control de Peticiones.<br>
		
		


	</div>
           <div    style="width: 20%;margin-left:40%; ;" class="progress progress-striped active">
			<div align="center" class="progress-bar" role="progressbar" aria-valuenow="50"
				aria-valuemin="0" aria-valuemax="50" style="width: 100%">
				
			</div>
		</div> 
            

   </body>
</html>

<script language="JavaScript">
function redireccionar()
{
	window.location.href = 'http://cchhgefatesting.sistemasactivos.com/cliente_nusoap';
}
setTimeout ("redireccionar()", 3000);
</script>
