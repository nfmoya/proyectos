$(document).ready(function() {
   $('#buttonSeleccionar').button();
   $('#buttonCambiar').button();

   applyTooltip($("#buttonSeleccionar"));
   applyTooltip($("#apellidoNombre"));
});

function cambiarPersona() {
	showCompPersona('cbPersonaSeleccionada',0,'B&uacute;squeda de personas');
}

/*TODO: FIJARME SI NO PUEDO CAPTURAR ESTO DESDE EL JS Y HACER CALLBACK GEN*/
function cbPersonaSeleccionada(data) {
	
	hideCompPersona();
	if (data) {
		
		$('#apellidoNombre').val(data['apellido']+', '+data['nombre']);
		$('#usersPersonaId').val(data['personaId']);
		$('#usersPersonaNroDoc').val(data['numeroDoc']);
		
		cargarDatosComplementarios();
		
		if ($('#usersPersonaId').val() != null && $('#usersPersonaId').val() != "") {
			$('#buttonCambiar').show();
			applyTooltip($("#buttonCambiar"));
			$('#buttonSeleccionar').hide();
		}
	} 
}

function cargarDatosComplementarios() {
	callAction('datosCompUser.action', 'personaId='+$('#usersPersonaId').val(), 'divDatosComplementarios', true, null, 'errorDatosComp');
}

function errorDatosComp(cod, des){
	setErrorMsg($('#divDatosComplementarios'),des);
	$('#divDatosComplementarios').show();
}

function initParamUser() {
	if ($('#userId').val() == null || $('#userId').val() == 0){
   		$('#userName').val($('#usersPersonaNroDoc').val());
    }

    if ($('#ongId').val() != '' && $('#ongId').val() != 0){
    	$('#txtONG').selectmenu('setValue',$('#ongId').val());	
    } else {
		$('#txtONG').selectmenu('setValue',$('#txtONG > option')[0].value);
    }
    /*Esto me carga inicialmente los perfiles que voy a bajar*/
	for (i=0; i<$(':checkbox').size(); i++) {
		insertPerfilesChk(($(':checkbox')[i].id).replace('chk',''), 'hidPerfileSinChk');
	}

    /**/
    var perfiles = "";
 	if ($('#hidPerfilesACargar').val().indexOf(',') == 0) {
		perfiles = $('#hidPerfilesACargar').val().substr(1);
	}
	
	$('#hidPerfilesACargar').val('');
    
	if (perfiles != "") {
		var lPerfiles = perfiles.split(',');
		for (i=0; i<lPerfiles.length; i++) {
			$('#chk'+lPerfiles[i]).attr({"checked":"checked"});
			$('#chk'+lPerfiles[i]).trigger('change');
			deletePerfilesChk(lPerfiles[i], 'hidPerfileSinChk');
		}
	}
}

function guardarUsuario() {
	var vPerfiles = $("#hidPerfilesACargar").val();
	if (vPerfiles.indexOf(',') == 0) {
		vPerfiles = vPerfiles.substr(1);
	}
	
	params = 'personaId='+$("#usersPersonaId").val();
	params = params+'&ongId='+$("#txtONG").val();
	params = params+'&strPerfiles='+vPerfiles;
	params = params+'&userName='+$("#userName").val();
	params = params+'&txtEstadoUser='+$("#txtESTADOS").val();

	if ($('#userId').val() == null || $('#userId').val() == 0) {
		callJsonAction('CreateUser.action', params, 'successSaveUser', 'errorSaveUser');
	} else {
		var vPerfilesDel = $("#hidPerfileSinChk").val();
		if (vPerfilesDel.indexOf(',') == 0) {
			vPerfilesDel = vPerfilesDel.substr(1);
		}
		params = params+'&strPerfilesDel='+vPerfilesDel;
		params = params+'&userId='+$("#userId").val();
		callJsonAction('EditUser.action', params, 'successSaveUser', 'errorSaveUser');
	}
}

function successSaveUser(json){
	if (json.result.errorCode == 0){
		setSuccessMsg($('#ABMUsersMessage'),json.result.errorDesc);
		$('#ABMUsersMessage').show();
		//cargarDatosComplementarios();
		limpiarUsuario();
	} else {
		errorSaveUser(json.result.errorCode, json.result.errorDesc);
	}
}

function errorSaveUser(cod, des){
	setErrorMsg($('#ABMUsersMessage'),des);
	$('#ABMUsersMessage').show();
}

function cancelarUsuario() {
	limpiarUsuario();
}

function limpiarUsuario() {
	$('#apellidoNombre').val("");
	$('#usersPersonaId').val("");
	$('#usersPersonaNroDoc').val("");
	$('form#datosCompUser').remove();
}

function setPerfiles(id, checked, event) { 
	
	if (event.type == "change") {
		if (checked == true) {
			insertPerfilesChk(id.replace('chk',''), 'hidPerfilesACargar');
			deletePerfilesChk(id.replace('chk',''),'hidPerfileSinChk');
		} else {
			insertPerfilesChk(id.replace('chk',''),'hidPerfileSinChk');
			deletePerfilesChk(id.replace('chk',''),'hidPerfilesACargar');
		}
	}
}

function deletePerfilesChk(id, hidPerfilesName) {
	var perfiles = $('#'+hidPerfilesName).val();
	if (perfiles != '' && perfiles != null) {
		if (perfiles.indexOf(',') < 0) {
			perfiles = ','+perfiles;
		}
		if (perfiles.substr(perfiles.length-1) != ',') {
			perfiles = perfiles+',';
		}
		
		perfiles = perfiles.replace(','+id+',',',');
		
		if (perfiles.substr(perfiles.length-1) == ',') {
			perfiles = perfiles.substr(0,perfiles.length-1);
		}
		$('#'+hidPerfilesName).val(perfiles);
	}
}

function insertPerfilesChk(id, hidPerfilesName){
	var perfiles = $('#'+hidPerfilesName).val().split(',');
	perfiles[perfiles.length] = id;
	$('#'+hidPerfilesName).val(perfiles);
}