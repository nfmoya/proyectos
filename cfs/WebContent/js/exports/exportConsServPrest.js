/**
 * @author david
 * 
 * Funcion que convierte los datos de un jqgrid en una tabla html
 * 
 * Se separo en tres por que si hay que exportar varias tablas al tener
 * varias etiquetas "<html></html>" solo imprimia la primera
 * asi que separando para luego appendear la cabecera y el final
 * se logra imprimir varias tablas al mismo tiempo 
 * 
 * @param title -> titulo de la pagina
 * @param grid -> id de la grilla
 * @param fileType -> formato a exportar
 * @returns {String} -> lo que retorna
 */ 
function exportDataGridToHtmlSP(title, grid,fileType){
	    
	 var columnNms = $('#'+grid).jqGrid('getGridParam','colNames');//Todas las columnas no header
	 var groupHeadersOptions = $('#'+grid).jqGrid('getGridParam', 'groupHeader');//Obtengo las columnas del header
	 var footerData = $('#'+grid).jqGrid('footerData','get'); //Obtengo todo el contenido del footer
	 var thead1 = "";
	 var thead2 = "";
	 var pageHead = title;
	 var hTotal = (columnNms.length-9)/4;//Cantidad de header dinamicas
	 
	     
	 /*--------------------------------------------------------------------------------------------------------*/
	 /* 1 - Armo los headers con colspan                                                                       */
	 /*--------------------------------------------------------------------------------------------------------*/
	 
	 
	 thead1 += '<th></th><th></th><th></th>';	 
	 if(hTotal > 0){
		 for(var hc=0;hc<hTotal;hc++){		 
			 var colTitle = new String(groupHeadersOptions.groupHeaders[hc].titleText);
			 thead1 +=	'<th colspan="4">'+ colTitle+ '</th>';    	
		 }	 
	 }
	 thead1 +=	'<th colspan="6">Totales</th>';   	   	 
	 thead1 = '<thead style="font-size:10px;"><tr>'+thead1+'</tr></thead>';
	 
	 /*--------------------------------------------------------------------------------------------------------*/
	 /*  2 - Armo las columnas comunes de la tabla                                                             */
	 /*--------------------------------------------------------------------------------------------------------*/
	
	 for(var cc=0;cc<columnNms.length;cc++){
		 thead2 = thead2 +'<th>'+columnNms[cc]+'</th>';
	 }
	 thead2 = '<thead style="font-size:10px;"><tr>'+thead2+'</tr></thead>';
	 
	 /*--------------------------------------------------------------------------------------------------------*/
	 /*  3 - Armado del contenido de la grilla                                                                 */
	 /*--------------------------------------------------------------------------------------------------------*/

	 var mya=new Array();
	 mya=jQuery('#'+grid).getDataIDs();  // Get All IDs
	 
	 var data=jQuery('#'+grid).getRowData(mya[0]);     // Get First row to get the labels
	 var colNames=new Array();
	 var ii=0;
	 for (var i in data){ 
		 colNames[ii++]=i;
	 }
	 
	    //Se agrega el contenido de la grilla
	    var htmlGrid = '<tbody>';
	    
	    for(i=0;i<mya.length;i++){
	    	htmlGrid+='<tr>';
	        data=jQuery('#'+grid).getRowData(mya[i]); // get each row
	        for(var j=0;j<colNames.length;j++){
	        	
	        	var datoCelda =	data[colNames[j]];//Obtengo el dato de la celda
	        	
	        	//Valida si la celda hay que convertirla a numero
	        	if(j > 3){
	        		datoCelda = formato_numero(datoCelda,2,',','');
	        	}	        	
	        	
	 	       	if(j > 2){
	 	       		htmlGrid=htmlGrid+'<td style="text-align:right">'+ datoCelda +'</td>'; // output each column as tab delimited
		        }else{	        		
		        	htmlGrid=htmlGrid+'<td>'+ datoCelda +'</td>'; // output each column as tab delimited
		       	}        	
	        }
	        	htmlGrid=htmlGrid+'</tr>';  // output each row with end of line	 
	       }
	    
	    htmlGrid=htmlGrid+'</tbody>';
	    
	    
	 /*--------------------------------------------------------------------------------------------------------*/
	 /*  4 - Armado del footer de la grilla                                                                    */
	 /*--------------------------------------------------------------------------------------------------------*/
	  
		var footHtml = '<tfoot><tr>';		
		for(var j=0;j<colNames.length;j++){
			footHtml+='<td></td>';		
		}
		
		footHtml +='</tr><tr>';//Linea en blanco
		
		for(var j=0;j<colNames.length;j++){
			
			var datoFoot =	footerData[colNames[j]];//Obtengo el dato de la celda
			
			//Valida si la celda hay que convertirla a numero
        	if(j > 2){
        		datoFoot = formatoSinPuntos(datoFoot);        		
        	}  
			
			if( j > 0) 
				footHtml+='<td  style="text-align:right">' + datoFoot + '</td>';
			else
				footHtml+='<td>' + datoFoot + '</td>';	
		}
		
		footHtml +='</tr></tfoot>';
		
	/*--------------------------------------------------------------------------------------------------------*/
	 /*  5 - Armado del todo el cotenido                                                                      */
	 /*--------------------------------------------------------------------------------------------------------*/
		
		var htmlFilter = getFiltrosToHtmlSP('Filtros de Busqueda');
		
		var html='<div><h3 style="text-align: center;">'+pageHead+'</h3></div>'+	    
 		
		htmlFilter +
		'<table border="1" cellspacing="0" cellpadding="0"' +
		' style="width: 100%; font-size: 8px;">'+
  		thead1 +
  		thead2 +
		htmlGrid +
		footHtml +
	    '</table>';	 
		
		$('#nameFile').val('Exportacion de Servicios Prestados');
		$('#html').val(createHtmlToExport(html));
		$('#fileType').val(fileType);		
		$('#exportForm').get(0).submit();
}

/**
 * @autor David XA50126
 * 
 * Funcion que genera un string con formato html de los filtros usados en la pantalla
 * para exportar junto con la grilla
 * 
 * @param titleFilter -> Titulo del filtro
 * @returns {String}
 */
function getFiltrosToHtmlSP(titleFilter){
	
   //Armado de los filtros

   var gridEnc = '<table><tr>';
   
   //'<caption >'+ titleFilter +'</caption><tr>'+
   gridEnc += 	'<td></td><td colspan="4" style="text-align: center; font-size: 8px;">'+ titleFilter +'</td></tr><tr><td>&nbsp;</td></tr>'+
              	'<tr><td colspan="2">Proveedor</td>'+
              	'<td colspan="14">'+ $('#selectedProveedor option:selected').text() +'</td>'+
              	'</tr>';
   gridEnc += 	'<tr>'+
              	'<td colspan="2">Sector</td>'+
              	'<td colspan="14">'+ $('#selectedSector option:selected').text() +'</td>'+
              	'</tr>';
   gridEnc += 	'<tr>'+
              	'<td colspan="2">Grupo de Productos</td>'+
              	'<td colspan="14">'+ $('#selectedGrupo option:selected').text() +'</td>'+
              	'</tr>';
   gridEnc += 	'<tr>'+
			 	'<td colspan="2">Producto</td>'+
			 	'<td colspan="14">'+ $('#selectedProducto option:selected').text() +'</td>'+
			 	'</tr>';
   gridEnc += 	'<tr>'+
              	'<td colspan="2">Unidad Valoracion</td>'+
              	'<td colspan="14">'+ $('#nbUniVal').val() +'</td>'+
              	'</tr>';
   gridEnc += 	'<tr>'+
              	'<td colspan="2">Fecha Remito desde</td>'+
              	'<td colspan="14" align="left">'+$('#fhDesde').val() +'</td>'+
              	'</tr>';
   gridEnc += 	'<tr>'+
              	'<td colspan="2">Fecha Remito hasta</td>'+
              	'<td colspan="14">'+ $('#fhHasta').val() +'</td>'+
              	'</tr>';
   gridEnc += 	'<tr>'+
				'<td colspan="2">Remito Desde</td>'+
				'<td colspan="14">'+ $('#remitoDesde').val() +'</td>'+
				'</tr>';
   gridEnc += 	'<tr>'+
				'<td colspan="2">Remito Hasta</td>'+
				'<td colspan="14">'+ $('#remitoHasta').val() +'</td>'+
				'</tr>';
   gridEnc += 	'<tr>'+
				'<td colspan="2">Estado Detalle</td>'+
				'<td colspan="14">'+ $('#stLoteDet option:selected').text() +'</td>'+
				'</tr>';
   gridEnc += 	'<tr>'+
				'<td colspan="2">Nro Lote</td>'+
				'<td colspan="14">'+ $('#cdLote').val() +'</td>'+
				'</tr>';
				
   gridEnc += 	'<tr><td colspan="16"></td></tr>';
   gridEnc += 	'</table><br/><br/><br/>';
   
   return  gridEnc; 
}

/**
 * @autor XA50126
 * 
 * Metodo que retorna el codigo html completo para enviar a exportar
 * uniendo el header con la/s tabla/s que se le pasen mas los tag de 
 * cierre de la pagina html
 * 
 * @param tbl
 * @returns
 */
function createHtmlToExport(tbl){
	 
	return (getHeaderHtml() + tbl + getFootHtml());	
}

/**
 * @author XA50126
 * 
 * Metodo que retorna los tag iniciales del html, con los estilos
 * 
 * @returns {String}
 */
function getHeaderHtml(){
	return (			
	    	'<html>' +
	    	  '<head>' +
	    	  	'<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />' +
	    	  	'<style TYPE="text/css">td {font-family: Arial; font-size: 8px;}</style>'+
			  '</head>'+		
			  '<body>'			  
	);	
}

/**
 * @author XA50126
 * 
 * Metodo que retorna los tags del final de las etiquetas html
 * 
 * @returns {String}
 */
function getFootHtml(){	
	return 	('</body></html>');	
}