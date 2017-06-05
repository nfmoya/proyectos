/*--------------------------------------------------------------------------------------------------------*/
/* INICIALIZA FILTROS */
/*--------------------------------------------------------------------------------------------------------*/
function initialiseFiltros(){
   	if ($('#filtroProveedorList').children('option').length == 1) {
   	   recargarPeriodo($('#filtroProveedorList').val());   		
   	}
	
	var $proveedor = $('#filtroProveedorList');
	$proveedor.change(function(){
	   hideCommonDataElements();
       limpiarProveedor();
	   recargarPeriodo($('#filtroProveedorList').val());
	});
	onchangeOptions('filtroProveedorList');

	var $sector = $('#filtroSectorList');
	$sector.change(function(){
	   hideCommonDataElements();
       limpiarSector();
	});
	onchangeOptions('filtroSectorList');

	var $periodo = $('#filtroPeriodoList');
	$periodo.change(function(){
	   borrarMensaje();
	   obtenerPeriodo($('#filtroProveedorList').val(), $('#filtroPeriodoList').val());
	});
	
	var situacion = $('#filtroSituacionConciliacionList');
	situacion.change(function(){
	   borrarMensaje();
       recargarNroConciliacion();
	});
	
	var conciliacion = $('#filtroConciliacionList');
	conciliacion.change(function(){
       borrarMensaje();
       actualizaCdConciliacion();
	});
}

function borrarMensaje() {
   $('#cdConciliacion').val('0');	
   $('#conciliacion_responseMsgs').hide();   
}

function actualizaCdConciliacion() {
	$('#cdConciliacion').val($('#filtroConciliacionList').val());
}

function onchangeOptions(selectorId){
   $("#"+selectorId).change(function(){
      hideCommonDataElements();
   });
}

function hideCommonDataElements(){
   if ($('#filtroProveedorList').val()=="0"){
      resetPeriodoOption( ' Sin Periodo  ', '0', false);	
      resetConciliacionOption(' Sin Conciliacion ', '0', false);	
   }
   if ($('#filtroSectorList').val()=="0"){
      resetConciliacionOption(' Sin Conciliacion ', '0', false);	
   }   
}

function limpiarProveedor() {
   limpiarSector();
}

function limpiarSector() {
   borrarMensaje();
   $('#stServSinConcil').val("S");
   $('#stFactSinConcil').val("S");   
}

function resetPeriodoOption(label, value, disabled){
   document.getElementById('filtroPeriodoList').options.length=0;
   document.getElementById('filtroPeriodoList')[0]= new Option(label,value);
   $('#filtroPeriodoList').selectmenu('destroy');
   $('#filtroPeriodoList').selectmenu({style:'dropdown', maxHeight:'180', width:'250px'}).selectmenu('disabled',true);
}

/*--------------------------------------------------------------------------------------------------------*/
/* FUNCIONES DE RECARGA */
/*--------------------------------------------------------------------------------------------------------*/

/*--------------------------------------------------------------------------------------------------------*/
/* RECARGA PERIODOS */
/*--------------------------------------------------------------------------------------------------------*/
function recargarPeriodo(cdProveedor) {
   callJsonAction("comboNoMediblesPeriodos.action","opcion=2&cdProveedor="+cdProveedor, "successPeriodos" , "errorPeriodos");
}

function successPeriodos(jsonData){
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
         recargarNroConciliacion();         
      }
   } catch(e) {
//    jsError('errorPeriodos', e);
   }
}

function errorPeriodos(cod, des){
//   jsError(cod, des);
}

/*--------------------------------------------------------------------------------------------------------*/
/* DETALLE PERIODO */
/*--------------------------------------------------------------------------------------------------------*/
function obtenerPeriodo(cdProveedor, cdPeriodo) {
   callJsonAction("periodoNoMedibles.action","cdProveedor="+cdProveedor+"&cdPeriodo="+cdPeriodo, "successDetallePeriodo", "errorDetallePeriodo");
}

function successDetallePeriodo(jsonData) {
   try {
      if (jsonData.DetallePeriodo != undefined) {
	     if (jsonData.DetallePeriodo.length>0) {
            $('#stPeriodo').val(jsonData.DetallePeriodo[0].stEstado);
/*            
 	        if ($("#stPeriodo").val() != 'ABI') {
 	        	alert("PERIODO de FACTURACION no habilitado");
 	        }  
*/ 	        
		 }
	  }
   } catch(e) {
//    jsError('errorPeriodo', e);
   }
}

function errorDetallePeriodo(cod, des) {
//   jsError(cod, des);
}

/*--------------------------------------------------------------------------------------------------------*/
/* RECARGA CONCILIACION */
/*--------------------------------------------------------------------------------------------------------*/
function recargarNroConciliacion() {
   var filtro = $('#filtroProveedorList').val()+';'+$('#filtroSectorList').val()+';'+$('#filtroPeriodoList').val()+';'+$('#filtroSituacionConciliacionList').val()+';';
   callJsonAction("comboNroNoMedibles.action","filtro="+filtro, "successNroConciliacion", "errorNroConciliacion");
}

function successNroConciliacion(jsonData) {
   try {
      document.getElementById('filtroConciliacionList').options.length=0;
      $('#filtroConciliacionList').selectmenu('destroy');
   
      if (jsonData.ConciliacionList != undefined){
    	 resetConciliacionOption(' Sin Conciliacion ', '0', true);	

	     if (jsonData.ConciliacionList.length>0){
	        for (var i=0;i<jsonData.ConciliacionList.length;i++)
		       document.getElementById('filtroConciliacionList')[document.getElementById('filtroConciliacionList').options.length] = new Option((jsonData.ConciliacionList[i]).desc, jsonData.ConciliacionList[i].cod);
	        
	        $('#filtroConciliacionList').selectmenu('destroy');	
	        $('#filtroConciliacionList').selectmenu({style:'dropdown', maxHeight:'180', width:'350px'});
		 }
	  }
   } catch(e) {
//    jsError('errorConciliacion', e);
   }
}

function errorNroConciliacion(cod, des){
//   jsError(cod, des);
}

function resetConciliacionOption(label, value, disabled){
   document.getElementById('filtroConciliacionList').options.length=0;
   document.getElementById('filtroConciliacionList')[0]= new Option(label,value);
   $('#filtroConciliacionList').selectmenu('destroy');
   $('#filtroConciliacionList').selectmenu({style:'dropdown', maxHeight:'180', width:'350px'}).selectmenu('disabled',true);
}

/*--------------------------------------------------------------------------------------------------------*/
/*--------------------------------------------------------------------------------------------------------*/

function destroyCombos() {
	$('#filtroProveedorList').selectmenu('destroy');	
	$('#filtroProveedorList').selectmenu({style:'dropdown', maxHeight:'180', width:'250px'});

	$('#filtroSectorList').selectmenu('destroy');	
	$('#filtroSectorList').selectmenu({style:'dropdown', maxHeight:'180', width:'250px'});

	$('#filtroPeriodoList').selectmenu('destroy');	
	$('#filtroPeriodoList').selectmenu({style:'dropdown', maxHeight:'180', width:'250px'});
		
	$('#filtroSituacionConciliacionList').selectmenu('destroy');	
	$('#filtroSituacionConciliacionList').selectmenu({style:'dropdown', width:'250px'});
	
	$('#filtroConciliacionList').selectmenu('destroy');	
	$('#filtroConciliacionList').selectmenu({style:'dropdown', maxHeight:'180', width:'350px'});	
	
}

/*--------------------------------------------------------------------------------------------------------*/
