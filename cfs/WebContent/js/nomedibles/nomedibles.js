//new
var sinReg;

$(document).ready(function(){	
	/*--------------------------------------------------------------------------------------------------------*/
	// REALIZA LA CONFIGURACION INICIAL DE LOS CONTROLES DEL FORMULARIO DE FILTROS                            
	// INICIALIZA LOS COMBOS, LOS DIALOGOS, LOS FILTROS, LOS CAMPOS DE FECHAS, LAS MASCARAS DE LOS CAMPOS, 
	// Y LOS BOTONES 
	/*--------------------------------------------------------------------------------------------------------*/

	/*
     Ver completo en http://unicode.coeurlumiere.com/ 
     http://www.etnassoft.com/2010/12/28/cadenas-de-escape-como-poner-tildes-en-javascript/
	*/
	
	sinReg = false;
	
	destroyCombos();

	$.ajaxSetup({
        cache : false,
        async : false
    });	
    
	initDialog();
   	$('#filtroSectorList').selectmenu('setValue', $("#sector").val());	
   	if ($("#sector").val() != "0") {
       $('#filtroSectorList').selectmenu('disabled', true);	
    }
    initialiseFiltros();
	
	$('#btnBusCons').button({icons : {secondary : "ui-icon-search"}}).click(function() {search();});  
	$('#btnGrabar').button({icons : {secondary : "ui-icon-disk"}}).click(function() { grabar();});	
	$('#btnAprobar').button({icons : {secondary : "ui-icon-circle-check"}}).click(function() { aprobar();});	
	$('#btnImprimir').button({icons : {secondary : "ui-icon-print"}}).click(function() {exportGridPDF();});
	$('#btnExportar').button({icons : {secondary : "ui-icon-bookmark"}}).click(function() { exportGridXLS();});	
    $('#btnLimpiar').button({icons : {secondary : "ui-icon-power"}}).click(function() {limpiar();});
    $('#btnObsAll').button().click(function() {editAllDiferencias();});    

    $("#btnGrabar").addClass('ui-state-disabled');
    $("#btnGrabar").attr("disabled", "disabled");
    $("#btnAprobar").addClass('ui-state-disabled');
    $("#btnAprobar").attr("disabled", "disabled");
    $("#btnImprimir").addClass('ui-state-disabled');
    $("#btnImprimir").attr("disabled", "disabled");
    $("#btnExportar").addClass('ui-state-disabled');
    $("#btnExportar").attr("disabled", "disabled");

    $("#btnObsAll").addClass('ui-state-disabled');
    $("#btnObsAll").attr("disabled", "disabled");
    
    $('#grabada').val('N');
    $('#modificada').val('N');  
    $('#recargaConciliacion').val("N");	
    
    $('#panelExport').show();
    if($('#cdConciliacion').val() != '0'){
    	search();
    }
});

/*--------------------------------------------------------------------------------------------------------*/
/* REALIZA LA BUSQUEDA DE ACUERDO A LOS VALORES INGRESADOS Y VALIDADOS                                    */
/*--------------------------------------------------------------------------------------------------------*/
function search() {
   var cdConciliacion;
   var continua = "S";

   if ($('#modificada').val() == "S") {
      if (confirm("Desea realizar una nueva consulta sin grabar los cambios?") == false) {
         continua = "N";
      }
   }
   $('#conciliacion_responseMsgs').hide();   
   if (continua == "S") {
      if ($('#cdConciliacion').val() == '') {
         $('#cdConciliacion').val('0');	
      }

      $('#conciliacion_responseMsgs').hide();   
      $("#grabada").val('N');
      $('#conciliacionesGrid').hide();

      cdConciliacion = $('#cdConciliacion').val();
	  if (validateInput() == true) {
         if ($('#cdConciliacion').val() == 0) {
    	    $('#conciliacion').val('0');
         } else {
     	    $('#conciliacion').val(cdConciliacion);
         }         
         $('#cdProveedor').val($('#filtroProveedorList').val());
         $('#cdSector').val($('#filtroSectorList').val());
         $('#cdPeriodo').val($('#filtroPeriodoList').val());
         $('#stConciliacion').val($('#filtroSituacionConciliacionList').val());
       
         /*
          * Obtengo el sector por de acuerdo con el perfil del usuario.  
          */
         var paramSector = getSectorByPerfil();
         var params = '';
         
         params += "cdConciliacion="+cdConciliacion;
         params += "&cdProveedor="+$('#cdProveedor').val();
//       params += "&cdSector="+ paramSector;
         params += "&cdSector="+$('#cdSector').val();
         params += "&cdPeriodo="+$('#cdPeriodo').val();
//alert(params);
         callJsonAction("consultaNoMedibles.action", params, "successConciliacion", "errorConciliacion");
      }
   }
   $("#btnImprimir").removeClass('ui-state-disabled');
   $("#btnImprimir").removeAttr("disabled");
   $("#btnExportar").removeClass('ui-state-disabled');
   $("#btnExportar").removeAttr("disabled");
}

/*--------------------------------------------------------------------------------------------------------*/
/* REALIZA LA VALIDACION DE LOS FILTROS INGRESADOS                                                        */
/*--------------------------------------------------------------------------------------------------------*/
function validateInput() {
   var err1 = '';
   var err2 = '';
   var err3 = '';
   if ($('#filtroProveedorList').val() == '0') {
   	  err1 += 'Falta ingresar el Proveedor \n';
   }
   if ($('#filtroSectorList').val() == '0') {
   	  err1 += 'Falta ingresar el Sector \n';
   }
   if ($('#filtroPeriodoList').val() == '0') {
   	  err1 += 'Falta ingresar el Periodo de Facturacion \n';   
   }
   if ($('#filtroSituacionConciliacionList').val() != 'PEN') {
      if ($('#cdConciliacion').val() == '0') {
         err2 += '\nDebe ingresar el Numero de Conciliacion \n';
      }
   }
   if (err1 != '' || err2 != '') {
      if ($('#cdConciliacion').val() == '0') {
    	 err3 += '\nDebe ingresar el Numero de Conciliacion \n';
      }
   }
   var retorna = false;
   
   if (err1 == '' && err2 == '') {
      retorna = true;
   } else {
      if (err3 == '') {
	     retorna = true;
      }
   }
   if (retorna == false) {
	   fAlert(err1+err2);
   }
   return retorna;
}

/*--------------------------------------------------------------------------------------------------------*/
// OBTIENE LOS DATOS DE LA CONCILIACION Y LLENA LOS FILTROS                                               
// CUANDO SE INGRESA EL NUMERO DE CONCILIACION SE LLENAN TODOS LOS COMBOS Y SE LE SETEAN LOS VALORES
// OBTENIDOS EN LA CONSULTA (PROVEEDOR, SECTOR, PERIODO, PRODUCTO Y ESTADO CONCILIACION)
/*--------------------------------------------------------------------------------------------------------*/
function successConciliacion(jsonData) {
   $('#filtroSituacionConciliacionList').selectmenu('disabled', false);	
   try {

      if (jsonData.ConciliacionCabeceraList!=undefined) {
         if (jsonData.ConciliacionCabeceraList.length>0) {
            for (var i=0;i<jsonData.ConciliacionCabeceraList.length;i++) {
            	var registro = jsonData.ConciliacionCabeceraList[i];
        	    $('#conciliacion').val(registro.cdConciliacion);
            	$('#cdProveedor').val(registro.cdProveedor);
         	    $('#cdSector').val(registro.cdSector);
        	    $('#cdPeriodo').val(registro.cdPeriodoFact);
        	    $('#stConciliacion').val(registro.stConciliacion);
        	    setDescripcionEstado($('#stConciliacion').val());

              sinReg = false;
	        }
	     }else{
	    	 sinReg = true;
	     }
      }

      if ($('#cdConciliacion').val() != '0') {
         $('#filtroProveedorList').selectmenu('setValue', '0');
         $('#filtroSectorList').selectmenu('setValue', '0');
         $('#filtroPeriodoList').selectmenu('setValue', '0');
         
      	 $('#filtroProveedorList').selectmenu('setValue', $('#cdProveedor').val());
         $('#filtroSectorList').selectmenu('setValue', $('#cdSector').val());

       	 $('#filtroSituacionConciliacionList').selectmenu('setValue', $('#stConciliacion').val());
         consultarPeriodo($('#cdProveedor').val());  
         
         if($('#stConciliacion').val() == 'APR'){
             $("#checkAll").addClass('ui-state-disabled');
             $("#checkAll").attr("disabled", "disabled");   
         }   
           	     
         if ($('#cdSector').val() != '0' && $('#cdPeriodo').val() != '0') {
            var mensaje_alerta = "";
/*            
            if ($("#stPeriodo").val() != 'ABI') {
               mensaje_alerta = "PERIODO de FACTURACION no habilitado\n";
            }  
*/            
            if (mensaje_alerta != "") {
            	fAlert(mensaje_alerta);
            }  	     
         }  	     
      }
      loadGrillas();
      
   } catch(e) {
      jsError('errorConciliacion', e);
   }
}

function errorConciliacion(cod, des){
   jsError(cod, des);
}

function convierteFecha(fecha) {
   return fecha.substr(8,2)+'/'+fecha.substr(5,2)+'/'+fecha.substr(0,4);	
}

/*--------------------------------------------------------------------------------------------------------*/
/* LIMPIA Y CARGA LAS GRILLAS CONCILIACIONES, DIFERENCIAS Y SALDOS                                        */
/*--------------------------------------------------------------------------------------------------------*/
function loadGrillas() {
   limpiarGrilla('gridConciliacionesId' , 'gridConciliacionesPager' , 'gridConciliaciones');
   loadConciliacionesGrid();

   var rowsConcil = jQuery('#gridConciliacionesId').jqGrid('getGridParam', 'records');
   console.log(rowsConcil);
  
   if (rowsConcil > 0) { 
      $('#conciliacionesGrid').show();

	  sinReg = false;
   } else {
	  sinReg = true;
	  alertSinRegistros();   
   }
}

/*--------------------------------------------------------------------------------------------------------*/
/* LIMPIA TODAS LAS VARIABLES Y OCULTA LAS GRILLAS DEJANDO LA PANTALLA COMO AL INGRESAR POR 1RA VEZ
/*--------------------------------------------------------------------------------------------------------*/
function limpiar() {
   var continua = 'S';
   if ($('#modificada').val() == "S") {
	  if (confirm("Se realizaron modificaciones en la conciliacion, \n" +
                  "si continua las perdera. Desea continuar?") == false) {
         continua = 'N';
      }
   }
   if (continua == "S") {
      $('#filtroProveedorList').selectmenu('setValue', '0');
      $('#filtroSectorList').selectmenu('disabled', false);	
      $('#filtroSectorList').selectmenu('setValue', '0');	
      if ($("#sector").val() != "0") {
         $('#filtroSectorList').selectmenu('setValue', $("#sector").val());	
         $('#filtroSectorList').selectmenu('disabled', true);	
      }
      $('#filtroPeriodoList').selectmenu('setValue', '0');
      $('#filtroSituacionConciliacionList').selectmenu('setValue', 'PEN');	
      $('#filtroConciliacionList').selectmenu('setValue', '0');
      $('#cdConciliacion').val('0');

      $('#conciliacionesGrid').hide();
   
      $("#btnGrabar").addClass('ui-state-disabled');
      $("#btnGrabar").attr("disabled", "disabled");
      $("#btnAprobar").addClass('ui-state-disabled');
      $("#btnAprobar").attr("disabled", "disabled");  
   
      $("#modificada").val('N');
      $("#grabada").val('N');
      borrarMensaje();
   }      
}

/*--------------------------------------------------------------------------------------------------------*/
/* FUNCIONES UTILIZADAS PARA LLENAR LOS FILTROS CUANDO SE INGRESA EL NRO DE CONCILIACION                  */
/* SE USAN FUNCIONES DE AJAX DIFERENTES A LA CARGA, DE MANERA QUE SE CARGUEN TODOS EN FORMA SINCRONICA    */
/*--------------------------------------------------------------------------------------------------------*/

/*--------------------------------------------------------------------------------------------------------*/
/* PERIODO                                                                                                */
/*--------------------------------------------------------------------------------------------------------*/
function consultarPeriodo(cdProveedor) {
   callJsonSyncAction("comboConciliacionPeriodos.action","opcion=2&cdProveedor="+cdProveedor, "successConsultarPeriodos" , "errorConsultarPeriodos");
}

function successConsultarPeriodos(jsonData){
   try {
      document.getElementById('filtroPeriodoList').options.length=0;
      $('#filtroPeriodoList').selectmenu('destroy');
	      
      if (jsonData.ProveedorPeriodoList!=undefined) {
         resetPeriodoOption(' Sin Periodo ', '0', true);	
         
         if (jsonData.ProveedorPeriodoList.length>0) {
    	    for (var i=0;i<jsonData.ProveedorPeriodoList.length;i++)
    		    document.getElementById('filtroPeriodoList')[document.getElementById('filtroPeriodoList').options.length] = new Option((jsonData.ProveedorPeriodoList[i]).desc, jsonData.ProveedorPeriodoList[i].cod);
    	    
            $('#filtroPeriodoList').selectmenu('destroy');	
            $('#filtroPeriodoList').selectmenu({style:'dropdown', maxHeight:'180', width:'250px'});
         }
      }
      $('#filtroPeriodoList').selectmenu('setValue', $('#cdPeriodo').val());
   } catch(e) {
   }
}

function errorConsultarPeriodos(cod, des){
}

/**
 *
 * Hay que validar el perfil del usuario para saber si se le permite o no
 * buscar todos los sectores o solo el propio
 *
 * @author David
 * 
 * @returns {String} -> Retorna el sector
 */
function getSectorByPerfil(){
	
	var perfilUser = $('#hidUserPerfil').val();
    
    if(perfilUser == 1 || perfilUser == 5 || perfilUser == 7)
      paramSector = '0';
    else
      paramSector = $('#cdSector').val();
    
    return paramSector;
	
}

/**
 * Funcion que verifica que no haya registro ni en la grilla ni en el filtro y 
 * muestra el cartel de aviso
 * 
 * @autor David XA50126 
 */
function alertSinRegistros(){

	if(sinReg){
		fAlertCB('&nbsp;&nbsp; No se encontraron resultado para la busqueda &nbsp;');
		sinReg = false;
	}	
}
