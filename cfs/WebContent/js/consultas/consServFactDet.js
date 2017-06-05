var expandido = "no";
$(document).ready(function() {
	
	destroyCombosGeneralOne('selectedProveedor');
	destroyCombosGeneralOne('selectedSector');
	destroyCombosGeneralOne('selectedProducto');
	destroyCombosGeneralOne('selectedGrupo');
	destroyCombosGeneralOne('fhDesde');
	destroyCombosGeneralOne('fhHasta');
	destroyCombosGeneralOne('selectedComp');
	destroyCombosGeneralOne('habilitadoSel');
	search();
	$('#btnBusCons').button({
		icons : {
			secondary : "ui-icon-search"
		}
	}).click(function() {
		search();
	});
	$('#btnSummary').button({
		icons : {
			secondary : "ui-icon-carat-1-n"
		}
	}).click(function() {
		var params = cargaParamsGenerales();
		params += '&nbUniVal=' + $('#nbUniVal').val();
		loadContentDiv('ConsServFact.action?'+ params);
	});

	
	$('#btnExportar').button({icons : {secondary : "ui-icon-bookmark"}}).click(function() {exportDataGrid(true);});	
	$('#btnImprimir').button({icons : {secondary : "ui-icon-print"}}).click(function() {exportDataGrid(false);});
	hideShowButtonsSF(false);
	initialiseFiltros();
	
   	if ($("#sector").val() != "0") {
   	   	$('#selectedSector').selectmenu('setValue', $("#sector").val());
       $('#selectedSector').selectmenu('disabled', true);	
    }
   		
	initServFactDialog();

});

function hideShowButtonsSF(bool){
	
	if(bool){
		$('#btnExportar').show();
		$('#btnImprimir').show();
	}else{
		$('#btnExportar').hide();
		$('#btnImprimir').hide();
	}	
}
/* RECARGA PRODUCTOS */
function initialiseFiltros() {
	var $proveedor = $('#selectedProveedor');
	$proveedor.change(function() {
		hideCommonDataElements();
		limpiarProveedor();
		recargarCombos($('#selectedProveedor').val());
	});
	onchangeOptions('selectedProveedor');

	var $sector = $('#selectedSector');
	$sector.change(function() {
		hideCommonDataElements();
		limpiarSector();
		recargarProducto($('#selectedProveedor').val(), $('#selectedSector')
				.val());
	});
	onchangeOptions('selectedSector');

	// var $periodoDs = $('#fhDesde');
	// $periodoDs.change(function(){
	// obtenerPeriodo($('#selectedProveedor').val(), $('#fhDesde').val());
	// });
	// var $periodoHs = $('#fhHasta');
	// $periodoHs.change(function(){
	// obtenerPeriodo($('#selectedProveedor').val(), $('#fhHasta').val());
	// });

	var $producto = $('#selectedProducto');
	$producto.change(function() {
		obtenerProducto($('#selectedProveedor').val(), $('#selectedProducto')
				.val());
	});

}
function search() {
	$('#agregarGrant').val('S');
	limpiarGrilla('gridServFactId', 'gridServFactPager', 'gridServFact');

	$('#conServFactGrid').show();
	$('#botonera').show();
	$('#buttonCancelar').button({
		icons : {
			secondary : ""
		}
	}).click(function() {
		resetServFactForm();
		cleanDataConfirmation();
	});
	loadServFactGrid();
}

function initServFactDialog() {
	$("#dialogEditServFact").dialog({
		bgiframe : true,
		autoOpen : false,
		modal : true,
		width : 450,

		close : reloadServFactesGrid,
		buttons : [ {
			id : 'guardar-button',
			text : "Grabar",
			click : function() {
				try {
					saveServFact();
				} catch (e) {
					jsError('saveServFact(...)', e.message);
				}
			}
		}, {
			id : 'cancelar-button',
			text : "Cancelar",
			click : function() {
				$('#conServFact_diag_responseMsgs').hide();
				resetServFactForm();
				cleanDataConfirmation();
				$(this).dialog('close');
			}
		} ]
	});
}

// AGREGA BOTONERA DE LA GRILLA
function addButtonsToServFactGrid() {
	if ($('#addGrant').val() == 'S') {
		var title = 'Agregar ServFact';
		$('#gridServFactId').navButtonAdd('#gridServFactPager', {
			caption : 'Agregar',
			buttonicon : 'ui-icon ui-icon-plus',
			onClickButton : function() {
				try {
					$('#gridServFactId').resetSelection();
					processEditServFact(0, false, 'Ingresar ServFact', 'A');
				} catch (e) {
					jsError('loadServFactesGrid(...)', e);
				}
			},
			position : 'last',
			title : title
		});
	}
	if ($('#editGrant').val() == 'S') {
		var title = 'Editar ServFact';
		$('#gridServFactId').navButtonAdd(
				'#gridServFactPager',
				{
					caption : 'Editar',
					buttonicon : 'ui-icon ui-icon-pencil',
					onClickButton : function() {
						try {
							var rowid = getSelRowFromServFactGrid();
							if (rowid != null) {
								processEditServFact(rowid, false,
										'Editar ServFact', 'E');
							} else {
								seleccioneRegistro();
							}
						} catch (e) {
							jsError('loadServFactesGrid(...)', e);
						}
					},
					position : 'last',
					title : title
				});
	}
	if ($('#deleteGrant').val() == 'S') {
		var title = 'Eliminar ServFact';
		$('#gridServFactId').navButtonAdd(
				'#gridServFactPager',
				{
					caption : 'Eliminar',
					buttonicon : 'ui-icon ui-icon-pencil',
					onClickButton : function() {
						try {
							var rowid = getSelRowFromServFactGrid();
							if (rowid != null) {
								processEditServFact(rowid, false,
										'Eliminar ServFact', 'D');
							} else {
								seleccioneRegistro();
							}
						} catch (e) {
							jsError('loadServFactesGrid(...)', e);
						}
					},
					position : 'last',
					title : title
				});
	}
	if ($('#selectGrant').val() == 'S') {
		var title = 'Consultar ServFact';
		$('#gridServFactId').navButtonAdd(
				'#gridServFactPager',
				{
					caption : 'Consultar',
					buttonicon : 'ui-icon ui-icon-pencil',
					onClickButton : function() {
						try {
							var rowid = getSelRowFromServFactGrid();
							if (rowid != null) {
								processEditServFact(rowid, false,
										'Consultar ServFact', 'C');
							} else {
								seleccioneRegistro();
							}
						} catch (e) {
							jsError('loadServFactesGrid(...)', e);
						}
					},
					position : 'last',
					title : title
				});
	}

}

// FUNCTIONES GENERALES GRILLA
function reloadServFactesGrid() {
	$('#gridServFactId').trigger('reloadGrid');
}

function getSelRowFromServFactGrid() {
	return $("#gridServFactId").getGridParam('selrow');
}

function resetServFactForm() {
	$(':input', '#dialogEditServFact').not('input[type=hidden], :button').val(
			'').removeAttr('checked').removeAttr('selected');
}

function cleanDataConfirmation() {
	$('#cdServFact').val('');
	$('#nbServFact').val('');
	$('#nbServFactAbrev').val('');
	$('#nbServFactAlt').val('');
	$('#stHabilitado').val('');
}

function dateFormat_serviceToJs(fecha) {
	var value = "";
	if (fecha != null && fecha != "") {
		value = fecha.substr(8, 2) + '/' + fecha.substr(5, 2) + '/'
				+ fecha.substr(0, 4);
	}
	return value;
}

function onchangeOptions(selectorId) {
	$("#" + selectorId).change(function() {
		hideCommonDataElements();
	});
}

function hideCommonDataElements() {
	if ($('#selectedProveedor').val() == "0") {
		resetProductoOption(' Sin Producto ', '0', false);
		resetPeriodoHsOption(' Sin Per&iacute;odo  ', '0', false);
		resetPeriodoDsOption(' Sin Per&iacute;odo  ', '0', false);
	}
	if ($('#selectedSector').val() == "0") {
		resetProductoOption(' Sin Producto ', '0', false);
	}
}
// Da formato a los combos
function destroyCombos() {

	$.each($('select'), function () {
        $(this).selectmenu('destroy');
    });
	$.each($('select'), function () {
        $(this).selectmenu({
    		style : 'dropdown',
    		width : $(this).attr("width"), maxHeight:'200'});
    });
}

// GRILLA
function loadServFactGrid() {
	var error = '0';
	var mensaje = "";
	var params = cargaParamsGenerales();

	params += '&expand=' + expandido;
	if ($('#cdLote').val() == null || $.trim($('#cdLote').val()) == '') {

		if ($('#selectedProveedor').val() === '0') {
			error = '1';
			mensaje = "Debe seleccionar un proveedor \n";
		}
		if ($('#fhDesde').val() === '0') {
			error = '1';
			mensaje += "Debe seleccionar un per\u00edodo Desde \n";
		}
		if ($('#fhHasta').val() === '0') {
			error = '1';
			mensaje += "Debe seleccionar un per\u00edodo Hasta ";
		}
		if ((validaPerido($('#fhDesde').val(), $('#fhHasta').val()))) {
			error = '1';
			mensaje += "Per\u00edodo Hasta menor a Per\u00edodo Desde";

		}
	}
	if (error == '0') {

		try {

			jQuery('#gridServFactId').jqGrid(
					{
						url : 'JsonServFactDetList.action?' + params,
						datatype : 'json',
						mtype : 'POST',
						colNames :  [ 'Sector Ctrl.' , 'Grupo Prod.'   ,'Cod. Prod.','Producto','Lote Fact','Per. Fact','Tipo Cpr','Nro. Comprob.','Remito',
						              'Fecha Remito','Fecha Cierre','Cant. Fact.','Valor(en U.Val.)',
							             'Observaciones','.Est','Concil.'
							             ],
						colModel : [ 
									{name : 'cdSector'       , index : 'cdSector'   , width : 60, align : 'left', hidden : false }, 
									{name : 'cdGrupoProd'    , index : 'cdGrupoProd', width : 90, align : 'left'  , hidden : false }, 
									{name : 'cdProd'         , index : 'cdProd'     , width : 70, align : 'left'  , hidden : false }, 
									{name : 'dsProd'         , index : 'dsProd'     , width : 125, align : 'left'  , hidden : false }, 
									{name : 'cdLoteFact'     , index : 'cdLoteFact' , width : 70, align : 'right'  , hidden : false }, 
									{name : 'cdPerFact'      , index : 'cdPerFact'  , width : 50, align : 'center'  , hidden : false }, 
									{name : 'cdTipComp'      , index : 'cdTipComp'  , width : 50, align : 'center'  , hidden : false }, 
									{name : 'nuComp'         , index : 'nuComp'     , width : 85, align : 'right'  , hidden : false }, 
									{name : 'cdRemito'       , index : 'cdRemito'   , width : 85, align : 'right'  , hidden : false }, 
									{name : 'fechaRemito'    , index : 'fechaRemito', width : 70, align : 'center'  , hidden : false }, 
									{name : 'fechaCierre'    , index : 'fechaCierre', width : 70, align : 'center'  , hidden : false }, 
									{name : 'ctFact'         , index : 'ctFact'     , width : 70, align : 'right'  , hidden : false, formatter: 'integer' }, 
									{name : 'nuValor'        , index : 'nuValor'    , width : 85, align : 'right'  , hidden : false, formatter: 'number', formatoptions: { decimalPlaces: 2 } }, 
									{name : 'observ'         , index : 'observ'     , width : 500, align : 'left'  , hidden : false }, 
									{name : 'stLote'         , index : 'stLote'     , width : 25, align : 'center'  , hidden : false }, 
									{name : 'cdConciliacion' , index : 'cdConciliacion', width : 70, align : 'right' , hidden : false }
									],
						jsonReader : {
							root : 'gridModelToShow',
							repeatitems : false,
							id : 'cdServFact'
						},
						scrollOffset : 0,
						width : 1050,
						emptyrecords : 'Sin registros',
						rowNum : 0,
//						rowList : [ ],
						pager : '#gridServFactPager',
						sortname : 'cdProd',
						viewrecords : true,
						sortorder : 'asc',
						shrinkToFit : false,
						caption : 'Detalle de Servicios Facturados',
						height : '100%',
						footerrow : true,
						userDataOnFooter : true,
						onSelectRow : function(row_id) {
							var values = jQuery("#gridServFactId").getRowData(
									row_id);
							// showHideEdit(values['cdServFact']);
						},
						loadComplete : function(data) {
							totales = data.totales;
							sumaTotales(totales);
							$('#gridServFactId').trigger('reloadGrid');
							if (data.result.errorCode != '0') {
								setErrorMsg($('#conServFact_responseMsgs'),
										data.result.errorDesc);
								$('#conServFact_responseMsgs').show();
							} else {
								$('#conServFact_responseMsgs').hide();
							}
							hideShowButtonsSF(true);
							
							var rows = jQuery("#gridServFactId").getDataIDs();
							
							if (rows.length == 0) {
             	            	  height = 0; //23.52 * rowData.length; 
             	               } else  if (rows.length <= 30){
             	            	   height = '100%'; //23.52 * rowData.length; 
             	               }else{
             	            	   height = 400;
             	               }
                               
                              $("#gridServFactId").jqGrid("setGridHeight",height);							
						}
					});
			$('.ui-jqgrid-title').css('width', '98%');

			// ??? no se que hacen ������
			$(".ui-jqgrid-labels").css('font-size', '10px');
			$(".ui-pg-table").css('font-size', '10px');
			$('.ui-jqgrid .ui-jqgrid-bdiv').css('overflow-y', 'scroll');
			// Saco los botones genericos de la grilla
			$("#gridServFactId").navGrid('#gridDifConciliaPager', {
				refresh : false,
				edit : false,
				add : false,
				del : false,
				search : false
			});

			// Reload de la tabla para el display de los datos cargados

			addButtonsToServFactGrid();

			// }
			// }
			// });
		} catch (e) {
			jsError('loadServFactGrid(...)', e);
			setErrorMsg($('#conServFact_responseMsgs'), json.result.errorDesc);
			$('#conServFact_responseMsgs').show();
		}
	} else {
		alert(mensaje);
	}
}

function limpiarProveedor() {
	$('#fhDesde').val();
	$('#fhHasta').val();
	limpiarSector();
}

function limpiarSector() {

	$('#cdUniVal').val("");
	$('#nbUniVal').val("");
	$('#nuValBrutoUniVal').val("");
}

function resetProductoOption(label, value, disabled) {
	document.getElementById('selectedProducto').options.length = 0;
	document.getElementById('selectedProducto')[0] = new Option(label, value);
	$('#selectedProducto').selectmenu('destroy');
	$('#selectedProducto').selectmenu({
		style : 'dropdown',
		width : '200px', maxHeight:'200'
	}).selectmenu('disabled', true);
}

function resetPeriodoDsOption(label, value, disabled) {
	document.getElementById('fhDesde').options.length = 0;
	document.getElementById('fhDesde')[0] = new Option(label, value);
	$('#fhDesde').selectmenu('destroy');
	$('#fhDesde').selectmenu('destroy');
	$('#fhDesde').selectmenu({
		style : 'dropdown',
		width : '100px', maxHeight:'200'
	});
}
function resetPeriodoHsOption(label, value, disabled) {
	document.getElementById('fhHasta').options.length = 0;
	document.getElementById('fhHasta')[0] = new Option(label, value);
	$('#fhHasta').selectmenu('destroy');
	$('#fhHasta').selectmenu({
		style : 'dropdown',
		width : '100px', maxHeight:'200'
	});
}

function recargarCombos(cdProveedor) {
	recargarPeriodoDs(cdProveedor);
}

function recargarPeriodoDs(cdProveedor) {
	callJsonAction("comboConciliacionPeriodos.action", "opcion=2&cdProveedor="
			+ cdProveedor, "successPeriodosDesde", "errorPeriodos");
}
function recargarPeriodoHs(cdProveedor) {
	callJsonAction("comboConciliacionPeriodos.action", "opcion=2&cdProveedor="
			+ cdProveedor, "successPeriodosHasta", "errorPeriodos");
}

function recargarProducto(cdProveedor, cdSector) {
	callJsonAction("comboConciliacionPeriodos.action", "opcion=1&cdProveedor="
			+ cdProveedor + "&cdSector=" + cdSector, "successProductos",
			"errorProducto");
}

function obtenerPeriodo(cdProveedor, cdPeriodo) {
	callJsonAction("periodoConciliacion.action", "cdProveedor=" + cdProveedor
			+ "&cdPeriodo=" + cdPeriodo, "successDetallePeriodo",
			"errorDetallePeriodo");
}

function obtenerProducto(cdProveedor, cdProducto) {
	callJsonAction("productoConciliacion.action", "cdProveedor=" + cdProveedor
			+ "&cdProducto=" + cdProducto, "successDetalleProducto",
			"errorDetalleProducto");
}

function obtenerUniVal(cdUnival) {
	callJsonAction("uniValConciliacion.action", "cdUniVal=" + cdUnival,
			"successDetalleUniVal", "errorDetalleUniVal");
}

function obtenerProveedorValor(cdProveedor, cdPeriodo, cdUnival) {
	callJsonAction("proveedorValorConciliacion.action",
			"cdProveedor=" + cdProveedor + "&cdPeriodo=" + cdPeriodo
					+ "&cdUniVal=" + cdUnival, "successDetalleProveedorValor",
			"errorDetalleProveedorValor");
}

/*--------------------------------------------------------------------------------------------------------*/
/* RECARGA PERIODOS */
/*--------------------------------------------------------------------------------------------------------*/
function successPeriodosDesde(jsonData) {
	try {
		document.getElementById('fhDesde').options.length = 0;
		$('#fhDesde').selectmenu('destroy');

		if (jsonData.ProveedorPeriodoList != undefined) {
			resetPeriodoDsOption(' Sin Periodo ', '0', true);

			if (jsonData.ProveedorPeriodoList.length > 0) {
				for (var i = 0; i < jsonData.ProveedorPeriodoList.length; i++)
					$("#fhDesde").append(
							new Option((jsonData.ProveedorPeriodoList[i]).desc,
									jsonData.ProveedorPeriodoList[i].cod));
				$('#fhDesde').selectmenu('destroy');
				$('#fhDesde').selectmenu({
					style : 'dropdown',
					width : '100px', maxHeight:'200'
				});
			} else {
				alert("El proveedor no posee per\u00edodos");
			}
			recargarPeriodoHs($('#selectedProveedor').val());
		}

	} catch (e) {
		jsError('errorPeriodos', e);
	}
}

function errorPeriodos(cod, des) {
	jsError(cod, des);
}
function successPeriodosHasta(jsonData) {
	try {
		document.getElementById('fhHasta').options.length = 0;
		$('#fhHasta').selectmenu('destroy');

		if (jsonData.ProveedorPeriodoList != undefined) {
			resetPeriodoHsOption(' Sin Periodo ', '0', true);

			if (jsonData.ProveedorPeriodoList.length > 0) {
				for (var i = 0; i < jsonData.ProveedorPeriodoList.length; i++)
					$("#fhHasta").append(
							new Option((jsonData.ProveedorPeriodoList[i]).desc,
									jsonData.ProveedorPeriodoList[i].cod));
				$('#fhHasta').selectmenu('destroy');
				$('#fhHasta').selectmenu({
					style : 'dropdown',
					width : '100px', maxHeight:'200'
				});
			}
		}
	} catch (e) {
		jsError('errorPeriodos', e);
	}
}
/*--------------------------------------------------------------------------------------------------------*/
/* DETALLE PERIODO */
/*--------------------------------------------------------------------------------------------------------*/
function successDetallePeriodo(jsonData) {
	try {
		if (jsonData.DetallePeriodo != undefined) {
			if (jsonData.DetallePeriodo.length > 0) {
				$('#fhDesde').val(jsonData.DetallePeriodo[0].fhDesde);
				$('#fhHasta').val(jsonData.DetallePeriodo[0].fhHasta);
				obtenerProveedorValor($('#selectedProveedor').val(), $(
						'#filtroPeriodoList').val(), $('#cdUniVal').val());
			}
		}
	} catch (e) {
		jsError('errorPeriodo', e);
	}
}

function errorDetallePeriodo(cod, des) {
	alert("errorDetallePeriodo");
	jsError(cod, des);
}

/*--------------------------------------------------------------------------------------------------------*/
/* RECARGA PRODUCTOS */
/*--------------------------------------------------------------------------------------------------------*/
function successProductos(jsonData) {
	try {
		document.getElementById('selectedProducto').options.length = 0;
		$('#selectedProducto').selectmenu('destroy');
		if (jsonData.ProductoPrecioList != undefined) {
			resetProductoOption(' Sin Producto ', '0', true);

			if (jsonData.ProductoPrecioList.length > 0) {
				for (var i = 0; i < jsonData.ProductoPrecioList.length; i++)
					$("#selectedProducto").append(
							new Option((jsonData.ProductoPrecioList[i]).desc,
									jsonData.ProductoPrecioList[i].cod));

				$('#selectedProducto').selectmenu('destroy');
				$('#selectedProducto').selectmenu({
					style : 'dropdown',
					width : '200px', maxHeight:'200'
				});

			}
		}
	} catch (e) {
		jsError('errorProducto', e);
	}
}

function errorProducto(cod, des) {
	alert("errorProducto");
	jsError(cod, des);
}

/*--------------------------------------------------------------------------------------------------------*/
/* DETALLE PRODUCTO */
/*--------------------------------------------------------------------------------------------------------*/
function successDetalleProducto(jsonData) {
	try {
		if (jsonData.DetalleProducto != undefined) {
			if (jsonData.DetalleProducto.length > 0) {
				$('#cdUniVal').val(jsonData.DetalleProducto[0].cdUniVal);
				obtenerUniVal($('#cdUniVal').val());
			}
		}
	} catch (e) {
		alert("errorDetalleProducto");
		jsError('errorDetalleProducto', e);
	}
}

function errorDetalleProducto(cod, des) {
	jsError(cod, des);
}

/*--------------------------------------------------------------------------------------------------------*/
/* DETALLE UNIVAL */
/*--------------------------------------------------------------------------------------------------------*/
function successDetalleUniVal(jsonData) {
	try {
		if (jsonData.DetalleUniVal != undefined) {

			if (jsonData.DetalleUniVal.length > 0) {
				$('#nbUniVal').val(
						jsonData.DetalleUniVal[0].cdCodTabla.trim() + ' - '
								+ jsonData.DetalleUniVal[0].nbCodTabla);
			}
		}
	} catch (e) {
		jsError('errorUniVal', e);
	}
}

function errorDetalleUniVal(cod, des) {
	jsError(cod, des);
}

/*--------------------------------------------------------------------------------------------------------*/
/* IMPORTE VALOR MONETARIO PROVEEDOR PERIODO UNIVAL */
/*--------------------------------------------------------------------------------------------------------*/
function successDetalleProveedorValor(jsonData) {
	try {
		if (jsonData.DetalleProveedorValor != undefined) {
			if (jsonData.DetalleProveedorValor.length > 0) {
				$('#nuValBrutoUniVal').val(
						jsonData.DetalleProveedorValor[0].nuValBrutoUniVal);
			}
		}
	} catch (e) {
		jsError('errorUniVal', e);
	}
}

function errorDetalleProveedorValor(cod, des) {
	jsError(cod, des);
}


/**
 * Llama a un action para validar los periodos desde/hasta y ver cual es menor y cual es mayor. 
 * 
 * @param pd
 * @param ph
 * @returns {Boolean}
 * 
 * @author Fede/David
 */
function validaPerido(pd,ph){
    if(pd == '0'
    || ph == '0')
 		periodosValidos = true;			 	
    else{
    	var params2='';
    	params2 += 'periodD='+pd;
        params2 += '&periodH='+ph;
        params2 += '&codProveedor='+$('#selectedProveedor').val();
    	
		$.ajax({
			async:false
			, url:'validatePeriodFact.action?'+params2
			, success: function(data){
				 	if((data.result.errorCode) == 0) {
				 		periodosValidos = true;			 		
				 	}else
				 		periodosValidos = false;
				
				}
			, timeout:15000
			, error: function(msg){
				periodosValidos = false;
				
			}
		});
	}		


}
function cargaParamsGenerales(){

	var params = 'selectedSector=' + $('#selectedSector').val();
	params += '&selectedProducto=' + $('#selectedProducto').val();
	params += '&selectedProveedor=' + $('#selectedProveedor').val();
	params += '&selectedGrupo=' + $('#selectedGrupo').val();
	params += '&habilitadoSel=' + $('#habilitadoSel').val();
	params += '&cdComprobante=' + $('#cdComprobante').val();
	params += '&remitoDesde=' + $('#remitoDesde').val();
	params += '&remitoHasta=' + $('#remitoHasta').val();
	params += '&cdLote=' + $('#cdLote').val();
	params += '&fhDesde=' + $('#fhDesde').val();
	params += '&fhHasta=' + $('#fhHasta').val();
	params += '&selectedComp=' + $('#selectedComp').val();
	return params;
}

function sumaTotales( vueltas ){
    

    jQuery("#gridServFactId").footerData('set',{cdGrupoProd: vueltas[0], 
    	ctFact : vueltas[1] , nuValor: vueltas[2]});
}

/**
 * @author Fede
 * 
 * Funcion que exporta el contenido del grid a PDF/XLS
 * @xls  Parametro que si es TRUE exporta Documento EXCEL , sino PDF
 */
function exportDataGrid( xls  ){
	var params = '';
	params += $('#globalParams').val(); 

	if($.trim(params) === ''){
		fAlertCB('Para Exportar debe haber realizado una busqueda Primero');
	}else{			
		var title = 'Detalle de Servicios Facturados';
		var titleFilter = 'Proveedor,Sector Ctrl.,Cod. Prod.,Unidad Valoración ,Período Fact. Desde,Período Fact. Hasta,Tipo Comprobante,Nro. Comprobante, Remito Desde, Remito Hasta , Nro. Lote , Estado Detalle';

		var filterData = '';
		filterData += $('#selectedProveedor option:selected').text() + ';'+$('#selectedSector option:selected').text() + ';'+ $('#selectedProducto option:selected').text()+ ';' 
						+ $('#nbUniVal').val() + ';'
						;
		filterData += $('#fhDesde option:selected').text() +';'+$('#fhHasta option:selected').text() +';'+$('#selectedComp option:selected').text() +';'
						+ $('#cdComprobante').val() + ';'	+ $('#remitoDesde').val() + ';'	+ $('#remitoHasta').val() + ';'
						+ $('#cdLote').val() + ';';
		filterData += $('#habilitadoSel option:selected').text();
		filterData = normalize(accentDecode(filterData));
		var printContent = '';
		printContent += exportDataFilterToHtml('Parametros de Busqueda',titleFilter,title,filterData);
		printContent += exportDataGridToHtml('',title,'gridServFactId','12,13');
		
		printContent = normalize(accentDecode(printContent));
		$('#nameFile').val(title);
		$('#html').val(createHtmlToExport(printContent));
		if(xls)
			$('#fileType').val('xls');		
		else
			$('#fileType').val('pdf');		

		$('#exportForm').get(0).submit();        	
	}
}
