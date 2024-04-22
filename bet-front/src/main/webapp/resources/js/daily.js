let expenseDatatable = {}; 
let fixedExpenseList = [];
let destinationList = [];
let expenseList = [];
let inventoryList = [];
let busList = [];
let inventoryBean = {};
let dailyExpenseValidator;
let expenseAddValidator;
let expenseTypeAddValidator;
let amountTotal = 0;
let expenseTotal = 0;

$(init);

function init() {
	
	bindValidator();
	bindModal();
	
	bindDropDown();
	
	//Date range picker
    $('#dateRange').daterangepicker({
		 locale: {
            format: 'DD/MM/YYYY'
        }
	});
    
    $("#onPaperIncomeLeave, #onPaperIncomeReturn, #inHandCash").on('change', function() {
		if(!isEmpty($("#inHandCash").val())){
			var adjustment = (parseInt($("#onPaperIncomeLeave").val()) + parseInt($("#onPaperIncomeReturn").val())) - parseInt($("#inHandCash").val());
			if(adjustment != 0){
				$("#adjustment").text(adjustment.toLocaleString("en") + " Ks");
			}else{
				$("#adjustment").text("-");
			}
			
		}
		
	});
	
	$("#onPaperIncomeLeave, #onPaperIncomeReturn, #extraIncome").on('change', function() {
		onPaperIncome = getIntFromField($("#onPaperIncomeLeave").val()) + getIntFromField($("#onPaperIncomeReturn").val());
		extraIncome = getIntFromField($("#extraIncome").val());
		amountTotal = onPaperIncome + extraIncome;
		var result = amountTotal - expenseTotal;
		if(result != 0){
			$("#total").text(result.toLocaleString("en") + " Ks");
		}else{
			$("#total").text("-");
		}
	});
}

function bindDropDown(){
	
	bindBusDropDown();
	bindDestinationDropDown();
	
	
	$( "#bus, .path" ).on( "change", function() {
		bindFixedExpenseDropDown();
	});
}

function bindModal(){
	
	initExpenseDatatable();
	
	bindExpenseTypeAddButtonClick();
	bindExpenseAddButtonClick();
	
	bindDailyAddApi();
	
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
					
						$(".path").append("<option value=''></option>");
						
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
				
				}
				
				
			});
}

function bindFixedExpenseDropDown(selectedId){
	
	$("#expenseType").empty();
	
	if(!isEmpty($("#bus").val()) && !isEmpty($("#path2").val())){
		
		hideLoadingOverlay();
		
		$.ajax({
				url : getPathName() + "/fixed-expense/api/dropdown",
				type : "POST",
				data: {
					busId : $("#bus").val(),
					path : ($("#path1").val() + "," + $("#path2").val() + "," + $("#path3").val())
				},
				dataType: 'json',
				success : function(data) {
					
					$("#expenseType").append("<option value=''></option>")
					
					if(null != data && data.length > 0){	
						
						$("#expenseType").append("<optgroup label='Fixed Expense'>");
						
						$(data).each(function( index, fixedExpense ) {
							$("#expenseType").append("<option data-type='fixed' value='" + fixedExpense.expenseId + "'>" + fixedExpense.name + " [" + fixedExpense.amount.toLocaleString("en") +" Ks]</option>");			
						});
						
						$("#expenseType").append("</optgroup>");
						
					}
					
					fixedExpenseList = data;
				},
				complete : function(data){
					bindInventoryDropDown(selectedId);
				}
			});
	}else{
		$("#expenseType").append("<option value=''></option>");
		bindInventoryDropDown(selectedId);
	}
	
}

function bindInventoryDropDown(selectedId){
		
		$.ajax({
				url : getPathName() + "/inventory/api/dropdown",
				type : "GET",
				contentType: "application/json",
				dataType: 'json',
				success : function(data) {
					if(null != data && data.length > 0){
					
						$("#expenseType").append("<optgroup label='Inventory'>");
						
						$(data).each(function( index, inventory ) {
							$("#expenseType").append("<option data-type='inventory' value='" + inventory.id + "'>" + inventory.item + " [" + inventory.price.toLocaleString("en") +" Ks]</option>");				
						});
						
						$("#expenseType").append("</optgroup>");
						
						inventoryList = data;
					}
				},
				complete : function(data){
					
					bindExpenseTypeDropDown(selectedId);
				}
			});
}

function bindExpenseTypeDropDown(selectedId){
	
	
		$.ajax({
				url : getPathName() + "/expense-type/api/dropdown",
				type : "GET",
				contentType: "application/json",
				dataType: 'json',
				success : function(data) {
					if(null != data && data.length > 0){
						
						$("#expenseType").append("<optgroup label='Expense Type'>");				
						$(data).each(function( index, expenseType ) {
							if(null != fixedExpenseList && fixedExpenseList.length > 0){
							$(fixedExpenseList).each(function( index, fixedExpense ) {
								if(fixedExpense.expenseId != expenseType.id){
									$("#expenseType").append("<option data-type='expense' value='" + expenseType.id + "'>" + expenseType.name + "</option>");	
								}
							}); 
							}else{
								$("#expenseType").append("<option data-type='expense' value='" + expenseType.id + "'>" + expenseType.name + "</option>");	
								
							}
								
						});
						$("#expenseType").append("</optgroup>");	
					}
				},
				complete : function(data){
					if(null != selectedId){
						$("#expenseType").val(selectedId);	
					}
					
					$('#expenseType').select2({
						theme: 'bootstrap4',
				    	placeholder: "Select Expense Type.",
				    	allowClear: true
					});
					
					$( "#expenseType" ).on( "change", function() {
						
						var type = $('#expenseType option:selected').attr('data-type');
						
						if(type == "inventory"){
							
							$('#amount').attr("placeholder", "Quantity");
							
							$(inventoryList).each(function( index, inventory ) {
								if($('#expenseType').val() == inventory.id){
									inventoryBean = inventory;
								}
							});
						}else if(type == "fixed"){
							$('#amount').attr("placeholder", "Amount");
							
							$(fixedExpenseList).each(function( index, fixedExpense ) {
								if($('#expenseType').val() == fixedExpense.expenseId){
									$('#amount').val(fixedExpense.amount);
								}
							});
						}else{
							$('#amount').val("");
							$('#amount').attr("placeholder", "Amount");
						}
					  	
					} );
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
						placeholder: "Select Bus.",
						theme: 'bootstrap4',
				    	allowClear: true
					});
				}
			});
}


function bindModalCloseButtonClick(){
	$(".cancel, .close").click(function () {
		resetExpenseTypeAddForm();
	});
}

function bindDailyAddApi(){
	
	$("#saveDaily").click(function () {
		
		if($("#addDailyForm").valid()){
			
			if(expenseList.length > 0){
				
				var dateRange = $("#dateRange").val().split(" - ");
				
				//showLoadingOverlay();
				
				dailyExpenseBean = {
					busId: $("#bus").val(),
					path: ($("#path1").val() + "," + $("#path2").val() + "," + $("#path3").val()),
					fromDateAsString : dateRange[0],
					toDateAsString : dateRange[1],
					onPaperIncomeLeave: getIntFromField($("#onPaperIncomeLeave").val()),
					onPaperIncomeReturn: getIntFromField($("#onPaperIncomeReturn").val()),
					inHandCash: getIntFromField($("#inHandCash").val()),
					extraIncome: getIntFromField($("#extraIncome").val()),
					expenseItemList: expenseList
				};
				
				console.log(dailyExpenseBean);
				
				$.ajax({
					url : getPathName() + "/daily-expense/api/add",
					type : "POST",
					contentType: "application/json",
					data :JSON.stringify(dailyExpenseBean),
					dataType: 'json',
					success : function(data) {
						if(data.httpStatus == "OK"){
							expenseList = [];
							//$("#fixedExpenseModal").modal("hide");
	
							expenseDatatable.clear().draw();
							expenseDatatable.rows.add(expenseList); // Add new data
							expenseDatatable.columns.adjust().draw();
							resetDailyExpenseForm();
							toastr.success('Daily Income/Expense added successfully.');
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

function bindExpenseTypeAddButtonClick(){
	
	$( "#addExpenseType" ).on( "click", function() {
		
		$("#expenseTypeModal").modal();
		
	});
	
	$( "#saveExpenseType" ).on( "click", function() {
			
			if($("#expenseTypeAddForm").valid()){
				
				showLoadingOverlay();
				
				expenseTypeBean = {
					name: $("#expenseTypeName").val()
				};
				
				$.ajax({
					url : getPathName() + "/expense-type/api/add",
					type : "POST",
					contentType: "application/json",
					data :JSON.stringify(expenseTypeBean),
					dataType: 'json',
					success : function(data) {
						if(data.httpStatus == "OK"){
							
							resetExpenseTypeAddForm();
							bindExpenseTypeDropDown(data.createdId);
							
							$("#expenseTypeModal").modal("hide");
	
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


function bindExpenseAddButtonClick(){
	
	$( "#addExpense" ).on( "click", function() {
			
			if(true){
				
				var type = $('#expenseType option:selected').attr('data-type');
				
				if(type == "inventory"){
					
					expenseList.push({
						inventoryId:  $("#expenseType").val(),
						quantity: parseInt($("#amount").val()),
						name:  inventoryBean.item + " [Quantity :" + $("#amount").val() + "]",
						type: type,
						amount: parseInt($("#amount").val()) * parseInt(inventoryBean.price)
					});
				}else{
					expenseList.push({
						expenseTypeId:  $("#expenseType").val(),
						name: $("#expenseType").find(":selected").text(),
						type: type,
						amount: $("#amount").val()
					});
				}
				
				$("#amount").val("");
				$("#expenseType").val(null).trigger('change');
				
				expenseDatatable.clear().draw();
				expenseDatatable.rows.add(expenseList); // Add new data
				expenseDatatable.columns.adjust().draw();
			}
		});
}


function resetExpenseTypeAddForm(){
	$("#expenseTypeName").val("");
	expenseTypeAddValidator.resetForm();
}

function resetDailyExpenseForm(){
	$("#bus, #path1, #path2, #path3, #onPaperIncomeLeave, #onPaperIncomeReturn, #inHandCash, #extraIncome").val("");
	dailyExpenseValidator.resetForm();
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
        searching: false,
         columnDefs: [{ width: '10%', targets: 2 }],
	    columns: [
		{ mData : function(data, type, full, meta) {
				return data.name;
			},
		    sClass: "text-center"}, 
		    { mData : function(data, type, full, meta) {
				return parseInt(data.amount).toLocaleString("en") + " Ks";
			},
		    sClass: "text-right"},      
	 
		      { mData : function(data, type, full, meta) {
				return '<button type="button" class="btn btn-outline-danger delete-expense" data-index="' + meta.row + '" title="Delete Expense"><i class="fas fa-trash"></i></button>';
			},
		    sClass: "text-center",
	    	bSortable: false }
	    ],
		"fnDrawCallback": function (){
			
			$( ".delete-expense" ).on( "click", function() {
				expenseList.splice(parseInt($(this).attr("data-index")), 1); 
				expenseDatatable.clear().draw();
				expenseDatatable.rows.add(expenseList); // Add new data
				expenseDatatable.columns.adjust().draw();
			});
	   } ,
	   footerCallback: function (row, data, start, end, display) {
        let api = this.api();
 
        // Remove the formatting to get integer data for summation
        let intVal = function (i) {
            return typeof i === 'string'
                ? i.replace(/[\Ks,]/g, '') * 1
                : typeof i === 'number'
                ? i
                : 0;
        };
 
        // Total over all pages
        total = api
            .column(1)
            .data()
            .reduce((a, b) => intVal(a) + intVal(b), 0);
 
        // Total over this page
        pageTotal = api
            .column(1, { page: 'current' })
            .data()
            .reduce((a, b) => intVal(a) + intVal(b), 0);
            
        expenseTotal = total;
        var result = amountTotal - expenseTotal;
        if(result != 0){
			$("#total").text(result.toLocaleString("en") + " Ks");
		}else{
			$("#total").text("-");
		}
 
        // Update footer
        api.column(1).footer().innerHTML = pageTotal.toLocaleString("en") + ' Ks (' + total.toLocaleString("en") + ' Ks total)';
    }
	});
		
}


function getIndexFromExpenseListById(list, id){
	var returnIndex = {};
	$.each(list, function(key, value) {
		if(value.expenseTypeId == id){
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
						
						expenseDatatable.ajax.reload();
						
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
	
	expenseTypeAddValidator = $("#expenseTypeAddForm").validate({
		rules : {
			expenseTypeName : {
				required : true
			}
		}
	});
	
	expenseAddValidator = $("#expenseForm").validate({
		rules : {
			expenseType : {
				required : true
			},
			amount : {
				required : true
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
	
	dailyExpenseValidator = $("#addDailyForm").validate({
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

