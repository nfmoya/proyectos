var row;
$(document).ready(function(){
	destroyCombos();
	
	initProductoAgrupadoDialog();
//	cleanDataConfirmation();
	
	search();	
});

function search() {
	
	$('#agregarGrant').val('S');
	limpiarGrilla('gridProductosAgrupadosId', 'gridProductosAgrupadosPager', 'gridProductosAgrupados');

	$('#productosAgrupadosGrid').show();
	
	loadAgrupadosGrid();
}

function initProductoAgrupadoDialog(){
	$("#dialogEditProductoAgrupado").dialog({
		bgiframe: true,
		autoOpen: false,
		modal: true,
		width: 650,
		
		close: reloadProductosAgrupadosGrid, 
		buttons: [ 
			{   id: 'guardar-button',
				text: "Grabar",
		        click: function() {
		        	try {
						saveProductoAgrupado();
					} catch(e) {
						jsError('saveSector(...)',e.message);
					}
				}
		    },
			{   id: 'cancelar-button',
   	            text: "Cancelar",
   	        	click: function() {
   	     		$('#producto_diag_responseMsgs').hide();
//					resetProductoAgrupadoForm();
		    		cleanDataConfirmation();
	            	$(this).dialog('close');
            	}
	        }
		]
	});
}

// GRILLA
function loadAgrupadosGrid(){
	var params = '';
	params += 'cdProveedor=' + $('#filtroProveedorList').val();
	params += '&cdProductoOrig=' + $('#filtroProductoOrigList').val();
	params += '&cdProducto=' + $('#filtroProductoList').val();
	params += '&stHabilitado=' + $('#filtroHabilitadoList').val();	
//	alert(params);
	try {
		showGrid({
			id : 'gridProductosAgrupadosId',
			idPager : 'gridProductosAgrupadosPager',
			url : 'JsonProductosAgrupadosList.action?'+params,
			colNames : [ 'Proveedor', 'Prod Orig', 'Producto', 'Item', 'Grupo', 'Habilitado' ],
			colModel : [ 
				{name : 'cdProveedor'    , index : 'id', width : 100, align : 'left', hidden : false }, 
				{name : 'cdProductoOrig' , width :  90, align : 'left', hidden : false }, 
				{name : 'cdProducto'     , width :  90, align : 'left', hidden : false }, 
				{name : 'desItem'        , width : 150, align : 'left', hidden : false }, 
				{name : 'desGrupo'       , width : 260, align : 'left', hidden : false }, 
				{name : 'stHabilitado'   , width : 100, align : 'center', hidden : false }
			],
			rowNum : 10,
			rowList : [ 10, 15, 20 ],
			sortName : 'cdProductoOrig',
			caption : "Productos Agrupados ",
			height : '100%',
			width : '100%',
			multiselect: false,
			loadonce : true,
			loadCompleteFunc : '',
            editurl: 'clientArray',
			shrinkToFit: true,
	        ondblClickRow: function(rowid, iRow, iCol, e) {
					viewDialogEditProducto(rowid);
	        }
		});
		$('.ui-jqgrid-title').css('width', '98%');
		addButtonsToProductoGrid();
	} catch(e) {
		jsError('loadProductosAgrupadosGrid(...)', e);
	}
}

// AGREGA BOTONERA DE LA GRILLA
function addButtonsToProductoGrid(){
	if($('#addGrant').val()=='S'){
		var title = 'Agregar Producto Agrupado';
		$('#gridProductosAgrupadosId').navButtonAdd('#gridProductosAgrupadosPager', {
			caption: 'Agregar',
			buttonicon: 'ui-icon ui-icon-plus',
			onClickButton: function() {
				try {
					$('#gridProductosAgrupadosId').resetSelection();
					processEditProductoAgrupado(0, false, 'Ingresar Producto', 'A');
				} catch (e) {
					jsError('loadProductosAgrupadosGrid(...)', e);
				}
			}, 
			position:'last', 
			title:title
		});
	}
	if($('#editGrant').val()=='S') {
		var title = 'Editar Producto Agrupado';
		$('#gridProductosAgrupadosId').navButtonAdd('#gridProductosAgrupadosPager', {
			caption: 'Editar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromProductoGrid(); 
					if (rowid != null) {
						processEditProductoAgrupado(rowid, false, 'Editar Producto Agrupado', 'E');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadProductosAgrupadosGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
	}
	if($('#deleteGrant').val()=='S') {
		var title = 'Eliminar Producto Agrupado';
		$('#gridProductosAgrupadosId').navButtonAdd('#gridProductosAgrupadosPager', {
			caption: 'Eliminar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromProductoGrid(); 
					if (rowid != null) {
						processEditProductoAgrupado(rowid, false, 'Eliminar Producto Agrupado', 'D');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadProductosAgrupadosGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
	}		
	if($('#selectGrant').val()=='S') {
		var title = 'Consultar Producto Agrupado';
		$('#gridProductosAgrupadosId').navButtonAdd('#gridProductosAgrupadosPager', {
			caption: 'Consultar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromProductoGrid(); 
					if (rowid != null) {
						processEditProductoAgrupado(rowid, false, 'Consultar Producto Agrupado', 'C');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadProductosAgrupadosGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
	}		
	
}

// FUNCTIONES GENERALES GRILLA
function reloadProductosAgrupadosGrid(){
	$('#gridProductosAgrupadosId').trigger('reloadGrid');
}

function getSelRowFromProductoGrid() { 
	return $("#gridProductosAgrupadosId").getGridParam('selrow');
}

function resetProductoAgrupadoForm() {
	$(':input','#dialogEditProducto').not('input[type=hidden], :button').val('').removeAttr('checked').removeAttr('selected');
}

var $proveedor = $('#proveedorProductoList');
$proveedor.change(function(){
   recargarProducto($('#proveedorProductoList').val());
   if ($('#proveedorProductoList').val()=="0"){
       resetProductoOption(' Sin Producto ', '0', true);	
       resetOriginalOption(' Sin Producto ', '0', true);
   }
});

//EDIT PRODUCTO
function processEditProductoAgrupado(rowid, tipo, sTitle, tipoAcceso) {
	cleanDataConfirmation();
	cleanMsgConfirmation();
	cleanCombos();
	
	$("#tipoModificacion").val(tipoAcceso);
	
    if (tipoAcceso == 'A') {
		$("#proveedorProductoList").selectmenu('disabled',false);
		$("#originalList").selectmenu('disabled',false);
		$("#productoList").selectmenu('disabled',false);
		$("#desItem").attr('disabled',false).removeClass("ui-state-disabled"); 
		$("#desGrupo").attr('disabled',false).removeClass("ui-state-disabled");
		$("#habilitadoProductoList").selectmenu('disabled',false);
    }
    if (tipoAcceso != 'A') {
    	var cdProveedor    = $('#gridProductosAgrupadosId').jqGrid('getCell', rowid, 'cdProveedor');    	
		var cdProductoOrig = $.trim($('#gridProductosAgrupadosId').jqGrid('getCell', rowid, 'cdProductoOrig'));
		var cdProducto     = $.trim($('#gridProductosAgrupadosId').jqGrid('getCell', rowid, 'cdProducto'));
		var desItem        = $('#gridProductosAgrupadosId').jqGrid('getCell', rowid, 'desItem');
		var desGrupo       = $('#gridProductosAgrupadosId').jqGrid('getCell', rowid, 'desGrupo');
		var stHabilitado   = $('#gridProductosAgrupadosId').jqGrid('getCell', rowid, 'stHabilitado');

		recargarProducto(cdProveedor);

		$("#proveedorProductoList").selectmenu('setValue', cdProveedor);
		$("#originalList").selectmenu('setValue', cdProductoOrig);
		$("#productoList").selectmenu('setValue', cdProducto);
		$("#desItem").val(desItem);
		$("#desGrupo").val(desGrupo);
		$("#habilitadoProductoList").selectmenu('setValue', stHabilitado);
		
	    console.log(tipoAcceso);
		    
		$("#proveedorProductoList").selectmenu('disabled',true);
		$("#originalList").selectmenu('disabled',true);
		$("#productoList").selectmenu('disabled',true);
	    if (tipoAcceso != 'E' ) {
			$("#desItem").attr('disabled',true).addClass("ui-state-disabled");
			$("#desGrupo").attr('disabled',true).addClass("ui-state-disabled");
			$("#habilitadoProductoList").selectmenu('disabled',true);
	    } else {
	    	$("#productoList").selectmenu('disabled',false);
    		$("#desItem").attr('disabled',true).removeClass("ui-state-disabled");
    		$("#desGrupo").attr('disabled',false).removeClass("ui-state-disabled");
			$("#habilitadoProductoList").selectmenu('disabled',false);
	    }  	    
    }
	if (tipoAcceso == 'C') {
		$(":button:contains('Grabar')").button().hide();				
	} else {
		$(":button:contains('Grabar')").button().show();				
	}
	$('#dialogEditProductoAgrupado').dialog('option','title',sTitle);
	$('#dialogEditProductoAgrupado').dialog('open');
}

function saveProductoAgrupado() {
	var params = '';
	var confirma = "S";
	var tipoModificacion = $("#tipoModificacion").val();

	if (tipoModificacion == "D") {
		confirmarDeleteProductoAgrupado();
	} else {
		if (tipoModificacion == "E") {
			params += 'opcion=2';
		} else {
			params += 'opcion=1';		
		}
		params += '&cdProveedor='+$('#proveedorProductoList').val();
		params += '&cdProductoOrig='+$('#originalList').val();
		params += '&cdProducto='+$('#productoList').val();
		params += '&desItem='+$('#desItem').val();
		params += '&desGrupo='+$('#desGrupo').val();
		params += '&stHabilitado='+$("#habilitadoProductoList").val();
		if (tipoModificacion == "E") {
			if (!(confirm('Confirma la modificaci\u00f3n del Producto Agrupado?'))) {
				confirma = "N";
			}
		}
		if (confirma == "S") {
			var err = "";
			if ($("#proveedorProductoList").val() == 0) {
				err += "El Proveedor es obligatorio\n";
			}
			if ($("#originalList").val() == 0) {
				err += "El Producto Original es obligatorio\n";
			}
			if ($("#productoList").val() == 0) {
				err += "El Producto es obligatorio\n";
			}
			if ($.trim($("#desItem").val()) == '') {
				err += "La descrici\u00f3nn del Item es obligatoria\n";
			}
			if ($.trim($("#desGrupo").val()) == '') {
				err += "La descrici\u00f3nn del Grupo es obligatoria\n";
			}
			if (err != "") {
				alert("Verifique los datos ingresados\n\n" + err);
			} else {
				callJsonAction('saveProductoAgrupado.action', params, 'successSaveProductoAgrupado','errorSaveProductoAgrupado');
			}
		}
	}
}

function successSaveProductoAgrupado(json){	
	if (isSuccessResult(json.result.errorCode)){
		
		$.ajax({//recarga el combo de sectores
		    'type': 'POST',
		    'url': 'ABMCProductosAgrupados".action',
		    'data': "",
		    'dataType': 'text',
		    'success': function(data) {
//		    	$("#sectoresFilter").html("");
//		    	$("#sectoresFilter").html(data);
		    	setSuccessMsg($('#producto_responseMsgs'),"Editado Exitosamente");
				$('#producto_responseMsgs').show();
				$('#dialogEditProductoAgrupado').dialog('close');
		    
		    },
		    'error': function(e) {

		    }
		});
		destroyCombos();
		resetProductoAgrupadoForm();
		search();
		cleanDataConfirmation();
		search();
	} else {		
		setErrorMsg($('#producto_diag_responseMsgs'),json.result.errorDesc);
		$('#producto_diag_responseMsgs').show();
	}	
	cleanDataConfirmation();
	
}


function errorSaveProductoAgrupado(errorCode, errorDesc){
	setErrorMsg($('#producto_responseMsgs'),errorDesc);
	$('#producto_responseMsgs').show();	
	$('#dialogEditProductoAgrupado').dialog('close');
	resetProductoAgrupadoForm();
	cleanDataConfirmation();
}

function confirmarDeleteProductoAgrupado(){	
	var params = '';
	params += 'cdProveedor='+$("#proveedorProductoList").val();
	params += '&cdProductoOrig='+$("#originalList").val();
//alert(params);	
	if (confirm('Confirma la baja del Producto?')) {
		callJsonAction('deleteProductoAgrupado.action', params, 'successDeleteProductoAgrupado', 'errorDeleteProductoAgrupado');
	}
}

function successDeleteProductoAgrupado(json){	 
	if (isSuccessResult(json.result.errorCode)){		
		setSuccessMsg($('#producto_responseMsgs'),"Eliminado Exitosamente");
		$('#producto_responseMsgs').show();
		$('#dialogDeleteProductoAgrupado').dialog('close');
		search();
	} else {		
		setErrorMsg($('#sector_responseMsgs'),json.result.errorDesc);
		$('#sector_responseMsgs').show();
	}	
	cleanDataConfirmation();
}

function errorDeleteProductoAgrupado(errorCode, errorDesc){
	setErrorMsg($('#producto_responseMsgs'),errorDesc);
	$('#producto_responseMsgs').show();	
	$('#dialogDeleteProductoAgrupado').dialog('close');
	cleanDataConfirmation();
}

function cleanDataConfirmation(){
	$("#cdProveedor").val('');
	$("#cdProductoOrig").val('');
	$("#cdProducto").val('');
	$("#stHabilitado").val('');
	$("#desItem").val('');
	$("#desGrupo").val('');
}
	
function cleanMsgConfirmation(){
	$('#producto_responseMsgs').hide();
	$('#producto_responseMsgs').val('');
	$('#msgEspera').hide();
	$('#msgEspera').val('');
	$('#msgConfirmacion').hide();
	$('#msgConfirmacion').val('');
	$('#producto_diag_responseMsgs').hide();
	$('#producto_diag_responseMsgs').val('');
}

function dateFormat_serviceToJs(fecha){
	var value = "";
	if (fecha != null && fecha != "") {
		value = fecha.substr(8,2)+'/'+fecha.substr(5,2)+'/'+fecha.substr(0,4);
	}
	return value;
}

function destroyCombos() {
	destroyCombosGeneralOne('filtroProveedorList');
	destroyCombosGeneralOne('filtroProductoOrigList');
	destroyCombosGeneralOne('filtroProductoList');
	destroyCombosGeneralOne('filtroHabilitadoList');
}	

function cleanCombos() {
	$('#proveedorProductoList').selectmenu('destroy');	
	$('#proveedorProductoList').selectmenu({style:'dropdown', width:'300px'});
	$('#originalList').selectmenu('destroy');	
    $('#originalList').selectmenu({style:'dropdown', maxHeight:'180', width:'300px'});	
	$('#productoList').selectmenu('destroy');	
    $('#productoList').selectmenu({style:'dropdown', maxHeight:'180', width:'300px'});	
	$('#habilitadoProductoList').selectmenu('destroy');	
	$('#habilitadoProductoList').selectmenu({style:'dropdown', width:'200px'});
}

function recargarProducto(cdProveedor) {
//alert("...");	
   callJsonActionPost("comboProductosAgrupados.action","cdProveedor="+cdProveedor, "successProductos" , "errorProductos");
}

function successProductos(jsonData){
   try {
      document.getElementById('productoList').options.length=0;
      document.getElementById('originalList').options.length=0;
      $('#productoList').selectmenu('destroy');
      $('#originalList').selectmenu('destroy');
      
      if (jsonData.ProductoList!=undefined) {
         resetProductoOption(' Sin Producto ', '0', true);	
         resetOriginalOption(' Sin Producto ', '0', true);	
         
         if (jsonData.ProductoList.length>0) {
    	    for (var i=0;i<jsonData.ProductoList.length;i++) {
    		    document.getElementById('productoList')[document.getElementById('productoList').options.length] = new Option((jsonData.ProductoList[i]).desc, jsonData.ProductoList[i].cod);
    	    }    	    
            $('#productoList').selectmenu('destroy');	
            $('#productoList').selectmenu({style:'dropdown', maxHeight:'180', width:'250px'});
     	    for (var i=0;i<jsonData.ProductoListOrig.length;i++) {
     		    document.getElementById('originalList')[document.getElementById('originalList').options.length] = new Option((jsonData.ProductoListOrig[i]).desc, jsonData.ProductoListOrig[i].cod);
     	    }
            $('#originalList').selectmenu('destroy');	
            $('#originalList').selectmenu({style:'dropdown', maxHeight:'180', width:'250px'}); 
         }     
       }
   } catch(e) {
//	    jsError('errorPeriodos', e);
   }
}

function errorProductos(cod, des){
//   jsError(cod, des);
}

function resetProductoOption(label, value, disabled){
   document.getElementById('productoList').options.length=0;
   document.getElementById('productoList')[0]= new Option(label,value);
   $('#productoList').selectmenu('destroy');
   $('#productoList').selectmenu({style:'dropdown', maxHeight:'180', width:'250px'}).selectmenu('disabled',true);
//	$('#habilitadoProductoList').selectmenu('destroy');	
//	$('#habilitadoProductoList').selectmenu({style:'dropdown', width:'200px'});   
}

function resetOriginalOption(label, value, disabled){
   document.getElementById('originalList').options.length=0;
   document.getElementById('originalList')[0]= new Option(label,value);
   $('#originalList').selectmenu('destroy');
   $('#originalList').selectmenu({style:'dropdown', maxHeight:'180', width:'250px'}).selectmenu('disabled',true);
//	$('#habilitadoProductoList').selectmenu('destroy');	
//	$('#habilitadoProductoList').selectmenu({style:'dropdown', width:'200px'});   
}
