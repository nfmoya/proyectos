<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>

</head>
<body>

<input type="hidden" id="path" value="<%= request.getContextPath()%>" />
<input type="hidden" id="or" value="<%= request.getAttribute("origen") %>" />


</body>

<script type="text/javascript">

$( document ).ready(function() {
 	
	 var path = document.getElementById("path").value;
     var ori = document.getElementById("or").value;
	
     myWindow =  window.open("/pkmslogout.form");
     if(ori == 0){
    	 alert("HA FINALIZADO LA SESION.");	 
	 }else{
		 alert("HA FINALIZADO LA SESION.");	 
		 
	 }
         
     myWindow.close();
     window.location.href = path;
     
	});
  
</script>
<script type="text/javascript">
	window.onload = function() {
 			
	     var path = document.getElementById("path").value;
	     var ori = document.getElementById("or").value;
		
         myWindow =  window.open("/pkmslogout.form");
	     if(ori == 0){
	    	 alert("HA FINALIZADO LA SESION.");
	    	 
		 }else{
			 
			 alert("HA FINALIZADO LA SESION.");		 
		 }
	     
	     
	     myWindow.close();
	     window.location.href = path;
	};
		</script>
	
</html>