$(document).ready(function(){	
	
	destroyCombosGeneralOne('filtroTablaList');
	destroyCombosGeneralOne('tablaList');
	destroyCombosGeneralOne('habilitadoList');
	
//	destroyCombosGeneralAll();
	
	initTablaDialog();
	cleanDataConfirmation();
//  $('#btnBusCons').button({icons : {secondary : "ui-icon-search"}}).click(function() {search();});
	
	$('#buttonCancelar').button({
		icons : {
			secondary : ""
		}
	}).click(function() {
		resetTablaForm();
		cleanDataConfirmation();
	});

	search();

	addCheckSessionAll();
});

function search() {
	
	$('#agregarGrant').val('S');
	$('#tablasGrid').show();
	$('#botonera').show();
	
	if($('#filtroTablaList').val() != 0){
		loadTablasGrid();
	}else{
		$('#tablasGrid').hide();
		$('#botonera').hide();
	}
}

function initTablaDialog(){
	$("#dialogEditTabla").dialog({
		bgiframe: true,
		autoOpen: false,
		modal: true,
		width: 450,
		
		close: reloadTablasGrid, 
		buttons: [ 
			{   id: 'guardar-button',
				text: "Grabar",
		        click: function() {
		        	try {
						saveTabla();
					} catch(e) {
						jsError('saveTabla(...)',e.message);
					}
				}
		    },
			{   id: 'cancelar-button',
   	            text: "Cancelar",
   	        	click: function() {
   	        		$('#tabla_diag_responseMsgs').hide();
					resetTablaForm();
		    		cleanDataConfirmation();
	            	$(this).dialog('close');
            	}
	        }
		]
	});
}

// GRILLA
function loadTablasGrid(){
	
	limpiarGrilla('gridTablasId', 'gridTablasPager', 'gridTablas');
//	var params = ""; 
	var params = 'cdCodTabla='+$('#filtroTablaList').val();
	try {
		showGrid({
			id : 'gridTablasId',
			idPager : 'gridTablasPager',
			url : 'JsonTablasList.action?'+params,
			colNames : [ 'Tabla', 'C&oacute;digo', 'Descripci&oacute;n Larga', 'Descr. Corta', 'Atributo 1', 
			             'Atributo 2', 'Atributo 3', 'Habilitado' ],
			colModel : [ 
				{name : 'cdTabla'          , index : 'id', width : 80, align : 'left', hidden : true }, 
				{name : 'cdCodTabla'       , width : 80, align : 'left', hidden : false }, 
				{name : 'nbCodTabla'       , width : 120, align : 'left', hidden : false }, 
				{name : 'nbCodTablaCorto'  , width : 80, align : 'left', hidden : false }, 
				{name : 'nbAtributoTabla1' , width : 100, align : 'left', hidden : false }, 
				{name : 'nbAtributoTabla2' , width : 100, align : 'left', hidden : false }, 
				{name : 'nbAtributoTabla3' , width : 100, align : 'left', hidden : false }, 
				{name : 'stHabilitado'     , width : 50 , align : 'left', hidden : false }
			],
			rowNum : 10,
			rowList : [ 10, 15, 20 ],
			sortName : 'cdTabla',
			caption : "Tablas ",
			height : '100%',
			width : '100%',
			multiselect: false,
			loadonce : true,
			loadCompleteFunc : '',
            editurl: 'clientArray',
			shrinkToFit: false,
	        ondblClickRow: function(rowid, iRow, iCol, e) {
					viewDialogEditTabla(rowid);
	        }
		});
		$('.ui-jqgrid-title').css('width', '98%');
		addButtonsToTablaGrid();
	} catch(e) {
		jsError('loadTablasGrid(...)', e);;
	}
}

// AGREGA BOTONERA DE LA GRILLA
function addButtonsToTablaGrid(){
	if($('#addGrant').val()=='S'){
		var title = 'Agregar Registro';
		$('#gridTablasId').navButtonAdd('#gridTablasPager', {
			caption: 'Agregar',
			buttonicon: 'ui-icon ui-icon-plus',
			onClickButton: function() {
				try {
					$('#gridTablasId').resetSelection();
					processEditTabla(0, false, 'Agregar Registro', 'A');
				} catch (e) {
					jsError('loadTablasGrid(...)', e);
				}
			}, 
			position:'last', 
			title:title
		});
	}
	if($('#editGrant').val()=='S') {
		var title = 'Editar Registro';
		$('#gridTablasId').navButtonAdd('#gridTablasPager', {
			caption: 'Editar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromTablaGrid(); 
					if (rowid != null) {
						processEditTabla(rowid, false, 'Editar Registro', 'E');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadTablasGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
	}
	if($('#deleteGrant').val()=='S') {
		var title = 'Eliminar Registro';
		$('#gridTablasId').navButtonAdd('#gridTablasPager', {
			caption: 'Eliminar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromTablaGrid(); 
					if (rowid != null) {
						processEditTabla(rowid, false, 'Eliminar Registro', 'D');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadTablasGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
	}		
	if($('#selectGrant').val()=='S') {
		var title = 'Consultar Registro';
		$('#gridTablasId').navButtonAdd('#gridTablasPager', {
			caption: 'Consultar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromTablaGrid(); 
					if (rowid != null) {
						processEditTabla(rowid, false, 'Consultar Registro','C');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadTablasGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
	}		
}

// FUNCTIONES GENERALES GRILLA
function reloadTablasGrid(){
	$('#gridTablasId').trigger('reloadGrid');
}

function getSelRowFromTablaGrid() { 
	return $("#gridTablasId").getGridParam('selrow');
}

function resetTablaForm() {
	$(':input','#dialogEditTabla').not('input[type=hidden], :button').val('').removeAttr('checked').removeAttr('selected');
}

//EDIT PRODUCTO
function processEditTabla(rowid, tipo, sTitle, tipoAcceso) {
	cleanDataConfirmation();
	cleanMsgConfirmation();

	/* Hay que limpiarlo sino rompen los combos y no te deja seleccinar la tabla
	** Luego de haber recorrido otros menues ... es medio loco pero asi anda!
	*/
	$("#tipoModificacion").val(tipoAcceso);;
	
//	destroyCombosGeneralOne('tablaList');
//	destroyCombosGeneralOne('habilitadoList');

	$('#tablaList').selectmenu('setValue', $('#filtroTablaList').val());
	if (tipoAcceso == 'A') {
		$('#tablaList').selectmenu('disabled', false);	
	    $('#cdCodTabla').attr('disabled',false).removeClass("ui-state-disabled");
		$("#nbCodTabla").attr('disabled',false).removeClass("ui-state-disabled");
		$("#nbCodTablaCorto").attr('disabled',false).removeClass("ui-state-disabled");
		$("#nbAtributoTabla1").attr('disabled',false).removeClass("ui-state-disabled");
		$("#nbAtributoTabla2").attr('disabled',false).removeClass("ui-state-disabled");
		$("#nbAtributoTabla3").attr('disabled',false).removeClass("ui-state-disabled");
		$("#habilitadoList").selectmenu('disabled',false); 
    	$('#habilitadoList').selectmenu('setValue', 'S');
    }else if (tipoAcceso != 'A') {
		var cdTabla          = $('#gridTablasId').jqGrid('getCell', rowid, 'cdTabla');
		var cdCodTabla       = $('#gridTablasId').jqGrid('getCell', rowid, 'cdCodTabla');
		var nbCodTabla       = $('#gridTablasId').jqGrid('getCell', rowid, 'nbCodTabla');
		var nbCodTablaCorto  = $('#gridTablasId').jqGrid('getCell', rowid, 'nbCodTablaCorto');
		var nbAtributoTabla1 = $('#gridTablasId').jqGrid('getCell', rowid, 'nbAtributoTabla1');
		var nbAtributoTabla2 = $('#gridTablasId').jqGrid('getCell', rowid, 'nbAtributoTabla2');
		var nbAtributoTabla3 = $('#gridTablasId').jqGrid('getCell', rowid, 'nbAtributoTabla3');
		var stHabilitado     = $('#gridTablasId').jqGrid('getCell', rowid, 'stHabilitado');

		$('#tablaList').selectmenu('setValue', $('#filtroTablaList').val());	
		$("#cdCodTabla").val(cdCodTabla);
		$("#nbCodTabla").val(nbCodTabla);
		$("#nbCodTablaCorto").val(nbCodTablaCorto);
		$("#nbAtributoTabla1").val(nbAtributoTabla1);
		$("#nbAtributoTabla2").val(nbAtributoTabla2);
		$("#nbAtributoTabla3").val(nbAtributoTabla3);
		$("#habilitadoList").selectmenu('setValue', stHabilitado);
		
		$('#tablaList').selectmenu('disabled', true);	
	    $('#cdCodTabla').attr('disabled',true).addClass("ui-state-disabled");
	    
	    if (tipoAcceso == 'C' || tipoAcceso == 'D' ) {
    		$("#nbCodTabla").attr('disabled',true).addClass("ui-state-disabled");
    		$("#nbCodTablaCorto").attr('disabled',true).addClass("ui-state-disabled");
    		$("#nbAtributoTabla1").attr('disabled',true).addClass("ui-state-disabled");
    		$("#nbAtributoTabla2").attr('disabled',true).addClass("ui-state-disabled");
    		$("#nbAtributoTabla3").attr('disabled',true).addClass("ui-state-disabled");
    		$("#habilitadoList").selectmenu('disabled',true);    		
	    } else {
    		$("#nbCodTabla").attr('disabled',false).removeClass("ui-state-disabled");
    		$("#nbCodTablaCorto").attr('disabled',false).removeClass("ui-state-disabled");
    		$("#nbAtributoTabla1").attr('disabled',false).removeClass("ui-state-disabled");
    		$("#nbAtributoTabla2").attr('disabled',false).removeClass("ui-state-disabled");
    		$("#nbAtributoTabla3").attr('disabled',false).removeClass("ui-state-disabled");
    		$("#habilitadoList").selectmenu('disabled',false);
	    }
    }    
	if (tipoAcceso == 'C') {
		$(":button:contains('Grabar')").button().hide();				
	} else {
		$(":button:contains('Grabar')").button().show();				
	}
	
	$('#dialogEditTabla').dialog('option','title',sTitle);
	$('#dialogEditTabla').dialog('open');
}

function saveTabla() {
	
	var params = '';
	var confirma = "S";
	var tipoModificacion = $("#tipoModificacion").val();
	
	if (tipoModificacion == "D") {
		confirmarDeleteTabla();
	} else {
		if (tipoModificacion == "E") {
			params += 'opcion=2';
		} else {
			params += 'opcion=1';		
		}
		params += '&cdTabla='+$("#tablaList").val();
		params += '&cdCodTabla='+$('#cdCodTabla').val();
		params += '&nbCodTabla='+$('#nbCodTabla').val();
		params += '&nbCodTablaCorto='+$('#nbCodTablaCorto').val();
		params += '&nbAtributoTabla1='+ $('#nbAtributoTabla1').val().replace('+', '%2B'); 
		params += '&nbAtributoTabla2='+$('#nbAtributoTabla2').val().replace('+', '%2B'); ;
		params += '&nbAtributoTabla3='+$('#nbAtributoTabla3').val().replace('+', '%2B'); ;
		params += '&stHabilitado='+$("#habilitadoList").val();
		if (tipoModificacion == "E") {
			if (!(confirm('Confirma la modificaci\u00f3n de la Tabla?'))) {
				confirma = "N";
			}
		}
		if (confirma == "S") {
			var err = "";
			if ($.trim($("#tablaList").val()) == '') {
				err += "La Tabla es obligatoria\n";
			}
			if ($.trim($("#cdCodTabla").val()) == '') {
				err += "El C\u00f3digo es obligatorio\n";
			}
			if ($.trim($("#nbCodTabla").val()) == '') {
				err += "La Descripci\u00f3n larga es obligatoria\n";
			}
			if ($.trim($("#nbCodTablaCorto").val()) == '') {
				err += "La Descripci\u00f3n corta es obligatoria\n";
			}
			if ($.trim($("#habilitadoList").val()) == '') {
				err += "El Estado es obligatorio\n";
			}
			if (err != "") {
				alert("Verifique los datos ingresados\n\n" + err);
			} else {
				callJsonAction('saveTabla.action', params, 'successSaveTabla','errorSaveTabla');
			}
		}
	}
}

function successSaveTabla(json){	 
	if (isSuccessResult(json.result.errorCode)){		
		setSuccessMsg($('#tabla_responseMsgs'),"Editado Exitosamente");
		$('#tabla_responseMsgs').show();
		$('#dialogEditTabla').dialog('close');
		resetTablaForm();
		search();
		cleanDataConfirmation();
	} else {		
		setErrorMsg($('#tabla_diag_responseMsgs'),json.result.errorDesc);
		$('#tabla_diag_responseMsgs').show();
	}	
}

function errorSaveTabla(errorCode, errorDesc){
	setErrorMsg($('#tabla_responseMsgs'),errorDesc);
	$('#tabla_responseMsgs').show();	
	$('#dialogEditTabla').dialog('close');
	resetTablaForm();
	cleanDataConfirmation();
}

function confirmarDeleteTabla(){	
	var params = '';
	params += 'cdTabla='+$("#tablaList").val();
	params += '&cdCodTabla='+$('#cdCodTabla').val();
	if (confirm('Confirma la baja de la Tabla?')) {
		callJsonAction('deleteTabla.action', params, 'successDeleteTabla', 'errorDeleteTabla');
	}
}

function successDeleteTabla(json){	 
	if (isSuccessResult(json.result.errorCode)){		
		setSuccessMsg($('#tabla_responseMsgs'),"Eliminado Exitosamente");
		$('#tabla_responseMsgs').show();
		$('#dialogDeleteTabla').dialog('close');
		search();
	} else {		
		setErrorMsg($('#tabla_responseMsgs'),json.result.errorDesc);
		$('#tabla_responseMsgs').show();
	}	
	cleanDataConfirmation();
}

function errorDeleteTabla(errorCode, errorDesc){
	setErrorMsg($('#tabla_responseMsgs'),errorDesc);
	$('#tabla_responseMsgs').show();	
	$('#dialogDeleteProducto').dialog('close');
	cleanDataConfirmation();
}

function cleanDataConfirmation(){
	$('#cdTabla').val('');
	$('#cdCodTabla').val('');
	$('#nbCodTabla').val('');
	$('#nbCodTablaCorto').val('');
	$('#nbAtributoTabla1').val('');
	$('#nbAtributoTabla2').val('');
	$('#nbAtributoTabla3').val('');
	$('#stHabilitado').val('');
	$("#habilitadoList").selectmenu('setValue', '');
}
	
function cleanMsgConfirmation(){
	$('#tabla_responseMsgs').hide();
	$('#tabla_responseMsgs').val('');
	$('#msgEspera').hide();
	$('#msgEspera').val('');
	$('#tabla_diag_responseMsgs').hide();
	$('#tabla_diag_responseMsgs').val('');
	$('#msgConfirmacion').hide();
	$('#msgConfirmacion').val('');
}

function dateFormat_serviceToJs(fecha){
	var value = "";
	if (fecha != null && fecha != "") {
		value = fecha.substr(8,2)+'/'+fecha.substr(5,2)+'/'+fecha.substr(0,4);
	}
	return value;
}