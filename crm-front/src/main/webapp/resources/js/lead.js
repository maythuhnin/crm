let leadDatatable = {}; 
let leadList = [];
let driverList =  [];
let AddValidator;
let UpdateValidator;
let driverAddValidator;

$(init);

function init() {
	
	bindValidator();
	bindModal();
	initDatatable();
	bindDropDown();
}

function bindModal(){
	
	bindLeadAddButtonClick();
	bindDriverAddButtonClick();
	
	bindLeadAddApi();
	bindLeadUpdateApi();
	bindLeadDeleteApi();
	
	bindDriverAddApi();
	
	bindModalCloseButtonClick();
	
}

function bindModalCloseButtonClick(){
	$(".close-").click(function () {
		resetLeadForm();
	});
	
	$(".close-driver").click(function () {
		resetDriverForm();
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
				phone : $("#phone").val(),
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
						leadDatatable.ajax.reload();
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

function bindLeadAddButtonClick(){
	$("#addLead").click(function () {
		resetLeadForm();
		 $('[data-mask]').inputmask();
		$("#addLeadModal").modal("show");
	});
}

function bindLeadAddApi(){
	
	$("#saveLead").click(function () {

		if($("#addLeadForm").valid()){
			
			showLoadingOverlay();
			
			Bean = {
				licensePlate : $("#licensePlate").val(),
				primaryDriverId : parseInt($( "#primaryDriver" ).val()),
				secondaryDriverId : parseInt($( "#secondaryDriver" ).val()),
				status : $("#status").val()
			};
			
			$.ajax({
				url : getPathName() + "//api/add",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify(Bean),
				dataType: 'json',
				success : function(data) {
					
					if(data.httpStatus == "OK"){
						leadDatatable.ajax.reload();
						$("#addLeadModal").modal("hide");
		
						resetLeadForm();
						
						toastr.success('Lead added successfully.');
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


function bindLeadUpdateApi(){
	$( "#editLead" ).on( "click", function()  {
		
		if($("#editLeadForm").valid()){
			
			showLoadingOverlay();
			
			Bean = {
				id : parseInt($("#Id").val()),
				licensePlate : $("#editLicensePlate").val(),
				primaryDriverId : parseInt($( "#editPrimaryDriver" ).val()),
				secondaryDriverId : parseInt($( "#editSecondaryDriver" ).val()),
				phone : $( "#editPhone" ).val(),
				status : $("#editStatus").val()
			};
			
			$.ajax({
				url : getPathName() + "//api/edit",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify(Bean),
				dataType: 'json',
				success : function(data) {
					if(data.httpStatus == "OK"){
						leadDatatable.ajax.reload();
						$("#editLeadModal").modal("hide");
		
						resetLeadForm();
						
						toastr.success('Lead edited successfully.');
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

function resetLeadForm(){
	$("#licensePlate, #primaryDriver, #secondaryDriver, #primaryPhone").val("");
	$("#editLicensePlate, #editPrimaryDriver, #editSecondaryDriver, #editPhone").val("");
	$("#status, editStatus").val("OK");
	 AddValidator.resetForm();
	 UpdateValidator.resetForm();
}

function resetDriverForm(){
	$("#name").val("");
	$("#phone").tagsinput('removeAll');
	driverAddValidator.resetForm();
}

function bindDropDown(){

	 bindDriverDropDown();
	 
	$('#searchStatus').select2({
		theme: 'bootstrap4',
    	placeholder: "Search Status.",
    	allowClear: false
	});
	
	$('#searchCompany').select2({
		theme: 'bootstrap4',
    	placeholder: "Search Company.",
    	allowClear: false
	});
	
	$('.select2-selection__rendered').addClass("float-left");
	
	$("#searchStatus").on('change', function() {
		leadDatatable.column(4).search( $('#searchStatus').val()).draw();
	});
	
	$("#searchDriver").on('change', function() {
		leadDatatable.search( $('#searchDriver').val()).draw();
	});
	
	$("#searchBox").on('input', function() {
		leadDatatable.search($("#searchBox").val()).draw();
	});
	
	$("#clearFilters").on('click', function() {	
		$("#searchBox, #searchDriver, #searchStatus").val("");
		$("#searchBox, #searchDriver, #searchStatus").trigger("change");
	});
}


function bindDriverDropDown(selectedId, editId){
	
	var primaryDriver = $("#primaryDriver").val();
	var secondaryDriver = $("#secondaryDriver").val();
	var editPrimaryDriver = $("#editPrimaryDriver").val();
	var editSecondaryDriver = $("#editSecondaryDriver").val();
	
	$("#primaryDriver, #secondaryDriver, #editPrimaryDriver, #editSecondaryDriver, #searchDriver").empty();
	
	$.ajax({
		url : getPathName() + "/driver/api/dropdown",
		type : "GET",
		contentType: "application/json",
		dataType: 'json',
		success : function(data) {
			
			if(null != data && data.length > 0){
				$("#primaryDriver, #secondaryDriver, #editPrimaryDriver, #editSecondaryDriver, #searchDriver").append("<option value=''></option>");
	
				$(data).each(function( index, driver ) {
					$("#primaryDriver, #secondaryDriver, #editPrimaryDriver, #editSecondaryDriver").append("<option value='" + driver.id + "'>" + driver.name + (isEmpty(driver.phone) ? "" : " [" + driver.phone +"]") +"</option>");
					$("#searchDriver").append("<option value='" + driver.name + "'>" + driver.name + "</option>");
				
				});
				
				driverList = data;
				
				$("#primaryDriver").val(primaryDriver);
				$("#secondaryDriver").val(secondaryDriver);
				$("#editPrimaryDriver").val(editPrimaryDriver);
				$("#editSecondaryDriver").val(editSecondaryDriver);
			}
			
		},
		complete : function(data){
			if(null != selectedId){
				$("#" + editId).val(selectedId);
				$("#" + editId).trigger("change");
			}
			
			$( "#primaryDriver" ).on( "change", function() {
				var driverBean = getBeanFromListById(driverList, $(this).val());
				$( "#primaryPhone" ).val(driverBean.phone);
			});
		}
	});	
}


function initDatatable() {
	
	leadDatatable = $('#leadDatatable').DataTable({
		lengthChange: false, 
		autoWidth: false,
      	dom: 'Bfrtip',
		ajax: {
	        url:  getPathName() + '/lead/api/datatable',
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
	    columns: [
		{ mData : function(data, type, full, meta) {
				return data.contactName;
			},
		    sClass: "text-center"}, 
	     { mData : function(data, type, full, meta) {
				leadList.push(data);
				return data.company;
			},
		    sClass: "text-center"}, 
	      
	      { mData : function(data, type, full, meta) {
	
				return data.phone;
			},
		    sClass: "text-center"}, 
	    { mData : function(data, type, full, meta) {
	
				return data.email;
			},
		    sClass: "text-center"},
	    { mData : function(data, type, full, meta) {
	
				return data.facebook;
			},
		    sClass: "text-center" },
		     { mData : function(data, type, full, meta) {
	
				return data.remark;
			},
		    sClass: "text-center" },
		     { mData : function(data, type, full, meta) {
	
				return data.status;
			},
		    sClass: "text-center" },
	    { mData : function(data, type, full, meta) {
	
				return '<div><button type="button" class="btn btn-outline-danger btn-sm delete- mr-1" data-id="' + data.id + '" title="Delete Lead">Delete <i class="fas fa-trash" data-id="' + data.id + '" title="Delete Lead"></i></button><button type="button" class="btn btn-outline-primary edit- btn-sm" data-id="' + data.id + '" title="Edit Lead">Edit <i class="fas fa-edit mt-1" data-id="' + data.id + '" title="Edit Lead"></i></button></div>';
			},
		    sClass: "text-center",
	    	bSortable: false }
	    ],
		"fnDrawCallback": function () {
			$( ".edit-" ).on( "click", function() {
				var Id = $(this).attr("data-id");
				var Bean = getBeanFromListById(leadList, Id);
				$("#Id").val(Id);
				$("#editLicensePlate").val(Bean.licensePlate);
				$("#editPrimaryDriver").val(Bean.primaryDriverId);
				$("#editSecondaryDriver").val(Bean.secondaryDriverId);
				$("#editPhone").val(Bean.phone);
				$("#editStatus").val(Bean.status);
				$( "#editPrimaryDriver" ).on( "change", function() {
					var driverBean = getBeanFromListById(driverList, $(this).val());
					$( "#editPhone" ).val(driverBean.phone);
				});
				$("#editLeadModal").modal();
			});
			
			$( ".delete-" ).on( "click", function() {
				var Id = $(this).attr("data-id");
				var Bean = getBeanFromListById(leadList, Id);
				$("#delLeadId").val(Id);
				$("#delName").text(Bean.licensePlate);
				$("#deleteModal").modal();
			});
	   } 
	});
	
	$("#leadDatatable_filter label").addClass("d-none");
	
}


function getLeadStatus(status){
	switch(status) {
	  case "OK":
		  return "<span class='text-success'>OK</span>";
	  case "REPAIRING":
		  return "<span class='text-warning'>REPAIRING</span>";
	  default:
	}
}

function bindLeadDeleteApi(){
		
		$( "#deleteLead" ).on( "click", function()  {

			showLoadingOverlay();
			
			$.ajax({
				url : getPathName() + "//api/delete",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify($("#delLeadId").val()),
				dataType: 'json',
				success : function(data) {
					if(data.httpStatus == "OK"){
						
						leadDatatable.ajax.reload();
						
						$("#deleteModal").modal("hide");
						
						toastr.success('Lead deleted successfully.');
					
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
	AddValidator = $("#addLeadForm").validate({
		rules : {
			licensePlate : {
				required : true,
				maxlength : 7
			},
			status : {
				required : true
			}
		}
	});
	
	UpdateValidator = $("#editLeadForm").validate({
		rules : {
			editLicensePlate : {
				required : true,
				maxlength : 7
			},
			editStatus : {
				required : true
			}
		}
	});
	
	driverAddValidator = $("#driverForm").validate({
		rules : {
			name : {
				required : true,
				maxlength : 200
			},
			phone : {
				isPhoneLengthValid : true,
				isPhonePatternValid: true
			}
		},
		messages: {
			phone: {
				isPhoneLengthValid: "Please enter no more than 100 characters.",
				isPhonePatternValid: "Please enter a valid phone number."
			}
		},
		errorPlacement : function(error, element) {
			if ($(element).prop("name") === "phone") {
				error.insertBefore($("#phone"));
			} else {
				error.insertAfter(element); // default error placement.
			}
		}
	});
}

