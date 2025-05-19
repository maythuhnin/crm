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
	
	bindBusDropDown();
	initIncomeReportDatatable();
	bindSearch();
	
}


function bindSearch(){
	 
	$(".dataTables_filter").addClass("d-none");
	
	$("#searchBox").on('input', function() {
		incomeReportDatatable.search($("#searchBox").val()).draw();
	});
	
	$("#clearFilters").on('click', function() {	
		$("#searchBox, #searchBus, #searchOrder").val("");
		$("#searchBus, #searchOrder").trigger("change");
		
		//Date picker
		$('#searchMonth').datetimepicker({
			format: 'MM/YYYY', 
			defaultDate: new Date()
		});

		incomeReportDatatable.search("").draw();
	});
	
	$("#searchMonth").on("change.datetimepicker", ({date, oldDate}) => {              
    	incomeReportDatatable.ajax.reload();
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
					
					$('#searchOrder').select2({
						theme: 'bootstrap4',
						placeholder: "Select Order.",
						allowClear: true
					});
					
					$("#searchBus, #searchOrder").on('change', function() {
						incomeReportDatatable.clear().draw();
						incomeReportDatatable.ajax.reload();
					});
				}
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
				d.busId = $("#searchBus").val();
				d.isOrder = $("#searchOrder").val();
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
				return data.roundTrips;
			},
			
		    sClass: "text-right"},     
		    { mData : function(data, type, full, meta) {
				return data.restDays;
			},
		    sClass: "text-right"},
		    { mData : function(data, type, full, meta) {
				return getCurrencyFormat(getIntFromField(data.totalIncome) - getIntFromField(data.totalExpense) + getIntFromField(data.extraIncome));
			},
		    sClass: "text-right"} 
	    ]
	});
			
}

