$(document).ready(function(){
	$("#fhDesde").mask("99/99/9999",{placeholder:" "});
	$("#fhHasta").mask("99/99/9999",{placeholder:" "});
	destroyCombos();
	
	$.ajaxSetup({
        cache : false
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
	}).next('button').text('&nbsp;').addClass('ui-button-text').button({icons:{primary : 'ui-icon ui-icon-calendar'}}).removeClass().addClass('ui-button ui-widget ui-state-default ui-button-icon-only ui-corner-right ui-button-icon').attr('id', 'fhDesdeBut');

	$("#fhHasta").datepicker({
		changeMonth: true,
		changeYear: true,
		regional: 'es',
		yearRange: 'c-100:c',
		dateFormat: 'dd/mm/yy',
		showAnim: '',
		showOn: "button",
		duration: 0
	}).next('button').text('&nbsp;').addClass('ui-button-text').button({icons:{primary : 'ui-icon ui-icon-calendar'}}).removeClass().addClass('ui-button ui-widget ui-state-default ui-button-icon-only ui-corner-right ui-button-icon').attr('id', 'fhHastaBut');

//    $('input[type="text"]').each(function() {
//        $(this).setMask($(this).attr('alt'));
//    });
	
	initProveedorPeriodoDialog();
	cleanDataConfirmation();
	
	search();
});

function search() {
	$('#agregarGrantPVP').val('S');
	limpiarGrilla('gridProveedoresPeriodosId', 'gridProveedoresPeriodosPager', 'gridProveedoresPeriodos');

	$('#proveedoresPeriodosGrid').show();
	$('#botonera').show();
	$('#buttonCancelar').button({
		icons : {
			secondary : ""
		}
	}).click(function() {
		resetProveedorPeriodoForm();
		cleanDataConfirmation();

	});
	loadProveedoresPeriodosGrid();
}

function initProveedorPeriodoDialog(){
	$("#dialogEditProveedorPeriodo").dialog({
		bgiframe: true,
		autoOpen: false,
		modal: true,
		width: 550,
		
		close: reloadProveedoresPeriodosGrid, 
		buttons: [ 
			{   id: 'guardar-button',
				text: "Grabar",
		        click: function() {
		        	try {
		        		saveProveedorPeriodo();
					} catch(e) {
						jsError('saveProveedorPeriodo(...)',e.message);
					}
				}
		    },
		    {   id: 'cancelar-button',
		    	text: "Cancelar",
		       	click: function() {
   	        		$('#prov_per_responseMsgs').hide();
		    		resetProveedorPeriodoForm();
		    		cleanDataConfirmation();
		           	$(this).dialog('close');
		      	}
		    }
		]
	});	
}

// GRILLA
function loadProveedoresPeriodosGrid(){
	var params = '';
	if (($("#cdProveedorSeleccionado").val() != '') && ($("#cdProveedorSeleccionado").val() != undefined)) {
	   params = 'cdProveedor='+$("#cdProveedorSeleccionado").val();
	} else {
       params = 'cdProveedor=999';
	}
	try {
		showGrid({
			id : 'gridProveedoresPeriodosId',
			idPager : 'gridProveedoresPeriodosPager',
			url : 'JsonProveedoresPeriodosList.action?'+params,
			colNames : [ 'C&oacute;d Proveedor', 'Per&iacute;odo', 'Descripci&oacute;n', 'C&oacute;digo Alternativo',  
			             'Fecha Desde', 'Fecha Hasta', 'Estado' ],
			colModel : [ 
				{name : 'cdProveedor'   , index : 'id', width : 100, align : 'left', hidden : true }, 
				{name : 'cdPeriodoFact' , width : 100, align : 'left', hidden : false }, 
				{name : 'nbPeriodoFact' , width : 150, align : 'left', hidden : false }, 
				{name : 'cdPerFactAlt'  , width : 100, align : 'left', hidden : false }, 
				{name : 'fhDesde'       , width : 100, align : 'left', hidden : false,formatter: 'date', formatoptions: { srcformat: 'Y/m/d', newformat: 'd/m/Y'} }, 
				{name : 'fhHasta'       , width : 100, align : 'left', hidden : false,formatter: 'date', formatoptions: { srcformat: 'Y/m/d', newformat: 'd/m/Y'} }, 
				{name : 'stEstado'      , width : 50 , align : 'left', hidden : false }
			],
			rowNum : 10,
			rowList : [ 10, 15, 20 ],
			sortName : 'fhDesde',
			sortorder: 'desc',
			caption : "Per&iacute;odos de Facturaci&oacute;n",
			height : '100%',
			width : '100%',
			multiselect: false,
			loadonce : true,
			loadCompleteFunc : '',
            editurl: 'clientArray',
			shrinkToFit: true
		});
		$('.ui-jqgrid-title').css('width', '98%');
		addButtonsToProveedorPeriodoGrid();
		$("#gridProveedoresPeriodosId").jqGrid('setGridParam', 
			    {onSelectRow: function(rowid,iRow,iCol,e){asignoPeriodo(rowid);}});
	} catch(e) {
		jsError('loadProveedoresPeriodosGrid(...)', e);
	}
}

// AGREGA BOTONERA DE LA GRILLA
function addButtonsToProveedorPeriodoGrid(){
	if($('#addGrantPVP').val()=='S'){
		var title = 'Agregar Per&iacute;odo';
		$('#gridProveedoresPeriodosId').navButtonAdd('#gridProveedoresPeriodosPager', {
			caption: 'Agregar',
			buttonicon: 'ui-icon ui-icon-plus',
			onClickButton: function() {
				try {
					$('#gridProveedoresPeriodosId').resetSelection();
					processEditProveedorPeriodo(0, false, 'Ingresar Per&iacute;odo', 'A');
				} catch (e) {
					jsError('loadProveedoresPeriodosGrid(...)', e);
				}
			}, 
			position:'last', 
			title:title
		});
	}
	if($('#editGrantPVP').val()=='S') {
		var title = 'Editar Per&iacute;odo';
		$('#gridProveedoresPeriodosId').navButtonAdd('#gridProveedoresPeriodosPager', {
			caption: 'Editar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromProveedorPeriodoGrid(); 
					if (rowid != null) {
						processEditProveedorPeriodo(rowid, false, 'Editar Per&iacute;odo', 'E');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadProveedoresPeriodosGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
	}
	if($('#deleteGrantPVP').val()=='S') {
		var title = 'Eliminar Per&iacute;odo';
		$('#gridProveedoresPeriodosId').navButtonAdd('#gridProveedoresPeriodosPager', {
			caption: 'Eliminar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromProveedorPeriodoGrid(); 
					if (rowid != null) {
						processEditProveedorPeriodo(rowid, false, 'Eliminar Per&iacute;odo', 'D');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadProveedoresPeriodosGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
	}		
	if($('#selectGrantPVP').val()=='S') {
		var title = 'Consultar Per&iacute;odo';
		$('#gridProveedoresPeriodosId').navButtonAdd('#gridProveedoresPeriodosPager', {
			caption: 'Consultar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromProveedorPeriodoGrid(); 
					if (rowid != null) {
						processEditProveedorPeriodo(rowid, false, 'Consultar Per&iacute;odo', 'C');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadProveedoresPeriodosGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
	}		
}

// FUNCTIONES GENERALES GRILLA
function reloadProveedoresPeriodosGrid(){
	$('#gridProveedoresPeriodosId').trigger('reloadGrid');
}

function getSelRowFromProveedorPeriodoGrid() { 
	return $("#gridProveedoresPeriodosId").getGridParam('selrow');
}

function resetProveedorPeriodoForm() {
	$(':input','#dialogEditProveedorPeriodo').not('input[type=hidden], :button').val('').removeAttr('checked').removeAttr('selected');
}

function asignoPeriodo(rowid) {
	var cdPeriodoFact = $('#gridProveedoresPeriodosId').jqGrid('getCell', rowid, 'cdPeriodoFact');
	var nbPeriodoFact = $('#gridProveedoresPeriodosId').jqGrid('getCell', rowid, 'nbPeriodoFact');
    $('#cdPeriodoFactSeleccionado').val(cdPeriodoFact);
    $('#nbPeriodoFactSeleccionado').val(nbPeriodoFact);
	$("#tabsApplication").enableTab(2);    
	$("#tabsApplication").enableTab(3);
}

//EDIT PERIODO
function processEditProveedorPeriodo(rowid, tipo, sTitle, tipoAcceso) {
	cleanDataConfirmation();
	cleanMsgConfirmation();
	destroyCombos();

	$("#tipoModificacion").val(tipoAcceso);

	if (tipoAcceso == 'A') {
	    $('#proveedorPeriodoList').selectmenu('disabled',true);
		$("#proveedorPeriodoList").selectmenu('setValue', $('#cdProveedorSeleccionado').val());	    
	    $('#cdPeriodoFact').attr('disabled',false).removeClass("ui-state-disabled");
	}
    if (tipoAcceso != 'A') {
		var cdProveedor   = $('#gridProveedoresPeriodosId').jqGrid('getCell', rowid, 'cdProveedor');
		var cdPeriodoFact = $('#gridProveedoresPeriodosId').jqGrid('getCell', rowid, 'cdPeriodoFact');
		var nbPeriodoFact = $('#gridProveedoresPeriodosId').jqGrid('getCell', rowid, 'nbPeriodoFact');
		var cdPerFactAlt  = $('#gridProveedoresPeriodosId').jqGrid('getCell', rowid, 'cdPerFactAlt');
		var fhDesde       = $('#gridProveedoresPeriodosId').jqGrid('getCell', rowid, 'fhDesde');
		var fhHasta       = $('#gridProveedoresPeriodosId').jqGrid('getCell', rowid, 'fhHasta');
		var stEstado      = $('#gridProveedoresPeriodosId').jqGrid('getCell', rowid, 'stEstado');
		
		$("#proveedorPeriodoList").selectmenu('setValue', cdProveedor);
		$("#cdPeriodoFact").val(cdPeriodoFact);
		$("#nbPeriodoFact").val(nbPeriodoFact);
		$("#cdPerFactAlt").val(cdPerFactAlt);
		 
		
//		$("#fhDesde").val(dateFormat_serviceToJs(fhDesde));
//		$("#fhHasta").val(dateFormat_serviceToJs(fhHasta));
		$("#fhDesde").val((fhDesde));
		$("#fhHasta").val((fhHasta));
		$("#estadoList").selectmenu('setValue', stEstado);
		
	    $('#proveedorPeriodoList').selectmenu('disabled',true);
	    $('#cdPeriodoFact').attr('disabled',true).addClass("ui-state-disabled");
	    
		if (tipoAcceso == 'C' || tipoAcceso == 'D' ) {
			$("#fhDesdeBut").button().hide();
			$("#fhHastaBut").button().hide();
			$("#nbPeriodoFact").attr('disabled',true).addClass("ui-state-disabled");
			$("#cdPerFactAlt").attr('disabled',true).addClass("ui-state-disabled");
			$("#fhDesde").attr('disabled',true).addClass("ui-state-disabled");
			$("#fhHasta").attr('disabled',true).addClass("ui-state-disabled");
			$("#estadoList").selectmenu('disabled',true);
		} else {
			$("#fhDesdeBut").button().show();
			$("#fhHastaBut").button().show();
			$("#nbPeriodoFact").attr('disabled',false).removeClass("ui-state-disabled");
			$("#cdPerFactAlt").attr('disabled',false).removeClass("ui-state-disabled");
			$("#fhDesde").attr('disabled',false).removeClass("ui-state-disabled");
			$("#fhHasta").attr('disabled',false).removeClass("ui-state-disabled");
			$("#estadoList").selectmenu('disabled',false);
		}
	}
	if (tipoAcceso == 'C') {
//		$(":button:contains('Grabar')").attr("disabled","disabled");
		$(":button:contains('Grabar')").button().hide();				
	} else {
//		$(":button:contains('Grabar')").removeAttr("disabled");
		$(":button:contains('Grabar')").button().show();				
	}	    
	$('#dialogEditProveedorPeriodo').dialog('option','title',sTitle);
	$('#dialogEditProveedorPeriodo').dialog('open');
}

function saveProveedorPeriodo() {
	var params   = '';
	var confirma = "S";
	var tipoModificacion = 	$("#tipoModificacion").val();
	
	if (tipoModificacion == "D") {
		confirmarDeletePeriodo();
	} else {
		if (tipoModificacion == "E") {
			params += 'opcion=2';
		} else {
			params += 'opcion=1';		
		}
		params += '&cdProveedor='+$('#proveedorPeriodoList').val();
		params += '&cdPeriodoFact='+$('#cdPeriodoFact').val();
		params += '&nbPeriodoFact='+$('#nbPeriodoFact').val();
		params += '&cdPerFactAlt='+$('#cdPerFactAlt').val();
		params += '&fhDesde='+dateFormat_JsToService($("#fhDesde").val());
		params += '&fhHasta='+dateFormat_JsToService($("#fhHasta").val());
		params += '&stEstado='+$("#estadoList").val();
		if (tipoModificacion == "E") {
			if (!(confirm('Confirma la modificaci\u00f3n del Periodo?'))) {
				confirma = "N";
			}
		}
		if (confirma == "S") {
			var err = "";
			if ($("#proveedorList").val() == 0) {
				err += "El C\u00f3digo es obligatorio\n";
			}
			if ($.trim($("#cdPeriodoFact").val()) == '') {
				err += "El Per\u00edodo es obligatorio\n";
			}
			if ($.trim($("#nbPeriodoFact").val()) == '') {
				err += "La Descripci\u00f3n es obligatoria\n";
			}
			if (!validateDate($("#fhDesde").val())) {
				err += "La fecha Desde es invalida\n";
			} 
			if (!validateDate($("#fhHasta").val())) {
				err += "La fecha Hasta es invalida\n";
			} 
			if ($.trim($("#estadoList").val()) == '') {
				err += "El Estado es obligatorio\n";
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
			if (err != "") {
				alert("Verifique los datos ingresados\n\n" + err);
			} else {
				callJsonAction('saveProveedorPeriodo.action', params, 'successSaveProveedorPeriodo','errorSaveProveedorPeriodo');
			}
		}
	}
}

function successSaveProveedorPeriodo(json){
	if (isSuccessResult(json.result.errorCode)){		
		setSuccessMsg($('#proveedor_responseMsgs'),"Editado Exitosamente");
		$('#proveedor_responseMsgs').show();
		$('#dialogEditProveedorPeriodo').dialog('close');
		resetProveedorPeriodoForm();
		search();
		cleanDataConfirmation();
		search();
	} else {		
		setErrorMsg($('#prov_per_responseMsgs'),json.result.errorDesc);
		$('#prov_per_responseMsgs').show();
	}	
}

function errorSaveProveedorPeriodo(errorCode, errorDesc){
	setErrorMsg($('#proveedor_responseMsgs'),errorDesc);
	$('#proveedor_responseMsgs').show();	
	$('#dialogEditProveedorPeriodo').dialog('close');
	resetProveedorForm();
	cleanDataConfirmation();
}

function confirmarDeletePeriodo(){	
	var params = '';
	params += 'cdProveedor='+$('#proveedorPeriodoList').val();
	params += '&cdPeriodoFact='+$('#cdPeriodoFact').val();
	if (confirm('Confirma la baja del Periodo?')) {
		callJsonAction('deleteProveedorPeriodo.action', params, 'successDeleteProveedorPeriodo', 'errorDeleteProveedorPeriodo');
	}
}

function successDeleteProveedorPeriodo(json){	 
	if (isSuccessResult(json.result.errorCode)){		
		setSuccessMsg($('#proveedor_responseMsgs'),"Eliminado Exitosamente");
		$('#proveedor_responseMsgs').show();
		$('#dialogDeleteProveedorPeriodo').dialog('close');
		search();
	} else {		
		setErrorMsg($('#proveedor_responseMsgs'),json.result.errorDesc);
		$('#proveedor_responseMsgs').show();
	}	
	cleanDataConfirmation();
}

function errorDeleteProveedorPeriodo(errorCode, errorDesc){
	setErrorMsg($('#proveedor_responseMsgs'),errorDesc);
	$('#proveedor_responseMsgs').show();	
	$('#dialogDeleteProveedorPeriodo').dialog('close');
	cleanDataConfirmation();
}

function cleanDataConfirmation(){
//	$('#cdProveedor').val('');
	$('#cdPeriodoFact').val('');
	$('#nbPeriodoFact').val('');
	$('#cdPerFactAlt').val('');
	$('#fhDesde').val('');
	$('#fhHasta').val('');
	$('#stEstado').val('');
}
	
function cleanMsgConfirmation(){
	$('#proveedor_responseMsgs').hide();
	$('#proveedor_responseMsgs').val('');
	$('#msgEspera').hide();
	$('#msgEspera').val('');
	$('#msgConfirmacion').hide();
	$('#msgConfirmacion').val('');
	$('#prov_per_responseMsgs').hide();
	$('#prov_per_responseMsgs').val('');
}

function dateFormat_serviceToJs(fecha){
	var value = "";
	if (fecha != null && fecha != "") {
		value = fecha.substr(8,2)+'/'+fecha.substr(5,2)+'/'+fecha.substr(0,4);
	}
	return value;
}

function destroyCombos() {
	$('#estadoList').selectmenu('destroy');	
	$('#estadoList').selectmenu({style:'dropdown', width:'200px'});

	$('#proveedorPeriodoList').selectmenu('destroy');	
	$('#proveedorPeriodoList').selectmenu({style:'dropdown', width : $('#proveedorPeriodoList').attr("width")});	
}

