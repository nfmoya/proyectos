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
               '<td colspan="16">'+ $('#filtroProveedorList option:selected').text() +'</td>'+
               '</tr>';
    gridEnc += '<tr>'+
               '<td colspan="2">Sector</td>'+
               '<td colspan="16">'+ $('#filtroSectorList option:selected').text() +'</td>'+
               '</tr>';
    gridEnc += '<tr>'+
               '<td colspan="2">Periodo</td>'+
               '<td colspan="16">'+ $('#filtroPeriodoList option:selected').text() +'</td>'+
               '</tr>';
    gridEnc += '<tr>'+
               '<td colspan="2">Producto</td>'+
               '<td colspan="16">'+ $('#filtroProductoList option:selected').text() +'</td>'+
               '</tr>';
    gridEnc += '<tr>'+
               '<td colspan="2">Estado Conciliacion</td>'+
               '<td colspan="16">'+ $('#filtroSituacionConciliacionList option:selected').text() +'</td>'+
               '</tr>';
    gridEnc += '<tr>'+
               '<td colspan="2">Fecha Remito Desde</td>'+
               '<td colspan="16" align="left">'+ $('#fhRemitoDesde').val() +'</td>'+
               '</tr>';
    gridEnc += '<tr>'+
               '<td colspan="2">Fecha Remito Hasta</td>'+
               '<td colspan="16" align="left">'+ $('#fhRemitoHasta').val() +'</td>'+
               '</tr>';
    gridEnc += '<tr>'+
               '<td colspan="2">Fecha Fin Serv Desde</td>'+
               '<td colspan="16" align="left">'+ $('#fhFinServicioDesde').val() +'</td>'+
               '</tr>';
    gridEnc += '<tr>'+
               '<td colspan="2">Fecha Fin Serv Hasta</td>'+
               '<td colspan="16" align="left">'+ $('#fhFinServicioHasta').val() +'</td>'+
               '</tr>';
    gridEnc += '<tr>'+
               '<td colspan="2">Nro Conciliacion</td>'+
               '<td colspan="16" align="left">'+ $('#cdConciliacion').val() +'</td>'+
               '</tr>';
    gridEnc += '<tr>'+
               '<td colspan="2">Ignora Valores</td>'+
               '<td colspan="16">'+ ($('#stIgnoraVal').is(':checked') ? 'SI' : 'NO') +'</td>'+
               '</tr>';
    gridEnc += '<tr><td colspan="18"></td></tr>';
    gridEnc += '</table>';
  
    // CONCILIACION
    var sumCtServPrest       = 0;
    var sumImPrecioTotalPres = 0;
    var sumCtServFact        = 0;
    var sumImPrecioTotalFact = 0;
    var sumCtServFactDife    = 0;
    var sumImServFactDife    = 0;
    var gridCon = '<table style="width: 100%;font-family:courier new;font-size:7px;">';
    gridCon += '<tr><td colspan="20" align="center"><h3>CONCILIACION</h3></td></tr>';
    gridCon += '<tr><td colspan="20" align="center">----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</td></tr>';
    gridCon += '<tr>'+
             '<td colspan="9" align="center">Servicios Prestados</td>'+
             '<td align="center">|</td>' +
             '<td colspan="7" align="center">Servicios Facturados</td>'+
             '<td align="center">|</td>' +
             '<td colspan="2" align="center">Diferencias</td>'+
             '</tr>';
    gridCon += '<tr>'+
             '<td width=" 6%" align="right">Rem.Serv</td>'+
             '<td width=" 6%" align="center">Fec.Remito</td>'+
             '<td width=" 6%" align="center">F.Fin Serv</td>'+
             '<td width=" 6%" align="right">Cant.Serv</td>'+
             '<td width=" 8%" align="right">Importe.Serv</td>'+
             '<td width=" 2%" align="center">T.Val</td>'+
             '<td width=" 2%" align="center">Mon</td>'+
             '<td width=" 5%" align="right">Conc Serv</td>'+
             '<td width=" 2%" align="center">Sel</td>'+
             '<td width=" 1%" align="center">|</td>' +
             '<td width=" 5%" align="right">Rem.Fact</td>'+
             '<td width=" 5%" align="center">Tp.Cb.</td>'+
             '<td width=" 8%" align="right">Nro Comprob</td>'+
             '<td width=" 6%" align="right">Cant.Fact</td>'+
             '<td width=" 8%" align="right">Importe.Fact</td>'+
             '<td width=" 5%" align="right">Conc Fact</td>'+
             '<td width=" 2%" align="center">Sel</td>'+
             '<td width=" 1%" align="center">|</td>' +
             '<td width=" 6%" align="right">Cant.Dife</td>'+
             '<td width=" 8%" align="right">Importe.Dife</td>'+
             '</tr>';
    gridCon += '<tr><td colspan="20" align="center">----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</td></tr>';

    var rows = $('#gridConciliacionesId').jqGrid('getRowData');
    for (var i = 0; i < rows.length; i++) {
      var row = rows[i];

      gridCon += '<tr>';
      gridCon += '<td align="right">'+pad(row['cdRemitoPres'],15, ' ', 1) + '</td>' +
                 '<td align="center">'+(row['fhRemito'].trim() == '' ? '' : pad(row['fhRemito'],15, ' ', 3)) + '</td>' +
                 '<td align="right">'+(row['fhFinServ'].trim() == '' ? '' : pad(row['fhFinServ'],15, ' ', 3)) + '</td>' +
                 '<td align="right">'+row['ctServPrest'] + '</td>' +
                 '<td align="right">'+row['imPrecioTotalPres'] + '</td>' +
                 '<td align="center">'+row['cdTipVal'] + '</td>' +
                 '<td align="center">'+row['cdMoneda'] + '</td>' +
                 '<td align="center">'+pad(row['cdConciliacionPres'], 12, ' ', 3) + '</td>' +
                 '<td align="center">'+(row['chkPres'] == 1 ? 'X' : ' ') + '</td>' +
                 '<td align="center">|</td>' +
                 '<td align="right">'+pad(row['cdRemitoFact'],15, ' ', 1) + '</td>' +
                 '<td align="center">'+pad(row['tpComprobante'], 5, ' ', 3) + '</td>' +
                 '<td align="right">'+pad(row['nuComprobante'],18, ' ', 1) + '</td>' +
                 '<td align="right">'+row['ctServFact'] + '</td>' +
                 '<td align="right">'+row['imPrecioTotalFact'] + '</td>' +
                 '<td align="center">'+pad(row['cdConciliacionFact'], 12, ' ', 3) + '</td>' +
                 '<td align="center">'+(row['chkFact'] == 1 ? 'X' : ' ') + '</td>' +
                 '<td align="center">|</td>' +
                 '<td align="right">'+convierteNumero(Number(row['ctServFactDife']),0) + '</td>' +
                 '<td align="right">'+convierteNumero(Number(row['imServFactDife']),2) + '</td>';
      gridCon += '</tr>';

      if (row['chkPres'] == 1) {
         sumCtServPrest       += Number(removeCommas(row['ctServPrest']));
         sumImPrecioTotalPres += Number(removeCommas(row['imPrecioTotalPres']));
      }
      if (row['chkFact'] == 1) {
         sumCtServFact        += Number(removeCommas(row['ctServFact']));
         sumImPrecioTotalFact += Number(removeCommas(row['imPrecioTotalFact']));
      }
//    sumCtServFactDife    += Number(row['ctServFactDife']);
//    sumImServFactDife    += Number(row['imServFactDife']);
    }
    sumCtServFactDife = sumCtServFact - sumCtServPrest;
    sumImServFactDife = sumImPrecioTotalFact - sumImPrecioTotalPres;
    
    // IMPRIME EL TOTAL DE LA CONCILIACION    
    gridCon += '<tr><td colspan="20" align="center">----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</td></tr>';
    gridCon += '<tr>';
    gridCon += '<td></td>' +
             '<td></td>' +
             '<td></td>' +
             '<td align="right">'+pad(convierteNumero(sumCtServPrest,0), 9, ' ', 1) + '</td>' +
             '<td align="right">'+pad(convierteNumero(sumImPrecioTotalPres,2),12, ' ', 1) + '</td>' +
             '<td></td>' +
             '<td></td>' +
             '<td></td>' +
             '<td></td>' +
             '<td></td>' +
             '<td></td>' +
             '<td></td>' +
             '<td></td>' +
             '<td align="right">'+pad(convierteNumero(sumCtServFact,0), 9, ' ', 1) + '</td>' +
             '<td align="right">'+pad(convierteNumero(sumImPrecioTotalFact,2),12, ' ', 1) + '</td>' +
             '<td></td>' +
             '<td></td>' +
             '<td></td>' +
             '<td align="right">'+pad(convierteNumero(sumCtServFactDife,0), 9, ' ', 1) + '</td>' +
             '<td align="right">'+pad(convierteNumero(sumImServFactDife,2),12, ' ', 1) + '</td>';
    gridCon += '</tr>';
    gridCon += '<tr><td colspan="20" align="center">----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</td></tr>';
    gridCon += '<tr><td colspan="20"></td></tr>';

    // DIFERENCIAS
    var sumCtDiferencia = 0;
    var sumImPrecioTot  = 0;
  
    gridCon += '<tr><td colspan="20" align="center"><h3>DIFERENCIAS</h3></td></tr>';
    gridCon += '<tr><td colspan="20" align="center">----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</td></tr>';
    gridCon += '<tr>'+
             '<td>Nro Dif</td>'+
             '<td colspan="9">Observaciones</td>'+
             '<td>Sit.Solucion</td>'+
             '<td>Estado</td>'+
             '<td>Nro.Remito</td>'+
             '<td>Pza.Desde</td>'+
             '<td colspan="2">Pza.Hasta</td>'+
             '<td align="right">Cantidad</td>'+
             '<td align="right">Valor</td>'+
             '</tr>';
    gridCon += '<tr><td colspan="20" align="center">----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</td></tr>';
  
    var rows = $('#gridDiferenciasId').jqGrid('getRowData');
    for (var i = 0; i < rows.length; i++) {
       var row = rows[i];
       
       gridCon += '<tr>';
       gridCon += '<td align="right">'+pad(row['cdOrden'], 9, ' ', 1) + '</td>' +
                  '<td align="left" colspan="9">'+pad(row['observacion'].substr(0,80) ,84, ' ', 2)  + '</td>' +
                  '<td align="center">'+pad(row['tpSolucion'],10, ' ', 3) + '</td>' +
                  '<td align="center">'+pad(row['stDiferencia'], 6, ' ', 3) + '</td>' +
                  '<td align="right">'+pad(row['cdRemito'],10, ' ', 3) + '</td>' +
                  '<td align="center">'+pad(row['pzaDesde'].substr(0,15),15, ' ', 3) + '</td>' +
                  '<td align="center" colspan="2">'+pad(row['pzaHasta'].substr(0,15),15, ' ', 3) + '</td>' +
                  '<td align="right">'+pad(convierteNumero(Number(row['ctDiferencia']),0), 9, ' ', 1) + '</td>' +
                  '<td align="right">'+pad(convierteNumero(Number(row['imPrecioTot']),2),12, ' ', 1) + '</td>';
       gridCon += '</tr>';
       if (row['stDiferencia'] != 'ANU') {
          sumCtDiferencia += Number(row['ctDiferencia']);
          sumImPrecioTot  += Number(row['imPrecioTot']);
       }
    }
    // IMPRIME EL TOTAL DE LAS DIFERENCIAS    
    gridCon += '<tr><td colspan="20" align="center">----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</td></tr>';
    gridCon += '<tr>';
    gridCon += '<td></td>' +
             '<td colspan="9"></td>' +
             '<td></td>' +
             '<td></td>' +
             '<td></td>' +
             '<td></td>' +
             '<td colspan="2"></td>' +
             '<td align="right">'+pad(convierteNumero(sumCtDiferencia,0), 9, ' ', 1) + '</td>' +
             '<td align="right">'+pad(convierteNumero(sumImPrecioTot,2),12, ' ', 1) + '</td>';
    gridCon += '</tr>';
    gridCon += '<tr><td colspan="20" align="center">----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</td></tr>';
    gridCon += '<tr><td colspan="20"></td></tr>';
  
    // SALDO   
    var detalleSaldo   = 'Saldo conciliacion ( serv facturados - prestados - diferencias)';
    var saldoCantidad  = sumCtServFactDife - sumCtDiferencia;
    var saldoImporte   = sumImServFactDife - sumImPrecioTot;
    gridCon += '<tr><td colspan="20" align="center"><h3>SALDOS</h3></td></tr>';
    gridCon += '<tr><td colspan="20" align="center">----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</td></tr>';
    gridCon += '<tr>' +
	           '<td colspan="18" align="center">'+detalleSaldo+'</td>'+
               '<td align="right">'+pad(convierteNumero(saldoCantidad,0), 9, ' ', 1) + '</td>' +
               '<td align="right">'+pad(convierteNumero(saldoImporte,2),12, ' ', 1) + '</td>' +
               '</tr>';
    gridCon += '<tr><td colspan="20" align="center">----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</td></tr>';
    gridCon += '<tr><td colspan="20"></td></tr>';   
    gridCon += '</table>';		
  
    html +=  gridEnc + gridCon + 
           '</body>'+
           '</html>'; 

    
    // Establecemos la nueva ventana.
   	var title = "Conc_" + ($('#cdProveedor').val()).trim() + "_" + 
                          ($('#cdSector').val()).trim() + "_" +
                          ($('#cdPeriodo').val()).trim() + "_" +
                          ($('#cdProducto').val()).trim();
   	
    var createdAt = new Date(); 
    var windowName = title + '_' + createdAt.getTime(); 
   	
    $('#nameFile').val(windowName);
    $('#html').val(html);
    $('#fileType').val('pdf');		
    $('#filePath').val('C:\\BBVA\\');		
    $('#exportForm').get(0).submit();
  	
/*    
    var windowUrl = 'about:blank'; 
//  var createdAt = new Date(); 
//  var windowName = title + ' ' + createdAt.getTime(); 
    var printWindow = window.open(windowUrl, windowName, 'resizable=1,scrollbars=1,left=300,top=000,width:auto,height=auto,titlebar=0');         
	printWindow.document.write(html);
    printWindow.document.close();     
    // Establecemos el foco.
    printWindow.focus();     
    // Lanzamos la impresion.
    printWindow.print();
    //Cerrar
    printWindow.close();        
*/    
}

/*--------------------------------------------------------------------------------------------------------*/
// EXPORTACION A PDF
/*--------------------------------------------------------------------------------------------------------*/
function exportGridPDF_jsPDF() {
   	var linea = "";
   	var archivo = "Conc_" + ($('#cdProveedor').val()).trim() + "_" + 
                            ($('#cdSector').val()).trim() + "_" +
                            ($('#cdPeriodo').val()).trim() + "_" +
                            ($('#cdProducto').val()).trim() + ".pdf";
   	var doc = new jsPDF('landscape');
	doc.setFont("courier");
	// CONCILIACION
    var sumCtServPrest       = 0;
    var sumImPrecioTotalPres = 0;
    var sumCtServFact        = 0;
    var sumImPrecioTotalFact = 0;
    var sumCtServFactDife    = 0;
    var sumImServFactDife    = 0;
	doc.setFontSize(10);
	doc.line(10, 15, 290, 15); // horizontal line
	doc.text(10, 20, 'Proveedor           : ' + $('#filtroProveedorList option:selected').text());
	doc.text(10, 25, 'Sector              : ' + $('#filtroSectorList option:selected').text());
	doc.text(10, 30, 'Periodo             : ' + $('#filtroPeriodoList option:selected').text());
	doc.text(10, 35, 'Producto            : ' + $('#filtroProductoList option:selected').text());
	doc.text(10, 40, 'Estado Conciliacion : ' + $('#filtroSituacionConciliacionList option:selected').text());
	doc.text(10, 45, 'Nro Conciliacion    : ' + $('#cdConciliacion').val());
	doc.text(10, 50, 'Ignora Valores      : ' + ($('#stIgnoraVal').is(':checked') ? 'SI' : 'NO')); 

	doc.text(10, 57, 'CONCILIACION');
	doc.setFontSize(7);
	doc.line(10, 60, 290, 60);
	doc.text(10, 63, '                               Servicios Prestados                                                                 Servicios Facturados                                   Diferencias     ');   
	doc.text(10, 66, '  Rem.Serv  Fec.Remito  F.Fin Serv  Cant.Serv  Importe Serv  Conc Serv  Sel.Serv    Rem.Fact  Tp.Cb.   Nro Comprob   Cant.Fact  Importe Fact  Conc.Fact  Sel.Fact  Cant.Dife  Importe Dife');   
	doc.line(10, 69, 290, 69);
	doc.line(132,60, 132, 69); 	
	doc.line(250,60, 250, 69); 	

    var nroLinea = 69;	
    var rows = $('#gridConciliacionesId').jqGrid('getRowData');
    for (var i = 0; i < rows.length; i++) {
        var row = rows[i];
        
    	doc.line(132, nroLinea, 132, nroLinea+3); 	
    	doc.line(250, nroLinea, 250, nroLinea+3); 	
        
        linea = pad(row['cdRemitoPres']      ,10, ' ', 1) + '  ' +
                pad(row['fhRemito']          ,10, ' ', 3) + '  ' +
                pad(row['fhFinServ']         ,10, ' ', 3) + '  ' +
                pad(row['ctServPrest']       , 9, ' ', 1) + '  ' +
                pad(row['imPrecioTotalPres'] ,12, ' ', 1) + '  ' +
                pad(row['cdConciliacionPres'], 9, ' ', 3) + '  ' +
                pad((row['chkPres'] == 1 ? 'X' : ' '), 8, ' ', 3) + '  ' +
                pad(row['cdRemitoFact']      ,10, ' ', 1) + '  ' +
                pad(row['tpComprobante']     , 5, ' ', 3) + '  ' +
                pad(row['nuComprobante']     ,13, ' ', 1) + '  ' +
                pad(row['ctServFact']        , 9, ' ', 1) + '  ' +
                pad(row['imPrecioTotalFact'] ,12, ' ', 1) + '  ' +
                pad(row['cdConciliacionFact'], 9, ' ', 3) + '  ' +
                pad((row['chkFact'] == 1 ? 'X' : ' '), 8, ' ', 3) + '  ' +
                pad(addCommas(row['ctServFactDife'])  , 9, ' ', 1) + '  ' +
                pad(addCommas(row['imServFactDife']) ,12, ' ', 1);

        if (row['chkPres'] == 1) {
       	  sumCtServPrest       += Number(removeCommas(row['ctServPrest']));
       	  sumImPrecioTotalPres += Number(removeCommas(row['imPrecioTotalPres']));
        }
        if (row['chkFact'] == 1) {
       	   sumCtServFact        += Number(removeCommas(row['ctServFact']));
       	   sumImPrecioTotalFact += Number(removeCommas(row['imPrecioTotalFact']));
        }
        sumCtServFactDife    += Number(removeCommas(row['ctServFactDife']));
        sumImServFactDife    += Number(removeCommas(row['imServFactDife']));
        
        nroLinea = nroLinea + 3;
        if (nroLinea > 193) {
        	doc.addPage();
        	nroLinea = 15;
        }
    	doc.text(10, nroLinea, linea);        
    }
    // IMPRIME EL TOTAL DE LA CONCILIACION    
    if (nroLinea > 178) {
    	doc.addPage();
    	nroLinea = 10;
    }
    var lineaInicial = nroLinea;

    linea = pad(''                                         ,34, ' ', 1) + '  ' +
            pad(addCommas(sumCtServPrest.toFixed())        , 9, ' ', 1) + '  ' +
            pad(addCommas(sumImPrecioTotalPres.toFixed(2)) ,12, ' ', 1) + '  ' +
            pad(''                                         ,54, ' ', 3) + '  ' +
            pad(addCommas(sumCtServFact.toFixed())         , 9, ' ', 1) + '  ' +
            pad(addCommas(sumImPrecioTotalFact.toFixed(2)) ,12, ' ', 1) + '  ' +
            pad(''                                         ,19, ' ', 3) + '  ' +
            pad(addCommas(sumCtServFactDife.toFixed())     , 9, ' ', 1) + '  ' +
            pad(addCommas(sumImServFactDife.toFixed(2))    ,12, ' ', 1);

    nroLinea = nroLinea + 3;
	doc.line(10, nroLinea, 290, nroLinea);
    nroLinea = nroLinea + 4;
	doc.text(10, nroLinea, linea);
    nroLinea = nroLinea + 2;
	doc.line(10, nroLinea, 290, nroLinea);

    var lineaFinal = nroLinea;
	
	doc.line(132, lineaInicial, 132, lineaFinal); 	
	doc.line(250, lineaInicial, 250, lineaFinal); 	
	
	// DIFERENCIAS
	var sumCtDiferencia = 0;
    var sumImPrecioTot  = 0;

    if (nroLinea > 175) {
    	doc.addPage();
    	nroLinea = 10;
    }
    nroLinea = nroLinea + 10;
	doc.setFontSize(10);
	doc.text(10, nroLinea, 'DIFERENCIA');
	doc.setFontSize(7);
    nroLinea = nroLinea + 2;
	doc.line(10, nroLinea, 290, nroLinea);
    nroLinea = nroLinea + 3;
	doc.text(10, nroLinea, '  Nro Dif  Observaciones                                                                      Sit.Solucion  Estado  Nro.Remito      Pza.Desde       Pza.Hasta       Cantidad         Valor');
    nroLinea = nroLinea + 3;
	doc.line(10, nroLinea, 290, nroLinea);
    var rows = $('#gridDiferenciasId').jqGrid('getRowData');
    for (var i = 0; i < rows.length; i++) {
        var row = rows[i];

        linea = pad(row['cdOrden']                  , 9, ' ', 1) + '  ' +
                pad(row['observacion'].substr(0,80) ,84, ' ', 2) + '  ' +
                pad(row['tpSolucion']               ,10, ' ', 3) + '  ' +
                pad(row['stDiferencia']             , 6, ' ', 3) + '  ' +
                pad(row['cdRemito']                 ,10, ' ', 3) + '  ' +
                pad(row['pzaDesde'].substr(0,15)    ,15, ' ', 3) + '  ' +
                pad(row['pzaHasta'].substr(0,15)    ,15, ' ', 3) + '  ' +
                pad(addCommas(row['ctDiferencia'])  , 9, ' ', 1) + '  ' +
                pad(addCommas(row['imPrecioTot'])   ,12, ' ', 1) + '  ';
        sumCtDiferencia += Number(removeCommas(row['ctDiferencia']));
        sumImPrecioTot  += Number(removeCommas(row['imPrecioTot']));
        
        nroLinea = nroLinea + 3;
        if (nroLinea > 193) {
        	doc.addPage();
        	nroLinea = 15;
        }
    	doc.text(10, nroLinea, linea);        
    }
    // IMPRIME EL TOTAL DE LAS DIFERENCIAS    
    if (nroLinea > 178) {
    	doc.addPage();
    	nroLinea = 10;
    }
    linea = pad(''                                   ,161, ' ', 3) + '  ' +
            pad(addCommas(sumCtDiferencia.toFixed()) ,  9, ' ', 1) + '  ' +
            pad(addCommas(sumImPrecioTot.toFixed(2)) , 12, ' ', 1);

    nroLinea = nroLinea + 3;
	doc.line(10, nroLinea, 290, nroLinea);
    nroLinea = nroLinea + 4;
	doc.text(10, nroLinea, linea);
    nroLinea = nroLinea + 2;
	doc.line(10, nroLinea, 290, nroLinea);

	// SALDOS
    var detalleSaldo   = 'Saldo conciliacion ( serv facturados - prestados - diferencias)';
	var saldoCantidad  = sumCtServFactDife - sumCtDiferencia;
    var saldoImporte   = sumImServFactDife - sumImPrecioTot;
    if (nroLinea > 168) {
    	doc.addPage();
    	nroLinea = 10;
    }
    nroLinea = nroLinea + 10;
	doc.setFontSize(10);
	doc.text(10, nroLinea, 'SALDO');
	doc.setFontSize(7);
    nroLinea = nroLinea + 2;
	doc.line(10, nroLinea, 290, nroLinea);
    nroLinea = nroLinea + 4;
    linea = pad(detalleSaldo                       ,161, ' ', 3) + '  ' +
            pad(addCommas(saldoCantidad.toFixed()) ,  9, ' ', 1) + '  ' +
            pad(addCommas(saldoImporte.toFixed(2)) , 12, ' ', 1);
	doc.text(10, nroLinea, linea);
	nroLinea = nroLinea + 2;
	doc.line(10, nroLinea, 290, nroLinea);
	
    // VARIANTES PARA VISUALIZAR EL PDF	

	// Graba el arhivo
	doc.save(archivo);
	
	// Abre el archivo en la misma ventana
    // doc.output('datauri');

	// Abre el archivo en una ventana nueva
	// doc.output('dataurlnewwindow');
	
	// Obtiene el archivo en un string y lo abre en una ventana nueva
	// var string = doc.output('datauristring');
	// var iframe = "<iframe width='100%' height='100%' src='" + string + "'></iframe>";
	// var x = window.open();
	// x.document.open();
	// x.document.write(iframe);
	// x.document.close();	
}

/*--------------------------------------------------------------------------------------------------------*/
// EXPORTACION A EXCEL
/*--------------------------------------------------------------------------------------------------------*/
function exportGridXLS(){
   var archivo = "Conc_" + ($('#cdProveedor').val()).trim() + "_" + 
                           ($('#cdSector').val()).trim() + "_" +
                           ($('#cdPeriodo').val()).trim() + "_" +
                           ($('#cdProducto').val()).trim();
   var html='<html>' +
        '<head> ' +
	    '   <style script="css/text" media="all">'+
	    '     table.tableList th {border:1px solid black;border-bottom:2px solid black;text-align:center;vertical-align: middle;padding:5px;background-color:#c5dbec !important;font-style:italic;-webkit-print-color-adjust: exact; }  '+
	    '     table.tableList td {border:1px solid black;text-align: left;vertical-align: top;padding:5px;}'+
	    '   </style>'+
	    '</head>'+		
	    '<body>';

   var gridEnc = '<table>';
   gridEnc += '<tr>'+
              '<td colspan="2">Proveedor</td>'+
              '<td colspan="14">'+ $('#filtroProveedorList option:selected').text() +'</td>'+
              '</tr>';
   gridEnc += '<tr>'+
              '<td colspan="2">Sector</td>'+
              '<td colspan="14">'+ $('#filtroSectorList option:selected').text() +'</td>'+
              '</tr>';
   gridEnc += '<tr>'+
              '<td colspan="2">Periodo</td>'+
              '<td colspan="14">'+ $('#filtroPeriodoList option:selected').text() +'</td>'+
              '</tr>';
   gridEnc += '<tr>'+
              '<td colspan="2">Producto</td>'+
              '<td colspan="14">'+ $('#filtroProductoList option:selected').text() +'</td>'+
              '</tr>';
   gridEnc += '<tr>'+
              '<td colspan="2">Estado Conciliacion</td>'+
              '<td colspan="14">'+ $('#filtroSituacionConciliacionList option:selected').text() +'</td>'+
              '</tr>';
   gridEnc += '<tr>'+
              '<td colspan="2">Fecha Remito Desde</td>'+
              '<td colspan="16" align="left">'+ $('#fhRemitoDesde').val() +'</td>'+
              '</tr>';
   gridEnc += '<tr>'+
              '<td colspan="2">Fecha Remito Hasta</td>'+
              '<td colspan="16" align="left">'+ $('#fhRemitoHasta').val() +'</td>'+
              '</tr>';
   gridEnc += '<tr>'+
              '<td colspan="2">Fecha Fin Serv Desde</td>'+
              '<td colspan="16" align="left">'+ $('#fhFinServicioDesde').val() +'</td>'+
              '</tr>';
   gridEnc += '<tr>'+
              '<td colspan="2">Fecha Fin Serv Hasta</td>'+
              '<td colspan="16" align="left">'+ $('#fhFinServicioHasta').val() +'</td>'+
              '</tr>';
   gridEnc += '<tr>'+
              '<td colspan="2">Nro Conciliacion</td>'+
              '<td colspan="14" align="left">'+ $('#cdConciliacion').val() +'</td>'+
              '</tr>';
   gridEnc += '<tr>'+
              '<td colspan="2">Ignora Valores</td>'+
              '<td colspan="14">'+ ($('#stIgnoraVal').is(':checked') ? 'SI' : 'NO') +'</td>'+
              '</tr>';
   gridEnc += '<tr><td colspan="16"><td></tr>';
   gridEnc += '</table>';
   
   // CONCILIACION
   var sumCtServPrest       = 0;
   var sumImPrecioTotalPres = 0;
   var sumCtServFact        = 0;
   var sumImPrecioTotalFact = 0;
   var sumCtServFactDife    = 0;
   var sumImServFactDife    = 0;
   var gridCon = '<table>';
   gridCon += '<tr><td colspan="16" align="center"><h3>CONCILIACION</h3></td></tr>';
   gridCon += '<tr>'+
              '<td colspan="7" align="center">Servicios Prestados</td>'+
              '<td colspan="7" align="center">Servicios Facturados</td>'+
              '<td colspan="2" align="center">Diferencias</td>'+
              '</tr>';
   gridCon += '<tr>'+
              '<td align="right">Rem.Serv</td>'+
              '<td align="center">Fec.Remito</td>'+
              '<td align="center">F.Fin Serv</td>'+
              '<td align="right">Cant.Serv</td>'+
              '<td align="right">Importe Serv</td>'+
              '<td align="center">T.Val</td>'+
              '<td align="center">Mon</td>'+
              '<td align="right">Conc Serv</td>'+
              '<td align="center">Sel.Serv</td>'+
              '<td align="right">Rem.Fact</td>'+
              '<td align="center">Tp.Cb.</td>'+
              '<td align="right">Nro Comprob</td>'+
              '<td align="right">Cant.Fact</td>'+
              '<td align="right">Importe Fact</td>'+
              '<td align="right">Conc.Fact</td>'+
              '<td align="center">Sel.Fact</td>'+
              '<td align="right">Cant.Dife</td>'+
              '<td align="right">Importe Dife</td>'+
              '</tr>';

   var rows = $('#gridConciliacionesId').jqGrid('getRowData');
   for (var i = 0; i < rows.length; i++) {
       var row = rows[i];

       gridCon += '<tr>';
       gridCon += '<td align="right">'+pad(row['cdRemitoPres'],10, ' ', 1) + '</td>' +
                  '<td align="center">'+(row['fhRemito'].trim() == '' ? '' : pad(row['fhRemito'],10, ' ', 3)) + '</td>' +
                  '<td align="right">'+(row['fhFinServ'].trim() == '' ? '' : pad(row['fhFinServ'],10, ' ', 3)) + '</td>' +
                  '<td align="right">'+(formatoSinPuntos(row['ctServPrest'])) + '</td>' +
                  '<td align="right">'+(formatoSinPuntos(row['imPrecioTotalPres'])) + '</td>' +
                  '<td align="center">'+(row['cdTipVal'])+'</td>' +
                  '<td align="center">'+(row['cdMoneda'])+'</td>' +
                  '<td align="center">'+pad(row['cdConciliacionPres'], 9, ' ', 3) + '</td>' +
                  '<td align="center">'+(row['chkPres'] == 1 ? 'X' : ' ') + '</td>' +
                  '<td align="right">'+pad(row['cdRemitoFact'],10, ' ', 1) + '</td>' +
                  '<td align="center">'+pad(row['tpComprobante'], 5, ' ', 3) + '</td>' +
                  '<td align="right">'+pad(row['nuComprobante'],13, ' ', 1) + '</td>' +
                  '<td align="right">'+(formatoSinPuntos(row['ctServFact'])) + '</td>' +
                  '<td align="right">'+(formatoSinPuntos(row['imPrecioTotalFact'])) + '</td>' +
                  '<td align="center">'+pad(row['cdConciliacionFact'], 9, ' ', 3) + '</td>' +
                  '<td align="center">'+(row['chkFact'] == 1 ? 'X' : ' ') + '</td>' +
                  '<td align="right">'+(formato_numero(row['ctServFactDife'],2,',','')) + '</td>' +
                  '<td align="right">'+(formato_numero(row['imServFactDife'],2,',','')) + '</td>';      
       gridCon += '</tr>';

       if (row['chkPres'] == 1) {
      	  sumCtServPrest       += Number(removeCommas(row['ctServPrest']));
      	  sumImPrecioTotalPres += Number(removeCommas(row['imPrecioTotalPres']));
       }
       if (row['chkFact'] == 1) {
      	   sumCtServFact        += Number(removeCommas(row['ctServFact']));
      	   sumImPrecioTotalFact += Number(removeCommas(row['imPrecioTotalFact']));
       }
//     sumCtServFactDife    += Number(removeCommas(row['ctServFactDife']));
//     sumImServFactDife    += Number(removeCommas(row['imServFactDife']));
   }
   sumCtServFactDife = sumCtServFact - sumCtServPrest;
   sumImServFactDife = sumImPrecioTotalFact - sumImPrecioTotalPres;
   
   // IMPRIME EL TOTAL DE LA CONCILIACION    
   gridCon += '<tr>';
   gridCon += '<td></td>' +
              '<td></td>' +
              '<td></td>' +
              '<td align="right">'+formato_numero(sumCtServPrest,2,',','') + '</td>' +
              '<td align="right">'+formato_numero(sumImPrecioTotalPres,2,',','') + '</td>' +
              '<td></td>' +
              '<td></td>' +
              '<td></td>' +
              '<td></td>' +
              '<td></td>' +
              '<td></td>' +
              '<td></td>' +
              '<td align="right">'+formato_numero(sumCtServFact,2,',','') + '</td>' +
              '<td align="right">'+formato_numero(sumImPrecioTotalFact,2,',','') + '</td>' +
              '<td></td>' +
              '<td></td>' +
              '<td align="right">'+formato_numero(sumCtServFactDife,2,',','') + '</td>' +
              '<td align="right">'+formato_numero(sumImServFactDife,2,',','') + '</td>';
   gridCon += '</tr>';
   gridCon += '<tr><td colspan="16"><td></tr>';
   gridCon += '</table>';		

   // DIFERENCIAS
   var sumCtDiferencia = 0;
   var sumImPrecioTot  = 0;
   
   var gridDif = '   <table>';
   gridDif += '<tr><td colspan="16" align="center"><h3>DIFERENCIAS</h3></td></tr>';
   gridDif += '<tr>'+
              '<td>Nro Dif</td>'+
              '<td colspan="8">Observaciones</th>'+
              '<td>Sit.Solucion</td>'+
              '<td>Estado</td>'+
              '<td>Nro.Remito</td>'+
              '<td>Pza.Desde</td>'+
              '<td>Pza.Hasta</td>'+
              '<td align="right">Cantidad</td>'+
              '<td align="right">Valor</td>'+
              '</tr>';
   
   var rows = $('#gridDiferenciasId').jqGrid('getRowData');
   for (var i = 0; i < rows.length; i++) {
       var row = rows[i];
        
       gridDif += '<tr>';
       gridDif += '<td align="right">'+pad(row['cdOrden']                  , 9, ' ', 1) + '</td>' +
                  '<td align="left" colspan="8">'+pad(row['observacion'].substr(0,80) ,84, ' ', 2)  + '</td>' +
                  '<td align="center">'+pad(row['tpSolucion']               ,10, ' ', 3) + '</td>' +
                  '<td align="center">'+pad(row['stDiferencia']             , 6, ' ', 3) + '</td>' +
                  '<td align="right">'+pad(row['cdRemito']                 ,10, ' ', 3) + '</td>' +
                  '<td align="center">'+pad(row['pzaDesde'].substr(0,15)    ,15, ' ', 3) + '</td>' +
                  '<td align="center">'+pad(row['pzaHasta'].substr(0,15)    ,15, ' ', 3) + '</td>' +
                  '<td align="right">'+formato_numero(row['ctDiferencia'],2,',','') + '</td>' +
                  '<td align="right">'+formato_numero(row['imPrecioTot'],2,',','') + '</td>';
       gridDif += '</tr>';
       if (row['stDiferencia'] != 'ANU') {
          sumCtDiferencia += Number(row['ctDiferencia']);
          sumImPrecioTot  += Number(row['imPrecioTot']);
       }
   }
   // IMPRIME EL TOTAL DE LAS DIFERENCIAS    
   gridDif += '<tr>';
   gridDif += '<td></td>' +
              '<td colspan="8"></td>' +
              '<td></td>' +
              '<td></td>' +
              '<td></td>' +
              '<td></td>' +
              '<td></td>' +
              '<td align="right">'+formato_numero(sumCtDiferencia,2,',','') + '</td>' +
              '<td align="right">'+formato_numero(sumImPrecioTot,2,',','') + '</td>';
   gridDif += '</tr>';
   gridDif += '<tr><td colspan="16"><td></tr>';
   gridDif += '</table>';
   
   // SALDO   
   var detalleSaldo   = 'Saldo conciliacion ( serv facturados - prestados - diferencias)';
   var saldoCantidad  = sumCtServFactDife - sumCtDiferencia;
   var saldoImporte   = sumImServFactDife - sumImPrecioTot;
   var gridSal = '<table>';
   gridSal += '<tr><td colspan="16" align="center"><h3>SALDOS</h3></td></tr>';
   gridSal += '<tr>' +
	          '<td colspan= "14" align="center">'+detalleSaldo+'</td>'+
              '<td align="right">'+formato_numero(saldoCantidad,2,',','') + '</td>' +
              '<td align="right">'+formato_numero(saldoImporte,2,',','') + '</td>' +
              '</tr>';
   gridSal += '<tr><td colspan="16"><td></tr>';
   gridSal += '</table>';		
   
   html +=  gridEnc + gridCon + gridDif + gridSal + 
            '</body>'+
            '</html>'; 
   $('#nameFile').val(archivo);
   $('#html').val(html);
   $('#fileType').val('xls');		
   $('#exportForm').get(0).submit();
}

//function exportGridXLS_David(){
//	var title = 'Conciliaciones';
//	var printContent = '';
//	
//	printContent = printContent + exportDataGridToHtml('6,7,14,15,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35','Conciliaciones','gridConciliacionesId');
//	printContent = printContent + '<br><br>';
//	printContent = printContent + exportDataGridToHtml('10,11,12,13,14,15,16','Diferencias','gridDiferenciasId');
//	printContent += '<br><br>';	
//	printContent = printContent + exportDataGridToHtml('','Saldos','gridSaldosId');	
//	
////	window.open('data:application/vnd.ms-excel,' + encodeURIComponent( getHeaderHtml() + printContent + getFootHtml()));
//
//	$('#nameFile').val(title);
//	$('#html').val(createHtmlToExport(printContent));
//	$('#fileType').val('xls');		
//	$('#exportForm').get(0).submit();
//}
