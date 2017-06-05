/*--------------------------------------------------------------------------------------------------------*/
// GRILLA CONCILIACION
/*--------------------------------------------------------------------------------------------------------*/

/*--------------------------------------------------------------------------------------------------------*/
// CARGA GRILLA CONCILIACION
/*--------------------------------------------------------------------------------------------------------*/
function loadConciliacionesGrid(){
	var params = '';
	params += 'cdProveedor='+$('#filtroProveedorList').val();
	params += '&cdSector='+$('#filtroSectorList').val();
	params += '&cdPeriodoFact='+$('#filtroPeriodoList').val();	
	params += '&stConciliacion='+$('#filtroSituacionConciliacionList').val();	
	params += '&cdConciliacion='+$('#cdConciliacion').val();
//alert(params);
	try {
		showGrid({
			id : 'gridConciliacionesId',
 			idPager : 'gridConciliacionesPager',
			url : 'JsonNoMediblesList.action?'+params,
			colNames : [ 'Producto' , 'Periodo'  , 'Cant Fact', 'Unidad'   , 'Val Fact', 
			             'Concilia' , 'Cant Fact', 'Unidad'   , 'Val Fact' , '% Desvio Adm', 
			             'Valor % Desvio', 'Conc Fact', 'Dif', 'Dif Valor', '', 'Observaciones', 'Sol', ''
            ],
            colModel : [ 
                {name : 'cdProducto'       , width :  80, align : 'right' , hidden : false }, 
                {name : 'cdPeriodoAnt'     , width :  50, align : 'center', hidden : false },
                {name : 'ctServFactAnt'    , width :  60, align : 'right' , hidden : false ,
   					    formatter: qDifFormatter
//  	           	    formatter: "number", 
//	                    formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}
                },
                {name : 'cdUniValAnt'      , width :  40, align : 'right' , hidden : false },
                {name : 'imPrecioTotalAnt' , width :  90, align : 'right' , hidden : false ,
   					    formatter: iDifFormatter
//  	           	    formatter: "number", 
//	                    formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}
                },
                {name : 'cdConciliacionAnt', width :  40, align : 'right' , hidden : false }, 
                {name : 'ctServFactAct'    , width :  60, align : 'right' , hidden : false ,
   					    formatter: qDifFormatter
//  	           	    formatter: "number", 
//	                    formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}
                },
                {name : 'cdUniValAct'      , width :  40, align : 'right' , hidden : false },
                {name : 'imPrecioTotalAct' , width :  90, align : 'right' , hidden : false ,
   					    formatter: iDifFormatter
//  	           	    formatter: "number", 
//	                    formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}
                },
                {name : 'nuPorcVarMax'     , width :  50, align : 'right' , hidden : false }, 
                {name : 'nuPorcVarVal'     , width :  50, align : 'right' , hidden : false , 
   					    formatter: iDifFormatter
                },
                {name : 'cdConciliacionAct', width :  40, align : 'right' , hidden : false }, 
                {name : 'imDiferencia'     , width :  70, align : 'right' , hidden : false ,
   					    formatter: iDifFormatter
//  	           	    formatter: "number", 
//	                    formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}
                },
                {name : 'chkDif'           , width :  35, align : 'center', hidden : true  },
                {name : 'idChkDif'         , width :  30, align : 'center', hidden : false ,
						formatter : checkboxFormatter, sortable : false, classes:"grid-col"},                
                {name : 'nbObservaciones'  , width : 100, align : 'left'  , hidden : false },
                {name : 'tpSolucion'       , width :  40, align : 'left'  , hidden : false },
                {name : ''                 , width :  20, align : 'center', index: 'edit', 
                	                         sortable:false, formatter: displayButtons }
            ],
		    width: 1050,
            rowNum:1000,
            sortName : 'conciliacion',
            caption : "Conciliaci\u00f3n",
            scrollerbar:true,
            height :'400',
            multiselect: false,
            loadonce : true,
			viewrecords : true,
            editurl: 'clientArray',
            shrinkToFit: false,
			footerrow : true,
		    gridComplete: function() {
               var rowData = $("#gridConciliacionesId").getDataIDs();
               for (var i=0; i < rowData.length; i++) {
                  var cl = rowData[i];
                  var imPrecioTotalAnt  = $('#gridConciliacionesId').getCell(cl, 'imPrecioTotalAnt');
                  var imPrecioTotalAct  = $('#gridConciliacionesId').getCell(cl, 'imPrecioTotalAct');
                  var nuPorcVarMax      = removeCommas($('#gridConciliacionesId').getCell(cl, 'nuPorcVarMax'));
                  var nuPorcVarVal      = removeCommas($('#gridConciliacionesId').getCell(cl, 'nuPorcVarVal'));
                  var imDiferencia      = removeCommas($('#gridConciliacionesId').getCell(cl, 'imDiferencia'));
                  var nbObservaciones   = $.trim($('#gridConciliacionesId').getCell(cl, 'nbObservaciones'));
/*				  
                  if ($.trim(nbObservaciones) == '') {
                      imDiferencia = removeCommas(imPrecioTotalAct) - removeCommas(imPrecioTotalAnt);                	  
                  }
                  var nuPorcVarVal      = 0;
                  if (imPrecioTotalAnt == 0 ) {
                	  if (imPrecioTotalAct == 0) {
                          nuPorcVarVal = 0;
                	  } else {
                          nuPorcVarVal = 100;
                	  }
                  } else {
                      nuPorcVarVal = ((removeCommas(imPrecioTotalAct)/removeCommas(imPrecioTotalAnt))-1)*100;
					  if (nuPorcVarVal < 0) {
						  nuPorcVarVal *= -1;
					  }
                  }
              	  $('#gridConciliacionesId').jqGrid('setCell', cl, 'imDiferencia', imDiferencia);
              	  $('#gridConciliacionesId').jqGrid('setCell', cl, 'nuPorcVarVal', nuPorcVarVal);
*/
				  if (nbObservaciones == '') {
                     if (imDiferencia != 0 && (nuPorcVarVal>nuPorcVarMax)) {
                   	    $("#gridConciliacionesId").jqGrid('setRowData', cl, false, {color:'red'});
                     } else {
                 	    $("#gridConciliacionesId").jqGrid('setRowData', cl, false, {color:'black'});      	  
					    $("#chkDif_"+i).attr("disabled", "disabled");
                     }
				  } else {
					 $("#chkDif_"+i).attr("disabled", "disabled");
				  }
//				 $('#gridConciliacionesId').jqGrid('setCell', cl, 'nbObservaciones', i);
				  
//                $("#chkDif_"+i).attr("disabled", "disabled");
//                $("#chkDif_"+i).addClass('ui-state-disabled');
//                alert("#chkDif_"+i);
               }
               
               var height; //$(window).height();
               if (rowData.length <= 50) {
            	  height = 23.52 * rowData.length; 
               } else {
             	  height = 400; 
               }
               $("#gridConciliacionesId").jqGrid("setGridHeight",height); 
               $('#modificada').val("N");  			   
            }
		});
		$("#gridConciliacionesId").jqGrid('setGroupHeaders', {
			useColSpanStyle : true,
			groupHeaders : [ {
				startColumnName : 'cdProducto',
				numberOfColumns : 6,
				titleText : '<p style="text-align:center">Periodo Anterior</p>'
			}, {
				startColumnName : 'ctServFactAct',
				numberOfColumns : 6,
				titleText : '<p style="text-align:center">'+$('#filtroPeriodoList').val()+'</p>'
			}, {
				startColumnName : 'imDiferencia',
				numberOfColumns : 4,
				titleText : '<p style="text-align:center">Diferencias</p>'
			}]
		});
//		addButtonsToConciliacionesGrid();	
		$('.ui-jqgrid-title').css('width', '100%');

 		if ($("#saveGrant").val() == 'S') {
	       // Si el Estado de Conciliacion es Pendiente o Aprobada habilito el boton Grabar
	       var stConciliacion = $('#stConciliacion').val();
	       if ((stConciliacion == 'PEN' || stConciliacion == 'GRA')) {
	          $("#btnGrabar").removeClass('ui-state-disabled');
	          $("#btnGrabar").removeAttr("disabled");
	    	  $("#btnAprobar").removeClass('ui-state-disabled');
	    	  $("#btnAprobar").removeAttr("disabled");
	    	  $("#checkAll").removeClass('ui-state-disabled');
			  $("#checkAll").removeAttr("disabled");
		   } else {
	    	  $("#btnGrabar").addClass('ui-state-disabled');
	    	  $("#btnGrabar").attr("disabled", "disabled");
	    	  $("#btnAprobar").addClass('ui-state-disabled');
	    	  $("#btnAprobar").attr("disabled", "disabled");
              $("#checkAll").attr("disabled", "disabled");
              $("#checkAll").addClass('ui-state-disabled');			   
	       }
	    }
		Totaliza();	
	} catch(e) {
		jsError('loadConciliacionesGrid(...)', e);
	}
}

$('#checkAll').click(function() {
    var chkDif  = '';
	if ($(this).is(':checked')) {
        $("#btnObsAll").removeClass('ui-state-disabled');
        $("#btnObsAll").removeAttr("disabled");
		chkDif  = '1';
	} else {
	    $("#btnObsAll").addClass('ui-state-disabled');
	    $("#btnObsAll").attr("disabled", "disabled");		
		chkDif  = '0';
	}
    var rowData = $("#gridConciliacionesId").getDataIDs();
	for (var i=0; i < rowData.length; i++) {
		if ($("#chkDif_"+i).is(":enabled")) {
			$('#gridConciliacionesId').jqGrid('setCell', rowData[i], 'chkDif', chkDif);
		}
/*		
		if (chkDif == '1') {
			$("#chkDif_"+i).attr("disabled", "disabled");			
		} else {
//			$("#chkDif_"+i).removeAttr("disabled");
		}
*/		
	}
	$('#checkAll').attr("checked", false);
    $('#modificada').val("S");	
    if (chkDif == '1') {
        editAllDiferencias();    
    }
});

function displayButtons(cellvalue, options, rowObject) {
	var edit = '';
	if ($('#stConciliacion').val() == 'APR') {
	   edit = "<img src='${realpath}/../images/lapiz.png' alt='editar' class=\"lapiz\" onclick=\"#\" />";
	} else {
	   edit = "<img src='${realpath}/../images/lapiz.png' style='cursor:pointer;' alt='editar' class=\"lapiz\" onclick=\"editDiferencia('"+options.rowId+"');\"  />";	   
	}
    return edit;
}

function editDiferencia(rowid) {
	$("#rowIdDiferencia").val(rowid);	
	$('#solucionDifListEdit').selectmenu('destroy');	
	$('#solucionDifListEdit').selectmenu({style:'dropdown', width:'250px'});
    $('#solucionDifListEdit').selectmenu('disabled', false);	
	
   	var cdProducto      = $('#gridConciliacionesId').jqGrid('getCell', rowid, 'cdProducto');
   	var nbObservaciones = $.trim($('#gridConciliacionesId').jqGrid('getCell', rowid, 'nbObservaciones'));
	var tpSolucion      = $('#gridConciliacionesId').jqGrid('getCell', rowid, 'tpSolucion');
	
//	$("#solucionDifListEdit").selectmenu('setValue', tpSolucion);   	
   	$("#nbObservaciones").val(nbObservaciones);
   	$('#dialogDiferenciaConciliacion').dialog('option','title','Editar Diferencia - Producto '+cdProducto);
   	$('#dialogDiferenciaConciliacion').dialog('open');
}

function editAllDiferencias() {
	$("#rowIdDiferencia").val(9999999);	
	$("#nbObservaciones").val($("#nbObservacGeneral").val()); 
	$("#tpSolucion").val($("#tpSolucionGeneral").val());
	$('#solucionDifListEdit').selectmenu('destroy');
	$('#solucionDifListEdit').selectmenu({style:'dropdown', width:'250px'});	
    $('#solucionDifListEdit').selectmenu('disabled', false);	
	
   	$('#dialogDiferenciaConciliacion').dialog('option','title','Editar Diferencias - SELECCIONADOS');
   	$('#dialogDiferenciaConciliacion').dialog('open');
}


/*--------------------------------------------------------------------------------------------------------*/
//AGREGA BOTONERA EN LA GRILLA PARA VER EL DETALLE DEL REMITO SELECCIONADO
/*--------------------------------------------------------------------------------------------------------*/
function addButtonsToConciliacionesGrid() {
   var title = 'Editar Diferecia';
   $('#gridConciliacionesId').navButtonAdd('#gridConciliacionesPager', {
   caption: 'Editar Diferecia',
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
function checkboxFormatter(cellvalue, options, rowObject) {
   var bchk     = '';
   var disabled = '';
   cellvalue    = cellvalue + "";
   cellvalue    = rowObject.chDif;
   bchk         = cellvalue == '1' ? "checked='checked' " : " ";
   if ($('#stConciliacion').val() == 'APR') {
	   disabled = "disabled='disabled' ";
   }
   i = (options.rowId-1);
   return "<input class='habilitar' id='chkDif_"+i+"' type='checkbox'" + bchk + disabled
            + "onclick='changeChk(" + options.rowId + ",this)'"
            + " value='" + cellvalue + "' />";
}

/*--------------------------------------------------------------------------------------------------------*/
// FUNCION UTILIZADA CUANDO SE PRESIONA EL CHECKBOX DE SERVICIOS PRESTADOS, LO CHEQUEA O NO DE ACUERDO A 
// NUEVA CONDICION, Y CALCULA LA DIFERENCIA ENTRE EL SERVICIO PRESTADO Y EL FACTURADO, COMO TAMBIEN EL 
// CALCULO DE LOS TOTALES EN CANTIDAD E IMPORTE DE SERVICIOS PRESTADOS, FACTURADOS Y DIFERENCIAS
/*--------------------------------------------------------------------------------------------------------*/
function changeChk(rowid, check) {
   var checkValue = (check.checked) ? '1' : '0';
   check.value = checkValue;
   $('#gridConciliacionesId').jqGrid('setCell', rowid, 'chkDif', checkValue);
   $('#modificada').val("S");     
   if (checkValue == '1') {
	   editDiferencia(rowid); 
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
