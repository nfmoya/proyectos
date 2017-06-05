 /*
 * connection manager cfs
 * JM 
 *
 */
(function( $ ) {

$.widget( "connectionManager", {
	 version: "@VERSION",
	 options: {
		 action : '',
		 responseContainer : '',
		 params : '',
		 jsonType : true,
		 imgLoading : '<img src="${realpath}/../images/gears.gif">',
		 successCallBack : null,
		 errorCallBack : null,
		 cleanDialogs : true
		 /*TODO: AGREGAR PARAMETROS PARA MAS TIPOS DE LLAMADAS*/
	 },
	_create: function() {
		if (this.options.params == null) { this.options.params = ''; }
	},
	addParameter: function(param, value) {
		this.options.params = this.options.params + '&' + param + '=' + escape(value);
	},
	execute: function() {
		this.options.action = this.options.action + '?' + this.options.params.substr(1);
		
		/* Muestro la imagen de 'cargando' */
		if (this.options.responseContainer != '') {
			$("#" + this.options.responseContainer).html(imgLoading);
		}
		
		/*En el caso de ser una llamada que devuelve un jsp solo puede ser success o error.*/
		if (!jsonType) {	
			$.ajax({
			  async:true
			  , type: "POST"
				  , contentType: "text/html;charset=UTF-8"
			  , url: this.options.action
			  ,success: function(msg){
			  	/*TODO: APLICAR ESTILOS A JSP:ERROR*/
			  	if (this.options.responseContainer != '') {
				  	$(this).connectionManager('cleanContainer');
				  	$("#" + this.options.responseContainer).html(msg);
			  	}	
			  	if (this.options.successCallBack != null ){
					eval(this.options.successCallBack+'()');
				}
			  }
			  , timeout:15000
			  ,error: function(msg) {
			  	/* Por aca no deber�a salir nunca a menos que el servidor est� bajo */
				if (this.options.responseContainer != '') {
				  	$(this).connectionManager('cleanContainer');
				  	/*TODO: APLICAR ESTILOS DE ERROR O USAR FUTURO PLUGIN*/
				  	$("#" + this.options.responseContainer).html('El servidor se encuentra fuera de servicio.');
			  	} else {
			  		/*TODO: O JSERROR */
			  		alert('El servidor se encuentra fuera de servicio.');
			  	}
			  }
			});
		/* En el caso de ser una llamada de tipo json, termina en success o error */
		} else {
			$.ajax({
					async:true
					, type: "POST"
					, dataType: "json"
						  , contentType: "text/html;charset=UTF-8"
					, url:this.options.action
					, success: function(json){
							/*Por aca sale tanto para ok como para errores en el servicio, dao y presentaci�n, debo diferencialo por los mensajes...*/
							if (json.result.errorCode == 0) {
								/* Llamar a funcion de callback success*/
								if (this.options.successCallBack != null ){
									eval(this.options.successCallBack+'(json)');
								}
							} else {
								eval(this.options.errorCallBack+'('+json.result.errorCode+', '+json.result.errorDesc+')');
								//alert('codError: '+json.result.errorCode);
								//alert('desError: '+json.result.errorDesc);	
							}
						}
					, timeout:15000
					, error: function(msg){
							/*Por aca sale por errores en el action y por si el servidor esta bajo o algo asi...*/
							if ((msg.responseText == null || msg.responseText == '')) {
								eval(this.options.errorCallBack+'(6, El servidor se encuentra fuera de servicio)');
							} else {
								/*TODO: CAMBIAR O TERMINAR DE DEFINIR BIEN LOS CODIGOS*/
								eval(this.options.errorCallBack+'(6, '+msg.responseText+')');
							}
						 }
			       }); 
		}
		
	},
	cleanContainer: function() {
		$("#" + this.options.responseContainer).remove();
	
		var element = document.getElementById(this.options.responseContainer);
		while (element.hasChildNodes())
	        {
	          element.removeChild(element.firstChild);
	        } 
	    /*borra los dialogs creados*/
		if (this.options.cleanDialogs){
			$("#dialog-form").empty().remove();
		}
	},
	destroy: function() {
		/* TODO: PODRIA LIMPIAR HTML o algo*/
	
		$.Widget.prototype.destroy.apply( this, arguments );
	}
});

}( jQuery ));
