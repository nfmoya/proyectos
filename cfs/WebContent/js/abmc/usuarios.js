$(document).ready(function(){
    
//	destroyCombos();
//	initUsuarioDialog();   
     
	search();
});

function search() {
	
	$('#agregarGrant').val('S');
	limpiarGrilla('gridUsuariosId', 'gridUsuariosPager', 'gridUsuarios');

	$('#usuariosGrid').show();
//	$('#botonera').show();
//	$('#buttonCancelar').button({
//		icons : {
//			secondary : ""
//		}
//	}).click(function() {
//
//	});
	
	
	loadUsuariosGrid();
}

function initUsuarioDialog(){
	$("#dialogEditUsuario").dialog({
		bgiframe: true,
		autoOpen: false,
		modal: true,
		width: 450,
		
		close: reloadUsuariosGrid, 
		buttons: [ 
			{text: "Guardar",
		        click: function() {
		        	try {
						saveUsuario();
					} catch(e) {
						jsError('saveUsuario(...)',e.message);
					}
				}
		    },
   	        {text: "Cancelar",
   	        	click: function() {
					resetUsuarioForm();
	            	$(this).dialog('close');
            	}
	        }
		]
	});
	
    /* Propiedades de la ventana emergente de eliminar usuario */
	$("#dialogDeleteUsuario").dialog({  
		bgiframe: true,  
		autoOpen: false,  
		height: 'auto',  
		width: 400,
		modal: true,  
		close: cleanDataConfirmation, 
		buttons: {  
		    Eliminar: function() {  
				try {
					confirmarDelete();
				} catch(e) {
					jsError('confirmar delete',e.message);
				}	
           },  
           Cancelar: function() {  
        	   $(this).dialog('close');  
           	}  
        }
	}); 
}

// GRILLA
function loadUsuariosGrid(){
	var params = ""; 
	try {
		showGrid({
			id : 'gridUsuariosId',
			idPager : 'gridUsuariosPager',
			url : 'JsonUsuariosList.action?'+params,
			colNames : [ 'ID Usuario', 'User Name', 'Nombre', 'Apellido', 'PerfilId', 'Perfil', 'Cod Sector', 'Sector', 'Email' ],
			colModel : [ 
				{name : 'usuarioId'    , index : 'id', width : 0, align : 'left', hidden : true }, 
				{name : 'userName'     , width : 70, align : 'left', hidden : false }, 
				{name : 'nombre'       , width : 140, align : 'left', hidden : false }, 
				{name : 'apellido'     , width : 145, align : 'left', hidden : false }, 
				{name : 'perfil'       , width : 0, align : 'left', hidden : true }, 
				{name : 'perfilName'   , width : 70, align : 'left', hidden : false },
				{name : 'cdSector'     , width : 70, align : 'left', hidden : false }
				,{name : 'nbSector'     , width : 120, align : 'left', hidden : false }
				,{name : 'email'     , width : 230, align : 'left', hidden : false }
			],
			rowNum : 2000,
//			rowList : [ 10, 15, 20 ],
			sortName : '',
			caption : "Usuarios ",
			height : '100%',
			width : '100%',
			multiselect: false,
			loadonce : true,
			loadCompleteFunc :'',
			gridComplete: function() {
				rows= jQuery("#gridUsuariosId").getDataIDs();
				if (rows.length == 0) {
			    	  height = 0; //23.52 * rowData.length; 
			       } else  if (rows.length <= 30){
			    	   height = '100%'; //23.52 * rowData.length; 
			       }else{
			    	   height = 400;
			       }
			       
				$("#gridUsuariosId").jqGrid("setGridHeight",height);
				
			},
            editurl: 'clientArray'
//	        ,ondblClickRow: function(rowid, iRow, iCol, e) {
//					viewDialogEditUsuario(rowid);
//	        }
		});
		$('.ui-jqgrid-title').css('width', '98%');
//		addButtonsToUsuarioGrid();
		
	} catch(e) {
		jsError('loadUsuariosGrid(...)', e);
	}
}

// AGREGA BOTONERA DE LA GRILLA
function addButtonsToUsuarioGrid(){
//	if($('#grantABMCreate').val()=='Y'){
		var title = 'Agregar Usuario';
		$('#gridUsuariosId').navButtonAdd('#gridUsuariosPager', {
			caption: 'Agregar',
			buttonicon: 'ui-icon ui-icon-plus',
			onClickButton: function() {
				try {
					$('#gridUsuariosId').resetSelection();
					processAddUsuario('Editar Usuario');
				} catch (e) {
					jsError('loadUsuariosGrid(...)', e);
				}
			}, 
			position:'last', 
			title:title
		});
//	}
//	if($('#grantABMEdit').val()=='Y') {
		var title = 'Editar Usuario';
		$('#gridUsuariosId').navButtonAdd('#gridUsuariosPager', {
			caption: 'Editar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromUsuarioGrid(); 
					if (rowid != null) {
						processEditUsuario(rowid, false, 'Agregar Usuario');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadUsuariosGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
//	}
//	if($('#grantABMEdit').val()=='Y') {
		var title = 'Eliminar Usuario';
		$('#gridUsuariosId').navButtonAdd('#gridUsuariosPager', {
			caption: 'Eliminar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromUsuarioGrid(); 
					if (rowid != null) {
						processDeleteUsuario(rowid, false, 'Eliminar Usuario');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadUsuariosGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
//	}		
}

// FUNCTIONES GENERALES GRILLA
function reloadUsuariosGrid(){
	$('#gridUsuariosId').trigger('reloadGrid');
}

function getSelRowFromUsuarioGrid() { 
	return $("#gridUsuariosId").getGridParam('selrow');
}

function resetUsuarioForm() {
	$(':input','#dialogEditUsuario').not('input[type=hidden], :button').val('').removeAttr('checked').removeAttr('selected');
}

//INSERT PRODUCTO
function processAddUsuario(sTitle) {
	cleanDataConfirmation();
    destroyCombos();

    $("#tipoModificacion").val("A");
	$("#perfilList").selectmenu('setValue', 0);
	$("#sectorList").selectmenu('setValue', 0);
	$('#dialogEditUsuario').dialog('option','title',sTitle);
	$('#dialogEditUsuario').dialog('open');
}

//EDIT PRODUCTO
function processEditUsuario(rowid, tipo, sTitle) {
	cleanDataConfirmation();
    destroyCombos();

	var usuarioId  = $('#gridUsuariosId').jqGrid('getCell', rowid, 'usuarioId');
	var userName   = $('#gridUsuariosId').jqGrid('getCell', rowid, 'userName');
	var perfil     = $('#gridUsuariosId').jqGrid('getCell', rowid, 'perfil');
	var nombre     = $('#gridUsuariosId').jqGrid('getCell', rowid, 'nombre');
	var apellido   = $('#gridUsuariosId').jqGrid('getCell', rowid, 'apellido');
	var cdSector   = $('#gridUsuariosId').jqGrid('getCell', rowid, 'cdSector');
	$("#tipoModificacion").val("E");

//	if (confirm('Modifica el Usuario seleccionado ?') == true) {	
		$("#usuarioId").val(usuarioId);
		$("#userName").val(userName);
		$("#nombre").val(nombre);
		$("#apellido").val(apellido);
		$("#perfilList").selectmenu('setValue', perfil);
		$("#sectorList").selectmenu('setValue', cdSector);
		
		$('#dialogEditUsuario').dialog('option','title',sTitle);
		$('#dialogEditUsuario').dialog('open');
//	}
}

function saveUsuario() {
	var params = '';
	var confirma = "S";
	var tipoModificacion = 	$("#tipoModificacion").val();
	if (tipoModificacion == "E") {
		params += 'opcion=2&usuarioId='+ parseInt($('#usuarioId').val());
	} else {
		params += 'opcion=1&usuarioId=0';		
	}
	params += '&userName='+$('#userName').val();
	params += '&nombre='+$('#nombre').val();
	params += '&apellido='+$('#apellido').val();
	params += '&perfil='+$("#perfilList").val();
	params += '&cdSector='+$("#sectorList").val();
	if (tipoModificacion == "E") {
		if (!(confirm('Confirma la modificaci\u00f3n del Usuario?'))) {
			confirma = "N";
		}
	}
	if (confirma == "S") {
		var err = "";
		if ($("#perfilList").val() == 0) {
			err += "El Perfil es obligatorio\n";
		}
		if ($('#nombre').val() == "") {
			err += "El Nombre es obligatorio\n";
		}
		if ($('#apellido').val() == "") {
			err += "El Apellido es obligatorio\n";
		}
		if ($("#userName").val() == 0) {
			err += "El userName es obligatorio\n";
		}
		if ($("#sectorList").val() == 0) {
			err += "El sector es obligatorio\n";
		}
		if (err != "") {
			alert("Verifique los datos ingresados\n\n" + err);
		} else {
			callJsonAction('saveUsuario.action', params, 'successSaveUsuario','errorSaveUsuario');
		}
	}
}

function successSaveUsuario(json){	 
	if (isSuccessResult(json.result.errorCode)){		
		setSuccessMsg($('#usuario_responseMsgs'),"Editado Exitosamente");
		$('#usuario_responseMsgs').show();
		$('#dialogEditUsuario').dialog('close');
		resetUsuarioForm();
		search();
	} else {		
		setErrorMsg($('#usuario_responseMsgs'),json.result.errorDesc);
		$('#usuario_responseMsgs').show();
	}	
	cleanDataConfirmation();
}

function errorSaveUsuario(errorCode, errorDesc){
	setErrorMsg($('#usuario_responseMsgs'),errorDesc);
	$('#usuario_responseMsgs').show();	
	$('#dialogEditUsuario').dialog('close');
	resetUsuarioForm();
	cleanDataConfirmation();
}

// DELETE PRODUCTO
function processDeleteUsuario(rowid, tipo, sTitle) {
	cleanDataConfirmation();
	var usuarioId  = $('#gridUsuariosId').jqGrid('getCell', rowid, 'usuarioId');
	var userName   = $('#gridUsuariosId').jqGrid('getCell', rowid, 'userName');
	var nombre     = $('#gridUsuariosId').jqGrid('getCell', rowid, 'nombre');
	var apellido   = $('#gridUsuariosId').jqGrid('getCell', rowid, 'apellido');
	var perfilName = $('#gridUsuariosId').jqGrid('getCell', rowid, 'perfilName');
	var cdSector   = $('#gridUsuariosId').jqGrid('getCell', rowid, 'cdSector');

	$("#usuarioId").val(usuarioId);
	$("#bajaUserName").val(userName);
	$("#bajaNombre").val(nombre);
	$("#bajaApellido").val(apellido);
	$("#bajaPerfilName").val(perfilName);

	$('#dialogDeleteUsuario').dialog('option','title',sTitle);
	$('#dialogDeleteUsuario').dialog('open');
}

function confirmarDelete(){	
	var params = 'usuarioId='+$('#usuarioId').val();
	if (confirm('Confirma la baja del Usuario?')) {
		callJsonAction('deleteUsuario.action',params, 'successDeleteUsuario', 'errorDeleteUsuario');
	}
}

function successDeleteUsuario(json){	 
	if (isSuccessResult(json.result.errorCode)){		
		setSuccessMsg($('#usuario_responseMsgs'),"Eliminado Exitosamente");
		$('#usuario_responseMsgs').show();
		$('#dialogDeleteUsuario').dialog('close');
		search();
	} else {		
		setErrorMsg($('#usuario_responseMsgs'),json.result.errorDesc);
		$('#usuario_responseMsgs').show();
	}	
	cleanDataConfirmation();
}

function errorDeleteUsuario(errorCode, errorDesc){
	setErrorMsg($('#usuario_responseMsgs'),errorDesc);
	$('#usuario_responseMsgs').show();	
	$('#dialogDeleteProducto').dialog('close');
	cleanDataConfirmation();
}

function cleanDataConfirmation(){
	$('#usuarioId').val('');
	$('#perfil').val('');
	$('#perfilName').val('');
	$('#userName').val('');
	$('#nombre').val('');
	$('#apellido').val('');
	$('#cdSector').val('');
}

function destroyCombos() {
   $('#perfilList').selectmenu('destroy');	
   $('#perfilList').selectmenu({style:'dropdown', width:'200px'});

   $('#sectorList').selectmenu('destroy');	
   $('#sectorList').selectmenu({style:'dropdown', width:'200px'});
}

function dateFormat_serviceToJs(fecha){
	var value = "";
	if (fecha != null && fecha != "") {
		value = fecha.substr(8,2)+'/'+fecha.substr(5,2)+'/'+fecha.substr(0,4);
	}
	return value;
}


