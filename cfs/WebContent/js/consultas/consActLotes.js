/**
 * Inicilizacion de combos y botones de la pantalla
 * 
 * @author Alexis Comerci
 */
$(document).ready(function() {
	
	//fhAltaDesde fhAltaHasta
	$("#fhAltaDesde").mask("99/99/9999",{placeholder:" "});
	$("#fhAltaHasta").mask("99/99/9999",{placeholder:" "});
	
	$(':button').button();
	$('#btnBuscar').button({
		icons : {
			secondary : "ui-icon-search"
		}
	}).click(function() {
		search();
	});
	$('#btnExportar').button({
		icons : {
			secondary : "ui-icon-bookmark"
		}
	}).click(function() {
		alert('Exportar');
	});
	$('#btnImprimir').button({
		icons : {
			secondary : "ui-icon-print"
		}
	}).click(function() {
		alert('Imprimir');
	});
	$('#panelExport').show();
	destroyCombos();
	datepickers();
});

/**
 * Limpio los div ocultos los carteles si los hay y genero la consulta
 * 
 * @author Alexis Comerci
 */
function search() {
	$('#consActLotes_responseMsgs').hide();
	// Proceso que valida los datos para el search
	if (searchValidate()) {
		$("#tipoLoteAnular").val($('#tipoLoteList').val());
		limpiarGrilla('gridConsActLotesId', 'gridConsActLotesPager',
				'consActLotesGrid');
		searchConsActLotes();
	}
}

/**
 * Destruyo los combos de la pantalla y les setea el style
 * 
 * @author Alexis Comerci
 */
function destroyCombos() {
	destroyCombosGeneralOne('tipoLoteList');

	destroyCombosGeneralOne('proveedorList');

	destroyCombosGeneralOne('stLoteList');
}

function datepickers() {
	$("#fhAltaDesde")
			.datepicker({
				changeMonth : true,
				changeYear : true,
				regional : 'es',
				yearRange : 'c-100:c',
				dateFormat : 'dd/mm/yy',
				showAnim : '',
				showOn : "button",
				duration : 0
			})
			.next('button')
			.text('&nbsp;')
			.addClass('ui-button-text')
			.button({
				icons : {
					primary : 'ui-icon ui-icon-calendar'
				}
			})
			.removeClass()
			.addClass(
					'ui-button ui-widget ui-state-default ui-button-icon-only ui-corner-right ui-button-icon');

	$("#fhAltaHasta")
			.datepicker({
				changeMonth : true,
				changeYear : true,
				regional : 'es',
				yearRange : 'c-100:c',
				dateFormat : 'dd/mm/yy',
				showAnim : '',
				showOn : "button",
				duration : 0
			})
			.next('button')
			.text('&nbsp;')
			.addClass('ui-button-text')
			.button({
				icons : {
					primary : 'ui-icon ui-icon-calendar'
				}
			})
			.removeClass()
			.addClass(
					'ui-button ui-widget ui-state-default ui-button-icon-only ui-corner-right ui-button-icon');
}

/**
 * Funcion que ejecuta la busqueda de lotes
 * 
 * @author Alexis Comerci
 */
function searchConsActLotes() {
	var params = '';
	params += 'tipoLote=' + $('#tipoLoteList').val();
	params += '&cdProveedor=' + $('#proveedorList').val();
	params += '&fhAltaDesde=' + $('#fhAltaDesde').val();
	params += '&fhAltaHasta=' + $('#fhAltaHasta').val();
	params += '&stLote=' + $('#stLoteList').val();
	params += '&cdLote=' + $('#cdLote').val();

	loadConsActLotesGrid(params);
}

/**
 * Metodo que se encarga de cargar la grilla en pantalla
 * 
 * @param params
 * 
 * @author Alexis Comerci
 */
function loadConsActLotesGrid(params) {
	limpiarGrilla('gridConsActLotesId', 'gridConsActLotesPager',
			'consActLotesGrid');

	try {
		$('#gridConsActLotesId').jqGrid(
				{
					url : 'JsonConsActLotesList.action?' + params,
					datatype : 'json',
					mtype : 'POST',
					colNames : [ 'Nro. Lote', 'T. Inter.', 'Nomb. Archivo',
							'Proveedor', 'Est. Lote', 'Observaciones',
							'F. Alta', 'Usu. Alta', 'Anular' ],
					colModel : [ {
						name : 'cdLote',
						index : false,
						width : 70,
						align : 'right',
						hidden : false
					}, {
						name : 'tpInterfaz',
						width : 45,
						align : 'center',
						hidden : false
					}, {
						name : 'nbArchivo',
						width : 120,
						align : 'left',
						hidden : false
					}, {
						name : 'cdProveedor',
						width : 60,
						align : 'center',
						hidden : false
					}, {
						name : 'stLotecab',
						width : 50,
						align : 'center',
						hidden : false
					}, {
						name : 'nbObservaciones',
						width : 530,
						align : 'left',
						hidden : false
					}, {
						name : 'fhAlta',
						width : 63,
						align : 'center',
						hidden : false
					}, {
						name : 'nbUsuarioAlta',
						width : 55,
						align : 'left',
						hidden : false
					}, {
						name : 'anular',
						width : 10,
						align : 'left',
						hidden : true
					} ],
					jsonReader : {
						root : 'gridModelToShow',
						repeatitems : false,
						id : 'cdLote'
					},
					width : 1050,
					emptyrecords : 'Sin registros',
					rowNum : 0,
//					rowList : [ ],
					pager : '#gridConsActLotesPager',
					sortname : 'cdLote, fhAlta',
					viewrecords : true,
					sortorder : 'asc',
					shrinkToFit : false,
					caption : 'Consulta y Actualizaci&oacute;n de Lotes',
					height : '100%',
					onSelectRow : function(row_id) {
						var values = jQuery("#gridConsActLotesId").getRowData(
								row_id);
						showHideEdit(values['anular'], values['stLotecab']);
					}
					
					 ,gridComplete: function() {
						 var rows = jQuery("#gridConsActLotesId").getDataIDs();									
						 if (rows.length == 0) {
							 height = 0; //23.52 * rowData.length; 
          	             } else  if (rows.length <= 30){
          	            	   height = '100%'; //23.52 * rowData.length; 
          	             }else{
          	               height = 400;
          	             }
                            
						$("#gridConsActLotesId").jqGrid("setGridHeight",height);	              
        	           }
				});
		
		$('.ui-jqgrid .ui-jqgrid-bdiv').css('overflow-y', 'scroll');

		// Reload de la tabla para el display de los datos cargados
		$('#gridConsActLotesId').trigger('reloadGrid');

		// Saco los botones genericos de la grilla
		$("#gridConsActLotesId").navGrid('#gridConsActLotesPager', {
			refresh : false,
			edit : false,
			add : false,
			del : false,
			search : false
		});

		// Agrego los botones personalizados
		addButtonsToGrid();

		$(".ui-jqgrid-labels").css('font-size', '10px');
		$(".ui-pg-table").css('font-size', '10px');

	} catch (e) {
		jsError('loadConsActLotesGrid', e.message);
	}

	// Muestro la grilla en pantalla
	$('#consActLotesGrid').show();
}

/**
 * Retona la fila seleccionada
 * 
 * @returns int or NULL
 * 
 * @author Alexis Comerci
 */
function getSelRowGrid() {
	return $("#gridConsActLotesId").getGridParam('selrow');
}

/**
 * Agrega la botonera a la grilla
 * 
 * @author Alexis Comerci
 */
function addButtonsToGrid() {

	var permisoAnular; 
	
	if($('#tipoLoteList').val() == 'SP'){
		permisoAnular = $('#anularPrestGrant').val();
	}else{
		permisoAnular = $('#anularFactGrant').val();
	}	
	
	if (permisoAnular == 'S') {		
		$("#gridConsActLotesId").navGrid('#gridConsActLotesPager')
				.navButtonAdd(
						'#gridConsActLotesPager',
						{
							caption : 'Anular',
							buttonicon : 'ui-icon ui-icon-pencil',
							id : 'btnGridAnular',
							onClickButton : function() {
								try {
									var rowid = getSelRowGrid();

									if (rowid != null) {
										var values = jQuery(
												"#gridConsActLotesId")
												.getRowData(rowid);

										var nroLote = values['cdLote'];

										// Seteo lo valores en el popoUp
										$('#nroLoteAnular').val(nroLote);

										// Abro dialogo
										openAnularLoteDialog();
									}
								} catch (e) {
									jsError('addButtonsToGrid(...)', e);
								}
							},
							position : 'last',
							title : 'Anular'
						});

		$('#btnGridAnular').hide();
	}
}

/**
 * Si se puede anular el lote abre la ventana en modo dialog
 * 
 * @author Alexis Comerci
 */
function openAnularLoteDialog() {
	if ($('#puedeAnular').val() == "S")
		$('#dialogAnularLote').dialog('option', 'modal', true).dialog('open');
	else {
		fAlertCB("El lote no se puede anular debido a que tiene registros conciliados.");
	}
}

/**
 * Funcion que se ejecuta cada vez que seleccionamos una fila.
 * 
 * @param value -> Contenido de la columna Anular
 * 
 * @author Alexis Comerci
 */

function showHideEdit(anular, status) {
	if (status === 'ACT')
		$('#btnGridAnular').show();
	else
		$('#btnGridAnular').hide();
	
	if (anular === '1') {
		$('#puedeAnular').val("S");
	} else {
		$('#puedeAnular').val("N");
	}
}

/**
 * Declaracion de las propiedades del dialog de confirmar anulacion
 * 
 * @author Alexis Comerci
 */
$("#dialogAnularLote").dialog({
	bgiframe : true,
	autoOpen : false,
	height : 'auto',
	width : 350,
	modal : true,
	resizable : false,
	close : $(this).close,
	buttons : {
		Si : function() {
			try {
				anularLoteAction();
				$(this).dialog('close');
			} catch (e) {
				jsError('dialogAnularLote', e.message);
			}
		},
		No : function() {
			$('#dialogAnularLote').dialog('close');
			$(this).dialog('close');
		}
	}
});

/**
 * Valida los filtros
 * 
 * @returns {Boolean}
 * 
 * @author Alexis Comerci
 */
function searchValidate() {
	if ($.trim($('#tipoLoteList').val()) == '') {
		fAlertCB('El Tipo Lote debe ser informado');
		return false;
	} else if ($.trim($('#cdLote').val()) == '' || $('#cdLote').val() == '0') {
		if ($.trim($('#proveedorList').val()) == '') {
			fAlertCB('Debe informar Proveedor o Nro. Lote');
			return false;
		} else if ($('#fhAltaDesde').val() != ''
				&& $.trim($('#fhAltaHasta').val()) == '') {
			fAlertCB('Debe informar Fecha Alta Hasta si informa Fecha Alta Desde');
			return false;
		} else if ($.trim($('#fhAltaDesde').val()) == ''
				&& $('#fhAltaHasta').val() != '') {
			fAlertCB('Debe informar Fecha Alta Desde si informa Fecha Alta Hasta');
			return false;
		} else if ($('#fhAltaDesde').val() != ''
				&& $('#fhAltaHasta').val() != '') {
			var fhDesde = $('#fhAltaDesde').val();
			var fhHasta = $('#fhAltaHasta').val();
			var fhSplit = fhDesde.split('/');
			if(isDate(fhDesde)){
				if(!isDate(fhHasta)){
					fAlertCB('Fecha Alta Hasta inv\u00e1lida');	
					return false;			
				}
			}else{
				fAlertCB('Fecha Alta Desde inv\u00e1lida');		
				return false;		
			}
				
			fhDesde = fhSplit[2] + fhSplit[1] + fhSplit[0];
			var fhSplit = fhHasta.split('/');
			fhHasta = fhSplit[2] + fhSplit[1] + fhSplit[0];
			if (fhDesde > fhHasta) {
				fAlertCB('Fecha Alta Desde debe ser menor o igual a Fecha Alta Hasta');
				return false;
			} else{
				return true;
			};
		} else
			return true;
	} else {
		if (isNaN($('#cdLote').val()) && $('#cdLote').length > 10) {
			fAlertCB('Nro. Lote debe ser un n\u00famero de hasta 10 d\u00edgitos');
			return false;
		} else
			return true;
	}
}

/**
 * Acccion que carga el formbean con los parametros para anular y llama a la
 * accion
 * 
 * @param values
 * 
 * @author Alexis Comerci
 */
function anularLoteAction() {
	var params = '';
	params += 'tipoLote=' + $('#tipoLoteAnular').val();
	params += '&cdLote=' + $('#nroLoteAnular').val();

	callJsonAction('consActLotesAnular.action', params, 'successAnularLote',
			'errorAnularLote');
}

/**
 * Accion que salio bien el json de anular
 * 
 * @author Alexis Comerci
 */
function successAnularLote(json) {
	if (isSuccessResult(json.result.errorCode)) {
		setSuccessMsg($('#consActLotes_responseMsgs'), "Lote anulado exitosamente");
		$('#consActLotes_responseMsgs').show();
		searchConsActLotes();
	} else {
		setErrorMsg($('#consActLotes_responseMsgs'), json.result.errorDesc);
		$('#consActLotes_responseMsgs').show();
	}
}

/**
 * Accion que salio mal el json de anular
 * 
 * @author Alexis Comerci
 */
function errorAnularLote(errorCode, errorDesc) {
	setErrorMsg($('#consActLotes_responseMsgs'), errorDesc);
	$('#consActLotes_responseMsgs').show();
}