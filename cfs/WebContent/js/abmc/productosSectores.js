$(document).ready(function(){
//    destroyCombosGeneralAll();

	$.ajaxSetup({
      cache : false,
      async : false
  });
//	
//	$("#fhDesde").datepicker({
//		changeMonth: true,
//		changeYear: true,
//		regional: 'es',
//		yearRange: 'c-100:c',
//		dateFormat: 'dd/mm/yy',
//		showAnim: '',
//		showOn: "button",
//		duration: 0
//	}).next('button').text('&nbsp;').addClass('ui-button-text').button({icons:{primary : 'ui-icon ui-icon-calendar'}}).removeClass().addClass('ui-button ui-widget ui-state-default ui-button-icon-only ui-corner-right ui-button-icon');
//
//	$("#fhHasta").datepicker({
//		changeMonth: true,
//		changeYear: true,
//		regional: 'es',
//		yearRange: 'c-100:c',
//		dateFormat: 'dd/mm/yy',
//		showAnim: '',
//		showOn: "button",
//		duration: 0
//	}).next('button').text('&nbsp;').addClass('ui-button-text').button({icons:{primary : 'ui-icon ui-icon-calendar'}}).removeClass().addClass('ui-button ui-widget ui-state-default ui-button-icon-only ui-corner-right ui-button-icon');
	initialiseFiltroProductoSector();
	initProductoSectorDialog();
	cleanDataConfirmation();
//	destroyCombosGeneralAll();
	
	search();
});
	
function search() {
	$('#agregarGrantPS').val('S');
	limpiarGrilla('gridProductosSectoresId', 'gridProductosSectoresPager', 'gridProductosSectores');

	$('#productosSectoresGrid').show();
	$('#botonera').show();
	$('#buttonCancelar').button({
		icons : {
			secondary : ""
		}
	}).click(function() {
		resetProductoPrecioForm();
		cleanDataConfirmation();

	});
	loadProductosSectoresGrid();
}

function initProductoSectorDialog(){
	$("#dialogEditProductoSector").dialog({
		bgiframe: true,
		autoOpen: false,
		modal: true,
		width: 550,
		
		close: reloadProductosSectoresGrid, 
		buttons: [ 
			{   id: 'guardar-button',
				text: "Grabar",
			    click: function() {
			    	try {
				    	saveProductoSector();
					} catch(e) {
						jsError('saveProductoSector(...)',e.message);
					}
				}
			},
 	        {   id: 'cancelar-button',
		   	    text: "Cancelar",
		   	    click: function() {
 	        		$('#prod_sector_responseMsgs').hide();
		   	   		resetProductoSectorForm();
		   			cleanDataConfirmation();
		        	$(this).dialog('close');
	            }
		    }
		]
	});
}

//GRILLA
function loadProductosSectoresGrid(){
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
			id : 'gridProductosSectoresId',
			idPager : 'gridProductosSectoresPager',
			url : 'JsonProductosSectoresList.action?'+params,
			colNames : [ 'Proveedor', 'Producto', 'Sector', 'Nombre', 'Habilitado' ],
			colModel : [ 
				{name : 'cdProveedor'        , width : 140, align : 'left', hidden : true }, 
				{name : 'cdProducto'         , index : 'id', width : 140, align : 'left', hidden : true }, 
				{name : 'cdSector'           , width : 160, align : 'center', hidden : false }, 
				{name : 'nbSector'           , width : 240, align : 'center', hidden : false }, 
				{name : 'stHabilitado'       , width : 160, align : 'center', hidden : false }
			],
			rowNum : 10,
			rowList : [ 10, 15, 20 ],
			sortName : 'cdProducto',
			caption : "Relaci&oacute;n Producto-Sectores de Control",
			height : '100%',
			width : 720,
			multiselect: false,
			loadonce : true,
			loadCompleteFunc : '',
          editurl: 'clientArray'
		});
		$('.ui-jqgrid-title').css('width', '98%');
		addButtonsToProductoSectorGrid();
	} catch(e) {
		jsError('loadProductosSectoresGrid(...)', e);
	}
}

//AGREGA BOTONERA DE LA GRILLA
function addButtonsToProductoSectorGrid(){
	if($('#addGrantPS').val()=='S'){
		var title = 'Agregar Sector Producto';
		$('#gridProductosSectoresId').navButtonAdd('#gridProductosSectoresPager', {
			caption: 'Agregar',
			buttonicon: 'ui-icon ui-icon-plus',
			onClickButton: function() {
				try {
					$('#gridProductosSectoresId').resetSelection();
					processEditProductoSector(0, false, 'Ingresar Sector Producto', 'A');

				} catch (e) {
					jsError('loadProductosSectoresGrid(...)', e);
				}
			}, 
			position:'last', 
			title:title
		});
	}
	if($('#editGrantPS').val()=='S') {
		var title = 'Editar Sector Producto';
		$('#gridProductosSectoresId').navButtonAdd('#gridProductosSectoresPager', {
			caption: 'Editar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromProductoSectorGrid(); 
					if (rowid != null) {
						processEditProductoSector(rowid, false, 'Editar Sector Producto', 'E');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadProductosSectoresGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
	}
	if($('#deleteGrantPS').val()=='S') {
		var title = 'Eliminar Sector Producto';
		$('#gridProductosSectoresId').navButtonAdd('#gridProductosSectoresPager', {
			caption: 'Eliminar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromProductoSectorGrid(); 
					if (rowid != null) {
						processEditProductoSector(rowid, false, 'Eliminar Sector Producto', 'D');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadProductosSectoresGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
	}
	if($('#selectGrantPS').val()=='S') {
		var title = 'Consulta Sector Producto';
		$('#gridProductosSectoresId').navButtonAdd('#gridProductosSectoresPager', {
			caption: 'Consultar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromProductoSectorGrid(); 
					if (rowid != null) {
						processEditProductoSector(rowid, false, 'Consultar Sector Producto', 'C');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadProductosSectoresGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
	}
}

//FUNCTIONES GENERALES GRILLA
function reloadProductosSectoresGrid(){
	$('#gridProductosSectoresId').trigger('reloadGrid');
}

function getSelRowFromProductoSectorGrid() { 
	return $("#gridProductosSectoresId").getGridParam('selrow');
}

function resetProductoSectorForm() {
	$(':input','#dialogEditProductoSector').not('input[type=hidden], :button').val('').removeAttr('checked').removeAttr('selected');
}

//EDIT PRODUCTO
function processEditProductoSector(rowid, tipo, sTitle, tipoAcceso) {
	cleanDataConfirmation();
	cleanMsgConfirmation();
	

	destroyCombosGeneralOne('proveedorProductoSectorList');
	destroyCombosGeneralOne('productoSectorList');
	destroyCombosGeneralOne('habilitadoProductoSectorList');
	destroyCombosGeneralOne('sectorList');	
	
	$("#tipoModificacion").val(tipoAcceso);
	
  if (tipoAcceso == 'A') {
  	$('#proveedorProductoSectorList').selectmenu('setValue', '0');
  	$('#productoSectorList').selectmenu('setValue', '0');
  	$('#sectorList').selectmenu('setValue', '0');
	    $('#proveedorProductoSectorList').selectmenu('disabled',false);
	    $('#productoSectorList').selectmenu('disabled',false);
	    $('#sectorList').selectmenu('disabled',false);
      $('#habilitadoProductoSectorList').selectmenu('disabled',false);
		$("#proveedorProductoSectorList").selectmenu('setValue', $('#cdProveedorSeleccionado').val()); 
	
      $('#proveedorProductoSectorList').selectmenu('disabled',true);
      recargarProducto($('#proveedorProductoSectorList').val());
  }
  if (tipoAcceso != 'A') {
		var cdProveedor       = $('#gridProductosSectoresId').jqGrid('getCell', rowid, 'cdProveedor');
//		var cdProducto        = $('#gridProductosSectoresId').jqGrid('getCell', rowid, 'cdProducto');
		var cdSector          = $('#gridProductosSectoresId').jqGrid('getCell', rowid, 'cdSector');
		var stHabilitado      = $('#gridProductosSectoresId').jqGrid('getCell', rowid, 'stHabilitado');
		
		$('#proveedorProductoSectorList').selectmenu('setValue', cdProveedor);
		$("#sectorList").selectmenu('setValue', cdSector);
		$("#cdSectorOld").val(cdSector);
		$('#habilitadoProductoSectorList').selectmenu('setValue', stHabilitado);	
		
		recargarProducto(cdProveedor);
		
	    $('#proveedorProductoSectorList').selectmenu('disabled',true);
	    $('#productoSectorList').selectmenu('disabled',true);	    
	    
	    if (tipoAcceso == 'C' || tipoAcceso == 'D' ) {
	        $('#habilitadoProductoSectorList').selectmenu('disabled',true);
	        $('#sectorList').selectmenu('disabled',true);
	    } else {
	        $('#habilitadoProductoSectorList').selectmenu('disabled',false);
	        $('#sectorList').selectmenu('disabled',false);
	    }
  }
	if (tipoAcceso == 'C') {
		$(":button:contains('Grabar')").button().hide();				
	} else {
		$(":button:contains('Grabar')").button().show();				
	}
 	$('#dialogEditProductoSector').dialog('option','title',sTitle);
  $('#dialogEditProductoSector').dialog('open');
}

function initialiseFiltroProductoSector(){
 var $proveedor = $('#proveedorProductoSectorList');
 $proveedor.change(function(){
    hideCommonDataElements();
    recargarProducto($proveedor.val());
 });
 onchangeOptions('proveedorProductoSectorList');
}

function onchangeOptions(selectorId){
 $("#"+selectorId).change(function(){
    hideCommonDataElements();
 });
}

function hideCommonDataElements(){
 if ($('#proveedorProductoSectorList').val()=="0"){
    resetProductoSectorOption(' Sin Producto ', '0', false);	
 }
}

function recargarProducto(cdProveedor) {
  callJsonAction("comboProductosSectores.action","cdProveedor="+cdProveedor,"successProductos","errorProductos");
}

function successProductos(jsonData){
 try {
   document.getElementById('productoSectorList').options.length=0;
   $('#productoSectorList').selectmenu('destroy');
   if (jsonData.ProductoSectorList!=undefined){
 	  resetProductoSectorOption(' Sin Producto ', '0', true);	
           	  
      if (jsonData.ProductoSectorList.length>0){
         for (var i=0;i<jsonData.ProductoSectorList.length;i++){
            document.getElementById('productoSectorList')[document.getElementById('productoSectorList').options.length] = new Option((jsonData.ProductoSectorList[i]).desc, jsonData.ProductoSectorList[i].cod);
         }
       }
             destroyCombosGeneralOne('productoSectorList');//.selectmenu('disabled',true);
        $('#productoSectorList').selectmenu('setValue', $("#cdProductoSeleccionado").val());
      $('#productoSectorList').selectmenu('disabled',true);
   }
//    destroyCombosGeneralOne('productoSectorList');
 } catch(e) {
    jsError('successProductos', e);
 }
}

function resetProductoSectorOption(label, value, disabled){
 document.getElementById('productoSectorList').options.length=0;
 document.getElementById('productoSectorList')[0]= new Option(label,value);
}

function errorProductos(cod, des){
 jsError('errProducto', des);
}

function saveProductoSector() {
	var params   = '';
	var err      = "";
	var tipoModificacion = $("#tipoModificacion").val();
	
	if (tipoModificacion == "D") {
		confirmarDeleteProductoSector();
	} else {
		if (tipoModificacion == "E") {
			params += 'opcion=2';
		} else {
			params += 'opcion=1';		
		}
		params += '&cdProveedor='+$('#proveedorProductoSectorList').val();
		params += '&cdProducto='+$('#productoSectorList').val();
		params += '&cdSector='+$('#sectorList').val();
		params += '&stHabilitado='+$("#habilitadoProductoSectorList").val();
		params += '&cdSectorOld='+$('#cdSectorOld').val();
		
		if ($('#proveedorProductoSectorList').val() == "0") {
			err += "El Proveedor es obligatorio\n";
		}
		if ($('#productoSectorList').val() == "0") {
			err += "El Producto es obligatorio\n";
		}
		if ($('#sectorList').val() == "0") {
			err += "El Sector es obligatorio\n";
		}
		if ($.trim($("#habilitadoProductoSectorList").val()) == '') {
			err += "El Estado es obligatorio\n";
		}
		if (err != "") {
			alert("Verifique los datos ingresados\n\n" + err);
		} else {
			callJsonAction('saveProductoSector.action', params, 'successSaveProductoSector','errorSaveProductoSector');
		}
	}
}

function successSaveProductoSector(json){	 
	if (isSuccessResult(json.result.errorCode)){		
		setSuccessMsg($('#producto_responseMsgs'),"Editado Exitosamente");
		$('#producto_responseMsgs').show();
		$('#dialogEditProductoSector').dialog('close');
		resetProductoSectorForm();
		search();
		cleanDataConfirmation();
		search();
	} else {		
		setErrorMsg($('#prod_sector_responseMsgs'),json.result.errorDesc);
		$('#prod_sector_responseMsgs').show();
	}	
}

function errorSaveProductoSector(errorCode, errorDesc){
	setErrorMsg($('#producto_responseMsgs'),errorDesc);
	$('#producto_responseMsgs').show();	
	$('#dialogEditProductoSector').dialog('close');
	resetProductoSectorForm();
	cleanDataConfirmation();
}

function confirmarDeleteProductoSector(){	
	var params = '';
	params += 'cdProveedor='+$('#bajaCdProveedorProductoSector').val();
	params += '&cdProducto='+$('#bajaCdProductoSector').val();
	params += '&cdSector='+$('#bajaCdSector').val();	
	
	if (confirm('Confirma la baja del Sector del Producto?')) {
		callJsonAction('deleteProductoSector.action', params, 'successDeleteProductoSector', 'errorDeleteProductoSector');
	}
}

function successDeleteProductoSector(json){	 
	if (isSuccessResult(json.result.errorCode)){		
		setSuccessMsg($('#producto_responseMsgs'),"Eliminado Exitosamente");
		$('#producto_responseMsgs').show();
		$('#dialogEditProductoSector').dialog('close');
		search();
	} else {		
		setErrorMsg($('#producto_responseMsgs'),json.result.errorDesc);
		$('#producto_responseMsgs').show();
	}	
	cleanDataConfirmation();
}

function errorDeleteProductoSector(errorCode, errorDesc){
	setErrorMsg($('#producto_responseMsgs'),errorDesc);
	$('#producto_responseMsgs').show();	
	$('#dialogEditProductoSector').dialog('close');
	cleanDataConfirmation();
}

function cleanDataConfirmation(){
//	$("#cdProveedorProductoSector").val('');
	$("#cdProductoSector").val('');
	$("#cdSector").val('');
	$('#stHabilitado').val('');
}

function cleanMsgConfirmation(){
	$('#producto_responseMsgs').hide();
	$('#producto_responseMsgs').val('');
	$('#msgEspera').hide();
	$('#msgEspera').val('');
	$('#msgConfirmacion').hide();
	$('#msgConfirmacion').val('');
	$('#prod_sector_responseMsgs').hide();
	$('#prod_sector_responseMsgs').val('');	
}

function dateFormat_serviceToJs(fecha){
	var value = "";
	if (fecha != null && fecha != "") {
		value = fecha.substr(8,2)+'/'+fecha.substr(5,2)+'/'+fecha.substr(0,4);
	}
	return value;
}

