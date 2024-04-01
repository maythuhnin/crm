let pathDatatable = {}; 
let pathList = [];
let pathAddValidator;
let pathEditValidator;

$(init);

function init() {
	
	bindValidator();
	bindModal();
	initPathDatatable();
	bindSearch();
}

function bindModal(){
	
	bindPathAddButtonClick();
	
	bindPathAddApi();
	bindPathEditApi();
	bindPathDeleteApi();
	
	bindModalCloseButtonClick();
	
}


function bindSearch(){
	
	$("#pathDatatable_filter label").addClass("d-none");
	
	$("#searchBox").on('input', function() {
		pathDatatable.search($("#searchBox").val()).draw();
	});
	
	$("#clearFilters").on('click', function() {	
		$("#searchBox").val("");
		pathDatatable.search("").draw();
	});
}

function bindModalCloseButtonClick(){
	$(".cancel, .close").click(function () {
		resetPathAddForm();
	});
}

function bindPathAddApi(){
	
	$("#savePath").click(function () {
		
		if($("#addPathForm").valid()){
			
			showLoadingOverlay();
			
			pathBean = {
				name : $("#name").val(),
				isOrder : $('#isOrder').is(":checked")
			};
			
			$.ajax({
				url : getPathName() + "/path/api/add",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify(pathBean),
				dataType: 'json',
				success : function(data) {
					if(data.httpStatus == "OK"){
						pathDatatable.ajax.reload();
						$("#addPathModal").modal("hide");

						resetPathAddForm();
						toastr.success('Path added successfully.');
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

function bindPathEditApi(){
	
	$("#editPath").click(function () {
		
		if($("#editPathForm").valid()){
			
			showLoadingOverlay();
			
			pathBean = {
				id : parseInt($("#editPathId").val()),
				name : $("#editName").val(),
				isOrder : $('#editIsOrder').is(":checked")
			};
			
			$.ajax({
				url : getPathName() + "/path/api/edit",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify(pathBean),
				dataType: 'json',
				success : function(data) {
					if(data.httpStatus == "OK"){
						pathDatatable.ajax.reload();
						$("#editPathModal").modal("hide");

						resetPathAddForm();
						toastr.success('Path edited successfully.');
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

function bindPathAddButtonClick(){
	$( "#addPath" ).on( "click", function() {
		$("#addPathModal").modal();
	});
}


function resetPathAddForm(){
	$("#name, #isOrder").val("");
	$("#editName, #editIsOrder, #editPathId").val("");
	pathAddValidator.resetForm();
	pathEditValidator.resetForm();
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

function initPathDatatable() {
	
	pathDatatable = $('#pathDatatable').DataTable({
		lengthChange: false, 
		autoWidth: false,
      	dom: 'Bfrtip',
		ajax: {
	        url:  getPathName() + '/path/api/datatable',
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
				pathList.push(data);
				return getIsOrder(data.isOrder);			
			},
		    sClass: "text-center"} ,
		      { mData : function(data, type, full, meta) {
	
				return '<button type="button" class="btn btn-outline-danger delete-path mr-1 btn-sm" data-id="' + data.id + '" title="Delete Path">Delete <i class="fas fa-trash"></i></button><button type="button" class="btn btn-outline-primary edit-path btn-sm" data-id="' + data.id + '" title="Edit Path">Edit <i class="fas fa-edit"></i></button>';
			},
		    sClass: "text-center",
	    	bSortable: false }
	    ],
		"fnDrawCallback": function () {

			$( ".edit-path" ).on( "click", function() {
				var pathId = $(this).attr("data-id");
				var pathBean = getBeanFromListById(pathList, pathId);
			    $('#editPathId').val(pathId);
			    $('#editName').val(pathBean.name);
			    pathBean.isOrder ? $('#editIsOrder').prop("checked", true) : $('#editIsOrder').prop("checked", false);
			    
				$("#editPathModal").modal();
			});
			
			$( ".delete-path" ).on( "click", function() {
				var pathId = $(this).attr("data-id");
				var pathBean = getBeanFromListById(pathList, pathId);
			    $('#delPathId').val(pathId);
			    $('#delName').text(pathBean.name);
				$("#deletePathModal").modal();
			});
	   } 
	});
		
}

function bindPathDeleteApi(){
		
		$( "#deletePath" ).on( "click", function()  {

			showLoadingOverlay();
			
			$.ajax({
				url : getPathName() + "/path/api/delete",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify($("#delPathId").val()),
				dataType: 'json',
				success : function(data) {
					if(data.httpStatus == "OK"){
						
						pathDatatable.ajax.reload();
						
						$("#deletePathModal").modal("hide");
						
						toastr.success('Path deleted successfully.');
					
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
	pathAddValidator = $("#addPathForm").validate({
		rules : {
			name : {
				required : true,
				maxlength : 200
			}
		}
	});
	
	pathEditValidator = $("#editPathForm").validate({
		rules : {
			editName : {
				required : true,
				maxlength : 200
			}
		}
	});
}

