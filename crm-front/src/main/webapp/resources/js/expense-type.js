let expenseTypeDatatable = {}; 
let expenseTypeList = [];
let expenseTypeAddValidator;
let expenseTypeEditValidator;

$(init);

function init() {
	
	bindValidator();
	bindModal();
	initExpenseTypeDatatable();
	bindSearch();
}

function bindModal(){
	
	bindExpenseTypeAddButtonClick();
	
	bindExpenseTypeAddApi();
	bindExpenseTypeEditApi();
	bindExpenseTypeDeleteApi();
	
	bindModalCloseButtonClick();
	
}


function bindSearch(){
	
	$("#expenseTypeDatatable_filter label").addClass("d-none");
	
	$("#searchBox").on('input', function() {
		expenseTypeDatatable.search($("#searchBox").val()).draw();
	});
	
	$("#clearFilters").on('click', function() {	
		$("#searchBox").val("");
		expenseTypeDatatable.search("").draw();
	});
}

function bindModalCloseButtonClick(){
	$(".cancel, .close").click(function () {
		resetExpenseTypeAddForm();
	});
}

function bindExpenseTypeAddApi(){
	
	$("#saveExpenseType").click(function () {
		
		if($("#addExpenseTypeForm").valid()){
			
			showLoadingOverlay();
			
			expenseTypeBean = {
				name : $("#name").val()
			};
			
			$.ajax({
				url : getPathName() + "/expense-type/api/add",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify(expenseTypeBean),
				dataType: 'json',
				success : function(data) {
					if(data.httpStatus == "OK"){
						expenseTypeDatatable.ajax.reload();
						$("#addExpenseTypeModal").modal("hide");

						resetExpenseTypeAddForm();
						toastr.success('Expense Type added successfully.');
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

function bindExpenseTypeEditApi(){
	
	$("#editExpenseType").click(function () {
		
		if($("#editExpenseTypeForm").valid()){
			
			showLoadingOverlay();
			
			expenseTypeBean = {
				id : parseInt($("#editExpenseTypeId").val()),
				name : $("#editName").val()
			};
			
			$.ajax({
				url : getPathName() + "/expense-type/api/edit",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify(expenseTypeBean),
				dataType: 'json',
				success : function(data) {
					if(data.httpStatus == "OK"){
						expenseTypeDatatable.ajax.reload();
						$("#editExpenseTypeModal").modal("hide");

						resetExpenseTypeAddForm();
						toastr.success('Expense Type edited successfully.');
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

function bindExpenseTypeAddButtonClick(){
	$( "#addExpenseType" ).on( "click", function() {
		$("#addExpenseTypeModal").modal();
	});
}


function resetExpenseTypeAddForm(){
	$("#name").val("");
	$("#editName, #editExpenseTypeId").val("");
	expenseTypeAddValidator.resetForm();
	expenseTypeEditValidator.resetForm();
}

function initExpenseTypeDatatable() {
	
	expenseTypeDatatable = $('#expenseTypeDatatable').DataTable({
		lengthChange: false, 
		autoWidth: false,
      	dom: 'Bfrtip',
		ajax: {
	        url:  getPathName() + '/expense-type/api/datatable',
	        type: "GET",
	         data : function(d) {
				
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
         columnDefs: [{ width: '20%', targets: 1 }],
	    columns: [
		{ mData : function(data, type, full, meta) {
				expenseTypeList.push(data);
				return data.name;
			},
		    sClass: "text-center"},      
	        { mData : function(data, type, full, meta) {
	
				return '<button type="button" class="btn btn-outline-danger delete-expenseType mr-1 btn-sm" data-id="' + data.id + '" title="Delete Expense Type">Delete <i class="fas fa-trash"></i></button><button type="button" class="btn btn-outline-primary edit-expenseType btn-sm" data-id="' + data.id + '" title="Edit Expense Type">Edit <i class="fas fa-edit"></i></button>';
			},
		    sClass: "text-center",
	    	bSortable: false }
	    ],
		"fnDrawCallback": function () {

			$( ".edit-expenseType" ).on( "click", function() {
				var expenseTypeId = $(this).attr("data-id");
				var expenseTypeBean = getBeanFromListById(expenseTypeList, expenseTypeId);
			    $('#editExpenseTypeId').val(expenseTypeId);
			    $('#editName').val(expenseTypeBean.name);
			    
				$("#editExpenseTypeModal").modal();
			});
			
			$( ".delete-expenseType" ).on( "click", function() {
				var expenseTypeId = $(this).attr("data-id");
				var expenseTypeBean = getBeanFromListById(expenseTypeList, expenseTypeId);
			    $('#delExpenseTypeId').val(expenseTypeId);
			    $('#delName').text(expenseTypeBean.name);
				$("#deleteExpenseTypeModal").modal();
			});
	   } 
	});
		
}

function bindExpenseTypeDeleteApi(){
		
		$( "#deleteExpenseType" ).on( "click", function()  {

			showLoadingOverlay();
			
			$.ajax({
				url : getPathName() + "/expense-type/api/delete",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify($("#delExpenseTypeId").val()),
				dataType: 'json',
				success : function(data) {
					if(data.httpStatus == "OK"){
						
						expenseTypeDatatable.ajax.reload();
						
						$("#deleteExpenseTypeModal").modal("hide");
						
						toastr.success('Expense Type deleted successfully.');
					
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
	expenseTypeAddValidator = $("#addExpenseTypeForm").validate({
		rules : {
			name : {
				required : true,
				maxlength : 200
			}
		}
	});
	
	expenseTypeEditValidator = $("#editExpenseTypeForm").validate({
		rules : {
			editName : {
				required : true,
				maxlength : 200
			}
		}
	});
}

