$(document).ready(function(){
	$("#fhDesde").mask("99/99/9999",{placeholder:" "});
	$("#fhHasta").mask("99/99/9999",{placeholder:" "});

//	destroyCombosGeneralAll();

	$.ajaxSetup({
        cache : false,
        async : false
    });
	$("#fhDesde").datepicker({
		changeMonth: true,
		changeYear: true,
		regional: 'es',
		yearRange: 'c-100:c',
		dateFormat: 'dd/mm/yy',
		showAnim: '',
		showOn: "button",
		duration: 0
	}).next('button').text('&nbsp;').addClass('ui-button-text').button({icons:{primary : 'ui-icon ui-icon-calendar'}}).removeClass().addClass('ui-button ui-widget ui-state-default ui-button-icon-only ui-corner-right ui-button-icon').attr('id', 'fhDesdeBut2');

	$("#fhHasta").datepicker({
		changeMonth: true,
		changeYear: true,
		regional: 'es',
		yearRange: 'c-100:c',
		dateFormat: 'dd/mm/yy',
		showAnim: '',
		showOn: "button",
		duration: 0
	}).next('button').text('&nbsp;').addClass('ui-button-text').button({icons:{primary : 'ui-icon ui-icon-calendar'}}).removeClass().addClass('ui-button ui-widget ui-state-default ui-button-icon-only ui-corner-right ui-button-icon').attr('id', 'fhHastaBut2');

//    $('input[type="text"]').each(function() {
//        $(this).setMask($(this).attr('alt'));
//    });

    initialiseFiltroProductoPeriodo();
	initProductoPeriodoDialog();
	cleanDataConfirmation();
	
	search();
});
	
function search() {
	$('#agregarGrantPP').val('S');
	limpiarGrilla('gridProductosPeriodosId', 'gridProductosPeriodosPager', 'gridProductosPeriodos');

	$('#productosPeriodosGrid').show();
	$('#botonera').show();
	$('#buttonCancelar').button({
		icons : {
			secondary : ""
		}
	}).click(function() {

		resetProductoPeriodoForm();
		cleanDataConfirmation();
	});
	loadProductosPeriodosGrid();
}

function initProductoPeriodoDialog(){
	$("#dialogEditProductoPeriodo").dialog({
		bgiframe: true,
		autoOpen: false,
		modal: true,
		width: 680,
		
		close: reloadProductosPeriodosGrid, 
		buttons: [ 
			{   id: 'guardar-button',
				text: "Grabar",
		        click: function() {
		        	try {
						saveProductoPeriodo();
					} catch(e) {
						jsError('saveProductoPeriodo(...)',e.message);
					}
				}
		    },
   	        {   id: 'cancelar-button',
   	            text: "Cancelar",
   	        	click: function() {
   	        		$('#prod_Periodo_responseMsgs').hide();
					resetProductoPeriodoForm();
					cleanDataConfirmation();
	            	$(this).dialog('close');
            	}
	        }
		]
	});
}

// GRILLA
function loadProductosPeriodosGrid(){
	var params = '';
	if (($("#cdProveedorSeleccionado").val() != '') && ($("#cdProveedorSeleccionado").val() != undefined)) {
	   params = 'cdProveedor='+$("#cdProveedorSeleccionado").val();
	   if (($("#cdProductoSeleccionado").val() != '') && ($("#cdProductoSeleccionado").val() != undefined)) {
		   params += '&cdProducto='+$("#cdProductoSeleccionado").val();
	   }
	} else {
       params = 'cdProveedor=999&cdProducto=999';
	}
	try {
		showGrid({
			id : 'gridProductosPeriodosId',
			idPager : 'gridProductosPeriodosPager',
			url : 'JsonProductosPeriodosList.action?'+params,
			colNames : [ 'Proveedor', 'Producto', 'Fecha Desde', 'Fecha Hasta', 'Habilitado' ],
			colModel : [ 
				{name : 'cdProveedor'        , width : 140, align : 'left', hidden : true }, 
				{name : 'cdProducto'         , index : 'id', width : 140, align : 'left', hidden : true }, 
				{name : 'fhDesde'            , width : 200, align : 'center', hidden : false,formatter: 'date', formatoptions: { srcformat: 'Y/m/d', newformat: 'd/m/Y'} }, 
				{name : 'fhHasta'            , width : 200, align : 'center', hidden : false,formatter: 'date', formatoptions: { srcformat: 'Y/m/d', newformat: 'd/m/Y'} }, 
				{name : 'stHabilitado'       , width : 120, align : 'center', hidden : false }
			],
			rowNum : 10,
			rowList : [ 10, 15, 20 ],
			sortName : 'cdProducto',
			caption : "Periodos de Valorizaci&oacute;n",
			height : '100%',
			width : '100%',
			multiselect: false,
			loadonce : true,
			loadCompleteFunc : '',
            editurl: 'clientArray'
		});
		$('.ui-jqgrid-title').css('width', '98%');
		addButtonsToProductoPeriodoGrid();
		$("#gridProductosPeriodosId").jqGrid('setGridParam', 
			    {onSelectRow: function(rowid,iRow,iCol,e){asignoPeriodo(rowid);}});		
	} catch(e) {
		jsError('loadProductosPeriodosGrid(...)', e);
	}
}

function asignoPeriodo(rowid) {
	var cdProveedor = $('#gridProductosPeriodosId').jqGrid('getCell', rowid, 'cdProveedor');
	var cdProducto  = $('#gridProductosPeriodosId').jqGrid('getCell', rowid, 'cdProducto');
	var fhDesde     = $('#gridProductosPeriodosId').jqGrid('getCell', rowid, 'fhDesde');
	var fhHasta     = $('#gridProductosPeriodosId').jqGrid('getCell', rowid, 'fhHasta');
    $('#cdProveedor').val(cdProveedor);
    $('#cdProducto').val(cdProducto);
    $('#productoPeriodoSeleccionado').val(fhDesde);
	$('#tarifaCantDesde').val("");
	$('#tarifaFhHasta').val(fhHasta);
//alert($('#tarifaFhHasta').val());
//alert($('#productoPeriodoSeleccionado').val());    
//  $('#fhDesde').val(fhDesde);
	$("#tabsApplication").enableTab(4);    
	$("#tab4").show();
}

// AGREGA BOTONERA DE LA GRILLA
function addButtonsToProductoPeriodoGrid(){
	if($('#addGrantPP').val()=='S'){
		var title = 'Agregar Periodo Producto';
		$('#gridProductosPeriodosId').navButtonAdd('#gridProductosPeriodosPager', {
			caption: 'Agregar',
			buttonicon: 'ui-icon ui-icon-plus',
			onClickButton: function() {
				try {
					$('#gridProductosPeriodosId').resetSelection();
					processEditProductoPeriodo(0, false, 'Ingresar Tarifa', 'A');
				} catch (e) {
					jsError('loadProductosPeriodosGrid(...)', e);
				}
			}, 
			position:'last', 
			title:title
		});
	}
	if($('#editGrantPP').val()=='S') {
		var title = 'Editar Periodo Producto';
		$('#gridProductosPeriodosId').navButtonAdd('#gridProductosPeriodosPager', {
			caption: 'Editar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromProductoPeriodoGrid(); 
					if (rowid != null) {
						processEditProductoPeriodo(rowid, false, 'Editar Tarifa', 'E');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadProductosPeriodosGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
	}
	if($('#deleteGrantPP').val()=='S') {
		var title = 'Eliminar Periodo Producto';
		$('#gridProductosPeriodosId').navButtonAdd('#gridProductosPeriodosPager', {
			caption: 'Eliminar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromProductoPeriodoGrid(); 
					if (rowid != null) {
						processEditProductoPeriodo(rowid, false, 'Eliminar Tarifa', 'D');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadProductosPeriodosGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
	}
	if($('#selectGrant').val()=='S') {
		var title = 'Consulta Periodo Producto';
		$('#gridProductosPeriodosId').navButtonAdd('#gridProductosPeriodosPager', {
			caption: 'Consultar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromProductoPeriodoGrid(); 
					if (rowid != null) {
						processEditProductoPeriodo(rowid, false, 'Consultar Tarifa', 'C');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadProductosPeriodosGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
	}
}

// FUNCTIONES GENERALES GRILLA
function reloadProductosPeriodosGrid(){
	$('#gridProductosPeriodosId').trigger('reloadGrid');
}

function getSelRowFromProductoPeriodoGrid() { 
	return $("#gridProductosPeriodosId").getGridParam('selrow');
}

function resetProductoPeriodoForm() {
	$(':input','#dialogEditProductoPeriodo').not('input[type=hidden], :button').val('').removeAttr('checked').removeAttr('selected');
}

//EDIT PRODUCTO
function processEditProductoPeriodo(rowid, tipo, sTitle, tipoAcceso) {
	cleanDataConfirmation();
	cleanMsgConfirmation();
//	destroyCombos();
	destroyCombosGeneralOne('proveedorProductoPeriodoList');
	destroyCombosGeneralOne('habilitadoProductoPeriodoList');

	$("#tipoModificacion").val(tipoAcceso);

    if (tipoAcceso == 'A') {
		$('#productoPeriodoList').selectmenu('disabled',false);
		$("#fhDesde").attr('disabled',false);
		$("#fhHasta").attr('disabled',false);
		$("#fhDesde").attr('disabled',false).removeClass("ui-state-disabled");
		$("#fhDesdeBut2").button().show();
		$('#habilitadoProductoPeriodoList').selectmenu('disabled',false);    
		$("#proveedorProductoPeriodoList").selectmenu('setValue', $('#cdProveedorSeleccionado').val()); 
	
        $('#proveedorProductoPeriodoList').selectmenu('disabled',true);
		$('#habilitadoProductoPeriodoList').selectmenu('setValue', "S");
        recargarProducto($('#proveedorProductoPeriodoList').val());   		
		
    }
    if (tipoAcceso != 'A') {
    	
		var cdProveedor       = $('#gridProductosPeriodosId').jqGrid('getCell', rowid, 'cdProveedor');
		var cdProducto        = $('#gridProductosPeriodosId').jqGrid('getCell', rowid, 'cdProducto');
		var fhDesde           = $('#gridProductosPeriodosId').jqGrid('getCell', rowid, 'fhDesde');
		var fhHasta           = $('#gridProductosPeriodosId').jqGrid('getCell', rowid, 'fhHasta');
		var stHabilitado      = $('#gridProductosPeriodosId').jqGrid('getCell', rowid, 'stHabilitado');
//alert("ACA - "+fhHasta);
		recargarProducto(cdProveedor);
		
        $('#proveedorProductoPeriodoList').selectmenu('setValue', cdProveedor);
//		$("#fhDesde").val(dateFormat_serviceToJs(fhDesde));
//		$("#fhHasta").val(dateFormat_serviceToJs(fhHasta));
		$("#fhDesde").val((fhDesde));
		$("#fhHasta").val((fhHasta));
		$('#habilitadoProductoPeriodoList').selectmenu('setValue', stHabilitado);
/*
        var selectobject=document.getElementById("productoPeriodoList");
        for (var i=0; i<selectobject.length; i++){
        }

		
		$('#productoPeriodoList').selectmenu('setValue', $("#producto").val());
*/
		$('#proveedorProductoPeriodoList').selectmenu('disabled',true);
		$('#productoPeriodoList').selectmenu('disabled',true);
		$("#fhDesde").attr('disabled',true).addClass("ui-state-disabled");
//		$("#fhHasta").attr('disabled',true).addClass("ui-state-disabled");
//		$("#disableFechas").attr('disabled',true);
		if (tipoAcceso == 'C' || tipoAcceso == 'D' ) {
			$("#fhDesdeBut2").button().hide();
			$("#fhHastaBut2").button().hide();
			$("#fhHasta").attr('disabled',true).addClass("ui-state-disabled");
//			$("#fhDesde").attr('disabled',true).addClass("ui-state-disabled");
			
			$('#habilitadoProductoPeriodoList').selectmenu('disabled',true);
		} else {
			$("#fhDesdeBut2").button().hide();//se pidio que no se pueda modificar la fecha "desde" cuando es edicion por se clave.
//			$("#fhDesdeBut2").button().show();
			$("#fhHastaBut2").button().show();
			$("#fhHasta").attr('disabled',false).removeClass("ui-state-disabled");
//			$("#fhDesde").attr('disabled',false).removeClass("ui-state-disabled");
			$('#habilitadoProductoPeriodoList').selectmenu('disabled',false);    	
		}
    }	   
	if (tipoAcceso == 'C') {
		$(":button:contains('Grabar')").button().hide();				
	} else {
		$(":button:contains('Grabar')").button().show();				
	}		
    $('#dialogEditProductoPeriodo').dialog('option','title',sTitle);
	$('#dialogEditProductoPeriodo').dialog('open');
}

function initialiseFiltroProductoPeriodo(){
   var $proveedor = $('#proveedorProductoPeriodoList');
   $proveedor.change(function(){
      hideCommonDataElements();
      recargarProducto($("#cdProveedorSeleccionado").val());
   });
   onchangeOptions('proveedorProductoPeriodoList');
}

function onchangeOptions(selectorId){
   $("#"+selectorId).change(function(){
      hideCommonDataElements();
   });
}

function hideCommonDataElements(){
   if ($('#proveedorProductoPeriodoList').val()=="0"){
      resetProductoPeriodoOption(' Sin Producto ', '0', false);	
   }
}

function recargarProducto(cdProveedor) {
    cdProveedor = $("#cdProveedorSeleccionado").val();
    callJsonAction("comboProductosPeriodos.action","cdProveedor="+cdProveedor,"successProductos","errorProductos");
}

function successProductos(jsonData){
   try {
      document.getElementById('productoPeriodoList').options.length=0;
      $('#productoPeriodoList').selectmenu('destroy');
      if (jsonData.ProductoPeriodoList!=undefined){
    	  resetProductoPeriodoOption(' Sin Producto ', '0', true);	
              	  
         if (jsonData.ProductoPeriodoList.length>0){
            for (var i=0;i<jsonData.ProductoPeriodoList.length;i++)
               document.getElementById('productoPeriodoList')[document.getElementById('productoPeriodoList').options.length] = new Option((jsonData.ProductoPeriodoList[i]).desc, jsonData.ProductoPeriodoList[i].cod);
            
            destroyCombosGeneralOne('productoPeriodoList');
//            $('#productoPeriodoList').selectmenu('destroy');
            $('#productoPeriodoList').selectmenu('disabled',true);
         }
         $('#productoPeriodoList').selectmenu('setValue', $("#cdProductoSeleccionado").val());
      }
   } catch(e) {
      jsError('successProductos', e);
   }
}

function resetProductoPeriodoOption(label, value, disabled){
   document.getElementById('productoPeriodoList').options.length=0;
   document.getElementById('productoPeriodoList')[0]= new Option(label,value);
   $('#productoPeriodoList').selectmenu('destroy');
   $('#productoPeriodoList').selectmenu({style:'dropdown',  width : $('#proveedorProductoPeriodoList').attr("width")}).selectmenu('disabled',true);
}

function errorProductos(cod, des){
   jsError('errProducto', des);
}

function saveProductoPeriodo() {
	var params   = '';
	var err      = "";
	var tipoModificacion = $("#tipoModificacion").val();
	
	if (tipoModificacion == "D") {
		confirmarDeleteProductoPeriodo();
	} else {
		if (tipoModificacion == "E") {
			params += 'opcion=2';
		} else {
			params += 'opcion=1';
		}
		params += '&cdProveedor='+$('#proveedorProductoPeriodoList').val();
		params += '&cdProducto='+$('#productoPeriodoList').val();
		params += '&fhDesde='+dateFormat_JsToService($("#fhDesde").val());
		params += '&fhHasta='+dateFormat_JsToService($("#fhHasta").val());
		params += '&stHabilitado='+$("#habilitadoProductoPeriodoList").val();
		
		if ($('#proveedorProductoPeriodoList').val() == "0") {
			err += "El Proveedor es obligatorio\n";
		}
		if ($('#productoPeriodoList').val() == "0") {
			err += "El Producto es obligatorio\n";
		}
		if (!validateDate($("#fhDesde").val())) {
			err += "La fecha Desde es invalida\n";
		} 
		if (!validateDate($("#fhHasta").val())) {
			err += "La fecha Hasta es invalida\n";
		} 
		if (validateDate($("#fhDesde").val()) && validateDate($("#fhHasta").val())) {
			var fd_  = $("#fhDesde").val();
			var fde = fd_.substr(6,4)+fd_.substr(3,2)+fd_.substr(0,2);
			
			var fh_  = $("#fhHasta").val();
			var fha  = fh_.substr(6,4)+fh_.substr(3,2)+fh_.substr(0,2);
			
			if (fde > fha) {
				err += "La fecha Desde no debe ser mayor a la fecha Hasta\n";
			} 
		}		
		if ($.trim($("#habilitadoProductoPeriodoList").val()) == '') {
			err += "El Estado es obligatorio\n";
		}
		
		if (err != "") {
			alert("Verifique los datos ingresados\n\n" + err);
		} else {
			callJsonAction('saveProductoPeriodo.action', params, 'successSaveProductoPeriodo','errorSaveProductoPeriodo');
		}
	}
}

function successSaveProductoPeriodo(json){	 
	if (isSuccessResult(json.result.errorCode)){		
		setSuccessMsg($('#producto_responseMsgs'),"Editado Exitosamente");
		$('#producto_responseMsgs').show();
		$('#dialogEditProductoPeriodo').dialog('close');
		resetProductoPeriodoForm();
		search();
		cleanDataConfirmation();
	} else {		
		setErrorMsg($('#prod_Periodo_responseMsgs'),json.result.errorDesc);
		$('#prod_Periodo_responseMsgs').show();
	}	
}

function errorSaveProductoPeriodo(errorCode, errorDesc){
	setErrorMsg($('#producto_responseMsgs'),errorDesc);
	$('#producto_responseMsgs').show();	
	$('#dialogEditProductoPeriodo').dialog('close');
	resetProductoPeriodoForm();
	cleanDataConfirmation();
}

function confirmarDeleteProductoPeriodo(){	
	var params = '';
	params += 'cdProveedor='+$('#proveedorProductoPeriodoList').val();
	params += '&cdProducto='+$('#productoPeriodoList').val();	
	params += '&fhDesde='+dateFormat_JsToService($("#fhDesde").val());	
	
	if (confirm('Confirma la baja del Periodo del Producto?')) {
		callJsonAction('deleteProductoPeriodo.action', params, 'successDeleteProductoPeriodo', 'errorDeleteProductoPeriodo');
	}
}

function successDeleteProductoPeriodo(json){	 
	if (isSuccessResult(json.result.errorCode)){		
		setSuccessMsg($('#producto_responseMsgs'),"Eliminado Exitosamente");
		$('#producto_responseMsgs').show();
		$('#dialogEditProductoPeriodo').dialog('close');
		search();
	} else {		
		setErrorMsg($('#producto_responseMsgs'),json.result.errorDesc);
		$('#producto_responseMsgs').show();
	}	
	cleanDataConfirmation();
}

function errorDeleteProductoPeriodo(errorCode, errorDesc){
	setErrorMsg($('#producto_responseMsgs'),errorDesc);
	$('#producto_responseMsgs').show();	
	$('#dialogEditProductoPeriodo').dialog('close');
	cleanDataConfirmation();
}

function cleanDataConfirmation(){
//	$("#cdProveedorProductoPeriodo").val('');
//	$("#cdProductoPeriodo").val('');
	$("#fhDesde").val('');
	$("#fhHasta").val('');
	$('#stHabilitadoProductoPeriodo').val('');
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
