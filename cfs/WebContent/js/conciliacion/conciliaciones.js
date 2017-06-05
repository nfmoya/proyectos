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

	$("#fhRemitoDesde").mask("99/99/9999",{placeholder:" "});
	$("#fhRemitoHasta").mask("99/99/9999",{placeholder:" "});
	$("#fhFinServicioDesde").mask("99/99/9999",{placeholder:" "});
	$("#fhFinServicioHasta").mask("99/99/9999",{placeholder:" "});
	
	$("#fhRemitoDesde").datepicker({
		changeMonth: true,
		changeYear: true,
		regional: 'es',
		yearRange: 'c-100:c',
		dateFormat: 'dd/mm/yy',
		showAnim: '',
		showOn: "button",
		duration: 0
	}).next('button').text('&nbsp;').addClass('ui-button-text').button({icons:{primary : 'ui-icon ui-icon-calendar'}}).removeClass().addClass('ui-button ui-widget ui-state-default ui-button-icon-only ui-corner-right ui-button-icon');

	$("#fhRemitoHasta").datepicker({
		changeMonth: true,
		changeYear: true,
		regional: 'es',
		yearRange: 'c-100:c',
		dateFormat: 'dd/mm/yy',
		showAnim: '',
		showOn: "button",
		duration: 0
	}).next('button').text('&nbsp;').addClass('ui-button-text').button({icons:{primary : 'ui-icon ui-icon-calendar'}}).removeClass().addClass('ui-button ui-widget ui-state-default ui-button-icon-only ui-corner-right ui-button-icon');

	$("#fhFinServicioDesde").datepicker({
		changeMonth: true,
		changeYear: true,
		regional: 'es',
		yearRange: 'c-100:c',
		dateFormat: 'dd/mm/yy',
		showAnim: '',
		showOn: "button",
		duration: 0
	}).next('button').text('&nbsp;').addClass('ui-button-text').button({icons:{primary : 'ui-icon ui-icon-calendar'}}).removeClass().addClass('ui-button ui-widget ui-state-default ui-button-icon-only ui-corner-right ui-button-icon');

	$("#fhFinServicioHasta").datepicker({
		changeMonth: true,
		changeYear: true,
		regional: 'es',
		yearRange: 'c-100:c',
		dateFormat: 'dd/mm/yy',
		showAnim: '',
		showOn: "button",
		duration: 0
	}).next('button').text('&nbsp;').addClass('ui-button-text').button({icons:{primary : 'ui-icon ui-icon-calendar'}}).removeClass().addClass('ui-button ui-widget ui-state-default ui-button-icon-only ui-corner-right ui-button-icon');

//	$('input[type="text"]').each(function() {
//        $(this).setMask($(this).attr('alt'));
//    });
	
	$('#btnBusCons').button({icons : {secondary : "ui-icon-search"}}).click(function() {search();});  
	$('#btnGrabar').button({icons : {secondary : "ui-icon-disk"}}).click(function() { grabar();});	
	$('#btnAprobar').button({icons : {secondary : "ui-icon-circle-check"}}).click(function() { aprobar();});	
//	$('#btnRepetidos').button({icons : {secondary : "ui-icon-circle-check"}}).click(function() { loadRepetidos();});	
	$('#btnImprimir').button({icons : {secondary : "ui-icon-print"}}).click(function() {exportGridPDF();});
	$('#btnExportar').button({icons : {secondary : "ui-icon-bookmark"}}).click(function() { exportGridXLS();});	
    $('#btnLimpiar').button({icons : {secondary : "ui-icon-power"}}).click(function() {limpiar();});

    $("#btnGrabar").addClass('ui-state-disabled');
    $("#btnGrabar").attr("disabled", "disabled");
    $("#btnAprobar").addClass('ui-state-disabled');
    $("#btnAprobar").attr("disabled", "disabled");
    $("#btnImprimir").addClass('ui-state-disabled');
    $("#btnImprimir").attr("disabled", "disabled");
    $("#btnExportar").addClass('ui-state-disabled');
    $("#btnExportar").attr("disabled", "disabled");
//  $("#btnRepetidos").addClass('ui-state-disabled');
//  $("#btnRepetidos").attr("disabled", "disabled");
    $("#stIgnoraVal").removeClass('ui-state-disabled');
    $("#stIgnoraVal").removeAttr("disabled");

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
      $('#diferenciasGrid').hide();
      $('#saldosGrid').hide();
      $('#divObservaciones').hide();

      cdConciliacion = $('#cdConciliacion').val();
	  if (validateInput() == true) {
//       $("#btnBusCons").addClass('ui-state-disabled'); 
//	     $("#btnBusCons").attr("disabled", "disabled");	
         if ($('#cdConciliacion').val() == 0) {
    	    $('#conciliacion').val('0');
         } else {
     	    $('#conciliacion').val(cdConciliacion);
         }
         $('#cdProveedor').val($('#filtroProveedorList').val());
         $('#cdSector').val($('#filtroSectorList').val());
         $('#cdProducto').val($('#filtroProductoList').val());
         $('#cdPeriodo').val($('#filtroPeriodoList').val());
         $('#observaciones').val('');
         $('#stConciliacion').val($('#filtroSituacionConciliacionList').val());
       
         /*
          * Obtengo el sector por de acuerdo con el perfil del usuario.  
          */
         var paramSector = getSectorByPerfil();
         var params = '';
         
         params += "cdConciliacion="+cdConciliacion;
         params += "&cdProveedor="+$('#cdProveedor').val();
         params += "&cdSector="+ paramSector;
         params += "&cdProducto="+$('#cdProducto').val();
         params += "&cdPeriodo="+$('#cdPeriodo').val();

         callJsonAction("consultaConciliacion.action", params, "successConciliacion", "errorConciliacion");
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
   if ($('#filtroProductoList').val() == '0') {
   	  err1 += 'Falta ingresar el Producto \n';
   }
   if ($('#filtroPeriodoList').val() == '0') {
   	  err1 += 'Falta ingresar el Periodo de Facturacion \n';   
   }
   
   if ($("#fhRemitoDesde").val() != '') {
      if (!(validateDate($("#fhRemitoDesde").val()))) {
       	  err1 += 'La Fecha Remito Desde es invalida \n';   
      }
   }

   if ($("#fhRemitoHasta").val() != '') {
      if (!(validateDate($("#fhRemitoHasta").val()))) {
       	  err1 += 'La Fecha Remito Hasta es invalida \n';   
      }
   }
   
   if ($("#fhFinServicioDesde").val() != '') {
      if (!(validateDate($("#fhFinServicioDesde").val()))) {
       	  err1 += 'La Fecha de Fin de Servicio Desde es invalida \n';   
      }
   }

   if ($("#fhFinServicioHasta").val() != '') {
      if (!(validateDate($("#fhFinServicioHasta").val()))) {
       	  err1 += 'La Fecha de Fin de Servicio Hasta es invalida \n';   
      }
   }
   
   if (validateDate($("#fhRemitoDesde").val()) && validateDate($("#fhRemitoHasta").val())) {
      var fd_  = $("#fhRemitoDesde").val();
      var fde = fd_.substr(6,4)+fd_.substr(3,2)+fd_.substr(0,2);
		
      var fh_  = $("#fhRemitoHasta").val();
      var fha  = fh_.substr(6,4)+fh_.substr(3,2)+fh_.substr(0,2);
		
      if (fde > fha) {
         err1 += "La fecha Remito Desde no debe ser mayor a la fecha Remito Hasta\n";
      } 
   }
   
   if (validateDate($("#fhFinServicioDesde").val()) && validateDate($("#fhFinServicioHasta").val())) {
      var fd_  = $("#fhFinServicioDesde").val();
      var fde = fd_.substr(6,4)+fd_.substr(3,2)+fd_.substr(0,2);
			
      var fh_  = $("#fhFinServicioHasta").val();
      var fha  = fh_.substr(6,4)+fh_.substr(3,2)+fh_.substr(0,2);
			
      if (fde > fha) {
         err1 += "La fecha Fin Servicio Desde no debe ser mayor a la fecha Fin Servicio Hasta\n";
      } 
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
function successConciliacion(jsonData){

   var ignoraVal = "";
   var fechaRMD = "";
   var fechaRMH = "";
   var fechaFSD = "";
   var fechaFSH = "";
   $('#filtroSituacionConciliacionList').selectmenu('disabled', false);	
   try {

      if (jsonData.ConciliacionCabeceraList!=undefined) {
         if (jsonData.ConciliacionCabeceraList.length>0) {
            for (var i=0;i<jsonData.ConciliacionCabeceraList.length;i++) {
            	var registro = jsonData.ConciliacionCabeceraList[i];
        	    $('#conciliacion').val(registro.cdConciliacion);
            	$('#cdProveedor').val(registro.cdProveedor);
         	    $('#cdSector').val(registro.cdSector);
        	    $('#cdProducto').val(registro.cdProducto);
        	    $('#cdPeriodo').val(registro.cdPeriodoFact);
        	    $('#observaciones').val(registro.nbObservaciones);
        	    $('#stConciliacion').val(registro.stConciliacion);
        	    setDescripcionEstado($('#stConciliacion').val());
//        	    $('#cdConciliacion').val(registro.cdConciliacion);
        	    ignoraVal = registro.stIgnoraVal;
       	        fechaRMD  = registro.fhRemitoDesde;
       	        fechaRMH  = registro.fhRemitoHasta;
    	        fechaFSD  = registro.fhFinSerDesde;
    	        fechaFSH  = registro.fhFinSerHasta;

              sinReg = false;
	        }
	     }else{
	    	 sinReg = true;
	     }
      }

      if ($('#cdConciliacion').val() != '0') {
         $('#filtroProveedorList').selectmenu('setValue', '0');
         $('#filtroSectorList').selectmenu('setValue', '0');
         $('#filtroProductoList').selectmenu('setValue', '0');
         $('#filtroPeriodoList').selectmenu('setValue', '0');
         $('#nbUniVal').val("");
         $('#nuValBrutoUniVal').val(""); 
         
      	 $('#filtroProveedorList').selectmenu('setValue', $('#cdProveedor').val());
         $('#filtroSectorList').selectmenu('setValue', $('#cdSector').val());

       	 $('#filtroSituacionConciliacionList').selectmenu('setValue', $('#stConciliacion').val());
         $('#fhRemitoDesde').val("");
	     if (fechaRMD.trim() != "") {
     	    $('#fhRemitoDesde').val(convierteFecha(fechaRMD)); 
	     }
      	 $('#fhRemitoHasta').val(""); 
	     if (fechaRMH.trim() != "") {
     	    $('#fhRemitoHasta').val(convierteFecha(fechaRMH)); 
	     }     
         $('#fhFinServicioDesde').val("");
	     if (fechaFSD.trim() != "") {
     	    $('#fhFinServicioDesde').val(convierteFecha(fechaFSD)); 
	     }
      	 $('#fhFinServicioHasta').val(""); 
	     if (fechaFSH.trim() != "") {
     	    $('#fhFinServicioHasta').val(convierteFecha(fechaFSH)); 
	     }
 	     if (ignoraVal == "S") {
 	        $('#stIgnoraVal').attr('checked','checked');
  	     } else {
  	        $('#stIgnoraVal').removeAttr('checked');
  	     } 
 	    
         consultarPeriodo($('#cdProveedor').val());  
  	     
         if ($('#cdSector').val() != '0' && $('#cdProducto').val() != '0' && $('#cdPeriodo').val() != '0') {
            var mensaje_alerta = "";
            if ($("#stPeriodo").val() != 'ABI') {
               mensaje_alerta = "PERIODO de FACTURACION no habilitado\n";
            }  
            if ($("#stProducto").val() != 'S') {
               mensaje_alerta = "PRODUCTO no habilitado\n";
	        }
	        if ($("#stProductoSector").val() != 'S') {
  	           mensaje_alerta = "Relacion PRODUCTO SECTOR no habilitado\n";
	        }  
            if (mensaje_alerta != "") {
            	fAlert(mensaje_alerta);
            }  	     
         }  	     
      }
      
      if ($('#cdSector').val() != '0' && $('#cdProducto').val() != '0' && $('#cdPeriodo').val() != '0') {
         cargarDatosConciliacion($('#cdProveedor').val(), $('#cdProducto').val());
      }else{
        alertSinRegistros();
      }  
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
/* CARGA LOS DATOS DE CONCILIACION RELACIONADOS CON LA CONFIGURACION DEL PRODUCTO                         */
/*--------------------------------------------------------------------------------------------------------*/
function cargarDatosConciliacion(cdProveedor, cdProducto) {
  callJsonAction("productoConciliacion.action","cdProveedor="+$('#cdProveedor').val()+"&cdProducto="+$('#cdProducto').val(), "successDatosConciliacion", "errorConciliacion");
}

function successDatosConciliacion(jsonData) {
   try {

      if (jsonData.DetalleProducto != undefined) {
 	     if (jsonData.DetalleProducto.length>0) {
             $('#stConcilSinVal').val(jsonData.DetalleProducto[0].stConcilSinVal);       
             $('#stServSinConcil').val(jsonData.DetalleProducto[0].stServSinConcil);       
             $('#stFactSinConcil').val(jsonData.DetalleProducto[0].stFactSinConcil);       
             $('#cdUniVal').val(jsonData.DetalleProducto[0].cdUniVal);

             if ($('#stConcilSinVal').val() == 'S') {
  	            $("#stIgnoraVal").removeClass('ui-state-disabled');
                $("#stIgnoraVal").removeAttr("disabled");
             } else {
                $("#stIgnoraVal").addClass('ui-state-disabled');
                $("#stIgnoraVal").attr("disabled", "disabled");
             }             
         }
      }

	  loadGrillas();
	  
   } catch(e) {
      jsError('errorProducto', e);
   }
}

/*--------------------------------------------------------------------------------------------------------*/
/* LIMPIA Y CARGA LAS GRILLAS CONCILIACIONES, DIFERENCIAS Y SALDOS                                        */
/*--------------------------------------------------------------------------------------------------------*/
function loadGrillas() {
   loadSaldosGrid();
		
   limpiarGrilla('gridDiferenciasId' , 'gridDiferenciasPager' , 'gridDiferencias');
   loadDiferenciasGrid();
    
   limpiarGrilla('gridConciliacionesId' , 'gridConciliacionesPager' , 'gridConciliaciones');
// limpiarGrillaSinPagina('gridConciliacionesId' , 'gridConciliaciones');
   loadConciliacionesGrid();

   var rowsConcil = jQuery('#gridConciliacionesId').jqGrid('getGridParam', 'records');
   console.log(rowsConcil);
  
   if (rowsConcil > 0) { 
      $('#conciliacionesGrid').show();
	  $('#saldosGrid').show();  
	  $('#diferenciasGrid').show();  
	  $('#divObservaciones').show();

	  sinReg = false;
   } else {
	  sinReg = true;
	  alertSinRegistros();   
   }
}


/*--------------------------------------------------------------------------------------------------------*/
// LIMPIA Y CARGA LAS GRILLAS CONCILIACIONES, DIFERENCIAS Y SALDOS                                        
// SE DEBE RESPETAR EL ORDEN DE CARGA PORQUE LA FUNCION TOTALIZA NECESITA QUE ESTEN CARGADAS LAS GRILLAS  
// DE DIFERENCIAS Y SALDOS                                                                                 
/*--------------------------------------------------------------------------------------------------------*/
/*
function loadGrillas() {
  
  limpiarGrilla('gridConciliacionesId' , 'gridConciliacionesPager' , 'gridConciliaciones');
  limpiarGrilla('gridDiferenciasId' , 'gridDiferenciasPager' , 'gridDiferencias');
  // limpiarGrillaSinPagina('gridConciliacionesId' , 'gridConciliaciones'); 
  loadConciliacionesGrid();

  var rowsConcil = jQuery('#gridConciliacionesId').jqGrid('getGridParam', 'records');
  console.log(rowsConcil);
 
  if(rowsConcil > 0){   
    loadDiferenciasGrid();
    loadSaldosGrid();

    $('#conciliacionesGrid').show();
    $('#saldosGrid').show();  
    $('#diferenciasGrid').show();  
    $('#divObservaciones').show();

    sinReg = false;

  }else{
	  sinReg = true;
	  alertSinRegistros();   
  }  	
}
*/

/*--------------------------------------------------------------------------------------------------------*/
/* CARGA LA GRILLA DE REMITOS REPETIDOS, SI TIENE REGISTROS                                               */
/*--------------------------------------------------------------------------------------------------------*/
function loadRepetidos() {
   limpiarGrilla('gridRepetidosId' , 'gridRepetidosPager' , 'gridRepetidos');
   loadRemitosRepetidos();
   var rowsRepetidos = $('#gridRepetidosId').jqGrid('getRowData');

   if (rowsRepetidos.length > 0) {
      $('#gridRepetidosId').show();
      $('#dialogRemitosRepetidos').dialog('option','title','Consultar Remitos Repetidos');
      $('#dialogRemitosRepetidos').dialog('open');
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
      $('#filtroProductoList').selectmenu('setValue', '0');
      $('#filtroPeriodoList').selectmenu('setValue', '0');
      $('#fhRemitoDesde').val("");
      $('#fhRemitoHasta').val("");
      $('#fhFinServicioDesde').val("");
      $('#fhFinServicioHasta').val(""); 
      $('#filtroSituacionConciliacionList').selectmenu('setValue', 'PEN');	
      $('#filtroConciliacionList').selectmenu('setValue', '0');
      $('#cdConciliacion').val('0');

      $('#conciliacionesGrid').hide();
      $('#diferenciasGrid').hide();
      $('#saldosGrid').hide();
      $('#divObservaciones').hide();
   
      $("#btnGrabar").addClass('ui-state-disabled');
      $("#btnGrabar").attr("disabled", "disabled");
      $("#btnAprobar").addClass('ui-state-disabled');
      $("#btnAprobar").attr("disabled", "disabled");
//    $("#btnRepetidos").addClass('ui-state-disabled');
//    $("#btnRepetidos").attr("disabled", "disabled");
      
      $("#add_dif").addClass('ui-state-disabled');
      $("#edit_dif").addClass('ui-state-disabled');
   
//    $("#btnBusCons").removeClass('ui-state-disabled');
//    $("#btnBusCons").removeAttr("disabled");
   
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
      consultarDetallePeriodo($('#cdProveedor').val(), $('#cdPeriodo').val());
      consultarProducto($('#cdProveedor').val(), $('#cdSector').val());      
   } catch(e) {
   }
}

function errorConsultarPeriodos(cod, des){
}

/*--------------------------------------------------------------------------------------------------------*/
/* PRODUCTO                                                                                               */
/*--------------------------------------------------------------------------------------------------------*/
function consultarProducto(cdProveedor, cdSector) {
   callJsonSyncAction("comboConciliacionPeriodos.action","opcion=1&cdProveedor="+cdProveedor+"&cdSector="+cdSector, "successConsultarProductos", "errorConsultarProductos");
}

function successConsultarProductos(jsonData) {
   try {
      document.getElementById('filtroProductoList').options.length=0;
      $('#filtroProductoList').selectmenu('destroy');
   
      if (jsonData.ProductoPrecioList != undefined){
    	 resetProductoOption(' Sin Producto ', '0', true);	
         resetConciliacionOption(' Sin Conciliacion ', '0', false);	

	     if (jsonData.ProductoPrecioList.length>0){
	        for (var i=0;i<jsonData.ProductoPrecioList.length;i++)
		       document.getElementById('filtroProductoList')[document.getElementById('filtroProductoList').options.length] = new Option((jsonData.ProductoPrecioList[i]).desc, jsonData.ProductoPrecioList[i].cod);
	        
	        $('#filtroProductoList').selectmenu('destroy');	
	        $('#filtroProductoList').selectmenu({style:'dropdown', maxHeight:'180', width:'250px'});
		 }
         $('#filtroProductoList').selectmenu('setValue', $('#cdProducto').val());
	     consultarDetalleProducto($('#cdProveedor').val(), $('#cdSector').val(), $('#cdProducto').val());
	  }
   } catch(e) {
	   fAlert("ERROR 1");
   }
}

function errorConsultarProductos(cod, des){
	fAlert("ERROR 2");
}

/*--------------------------------------------------------------------------------------------------------*/
/* DETALLE PERIODO                                                                                        */
/*--------------------------------------------------------------------------------------------------------*/
function consultarDetallePeriodo(cdProveedor, cdPeriodo) {
   callJsonSyncAction("periodoConciliacion.action","cdProveedor="+cdProveedor+"&cdPeriodo="+cdPeriodo, "successConsultarDetallePeriodo", "errorConsultarDetallePeriodo");
}

function successConsultarDetallePeriodo(jsonData) {
   try {
      if (jsonData.DetallePeriodo != undefined) {
	     if (jsonData.DetallePeriodo.length>0) {
            $('#fhDesde').val(jsonData.DetallePeriodo[0].fhDesde);
            $('#fhHasta').val(jsonData.DetallePeriodo[0].fhHasta);
            $('#stPeriodo').val(jsonData.DetallePeriodo[0].stEstado);

 	        consultarProveedorValor($('#cdProveedor').val(), $('#cdPeriodo').val(), $('#cdUniVal').val());
		 }
	  }
   } catch(e) {
//    jsError('errorPeriodo', e);
   }
}

function errorConsultarDetallePeriodo(cod, des) {
//   jsError(cod, des);
}

/*--------------------------------------------------------------------------------------------------------*/
/* DETALLE PRODUCTO                                                                                       */
/*--------------------------------------------------------------------------------------------------------*/
function consultarDetalleProducto(cdProveedor, cdSector, cdProducto) {
   callJsonSyncAction("productoConciliacion.action","cdProveedor="+cdProveedor+"&cdSector="+cdSector+"&cdProducto="+cdProducto, "successConsultarDetalleProducto", "errorConsultarDetalleProducto");
}

function successConsultarDetalleProducto(jsonData) {
   try {
      if (jsonData.DetalleProducto != undefined) {
	     if (jsonData.DetalleProducto.length>0) {
	    	if ($('#cdConciliacion').val() != "") { 
               $('#nuDiaEmiFDesde').val(jsonData.DetalleProducto[0].nuDiaEmiFDesde);
               $('#nuDiaEmiFHasta').val(jsonData.DetalleProducto[0].nuDiaEmiFHasta);
               $('#nuDiaCierreFDesde').val(jsonData.DetalleProducto[0].nuDiaCierreFDesde);
               $('#nuDiaCierreFHasta').val(jsonData.DetalleProducto[0].nuDiaCierreFHasta);
	    	}
            $('#stConcilSinVal').val(jsonData.DetalleProducto[0].stConcilSinVal);       
            $('#stServSinConcil').val(jsonData.DetalleProducto[0].stServSinConcil);       
            $('#stFactSinConcil').val(jsonData.DetalleProducto[0].stFactSinConcil);       
            $('#cdUniVal').val(jsonData.DetalleProducto[0].cdUniVal);
            $('#stProducto').val(jsonData.DetalleProducto[0].stHabilitado);
            $('#stProductoSector').val(jsonData.DetalleProducto[0].stRelacionSector);
            consultarUniVal($('#cdUniVal').val());
 	     }
	  }
   } catch(e) {
//    jsError('errorProducto', e);
   }
}

function errorConsultarDetalleProducto(cod, des) {
//   jsError(cod, des);
}

/*--------------------------------------------------------------------------------------------------------*/
/* DETALLE UNIVAL                                                                                         */
/*--------------------------------------------------------------------------------------------------------*/
function consultarUniVal(cdUnival) {
   $('#nbUniVal').val("");	
   callJsonSyncAction("uniValConciliacion.action","cdUniVal="+cdUnival, "successConsultarDetalleUniVal", "errorConsultarDetalleUniVal");
}

function successConsultarDetalleUniVal(jsonData) {
   try {
      if (jsonData.DetalleUniVal != undefined) {
	     if (jsonData.DetalleUniVal.length>0) {
	       $('#nbUniVal').val(jsonData.DetalleUniVal[0].cdCodTabla + ' - '+jsonData.DetalleUniVal[0].nbCodTabla);
	       consultarProveedorValor($('#cdProveedor').val(), $('#cdPeriodo').val(), $('#cdUniVal').val());
		 }
	  }
   } catch(e) {
//    jsError('errorUniVal', e);
   }
}

function errorConsultarDetalleUniVal(cod, des) {
//   jsError(cod, des);
}

/*--------------------------------------------------------------------------------------------------------*/
/* IMPORTE VALOR MONETARIO PROVEEDOR PERIODO UNIVAL                                                       */
/*--------------------------------------------------------------------------------------------------------*/
function consultarProveedorValor(cdProveedor, cdPeriodo, cdUnival) {
   $('#nuValBrutoUniVal').val("");	
   callJsonSyncAction("proveedorValorConciliacion.action","cdProveedor="+cdProveedor+"&cdPeriodo="+cdPeriodo+"&cdUniVal="+cdUnival, "successConsultarDetalleProveedorValor", "errorConsultarDetalleProveedorValor");
}

function successConsultarDetalleProveedorValor(jsonData) {
   try {
      if (jsonData.DetalleProveedorValor != undefined) {
	     if (jsonData.DetalleProveedorValor.length>0) {
	       $('#nuValBrutoUniVal').val(jsonData.DetalleProveedorValor[0].nuValBrutoUniVal);
		 }
	  }
   } catch(e) {
//    jsError('errorUniVal', e);
   }
}

function errorConsultarDetalleProveedorValor(cod, des) {
//   jsError(cod, des);
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
