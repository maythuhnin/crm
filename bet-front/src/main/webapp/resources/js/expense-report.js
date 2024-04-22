let expenseReportDatatable = {}; 
let expenseReportList = [];
let expenseList = [];
let busList = [];

$(init);

function init() {
	
	bindBusDropDown();
	initExpenseReportDatatable();
	bindSearch();
	
}


function bindBusDropDown(){
	
			$("#searchBus").empty();
			
			$.ajax({
				url : getPathName() + "/bus/api/dropdown",
				type : "GET",
				contentType: "application/json",
				dataType: 'json',
				success : function(data) {
					if(null != data && data.length > 0){
					
					$("#searchBus").append("<option value=''></option>")
					
					$(data).each(function( index, bus ) {
						$("#searchBus").append("<option value='" + bus.id + "'>" + bus.licensePlate + "</option>");				
					});
					
					busList = data;
					
			}
				},
				complete : function(data){
					$('#searchBus').select2({
						theme: 'bootstrap4',
				    	allowClear: true
					});
				}
			});
}


function bindSearch(){
	 
	$(".dataTables_filter").addClass("d-none");
	
	$("#searchBox").on('input', function() {
		expenseReportDatatable.search($("#searchBox").val()).draw();
	});
	
	$("#clearFilters").on('click', function() {	
		$("#searchBox").val("");
		expenseReportDatatable.search("").draw();
	});
}



function initExpenseReportDatatable() {
	
	expenseReportDatatable = $('#expenseReportDatatable').DataTable({
		lengthChange: false, 
		autoWidth: false,
      	dom: 'Bfrtip',
		ajax: {
	        url:  getPathName() + '/daily-expense/api/datatable',
	        type: "GET",
	         data : function(d) {
				
			},
	        dataSrc: 'responseData',
	        dataType: "json"
	    },
	    "order": [0],
	    scrollX:        true,
        scrollCollapse: true,
         columnDefs: [{ width: '20%', targets: 3 }],
	    columns: [
		 {
                className: 'dt-control',
                orderable: false,
                data: null,
                defaultContent: '',
	    	bSortable: false
            },  
         { mData : function(data, type, full, meta) {
				return data.dateRange;
			},
		    sClass: "text-center"},
		{ mData : function(data, type, full, meta) {
				return getPathText(data.path);
			},
		    sClass: "text-center"}, 
		{ mData : function(data, type, full, meta) {
				return isEmpty(data.bus) ? "-" : data.bus;
			},
		    sClass: "text-center"},      
		 { mData : function(data, type, full, meta) {
				return isEmpty(data.bus) ? "-" : data.bus;
			},
		    sClass: "text-center"},      
		{ mData : function(data, type, full, meta) {
				return isEmpty(data.bus) ? "-" : data.bus;
			},
		    sClass: "text-center"}, 
	{ mData : function(data, type, full, meta) {
				return isEmpty(data.bus) ? "-" : data.bus;
			},
		    sClass: "text-center"},      
		    { mData : function(data, type, full, meta) {
				return isEmpty(data.bus) ? "-" : data.bus;
			},
		    sClass: "text-center"},      
		    { mData : function(data, type, full, meta) {
				return isEmpty(data.bus) ? "-" : data.bus;
			},
		    sClass: "text-center"},           
	 
		      
	    ]
	});
	
	$('#expenseReportDatatable tbody').on('click', 'td.dt-control', function () {
        var tr = $(this).closest('tr');
        var row = expenseReportDatatable.row(tr);
 
        if (row.child.isShown()) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        } else {
            // Open this row
            row.child(bindExpenseSubTable(row.data().id)).show();
            tr.addClass('shown');
        }
        
    });
		
}

function bindExpenseSubTable(pathId){
	
	var subtableList = getExpense(pathId);
	var returnHtml = "";
	
	returnHtml += '<table cellpadding="5" cellspacing="0" border="0" class="table table-bordered sub-table">' +
        	'<tr>' +
	        '<th class="text-center">Expense Type</th>' +
	        '<th class="text-right">Amount</th>' +
       		 '</tr>';
       		 if(subtableList.length > 0){
		 		$.each(subtableList, function(key, value) {
					returnHtml += '<tr>' +
		        	'<td class="text-center">' + value.name + '</td>' +
			        '<td class="text-right">' + value.amount.toLocaleString("en") + ' Ks </td>' +
			        '</tr>'
	        	});
				}else{
					returnHtml += '<tr><td colspan="2" class="text-center">No data available in table</td></td>';
				}
         
       returnHtml +=  '</table>';
       
       return returnHtml;
}

function getExpense(pathId){
	
	var returnList = [];
	
		$.ajax({
			url:  getPathName() + '/fixed-expense/amount/api/datatable',
	        type: "POST",
	         data : {
				pathId : parseInt(pathId)
			},
	        dataSrc: 'responseData',
	        dataType: "json",
			async : false,
			success : function(data) {
				
				returnList = data;
	       
			}
	});
	
	return returnList;
	
}


function initExpenseDatatable() {
	
	expenseDatatable = $('#expenseDatatable').DataTable({
		lengthChange: false, 
		autoWidth: false,
      	dom: 'Bfrtip',
		data: expenseList,
	    "order": [0],
	    scrollX:        true,
        scrollCollapse: true,
         columnDefs: [{ width: '10%', targets: 2 }],
	    columns: [
		{ mData : function(data, type, full, meta) {
				return data.name;
			},
		    sClass: "text-center"}, 
		    { mData : function(data, type, full, meta) {
				return data.amount.toLocaleString("en") + " Ks";
			},
		    sClass: "text-right"},      
	 
		      { mData : function(data, type, full, meta) {
	
				return '<button type="button" class="btn btn-outline-danger delete-expense" data-id="' + data.expenseId + '" title="Delete Expense"><i class="fas fa-trash"></i></button>';
			},
		    sClass: "text-center",
	    	bSortable: false }
	    ],
		"fnDrawCallback": function (){
			
			$( ".delete-expense" ).on( "click", function() {
				expenseList.splice(getIndexFromExpenseListById(expenseList, parseInt($(this).attr("data-id")), 1)); 
				expenseDatatable.clear().draw();
				expenseDatatable.rows.add(expenseList); // Add new data
				expenseDatatable.columns.adjust().draw();
			});
	   } 
	});
		
}


function getIndexFromExpenseListById(list, id){
	var returnIndex = {};
	$.each(list, function(key, value) {
		if(value.expenseId == id){
			returnIndex = key;
		}
	});
	
	return returnIndex;
}

function getPathText(path){
	var pathText = "";
	var pathIds = path.split(",");
	
	$(pathIds).each(function( index, pathId ) {
		$(destinationList).each(function( index, destination ) {
			if(pathId == destination.id){
				pathText += (pathText != "" ? ' <i class="fas fa-exchange-alt"></i> ' : "") + destination.name;
			}
		});
	});
	
	return pathText;
}

function getBusText(bus){
	var busText = "";
	var busIds = bus.split(",");
	
	$(busIds).each(function( index, busId ) {
		$(busList).each(function( index, bus ) {
			if(busId == bus.id){
				busText += (busText != "" ? ' , ' : "") + bus.licensePlate;
			}
		});
	});
	
	return busText;
}
