$(document).ready(function(){
	destroyCombos();	
	
    $('#btnBusCons').button({icons : {secondary : "ui-icon-search"}}).click(function() {search();});

	initSectorDialog();
	cleanDataConfirmation();
	
	search();	
	addCheckSessionAll();
});

function search() {
	$('#agregarGrant').val('S');
	limpiarGrilla('gridSectoresId', 'gridSectoresPager', 'gridSectores');

	$('#sectoresGrid').show();
	$('#botonera').show();
	$('#buttonCancelar').button({
		icons : {
			secondary : ""
		}
	}).click(function() {
		resetSectorForm();
		cleanDataConfirmation();
	});
	loadSectoresGrid();
}

function initSectorDialog(){
	$("#dialogEditSector").dialog({
		bgiframe: true,
		autoOpen: false,
		modal: true,
		width: 450,
		
		close: reloadSectoresGrid, 
		buttons: [ 
			{   id: 'guardar-button',
				text: "Grabar",
		        click: function() {
		        	try {
						saveSector();
					} catch(e) {
						jsError('saveSector(...)',e.message);
					}
				}
		    },
			{   id: 'cancelar-button',
   	            text: "Cancelar",
   	        	click: function() {
   	     		$('#sector_diag_responseMsgs').hide();
					resetSectorForm();
		    		cleanDataConfirmation();
	            	$(this).dialog('close');
            	}
	        }
		]
	});
}

// GRILLA
function loadSectoresGrid(){
	var params = 'cdSector='+$('#filtroSectorList').val();
	try {
		showGrid({
			id : 'gridSectoresId',
			idPager : 'gridSectoresPager',
			url : 'JsonSectoresList.action?'+params,
			colNames : [ 'Sector', 'Nombre', 'Nombre Corto', 'Sector Alternativo', 'Habilitado' ],
			colModel : [ 
				{name : 'cdSector'       , index : 'id', width : 100, align : 'left', hidden : false }, 
				{name : 'nbSector'       , width : 200, align : 'left', hidden : false }, 
				{name : 'nbSectorAbrev'  , width : 120, align : 'left', hidden : false }, 
				{name : 'cdSectorAlt'    , width : 200, align : 'left', hidden : false }, 
				{name : 'stHabilitado'   , width : 50 , align : 'left', hidden : false }
			],
			rowNum : 10,
			rowList : [ 10, 15, 20 ],
			sortName : 'cdSector',
			caption : "Sectores ",
			height : '100%',
			width : '100%',
			multiselect: false,
			loadonce : true,
			loadCompleteFunc : '',
            editurl: 'clientArray',
			shrinkToFit: true,
	        ondblClickRow: function(rowid, iRow, iCol, e) {
					viewDialogEditSector(rowid);
	        }
		});
		$('.ui-jqgrid-title').css('width', '98%');
		addButtonsToSectorGrid();
	} catch(e) {
		jsError('loadSectoresGrid(...)', e);
	}
}

// AGREGA BOTONERA DE LA GRILLA
function addButtonsToSectorGrid(){
	if($('#addGrant').val()=='S'){
		var title = 'Agregar Sector';
		$('#gridSectoresId').navButtonAdd('#gridSectoresPager', {
			caption: 'Agregar',
			buttonicon: 'ui-icon ui-icon-plus',
			onClickButton: function() {
				try {
					$('#gridSectoresId').resetSelection();
					processEditSector(0, false, 'Ingresar Sector', 'A');
				} catch (e) {
					jsError('loadSectoresGrid(...)', e);
				}
			}, 
			position:'last', 
			title:title
		});
	}
	if($('#editGrant').val()=='S') {
		var title = 'Editar Sector';
		$('#gridSectoresId').navButtonAdd('#gridSectoresPager', {
			caption: 'Editar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromSectorGrid(); 
					if (rowid != null) {
						processEditSector(rowid, false, 'Editar Sector', 'E');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadSectoresGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
	}
	if($('#deleteGrant').val()=='S') {
		var title = 'Eliminar Sector';
		$('#gridSectoresId').navButtonAdd('#gridSectoresPager', {
			caption: 'Eliminar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromSectorGrid(); 
					if (rowid != null) {
						processEditSector(rowid, false, 'Eliminar Sector', 'D');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadSectoresGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
	}		
	if($('#selectGrant').val()=='S') {
		var title = 'Consultar Sector';
		$('#gridSectoresId').navButtonAdd('#gridSectoresPager', {
			caption: 'Consultar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromSectorGrid(); 
					if (rowid != null) {
						processEditSector(rowid, false, 'Consultar Sector', 'C');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadSectoresGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
	}		
	
}

// FUNCTIONES GENERALES GRILLA
function reloadSectoresGrid(){
	$('#gridSectoresId').trigger('reloadGrid');
}

function getSelRowFromSectorGrid() { 
	return $("#gridSectoresId").getGridParam('selrow');
}

function resetSectorForm() {
	$(':input','#dialogEditSector').not('input[type=hidden], :button').val('').removeAttr('checked').removeAttr('selected');
}

//EDIT PRODUCTO
function processEditSector(rowid, tipo, sTitle, tipoAcceso) {
	cleanDataConfirmation();
	cleanMsgConfirmation();
	destroyCombos();
	
	$("#tipoModificacion").val(tipoAcceso);
	
    if (tipoAcceso == 'A') {
		$("#cdSector").attr('disabled',false).removeClass("ui-state-disabled");
		$("#nbSector").attr('disabled',false).removeClass("ui-state-disabled");
		$("#nbSectorAbrev").attr('disabled',false).removeClass("ui-state-disabled"); 
		$("#cdSectorAlt").attr('disabled',false).removeClass("ui-state-disabled");
		$("#habilitadoListSect").selectmenu('disabled',false);
    }
    if (tipoAcceso != 'A') {
    	var cdSector       = $('#gridSectoresId').jqGrid('getCell', rowid, 'cdSector');
		var nbSector       = $('#gridSectoresId').jqGrid('getCell', rowid, 'nbSector');
		var nbSectorAbrev  = $('#gridSectoresId').jqGrid('getCell', rowid, 'nbSectorAbrev');
		var cdSectorAlt    = $('#gridSectoresId').jqGrid('getCell', rowid, 'cdSectorAlt');
		var stHabilitado   = $('#gridSectoresId').jqGrid('getCell', rowid, 'stHabilitado');
		
		$("#cdSector").val(cdSector);
		$("#nbSector").val(nbSector);
		$("#nbSectorAbrev").val(nbSectorAbrev);
		$("#cdSectorAlt").val(cdSectorAlt);
		$("#habilitadoListSect").selectmenu('setValue', stHabilitado);
		
	    $('#cdSector').attr('disabled',true).addClass("ui-state-disabled");
	    
	    console.log(tipoAcceso);
		    
	    if (tipoAcceso != 'E' ) {
	    	
			$("#nbSector").attr('disabled',true).addClass("ui-state-disabled");
			$("#nbSectorAbrev").attr('disabled',true).addClass("ui-state-disabled");
			$("#cdSectorAlt").attr('disabled',true).addClass("ui-state-disabled");
			$("#habilitadoListSect").selectmenu('disabled',true);
	    } else {
	    		    	
    		$("#nbSector").attr('disabled',false).removeClass("ui-state-disabled");
    		$("#nbSectorAbrev").attr('disabled',false).removeClass("ui-state-disabled");
    		$("#cdSectorAlt").attr('disabled',false).removeClass("ui-state-disabled");
    		$("#habilitadoListSect").selectmenu('disabled',false);
	    }
    }
	if (tipoAcceso == 'C') {
		$(":button:contains('Grabar')").button().hide();				
	} else {
		$(":button:contains('Grabar')").button().show();				
	}
	$('#dialogEditSector').dialog('option','title',sTitle);
	$('#dialogEditSector').dialog('open');
}

function saveSector() {
	var params = '';
	var confirma = "S";
	var tipoModificacion = $("#tipoModificacion").val();

	if (tipoModificacion == "D") {
		confirmarDeleteSector();
	} else {
		if (tipoModificacion == "E") {
			params += 'opcion=2';
		} else {
			params += 'opcion=1';		
		}
		params += '&cdSector='+$('#cdSector').val();
		params += '&nbSector='+$('#nbSector').val();
		params += '&nbSectorAbrev='+$('#nbSectorAbrev').val();
		params += '&cdSectorAlt='+$('#cdSectorAlt').val();
		params += '&stHabilitado='+$("#habilitadoListSect").val();
		if (tipoModificacion == "E") {
			if (!(confirm('Confirma la modificaci\u00f3n del Sector?'))) {
				confirma = "N";
			}
		}
		if (confirma == "S") {
			var err = "";
			if ($("#cdSector").val() == 0) {
				err += "El Sector es obligatorio\n";
			}
			if ($.trim($("#nbSector").val()) == '') {
				err += "El Nombre es obligatorio\n";
			}
			if (err != "") {
				alert("Verifique los datos ingresados\n\n" + err);
			} else {
				callJsonAction('saveSector.action', params, 'successSaveSector','errorSaveSector');
				
			}
		}
	}
}

function successSaveSector(json){	
		
	if (isSuccessResult(json.result.errorCode)){
		
		$.ajax({//recarga el combo de sectores
		    'type': 'POST',
		    'url': 'ABMCSectores.action',
		    'data': "",
		    'dataType': 'text',
		    'success': function(data) {

		    	$("#sectoresFilter").html("");
		    	$("#sectoresFilter").html(data);
		    	setSuccessMsg($('#sector_responseMsgs'),"Editado Exitosamente");
				$('#sector_responseMsgs').show();
				$('#dialogEditSector').dialog('close');
		    
		    },
		    'error': function(e) {

		    }
		  });
		
		destroyCombos();
		resetSectorForm();
		search();
		cleanDataConfirmation();
		search();
	} else {		
		setErrorMsg($('#sector_diag_responseMsgs'),json.result.errorDesc);
		$('#sector_diag_responseMsgs').show();
	}	
	cleanDataConfirmation();
	
}


function errorSaveSector(errorCode, errorDesc){
	setErrorMsg($('#sector_responseMsgs'),errorDesc);
	$('#sector_responseMsgs').show();	
	$('#dialogEditSector').dialog('close');
	resetSectorForm();
	cleanDataConfirmation();
}

function confirmarDeleteSector(){	
	var params = '';
	params += 'cdSector='+$('#cdSector').val();
	if (confirm('Confirma la baja del Sector?')) {
		callJsonAction('deleteSector.action', params, 'successDeleteSector', 'errorDeleteSector');
	}
}

function successDeleteSector(json){	 
	if (isSuccessResult(json.result.errorCode)){		
		setSuccessMsg($('#sector_responseMsgs'),"Eliminado Exitosamente");
		$('#sector_responseMsgs').show();
		$('#dialogDeleteSector').dialog('close');
		search();
	} else {		
		setErrorMsg($('#sector_responseMsgs'),json.result.errorDesc);
		$('#sector_responseMsgs').show();
	}	
	cleanDataConfirmation();
}

function errorDeleteSector(errorCode, errorDesc){
	setErrorMsg($('#sector_responseMsgs'),errorDesc);
	$('#sector_responseMsgs').show();	
	$('#dialogDeleteProducto').dialog('close');
	cleanDataConfirmation();
}

function cleanDataConfirmation(){
	$('#cdSector').val('');
	$('#nbSector').val('');
	$('#nbSectorAbrev').val('');
	$('#nbSectorAlt').val('');
	$('#stHabilitado').val('');
}
	
function cleanMsgConfirmation(){
	$('#sector_responseMsgs').hide();
	$('#sector_responseMsgs').val('');
	$('#msgEspera').hide();
	$('#msgEspera').val('');
	$('#msgConfirmacion').hide();
	$('#msgConfirmacion').val('');
	$('#sector_diag_responseMsgs').hide();
	$('#sector_diag_responseMsgs').val('');
}

function dateFormat_serviceToJs(fecha){
	var value = "";
	if (fecha != null && fecha != "") {
		value = fecha.substr(8,2)+'/'+fecha.substr(5,2)+'/'+fecha.substr(0,4);
	}
	return value;
}

function destroyCombos() {
	destroyCombosGeneralOne('habilitadoListSect');
	destroyCombosGeneralOne('filtroSectorList');
}	

