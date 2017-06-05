/**
 * Funcion que permite dar formato numero a texto
 * 
 * @autor lo encontre en la web
 * 
 *  v2007-08-06
 * 
 * @param numero -> numero a transformar
 * @param decimales -> Cantidad de decimales
 * @param separador_decimal -> caracterer de separacion
 * @param separador_miles -> caracter de separacion
 * @returns -> numero transformado
 */

function formato_numero(numero, decimales, separador_decimal, separador_miles){
    numero=parseFloat(numero);
    if(isNaN(numero)){
        return "";
    }

//    if(decimales!==undefined){
//        // Redondeamos
//        numero=numero.toFixed(decimales);
//    }

    // Convertimos el punto en separador_decimal
    numero=numero.toString().replace(".", separador_decimal!==undefined ? separador_decimal : ",");

    if(separador_miles){
        // Añadimos los separadores de miles
        var miles=new RegExp("(-?[0-9]+)([0-9]{3})");
        while(miles.test(numero)) {
            numero=numero.replace(miles, "$1" + separador_miles + "$2");
        }
    }
    
    return numero;
}

/**
 * Funcion que reemplaza los puntos por vacio.
 * 
 * @autor David
 * @param x -> String a transformar
 * @returns -> String
 */
function formatoSinPuntos(x){
	//Uso un while por que el replace solo quita la primera ocurrencia
	while(x.toString().indexOf('.') != -1){
		x=x.replace('.','');
	}	
	
	return x;
}
