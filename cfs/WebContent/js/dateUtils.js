/*TODO: PASAR A PLUGIN DE JQUERY*/

/*	Function formatFecha_serviceToJs
	@param fecha yyyy-mm-dd 0:0:0 etc..
	@return value dd/mm/yyyy
*/
function dateFormat_serviceToJs(fecha){
	var value = "";
	if (fecha != null && fecha != "") {
		value = fecha.substr(8,2)+'/'+fecha.substr(5,2)+'/'+fecha.substr(0,4);
	}

	return value;
}

/*	Function formatFecha_serviceToJs
	@param value dd/mm/yyyy
	@return fecha yyyy-mm-dd 0:0:0 etc..
*/
function dateFormat_JsToService(fecha){
	var value = fecha.substr(6,4)+'/'+fecha.substr(3,2)+'/'+fecha.substr(0,2);
	return value;
}

function cleanMaskFechaJS(fecha) {
	var value = fecha.substr(0,2)+fecha.substr(3,2)+fecha.substr(6,4);
	return value;
}

function validateDate(fecha) {
	var strFecha = cleanMaskFechaJS(fecha);
	var	dd = parseInt(strFecha.substr(0,2),10);
	var mm = parseInt(strFecha.substr(2,2),10);
	var yyyy = parseInt(strFecha.substr(4,4),10);
	
	/*longitud*/
	if (strFecha.length < 8) {
		return false;
	/*dias*/
	} else if (dd <= 0 || dd > 31) {
		return false;
	/*meses*/
	} else if (mm <= 0 || mm > 12) {
		return false;
	/*anios*/
	} else if (yyyy < 1900) {
		return false;
	/*meses 30 dias*/
	} else if (((mm == 4 || mm == 6 || mm == 9 || mm == 11) && dd > 30) || (mm == 2 && dd > 29)) {
		return false;
	/*anio biciesto*/
	} else if ((mm == 2) && (dd > 28) && (yyyy%4 > 0)) {
		return false;
	}

	return true;
}