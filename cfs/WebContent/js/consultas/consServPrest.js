var expandido = "no";
$(document).ready(function() {

	$("#fhDesde").mask("99/99/9999",{placeholder:" "});
	$("#fhHasta").mask("99/99/9999",{placeholder:" "});

		
	destroyCombosGeneralOne('selectedProveedor');
	destroyCombosGeneralOne('selectedSector');
	destroyCombosGeneralOne('selectedGrupo');
	destroyCombosGeneralOne('selectedProducto');
	destroyCombosGeneralOne('stLoteDet');

	initButtons();
	
	initialiseFiltros();
	
   	if ($("#sector").val() != "0") {
   	   	$('#selectedSector').selectmenu('setValue', $("#sector").val());	
       $('#selectedSector').selectmenu('disabled', true);	
    }

	initServPrestDialog();
	
	datepickers();

    if (($('#selectedProveedor').val() != null) && ($('#selectedProveedor').val() != '') && ($('#selectedProveedor').val() != '0') &&
        ($('#selectedSector').val() != null) && ($('#selectedSector').val() != '') && ($('#selectedSector').val() != '0'))
        search(true);    
});

/**
 * @autor XA50126
 * 
 * Funcion que setea las propiedades principales de los botones de la pantalla
 * 
 */
function initButtons(){
	$('#btnBusCons').button({icons : {secondary : "ui-icon-search"}}).click(function() {
		search(true);
	});
	$('#btnExpand').button({icons : {secondary : "ui-icon-gear"}}).click(function() {
			expandirContraer();
			search(true);
	});
	
    $('#btnDetalle').button().click(function() {
            var url = 'ConsServPrestDetalle.action';
            	url += '?selectedProveedor=' + $('#selectedProveedor').val();
                url += '&selectedSector=' + $('#selectedSector').val();
                url += '&selectedGrupo=' + $('#selectedGrupo').val();
                url += '&selectedProducto=' + $('#selectedProducto').val();
                url += '&nbUniVal=' + $('#nbUniVal').val();
                url += '&fhRemito=' + $('#fhDesde').val();
                url += '&fhFinServ=' + $('#fhHasta').val();
                url += '&remitoDesde=' + $('#remitoDesde').val();
                url += '&remitoHasta=' + $('#remitoHasta').val();
                url += '&cdLote=' + $('#cdLote').val();
                url += '&stLoteDet=' + $('#stLoteDet').val();;	
            
            loadContentDiv(url);
    });
    
	$('#btnContract').button({icons : {secondary : "ui-icon-gear"}}).click(function() {
//		var r = confirm("Recuerde que al contraer los grupos se descartar\u00e1 el filtro de producto");
//		if(r == true){
			expandirContraer();
			search(false);
//		}
	});
	
	  $('#btnLimpiarDesde').button({
			icons : {
				primary : "ui-icon-trash"
			} ,
			text : false
		}).click(function() {
			$('#fhDesde').datepicker('setDate', null);
		});
	    $('#btnLimpiarHasta').button({
			icons : {
				primary : "ui-icon-trash"
			},
	    	text : false
		}).click(function() {
			$('#fhHasta').datepicker('setDate', null);
		});
	    
	    $('#btnExportar').button({icons : {secondary : "ui-icon-bookmark"}}).click(function() {exportGridXLS();});	
		$('#btnImprimir').button({icons : {secondary : "ui-icon-print"}}).click(function() {exportGridPDF();});	
		
		hideShowButtonsSP(false);
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
		//$("#filaProducto").css("display", "block");
		//$("#filaUniVal").css("display", "block");
	}else{
		$("#btnExpand").css("display", "block");
		$("#btnContract").css("display", "none");
		//$("#filaProducto").css("display", "none");
		//$("#filaUniVal").css("display", "none");
	}
}

/* RECARGA PRODUCTOS */
function initialiseFiltros() {
	var $proveedor = $('#selectedProveedor');
	$proveedor.change(function() {
		hideCommonDataElements();
		limpiarProveedor();
        if ($('#selectedSector').val() != "0") {	
           recargarProducto($('#selectedProveedor').val(), $('#selectedSector').val());
  	    }   
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
	$('#agregarGrant').val('S');
	limpiarGrilla('gridServPrestesId', 'gridServPrestesPager', 'gridServPrestes');

	$('#conServPrestGrid').show();
	$('#botonera').show();
	$('#buttonCancelar').button({
		icons : {
			secondary : ""
		}
	}).click(function() {
		resetServPrestForm();
		cleanDataConfirmation();
	});
	loadServPrestGrid(value);
}

/**
 * @autor XA50126
 * 
 * Funcion que oculta o muestra los botones de la pantalla de acuerdo
 * a si hay o no registros en la consulta
 * 
 * @param bool
 */
function hideShowButtonsSP(bool){
	
	if(bool){
		$('#botoneraServPrest').show();
	}else{
		$('#botoneraServPrest').hide();
	}	
}

function initServPrestDialog() {
	$("#dialogEditServPrest").dialog({
		bgiframe : true,
		autoOpen : false,
		modal : true,
		width : 450,

		close : reloadServPrestesGrid,
		buttons : [ {
			id : 'guardar-button',
			text : "Grabar",
			click : function() {
				try {
					saveServPrest();
				} catch (e) {
					jsError('saveServPrest(...)', e.message);
				}
			}
		}, {
			id : 'cancelar-button',
			text : "Cancelar",
			click : function() {
				$('#conServPrest_diag_responseMsgs').hide();
				resetServPrestForm();
				cleanDataConfirmation();
				$(this).dialog('close');
			}
		} ]
	});
}

// AGREGA BOTONERA DE LA GRILLA
function addButtonsToServPrestGrid() {
	if ($('#addGrant').val() == 'S') {
		var title = 'Agregar ServPrest';
		$('#gridServPrestesId').navButtonAdd('#gridServPrestesPager', {
			caption : 'Agregar',
			buttonicon : 'ui-icon ui-icon-plus',
			onClickButton : function() {
				try {
					$('#gridServPrestesId').resetSelection();
					processEditServPrest(0, false, 'Ingresar ServPrest', 'A');
				} catch (e) {
					jsError('loadServPrestesGrid(...)', e);
				}
			},
			position : 'last',
			title : title
		});
	}
	if ($('#editGrant').val() == 'S') {
		var title = 'Editar ServPrest';
		$('#gridServPrestesId').navButtonAdd(
				'#gridServPrestesPager',
				{
					caption : 'Editar',
					buttonicon : 'ui-icon ui-icon-pencil',
					onClickButton : function() {
						try {
							var rowid = getSelRowFromServPrestGrid();
							if (rowid != null) {
								processEditServPrest(rowid, false,
										'Editar ServPrest', 'E');
							} else {
								seleccioneRegistro();
							}
						} catch (e) {
							jsError('loadServPrestesGrid(...)', e);
						}
					},
					position : 'last',
					title : title
				});
	}
	if ($('#deleteGrant').val() == 'S') {
		var title = 'Eliminar ServPrest';
		$('#gridServPrestesId').navButtonAdd(
				'#gridServPrestesPager',
				{
					caption : 'Eliminar',
					buttonicon : 'ui-icon ui-icon-pencil',
					onClickButton : function() {
						try {
							var rowid = getSelRowFromServPrestGrid();
							if (rowid != null) {
								processEditServPrest(rowid, false,
										'Eliminar ServPrest', 'D');
							} else {
								seleccioneRegistro();
							}
						} catch (e) {
							jsError('loadServPrestesGrid(...)', e);
						}
					},
					position : 'last',
					title : title
				});
	}
	if ($('#selectGrant').val() == 'S') {
		var title = 'Consultar ServPrest';
		$('#gridServPrestesId').navButtonAdd(
				'#gridServPrestesPager',
				{
					caption : 'Consultar',
					buttonicon : 'ui-icon ui-icon-pencil',
					onClickButton : function() {
						try {
							var rowid = getSelRowFromServPrestGrid();
							if (rowid != null) {
								processEditServPrest(rowid, false,
										'Consultar ServPrest', 'C');
							} else {
								seleccioneRegistro();
							}
						} catch (e) {
							jsError('loadServPrestesGrid(...)', e);
						}
					},
					position : 'last',
					title : title
				});
	}

}

// FUNCTIONES GENERALES GRILLA
function reloadServPrestesGrid() {
	$('#gridServPrestesId').trigger('reloadGrid');
}

function getSelRowFromServPrestGrid() {
	return $("#gridServPrestesId").getGridParam('selrow');
}

function resetServPrestForm() {
	$(':input', '#dialogEditServPrest').not('input[type=hidden], :button').val(
			'').removeAttr('checked').removeAttr('selected');
}

function cleanDataConfirmation() {
	$('#cdServPrest').val('');
	$('#nbServPrest').val('');
	$('#nbServPrestAbrev').val('');
	$('#nbServPrestAlt').val('');
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

//	$('#selectedProducto').selectmenu('destroy');
//	$('#selectedProducto').selectmenu({
//		style : 'dropdown',
//		width : '200px'
//	});
//
//	$('#selectedProveedor').selectmenu('destroy');
//	$('#selectedProveedor').selectmenu({
//		style : 'dropdown',
//		width : '200px'
//	});
//
//	$('#selectedGrupo').selectmenu('destroy');
//	$('#selectedGrupo').selectmenu({
//		style : 'dropdown',
//		width : '200px'
//	});
//
//	$('#selectedSector').selectmenu('destroy');
//	$('#selectedSector').selectmenu({
//		style : 'dropdown',
//		width : '200px'
//	});
//	
//	$('#stLoteDet').selectmenu('destroy');
//	$('#stLoteDet').selectmenu({
//		style : 'dropdown',
//		width : '200px'
//	});

}

function monthDiff(date1, date2) {
    
    var diffYears = date2.getFullYear()-date1.getFullYear();
    var diffMonths = date2.getMonth()-date1.getMonth();
    var diffDays = date2.getDate()-date1.getDate();

    var months = (diffYears*12 + diffMonths);
    if(diffDays>0) {
        months += '.'+diffDays;
    } else if(diffDays<0) {
        months--;
        months += '.'+(new Date(date2.getFullYear(),date2.getMonth(),0).getDate()+diffDays);
    }   
    
    return months;
}

// GRILLA
function loadServPrestGrid(value) {
	var error = '0';
	var mensaje = "";
	var params = 'selectedSector=' + $('#selectedSector').val();
	
	if(value) {
		params += '&selectedProducto='+ $('#selectedProducto').val() ;	
	} else {
		params += '&selectedProducto=';
	}
	
	params += '&selectedProveedor=' + $('#selectedProveedor').val();
	params += '&selectedGrupo=' + $('#selectedGrupo').val();
	params += '&remitoDesde=' + $('#remitoDesde').val();
	params += '&remitoHasta=' + $('#remitoHasta').val();
	params += '&cdLote=' + $('#cdLote').val();
	params += '&fhDesde=' + $('#fhDesde').val();
	params += '&fhHasta=' + $('#fhHasta').val();
	params += '&stLoteDet=' + $('#stLoteDet').val();;
	params += '&expand=' + expandido;
	if($('#cdLote').val() == null
	|| $('#cdLote').val() == ''){
		if($('#selectedProveedor').val() === '0'){
			error = '1';
			mensaje = "Debe seleccionar un proveedor \n";
		}
		if($('#fhDesde').val() === ''){
			error = '1';
			mensaje += "Debe seleccionar una Fecha Remito Desde \n";
		}else
			if(!isDate($('#fhDesde').val())){
				
				error = '1';
				mensaje += ('Fecha Alta Desde inv\u00e1lida\n');		
			}
		if($('#fhHasta').val() === ''){
			error = '1';
			mensaje += "Debe seleccionar una Fecha Remito Hasta \n";
		}else if(!isDate($('#fhHasta').val())){
			error = '1';
			mensaje += ('Fecha Alta Hasta inv\u00e1lida\n');	
		}
		
		
        if (error === '0') {
            if (($('#fhDesde').val() != '') && ($('#fhHasta').val() != '')) {
                var fhDesde = $('#fhDesde').val();
                var fhHasta = $('#fhHasta').val();
                var fhSplit = fhDesde.split('/');
                fhDesde = fhSplit[2] + fhSplit[1] + fhSplit[0];
                var fhSplit = fhHasta.split('/');
                fhHasta = fhSplit[2] + fhSplit[1] + fhSplit[0];
                if (fhDesde > fhHasta) {
                    error = '1';
                    mensaje += 'Fecha Remito Desde debe ser menor o igual a Fecha Remito Hasta';
                }
            }
        }
        if (error === '0') {
            if (($('#fhDesde').val() != '') && ($('#fhHasta').val() != '')) {
                var fhDesde = $('#fhDesde').datepicker('getDate');
                var fhHasta = $('#fhHasta').datepicker('getDate');
                if (monthDiff(fhDesde, fhHasta) > 12) {
                    error = '1';
                    mensaje += 'El periodo seleccionado no debe exceder los 12 meses';
                }
            }
        }
        if (error === '0') {
            if (($('#remitoDesde').val() != '') && ($('#remitoHasta').val() != '')) {
                var desde = parseInt($('#remitoDesde').val());
                var hasta = parseInt($('#remitoHasta').val());
                if (desde > hasta) {
                    error = '1';
                    mensaje += 'Nro. Remito Desde debe ser menor o igual a Nro. Remito Hasta';
                }
            }
        }
	}	
	if(error === '0'){
	try {

		var col_names = [];
		var col_model = [];
		var col_group = [];
		var col_index = [];
		var totales;
		$.ajax({
			url : 'JsonServPrestModel.action?' + params,
			success : function(data) {
				$('#gridServPrestId').jqGrid('GridUnload');
				col_names = data.names;
//				alert(col_names);
				col_model = data.model;
//				alert(col_model);
				col_group = data.groups;
				col_index = data.index;
				totales = data.totales;
				if (col_index.length > 13) {
					alert("Los Per�odos a consultar deben ser menos de 12 ");
				} else {
					params += '&cantPeriodos=' + col_index.length;
					jQuery('#gridServPrestId').jqGrid(
							{

								formatter : {
									currency : {decimalSeparator:",", thousandsSeparator: ".", decimalPlaces: 2, prefix: "", suffix:"", defaultValue: '0,00'},
									entero : { thousandsSeparator: ".", decimalPlaces: 0, prefix: "", suffix:"", defaultValue: '0'},
								},
								url : 'JsonServPrestList.action?' + params,
								datatype : 'json',
								mtype : 'POST',
								colNames : col_names,
								colModel : col_model,
								jsonReader : {
									root : 'gridModelToShow',
									repeatitems : false,
									id : 'cdServPrest'
								},
//								width: '100%',
//								height: '100%',
						        scrollOffset: 0,
								width : 1050,
								emptyrecords : 'Sin registros',
								rowNum : 0,
//								rowList : [ ],
								pager : '#gridServPrestPager',
								sortname : 'cdSector, cdGrupoProd, cdProd',
								viewrecords : true,
								sortorder : 'asc',
								shrinkToFit : false,
								caption : 'Servicios Prestados',
								height : '100%',
						        footerrow: true,
						        userDataOnFooter: true,
								onSelectRow : function(row_id) {
//									var values = jQuery("#gridServPrestId")
//											.getRowData(row_id);
									// showHideEdit(values['cdServPrest']);
								},
								loadComplete : function( data ){
									totales = data.totales;
									sumaTotales(totales);
									$('#gridServPrestId').trigger('reloadGrid');
									if(data.result.errorCode != '0'){
										setErrorMsg($('#conServPrest_responseMsgs'),data.result.errorDesc);
										$('#conServPrest_responseMsgs').show();
									}else{$('#conServPrest_responseMsgs').hide();}
									
//									COnteo de columnas
									var rows = jQuery("#gridServPrestId").jqGrid('getGridParam', 'records');
									if(rows > 0)
										hideShowButtonsSP(true);
									else
										hideShowButtonsSP(false);
									
									rows = jQuery("#gridServPrestId").getDataIDs();									
									if (rows.length == 0) {
                   	            	  height = 0; //23.52 * rowData.length; 
                   	               } else  if (rows.length <= 30){
                   	            	   height = '100%'; //23.52 * rowData.length; 
                   	               }else{
                   	            	   height = 400;
                   	               }
                                     
                                    $("#gridServPrestId").jqGrid("setGridHeight",height);
								}
								 
							});
					$('.ui-jqgrid-title').css('width', '98%');

					// ??? no se que hacen ������
					$(".ui-jqgrid-labels").css('font-size', '10px');
					$(".ui-pg-table").css('font-size', '10px');
					// Saco los botones genericos de la grilla
					$("#gridServPrestId").navGrid('#gridDifConciliaPager', {
						refresh : false,
						edit : false,
						add : false,
						del : false,	
						search : false
					});
				    
					// Reload de la tabla para el display de los datos cargados

					addButtonsToServPrestGrid();


					jQuery("#gridServPrestId").jqGrid('setGroupHeaders', {
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
							startColumnName : 'ctServPrestTotal',
							numberOfColumns : 6,
							titleText : 'Totales'
						}

						]
					});
//					jQuery("#gridServPrestId").jqGrid('setFrozenColumns');	
				}
			}
		});
	} catch (e) {
		jsError('loadServPrestGrid(...)', e);
		setErrorMsg($('#conServPrest_responseMsgs'),json.result.errorDesc);
		$('#conServPrest_responseMsgs').show();
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
	//$('#selectedProducto').selectmenu('destroy');
	//$('#selectedProducto').selectmenu({
	//	style : 'dropdown',
	//	width : '200px'
	//}).selectmenu('disabled', true);
}

function recargarProducto(cdProveedor, cdSector) {
	callJsonAction("comboConciliacionPeriodos.action", "opcion=1&cdProveedor="+ cdProveedor + "&cdSector=" + cdSector, "successProductos","errorProducto");
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
				//$('#selectedProducto').selectmenu('destroy');
				//$('#selectedProducto').selectmenu({
				//	style : 'dropdown',
				//	width : '200px'
				//});

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
    

    jQuery("#gridServPrestId").footerData('set',{cdGrupoProd: vueltas[0], 
      ctServPrestMes1 : vueltas[1] , valServPrestMes1: vueltas[2], ctRemitosMes1 : vueltas[3] , ctRemitosAConcilMes1: vueltas[4], 
      ctServPrestMes2 : vueltas[5] , valServPrestMes2: vueltas[6], ctRemitosMes2 : vueltas[7] , ctRemitosAConcilMes2: vueltas[8], 
      ctServPrestMes3 : vueltas[9] , valServPrestMes3: vueltas[10], ctRemitosMes3 : vueltas[11] , ctRemitosAConcilMes3: vueltas[12], 
      ctServPrestMes4 : vueltas[13] , valServPrestMes4: vueltas[14], ctRemitosMes4 : vueltas[15] , ctRemitosAConcilMes4: vueltas[16],
      ctServPrestMes5 : vueltas[17] , valServPrestMes5: vueltas[18], ctRemitosMes5 : vueltas[19] , ctRemitosAConcilMes5: vueltas[20], 
      ctServPrestMes6 : vueltas[21] , valServPrestMes6: vueltas[22], ctRemitosMes6 : vueltas[23] , ctRemitosAConcilMes6: vueltas[24],
      ctServPrestMes7 : vueltas[25] , valServPrestMes7: vueltas[26], ctRemitosMes7 : vueltas[27] , ctRemitosAConcilMes7: vueltas[28],
      ctServPrestMes8 : vueltas[29] , valServPrestMes8: vueltas[30], ctRemitosMes8 : vueltas[31] , ctRemitosAConcilMes8: vueltas[32],
      ctServPrestMes9 : vueltas[33] , valServPrestMes9: vueltas[34], ctRemitosMes9 : vueltas[35] , ctRemitosAConcilMes9: vueltas[36],
      ctServPrestMes10 : vueltas[37] , valServPrestMes10: vueltas[38], ctRemitosMes10 : vueltas[39] , ctRemitosAConcilMes10: vueltas[40],
      ctServPrestMes11 : vueltas[41] , valServPrestMes11: vueltas[42], ctRemitosMes11 : vueltas[43] , ctRemitosAConcilMes11: vueltas[44],
      ctServPrestMes12 : vueltas[45] , valServPrestMes12: vueltas[46], ctRemitosMes12 : vueltas[47] , ctRemitosAConcilMes12: vueltas[48],

        ctServPrestTotal : vueltas[49] , valServPrestTotal: vueltas[50], valBrutaTotal : vueltas[51] , 
        valNetoTotal: vueltas[52], ctRemitosTotal : vueltas[53] , ctRemitosAConcilTotal: vueltas[54]});
}
function sumaTotCol(col){
	var grid = $("#gridServPrestId");
    var sum = grid.jqGrid('getCol', col, false, 'sum');
    return sum;
}
function seteaValorTotal(col , sum){
	var grid = $("#gridServPrestId");
	 grid.jqGrid('footerData','set',{col: sum });
}

function datepickers() {
	$("#fhDesde")
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
			.attr('readonly', 'true')
			.button({
				icons : {
					primary : 'ui-icon ui-icon-calendar'
				}
			})
			.removeClass()
			.addClass(
					'ui-button ui-widget ui-state-default ui-button-icon-only ui-corner-right ui-button-icon');

	$("#fhHasta")
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
	
	$("#fhDesde").attr('readonly', 'true');
	$("#fhHasta").attr('readonly', 'true');
}

function errorPeriodos(cod, des) {
	jsError(cod, "111."+des);
}

/**
 * @autor XA50126
 * 
 * Funcion que ejecuta la exportacion de la grilla
 * 
 */
function exportGridXLS(){
	exportDataGridToHtmlSP('Consulta Servicios Prestados','gridServPrestId','xls');
}

function exportGridPDF(){
	exportDataGridToHtmlSP('Consulta Servicios Prestados','gridServPrestId','pdf');
}