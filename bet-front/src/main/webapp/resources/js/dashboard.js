var itemList = [];
var searchList = [];
var cartList = [];
var packagingList = [];
var subTotal = 0;

$(init);

function init() {
	
	var dashboardRowHeight = parseInt($(".content-wrapper").css("height")) - 46;
	
	$(".row-dashboard").css("min-height", dashboardRowHeight);
	$(".row-items .card").css("min-height", dashboardRowHeight - 158);
	
	bindItemDropDown();
	bindSubmit();
	$( "#discount, #discountType" ).on( "change", function() {
	  	 updateDiscount();
	} );
	
	$( "#clearData" ).on( "click", function() {
		resetData();
	});
	
	$( "#paidAmount" ).on( "input", function() {
		
	  	var discountAmount = 0;
		if($("#discountType").val() == "PERCENTAGE"){
			discountAmount = (subTotal / 100) * $("#discount").val();
		}else{
			discountAmount = $("#discount").val();
		}
		
		$("#changeAmount").text($( "#paidAmount" ).val() - (subTotal - discountAmount));
	} );
}

function updateDiscount(){
	var discountAmount = 0;
	if($("#discountType").val() == "PERCENTAGE"){
		discountAmount = (subTotal / 100) * $("#discount").val();
	}else{
		discountAmount = $("#discount").val();
	}
		
	$("#total").text(subTotal - discountAmount);
}

function resetData(){
	cartList = [];
	drawCart();
						
	$(".item-detail").addClass("d-none");
	$("#stockDetail").empty();
	$( "#discount" ).val(0)
	$( "#searchItem" ).val("");
	$( "#discount" ).val("PERCENTAGE");
	subTotal = 0;
	$("#subTotal").text(0);
	$("#total").text(0);
}

function bindItemDropDown() {
	
	$("#searchItem").empty();
	
	$.ajax({
		url : getPathName() + "/item/api/dropdown",
		dataType: "json",
		success : function(data) {
			itemList = data;
			$.each(data, function(key, item) {	
				searchList.push(item.code + " - " + item.name);	
			});
		},
		complete : function(){
			
			$( "#searchItem" ).autocomplete({
		      source: searchList,
		      select: function( event, ui ) {
				$.each(itemList, function(key, item) {
				
					if((item.code + " - " + item.name) == ui.item.label){
						$( "#itemName" ).text(item.code + " - " + item.name);
						$( "#hdItemName" ).val(item.code + " - " + item.name);
						$( "#category" ).text(item.category);
						$( "#manufacturer" ).text(item.manufacturer);
						$( "#country" ).text(item.country);
						$( "#searchId" ).val(item.id);
						getStockDetails(item.id);
						
						$( "#searchItem" ).val("");
					}
				});
				
				return false;
			}
		    });
		}
	});
	
    
}


function getStockDetails(itemId) {

	$.ajax({
		url : getPathName() + "/item/api/" + itemId + "/packagingListWithStock",
		dataType: "json",
		success : function(data) {
			
			$.merge( packagingList, data );
			
			$(".item-detail").removeClass("d-none");		
			
				if(data.length > 0){
				$("#stockDetail").empty();
				var htmlBody = "";
				var packagingType = null;
				$.each(data, function(key, item) {
					
					var alreadyExists = false;
					$.each(packagingList, function(pkgKey, pkgItem) {
						if(pkgItem.stockId == item.stockId){
							alreadyExists = true;
						}
						
					});
					
					if(!alreadyExists){
						packagingList.push(item);
					}
					
					
					if(packagingType == null || packagingType != item.packaging){
						var stockData = getStockByPackagingId(item.packagingId);
					
						htmlBody += "<tr><td>" + getUOMText(item.packaging) + "</td>";
						htmlBody += "<td>" + stockData.resellPrice + "</td>";
						htmlBody += "<td>" + stockData.total + "</td>";
						if(stockData.total > 0){
						htmlBody += '<td><input class="form-control" type="number" value="1" min="1" max="' + stockData.total + '" required/></td>';
							
						}else{
							htmlBody += '<td></td>';
						
						}
						htmlBody += '<td><button class="btn btn-outline-primary float-right add-cart" data-id="' + item.packagingId +'"> ' + "<i class='fa fa-plus'></i>" + ' </button></td></tr>';

						packagingType = item.packaging;
					}
						
					
							
				});	
				
				$("#stockDetail").append(htmlBody);
			}	
			
				
		},
		complete(){
			$('.add-cart').on('click', function(e) {
					cartList.push({
						itemName : $( "#hdItemName" ).val(),
						itemId : $("#searchId").val(),
						packagingId : $(this).attr("data-id"),
						quantity : $(this).parent().prev().find("input").val()
					});
					
					drawCart();
			});
			
			
		}
	});
}

function drawCart(){
	
	var cartHtml = "";
	$("#cartBody").empty();
	
	subTotal = 0;
	
	
	$.each(cartList, function(key, item) {
		var itemPackagingList = getPackagingById(parseInt(item.packagingId));
		cartHtml += "<tr><td>" + (key + 1) + "</td>";
		cartHtml += "<td>" + item.itemName + "</td>";
		cartHtml += "<td>" + getUOMText(itemPackagingList[0].packaging) + "</td>";
		cartHtml += "<td>" + "<select class='form-control' id='expiry" + item.packagingId + "'>";
		$.each(itemPackagingList, function(key, item) {
			cartHtml += "<option value='" + item.stockId + "'>" + item.expiryDate + "</option>";
		});
		cartHtml += "</select></td>";
		cartHtml += "<td>" + item.quantity + "</td>";
		cartHtml += "<td>" + itemPackagingList[0].price + "</td>";
		cartHtml += "<td>" + itemPackagingList[0].price * item.quantity + "</td>";
		cartHtml += '<td> <span class="cart-remove" data-id="' + key + '"><svg width="25" height="24" viewBox="0 0 25 24" fill="none" xmlns="http://www.w3.org/2000/svg"> <path opacity="0.8" fill-rule="evenodd" clip-rule="evenodd" d="M12.5 13.7299L15.2515 11.2286L16.9485 12.7714L14.1971 15.2727L16.9485 17.7741L15.2515 19.3168L12.5 16.8155L9.74853 19.3168L8.05147 17.7741L10.8029 15.2727L8.05147 12.7714L9.74853 11.2286L12.5 13.7299ZM6.5 3.27273V2.18182C6.5 0.976833 7.57452 0 8.9 0H16.1C17.4255 0 18.5 0.976833 18.5 2.18182V3.27273H22.1C23.4255 3.27273 24.5 4.24956 24.5 5.45455V7.63636C24.5 8.84135 23.4255 9.81818 22.1 9.81818H22.0038L20.9 21.8182C20.9 23.0232 19.8255 24 18.5 24H6.5C5.17452 24 4.1 23.0232 4.10415 21.9088L2.99584 9.81818H2.9C1.57452 9.81818 0.5 8.84135 0.5 7.63636V5.45455C0.5 4.24956 1.57452 3.27273 2.9 3.27273H6.5ZM6.5 5.45455H2.9V7.63636H22.1V5.45455H18.5H6.5ZM5.40379 9.81818L6.5 21.8182H18.5L18.5041 21.7276L19.5958 9.81818H5.40379ZM16.1 3.27273V2.18182H8.9V3.27273H16.1Z" fill="#F21B3F" /> </svg><span>'; 
		cartHtml += "</td></tr>";
		
		subTotal += itemPackagingList[0].price * item.quantity;
	});
	
	updateDiscount();
			
	$("#cartBody").append(cartHtml);
	$("#subTotal").text(subTotal);
	
	$(".cart-remove").click(function() {
		cartList.splice($(this).attr("data-id"), 1);
		drawCart();
	});
}

function bindSubmit(){
	
	$("#submit").click(function() {
		
		if(cartList.length > 0){
			
			showLoadingOverlay();
			
			var salesItemList = [];
			var subTotal = 0;
			
			$.each(cartList,function(index, value) {
				
				var stockId = $("#expiry"+value.packagingId).val();
				stockBean = getPackagingByStockId(stockId);
				
				salesItemList.push({
					itemId : parseInt(value.itemId),
					stockId : parseInt(stockId),
					price : stockBean.price,
					quantity : parseInt(value.quantity),
					priceHistoryId : parseInt(stockBean.priceHistoryId)
				});
				
				subTotal += value.quantity * stockBean.price;
			});
			
			var discountAmount = 0;
			if($("#discountType").val() == "PERCENTAGE"){
				discountAmount = (subTotal / 100) * $("#discount").val();
			}else{
				discountAmount = $("#discount").val();
			}
			
			
			var salesBean = {
				subTotal : subTotal,
				total : subTotal - discountAmount,
				discount : parseInt($('#discount').val()),
				discountType : $('#discountType').val(),
				adjustment :0,
				salesType : "SALES",
				itemsList : salesItemList
			}
			
			
			 $(window).unbind('beforeunload');
			
			$.ajax({
				url : getPathName() + "/daily-sales/api/add",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify(salesBean),
				dataType: 'json',
				success : function(data) {
					if(data.httpStatus == "OK"){
						
						resetData();
						toastr.success('Order added successfully.');	
					}
				},
				complete : function(){
					hideLoadingOverlay();
					
				}
			});
		}
		
	});
}

function getPackagingById(packagingId){
	
	var returnVal = [];
	$.each(packagingList, function(key, value) {
		if(value.packagingId == packagingId){
			
			returnVal.push(value);
		}
	});	
	
	return returnVal;
}

function getPackagingByStockId(stockId){
	
	var returnVal = {};
	$.each(packagingList, function(key, value) {
		if(value.stockId == stockId){
			returnVal = value;
		}
	});	
	
	return returnVal;
}