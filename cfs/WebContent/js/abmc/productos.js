$(document).ready(function(){
	destroyCombosGeneralOne('filtroProveedorList');
	destroyCombosGeneralOne('filtroProductoList');
	destroyCombosGeneralOne('filtroGrupoList');
	destroyCombosGeneralOne('filtroHabilitadoList');

	$.ajaxSetup({
        cache : false
    });	
	
	initialiseFiltroProducto();
    
	$("#tabsApplication").tabs('select',0);
	$("#tab1").hide();
	$("#tab2").hide();
	$("#tab3").hide();
	$("#tab4").hide();
	
	$("#tabsApplication").disableTab(1);
    $("#tabsApplication").disableTab(2);
	$("#tabsApplication").disableTab(3);
    $("#tabsApplication").disableTab(4);
});

function search() {
	limpiarGrilla('gridProductosBasicosId' , 'gridProductosBasicosPager' , 'gridProductosBasicos');
	limpiarGrilla('gridProductosPreciosId' , 'gridProductosPreciosPager' , 'gridProductosPrecios');
	limpiarGrilla('gridProductosSectoresId', 'gridProductosSectoresPager', 'gridProductosSectores');

	$('#productosBasicosGrid').show();
	$('#productosPreciosGrid').show();
	$('#productosSectoresGrid').show();
	$('#botonera').show();
	$('#buttonCancelar').button({
		icons : {
			secondary : ""
		}
	}).click(function() {

	});
	loadProductosBasicosGrid();
	loadProductosPreciosGrid();
	loadProductosSectoresGrid();
}

function limpiar(opcion) {
	$('#producto_responseMsgs').hide();	
//	$("#cdProveedorSeleccionado").val("");
//	$("#cdProductoSeleccionado").val("");
//	$("#nbProductoSeleccionado").val("");
/*	
    if (opcion == 1) {
    	$('#filtroGrupoList').selectmenu('setValue', '0');	
    	$('#filtroHabilitadoList').selectmenu('setValue', '0');
    }
*/    
	$("#tabsApplication").tabs('select',0);
	$("#tab1").hide();
	$("#tab2").hide();
	$("#tab3").hide();
	$("#tab4").hide();
	
	$("#tabsApplication").disableTab(1);
    $("#tabsApplication").disableTab(2);
	$("#tabsApplication").disableTab(3);
    $("#tabsApplication").disableTab(4);
}

function initialiseFiltroProducto(){

	var $proveedor = $('#filtroProveedorList');
	$proveedor.change(function(){
	   hideCommonDataElements();
	   recargarProducto($proveedor.val());
	});
	onchangeOptions('filtroProveedorList');
}

function onchangeOptions(selectorId){
   $("#"+selectorId).change(function(){
      hideCommonDataElements();
   });
}

function hideCommonDataElements(){
   if ($('#filtroProveedorList').val()=="0"){
      resetProductoPrecioOption(' Todos los Productos ', '0', false);	
   }
}

function recargarProducto(cdProveedor) {
   callJsonAction("comboProductosPrecios.action","cdProveedor="+cdProveedor,"successProductos","errorProductos");
}

function successProductos(jsonData){
   try {
      document.getElementById('filtroProductoList').options.length=0;
      $('#filtroProductoList').selectmenu('destroy');
      if (jsonData.ProductoPrecioList!=undefined){
 	      resetProductoOption(' Todos los Productos ', '0', true);	
              	  
          if (jsonData.ProductoPrecioList.length>0){
             for (var i=0;i<jsonData.ProductoPrecioList.length;i++)
	            document.getElementById('filtroProductoList')[document.getElementById('filtroProductoList').options.length] = new Option((jsonData.ProductoPrecioList[i]).desc, jsonData.ProductoPrecioList[i].cod);
	      }       
	  }
    $('#filtroProductoList').selectmenu('destroy');	
  	$('#filtroProductoList').selectmenu({style:'dropdown', width : $('#filtroProductoList').attr("width"), maxHeight:'200'});	
   } catch(e) {
      jsError('successProductos', e);
   }
}

function resetProductoOption(label, value, disabled){
   document.getElementById('filtroProductoList').options.length=0;
   document.getElementById('filtroProductoList')[0]= new Option(label,value);
   $('#filtroProductoList').selectmenu('destroy');
   $('#filtroProductoList').selectmenu({style:'dropdown', width:'200px', maxHeight:'200'});
}

function errorProductos(cod, des){
   jsError('errProducto', des);
}
