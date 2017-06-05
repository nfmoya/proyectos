// show a simple loading indicator
	/*jQuery('<div id="loader"><img src="${realpath}/../images/gears.gif" alt="loading..." /></div>')
		.css({position: "relative", top: "1em", left: "25em"})
		.appendTo("body")
		.hide();*/
$(document).ready(function() {
	//Inicializamos datepicker y resaltamos los dias con eventos 
	if($('#grantWelcomeView').val()==='Y') {
		initDatepicker();

		// Dialog que muestra formulario de Administración de Agenda
		$('#dialog-form').dialog({
			autoOpen: false,
			height: 'auto',
			width: 700,
			modal: true,
			buttons: {
				'Volver': function() {
					$(this).dialog('close');
				}
			},
			beforeClose: function(event, ui) { 
				beforeCloseDialog(); 
			}
		});
	}
});

function beforeCloseDialog(){
	$('#tasks_responseMsgs').hide();
	hideContentTask();
	cleanTaskGrid();
	//Se elimina el calendario y se crea nuevamente actualizando dias en que se hay un evento.
	destroyDatepicker();
	initDatepicker();
}

function hideContentTask(){ $('#contentTask').hide(); }

function refreshDatepicker(){ $('#ui-datepicker').datepicker('refresh'); }

function destroyDatepicker() { $('#ui-datepicker').datepicker('destroy');}

function initDatepicker() {
	callJsonAction('dayListEvents.action', '','loadDatepicker','getTaskErrorMessages');
}

var daysWithRecords = new Array();
function loadDatepicker(jsonData){
	daysWithRecords = [];
	daysWithRecords = jsonData.daysWithRecords;
	$('#ui-datepicker').datepicker({
		regional: 'es',
		dateFormat: 'dd M yy',
		altFormat: 'dd/mm/yy',
		altField: '#alt-ui-datepicker',
		changeMonth: true,
		changeYear: true,
        onSelect: function(dateText, instance)	{
	        // Se guarda la fecha seleccionada para su posterior uso.
			$('#selectedDateLabel').html(dateText);
			
			//Se carga la grilla de tareas antes de mostrar el dialog de Administracion de Agenda
			searchTasks();
			
			$('#dialog-form').dialog('open');
		},
		beforeShowDay: checkDayEvents,
		onChangeMonthYear: getDaysWithEvents
	});
	refreshDatepicker();
	
	$('#ui-datepicker td.specialDate_task_a a').removeClass('ui-state-default');
	$('#ui-datepicker td.specialDate_task_a a').removeClass('ui-state-active');
	$('#ui-datepicker td.specialDate_task_a a').removeClass('ui-state-hover');
	
	$('#ui-datepicker td.specialDate_task_p a').removeClass('ui-state-default');
	$('#ui-datepicker td.specialDate_task_p a').removeClass('ui-state-active');
	$('#ui-datepicker td.specialDate_task_p a').removeClass('ui-state-hover');
	
	$('#ui-datepicker td.specialDate_task_m a').removeClass('ui-state-default');
	$('#ui-datepicker td.specialDate_task_m a').removeClass('ui-state-active');
	$('#ui-datepicker td.specialDate_task_m a').removeClass('ui-state-hover');
}

/** @Deprecated */
function openSelectedDay(dateText){
	var dateTextStr = $.datepicker.formatDate('dd M yy', new Date(parseInt(dateText.substr(6,4)),parseInt(dateText.substr(3,2))-1,parseInt(dateText.substr(0,2))));
	$('#selectedDateLabel').html(dateTextStr);
	searchTasks(dateText);
	$('#dialog-form').dialog('open');
}

//Checkear los días que al menos tienen una tarea cargada
function getDaysWithEvents(theyear, themonth){
	//Obtenemos la lista de días seleccionados
	daysWithRecords = [];
	params = 'calendarYear='+theyear;
	params += '&calendarMonth='+themonth;
	callJsonAction('dayListEvents.action', params,'loadDatepicker','getTaskErrorMessages');
}

function checkDayEvents(thedate) {
	var theday = thedate.getDate();
	var bool = false;
	var identifier ='';
	for(var i=0; i < daysWithRecords.length;i++) {
		if(theday == daysWithRecords[i].day) {
			bool = true;
			identifier = daysWithRecords[i].identifier; 
			break;
		}
	}
	if(!bool){
		return [true,''];
	} else {
		return [true, getClassSpecialDate(identifier)];
	}
}

function getClassSpecialDate(identifier){
	return 'specialDate_task_'+identifier.toLowerCase();
}

//Funciones de la Grilla de Tareas
function searchTasks(auxDateSelected){
	cleanTaskGrid();

	$('#tasksGrid').show();
	
	var params='';
	if(auxDateSelected)
		params += 'dateSelected='+auxDateSelected;
	else
		params += 'dateSelected='+$('#alt-ui-datepicker').val();
	
	loadTaskGrid(params);
}

function refreshTasks() {
	searchTasks();
}

function cleanTaskGrid() {
	limpiarGrilla('gridTaskId','gridTaskPager','gridTask');
}

function getSelectedRowFromGrid(){
	return $('#gridTaskId').getGridParam('selrow');;
}

function getRowDataTask(){
	return $('#gridTaskId').getRowData(getSelectedRowFromGrid());
}

function resetTasksGrid(){
	$('#gridTaskId').resetSelection();
}

function showResponseErrorMessage(errorCode, errorMessage) {
	setErrorMsg($('#tasks_responseMsgs'), errorMessage);
	$('#tasks_responseMsgs').show();
}

function loadTaskGrid(params){
	try {
		showGrid({
			id:'gridTaskId',
			idPager:'gridTaskPager',
			url:'taskListToGridModel.action?'+params,
			colNames:['ID', 'Motivo', 'ID Motivo', 'Detalle', 'Fecha de Tarea', 'Estado'],
		    colModel:[{name:'id', witdh:0, align:'left', hidden:true},
		    {name:'reasonName', width:150, align:'left'},
	        {name:'reasonCode', witdh:100, align:'left', hidden:true},
	        {name:'description', width:300,align:'left'},
	        {name:'dateTask',  width:100, align:'left',sortable:false},
	        {name:'buildStatus', width:80, align:'left'}
		    ],
		    rowNum:5,
		    rowList:[5,7,10],
			sortName: 'reasonName',
		    caption: 'Tareas',
		    height: '100%',
		    selectRowFunction:'addButtonsToTaskGrid()'
		});
	
		addButtonsToTaskGrid();
	
	} catch(e) {
		jsError('loadTaskGrid(...)', e);
	}
}

function addButtonsToTaskGrid(){
	cleanButtonGridPager('gridTaskPager');
	if($('#grantABMTasks').val()=='Y') {
		$('#gridTaskId').navButtonAdd('#gridTaskPager', 
			{	caption: 'Agregar',
				buttonicon: 'ui-icon ui-icon-plus',
				onClickButton: function() {
					try {
						resetTasksGrid();
						viewFormToEditTask("N");
						//viewFormToEditTask();
					} catch (e) {
						jsError('loadTaskGrid(...)', e);
					}
				}, 
				position:'last', 
				title:'Agregar Tarea'
			}
		);
	
		if(getSelectedRowFromGrid() != null){
			var buildStatus = getRowDataTask()['buildStatus'];
			if(buildStatus.substring(0,1)==='P'){
				$('#gridTaskId').navButtonAdd('#gridTaskPager',
					{	caption: 'Editar',
						buttonicon: 'ui-icon ui-icon-pencil',
						onClickButton: function() {
							try {
								if(getSelectedRowFromGrid() == null)
									seleccioneRegistro();
								else
									viewFormToEditTask("Y");
									//viewFormToEditTask();
							} catch(e) {
								jsError('loadTaskGrid(...)', e);
							}
						},
						position:'last',
						title:'Editar Tarea'
					}
				);
					
				$('#gridTaskId').navButtonAdd('#gridTaskPager',
					{	caption:'Borrar',
						buttonicon:'ui-icon ui-icon-trash',
						onClickButton: function() {
							try {
								if(getSelectedRowFromGrid() == null)
									seleccioneRegistro();
								else
									viewDialogDeleteTask();
									
							} catch(e) {
								jsError('loadTaskGrid(...)', e);
							}
						},
						position:'last',
						title:'Borrar Tarea'
				});
			} else if($('#grantTasksView').val()!='Y') {
				resetTasksGrid();
			}
		}
	}
	if($('#grantTasksView').val()=='Y') {
		$('#gridTaskId').navButtonAdd('#gridTaskPager',
			{	caption:'Detalle',
				buttonicon:'ui-icon ui-icon-note',
				onClickButton: function() {
					if(getSelectedRowFromGrid() != null)
						loadTaskDetail();
					else
						seleccioneRegistro();
				}
		});
	}
}

function loadTaskDetail(){
	$('body').dialogcfs({
		vTitle: 'Detalle de la Tarea',
	    vHeight: 500,
	    vWidth: 400,
	    dialogMsg: '',
	    vText: false,
	    vFunctionAceptar: 'true'
	});
	
	$('body').dialogcfs("open");

	callAction('loadTaskDetails.action','id='+getRowDataTask()['id'],'dialogAvisoGenerico',false,null,null);
}

//@Deprecated: Validar Fecha de Tarea
var errorMessage = 'Fecha de Tarea inv&aacute;lida !\n' + 'La Fecha de Tarea debe ser mayor o igual a la fecha actual';
function validateDateOfTask(dateText, cal) {
	var defaultDate = $('#dateTaskForDisplay').datepicker('option', 'defaultDate');
	var auxDate = $.datepicker.formatDate('yy-mm-dd', defaultDate);
	var date = $('#dateTaskForDisplay').datepicker('getDate');
	if(date != null && date < auxDate) {
	        alert(errorMessage);
	        cal.value = auxDate;
	}
}

function viewFormToEditTask(modificable){
	callAction('initTask.action', 'modificable='+modificable, 'contentTask', false, 'initTaskForm', null);
}

function initTaskForm(jsonData) {
	resetTaskForm();
    try {
		// Cargar el selector 'contentTask' con el formulario de creación/edición de tareas
		$('#contentTask').html(jsonData);
		
		// Aplicamos formato de widget a los tags html
		$('#dateTaskForDisplay').datepicker({
			regional:'es',
			defaultDate: new Date(),
			minDate: +0,// Se establece que la fecha mínima sea la fecha actual
			showAnim:'',
			showOn: 'button',
			duration:0,
			dateFormat: 'dd/mm/yy',
			altFormat: 'ddmmyy',
			altField: '#alt-dateTask',
			onSelect: function (dateText, instance){
				var defaultDate = $(this).datepicker('option', 'defaultDate');
				var auxDefaultDate = $.datepicker.formatDate('dd/mm/yy', defaultDate);
				//@see: se precisa hacer algo aquí???
			}
		}).next('button').
			text('&nbsp;').
			addClass('ui-button-text').
			button({ icons:{primary:'ui-icon ui-icon-calendar'} }).
			removeClass().
			addClass('ui-button ui-widget ui-state-default ui-button-icon-only ui-corner-right ui-button-icon');
		
		$('#selectedReasonCode').selectmenu({style:'dropdown'});
		$(':button').button();
		
		// Ejecutamos este evento en caso de que usuario haya hecho clic en Editar
		if(getSelectedRowFromGrid() != null) {
			var rowData = getRowDataTask();
			$('#taskId').val(rowData['id']);
			$('#selectedReasonCode').selectmenu('setValue',rowData['reasonCode']);
			$('#selectedReasonCode').selectmenu('disable');
			
			//@see Seteamos la fecha de la tarea
			$('#dateTaskForDisplay').datepicker('setDate', rowData['dateTask']);
			$('#description').val(rowData['description']);
			
			// Buscamos los involucrados de la tarea
			searchReceiversFromTask();
			
			$('#link_create').text('Editar');
			$('#link_create').button().click(function() {
				submitForm();
			});
			
		}else{
			$('#link_create').button().click(function() {
				submitForm();
			});
		}
		$('#link_cancel').button().click(function(){
			resetTaskForm();
			$('#contentTask').fadeOut(1000);
		});
		//Agregamos un efecto al formulario de Carga y Edición de Tarea 
		//$('#contentTask').toggle('blind', 500);
		$('#contentTask').fadeIn(1000);
		
    } catch (e) {
    	jsError('initTaskForm',e.message);
    }
    return true;
}

function searchReceiversFromTask() {
	callJsonAction('searchReceiversFromTask.action', 'id='+getRowDataTask()['id'], 'drawReceivers','getTaskErrorMessages');
	return false;
}

function drawReceivers(jsonData){
	var receivers = jsonData.receivers;
	
	//http://api.jquery.com/jQuery.inArray/#comment-31436032, soporte para otros browsers
	$('input:checkbox[name=\'selectedReceivers\']').each(function(index) {
		$this = $(this);
		$nestedId = $this.attr('id');
		$val = $nestedId.substring('receiver_'.length,$nestedId.length);
		if($.inArray(parseInt($val), receivers) > -1){
			$this.attr('checked','checked');
			$this.attr('disabled','disabled');
		} else {
			$this.attr('disabled','disabled');
		}
	});
}

/*Submit del formulario de tareas */
function submitForm() {
    var params='';
    
    params += 'id='+$('#taskId').val();
	params += '&selectedReasonCode='+ $('#selectedReasonCode').val();
	params += '&dateTask='+$('#dateTaskForDisplay').val();
	params += '&description='+$('#description').val();
	
	var auxSelectedReceivers=[];
	$('input:checkbox[name=\'selectedReceivers\']:checked').each(function(index) { 
		$nestedId = $(this).attr('id');
		$val = $nestedId.substring('receiver_'.length,$nestedId.length);
		auxSelectedReceivers.push($val);
	});
	params += '&selectedReceivers='+auxSelectedReceivers;
	
	callJsonAction('saveTask.action', params, 'getSuccessTask', 'getTaskErrorMessages');
}

function viewDialogDeleteScholarshipsType(){
	fDialog('Eliminar Tarea', 'deleteTask()', 'resetTasksGrid()');
}

function deleteTask() {
	callJsonAction('deleteTask.action', 'id='+getRowDataTask()['id'], 'getSuccessTask', 'getTaskErrorMessages');
}

function resetTaskForm() {
	try {
		$('#task_responseMessage').hide();
		$(':input','#editTask').not(':button, #taskId').val('').removeAttr('checked').removeAttr('selected');
		$('#selectedReasonCode').selectmenu('setValue', 0);
		$('#selectedReasonCode').selectmenu('destroy');
	} catch(e) { 
		jsError('resetTaskForm',e.message);
	}
}

function getSuccessTask(json) {
	if(isSuccessResult(json.result.errorCode)){
		setSuccessMsg($('#tasks_responseMsgs'), json.result.errorDesc);
		$('#tasks_responseMsgs').show();
		//$('#task_responseMessage').show(); TODO: Mostrar mensaje de exito a la izquierda de la fecha
		hideContentTask();
		refreshTasks();
	} else if(isWarningResult(json.result.errorCode) || isValidationError(json.result.errorCode)) {
		getTaskErrorMessages(json.result.errorCode, json.result.errorDesc);
	}
}

function getTaskErrorMessages(errorCode, errorMessage) {
	setErrorMsg($('#task_responseMessage'), errorMessage);
	$('#task_responseMessage').show();
}

function viewDialogDeleteTask(){
	fDialog('Eliminar Tarea', 'deleteTask()', 'resetTasksGrid()');
}

function deleteTask(){
	callJsonAction('deleteTask.action', 'id='+getRowDataTask()['id'], 'getDelTaskMsg', 'getTaskErrorMessages');
}

function getDelTaskMsg(json){
	if(isSuccessResult(json.result.errorCode)) {
		setSuccessMsg($('#tasks_responseMsgs'), json.result.errorDesc);
		$('#tasks_responseMsgs').show();
		refreshTasks();
	} else if(isWarningResult(json.result.errorCode) || isValidationError(json.result.errorCode)) {
		showResponseErrorMessage(json.result.errorCode, json.result.errorDesc);
	}
}