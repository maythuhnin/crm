let inventoryDatatable = {}; 
let inventoryList = [];
let inventoryAddValidator;
let inventoryEditValidator;

$(init);

function init() {
	
	bindValidator();
	bindModal();
	initInventoryDatatable();
	bindSearch();
}

function bindModal(){
	
	bindInventoryAddButtonClick();
	
	bindInventoryAddApi();
	bindInventoryEditApi();
	bindInventoryDeleteApi();
	
	bindModalCloseButtonClick();
	
	//Date picker
	$('#receivedDate').datetimepicker({
		format: 'DD/MM/YYYY',
		defaultDate: new Date(),  
	});
	
}

function bindSearch(){
	
	$("#inventoryDatatable_filter label").addClass("d-none");
	
	$("#searchBox").on('input', function() {
		inventoryDatatable.search($("#searchBox").val()).draw();
	});
	
	$("#clearFilters").on('click', function() {	
		$("#searchBox").val("");
		inventoryDatatable.search("").draw();
	});
}

function bindModalCloseButtonClick(){
	$(".cancel, .close").click(function () {
		resetInventoryAddForm();
	});
}

function bindInventoryAddApi(){
	
	$("#saveInventory").click(function () {
		
		if($("#addInventoryForm").valid()){
			
			showLoadingOverlay();
			
			inventoryBean = {
				item : $("#item").val(),
				receivedDateAsString : $("#receivedDate").find("input").val(),
				quantity : $("#quantity").val(),
				price : $("#price").val()
			};
			
			$.ajax({
				url : getPathName() + "/inventory/api/add",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify(inventoryBean),
				dataType: 'json',
				success : function(data) {
					if(data.httpStatus == "OK"){
						inventoryDatatable.ajax.reload();
						$("#addInventoryModal").modal("hide");

						resetInventoryAddForm();
						toastr.success('Inventory added successfully.');
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

function bindInventoryEditApi(){
	
	$("#editInventory").click(function () {
		
		if($("#editInventoryForm").valid()){
			
			showLoadingOverlay();
			
			inventoryBean = {
				id : parseInt($("#editInventoryId").val()),
				item : $("#editItem").val(),
				receivedDateAsString : $("#editReceivedDate").find("input").val(),
				quantity : $("#editQuantity").val(),
				price : $("#editPrice").val()
			};
			
			$.ajax({
				url : getPathName() + "/inventory/api/edit",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify(inventoryBean),
				dataType: 'json',
				success : function(data) {
					if(data.httpStatus == "OK"){
						inventoryDatatable.ajax.reload();
						$("#editInventoryModal").modal("hide");

						resetInventoryAddForm();
						toastr.success('Inventory edited successfully.');
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

function bindInventoryAddButtonClick(){
	$( "#addInventory" ).on( "click", function() {
		
		$("#addInventoryModal").modal();
	});
}


function resetInventoryAddForm(){
	$('#receivedDate, #editReceivedDate').datetimepicker({
		format: 'DD/MM/YYYY',
		defaultDate: new Date(),
	});
	$("#item, #price, #quantity").val("");
	$("#editItem, #editPrice, #editQuantity").val("");
	inventoryAddValidator.resetForm();
	inventoryEditValidator.resetForm();
}


function initInventoryDatatable() {
	
	inventoryDatatable = $('#inventoryDatatable').DataTable({
		lengthChange: false, 
		autoWidth: false,
      	dom: 'Bfrtip',
		ajax: {
	        url:  getPathName() + '/inventory/api/datatable',
	        type: "GET",
	         data : function(d) {
				
			},
	        dataSrc: 'responseData',
	        dataType: "json"
	    },
	    "order": [0],
	    scrollX:        true,
        scrollCollapse: true,
         columnDefs: [{ width: '25%', targets: 4 }],
	    columns: [
		{ mData : function(data, type, full, meta) {
				return data.item;
			},
		    sClass: "text-center"},      
	      { mData : function(data, type, full, meta) {
				inventoryList.push(data);
				return data.receivedDate;			
			},
		    sClass: "text-center"} ,
		    { mData : function(data, type, full, meta) {
				return data.price.toLocaleString("en") + " Ks";			
			},
		    sClass: "text-right"} ,
		    { mData : function(data, type, full, meta) {
				return data.quantity;			
			},
		    sClass: "text-center"} ,
		      { mData : function(data, type, full, meta) {
				return '<button type="button" class="btn btn-default btn-sm stock-history mr-1" data-id="' + data.id + '" title="Stock History">Stock History <i class="fas fa-info-circle"></i></button><button type="button" class="btn btn-outline-danger btn-sm delete-inventory mr-1" data-id="' + data.id + '" title="Delete Inventory">Delete <i class="fas fa-trash"></i></button>';
			},
		    sClass: "text-center",
	    	bSortable: false }
	    ],
		"fnDrawCallback": function () {

			$( ".stock-history" ).on( "click", function() {
				var inventoryId = $(this).attr("data-id");
				$("#stockHistoryModal").modal();	
				 setTimeout(function() {
				     initStockHistoryDatatable(inventoryId);
				  }, 100);
			});
			
			$( ".edit-inventory" ).on( "click", function() {
				var inventoryId = $(this).attr("data-id");
				var inventoryBean = getBeanFromListById(inventoryList, inventoryId);
			    $('#editInventoryId').val(inventoryId);
			    $('#editItem').val(inventoryBean.item);
			    $('#editQuantity').val(inventoryBean.quantity);
			    $('#editPrice').val(inventoryBean.price);
			    var date = moment(inventoryBean.receivedDate, "DD/MM/YYYY");
			   
			   console.log(date.toDate());
			    $('#editReceivedDate').datetimepicker({
					format: 'DD/MM/YYYY',
					defaultDate: date.toDate(),  
				});
			    $("#editInventoryModal").modal();
			});
			
			$( ".delete-inventory" ).on( "click", function() {
				var inventoryId = $(this).attr("data-id");
				var inventoryBean = getBeanFromListById(inventoryList, inventoryId);
			    $('#delInventoryId').val(inventoryId);
			    $('#delName').text(inventoryBean.item);
				$("#deleteInventoryModal").modal();
			});
	   } 
	});
		
}


function initStockHistoryDatatable(inventoryId) {
	
	$('#stockHistoryDatatable').DataTable().clear().destroy();
	
	stockHistoryDatatable = $('#stockHistoryDatatable').DataTable({
		lengthChange: false, 
		autoWidth: false,
      	dom: 'Bfrtip',
		ajax: {
	        url:  getPathName() + '/inventory/stock/api/datatable',
	        type: "GET",
	        data : function(d) {
				d.inventoryId = inventoryId;
			},
	        dataSrc: 'responseData',
	        dataType: "json"
	    },
	    "order": [2],
	     "scrollX": true,
        "sScrollXInner": "100%",
        scrollCollapse: true,
        searching: false,
         columnDefs: [{ width: '20%', targets: 2 }],
	    columns: [
		{ mData : function(data, type, full, meta) {
				return (data.stockIn) ? "<span class='text-success'>Stock In</span>" : "<span class='text-danger'>Stock Out</span>";
			},
		    sClass: "text-center"},
		   { mData : function(data, type, full, meta) {
				return data.quantity;
			},
		    sClass: "text-center"}, 
		    { mData : function(data, type, full, meta) {
				return data.transactionDate;
			},
		    sClass: "text-center"},     
	      { mData : function(data, type, full, meta) {
	
				return isEmpty(data.transactionRef) ? "-" : data.transactionRef;
			},
		    sClass: "text-right"}
		      
	    ]
	});
		
}



function bindInventoryDeleteApi(){
		
		$( "#deleteInventory" ).on( "click", function()  {

			showLoadingOverlay();
			
			$.ajax({
				url : getPathName() + "/inventory/api/delete",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify($("#delInventoryId").val()),
				dataType: 'json',
				success : function(data) {
					if(data.httpStatus == "OK"){
						
						inventoryDatatable.ajax.reload();
						
						$("#deleteInventoryModal").modal("hide");
						
						toastr.success('Inventory deleted successfully.');
					
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
	inventoryAddValidator = $("#addInventoryForm").validate({
		rules : {
			item : {
				required : true,
				maxlength: 200
			},
			quantity : {
				required : true
			},
			price : {
				required : true
			},
			receivedDate : {
				required : true,
				dateFormat: true
			}
		},
		errorPlacement : function(error, element) {
			if ($(element).prop("name") === "receivedDate") {
				error.insertAfter($("#receivedDate"));
			} else {
				error.insertAfter(element); // default error placement.
			}
		}
	});
	
	inventoryEditValidator = $("#editInventoryForm").validate({
		rules : {
			editItem : {
				required : true,
				maxlength: 200
			},
			editQuantity : {
				required : true
			},
			editPrice : {
				required : true
			},
			editReceivedDate : {
				required : true
			}
		}
	});
}

