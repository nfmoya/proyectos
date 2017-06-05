$(document).ready(function() {
	
	$.ajaxSetup({
		cache : false
	});
	$(':button').button();
	$('#btnBuscar').button({
		icons : {
			secondary : "ui-icon-search"
		}
	}).click(function() {
		$('#busqConciliacion_responseMsgs').hide();
		search();
	});
	initialiseFiltroProducto();
	$('#btnExportar').button({
		icons : {
			secondary : "ui-icon-bookmark"
		}
	}).click(function() {
		exportExcel();
	});
	$('#btnImprimir').button({
		icons : {
			secondary : "ui-icon-print"
		}
	}).click(function() {
		exportDataGridPDF();
	});
	destroyCombos();
	// Agregado check box para productos no medibles.
	$('#checkNoMedible').click(function() {
		if($('#checkNoMedible').is(':checked')){
			$('#checkNoMedible').val('true');
			$('#stDiferencia').val('0');
			$('#productosList').val("0");
			$('#productosList').selectmenu('value','0');
			$('#productosList').selectmenu('disabled',true);
			$('#comboDiferencias').hide();
			
		}else{
			$('#checkNoMedible').val('false');
			$('#comboDiferencias').show();
			$('#productosList').selectmenu('disabled',false);
		}
	});
	$('#checkNoMedible').val('false');
	
	validateSectorUsuario();
	var cantProv = $('#proveedorList').find('option').length;

	if(cantProv > 1){
		 $('#proveedorList').append("<option value='0'> Todos los Proveedores &nbsp;</option>"); 
		 $('#proveedorList').selectmenu('setValue','0');	
		 
		$('#proveedorList').selectmenu('destroy');
		$('#proveedorList').selectmenu({
			style : 'dropdown',
			maxHeight:'200', width: $(this).attr('width')
		}).selectmenu('disabled', false);

	}

});

function limpiar(opcion) {
	$("#cdProveedorSeleccionado").val("");
	$("#cdProductoSeleccionado").val("");
	$("#cdProductoSeleccionado").val("");
	/*
	 * if (opcion == 1) { $('#filtroGrupoList').selectmenu('setValue', '0');
	 * $('#filtroHabilitadoList').selectmenu('setValue', '0'); }
	 */
	$("#tabsApplication").tabs('select', 0);
	$("#tabsApplication").disableTab(1);
	$("#tabsApplication").disableTab(2);

}

function searchValidate() {
	
	var message = '';
	if ($('#cdConciliacion').val() == '') {
		if ($.trim($('#proveedorList').val()) == '0') {
			message += '<center> * El Proveedor debe ser informado <br/></center>';

		}  

		if ($.trim($('#sectorList').val()) == '0') {
			if ($("#userSector").val() != "0") {
				message += '&nbsp;&nbsp;* El Sector debe ser informado  <br/>';
			}
			
// } if ($.trim($('#selectFDesdeList').val()) == '0') {
// message += '&nbsp;&nbsp;* El per&iacute;odo Desde debe ser informado <br/>';
//
// } if ($.trim($('#selectFHastaList').val()) == '0') {
// message += '&nbsp;&nbsp;* El per&iacute;odo Hasta debe ser informado <br>';

// } if ($.trim($('#productosList').val()) == '0') {
// message += '&nbsp;&nbsp;* Debe informar el Producto <br>';


		} if ($('#selectFDesdeList').val() == '0' ||
				$('#selectFHastaList').val() == '0') {
				message += '&nbsp;&nbsp;* Debe informar correctamente el Per&iacute;do Facturaci&oacute;n <br>';
//
		}
	} else {
		if (isNaN($('#cdConciliacion').val())
				&& $('#cdConciliacion').length > 10) {
			message += '&nbsp;&nbsp;* Nro. Conciliacion debe ser un Numero de 10 Digitos <br>';
			return false;
		} 
		
	}
	return message;
}
function destroyCombos() {

	$('#proveedorList').selectmenu('destroy');
	$('#proveedorList').selectmenu({
		style : 'dropdown',
		maxHeight:'200', width: $(this).attr('width')
	});
	
	$('#sectorList').selectmenu('destroy');
	$('#sectorList').selectmenu({
		style : 'dropdown',
		width : '200px',
		maxHeight:'200', width: $(this).attr('width')
	});

	$('#productosList').selectmenu('destroy');
	$('#productosList').selectmenu({
		style : 'dropdown',
		maxHeight:'200', width: $(this).attr('width')
	});

	$('#estadoDifList').selectmenu('destroy');
	$('#estadoDifList').selectmenu({
		style : 'dropdown',
		width : '200px'
	});

	$('#stDiferencia').selectmenu('destroy');
	$('#stDiferencia').selectmenu({
		style : 'dropdown',
		width : '200px'
	});

	$('#selectFDesdeList').selectmenu('destroy');
	$('#selectFDesdeList').selectmenu({
		style : 'dropdown',
		maxHeight:'200',
		width : '100px'
	});

	$('#selectFHastaList').selectmenu('destroy');
	$('#selectFHastaList').selectmenu({
		style : 'dropdown',
		maxHeight:'200',
		width : '100px'
	});

	// $('#solucionDifListEdit').selectmenu({style:'dropdown'});
}

function initialiseFiltroProducto() {

	var $proveedor = $('#proveedorList');

	$proveedor.change(function() {
		hideCommonDataElements();
		recargarProducto($proveedor.val());
	});
	onchangeOptions('proveedorList');
}

function onchangeOptions(selectorId) {
	$("#" + selectorId).change(function() {
		hideCommonDataElements();
	});
}

function hideCommonDataElements() {
	if ($('#proveedorList').val() == "0") {
		resetProductoPrecioOption(' Todos los Productos ', '0', false);
	}
}

function resetProductoPrecioOption(label, value, disabled) {
	document.getElementById('productosList').options.length = 0;
	document.getElementById('productosList')[0] = new Option(label, value);
	$('#productosList').selectmenu('destroy');
	$('#productosList').selectmenu({
		style : 'dropdown',
		maxHeight:'200', width: $(this).attr('width')
	}).selectmenu('disabled', true);
}

function resetProveedor(label, value, disabled) {
	document.getElementById('proveedorList').options.length = 0;
	document.getElementById('proveedorList')[0] = new Option(label, value);
// $('#proveedorList').selectmenu('destroy');
// $('#proveedorList').selectmenu({
// style : 'dropdown',
// maxHeight:'200', width: $(this).attr('width')
// }).selectmenu('disabled', disabled);
}

function recargarProducto(cdProveedor) {
	callJsonAction("comboProductosPrecios.action",
			"cdProveedor=" + cdProveedor +"&isConsConcil=true" , "successProductos", "errorProductos");
}

/**
 * Funcion que bloquea el sector si el usuarios no es Administrador
 * 
 */
function validateSectorUsuario(){
	
	$('#sectorList').selectmenu('setValue', $("#userSector").val());	
   	if ($("#userSector").val() != '0') {
       $('#sectorList').selectmenu('disabled', true);
    }	
}


function successProductos(jsonData) {
	try {
		document.getElementById('productosList').options.length = 0;
		$('#productosList').selectmenu('destroy');
		if (jsonData.ProductoPrecioList != undefined) {
			resetProductoOption(' Todos los Productos ', '0', true);

			if (jsonData.ProductoPrecioList.length > 0) {
				for ( var i = 0; i < jsonData.ProductoPrecioList.length; i++)
					document.getElementById('productosList')[document
							.getElementById('productosList').options.length] = new Option(
							(jsonData.ProductoPrecioList[i]).desc,
							jsonData.ProductoPrecioList[i].cod);

				$('#productosList').selectmenu('destroy');
				$('#productosList').selectmenu({
					style : 'dropdown',
					maxHeight:'200', width: $(this).attr('width')
				});
			}
			searchPeriodByFilter();
		}
	} catch (e) {
// jsError('successProductos', e);
	}
}

function searchPeriodByFilter(){
	
	var params = '';
	var prov = $('#proveedorList').val();
	
	if(prov != 0){			
		params += 'cdProveedor='+prov;		
		callJsonAction('searchPeriodByFilter.action',params,'successPeriod','errorPeriod');
	}else {
		
		limpiarCombo(' Seleccione ','0','selectFDesdeList','100px');
		limpiarCombo(' Seleccione ','0','selectFHastaList','100px');
	}	
}

function successPeriod(json){
	cargarCombo(json,'selectFDesdeList','successPeriodDesde', false,'100px', ' Seleccione ');
	cargarCombo(json,'selectFHastaList','successPeriodHasta', false,'100px', ' Seleccione ');
}

function resetProductoOption(label, value, disabled) {
	document.getElementById('productosList').options.length = 0;
	document.getElementById('productosList')[0] = new Option(label, value);
	$('#productosList').selectmenu('destroy');
	$('#productosList').selectmenu({
		style : 'dropdown',
		maxHeight:'200',
		width : '200px'
	});
}

function search() {
// $('#busqConciliacion_responseMsgs').hide();
	var msg = searchValidate();
	if (msg == '') {
		if(validaPeridoConc($('#selectFDesdeList').val(), $('#selectFHastaList').val())){
			limpiarGrilla('gridBusqConciliacionesId', 'gridBusqConciliacionesPager',
					'gridBusqConciliaciones');
			var params = '';
			params += 'cdConciliacion=' + $('#cdConciliacion').val();
			params += '&cdProveedor=' + $('#proveedorList').val();
			params += '&cdSector=' + $('#sectorList').val();
			params += '&cdProducto=' + $('#productosList').val();
			params += '&fhDesde=' + $('#selectFDesdeList').val();
			params += '&fhHasta=' + $('#selectFHastaList').val();
			params += '&estadoDif=' + $('#estadoDifList').val();
			params += '&stDiferencia=' + $('#stDiferencia').val();
			params += '&checkNoMedible=' + $('#checkNoMedible').val();
			
			loadConciliacionesGrid(params);
		}
	}else{
		fAlertCB(msg);
	}
}

// GRILLA
function loadConciliacionesGrid(params) {
	$('#globalParams').val(params);
	
	try {
		showGrid({
			id : 'gridBusqConciliacionesId',
			idPager : 'gridBusqConciliacionesPager',
			url : 'jsonBusqConcList.action?' + params,
			colNames : [ 'Nro Conc'
			             , 'Sector Control'
			             , 'Per Fact.'
			             , 'ST Per Fact.'
			             , 'Producto'
			             , 'Descripci&oacute;n'
			             , 'Estado'
			             , 'Ign. val.'// 'Ignorar valores'
			             , 'Diferencias'
			             , 'Observaciones'
			             , 'Fecha Concil.'
			             , 'Usuarios Concil.' ]
		 ,colModel : [ 
		               {name : 'cdConciliacion',index : 'id', width : 57, align : 'right', hidden : false} 
		               ,{name : 'cdSector',width : 87,align : 'right',hidden : false}
		               ,{name : 'periodoFacturacion',width : 35,align : 'center',hidden : false}
		               ,{name : 'stPeriodoFact',width : 100,align : 'left',hidden : true}
		               ,{name : 'cdProducto',width : 70,align : 'right',hidden : false}
		               ,{name : 'descProducto',width : 100,align : 'center',hidden : false}
		               ,{name : 'estadoConciliacion',width : 115,align : 'center',hidden : false}
		               ,{name : 'stIgnoraVal',width : 33,align : 'center',hidden : false}
		               ,{name : 'stDiferencia',	width : 90,align : 'center',hidden : false}
		               ,{name : 'observaciones',width : 278,align : 'center',hidden : false}
		               ,{name : 'fechaConciliacion',width : 60,align : 'center',hidden : false}
		               ,{name : 'usuarioConciliacion',width : 70,align : 'center',hidden : false}
		               ],
			rowNum : 2000,
// rowList : [ 10, 15, 20 ],
            scrollerbar:true,
			emptyrecords : 'Sin registros',
			sortName : '',
			caption : "Conciliaciones",
			height : '100%',
			search: true,
// height :'400',
// width : '300%',
            width : '1050',
            multiselect : false,
			loadonce : true,
			loadCompleteFunc : '',
			viewrecords : true,
			editurl : '',
			shrinkToFit : false,
		    gridComplete: function() {
               var rowData = $("#gridBusqConciliacionesId").getDataIDs();
               var height;
               if (rowData.length <= 30) {
            	  height = 23.52 * rowData.length; 
               } else {
             	  height = 400; 
               }
               $("#gridBusqConciliacionesId").jqGrid("setGridHeight",height);
            }	
		});
		$('.ui-jqgrid-title').css('width', '210%');
		addButtonsToConciliacionGrid();
		$('#panelExport').show(); 

	} catch (e) {
		jsError('loadConciliacionesGrid', e.message);
	}
	$('#busqConciliacionesGrid').show();
}

function errorProductos(cod, des) {
// jsError('errProducto', des);
}

$("#dialogAnularConcilia").dialog({  
	bgiframe: true,  
	autoOpen: false,  
	height: 'auto',  
	width: 700,
	modal: true,
	resizable:false,
	close:$(this).close , 
	buttons: {  
	    Anular: 
	    	function() {  
				try {					
					$('#confirmAnularConcilia').html('Usted est&aacute; a punto de anular definitivamente esta conciliaci&oacute;n. Todos los registros asociados a la misma dejar&aacute;n de estarlo. Confirma la anulaci&oacute;n?');
					$('#confirmAnularConcilia').dialog('option','modal',true).dialog('open');
				} catch(e) {
					jsError('dialogAnularConcilia',e.message);
				}					
        	},  
        Salir: 
        	function() {        	
					$(this).dialog("close");
       	}
		}
	});

$("#confirmAnularConcilia").dialog({			
	bgiframe: true,  
	autoOpen: false,  
	height: 'auto',  
	width: 350,
	modal: true,
	resizable:false,
	close:$(this).close , 
	buttons: {  
	    Si: function() {  
			try {
				// Obtengo el contenido de la fila seleccionada
				var values = jQuery("#gridBusqConciliacionesId").getRowData(getSelRowFromConciliacionGrid());						
				anularConciliacion(values);
				
			} catch(e) {
				jsError('confirmAnularConcilia',e.message);
			}					
        },  
        No: function() {
        	$('#confirmAnularConcilia').dialog('close');
        	$(this).dialog('close');
        }  
	}
});
// AGREGA BOTONERA DE LA GRILLA
function addButtonsToConciliacionGrid() {
		
	if ($('#anularConcil').val() == 'S') {
		
		var title = 'Anular Conciliciacion';
		$('#gridBusqConciliacionesId').navButtonAdd(
				'#gridBusqConciliacionesPager', {
					caption : 'Anular',
					id : 'btnGridAnular',
					buttonicon : 'ui-icon ui-icon-pencil',
					onClickButton : function() {
						try {
							var rowid = getSelRowFromConciliacionGrid();
							if (rowid != null) {
								var values = jQuery("#gridBusqConciliacionesId").getRowData(rowid);
								
								if(!($.trim(values['stPeriodoFact']) === 'ABI')){
									
									fAlertCB('No se puede Anular una Conciliaci\u00f3n con Per\u00edodo de Facturaci\u00f3n cerrado');
									
								}else{
									
									if($.trim(values['estadoConciliacion']) === 'Aprobada' || $.trim(values['estadoConciliacion']) === 'Grabada sin Aprobación'){
										$('#observaciones').val($.trim(values['observaciones']));
										openAnularDialog();
									}else{
										fAlertCB('No puede anular esta Conciliaci&oacute;n');
									}
							}

							} else {
								seleccioneRegistro();
							}
						} catch (e) {
							jsError('loadConciliacionesGrid(...)', e);
						}
					},
					position : 'last',
					title : title
				});
	}
	
	$('#gridBusqConciliacionesId').navButtonAdd(
			'#gridBusqConciliacionesPager', {
				caption : 'Detalle',
				buttonicon : 'ui-icon-search',
				id : 'btnGridDet',
				onClickButton : function() {
					try {
						var rowid = getSelRowFromConciliacionGrid();
						if (rowid != null) {
							var values = jQuery("#gridBusqConciliacionesId").getRowData(rowid);
							// javascript:loadContentDiv('ABMCConciliaciones.action');
							if($('#checkNoMedible').is(':checked')){
								callAction('ABMCNoMedibles.action?cdConciliacion='+$.trim(values['cdConciliacion']), null, "contenido", true, null, null);
							}else{
								callAction('ABMCConciliaciones.action?cdConciliacionDetalle='+$.trim(values['cdConciliacion']), null, "contenido", true, null, null);
							}
//							
							// window.open('ABMCConciliaciones.action','_self');
						} else {
							seleccioneRegistro();
						}
					} catch (e) {
						jsError('loadConciliacionesGrid(...)', e);
					}
				},
				position : 'last',
				title : title
			});
	
// $('#btnGridAnular').hide();
// $('#btnGridDet').hide();

}

function openAnularDialog(){
	
// Setea el estilo aca por que el en ready no funciona el setear el valor de la
// fila en el combo

	$('#dialogAnularConcilia').dialog('option','modal',true).dialog('open');
	
}

function getSelRowFromConciliacionGrid() {
	return $("#gridBusqConciliacionesId").getGridParam('selrow');
}

function isNumber(evt) {
	evt = (evt) ? evt : window.event;
	var charCode = (evt.which) ? evt.which : evt.keyCode;
	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	}
	return true;
}
	
function cargarCombo(json, idCombo, method, bool,px, valDefault){
		try {		
	
			$('#' + idCombo).empty();
			$('#' + idCombo).selectmenu('destroy');
			
			if (json.paramList!=undefined){	    	
				$('#' + idCombo).get(0)[$('#' + idCombo).get(0).length] = new Option(valDefault,'0');
		    	// Cargo el resto de las opciones del combo
		    	if (json.paramList.length>0){
		    	    		
		    		var cod;
		    		var desc;	    		
		    		for (var i=0;i<json.paramList.length;i++){		
		    			cod = json.paramList[i].cod;
		    			desc = json.paramList[i].desc;
		    			if(bool)
		    				$('#' + idCombo).get(0)[$('#' + idCombo).get(0).length] = new Option((desc, cod));
		    			else
		    				$('#' + idCombo).get(0)[$('#' + idCombo).get(0).length] = new Option((cod, cod));
		    		}	         	
			    }else{
			    	// Cargo el valor de default
			    	$('#' + idCombo).get(0)[0]= new Option(valDefault,'0');
				}
			}else{
				// Cargo el valor de default
		    	$('#' + idCombo).get(0)[0]= new Option(valDefault,'0');
			}
		    
			// Cargo los estilos
	    	$('#' + idCombo).selectmenu('destroy');
	    	$('#' + idCombo).selectmenu({style:'dropdown', maxHeight:'200', width:px});
		    
		} catch(e) {
			jsError('Error en '+ method + '--->', e);
		}	
	}

	function errorPeriod(cod, des){
// jsError('error en carga de lista de Peridos: ', des);
	}

	function anularConciliacion(values){
		
		var params = '';
		params += 'cdConciliacion='+ values['cdConciliacion'];
		params += '&observaciones='+$('#observaciones').val();
		params += '&checkProdNoMedible='+$('#checkNoMedible').val();
		callJsonAction('anularConciliacion.action', params, 'successAnulacion', 'errorAnulacion');
	}
	
	function successAnulacion(json){
		if(json.result.errorCode == 0){
			setSuccessMsg($('#busqConciliacion_responseMsgs'), json.result.errorDesc);
			$('#busqConciliacion_responseMsgs').show();
			$('#dialogAnularConcilia').dialog('close');
			$('#confirmAnularConcilia').dialog('close');
			search();
		}else{
			errorAnulacion(json);
		}
	}
	
	function errorAnulacion(json){
		setErrorMsg($('#busqConciliacion_responseMsgs'), errorMessage);
		$('#busqConciliacion_responseMsgs').show();
		$('#dialogAnularConcilia').dialog('close');
		$(this).dialog('close');
		search();
	}
	
	/**
	 * Funcion que se encarga de exportar a excel la grilla de la pantalla
	 * 
	 */
	function exportExcel(){
		
		var params = '';
		params += $('#globalParams').val(); 

		if($.trim(params) === ''){
			fAlertCB('Para Exportar debe haber realizado una busqueda Primero');
		}else{
			var title = 'Busqueda de Conciliaciones';
			var nameFilter = 'Proveedor,Sector Control,Periodo Fact. Desde,Periodo Fact. Hasta,Producto,Estado,Con/Sin Dif.,Nro. Concilia';
			var filterData = $('#proveedorList option:selected').text() + ';'+$('#sectorList option:selected').text() + ';'+ $('#selectFDesdeList option:selected').text()+ ';';
				filterData += $('#selectFHastaList option:selected').text() +';'+$('#productosList option:selected').text() +';'+$('#estadoDifList option:selected').text() +';';
				filterData += $('#stDiferencia option:selected').text() +';' + $('#cdConciliacion').val();
			
			var printContent = '';
			printContent += exportDataFilterToHtml('Parametros de Busqueda',nameFilter,title, filterData);
			printContent += exportDataGridToHtml('',title,'gridBusqConciliacionesId','');
			
			$('#nameFile').val(title);
			$('#html').val(createHtmlToExport(printContent));
			$('#fileType').val('xls');		
			$('#exportForm').get(0).submit();	        	
		}	
	}
	
	/**
	 * @author david
	 * 
	 * Funcion que exporta el contenido del grid a PDF
	 * 
	 */
	function exportDataGridPDF(){
		var params = '';
		params += $('#globalParams').val(); 

		if($.trim(params) === ''){
			fAlertCB('Para Exportar debe haber realizado una busqueda Primero');
		}else{
			var title = 'Busqueda de Conciliaciones';
			var nameFilter = 'Proveedor,Sector Control,Periodo Fact. Desde,Periodo Fact. Hasta,Producto,Estado,Con/Sin Dif.,Nro. Concilia';
			var filterData = $('#proveedorList option:selected').text() + ';'+$('#sectorList option:selected').text() + ';'+ $('#selectFDesdeList option:selected').text()+ ';';
				filterData += $('#selectFHastaList option:selected').text() +';'+$('#productosList option:selected').text() +';'+$('#estadoDifList option:selected').text() +';';
				filterData += $('#stDiferencia option:selected').text() +';' + $('#cdConciliacion').val();
			
			var printContent = '';
			printContent += exportDataFilterToHtml('Parametros de Busqueda',nameFilter,title, filterData);
			printContent += exportDataGridToHtml('',title,'gridBusqConciliacionesId','');
			
			$('#nameFile').val(title);
			$('#html').val(createHtmlToExport(printContent));
			$('#fileType').val('pdf');		
			$('#exportForm').get(0).submit();	        	
		}	
	}
	
	/**
	 * @author david
	 * 
	 * Funcion que limpia los combos
	 * 
	 * @param desc ->
	 *            titulo valor nulo
	 * @param cod ->
	 *            id de posicion
	 * @param id ->
	 *            id combo
	 * @param hpx ->
	 *            pixeles de alto
	 * @param wpx ->
	 *            pixeles de ancho
	 */
	function limpiarCombo(desc, cod, id, hpx, wpx) {
			
// Vacio el combo
		$('#' + id).empty();	
// Cargo el valor de default
		$('#' + id).get(0)[0]= new Option(desc,cod);
// Cargo los estilos
		$('#' + id).selectmenu('destroy');
		$('#' + id).selectmenu({style:'dropdown', maxHeight:hpx ,width:wpx});
	}	
	
	function validaPeridoConc(pd,ph){
		var periodosValidos;
	    if(pd == '0'
	    || ph == '0')
	 		periodosValidos = true;			 	
	    else{
	    	var params2='';
	    	params2 += 'periodD='+pd;
	        params2 += '&periodH='+ph;
	        params2 += '&codProveedor='+$('#proveedorList').val();
	        params2 += '&invoker=busquedaConcilicaciones';
			$.ajax({
				async:false
				, url:'validatePeriodFact.action?'+params2
				, success: function(data){
 
					if((data.result.errorCode) == 0) {
					 		periodosValidos = true;			 		
					 	}else{
					 		periodosValidos = false;
					 		fAlertCB(data.result.errorDesc);
					 	}	
					
				}
				, timeout:15000
				, error: function(msg){
					periodosValidos = false;
				}
			});
			return periodosValidos;
		}		


	}
	
