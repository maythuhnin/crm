let driverDatatable = {}; 
let driverList = [];
let driverAddValidator;
let driverEditValidator;

$(init);

function init() {
	
	bindValidator();
	bindModal();
	initDriverDatatable();
	bindSearch();
}

function bindModal(){
	
	bindDriverAddButtonClick();
	
	bindDriverAddApi();
	bindDriverEditApi();
	bindDriverDeleteApi();
	
	bindModalCloseButtonClick();
	
}

function bindSearch(){
	
	$("#driverDatatable_filter label").addClass("d-none");
	
	$("#searchBox").on('input', function() {
		driverDatatable.search($("#searchBox").val()).draw();
	});
	
	$("#clearFilters").on('click', function() {	
		$("#searchBox").val("");
		driverDatatable.search("").draw();
	});
}

function bindModalCloseButtonClick(){
	$(".cancel, .close").click(function () {
		resetDriverAddForm();
	});
}

function bindDriverAddApi(){
	
	$("#saveDriver").click(function () {
		
		if($("#addDriverForm").valid()){
			
			showLoadingOverlay();
			
			driverBean = {
				name : $("#name").val(),
				phone : $("#phone").val()
			};
			
			$.ajax({
				url : getPathName() + "/driver/api/add",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify(driverBean),
				dataType: 'json',
				success : function(data) {
					if(data.httpStatus == "OK"){
						driverDatatable.ajax.reload();
						$("#addDriverModal").modal("hide");

						resetDriverAddForm();
						toastr.success('Driver added successfully.');
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

function bindDriverEditApi(){
	
	$("#editDriver").click(function () {
		
		if($("#editDriverForm").valid()){
			
			showLoadingOverlay();
			
			driverBean = {
				id : parseInt($("#editDriverId").val()),
				name : $("#editName").val(),
				phone : $("#editPhone").val()
			};
			
			$.ajax({
				url : getPathName() + "/driver/api/edit",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify(driverBean),
				dataType: 'json',
				success : function(data) {
					if(data.httpStatus == "OK"){
						driverDatatable.ajax.reload();
						$("#editDriverModal").modal("hide");

						resetDriverAddForm();
						toastr.success('Driver edited successfully.');
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

function bindDriverAddButtonClick(){
	$( "#addDriver" ).on( "click", function() {
		$("#addDriverModal").modal();
	});
}


function resetDriverAddForm(){
	$("#name, #phone").val("");
	$("#editName, #editPhone, #editDriverId").val("");
	driverAddValidator.resetForm();
	driverEditValidator.resetForm();
}


function initDriverDatatable() {
	
	driverDatatable = $('#driverDatatable').DataTable({
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
         columnDefs: [{ width: '20%', targets: 2 }],
	    columns: [
		{ mData : function(data, type, full, meta) {
				return data.name;
			},
		    sClass: "text-center"},      
	      { mData : function(data, type, full, meta) {
				driverList.push(data);
				return data.phone			
			},
		    sClass: "text-center"} ,
		      { mData : function(data, type, full, meta) {
	
				return '<button type="button" class="btn btn-outline-danger btn-sm delete-driver mr-1" data-id="' + data.id + '" title="Delete Driver">Delete <i class="fas fa-trash"></i></button><button type="button" class="btn btn-outline-primary btn-sm edit-driver" data-id="' + data.id + '" title="Edit Driver">Edit <i class="fas fa-edit"></i></button>';
			},
		    sClass: "text-center",
	    	bSortable: false }
	    ],
		"fnDrawCallback": function () {

			$( ".edit-driver" ).on( "click", function() {
				var driverId = $(this).attr("data-id");
				var driverBean = getBeanFromListById(driverList, driverId);
			    $('#editDriverId').val(driverId);
			    $('#editName').val(driverBean.name);
			    $('#editPhone').val(driverBean.phone);
				$("#editDriverModal").modal();
			});
			
			$( ".delete-driver" ).on( "click", function() {
				var driverId = $(this).attr("data-id");
				var driverBean = getBeanFromListById(driverList, driverId);
			    $('#delDriverId').val(driverId);
			    $('#delName').text(driverBean.name);
				$("#deleteDriverModal").modal();
			});
	   } 
	});
		
}

function bindDriverDeleteApi(){
		
		$( "#deleteDriver" ).on( "click", function()  {

			showLoadingOverlay();
			
			$.ajax({
				url : getPathName() + "/driver/api/delete",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify($("#delDriverId").val()),
				dataType: 'json',
				success : function(data) {
					if(data.httpStatus == "OK"){
						
						driverDatatable.ajax.reload();
						
						$("#deleteDriverModal").modal("hide");
						
						toastr.success('Driver deleted successfully.');
					
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
	driverAddValidator = $("#addDriverForm").validate({
		rules : {
			name : {
				required : true,
				maxlength: 200
			},
			phone : {
				maxlength : 100
			}
		}
	});
	
	driverEditValidator = $("#editDriverForm").validate({
		rules : {
			editName : {
				required : true,
				maxlength: 200
			},
			editPhone : {
				maxlength : 100
			}
		}
	});
}

