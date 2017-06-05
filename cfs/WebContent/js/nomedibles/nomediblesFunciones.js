//
/*--------------------------------------------------------------------------------------------------------*/
// FUNCIONES COMUNES A LAS CONCILIACIONES 
/*--------------------------------------------------------------------------------------------------------*/

/*--------------------------------------------------------------------------------------------------------*/
//INICIALIZA LOS DIALOGOS QUE SE UTILIZAN EN EL CIRCUITO DE CONCILIACIONES
/*--------------------------------------------------------------------------------------------------------*/
function initDialog() {
	$("#dialogConsultaServicio").dialog({
		bgiframe: true,
		autoOpen: false,
		modal: true,
		width: 650,
		height:400,
		buttons: [ 
   	        {   id: 'cancelar-button',
   	            text: "Cancelar",
   	        	click: function() {
	            	$(this).dialog('close');
            	}
	        }
		]
	});
	$("#dialogDiferenciaConciliacion").dialog({
		bgiframe: true,
		autoOpen: false,
		modal: true,
		width: 500,
		height:280,
		buttons: [ 
			{   id: 'guardar-button',
				text: "Grabar",
				click: function() {
			       try {
					  saveDiferenciaConciliacion();
				   } catch(e) {
					  jsError('saveDiferenciaConciliacion(...)',e.message);
				   }
				}
			},
		    {   id: 'cancelar-button',
   	            text: "Cancelar",
   	        	click: function() {
	            	$(this).dialog('close');
            	}
	        }
		]
	});	
}

/*--------------------------------------------------------------------------------------------------------*/
//INSERTA O MODIFICA DIFERENCIA CONCILIACION 
//GRABA EN LA GRILLA, SOLO SE GRABA EN LA TABLA TCF014_DIFCONCILIA CUANDO SE GRABA LA CONCILIACION
/*--------------------------------------------------------------------------------------------------------*/
function saveDiferenciaConciliacion() {
	var rowid           = $("#rowIdDiferencia").val();
	var nbObservaciones = $("#nbObservaciones").val(); 
    var tpSolucion      = $("#solucionDifListEdit").val();
	var chkid;
    if ($.trim(nbObservaciones) == "" || tpSolucion == '') {
    	alert("Debe ingresar las Observaciones y la Situación");
    	return;
    }
	if (nbObservaciones != "") {
		imDiferencia = 0;
	}
	if (rowid == 9999999) {
        var rowData = $("#gridConciliacionesId").getDataIDs();
        for (var i = 0; i < rowData.length; i++) {
			var rowid = rowData[i];
			var chkDif = $('#gridConciliacionesId').getCell(rowid, 'chkDif');
			if (!$("#chkDif_"+i).is(":disabled")) {
//			if (chkDif == '1') {
			   chkid = rowid-1;
//             $('#gridConciliacionesId').jqGrid('setCell', rowid, 'imDiferencia'    , imDiferencia);
               $('#gridConciliacionesId').jqGrid('setCell', rowid, 'nbObservaciones' , nbObservaciones);
               $('#gridConciliacionesId').jqGrid('setCell', rowid, 'tpSolucion'      , tpSolucion);
               $("#gridConciliacionesId").jqGrid('setRowData', rowid, false, {color:'blue'});   
 	           $("#chkDif_"+chkid).attr("disabled", "disabled");
           }
        }	
	} else {
		chkid = rowid-1;
//		$('#gridConciliacionesId').jqGrid('setCell', rowid, 'imDiferencia'    , imDiferencia);
		$('#gridConciliacionesId').jqGrid('setCell', rowid, 'nbObservaciones' , nbObservaciones);
        $('#gridConciliacionesId').jqGrid('setCell', rowid, 'tpSolucion'      , tpSolucion);
		$('#gridConciliacionesId').jqGrid('setCell', rowid, 'idChkDif' , 0);		
		$("#gridConciliacionesId").jqGrid('setRowData', rowid, false, {color:'blue'});
        $("#chkDif_"+chkid).attr("disabled", "disabled");		
	}
	$('#responseMessageToEdit').show();
	$('#dialogDiferenciaConciliacion').dialog('close');
	
	$("#nbObservacGeneral").val(nbObservaciones); 
	$("#tpSolucionGeneral").val(tpSolucion); 
	$("#rowIdDiferencia").val(0); 
	
	Totaliza();
}
/*--------------------------------------------------------------------------------------------------------*/
// CALCULA LOS TOTALES DE LAS GRILLAS Y EL SALDO DE CONCILIACION Y HABILITA/DESHABILITA LOS BOTONES DE
// GRABAR Y APROBAR, Y LA FUNCIONALIDAD DE EDITAR DIFERENCIAS DE ACUERDO A LAS CONDICIONES ESTABLECIDAS
/*--------------------------------------------------------------------------------------------------------*/
function Totaliza() {
	var sumCtServFactAnt    = 0;
    var sumImPrecioTotalAnt = 0;
    var sumCtServFactAct    = 0;
    var sumImPrecioTotalAct = 0;
    var sumImDiferencia     = 0;
    var sumDifDesvio        = 0;
    var rows = $('#gridConciliacionesId').jqGrid('getRowData');
    for (var i=0; i<rows.length; i++) {
    	var row = rows[i];
        if (!$("#chkDif_"+i).is(":disabled")) {
        	sumDifDesvio += Number(removeCommas(row['imDiferencia']));
    	}
        sumCtServFactAnt    += Number(removeCommas(row['ctServFactAnt']));
        sumImPrecioTotalAnt += Number(removeCommas(row['imPrecioTotalAnt']));  
        sumCtServFactAct    += Number(removeCommas(row['ctServFactAct']));
        sumImPrecioTotalAct += Number(removeCommas(row['imPrecioTotalAct']));
        sumImDiferencia     += Number(removeCommas(row['imDiferencia']));
    }
    $('#gridConciliacionesId').jqGrid('footerData','set',{ctServFactAnt    : sumCtServFactAnt    ,
	                                                      imPrecioTotalAnt : sumImPrecioTotalAnt ,
                                                          ctServFactAct    : sumCtServFactAct    , 
                                                          imPrecioTotalAct : sumImPrecioTotalAct , 
                                                          imDiferencia     : sumImDiferencia
                                                         }, true); 
														 
//    $('#difDesvio').val(sumDifDesvio.toFixed(2));
    var desvio = sumDifDesvio;    
    $('#difDesvio').val(convierteNumero(sumDifDesvio,2));
    var stConciliacion = $('#stConciliacion').val();
    if ((stConciliacion != 'APR')) {
	    if (desvio == 0) {	
	        $("#btnAprobar").removeClass('ui-state-disabled');
	        $("#btnAprobar").removeAttr("disabled");
	    } else {
	        $("#btnAprobar").addClass('ui-state-disabled');
	        $("#btnAprobar").attr("disabled", "disabled");
	    }
    }
}

/*--------------------------------------------------------------------------------------------------------*/
// DADO QUE EN LAS CANTIDADES E IMPORTES DE SERVICIOS PRESTADOS Y FACTURADOS SE UTILIZA UN FORMATO ESPECIAL
// EN EL QUE SE AGREGA UNA COMA COMO SEPARACION DE MILES, SE DEBE SACAR PARA CONVERTIR A NUMERO, DE ESA 
// FORMA, SE SUMAN DICHAS COLUMNAS, COMO TAMBIEN SE CALCULAN LAS DIFRENCIAS Y SE OBTIENEN LOS VALORES PARA
// APROBAR LA CONCILIACION
/*--------------------------------------------------------------------------------------------------------*/
function removeCommas(numero) {
	if (numero == '') {
		return 0;
	} else {
		numero = numero.replace('.', '');
		return parseFloat(numero.replace(',', '.'));
	}
}

/*--------------------------------------------------------------------------------------------------------*/
// GRABAR CONCILIACION
/*--------------------------------------------------------------------------------------------------------*/

/*--------------------------------------------------------------------------------------------------------*/
//FUNCIONES PARA GRABAR - BOTONES
/*--------------------------------------------------------------------------------------------------------*/
function grabar() {
	$('#anConciliacion').val($('#stConciliacion').val());
	$('#stConciliacion').val('GRA');
	$('#nbConciliacion').val('GRABADA');
	saveConciliacion('GRA');	
}

function aprobar() {
	$('#anConciliacion').val($('#stConciliacion').val());
	$('#stConciliacion').val('APR');
	$('#nbConciliacion').val('APROBADA');
	saveConciliacion('APR');
    $("#btnGrabar").addClass('ui-state-disabled');
    $("#btnGrabar").attr("disabled", "disabled");	
    $("#btnAprobar").addClass('ui-state-disabled');
    $("#btnAprobar").attr("disabled", "disabled");	
}

function saveConciliacion(estado) {
	var listaNom = '';
   
	var rows = $('#gridConciliacionesId').jqGrid('getRowData');
	for (var i = 0; i < rows.length; i++) {
		var row = rows[i];
		if (listaNom != '') {
			listaNom += '|';
		}
		listaNom +=  row['cdProducto'] + ':' + $.trim(row['nbObservaciones']) + ':' + row['tpSolucion'];
	}

	var params = '';
	params += 'cdConciliacion='+$('#conciliacion').val();
	params += '&cdProveedor='+$('#cdProveedor').val();
	params += '&cdSector='+$('#cdSector').val();
	params += '&cdPeriodo='+$('#cdPeriodo').val();
	params += '&stConciliacion='+estado;
	params += '&listaNom='+listaNom;

	// PARA DEBUGEAR LOS PARAMETROS QUE SE ENVIAN PARA GRABAR
// 	$('#divParametros').show();
// 	$('#parametrosGrabar').val(params);
// 	alert(params);
	var confirmo_grabacion = 'S';
	var mensaje = '';
	if (estado == 'APR') {
		mensaje = 'Aprueba conciliacion?';
	}
	if (mensaje != '') {
		if (!confirm(mensaje)) {
			confirmo_grabacion = 'N';
			$("#grabada").val('N');
			$('#stConciliacion').val($('#anConciliacion').val());
			setDescripcionEstado($('#stConciliacion').val());     	 
		}
	}
	if (confirmo_grabacion == 'S') {
		$("#grabada").val('S');
		$('#modificada').val("N");
		callJsonActionPost('saveNoMedibles.action', params, 'successSaveConciliacion','errorSaveConciliacion');
		if ((estado == 'APR')) {
			$("#checkAll").addClass('ui-state-disabled');
			$("#checkAll").attr("disabled", "disabled"); 
			$("#btnObsAll").addClass('ui-state-disabled');
			$("#btnObsAll").attr("disabled", "disabled");
			$(".habilitar").attr("checked", false);
			$(".habilitar").attr("disabled", "disabled");
			$(".lapiz").attr("disabled", "disabled");
		
			var rowData = $("#gridConciliacionesId").getDataIDs();
			for (var i = 0; i < rowData.length; i++) {	
				var cl = rowData[i];
				$("#gridConciliacionesId").jqGrid('setCell', cl, 'idChkDif', '', 'not-editable-cell');					
			}
		}
	}	 
}

function successSaveConciliacion(json){
	var conciliacion = json.Cd_Conciliacion;	
	if (isSuccessResult(json.result.errorCode)){		
//	   	alert(json.Cd_Conciliacion);
		var rowData = $("#gridConciliacionesId").getDataIDs();
		for (var i = 0; i < rowData.length; i++) {	
			var cl = rowData[i];
	        $('#gridConciliacionesId').jqGrid('setCell', cl, 'cdConciliacionAct', json.Cd_Conciliacion);			
		}
        reloadConciliacionesGrid();
        $('#conciliacionesGrid').hide();
		alert("La conciliacion " + conciliacion + " fue grabada exitosamente");		
	} else {
		alert(json.result.errorDesc);
	}  
}

function errorSaveConciliacion(errorCode, errorDesc){
	alert(errorDesc);
}

var STR_PAD_LEFT = 1;
var STR_PAD_RIGHT = 2;
var STR_PAD_BOTH = 3;

function pad(str, len, pad, dir) {
    if (typeof(len) == "undefined") { var len = 0; }
    if (typeof(pad) == "undefined") { var pad = ' '; }
    if (typeof(dir) == "undefined") { var dir = STR_PAD_RIGHT; }
    if (len + 1 >= str.length) {
        switch (dir){
            case STR_PAD_LEFT:
                str = Array(len + 1 - str.length).join(pad) + str;
            break;
            case STR_PAD_BOTH:
                var right = Math.ceil((padlen = len - str.length) / 2);
                var left = padlen - right;
                str = Array(left+1).join(pad) + str + Array(right+1).join(pad);
            break;
            default:
                str = str + Array(len + 1 - str.length).join(pad);
            break;
        }
    }
    return str;
}

function setDescripcionEstado(estado) {
	switch(estado) {
	case 'GRA':
		$('#nbConciliacion').val('GRABADA SIN APROBAR');
		break;
	case 'APR':
		$('#nbConciliacion').val('APROBADA');
		break;
	default:
		$('#nbConciliacion').val('PENDIENTE');
		break;
	}
}

