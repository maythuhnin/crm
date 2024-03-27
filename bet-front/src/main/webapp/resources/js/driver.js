let driverDatatable = {}; 
let driverList = [];
let driverAddValidator;
let driverEditValidator;

$(init);

function init() {
	
	bindValidator();
	bindModal();
	initDriverDatatable();
}

function bindModal(){
	
	bindDriverAddButtonClick();
	
	bindDriverAddApi();
	bindDriverEditApi();
	bindDriverDeleteApi();
	
	bindModalCloseButtonClick();
	
}

function bindModalCloseButtonClick(){
	$(".cancel, .close").click(function () {
		resetDriverAddForm();
	});
}

function bindDriverAddApi(){
	
	$("#saveDriver").click(function () {
		
		if($("#driverForm").valid()){
			
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
		{ render : function(data, type, full, meta) {
				return full.name;
			},
		    sClass: "text-center"},      
	      { render : function(data, type, full, meta) {
				driverList.push(full);
				return full.phone			
			},
		    sClass: "text-right"} ,
		      { render : function(data, type, full, meta) {
	
				return '<button type="button" class="btn btn-primary mr-1 edit-driver" data-id="' + full.id + '" title="Driver History"><i class="fas fa-edit"></i></button><button type="button" class="btn btn-default delete-driver" data-id="' + full.id + '" title="Driver History"><i class="fas fa-trash"></i></button>';
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
				required : true
			},
			phone : {
				required : true
			}
		}
	});
	
	driverEditValidator = $("#editDriverForm").validate({
		rules : {
			editName : {
				required : true
			},
			editPhone : {
				required : true
			}
		}
	});
}

