$(document).ready(function(){
	destroyCombosGeneralOne('filtroProveedorList');

	$.ajaxSetup({
        cache : false
    });	

	$("#tabsApplication").tabs('select',0);
	$("#tabsApplication").disableTab(1, false);
    $("#tabsApplication").disableTab(2, false);
    $("#tabsApplication").disableTab(3, false);
	addCheckSessionAll();

});

function search() {
	limpiarGrilla('gridProveedoresBasicosId'    , 'gridProveedoresBasicosPager'    , 'gridProveedoresBasicos');
	limpiarGrilla('gridProveedoresPeriodosId'   , 'gridProveedoresPeriodosPager'   , 'gridProveedoresPeriodos');
	limpiarGrilla('gridProveedoresValoresId'    , 'gridProveedoresValoresPager'    , 'gridProveedoresValores');
	limpiarGrilla('gridProveedoresTipoCambioId' , 'gridProveedoresTipoCambioPager' , 'gridProveedoresTipoCambio');

	$('#productosBasicosGrid').show();
	$('#productosPeriodosGrid').show();
	$('#productosValoresGrid').show();
	$('#productosTipoCambioGrid').show();
	$('#botonera').show();
	$('#buttonCancelar').button({
		icons : {
			secondary : ""
		}
	}).click(function() {

	});
	loadProveedoresBasicosGrid();
	loadProveedoresPeriodosGrid();
	loadProveedoresValoresGrid();
	loadProveedoresTipoCambioGrid();
}

function limpiar() {
	$('#proveedor_responseMsgs').hide();
	$('#proveedor_responseMsgs').val('');
	$("#cdProveedorSeleccionado").val("");
    $("#nbProveedorSeleccionado").val("");
	$("#cdPeriodoFactSeleccionado").val("");
	$("#nbPeriodoFactSeleccionado").val("");
	$("#tabsApplication").tabs('select',0);
	$("#tabsApplication").disableTab(1, false);
    $("#tabsApplication").disableTab(2, false);
    $("#tabsApplication").disableTab(3, false);
    search();
}
