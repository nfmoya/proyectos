$(document).ready(function(){
	destroyCombos();
	
	$.ajaxSetup({
        cache : false
    });

	initialiseFiltroPeriodoValor();
// 	recargarPeriodo($('#cdProveedorSeleccionado').val(), $('#cdPeriodoFactSeleccionado').val());
	
	initProveedorValorDialog();
	cleanDataConfirmation();
	
	search();
	
});

function search() {
	$('#agregarGrantPVAL').val('S');
	limpiarGrilla('gridProveedoresValoresId', 'gridProveedoresValoresPager', 'gridProveedoresValores');

	$('#proveedoresValoresGrid').show();
	$('#botonera').show();
	$('#buttonCancelar').button({
		icons : {
			secondary : ""
		}
	}).click(function() {
		resetProveedorValorForm();
		cleanDataConfirmation();

	});
	loadProveedoresValoresGrid();
}

function initProveedorValorDialog(){
	$("#dialogEditProveedorValor").dialog({
		bgiframe: true,
		autoOpen: false,
		modal: true,
		width: 550,
		
		close: reloadProveedoresValoresGrid, 
		buttons: [ 
			{   id: "grabar-button",
				text: "Grabar",
		        click: function() {
		        	try {
						saveProveedorValor();
					} catch(e) {
						jsError('saveProveedorValor(...)',e.message);
					}
				}
		    },
   	        {   id:"cancelar-button",
		    	text: "Cancelar",
   	        	click: function() {
   	        		$('#prov_valor_responseMsgs').hide();
//   	        		setErrorMsg($('#prov_valor_responseMsgs'),json.result.errorDesc);
   	        		resetProveedorValorForm();
   	     			cleanDataConfirmation();
	            	$(this).dialog('close');
            	}
	        }
		]
	});
}

// GRILLA
function loadProveedoresValoresGrid(){
	var params = '';
	if (($("#cdProveedorSeleccionado").val() != '') && ($("#cdProveedorSeleccionado").val() != undefined)) {
	   params = 'cdProveedor='+$("#cdProveedorSeleccionado").val();
	   if (($("#cdPeriodoFactSeleccionado").val() != '') && ($("#cdPeriodoFactSeleccionado").val() != undefined)) {
		   params += '&cdPeriodo='+$("#cdPeriodoFactSeleccionado").val();
	   }
	} else {
       params = 'cdProveedor=999&cdPeriodo=999';
	}
	try {
		showGrid({
			id : 'gridProveedoresValoresId',
			idPager : 'gridProveedoresValoresPager',
			url : 'JsonProveedoresValoresList.action?'+params,
		
			colNames : [ 'C&oacute;d Proveedor', 'Per&iacute;odo', 'C&oacute;d Unid Valor', 'Valor Bruto Unid Val', 'Valor Neto Unid Val', 'Habilitado' ],
			colModel : [ 
				{name : 'cdProveedor'      , index : 'id', width : 100, align : 'left', hidden : true }, 
				{name : 'cdPeriodoFact'    , width : 100, align : 'left', hidden : true }, 
				{name : 'cdUniVal'         , width : 100, align : 'left', hidden : false }, 
				{name : 'nuValBrutoUniVal' , width : 120, align : 'right', hidden : false }, 
				{name : 'nuValNetoUniVal'  , width : 120, align : 'right', hidden : false }, 
				{name : 'stHabilitado'     , width : 50 , align : 'left', hidden : false }
			],
			rowNum : 10,
			rowList : [ 10, 15, 20 ],
			sortName : 'cdProveedor',
			caption : "Valores Monetarios de Unid Val",
			height : '100%',
			width : 720,
			multiselect: false,
			loadonce : true,
			loadCompleteFunc : '',
            editurl: 'clientArray',
			shrinkToFit: true
		});
		$('.ui-jqgrid-title').css('width', '98%');
		addButtonsToProveedorValorGrid();
	} catch(e) {
		jsError('loadProveedoresValoresGrid(...)', e);
	}
}

// AGREGA BOTONERA DE LA GRILLA
function addButtonsToProveedorValorGrid(){
	if($('#addGrantPVAL').val()=='S'){
		var title = 'Agregar Proveedor';
		$('#gridProveedoresValoresId').navButtonAdd('#gridProveedoresValoresPager', {
			caption: 'Agregar',
			buttonicon: 'ui-icon ui-icon-plus',
			onClickButton: function() {
				try {
					$('#gridProveedoresValoresId').resetSelection();
					processEditProveedorValor(0, false, 'Ingresar Valor', 'A');
				} catch (e) {
					jsError('loadProveedoresValoresGrid(...)', e);
				}
			}, 
			position:'last', 
			title:title
		});
	}
	if($('#editGrantPVAL').val()=='S') {
		var title = 'Editar Proveedor';
		$('#gridProveedoresValoresId').navButtonAdd('#gridProveedoresValoresPager', {
			caption: 'Editar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromProveedorValorGrid(); 
					if (rowid != null) {
						processEditProveedorValor(rowid, false, 'Editar Valor','E');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadProveedoresValoresGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
	}
	if($('#deleteGrantPVAL').val()=='S') {
		var title = 'Eliminar Proveedor';
		$('#gridProveedoresValoresId').navButtonAdd('#gridProveedoresValoresPager', {
			caption: 'Eliminar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromProveedorValorGrid(); 
					if (rowid != null) {
						processEditProveedorValor(rowid, false, 'Eliminar Valor', 'D');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadProveedoresValoresGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
	}
	if($('#selectGrantPVAL').val()=='S') {
		var title = 'Consultar Proveedor';
		$('#gridProveedoresValoresId').navButtonAdd('#gridProveedoresValoresPager', {
			caption: 'Consultar',
			buttonicon: 'ui-icon ui-icon-pencil',
			onClickButton: function() {
				try {
					var rowid = getSelRowFromProveedorValorGrid(); 
					if (rowid != null) {
						processEditProveedorValor(rowid, false, 'Consultar Valor', 'C');
					} else {
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('loadProveedoresValoresGrid(...)', e);
				}
			},
			position:'last',
			title:title
		});
	}
}

// FUNCTIONES GENERALES GRILLA
function reloadProveedoresValoresGrid(){
	$('#gridProveedoresValoresId').trigger('reloadGrid');
}

function getSelRowFromProveedorValorGrid() { 
	return $("#gridProveedoresValoresId").getGridParam('selrow');
}

function resetProveedorValorForm() {
	$(':input','#dialogEditProveedorValor').not('input[type=hidden], :button').val('').removeAttr('checked').removeAttr('selected');
}

//EDIT PRODUCTO
function processEditProveedorValor(rowid, tipo, sTitle, tipoAcceso) {
	cleanDataConfirmation();
	cleanMsgConfirmation();
	destroyCombos();
	
	$("#tipoModificacion").val(tipoAcceso);

    if (tipoAcceso == 'A') {
	    $('#periodoValorList').selectmenu('disabled',true); 
	    $('#uniValList').selectmenu('disabled',false);
        $("#nuValBrutoUniVal").attr('disabled',false).removeClass("ui-state-disabled");
        $("#nuValNetoUniVal").attr('disabled',false).removeClass("ui-state-disabled");
        $("#habilitadoValorList").selectmenu('disabled',false);	
		$("#proveedorValorList").selectmenu('setValue', $('#cdProveedorSeleccionado').val()); 
        $('#proveedorValorList').selectmenu('disabled',true);
		$("#periodoValorList").selectmenu('setValue', $('#cdPeriodoFactSeleccionado').val()); 
	    $('#periodoValorList').selectmenu('disabled',true);

		recargarPeriodo($('#cdProveedorSeleccionado').val(), $('#cdPeriodoFactSeleccionado').val());	    
    }
    if (tipoAcceso != 'A') {	
		var cdProveedor      = $('#gridProveedoresValoresId').jqGrid('getCell', rowid, 'cdProveedor');
		var cdPeriodoFact    = $('#gridProveedoresValoresId').jqGrid('getCell', rowid, 'cdPeriodoFact');
		var cdUniVal         = $('#gridProveedoresValoresId').jqGrid('getCell', rowid, 'cdUniVal');
		var nuValBrutoUniVal = $('#gridProveedoresValoresId').jqGrid('getCell', rowid, 'nuValBrutoUniVal');
		var nuValNetoUniVal  = $('#gridProveedoresValoresId').jqGrid('getCell', rowid, 'nuValNetoUniVal');
		var stHabilitado     = $('#gridProveedoresValoresId').jqGrid('getCell', rowid, 'stHabilitado');

		$("#proveedorValorList").selectmenu('setValue', cdProveedor);
		$("#periodoValorList").selectmenu('setValue', cdPeriodoFact); 
		$("#uniValList").selectmenu('setValue', cdUniVal);
        $("#nuValBrutoUniVal").val(nuValBrutoUniVal);
        $("#nuValNetoUniVal").val(nuValNetoUniVal);
        $("#habilitadoValorList").selectmenu('setValue', stHabilitado);

		recargarPeriodo($('#cdProveedorSeleccionado').val(), $('#cdPeriodoFactSeleccionado').val());	    

	    $('#proveedorValorList').selectmenu('disabled',true);
	    $('#periodoValorList').selectmenu('disabled',true);
	    $('#uniValList').selectmenu('disabled',true);
//	    $('#cdUniVal').attr('disabled',true).addClass("ui-state-disabled");

	    if (tipoAcceso == 'C' || tipoAcceso == 'D' ) {
	        $("#nuValBrutoUniVal").attr('disabled',true).addClass("ui-state-disabled");
	        $("#nuValNetoUniVal").attr('disabled',true).addClass("ui-state-disabled");
	        $("#habilitadoValorList").selectmenu('disabled',true);	        
	    } else {
	        $("#nuValBrutoUniVal").attr('disabled',false).removeClass("ui-state-disabled");
	        $("#nuValNetoUniVal").attr('disabled',false).removeClass("ui-state-disabled");
	        $("#habilitadoValorList").selectmenu('disabled',false);	        
	    }
	}
	if (tipoAcceso == 'C') {
		$(":button:contains('Grabar')").button().hide();				
	} else {
		$(":button:contains('Grabar')").button().show();				
	}
	$('#dialogEditProveedorValor').dialog('option','title',sTitle);
	$('#dialogEditProveedorValor').dialog('open');
}

function initialiseFiltroPeriodoValor(){
   var $proveedor = $('#cdProveedorSeleccionado');
   var $periodo   = $('#cdPeriodoFactSeleccionado');
   $proveedor.change(function(){
      hideCommonDataElements();
      recargarPeriodo($proveedor.val(), $periodo.val());
   });
   onchangeOptions('proveedorValorList');
}

function onchangeOptions(selectorId){
   $("#"+selectorId).change(function(){
      hideCommonDataElements();
   });
}

function hideCommonDataElements(){
   if ($('#proveedorValorList').val()=="0"){
	   resetPeriodoOption(' Sin Per&iacute;odo ', '0', false);	
   }
}

function recargarPeriodo(cdProveedor, cdPeriodo) {
    callJsonAction("comboProveedoresPeriodos.action","cdProveedor="+cdProveedor+"&cdPeriodo="+cdPeriodo,"successPeriodos","errorPeriodos");
}	

function successPeriodos(jsonData){		
   try {
      document.getElementById('periodoValorList').options.length=0;
      $('#periodoValorList').selectmenu('destroy');
      if (jsonData.ProveedorPeriodoList!=undefined){
    	  resetPeriodoOption(' Sin Per&iacute;odo ', '0', true);	

         if (jsonData.ProveedorPeriodoList.length>0){
            for (var i=0;i<jsonData.ProveedorPeriodoList.length;i++)
               document.getElementById('periodoValorList')[document.getElementById('periodoValorList').options.length] = new Option((jsonData.ProveedorPeriodoList[i]).desc, jsonData.ProveedorPeriodoList[i].cod);
            
            $('#periodoValorList').selectmenu('destroy');

            $('#periodoValorList').selectmenu({style:'dropdown', maxHeight:'200', width:'200px'}).selectmenu('disabled',true);
         }
/*         
       var selectobject=document.getElementById("periodoValorList");
       for (var i=0; i<selectobject.length; i++){
       }
       $('#periodoValorList').selectmenu('setValue', 'pe1   ');
*/
         $('#periodoValorList').selectmenu('setValue', $("#cdPeriodoFactSeleccionado").val());
         
      }
   } catch(e) {
      jsError('errorPeriodos', e);
   }
}

function resetPeriodoOption(label, value, disabled){
   document.getElementById('periodoValorList').options.length=0;
   document.getElementById('periodoValorList')[0]= new Option(label,value);
   $('#periodoValorList').selectmenu('destroy');
   $('#periodoValorList').selectmenu({style:'dropdown', maxHeight:'200', width:'200px'}).selectmenu('disabled',true);
}

function errorPeriodos(cod, des){
   jsError('errPeriodo', des);
}

function saveProveedorValor() {
	var params = '';
	var confirma = "S";
	var tipoModificacion = 	$("#tipoModificacion").val();
	if (tipoModificacion == "D") {
		confirmarDeleteValor();
	} else {

		if (tipoModificacion == "E") {
			params += 'opcion=2';
		} else {
			params += 'opcion=1';		
		}
		params += '&cdProveedor='+$('#proveedorValorList').val();
		params += '&cdPeriodoFact='+$('#periodoValorList').val();
//		params += '&cdUniVal='+$('#cdUniVal').val();
		params += '&cdUniVal='+$('#uniValList').val();
		params += '&nuValBrutoUniVal='+$('#nuValBrutoUniVal').val();
		params += '&nuValNetoUniVal='+$('#nuValNetoUniVal').val();
		params += '&stHabilitado='+$("#habilitadoValorList").val();
		if (tipoModificacion == "E") {
			if (!(confirm('Confirma la modificaci\u00f3n del Valor?'))) {
				confirma = "N";
			}
		}
		if (confirma == "S") {
			var err = "";
			if ($("#proveedorValorList").val() == '0') {
				err += "El Proveedor es obligatorio\n";
			}
			if ($("#periodoValorList").val() == '0') {
				err += "El Periodo es obligatorio\n";
			}
			
			
			if ($("#uniValList").val() == '0') {
//			if ($("#cdUniVal").val() == '') {
				err += "El C\u00f3digo de Valor es obligatorio\n";
			}
			if ($.trim($("#nuValBrutoUniVal").val()) == '') {
				err += "El Valor Bruto es obligatorio\n";
			} else {
				if ($("#nuValBrutoUniVal").val() < '0') {
					err += "El Valor Bruto no puede ser negativo\n";
				}
			}
			if ($.trim($("#nuValNetoUniVal").val()) == '') {
				err += "El Valor Neto es obligatorio\n";
			} else {
				if ($("#nuValNetoUniVal").val() < '0') {
					err += "El Valor Neto no puede ser negativo\n";
				}
			}
			if ($.trim($("#habilitadoValorList").val()) == '') {
				err += "El Estado es obligatorio\n";
			}
			if (err != "") {
				alert("Verifique los datos ingresados\n\n" + err);
			} else {
				callJsonAction('saveProveedorValor.action', params, 'successSaveProveedorValor','errorSaveProveedorValor');
			}
		}
	}
}

function successSaveProveedorValor(json){	 
	if (isSuccessResult(json.result.errorCode)){		
		setSuccessMsg($('#proveedor_responseMsgs'),"Editado Exitosamente");
		$('#proveedor_responseMsgs').show();
		$('#dialogEditProveedorValor').dialog('close');
		resetProveedorForm();
		search();
		cleanDataConfirmation();
		search();
	} else {		
		setErrorMsg($('#prov_valor_responseMsgs'),json.result.errorDesc);
		$('#prov_valor_responseMsgs').show();
	}	
}

function errorSaveProveedorValor(errorCode, errorDesc){
	setErrorMsg($('#proveedor_responseMsgs'),errorDesc);
	$('#proveedor_responseMsgs').show();	
	$('#dialogEditProveedorValor').dialog('close');
	resetProveedorValorForm();
	cleanDataConfirmation();
}

function confirmarDeleteValor(){	
	var params = '';
	params += 'cdProveedor='+$('#proveedorValorList').val();
	params += '&cdPeriodoFact='+$('#periodoValorList').val();
//	params += '&cdUniVal='+$('#cdUniVal').val();
	params += '&cdUniVal='+$('#uniValList').val();
	if (confirm('Confirma la baja de la Unidad Valoraci\u00f3n?')) {
		callJsonAction('deleteProveedorValor.action', params, 'successDeleteProveedorValor', 'errorDeleteProveedorValor');
	}
}

function successDeleteProveedorValor(json){	 
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

function errorDeleteProveedorValor(errorCode, errorDesc){
	setErrorMsg($('#proveedor_responseMsgs'),errorDesc);
	$('#proveedor_responseMsgs').show();	
	$('#dialogDeleteProducto').dialog('close');
	cleanDataConfirmation();
}

function cleanDataConfirmation(){
//	$('#cdProveedor').val('');
//	$('#cdPeriodoFact').val('');
	$('#cdUniVal').val('');
	$('#nuValBrutoUniVal').val('');
	$('#nuValNetoUniVal').val('');
//	$('#stHabilitado').val('');
}
	
function cleanMsgConfirmation(){
	$('#proveedor_responseMsgs').hide();
	$('#proveedor_responseMsgs').val('');
	$('#msgEspera').hide();
	$('#msgEspera').val('');
	$('#msgConfirmacion').hide();
	$('#msgConfirmacion').val('');
	$('#prov_valor_responseMsgs').hide();
	$('#prov_valor_responseMsgs').val('');
}

function dateFormat_serviceToJs(fecha){
	var value = "";
	if (fecha != null && fecha != "") {
		value = fecha.substr(8,2)+'/'+fecha.substr(5,2)+'/'+fecha.substr(0,4);
	}
	return value;
}

function destroyCombos() {
	$('#habilitadoValorList').selectmenu('destroy');	
	$('#habilitadoValorList').selectmenu({style:'dropdown', maxHeight:'200', width:'200px'});	

	$('#proveedorValorList').selectmenu('destroy');	
	$('#proveedorValorList').selectmenu({style:'dropdown', maxHeight:'200', width:'200px'});

	$('#periodoValorList').selectmenu('destroy');	
	$('#periodoValorList').selectmenu({style:'dropdown', maxHeight:'200', width:'200px'});

	$('#uniValList').selectmenu('destroy');	
	$('#uniValList').selectmenu({style:'dropdown', maxHeight:'200', width:'200px'});

}
