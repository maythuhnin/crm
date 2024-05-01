let expenseReportDatatable = {}; 
let expenseReportList = [];
let expenseList = [];
let busList = [];

$(init);

function init() {
	
	bindBusDropDown();
	getDestinationList();
	bindSearch();
	
}


function getDestinationList(){
	
			$.ajax({
				url : getPathName() + "/destination/api/dropdown",
				type : "GET",
				contentType: "application/json",
				dataType: 'json',
				success : function(data) {
					if(null != data && data.length > 0){
					
					
						destinationList = data;
						
						
					}
				},
				complete : function(data){
					initExpenseReportDatatable();
				}
				
				
			});
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
					
					$("#searchBus").append("<option value='0'></option>")
					
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
					
					$("#searchBus").on('change', function() {
						expenseReportDatatable.clear().draw();
						expenseReportDatatable.ajax.reload();
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
		dom: 'Bfrtip',
		lengthChange: false, 
		autoWidth: true,
		ajax: {
	        url:  getPathName() + '/daily-expense/api/datatable',
	        type: "POST",
	        data : function(d) {
				d.busId = $("#searchBus").val();
			},
	        dataSrc: 'responseData',
	        dataType: "json"
	    },
	    processing: true,
        serverSide: false,
	    "order": [1],
	    scrollX:        false,
        scrollCollapse: true,
	    paging: false,
	    scrollY: '50vh',
	    responsive: false,
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
				return isEmpty(getCurrencyFormat(data.onPaperIncomeLeave)) ? "-" : getCurrencyFormat(data.onPaperIncomeLeave);
			},
		    sClass: "text-right"},      
		 { mData : function(data, type, full, meta) {
				return isEmpty(getCurrencyFormat(data.onPaperIncomeReturn)) ? "-" : getCurrencyFormat(data.onPaperIncomeReturn);
			},
		    sClass: "text-right"},      
		{ mData : function(data, type, full, meta) {
				return isEmpty(getCurrencyFormat(data.inHandCash)) ? "-" : getCurrencyFormat(data.inHandCash);
			},
		    sClass: "text-right"}, 
			{ mData : function(data, type, full, meta) {
				return getCurrencyFormat((data.onPaperIncomeLeave + data.onPaperIncomeReturn) - data.inHandCash);
			},
		    sClass: "text-right"},      
		    { mData : function(data, type, full, meta) {
				return isEmpty(getCurrencyFormat(data.extraIncome)) ? "-" : getCurrencyFormat(data.extraIncome);
			},
			
		    sClass: "text-right"},  
		     { mData : function(data, type, full, meta) {
				return isEmpty(getCurrencyFormat(data.expenseTotal)) ? "-" : getCurrencyFormat(data.expenseTotal);
			},
			
		    sClass: "text-right"},     
		    { mData : function(data, type, full, meta) {
				return getCurrencyFormat((data.onPaperIncomeLeave + data.onPaperIncomeReturn + data.extraIncome) - data.expenseTotal);
			},
		    sClass: "text-right"},           
	 
		      
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

function bindExpenseSubTable(dailyExpenseId){
	
	var total = 0;
	var subtableList = getExpense(dailyExpenseId);
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
			        '</tr>';
			        
			        total = total + value.amount;
	        	});
	        	returnHtml += '<tr>' +
		        	'<th class="text-right">Total : </th>' +
			        '<td class="text-right">' + total.toLocaleString("en") + ' Ks </td>' +
			        '</tr>'
	        	
				}else{
					returnHtml += '<tr><td colspan="2" class="text-center">No data available in table</td></td>';
				}
         
       returnHtml +=  '</table>';
       
       return returnHtml;
}

function getExpense(dailyExpenseId){
	
	var returnList = [];
	
		$.ajax({
			url:  getPathName() + '/daily-expense/api/subtable',
	        type: "POST",
	         data : {
				dailyExpenseId : parseInt(dailyExpenseId)
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
