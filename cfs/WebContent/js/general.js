var SUCCESS_RESULT = 0;
var WARNING_RESULT = 1;
var VALIDATION_ERROR_CONSTANT = 2;
var TOOLTIP_ACTIVE_STATE = false;

function limpiarContenedor(div){
	//div.children().empty().remove();
	var element = document.getElementById(div.attr('id'));
	while (element.hasChildNodes())
        {
          element.removeChild(element.firstChild);
        } 
    //$("#ui-datepicker-div").empty().remove();
}

$(document).ready(function() {
	if(typeof String.prototype.trim !== 'function') {
	  String.prototype.trim = function() {
	    return this.replace(/^\s+|\s+$/g, ''); 
	  };
	}
	
	if(typeof String.prototype.replaceAll !== 'function') {
	  String.prototype.replaceAll = function(txtSearch, txtReplace) {
		var text = this.replace(txtSearch,txtReplace);
	  	while (text.indexOf(txtSearch) != -1)
			text = text.replace(txtSearch,txtReplace);
	    return text; 
	  };
	}
	
//	$.mask.rules = {
//		'o' : /[0-9a-zA-Z\s,.]/
//	};
	loginUserName();
	/*$('#selUserName').selectmenu({style:'dropdown'});
	$('#buttonLogin').button();*/
	
	/*jQuery("#selectUserName").dialog({  
		bgiframe: true,  
		autoOpen: true,  
		height: 100,  
		modal: true
	});*/
	
	//if ($('#hidUserName').val() == null || $('#hidUserName').val() == '') {
		/*$('#selectUserName').show();	*/
	//} else {
	//	$('#selectUserName').hide();
	//	loginUserName();
	//}
	
	$(document).click( function(){
 
			checkSession();

	  });
		
//	});
//	document.onclick = checkSession;
	
	
});

function addCheckSessionAll(){

	$(".ui-selectmenu").each(function(){

        $(this).click(function(){
            	checkSession();
            }
        );
	});
}
function checkSession() {

  // cookie de webseal
  // alert(leerCookie('PD-H-SESSION-ID')) ; 
  
//	  $.ajax({
//	    'type': 'POST',
//	    'url': 'checkSessionUser.action',
//	    'data': {
//	     
//	    },
//	    'dataType': 'json',
//	    'success': function(data) {
//	    
//	    	successCheck(data);
//	    
//		},
//
//	    'error': function(data) {
//  
//        // window.location.href = "Logout.action?origen=1";
//		alert("HA FINALIZADO LA SESION.");
//		 window.location.href = $('#hidUrlInit').val();
//	       
//	    }
//	  });
	
}


function successCheck(resultado){

	if(resultado.estadoSesion == 1){
	
		  window.location.href = "Logout.action?origen=1";
		 	
	}
	
	
}

function leerCookie(nombre) {
	   a = document.cookie.substring(document.cookie.indexOf(nombre + '=') + nombre.length + 1,document.cookie.length);
	   if(a.indexOf(';') != -1)a = a.substring(0,a.indexOf(';'));
	   return a; 
	}
	
function loadContentDiv(url_){
	callAction(url_, null, "contenido", true, null, null);
}

function loadingImg(divObject) {
	try{
		divObject.html('<img src="${realpath}/../images/gears.gif">');
	}catch(e){
		/*TODO: IMPLEMENTAR UN DEBUG O ALGO ASI EN LUGAR DEL ALERT.*/
		alert('Error loadingImg : '+e.message);
	}
}

/* TODO: */
function jsError(metodo, mensaje) {
	
	checkSession();
	fAlert('Error Metodo '+metodo+': '+mensaje);
}

function limpiarGrilla(nombreLista, nombrePagina, nombreDiv){
	$('#gbox_'+nombreLista).remove(); 
	$('#'+nombreLista).remove(); 
	$('#'+nombrePagina).remove(); 
	/*$('#'+nombreDiv).append('<table id="'+nombreLista+'" class="txtAzulArialNrml8"></table>');*/
	$('#'+nombreDiv).append('<table id="'+nombreLista+'"></table>');
	$('#'+nombreDiv).append('<div id="'+nombrePagina+'"></div>');
}

function limpiarGrillaSinPagina(nombreLista, nombreDiv){
	$('#gbox_'+nombreLista).remove(); 
	$('#'+nombreLista).remove(); 
	$('#'+nombreDiv).append('<table id="'+nombreLista+'"></table>');
}


function cleanButtonGridPager(gridPagerContainer){
	$('#'+gridPagerContainer+' table.navtable td.ui-pg-button').remove();
}

/*** Llamadores de action java, con respuesta jsp y json ***/

/*En el caso de ser una llamada que devuelve un jsp solo puede ser success o error.*/
function callAction(sUrl, sData, divContainerId, cleanDialogs, successCallBack, errorCallBack){
	if (divContainerId != null){
		loadingImg($("#" + divContainerId));
	}
	
	$.ajax({
			  async:true
			  , type: "POST"
			  , contentType: "application/x-www-form-urlencoded;charset=UTF-8"
			  , url: sUrl
			  //, processData : false
			  , data : sData
			  , cache: false
			  , beforeSend: function( xhr ) {
			    xhr.overrideMimeType(  "application/x-www-form-urlencoded;charset=UTF-8" );
			  }
			  ,success: function(msg){
			  	/*TODO: APLICAR ESTILOS A JSP:ERROR*/
			  	if (divContainerId != null){
					limpiarContenedor($("#" + divContainerId));
				}
				if (cleanDialogs) {
					$("#dialog-form").empty().remove();
				}
				
				$("#" + divContainerId).html(msg);
				
			  	if (successCallBack != null){
					eval(successCallBack+'();');
				}
			  }
			  , timeout:15000
			  ,error: function(msg) {
			  	/* Por aca no deber�a salir nunca a menos que el servidor est� bajo */
				if (cleanDialogs) {
					$("#dialog-form").empty().remove();
				}
			  	if (divContainerId != null){
					limpiarContenedor($("#" + divContainerId));
					/*TODO: APLICAR ESTILOS DE ERROR O USAR FUTURO PLUGIN*/
				  	setErrorMsg($("#"+divContainerId), 'El servidor se encuentra fuera de servicio.');
				} else {
			  		/*TODO: O JSERROR */
			  		alert('El servidor se encuentra fuera de servicio.');
			  	}
			  	if (errorCallBack != null){
					eval(errorCallBack+'();');
				}
			  }
	});
}

/* En el caso de ser una llamada de tipo json, termina en success o error */
function callJsonAction(sUrl, sData, successCallBack, errorCallBack){
/*
TODO: IMPLEMENTAR SI O SI!
$.ajax({
  statusCode: {
    404: function() {
      alert('page not found');
    }
  }
});*/
		
	$.ajaxSetup({ scriptCharset: "UTF-8" , contentType: "application/x-www-form-u" +
			"rlencoded;charset=UTF-8"});
	$.ajax({
			async:true
			, type: "GET"
			, dataType: "json"
			, contentType: "application/x-www-form-urlencoded;charset=UTF-8"
			, url:sUrl
			//, processData : false
			, data : sData
			, data : encodeURIComponent(sData).replaceAll('%26','&').replaceAll('%3D','=') 
			, cache: false
			, mimeType: 'text/plain;charset=UTF-8'
			, beforeSend: function( xhr ) {
			    xhr.overrideMimeType(  "application/x-www-form-urlencoded;charset=UTF-8" );
			  }
			, success: function(json){
					/*Por aca sale tanto para ok como para errores en el servicio, dao y presentaci�n, debo diferencialo por los mensajes...*/
					if (isSuccessResult(json.result.errorCode)) {
						/* Llamar a funcion de callback success*/
						if (successCallBack != null ){
							eval(successCallBack+'(json);');
						}
					/* Warning (Aun falta terminar de implementar bien...)*/
					} else if (isWarningResult(json.result.errorCode)) {
						/* Llamar a funcion de callback success*/
						if (successCallBack != null ){
							eval(successCallBack+'(json);');
						}
					/* Validation*/
					} else if (isValidationError(json.result.errorCode)) {
						/* Llamar a funcion de error*/
						eval(errorCallBack+"("+json.result.errorCode+", '"+json.result.errorDesc+"');");
					/* Error */
					} else {
						eval(errorCallBack+"("+json.result.errorCode+", '"+json.result.errorDesc+"');");						
					}
				}
			, timeout:15000
			, error: function(msg){
				/*Por aca sale por errores en el action y por si el servidor esta bajo o algo asi...*/
				if (msg.responseText == null || msg.responseText == '') {
					eval(errorCallBack+'(6, "El servidor se encuentra fuera de servicio");');
				/*Validaciones desde el servidor (usando validation.xml)*/
				} else if (msg.responseText.indexOf('validation-msg') > 0) {
					eval(errorCallBack+"("+VALIDATION_ERROR_CONSTANT+", msg.responseText);");
				} else {
					/*TODO: CAMBIAR O TERMINAR DE DEFINIR BIEN LOS CODIGOS*/
					//eval(errorCallBack+"(6, '"+msg.responseText+"');");
					eval(errorCallBack+"(6, msg.responseText);");
				}
			 }
	    }); 
			
}


/* En el caso de ser una llamada de tipo json, termina en success o error */
function callJsonActionPost(sUrl, sData, successCallBack, errorCallBack){
	$.ajaxSetup({ scriptCharset: "UTF-8" , contentType: "application/x-www-form-u" +
			"rlencoded;charset=UTF-8"});
	$.ajax({
			async:false
			, type: "POST"
			, dataType: "json"
			, contentType: "application/x-www-form-urlencoded;charset=UTF-8"
			, url:sUrl
			//, processData : false
			, data : sData
			, data : encodeURIComponent(sData).replaceAll('%26','&').replaceAll('%3D','=') 
			, cache: false
			, mimeType: 'text/plain;charset=UTF-8'
			, beforeSend: function( xhr ) {
			    xhr.overrideMimeType(  "application/x-www-form-urlencoded;charset=UTF-8" );
			  }
			, success: function(json){
					/*Por aca sale tanto para ok como para errores en el servicio, dao y presentaci�n, debo diferencialo por los mensajes...*/
					if (isSuccessResult(json.result.errorCode)) {
						/* Llamar a funcion de callback success*/
						if (successCallBack != null ){
							eval(successCallBack+'(json);');
						}
					/* Warning (Aun falta terminar de implementar bien...)*/
					} else if (isWarningResult(json.result.errorCode)) {
						/* Llamar a funcion de callback success*/
						if (successCallBack != null ){
							eval(successCallBack+'(json);');
						}
					/* Validation*/
					} else if (isValidationError(json.result.errorCode)) {
						/* Llamar a funcion de error*/
						eval(errorCallBack+"("+json.result.errorCode+", '"+json.result.errorDesc+"');");
					/* Error */
					} else {
						eval(errorCallBack+"("+json.result.errorCode+", '"+json.result.errorDesc+"');");						
					}
				}
			, timeout:15000
			, error: function(msg){
				/*Por aca sale por errores en el action y por si el servidor esta bajo o algo asi...*/
				if (msg.responseText == null || msg.responseText == '') {
					eval(errorCallBack+'(6, "El servidor se encuentra fuera de servicio");');
				/*Validaciones desde el servidor (usando validation.xml)*/
				} else if (msg.responseText.indexOf('validation-msg') > 0) {
					eval(errorCallBack+"("+VALIDATION_ERROR_CONSTANT+", msg.responseText);");
				} else {
					/*TODO: CAMBIAR O TERMINAR DE DEFINIR BIEN LOS CODIGOS*/
					//eval(errorCallBack+"(6, '"+msg.responseText+"');");
					eval(errorCallBack+"(6, msg.responseText);");
				}
			 }
	    }); 			
}


/* Aun falta mostrar el caso en que de error... */
function callActionFile(sUrl, sData){
	var url = sUrl;
	if (sData != null && sData!=''){
		url = url+'?'+sData;
	}
	$('iframe#ifrm').remove();
	var el = document.createElement("iframe");
	el.setAttribute('id', 'ifrm');
	el.setAttribute('width', 0);
	el.setAttribute('height', 0);
	el.setAttribute('style', 'visibility: hidden');
	$('#contenido').append(el);
	el.setAttribute('src',url);
}

/*** Alerts comunes usando el plugin de dialogSabed ***/

function fAlertCB(texto, functionCB) {
	$('body').dialogSabed({
	    dialogMsg: texto,
	    vFunctionAceptar:functionCB
	});
	
	$('body').dialogSabed("open");
}

function fAlert(texto) {
	$('body').dialogSabed({
	    dialogMsg: texto
	});
	
	$('body').dialogSabed("open");
};

function fDialog(texto, funAceptar, funCancelar) {
	$('body').dialogSabed({
	    dialogMsg: texto,
	    vFunctionAceptar:funAceptar,
	    vFunctionCancelar:funCancelar
	});
	
	$('body').dialogSabed("open");
};

function seleccioneRegistro() {
	fAlertCB('Seleccione un registro');
}
/*** Mensajes de error y success genericos ***/

function setErrorMsg(div, msg){
	/*Fixed: Para que no repita estilos... en el caso de salir por error.
	  TODO: Falta corregir de otro forma el error*/	
	var divHtml = "";
	if (msg.indexOf("id='genErrorMsg'") == -1){
		divHtml = "<div id='genErrorMsg' class='ui-widget' style='max-width: 100%; float: left; padding: 5px;'> " + 
					"<div style='padding: 0 .7em; background-color: #FEF1EC; border: 1px solid #CD0A0A;' class='ui-corner-all'>  " + 
						"<p style='font-size: 10px;'><span style='float: left; margin-right: .3em;' class='ui-icon ui-icon-alert'></span> " + 
						msg + 
						"</p> " + 
			 		"</div> " + 
			  	"</div>";
	} else {
		divHtml = msg;
	}
	
	div.html(divHtml);
}

function setSuccessMsg(div, msg){
	var divHtml = "<div id='genSuccessMsg' class='ui-widget' style='max-width: 100%; float: left; padding: 5px;'> " + 
					"<div style='padding: 0 .7em; background-color: #b5f9c6; border: 1px solid #0f9f11;' class='ui-corner-all'>  " + 
						"<p style='font-size: 10px;'><span style='float: left; margin-right: .3em;' class='ui-icon ui-icon-circle-check'></span> " + 
						msg + 
						"</p> " + 
			 		"</div> " + 
			  	"</div>";
	div.html(divHtml);
}

/*** Utilidades de set y get para radiobuttons ***/

function getRadioButtonVal(divName){
	var value = $('#'+divName+' .ez-selected input').val();
	if (value == undefined){ value == null; } /* NULL O VACIO?*/
	return value;
}

function setRadioButtonVal(divName, value){
	$('#'+divName+' input[value='+value+']').attr({"checked":"checked"});
	$('#'+divName+' input[value='+value+']').trigger('change');
}

function enableButtonTag(selectorButtonId) {
	$('#'+selectorButtonId).removeClass('ui-button-disabled ui-state-disabled');
	$('#'+selectorButtonId).removeAttr('disabled');
	//$('#'+selectorButtonId).removeAttr('aria-disabled');
	
}

function reloadGrid(selectorId) {
    $('#'+selectorId).trigger('reloadGrid'); 
}

function validateMailInput(mailText){
	var isValid = true;
	
	if (mailText.indexOf('@') < 1 ||
	 	mailText.indexOf('@') != mailText.lastIndexOf('@') ||
	 	mailText.indexOf('@') > mailText.lastIndexOf('.')) {
		isValid = false;
	}
 
 	return isValid;
}

function enabledApplicationHelp(){
	TOOLTIP_ACTIVE_STATE = true;
	while($('div.tooltipHide').size() > 0){
		$('div.tooltipHide').removeClass('tooltipHide').addClass('tooltip');
	}
	return false;
}

function disabledApplicationHelp(){
	TOOLTIP_ACTIVE_STATE = false;
	while($('div.tooltip').size() > 0){
		$('div.tooltip').removeClass('tooltip').addClass('tooltipHide');
	}
	return false;
}

function applyTooltip(object){
	if (TOOLTIP_ACTIVE_STATE){
		object.tooltip({
			events: {
			  def:     "mouseenter,mouseleave",    // default show/hide events for an element
			  input:   "mouseenter,mouseleave",               // for all input elements
			  widget:  "focus mouseenter,blur mouseleave",  // select, checkbox, radio, button
			  tooltip: "mouseenter,mouseleave"     // the tooltip element
			}
		});
	} else {
		object.tooltip({
			events: {
			  def:     "mouseenter,mouseleave",    // default show/hide events for an element
			  input:   "mouseenter,mouseleave",               // for all input elements
			  widget:  "focus mouseenter,blur mouseleave",  // select, checkbox, radio, button
			  tooltip: "mouseenter,mouseleave"     // the tooltip element
			},
			tipClass: 'tooltipHide'
		});
	}
}

function isValidationError(cod){
	if (cod == VALIDATION_ERROR_CONSTANT) {
		return true;
	}
	return false;
}

function isSuccessResult(cod){
	if (cod == SUCCESS_RESULT) {
		return true;
	}
	return false;
}

function isWarningResult(cod){
	if (cod == WARNING_RESULT) {
		return true;
	}
	return false;
}

/* mini jQuery plugin that formats to two decimal places*/
(function($) {
    $.fn.currencyFormat = function() {
        this.each( function( i ) {
            $(this).change( function( e ){
                if( isNaN( parseFloat( this.value ) ) ) return;
                this.value = parseFloat(this.value).toFixed(2);
            });
        });
        return this;
    };
})( jQuery );



function destroyCombosGeneralAll(){
	$.each($('select'), function () {
        $(this).selectmenu('destroy');
    });
	$.each($('select'), function () {
        $(this).selectmenu({
    		style : 'dropdown',
    		width : $(this).attr("width"), maxHeight:'200'});
    });
}
function destroyCombosGeneralOne( nombre){
    $('#'+nombre).selectmenu('destroy');
	$('#'+nombre).selectmenu({
    		style : 'dropdown',
    		width : $(this).attr("width"), maxHeight:'200'});
}
function isDate(txtDate)
{
  var currVal = txtDate;
  if(currVal == '')
    return false;
  
  //Declare Regex  
  var rxDatePattern = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/; 
  var dtArray = currVal.match(rxDatePattern); // is format OK?

  if (dtArray == null)
     return false;
 
//Checks for dd/mm/yyyy format.
  dtDay = dtArray[1];
  dtMonth= dtArray[3];
  dtYear = dtArray[5];

  if (dtMonth < 1 || dtMonth > 12)
      return false;
  else if (dtDay < 1 || dtDay> 31)
      return false;
  else if ((dtMonth==4 || dtMonth==6 || dtMonth==9 || dtMonth==11) && dtDay ==31)
      return false;
  else if (dtMonth == 2)
  {
     var isleap = (dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0));
     if (dtDay> 29 || (dtDay ==29 && !isleap))
          return false;
  }
  return true;
}

/*--------------------------------------------------------------------------------------------------------*/
/* VARIANTE DE callJsonAction SINCRONICA PARA LLENAR LOS COMBOS CUANDO SE INGRESA EL NUMERO DE CONCILIACION
/*--------------------------------------------------------------------------------------------------------*/
function callJsonSyncAction(sUrl, sData, successCallBack, errorCallBack){
//	console.log(sData);
			
	$.ajaxSetup({ scriptCharset: "UTF-8" , 
		          contentType: "application/x-www-form-u" +	"rlencoded;charset=UTF-8"});
	$.ajax({
		async:false
		, type: "GET"
		, dataType: "json"
		, contentType: "application/x-www-form-urlencoded;charset=UTF-8"
		, url:sUrl
		//, processData : false
		, data : sData
		, data : encodeURIComponent(sData).replaceAll('%26','&').replaceAll('%3D','=') 
		, cache: false
		, mimeType: 'text/plain;charset=UTF-8'
		, beforeSend: function( xhr ) {
		    xhr.overrideMimeType(  "application/x-www-form-urlencoded;charset=UTF-8" );
	    }
		, success: function(json){
		    /*Por aca sale tanto para ok como para errores en el servicio, dao y presentaci�n, debo diferencialo por los mensajes...*/
			if (isSuccessResult(json.result.errorCode)) {
				/* Llamar a funcion de callback success*/
				if (successCallBack != null ){
					eval(successCallBack+'(json);');
				}
				/* Warning (Aun falta terminar de implementar bien...)*/
			} else if (isWarningResult(json.result.errorCode)) {
				/* Llamar a funcion de callback success*/
				if (successCallBack != null ){
					eval(successCallBack+'(json);');
				}
				/* Validation*/
			} else if (isValidationError(json.result.errorCode)) {
				/* Llamar a funcion de error*/
				eval(errorCallBack+"("+json.result.errorCode+", '"+json.result.errorDesc+"');");
				/* Error */
			} else {
				eval(errorCallBack+"("+json.result.errorCode+", '"+json.result.errorDesc+"');");
				//alert('codError: '+json.result.errorCode);
				//alert('desError: '+json.result.errorDesc);	
			}
		}
		, timeout:15000
		, error: function(msg){
			/*Por aca sale por errores en el action y por si el servidor esta bajo o algo asi...*/
			if (msg.responseText == null || msg.responseText == '') {
				eval(errorCallBack+'(6, "El servidor se encuentra fuera de servicio");');
			/*Validaciones desde el servidor (usando validation.xml)*/
			} else if (msg.responseText.indexOf('validation-msg') > 0) {
				eval(errorCallBack+"("+VALIDATION_ERROR_CONSTANT+", msg.responseText);");
			} else {
				/*TODO: CAMBIAR O TERMINAR DE DEFINIR BIEN LOS CODIGOS*/
				//eval(errorCallBack+"(6, '"+msg.responseText+"');");
				eval(errorCallBack+"(6, msg.responseText);");
		    }
		}
    }); 
}
function accentEncode(text) {
	var rp = String(text);
	//
	rp = rp.replace(/á/g, '&aacute;');
	rp = rp.replace(/é/g, '&eacute;');
	rp = rp.replace(/í/g, '&iacute');
	rp = rp.replace(/ó/g, '&oacute;');
	rp = rp.replace(/ú/g, '&uacute;');
	rp = rp.replace(/ñ/g, '&ntilde;');
	rp = rp.replace(/ü/g, '&uuml;');
	//
	rp = rp.replace(/Á/g, '&Aacute;');
	rp = rp.replace(/É/g, '&Eacute;');
	rp = rp.replace(/Í/g, '&Iacute;');
	rp = rp.replace(/Ó/g, '&Oacute;');
	rp = rp.replace(/Ú/g, '&Uacute;');
	rp = rp.replace(/Ñ/g, '&Ntilde;');
	rp = rp.replace(/Ü/g, '&Uuml;');
	return rp;
}
function accentDecode(tx){
	var rp = String(tx);
	//
	rp = rp.replace(/&aacute;/g, 'á');
	rp = rp.replace(/&eacute;/g, 'é');
	rp = rp.replace(/&iacute;/g, 'í');
	rp = rp.replace(/&oacute;/g, 'ó');
	rp = rp.replace(/&uacute;/g, 'ú');
	rp = rp.replace(/&ntilde;/g, 'ñ');
	rp = rp.replace(/&uuml;/g, 'ü');
	//
	rp = rp.replace(/&Aacute;/g, 'Á');
	rp = rp.replace(/&Eacute;/g, 'É');
	rp = rp.replace(/&Iacute;/g, 'Í');
	rp = rp.replace(/&Oacute;/g, 'Ó');
	rp = rp.replace(/&Uacute;/g, 'Ú');
	rp = rp.replace(/&Ñtilde;/g, 'Ñ');
	rp = rp.replace(/&Üuml;/g, 'Ü');
	//
	return rp;
}
var normalize = (function() {
	  var from = "ÃÀÁÄÂÈÉËÊÌÍÏÎÒÓÖÔÙÚÜÛãàáäâèéëêìíïîòóöôùúüûÑñÇç",
	      to   = "AAAAAEEEEIIIIOOOOUUUUaaaaaeeeeiiiioooouuuunncc",
	      mapping = {};
	 
	  for(var i = 0, j = from.length; i < j; i++ )
	      mapping[ from.charAt( i ) ] = to.charAt( i );
	 
	  return function( str ) {
	      var ret = [];
	      for( var i = 0, j = str.length; i < j; i++ ) {
	          var c = str.charAt( i );
	          if( mapping.hasOwnProperty( str.charAt( i ) ) )
	              ret.push( mapping[ c ] );
	          else
	              ret.push( c );
	      }
	      return ret.join( '' );
	  }
	 
	})();