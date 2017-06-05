
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="template"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<jsp:useBean id="cons" class="com.gefa.utils.Constants" scope="session"/>
<%@ page import="com.gefa.utils.Constants" %>

<!DOCTYPE html>
<html lang="en">
  <head>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="js/jquery.js"></script>
    <script src="js/jquery-ui.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/bootstrap-filestyle.min.js"> </script>
    
    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
    <link href="css/mensajes.css" rel="stylesheet">
    <link href="css/sticky-footer.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/starter-template.css" rel="stylesheet">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="ico/favicon.png">

    <title>Carga de Excels</title>


    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="../../assets/js/html5shiv.js"></script>
      <script src="../../assets/js/respond.min.js"></script>
    <![endif]-->
  </head>

  <body background="./images/fondo3.png"  >
  
    <div   class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
<!--         <div  class="navbar-header"> -->
<!--           <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse"> -->
<!--             <span class="icon-bar"></span> -->
<!--             <span class="icon-bar"></span> -->
<!--             <span class="icon-bar"></span> -->
<!--           </button> -->
<!--           <br> -->
<!--           <a class="navbar-brand" href="init.do"> -->
        
<!--           <img src="./images/logooriginal.png" width="80px" height="80px"/></a> -->
<!--         </div> -->
  		
        <div style="padding-left: 22%"   class="collapse navbar-collapse">
        
        <br><br><br><br>
         <ul  class="nav navbar-nav">
        
                <li class="active">
                  <li id="excelLoadLink"><a href="init.do">Carga de Archivos de Facturacion</a></li>
                  <li id="excelViewLink"><a href="registroDeCarga.do">Registro de Carga de Archivos</a></li>
                  <li id="excelViewLink"><a href="http://cchhgefatesting.sistemasactivos.com/gefa/inicio">Volver</a></li>
                  
<!--                   <li><a href="#contact">Otros</a></li> -->
<!--                 <li class="dropdown"> -->
<!--                   <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <b class="caret"></b></a> -->
<!--                   <ul class="dropdown-menu"> -->
<!--                 <li class="active"><a href="#">Home</a></li> -->
<!--                 <li><a href="#about">About</a></li> -->
<!--                 <li><a href="#contact">Contact</a></li> -->
<!--                     <li class="divider"></li> -->
<!--                     <li class="dropdown-header">Nav header</li> -->
<!--                     <li><a href="#">Separated link</a></li> -->
<!--                     <li><a href="#">One more separated link</a></li> -->
<!--                   </ul> -->
<!--                 </li> -->
               
              </ul>
              
      </div>
    </div>

    </div>
<!--     Main jumbotron for a primary marketing message or call to action -->
<!--     <div class="jumbotron"> -->
<!--       <div class="container"> -->
<!--         <h1>RESA!</h1> -->
<!--         <p>Read Excel SA es una aplicacion para cargar los excels de facturación de manera fácil y sencilla.</p> -->
<!--        </div> -->
<!--     </div> -->
    <div class="container">
		<br>
      <template:insert name="content" />
      
    </div><!-- /.container -->
	
<!--     <div class="footer"> -->
<!--       <div class="container"> -->
<!--         <p class="text-muted">© Sistemas Activos SRL 2014.</p> -->
<!--       </div> -->
<!--     </div> -->
  </body>
 
</html>
