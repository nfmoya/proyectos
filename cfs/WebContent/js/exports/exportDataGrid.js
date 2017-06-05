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
 * @param cols -> columnas excluidas
 * @param title -> titulo de la pagina
 * @param grid -> id de la grilla
 * @param celNums -> Columnas a transformar en numeros
 * @returns {String} -> lo que retorna
 */ 
function exportDataGridToHtml(cols,title, grid, celNums) {
	
	 var celFormat = new Array();
	 celFormat = celNums.split(",");
	 
	 var columns    = cols.toString().split(",");
	 var columnNms  = $('#'+grid).jqGrid('getGridParam','colNames');
	 var footerData = $('#'+grid).jqGrid('footerData','get');
     var columnMdl  = $('#'+grid).jqGrid("getGridParam", "colModel");
 
	 var theads = "";
	 var pageHead = title;

	 for (var cc=0;cc<columnNms.length;cc++) {
        var isAdd = true;
        if (cols != '') {
           for (var p=0;p<columns.length;p++) {
              if (cc==columns[p]) {
                 isAdd = false;
                 break;
              }
           }
        }
        if (isAdd) {
           theads = theads +'<th style="width:'+ columnMdl[cc].width + ';">'+columnNms[cc]+'</th>';
        }
     }
	 
	 theads = '<tr style="font-size:10px;">'+theads+'</tr>';
	 
	 //Insercion de datos
	 var mya=new Array();
	 mya=jQuery('#'+grid).getDataIDs();  // Get All IDs
	 
	 var data=jQuery('#'+grid).getRowData(mya[0]);     // Get First row to get the labels
	 var colNames=new Array();
	 var ii=0;
	 for (var i in data){ 
		 colNames[ii++]=i;
	 }
	 
	 //Se agrega el contenido de la grilla
	 var htmlGrid = '';
	    
	 for (i=0;i<mya.length;i++) {
	  	htmlGrid+='<tr style="font-size:7">';
	    data=jQuery('#'+grid).getRowData(mya[i]); // get each row
	    for (var j=0;j<colNames.length;j++) {
	       var isjAdd = true;	         
	       var datoCelda = $.trim(data[colNames[j]]);
	       if (datoCelda == null ||  datoCelda == 'undefined' || datoCelda === "") {
	          datoCelda = '&nbsp;';
	       }
	            	
	       if (cols != '') {
		      for (var pj=0;pj<columns.length;pj++) {
                 if (j==columns[pj]) {
		            isjAdd = false;
		            break;
		         }
		      }
	       }
	       if (isjAdd) {
//              if (isNaN(datoCelda))
	    	 //Si la columna esta entre las que hay que convertir
	    	  if (celFormat != '' && celFormat.indexOf(j.toString()) != -1){
	    		  datoCelda = formato_numero(datoCelda,2,',','');
	    		  htmlGrid=htmlGrid+'<td>'+datoCelda+'</td>'; // output each column as tab delimited
	    	  }else
  	             htmlGrid=htmlGrid+'<td style="text-align:right">'+datoCelda+'</td>'; // output each column as tab delimited
	       }
	    }
	    htmlGrid=htmlGrid+'</tr>';  // output each row with end of line
	 }

	 var footHtml = '<tfoot>';
	 if ((jQuery('#'+grid).jqGrid("getGridParam", "footerrow") == true) &&
          (!(footerData[colNames[0]] == null || footerData[colNames[0]] === 'undefined'))) {
        footHtml+='<tr>';		

	    for (var j=0;j<colNames.length;j++){
	 	   footHtml+='<td></td>';		
	    }

	    footHtml +='</tr><tr style="font-size:7">';
	    for (var j=0;j<colNames.length;j++){
	 	   if ( j > 0) 
	          footHtml+='<td  style="text-align:right">' + footerData[colNames[j]] + '</td>';
	 	   else
	 	      footHtml+='<td>' + footerData[colNames[j]] + '</td>';	
	    }
 	    footHtml+='</tr>';
	 }
	 
	 footHtml +='</tfoot>';
	 
	 var html = '<div><h3 style="text-align:center">'+pageHead+'</h3></div>'+		      
                '<table border="1" cellspacing="0" cellpadding="0" style="width: 100%;">'+
			      	'<thead>'+ 
				  		theads +
				  	'</thead>' +
					'<tbody>'+
						htmlGrid + 
				    '</tbody>'+ 
				    	footHtml +
			    '</table>';

     return html;
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
 * @autor David
 * 
 * Funcion que lo que hace es crear una tabla html en base a los parametros que
 * se le pasan al action.
 * 
 * @param title
 * @param nameFilters
 * @param pageHead
 * @param params
 * @returns {String}
 */
function exportDataFilterToHtml(title, nameFilters,pageHead, params){
    
	 var filters = getFiltersToString(params);
	 var columnFilters = nameFilters.toString().split(",");
	 var htmlFilters = "";
	 
	 for(var cc=0;cc<columnFilters.length;cc++){
		 htmlFilters = htmlFilters +'<tr><td colspan="3">'+columnFilters[cc]+'</td>'+
	    '<td colspan="8" align="left">'+ filters[cc] +'</td></tr>';
	 }
	 
//	 theads = '<tr style="font-size:10px;">'+theads+'</tr>';	 
	 //Insercion de datos Se agrega el contenido de la grilla
//   var htmlFilters = '<tr>';
//   for(var i=0;i<filters.length;i++){
//	   htmlFilters=htmlFilters+'<td>'+filters[i]+'</td>'; // output each column as tab delimited
//   }   
//   htmlFilters=htmlFilters+'</tr>';  // output each row with end of line

   var html='<div><h3 style="text-align: center;">'+pageHead+'</h3></div>'+
	   		'<table style="font-size:8px;"><tr>'+
   			'<td>&nbsp;</td><td colspan="4">'+ title +'</td></tr><tr><td >&nbsp;</td><td colspan="4">&nbsp;</td></tr>'+
				htmlFilters +
		    '</table>';	    
   return html;
}

/**
 * @autor XA50126
 * 
 * Funcion que retorna los datos parseados para crear el html
 * 
 * @param f
 * @returns {List} -> lista de parametros
 */
function getFiltersToString(f){
	
	var vec = f.toString().split(";");
	var lstParam = new Array();

	vec.forEach(function(entry) {
   	var data = entry.toString();

   	if(data == null || data == '0' || data == 'undefined' || (data.indexOf('Seleccione') > -1)){
   		lstParam.push('Todos');
   	}else{
   		if($.trim(data) == ''){
   	   		lstParam.push('&nbsp;');
   		}else{
   			lstParam.push($.trim(data));
   		}
   	}
	});

	return lstParam;
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
			  '<head> ' +
			  	'<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />' +
//			    ' <style type="css/text" media="all">'+
//			    '<style TYPE="text/css">td {font-family: Arial; font-size: 8px;}</style>'+
//			    ' table.tableList th {border:1px solid black;border-bottom:2px solid black;text-align:center;vertical-align: middle;padding:5px;font-family: Arial; font-size: 8px;}  '+
//			    ' table.tableList td {border:1px solid black;text-align: left;vertical-align: top;padding:5px;font-family: Arial; font-size: 8px;}'+
//			    ' </style>'+
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