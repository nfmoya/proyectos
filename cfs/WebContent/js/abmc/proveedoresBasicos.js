$(document).ready(function(){
	destroyCombos();	
	
//    $('input[type="text"]').each(function() {
//        $(this).setMask($(this).attr('alt'));
//    });
		
	initProveedorBasicoDialog();
	cleanDataConfirmation();
	
	search();	
	
});

function search() {
	
	$('#agregarGrant').val('S');
	limpiarGrilla('gridProveedoresBasicosId', 'gridProveedoresBasicosPager', 'gridProveedoresBasicos');

	$('#proveedoresBasicosGrid').show();
	$('#botonera').show();
	$('#buttonCancelar').button({
		icons : {
			secondary : ""
		}
	}).click(function() {
		resetProveedorForm();
		cleanDataConfirmation();

	});
	loadProveedoresBasicosGrid();
}

function initProveedorBasicoDialog(){
	$("#dialogEditProveedor").dialog({
		bgiframe: true,
		autoOpen: false,
		modal: true,
		width: 500,
		
		close: reloadProveedoresBasicosGrid, 
		buttons: [ 
			{   id: "btnGuardar",
				text: "Guardar",
		        click: function() {
		        	try {
						saveProveedorBasico();
					} catch(e) {
						jsError('saveProveedorBasico(...)',e.message);
					}
				}
		    },
			{   id: "btnCancelar",
		    	text: "Cancelar",
   	        	click: function() {
 	        		$('#prov_basic_responseMsgs').hide();
					resetProveedorBasicoForm();
		    		cleanDataConfirmation();
	            	$(this).dialog('close');
            	}
	        }
		]
	});
}

// GRILLA
function loadProveedoresBasicosGrid(){
	var params = 'cdProveedor='+$('#filtroProveedorList').val();
	try {
		showGrid({
			id : 'gridProveedoresBasicosId',
			idPager : 'gridProveedoresBasicosPager',
			url : 'JsonProveedoresList.action?'+params,
		
			colNames : [ 'C&oacute;digo', 'Nombre o Raz&oacute;n Social', 'Descr Corta', 'CUIT', 
			             'Habilitado', 'Precio Unitario', 'Suma Cant/Importe', 'Redondeo Medibles', 
			             'Redondeo No Medibles', 'Atributo 5' ],
			colModel : [ 
				{name : 'cdProveedor'      , index : 'id', width : 80, align : 'left', hidden : false }, 
				{name : 'nbProveedor'      , width :  200, align : 'left', hidden : false }, 
				{name : 'nbProveedorCorto' , width :  100, align : 'left', hidden : false }, 
				{name : 'nuCuit'           , width :   70, align : 'left', hidden : false }, 
				{name : 'stHabilitado'     , width :   60, align : 'center', hidden : false },
				{name : 'nbAtributoProv1'  , width :   60, align : 'left', hidden : false }, 
				{name : 'nbAtributoProv2'  , width :   60, align : 'left', hidden : false }, 
				{name : 'nbAtributoProv3'  , width :   60, align : 'left', hidden : false }, 
				{name : 'nbAtributoProv4'  , width :   60, align : 'left', hidden : false }, 
				{name : 'nbAtributoProv5'  , width :   60, align : 'left', hidden : true  } 
			],
			rowNum : 10,
			rowList : [ 10, 15, 20 ],
			sortName : 'cdProveedor',
			caption : "Proveedores Datos B&aacute;sicos ",
			height : '100%',
			width : 720,
			multiselect: false,
			loadonce : true,
			loadCompleteFunc : '',
            editurl: 'clientArray',
			shrinkToFit: true
		});
		$('.ui-jqgrid-title').css('width', '98%');
		addButtonsToProveedorBasicoGrid();
		$("#gridProveedoresBasicosId").jqGrid('setGridParam', 
			    {onSelectRow: function(rowid,iRow,iCol,e){asignoProveedor(rowid);}});
		
	} catch(e) {
		jsError('loadProveedoresBasicosGrid(...)', e);
	}
}

// AGREGA BOTONERA DE LA GRILLA
function addButtonsToProveedorBasicoGrid(){
	if($('#addGrant').val()=='S'){
		var title = 'Agregar Proveedor';
		$('#gridProveedoresBasicosId').navButtonAdd('#gridProveedoresBasicosPager', {
			caption: 'Agregar',
			buttonicon: 'ui-icon ui-icon-plus',
			onClickButton: function() {
				try {
					$('#gridProveedoresBasicosId').resetSelection();
					processEditProveedorBasico(0, false, 'Ingresar Proveedor', 'A');
				} catch (e) {
					jsError('loadProveedoresBasicosGrid(...)', e);
				}
			}, 
			position:'last', 
			title:title
		});
	}
	if($('#editGrant').val()=='S') {
		var title = 'Editar Proveedor';
		$('#gridProveedoresBasicosId').navButtonAdd('#gridProveedoresBasicosPager', {
			caption: 'Editar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromProveedorBasicoGrid(); 
					if (rowid != null) {
						processEditProveedorBasico(rowid, false, 'Editar Proveedor', 'E');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadProveedoresBasicosGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
	}
	if($('#deleteGrant').val()=='S') {
		var title = 'Eliminar Proveedor';
		$('#gridProveedoresBasicosId').navButtonAdd('#gridProveedoresBasicosPager', {
			caption: 'Eliminar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromProveedorBasicoGrid(); 
					if (rowid != null) {
						processEditProveedorBasico(rowid, false, 'Eliminar Proveedor', 'D');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadProveedoresBasicosGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
	}		
	if($('#selectGrant').val()=='S') {
		var title = 'Consultar Proveedor';
		$('#gridProveedoresBasicosId').navButtonAdd('#gridProveedoresBasicosPager', {
			caption: 'Consultar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromProveedorBasicoGrid(); 
					if (rowid != null) {
						processEditProveedorBasico(rowid, false, 'Consultar Proveedor', 'C');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadProveedoresBasicosGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
	}		
}

// FUNCTIONES GENERALES GRILLA
function reloadProveedoresBasicosGrid(){
	$('#gridProveedoresBasicosId').trigger('reloadGrid');
}

function getSelRowFromProveedorBasicoGrid() { 
	return $("#gridProveedoresBasicosId").getGridParam('selrow');
}

function resetProveedorBasicoForm() {
	$(':input','#dialogEditProveedor').not('input[type=hidden], :button').val('').removeAttr('checked').removeAttr('selected');
}

function asignoProveedor(rowid) {
	var cdProveedor   = $('#gridProveedoresBasicosId').jqGrid('getCell', rowid, 'cdProveedor');
	var nbProveedor   = $('#gridProveedoresBasicosId').jqGrid('getCell', rowid, 'nbProveedor');
	var stHab   = $('#gridProveedoresBasicosId').jqGrid('getCell', rowid, 'stHabilitado');
    $('#cdProveedorSeleccionado').val(cdProveedor);
    $('#nbProveedorSeleccionado').val(nbProveedor);
    $('#cdPeriodoFactSeleccionado').val('');
    $('#nbPeriodoFactSeleccionado').val('');
    if(stHab === 'S'){
    	$("#tabsApplication").enableTab(1);
    }else{
    	$("#tabsApplication").disableTab(1);
    }
	$("#tabsApplication").disableTab(2); 
	$("#tabsApplication").disableTab(3); 
}

//EDIT PRODUCTO
function processEditProveedorBasico(rowid, tipo, sTitle, tipoAcceso) {
	cleanDataConfirmation();
	cleanMsgConfirmation();
	destroyCombos();
	
	$("#tipoModificacion").val(tipoAcceso);
	 
    if (tipoAcceso == 'A') {
	    $('#cdProveedor').attr('disabled',false).removeClass("ui-state-disabled");
		$("#nbProveedor").attr('disabled',false).removeClass("ui-state-disabled");
		$("#nbProveedorCorto").attr('disabled',false).removeClass("ui-state-disabled");
		$("#nuCuit").attr('disabled',false).removeClass("ui-state-disabled");
//		$("#nbAtributoProv1").attr('disabled',false).removeClass("ui-state-disabled");
		$("#nbAtributoProv1").selectmenu('disabled',false).selectmenu('setValue', 'N');
		$("#nbAtributoProv2").selectmenu('disabled',false).selectmenu('setValue', 'N');
// 		$("#nbAtributoProv2").attr('disabled',false).removeClass("ui-state-disabled");
		$("#nbAtributoProv3").attr('disabled',false).removeClass("ui-state-disabled");
		$("#nbAtributoProv4").attr('disabled',false).removeClass("ui-state-disabled");
		$("#nbAtributoProv5").attr('disabled',false).removeClass("ui-state-disabled");
		$("#habilitadoListProv").selectmenu('disabled',false).selectmenu('setValue', 'S');
    }
    if (tipoAcceso != 'A') {
		var cdProveedor      = $('#gridProveedoresBasicosId').jqGrid('getCell', rowid, 'cdProveedor');
		var nbProveedor      = $('#gridProveedoresBasicosId').jqGrid('getCell', rowid, 'nbProveedor');
		var nbProveedorCorto = $('#gridProveedoresBasicosId').jqGrid('getCell', rowid, 'nbProveedorCorto');
		var nuCuit           = $('#gridProveedoresBasicosId').jqGrid('getCell', rowid, 'nuCuit');
		var nbAtributoProv1  = $('#gridProveedoresBasicosId').jqGrid('getCell', rowid, 'nbAtributoProv1');
		var nbAtributoProv2  = $('#gridProveedoresBasicosId').jqGrid('getCell', rowid, 'nbAtributoProv2');
		var nbAtributoProv3  = $('#gridProveedoresBasicosId').jqGrid('getCell', rowid, 'nbAtributoProv3');
		var nbAtributoProv4  = $('#gridProveedoresBasicosId').jqGrid('getCell', rowid, 'nbAtributoProv4');
		var nbAtributoProv5  = $('#gridProveedoresBasicosId').jqGrid('getCell', rowid, 'nbAtributoProv5');
		var stHabilitado     = $('#gridProveedoresBasicosId').jqGrid('getCell', rowid, 'stHabilitado');

		$("#cdProveedor").val(cdProveedor);
		$("#nbProveedor").val(nbProveedor);
		$("#nbProveedorCorto").val(nbProveedorCorto);
		$("#nuCuit").val(nuCuit);
		$("#nbAtributoProv1").val(nbAtributoProv1);
		$("#nbAtributoProv2").val(nbAtributoProv2);
		$("#nbAtributoProv3").val(nbAtributoProv3);
		$("#nbAtributoProv4").val(nbAtributoProv4);
		$("#nbAtributoProv5").val(nbAtributoProv5);
		$("#habilitadoListProv").val(stHabilitado);
		
	    $('#cdProveedor').attr('disabled',true).addClass("ui-state-disabled");
	    if (tipoAcceso != 'E' ) {
			$("#nbProveedor").attr('disabled',true).addClass("ui-state-disabled");
			$("#nbProveedorCorto").attr('disabled',true).addClass("ui-state-disabled");
			$("#nuCuit").attr('disabled',true).addClass("ui-state-disabled");
//			$("#nbAtributoProv1").attr('disabled',true).addClass("ui-state-disabled");
			$("#nbAtributoProv1").selectmenu('disabled',true);
			$("#nbAtributoProv2").selectmenu('disabled',true);
//			$("#nbAtributoProv2").attr('disabled',true).addClass("ui-state-disabled");
			$("#nbAtributoProv3").attr('disabled',true).addClass("ui-state-disabled");
			$("#nbAtributoProv4").attr('disabled',true).addClass("ui-state-disabled");
			$("#nbAtributoProv5").attr('disabled',true).addClass("ui-state-disabled");
			$("#habilitadoListProv").selectmenu('disabled',true);
	    } else {
			$("#nbProveedor").attr('disabled',false).removeClass("ui-state-disabled");
			$("#nbProveedorCorto").attr('disabled',false).removeClass("ui-state-disabled");
			$("#nuCuit").attr('disabled',false).removeClass("ui-state-disabled");
//			$("#nbAtributoProv1").attr('disabled',false).removeClass("ui-state-disabled");
			$("#nbAtributoProv1").selectmenu('disabled',false);
			$("#nbAtributoProv2").selectmenu('disabled',false);
//			$("#nbAtributoProv2").attr('disabled',false).removeClass("ui-state-disabled");
			$("#nbAtributoProv3").attr('disabled',false).removeClass("ui-state-disabled");
			$("#nbAtributoProv4").attr('disabled',false).removeClass("ui-state-disabled");
			$("#nbAtributoProv5").attr('disabled',false).removeClass("ui-state-disabled");
			$("#habilitadoListProv").selectmenu('disabled',false);
	    }
	}    
	if (tipoAcceso == 'C') {
		$("#btnGuardar").hide();
	} else {
		$("#btnGuardar").show();
	}
	$('#dialogEditProveedor').dialog('option','title',sTitle);
	$('#dialogEditProveedor').dialog('open');
}

function cleanMsgConfirmation(){
	$('#producto_responseMsgs').hide();
	$('#producto_responseMsgs').val('');
	$('#msgEspera').hide();
	$('#msgEspera').val('');
	$('#msgConfirmacion').hide();
	$('#msgConfirmacion').val('');
	$('#prov_basic_responseMsgs').hide();
	$('#prov_basic_responseMsgs').val('');		
}
function saveProveedorBasico() {
	var params = '';
	var confirma = "S";
	var tipoModificacion = $("#tipoModificacion").val();
	if (tipoModificacion == "D") {
		confirmarDeleteProveedor();
	} else {
		if (tipoModificacion == "E") {
			params += 'opcion=2';
		} else {
			params += 'opcion=1';		
		}
		params += '&cdProveedor='+$('#cdProveedor').val();
		params += '&nbProveedor='+$('#nbProveedor').val();
		params += '&nbProveedorCorto='+$('#nbProveedorCorto').val();
		params += '&nuCuit='+$('#nuCuit').val();
		params += '&nbAtributoProv1='+$('#nbAtributoProv1').val();
		params += '&nbAtributoProv2='+$('#nbAtributoProv2').val();
		params += '&nbAtributoProv3='+$('#nbAtributoProv3').val();
		params += '&nbAtributoProv4='+$('#nbAtributoProv4').val();
		params += '&nbAtributoProv5='+$('#nbAtributoProv5').val();
		params += '&stHabilitado='+$("#habilitadoListProv").val();
		if (tipoModificacion == "E") {
			if (!(confirm('Confirma la modificaci\u00f3n del Proveedor?'))) {
				confirma = "N";
			}
		}
		if (confirma == "S") {
			var err = "";
			if ($.trim($("#cdProveedor").val()) == '') {
				err += "El C\u00f3digo es obligatorio\n";
			}
			if ($.trim($("#nbProveedor").val()) == '') {
				err += "El Nombre es obligatorio\n";
			}
			if ($.trim($("#nbProveedorCorto").val()) == '') {
				err += "La Descripci\u00f3n corta es obligatoria\n";
			}
			if ($.trim($("#habilitadoListProv").val()) == '') {
				err += "El Estado es obligatorio\n";
			}
			if ($.trim($("#nuCuit").val()) == '') {
				err += "El Cuit es obligatorio\n";
			}else{
				if (!ValidaCuit($('#nuCuit').val())) {
					err += "El Cuit es invalido\n";
				}
			}
			if ($.trim($("#nbAtributoProv3").val()) == '' || (!isNumber($.trim($("#nbAtributoProv3").val())))) {
				err += "El Redondeo Medibles debe ser completado y numérico\n";
			}
			if ($.trim($("#nbAtributoProv4").val()) == '' || (!isNumber($.trim($("#nbAtributoProv4").val())))) {
				err += "El Redondeo No Medibles debe ser completado y numérico\n";
			}
			
			if (err != "") {
				alert("Verifique los datos ingresados\n\n" + err);
			} else {
				callJsonAction('saveProveedor.action', params, 'successSaveProveedorBasico','errorSaveProveedorBasico');
			}
		}
	}
}

function ValidaCuit(sCUIT) {     
    var aMult = '5432765432'; 
    var aMult = aMult.split(''); 
     
    if (isNumber(sCUIT) == true){
       if (sCUIT && sCUIT.length == 11) { 
          aCUIT = sCUIT.split(''); 
          var iResult = 0; 
          for (var i = 0; i <= 9; i++) { 
             iResult += aCUIT[i] * aMult[i]; 
          } 
          iResult = (iResult % 11); 
          iResult = 11 - iResult; 
         
          if (iResult == 11) iResult = 0; 
          if (iResult == 10) iResult = 9; 
         
          if (iResult == aCUIT[10]) { 
             return true; 
          }
       }
    }  
    return false; 
} 

function isNumber(n) {
   return !isNaN(parseFloat(n)) && isFinite(n);
}

function successSaveProveedorBasico(json){	 
	if (isSuccessResult(json.result.errorCode)){		
		setSuccessMsg($('#proveedor_responseMsgs'),"Editado Exitosamente");
		$('#proveedor_responseMsgs').show();
		$('#dialogEditProveedor').dialog('close');
//		resetProveedorForm();
		cleanDataConfirmation();
//		callJsonAction('ABMCProveedoresBasicos.action', params, 'successLoadProveedorBasico','errorSaveProveedorBasico');
		
//		reloadProveedoresBasicosGrid();
		alert("Alta/Modificaci\u00f3n Exitosa");
		recargarProveedor();
	} else {		
		setErrorMsg($('#prov_basic_responseMsgs'),json.result.errorDesc);
		$('#prov_basic_responseMsgs').show();
	}	
}

function recargarProveedor() {
	callJsonAction("comboProveedorAction.action", "","successProveedor","errorProveedor");
}

function successProveedor(jsonData){
   try {
      document.getElementById('filtroProveedorList').options.length=0;
      $('#filtroProveedorList').selectmenu('destroy');
      if (jsonData.filtroProveedorList!=undefined){
    	  $('#filtroProveedorList').append(new Option("Todos los Proveedores", "0"));
         if (jsonData.filtroProveedorList.length>0){
            for (var i=0;i<jsonData.filtroProveedorList.length;i++){	
            	$('#filtroProveedorList').append(new Option((jsonData.filtroProveedorList[i]).desc, jsonData.filtroProveedorList[i].cod));
//               document.getElementById('filtroProveedorList')[document.getElementById('filtroProveedorList').options.length] = new Option((jsonData.filtroProveedorList[i]).desc, jsonData.filtroProveedorList[i].cod);
            }
         }
	      search();
	      $('#filtroProveedorList').selectmenu('destroy');
	      $('#filtroProveedorList').selectmenu({style:'dropdown', width : $('#filtroProveedorList').attr("width"), maxHeight:'200'});
//	         $('#filtroProveedorList').selectmenu('setValue', $("#cdProveedorSeleccionado").val());
      }
   } catch(e) {
      jsError('successProductos', e);
   } finally{
	   
   }
   
}

function errorProveedor(cod, des){
   jsError('errProveedor', des);
}
function errorSaveProveedorBasico(errorCode, errorDesc){
	setErrorMsg($('#proveedor_responseMsgs'),errorDesc);
	$('#proveedor_responseMsgs').show();	
	$('#dialogEditProveedor').dialog('close');
	resetProveedorForm();
	cleanDataConfirmation();
	reloadProveedoresBasicosGrid();	
}

function confirmarDeleteProveedor(){	
	var params = 'cdProveedor='+$('#bajaCdProveedor').val();
	if (confirm('Confirma la baja del Proveedor?')) {
		callJsonAction('deleteProveedor.action',params, 'successDeleteProveedorBasico', 'errorDeleteProveedorBasico');
	}
}

function successDeleteProveedorBasico(json){	 
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

function errorDeleteProveedorBasico(errorCode, errorDesc){
	setErrorMsg($('#proveedor_responseMsgs'),errorDesc);
	$('#proveedor_responseMsgs').show();	
	$('#dialogDeleteProducto').dialog('close');
	cleanDataConfirmation();
}

function cleanDataConfirmation(){
	$('#proveedor_responseMsgs').hide();
	$('#proveedor_responseMsgs').val('');
	$('#cdProveedor').val('');
	$('#nbProveedor').val('');
	$('#nbProveedorCorto').val('');
	$('#nuCuit').val('');
	$('#nbAtributoProv1').val('');
	$('#nbAtributoProv2').val('');
	$('#nbAtributoProv3').val('');
	$('#nbAtributoProv4').val('');
	$('#nbAtributoProv5').val('');
	$('#stHabilitado').val('');
}
//
//function cleanDataConfirmation(){	
//	$('#proveedor_responseMsgs').hide();
//	$('#proveedor_responseMsgs').val('');
//	$('#msgEspera').hide();
//	$('#msgEspera').val('');
//	$('#msgConfirmacion').hide();
//	$('#msgConfirmacion').val('');
//	$('#prov_basic_responseMsgs').hide();
//	$('#prov_basic_responseMsgs').val('');
//}

function dateFormat_serviceToJs(fecha){
	var value = "";
	if (fecha != null && fecha != "") {
		value = fecha.substr(8,2)+'/'+fecha.substr(5,2)+'/'+fecha.substr(0,4);
	}
	return value;
}

function destroyCombos() {
	$('#habilitadoListProv').selectmenu('destroy');	
	$('#habilitadoListProv').selectmenu({style:'dropdown', width:'200px'});	
	$('#nbAtributoProv1').selectmenu('destroy');	
	$('#nbAtributoProv1').selectmenu({style:'dropdown', width:'200px'});
	$('#nbAtributoProv2').selectmenu('destroy');	
	$('#nbAtributoProv2').selectmenu({style:'dropdown', width:'200px'});
}	

function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}