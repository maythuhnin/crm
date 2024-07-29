let fixedExpenseDatatable = {}; 
let fixedExpenseList = [];
let destinationList = [];
let expenseList = [];
let busList = [];
let fixedExpenseAddValidator;
let expenseAddValidator;

$(init);

function init() {
	
	bindValidator();
	bindModal();
	
	bindDestinationDropDown();
	
	bindSearch();
	
}

function bindModal(){
	
	initExpenseDatatable();
	
	bindExpenseTypeDropDown();
	bindBusDropDown();
	
	bindFixedExpenseAddButtonClick();
	
	bindFixedExpenseAddApi();
	bindFixedExpenseDeleteApi();
	
	bindModalCloseButtonClick();
	
}


function bindDestinationDropDown(){
	
			$.ajax({
				url : getPathName() + "/destination/api/dropdown",
				type : "GET",
				contentType: "application/json",
				dataType: 'json',
				success : function(data) {
					if(null != data && data.length > 0){
					
						$(".path").append("<option value=''></option>")
						
						$(data).each(function( index, destination ) {
							$(".path").append("<option value='" + destination.id + "'>" + destination.name + "</option>");				
						});
					
						destinationList = data;
						
						
					}
				},
				complete : function(data){
					
					$('#path1').select2({
						theme: 'bootstrap4',
				    	placeholder: "Select Path.",
				    	allowClear: true
					});
					
					$('#path2').select2({
						theme: 'bootstrap4',
				    	placeholder: "Select Path.",
				    	allowClear: true
					});
					
					$('#path3').select2({
						theme: 'bootstrap4',
				    	placeholder: "Select Path.",
				    	allowClear: true
					});
					
					initFixedExpenseDatatable();
				}
				
				
			});
}


function bindExpenseTypeDropDown(){
	

			$.ajax({
				url : getPathName() + "/expense-type/api/dropdown",
				type : "GET",
				contentType: "application/json",
				dataType: 'json',
				success : function(data) {
					if(null != data && data.length > 0){
					
					$("#expenseType").append("<option value=''></option>")
					
					$(data).each(function( index, expenseType ) {
						$("#expenseType").append("<option value='" + expenseType.id + "'>" + expenseType.name + "</option>");				
					});
					
			}
				},
				complete : function(data){
					$('#expenseType').select2({
						theme: 'bootstrap4',
				    	placeholder: "Select Expense Type.",
				    	allowClear: true
					});
				}
			});
}

function bindBusDropDown(){
	

			$.ajax({
				url : getPathName() + "/bus/api/dropdown",
				type : "GET",
				contentType: "application/json",
				dataType: 'json',
				success : function(data) {
					if(null != data && data.length > 0){
					
					$("#bus").append("<option value=''></option>")
					
					$(data).each(function( index, bus ) {
						$("#bus").append("<option value='" + bus.id + "'>" + bus.licensePlate + "</option>");				
					});
					
					busList = data;
					
			}
				},
				complete : function(data){
					$('#bus').select2({
						theme: 'bootstrap4',
				    	allowClear: true
					});
				}
			});
}


function bindSearch(){
	 
	$(".dataTables_filter").addClass("d-none");
	
	$("#searchBox").on('input', function() {
		fixedExpenseDatatable.search($("#searchBox").val()).draw();
	});
	
	$("#clearFilters").on('click', function() {	
		$("#searchBox").val("");
		fixedExpenseDatatable.search("").draw();
	});
}

function bindModalCloseButtonClick(){
	$(".cancel, .close").click(function () {
		resetFixedExpenseAddForm();
	});
}

function bindFixedExpenseAddApi(){
	
	$("#saveFixedExpense").click(function () {
		
		if($("#addFixedExpenseForm").valid()){
			
			if(expenseList.length > 0){
				
				var url = "";
				var successMsg = "";
				
				if(isEmpty($("#editFixedExpenseId").val())){
					url = "/fixed-expense/api/add";
					successMsg = 'FixedExpense added successfully.';
				}else{
					url = "/fixed-expense/api/edit";
					successMsg = 'FixedExpense edited successfully.';
				}
				showLoadingOverlay();
				
				var busList = [];
				$($("#bus").val()).each(function( index, busId ) {
					if(!isEmpty(busId)){
						busList.push({busId : parseInt(busId)})	
					}
				});
				
				pathBean = {
					id: $("#editFixedExpenseId").val(),
					path: ($("#path1").val() + "," + $("#path2").val() + "," + $("#path3").val()),
					pathBusList: busList,
					pathExpenseList: expenseList
				};
				
				$.ajax({
					url : getPathName() + url,
					type : "POST",
					contentType: "application/json",
					data :JSON.stringify(pathBean),
					dataType: 'json',
					success : function(data) {
						if(data.httpStatus == "OK"){
							fixedExpenseDatatable.ajax.reload();
							$("#fixedExpenseModal").modal("hide");
	
							resetFixedExpenseAddForm();
							toastr.success(successMsg);
						}else if(data.httpStatus == "INTERNAL_SERVER_ERROR"){
							toastr.error(data.error);
						}
					},
					complete : function(data){
						hideLoadingOverlay();
					}
				});
	
			}else{
				toastr.warning("No Fixed Expense added.");
			}
		}
		
	});
}

function bindFixedExpenseAddButtonClick(){
	
	$( "#addFixedExpense" ).on( "click", function() {
		
		$(".add-title").removeClass("d-none");
		$(".edit-title").addClass("d-none");
				
		$('#editFixedExpenseId').val("");
		$("#fixedExpenseModal").modal();
		
		
	});
	
	$( "#addExpense" ).on( "click", function() {
			
			if($("#expenseForm").valid()){
				
				expenseList.push({
					expenseId:  $("#expenseType").val(),
					name:  $("#expenseType").find(":selected").text(),
					amount: $("#amount").val()
				});
				
				$("#amount").val("");
				$("#expenseType").val(null).trigger('change');
				
				expenseDatatable.clear().draw();
				expenseDatatable.rows.add(expenseList); // Add new data
				expenseDatatable.columns.adjust().draw();
			}
		});
}


function resetFixedExpenseAddForm(){
	$("#amount").val("");
	$("#path1, #path2, #path3, #expenseType, #bus").val(null).trigger('change');
	fixedExpenseAddValidator.resetForm();
	expenseAddValidator.resetForm();
	
	expenseList = [];
	expenseDatatable.clear().draw();
	expenseDatatable.rows.add(expenseList); // Add new data
	expenseDatatable.columns.adjust().draw();
}


function initFixedExpenseDatatable() {
	
	fixedExpenseDatatable = $('#fixedExpenseDatatable').DataTable({
		lengthChange: false, 
		autoWidth: false,
      	dom: 'Bfrtip',
		ajax: {
	        url:  getPathName() + '/fixed-expense/api/datatable',
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
         columnDefs: [{ width: '20%', targets: 3 }],
	    columns: [
		 {
                className: 'dt-control',
                orderable: false,
                data: null,
                defaultContent: '',
	    	bSortable: false
            },  
		{ mData : function(data, type, full, meta) {
				return getPathText(data.path);
			},
		    sClass: "text-center"}, 
		{ mData : function(data, type, full, meta) {
				return isEmpty(data.bus) ? "-" : data.bus;
			},
		    sClass: "text-center"},      
	 
		      { mData : function(data, type, full, meta) {
				fixedExpenseList.push(data);
				return '<button type="button" class="btn btn-outline-danger delete-fixedExpense mr-1 btn-sm" data-id="' + data.id + '" title="Delete FixedExpense">Delete <i class="fas fa-trash"></i></button><button type="button" class="btn btn-outline-primary edit-fixedExpense btn-sm" data-id="' + data.id + '" title="Edit FixedExpense">Edit <i class="fas fa-edit"></i></button>';
			},
		    sClass: "text-center",
	    	bSortable: false }
	    ],
		"fnDrawCallback": function () {

			$( ".edit-fixedExpense" ).on( "click", function() {
				
				$(".add-title").addClass("d-none");
				$(".edit-title").removeClass("d-none");
				
				var fixedExpenseId = $(this).attr("data-id");
				var fixedExpenseBean = getBeanFromListById(fixedExpenseList, fixedExpenseId);
				var destinationIds = fixedExpenseBean.path.split(",");
				
				$(destinationIds).each(function( index, destinationId ) {
					$("#path" + (index+1)).val(destinationId).trigger('change');
				});
				
				if(!isEmpty(fixedExpenseBean.busIds)){
					$('#bus').val(fixedExpenseBean.busIds.split(",")).change();
				}
				
				expenseList = getExpense(fixedExpenseId);
				
				expenseDatatable.clear().draw();
				expenseDatatable.rows.add(expenseList); // Add new data
				expenseDatatable.columns.adjust().draw();
				
			    $('#editFixedExpenseId').val(fixedExpenseId);
			    
				$("#fixedExpenseModal").modal();
			});
			
			$( ".delete-fixedExpense" ).on( "click", function() {
				var fixedExpenseId = $(this).attr("data-id");
				var fixedExpenseBean = getBeanFromListById(fixedExpenseList, fixedExpenseId);
			    $('#delFixedExpenseId').val(fixedExpenseId);
			    $('#delName').empty();
			    $('#delName').append(getPathText(fixedExpenseBean.path));
				$("#deleteFixedExpenseModal").modal();
			});
	   } 
	});
	
	$('#fixedExpenseDatatable tbody').on('click', 'td.dt-control', function () {
        var tr = $(this).closest('tr');
        var row = fixedExpenseDatatable.row(tr);
 
        if (row.child.isShown()) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        } else {
            // Open this row
            row.child(bindExpenseSubTable(row.data().id)).show();
            tr.addClass('shown');
        }
        
    });
		
}

function bindExpenseSubTable(pathId){
	
	var subtableList = getExpense(pathId);
	var returnHtml = "";
	
	returnHtml += '<table cellpadding="5" cellspacing="0" border="0" class="table table-bordered sub-table">' +
        	'<tr>' +
	        '<th class="text-center">Expense Type</th>' +
	        '<th class="text-right">Amount</th>' +
       		 '</tr>';
       		 if(subtableList.length > 0){
		 		$.each(subtableList, function(key, value) {
					returnHtml += '<tr>' +
		        	'<td class="text-center">' + value.name + '</td>' +
			        '<td class="text-right">' + value.amount.toLocaleString("en") + ' Ks </td>' +
			        '</tr>'
	        	});
				}else{
					returnHtml += '<tr><td colspan="2" class="text-center">No data available in table</td></td>';
				}
         
       returnHtml +=  '</table>';
       
       return returnHtml;
}

function getExpense(pathId){
	
	var returnList = [];
	
		$.ajax({
			url:  getPathName() + '/fixed-expense/amount/api/datatable',
	        type: "POST",
	         data : {
				pathId : parseInt(pathId)
			},
	        dataSrc: 'responseData',
	        dataType: "json",
			async : false,
			success : function(data) {
				
				returnList = data;
	       
			}
	});
	
	return returnList;
	
}


function initExpenseDatatable() {
	
	expenseDatatable = $('#expenseDatatable').DataTable({
		lengthChange: false, 
		autoWidth: false,
      	dom: 'Bfrtip',
		data: expenseList,
	    "order": [0],
	    scrollX:        true,
        scrollCollapse: true,
         columnDefs: [{ width: '10%', targets: 2 }],
	    columns: [
		{ mData : function(data, type, full, meta) {
				return data.name;
			},
		    sClass: "text-center"}, 
		    { mData : function(data, type, full, meta) {
				return data.amount.toLocaleString("en") + " Ks";
			},
		    sClass: "text-right"},      
	 
		      { mData : function(data, type, full, meta) {
	
				return '<button type="button" class="btn btn-outline-danger delete-expense" data-id="' + data.expenseId + '" title="Delete Expense"><i class="fas fa-trash"></i></button>';
			},
		    sClass: "text-center",
	    	bSortable: false }
	    ],
		"fnDrawCallback": function (){
			
			$( ".delete-expense" ).on( "click", function() {
				expenseList.splice(getIndexFromExpenseListById(expenseList, parseInt($(this).attr("data-id")), 1)); 
				expenseDatatable.clear().draw();
				expenseDatatable.rows.add(expenseList); // Add new data
				expenseDatatable.columns.adjust().draw();
			});
	   } 
	});
		
}


function getIndexFromExpenseListById(list, id){
	var returnIndex = {};
	$.each(list, function(key, value) {
		if(value.expenseId == id){
			returnIndex = key;
		}
	});
	
	return returnIndex;
}

function bindFixedExpenseDeleteApi(){
		
		$( "#deleteFixedExpense" ).on( "click", function()  {

			showLoadingOverlay();
			
			$.ajax({
				url : getPathName() + "/fixed-expense/api/delete",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify($("#delFixedExpenseId").val()),
				dataType: 'json',
				success : function(data) {
					if(data.httpStatus == "OK"){
						
						fixedExpenseDatatable.ajax.reload();
						
						$("#deleteFixedExpenseModal").modal("hide");
						
						toastr.success('FixedExpense deleted successfully.');
					
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

function getPathText(path){
	var pathText = "";
	var pathIds = path.split(",");
	
	$(pathIds).each(function( index, pathId ) {
		$(destinationList).each(function( index, destination ) {
			if(pathId == destination.id){
				pathText += (pathText != "" ? ' <i class="fas fa-exchange-alt"></i> ' : "") + destination.name;
			}
		});
	});
	
	return pathText;
}

function getBusText(bus){
	var busText = "";
	var busIds = bus.split(",");
	
	$(busIds).each(function( index, busId ) {
		$(busList).each(function( index, bus ) {
			if(busId == bus.id){
				busText += (busText != "" ? ' , ' : "") + bus.licensePlate;
			}
		});
	});
	
	return busText;
}

function bindValidator(){
	expenseAddValidator = $("#expenseForm").validate({
		rules : {
			expenseType : {
				required : true
			},
			amount : {
				required : true,
				min: 0
			}
		},
		errorPlacement : function(error, element) {
			if ($(element).prop("name") === "expenseType") {
				error.insertAfter($("#expenseType").closest("div").find(".select2-container"));
			} else {
				error.insertAfter(element); // default error placement.
			}
		}
	});
	
	fixedExpenseAddValidator = $("#addFixedExpenseForm").validate({
		rules : {
			path2 : {
				required : true
			},
			path1 : {
				required : {
			        depends: function(element) {
			          return (isEmpty($("#path3").val()));
			        }
				}
			},
			path3 : {
				required : {
			        depends: function(element) {
			          return (isEmpty($("#path1").val()));
			        }
				}
			}
		},
		errorPlacement : function(error, element) {
			
				error.insertAfter($("#" + $(element).prop("name")).closest("div").find(".select2-container"));
			
		}
	});
	
}

