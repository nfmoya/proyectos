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
		height:400,
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
	$("#dialogRemitosRepetidos").dialog({
		bgiframe: true,
		autoOpen: false,
		modal: true,
		width: 610,
		height:400,
		buttons: [ 
   	        {   id: 'cerrar-button',
   	            text: "Cerrar",
   	        	click: function() {
	            	$(this).dialog('close');
            	}
	        }
		]
	});
}

/*--------------------------------------------------------------------------------------------------------*/
// CALCULA LOS TOTALES DE LAS GRILLAS Y EL SALDO DE CONCILIACION Y HABILITA/DESHABILITA LOS BOTONES DE
// GRABAR Y APROBAR, Y LA FUNCIONALIDAD DE EDITAR DIFERENCIAS DE ACUERDO A LAS CONDICIONES ESTABLECIDAS
/*--------------------------------------------------------------------------------------------------------*/
function Totaliza() {
   var sumCtServPrest       = 0;
   var sumImPrecioTotalPres = 0;
   var sumCtServFact        = 0;
   var sumImPrecioTotalFact = 0;
   var sumCtServFactDife    = 0;
   var sumImServFactDife    = 0;
   var sumCtDiferencia      = 0;
   var sumImPrecioTotalDife = 0;
   var rows = $('#gridConciliacionesId').jqGrid('getRowData');
   for (var i = 0; i < rows.length; i++) {
      var row = rows[i];
      if (row['chkPres'] == 1 || $('#stConciliacion').val() == 'APR') {
    	  sumCtServPrest       += Number(removeCommas(row['ctServPrest']));
    	  sumImPrecioTotalPres += Number(removeCommas(row['imPrecioTotalPres']));
    	  
          sumCtServFactDife    -= Number(removeCommas(row['ctServPrest']));
          sumImServFactDife    -= Number(removeCommas(row['imPrecioTotalPres']));
      }
      if (row['chkFact'] == 1 || $('#stConciliacion').val() == 'APR') {
    	  sumCtServFact        += Number(removeCommas(row['ctServFact']));
    	  sumImPrecioTotalFact += Number(removeCommas(row['imPrecioTotalFact']));
    	  
          sumCtServFactDife    += Number(removeCommas(row['ctServFact']));
          sumImServFactDife    += Number(removeCommas(row['imPrecioTotalFact']));
      }
   }
   
   $('#gridConciliacionesId').jqGrid('footerData','set',{ctServPrest       : sumCtServPrest       ,
                                                         imPrecioTotalPres : sumImPrecioTotalPres ,
                                                         ctServFact        : sumCtServFact        , 
                                                         imPrecioTotalFact : sumImPrecioTotalFact , 
                                                         ctServFactDife    : sumCtServFactDife    , 
                                                         imServFactDife    : sumImServFactDife     
                                                        }, true);
   
   var rows = $('#gridDiferenciasId').jqGrid('getRowData');
   for (var i = 0; i < rows.length; i++) {
      var row = rows[i];
      if (row['stDiferencia'] == 'ACT') {
//       sumCtDiferencia      += Number(removeCommas(row['ctDiferencia']));
//       sumImPrecioTotalDife += Number(removeCommas(row['imPrecioTot']));
         sumCtDiferencia      += Number(row['ctDiferencia']);
         sumImPrecioTotalDife += Number(row['imPrecioTot']);
      }     
   }
   $('#gridDiferenciasId').jqGrid('footerData','set',{ctDiferencia: sumCtDiferencia, imPrecioTot: sumImPrecioTotalDife}, true);
   
   saldoCt = Math.round((sumCtServFactDife - sumCtDiferencia) * 100) / 100;
   saldoIm = Math.round((sumImServFactDife - sumImPrecioTotalDife) * 100) / 100;

   var rids  = $('#gridSaldosId').jqGrid('getDataIDs');
   var rowId = rids[0];
   var rowData = $('#gridSaldosId').jqGrid('getRowData', rowId);
   rowData.saldoCtDiferencia = saldoCt;
   rowData.saldoPrecioTotal  = saldoIm;
   $('#gridSaldosId').jqGrid('setRowData', rowId, rowData);	   

   var stIgnoraVal = ($('#stIgnoraVal').is(':checked')) ? 'S' : 'N';
   var stConciliacion = $('#stConciliacion').val();

// $("#btnRepetidos").removeClass('ui-state-disabled');
// $("#btnRepetidos").removeAttr("disabled");
      
   $("#btnGrabar").addClass('ui-state-disabled');
   $("#btnGrabar").attr("disabled", "disabled");
   $("#btnAprobar").addClass('ui-state-disabled');
   $("#btnAprobar").attr("disabled", "disabled");
   $("#add_dif").addClass('ui-state-disabled');
   $("#edit_dif").addClass('ui-state-disabled');

   if ($("#stPeriodo").val() == 'ABI' && $("#stProducto").val() == 'S' && $("#stProductoSector").val() == 'S') { 
      // Verifico que la conciliacion no esta grabada
      if (stConciliacion != 'APR') {
         //Verifico primero si hay registros seleccionados en la grilla de Conciliaciones
         if (sumCtServPrest != 0 || sumCtServFact != 0 || sumImPrecioTotalPres != 0 || sumImPrecioTotalFact != 0) {
            //if ($('#gridConciliacionesId').jqGrid('getGridParam','records') > 0) {
            if ($("#saveGrant").val() == 'S') {
	           $("#btnGrabar").removeClass('ui-state-disabled');
               $("#btnGrabar").removeAttr("disabled");
            }
            if ($("#differGrant").val() == 'S') {	   
               $("#add_dif").removeClass('ui-state-disabled');
               $("#edit_dif").removeClass('ui-state-disabled');
            }
            if ($("#approveGrant").val() == 'S') {
               // Si el Saldo de Cantidad e Importe son = 0 y no està aprobada, habilito el boton APROBAR
               if (saldoCt == 0 && saldoIm == 0) {
   	              $("#btnAprobar").removeClass('ui-state-disabled');
   	              $("#btnAprobar").removeAttr("disabled");
               } else {
  	              // Si el Saldo de Importe != 0, pero el de Cantidad = 0 admitiendo la aprobacion por 
	              // cantidad chequeando el campo stIgnoraVal, entonces se habilita el boton APROBAR
                  if (saldoCt == 0 && saldoIm != 0 && stIgnoraVal == 'S') {
                     $("#btnAprobar").removeClass('ui-state-disabled');
                     $("#btnAprobar").removeAttr("disabled");
                  } else {
                     $("#btnAprobar").addClass('ui-state-disabled');
                     $("#btnAprobar").attr("disabled", "disabled");
                  }
               }
            }
         }
      } 
   }   
}

/*--------------------------------------------------------------------------------------------------------*/
// DADO QUE EN LAS CANTIDADES E IMPORTES DE SERVICIOS PRESTADOS Y FACTURADOS SE UTILIZA UN FORMATO ESPECIAL
// EN EL QUE SE AGREGA UNA COMA COMO SEPARACION DE MILES, SE DEBE SACAR PARA CONVERTIR A NUMERO, DE ESA 
// FORMA, SE SUMAN DICHAS COLUMNAS, COMO TAMBIEN SE CALCULAN LAS DIFRENCIAS Y SE OBTIENEN LOS VALORES PARA
// APROBAR LA CONCILIACION
/*--------------------------------------------------------------------------------------------------------*/
//FUNCION CON DEBUG PARA LA CONSOLA
/*
function removeCommas(numero) {
	console.log(numero);
	debugger;
	   if (numero == '') {
		   return 0;
	   } else {
		   numero = numero.replace('.', '','g'); // 'g' es un flag que reemplaza TODO lo que encuentre
			console.log('replace: ' + numero);
			debugger;
		    var n = parseFloat(numero.replace(',', '.'));
		    console.log('parsefloat ' + n);
			debugger;
		    return n;
	   }
	}
*/
//original

function removeCommas(numero) {
	   if (numero == '') {
		   return 0;
	   } else {
		   numero = numero.replace(/\./g, '');
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
}

function saveConciliacion(estado) {
   // Se utiliza la variable msg_warn para verificar si hay algun elemento sin chequear
   // antes de aprobar la conciliacion, en el caso que haya, se muestra un mensaje de confirmacion
   var msg_warn  = '';
   var listaServ = '';
   var listaFact = '';
   var listaDife = '';
   var rows = $('#gridConciliacionesId').jqGrid('getRowData');
   // Arma los strings con los nros de lote y orden de Servicios Prestados y Facturados
   for (var i = 0; i < rows.length; i++) {
      var row = rows[i];
      if (row['chkPres'] == 1) {
          if (listaServ != '') {
        	  listaServ += '|';
          } 
          listaServ += row['cdLoteServ'] + ':' + row['cdOrdenServ'] + ':' + row['nbAtributoRef1'] + ':' + row['nbAtributoRef2'];
    	  if (estado == 'APR') {
    		  // Si  es aprobada envio los 2 valores para actualizar en la tabla de Servicios Prestados
    		  listaServ += ': ' + (row['imPrecioUnitPres']) + ':' + removeCommas(row['imPrecioTotalPres']);
    	  } else {
    		  // Si no es aprobada envio los 2 valores en cero
    		  listaServ += ':0:0';
    	  }
      } else {
    	  if (estado == 'APR') {
        	 if (Number(removeCommas(row['ctServPrest'])) != 0) {
            	msg_warn = 'S';
        	 }
    	  }
      }
      
      if (row['chkFact'] == 1) {
          if (listaFact != '') {
              listaFact += '|';
           } 
          listaFact += row['cdLoteFact'] + ':' + row['cdOrdenFact'];
      } else {
    	  if (estado == 'APR') {
        	 if (Number(removeCommas(row['ctServFact'])) != 0) {
                msg_warn = 'S';
        	 }
    	  }
      }
   }
   // Arma el string con los registros de las diferencias
   var rows = $('#gridDiferenciasId').jqGrid('getRowData');
   for (var i = 0; i < rows.length; i++) {
      var row = rows[i];
      if (listaDife != '') {
         listaDife += '|';
      } 
    /*
      listaDife += row['cdOrden'] + ':' + row['cdRemito'] + ':' + row['ctDiferencia'] + ':' + 
                   row['pzaDesde'] + ':' + row['pzaHasta'] + ':' + row['observacion'] + ':' + 
                   row['cdUniVal'] + ':' + row['imPrecioTot'] + ':' + row['tpSolucion'] + ':' + 
                   row['stDiferencia'] ;
     */ 
   // modifico a partir de aca - LM 22-10-15
      listaDife += row['cdOrden'] + ':' + row['cdRemito'] + ':' + removeCommas(row['ctDiferencia']) + ':' + 
      row['pzaDesde'] + ':' + row['pzaHasta'] + ':' + row['observacion'] + ':' + 
      row['cdUniVal'] + ':' + (removeCommas(row['imPrecioTot'])/100) + ':' + row['tpSolucion'] + ':' + 
      row['stDiferencia'] ;
   // fin modificacion LM 22-10-15
   }
   var stIgnoraVal = ($('#stIgnoraVal').is(':checked')) ? 'S' : 'N';

   var fhRemitoDesde = ($("#fhRemitoDesde").val() == '') ? '' : dateFormat_JsToService($("#fhRemitoDesde").val());
   var fhRemitoHasta = ($("#fhRemitoHasta").val() == '') ? '' : dateFormat_JsToService($("#fhRemitoHasta").val());
   var fhFinSerDesde = ($("#fhFinServicioDesde").val() == '') ? '' : dateFormat_JsToService($("#fhFinServicioDesde").val());
   var fhFinSerHasta = ($("#fhFinServicioHasta").val() == '') ? '' : dateFormat_JsToService($("#fhFinServicioHasta").val());
   
   var params = '';
   params += 'cdConciliacion='+$('#conciliacion').val();
   params += '&cdProveedor='+$('#cdProveedor').val();
   params += '&cdSector='+$('#cdSector').val();
   params += '&cdProducto='+$('#cdProducto').val();
   params += '&cdPeriodo='+$('#cdPeriodo').val();
   params += '&stIgnoraval='+stIgnoraVal;
   params += '&nbObservaciones='+$('#observaciones').val();
   params += '&stConciliacion='+estado;
   params += '&stConciliacion='+estado;
   params += '&fhRemitoDesde='+fhRemitoDesde;
   params += '&fhRemitoHasta='+fhRemitoHasta;
   params += '&fhFinServicioDesde='+fhFinSerDesde;
   params += '&fhFinServicioHasta='+fhFinSerHasta;
   params += '&listaServPres='+listaServ;
   params += '&listaServFact='+listaFact;
   params += '&listaServDife='+listaDife;

   // PARA DEBUGEAR LOS PARAMETROS QUE SE ENVIAN PARA GRABAR
   
//$('#divParametros').show();
//$('#parametrosGrabar').val(params);

   // Si se quiere aprobar, se debe confirmar, 
   // cambia el mensaje de acuerdo a si seleccionaron todos los registros
   var confirmo_grabacion = 'S';
   var mensaje = '';
   if (estado == 'APR') {
	   if (msg_warn != '') {
		   mensaje = 'Aprueba conciliacion sin seleccionar todos los registros?';
	   } else {
		   mensaje = 'Aprueba conciliacion?';
	   }
   }
   if (mensaje != '') {
      if (!confirm(mensaje)) {
         confirmo_grabacion = 'N';
         $("#grabada").val('N');
//       $('#modificada').val("N");
         // Si no aprueba la conciliacion, vuelvo al estado anterior
     	 $('#stConciliacion').val($('#anConciliacion').val());
     	 setDescripcionEstado($('#stConciliacion').val());     	 
      }
   }
   if (confirmo_grabacion == 'S') {
      $("#grabada").val('S');
      $('#modificada').val("N");  
//prompt("Copy to clipboard: Ctrl+C, Enter", params);	  
      callJsonAction('saveConciliacion.action', params, 'successSaveConciliacion','errorSaveConciliacion');
   }   
}

function successSaveConciliacion(json){
   var conciliacion = json.Cd_Conciliacion;
   
   if (isSuccessResult(json.result.errorCode)){		
      // Muestro del mensaje de grabacion con el Numero de Conciliacion
	   
//    setSuccessMsg($('#conciliacion_responseMsgs'),"La conciliacion " + conciliacion + " fue grabada exitosamente");
//    $('#conciliacion_responseMsgs').show();
      
      // Al APROBAR deshabilito los botones GRABAR y APROBAR y los botones de Diferencias, si solamente se GRABA
	  // deshabilito solo el boton APROBAR
	  if ($('#stConciliacion').val() == 'APR') {
	     $("#btnGrabar").addClass('ui-state-disabled');
	     $("#btnGrabar").attr("disabled", "disabled");
	     $("#add_dif").addClass('ui-state-disabled');
	     $("#edit_dif").addClass('ui-state-disabled');
	  }
      $("#btnAprobar").addClass('ui-state-disabled');	
      $("#btnAprobar").attr("disabled", "disabled");

      setDescripcionEstado($('#stConciliacion').val());
      
      // Actualiza el Nro de Conciliacion en los registros de la Grilla
      var rowData = $("#gridConciliacionesId").getDataIDs();
      for (var i = 0; i < rowData.length; i++) {
         var rw = rowData[i];
         // Limpio el Nro de Conciliacion en los registros de la Grilla
         $('#gridConciliacionesId').setCell(rw, 'cdConciliacionPres', '');
         $('#gridConciliacionesId').setCell(rw, 'cdConciliacionFact', '');
         
         var chkPres = $('#gridConciliacionesId').getCell(rw, 'chkPres');
         var chkFact = $('#gridConciliacionesId').getCell(rw, 'chkFact');
         if (chkPres == 1) {
            $('#gridConciliacionesId').setCell(rw, 'cdConciliacionPres', conciliacion);
         }
         if (chkFact == 1) {
            $('#gridConciliacionesId').setCell(rw, 'cdConciliacionFact', conciliacion);
         }
      }
      reloadConciliacionesGrid();
      //modifica LM para que limpie la pantalla completa
      $('#conciliacionesGrid').hide();
      $('#diferenciasGrid').hide();
      $('#saldosGrid').hide();
      $('#divObservaciones').hide();
      // fin modifica LM para que limpie la pantalla completa
      
      alert("La conciliacion " + conciliacion + " fue grabada exitosamente");
   } else {
      alert(json.result.errorDesc);
//	  setErrorMsg($('#conciliacion_responseMsgs'),json.result.errorDesc);
//    $('#conciliacion_responseMsgs').show();
   }
}

function errorSaveConciliacion(errorCode, errorDesc){
    alert(errorDesc);
//	setErrorMsg($('#conciliacion_responseMsgs'),errorDesc);
//	$('#conciliacion_responseMsgs').show();	
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

