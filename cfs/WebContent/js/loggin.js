function initMenu() {
  $('.menu ul').hide();
  $('.menu li a').click(
    function() {
	    $(this).next().slideToggle('normal');	
      }
    );
    
  $('.modulos ul').hide();
  $('.modulos li a').click(
    function() {
	    if ($(this).parent().parent().attr('class') == "modulos") {
	        $('.modulos ul').hide();/*PRUEBA*/
		    $(this).next().slideToggle('normal');	
	    }
      }
   ); 
    
}
 
function initButtonHelp(){
	$( "#buttonAppHelp" ).button({
		label: 'Activar Ayuda',
		icons: {
			primary: "ui-icon-help"
		}, height: '10px'
	}).click(function() {
		var options;
		if ( $(this).text() === "Activar Ayuda" ) {
			options = {
				label: "Desactivar Ayuda",
				icons: {
					primary: "ui-icon-cancel"
				}
			};
			enabledApplicationHelp();
		} else {
			options = {
				label: "Activar Ayuda",
				icons: {
					primary: "ui-icon-help"
				}
			};
			disabledApplicationHelp();
		}
		$( this ).button( "option", options );
	});
}
  
function loginUserName(){
	callAction("login.action", null, "barra-lateral", true, "loadMenu", null);
}

function loadMenu(){
	var username = $('#hidUserName').val();
	var fullname = $('#hidUserFullname').val();
	var menuCompleto = $('#hidMenuCompleto').val();
	$('#barra-lateral').html(menuCompleto);
	$('div.userNameLogged').html(fullname+' ('+username+')');
	
	//$('div.userNameLogged').append('&nbsp;&nbsp;&nbsp;(<a href=\'Logout.action?origen=0\' class=\'text2\'>cerrar sesi&oacute;n</a>)');
	$('div.userNameLogged').append('&nbsp;&nbsp;&nbsp;(<a id=\'linkCerrarSesion\' href=\'javascript:logOutLink();\' class=\'text2\'>cerrar sesi&oacute;n</a>)');
	
	$('div.userNameLogged').append('&nbsp;&nbsp;&nbsp;<button type=\'button\' role=\'button\' id=\'buttonAppHelp\' style=\'font-size: 7pt; font-family: Arial;\'/>');
	initButtonHelp();
		
	//$('div.userNameLogged').append('&nbsp;&nbsp;&nbsp;(<a href=\'ChangePasswordInit.action\' class=\'text\'>cambiar password</a>)');
	
	initMenu();
	var hid = $('#hid').val();
	if(hid !="estoy"){
//		alert(hid);
		callAction("mainMenuAfterLogin.action", null, "contenido", true, null, null);
	}
	
	
}