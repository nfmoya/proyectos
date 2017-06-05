/*--------------------------------------------------------------------------------------------------------*/
// GRILLA SALDOS
/*--------------------------------------------------------------------------------------------------------*/

/*--------------------------------------------------------------------------------------------------------*/
// CARGA GRILLA SALDOS
/*--------------------------------------------------------------------------------------------------------*/
function loadSaldosGrid() {
    var mydata = [ {saldoDetalle: "Saldo conciliaci&oacute;n ( serv facturados - prestados - diferencias)", saldoCtDiferencia: "0.00",  saldoPrecioTotal: "0.00"} ];
    $("#gridSaldosId").jqGrid({
    	datatype: "local",
    	data: mydata,
    	colNames: ["Detalle", "Cantidad", "Valor", ""],
    	colModel: [
                  {name: "saldoDetalle"     , width: 850},
                  {name: "saldoCtDiferencia", width: 50, formatter: "number", align: "right",
                   formatoptions:{decimalSeparator:",", thousandsSeparator: ".", decimalPlaces: 0}},              
                  {name: "saldoPrecioTotal" , width: 80, formatter: "number", align: "right" ,
                   formatoptions:{decimalSeparator:",", thousandsSeparator: ".", decimalPlaces: 2}},                   
                  {name: "espacio"          , width: 15}
        ],
        footerrow: false,
	    width: 1050,
	    height: '100%',
	    caption: 'Saldo Conciliacion',
	    gridComplete: function() {
            var rowsId = $("#gridSaldosId").getDataIDs();
            for (var i = 0; i < rowsId.length; i++) {
               $("#gridSaldosId").jqGrid('setRowData', rowsId[i], false, 'myclass');
            }
         }
    });
    $('#gridSaldosId').trigger('reloadGrid');
	$('#gridSaldosId').show();	    
}
