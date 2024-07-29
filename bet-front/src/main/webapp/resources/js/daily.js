let expenseDatatable = {}; 
let fixedExpenseList = [];
let expenseTypeList = [];
let destinationList = [];
let expenseList = [];
let inventoryList = [];
let busList = [];
let inventoryBean = {};
let dailyExpenseValidator;
let expenseAddValidator;
let amountTotal = 0;
let expenseTotal = 0;

$(init);

function init() {
	
	bindValidator();
	initExpenseDatatable();
	bindModal();
	bindDropDown();
	bindAmountCalc();
	bindCheckBox();
	
	//Date range picker
    $('#dateRange').daterangepicker({
		autoUpdateInput: false,
		 locale: {
            format: 'DD/MM/YYYY',
              cancelLabel: 'Clear'
        }
	});
	
	$('#dateRange').on('apply.daterangepicker', function(ev, picker) {
     	$(this).val(picker.startDate.format('DD/MM/YYYY') + ' - ' + picker.endDate.format('DD/MM/YYYY'));
  	});

}

function bindCheckBox(){
	
	$("#restDay").on('change', function() {
		if($('#restDay').is(':checked')){
			$(".no-rest").addClass("hide");
			$(".no-rest").removeClass("show");
		}else{
			$(".no-rest").addClass("show");
			$(".no-rest").removeClass("hide");
		}
		
	});
}

function bindAmountCalc(){
	
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
	getExpenseTypeData();
	
	$( "#bus, .path" ).on( "change", function() {
		bindFixedExpenseDropDown(false);
	});
}

function bindModal(){

	bindExpenseAddButtonClick();
	
	bindDailyAddApi();
	
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

function bindFixedExpenseDropDown(fromReset){
	
	if((!isEmpty($("#path1").val()) && !isEmpty($("#path2").val())) || (fromReset)){
		
		hideLoadingOverlay();
		
		$("#expenseType").empty();
		
		$.ajax({
				url : getPathName() + "/fixed-expense/api/dropdown",
				type : "POST",
				data: {
					busId : $("#bus").val(),
					path : (getEmptyStringIfNull($("#path1").val()) + "," + getEmptyStringIfNull($("#path2").val()) + "," + getEmptyStringIfNull($("#path3").val()))
				},
				dataType: 'json',
				success : function(data) {
					
					$("#expenseType").append("<option value=''></option>");
					
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
					if(!fromReset){
						bindInventoryDropDown();
					}
					
				}
			});
	}
	
}


function getInventoryData(){
		
		$.ajax({
				url : getPathName() + "/inventory/api/dropdown",
				type : "GET",
				contentType: "application/json",
				dataType: 'json',
				success : function(data) {
					
					inventoryList = data;
					
				},
				complete : function(data){
					bindInventoryDropDown();
					
				}
			});
}


function bindInventoryDropDown(selectedId){
		
		$("#expenseType").append("<option value=''></option>");
		
		if(null != inventoryList && inventoryList.length > 0){
		$("#expenseType").append("<optgroup label='Inventory'>");
						
		$(inventoryList).each(function( index, inventory ) {
			$("#expenseType").append("<option data-type='inventory' value='" + inventory.id + "'>" + inventory.item + " [" + inventory.price.toLocaleString("en") +" Ks] [InStock : " + inventory.quantity +"] </option>");				
		});
		
		$("#expenseType").append("</optgroup>");
		}
						
		bindExpenseTypeDropDown();
				
			
}


function getExpenseTypeData(){
	
	
		$.ajax({
				url : getPathName() + "/expense-type/api/dropdown",
				type : "GET",
				contentType: "application/json",
				dataType: 'json',
				success : function(data) {
					if(null != data && data.length > 0){
						
						expenseTypeList = data;	
					}
				},
				complete : function(data){
					
					getInventoryData();
				}
			});	
		
}

function bindExpenseTypeDropDown(selectedId){
	
	$("#expenseType").append("<optgroup label='Expense Type'>");				
						
	$(expenseTypeList).each(function( index, expenseType ) {
		var flag = false;
		if(null != fixedExpenseList && fixedExpenseList.length > 0){	
			$(fixedExpenseList).each(function( index, fixedExpense ) {
				if(fixedExpense.expenseId == expenseType.id){
					flag = true;
				}
			}); 
			
			if(!flag){
				$("#expenseType").append("<option data-type='expense' value='" + expenseType.id + "'>" + expenseType.name + "</option>");		
			}
		}else{
			$("#expenseType").append("<option data-type='expense' value='" + expenseType.id + "'>" + expenseType.name + "</option>");							
		}							
	});
	
	$("#expenseType").append("</optgroup>");	
					
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
			
			$('#amount').attr("max", inventoryBean.quantity);
		}else if(type == "fixed"){
			$('#amount').attr("placeholder", "Amount");
							
			$(fixedExpenseList).each(function( index, fixedExpense ) {
				if($('#expenseType').val() == fixedExpense.expenseId){
					$('#amount').val(fixedExpense.amount);
				}
			});
			
			$('#amount').removeAttr("max");
		}else{
			$('#amount').val("");
			$('#amount').removeAttr("max");
			$('#amount').attr("placeholder", "Amount");
		}
					  	
	} );
			
		
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

function bindDailyAddApi(){
	
	$("#saveDaily").click(function () {
		
		if($("#addDailyForm").valid()){
			
			if(expenseList.length > 0){
				
				var dateRange = $("#dateRange").val().split(" - ");
				
				showLoadingOverlay();
				
				dailyExpenseBean = {
					busId: $("#bus").val(),
					fromDateAsString : dateRange[0],
					toDateAsString : dateRange[1],
					restDay : $('#restDay').is(':checked') ? true : false,
					expenseItemList: expenseList
				};
				
				if(!$('#restDay').is(':checked')){
					dailyExpenseBean.path = ($("#path1").val() + "," + $("#path2").val() + "," + $("#path3").val());
					dailyExpenseBean.onPaperIncomeLeave = getIntFromField($("#onPaperIncomeLeave").val());
					dailyExpenseBean.onPaperIncomeReturn = getIntFromField($("#onPaperIncomeReturn").val());
					dailyExpenseBean.inHandCash = getIntFromField($("#inHandCash").val());
					dailyExpenseBean.extraIncome = getIntFromField($("#extraIncome").val());
				}
				
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
				toastr.error("No Expense added.");
			}
		}
		
	});
}

function bindExpenseAddButtonClick(){
	
	$( "#addExpense" ).on( "click", function() {
			
			if($("#expenseForm").valid()){
				
				var type = $('#expenseType option:selected').attr('data-type');
				
				if(type == "inventory"){
					
					var totalQuantity = 0;
					
					$.each(expenseList, function(key, value) {
						if(value.inventoryId == inventoryBean.id){
							totalQuantity += value.quantity;
						}
					});
					
					if(inventoryBean.quantity >= (totalQuantity + parseInt($("#amount").val()))){
						expenseList.push({
						inventoryId:  $("#expenseType").val(),
						quantity: parseInt($("#amount").val()),
						name:  inventoryBean.item + " [Quantity :" + $("#amount").val() + "]",
						type: type,
						amount: parseInt($("#amount").val()) * parseInt(inventoryBean.price)
						});
					}else{
						toastr.error("Insufficient Quantity. In Stock : " + inventoryBean.quantity);
					}
					
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

function resetDailyExpenseForm(){
	$('#restDay').removeAttr('checked');
	$("#bus, #path1, #path2, #path3").val('').trigger("change");
	$("#onPaperIncomeLeave, #onPaperIncomeReturn, #inHandCash, #extraIncome, #dateRange").val("");
	$("#total, #adjustment").text("-");
	 //Date range picker
    $('#dateRange').daterangepicker({
		autoUpdateInput: false,
		 locale: {
            format: 'DD/MM/YYYY',
              cancelLabel: 'Clear'
        }
	});
	
	$('#dateRange').on('apply.daterangepicker', function(ev, picker) {
     	$(this).val(picker.startDate.format('DD/MM/YYYY') + ' - ' + picker.endDate.format('DD/MM/YYYY'));
  	});
  	
	$("#expenseType").empty();
	getInventoryData();
	bindFixedExpenseDropDown(true);
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
        api.column(1).footer().innerHTML = total.toLocaleString("en") + ' Ks';
   		
    }
	});
	
	$(".dataTables_scrollFootInner").removeAttr("style");
		
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
			bus : {
				required : true
			},
			dateRange : {
				required : true,
				dateRangeFormat: true
			},
			path2 : {
				required : {
					depends: function(element) {
				    	return !$('#restDay').is(':checked');
				    }
				}
			},
			path1 : {
				required : {
			        depends: function(element) {
			          return (isEmpty($("#path3").val()) && !$('#restDay').is(':checked'));
			        }
				}
			},
			path3 : {
				required : {
			        depends: function(element) {
			          return (isEmpty($("#path1").val()) && !$('#restDay').is(':checked'));
			        }
				}
			},
			onPaperIncomeLeave : {
				required : {
					depends: function(element) {
				    	return !$('#restDay').is(':checked');
				    }
				}
			},
			onPaperIncomeReturn : {
				required : {
					depends: function(element) {
				    	return !$('#restDay').is(':checked');
				    }
			    }
			},
			inHandCash : {
				required : {
					depends: function(element) {
				    	return !$('#restDay').is(':checked');
				    }
			    }
			}
		},
		errorPlacement : function(error, element) {
			
			if($(element).prop("name") == "bus" || $(element).prop("name") == "path1" || $(element).prop("name") == "path2" || $(element).prop("name") == "path3"){
				error.insertAfter($("#" + $(element).prop("name")).closest("div").find(".select2-container"));
			}else if ($(element).prop("name") === "dateRange") {
				error.insertAfter($(".date-group"));
			}else{
				error.insertAfter($("#" + $(element).prop("name")));
			}
				
			
		}
	});
	
}

