var nuevoCodigoTarifa = 0;
var borraCodigoTarifa = 0; 
var registroSeleccionado = 0;

$(document).ready(function(){
	$("#fhDesde").mask("99/99/9999",{placeholder:" "});

	$.ajaxSetup({
        cache : false,
        async : false
    });

	initialiseProductoTarifa();
	initProductoTarifaDialog();
	cleanDataConfirmation();
	search();
});
	
function search() {
	$('#agregarGrantPP').val('S');
	limpiarGrilla('gridProductosPeriodosTarifasId', 'gridProductosPeriodosTarifasPager', 'gridProductosPeriodosTarifas');

	$('#productosPeriodosTarifasGrid').show();
	$('#botonera').show();
	$('#buttonCancelar').button({
		icons : {
			secondary : ""
		}
	}).click(function() {

		resetProductoTarifaForm();
		cleanDataConfirmation();
	});
	
	loadProductosPeriodosTarifasGrid();
//	alert($("#productoTipVal").val());		
	if ($("#productoTipVal").val() == "2") {
		$("#gridProductosPeriodosTarifasId").jqGrid('hideCol',["nuPorcTarifa"]);
		$("#gridProductosPeriodosTarifasId").jqGrid('showCol',["nuPrecioUniVal"]);	
		$("#tab4").text("Tarifas x Cantidad Val");
		$("#divPorcTarifa").hide();
		$("#divPrecioUniVal").show();
		$("#divHabilitado").hide();
		$("#divPrecioFijo").show();
	} else {
		$("#gridProductosPeriodosTarifasId").jqGrid('hideCol',["nuPrecioUniVal"]);
		$("#gridProductosPeriodosTarifasId").jqGrid('showCol',["nuPorcTarifa"]);	
		$("#tab4").text("Tarifas en % a aplicar");
		$("#divPorcTarifa").show();
		$("#divPrecioUniVal").hide();
		$("#divHabilitado").hide();
		$("#divPrecioFijo").hide();		
	}	
	
}

function initProductoTarifaDialog(){
	$("#dialogEditProductoTarifa").dialog({
		bgiframe: true,
		autoOpen: false,
		modal: true,
		width: 680,
		
		close: reloadProductosPeriodosTarifasGrid, 
		buttons: [ 
			{   id: 'guardar-button',
				text: "Grabar",
		        click: function() {
		        	try {
						saveProductoTarifa();
					} catch(e) {
						jsError('saveProductoTarifa(...)',e.message);
					}
				}
		    },
   	        {   id: 'cancelar-button',
   	            text: "Cancelar",
   	        	click: function() {
   	        		$('#prod_Periodo_responseMsgs').hide();
					resetProductoTarifaForm();
					cleanDataConfirmation();
	            	$(this).dialog('close');
            	}
	        }
		]
	});
}

// GRILLA
function loadProductosPeriodosTarifasGrid(){
//	alert($("#cdProveedorSeleccionado").val() + ' - ' + $("#cdProductoSeleccionado").val() + ' - ' + $("#productoPeriodoSeleccionado").val());
	var params = '';
/*	
	if (($("#cdProveedorSeleccionado").val() != '') && ($("#cdProveedorSeleccionado").val() != undefined)) {
	   params = 'cdProveedor='+$("#cdProveedorSeleccionado").val();
	   if (($("#cdProductoSeleccionado").val() != '') && ($("#cdProductoSeleccionado").val() != undefined)) {
		   params += '&cdProducto='+$("#cdProductoSeleccionado").val();
	   }
	   if (($("#productoPeriodoSeleccionado").val() != '') && ($("#productoPeriodoSeleccionado").val() != undefined)) {
		   params += '&fhDesde='+$("#productoPeriodoSeleccionado").val();
	   }
	} else {
       params = 'cdProveedor=999&cdProducto=999&fhDesde=';
	}
*/	
    params  = 'cdProveedor='+$("#cdProveedorSeleccionado").val();
    params += '&cdProducto='+$("#cdProductoSeleccionado").val();
    params += '&fhDesde='+dateFormat_JsToService($("#productoPeriodoSeleccionado").val());
//		alert("ACA");
    
	try {
		showGrid({
			id : 'gridProductosPeriodosTarifasId',
			idPager : 'gridProductosPeriodosTarifasPager',
			url : 'JsonProductosPeriodosTarifasList.action?'+params,
			colNames : [ 'Proveedor', 'Producto', 'Fecha Desde','Fecha Hasta', 'Cant Desde', 'Cant Hasta', 'Tarifa en Unid Val', 'Porc % Aplicar', 'Precio Fijo', 'Habilitado' ],
			colModel : [ 
				{name : 'cdProveedor'        , width : 140, align : 'left', hidden : true }, 
				{name : 'cdProducto'         , index : 'id', width : 140, align : 'left', hidden : true }, 
				{name : 'fhDesde'            , width : 80, align : 'center', hidden : false, sortable: false, formatter: 'date', formatoptions: { srcformat: 'Y/m/d', newformat: 'd/m/Y'} }, 
				{name : 'fhHasta'            , width : 80, align : 'center', hidden : false, sortable: false, formatter: 'date', formatoptions: { srcformat: 'Y/m/d', newformat: 'd/m/Y'} },
				{name : 'nuCantDesde'        , width : 100, align : 'center', hidden : false, sortable: false}, 
				{name : 'nuCantHasta'        , width : 100, align : 'center', hidden : false, sortable: false }, 
				{name : 'nuPrecioUniVal'     , width : 100, align : 'right' , hidden : false, sortable: false }, 
				{name : 'nuPorcTarifa'       , width : 100, align : 'right' , hidden : false, sortable: false }, 
				{name : 'stPrecioFijo'       , width : 100, align : 'center', hidden : false, sortable: false },
				{name : 'stHabilitado'       , width : 100, align : 'center', hidden : true, sortable: false },
			],
			rowNum : 10,
			rowList : [ 10, 15, 20 ],
			sortName : 'cdProducto',
			caption : "Tarifas de Periodos de Productos",
			height : '100%',
			width : '100%',
			multiselect: false,
			loadonce : true,
			loadCompleteFunc : '',
            editurl: 'clientArray',
            
		});
		$('.ui-jqgrid-title').css('width', '98%');
		addButtonsToProductoTarifaGrid();
	} catch(e) {
		jsError('loadProductosPeriodosTarifasGrid(...)', e);
	}
	
}

// AGREGA BOTONERA DE LA GRILLA
function addButtonsToProductoTarifaGrid(){
	if($('#addGrantTAR').val()=='S'){
		var title = 'Agregar Tarifa Periodo Producto';
		$('#gridProductosPeriodosTarifasId').navButtonAdd('#gridProductosPeriodosTarifasPager', {
			caption: 'Agre',
			buttonicon: 'ui-icon ui-icon-plus',
			onClickButton: function() {
				try {
					$('#gridProductosPeriodosTarifasId').resetSelection();
					processEditProductoTarifa(0, false, 'Ingresar Tarifa', 'A');
				} catch (e) {
					jsError('loadProductosPeriodosTarifasGrid(...)', e);
				}
			}, 
			position:'last', 
			title:title
		});
	}
	if($('#editGrantTAR').val()=='S') {
		var title = 'Editar Tarifa Periodo Producto';
		$('#gridProductosPeriodosTarifasId').navButtonAdd('#gridProductosPeriodosTarifasPager', {
			caption: 'Edit',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromProductoTarifaGrid(); 
					if (rowid != null) {
						processEditProductoTarifa(rowid, false, 'Editar Tarifa', 'E');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadProductosPeriodosTarifasGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
	}
	if($('#deleteGrantTAR').val()=='S') {
		var title = 'Eliminar Tarifa Periodo Producto';
		$('#gridProductosPeriodosTarifasId').navButtonAdd('#gridProductosPeriodosTarifasPager', {
			caption: 'Elim',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromProductoTarifaGrid(); 
					if (rowid != null) {
						processEditProductoTarifa(rowid, false, 'Eliminar Tarifa', 'D');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadProductosPeriodosTarifasGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
		
		var title = 'Eliminar Todas las Tarifas';
		$('#gridProductosPeriodosTarifasId').navButtonAdd('#gridProductosPeriodosTarifasPager', {
			caption: 'E. todo',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					confirmarDeleteAll();
				} catch(e) {
					jsError('loadProductosPeriodosTarifasGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
		
	}
	if($('#selectGrant').val()=='S') {
		var title = 'Consulta Tarifa Periodo Producto';
		$('#gridProductosPeriodosTarifasId').navButtonAdd('#gridProductosPeriodosTarifasPager', {
			caption: 'Cons',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromProductoTarifaGrid(); 
					if (rowid != null) {
						processEditProductoTarifa(rowid, false, 'Consultar Tarifa', 'C');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadProductosPeriodosTarifasGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
	}
}

// FUNCTIONES GENERALES GRILLA
function reloadProductosPeriodosTarifasGrid(){
	$('#gridProductosPeriodosTarifasId').trigger('reloadGrid');
}

function getSelRowFromProductoTarifaGrid() { 
	return $("#gridProductosPeriodosTarifasId").getGridParam('selrow');
}

function getSelRowFromProductoTarifaGridAllPages() {
	var page = $("#gridProductosPeriodosTarifasId").getGridParam('page') - 1;
	var rowNum = $('#gridProductosPeriodosTarifasId').getGridParam('rowNum');
	var selrow = $("#gridProductosPeriodosTarifasId").getGridParam('selrow');
	
	return String((page * rowNum) + Number(selrow));
}

function resetProductoTarifaForm() {
	$(':input','#dialogEditProductoTarifa').not('input[type=hidden], :button').val('').removeAttr('checked').removeAttr('selected');
}

//EDIT PRODUCTO
function processEditProductoTarifa(rowid, tipo, sTitle, tipoAcceso) {
	cleanDataConfirmation();
	cleanMsgConfirmation();
	destroyCombosGeneralOne('proveedorProductoTarifaList');
	destroyCombosGeneralOne('productoTarifaList');
	destroyCombosGeneralOne('productoTipValList');
	destroyCombosGeneralOne('habilitadoProductoTarifaList');
	initialiseProductoTarifa();
	registroSeleccionado = rowid;
	
	$("#tipoModificacion").val(tipoAcceso);

	var fhDesde = $('#productoPeriodoSeleccionado').val();
	$("#proveedorProductoTarifaList").selectmenu('setValue', $('#cdProveedorSeleccionado').val()); 	
	$("#productoTarifaList").selectmenu('setValue', $('#cdProductoSeleccionado').val()); 
	$("#fhDesdeTarifa").val(fhDesde);
	$('#productoTipValList').selectmenu('setValue', $("#productoTipVal").val());
	$("#productoTipValList").selectmenu('disabled',true);
	$("#fhDesdeTarifa").attr('disabled',false).addClass("ui-state-disabled");
	$("#nuCantDesde").attr('disabled',false).addClass("ui-state-disabled");
    $('#stPrecioFijo').attr('disabled',false).removeClass("ui-state-disabled");

	var fhHasta = $('#tarifaFhHasta').val();
	$('#fhHastaTarifa').val(fhHasta);
	$("#fhHastaTarifa").attr('disabled',false).addClass("ui-state-disabled");
	
    if (tipoAcceso == 'A') {
		$("#nuCantHasta").attr('disabled',false).removeClass("ui-state-disabled");
		$("#nuPrecioUniVal").attr('disabled',false).removeClass("ui-state-disabled");
		$("#nuPorcTarifa").attr('disabled',false).removeClass("ui-state-disabled");
        $('#proveedorProductoTarifaList').selectmenu('disabled',true);
        $('#productoTipValList').selectmenu('disabled',true);
		$('#productoTarifaList').selectmenu('disabled',false); 
        $('#stPrecioFijo').attr('disabled',false).removeClass("ui-state-disabled");
	
		$('#habilitadoProductoTarifaList').selectmenu('disabled',false);    
		$('#habilitadoProductoTarifaList').selectmenu('setValue', "S");		
    }
    if (tipoAcceso != 'A') {
		var nuCantDesde       = $('#gridProductosPeriodosTarifasId').jqGrid('getCell', rowid, 'nuCantDesde');
		var nuCantHasta       = $('#gridProductosPeriodosTarifasId').jqGrid('getCell', rowid, 'nuCantHasta');
		var nuPrecioUniVal    = $('#gridProductosPeriodosTarifasId').jqGrid('getCell', rowid, 'nuPrecioUniVal');
		var nuPorcTarifa      = $('#gridProductosPeriodosTarifasId').jqGrid('getCell', rowid, 'nuPorcTarifa');
		var stHabilitado      = $('#gridProductosPeriodosTarifasId').jqGrid('getCell', rowid, 'stHabilitado');
		var stPrecioFijo      = $('#gridProductosPeriodosTarifasId').jqGrid('getCell', rowid, 'stPrecioFijo');

		$("#nuCantDesde").val(nuCantDesde);
		$("#nuCantHasta").val(nuCantHasta);
		$("#nuPrecioUniVal").val(nuPrecioUniVal);
		$("#nuPorcTarifa").val(nuPorcTarifa);
		$('#habilitadoProductoTarifaList').selectmenu('setValue', stHabilitado);
		
	    if (stPrecioFijo == 'S') {
	        $('#stPrecioFijo').attr('checked','checked');
	     } else {
	        $('#stPrecioFijo').removeAttr('checked');
	     }
	    
		if (tipoAcceso == 'C' || tipoAcceso == 'D' ) {
			$("#nuCantHasta").attr('disabled',true).addClass("ui-state-disabled");
			$("#nuPrecioUniVal").attr('disabled',true).addClass("ui-state-disabled");
			$("#nuPorcTarifa").attr('disabled',true).addClass("ui-state-disabled");
			$('#habilitadoProductoTarifaList').selectmenu('disabled',true);
	        $('#stPrecioFijo').attr('disabled',true).removeClass("ui-state-disabled");
		} else {
			$("#nuCantHasta").attr('disabled',true).addClass("ui-state-disabled");
			$("#nuPrecioUniVal").attr('disabled',false).removeClass("ui-state-disabled");
			$("#nuPorcTarifa").attr('disabled',false).removeClass("ui-state-disabled");
			$('#habilitadoProductoTarifaList').selectmenu('disabled',false);
	        $('#stPrecioFijo').attr('disabled',false).removeClass("ui-state-disabled");
		}
    }
	if (tipoAcceso == 'C') {
		$("#nuPrecioUniVal").attr('disabled',true).addClass("ui-state-disabled");
        $('#stPrecioFijo').attr('disabled',true).addClass("ui-state-disabled");
		$(":button:contains('Grabar')").button().hide();
	} else {
		$(":button:contains('Grabar')").button().show();				
	}

	$('#proveedorProductoTarifaList').selectmenu('disabled',true);
	$('#productoTipValList').selectmenu('disabled',true);
	$('#productoTarifaList').selectmenu('disabled',true);
	$("#fhDesdeTarifa").attr('disabled',true).addClass("ui-state-disabled");
	$("#nuCantDesde").attr('disabled',true).addClass("ui-state-disabled");
//  $("#stPrecioFijo").attr('disabled',true).addClass("ui-state-disabled");

	if (tipoAcceso == 'A') {
		var params   = '';
		params += '&cdProveedor='+$('#cdProveedorSeleccionado').val();
		params += '&cdProducto='+$('#cdProductoSeleccionado').val();
		params += '&fhDesde='+dateFormat_JsToService(fhDesde);
//alert(nuevoCodigoTarifa);
		if (nuevoCodigoTarifa == 0) {
			if ($('#tarifaCantDesde').val() == "") {
				while (nuevoCodigoTarifa == 0){
					callJsonSyncAction('getNuevoCodigoTarifa.action', params, 'successNuevoCodigoTarifa','errorNuevoCodigoTarifa');
				}
	        	$("#nuCantDesde").val($('#tarifaCantDesde').val());
			} else {
	        	$("#nuCantDesde").val($('#tarifaCantDesde').val());
			}
			nuevoCodigoTarifa = parseInt($('#tarifaCantDesde').val());
//alert(nuevoCodigoTarifa);
		} else {
        	$("#nuCantDesde").val(nuevoCodigoTarifa);
		}
	}
    $('#dialogEditProductoTarifa').dialog('option','title',sTitle);
	$('#dialogEditProductoTarifa').dialog('open');
}

function successNuevoCodigoTarifa(jsonData){
	try {
		nuevoCodigoTarifa = 1;
	    if (jsonData.CodigoTarifaList!=undefined){
	        if (jsonData.CodigoTarifaList.length>0){
	            for (var i=0;i<jsonData.CodigoTarifaList.length;i++) {
	    			$('#tarifaCantDesde').val(jsonData.CodigoTarifaList[i]);
	    			nuevoCodigoTarifa = $('#tarifaCantDesde').val();
	            	
//	            	alert(jsonData.CodigoTarifaList[i]);
	            }
            }
        } 
   } catch(e) {
      jsError('successNuevoCodigoTarifa', e);
   }
}

function errorNuevoCodigoTarifa(cod, des){
//   jsError('errorNuevoCodigoTarifa', des);
}

function initialiseProductoTarifa(){
   recargarProducto($("#cdProveedorSeleccionado").val());
}

function recargarProducto(cdProveedor) {
    cdProveedor = $("#cdProveedorSeleccionado").val();
    callJsonAction("comboProductosPeriodosTarifas.action","cdProveedor="+cdProveedor,"successProductos","errorProductos");
}

function successProductos(jsonData){
	try {
      document.getElementById('productoTarifaList').options.length=0;
      $('#productoTarifaList').selectmenu('destroy');
      if (jsonData.ProductoPeriodoTarifaList!=undefined){
    	  resetProductoTarifaOption(' Sin Producto ', '0', true);	
         
          if (jsonData.ProductoPeriodoTarifaList.length>0){
            for (var i=0;i<jsonData.ProductoPeriodoTarifaList.length;i++) {
//            	alert(jsonData.ProductoPeriodoTarifaList[i].cod);
//            	alert(jsonData.ProductoPeriodoTarifaList[i].desc);
            	
                document.getElementById('productoTarifaList')[document.getElementById('productoTarifaList').options.length] = new Option((jsonData.ProductoPeriodoTarifaList[i]).desc, jsonData.ProductoPeriodoTarifaList[i].cod);            	
            }
            destroyCombosGeneralOne('productoTarifaList');
            $('#productoTarifaList').selectmenu('disabled',true);
         }
         $('#productoTarifaList').selectmenu('setValue', $("#cdProductoSeleccionado").val());
      }
   } catch(e) {
      jsError('successProductos', e);
   }
}

function resetProductoTarifaOption(label, value, disabled){
   document.getElementById('productoTarifaList').options.length=0;
   document.getElementById('productoTarifaList')[0]= new Option(label,value);
   $('#productoTarifaList').selectmenu('destroy');
   $('#productoTarifaList').selectmenu({style:'dropdown',  width : $('#proveedorProductoTarifaList').attr("width")}).selectmenu('disabled',true);
}

function errorProductos(cod, des){
   jsError('errProducto', des);
}

function saveProductoTarifa() {
	var params   = '';
	var err      = "";
	var tipoModificacion = $("#tipoModificacion").val();
	
	if (tipoModificacion == "D") {
		confirmarDeleteProductoTarifa();
	} else {
		if (tipoModificacion == "E") {
			params += 'opcion=2';
		} else {
			params += 'opcion=1';
		}
	    var stPrecioFijo = ($('#stPrecioFijo').is(':checked')) ? 'S' : 'N';
		
		params += '&cdProveedor='+$('#proveedorProductoTarifaList').val();
		params += '&cdProducto='+$('#productoTarifaList').val();
		params += '&fhDesde='+dateFormat_JsToService($("#fhDesdeTarifa").val());
		params += '&nuCantDesde='+$("#nuCantDesde").val();
		params += '&nuCantHasta='+$("#nuCantHasta").val();
		params += '&nuPrecioUniVal='+removeCommas($('#nuPrecioUniVal').val());
		params += '&nuPorcTarifa='+removeCommas($('#nuPorcTarifa').val());
//		params += '&stHabilitado='+$("#habilitadoProductoTarifaList").val();
		params += '&stHabilitado=S';
		params += '&stPrecioFijo='+stPrecioFijo;
		
		if ($('#proveedorProductoTarifaList').val() == "0") {
			err += "El Proveedor es obligatorio\n";
		}
		if ($('#ProductoTarifaList').val() == "0") {
			err += "El Producto es obligatorio\n";
		}
		if (!validateDate($("#fhDesdeTarifa").val())) {
			err += "La fecha Desde es invalida\n";
		}
		
		if ($("#productoTipVal").val() == "2") {		
			if ($.trim($('#nuPrecioUniVal').val()) == '') {
				err += "El Precio es obligatorio\n";
			} else {
				if($('#nuPrecioUniVal').val() > 99999999999.9999){	
					err += "El Precio excede el valor m\u00e1ximo \n";		
				}
				if($('#nuPrecioUniVal').val() < 0){	
					err += "El Precio no puede ser menor a cero \n";		
				}				
			}
		}
		if ($("#productoTipVal").val() == "3") {		
			if ($.trim($('#nuPorcTarifa').val()) == '') {
				err += "El Porcentaje a aplicar es obligatorio\n";
			} else {
				if($('#nuPorcTarifa').val() > 99999999999.9999){	
					err += "El Porcentaje excede el valor m\u00e1ximo \n";		
				}
				if($('#nuPrecioUniVal').val() < 0){	
					err += "El Porcentaje no puede ser menor a cero \n";		
				}
			}
		}
		if ($.trim($("#habilitadoProductoTarifaList").val()) == '') {
			err += "El Estado es obligatorio\n";
		}	
		if (err != "") {
			alert("Verifique los datos ingresados\n\n" + err);
		} else {
			callJsonAction('saveProductoPeriodoTarifa.action', params, 'successSaveProductoTarifa','errorSaveProductoTarifa');
			if (tipoModificacion != "E") {
				$('#tarifaCantDesde').val(parseInt($("#nuCantHasta").val())+1);	
				nuevoCodigoTarifa = parseInt($("#nuCantHasta").val())+1;			
			}
		}
	}
}

function successSaveProductoTarifa(json){	 
	if (isSuccessResult(json.result.errorCode)){
//alert($('#tarifaCantDesde').val());

		setSuccessMsg($('#producto_responseMsgs'),"Editado Exitosamente");
		$('#producto_responseMsgs').show();
		$('#dialogEditProductoTarifa').dialog('close');
		resetProductoTarifaForm();
		search();
		cleanDataConfirmation();
	} else {		
		setErrorMsg($('#prod_Periodo_responseMsgs'),json.result.errorDesc);
		$('#prod_Periodo_responseMsgs').show();
	}	
}

function errorSaveProductoTarifa(errorCode, errorDesc){
	setErrorMsg($('#producto_responseMsgs'),errorDesc);
	$('#producto_responseMsgs').show();	
	$('#dialogEditProductoTarifa').dialog('close');
	resetProductoTarifaForm();
	cleanDataConfirmation();
}

function confirmarDeleteProductoTarifa(){
	var totalRegistros = $("#gridProductosPeriodosTarifasId").jqGrid('getGridParam', 'records');
	
	borraCodigoTarifa = $("#nuCantDesde").val();
	
	var params = '';
	params += 'cdProveedor='+$('#proveedorProductoTarifaList').val();
	params += '&cdProducto='+$('#productoTarifaList').val();
	params += '&fhDesde='+dateFormat_JsToService($("#fhDesdeTarifa").val());	
	params += '&nuCantDesde='+$("#nuCantDesde").val();	
	
	if (confirm('Confirma la baja de la Tarifa del Periodo?')) {
		if (getSelRowFromProductoTarifaGridAllPages() != totalRegistros) {
			alert("No puede eliminar rangos intermedios, debe ser el último");
		} else {
			nuevoCodigoTarifa = borraCodigoTarifa;
			callJsonAction('deleteProductoPeriodoTarifa.action', params, 'successDeleteProductoTarifa', 'errorDeleteProductoTarifa');
		}
	}
}

function successDeleteProductoTarifa(json){	 
	if (isSuccessResult(json.result.errorCode)){		
		setSuccessMsg($('#producto_responseMsgs'),"Eliminado Exitosamente");
		$('#producto_responseMsgs').show();
		$('#dialogEditProductoTarifa').dialog('close');
		nuevoCodigoTarifa = borraCodigoTarifa;
		search();
	} else {	
		setErrorMsg($('#producto_responseMsgs'),json.result.errorDesc);
		$('#producto_responseMsgs').show();
	}
	cleanDataConfirmation();
}

function errorDeleteProductoTarifa(errorCode, errorDesc){
	setErrorMsg($('#producto_responseMsgs'),errorDesc);
	$('#producto_responseMsgs').show();	
	$('#dialogEditProductoTarifa').dialog('close');
	cleanDataConfirmation();
}

function confirmarDeleteAll(){	
	var params = '';
    params += 'cdProveedor='+$("#cdProveedorSeleccionado").val();
    params += '&cdProducto='+$("#cdProductoSeleccionado").val();
    params += '&fhDesde='+dateFormat_JsToService($("#productoPeriodoSeleccionado").val());	
	
	if (confirm('Confirma la eliminación de todas las Tarifas del Periodo?')) {		
		callJsonAction('deleteProductoPeriodoTarifas.action', params, 'successDeleteAll', 'errorDeleteAll');
	}
}

function successDeleteAll(json){	 
	if (isSuccessResult(json.result.errorCode)){		
		setSuccessMsg($('#producto_responseMsgs'),"Eliminado Exitosamente");
		$('#producto_responseMsgs').show();
		$('#dialogEditProductoTarifa').dialog('close');
		nuevoCodigoTarifa = 1;
		search();
	} else {	
		setErrorMsg($('#producto_responseMsgs'),json.result.errorDesc);
		$('#producto_responseMsgs').show();
	}
	cleanDataConfirmation();
}

function errorDeleteAll(errorCode, errorDesc){
	setErrorMsg($('#producto_responseMsgs'),errorDesc);
	$('#producto_responseMsgs').show();	
	$('#dialogEditProductoTarifa').dialog('close');
	cleanDataConfirmation();
}

function cleanDataConfirmation(){
	$("#fhDesdeTarifa").val('');
	$("#nuCantDesde").val('');
	$("#nuCantHasta").val('');
	$("#nuPrecioUniVal").val('');
}

function cleanMsgConfirmation(){
	$('#producto_responseMsgs').hide();
	$('#producto_responseMsgs').val('');
	$('#msgEspera').hide();
	$('#msgEspera').val('');
	$('#msgConfirmacion').hide();
	$('#msgConfirmacion').val('');
	$('#prod_Periodo_responseMsgs').hide();
	$('#prod_Periodo_responseMsgs').val('');		
}

function dateFormat_serviceToJs(fecha){
	var value = "";
	if (fecha != null && fecha != "") {
		value = fecha.substr(8,2)+'/'+fecha.substr(5,2)+'/'+fecha.substr(0,4);
	}
	return value;
}

function removeCommas(numero) {
   if (numero == '') {
	   return 0;
   } else {
	   return parseFloat(numero.replace(',', '.'));
   }
}
