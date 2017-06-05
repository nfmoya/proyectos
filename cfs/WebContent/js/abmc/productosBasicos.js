$(document).ready(function() {
	
//	limpiaCombosPantalla();
	sePuedeInhabilitar = "S";
	
	initProductoBasicoDialog();
	cleanDataConfirmation();
	search();
	
/*
	destroyCombosGeneralOne('proveedorProductoList');
	destroyCombosGeneralOne('grupoProductoList');
	destroyCombosGeneralOne('uniValList');
	destroyCombosGeneralOne('tipValList');
	destroyCombosGeneralOne('monedaList');	
	destroyCombosGeneralOne('nbAtributoAdic1List');
	destroyCombosGeneralOne('stProdImportPrestList');
	destroyCombosGeneralOne('stProdImportFactList');
	destroyCombosGeneralOne('stRemServObligList');
	destroyCombosGeneralOne('stRemFactObligList');
	destroyCombosGeneralOne('stAdmiteRemServList');
	destroyCombosGeneralOne('stAdmiteRemFactList');
	destroyCombosGeneralOne('stConcilSinValList');
	destroyCombosGeneralOne('stServSinConcilList');
	destroyCombosGeneralOne('stFactSinConcilList');
	destroyCombosGeneralOne('habilitadoProductoList');		

*/	
});

function search() {
	$('#agregarGrant').val('S');
	limpiarGrilla('gridProductosBasicosId', 'gridProductosBasicosPager',
			'gridProductosBasicos');

	$('#productosBasicosGrid').show();
	$('#botonera').show();

	loadProductosBasicosGrid();
}

function initProductoBasicoDialog() {
	$("#dialogEditProducto").dialog({
		bgiframe : true,
		autoOpen : false,
		modal : true,
		height : 500,
		width : 850,

		close : reloadProductosBasicosGrid,
		buttons : [ {
			id : 'btnGuardar',
			text : "Grabar",
			click : function() {
				try {
					saveProductoBasico();
				} catch (e) {
					jsError('saveProductoBasico(...)', e.message);
				}
			}
		}, {
			id : 'btnCancelar',
			text : "Cancelar",
			click : function() {
				$('#prod_basic_responseMsgs').hide();
				resetProductoBasicoForm();
				cleanDataConfirmation();
				$(this).dialog('close');
			}
		} ]
	});
}

// GRILLA
function loadProductosBasicosGrid() {
	var params = '';
	params += 'cdProveedor=' + $('#filtroProveedorList').val();
	params += '&cdProducto=' + $('#filtroProductoList').val();
	params += '&cdGrupo=' + $('#filtroGrupoList').val();
	params += '&stHabilitado=' + $('#filtroHabilitadoList').val();
	try {
		showGrid({
			id : 'gridProductosBasicosId',
			idPager : 'gridProductosBasicosPager',
			url : 'JsonProductosList.action?' + params,
			colNames : [ 'Proveedor', 'C&oacute;digo Producto',
					'Descripci&oacute;n', 'Descr. Corta',
					'C&oacute;digo Grupo Prod', 'Unid Val', 'Sec Sol Serv',
					'Sec Con Serv', 'Sec Con Fact', 'Prod Import Prest',
					'Prest Import Fact', 'Rem Serv Oblig', 'Rem Fact Oblig',
					'Admite Rem Serv', 'Admite Rem Fact', 'Atrib Ref1',
					'Atrib Ref2', 'Concil Sin Val', 'Serv Sin Concil',
					'Fact Sin Concil', 'D&iacute;a Emi Desde',
					'D&iacute;a Emi Hasta', 'D&iacute;a Cierre Desde',
					'D&iacute;a Cierre Hasta', 'Atrib Adic1', 'Atrib Adic2',
					'Atrib Adic3', 'Atrib Adic4', 'Atrib Adic5', 'Tip Val', 'Tip Val','Moneda', 'Porc Var Max', 'Habilitado' ],
			colModel : [ {
				name : 'cdProveedor',
				width : 100,
				align : 'left',
				hidden : true
			}, {
				name : 'cdProducto',
				index : 'id',
				width : 160,
				align : 'left',
				hidden : false
			}, {
				name : 'nbProducto',
				width : 220,
				align : 'left',
				hidden : false
			}, {
				name : 'nbProductoCorto',
				width : 160,
				align : 'left',
				hidden : true
			}, {
				name : 'cdGrupoProducto',
				width : 100,
				align : 'center',
				hidden : false
			},{
				name : 'cdUniVal',
				width : 60,
				align : 'center',
				hidden : false
			}, {
				name : 'cdSecSolServ',
				width : 40,
				align : 'center',
				hidden : true
			}, {
				name : 'cdSecConServ',
				width : 40,
				align : 'center',
				hidden : true
			}, {
				name : 'cdSecConFact',
				width : 40,
				align : 'center',
				hidden : true
			}, {
				name : 'stProdImportPrest',
				width : 40,
				align : 'center',
				hidden : true
			}, {
				name : 'stProdImportFact',
				width : 40,
				align : 'center',
				hidden : true
			}, {
				name : 'stRemServOblig',
				width : 40,
				align : 'center',
				hidden : true
			}, {
				name : 'stRemFactOblig',
				width : 40,
				align : 'center',
				hidden : true
			}, {
				name : 'stAdmiteRemServ',
				width : 40,
				align : 'center',
				hidden : true
			}, {
				name : 'stAdmiteRemFact',
				width : 40,
				align : 'center',
				hidden : true
			}, {
				name : 'nbAtributoRef1',
				width : 40,
				align : 'center',
				hidden : true
			}, {
				name : 'nbAtributoRef2',
				width : 40,
				align : 'center',
				hidden : true
			}, {
				name : 'stConcilSinVal',
				width : 40,
				align : 'center',
				hidden : true
			}, {
				name : 'stServSinConcil',
				width : 40,
				align : 'center',
				hidden : true
			}, {
				name : 'stFactSinConcil',
				width : 40,
				align : 'center',
				hidden : true
			}, {
				name : 'nuDiaEmiFDesde',
				width : 40,
				align : 'center',
				hidden : true
			}, {
				name : 'nuDiaEmiFHasta',
				width : 40,
				align : 'center',
				hidden : true
			}, {
				name : 'nuDiaCierreFDesde',
				width : 40,
				align : 'center',
				hidden : true
			}, {
				name : 'nuDiaCierreFHasta',
				width : 40,
				align : 'center',
				hidden : true
			}, {
				name : 'nbAtributoAdic1',
				width : 40,
				align : 'center',
				hidden : true
			}, {
				name : 'nbAtributoAdic2',
				width : 40,
				align : 'center',
				hidden : true
			}, {
				name : 'nbAtributoAdic3',
				width : 40,
				align : 'center',
				hidden : true
			}, {
				name : 'nbAtributoAdic4',
				width : 40,
				align : 'center',
				hidden : true
			}, {
				name : 'nbAtributoAdic5',
				width : 40,
				align : 'center',
				hidden : true
			},{
				name : 'descTipVal',
				width : 170,
				align : 'center',
				hidden : false
			}, {
				name : 'cdTipVal',
				width : 60,
				align : 'center',
				hidden : true
			}, {
				name : 'cdMoneda',
				width : 60,
				align : 'center',
				hidden : true
			}, {
				name : 'nuPorcVarMax',
				width : 60,
				align : 'center',
				hidden : true
			}, {
				name : 'stHabilitado',
				width : 50,
				align : 'center',
				hidden : false
			} ],
			rowNum : 10,
			rowList : [ 10, 15, 20 ],
			sortName : 'cdProducto',
			caption : "Productos Datos B&aacute;sicos",
			height : '100%',
			width : 780,
			multiselect : false,
			loadonce : true,
			loadCompleteFunc : '',
			editurl : 'clientArray',
			shrinkToFit : true
		});
		$('.ui-jqgrid-title').css('width', '98%');
		addButtonsToProductoBasicoGrid();
		$("#gridProductosBasicosId").jqGrid('setGridParam', {
			onSelectRow : function(rowid, iRow, iCol, e) {
				asignoProveedorProducto(rowid);
			}
		});

	} catch (e) {
		jsError('loadProductosBasicosGrid(...)', e);
	}
}

// AGREGA BOTONERA DE LA GRILLA
function addButtonsToProductoBasicoGrid() {
	if ($('#addGrant').val() == 'S') {
		var title = 'Agregar Producto';
		$('#gridProductosBasicosId').navButtonAdd(
				'#gridProductosBasicosPager',
				{
					caption : 'Agregar',
					buttonicon : 'ui-icon ui-icon-plus',
					onClickButton : function() {
						try {
							$('#gridProductosBasicosId').resetSelection();
							processEditProductoBasico(0, false,
									'Ingresar Producto', 'A');
						} catch (e) {
							jsError('loadProductosBasicosGrid(...)', e);
						}
					},
					position : 'last',
					title : title
				});
	}
	if ($('#editGrant').val() == 'S') {
		var title = 'Editar Producto';
		$('#gridProductosBasicosId').navButtonAdd(
				'#gridProductosBasicosPager',
				{
					caption : 'Editar',
					buttonicon : 'ui-icon ui-icon-pencil',
					onClickButton : function() {
						try {
							var rowid = getSelRowFromProductoBasicoGrid();
							if (rowid != null) {
								processEditProductoBasico(rowid, false,
										'Editar Producto', 'E');
							} else {
								seleccioneRegistro();
							}
						} catch (e) {
							jsError('loadProductosBasicosGrid(...)', e);
						}
					},
					position : 'last',
					title : title
				});
	}
	if ($('#deleteGrant').val() == 'S') {
		var title = 'Eliminar Producto';
		$('#gridProductosBasicosId').navButtonAdd(
				'#gridProductosBasicosPager',
				{
					caption : 'Eliminar',
					buttonicon : 'ui-icon ui-icon-pencil',
					onClickButton : function() {
						try {
							var rowid = getSelRowFromProductoBasicoGrid();
							if (rowid != null) {
								processEditProductoBasico(rowid, false,
										'Eliminar Producto', 'D');
							} else {
								seleccioneRegistro();
							}
						} catch (e) {
							jsError('loadProductosBasicosGrid(...)', e);
						}
					},
					position : 'last',
					title : title
				});
	}
	if ($('#selectGrant').val() == 'S') {
		var title = 'Consulta Producto';
		$('#gridProductosBasicosId').navButtonAdd(
				'#gridProductosBasicosPager',
				{
					caption : 'Consultar',
					buttonicon : 'ui-icon ui-icon-pencil',
					onClickButton : function() {
						try {
							var rowid = getSelRowFromProductoBasicoGrid();
							if (rowid != null) {
								processEditProductoBasico(rowid, false,
										'Consultar Producto', 'C');
							} else {
								seleccioneRegistro();
							}
						} catch (e) {
							jsError('loadProductosBasicosGrid(...)', e);
						}
					},
					position : 'last',
					title : title
				});
	}
}

// FUNCTIONES GENERALES GRILLA
function reloadProductosBasicosGrid() {
	$('#gridProductosBasicosId').trigger('reloadGrid');
//	recargarProductoAdmin($('#filtroProveedorList').val());
}

function getSelRowFromProductoBasicoGrid() {
	return $("#gridProductosBasicosId").getGridParam('selrow');
}

function resetProductoBasicoForm() {
	$(':input', '#dialogEditProducto').not('input[type=hidden], :button').val(
			'').removeAttr('checked').removeAttr('selected');
}

function asignoProveedorProducto(rowid) {
	var cdProveedor = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'cdProveedor');
	var cdProducto  = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'cdProducto');
	var nbProducto  = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'nbProducto');
	var cdTipVal    = $.trim($('#gridProductosBasicosId').jqGrid('getCell', rowid, 'cdTipVal'));	
	$('#cdProveedorSeleccionado').val(cdProveedor);
	$('#cdProductoSeleccionado').val(cdProducto);
	$('#nbProductoSeleccionado').val(nbProducto);
	$('#productoTipVal').val(cdTipVal.trim());
	$('#tarifaCantDesde').val("");
	$('#tarifaFhHasta').val("");	
	if (cdTipVal.trim() == "1") {
		$("#tabsApplication").enableTab(1);
		$("#tabsApplication").disableTab(2);
		$("#tabsApplication").enableTab(3);
		$("#tabsApplication").disableTab(4);
		$("#tab1").show();
		$("#tab2").hide();
		$("#tab3").show();
		$("#tab4").hide();
	} else {
		$("#tabsApplication").disableTab(1);
		$("#tabsApplication").enableTab(2);
		$("#tabsApplication").enableTab(3);
		$("#tabsApplication").disableTab(4);
		$("#tab1").hide();
		$("#tab2").show();
		$("#tab3").show();
		$("#tab4").hide();
		if (cdTipVal.trim() == "2") {	
			$("#tab4").text("Tarifas x Cantidad Val");
		} else {	
			$("#tab4").text("Tarifas en % a aplicar");
		}
	}	
}

// EDIT PRODUCTO
function processEditProductoBasico(rowid, tipo, sTitle, tipoAcceso) {
	cleanDataConfirmation();
	cleanMsgConfirmation();

	$("#tipoModificacion").val(tipoAcceso);
	recargarSectores();
	recargarProveedores();
	
	
//	limpiaCombosPantalla();
	
	if (tipoAcceso == 'A') {
		$('#proveedorProductoList').selectmenu('setValue', $('#filtroProveedorList').val());
		$('#grupoProductoList').selectmenu('setValue', $('#filtroGrupoList').val());
		$('#uniValList').selectmenu('setValue', '0');

		$("#proveedorProductoList").selectmenu('disabled', false);
		$("#cdProducto").attr('disabled', false).removeClass("ui-state-disabled");
		$("#nbProducto").attr('disabled', false).removeClass("ui-state-disabled");
		$("#nbProductoCorto").attr('disabled', false).removeClass("ui-state-disabled");
		$('#grupoProductoList').selectmenu('disabled', false);
		$('#uniValList').selectmenu('disabled', false);
		// $("#cdGrupoProducto").attr('disabled',false).removeClass("ui-state-disabled");
		// $("#cdUniVal").attr('disabled',false).removeClass("ui-state-disabled");
		$('#cdSecSolServList').selectmenu('disabled', false).selectmenu('setValue', '');
		$('#cdSecConServList').selectmenu('disabled', false).selectmenu('setValue', '');
		$('#cdSecConFactList').selectmenu('disabled', false).selectmenu('setValue', '');
		$("#stProdImportPrestList").selectmenu('disabled', false).selectmenu('setValue', 'S');
		$("#stProdImportFactList").selectmenu('disabled', false).selectmenu('setValue', 'S');
		$("#stRemServObligList").selectmenu('disabled', false).selectmenu('setValue', 'S');
		$("#stRemFactObligList").selectmenu('disabled', false).selectmenu('setValue', 'S');
		$("#stAdmiteRemServList").selectmenu('disabled', false).selectmenu('setValue', 'S');
		$("#stAdmiteRemFactList").selectmenu('disabled', false).selectmenu('setValue', 'S');
		$("#nbAtributoRef1").attr('disabled', false).removeClass("ui-state-disabled");
		$("#nbAtributoRef2").attr('disabled', false).removeClass("ui-state-disabled");
		$("#stConcilSinValList").selectmenu('disabled', false).selectmenu('setValue', 'S');
		$("#stServSinConcilList").selectmenu('disabled', false).selectmenu('setValue', 'S');
		$("#stFactSinConcilList").selectmenu('disabled', false).selectmenu('setValue', 'S');
		$("#nuDiaEmiFDesde").attr('disabled', false).removeClass("ui-state-disabled");
		$("#nuDiaEmiFHasta").attr('disabled', false).removeClass("ui-state-disabled");
		$("#nuDiaCierreFDesde").attr('disabled', false).removeClass("ui-state-disabled");
		$("#nuDiaCierreFHasta").attr('disabled', false).removeClass("ui-state-disabled");
		$('#nbAtributoAdic1List').selectmenu('disabled', false).selectmenu('setValue', 'N');
//		$("#nbAtributoAdic1").attr('disabled', false).removeClass("ui-state-disabled");
		$("#nbAtributoAdic2").attr('disabled', false).removeClass("ui-state-disabled");
		$("#nbAtributoAdic3").attr('disabled', false).removeClass("ui-state-disabled");
		$("#nbAtributoAdic4").attr('disabled', false).removeClass("ui-state-disabled");
		$("#nbAtributoAdic5").attr('disabled', false).removeClass("ui-state-disabled");
		$('#tipValList').selectmenu('disabled', false).selectmenu('setValue', '0');
		$('#monedaList').selectmenu('disabled', false).selectmenu('setValue', '');
		$("#nuPorcVarMax").attr('disabled', false).removeClass("ui-state-disabled");
		$('#habilitadoProductoList').selectmenu('disabled', false).selectmenu('setValue', 'S');
		$(':input', '#ABMCProductosBasicos').not(':text, :hidden').val('');

		$("#nuPorcVarMax").val('0');
		$("#nuDiaEmiFDesde").val('999');
		$("#nuDiaEmiFHasta").val('0');
		$("#nuDiaCierreFDesde").val('999');
		$("#nuDiaCierreFHasta").val('999');

	}
	if (tipoAcceso != 'A') {
		var cdProveedor = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'cdProveedor');
		var cdProducto = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'cdProducto');
		var nbProducto = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'nbProducto');
		var nbProductoCorto = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'nbProductoCorto');
		var cdGrupoProducto = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'cdGrupoProducto');
		var cdUniVal = $.trim($('#gridProductosBasicosId').jqGrid('getCell', rowid, 'cdUniVal'));
		var cdSecSolServ = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'cdSecSolServ');
		var cdSecConServ = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'cdSecConServ');
		var cdSecConFact = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'cdSecConFact');
		var stProdImportPrest = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'stProdImportPrest');
		var stProdImportFact = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'stProdImportFact');
		var stRemServOblig = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'stRemServOblig');
		var stRemFactOblig = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'stRemFactOblig');
		var stAdmiteRemServ = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'stAdmiteRemServ');
		var stAdmiteRemFact = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'stAdmiteRemFact');
		var nbAtributoRef1 = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'nbAtributoRef1');
		var nbAtributoRef2 = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'nbAtributoRef2');
		var stConcilSinVal = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'stConcilSinVal');
		var stServSinConcil = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'stServSinConcil');
		var stFactSinConcil = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'stFactSinConcil');
		var nuDiaEmiFDesde = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'nuDiaEmiFDesde');
		var nuDiaEmiFHasta = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'nuDiaEmiFHasta');
		var nuDiaCierreFDesde = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'nuDiaCierreFDesde');
		var nuDiaCierreFHasta = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'nuDiaCierreFHasta');
		var nbAtributoAdic1 = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'nbAtributoAdic1');
		var nbAtributoAdic2 = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'nbAtributoAdic2');
		var nbAtributoAdic3 = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'nbAtributoAdic3');
		var nbAtributoAdic4 = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'nbAtributoAdic4');
		var nbAtributoAdic5 = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'nbAtributoAdic5');		
		var cdTipVal = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'cdTipVal');
		var cdMoneda = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'cdMoneda');
		var nuPorcVarMax = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'nuPorcVarMax');
		var stHabilitado = $('#gridProductosBasicosId').jqGrid('getCell', rowid, 'stHabilitado');

		// if (confirm('Modifica el Producto seleccionado ?') == true) {
		$('#proveedorProductoList').selectmenu('setValue', cdProveedor);
		$("#cdProducto").val(cdProducto);
		$("#nbProducto").val(nbProducto);
		$("#nbProductoCorto").val(nbProductoCorto);
 		$('#grupoProductoList').selectmenu('setValue', cdGrupoProducto);
//		$("#grupoProductoList").val(cdGrupoProducto);
 		$('#uniValList').selectmenu('setValue', cdUniVal);
//		$("#uniValList").val(cdUniVal);
		
		$('#cdSecSolServList').selectmenu('setValue', cdSecSolServ);
		$('#cdSecConServList').selectmenu('setValue', cdSecConServ);
		$('#cdSecConFactList').selectmenu('setValue', cdSecConFact);
		
		$("#stProdImportPrestList").selectmenu('setValue', stProdImportPrest);
//		$("#stProdImportPrestList").val(stProdImportPrest);
		$("#stProdImportFactList").selectmenu('setValue', stProdImportFact);
//		$("#stProdImportFactList").val(stProdImportFact);
		$("#stRemServObligList").selectmenu('setValue', stRemServOblig);
//		$("#stRemServObligList").val(stRemServOblig);
		$("#stRemFactObligList").selectmenu('setValue', stRemFactOblig);
//		$("#stRemFactObligList").val(stRemFactOblig);
		$("#stAdmiteRemServList").selectmenu('setValue', stAdmiteRemServ);
//		$("#stAdmiteRemServList").val(stAdmiteRemServ);
		$("#stAdmiteRemFactList").selectmenu('setValue', stAdmiteRemFact);
//		$("#stAdmiteRemFactList").val(stAdmiteRemFact);
		$("#nbAtributoRef1").val(nbAtributoRef1);
		$("#nbAtributoRef2").val(nbAtributoRef2);
		$("#stConcilSinValList").selectmenu('setValue', stConcilSinVal);
//		$("#stConcilSinValList").val(stConcilSinVal);
		$("#stServSinConcilList").selectmenu('setValue', stServSinConcil);
//		$("#stServSinConcilList").val(stServSinConcil);
		$("#stFactSinConcilList").selectmenu('setValue', stFactSinConcil);
//		$("#stFactSinConcilList").val(stFactSinConcil);
		$("#nuDiaEmiFDesde").val(nuDiaEmiFDesde);
		$("#nuDiaEmiFHasta").val(nuDiaEmiFHasta);
		$("#nuDiaCierreFDesde").val(nuDiaCierreFDesde);
		$("#nuDiaCierreFHasta").val(nuDiaCierreFHasta);
//		$("#nbAtributoAdic1").val(nbAtributoAdic1);
		$('#nbAtributoAdic1List').selectmenu('setValue', nbAtributoAdic1);		
		$("#nbAtributoAdic2").val(nbAtributoAdic2);
		$("#nbAtributoAdic3").val(nbAtributoAdic3);
		$("#nbAtributoAdic4").val(nbAtributoAdic4);
		$("#nbAtributoAdic5").val(nbAtributoAdic5);
		$('#habilitadoProductoList').selectmenu('setValue', stHabilitado);
		
//		alert(''+cdUniVal+'-'+cdTipVal+'-'+cdMoneda+'-'+cdGrupoProducto+'-'+nuPorcVarMax);

		$('#tipValList').selectmenu('setValue', cdTipVal);
//		$('#tipValList').val(cdTipVal);
		$('#monedaList').selectmenu('setValue', cdMoneda);
//		$('#monedaList').val(cdMoneda);
		$('#nuPorcVarMax').val(nuPorcVarMax);
		
		$('#proveedorProductoList').selectmenu('disabled', 'disabled');
		$('#cdProducto').attr('disabled', 'disabled').addClass("ui-state-disabled");

		if (tipoAcceso == 'C' || tipoAcceso == 'D') {
			$("#nbProducto").attr('disabled', true).addClass("ui-state-disabled");
			$("#nbProductoCorto").attr('disabled', true).addClass("ui-state-disabled");
			$('#grupoProductoList').selectmenu('disabled', true);
			$('#uniValList').selectmenu('disabled', true);
			// $("#cdGrupoProducto").attr('disabled',true).addClass("ui-state-disabled");
			// $("#cdUniVal").attr('disabled',true).addClass("ui-state-disabled");
			$('#cdSecSolServList').selectmenu('disabled', true);
			$('#cdSecConServList').selectmenu('disabled', true);
			$('#cdSecConFactList').selectmenu('disabled', true);
			$("#stProdImportPrestList").selectmenu('disabled', true);
			$("#stProdImportFactList").selectmenu('disabled', true);
			$("#stRemServObligList").selectmenu('disabled', true);
			$("#stRemFactObligList").selectmenu('disabled', true);
			$("#stAdmiteRemServList").selectmenu('disabled', true);
			$("#stAdmiteRemFactList").selectmenu('disabled', true);
			$("#nbAtributoRef1").attr('disabled', true).addClass("ui-state-disabled");
			$("#nbAtributoRef2").attr('disabled', true).addClass("ui-state-disabled");
			$("#stConcilSinValList").selectmenu('disabled', true);
			$("#stServSinConcilList").selectmenu('disabled', true);
			$("#stFactSinConcilList").selectmenu('disabled', true);
			$("#nuDiaEmiFDesde").attr('disabled', true).addClass("ui-state-disabled");
			$("#nuDiaEmiFHasta").attr('disabled', true).addClass("ui-state-disabled");
			$("#nuDiaCierreFDesde").attr('disabled', true).addClass("ui-state-disabled");
			$("#nuDiaCierreFHasta").attr('disabled', true).addClass("ui-state-disabled");
			$("#nbAtributoAdic1List").selectmenu('disabled', true);
			$("#nbAtributoAdic2").attr('disabled', true).addClass("ui-state-disabled");
			$("#nbAtributoAdic3").attr('disabled', true).addClass("ui-state-disabled");
			$("#nbAtributoAdic4").attr('disabled', true).addClass("ui-state-disabled");
			$("#nbAtributoAdic5").attr('disabled', true).addClass("ui-state-disabled");
			
			$('#tipValList').selectmenu('disabled', true);
			$('#monedaList').selectmenu('disabled', true);
			$("#nuPorcVarMax").attr('disabled', true).addClass("ui-state-disabled");			
			$('#habilitadoProductoList').selectmenu('disabled', true);
		} else {
			$("#nbProducto").attr('disabled', false).removeClass("ui-state-disabled");
			$("#nbProductoCorto").attr('disabled', false).removeClass("ui-state-disabled");
			$('#grupoProductoList').selectmenu('disabled', false);
			$('#uniValList').selectmenu('disabled', false);
			// $("#cdGrupoProducto").attr('disabled',false).removeClass("ui-state-disabled");
			// $("#cdUniVal").attr('disabled',false).removeClass("ui-state-disabled");
			$('#cdSecSolServList').selectmenu('disabled', false);
			$('#cdSecConServList').selectmenu('disabled', false);
			$('#cdSecConFactList').selectmenu('disabled', false);
			$("#stProdImportPrestList").selectmenu('disabled', false);
			$("#stProdImportFactList").selectmenu('disabled', false);
			$("#stRemServObligList").selectmenu('disabled', false);
			$("#stRemFactObligList").selectmenu('disabled', false);
			$("#stAdmiteRemServList").selectmenu('disabled', false);
			$("#stAdmiteRemFactList").selectmenu('disabled', false);
			$("#nbAtributoRef1").attr('disabled', false).removeClass("ui-state-disabled");
			$("#nbAtributoRef2").attr('disabled', false).removeClass("ui-state-disabled");
			$("#stConcilSinValList").selectmenu('disabled', false);
			$("#stServSinConcilList").selectmenu('disabled', false);
			$("#stFactSinConcilList").selectmenu('disabled', false);
			$("#nuDiaEmiFDesde").attr('disabled', false).removeClass("ui-state-disabled");
			$("#nuDiaEmiFHasta").attr('disabled', false).removeClass("ui-state-disabled");
			$("#nuDiaCierreFDesde").attr('disabled', false).removeClass("ui-state-disabled");
			$("#nuDiaCierreFHasta").attr('disabled', false).removeClass("ui-state-disabled");
			$("#nbAtributoAdic1List").selectmenu('disabled', false);
			$("#nbAtributoAdic2").attr('disabled', false).removeClass("ui-state-disabled");
			$("#nbAtributoAdic3").attr('disabled', false).removeClass("ui-state-disabled");
			$("#nbAtributoAdic4").attr('disabled', false).removeClass("ui-state-disabled");
			$("#nbAtributoAdic5").attr('disabled', false).removeClass("ui-state-disabled");
			$('#tipValList').selectmenu('disabled', false);
			$('#monedaList').selectmenu('disabled', false);
			$("#nuPorcVarMax").attr('disabled', false).removeClass("ui-state-disabled");
			$('#habilitadoProductoList').selectmenu('disabled', false);
		}
	}
	if (tipoAcceso == 'C') {
		$("#btnGuardar").hide();
	} else {
		$("#btnGuardar").show();
	}
	$('#dialogEditProducto').dialog('option', 'title', sTitle);
	$('#dialogEditProducto').dialog('open');
}

function saveProductoBasico() {
	var params = '';
	var err = "";
	var tipoModificacion = $("#tipoModificacion").val();

	if (tipoModificacion == "D") {
		confirmarDeleteBasico();
	} else {
		if (tipoModificacion == "E") {
			params += 'opcion=2';
		} else {
			params += 'opcion=1';
		}
		if (sePuedeInhabilitar == "N" && $('#habilitadoProductoList').val() == "N") {
			alert("No se puede INHABILITAR este producto ya que tiene otros productos agrupados, corrijalo");
			return;
		} else {
			params += '&cdProveedor=' + $('#proveedorProductoList').val();
			params += '&cdProducto=' + $('#cdProducto').val();
			params += '&nbProducto=' + $('#nbProducto').val();
			params += '&nbProductoCorto=' + $('#nbProductoCorto').val();
			params += '&cdGrupoProducto=' + $('#grupoProductoList').val();
			params += '&cdUniVal=' + $('#uniValList').val();
			// params += '&cdGrupoProducto='+$('#cdGrupoProducto').val();
			// params += '&cdUniVal='+$('#cdUniVal').val();
			params += '&cdSecSolServ=' + $('#cdSecSolServList').val();
			params += '&cdSecConServ=' + $('#cdSecConServList').val();
			params += '&cdSecConFact=' + $('#cdSecConFactList').val();
			params += '&stProdImportPrest=' + $('#stProdImportPrestList').val();
			params += '&stProdImportFact=' + $('#stProdImportFactList').val();
			params += '&stRemServOblig=' + $('#stRemServObligList').val();
			params += '&stRemFactOblig=' + $('#stRemFactObligList').val();
			params += '&stAdmiteRemServ=' + $('#stAdmiteRemServList').val();
			params += '&stAdmiteRemFact=' + $('#stAdmiteRemFactList').val();
			params += '&nbAtributoRef1=' + $('#nbAtributoRef1').val();
			params += '&nbAtributoRef2=' + $('#nbAtributoRef2').val();
			params += '&stConcilSinVal=' + $('#stConcilSinValList').val();
			params += '&stServSinConcil=' + $('#stServSinConcilList').val();
			params += '&stFactSinConcil=' + $('#stFactSinConcilList').val();
			params += '&nuDiaEmiFDesde=' + $('#nuDiaEmiFDesde').val();
			params += '&nuDiaEmiFHasta=' + $('#nuDiaEmiFHasta').val();
			params += '&nuDiaCierreFDesde=' + $('#nuDiaCierreFDesde').val();
			params += '&nuDiaCierreFHasta=' + $('#nuDiaCierreFHasta').val();
			params += '&nbAtributoAdic1=' + $('#nbAtributoAdic1List').val();
			params += '&nbAtributoAdic2=' + $('#nbAtributoAdic2').val();
			params += '&nbAtributoAdic3=' + $('#nbAtributoAdic3').val();
			params += '&nbAtributoAdic4=' + $('#nbAtributoAdic4').val();
			params += '&nbAtributoAdic5=' + $('#nbAtributoAdic5').val();
			params += '&stHabilitado=' + $("#habilitadoProductoList").val();
			params += '&cdTipVal=' + $("#tipValList").val();
			params += '&cdMoneda=' + $("#monedaList").val();
			params += '&nuPorcVarMax=' + $("#nuPorcVarMax").val();		
	
			if ($.trim($('#proveedorProductoList').val()) == ""
					|| $.trim($('#proveedorProductoList').val()) == "0") {
				err += "El Proveedor es obligatorio\n";
			}
			if ($.trim($('#cdProducto').val()) == "") {
				err += "El Producto es obligatorio\n";
			}
			if ($.trim($('#nbProducto').val()) == "") {
				err += "El Nombre de Producto es obligatorio\n";
			}
			if ($.trim($('#nbProductoCorto').val()) == "") {
				err += "La Descripci\u00f3n corta es obligatoria\n";
			}
			if ($.trim($('#grupoProductoList').val()) == ""
					|| $.trim($('#grupoProductoList').val()) == "0") {
				err += "El Grupo es obligatorio\n";
			}
			if ($.trim($('#uniValList').val()) == ""
					|| $.trim($('#uniValList').val()) == "0") {
				err += "La Unidad de Valoraci\u00f3n es obligatoria\n";
			}
			if ($.trim($('#stProdImportPrestList').val()) == "") {
				err += "El indicador de Importacion en Interfaz Servicios Prestados es obligatorio\n";
			}
			if ($.trim($('#stProdImportFactList').val()) == "") {
				err += "El indicador de Importacion en Interfaz Servicios Facturados es obligatorio\n";
			}
			if ($.trim($('#stRemServObligList').val()) == "") {
				err += "El indicador de Remito Obligatorio en Servicios Prestados es obligatorio\n";
			}
			if ($.trim($('#stRemFactObligList').val()) == "") {
				err += "El indicador de Remito Obligatorio en Servicios Facturados es obligatorio\n";
			}
			if ($.trim($('#stAdmiteRemServList').val()) == "") {
				err += "El indicador de si Admite Remito ya registrados en Serv Prestados es obligatorio\n";
			}
			if ($.trim($('#stAdmiteRemFactList').val()) == "") {
				err += "El indicador de si Admite Remito ya registrados en Serv Facturados es obligatorio\n";
			}
			if ($.trim($('#stConcilSinValList').val()) == "") {
				err += "El indicador de si Admite Conciliar ignorando Valores es obligatorio\n";
			}
			if ($.trim($('#stServSinConcilList').val()) == "") {
				err += "El indicador de si Permite dejar sin Conciliar Registros Serv Prestados es obligatorio\n";
			}
			if ($.trim($('#stFactSinConcilList').val()) == "") {
				err += "El indicador de si Permite dejar sin Conciliar Registros Serv Facturados es obligatorio\n";
			}
			if ($.trim($("#habilitadoProductoList").val()) == "") {
				err += "El Estado es obligatorio\n";
			}
			if ($.trim($("#tipValList").val()) == "0") {
				err += "El Tipo Valorizacion es obligatorio\n";
			}		
			if ($.trim($("#monedaList").val()) == "0") {
				err += "La Moneda es obligatoria\n";
			}		
			if ($.trim($("#nuDiaEmiFDesde").val()) == "") {
				err += "D\u00edas Fecha Rem. vs Inicio Per. Fact. no puede ser vac\u00edo \n";
			} else {
				if ($("#nuDiaEmiFDesde").val() > 999) {
					err += "D\u00edas Fecha Rem. vs Inicio Per. Fact. no puede ser mayor a 999 \n";
				}
				if ($("#nuDiaEmiFDesde").val() < -999) {
					err += "D\u00edas Fecha Rem. vs Inicio Per. Fact. no puede ser menor a -999 \n";
				}
			}
			if ($.trim($("#nuDiaEmiFHasta").val()) == "") {
				err += "D\u00edas Fecha Rem. vs Fin Per. Fact. no puede ser vac\u00edo \n";
			} else {
				if ($("#nuDiaEmiFHasta").val() > 999) {
					err += "D\u00edas Fecha Rem. vs Fin Per. Fact. no puede ser mayor a 999 \n";
				}
				if ($("#nuDiaEmiFHasta").val() < -999) {
					err += "D\u00edas Fecha Rem. vs Fin Per. Fact. no puede ser menor a -999 \n";
				}
			}
			if ($.trim($("#nuDiaCierreFDesde").val()) == "") {
				err += "D\u00edas Fecha Fin Serv. vs Inicio Per. Fact. no puede ser vac\u00edo \n";
			} else {
				if ($("#nuDiaCierreFDesde").val() > 999) {
					err += "D\u00edas Fecha Fin Serv. vs Inicio Per. Fact. no puede ser mayor a 999 \n";
				}
				if ($("#nuDiaCierreFDesde").val() < -999) {
					err += "D\u00edas Fecha Fin Serv. vs Inicio Per. Fact. no puede ser menor a -999 \n";
				}
			}
			if ($.trim($("#nuDiaCierreFHasta").val()) == "") {
				err += "D\u00edas Fecha Fin Serv. vs Fin Per. Fact. no puede ser vac\u00edo \n";
			} else {
				if ($("#nuDiaCierreFHasta").val() > 999) {
					err += "D\u00edas Fecha Fin Serv. vs Fin Per. Fact. no puede ser mayor a 999 \n";
				}
				if ($("#nuDiaCierreFHasta").val() < -999) {
					err += "D\u00edas Fecha Fin Serv. vs Fin Per. Fact. no puede ser menor a -999 \n";
				}
			}
			if ($.trim($("#nuPorcVarMax").val()) < 0) {
				err += "El Porcentaje de Variación Máxima no puede ser menor a cero\n";
			}		
			
			if (err != "") {
				alert("Verifique los datos ingresados\n\n" + err);
			} else {
				callJsonActionPost('saveProducto.action', params, 'successSaveProductoBasico', 'errorSaveProductoBasico');
			}
		}
	}
}

function successSaveProductoBasico(json) {
	if (isSuccessResult(json.result.errorCode)) {
		setSuccessMsg($('#producto_responseMsgs'), "Editado Exitosamente");
		$('#producto_responseMsgs').show();
		$('#dialogEditProducto').dialog('close');
		resetProductoForm();
		cleanDataConfirmation();
		reloadProductosBasicosGrid();
		search();
	} else {
		setErrorMsg($('#prod_basic_responseMsgs'), json.result.errorDesc);
		$('#prod_basic_responseMsgs').show();
	}
}

function recargarProductoAdmin(cdProveedor) {
	callJsonActionPost("comboProductosPrecios.action", "cdProveedor=" + cdProveedor, "successProductos", "");
}

function errorProductos(cod, des) {
	jsError('errProducto', des);
}

function successProductos(jsonData) {
	try {
		document.getElementById('filtroProductoList').options.length = 0;
		$('#filtroProductoList').selectmenu('destroy');
		if (jsonData.ProductoPrecioList != undefined) {
			resetProductoOption(' Todos los Productos ', '0', true);

			if (jsonData.ProductoPrecioList.length > 0) {
				for ( var i = 0; i < jsonData.ProductoPrecioList.length; i++)
					document.getElementById('filtroProductoList')[document
							.getElementById('filtroProductoList').options.length] = new Option(
							(jsonData.ProductoPrecioList[i]).desc,
							jsonData.ProductoPrecioList[i].cod);
			}
		}
		$('#filtroProductoList').selectmenu('destroy');
		$('#filtroProductoList').selectmenu({
			style : 'dropdown',
			width : $('#filtroProductoList').attr("width"),
			maxHeight : '200'
		});
	} catch (e) {
		jsError('successProductos', e);
	}
}

function errorSaveProductoBasico(errorCode, errorDesc) {
	setErrorMsg($('#producto_responseMsgs'), errorDesc);
	$('#producto_responseMsgs').show();
	$('#dialogEditProducto').dialog('close');
	// resetProductoForm();
	cleanDataConfirmation();
}

// DELETE PRODUCTO
function confirmarDeleteBasico() {
	var params = '';
	params += 'cdProveedor=' + $('#proveedorProductoList').val();
	params += '&cdProducto=' + $('#cdProducto').val();
	if (confirm('Confirma la baja del Producto?')) {
		callJsonActionPost('deleteProducto.action', params,	'successDeleteProductoBasico', 'errorDeleteProductoBasico');
	}
}

function successDeleteProductoBasico(json) {
	if (isSuccessResult(json.result.errorCode)) {
		setSuccessMsg($('#producto_responseMsgs'), "Eliminado Exitosamente");
		$('#producto_responseMsgs').show();
		$('#dialogEditProducto').dialog('close');
		search();
	} else {
		setErrorMsg($('#producto_responseMsgs'), json.result.errorDesc);
		$('#producto_responseMsgs').show();
	}
	cleanDataConfirmation();
}

function errorDeleteProductoBasico(errorCode, errorDesc) {
	setErrorMsg($('#producto_responseMsgs'), errorDesc);
	$('#producto_responseMsgs').show();
	$('#dialogEditProducto').dialog('close');
	cleanDataConfirmation();
}

function cleanDataConfirmation() {
	$("#cdProveedor").val('0');
	$("#cdProducto").val('');
	$("#nbProducto").val('');
	$("#nbProductoCorto").val('');
	$("#cdGrupoProducto").val('0');
	$("#cdUniVal").val('0');
	$("#cdSecSolServ").val('0');
	$("#cdSecConServ").val('0');
	$("#cdSecConFact").val('0');
	$("#stProdImportPrest").val('');
	$("#stProdImportFact").val('');
	$("#stRemServOblig").val('');
	$("#stRemFactOblig").val('');
	$("#stAdmiteRemServ").val('');
	$("#stAdmiteRemFact").val('');
	$("#nbAtributoRef1").val('');
	$("#nbAtributoRef2").val('');
	$("#stConcilSinVal").val('');
	$("#stServSinConcil").val('');
	$("#stFactSinConcil").val('');
	$("#nuDiaEmiFDesde").val('');
	$("#nuDiaEmiFHasta").val('');
	$("#nuDiaCierreFDesde").val('');
	$("#nuDiaCierreFHasta").val('');
	$("#nbAtributoAdic1").val('');
	$("#nbAtributoAdic2").val('');
	$("#nbAtributoAdic3").val('');
	$("#nbAtributoAdic4").val('');
	$("#nbAtributoAdic5").val('');
	$('#cdTipVal').val('0');
	$('#cdMoneda').val('0');
	$('#nuPorcVarMax').val('0');
	$('#stHabilitado').val('');
}

function cleanMsgConfirmation() {
	$('#producto_responseMsgs').hide();
	$('#producto_responseMsgs').val('');
	$('#msgEspera').hide();
	$('#msgEspera').val('');
	$('#msgConfirmacion').hide();
	$('#msgConfirmacion').val('');
	$('#prod_basic_responseMsgs').hide();
	$('#prod_basic_responseMsgs').val('');
}

function dateFormat_serviceToJs(fecha) {
	var value = "";
	if (fecha != null && fecha != "") {
		value = fecha.substr(8, 2) + '/' + fecha.substr(5, 2) + '/' + fecha.substr(0, 4);
	}
	return value;
}

/*--------------------------------------------------------------------------------------------------------*/
/* CARGA PROVEEDORES */
/*--------------------------------------------------------------------------------------------------------*/
function recargarProveedores() {
	callJsonActionPost("comboProductoProveedores.action", "", "successProveedores", "errorProveedores");
}

function successProveedores(jsonData) {
	try {		
		$('#proveedorProductoList').empty();
		$('#proveedorProductoList').get(0)[0] = new Option(' &nbsp; Sin Proveedores &nbsp; ', '0');
		
//		document.getElementById('proveedorProductoList').options.length = 0;		

		if (jsonData.ProveedorList != undefined) {
			if (jsonData.ProveedorList.length > 0) {
				
//				document.getElementById('proveedorProductoList')[document
//						.getElementById('proveedorProductoList').options.length] = new Option(
//						"Sin Proveedores", "0");				
				for ( var i = 0; i < jsonData.ProveedorList.length; i++) {
					document.getElementById('proveedorProductoList')[document
							.getElementById('proveedorProductoList').options.length] = new Option(
							(jsonData.ProveedorList[i]).desc,
							jsonData.ProveedorList[i].cod);
				}							
			}
		}
		
//		destroyCombosGeneralOne('proveedorProductoList');
		$('#proveedorProductoList').selectmenu('destroy');
		$('#proveedorProductoList').selectmenu({style:'dropdown', width : $('#proveedorProductoList').attr("width"), maxHeight:'200'});
	
		$('#uniValList').selectmenu('destroy');
		$('#uniValList').selectmenu({style:'dropdown', width : $('#uniValList').attr("width"), maxHeight:'200'});

		$('#tipValList').selectmenu('destroy');
		$('#tipValList').selectmenu({style:'dropdown', width : $('#tipValList').attr("width"), maxHeight:'200'});

		$('#grupoProductoList').selectmenu('destroy');
		$('#grupoProductoList').selectmenu({style:'dropdown', width : $('#grupoProductoList').attr("width"), maxHeight:'200'});
		
		$('#monedaList').selectmenu('destroy');
		$('#monedaList').selectmenu({style:'dropdown', width : $('#monedaList').attr("width"), maxHeight:'200'});

		$('#stProdImportPrestList').selectmenu('destroy');
		$('#stProdImportPrestList').selectmenu({style:'dropdown', width : $('#stProdImportPrestList').attr("width"), maxHeight:'200'});
		
		$('#stProdImportFactList').selectmenu('destroy');
		$('#stProdImportFactList').selectmenu({style:'dropdown', width : $('#stProdImportFactList').attr("width"), maxHeight:'200'});
		
		$('#stRemServObligList').selectmenu('destroy');
		$('#stRemServObligList').selectmenu({style:'dropdown', width : $('#stRemServObligList').attr("width"), maxHeight:'200'});

		$('#stRemFactObligList').selectmenu('destroy');
		$('#stRemFactObligList').selectmenu({style:'dropdown', width : $('#stRemFactObligList').attr("width"), maxHeight:'200'});

		$('#stAdmiteRemServList').selectmenu('destroy');
		$('#stAdmiteRemServList').selectmenu({style:'dropdown', width : $('#stAdmiteRemServList').attr("width"), maxHeight:'200'});

		$('#stAdmiteRemFactList').selectmenu('destroy');
		$('#stAdmiteRemFactList').selectmenu({style:'dropdown', width : $('#stAdmiteRemFactList').attr("width"), maxHeight:'200'});

		$('#stConcilSinValList').selectmenu('destroy');
		$('#stConcilSinValList').selectmenu({style:'dropdown', width : $('#stConcilSinValList').attr("width"), maxHeight:'200'});

		$('#stServSinConcilList').selectmenu('destroy');
		$('#stServSinConcilList').selectmenu({style:'dropdown', width : $('#stServSinConcilList').attr("width"), maxHeight:'200'});

		$('#stFactSinConcilList').selectmenu('destroy');
		$('#stFactSinConcilList').selectmenu({style:'dropdown', width : $('#stFactSinConcilList').attr("width"), maxHeight:'200'});

		$('#nbAtributoAdic1List').selectmenu('destroy');
		$('#nbAtributoAdic1List').selectmenu({style:'dropdown', width : $('#nbAtributoAdic1List').attr("width"), maxHeight:'200'});

		$('#habilitadoProductoList').selectmenu('destroy');
		$('#habilitadoProductoList').selectmenu({style:'dropdown', width : $('#habilitadoProductoList').attr("width"), maxHeight:'200'});
		
	} catch (e) {
	}
}

function errorProveedores(cod, des) {
	// jsError(cod, des);
}

/*--------------------------------------------------------------------------------------------------------*/
/* CARGA SECTORES */
/*--------------------------------------------------------------------------------------------------------*/
function recargarSectores() {
	callJsonActionPost("comboProductoSectores.action", "", "successSectores", "errorSectores");
}

function successSectores(jsonData) {
	try {
		// document.getElementById('cdSecSolServList').options.length = 0;
		// $('#cdSecSolServList').selectmenu('destroy');
		$('#cdSecSolServList').empty();
		$('#cdSecSolServList').get(0)[0] = new Option(' &nbsp; Sin Sector &nbsp; ', '0');

		$('#cdSecConServList').empty();
		$('#cdSecConServList').get(0)[0] = new Option(' &nbsp; Sin Sector &nbsp; ', '0');
		// document.getElementById('cdSecConServList').options.length = 0;
		// $('#cdSecConServList').selectmenu('destroy');

		$('#cdSecConFactList').empty();
		$('#cdSecConFactList').get(0)[0] = new Option(' &nbsp; Sin Sector &nbsp; ', '0');
		// document.getElementById('cdSecConFactList').options.length = 0;
		// $('#cdSecConFactList').selectmenu('destroy');

		if (jsonData.SectorList != undefined) {
			if (jsonData.SectorList.length > 0) {
				for ( var i = 0; i < jsonData.SectorList.length; i++) {
					document.getElementById('cdSecSolServList')[document
							.getElementById('cdSecSolServList').options.length] = new Option(
							(jsonData.SectorList[i]).desc,
							jsonData.SectorList[i].cod);
					document.getElementById('cdSecConServList')[document
							.getElementById('cdSecConServList').options.length] = new Option(
							(jsonData.SectorList[i]).desc,
							jsonData.SectorList[i].cod);
					document.getElementById('cdSecConFactList')[document
							.getElementById('cdSecConFactList').options.length] = new Option(
							(jsonData.SectorList[i]).desc,
							jsonData.SectorList[i].cod);
				}
				 destroyCombosGeneralOne('cdSecConFactList');
				 destroyCombosGeneralOne('cdSecConServList');
				 destroyCombosGeneralOne('cdSecSolServList');
			}
		}
	} catch (e) {
		
	}
}

function errorSectores(cod, des) {
	// jsError(cod, des);
}

var habilitado = $('#habilitadoProductoList');
habilitado.change(function(){
//alert($('#habilitadoProductoList').val());
	if ($('#habilitadoProductoList').val() == "N" ) {
		verificaExistenciaAgrupados();		
	} 
});

function verificaExistenciaAgrupados() {
	var params = '';
	params += 'cdProveedor=' + $('#proveedorProductoList').val();
	params += '&cdProducto=' + $('#cdProducto').val();
	params += '&nbAtributoAdic1=' + $('#nbAtributoAdic1List').val();
	callJsonActionPost("verificaExistenciaAgrupados.action", params, "successExistenciaProducto", 'errorsExistenciaProducto');
}

function successExistenciaProducto(json) {
//	alert(json.result.errorDesc);
	if (isSuccessResult(json.result.errorCode)) {
		sePuedeInhabilitar = "S";
	} else {
//		alert(json.result.errorDesc);
		sePuedeInhabilitar = "N";
		$('#habilitadoProductoList').selectmenu('setValue', 'S');
		alert("No se puede INHABILITAR este producto ya que tiene otros productos agrupados");		
//		setErrorMsg($('#producto_responseMsgs'), json.result.errorDesc);
//		$('#producto_responseMsgs').show();
	}
//	alert($('#habilitadoProductoList').val());	
}

function errorsExistenciaProducto(errorCode, errorDesc) {
//	alert(errorDesc);	
	$('#habilitadoProductoList').selectmenu('setValue', 'S');
//	setErrorMsg($('#producto_responseMsgs'), errorDesc);
//	$('#producto_responseMsgs').show();
	sePuedeInhabilitar = "N";	
//	alert(sePuedeInhabilitar);	
}

function limpiaCombosPantalla(){
/*
	destroyCombosGeneralOne('proveedorProductoList');
	destroyCombosGeneralOne('grupoProductoList');
	destroyCombosGeneralOne('uniValList');
	destroyCombosGeneralOne('tipValList');
	destroyCombosGeneralOne('monedaList');	
	destroyCombosGeneralOne('nbAtributoAdic1List');
	destroyCombosGeneralOne('stProdImportPrestList');
	destroyCombosGeneralOne('stProdImportFactList');
	destroyCombosGeneralOne('stRemServObligList');
	destroyCombosGeneralOne('stRemFactObligList');
	destroyCombosGeneralOne('stAdmiteRemServList');
	destroyCombosGeneralOne('stAdmiteRemFactList');
	destroyCombosGeneralOne('stConcilSinValList');
	destroyCombosGeneralOne('stServSinConcilList');
	destroyCombosGeneralOne('stFactSinConcilList');
	destroyCombosGeneralOne('habilitadoProductoList');		
*/	
}