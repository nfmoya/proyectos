/*--------------------------------------------------------------------------------------------------------*/
// GRILLA DIFERENCIAS
/*--------------------------------------------------------------------------------------------------------*/

/*--------------------------------------------------------------------------------------------------------*/
// CARGA GRILLA DIFERENCIAS
/*--------------------------------------------------------------------------------------------------------*/
function loadDiferenciasGrid() {
	var params = '';
	if ($('#cdConciliacion').val() == '0' && $('#conciliacion').val() == '0') {
       params += 'cdConciliacion=';
	} else {
		if ($('#cdConciliacion').val() == '0') {
	       params += 'cdConciliacion='+$('#conciliacion').val();
		} else {
		   params += 'cdConciliacion='+$('#cdConciliacion').val();	
		}		
	}
	params += '&cdProveedor='+$('#filtroProveedorList').val();
	params += '&cdSector='+$('#filtroSectorList').val();
	params += '&pfDesde='+$('#filtroPeriodoList').val();	
	params += '&pfHasta='+$('#filtroPeriodoList').val();	
	params += '&cdProducto='+$('#filtroProductoList').val();	
	params += '&stDiferencia=ACT';
    limpiarGrilla('gridDiferenciasId' , 'gridDiferenciasPager' , 'gridDiferencias');
	try {
		$('#gridDiferenciasId').jqGrid({
	   	    url:'JsonDifConciliacionGridList.action?'+params,
	   	    datatype:'json',											
	        mtype:'POST',
	        colNames:[
	              	'Nro. Concil.'   , 'Nro Dif'          , 'Observaciones', 'Sit. Soluci&oacute;n', 'Estado'  ,
			        'Nro. Remito'    , 'Pza. Desde'       , 'Pza. Hasta'   , 'Cantidad'            , 'Valor'   ,
		            'Sector Control' , 'Per. Fact.'       , 'Producto'     , 'Descripci&oacute;n'  , 'U. Val.' ,
			        'Fecha Ult. Act.', 'Usuario Ult. Act.'
			],
			colModel:[			          
					{name : 'cdConciliacion', width : 100, align : 'left'  , hidden : true }, 
					{name : 'cdOrden'       , width :  45, align : 'right' , hidden : false , key: true}, 
					{name : 'observacion'   , width : 460, align : 'left'  , hidden : false }, 
					{name : 'tpSolucion'    , width :  70, align : 'center', hidden : false },
					{name : 'stDiferencia'  , width :  50, align : 'center', hidden : false }, 
					{name : 'cdRemito'      , width :  70, align : 'right' , hidden : false },
					{name : 'pzaDesde'      , width :  80, align : 'left'  , hidden : false }, 
					{name : 'pzaHasta'      , width :  80, align : 'left'  , hidden : false }, 
					{name : 'ctDiferencia'  , width :  50, align : 'right' , hidden : false , 
                	        formatter: "number", 
                            formatoptions:{decimalSeparator:",", thousandsSeparator: ".", decimalPlaces: 0}},              
					{name : 'imPrecioTot'   , width :  80, align : 'right' , hidden : false , 
                    	    formatter: "number", 
                            formatoptions:{decimalSeparator:",", thousandsSeparator: ".", decimalPlaces: 2}},              
					{name : 'cdSector'      , width : 100, align : 'left'  , hidden : true  }, 
					{name : 'cdPeriodoFact' , width : 100, align : 'left'  , hidden : true  }, 
					{name : 'cdProducto'    , width : 100, align : 'center', hidden : true  }, 
					{name : 'descProducto'  , width : 100, align : 'center', hidden : true  }, 
					{name : 'cdUniVal'      , width : 100, align : 'center', hidden : true  }, 
					{name : 'fhALta'        , width : 100, align : 'center', hidden : true  }, 
					{name : 'descUsuAlta'   , width : 100, align : 'center', hidden : true  }
			],
		    jsonReader : {
		    	root:'gridModelToShow',
		    	repeatitems:false,
		    	id:'cdOrden'
		    },
		    width: 1050,
		    emptyrecords:'Sin registros',
		    pager: '#gridDiferenciasPager',
		    sortname: 'cdConciliacion, cdOrden ,cdSector, cdPeriodoFact, cdProducto',
		    viewrecords: true,
		    sortorder: 'asc',
		    shrinkToFit: false,
		    caption: 'Diferencias de Conciliaci\u00f3n',
		    height: '100%',
			footerrow : true, 
		    onSelectRow : function(row_id){
		    	var values = jQuery("#gridDiferenciasId").getRowData(row_id);
		    	showHideEdit($.trim(values['stDiferencia']));
		    }
		});
        var sumCtDiferencia      = $('#gridDiferenciasId').jqGrid('getCol', 'ctDiferencia', false, 'sum');
        var sumImPrecioTotalDife = $('#gridDiferenciasId').jqGrid('getCol', 'imPrecioTot' , false, 'sum');
	    $('#gridDiferenciasId').jqGrid('footerData','set',{ctDiferencia: sumCtDiferencia, imPrecioTot: sumImPrecioTotalDife}, true);
	    
		$('#gridDiferenciasId').trigger('reloadGrid');
		$("#gridDiferenciasId").navGrid('#gridDiferenciasPager',{refresh:false,edit:false,add:false,del:false,search:false});
		addButtonsToDiferenciasGrid();

		$("#add_dif").addClass('ui-state-disabled');
	    $("#edit_dif").addClass('ui-state-disabled');
	    
	    Totaliza();
		
		//Define los estilos para la grilla	
		$(".ui-jqgrid-labels").css('font-size','10px');
		$(".ui-pg-table").css('font-size','10px');		

	} catch(e){
//		jsError('diferencias',e.message);
	}	
		
	//Muestro la grilla en pantalla
	$('#gridDiferenciasId').show();	
}

/*--------------------------------------------------------------------------------------------------------*/
// AGREGA BOTONERA EN LA GRILLA PARA DAR DE ALTA O EDITAR DIFERENCAS DE CONCILIACION
/*--------------------------------------------------------------------------------------------------------*/
function addButtonsToDiferenciasGrid() {
// if ($('#differGrant').val()=='S') {
      var title = 'Agregar Diferencia Conciliacion';
	  $('#gridDiferenciasId').navButtonAdd('#gridDiferenciasPager', {
		 caption: 'Agregar',
		 id:'add_dif',
		 onClickButton: function() {
			try {
				editDiferenciaConciliacion(0, 'Agregar Diferencia', 'A');			
			} catch(e) {
				jsError('editDiferenciaConciliacion(...)', e);
			}
		 },
		 position:'last',
		 title:title
	  });	   
	   
      var title = 'Editar Diferencia Conciliacion';
	  $('#gridDiferenciasId').navButtonAdd('#gridDiferenciasPager', {
		 caption: 'Editar',
		 id:'edit_dif',
		 onClickButton: function() {
			try {
				var rowid = getSelRowFromDiferenciasGrid(); 
				if (rowid != null) {
					editDiferenciaConciliacion(rowid, 'Editar Diferencia', 'E');
				} else {
					seleccioneRegistro();
				}
				
			} catch(e) {
				jsError('editDiferenciaConciliacion(...)', e);
			}
		 },
		 position:'last',
		 title:title
	  });
// }   
}

/*--------------------------------------------------------------------------------------------------------*/
//RELOAD DE LA GRILLA
/*--------------------------------------------------------------------------------------------------------*/
function reloadDiferenciasGrid(){
   $('#gridDiferenciasId').trigger('reloadGrid');
}

/*--------------------------------------------------------------------------------------------------------*/
//OBTENER EL REGISTRO SELECCIONADO DE LA GRILLA
/*--------------------------------------------------------------------------------------------------------*/
function getSelRowFromDiferenciasGrid() { 
   return $("#gridDiferenciasId").getGridParam('selrow');
}

/*--------------------------------------------------------------------------------------------------------*/
// EDICION DE DIFERENCIAS CONCILIACION
/*--------------------------------------------------------------------------------------------------------*/
function editDiferenciaConciliacion(rowid, title, tipoAcceso) {
	$('#solucionDifListEdit').selectmenu('destroy');	
	$('#solucionDifListEdit').selectmenu({style:'dropdown', width:'250px'});	

	$('#stDiferenciaEdit').selectmenu('destroy');	
	$('#stDiferenciaEdit').selectmenu({style:'dropdown', width:'250px'});	

	$("#tipoModificacion").val(tipoAcceso);	
	if (tipoAcceso == 'A') {
       $("#cdOrdenDife").val('0');
	   $("#obsDifConciliaEdit").val('');
       $('#solucionDifListEdit').selectmenu('setValue', '0');	
       $('#stDiferenciaEdit').selectmenu('setValue', 'ACT');
       $('#stDiferenciaEdit').selectmenu('disabled', true);	
	   $("#cdRemitoEdit").val('');
	   $("#nbPiezaDesdeEdit").val('');
	   $("#nbPiezaHastaEdit").val('');
	   $("#ctDiferenciaEdit").val('');
	   $("#imDiferenciaEdit").val('');
	} else {
		var cdOrden         = $('#gridDiferenciasId').jqGrid('getCell', rowid, 'cdOrden');
		var nbObservaciones = $('#gridDiferenciasId').jqGrid('getCell', rowid, 'observacion');
		var tpSolucion      = $('#gridDiferenciasId').jqGrid('getCell', rowid, 'tpSolucion');
		var stDiferencia    = $('#gridDiferenciasId').jqGrid('getCell', rowid, 'stDiferencia');
		var cdRemito        = $('#gridDiferenciasId').jqGrid('getCell', rowid, 'cdRemito');
		var nbPiezaDesde    = $('#gridDiferenciasId').jqGrid('getCell', rowid, 'pzaDesde');
		var nbPiezaHasta    = $('#gridDiferenciasId').jqGrid('getCell', rowid, 'pzaHasta');
		var ctDiferencia    = $('#gridDiferenciasId').jqGrid('getCell', rowid, 'ctDiferencia');
		var imPrecioTotal   = $('#gridDiferenciasId').jqGrid('getCell', rowid, 'imPrecioTot');
	    imPrecioTotal       = imPrecioTotal.replace('.',',');
		
		$("#cdOrdenDife").val(cdOrden);
		$("#obsDifConciliaEdit").val(nbObservaciones);
		$("#solucionDifListEdit").selectmenu('setValue', tpSolucion);
		$("#stDiferenciaEdit").selectmenu('setValue', stDiferencia);
        $('#stDiferenciaEdit').selectmenu('disabled', false);	
		$("#cdRemitoEdit").val(cdRemito);
		$("#nbPiezaDesdeEdit").val(nbPiezaDesde);
		$("#nbPiezaHastaEdit").val(nbPiezaHasta);
		$("#ctDiferenciaEdit").val(ctDiferencia);
		$("#imDiferenciaEdit").val(imPrecioTotal);
	}
    $('#dialogDiferenciaConciliacion').dialog('option','title', title);
    $('#dialogDiferenciaConciliacion').dialog('open');
}

/*--------------------------------------------------------------------------------------------------------*/
// INSERTA O MODIFICA DIFERENCIA CONCILIACION 
// GRABA EN LA GRILLA, SOLO SE GRABA EN LA TABLA TCF014_DIFCONCILIA CUANDO SE GRABA LA CONCILIACION
/*--------------------------------------------------------------------------------------------------------*/
function saveDiferenciaConciliacion() {
    var tipoModificacion    = $("#tipoModificacion").val();

    var nbObservacionesDife = $("#obsDifConciliaEdit").val(); 
    var tpSolucionDife      = $("#solucionDifListEdit").val(); 
    var stDiferencia        = $("#stDiferenciaEdit").val(); 

    var cdRemito            = $('#cdRemitoEdit').val();
    var nbPiezaDesde        = $("#nbPiezaDesdeEdit").val(); 
    var nbPiezaHasta        = $("#nbPiezaHastaEdit").val(); 
    var ctDiferencia        = $("#ctDiferenciaEdit").val();
    if (ctDiferencia == "") {
       ctDiferencia = "0";
    }
    var imPrecioTotalDife   = ($("#imDiferenciaEdit").val()).replace(',','.');
    if (imPrecioTotalDife == "") {
    	imPrecioTotalDife = "0";
     }
    var cdUniVal            = $('#cdUniVal').val();	
	var cdSector            = $('#cdSector').val();
	var cdProducto          = $('#cdProducto').val();
	var cdPeriodoFact       = $('#cdPeriodo').val();
	
	if (validateDiferencia() == true) {
       $('#modificada').val("S");  
       if (tipoModificacion == "A") {
	      var cdOrdenDife = 0;
   	      // Obtengo el ultimo Nro de Orden 
	      var rows = $('#gridDiferenciasId').jqGrid('getRowData');
	      for (var i = 0; i < rows.length; i++) {
	         var row = rows[i];
	         cdOrdenDife = Number(row['cdOrden']);
	      }
          cdOrdenDife ++;
	      var data = [{cdConciliacion: 0, cdOrden: cdOrdenDife, observacion: nbObservacionesDife, 
			           tpSolucion: tpSolucionDife, stDiferencia: stDiferencia, cdRemito: cdRemito, 
			           pzaDesde: nbPiezaDesde, pzaHasta: nbPiezaHasta, ctDiferencia: ctDiferencia, 
			           imPrecioTot: imPrecioTotalDife, cdSector: cdSector, cdPeriodoFact: cdPeriodoFact, 
                       cdProducto: cdProducto, descProducto: '', cdUniVal: cdUniVal, fhALta: '', descUsuAlta: ''}];
          $("#gridDiferenciasId").jqGrid('addRowData','cdOrden', data);
	   } else {
          var rowid = getSelRowFromDiferenciasGrid(); 
          $('#gridDiferenciasId').jqGrid('setCell', rowid, 'observacion' , nbObservacionesDife);
          $('#gridDiferenciasId').jqGrid('setCell', rowid, 'tpSolucion'  , tpSolucionDife);
          $('#gridDiferenciasId').jqGrid('setCell', rowid, 'stDiferencia', stDiferencia);
          $('#gridDiferenciasId').jqGrid('setCell', rowid, 'cdRemito'    , cdRemito);
          $('#gridDiferenciasId').jqGrid('setCell', rowid, 'pzaDesde'    , nbPiezaDesde);
          $('#gridDiferenciasId').jqGrid('setCell', rowid, 'pzaHasta'    , nbPiezaHasta);
          $('#gridDiferenciasId').jqGrid('setCell', rowid, 'ctDiferencia', ctDiferencia);
          $('#gridDiferenciasId').jqGrid('setCell', rowid, 'imPrecioTot' , imPrecioTotalDife);
	   }
   	   $('#responseMessageToEdit').show();
	   $('#dialogDiferenciaConciliacion').dialog('close');
	
   	   $("#obsDifConciliaEdit").val(''); 
	   $("#solucionDifListEdit").val('0'); 
	   $("#stDiferenciaEdit").val('0'); 
	   $('#cdRemitoEdit').val('');	
   	   $("#nbPiezaDesdeEdit").val(''); 
	   $("#nbPiezaHastaEdit").val(''); 
	   $("#ctDiferenciaEdit").val(''); 
	   $("#imDiferenciaEdit").val('');
	   $("#tipoModificacion").val('');
	
	   Totaliza();	
	}	
}

/*--------------------------------------------------------------------------------------------------------*/
// VALIDA LOS DATOS INGRESADOS EN EL FORMULARIO DE DIFERENCIAS
/*--------------------------------------------------------------------------------------------------------*/
function validateDiferencia() {
   var err = '';
	   
   var ctDiferencia        = $("#ctDiferenciaEdit").val(); 
   var imPrecioTotal       = $("#imDiferenciaEdit").val();	   
   var nbObservacionesDife = $("#obsDifConciliaEdit").val(); 
   var retorna = true;
   
   if ($("#solucionDifListEdit").val() == '0') {
      err += 'Debe ingresar la Situacion Solucion \n';
   }   
   if ((ctDiferencia  == '' || ctDiferencia  == '0') && 
       (imPrecioTotal == '' || imPrecioTotal == '0')) {
      err += 'Debe ingresar la Cantidad o el Valor \n';
   }
   if (nbObservacionesDife == '') {
      err += 'Debe ingresar las Observaciones \n';
   }
   	   
   if (err != '') {
      alert(err);
      retorna = false;
   }

   return retorna;
}

