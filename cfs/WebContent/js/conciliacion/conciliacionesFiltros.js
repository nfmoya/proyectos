/*--------------------------------------------------------------------------------------------------------*/
/* INICIALIZA FILTROS */
/*--------------------------------------------------------------------------------------------------------*/
function initialiseFiltros(){
    $("#divMontTot").hide();

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
	   recargarProducto($('#filtroProveedorList').val(), $('#filtroSectorList').val());
	});
	onchangeOptions('filtroSectorList');

	var $periodo = $('#filtroPeriodoList');
	$periodo.change(function(){
	   borrarMensaje();
	   obtenerPeriodo($('#filtroProveedorList').val(), $('#filtroPeriodoList').val());
	});
	
	var $producto = $('#filtroProductoList');
	$producto.change(function(){
	   borrarMensaje();
	   obtenerProducto($('#filtroProveedorList').val(), $('#filtroSectorList').val(), $('#filtroProductoList').val());
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
      resetProductoOption(' Sin Producto ', '0', false);
      resetPeriodoOption( ' Sin Periodo  ', '0', false);	
      resetConciliacionOption(' Sin Conciliacion ', '0', false);	
   }
   if ($('#filtroSectorList').val()=="0"){
      resetProductoOption(' Sin Producto ', '0', false);
      resetConciliacionOption(' Sin Conciliacion ', '0', false);	
   }   
}

function limpiarProveedor() {
   $('#fhDesde').val('');
   $('#fhHasta').val('');
   limpiarSector();
}

function limpiarSector() {
   borrarMensaje();
   $('#nuDiaEmiFDesde').val("999");
   $('#nuDiaEmiFHasta').val("999");
   $('#nuDiaCierreFDesde').val("999");
   $('#nuDiaCierreFHasta').val("999");
   $('#stConcilSinVal').val("S");
   $('#stServSinConcil').val("S");
   $('#stFactSinConcil').val("S");   
   $('#cdUniVal').val("");
   $('#nbUniVal').val("");
   $('#nuValBrutoUniVal').val("");	
   $('#fhRemitoDesde').val("");
   $('#fhRemitoHasta').val("");
   $('#fhFinServicioDesde').val("");
   $('#fhFinServicioHasta').val("");
}

function resetProductoOption(label, value, disabled){
   document.getElementById('filtroProductoList').options.length=0;
   document.getElementById('filtroProductoList')[0]= new Option(label,value);
   $('#filtroProductoList').selectmenu('destroy');
   $('#filtroProductoList').selectmenu({style:'dropdown', maxHeight:'180', width:'250px'}).selectmenu('disabled',true);
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
   callJsonAction("comboConciliacionPeriodos.action","opcion=2&cdProveedor="+cdProveedor, "successPeriodos" , "errorPeriodos");
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
         if ($('#filtroSectorList').val() != "0") {	
            recargarProducto($('#filtroProveedorList').val(), $('#filtroSectorList').val());
   	     }   
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
   callJsonAction("periodoConciliacion.action","cdProveedor="+cdProveedor+"&cdPeriodo="+cdPeriodo, "successDetallePeriodo", "errorDetallePeriodo");
}

function successDetallePeriodo(jsonData) {
   try {
      if (jsonData.DetallePeriodo != undefined) {
	     if (jsonData.DetallePeriodo.length>0) {
            $('#fhDesde').val(jsonData.DetallePeriodo[0].fhDesde);
            $('#fhHasta').val(jsonData.DetallePeriodo[0].fhHasta);
            $('#stPeriodo').val(jsonData.DetallePeriodo[0].stEstado);
            
            asignarFiltroFechas();
 	        obtenerProveedorValor($('#filtroProveedorList').val(), $('#filtroPeriodoList').val(), $('#cdUniVal').val());

 	        if ($("#stPeriodo").val() != 'ABI') {
 	        	alert("PERIODO de FACTURACION no habilitado");
 	        }  
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
/* RECARGA PRODUCTOS */
/*--------------------------------------------------------------------------------------------------------*/
function recargarProducto(cdProveedor, cdSector) {
   callJsonAction("comboConciliacionPeriodos.action","opcion=1&cdProveedor="+cdProveedor+"&cdSector="+cdSector, "successProductos", "errorProductos");
}

function successProductos(jsonData) {
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

            recargarNroConciliacion();
		 }
	  }
   } catch(e) {
//    jsError('errorProducto', e);
   }
}

function errorProducto(cod, des){
//   jsError(cod, des);
}

/*--------------------------------------------------------------------------------------------------------*/
/* DETALLE PRODUCTO */
/*--------------------------------------------------------------------------------------------------------*/
function obtenerProducto(cdProveedor, cdSector, cdProducto) {
   callJsonAction("productoConciliacion.action","cdProveedor="+cdProveedor+"&cdSector="+cdSector+"&cdProducto="+cdProducto, "successDetalleProducto", "errorDetalleProducto");
}

function successDetalleProducto(jsonData) {
   try {
      if (jsonData.DetalleProducto != undefined) {
	     if (jsonData.DetalleProducto.length>0) {
	    	if ($('#cdConciliacion').val() != "") { 
               $('#nuDiaEmiFDesde').val(jsonData.DetalleProducto[0].nuDiaEmiFDesde);
               $('#nuDiaEmiFHasta').val(jsonData.DetalleProducto[0].nuDiaEmiFHasta);
               $('#nuDiaCierreFDesde').val(jsonData.DetalleProducto[0].nuDiaCierreFDesde);
               $('#nuDiaCierreFHasta').val(jsonData.DetalleProducto[0].nuDiaCierreFHasta);
               asignarFiltroFechas();
	    	}
            $('#stConcilSinVal').val(jsonData.DetalleProducto[0].stConcilSinVal);       
            $('#stServSinConcil').val(jsonData.DetalleProducto[0].stServSinConcil);       
            $('#stFactSinConcil').val(jsonData.DetalleProducto[0].stFactSinConcil);       
            $('#stProducto').val(jsonData.DetalleProducto[0].stHabilitado);
            $('#stProductoSector').val(jsonData.DetalleProducto[0].stRelacionSector);
            $('#cdUniVal').val(jsonData.DetalleProducto[0].cdUniVal);
            $('#cdGrupoProducto').val(jsonData.DetalleProducto[0].cdGrupoProducto);
            
            if ($('#stConcilSinVal').val() == 'S') {
               $('#stIgnoraVal').attr('checked','checked');
	           $("#stIgnoraVal").removeClass('ui-state-disabled');
               $("#stIgnoraVal").removeAttr("disabled");
            } else {
               $('#stIgnoraVal').removeAttr('checked');
               $("#stIgnoraVal").addClass('ui-state-disabled');
               $("#stIgnoraVal").attr("disabled", "disabled");
            }
            obtenerUniVal($('#cdUniVal').val());

//          alert($('#cdGrupoProducto').val());
            if ($('#cdGrupoProducto').val() == "CON_MONTO_TO") {
                $("#divMontTot").show();
            } else {
                $("#divMontTot").hide();            	
            }

            var mensaje_alerta = "";
            if ($("#stProducto").val() != 'S') {
               mensaje_alerta = "PRODUCTO no habilitado\n";
    	    }
    	    if ($("#stProductoSector").val() != 'S') {
    	       mensaje_alerta = "Relacion PRODUCTO SECTOR no habilitado\n";
    	    }  
            if (mensaje_alerta != "") {
               alert(mensaje_alerta);
            }
 	     }
	  }
   } catch(e) {
//    jsError('errorProducto', e);
   }
}

function errorDetalleProducto(cod, des) {
//   jsError(cod, des);
}

/*--------------------------------------------------------------------------------------------------------*/
/* DETALLE UNIVAL */
/*--------------------------------------------------------------------------------------------------------*/
function obtenerUniVal(cdUnival) {
   $('#nbUniVal').val("");
   callJsonAction("uniValConciliacion.action","cdUniVal="+cdUnival, "successDetalleUniVal", "errorDetalleUniVal");
}

function successDetalleUniVal(jsonData) {
   try {
      if (jsonData.DetalleUniVal != undefined) {
	     if (jsonData.DetalleUniVal.length>0) {
	       $('#nbUniVal').val(jsonData.DetalleUniVal[0].cdCodTabla + ' - '+jsonData.DetalleUniVal[0].nbCodTabla);
	       obtenerProveedorValor($('#filtroProveedorList').val(), $('#filtroPeriodoList').val(), $('#cdUniVal').val());
		 }
	  }
   } catch(e) {
//    jsError('errorUniVal', e);
   }
}

function errorDetalleUniVal(cod, des) {
//   jsError(cod, des);
}

/*--------------------------------------------------------------------------------------------------------*/
/* IMPORTE VALOR MONETARIO PROVEEDOR PERIODO UNIVAL */
/*--------------------------------------------------------------------------------------------------------*/
function obtenerProveedorValor(cdProveedor, cdPeriodo, cdUnival) {
   $('#nuValBrutoUniVal').val("");
   callJsonAction("proveedorValorConciliacion.action","cdProveedor="+cdProveedor+"&cdPeriodo="+cdPeriodo+"&cdUniVal="+cdUnival, "successDetalleProveedorValor", "errorDetalleProveedorValor");
}

function successDetalleProveedorValor(jsonData) {
   try {
      if (jsonData.DetalleProveedorValor != undefined) {
	     if (jsonData.DetalleProveedorValor.length>0) {
	       $('#nuValBrutoUniVal').val(jsonData.DetalleProveedorValor[0].nuValBrutoUniVal);
		 }
         recargarNroConciliacion();
	  }
   } catch(e) {
//    jsError('errorUniVal', e);
   }
}

function errorDetalleProveedorValor(cod, des) {
//   jsError(cod, des);
}

/*--------------------------------------------------------------------------------------------------------*/
/* ASIGNAR FECHAS DE FILTROS */
/*--------------------------------------------------------------------------------------------------------*/
function asignarFiltroFechas() {
	if ($('#fhDesde').val() != "" && $('#fhHasta').val() != "") {	
		var fechaDesde = new Date($('#fhDesde').val().substr(0,4), $('#fhDesde').val().substr(5,2) - 1, $('#fhDesde').val().substr(8,2));
		var fechaHasta = new Date($('#fhHasta').val().substr(0,4), $('#fhHasta').val().substr(5,2) - 1, $('#fhHasta').val().substr(8,2));
		
		$('#fhRemitoDesde').val('');
		$('#fhRemitoHasta').val('');
		$('#fhFinServicioDesde').val('');
		$('#fhFinServicioHasta').val('');
	
		// Remito Desde
		if (parseInt($('#nuDiaEmiFDesde').val()) != 999) {
			var newdate = new Date(fechaDesde);
			newdate.setDate(newdate.getDate() + parseInt($('#nuDiaEmiFDesde').val()));
			var frd = ("0"+newdate.getDate()).slice(-2) + '/' + ("0"+(newdate.getMonth()+1)).slice(-2) + '/' + newdate.getFullYear();
			$('#fhRemitoDesde').val(frd);
		}
		
		// Remito Hasta
		if (parseInt($('#nuDiaEmiFHasta').val()) != 999) {
			var newdate = new Date(fechaHasta);
			newdate.setDate(newdate.getDate() + parseInt($('#nuDiaEmiFHasta').val()));
			var frh = ("0"+newdate.getDate()).slice(-2) + '/' + ("0"+(newdate.getMonth()+1)).slice(-2) + '/' + newdate.getFullYear();
			$('#fhRemitoHasta').val(frh);
		}
		
		// Finalizacion Servicio Desde
		if (parseInt($('#nuDiaCierreFDesde').val()) != 999) {
			var newdate = new Date(fechaDesde);
			newdate.setDate(newdate.getDate() + parseInt($('#nuDiaCierreFDesde').val()));
			var frd = ("0"+newdate.getDate()).slice(-2) + '/' + ("0"+(newdate.getMonth()+1)).slice(-2) + '/' + newdate.getFullYear();
			$('#fhFinServicioDesde').val(frd);
		}
		
		// Finalizacion Servicio Hasta
		if (parseInt($('#nuDiaCierreFHasta').val()) != 999) {
			var newdate = new Date(fechaHasta);
			newdate.setDate(newdate.getDate() + parseInt($('#nuDiaCierreFHasta').val()));
			var frh = ("0"+newdate.getDate()).slice(-2) + '/' + ("0"+(newdate.getMonth()+1)).slice(-2) + '/' + newdate.getFullYear();
			$('#fhFinServicioHasta').val(frh);
		}
	}
}

/*--------------------------------------------------------------------------------------------------------*/
/* RECARGA CONCILIACION */
/*--------------------------------------------------------------------------------------------------------*/
function recargarNroConciliacion() {
   var filtro = $('#filtroProveedorList').val()+';'+$('#filtroProductoList').val()+';'+$('#filtroSectorList').val()+';'+$('#filtroPeriodoList').val()+';'+$('#filtroSituacionConciliacionList').val()+';';
   callJsonAction("comboNroConciliacion.action","filtro="+filtro, "successNroConciliacion", "errorNroConciliacion");
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

	$('#filtroProductoList').selectmenu('destroy');	
	$('#filtroProductoList').selectmenu({style:'dropdown', maxHeight:'180', width:'250px'});

	$('#filtroSectorList').selectmenu('destroy');	
	$('#filtroSectorList').selectmenu({style:'dropdown', maxHeight:'180', width:'250px'});

	$('#filtroPeriodoList').selectmenu('destroy');	
	$('#filtroPeriodoList').selectmenu({style:'dropdown', maxHeight:'180', width:'250px'});
	
	$('#filtroUniValList').selectmenu('destroy');	
	$('#filtroUniValList').selectmenu({style:'dropdown', width:'250px'});
	
	$('#filtroSituacionConciliacionList').selectmenu('destroy');	
	$('#filtroSituacionConciliacionList').selectmenu({style:'dropdown', width:'250px'});
	
	$('#filtroConciliacionList').selectmenu('destroy');	
	$('#filtroConciliacionList').selectmenu({style:'dropdown', maxHeight:'180', width:'350px'});	
	
}

/*--------------------------------------------------------------------------------------------------------*/
