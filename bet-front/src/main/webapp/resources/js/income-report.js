let incomeReportDatatable = {}; 
let incomeReportList = [];
let incomeList = [];

$(init);

function init() {
	
	//Date picker
	$('#searchMonth').datetimepicker({
		format: 'MM/YYYY',
		defaultDate: new Date()
	});
	
	initIncomeReportDatatable();
	bindSearch();
	
}


function bindSearch(){
	 
	$(".dataTables_filter").addClass("d-none");
	
	$("#searchBox").on('input', function() {
		incomeReportDatatable.search($("#searchBox").val()).draw();
	});
	
	$("#clearFilters").on('click', function() {	
		$("#searchBox").val("");
		incomeReportDatatable.search("").draw();
	});
	
	$("#searchMonth").on("change.datetimepicker", ({date, oldDate}) => {              
    	incomeReportDatatable.ajax.reload();
    });
}



function initIncomeReportDatatable() {
	
	incomeReportDatatable = $('#incomeReportDatatable').DataTable({
		dom: 'Bfrtip',
		lengthChange: false, 
		autoWidth: true,
		ajax: {
	        url:  getPathName() + '/income-report/api/datatable',
	        type: "POST",
	        data : function(d) {
				d.month = $("#searchMonth").find("input").val();
			},
	        dataSrc: 'responseData',
	        dataType: "json"
	    },
	    processing: true,
        serverSide: false,
	    "order": [0],
	    scrollX:        false,
        scrollCollapse: true,
	    paging: false,
	    scrollY: '50vh',
	    responsive: false,
        columns: [
         { mData : function(data, type, full, meta) {
				return data.bus;
			},
		    sClass: "text-center"},
		{ mData : function(data, type, full, meta) {
				return isEmpty(data.totalIncome) ? "-" : getCurrencyFormat(data.totalIncome);
			},
		    sClass: "text-right"}, 
		{ mData : function(data, type, full, meta) {
				return isEmpty(data.totalExpense) ? "-" : getCurrencyFormat(data.totalExpense);
			},
		    sClass: "text-right"},      
		 { mData : function(data, type, full, meta) {
				return getCurrencyFormat(getIntFromField(data.totalIncome) - getIntFromField(data.totalExpense));
			},
		    sClass: "text-right"},      
		    
		    { mData : function(data, type, full, meta) {
				return isEmpty(data.extraIncome) ? "-" : getCurrencyFormat(data.extraIncome);
			},
			
		    sClass: "text-right"},  
		     { mData : function(data, type, full, meta) {
				return data.totalRecords - data.restDay;
			},
			
		    sClass: "text-right"},     
		    { mData : function(data, type, full, meta) {
				return data.restDay;
			},
		    sClass: "text-right"},
		    { mData : function(data, type, full, meta) {
				return getCurrencyFormat(getIntFromField(data.totalIncome) - getIntFromField(data.totalExpense) + getIntFromField(data.extraIncome));
			},
		    sClass: "text-right"} 
	    ]
	});
			
}

