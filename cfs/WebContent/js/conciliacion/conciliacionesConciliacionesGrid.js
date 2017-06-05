/*--------------------------------------------------------------------------------------------------------*/
// GRILLA CONCILIACION
/*--------------------------------------------------------------------------------------------------------*/

/*--------------------------------------------------------------------------------------------------------*/
// CARGA GRILLA CONCILIACION
/*--------------------------------------------------------------------------------------------------------*/
function loadConciliacionesGrid(){
	var params = '';
	var fhRemitoDesde = ($("#fhRemitoDesde").val() == '') ? '' : dateFormat_JsToService($("#fhRemitoDesde").val());
	var fhRemitoHasta = ($("#fhRemitoHasta").val() == '') ? '' : dateFormat_JsToService($("#fhRemitoHasta").val());
	var fhFinSerDesde = ($("#fhFinServicioDesde").val() == '') ? '' : dateFormat_JsToService($("#fhFinServicioDesde").val());
	var fhFinSerHasta = ($("#fhFinServicioHasta").val() == '') ? '' : dateFormat_JsToService($("#fhFinServicioHasta").val());
	params += 'cdProveedor='+$('#filtroProveedorList').val();
	params += '&cdSector='+$('#filtroSectorList').val();
	params += '&cdProducto='+$('#filtroProductoList').val();
	params += '&cdPeriodoFact='+$('#filtroPeriodoList').val();	
	params += '&fhRemitoDesde='+fhRemitoDesde;
	params += '&fhRemitoHasta='+fhRemitoHasta;
	params += '&fhFinServicioDesde='+fhFinSerDesde;
	params += '&fhFinServicioHasta='+fhFinSerHasta;
	params += '&stConciliacion='+$('#filtroSituacionConciliacionList').val();	
	params += '&cdConciliacion='+$('#cdConciliacion').val();

	try {
		showGrid({
			id : 'gridConciliacionesId',
 			idPager : 'gridConciliacionesPager',
			url : 'JsonConciliacionesList.action?'+params,
			colNames : [ 'Rem Serv'       , 'Fecha Remito'    , 'F. Fin Serv'    , 'Cant Srv'       , 'Importe Serv'   ,
			             'Imp Serv Conv'  , 'T.Val'          , 'Mon'             , 
			             'Conc Serv'      , 'Selecc Serv'     , 'Sel Serv'       , 'Rem Fact'       , 'Tp Cb'          , 
			             'Nro Comprob'    , 'Cant Fact'       , 'Importe Fact'   , 'Conc Fact'      , 'Selecc Fact'    , 
			             'Sel Fact'       , 'Cant Dife'       , 'Importe Dife'   , 'Unidad Val'     , 'Precio Unit'    ,  
			             'Lote Remito'    , 'Orden Remito'    , 'Sector Sol'     , 'Sector Control' , 'Producto'       ,  
			             'Pza Desde Serv' , 'Pza Hasta Serv'  , 'Remito Padre'   , 'Atr Ref 1'      , 'Atr Ref 2'      ,  
			             'Observaciones'  , 'Estado Lote'     , 'Fecha Mod'      , 'Usuario'        , 'Lote Factura'   , 
			             'Orden Factura'  
            ],
            colModel : [ 
                {name : 'cdRemitoPres'        , width :  82, align : 'right', hidden : false }, 
                {name : 'fhRemito'            , width :  62, align : 'center', hidden : false,
                        formatter: 'date', formatoptions: { srcformat: 'Y-m-d', newformat: 'd/m/Y'}},
                {name : 'fhFinServ'           , width :  62, align : 'center', hidden : false , 
                        formatter: 'date', formatoptions: { srcformat: 'Y-m-d', newformat: 'd/m/Y'}},
                {name : 'ctServPrest'         , width :  42, align : 'right' , hidden : false ,
   					    formatter: qDifFormatter},
//                	    formatter: "number", 
//                      formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 0}},              
                {name : 'imPrecioTotalPres'   , width :  70, align : 'right' , hidden : false , 
						formatter: iDifFormatter},              
//                 	    formatter: "number", 
//                      formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},    
				{name : 'imPrecioTotalPresConv'   , width :  70, align : 'right' , hidden : true , 
						formatter: iDifFormatter}, 
                {name : 'cdTipVal'            , width :  32, align : 'right' , hidden : false }, 
                {name : 'cdMoneda'            , width :  28, align : 'right' , hidden : false }, 
                {name : 'cdConciliacionPres'  , width :  50, align : 'right' , hidden : false }, 
                {name : 'chkPres'             , width :  35, align : 'center', hidden : true  },
                {name : 'idChkPres'           , width :  35, align : 'center', hidden : false , 
                        formatter : checkboxFormatterPres, sortable : false, classes:"grid-col"},
                {name : 'cdRemitoFact'        , width :  72, align : 'right' , hidden : false }, 
                {name : 'tpComprobante'       , width :  30, align : 'left'  , hidden : false }, 
                {name : 'nuComprobante'       , width :  60, align : 'left'  , hidden : false }, 
                {name : 'ctServFact'          , width :  42, align : 'right' , hidden : false , 
					    formatter: qDifFormatter},
//					    formatter: "number", 
//                      formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 0}},              
                {name : 'imPrecioTotalFact'   , width :  80, align : 'right' , hidden : false , 
	   					formatter: iDifFormatter},
//	   					formatter: "number", 
//                      formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},              
                {name : 'cdConciliacionFact'  , width :  50, align : 'right' , hidden : false }, 
                {name : 'chkFact'             , width :  35, align : 'center', hidden : true  , classes: 'not-editable-cell'},
                {name : 'idChkFact'           , width :  35, align : 'center', hidden : false , 
					    formatter : checkboxFormatterFact, sortable : false, classes:"grid-col"},
                {name : 'ctServFactDife'      , width :  40, align : 'right' , hidden : false , 
//					    formatter: qDifFormatter},              
		   				formatter: "number", 
	                    formatoptions:{decimalSeparator:",", thousandsSeparator: ".", decimalPlaces: 0}},              
                {name : 'imServFactDife'      , width :  70, align : 'right' , hidden : false , 
//                	    formatter: iDifFormatter},       
		   				formatter: "number", 
	                    formatoptions:{decimalSeparator:",", thousandsSeparator: ".", decimalPlaces: 2}},              
                {name : 'cdUniValPres'        , width : 100, align : 'right' , hidden : true  },
                {name : 'imPrecioUnitPres'    , width : 100, align : 'right' , hidden : true },
                {name : 'cdLoteServ'          , width : 100, align : 'right' , hidden : true  },
                {name : 'cdOrdenServ'         , width : 100, align : 'right' , hidden : true  },
                {name : 'cdSectorSol'         , width : 100, align : 'right' , hidden : true  },
                {name : 'cdSectorControl'     , width : 100, align : 'right' , hidden : true  },
                {name : 'cdProductoPres'      , width : 100, align : 'right' , hidden : true  },
                {name : 'nbPiezaDesdePres'    , width : 100, align : 'right' , hidden : true  },
                {name : 'nbPiezaHastaPres'    , width : 100, align : 'right' , hidden : true  },
                {name : 'cdRemitoPadre'       , width : 100, align : 'right' , hidden : true  },
                {name : 'nbAtributoRef1'      , width : 100, align : 'right' , hidden : true  },
                {name : 'nbAtributoRef2'      , width : 100, align : 'right' , hidden : true  },
                {name : 'nbObservaciones'     , width : 100, align : 'right' , hidden : true  },
                {name : 'stLoteDet'           , width : 100, align : 'right' , hidden : true  },
                {name : 'fhModificacion'      , width : 100, align : 'right' , hidden : true  },
                {name : 'nbUsuarioModif'      , width : 100, align : 'right' , hidden : true  },
                {name : 'cdLoteFact'          , width : 100, align : 'right' , hidden : true  },
                {name : 'cdOrdenFact'         , width : 100, align : 'right' , hidden : true  }
            ],
		    width: 1050,            
            rowNum:1000,
            sortName : 'conciliacion',
            caption : "Conciliaci\u00f3n",
            scrollerbar:true,
            height :'400',
//          width : '1050',
            multiselect: false,
            loadonce : true,
			viewrecords : true,
            editurl: 'clientArray',
            shrinkToFit: false,
			footerrow : true,  
	        ondblClickRow: function(rowid, iRow, iCol, e) {
               viewDialogConciliacion(rowid);
            },
		    gridComplete: function() {
               var rowData = $("#gridConciliacionesId").getDataIDs();
               for (var i = 0; i < rowData.length; i++) {
                  var cl = rowData[i];
                  var cantidad = $('#gridConciliacionesId').getCell(cl, 'ctServFactDife');   
                  var importe  = $('#gridConciliacionesId').getCell(cl, 'imServFactDife');
                  if($('#stIgnoraVal').is(':checked')) {
                     if (cantidad != 0) {
                        $("#gridConciliacionesId").jqGrid('setRowData', rowData[i], false, {color:'red'});
                     } 
                  } else {
                     if (cantidad != 0 || importe != 0) {
                        $("#gridConciliacionesId").jqGrid('setRowData', rowData[i], false, {color:'red'});
                     } 
                  }
               }
               var height; //$(window).height();
               if (rowData.length <= 50) {
            	  height = 23.52 * rowData.length; 
               } else {
             	  height = 400; 
               }
//             $('.ui-jqgrid-bdiv').height(height); // ANDA BIEN CON UNA SOLA GRILLA       
               $("#gridConciliacionesId").jqGrid("setGridHeight",height);        
               $('#modificada').val("N");  
               if ($('#recargaConciliacion').val() == "N") { 
                  loadRepetidos();
                  $('#recargaConciliacion').val("S");  
		       }
            }
		});
		$("#gridConciliacionesId").jqGrid('setGroupHeaders', {
			useColSpanStyle : true,
			groupHeaders : [ {
				startColumnName : 'cdRemitoPres',
				numberOfColumns : 11,
				titleText : '<p style="text-align:center">Servicios prestados</p>'
			}, {
				startColumnName : 'cdRemitoFact',
				numberOfColumns : 8,
				titleText : '<p style="text-align:center">Servicios facturados</p>'
			}, {
				startColumnName : 'ctServFactDife',
				numberOfColumns : 2,
				titleText : '<p style="text-align:center">Diferencias</p>'
			}]
		});
		addButtonsToConciliacionesGrid();
		
		$('.ui-jqgrid-title').css('width', '100%');

 		if ($("#saveGrant").val() == 'S') {
	       // Si el Estado de Conciliacion es Pendiente o Aprobada habilito el boton Grabar
	       var stConciliacion = $('#stConciliacion').val();
	       if (($("#stPeriodo").val() != 'CER') && (stConciliacion == 'PEN' || stConciliacion == 'GRA')) {
	          $("#btnGrabar").removeClass('ui-state-disabled');
	          $("#btnGrabar").removeAttr("disabled");
	       }
	    }
 		
		Totaliza();
 		
	} catch(e) {
		jsError('loadConciliacionesGrid(...)', e);
	}
}

/*--------------------------------------------------------------------------------------------------------*/
// FORMATEO DE COLUMNAS DE CANTIDADES (PARA AGREGARLES LA SEPARACION DE MILES)
// SE UTILIZA ESTA PORQUE EL USUARIO QUIERE QUE CUANDO LA CANTIDAD ES CERO, SE MUESTRE LA CELDA EN BLANCO 
/*--------------------------------------------------------------------------------------------------------*/
function qDifFormatter (cellvalue, options, rowObject) {
   if (cellvalue != 0) {
	  return addCommas(cellvalue.toFixed(0));	   
   } else {
	  return '';
   }
}

/*--------------------------------------------------------------------------------------------------------*/
// FORMATEO DE COLUMNAS DE IMPORTE (PARA AGREGARLES LA SEPARACION DE MILES Y PUNTO DECIMAL)
// SE UTILIZA ESTA PORQUE EL USUARIO QUIERE QUE CUANDO EL IMPORTE ES CERO, SE MUESTRE LA CELDA EN BLANCO 
/*--------------------------------------------------------------------------------------------------------*/
function iDifFormatter (cellvalue, options, rowObject) {
   if (cellvalue != 0) {
	  return convierteNumero(cellvalue, 2);	   
   } else {
	  return '';
   }
}

/*--------------------------------------------------------------------------------------------------------*/
// CONVIERTE LOS PUNTOS DECIMALES Y AGREGA LA COMA DE SEPARACION DE MILES
/*--------------------------------------------------------------------------------------------------------*/
function convierteNumero(importe, decimales) {
   importe = importe.toFixed(decimales);
   importe = importe.replace(".", ",");   
   return addCommas(importe);
}
/*--------------------------------------------------------------------------------------------------------*/
// AGREGA LA COMA DE SEPARACION DE MILES
/*--------------------------------------------------------------------------------------------------------*/
function addCommas(nStr) {
    nStr += '';
    x = nStr.split(',');
    x1 = x[0];
    x2 = x.length > 1 ? ',' + x[1] : '';
    var rgx = /(\d+)(\d{3})/;
    while (rgx.test(x1)) {
       x1 = x1.replace(rgx, '$1' + '.' + '$2');
    }
    return x1 + x2;
}	

/*--------------------------------------------------------------------------------------------------------*/
//AGREGA BOTONERA EN LA GRILLA PARA VER EL DETALLE DEL REMITO SELECCIONADO
/*--------------------------------------------------------------------------------------------------------*/
function addButtonsToConciliacionesGrid() {
   var title = 'Ver Detalle Servicios Prestados';
   $('#gridConciliacionesId').navButtonAdd('#gridConciliacionesPager', {
   caption: 'Detalle Remito',
      id:'detalle',
      onClickButton: function() {
         try {
            var rowid = getSelRowFromConciliacionGrid(); 
            if (rowid != null) {
               viewDialogConciliacion(rowid);
            } else {
               seleccioneRegistro();
			}
         } catch(e) {
//          jsError('detalleConciliacion(...)', e);
         }
      },
      position:'last',
      title:title
   });
}

/*--------------------------------------------------------------------------------------------------------*/
// RELOAD DE LA GRILLA
/*--------------------------------------------------------------------------------------------------------*/
function reloadConciliacionesGrid(){
   $('#gridConciliacionesId').trigger('reloadGrid');
}

/*--------------------------------------------------------------------------------------------------------*/
// OBTENER EL REGISTRO SELECCIONADO DE LA GRILLA
/*--------------------------------------------------------------------------------------------------------*/
function getSelRowFromConciliacionGrid() { 
   return $("#gridConciliacionesId").getGridParam('selrow');
}

/*--------------------------------------------------------------------------------------------------------*/
// FORMATEO DEL CHECKBOX DE SERVICIOS PRESTADOS, SI EL PARAMETRO SERVSINCONCIL='N' SE SELECCIONAN TODOS LOS
// REGISTROS Y SE DESHABILITAN, LO MISMO SI LA CONCILIACION ESTA APROBADA
// TAMBIEN SE DESHABILITA SI LA CANTIDAD Y EL IMPORTE SON IGUALES A CERO, PARA QUE NO SE PUEDA SELECCIONAR
/*--------------------------------------------------------------------------------------------------------*/
function checkboxFormatterPres(cellvalue, options, rowObject) {
   var bchk     = '';
   var disabled = '';
   cellvalue    = cellvalue + "";
   cellvalue    = rowObject.chkPres;
   bchk         = cellvalue == '1' ? "checked='checked' " : " ";
   disabled     = $('#stServSinConcil').val() == 'N' ? "disabled='disabled' " : " ";
   if ($('#stConciliacion').val() == 'APR') {
	   bchk     = "checked='checked' ";
	   disabled = "disabled='disabled' ";
   }
   if (rowObject.ctServPrest == 0 && rowObject.imPrecioTotalPres == 0) {
	   bchk     = " ";
	   disabled = "disabled='disabled' ";
   }	   
   return "<input class='habilitar' type='checkbox'" + bchk + disabled
            + "onclick='changeChkPres(" + options.rowId + ",this)'"
            + " value='" + cellvalue + "' offval='0' />";
}

/*--------------------------------------------------------------------------------------------------------*/
// FUNCION UTILIZADA CUANDO SE PRESIONA EL CHECKBOX DE SERVICIOS PRESTADOS, LO CHEQUEA O NO DE ACUERDO A 
// NUEVA CONDICION, Y CALCULA LA DIFERENCIA ENTRE EL SERVICIO PRESTADO Y EL FACTURADO, COMO TAMBIEN EL 
// CALCULO DE LOS TOTALES EN CANTIDAD E IMPORTE DE SERVICIOS PRESTADOS, FACTURADOS Y DIFERENCIAS
/*--------------------------------------------------------------------------------------------------------*/
function changeChkPres(rowid, check) {
   var checkValue = (check.checked) ? '1' : '0';
   check.value = checkValue;
   $('#gridConciliacionesId').jqGrid('setCell', rowid, 'chkPres', checkValue);

   calculaDiferencia(rowid);
   
   $('#modificada').val("S");  

   Totaliza();
}

/*--------------------------------------------------------------------------------------------------------*/
// FORMATEO DEL CHECKBOX DE SERVICIOS FACTURADOS, SI EL PARAMETRO FACTSINCONCIL='N' SE SELECCIONAN TODOS LOS
// REGISTROS Y SE DESHABILITAN, LO MISMO SI LA CONCILIACION ESTA APROBADA
// TAMBIEN SE DESHABILITA SI LA CANTIDAD Y EL IMPORTE SON IGUALES A CERO, PARA QUE NO SE PUEDA SELECCIONAR
/*--------------------------------------------------------------------------------------------------------*/
function checkboxFormatterFact(cellvalue, options, rowObject) {
   var bchk     = '';
   var disabled = '';
   cellvalue    = cellvalue + "";
   cellvalue    = rowObject.chkFact;
   bchk         = cellvalue == '1' ? "checked='checked' " : " ";
   disabled     = $('#stFactSinConcil').val() == 'N' ? "disabled='disabled' " : " ";
   if ($('#stConciliacion').val() == 'APR') {
	   bchk     = "checked='checked' ";
	   disabled = "disabled='disabled' ";
   }
   if (rowObject.ctServFact == 0 && rowObject.imPrecioTotalFact == 0) {
	   bchk     = " ";
	   disabled = "disabled='disabled' ";
   }	   
   return "<input type='checkbox'" + bchk + disabled
            + "onclick='changeChkFact(" + options.rowId + ",this)'"
            + " value='" + cellvalue + "' offval='0' />";
}

/*--------------------------------------------------------------------------------------------------------*/
// FUNCION UTILIZADA CUANDO SE PRESIONA EL CHECKBOX DE SERVICIOS FACTURADOS, LO CHEQUEA O NO DE ACUERDO A 
// NUEVA CONDICION, Y CALCULA LA DIFERENCIA ENTRE EL SERVICIO PRESTADO Y EL FACTURADO, COMO TAMBIEN EL 
// CALCULO DE LOS TOTALES EN CANTIDAD E IMPORTE DE SERVICIOS PRESTADOS, FACTURADOS Y DIFERENCIAS
/*--------------------------------------------------------------------------------------------------------*/
function changeChkFact(rowid, check) {
   var checkValue = (check.checked) ? '1' : '0';
   check.value = checkValue;
   $('#gridConciliacionesId').jqGrid('setCell', rowid, 'chkFact', checkValue);
   
   calculaDiferencia(rowid);

   $('#modificada').val("S");  

   Totaliza();
}

/*--------------------------------------------------------------------------------------------------------*/
// CALCULA LA DIFRENCIA DE SERVICIOS PRESTADOS Y FACTURADOS 
// NUEVA CONDICION, Y CALCULA LA DIFERENCIA ENTRE EL SERVICIO PRESTADO Y EL FACTURADO, COMO TAMBIEN EL 
// CALCULO DE LOS TOTALES EN CANTIDAD E IMPORTE DE SERVICIOS PRESTADOS, FACTURADOS Y DIFERENCIAS
/*--------------------------------------------------------------------------------------------------------*/
function calculaDiferencia(rowid) {
	console.log("calculaDiferencia");
   var ctServPrest = removeCommas($('#gridConciliacionesId').jqGrid('getCell', rowid, 'ctServPrest'));
   var imTotalPres = removeCommas($('#gridConciliacionesId').jqGrid('getCell', rowid, 'imPrecioTotalPresConv'));
   var chkPres     = $('#gridConciliacionesId').jqGrid('getCell', rowid, 'chkPres');
   var ctServFact  = removeCommas($('#gridConciliacionesId').jqGrid('getCell', rowid, 'ctServFact'));
   var imTotalFact = removeCommas($('#gridConciliacionesId').jqGrid('getCell', rowid, 'imPrecioTotalFact'));
   var chkFact     = $('#gridConciliacionesId').jqGrid('getCell', rowid, 'chkFact');
   var ctServDife  = 0; 
   var imServDife  = 0; 
	   
   if (chkFact == "1") {
	   ctServDife += ctServFact;
	   imServDife += imTotalFact;
   }
   if (chkPres == "1") {
	   ctServDife -= ctServPrest;
	   imServDife -= imTotalPres;
   }
   $('#gridConciliacionesId').jqGrid('setCell', rowid, 'ctServFactDife' , ctServDife);
   $('#gridConciliacionesId').jqGrid('setCell', rowid, 'imServFactDife' , imServDife);	   
   
   if (ctServDife != 0 || imServDife != 0) {
      $("#gridConciliacionesId").jqGrid('setRowData', rowid, false, {color:'red'});
   }  else {
      $("#gridConciliacionesId").jqGrid('setRowData', rowid, false, {color:'black'});
   }
}

/*--------------------------------------------------------------------------------------------------------*/
// MUESTRA EL DETALLE DEL SERVICIO PRESTADO SELECCIONADO EN LA GRILLA, YA SEA CON EL DOBLE CLICK, O DESDE  
// LA OPCION DETALLE REMITO 
/*--------------------------------------------------------------------------------------------------------*/
function viewDialogConciliacion(rowid) {
	var cdLoteServ         = $('#gridConciliacionesId').jqGrid('getCell', rowid, 'cdLoteServ');
	var cdOrdenServ        = $('#gridConciliacionesId').jqGrid('getCell', rowid, 'cdOrdenServ');
	var cdSectorSol        = $('#gridConciliacionesId').jqGrid('getCell', rowid, 'cdSectorSol');
	var cdSectorControl    = $('#gridConciliacionesId').jqGrid('getCell', rowid, 'cdSectorControl');
	var cdRemitoPres       = $('#gridConciliacionesId').jqGrid('getCell', rowid, 'cdRemitoPres');
	var fhRemito           = $('#gridConciliacionesId').jqGrid('getCell', rowid, 'fhRemito');
	var fhFinServ          = $('#gridConciliacionesId').jqGrid('getCell', rowid, 'fhFinServ');
	var cdProductoPres     = $('#gridConciliacionesId').jqGrid('getCell', rowid, 'cdProductoPres');
	var ctServPrest        = $('#gridConciliacionesId').jqGrid('getCell', rowid, 'ctServPrest');
	var cdUniValPres       = $('#gridConciliacionesId').jqGrid('getCell', rowid, 'cdUniValPres');
	var imPrecioUnitPres   = $('#gridConciliacionesId').jqGrid('getCell', rowid, 'imPrecioUnitPres');
	var imPrecioTotalPres  = $('#gridConciliacionesId').jqGrid('getCell', rowid, 'imPrecioTotalPres');
	var nbPiezaDesdePres   = $('#gridConciliacionesId').jqGrid('getCell', rowid, 'nbPiezaDesdePres');
	var nbPiezaHastaPres   = $('#gridConciliacionesId').jqGrid('getCell', rowid, 'nbPiezaHastaPres');
	var cdRemitoPadre      = $('#gridConciliacionesId').jqGrid('getCell', rowid, 'cdRemitoPadre');
	var nbAtributoRef1     = $('#gridConciliacionesId').jqGrid('getCell', rowid, 'nbAtributoRef1');
	var nbAtributoRef2     = $('#gridConciliacionesId').jqGrid('getCell', rowid, 'nbAtributoRef2');
	var nbObservaciones    = $('#gridConciliacionesId').jqGrid('getCell', rowid, 'nbObservaciones');
	var stLoteDet          = $('#gridConciliacionesId').jqGrid('getCell', rowid, 'stLoteDet');
	var cdConciliacionPres = $('#gridConciliacionesId').jqGrid('getCell', rowid, 'cdConciliacionPres');
	var fhModificacion     = $('#gridConciliacionesId').jqGrid('getCell', rowid, 'fhModificacion');
	var nbUsuarioModif     = $('#gridConciliacionesId').jqGrid('getCell', rowid, 'nbUsuarioModif');
	$("#cdLoteServ").val(cdLoteServ);
	$("#cdOrdenServ").val(cdOrdenServ);
	$("#cdSectorSol").val(cdSectorSol);
	$("#cdSectorControl").val(cdSectorControl);
	$("#cdRemitoPres").val(cdRemitoPres);
	$("#fhRemito").val(fhRemito);
	$("#fhFinServ").val(fhFinServ);
	$("#cdProductoPres").val(cdProductoPres);
	$("#ctServPrest").val(ctServPrest);
	$("#cdUniValPres").val(cdUniValPres);
	$("#imPrecioUnitPres").val(imPrecioUnitPres);
	$("#imPrecioTotalPres").val(imPrecioTotalPres);
	$("#nbPiezaDesdePres").val(nbPiezaDesdePres);
	$("#nbPiezaHastaPres").val(nbPiezaHastaPres);
	$("#cdRemitoPadre").val(cdRemitoPadre);
	$("#nbAtributoRef1").val(nbAtributoRef1);
	$("#nbAtributoRef2").val(nbAtributoRef2);
	$("#nbObservaciones").val(nbObservaciones);
	$("#stLoteDet").val(stLoteDet);
	$("#cdConciliacionPres").val(cdConciliacionPres);
	$("#fhModificacion").val(fhModificacion);
	$("#nbUsuarioModif").val(nbUsuarioModif);
	
    if (ctServPrest != 0) {
       $('#dialogConsultaServicio').dialog('option','title','Consultar Registro');
       $('#dialogConsultaServicio').dialog('open');
    }
}

