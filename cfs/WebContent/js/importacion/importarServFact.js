$(document).ready(function(){
	
	$(':button').button();
	$('#aceptarButton').button({icons : {secondary : "ui-icon-print"}}).click(function() {hacerSumit();});

	destroyCombos();

    document.getElementById('observaciones').setAttribute('maxlength', '50');
	var totalImportadosCorrec = $('#totalImportadosCorrec').val();
	var codError = $('#codError').val();
	var errors = $('#errors').val();
	 successImport(totalImportadosCorrec,codError,errors);
	

});

function hacerSumit() {
	
	var urlArch = $('#urlArchivo').val();
	if(urlArch.substr(urlArch.length - 4).toLowerCase() == '.txt'){
	
		var cdProv = $('#filtroProveedorList').val();
		if(cdProv == '0'){
			fAlert("Debe seleccionar un Proveedor.");
		}else if(urlArch ==""){
			fAlert("Debe seleccionar un archivo.");
		}else{
			document.forms[0].submit();
		}	
	}else{
		fAlert("Tipo de archivo seleccionado para importar invalido. Debe ser un archivo de texto .txt");
	}	
}


function successImport(totalImportadosCorrec,codError,errors){
	if(codError == 88){
		
		fAlertCB("Se importaron correctamente "+totalImportadosCorrec+" registros."+"    Error en envio de mails");
		destroyCombos();

	}
	if(codError == 90){
		fAlert("Proveedor no coincide con el del archivo seleccionado");
		
	}

	if(codError == 99)	{
		
		fAlertCB("Hubo errores, ver archivo de logs.");
		destroyCombos();
		}
	if(codError == 1){
		
		fAlertCB("Hubo errores, no se pudo procesar el archivo.");
		destroyCombos();
	}
	if(codError == 0
	|| codError == 2){
		
		fAlertCB("se importaron correctamente "+totalImportadosCorrec+" registros."+"\n       Se enviaron correctamente los mails");
		destroyCombos();
	}
	
		
	$('#totalImportadosCorrec').val("");
	$('#codError').val("");
	$('#errors').val("");

	}

function destroyCombos() {
	destroyCombosGeneralOne('filtroProveedorList');
	$('#filtroProveedorList').selectmenu('setValue', '0');
	$('#observaciones').val("");
	

	
}

/* RECARGA PRODUCTOS */
function initialiseFiltros(){
	var $proveedor = $('#filtroProveedorList');
	$proveedor.change(function(){
	   recargarCombos($('#filtroProveedorList').val());
	});
	onchangeOptions('filtroProveedorList');
}

function onchangeOptions(selectorId){
   $("#"+selectorId).change(function(){
   });
}


function obtenerProveedorValor(cdProveedor, cdPeriodo, cdUnival) {
	callJsonAction("proveedorValorConciliacion.action","cdProveedor="+cdProveedor+"&cdPeriodo="+cdPeriodo+"&cdUniVal="+cdUnival, "successDetalleProveedorValor", "errorDetalleProveedorValor");
}




/*--------------------------------------------------------------------------------------------------------*/
/* IMPORTE VALOR MONETARIO PROVEEDOR PERIODO UNIVAL */
/*--------------------------------------------------------------------------------------------------------*/
function successDetalleProveedorValor(jsonData) {

}

function errorDetalleProveedorValor(cod, des) {
   jsError(cod, des);
}


//obtenerProveedorValor($('#filtroProveedorList').val(), $('#filtroPeriodoList').val(), $('#cdUniVal').val());