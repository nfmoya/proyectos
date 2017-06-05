/** 
 * Inicilizacion de combos y botones de la pantalla
 * 
 * @author David
 */
$(document).ready(function(){
	
	$(':button').button();
	$('#btnBuscar').button({icons : {secondary : "ui-icon-search"}}).click(function() {search();});
	$('#btnExportar').button({icons : {secondary : "ui-icon-bookmark"}}).click(function() {exportGridXLS();});	
	$('#btnImprimir').button({icons : {secondary : "ui-icon-print"}}).click(function() {exportDataGridPDF();});
	//Botones ocultos hasta que se realice una busqueda
	$('#panelExport').hide();
	destroyCombos();
   	validateSectorUsuario();
	
});


/**
 * Destruyo los combos de la pantalla y les setea el style
 * 
 * @author David
 */
function destroyCombos() {
	
	$('#selectProveedorList').selectmenu('destroy');	
	$('#selectProveedorList').selectmenu({style:'dropdown', maxHeight:'200', width: $(this).attr('width')});

	$('#selectSectorList').selectmenu('destroy');	
	$('#selectSectorList').selectmenu({style:'dropdown',maxHeight:'200', width: $(this).attr('width')});

	$('#selectProductoList').selectmenu('destroy');	
	$('#selectProductoList').selectmenu({style:'dropdown', width: '200px'});
	
	$('#selectEstadoDifList').selectmenu('destroy');	
	$('#selectEstadoDifList').selectmenu({style:'dropdown', maxHeight:'200', width: '200px'});
	
	$('#selectSolucionDifList').selectmenu('destroy');	
	$('#selectSolucionDifList').selectmenu({style:'dropdown', maxHeight:'200' , width: '200px'});
	
	$('#selectFDesdeList').selectmenu('destroy');	
	$('#selectFDesdeList').selectmenu({style:'dropdown', width:'200px'});
	
	$('#selectFHastaList').selectmenu('destroy');
	$('#selectFHastaList').selectmenu({style:'dropdown', width:'200px'});
}


/**
 * Funcion que bloquea el sector si el usuarios no es Administrador
 * 
 */
function validateSectorUsuario(){
	$('#selectSectorList').selectmenu('setValue', $("#userSector").val());	
   	if ($("#userSector").val() != "0") {
       $('#selectSectorList').selectmenu('disabled', true);	
    }	
}

/**
 * Limpio los div -  ocultos los carteles si los hay y genero la consulta
 * 
 * @author David
 */
function search() {
	
	$('#difConciliacion_responseMsgs').hide();
	limpiarGrilla('gridDifConciliaId' , 'gridDifConciliaPager' , 'difConciliaGrid');
	$('#panelExport').hide();
	//Proceso que valida los datos para el search	
	var msg = searchValidateDif();

	
	
	if( msg == ''){
		if($.trim($('#cdConciliacion').val()) == ''){			
			var params='';
			params += 'periodD='+$('#selectFDesdeList').val();
		    params += '&periodH='+$('#selectFHastaList').val();
		    params += '&codProveedor='+$('#selectProveedorList').val();
			
			callJsonAction('validatePeriodFact.action',params,'searchDifConciliacion','errorConcilia');			
		}else{
			searchDifConciliacion();
		}		
	}else{
		alert(msg);
	}	
}


/**
 * Funcion que ejecuta la busqueda de las conciliaciones
 * Le paso por parametro los filtros
 * 
 * @author David
 */
function searchDifConciliacion(){
	
	var params = '';
	params+= 'cdConciliacion='+$('#cdConciliacion').val();
	params += '&cdProveedor='+$('#selectProveedorList').val();
	params += '&cdSector='+$('#selectSectorList').val();
	params += '&cdProducto='+$('#selectProductoList').val();
	params += '&pfDesde='+$('#selectFDesdeList').val();
    params += '&pfHasta='+$('#selectFHastaList').val();
    params += '&stDiferencia='+$('#selectEstadoDifList').val();    
    params += '&tpSolucion='+$('#selectSolucionDifList').val();

    loadDifConciliacionGrid(params);
}

/**
 * Metodo que se encarga de cargar la grilla en pantalla
 * 
 * @param params
 * 
 * @author David
 */
function loadDifConciliacionGrid(params){
	
	limpiarGrilla('gridDifConciliaId' , 'gridDifConciliaPager' , 'difConciliaGrid');	

	
	$('#globalParams').val(params);
	
	try {
		$('#gridDifConciliaId').jqGrid({
			url:'JsonDifConciliacionList.action?'+params,
		   	datatype:'json',											
		    mtype:'POST',
		    colNames:[
		              	 'idDif'
		              	 , 'Nro Conc'
			             , 'Nro. Difer.'
			             , 'Nro. Remito'
			             , 'Sector Control'
			             , 'Per Fact.'
			             , 'Estado Per.'
			             , 'Producto'
			             , 'Descripcion'
			             , 'Pza. Desde'
			             , 'Pza. Hasta'
			             , 'Cantidad'
			             , 'U. Val.'
			             , 'Valor'
			             , 'Tip. Val.'
			             , 'codEstado'
			             , 'Estado'
			             , 'Fecha Alta'
			             , 'Usuario Alta'
			             , 'Observaciones'
			             , 'codSolucion'
			             , 'Sit. Solucion'
			             
		             ],
			colModel:[
			        {name : 'idDif', index: false,width : 50, align : 'left', hidden : true },//OCULTAR
					{name : 'cdConciliacion', index: false,width : 57, align : 'right', hidden : false }, 
					{name : 'cdOrden' , width : 80, align : 'right', hidden : false },
					{name : 'cdRemito' , width : 80, align : 'right', hidden : false },
					{name : 'cdSector', width : 87, align : 'right', hidden : false }, 
					{name : 'cdPeriodoFact', width : 40, align : 'center', hidden : false },
					{name : 'stPeriodoFact', width : 30, align : 'center', hidden : true }, 
					{name : 'cdProducto', width : 80, align : 'center', hidden : false }, 
					{name : 'descProducto', width : 100, align : 'center', hidden : false }, 
					{name : 'pzaDesde', width : 80, align : 'center', hidden : false }, 
					{name : 'pzaHasta' , width : 80, align : 'center', hidden : false }, 
					{name : 'ctDiferencia', width : 50, align : 'right', hidden : false }, 
					{name : 'cdUniVal', width : 30, align : 'right', hidden : false }, 
					{name : 'imPrecioTot', width : 50, align : 'right', hidden : false }, 
					{name : 'cdTipVal', width : 50, align : 'center', hidden : false },
					{name : 'stDiferencia', width : 20, align : 'center', hidden : true}, //OCULTAR 
					{name : 'descDiferencia', width : 50, align : 'center', hidden : false},
					{name : 'fhALta', width : 60, align : 'center', hidden : false }, 
					{name : 'descUsuAlta', width : 60, align : 'center', hidden : false }, 
					{name : 'observacion', width : 600, align : 'center', hidden : false }, 
					{name : 'tpSolucion', width : 20, align : 'center', hidden : true },//OCULTAR
					{name : 'descSolucion', width : 120, align : 'center', hidden : false }
			],
		    jsonReader : {
		    	root:'gridModelToShow',
		    	repeatitems:false,
		    	id:'idDif'
		    }
			,width: 1000
		    ,emptyrecords:'Sin registros'
		    ,rowNum: 0
//		    ,rowList:[]
		    ,pager: '#gridDifConciliaPager'
		    ,sortname: 'cdConciliacion, cdOrden ,cdSector, cdPeriodoFact, cdProducto'
		    ,viewrecords: true
		    ,sortorder: 'asc'
//		    ,height : 0
		    ,shrinkToFit: false
		    ,caption: 'Diferencias en Conciliaciones'
		    ,onSelectRow : function(row_id){
		    	var values = jQuery("#gridDifConciliaId").getRowData(row_id);
		    	showHideEdit($.trim(values['stDiferencia']),$.trim(values['stPeriodoFact']));
		    }			
		    ,loadComplete:function(data){		    	
//		    	Si tiene Registro habilita el panel de exportacion
		    	var rows= jQuery("#gridDifConciliaId").jqGrid('getRowData');		    	
		    	
		    	if(rows.length > 0 ){$('#panelExport').show();}else{$('#panelExport').hide();}
		    	
		    	for(var i=0; i < data.gridModelToShow.length; i++){
								
					var valorEstado = $("#gridDifConciliaId").jqGrid('getCell', data.gridModelToShow[i].idDif, 'stDiferencia');
					var valorSolucion = $("#gridDifConciliaId").jqGrid('getCell', data.gridModelToShow[i].idDif, 'tpSolucion');
					//Seteamos el valor de las columnas de descripcion
					formatoCeldaEstado(data.gridModelToShow[i].idDif,valorEstado);					
					formatoCeldaSituacion(data.gridModelToShow[i].idDif,valorSolucion);					
				}
		    			    	
		    	rows= jQuery("#gridDifConciliaId").getDataIDs();
		    	if (rows.length == 0) {
	            	  height = 0; //23.52 * rowData.length; 
	               } else  if (rows.length <= 30){
	            	   height = '100%'; //23.52 * rowData.length; 
	               }else{
	            	   height = 400;
	               }
	               
		    	$("#gridDifConciliaId").jqGrid("setGridHeight",height); 		    	
		    }
		});
		
		//Reload de la tabla para el display de los datos cargados
		$('#gridDifConciliaId').trigger('reloadGrid');
		
		//Saco los botones genericos de la grilla
		$("#gridDifConciliaId").navGrid('#gridDifConciliaPager',{refresh:false,edit:false,add:false,del:false,search:false});
		
		//Agrego los botones personalizados
		addButtonsToGrid();
			
		//??? no se que hacen	
		$(".ui-jqgrid-labels").css('font-size','10px');
		$(".ui-pg-table").css('font-size','10px');
		$('.ui-jqgrid .ui-jqgrid-bdiv').css('overflow-y', 'scroll');

	} catch(e){
		jsError('paginaCentroComputos',e.message);
	}	
	
	//Muestro la grilla en pantalla
	$('#difConciliaGrid').show();	
}


/**
 * @author david
 * 
 * Funcion que retorna el el texto a mostrar de acuerdo con el contenido de la celda
 * 
 * @param cellvalue
 * @param options
 * @param rowObject
 */

function formatoCeldaEstado(id, valor){
		
	var texto = '';
	var center = 'center';
	
	switch(valor){
	case 'ACT':
		texto = 'Activo';
		break;
	case 'ANU':
		texto = 'Anulado';
		break;
	default:
		texto = 'Anulado';
		break;	
	}		
	
	$("#gridDifConciliaId").jqGrid('setCell', id, 'descDiferencia', texto, {'text-align': center});	
}

/**
 * @author david
 * 
 * Funcion que retorna el el texto a mostrar de acuerdo con el contenido de la celda
 * 
 * @param cellvalue
 * @param options
 * @param rowObject
 */

function formatoCeldaSituacion(id, valor){
	
	var texto = '';
	var center = 'center';
	
	switch(valor){
	case 'PENSOL':
		texto = 'Pendiente de Solucion';
		break;
	case 'SINAJU':
		texto = 'Sol. no genera Ajuste';
		break;
	case 'AJUSTE':
		texto = 'Sol. genera Ajuste';
		break;
	default:
		texto = 'Pendiente de Solucion';
		break;	
	}		
	
	$("#gridDifConciliaId").jqGrid('setCell', id, 'descSolucion', texto, {'text-align': center});	
}


/**
 * Retona la fila seleccionada
 * @returns int or NULL
 * 
 * @author David
 */
function getSelRowGrid(){
	return $("#gridDifConciliaId").getGridParam('selrow');
}

/**
 * Agrega la botonera a la grilla
 * 
 * @author David
 */
function addButtonsToGrid(){	
	
	if($('#editGrant').val()=='S') {
		$("#gridDifConciliaId").navGrid('#gridDifConciliaPager').navButtonAdd('#gridDifConciliaPager', {
			caption: 'Editar',
			buttonicon: 'ui-icon ui-icon-pencil',
			id: 'btnGridEdit',
			onClickButton: function() {
				try {
					var rowid = getSelRowGrid();
					
					if (rowid != null) {
						var values = jQuery("#gridDifConciliaId").getRowData(rowid);
						if($.trim(values['stDiferencia']) === 'ACT'){											
													
							var idConcilia = values['cdConciliacion'];
							var tpSolucion = values['tpSolucion'];
							var obs = values['observacion'];
							
							
//							Guardo los datos
							saveOldRecord(tpSolucion,obs);
							
							//Seteo lo valores en el popoUp
							$('#cdConciliacionEdit').val(idConcilia);
							$('#solucionDifListEdit option[value="'+ $.trim(tpSolucion) +'"]').attr('selected','selected');
							$("#obsDifConciliaEdit").val(obs);						
							
							//Abro dialogo
							openEditDifConciliaDialog();
						} else {
							fAlertCB('Para Editar tiene que estar en Estado ACT');							
						}
					}else{
						seleccioneRegistro();
					}
				} catch(e) {
					jsError('addButtonsToGrid(...)', e);
				}
			},
			position:'last',
			title:'Editar'
		});
		
		$('#btnGridEdit').hide();
	}	
}

/**
 * Abre la ventana en modo dialog
 * 
 * @author David
 */
function openEditDifConciliaDialog(){
	
//	Setea el estilo aca por que el en ready no funciona el setear el valor de la fila en el combo
	$('#solucionDifListEdit').selectmenu('destroy');
	$('#solucionDifListEdit').selectmenu({style:'dropdown', width:'150px'});	
	$('#dialogUpdateDifConcilia').dialog('option','modal',true).dialog('open');
}

/**
 * Funcion que se ejecuta cada vez que seleccionamos una fila
 * verifica que el estado del registro sea ACT (activo)
 * y habilita el boton Editar
 * 
 * @param reg -> Estado de la diferencia
 * @param per -> Estado del periodo
 * 
 * @author David
 */

function showHideEdit(reg,per){		
	if(reg === 'ACT' && per === 'ABI'){
		$('#btnGridEdit').show();	
	}else{
		$('#btnGridEdit').hide();
	}
}

/**
 * Metodo que guarda en las varibles hidden los valores actuales para 
 * comparar si hubo cambios
 * 
 * @author David
 */
function saveOldRecord(tp,obs){
	
	$('#oldTpSolucion').val(tp);
	$('#oldObservaciones').val(obs);	
}


/**
 * Declaracion de las propiedades del dialog de update
 * 
 * @author David
 */
$("#dialogUpdateDifConcilia").dialog({  
	bgiframe: true,  
	autoOpen: false,  
	height: 'auto',  
	width: 400,
	modal: true,
	resizable:false,
	close:$(this).close , 
	buttons: {  
	    Grabar: 
	    	function() {  
				try {					
					if (validaCambiosEdit()){
						$('#confirmUpdateDifConcilia').html('Seguro de Actualizar la Diferencia de Conciliacion?');
						$('#confirmUpdateDifConcilia').dialog('option','modal',true).dialog('open');
					}else{
						fAlertCB('No se han realizafdo modificaciones');
					}
				} catch(e) {
					jsError('dialogUpdateDifConcilia',e.message);
				}					
        	},  
        Salir: 
        	function() {        	
	        	if (validaCambiosEdit()){
	        		$('#confirmUpdateDifConcilia').html('Hay Cambios realizados. Desea guardarlos?');
	        		$('#confirmUpdateDifConcilia').dialog('option','modal',true).dialog('open');							
				}else{
					$(this).dialog("close");
				}	        		
        	}
		}
	});


/**
 * Declaracion de las propiedades del dialog de confirmar edicion
 * 
 * @author David
 */
$("#confirmUpdateDifConcilia").dialog({			
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
				//Obtengo el contenido de la fila seleccionada
				var values = jQuery("#gridDifConciliaId").getRowData(getSelRowGrid());						
				editConciliaAction(values);
				$('#dialogUpdateDifConcilia').dialog('close');
				$(this).dialog('close');
			} catch(e) {
				jsError('confirmUpdateDifConcilia',e.message);
			}					
        },  
        No: function() {
        	$('#dialogUpdateDifConcilia').dialog('close');
        	$(this).dialog('close');
        }  
	}
});

/**
 * Valido si hubo o no cambios
 * 
 * @author David
 */
function validaCambiosEdit(){
	
	var oldObs = $.trim($('#oldObservaciones').val().toUpperCase());
	var oldSol = $.trim($('#oldTpSolucion').val().toUpperCase());
	
	var newSol = $.trim($('#solucionDifListEdit').val()).toUpperCase();
	var newObs = $.trim($('#obsDifConciliaEdit').val().toUpperCase());
		
	if( newObs	=== oldObs && newSol === oldSol){	
		return false;
	}else{
		return true;		
	}
}

/**
 * Acccion que cargar el formbean con los parametros para editar
 * y llama a la accion
 * 
 * @param values
 * 
 * @author David
 */
function editConciliaAction(values){
	
	
	var params = '';
	params += 'cdConciliacion='+ values['cdConciliacion'];
	params += '&cdOrden='+ values['cdOrden'];
	params += '&cdRemito='+ values['cdRemito'];
	params += '&ctDiferencia='+ values['ctDiferencia'];
	params += '&imPrecioTot='+ values['imPrecioTot'];
    params += '&tpSolucion='+ $('#solucionDifListEdit').val();
    params += '&stDiferencia='+ values['stDiferencia'];    
    params += '&observaciones='+$('#obsDifConciliaEdit').val();
    
    
    callJsonAction('difConciliacionEdit.action', params, 'successEditConcilia', 'errorConcilia');
}

/**
 * Accion que salio todo bien vuelvo a ejecutar la busqueda
 * 
 * @author David
 */
function successEditConcilia(json){
	setSuccessMsg($('#difConciliacion_responseMsgs'), json.result.errorDesc);
	$('#difConciliacion_responseMsgs').show();
	searchDifConciliacion();
}


/**
 * Accion que salio todo mal
 * 
 * @author David
 */
function errorConcilia(errorCode, errorMessage){
	setErrorMsg($('#difConciliacion_responseMsgs'), errorMessage);
	$('#difConciliacion_responseMsgs').show();
}	


/**
 * Funcion que actualiza el combo de productos por proveedor y sector sino 
 * me fijo si alguno esta sin datos
 * 
 * @author david
 */
function changeComboProveedor(){
	
	var params = '';
	var prov = $('#selectProveedorList').val();
	var sector = $('#selectSectorList').val();

	if(prov != 0 && sector != 0){
				
		params += 'cdProveedor='+prov;
		params += '&cdSector='+sector;		
		
		callJsonAction('searchProductosByFilter.action',params,'successProductos','errorProductos');
	}else if (prov != 0){		
//		Asumo que el sector es 0 Lipio el combo
    	limpiarCombo(' Todos los Productos ','0','selectProductoList','200', '200px');
//    	Llamo al accion para cargar los periodos
    	searchPeriodByFilter(); 	
		
	}else if (sector == 0){
//		Asumo que el sector es 0 Limpio los combos
		limpiarCombo(' Todos los Productos ','0','selectProductoList','200', '200px');
		
	}else{
//		Asumo que el proveedor y sector es 0 Limpio los combos
		limpiarCombo(' Todos los Productos ','0','selectProductoList','200', '200px');
		limpiarCombo(' Todos los Periodos ','0','selectFDesdeList','200','200px');
		limpiarCombo(' Todos los Periodos ','0','selectFHastaList','200','200px');
    	
	}
}


/**
 * @author david
 * 
 * Recarga los combos por filtro
 * 
 */
function searchPeriodByFilter(){
	
	var params = '';
	var prov = $('#selectProveedorList').val();

	if(prov != 0){				
		params += 'cdProveedor='+prov;				
		callJsonAction('searchPeriodByFilter.action',params,'successPeriod','errorPeriod');
	}else {
		limpiarCombo('Todos los Periodos','0','selectFDesdeList','200', '200px');
		limpiarCombo('Todos los Periodos','0','selectFHastaList','200', '200px');
	}	
}


/**
 * Si todo sale bien recargo el combo de Periodos. 
 * 
 * @param json
 */
function successPeriod(json){
	successChargeCombo(json,'selectFDesdeList','successPeriodDesde', false, '200px','0',' Todos los Periodos ');
	successChargeCombo(json,'selectFHastaList','successPeriodHasta', false, '200px','0',' Todos los Periodos ');
}

/**
 * Si todo sale bien recargo el combo de productos. 
 * 
 * $('#id').get(0) ---> reEmplaza al  document.getElementById('id')
 * 
 * @param json -> rta del action
 */
function successProductos(json){
		
	successChargeCombo(json,'selectProductoList','selectProductoList', true,'','0',' Todos los Productos ');	
	//Llamo al accion para cambiar los periodos
	searchPeriodByFilter();
}

/**
 * Si salio mal la busqueda de productos!
 * 
 * @param cod
 * @param des
 */
function errorProductos(cod, des){
	jsError('error en carga de lista de Productos: ', des);
}

/**
 * Si salio mal la busqueda de Peridos!
 * 
 * @param cod
 * @param des
 */
function errorPeriod(cod, des){
	jsError('error en carga de lista de Peridos: ', des);
}

/**
 * Valida que si no se ingreso un numero de conciliacion
 * los demas campos hayan sido ingresados... 
 * y sino que el numero de conciliacion sea realmente un
 * numero  
 * 
 * @returns {String}
 * 
 * @author David
 */
function searchValidateDif(){
	var message = '';
	
	if($.trim($('#cdConciliacion').val()) == ''){
		if(	$.trim($('#selectProveedorList').val()) =='0')
			message += '* El Proveedor debe ser informado \n';
//		if($.trim($('#selectSectorList').val()) == '0')
//			message += '* El Sector debe ser informado \n';
//		if( $.trim($('#selectProductoList').val()) === '0')
//			message += '* El Producto debe  ser informado \n';
		if(($.trim($('#selectFDesdeList').val()) == '0'))
			message += '* Per\u00edodo de Facturaci\u00f3n Desde debe ser infomado \n';
		if(($.trim($('#selectFHastaList').val()) == '0'))
			message +='* Per\u00edodo de Facturaci\u00f3n Hasta debe ser infomado \n';			
		
	}else if(isNaN($('#cdConciliacion').val()) && $('#cdConciliacion').length > 10){
		message = '* Nro. Conciliaci\u00f3n debe ser un Numero menor a 10 Digitos \n';		
	}
	
	return message;	
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
		$('#unForm').get(0).submit();
//		callJsonAction('exportarExcelDifConcilia.action',params,'openExcel','getErrorMessages');	
	}	
}

/**
 * En caso de salir mal, Muestra el mensaje en pantalla
 * 
 * @param errorCode
 * @param errorMessage
 */
function getErrorMessages(errorCode, errorMessage){	
	
	setErrorMsg($('#difConciliacion_responseMsgs'), errorMessage);
	$('#difConciliacion_responseMsgs').show();	
}

/**
 * Abre la ventana con el excel exportado
 * 
 * @param json
 */
function openExcel(json){
	
	if (json.result.errorDesc != ''){
		setSuccessMsg($('#difConciliacion_responseMsgs'), json.result.errorDesc);
		$('#difConciliacion_responseMsgs').show();
	}
	window.location = json.url;
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
		var title = 'Diferencias de Conciliaciones';
		var titleFilter = 'Proveedor,Sector Control,Producto,Periodo Fact. Desde,Periodo Fact. Hasta,Estado,Sit. Solucion,Nro Concilia';
		
		var filterData = $('#selectProveedorList option:selected').text() + ';'+$('#selectSectorList option:selected').text() + ';'+ $('#selectFDesdeList option:selected').text()+ ';';
		filterData += $('#selectFHastaList option:selected').text() +';'+$('#selectProductoList option:selected').text() +';'+$('#selectEstadoDifList option:selected').text() +';';
		filterData += $('#selectSolucionDifList option:selected').text() +';' + $('#cdConciliacion').val();
		
		var printContent = '';
		printContent += exportDataFilterToHtml('Parametros de Busqueda',titleFilter,title,filterData);		
		printContent += exportDataGridToHtml('0,19',title,'gridDifConciliaId','12,15');

		$('#nameFile').val(title);
		$('#html').val(createHtmlToExport(printContent));
		$('#fileType').val('pdf');		
		$('#exportForm').get(0).submit();        	
	}
}


/**
 * @author david
 * 
 * Funcion que exporta el contenido del grid a PDF
 * 
 */
function exportGridXLS(){
	var params = '';
	params += $('#globalParams').val(); 

	if($.trim(params) === ''){
		fAlertCB('Para Exportar debe haber realizado una busqueda Primero');
	}else{			
		var title = 'Diferencias de Conciliaciones';
		var titleFilter = 'Proveedor,Sector Control,Producto,Periodo Fact. Desde,Periodo Fact. Hasta,Estado,Sit. Solucion,Nro Concilia';
		
		var filterData = $('#selectProveedorList option:selected').text() + ';'+$('#selectSectorList option:selected').text() + ';'+ $('#selectFDesdeList option:selected').text()+ ';';
		filterData += $('#selectFHastaList option:selected').text() +';'+$('#selectProductoList option:selected').text() +';'+$('#selectEstadoDifList option:selected').text() +';';
		filterData += $('#selectSolucionDifList option:selected').text() +';' + $('#cdConciliacion').val();
		
		var printContent = '';
		printContent += exportDataFilterToHtml('Parametros de Busqueda',titleFilter,title,filterData);		
		printContent += exportDataGridToHtml('0,19',title,'gridDifConciliaId','11,13');

		$('#nameFile').val(title);
		$('#html').val(createHtmlToExport(printContent));
		$('#fileType').val('xls');		
		$('#exportForm').get(0).submit();        	
	}	
}

/**
 * @author david
 * 
 * Funcion que limpia los combos
 * 
 * @param desc -> titulo valor nulo
 * @param cod -> id de posicion
 * @param id -> id combo
 * @param hpx -> pixeles de alto
 * @param wpx -> pixeles de ancho
 */
function limpiarCombo(desc, cod, id, hpx, wpx) {
		
//	Vacio el combo
	$('#' + id).empty();	
//	Cargo el valor de default
	$('#' + id).get(0)[0]= new Option(desc,cod);
//	Cargo los estilos
	$('#' + id).selectmenu('destroy');
	$('#' + id).selectmenu({style:'dropdown', maxHeight:hpx ,width:wpx});
}

/**
 * @author david
 * 
 * Funcion que recarga los combos con los datos del Action
 * 
 * @param json  -> datos json del Action
 * @param id ->id del combo a modificar
 * @param method -> nombre del metodo que lo invoca 
 * @param bool -> Graba cod - desc sino cod - cod
 * @param wpx -> tamanio del combo para el stylo
 * @param idDefault -> valor para el combo vacio
 * @param valDefault -> Descripcion para el combo vacio
 * 
 */

function successChargeCombo(json, id, method, bool,wpx, idDefault, valDefault){
	try {		
		
//		Vacio el combo
		$('#' + id).empty();
//		$('#' + id).selectmenu('destroy');		
		
	    if (json.paramList!=undefined){
	    	
	    	//Cargo el resto de las opciones del combo
	    	if (json.paramList.length>0){
	    		
	    		//Seteo si o si la opcion default
	    		$('#' + id).get(0)[0] = new Option(valDefault , idDefault);	    		
	    		
	    		var cod;
	    		var desc;	    		
	    		for (var i=0;i<json.paramList.length;i++){	
	    			cod = json.paramList[i].cod;
	    			desc = json.paramList[i].desc;
	    			if(bool)
	    				$('#' + id).get(0)[$('#' + id).get(0).length] = new Option(desc, cod);
	    			else
	    				$('#' + id).get(0)[$('#' + id).get(0).length] = new Option(cod, cod);
	    		}
		    }else{
//				Cargo el valor de default no hay datos
		    	$('#' + id).get(0)[0]= new Option(valDefault,idDefault);
			}	    	
		}else{
//			Cargo el valor de default sin datos en la lista
	    	$('#' + id).get(0)[0]= new Option(valDefault,idDefault);
		}	    	    
//    	Cargo los estilos Ancho dinamico o fijo
	    $('#' + id).selectmenu('destroy');
    	if((wpx == '' || wpx == undefined) && ($('#' + id).width() > 200))
    		$('#' + id).selectmenu({style:'dropdown', maxHeight:'200' ,width: $(this).attr('width')});
    	else    		
    		$('#' + id).selectmenu({style:'dropdown', maxHeight:'200' , width:'200px'});
    	

	} catch(e) {
		jsError('Error en '+ method + '--->', e);
	}	
}