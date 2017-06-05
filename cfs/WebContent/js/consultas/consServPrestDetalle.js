var expandido = "no";
$(document).ready(function() {

	destroyCombos();

	$('#btnBusCons').button({
		icons : {
			secondary : "ui-icon-search"
		}
	}).click(function() {
		search();
	});
	$('#btnExpand').button({
		icons : {
			secondary : "ui-icon-gear"
		}
	}).click(function() {
//		if(r == true){
			expandirContraer();
			search();
//		}
	});

	$('#btnContract').button({
		icons : {
			secondary : "ui-icon-gear"
		}
	}).click(function() {
		var r = confirm("Recuerde que al contraer los grupos se descartar\u00e1 el filtro de producto");
		if(r == true){
			expandirContraer();
			search();
		}
	});
	
	$('#btnLimpiarDesde').button({
		icons : {
			primary : "ui-icon-trash"
		} ,
		text : false
	}).click(function() {
		$('#fhRemito').datepicker('setDate', null);
	});
    $('#btnLimpiarHasta').button({
		icons : {
			primary : "ui-icon-trash"
		},
    	text : false
	}).click(function() {
		$('#fhFinServ').datepicker('setDate', null);
	});
	
	initialiseFiltros();
	
   	if ($("#sector").val() != "0") {
   	   	$('#selectedSector').selectmenu('setValue', $("#sector").val());	
       $('#selectedSector').selectmenu('disabled', true);	
    }
   		

	initServPrestDetalleDialog();
        datepickers();
    $('#btnExportar').button({icons : {secondary : "ui-icon-bookmark"}}).click(function() {exportDataGrid2(true);});	
    $('#btnImprimir').button({icons : {secondary : "ui-icon-print"}}).click(function() {exportDataGrid2(false);});
	hideShowButtonsSF(false);
	
	//$('#btnExportar').button({icons : {secondary : "ui-icon-bookmark"}}).click(function() {alert('Exportar');});
	//$('#btnImprimir').button({icons : {secondary : "ui-icon-print"}}).click(function() {alert('Imprimir');});
        $('#btnResumen').button().click(function () {
            
            var url = 'ConsServPrest.action';
            
            url += '?selectedProveedor=' + $('#selectedProveedor').val();
            url += '&selectedSector=' + $('#selectedSector').val();
            url += '&selectedGrupo=' + $('#selectedGrupo').val();
            url += '&selectedProducto=' + $('#selectedProducto').val();
            url += '&nbUniVal=' + $('#nbUniVal').val();
            url += '&fhDesde=' + $('#fhRemito').val();
            url += '&fhHasta=' + $('#fhFinServ').val();
            url += '&remitoDesde=' + $('#remitoDesde').val();
            url += '&remitoHasta=' + $('#remitoHasta').val();
            url += '&cdLote=' + $('#cdLote').val();
            url += '&stLoteDet=' + $('#stLoteDet').val();
            
            loadContentDiv(url);
        });
        $('#panelExport').show();
        
        if (($('#selectedProveedor').val() != null) && ($('#selectedProveedor').val() != '') && ($('#selectedProveedor').val() != '0') &&
            ($('#selectedSector').val() != null) && ($('#selectedSector').val() != '') && ($('#selectedSector').val() != '0'))
            search();
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
function datepickers() {
	$("#fhRemito")
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

	$("#fhFinServ")
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
	
//	$("#fhRemito").attr('readonly', 'true');
//	$("#fhFinServ").attr('readonly', 'true');
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
		$("#filaProducto").css("display", "block");
		$("#filaUniVal").css("display", "block");
	}else{
		$("#btnExpand").css("display", "block");
		$("#btnContract").css("display", "none");
		$("#filaProducto").css("display", "none");
		$("#filaUniVal").css("display", "none");
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
function search() {
	$('#agregarGrant').val('S');
	limpiarGrilla('gridServPrestDetalleesId', 'gridServPrestDetalleesPager', 'gridServPrestDetallees');

	$('#conServPrestDetalleGrid').show();
	$('#botonera').show();
	$('#buttonCancelar').button({
		icons : {
			secondary : ""
		}
	}).click(function() {
		resetServPrestDetalleForm();
		cleanDataConfirmation();
	});
	loadServPrestDetalleGrid();
}

function initServPrestDetalleDialog() {
	$("#dialogEditServPrestDetalle").dialog({
		bgiframe : true,
		autoOpen : false,
		modal : true,
		width : 450,

		close : reloadServPrestDetalleesGrid,
		buttons : [ {
			id : 'guardar-button',
			text : "Grabar",
			click : function() {
				try {
					saveServPrestDetalle();
				} catch (e) {
					jsError('saveServPrestDetalle(...)', e.message);
				}
			}
		}, {
			id : 'cancelar-button',
			text : "Cancelar",
			click : function() {
				$('#conServPrestDetalle_diag_responseMsgs').hide();
				resetServPrestDetalleForm();
				cleanDataConfirmation();
				$(this).dialog('close');
			}
		} ]
	});
}

// AGREGA BOTONERA DE LA GRILLA
function addButtonsToServPrestDetalleGrid() {
	if ($('#addGrant').val() == 'S') {
		var title = 'Agregar ServPrestDetalle';
		$('#gridServPrestDetalleesId').navButtonAdd('#gridServPrestDetalleesPager', {
			caption : 'Agregar',
			buttonicon : 'ui-icon ui-icon-plus',
			onClickButton : function() {
				try {
					$('#gridServPrestDetalleesId').resetSelection();
					processEditServPrestDetalle(0, false, 'Ingresar ServPrestDetalle', 'A');
				} catch (e) {
					jsError('loadServPrestDetalleesGrid(...)', e);
				}
			},
			position : 'last',
			title : title
		});
	}
	if ($('#editGrant').val() == 'S') {
		var title = 'Editar ServPrestDetalle';
		$('#gridServPrestDetalleesId').navButtonAdd(
				'#gridServPrestDetalleesPager',
				{
					caption : 'Editar',
					buttonicon : 'ui-icon ui-icon-pencil',
					onClickButton : function() {
						try {
							var rowid = getSelRowFromServPrestDetalleGrid();
							if (rowid != null) {
								processEditServPrestDetalle(rowid, false,
										'Editar ServPrestDetalle', 'E');
							} else {
								seleccioneRegistro();
							}
						} catch (e) {
							jsError('loadServPrestDetalleesGrid(...)', e);
						}
					},
					position : 'last',
					title : title
				});
	}
	if ($('#deleteGrant').val() == 'S') {
		var title = 'Eliminar ServPrestDetalle';
		$('#gridServPrestDetalleesId').navButtonAdd(
				'#gridServPrestDetalleesPager',
				{
					caption : 'Eliminar',
					buttonicon : 'ui-icon ui-icon-pencil',
					onClickButton : function() {
						try {
							var rowid = getSelRowFromServPrestDetalleGrid();
							if (rowid != null) {
								processEditServPrestDetalle(rowid, false,
										'Eliminar ServPrestDetalle', 'D');
							} else {
								seleccioneRegistro();
							}
						} catch (e) {
							jsError('loadServPrestDetalleesGrid(...)', e);
						}
					},
					position : 'last',
					title : title
				});
	}
	if ($('#selectGrant').val() == 'S') {
		var title = 'Consultar ServPrestDetalle';
		$('#gridServPrestDetalleesId').navButtonAdd(
				'#gridServPrestDetalleesPager',
				{
					caption : 'Consultar',
					buttonicon : 'ui-icon ui-icon-pencil',
					onClickButton : function() {
						try {
							var rowid = getSelRowFromServPrestDetalleGrid();
							if (rowid != null) {
								processEditServPrestDetalle(rowid, false,
										'Consultar ServPrestDetalle', 'C');
							} else {
								seleccioneRegistro();
							}
						} catch (e) {
							jsError('loadServPrestDetalleesGrid(...)', e);
						}
					},
					position : 'last',
					title : title
				});
	}

}

// FUNCTIONES GENERALES GRILLA
function reloadServPrestDetalleesGrid() {
	$('#gridServPrestDetalleesId').trigger('reloadGrid');
}

function getSelRowFromServPrestDetalleGrid() {
	return $("#gridServPrestDetalleesId").getGridParam('selrow');
}

function resetServPrestDetalleForm() {
	$(':input', '#dialogEditServPrestDetalle').not('input[type=hidden], :button').val(
			'').removeAttr('checked').removeAttr('selected');
}

function cleanDataConfirmation() {
	$('#cdServPrestDetalle').val('');
	$('#nbServPrestDetalle').val('');
	$('#nbServPrestDetalleAbrev').val('');
	$('#nbServPrestDetalleAlt').val('');
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
		resetPeriodoHsOption(' Sin Periodo  ', '0', false);
		resetPeriodoDsOption(' Sin Periodo  ', '0', false);
	}
	if ($('#selectedSector').val() == "0") {
		resetProductoOption(' Sin Producto ', '0', false);
	}
}
// Da formato a los combos
function destroyCombos() {
	destroyCombosGeneralOne('selectedProducto');
//	$('#selectedProducto').selectmenu('destroy');
//	$('#selectedProducto').selectmenu({
//		style : 'dropdown',
//		width : '200px'
//	});

	destroyCombosGeneralOne('selectedProveedor');
//	$('#selectedProveedor').selectmenu('destroy');
//	$('#selectedProveedor').selectmenu({
//		style : 'dropdown',
//		width : '200px'
//	});

	destroyCombosGeneralOne('selectedGrupo');
//	$('#selectedGrupo').selectmenu('destroy');
//	$('#selectedGrupo').selectmenu({
//		style : 'dropdown',
//		width : '200px'
//	});
	destroyCombosGeneralOne('selectedSector');

//	$('#selectedSector').selectmenu('destroy');
//	$('#selectedSector').selectmenu({
//		style : 'dropdown',
//		width : '200px'
//	});

	destroyCombosGeneralOne('stLoteDet');
//	$('#stLoteDet').selectmenu('destroy');
//	$('#stLoteDet').selectmenu({
//		style : 'dropdown',
//		width : '200px'
//	});
}

// GRILLA
function loadServPrestDetalleGrid() {
	var error = '0';
	var mensaje = "";
	var params = 'selectedSector=' + $('#selectedSector').val();
	params += '&selectedProducto=' + $('#selectedProducto').val();
        params += '&selectedProveedor=' + $('#selectedProveedor').val();	
	params += '&selectedGrupo=' + $('#selectedGrupo').val();
        params += '&fhRemito=' + $('#fhRemito').val();
        params += '&fhFinServ=' + $('#fhFinServ').val();
	params += '&remitoDesde=' + $('#remitoDesde').val();
	params += '&remitoHasta=' + $('#remitoHasta').val();
	params += '&cdLote=' + $('#cdLote').val();
        params += '&stLoteDet=' + $('#stLoteDet').val();
	params += '&expand=' + expandido;
	if($('#cdLote').val() == null
	|| $('#cdLote').val() == ''){
		if($('#selectedProveedor').val() === '0'){
			error = '1';
			mensaje = "Debe seleccionar un proveedor \n";
		}
		
		if($('#fhRemito').val() === ''){
			error = '1';
			mensaje += "Debe seleccionar una Fecha Remito Desde \n";
		}else
			if(!isDate($('#fhRemito').val())){
				
				error = '1';
				mensaje += ('Fecha Desde inv\u00e1lida\n');		
			}
		if($('#fhFinServ').val() === ''){
			error = '1';
			mensaje += "Debe seleccionar una Fecha Remito Hasta \n";
		}else if(!isDate($('#fhFinServ').val())){
			error = '1';
			mensaje += ('Fecha Hasta inv\u00e1lida\n');	
		}
		
        }
        if (error === '0') {
            if (($('#fhRecibo').val() != '') && ($('#fhFinServ').val() != '')) {
                var fhDesde = $('#fhRemito').val();
                var fhHasta = $('#fhFinServ').val();
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
            if (($('#remitoDesde').val() != '') && ($('#remitoHasta').val() != '')) {
                var desde = parseInt($('#remitoDesde').val());
                var hasta = parseInt($('#remitoHasta').val());
                if (desde > hasta) {
                    error = '1';
                    mensaje += 'Nro. Remito Desde debe ser menor o igual a Nro. Remito Hasta';
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
			url : 'JsonServPrestDetalleModel.action?' + params,
			success : function(data) {
				$('#gridServPrestDetalleId').jqGrid('GridUnload');
				col_names = data.names;
				col_model = data.model;
				col_group = data.groups;
				col_index = data.index;
				totales = data.totales;
                                //params += '&cantPeriodos=' + col_index.length;
                                jQuery('#gridServPrestDetalleId').jqGrid(
                                                {
                                                        url : 'JsonServPrestDetalleList.action?' + params,
                                                        datatype : 'json',
                                                        mtype : 'POST',
                                                        colNames : col_names,
                                                        colModel : col_model,
                                                        jsonReader : {
                                                                root : 'gridModelToShow',
                                                                repeatitems : false,
                                                                id : 'cdServPrestDetalle'
                                                        },
//								width: '100%',
//								height: '100%',
                                                scrollOffset: 0,
                                                        width : 1040,
                                                        emptyrecords : 'Sin registros',
                                                        rowNum : 0,
//                                                        rowList : [ ],
                                                        pager : '#gridServPrestDetallePager',
                        								sortname : 'cdSector, cdGrupoProd, cdProd',
                                                        viewrecords : true,
                                                        sortorder : 'asc',
                                                        shrinkToFit : false,
                                                        caption : 'Servicios Prestados',
                                                       height : '100%',
                                                footerrow: true,
                                                userDataOnFooter: true,
/*                                                        onSelectRow : function(row_id) {
                                                                var values = jQuery("#gridServPrestDetalleId")
                                                                                .getRowData(row_id);
                                                                // showHideEdit(values['cdServPrestDetalle']);
                                                        },
*/                                                        
                                                loadComplete : function( data ){
                                                        totales = data.totales;
                                                        sumaTotales(totales);
                                                        $('#gridServPrestDetalleId').trigger('reloadGrid');
                                                        if(data.result.errorCode != '0'){
                                                                setErrorMsg($('#conServPrestDetalle_responseMsgs'),data.result.errorDesc);
                                                                $('#conServPrestDetalle_responseMsgs').show();
                                                        }else{$('#conServPrestDetalle_responseMsgs').hide();
                                                       }
                                                        
                                                       var rows= jQuery("#gridServPrestDetalleId").getDataIDs();
                                                        
                                                       if (rows.length == 0) {
                                      	            	  height = 0; //23.52 * rowData.length; 
                                      	               } else  if (rows.length <= 30){
                                      	            	   height = '100%'; //23.52 * rowData.length; 
                                      	               }else{
                                      	            	   height = 400;
                                      	               }
                                                        
                                                       $("#gridServPrestDetalleId").jqGrid("setGridHeight",height);
                                                        
                                                }                                               
                                 });
                                $('.ui-jqgrid-title').css('width', '98%');

                                // ??? no se que hacen ������
                                $(".ui-jqgrid-labels").css('font-size', '10px');
                                $(".ui-pg-table").css('font-size', '10px');
                        		$('.ui-jqgrid .ui-jqgrid-bdiv').css('overflow-y', 'scroll');
                                // Saco los botones genericos de la grilla
                                $("#gridServPrestDetalleId").navGrid('#gridDifConciliaPager', {
                                        refresh : false,
                                        edit : false,
                                        add : false,
                                        del : false,	
                                        search : false
                                });


                                // Reload de la tabla para el display de los datos cargados

                                addButtonsToServPrestDetalleGrid();


                                jQuery("#gridServPrestDetalleId").jqGrid('setGroupHeaders', {
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
                                                startColumnName : 'ctServPrestDetalleTotal',
                                                numberOfColumns : 6,
                                                titleText : 'Totales'
                                        }

                                        ]
                                });
//					jQuery("#gridServPrestDetalleId").jqGrid('setFrozenColumns');
			}
		});

		hideShowButtonsSF(true);
	} catch (e) {
		jsError('loadServPrestDetalleGrid(...)', e);
		setErrorMsg($('#conServPrestDetalle_responseMsgs'),json.result.errorDesc);
		$('#conServPrestDetalle_responseMsgs').show();
	}
	}else{
		alert(mensaje);
	}
}

function limpiarProveedor() {
	limpiarSector();
        $('#fhRecibo').val("");
        $('#fhFinServ').val("");
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
		width : '200px'
	}).selectmenu('disabled', true);
}

function resetPeriodoHsOption(label, value, disabled) {
	document.getElementById('fhHasta').options.length = 0;
	document.getElementById('fhHasta')[0] = new Option(label, value);
//	$('#fhHasta').selectmenu('destroy');
//	$('#fhHasta').selectmenu({
//		style : 'dropdown',
//		width : '200px'
//	}).selectmenu('disabled', true);
	$('#fhHasta').selectmenu('destroy');
	$('#fhHasta').selectmenu({
		style : 'dropdown',
		width : '100px'
	});
}

function recargarCombos(cdProveedor) {
	//recargarPeriodoDs(cdProveedor);
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
    
    jQuery("#gridServPrestDetalleId").footerData('set', {cdSector: vueltas[0], ctServPrest: vueltas[1], imPrecioTotal: vueltas[2]});
}
function sumaTotCol(col){
	var grid = $("#gridServPrestDetalleId");
    var sum = grid.jqGrid('getCol', col, false, 'sum');
    return sum;
}
function seteaValorTotal(col , sum){
	var grid = $("#gridServPrestDetalleId");
	 grid.jqGrid('footerData','set',{col: sum });
}

/**
 * Funcion que recibe los parametros de periodo de Facturacion
 * desde y hasta y los compara 
 * 
 * @param pd
 * @param ph
 * @returns {Boolean}
 * 
 * @author David
 */
function validaPerido(pd,ph){
	
	var npd = covertPeriodoToComparable(pd);
	var nph = covertPeriodoToComparable(ph);
	
	if(npd <= nph)
		return false;
	else
		return true;	
}

/**
 * Metodo que parsea el periodo y obtiene una cadena de
 * caracteres para que se puedan comparar
 * 
 * @param periodo
 * @returns {String}
 * 
 * @author David
 */
function covertPeriodoToComparable(periodo){
	
	var mes = periodo.substr(0,3);
	var anio = periodo.substr(3,2);
	
	switch(mes){
		case 'ENE':
			return '01'+anio;
			break;
		case 'FEB':
			return '02'+anio;
			break;
		case 'MAR':
			return '03'+anio;
			break;
		case 'ABR':
			return '04'+anio;
			break;
		case 'MAY':
			return '05'+anio;
			break;
		case 'JUN':
			return '06'+anio;
			break;
		case 'JUL':
			return '07'+anio;
			break;
		case 'AGO':
			return '08'+anio;
			break;
		case 'SEP':
			return '09'+anio;
			break;
		case 'OCT':
			return '10'+anio;
			break;
		case 'NOV':
			return '11'+anio;
			break;
		case 'DIC':
			return '12'+anio;
			break;
		default:
			return '01'+anio;
			break;
	}
}

	/**
	 * @author Fede
	 * 
	 * Funcion que exporta el contenido del grid a PDF/XLS
	 * @xls  Parametro que si es TRUE exporta Documento EXCEL , sino PDF
	 */
	function exportDataGrid2( xls  ){
		var params = '';
		params += $('#globalParams').val(); 

		if($.trim(params) === ''){
			fAlertCB('Para Exportar debe haber realizado una busqueda Primero');
		}else{			
			var title = 'Detalle de Servicios Prestados';
			var titleFilter = 'Proveedor,Sector ,Grupo de Productos, Producto,Fecha Remito Desde,Fecha Remito Hasta,Remito Desde, RemitoHasta , Nro. Lote, Estado Detalle ';

			var filterData = '';
			filterData += $('#selectedProveedor option:selected').text() + ';'+$('#selectedSector option:selected').text() + ';'+ $('#selectedGrupo option:selected').text()+ ';' 
							+ $('#selectedProducto option:selected').text()  + ';'
							;
			filterData +=  $('#fhRemito').val() + ';'	+ $('#fhFinServ').val() + ';'	+ $('#remitoDesde').val() + ';' 
							+ $('#remitoHasta').val() + ';'
							+ $('#cdLote').val() + ';';
			filterData += $('#stLoteDet option:selected').text();
			filterData = normalize(accentDecode(filterData));
			var printContent = '';
			printContent += exportDataFilterToHtml('Parametros de Busqueda',titleFilter,title,filterData);
			printContent += exportDataGridToHtml('',title,'gridServPrestDetalleId','12,13');
			
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