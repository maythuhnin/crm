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
		$("#busModal").modal("show");
	});
}

function bindBusAddApi(){
	
	$("#saveBus").click(function () {

		if($("#busForm").valid()){
			
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
						$("#busModal").modal("hide");
		
						resetBusForm();
						
						$("#busModal").modal("hide");
						
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
	$( "#updateBus" ).on( "click", function()  {
		
		if($("#updateBusForm").valid()){
			
			showLoadingOverlay();
			
			busBean = {
				id : parseInt($("#busId").val()),
				licensePlate : $("#updateLicensePlate").val(),
				primaryDriverId : parseInt($( "#updatePrimaryDriver" ).val()),
				secondaryDriverId : parseInt($( "#updateSecondaryDriver" ).val()),
				phone : $( "#updatePhone" ).val(),
				status : $("#updateStatus").val()
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
						$("#updateBusModal").modal("hide");
		
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
	$("#updateLicensePlate, #updatePrimaryDriver, #updateSecondaryDriver, #updatePhone").val("");
	$("#status, updateStatus").val("OK");
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
	
	$('.select2-selection__rendered').addClass("float-left");
	
	$(".select-filter").on('change', function() {
		busDatatable.column(4).search( $('#searchStatus').val()).draw();
	});
	
	$("#searchBox").on('input', function() {
		console.log($(this).val()); 
		busDatatable.column(0).search($("#searchBox").val()).draw();
		console.log(busDatatable.column(0).search($("#searchBox").val()));
		//busDatatable.column(1).search($("#searchBox").val()).draw();
	});
	
	$(".select2-selection__clear, #clearFilters").on('click', function() {	
		$('#searchStatus').val("");
		busDatatable.column(4).search("").draw();
	});
}


function bindDriverDropDown(selectedId, updateId){
	
	$("#primaryDriver, #secondaryDriver, #updatePrimaryDriver, #updateSecondaryDriver").empty();
	
	$.ajax({
		url : getPathName() + "/driver/api/dropdown",
		type : "GET",
		contentType: "application/json",
		dataType: 'json',
		success : function(data) {
			
			if(null != data && data.length > 0){
				$("#primaryDriver, #secondaryDriver, #updatePrimaryDriver, #updateSecondaryDriver").append("<option value=''></option>")
	
				$(data).each(function( index, driver ) {
					$("#primaryDriver, #secondaryDriver, #updatePrimaryDriver, #updateSecondaryDriver").append("<option value='" + driver.id + "'>" + driver.name + " ["+driver.phone+"]</option>");
				});
				
				driverList = data;
			}
			
		},
		complete : function(data){
			if(null != selectedId){
				$("#" + updateId).val(selectedId);	
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
		{ render : function(data, type, full, meta) {
				return full.licensePlate;
			},
		    sClass: "text-center"}, 
	     { render : function(data, type, full, meta) {
				busList.push(full);
				return full.primaryDriver;
			},
		    sClass: "text-center"}, 
	      
	      { render : function(data, type, full, meta) {
	
				return full.secondaryDriver;
			},
		    sClass: "text-center"}, 
	    { render : function(data, type, full, meta) {
	
				return full.phone;
			},
		    sClass: "text-center"},
	    { render : function(data, type, full, meta) {
	
				return getBusStatus(full.status);
			},
		    sClass: "text-center" },
	    { render : function(data, type, full, meta) {
	
				return '<div><i class="fas fa-edit update-bus mt-1 mr-1" data-id="' + full.id + '" title="Edit"></i><i class="fas fa-trash delete-bus mt-1" data-id="' + full.id + '" title="Delete"></i></div>';
			},
		    sClass: "text-center",
	    	bSortable: false }
	    ],
		"fnDrawCallback": function () {
			$( ".update-bus" ).on( "click", function() {
				var busId = $(this).attr("data-id");
				var busBean = getBeanFromListById(busList, busId);
				$("#busId").val(busId);
				$("#updateLicensePlate").val(busBean.licensePlate);
				$("#updatePrimaryDriver").val(busBean.primaryDriverId);
				$("#updateSecondaryDriver").val(busBean.secondaryDriverId);
				$("#updatePhone").val(busBean.phone);
				$("#updateStatus").val(busBean.status);
				$("#updateBusModal").modal();
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
	
	$("#busDatatable_filter").prepend(getFilters);
	 $("#busDatatable_filter label").addClass("float-right");
	
}

function getFilters(){
	var filters = '<div class="row">' +
	'<div class="col-sm-3"><select class="form-control select2 select-filter" style="width: 100%;" id="searchStatus">' +
			        	'<option></option>' +
			            '<option value="OK">OK</option>' +
				       '<option value="REPAIRING">REPAIRING</option>' +
			        '</select></div>'+   
	'<div class="col-sm-2">' +
				'<button type="button" class="btn btn-default float-left" id="clearFilters">' +
	           'Clear Filters </button>' +
	        '</div>';
	        
	        return filters;
	
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
	busAddValidator = $("#busForm").validate({
		rules : {
			name : {
				required : true,
				maxlength : 50
			},
			busname : {
				required : true
			},
			role : {
				required : true
			},
			password : {
				required : true
			},
			confirmPassword : {
				required : true
			}
		}
	});
	
	busUpdateValidator = $("#updateBusForm").validate({
		rules : {
			updateName : {
				required : true,
				maxlength : 50
			},
			updateBusname : {
				required : true
			},
			updateRole : {
				required : true
			}
		}
	});
	
	driverAddValidator = $("#driverForm").validate({
		rules : {
			name : {
				required : true,
				maxlength : 200
			}
		}
	});
}

