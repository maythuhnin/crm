let destinationDatatable = {}; 
let destinationList = [];
let destinationAddValidator;
let destinationEditValidator;

$(init);

function init() {
	
	bindValidator();
	bindModal();
	initDestinationDatatable();
	bindSearch();
}

function bindModal(){
	
	bindDestinationAddButtonClick();
	
	bindDestinationAddApi();
	bindDestinationEditApi();
	bindDestinationDeleteApi();
	
	bindModalCloseButtonClick();
	
}


function bindSearch(){
	
	$("#destinationDatatable_filter label").addClass("d-none");
	
	$("#searchBox").on('input', function() {
		destinationDatatable.search($("#searchBox").val()).draw();
	});
	
	$("#clearFilters").on('click', function() {	
		$("#searchBox").val("");
		destinationDatatable.search("").draw();
	});
}

function bindModalCloseButtonClick(){
	$(".cancel, .close").click(function () {
		resetDestinationAddForm();
	});
}

function bindDestinationAddApi(){
	
	$("#saveDestination").click(function () {
		
		if($("#addDestinationForm").valid()){
			
			showLoadingOverlay();
			
			destinationBean = {
				name : $("#name").val(),
				isOrder : $('#isOrder').is(":checked")
			};
			
			$.ajax({
				url : getPathName() + "/destination/api/add",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify(destinationBean),
				dataType: 'json',
				success : function(data) {
					if(data.httpStatus == "OK"){
						destinationDatatable.ajax.reload();
						$("#addDestinationModal").modal("hide");

						resetDestinationAddForm();
						toastr.success('Destination added successfully.');
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

function bindDestinationEditApi(){
	
	$("#editDestination").click(function () {
		
		if($("#editDestinationForm").valid()){
			
			showLoadingOverlay();
			
			destinationBean = {
				id : parseInt($("#editDestinationId").val()),
				name : $("#editName").val(),
				isOrder : $('#editIsOrder').is(":checked")
			};
			
			$.ajax({
				url : getPathName() + "/destination/api/edit",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify(destinationBean),
				dataType: 'json',
				success : function(data) {
					if(data.httpStatus == "OK"){
						destinationDatatable.ajax.reload();
						$("#editDestinationModal").modal("hide");

						resetDestinationAddForm();
						toastr.success('Destination edited successfully.');
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

function bindDestinationAddButtonClick(){
	$( "#addDestination" ).on( "click", function() {
		$("#addDestinationModal").modal();
	});
}


function resetDestinationAddForm(){
	$("#name, #isOrder").val("");
	$("#editName, #editIsOrder, #editDestinationId").val("");
	destinationAddValidator.resetForm();
	destinationEditValidator.resetForm();
}

function getIsOrder(isOrder){
	switch(isOrder) {
	  case true:
		  return "<span class='text-success'>YES</span>";
	  case false:
		  return "NO";
	  default:
	}
}

function initDestinationDatatable() {
	
	destinationDatatable = $('#destinationDatatable').DataTable({
		lengthChange: false, 
		autoWidth: false,
      	dom: 'Bfrtip',
		ajax: {
	        url:  getPathName() + '/destination/api/datatable',
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
         columnDefs: [{ width: '20%', targets: 2 }],
	    columns: [
		{ mData : function(data, type, full, meta) {
				return data.name;
			},
		    sClass: "text-center"},      
	      { mData : function(data, type, full, meta) {
				destinationList.push(data);
				return getIsOrder(data.isOrder);			
			},
		    sClass: "text-center"} ,
		      { mData : function(data, type, full, meta) {
	
				return '<button type="button" class="btn btn-outline-danger delete-destination mr-1 btn-sm" data-id="' + data.id + '" title="Delete Destination">Delete <i class="fas fa-trash"></i></button><button type="button" class="btn btn-outline-primary edit-destination btn-sm" data-id="' + data.id + '" title="Edit Destination">Edit <i class="fas fa-edit"></i></button>';
			},
		    sClass: "text-center",
	    	bSortable: false }
	    ],
		"fnDrawCallback": function () {

			$( ".edit-destination" ).on( "click", function() {
				var destinationId = $(this).attr("data-id");
				var destinationBean = getBeanFromListById(destinationList, destinationId);
			    $('#editDestinationId').val(destinationId);
			    $('#editName').val(destinationBean.name);
			    destinationBean.isOrder ? $('#editIsOrder').prop("checked", true) : $('#editIsOrder').prop("checked", false);
			    
				$("#editDestinationModal").modal();
			});
			
			$( ".delete-destination" ).on( "click", function() {
				var destinationId = $(this).attr("data-id");
				var destinationBean = getBeanFromListById(destinationList, destinationId);
			    $('#delDestinationId').val(destinationId);
			    $('#delName').text(destinationBean.name);
				$("#deleteDestinationModal").modal();
			});
	   } 
	});
		
}

function bindDestinationDeleteApi(){
		
		$( "#deleteDestination" ).on( "click", function()  {

			showLoadingOverlay();
			
			$.ajax({
				url : getPathName() + "/destination/api/delete",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify($("#delDestinationId").val()),
				dataType: 'json',
				success : function(data) {
					if(data.httpStatus == "OK"){
						
						destinationDatatable.ajax.reload();
						
						$("#deleteDestinationModal").modal("hide");
						
						toastr.success('Destination deleted successfully.');
					
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
	destinationAddValidator = $("#addDestinationForm").validate({
		rules : {
			name : {
				required : true,
				maxlength : 200
			}
		}
	});
	
	destinationEditValidator = $("#editDestinationForm").validate({
		rules : {
			editName : {
				required : true,
				maxlength : 200
			}
		}
	});
}

