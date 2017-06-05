$(document).ready(function(){
	
	$(':button').button();
	$('#aceptarButton').button({icons : {secondary : "ui-icon-print"}}).click(function() {validarYEnviar();});
	destroyCombos();
    document.getElementById('observaciones').setAttribute('maxlength', '50');
	
	var totalImportadosCorrec = $('#totalImportadosCorrec').val();
	var codError = $('#codError').val();
	var errors = $('#errors').val();
//	alert(totalImportadosCorrec);
//	alert(codError);
//	alert(errors);
	 successImport(totalImportadosCorrec,codError,errors);
	
});

function validarYEnviar() {
	
	var proveedor = $('#filtroProveedorList').val();
	var observ = $('#observaciones').val();
	var urlArch = $('#urlArchivo').val();
	
	msg = "";
	if (proveedor=="0")  
		msg += "Debe selecionar un proveedor\n";
	
	if (urlArch=="") 
		msg +="Debe seleccionar un archivo\n";
	
	if (msg!="") 
		alert(msg);
	
	if ( !(proveedor=="0") && !(urlArch=="")  ) {
		document.forms[0].submit();
		//hacerSummit(proveedor,observ,urlArch);
	}
		
		
	
}

function successImport(totalImportadosCorrec,codError,errors){

	if(codError == 88){
			
			fAlertCB("se importaron correctamente "+totalImportadosCorrec+" registros."+"    Error en envio de mails");
			destroyCombos();

		}
		if(codError == 90){
			
			fAlertCB(errors);
			
		}
		/*if(codError == 99 && totalImportadosCorrec != 0 ){
			
			fAlertCB("se importaron correctamente "+totalImportadosCorrec+" registros.");
			destroyCombos();
		}*/

		if(codError == 99)	{
			
			fAlertCB("Hubo errores, ver archivo de logs.");
			destroyCombos();
			}
		if(codError == 1){
			
			fAlertCB("Hubo errores, no se pudo procesar el archivo.");
			destroyCombos();
		}
		if(codError == 0){
			
			fAlertCB("se importaron correctamente "+totalImportadosCorrec+" registros."+"\n       Se enviaron correctamente los mails");
			destroyCombos();
		}
		
			
		$('#totalImportadosCorrec').val("");
		$('#codError').val("");
		$('#errors').val("");

		}


function destroyCombos() {
	destroyCombosGeneralOne('filtroProveedorList');
}

/* RECARGA PRODUCTOS */
function initialiseFiltros(){
	var $proveedor = $('#filtroProveedorList');
	$proveedor.change(function(){
	  // hideCommonDataElements();
       //limpiarProveedor();
	   recargarCombos($('#filtroProveedorList').val());
	});
	onchangeOptions('filtroProveedorList');
}

function onchangeOptions(selectorId){
   $("#"+selectorId).change(function(){
      //hideCommonDataElements();
   });
}

function hideCommonDataElements(){
  
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