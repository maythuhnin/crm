let loanDatatable = {}; 
let loanHistoryDatatable = {};
let loanAddValidator;

$(init);

function init() {
	
	bindValidator();
	bindModal();
	initLoanDatatable();
	bindSearch();
}

function bindModal(){
	
	bindLoanAddButtonClick();
	
	bindLoanAddApi();
	bindLoanDeleteApi();
	
	bindModalCloseButtonClick();
	
}


function bindSearch(){
	
	$("#loanDatatable_filter label").addClass("d-none");
	
	$("#searchBox").on('input', function() {
		loanDatatable.search($("#searchBox").val()).draw();
	});
	
	$("#clearFilters").on('click', function() {	
		$("#searchBox").val("");
		loanDatatable.search("").draw();
	});

}

function bindModalCloseButtonClick(){
	$(".cancel, .close").click(function () {
		resetLoanAddForm();
	});
}

function bindLoanAddApi(){
	
	$("#saveLoan").click(function () {
		
		if($("#loanForm").valid()){
			
			showLoadingOverlay();
			
			loanHistoryBean = {
				driverId : parseInt($("#driverId").val()),
				loanDateAsString : $("#loanDate").find("input").val(),
				amount : parseFloat($("#amount").val()),
				remark : $("#remark").val(),
				type:  $('input[name=type]:checked', '#loanForm').val()
			};
			
			$.ajax({
				url : getPathName() + "/loan/api/add",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify(loanHistoryBean),
				dataType: 'json',
				success : function(data) {
					if(data.httpStatus == "OK"){
						loanDatatable.ajax.reload();
						$("#addLoanModal").modal("hide");

						resetLoanAddForm();
						toastr.success('Driver Loan added successfully.');
					}else if(data.httpStatus == "INTERNAL_SERVER_ERROR"){
						toastr.error(data.error);
					}
				},
				complete : function(data){
					hideLoadingOverlay();
				}
			});

		}
		
	});
}

function bindLoanAddButtonClick(){
	//Date picker
	$('#loanDate').datetimepicker({
		format: 'DD/MM/YYYY',
		defaultDate: new Date(),  
	});
}


function resetLoanAddForm(){
	$("#loanDate, #amount, #remark").val("");
	$("#typeLoan").prop("checked", true);
	loanAddValidator.resetForm();
}

function initLoanHistoryDatatable(driverId) {
	
	$('#loanHistoryDatatable').DataTable().clear().destroy();
	
	loanHistoryDatatable = $('#loanHistoryDatatable').DataTable({
		lengthChange: false, 
		autoWidth: false,
      	dom: 'Bfrtip',
		ajax: {
	        url:  getPathName() + '/loan/api/' + driverId+ '/datatable',
	        type: "GET",
	        dataSrc: 'responseData',
	        dataType: "json"
	    },
	    "order": [0],
	     "scrollX": true,
        "sScrollXInner": "100%",
        scrollCollapse: true,
        searching: false,
         columnDefs: [{ width: '20%', targets: 2 }],
	    columns: [
		{ mData : function(data, type, full, meta) {
				return data.loanDate;
			},
		    sClass: "text-center"},
		   { mData : function(data, type, full, meta) {
				return isEmpty(data.remark) ? "-" : data.remark;
			},
		    sClass: "text-center"}, 
		    { mData : function(data, type, full, meta) {
				return getLoanType(data.type);
			},
		    sClass: "text-center"},     
	      { mData : function(data, type, full, meta) {
	
				return (data.type == 0 ? "+" : "-") + data.loanAmount;
			},
		    sClass: "text-right"} ,
		      { mData : function(data, type, full, meta) {
	
				return '<button type="button" class="btn btn-danger btn-sm delete-loan" data-driver="'+ data.driverId+'"  data-amount="' + fudatall.loanAmount +'" data-date="' + data.loanDate + '" data-id="' + data.id + '" title="Delete Loan"><i class="fas fa-trash"></i></button>';
			},
		    sClass: "text-center",
	    	bSortable: false }
	    ],
		"fnDrawCallback": function () {
			
			$( ".delete-loan" ).on( "click", function() {
					var loanId = $(this).attr("data-id");
						
					$('#delLoanId').val(loanId);
					$('#delDriverId').val($(this).attr("data-driver"));
					$('#delName').text($(this).attr("data-date") + " ("+ $(this).attr("data-amount") + ")");
					$("#deleteModal").modal();
				});
			
	   } ,
	   footerCallback: function (row, data, start, end, display) {
        let api = this.api();
 
        // Remove the formatting to get integer data for summation
        let intVal = function (i) {
            return typeof i === 'string'
                ? i.replace(/[\$,]/g, '') * 1
                : typeof i === 'number'
                ? i
                : 0;
        };
 
        // Total over all pages
        total = api
            .column(3)
            .data()
            .reduce((a, b) => intVal(a) + intVal(b), 0);
 
        // Total over this page
        pageTotal = api
            .column(3, { page: 'current' })
            .data()
            .reduce((a, b) => intVal(a) + intVal(b), 0);
 
        // Update footer
        api.column(3).footer().innerHTML =
            'MMK' + pageTotal + ' (MMK' + total + ' total)';
    }
	});
		
}


function initLoanDatatable() {
	
	loanDatatable = $('#loanDatatable').DataTable({
		lengthChange: false, 
		autoWidth: false,
      	dom: 'Bfrtip',
		ajax: {
	        url:  getPathName() + '/driver/api/datatable',
	        type: "GET",
	         data : function(d) {
				
			},
	        dataSrc: 'responseData',
	        dataType: "json"
	    },
	    "order": [0],
	    scrollX:        true,
        scrollCollapse: true,
         columnDefs: [{ width: '30%', targets: 2 }],
	    columns: [
		{ mData : function(data, type, full, meta) {
				return data.name;
			},
		    sClass: "text-center"},      
	      { mData : function(data, type, full, meta) {
	
				return isEmpty(data.loanAmount) ? "-" : data.loanAmount;			
			},
		    sClass: "text-right"} ,
		      { mData : function(data, type, full, meta) {
	
				return '<button type="button" class="btn btn-default btn-sm loan-history mr-1" data-id="' + data.id + '" title="Loan History">Loan History <i class="fas fa-info-circle"></i></button><button type="button" class="btn btn-outline-primary btn-sm add-loan" data-id="' + data.id + '" title="Add Loan">Add Loan <i class="fas fa-plus-circle"></i></button>';
			},
		    sClass: "text-center",
	    	bSortable: false }
	    ],
		"fnDrawCallback": function () {
			$( ".loan-history" ).on( "click", function() {
				var driverId = $(this).attr("data-id");
				$("#loanHistoryModal").modal();	
				 setTimeout(function() {
				     initLoanHistoryDatatable(driverId);
				  }, 100);
			});
			
			$( ".add-loan" ).on( "click", function() {
				var driverId = $(this).attr("data-id");
			    $('#driverId').val(driverId);
				$("#addLoanModal").modal();
			});
	   } 
	});
		
}

function getLoanType(type){
	switch(type) {
	  case 0:
		  return "Loan";
	  case 1:
		  return "Return";
	  default:
	}
}

function bindLoanDeleteApi(){
		
		$( "#deleteLoan" ).on( "click", function()  {

			showLoadingOverlay();
			
			$.ajax({
				url : getPathName() + "/loan/api/delete",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify($("#delLoanId").val()),
				dataType: 'json',
				success : function(data) {
					if(data.httpStatus == "OK"){
						
						loanDatatable.ajax.reload();
						
						getLoanHistoryTable($("#delDriverId").val());
						$("#deleteModal").modal("hide");
						
						toastr.success('Driver Loan deleted successfully.');
					
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

function bindValidator(){
	loanAddValidator = $("#loanForm").validate({
		rules : {
			loanDate : {
				required : true
			},
			amount : {
				required : true,
				digit: true
			},
			type : {
				required : true
			},
			remark : {
				maxlength: 500
			}
		}
	});
}

