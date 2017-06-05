var myMessages = ['info','warning','error','success'];
function hideAllMessages() {
    var messagesHeights = new Array(); // this array will store height for each
    var i = 0;
    for (i=0; i<myMessages.length; i++) {
     messagesHeights[i] = $('.' + myMessages[i]).outerHeight(); // fill array
     $('.' + myMessages[i]).css('top', -messagesHeights[i]); //move element outside viewport     
    }
}
function showMessage(i) {
	var type = myMessages[i];
	console.log('muestra mensajes de '+type);
    $('.'+ type +'-trigger').click(function(){              
          $('.'+type).animate({top:"0"}, 500);
    });
}
function hideMessages() {
	// When message is clicked, hide it
    $('.message').click(function(){              
        $(this).animate({top: -$(this).outerHeight()}, 500);
});        
}
function recorrerMensajes(){
	var i = 0;
//	recorrer mensajes error
	$('#erroresList li').each(function(indice, elemento) {
		console.log('El elemento con el índice '+indice+' contiene '+$(elemento).text());
		$('#alertDanger').append( "<p>"+$(elemento).text()+"</p>" );
		i = 1;
    });
	if(i == 1){
		$('#alertDanger').show();
	}else{
//		recorrer mensajes success
		$('#alertDanger').hide();
		
		$('#mensajesList li').each(function(indice, elemento) {
			console.log('El elemento con el índice '+indice+' contiene '+$(elemento).text());
			$('#alertSuccess').append( "<p>"+$(elemento).text()+"</p>" );
			i = 1;
	    });
		if(i == 1){
			$('#alertSuccess').show();
		}else{
			$('#alertSuccess').hide();
			$('#warningsList li').each(function(indice, elemento) {
				console.log('El elemento con el índice '+indice+' contiene '+$(elemento).text());
				$('#alertWarning').append( "<p>"+$(elemento).text()+"</p>" );
				i = 1;
		    });
			if(i == 1){
				$('#alertWarning').show();
			}
		}
	}
}