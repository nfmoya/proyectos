$(document).ready(function(){
	$("#fhDesdeProdu").mask("99/99/9999",{placeholder:" "});
	$("#fhHastaProdu").mask("99/99/9999",{placeholder:" "});

//	destroyCombosGeneralAll();

	$.ajaxSetup({
        cache : false,
        async : false
    });
	
	$("#fhDesdeProdu").datepicker({
		changeMonth: true,
		changeYear: true,
		regional: 'es',
		yearRange: 'c-100:c',
		dateFormat: 'dd/mm/yy',
		showAnim: '',
		showOn: "button",
		duration: 0
	}).next('button').text('&nbsp;').addClass('ui-button-text').button({icons:{primary : 'ui-icon ui-icon-calendar'}}).removeClass().addClass('ui-button ui-widget ui-state-default ui-button-icon-only ui-corner-right ui-button-icon').attr('id', 'fhDesdeBut2');

	$("#fhHastaProdu").datepicker({
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

    initialiseFiltroProductoPrecio();
	initProductoPrecioDialog();
	cleanDataConfirmation();
	
	search();
});
	
function search() {
	$('#agregarGrantPP').val('S');
	limpiarGrilla('gridProductosPreciosId', 'gridProductosPreciosPager', 'gridProductosPrecios');

	$('#productosPreciosGrid').show();
	$('#botonera').show();
	$('#buttonCancelar').button({
		icons : {
			secondary : ""
		}
	}).click(function() {

		resetProductoPrecioForm();
		cleanDataConfirmation();
	});
	loadProductosPreciosGrid();
}

function initProductoPrecioDialog(){
	$("#dialogEditProductoPrecio").dialog({
		bgiframe: true,
		autoOpen: false,
		modal: true,
		width: 550,
		
		close: reloadProductosPreciosGrid, 
		buttons: [ 
			{   id: 'guardar-button',
				text: "Grabar",
		        click: function() {
		        	try {
						saveProductoPrecio();
					} catch(e) {
						jsError('saveProductoPrecio(...)',e.message);
					}
				}
		    },
   	        {   id: 'cancelar-button',
   	            text: "Cancelar",
   	        	click: function() {
   	        		$('#prod_precio_responseMsgs').hide();
					resetProductoPrecioForm();
					cleanDataConfirmation();
	            	$(this).dialog('close');
            	}
	        }
		]
	});
}

// GRILLA
function loadProductosPreciosGrid(){
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
			id : 'gridProductosPreciosId',
			idPager : 'gridProductosPreciosPager',
			url : 'JsonProductosPreciosList.action?'+params,
			colNames : [ 'Proveedor', 'Producto', 'Fecha Desde', 'Fecha Hasta', 'Tarifa en Unid Val', 'Habilitado' ],
			colModel : [ 
				{name : 'cdProveedor'        , width : 140, align : 'left', hidden : true }, 
				{name : 'cdProducto'         , index : 'id', width : 140, align : 'left', hidden : true }, 
				{name : 'fhDesde'            , width : 160, align : 'center', hidden : false,formatter: 'date', formatoptions: { srcformat: 'Y/m/d', newformat: 'd/m/Y'} }, 
				{name : 'fhHasta'            , width : 160, align : 'center', hidden : false,formatter: 'date', formatoptions: { srcformat: 'Y/m/d', newformat: 'd/m/Y'} }, 
				{name : 'nuPrecioUniVal'     , width : 160, align : 'right', hidden : false }, 
				{name : 'stHabilitado'       , width : 160, align : 'center', hidden : false }
			],
			rowNum : 10,
			rowList : [ 10, 15, 20 ],
			sortName : 'cdProducto',
			caption : "Tarifas en Unidades de Valoraci&oacute;n",
			height : '100%',
			width : '100%',
			multiselect: false,
			loadonce : true,
			loadCompleteFunc : '',
            editurl: 'clientArray'
		});
		$('.ui-jqgrid-title').css('width', '98%');
		addButtonsToProductoPrecioGrid();
	} catch(e) {
		jsError('loadProductosPreciosGrid(...)', e);
	}
}

// AGREGA BOTONERA DE LA GRILLA
function addButtonsToProductoPrecioGrid(){
	if($('#addGrantPP').val()=='S'){
		var title = 'Agregar Precio Producto';
		$('#gridProductosPreciosId').navButtonAdd('#gridProductosPreciosPager', {
			caption: 'Agregar',
			buttonicon: 'ui-icon ui-icon-plus',
			onClickButton: function() {
				try {
					$('#gridProductosPreciosId').resetSelection();
					processEditProductoPrecio(0, false, 'Ingresar Tarifa', 'A');
				} catch (e) {
					jsError('loadProductosPreciosGrid(...)', e);
				}
			}, 
			position:'last', 
			title:title
		});
	}
	if($('#editGrantPP').val()=='S') {
		var title = 'Editar Precio Producto';
		$('#gridProductosPreciosId').navButtonAdd('#gridProductosPreciosPager', {
			caption: 'Editar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromProductoPrecioGrid(); 
					if (rowid != null) {
						processEditProductoPrecio(rowid, false, 'Editar Tarifa', 'E');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadProductosPreciosGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
	}
	if($('#deleteGrantPP').val()=='S') {
		var title = 'Eliminar Precio Producto';
		$('#gridProductosPreciosId').navButtonAdd('#gridProductosPreciosPager', {
			caption: 'Eliminar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromProductoPrecioGrid(); 
					if (rowid != null) {
						processEditProductoPrecio(rowid, false, 'Eliminar Tarifa', 'D');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadProductosPreciosGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
	}
	if($('#selectGrantPP').val()=='S') {
		var title = 'Consulta Precio Producto';
		$('#gridProductosPreciosId').navButtonAdd('#gridProductosPreciosPager', {
			caption: 'Consultar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromProductoPrecioGrid(); 
					if (rowid != null) {
						processEditProductoPrecio(rowid, false, 'Consultar Tarifa', 'C');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadProductosPreciosGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
	}
}

// FUNCTIONES GENERALES GRILLA
function reloadProductosPreciosGrid(){
	$('#gridProductosPreciosId').trigger('reloadGrid');
}

function getSelRowFromProductoPrecioGrid() { 
	return $("#gridProductosPreciosId").getGridParam('selrow');
}

function resetProductoPrecioForm() {
	$(':input','#dialogEditProductoPrecio').not('input[type=hidden], :button').val('').removeAttr('checked').removeAttr('selected');
}

//EDIT PRODUCTO
function processEditProductoPrecio(rowid, tipo, sTitle, tipoAcceso) {
	cleanDataConfirmation();
	cleanMsgConfirmation();
//	destroyCombos();
	destroyCombosGeneralOne('proveedorProductoPrecioList');
	destroyCombosGeneralOne('habilitadoProductoPrecioList');

	$("#tipoModificacion").val(tipoAcceso);

    if (tipoAcceso == 'A') {
		$('#productoPrecioList').selectmenu('disabled',false);
		$("#fhDesdeProdu").attr('disabled',false);
		$("#fhHastaProdu").attr('disabled',false);
		$("#fhDesdeProdu").attr('disabled',false).removeClass("ui-state-disabled");
		$("#fhDesdeBut2").button().show();
		$("#nuPrecioUniVal").attr('disabled',false);
		$('#habilitadoProductoPrecioList').selectmenu('disabled',false);    
		$("#proveedorProductoPrecioList").selectmenu('setValue', $('#cdProveedorSeleccionado').val()); 
	
        $('#proveedorProductoPrecioList').selectmenu('disabled',true);
		$('#habilitadoProductoPrecioList').selectmenu('setValue', "S");
        recargarProducto($('#proveedorProductoPrecioList').val());   		
		
    }
    if (tipoAcceso != 'A') {
    	
		var cdProveedor       = $('#gridProductosPreciosId').jqGrid('getCell', rowid, 'cdProveedor');
		var cdProducto        = $('#gridProductosPreciosId').jqGrid('getCell', rowid, 'cdProducto');
		var fhDesde           = $('#gridProductosPreciosId').jqGrid('getCell', rowid, 'fhDesde');
		var fhHasta           = $('#gridProductosPreciosId').jqGrid('getCell', rowid, 'fhHasta');
		var nuPrecioUniVal    = $('#gridProductosPreciosId').jqGrid('getCell', rowid, 'nuPrecioUniVal');
		var stHabilitado      = $('#gridProductosPreciosId').jqGrid('getCell', rowid, 'stHabilitado');

		recargarProducto(cdProveedor);
		
        $('#proveedorProductoPrecioList').selectmenu('setValue', cdProveedor);
//		$("#fhDesde").val(dateFormat_serviceToJs(fhDesde));
//		$("#fhHasta").val(dateFormat_serviceToJs(fhHasta));
		$("#fhDesdeProdu").val((fhDesde));
		$("#fhHastaProdu").val((fhHasta));
		$("#nuPrecioUniVal").val(nuPrecioUniVal);
		$('#habilitadoProductoPrecioList').selectmenu('setValue', stHabilitado);
/*
        var selectobject=document.getElementById("productoPrecioList");
        for (var i=0; i<selectobject.length; i++){
        }

		
		$('#productoPrecioList').selectmenu('setValue', $("#producto").val());
*/
		$('#proveedorProductoPrecioList').selectmenu('disabled',true);
		$('#productoPrecioList').selectmenu('disabled',true);
		$("#fhDesdeProdu").attr('disabled',true).addClass("ui-state-disabled");
//		$("#fhHasta").attr('disabled',true).addClass("ui-state-disabled");
//		$("#disableFechas").attr('disabled',true);
		if (tipoAcceso == 'C' || tipoAcceso == 'D' ) {
			$("#fhDesdeBut2").button().hide();
			$("#fhHastaBut2").button().hide();
			$("#fhHastaProdu").attr('disabled',true).addClass("ui-state-disabled");
//			$("#fhDesde").attr('disabled',true).addClass("ui-state-disabled");
			
//			$("#nuPrecioUniVal").attr('disabled',true).addClass("ui-state-disabled");
			$('#habilitadoProductoPrecioList').selectmenu('disabled',true);
		} else {
			$("#fhDesdeBut2").button().hide();//se pidio que no se pueda modificar la fecha "desde" cuando es edicion por se clave.
//			$("#fhDesdeBut2").button().show();
			$("#fhHastaBut2").button().show();
			$("#fhHastaProdu").attr('disabled',false).removeClass("ui-state-disabled");
//			$("#fhDesde").attr('disabled',false).removeClass("ui-state-disabled");
			$("#nuPrecioUniVal").attr('disabled',false);
			$('#habilitadoProductoPrecioList').selectmenu('disabled',false);    	
		}
    }	   
	if (tipoAcceso == 'C') {
		$("#nuPrecioUniVal").attr('disabled',true).addClass("ui-state-disabled");
		$(":button:contains('Grabar')").button().hide();				
	} else {
		$(":button:contains('Grabar')").button().show();				
	}		
    $('#dialogEditProductoPrecio').dialog('option','title',sTitle);
	$('#dialogEditProductoPrecio').dialog('open');
}

function initialiseFiltroProductoPrecio(){
   var $proveedor = $('#proveedorProductoPrecioList');
   $proveedor.change(function(){
      hideCommonDataElements();
      recargarProducto($("#cdProveedorSeleccionado").val());
   });
   onchangeOptions('proveedorProductoPrecioList');
}

function onchangeOptions(selectorId){
   $("#"+selectorId).change(function(){
      hideCommonDataElements();
   });
}

function hideCommonDataElements(){
   if ($('#proveedorProductoPrecioList').val()=="0"){
      resetProductoPrecioOption(' Sin Producto ', '0', false);	
   }
}

function recargarProducto(cdProveedor) {
    cdProveedor = $("#cdProveedorSeleccionado").val();
    callJsonAction("comboProductosPrecios.action","cdProveedor="+cdProveedor,"successProductos","errorProductos");
}

function successProductos(jsonData){
   try {
      document.getElementById('productoPrecioList').options.length=0;
      $('#productoPrecioList').selectmenu('destroy');
      if (jsonData.ProductoPrecioList!=undefined){
    	  resetProductoPrecioOption(' Sin Producto ', '0', true);	
              	  
         if (jsonData.ProductoPrecioList.length>0){
            for (var i=0;i<jsonData.ProductoPrecioList.length;i++)
               document.getElementById('productoPrecioList')[document.getElementById('productoPrecioList').options.length] = new Option((jsonData.ProductoPrecioList[i]).desc, jsonData.ProductoPrecioList[i].cod);
            
            destroyCombosGeneralOne('productoPrecioList');
//            $('#productoPrecioList').selectmenu('destroy');
            $('#productoPrecioList').selectmenu('disabled',true);
         }
         $('#productoPrecioList').selectmenu('setValue', $("#cdProductoSeleccionado").val());
      }
   } catch(e) {
      jsError('successProductos', e);
   }
}

function resetProductoPrecioOption(label, value, disabled){
   document.getElementById('productoPrecioList').options.length=0;
   document.getElementById('productoPrecioList')[0]= new Option(label,value);
   $('#productoPrecioList').selectmenu('destroy');
   $('#productoPrecioList').selectmenu({style:'dropdown',  width : $('#proveedorProductoPrecioList').attr("width")}).selectmenu('disabled',true);
}

function errorProductos(cod, des){
   jsError('errProducto', des);
}

function saveProductoPrecio() {
	var params   = '';
	var err      = "";
	var tipoModificacion = $("#tipoModificacion").val();
	
	if (tipoModificacion == "D") {
		confirmarDeleteProductoPrecio();
	} else {
		if (tipoModificacion == "E") {
			params += 'opcion=2';
		} else {
			params += 'opcion=1';
		}
		params += '&cdProveedor='+$('#proveedorProductoPrecioList').val();
		params += '&cdProducto='+$('#productoPrecioList').val();
		params += '&fhDesde='+dateFormat_JsToService($("#fhDesdeProdu").val());
		params += '&fhHasta='+dateFormat_JsToService($("#fhHastaProdu").val());
		params += '&nuPrecioUniVal='+$('#nuPrecioUniVal').val();
		params += '&stHabilitado='+$("#habilitadoProductoPrecioList").val();
		
		if ($('#proveedorProductoPrecioList').val() == "0") {
			err += "El Proveedor es obligatorio\n";
		}
		if ($('#productoPrecioList').val() == "0") {
			err += "El Producto es obligatorio\n";
		}
		if (!validateDate($("#fhDesdeProdu").val())) {
			err += "La fecha Desde es invalida\n";
		} 
		if (!validateDate($("#fhHastaProdu").val())) {
			err += "La fecha Hasta es invalida\n";
		} 
		if (validateDate($("#fhDesdeProdu").val()) && validateDate($("#fhHastaProdu").val())) {
			var fd_  = $("#fhDesdeProdu").val();
			var fde = fd_.substr(6,4)+fd_.substr(3,2)+fd_.substr(0,2);
			
			var fh_  = $("#fhHastaProdu").val();
			var fha  = fh_.substr(6,4)+fh_.substr(3,2)+fh_.substr(0,2);
			
			if (fde > fha) {
				err += "La fecha Desde no debe ser mayor a la fecha Hasta\n";
			} 
		}
		if ($.trim($('#nuPrecioUniVal').val()) == '') {
			err += "El Precio es obligatorio\n";
		}else{
			if($('#nuPrecioUniVal').val() > 99999999999.9999){	
				err += "El Precio excede el valor m\u00e1ximo \n";		
			}
		}
			
		if ($.trim($("#habilitadoProductoPrecioList").val()) == '') {
			err += "El Estado es obligatorio\n";
		}
		
		if (err != "") {
			alert("Verifique los datos ingresados\n\n" + err);
		} else {
			callJsonAction('saveProductoPrecio.action', params, 'successSaveProductoPrecio','errorSaveProductoPrecio');
		}
	}
}

function successSaveProductoPrecio(json){	 
	if (isSuccessResult(json.result.errorCode)){		
		setSuccessMsg($('#producto_responseMsgs'),"Editado Exitosamente");
		$('#producto_responseMsgs').show();
		$('#dialogEditProductoPrecio').dialog('close');
		resetProductoPrecioForm();
		search();
		cleanDataConfirmation();
	} else {		
		setErrorMsg($('#prod_precio_responseMsgs'),json.result.errorDesc);
		$('#prod_precio_responseMsgs').show();
	}	
}

function errorSaveProductoPrecio(errorCode, errorDesc){
	setErrorMsg($('#producto_responseMsgs'),errorDesc);
	$('#producto_responseMsgs').show();	
	$('#dialogEditProductoPrecio').dialog('close');
	resetProductoPrecioForm();
	cleanDataConfirmation();
}

function confirmarDeleteProductoPrecio(){	
	var params = '';
	params += 'cdProveedor='+$('#proveedorProductoPrecioList').val();
	params += '&cdProducto='+$('#productoPrecioList').val();	
	params += '&fhDesde='+dateFormat_JsToService($("#fhDesde").val());	
	
	if (confirm('Confirma la baja del Precio del Producto?')) {
		callJsonAction('deleteProductoPrecio.action', params, 'successDeleteProductoPrecio', 'errorDeleteProductoPrecio');
	}
}

function successDeleteProductoPrecio(json){	 
	if (isSuccessResult(json.result.errorCode)){		
		setSuccessMsg($('#producto_responseMsgs'),"Eliminado Exitosamente");
		$('#producto_responseMsgs').show();
		$('#dialogEditProductoPrecio').dialog('close');
		search();
	} else {		
		setErrorMsg($('#producto_responseMsgs'),json.result.errorDesc);
		$('#producto_responseMsgs').show();
	}	
	cleanDataConfirmation();
}

function errorDeleteProductoPrecio(errorCode, errorDesc){
	setErrorMsg($('#producto_responseMsgs'),errorDesc);
	$('#producto_responseMsgs').show();	
	$('#dialogEditProductoPrecio').dialog('close');
	cleanDataConfirmation();
}

function cleanDataConfirmation(){
//	$("#cdProveedorProductoPrecio").val('');
//	$("#cdProductoPrecio").val('');
	$("#fhDesdeProdu").val('');
	$("#fhHastaProdu").val('');
	$("#nuPrecioUniVal").val('');
	$('#stHabilitadoProductoPrecio').val('');
}

function cleanMsgConfirmation(){
	$('#producto_responseMsgs').hide();
	$('#producto_responseMsgs').val('');
	$('#msgEspera').hide();
	$('#msgEspera').val('');
	$('#msgConfirmacion').hide();
	$('#msgConfirmacion').val('');
	$('#prod_precio_responseMsgs').hide();
	$('#prod_precio_responseMsgs').val('');		
}

function dateFormat_serviceToJs(fecha){
	var value = "";
	if (fecha != null && fecha != "") {
		value = fecha.substr(8,2)+'/'+fecha.substr(5,2)+'/'+fecha.substr(0,4);
	}
	return value;
}


