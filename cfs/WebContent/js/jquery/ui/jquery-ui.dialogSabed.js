 /*
 * Dialog SABED
 * JM 
 *
 */
(function( $ ) {

$.widget( "ui.dialogSabed", {
	 version: "@VERSION",
	 options: {
		// vHeight : 120,
		 vWidth : 200,
		 vText : true,
		 dialogName : 'dialogAvisoGenerico',
		 dialogMsgContainer : 'textoAvisoGenerico',
		 dialogMsg : '',
		 vTitle : 'Aviso',
		 vFunctionClose :  null,
		 vFunctionAceptar :  null,
		 vFunctionCancelar : null
	 },
	_create: function() {
		/*TODO: ACA PODRIA AGREGAR EL HTML Y LUEGO LIMPIARLO.*/
		/*$('#'+this.options.dialogMsgContainer).text(this.options.dialogMsg);*/
		//$('#'+this.options.dialogName).remove();
		if (this.options.vText) {
			this.dialogDiv = "<div id='"+this.options.dialogName+"' title='"+this.options.vTitle+"' align='center' style='display: none;'> <p class='text ui-widget-content ui-corner-all' style='text-align:center;' id='"+this.options.dialogMsgContainer+"'>"+this.options.dialogMsg+"</p></div>";
		} else {
			this.dialogDiv = "<div id='"+this.options.dialogName+"' title='"+this.options.vTitle+"' align='center' style='display: none;'/>";
		}
		
		$('body').append(this.dialogDiv);
		
		if (this.options.vText) {
			$('#'+this.options.dialogName).dialog({  
				bgiframe: true,  
				autoOpen: false,  
				//height: this.options.vHeight,  
				modal: true,  
				title: this.options.vTitle,
				open: function(event, ui) {$(".ui-dialog-titlebar-close").hide();},
				closeOnEscape: false,
				buttons: {  
		            aceptar: function() {  
		            	$('body').dialogSabed('close');  
		            	}  
		            }
			});
			this.buttons(this.options.vFunctionAceptar, this.options.vFunctionCancelar);
		} else {
			$('#'+this.options.dialogName).dialog({  
				bgiframe: true,  
				autoOpen: false,  
				//height: this.options.vHeight,  
				width: this.options.vWidth, 
				modal: true,  
				open: function(event, ui){$(".ui-dialog-titlebar-close").hide();},
				closeOnEscape: false,
				title: this.options.vTitle
			});		
			
			if (this.options.vFunctionAceptar != "" && this.options.vFunctionAceptar != null) {
				this.buttons(this.options.vFunctionAceptar, this.options.vFunctionCancelar);
			}
		}
		
	},
	buttons: function(funAceptar, funCancelar) {
		if (funCancelar != null) {
			$('#dialogAvisoGenerico').dialog("option","buttons",{"Aceptar" : function(){eval(funAceptar); $('body').dialogSabed('close'); }, "Cancelar" : function(){eval(funCancelar);  $('body').dialogSabed('close');}});

		} else {
			$('#dialogAvisoGenerico').dialog("option","buttons",{"Aceptar" : function(){eval(funAceptar); $('body').dialogSabed('close');}});
		} 
	},
	open: function() {
		$('#dialogAvisoGenerico').dialog('option', 'modal', true).dialog('open');
	},
	close: function() {
		/* TODO: PODRIA LIMPIAR HTML */
		$('#'+this.options.dialogName).dialog('close');
		eval(this.options.vFunctionClose);
		$('#'+this.options.dialogName).dialog('destroy');
		$('#'+this.options.dialogName).remove();
		$.Widget.prototype.destroy.apply( this, arguments );
		
		//this.dialogDiv.remove();
		/*$('#'+this.options.dialogName).dialog('destroy');*/
	}
});

}( jQuery ));
