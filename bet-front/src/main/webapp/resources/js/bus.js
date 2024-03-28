let busDatatable = {}; 
let busList = [];
let driverList =  [];
let busAddValidator;
let busUpdateValidator;
let driverAddValidator;

$(init);

function init() {
	
	bindValidator();
	bindModal();
	initDatatable();
	bindDropDown();
}

function bindModal(){
	
	bindBusAddButtonClick();
	bindDriverAddButtonClick();
	
	bindBusAddApi();
	bindBusUpdateApi();
	bindBusDeleteApi();
	
	bindDriverAddApi();
	
	bindModalCloseButtonClick();
	
}

function bindModalCloseButtonClick(){
	$(".cancel, .close").click(function () {
		resetBusForm();
	});
}

function bindDriverAddButtonClick(){
	$(".add-driver").click(function () {
		resetDriverForm();
		$("#driverType").val($(this).attr("data-type"));
		$("#driverModal").modal("show");
	});
}

function bindDriverAddApi(){
	
	$("#saveDriver").click(function () {
		
		if($("#driverForm").valid()){
			
			showLoadingOverlay();
			
			driverBean = {
				name : $("#name").val(),
				phone : $("#driverPhone").val(),
				loanAmount: parseFloat(0.0)
			};
			
			$.ajax({
				url : getPathName() + "/driver/api/add",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify(driverBean),
				dataType: 'json',
				success : function(data) {
					if(data.httpStatus == "OK"){
						busDatatable.ajax.reload();
						$("#driverModal").modal("hide");
		
						resetDriverForm();
						bindDriverDropDown(data.createdId, $("#driverType").val());
						
						$("#driverModal").modal("hide");
						
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

function bindBusAddButtonClick(){
	$("#addBus").click(function () {
		resetBusForm();
		 $('[data-mask]').inputmask();
		$("#addBusModal").modal("show");
	});
}

function bindBusAddApi(){
	
	$("#saveBus").click(function () {

		if($("#addBusForm").valid()){
			
			showLoadingOverlay();
			
			busBean = {
				licensePlate : $("#licensePlate").val(),
				primaryDriverId : parseInt($( "#primaryDriver" ).val()),
				secondaryDriverId : parseInt($( "#secondaryDriver" ).val()),
				status : $("#status").val()
			};
			
			$.ajax({
				url : getPathName() + "/bus/api/add",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify(busBean),
				dataType: 'json',
				success : function(data) {
					
					if(data.httpStatus == "OK"){
						busDatatable.ajax.reload();
						$("#addBusModal").modal("hide");
		
						resetBusForm();
						
						toastr.success('Bus added successfully.');
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


function bindBusUpdateApi(){
	$( "#editBus" ).on( "click", function()  {
		
		if($("#editBusForm").valid()){
			
			showLoadingOverlay();
			
			busBean = {
				id : parseInt($("#busId").val()),
				licensePlate : $("#editLicensePlate").val(),
				primaryDriverId : parseInt($( "#editPrimaryDriver" ).val()),
				secondaryDriverId : parseInt($( "#editSecondaryDriver" ).val()),
				phone : $( "#editPhone" ).val(),
				status : $("#editStatus").val()
			};
			
			$.ajax({
				url : getPathName() + "/bus/api/edit",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify(busBean),
				dataType: 'json',
				success : function(data) {
					if(data.httpStatus == "OK"){
						busDatatable.ajax.reload();
						$("#editBusModal").modal("hide");
		
						resetBusForm();
						
						toastr.success('Bus edited successfully.');
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

function resetBusForm(){
	$("#licensePlate, #primaryDriver, #secondaryDriver, #phone").val("");
	$("#editLicensePlate, #editPrimaryDriver, #editSecondaryDriver, #editPhone").val("");
	$("#status, editStatus").val("OK");
	 busAddValidator.resetForm();
	 busUpdateValidator.resetForm();
}

function resetDriverForm(){
	$("#name,#driverPhone").val("");
	driverAddValidator.resetForm();
}

function bindDropDown(){

	 bindDriverDropDown();
	 
	$('#searchStatus').select2({
		theme: 'bootstrap4',
    	placeholder: "Search Status.",
    	allowClear: false
	});
	
	$('#searchDriver').select2({
		theme: 'bootstrap4',
    	placeholder: "Search Driver.",
    	allowClear: false
	});
	
	$('.select2-selection__rendered').addClass("float-left");
	
	$("#searchStatus").on('change', function() {
		busDatatable.column(4).search( $('#searchStatus').val()).draw();
	});
	
	$("#searchDriver").on('change', function() {
		busDatatable.search( $('#searchDriver').val()).draw();
	});
	
	$("#searchBox").on('input', function() {
		busDatatable.search($("#searchBox").val()).draw();
	});
	
	$("#clearFilters").on('click', function() {	
		$("#searchBox, #searchDriver, #searchStatus").val("");
		$("#searchBox, #searchDriver, #searchStatus").trigger("change");
	});
}


function bindDriverDropDown(selectedId, editId){
	
	$("#primaryDriver, #secondaryDriver, #editPrimaryDriver, #editSecondaryDriver, #searchDriver").empty();
	
	$.ajax({
		url : getPathName() + "/driver/api/dropdown",
		type : "GET",
		contentType: "application/json",
		dataType: 'json',
		success : function(data) {
			
			if(null != data && data.length > 0){
				$("#primaryDriver, #secondaryDriver, #editPrimaryDriver, #editSecondaryDriver, #searchDriver").append("<option value=''></option>")
	
				$(data).each(function( index, driver ) {
					$("#primaryDriver, #secondaryDriver, #editPrimaryDriver, #editSecondaryDriver").append("<option value='" + driver.id + "'>" + driver.name + " [" + driver.phone +"]</option>");
					$("#searchDriver").append("<option value='" + driver.name + "'>" + driver.name + "</option>");
				
				});
				
				driverList = data;
			}
			
		},
		complete : function(data){
			if(null != selectedId){
				$("#" + editId).val(selectedId);	
			}
			
			$( "#primaryDriver" ).on( "change", function() {
				var driverBean = getBeanFromListById(driverList, $(this).val());
				$( "#phone" ).val(driverBean.phone);
			});
		}
	});	
}


function initDatatable() {
	
	busDatatable = $('#busDatatable').DataTable({
		lengthChange: false, 
		autoWidth: false,
      	dom: 'Bfrtip',
		ajax: {
	        url:  getPathName() + '/bus/api/datatable',
	        type: "GET",
	         data : function(d) {
				
			},
	        dataSrc: 'responseData',
	        dataType: "json"
	    },
	    "order": [1],
	    scrollX:        true,
        scrollCollapse: true,
	    columns: [
		{ mData : function(data, type, full, meta) {
				return data.licensePlate;
			},
		    sClass: "text-center"}, 
	     { mData : function(data, type, full, meta) {
				busList.push(data);
				return data.primaryDriver;
			},
		    sClass: "text-center"}, 
	      
	      { mData : function(data, type, full, meta) {
	
				return data.secondaryDriver;
			},
		    sClass: "text-center"}, 
	    { mData : function(data, type, full, meta) {
	
				return data.phone;
			},
		    sClass: "text-center"},
	    { mData : function(data, type, full, meta) {
	
				return getBusStatus(data.status);
			},
		    sClass: "text-center" },
	    { mData : function(data, type, full, meta) {
	
				return '<div><button type="button" class="btn btn-outline-danger btn-sm delete-bus mr-1" data-id="' + data.id + '" title="Delete Bus">Delete <i class="fas fa-trash" data-id="' + data.id + '" title="Delete Bus"></i></button><button type="button" class="btn btn-outline-primary edit-bus btn-sm" data-id="' + data.id + '" title="Edit Bus">Edit <i class="fas fa-edit mt-1" data-id="' + data.id + '" title="Edit Bus"></i></button></div>';
			},
		    sClass: "text-center",
	    	bSortable: false }
	    ],
		"fnDrawCallback": function () {
			$( ".edit-bus" ).on( "click", function() {
				var busId = $(this).attr("data-id");
				var busBean = getBeanFromListById(busList, busId);
				$("#busId").val(busId);
				$("#editLicensePlate").val(busBean.licensePlate);
				$("#editPrimaryDriver").val(busBean.primaryDriverId);
				$("#editSecondaryDriver").val(busBean.secondaryDriverId);
				$("#editPhone").val(busBean.phone);
				$("#editStatus").val(busBean.status);
				$("#editBusModal").modal();
			});
			
			$( ".delete-bus" ).on( "click", function() {
				var busId = $(this).attr("data-id");
				var busBean = getBeanFromListById(busList, busId);
				$("#delBusId").val(busId);
				$("#delName").text(busBean.licensePlate);
				$("#deleteModal").modal();
			});
	   } 
	});
	
	$("#busDatatable_filter label").addClass("d-none");
	
}


function getBusStatus(status){
	switch(status) {
	  case "OK":
		  return "<span class='text-success'>OK</span>";
	  case "REPAIRING":
		  return "<span class='text-warning'>REPAIRING</span>";
	  default:
	}
}

function bindBusDeleteApi(){
		
		$( "#deleteBus" ).on( "click", function()  {

			showLoadingOverlay();
			
			$.ajax({
				url : getPathName() + "/bus/api/delete",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify($("#delBusId").val()),
				dataType: 'json',
				success : function(data) {
					if(data.httpStatus == "OK"){
						
						busDatatable.ajax.reload();
						
						$("#deleteModal").modal("hide");
						
						toastr.success('Bus deleted successfully.');
					
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
	busAddValidator = $("#addBusForm").validate({
		rules : {
			licensePlate : {
				required : true,
				maxlength : 7
			},
			primaryDriver : {
				required : true
			},
			status : {
				required : true
			}
		},
		errorPlacement : function(error, element) {
			if ($(element).prop("id") === "primaryDriver") {
				error.insertAfter($(".add-input-group"));
			} else {
				error.insertAfter(element); // default error placement.
			}
		}
	});
	
	busUpdateValidator = $("#editBusForm").validate({
		rules : {
			editLicensePlate : {
				required : true,
				maxlength : 7
			},
			editPrimaryDriver : {
				required : true
			},
			editStatus : {
				required : true
			}
		},
		errorPlacement : function(error, element) {
			if ($(element).prop("id") === "editPrimaryDriver") {
				error.insertAfter($(".edit-input-group"));
			} else {
				error.insertAfter(element); // default error placement.
			}
		}
	});
	
	driverAddValidator = $("#driverForm").validate({
		rules : {
			name : {
				required : true,
				maxlength : 200
			},
			driverPhone:{
				maxlength : 100
			}
		}
	});
}

