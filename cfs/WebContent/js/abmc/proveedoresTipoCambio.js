$(document).ready(function(){
	destroyCombos();
	$.ajaxSetup({
        cache : false
    });
	initialiseFiltroPeriodoTipoCambio();
	initProveedorTipoCambioDialog();
	cleanDataConfirmation();

	search();
});

function search() {
	$('#agregarGrantTC').val('S');
	limpiarGrilla('gridProveedoresTipoCambioId', 'gridProveedoresTipoCambioPager', 'gridProveedoresTipoCambio');

	$('#proveedoresTipoCambioGrid').show();
	$('#botonera').show();
	$('#buttonCancelar').button({
		icons : {
			secondary : ""
		}
	}).click(function() {
		resetProveedorTipoCambioForm();
		cleanDataConfirmation();
	});
	loadProveedoresTipoCambioGrid();
}

function initProveedorTipoCambioDialog(){
	$("#dialogEditProveedorTipoCambio").dialog({
		bgiframe: true,
		autoOpen: false,
		modal: true,
		width: 550,
		
		close: reloadProveedoresTipoCambioGrid, 
		buttons: [ 
			{   id: "grabar-button",
				text: "Grabar",
		        click: function() {
		        	try {
						saveProveedorTipoCambio();
					} catch(e) {
						jsError('saveProveedorTipoCambio(...)',e.message);
					}
				}
		    },
   	        {   id:"cancelar-button",
		    	text: "Cancelar",
   	        	click: function() {
   	        		$('#prov_tipocambio_responseMsgs').hide();
//   	        	setErrorMsg($('#prov_valor_responseMsgs'),json.result.errorDesc);
   	        		resetProveedorTipoCambioForm();
   	     			cleanDataConfirmation();
	            	$(this).dialog('close');
            	}
	        }
		]
	});
}

// GRILLA
function loadProveedoresTipoCambioGrid(){
	var params = '';
	if (($("#cdProveedorSeleccionado").val() != '') && ($("#cdProveedorSeleccionado").val() != undefined)) {
	   params = 'cdProveedor='+$("#cdProveedorSeleccionado").val();
	   if (($("#cdPeriodoFactSeleccionado").val() != '') && ($("#cdPeriodoFactSeleccionado").val() != undefined)) {
		   params += '&cdPeriodo='+$("#cdPeriodoFactSeleccionado").val();
	   }
	} else {
       params = 'cdProveedor=999&cdPeriodo=999';
	}
//alert(params);
	try {
		showGrid({
			id : 'gridProveedoresTipoCambioId',
			idPager : 'gridProveedoresTipoCambioPager',
			url : 'JsonProveedoresTipoCambioList.action?'+params,		
			colNames : [ 'C&oacute;d Proveedor', 'Per&iacute;odo', 'Cod Moneda', 'Moneda', '+- D&iacute;as', 'Cotizaci&oacute;n', 'Habilitado' ],
			colModel : [ 
				{name : 'cdProveedor'    , index : 'id', width : 100, align : 'left', hidden : true }, 
				{name : 'cdPeriodoFact'  , width : 100, align : 'left'  , hidden : true  }, 
				{name : 'cdMoneda'       , width :  80, align : 'left'  , hidden : true  }, 
				{name : 'nbMoneda'       , width :  80, align : 'left'  , hidden : false }, 
				{name : 'nuDias'         , width : 100, align : 'center', hidden : false }, 
				{name : 'cdCotizacion'   , width : 100, align : 'center', hidden : false }, 
				{name : 'stHabilitado'   , width :  80, align : 'center', hidden : false }
			],
			rowNum : 10,
			rowList : [ 10, 15, 20 ],
			sortName : 'cdProveedor',
			caption : "Tipos de Cambio",
			height : '100%',
			width : 720,
			multiselect: false,
			loadonce : true,
			loadCompleteFunc : '',
            editurl: 'clientArray',
			shrinkToFit: true
		});
		$('.ui-jqgrid-title').css('width', '98%');
		addButtonsToProveedorTipoCambioGrid();
	} catch(e) {
		jsError('loadProveedoresTipoCambioGrid(1)', e);
	}
}

// AGREGA BOTONERA DE LA GRILLA
function addButtonsToProveedorTipoCambioGrid(){
	if($('#addGrantTC').val()=='S') {
		var title = 'Agregar Proveedor';
		$('#gridProveedoresTipoCambioId').navButtonAdd('#gridProveedoresTipoCambioPager', {
			caption: 'Agregar',
			buttonicon: 'ui-icon ui-icon-plus',
			onClickButton: function() {
				try {
					$('#gridProveedoresTipoCambioId').resetSelection();
					processEditProveedorTipoCambio(0, false, 'Ingresar Tipo Cambio', 'A');
				} catch (e) {
					jsError('loadProveedoresTipoCambioGrid(2)', e);
				}
			}, 
			position:'last', 
			title:title
		});
	}
	if($('#editGrantTC').val()=='S') {
		var title = 'Editar Proveedor';
		$('#gridProveedoresTipoCambioId').navButtonAdd('#gridProveedoresTipoCambioPager', {
			caption: 'Editar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromProveedorTipoCambioGrid(); 
					if (rowid != null) {
						processEditProveedorTipoCambio(rowid, false, 'Editar Tipo Cambio','E');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadProveedoresTipoCambioGrid(3)', e);
				}
			},
			position:'last',
			title:title
		});
	}
	if($('#deleteGrantTC').val()=='S') {
		var title = 'Eliminar Proveedor';
		$('#gridProveedoresTipoCambioId').navButtonAdd('#gridProveedoresTipoCambioPager', {
			caption: 'Eliminar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromProveedorTipoCambioGrid(); 
					if (rowid != null) {
						processEditProveedorTipoCambio(rowid, false, 'Eliminar Tipo Cambio', 'D');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadProveedoresTipoCambioGrid(4)', e);
				}
			},
			position:'last',
			title:title
		});
	}
	if($('#selectGrant').val()=='S') {
		var title = 'Consultar Proveedor';
		$('#gridProveedoresTipoCambioId').navButtonAdd('#gridProveedoresTipoCambioPager', {
			caption: 'Consultar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromProveedorTipoCambioGrid(); 
					if (rowid != null) {
						processEditProveedorTipoCambio(rowid, false, 'Consultar Tipo Cambio', 'C');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadProveedoresTipoCambioGrid(5)', e);
				}
			},
			position:'last',
			title:title
		});
	}
}

// FUNCTIONES GENERALES GRILLA
function reloadProveedoresTipoCambioGrid(){
	$('#gridProveedoresTipoCambioId').trigger('reloadGrid');
}

function getSelRowFromProveedorTipoCambioGrid() { 
	return $("#gridProveedoresTipoCambioId").getGridParam('selrow');
}

function resetProveedorTipoCambioForm() {
	$(':input','#dialogEditProveedorTipoCambio').not('input[type=hidden], :button').val('').removeAttr('checked').removeAttr('selected');
}

//EDIT PRODUCTO
function processEditProveedorTipoCambio(rowid, tipo, sTitle, tipoAcceso) {
//	alert("al metodo");
	cleanDataConfirmation();
	cleanMsgConfirmation();
	destroyCombos();
	
	$("#tipoModificacion").val(tipoAcceso);
	
    if (tipoAcceso == 'A') {
		$("#proveedorTipoCambioList").selectmenu('setValue', $('#cdProveedorSeleccionado').val()); 
        $('#proveedorTipoCambioList').selectmenu('disabled',true);
		$("#periodoTipoCambioList").selectmenu('setValue', $('#cdPeriodoFactSeleccionado').val()); 
	    $('#periodoTipoCambioList').selectmenu('disabled',true);
 	    $('#monedaTipoCambioList').selectmenu('disabled',false);
        $("#nuDias").attr('disabled',false).removeClass("ui-state-disabled");
        $("#cotizacionTipoCambioList").selectmenu('disabled',false);
        $("#habilitadoTipoCambioList").selectmenu('disabled',false);
        recargarPeriodo($('#cdProveedorSeleccionado').val(), $('#cdPeriodoFactSeleccionado').val());
    }
	
    if (tipoAcceso != 'A') {	
		var cdProveedor      = $('#gridProveedoresTipoCambioId').jqGrid('getCell', rowid, 'cdProveedor');
		var cdPeriodoFact    = $('#gridProveedoresTipoCambioId').jqGrid('getCell', rowid, 'cdPeriodoFact');
		var cdMoneda         = $('#gridProveedoresTipoCambioId').jqGrid('getCell', rowid, 'cdMoneda');
		var nuDias           = $('#gridProveedoresTipoCambioId').jqGrid('getCell', rowid, 'nuDias');
		var cdCotizacion     = $('#gridProveedoresTipoCambioId').jqGrid('getCell', rowid, 'cdCotizacion');
		var stHabilitado     = $('#gridProveedoresTipoCambioId').jqGrid('getCell', rowid, 'stHabilitado');
		
		$("#proveedorTipoCambioList").selectmenu('setValue', cdProveedor);
		$("#periodoTipoCambioList").selectmenu('setValue', cdPeriodoFact); 
 		$("#monedaTipoCambioList").selectmenu('setValue', cdMoneda);	 		
        $("#nuDias").val(nuDias);
        $("#cotizacionTipoCambioList").selectmenu('setValue', cdCotizacion);
        $("#habilitadoTipoCambioList").selectmenu('setValue', stHabilitado);
//      alert("Antes VAL -"+ $('#monedaTipoCambioList').val());
        recargarPeriodo($('#cdProveedorSeleccionado').val(), $('#cdPeriodoFactSeleccionado').val());	    
//      alert("despues VAL -"+ $('#monedaTipoCambioList').val());
	    $('#proveedorTipoCambioList').selectmenu('disabled',true);
	    $('#periodoTipoCambioList').selectmenu('disabled',true);
	    $('#monedaTipoCambioList').selectmenu('disabled',true);

	    if (tipoAcceso == 'C' || tipoAcceso == 'D' ) {
	        $("#nuDias").attr('disabled',true).addClass("ui-state-disabled");
	        $("#cotizacionTipoCambioList").selectmenu('disabled',true);	        
	        $("#habilitadoTipoCambioList").selectmenu('disabled',true);	        
	    } else {
	        $("#nuDias").attr('disabled',false).removeClass("ui-state-disabled");
	        $("#cotizacionTipoCambioList").selectmenu('disabled',false);	        
	        $("#habilitadoTipoCambioList").selectmenu('disabled',false);	        
	    }
	}
	
	if (tipoAcceso == 'C') {
		$(":button:contains('Grabar')").button().hide();				
	} else {
		$(":button:contains('Grabar')").button().show();				
	}
	$('#dialogEditProveedorTipoCambio').dialog('option','title',sTitle);
	$('#dialogEditProveedorTipoCambio').dialog('open');
}

function initialiseFiltroPeriodoTipoCambio(){
    var $proveedor = $('#cdProveedorSeleccionado');
    var $periodo   = $('#cdPeriodoFactSeleccionado');
    $proveedor.change(function(){
    	hideCommonDataElements();
        recargarPeriodo($proveedor.val(), $periodo.val());
    });
    onchangeOptions('proveedorTipoCambioList');
}

function onchangeOptions(selectorId){
    $("#"+selectorId).change(function(){
    	hideCommonDataElements();
    });
}

function hideCommonDataElements(){
    if ($('#proveedorTipoCambioList').val()=="0"){
	    resetPeriodoOption(' Sin Per&iacute;odo ', '0', false);	
    }
}

function recargarPeriodo(cdProveedor, cdPeriodo) {
    callJsonAction("comboProveedoresPeriodos.action","cdProveedor="+cdProveedor+"&cdPeriodo="+cdPeriodo,"successPeriodos","errorPeriodos");
}	

function successPeriodos(jsonData){		
	try {
		document.getElementById('periodoTipoCambioList').options.length=0;
		$('#periodoTipoCambioList').selectmenu('destroy');
		if (jsonData.ProveedorPeriodoList!=undefined){
			resetPeriodoOption(' Sin Per&iacute;odo ', '0', true);	

			if (jsonData.ProveedorPeriodoList.length>0){
				for (var i=0;i<jsonData.ProveedorPeriodoList.length;i++)
					document.getElementById('periodoTipoCambioList')[document.getElementById('periodoTipoCambioList').options.length] = new Option((jsonData.ProveedorPeriodoList[i]).desc, jsonData.ProveedorPeriodoList[i].cod);
            
				$('#periodoTipoCambioList').selectmenu('destroy');
				$('#periodoTipoCambioList').selectmenu({style:'dropdown', maxHeight:'200', width:'200px'}).selectmenu('disabled',true);
			}
/*         
       var selectobject=document.getElementById("periodoValorList");
       for (var i=0; i<selectobject.length; i++){
       }
       $('#periodoValorList').selectmenu('setValue', 'pe1   ');
*/
			$('#periodoTipoCambioList').selectmenu('setValue', $("#cdPeriodoFactSeleccionado").val());         
		}
	} catch(e) {
		jsError('errorPeriodos', e);
	}
}

function resetPeriodoOption(label, value, disabled){
	document.getElementById('periodoTipoCambioList').options.length=0;
	document.getElementById('periodoTipoCambioList')[0]= new Option(label,value);
	$('#periodoTipoCambioList').selectmenu('destroy');
	$('#periodoTipoCambioList').selectmenu({style:'dropdown', maxHeight:'200', width:'200px'}).selectmenu('disabled',true);
}

function errorPeriodos(cod, des){
	jsError('errPeriodo', des);
}

function saveProveedorTipoCambio() {
	var params = '';
	var confirma = "S";
	var tipoModificacion = 	$("#tipoModificacion").val();
	if (tipoModificacion == "D") {
		confirmarDeleteTipoCambio();
	} else {
		if (tipoModificacion == "E") {
			params += 'opcion=2';
		} else {
			params += 'opcion=1';		
		}
		params += '&cdProveedor='+$('#proveedorTipoCambioList').val();
		params += '&cdPeriodoFact='+$('#periodoTipoCambioList').val();
		params += '&cdMoneda='+$('#monedaTipoCambioList').val();
		params += '&nuDias='+$('#nuDias').val();
		params += '&cdCotizacion='+$("#cotizacionTipoCambioList").val();
		params += '&stHabilitado='+$("#habilitadoTipoCambioList").val();
		if (tipoModificacion == "E") {
			if (!(confirm('Confirma la modificaci\u00f3n del Tipo Cambio?'))) {
				confirma = "N";
			}
		}
		if (confirma == "S") {
			var err = "";
			if ($("#proveedorTipoCambioList").val() == '0') {
				err += "El Proveedor es obligatorio\n";
			}
			if ($("#periodoTipoCambioList").val() == '0') {
				err += "El Periodo es obligatorio\n";
			}	
			if ($("#monedaTipoCambioList").val() == '0') {
				err += "La Moneda es obligatoria\n";
			}
			if ($.trim($("#nuDias").val()) == '') {
				err += "El Valor de Dias es obligatorio\n";
			}
			if ($.trim($("#cotizacionTipoCambioList").val()) == '') {
				err += "El Tipo de Cotizaci&oacute;n es obligatorio\n";
			}
			if ($.trim($("#habilitadoTipoCambioList").val()) == '') {
				err += "El Estado es obligatorio\n";
			}
			if (err != "") {
				alert("Verifique los datos ingresados\n\n" + err);
			} else {
//alert(params);
				callJsonAction('saveProveedorTipoCambio.action', params, 'successSaveProveedorTipoCambio','errorSaveProveedorTipoCambio');
			}
		}
	}
}

function successSaveProveedorTipoCambio(json){	 
	if (isSuccessResult(json.result.errorCode)) {		
		setSuccessMsg($('#proveedor_responseMsgs'),"Editado Exitosamente");
		$('#proveedor_responseMsgs').show();
		$('#dialogEditProveedorTipoCambio').dialog('close');
		resetProveedorForm();
		search();
		cleanDataConfirmation();
		search();
	} else {
		setErrorMsg($('#prov_tipocambio_responseMsgs'),json.result.errorDesc);
		$('#prov_tipocambio_responseMsgs').show();
	}
}

function errorSaveProveedorTipoCambio(errorCode, errorDesc){
	setErrorMsg($('#proveedor_responseMsgs'),errorDesc);
	$('#proveedor_responseMsgs').show();	
	$('#dialogEditProveedorTipoCambio').dialog('close');
	resetProveedorTipoCambioForm();
	cleanDataConfirmation();
}

function confirmarDeleteTipoCambio(){	
	var params = '';
	params += 'cdProveedor='+$('#proveedorTipoCambioList').val();
	params += '&cdPeriodoFact='+$('#periodoTipoCambioList').val();
	params += '&cdMoneda='+$('#monedaTipoCambioList').val();
	if (confirm('Confirma la baja del Tipo de Cambio?')) {
		callJsonAction('deleteProveedorTipoCambio.action', params, 'successDeleteProveedorTipoCambio', 'errorDeleteProveedorTipoCambio');
	}
}

function successDeleteProveedorTipoCambio(json){	 
	if (isSuccessResult(json.result.errorCode)){		
		setSuccessMsg($('#proveedor_responseMsgs'),"Eliminado Exitosamente");
		$('#proveedor_responseMsgs').show();
		$('#dialogDeleteProveedor').dialog('close');
		search();
	} else {		
		setErrorMsg($('#proveedor_responseMsgs'),json.result.errorDesc);
		$('#proveedor_responseMsgs').show();
	}	
	cleanDataConfirmation();
}

function errorDeleteProveedorTipoCambio(errorCode, errorDesc){
	setErrorMsg($('#proveedor_responseMsgs'),errorDesc);
	$('#proveedor_responseMsgs').show();	
	$('#dialogDeleteProducto').dialog('close');
	cleanDataConfirmation();
}

function cleanDataConfirmation(){
	
//	$('#cdProveedor').val('');
//	$('#cdPeriodoFact').val('');
//	$('#cdTipoCambio').val('');
	$('#nuDias').val('');
//	$('#stHabilitado').val('');
}
	
function cleanMsgConfirmation(){
	$('#proveedor_responseMsgs').hide();
	$('#proveedor_responseMsgs').val('');
	$('#msgEspera').hide();
	$('#msgEspera').val('');
	$('#msgConfirmacion').hide();
	$('#msgConfirmacion').val('');
	$('#prov_tipocambio_responseMsgs').hide();
	$('#prov_tipocambio_responseMsgs').val('');
	
}

function dateFormat_serviceToJs(fecha) {
	var value = "";
	if (fecha != null && fecha != "") {
		value = fecha.substr(8,2)+'/'+fecha.substr(5,2)+'/'+fecha.substr(0,4);
	}
	return value;
}

function destroyCombos() {
	$('#proveedorTipoCambioList').selectmenu('destroy');	
	$('#proveedorTipoCambioList').selectmenu({style:'dropdown', maxHeight:'200', width:'200px'});

	$('#periodoTipoCambioList').selectmenu('destroy');	
	$('#periodoTipoCambioList').selectmenu({style:'dropdown', maxHeight:'200', width:'200px'});
	
	$('#monedaTipoCambioList').selectmenu('destroy');	
	$('#monedaTipoCambioList').selectmenu({style:'dropdown', maxHeight:'200', width:'200px'});
	
	$('#cotizacionTipoCambioList').selectmenu('destroy');	
	$('#cotizacionTipoCambioList').selectmenu({style:'dropdown', maxHeight:'200', width:'200px'});	
	
	$('#habilitadoTipoCambioList').selectmenu('destroy');	
	$('#habilitadoTipoCambioList').selectmenu({style:'dropdown', maxHeight:'200', width:'200px'});	
//	alert("destroyCombos-AFTER");
}
