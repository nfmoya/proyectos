// ******************************************************************************* 
// Descripcion : Define el estilo y tipo de paginaci�n Para el JQGrid
// Autor : David Alberto Valdez Clavijo                                       
// Fecha Creacion : 28/06/2011                                                 
// ******************************************************************************** 

	function showGrid(data){ 
		try{
			var lUrl;
			var order;
			if(data.sortorder == 'desc')
				order = data.sortorder;
			else
				order = 'asc';
			if (data.url.indexOf('=') >= 0){
				lUrl = data.url + '&rowsName=' + data.id;
			} else {
				lUrl = data.url + 'rowsName=' + data.id;
			}
			jQuery('#' + data.id).jqGrid({
					url:lUrl
					,datatype: "json"
					,mtype: 'POST'
					,colNames: data.colNames 
					,colModel: data.colModel
					,rowNum: data.rowNum
					,loadui: "enable"
					,emptyrecords: "Sin registros"
					,rowList: data.rowList
					,sortname: data.sortName
					,multiselect: false						
					,viewrecords: true
					,sortorder: order
					,caption: data.caption
					,pager: '#' + data.idPager
					,height: data.height
					,width: data.width
					,editable: data.editable
					,editurl: data.editurl
//					,remove autowidth: true
					,footerrow : data.footerrow  
					,shrinkToFit: false
					,jsonReader:{
						    root:"rowsToShow",
						    page: "page",
						    total: "total",
						    records: "records",
						    repeatitems: false}
					,gridComplete: data.gridComplete
//					,onSelectRow: function(row_id) {
//							eval(data.selectRowFunction);
//		    		},
		    		,onSelectRow: function (id) {
		    			data.ondblClickRow;
		    		},
		    		ondblClickRow: data.ondblClickRow,
		    		click: data.click,
		    		loadComplete: function(){
		    			if(data.loadCompleteFunc)
		    				eval(data.loadCompleteFunc);
		    		}
					}).navGrid('#' + data.idPager, {refresh:false,edit:false,add:false,del:false,search:false});
					
			var $x1 = $(".ui-jqgrid-labels");
			$x1.css('font-size','9px');
			var $x = $(".ui-pg-table");
			$x.css('font-size','9px');
		}catch(e){
			alert('Error M�todo showGrid: '+e.message);
		}
	}

