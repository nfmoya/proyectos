/*--------------------------------------------------------------------------------------------------------*/
// GRILLA REMITOS REPETIDOS
/*--------------------------------------------------------------------------------------------------------*/

/*--------------------------------------------------------------------------------------------------------*/
// CARGA GRILLA REMITOS REPETIDOS
/*--------------------------------------------------------------------------------------------------------*/
function loadRemitosRepetidos() {
   var params = '';

/*   
   // ORIGINALMENTE SE ARMABA UN STRING CON LA LISTA DE REMITOS FACTURADOS, PERO NO FUNCIONABA BIEN CUANDO
   // LA CANTIDAD ERA GRANDE PORQUE EL PARAMETRO EN EL SP TIENE UN LIMITE DE 1500 CARACTERES, SE DEJA
   // COMENTADO SOLO COMO REFERENCIA  
   var listaFact = '';
   var rows = $('#gridConciliacionesId').jqGrid('getRowData');
   // Arma los strings con los nros de remitos facturados para buscar si hay repetidos
   for (var i = 0; i < rows.length; i++) {
      var row    = rows[i];
      var remito = row['cdRemitoFact'];
      if (remito.trim() != '') {
         if (listaFact.indexOf(remito) == -1) {
        	if ((listaFact+row['cdRemitoFact']).length >= 1500) {
        	   break;
        	}
            if (listaFact.trim() != '') {
               listaFact += '|';
            } 
            listaFact += row['cdRemitoFact'];
         }      
      }
   }   
   //exec dbo.sp_CFS_busqueda_repetidos_conciliacion 'OCA', '21590', 'ENE14', '1111|2222|3333|4444|5555|6666|7777|9999'   
*/

   params += 'cdProveedor='+$('#filtroProveedorList').val();
   params += '&cdProducto='+$('#filtroProductoList').val();
   params += '&cdPeriodo='+$('#filtroPeriodoList').val();	
   params += '&cdSector='+$('#filtroSectorList').val();
   params += '&cdConciliacion='+$('#cdConciliacion').val();

   try {
      showGrid({
         id : 'gridRepetidosId',
         idPager : 'gridRepetidosPager',
         url : 'JsonRepetidosList.action?'+params,
         colNames : [ 'Remito', 'Periodo', 'Tp Cb', 'Nro Comprob', 'Cant Fact', 'Importe', 'Total' ],
         colModel : [ 
                {name : 'cdRemito'      , width :  80, align : 'right' , hidden : false }, 
                {name : 'cdPeriodoFact' , width :  50, align : 'center', hidden : false }, 
                {name : 'tpComprobante' , width :  50, align : 'left'  , hidden : false }, 
                {name : 'nuComprobante' , width : 120, align : 'left'  , hidden : false }, 
                {name : 'ctServFact'    , width :  60, align : 'right' , hidden : false , 
            	        formatter: "number", 
                        formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},              
                {name : 'imPrecioUnit'  , width :  80, align : 'right' , hidden : false ,
                  	    formatter: "number", 
                        formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},              
                {name : 'imPrecioTotal' , width :  80, align : 'right' , hidden : false , 
                	    formatter: "number", 
                        formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},              
            ],
            sortName : 'cdRemito',
            caption : "Remitos Repetidos",
            scrollerbar:true,
            height :'200',
            width : '580',
            multiselect: false,
            loadonce : true,
			viewrecords : true,
            editurl: 'clientArray',
            shrinkToFit: true,
			footerrow : false,      
		});
        $('.ui-jqgrid-title').css('width', '98%');
	} catch(e) {
		jsError('loadRepetidosGrid(...)', e);
	}
}
