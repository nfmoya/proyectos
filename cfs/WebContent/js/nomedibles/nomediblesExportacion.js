/*--------------------------------------------------------------------------------------------------------*/
// EXPORTACION A PDF
/*--------------------------------------------------------------------------------------------------------*/
function exportGridPDF(){
    var html='<html>' +
	         '<head>' +
	  	     '<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />' +
	  	     '<style TYPE="text/css">td {font-family: Arial; font-size: 7px;}</style>'+
	         '</head>'+
	         '<body>';

    var gridEnc = '<table style="font-family:courier new;font-size:14px;">';
    gridEnc += '<tr>'+
               '<td colspan="2">Proveedor</td>'+
               '<td colspan="15">'+ $('#filtroProveedorList option:selected').text() +'</td>'+
               '</tr>';
    gridEnc += '<tr>'+
               '<td colspan="2">Sector</td>'+
               '<td colspan="15">'+ $('#filtroSectorList option:selected').text() +'</td>'+
               '</tr>';
    gridEnc += '<tr>'+
               '<td colspan="2">Periodo</td>'+
               '<td colspan="15">'+ $('#filtroPeriodoList option:selected').text() +'</td>'+
               '</tr>';
    gridEnc += '<tr>'+
               '<td colspan="2">Estado Conciliacion</td>'+
               '<td colspan="15">'+ $('#filtroSituacionConciliacionList option:selected').text() +'</td>'+
               '</tr>';
    gridEnc += '<tr>'+
               '<td colspan="2">Nro Conciliacion</td>'+
               '<td colspan="15" align="left">'+ $('#cdConciliacion').val() +'</td>'+
               '</tr>';
    gridEnc += '<tr><td colspan="17"></td></tr>';
    gridEnc += '</table>';
	
    // CONCILIACION
	var sumCtServFactAnt    = 0;
    var sumImPrecioTotalAnt = 0;
    var sumCtServFactAct    = 0;
    var sumImPrecioTotalAct = 0;
    var sumImDiferencia     = 0;
    var sumDifDesvio        = 0;	
    var gridCon = '<table style="width: 100%;font-family:courier new;font-size:7px;">';
    gridCon += 	'<tr><td colspan="17" align="center"><h3>CONCILIACION NO MEDIBLES</h3></td></tr>';
    gridCon += 	'<tr><td colspan="17" align="center">----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</td></tr>';
    gridCon += 	'<tr>'+
				'<td colspan="6" align="center">Periodo Anterior</td>'+
				'<td align="center">|</td>' +
				'<td colspan="6" align="center">'+ $('#filtroPeriodoList option:selected').text() +'</td>'+
				'<td align="center">|</td>' +
				'<td colspan="3" align="center">Diferencias</td>'+
				'</tr>';

    gridCon +=  '<tr>'+
				'<td width=" 6%" align="right">Producto</td>'+
				'<td width=" 6%" align="center">Periodo</td>'+
				'<td width=" 6%" align="center">Cant Fact</td>'+
				'<td width=" 6%" align="right">Unidad</td>'+
				'<td width=" 8%" align="right">Val Fact</td>'+
				'<td width=" 2%" align="center">Concilia</td>'+
				'<td width=" 1%" align="center">|</td>' +
				'<td width=" 2%" align="center">Cant Fact</td>'+
				'<td width=" 5%" align="right">Unidad</td>'+
				'<td width=" 2%" align="center">Val Fact</td>'+
				'<td width=" 5%" align="right">% Desvio Adm</td>'+
				'<td width=" 5%" align="center">Valor % Desvio</td>'+
				'<td width=" 8%" align="right">Conc Fact</td>'+
				'<td width=" 1%" align="center">|</td>' +
				'<td width=" 6%" align="right">Diferencia</td>'+
				'<td width="10%" align="right">Observaciones</td>'+
				'<td width=" 3%" align="right">Solucion</td>'+
				'</tr>';
	gridCon += '<tr><td colspan="17" align="center">----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</td></tr>';
	var rows = $('#gridConciliacionesId').jqGrid('getRowData');
	for (var i = 0; i < rows.length; i++) {
		var row = rows[i];
		gridCon +=  '<tr>';
		gridCon +=  '<td align="right">'+pad(row['cdProducto'],15, ' ', 1) + '</td>' +
					'<td align="right">'+pad(row['cdPeriodoAnt'],10, ' ', 1) + '</td>' +
					'<td align="right">'+row['ctServFactAnt'] + '</td>' +
					'<td align="center">'+row['cdUniValAnt'] + '</td>' +
					'<td align="right">'+row['imPrecioTotalAnt'] + '</td>' +
					'<td align="center">'+pad(row['cdConciliacionAnt'], 12, ' ', 3) + '</td>' +
					'<td align="center">|</td>' +
					'<td align="right">'+row['ctServFactAct'] + '</td>' +
					'<td align="center">'+row['cdUniValAct'] + '</td>' +
					'<td align="right">'+row['imPrecioTotalAct'] + '</td>' +
					'<td align="right">'+row['nuPorcVarMax'] + '</td>' +
					'<td align="right">'+row['nuPorcVarVal'] + '</td>' +
					'<td align="center">'+pad(row['cdConciliacionAct'], 12, ' ', 3) + '</td>' +
					'<td align="center">|</td>' +
					'<td align="right">'+row['imDiferencia'] + '</td>' +
					'<td align="left">'+row['nbObservaciones'] + '</td>' +
					'<td align="left">'+row['tpSolucion'] + '</td>';
		gridCon += '</tr>';		
		sumCtServFactAnt    += Number(removeCommas(row['ctServFactAnt']));
		sumImPrecioTotalAnt += Number(removeCommas(row['imPrecioTotalAnt']));  
		sumCtServFactAct    += Number(removeCommas(row['ctServFactAct']));
		sumImPrecioTotalAct += Number(removeCommas(row['imPrecioTotalAct']));
        sumImDiferencia     += Number(removeCommas(row['imDiferencia']));	
    }
	sumDifDesvio = $('#difDesvio').val();
    // IMPRIME EL TOTAL DE LA CONCILIACION    
    gridCon +=	'<tr><td colspan="17" align="center">----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</td></tr>';
    gridCon += 	'<tr>';
    gridCon += 	'<td></td>' +
				'<td></td>' +
				'<td align="right">'+pad(convierteNumero(sumCtServFactAnt,0), 9, ' ', 1) + '</td>' +
				'<td></td>' +
				'<td align="right">'+pad(convierteNumero(sumImPrecioTotalAnt,2),12, ' ', 1) + '</td>' +
				'<td></td>' +
				'<td align="center">|</td>' +
				'<td align="right">'+pad(convierteNumero(sumCtServFactAct,0), 9, ' ', 1) + '</td>' +
				'<td></td>' +
				'<td align="right">'+pad(convierteNumero(sumImPrecioTotalAct,2),12, ' ', 1) + '</td>' +
				'<td></td>' +
				'<td></td>' +
				'<td></td>' +
				'<td align="center">|</td>' +
				'<td align="right">'+pad(convierteNumero(sumImDiferencia,2),12, ' ', 1) + '</td>' + 
				'<td></td>' +
				'<td></td>';
    gridCon += 	'</tr>';
    gridCon += 	'<tr><td colspan="17"></td></tr>';
    gridCon += 	'<tr>';
    gridCon += 	'<td colspan="12"></td>' +
				'<td colspan="2">Desvio de Diferencias</td>' +
				'<td align="right">'+sumDifDesvio+'</td>' + 
				'<td colspan="2"></td>';
    gridCon += 	'</tr>';
    gridCon += 	'<tr><td colspan="17" align="center">----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</td></tr>';
    gridCon += 	'</table>';
	
    html +=  gridEnc + gridCon + 
           '</body>'+
           '</html>'; 
		   
    // Establecemos la nueva ventana.
   	var title = "Conc_" + ($('#cdProveedor').val()).trim() + "_" + 
                          ($('#cdSector').val()).trim() + "_" +
                          ($('#cdPeriodo').val()).trim();
   	
    var createdAt = new Date(); 
    var windowName = title + '_' + createdAt.getTime(); 
   	
    $('#nameFile').val(windowName);
    $('#html').val(html);
    $('#fileType').val('pdf');		
    $('#filePath').val('C:\\temp\\');		
    $('#exportForm').get(0).submit();
}

/*--------------------------------------------------------------------------------------------------------*/
// EXPORTACION A EXCEL
/*--------------------------------------------------------------------------------------------------------*/
function exportGridXLS(){
	var archivo = "Conc_" + ($('#cdProveedor').val()).trim() + "_" + 
                            ($('#cdSector').val()).trim() + "_" +
                            ($('#cdPeriodo').val()).trim();
	var html =	'<html>' +
				'<head> ' +
				'   <style script="css/text" media="all">'+
				'     table.tableList th {border:1px solid black;border-bottom:2px solid black;text-align:center;vertical-align: middle;padding:5px;background-color:#c5dbec !important;font-style:italic;-webkit-print-color-adjust: exact; }  '+
				'     table.tableList td {border:1px solid black;text-align: left;vertical-align: top;padding:5px;}'+
				'   </style>'+
				'</head>'+		
				'<body>';

	var gridEnc = '<table>';
	gridEnc += 	'<tr>'+
				'<td colspan="2">Proveedor</td>'+
				'<td colspan="13">'+ $('#filtroProveedorList option:selected').text() +'</td>'+
				'</tr>';
	gridEnc += 	'<tr>'+
				'<td colspan="2">Sector</td>'+
				'<td colspan="13">'+ $('#filtroSectorList option:selected').text() +'</td>'+
				'</tr>';
	gridEnc += 	'<tr>'+
				'<td colspan="2">Periodo</td>'+
				'<td colspan="13">'+ $('#filtroPeriodoList option:selected').text() +'</td>'+
				'</tr>';
	gridEnc += 	'<tr>'+
				'<td colspan="2">Estado Conciliacion</td>'+
				'<td colspan="13">'+ $('#filtroSituacionConciliacionList option:selected').text() +'</td>'+
				'</tr>';
	gridEnc += 	'<tr>'+
				'<td colspan="2">Nro Conciliacion</td>'+
				'<td colspan="13" align="left">'+ $('#cdConciliacion').val() +'</td>'+
				'</tr>';
	gridEnc += 	'<tr><td colspan="15"><td></tr>';
	gridEnc += 	'</table>';
   
    // CONCILIACION
	var sumCtServFactAnt    = 0;
    var sumImPrecioTotalAnt = 0;
    var sumCtServFactAct    = 0;
    var sumImPrecioTotalAct = 0;
    var sumImDiferencia     = 0;
    var sumDifDesvio        = 0;	
    var gridCon = '<table style="width: 100%;font-family:courier new;font-size:10px;">';
    gridCon += 	'<tr><td colspan="15" align="center"><h3>CONCILIACION NO MEDIBLES</h3></td></tr>';
    gridCon += 	'<tr>'+
				'<td colspan="6" align="center">Periodo Anterior</td>'+
				'<td colspan="6" align="center">'+ $('#filtroPeriodoList option:selected').text() +'</td>'+
				'<td colspan="3" align="center">Diferencias</td>'+
				'</tr>';

    gridCon +=  '<tr>'+
				'<td align="right">Producto</td>'+
				'<td align="center">Periodo</td>'+
				'<td align="center">Cant Fact</td>'+
				'<td align="right">Unidad</td>'+
				'<td align="right">Val Fact</td>'+
				'<td align="center">Concilia</td>'+
				'<td align="center">Cant Fact</td>'+
				'<td align="right">Unidad</td>'+
				'<td align="center">Val Fact</td>'+
				'<td align="right">% Desvio Adm</td>'+
				'<td align="center">Valor % Desvio</td>'+
				'<td align="right">Conc Fact</td>'+
				'<td align="right">Diferencia</td>'+
				'<td align="right">Observaciones</td>'+
				'<td align="right">Solucion</td>'+
				'</tr>';
	gridCon += '<tr><td colspan="20" align="center">----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</td></tr>';

	var rows = $('#gridConciliacionesId').jqGrid('getRowData');
	for (var i = 0; i < rows.length; i++) {
		var row = rows[i];
		gridCon +=  '<tr>';
		gridCon +=  '<td align="right">'+pad(row['cdProducto'],15, ' ', 1) + '</td>' +
					'<td align="right">'+pad(row['cdPeriodoAnt'],10, ' ', 1) + '</td>' +
					'<td align="right">'+formatoSinPuntos(row['ctServFactAnt']) + '</td>' +
					'<td align="center">'+row['cdUniValAnt'] + '</td>' +
					'<td align="right">'+formatoSinPuntos(row['imPrecioTotalAnt']) + '</td>' +
					'<td align="center">'+pad(row['cdConciliacionAnt'], 12, ' ', 3) + '</td>' +
					'<td align="right">'+formatoSinPuntos(row['ctServFactAct']) + '</td>' +
					'<td align="center">'+row['cdUniValAct'] + '</td>' +
					'<td align="right">'+formatoSinPuntos(row['imPrecioTotalAct']) + '</td>' +
					'<td align="right">'+formatoSinPuntos(row['nuPorcVarMax']) + '</td>' +
					'<td align="right">'+formatoSinPuntos(row['nuPorcVarVal']) + '</td>' +
					'<td align="center">'+pad(row['cdConciliacionAct'], 12, ' ', 3) + '</td>' +
					'<td align="right">'+formatoSinPuntos(row['imDiferencia']) + '</td>' +
					'<td align="left">'+row['nbObservaciones'] + '</td>' +
					'<td align="left">'+row['tpSolucion'] + '</td>';
		gridCon += '</tr>';
		
		sumCtServFactAnt    += Number(removeCommas(row['ctServFactAnt']));
		sumImPrecioTotalAnt += Number(removeCommas(row['imPrecioTotalAnt']));  
		sumCtServFactAct    += Number(removeCommas(row['ctServFactAct']));
		sumImPrecioTotalAct += Number(removeCommas(row['imPrecioTotalAct']));
        sumImDiferencia     += Number(removeCommas(row['imDiferencia']));	
    }	
	sumDifDesvio = $('#difDesvio').val();

    // IMPRIME EL TOTAL DE LA CONCILIACION    
    gridCon += 	'<tr>';
    gridCon += 	'<td></td>' +
				'<td></td>' +
				'<td align="right">'+formato_numero(sumCtServFactAnt,2,',','')+'</td>' +
				'<td></td>' +
				'<td align="right">'+formato_numero(sumImPrecioTotalAnt,2,',','')+'</td>' +
				'<td></td>' +
				'<td align="right">'+formato_numero(sumCtServFactAct,2,',','')+'</td>' +
				'<td></td>' +
				'<td align="right">'+formato_numero(sumImPrecioTotalAct,2,',','')+'</td>' +
				'<td></td>' +
				'<td></td>' +
				'<td></td>' +
				'<td align="right">'+formato_numero(sumImDiferencia,2,',','')+'</td>' + 
				'<td></td>' +
				'<td></td>';
    gridCon += 	'</tr>';	
    gridCon += 	'<tr><td colspan="15"></td></tr>';
	
    gridCon += 	'<tr>';
    gridCon += 	'<td colspan="10"></td>' +
				'<td colspan="2">Desvio de Diferencias</td>' +
				'<td align="right">'+sumDifDesvio+'</td>' + 
				'<td colspan="2"></td>';
    gridCon += 	'</tr>';	
	
    gridCon += 	'</table>';
   
	html +=  	gridEnc + gridCon +
				'</body>'+
				'</html>'; 
	$('#nameFile').val(archivo);
	$('#html').val(html);
	$('#fileType').val('xls');		
	$('#exportForm').get(0).submit();
}
