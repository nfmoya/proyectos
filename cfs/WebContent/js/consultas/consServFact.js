var expandido = "no";
var searched = false;
var periodosValidos = false;
$(document).ready(function() {

	destroyCombosGeneralOne('selectedProveedor');
	destroyCombosGeneralOne('selectedSector');
	destroyCombosGeneralOne('selectedProducto');
	destroyCombosGeneralOne('selectedGrupo');
	destroyCombosGeneralOne('fhDesde');
	destroyCombosGeneralOne('fhHasta');
	destroyCombosGeneralOne('selectedComp');
	destroyCombosGeneralOne('habilitadoSel');
	

	$('#btnBusCons').button({
		icons : {
			secondary : "ui-icon-search"
		}
	}).click(function() {
		search(true);
	});
	$('#btnExpand').button({
		icons : {
			secondary : "ui-icon-gear"
		}
	}).click(function() {
		expandirContraer();
		if(searched)
			search(true);
	});
	$('#btnDetail').button({
		icons : {
			secondary : "ui-icon-carat-1-s"
		}
	}).click(function() {
		var params = cargaParamsGenerales();
		params += '&nbUniVal=' + $('#nbUniVal').val();
		loadContentDiv('ConsServFactDet.action?'+ params);
	});

	$('#btnContract').button({
		icons : {
			secondary : "ui-icon-gear"
		}
	}).click(function() {
//		var r = confirm("Recuerde que al contraer no se agrupar\u00e1 por producto");
//		if(r == true){
			expandirContraer();
			search(false);
//		}
	});
	
	$('#btnExportar').button({icons : {secondary : "ui-icon-bookmark"}}).click(function() {exportGridXLS();});	
	$('#btnImprimir').button({icons : {secondary : "ui-icon-print"}}).click(function() {exportGridPDF();});
	
	initialiseFiltros();

   	if ($("#sector").val() != "0") {
   	   	$('#selectedSector').selectmenu('setValue', $("#sector").val());	
       $('#selectedSector').selectmenu('disabled', true);	
    }
   	
	initServFactDialog();
	
	hideShowButtonsSF(false);
});

/**
 * @autor XA50126
 * 
 * Funcion que oculta o muestra los botones de la pantalla de acuerdo
 * a si hay o no registros en la consulta
 * 
 * @param bool
 */
function hideShowButtonsSF(bool){
	
	if(bool){
		$('#botoneraServFact').show();
	}else{
		$('#botoneraServFact').hide();
	}	
}

function expandirContraer(){
	if(expandido === "no"){
		expandido = "si";
	}else{
		expandido = "no";
	}
	if(expandido === "no"){	
		$("#btnExpand").css("display", "none");
		$("#btnContract").css("display", "block");
//		$("#filaProducto").css("display", "block");
//		$("#filaUniVal").css("display", "block");
	}else{
		$("#btnExpand").css("display", "block");
		$("#btnContract").css("display", "none");
//		$("#filaProducto").css("display", "none");
//		$("#filaUniVal").css("display", "none");
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

	var $producto = $('#selectedProducto');
	$producto.change(function() {
		obtenerProducto($('#selectedProveedor').val(), $('#selectedProducto')
				.val());
	});

}
function search(value) {
	searched = true;
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
	loadServFactGrid(value);
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
        $(this).selectmenu({
    		style : 'dropdown',
    		width : $(this).attr("width"), maxHeight:'200'});
    });
}

// GRILLA
function loadServFactGrid(value) {
	var error = '0';
	var mensaje = "";
	var params = cargaParamsGenerales(value);
	params += '&expand=' + expandido;
	if($('#cdLote').val() == null
	|| $('#cdLote').val() == ''){
		if($('#selectedProveedor').val() === '0'){
			error = '1';
			mensaje = "Debe seleccionar un proveedor \n";
		}

		if($('#fhDesde').val() === '0'){
			error = '1';
			mensaje += "Debe seleccionar un per\u00edodo Desde \n";
		}
		if($('#fhHasta').val() === '0'){
			error = '1';
			mensaje += "Debe seleccionar un per\u00edodo Hasta \n";
		}
			
		if(($('#remitoDesde').val() > $('#remitoHasta').val())){
			error = '1';
			mensaje += "Remito Hasta menor a Remito Desde";
			
		}
	}	
	validaPerido($('#fhDesde').val(), $('#fhHasta').val());
	if(!periodosValidos){
		error = '1';
		mensaje += "Per\u00edodo Hasta menor a Per\u00edodo Desde \n";
	}
	if(error == '0'){
	try {

		var col_names = [];
		var col_model = [];
		var col_group = [];
		var col_index = [];
		var totales;
		var cantidad;
		
		$.ajax({
			url : 'JsonServFactModel.action?' + params,
			success : function(data) {
				$('#gridServFactId').jqGrid('GridUnload');
				col_names = data.names;		
				col_model = data.model;
				col_group = data.groups;
				col_index = data.index;
				totales   = data.totales;
				cantidad  = data.cant;
			
				if (cantidad > 12) {
					alert("Los Per\u00edodos a consultar deben ser menos de 12 ");
				} else {
					params += '&cantPeriodos=' + col_index.length;
					jQuery('#gridServFactId').jqGrid(
							{
								url : 'JsonServFactList.action?' + params,
								datatype : 'json',
								mtype : 'POST',
								colNames : col_names,
								colModel : col_model,
								jsonReader : {
									root : 'gridModelToShow',
									repeatitems : false,
									id : 'cdServFact'
								},
						        scrollOffset: 0,
								width : 1050,
								emptyrecords : 'Sin registros',
								rowNum : 0,//Muchos registros
//								rowList : [ ],
								pager : '#gridServFactPager',
								sortname : 'cdProd',
								viewrecords : true,
								sortorder : 'asc',
								shrinkToFit : false,
								caption : 'Servicios Facturados',
								height : '100%',
						        footerrow: true,
						        userDataOnFooter: true,
								onSelectRow : function(row_id) {
//									var values = jQuery("#gridServFactId")
//											.getRowData(row_id);
								},
								loadComplete : function( data ){
									totales = data.totales;
									sumaTotales(totales);
									$('#gridServFactId').trigger('reloadGrid');
									if(data.result.errorCode != '0'){
										setErrorMsg($('#conServFact_responseMsgs'),data.result.errorDesc);
										$('#conServFact_responseMsgs').show();
									}else{
										$('#conServFact_responseMsgs').hide();
									}
									var rows = $("#gridServFactId").jqGrid('getGridParam', 'records');
									if(rows > 0)
										hideShowButtonsSF(true);
									else
										hideShowButtonsSF(false);	
									
									var rows = jQuery("#gridServFactId").getDataIDs();								
									if (rows.length == 0) {
		             	           	   height = 0; //23.52 * rowData.length; 
		             	            } else  if (rows.length <= 30){
		             	               height = '100%'; //23.52 * rowData.length; 
		             	            }else{
		             	               height = 400;
		             	            }
		                            $("#gridServFactId").jqGrid("setGridHeight",height);
		                            
					                var rowData = $("#gridServFactId").getDataIDs();
		                            for (var i = 0; i < rowData.length; i++) {
		                                var cl     = rowData[i];
		                                var varmax = $('#gridServFactId').getCell(cl, 'nuPorcVarMax');
						                for (var i = 2; i <= cantidad; i++) {		                                
			                                var canti = $('#gridServFactId').getCell(cl, 'ctRemitosMes'+i);   
			                                var valor = $('#gridServFactId').getCell(cl, 'ctRemitosAConcilMes'+i);

			                                if (canti < varmax) {
			                                	$('#gridServFactId').jqGrid("setCell", cl, "ctRemitosMes"+i, "", {color:'lightgreen'});
			                                	$('#gridServFactId').jqGrid("setCell", cl, "ctRemitosMes"+i, "", {'font-weight': 'bold'});
			                                } else if (canti > varmax) {
			                                	$('#gridServFactId').jqGrid("setCell", cl, "ctRemitosMes"+i, "", {color:'red'});
			                                	$('#gridServFactId').jqGrid("setCell", cl, "ctRemitosMes"+i, "", {'font-weight': 'bold'});
			                                }
			                                if (valor < varmax) {
			                                	$('#gridServFactId').jqGrid("setCell", cl, "ctRemitosAConcilMes"+i, "", {color:'lightgreen'});
			                                	$('#gridServFactId').jqGrid("setCell", cl, "ctRemitosAConcilMes"+i, "", {'font-weight': 'bold'});
			                                } else if (valor > varmax) {
			                                	$('#gridServFactId').jqGrid("setCell", cl, "ctRemitosAConcilMes"+i, "", {color:'red'});
			                                	$('#gridServFactId').jqGrid("setCell", cl, "ctRemitosAConcilMes"+i, "", {'font-weight': 'bold'});
			                                }			                                
						                }
		                            }
								}
							});
					$('.ui-jqgrid-title').css('width', '98%');

					// ??? no se que hacen
					$(".ui-jqgrid-labels").css('font-size', '10px');
					$(".ui-pg-table").css('font-size', '10px');
//					$('.ui-jqgrid .ui-jqgrid-bdiv').css('overflow-y', 'scroll');
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


					jQuery("#gridServFactId").jqGrid('setGroupHeaders', {
						useColSpanStyle : true,
						groupHeaders : [ {
							startColumnName : col_index[0],
							numberOfColumns : 4,
							titleText : col_group[0]
						}, {
							startColumnName : col_index[1],
							numberOfColumns : 4,
							titleText : col_group[1]
						}, {
							startColumnName : col_index[2],
							numberOfColumns : 4,
							titleText : col_group[2]
						}, {
							startColumnName : col_index[3],
							numberOfColumns : 4,
							titleText : col_group[3]
						}, {
							startColumnName : col_index[4],
							numberOfColumns : 4,
							titleText : col_group[4]
						}, {
							startColumnName : col_index[5],
							numberOfColumns : 4,
							titleText : col_group[5]
						}, {
							startColumnName : col_index[6],
							numberOfColumns : 4,
							titleText : col_group[6]
						}, {
							startColumnName : col_index[7],
							numberOfColumns : 4,
							titleText : col_group[7]
						}, {
							startColumnName : col_index[8],
							numberOfColumns : 4,
							titleText : col_group[8]
						}, {
							startColumnName : col_index[9],
							numberOfColumns : 4,
							titleText : col_group[9]
						}, {
							startColumnName : col_index[10],
							numberOfColumns : 4,
							titleText : col_group[10]
						}, {
							startColumnName : col_index[11],
							numberOfColumns : 4,
							titleText : col_group[11]
						}, {
							startColumnName : 'ctServFactTotal',
							numberOfColumns : 6,
							titleText : 'Totales'
						}

						]
					});
//					jQuery("#gridServFactId").jqGrid('setFrozenColumns');
					
				}
				
				$('.ui-jqgrid .ui-jqgrid-bdiv').css('overflow-y', 'scroll');
			}
		});
	} catch (e) {
		jsError('loadServFactGrid(...)', e);
		setErrorMsg($('#conServFact_responseMsgs'),json.result.errorDesc);
		$('#conServFact_responseMsgs').show();
	}
	}else{
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
	destroyCombosGeneralOne( 'selectedProducto');
	$('#selectedProducto').selectmenu('disabled', true);
}

function resetPeriodoDsOption(label, value, disabled) {
	document.getElementById('fhDesde').options.length = 0;
	document.getElementById('fhDesde')[0] = new Option(label, value);
	destroyCombosGeneralOne( 'fhDesde');
}
function resetPeriodoHsOption(label, value, disabled) {
	document.getElementById('fhHasta').options.length = 0;
	document.getElementById('fhHasta')[0] = new Option(label, value);
	destroyCombosGeneralOne( 'fhHasta');
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
				destroyCombosGeneralOne( 'fhDesde');
			}else{
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
				destroyCombosGeneralOne( 'fhHasta');
			}
	        if ($('#selectedSector').val() != "0") {	
	            recargarProducto($('#selectedProveedor').val(), $('#selectedSector').val());
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

				destroyCombosGeneralOne( 'selectedProducto');

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
						jsonData.DetalleUniVal[0].cdCodTabla.trim()  + ' - '
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
function sumaTotales( vueltas ){
    

    jQuery("#gridServFactId").footerData('set',{cdGrupoProd: vueltas[0], 
      ctServFactMes1 : vueltas[1] , valServFactMes1: vueltas[2], ctRemitosMes1 : vueltas[3] , ctRemitosAConcilMes1: vueltas[4], 
      ctServFactMes2 : vueltas[5] , valServFactMes2: vueltas[6], ctRemitosMes2 : vueltas[7] , ctRemitosAConcilMes2: vueltas[8], 
      ctServFactMes3 : vueltas[9] , valServFactMes3: vueltas[10], ctRemitosMes3 : vueltas[11] , ctRemitosAConcilMes3: vueltas[12], 
      ctServFactMes4 : vueltas[13] , valServFactMes4: vueltas[14], ctRemitosMes4 : vueltas[15] , ctRemitosAConcilMes4: vueltas[16],
      ctServFactMes5 : vueltas[17] , valServFactMes5: vueltas[18], ctRemitosMes5 : vueltas[19] , ctRemitosAConcilMes5: vueltas[20], 
      ctServFactMes6 : vueltas[21] , valServFactMes6: vueltas[22], ctRemitosMes6 : vueltas[23] , ctRemitosAConcilMes6: vueltas[24],
      ctServFactMes7 : vueltas[25] , valServFactMes7: vueltas[26], ctRemitosMes7 : vueltas[27] , ctRemitosAConcilMes7: vueltas[28],
      ctServFactMes8 : vueltas[29] , valServFactMes8: vueltas[30], ctRemitosMes8 : vueltas[31] , ctRemitosAConcilMes8: vueltas[32],
      ctServFactMes9 : vueltas[33] , valServFactMes9: vueltas[34], ctRemitosMes9 : vueltas[35] , ctRemitosAConcilMes9: vueltas[36],
      ctServFactMes10 : vueltas[37] , valServFactMes10: vueltas[38], ctRemitosMes10 : vueltas[39] , ctRemitosAConcilMes10: vueltas[40],
      ctServFactMes11 : vueltas[41] , valServFactMes11: vueltas[42], ctRemitosMes11 : vueltas[43] , ctRemitosAConcilMes11: vueltas[44],
      ctServFactMes12 : vueltas[45] , valServFactMes12: vueltas[46], ctRemitosMes12 : vueltas[47] , ctRemitosAConcilMes12: vueltas[48],

        ctServFactTotal : vueltas[49] , valServFactTotal: vueltas[50], valBrutaTotal : vueltas[51] , 
        valNetoTotal: vueltas[52], ctRemitosTotal : vueltas[53] , ctRemitosAConcilTotal: vueltas[54]});
}
function sumaTotCol(col){
	var grid = $("#gridServFactId");
    var sum = grid.jqGrid('getCol', col, false, 'sum');
    return sum;
}
function seteaValorTotal(col , sum){

	var grid = $("#gridServFactId");
	 grid.jqGrid('footerData','set',{col: sum });
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
	
	params += '&selectedProducto='+ $('#selectedProducto').val() ;	
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

/**
 * @autor XA50126
 * 
 * Funcion que ejecuta la exportacion de la grilla
 * 
 */
function exportGridXLS(){
	exportDataGridToHtmlSF('Consulta Servicios Facturados','gridServFactId','xls');
}

function exportGridPDF(){
	exportDataGridToHtmlSF('Consulta Servicios Facturados','gridServFactId','pdf');
}