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
						placeholder: "Select Bus.",
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
	
	bindExpenseDeleteApi();
	 
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
				expenseList.push(data);
				return data.dateRange;
			},
		    sClass: "text-center"},
		{ mData : function(data, type, full, meta) {
				return isEmpty(data.path) ? "Rest Day" : getPathText(data.path);
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
				return (data.restDay == true) ? getCurrencyFormat(data.expenseTotal) : getCurrencyFormat((data.onPaperIncomeLeave + data.onPaperIncomeReturn + data.extraIncome) - data.expenseTotal);
			},
		    sClass: "text-right"},
		      { mData : function(data, type, full, meta) {
	
				return '<div><button type="button" class="btn btn-outline-danger btn-sm delete-expense mr-1" data-id="' + data.id + '" title="Delete Daily Income/Expense">Delete <i class="fas fa-trash" data-id="' + data.id + '" title="Delete Daily Income/Expense"></i></button><button type="button" href="" class="btn btn-outline-primary edit-expense btn-sm" data-id="' + data.id + '" title="Edit Daily Income/Expense">Edit <i class="fas fa-edit mt-1" data-id="' + data.id + '" title="Edit Daily Income/Expense"></i></button></div>';
			},
		    sClass: "text-center",
	    	bSortable: false }           
	 
		      
	    ],
	    "fnDrawCallback": function () {
		
		$( ".delete-expense" ).on( "click", function() {
			var expenseId = $(this).attr("data-id");
			var expenseBean = getBeanFromListById(expenseList, expenseId);
			$("#delExpenseId").val(expenseId);
			$("#delName").text(expenseBean.dateRange);
			$("#deleteModal").modal();
		});
		
		$( ".edit-expense" ).on( "click", function() {
			console.log("clicked");
			var expenseId = $(this).attr("data-id");
			var url = getPathName() + '/daily/' + expenseId +'/edit'
			window.open(url, '_blank');
			
		});
		},
    "fnRowCallback": function(nRow, aData, iDisplayIndex, iDisplayIndexFull) {
	
		
			
      if (aData.restDay) {
		$('td:eq(2)', nRow).attr('colspan', 6);
		$('td:eq(3)', nRow).css('display', 'none');
		$('td:eq(4)', nRow).css('display', 'none');
		$('td:eq(5)', nRow).css('display', 'none');
		$('td:eq(6)', nRow).css('display', 'none');
		$('td:eq(7)', nRow).css('display', 'none');
	    $('td', nRow).addClass('table-secondary');
      } 
    }
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


function bindExpenseDeleteApi(){
		
		$( "#deleteExpense" ).on( "click", function()  {

			showLoadingOverlay();
			
			$.ajax({
				url : getPathName() + "/daily-expense/api/delete",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify($("#delExpenseId").val()),
				dataType: 'json',
				success : function(data) {
					if(data.httpStatus == "OK"){
						
						expenseReportDatatable.ajax.reload();
						
						$("#deleteModal").modal("hide");
						
						toastr.success('Daily Income/Expense deleted successfully.');
					
					}else if(data.httpStatus == "INTERNAL_SERVER_ERROR"){
						toastr.error(data.error);
					}
				},
				complete : function(data){
					hideLoadingOverlay();
				}
			});	
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
