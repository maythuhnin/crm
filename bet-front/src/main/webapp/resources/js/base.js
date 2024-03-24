
$(init);

var toastr;

function init() {
	
	$(document).ajaxStart(function () {
     	showLoadingOverlay();
	});
	
	$( document ).on( "ajaxStop", function() {
  		hideLoadingOverlay();
	} );
	
	//getNotificationData();
	bindToastr();
	bindProfile();
}

function bindProfile(){
	$("#profile").click(function() {
		
		$("#profileModal").modal();	
		profileValidate();
		
		$('#changePassword').unbind('click');
		$("#changePassword") .on( "click",function() {
			
			if($("#profileForm").valid()){
				
				if($("#headerNewPassword").val() == $("#headerConfirmPassword").val()){
					var userBean = {
						password : $("#headerOldPassword").val(),
						newPassword : $("#headerNewPassword").val(),
					}
				}
				
				showLoadingOverlay();
					
				$.ajax({
					url : getPathName() + "/user/api/password",
					type : "POST",
					contentType: "application/json",
					data :JSON.stringify(userBean),
					dataType: 'json',
					success : function(data) {
						if(data.httpStatus == "OK"){
							$("#profileModal").modal("hide");
							toastr.success('Password successfully changed.');
						}else if(data.httpStatus == "INTERNAL_SERVER_ERROR"){
							toastr.error(data.error);
						}
						
					},
					complete : function(){
							hideLoadingOverlay();
						}
					});	
				}
		});
	});
}

function profileValidate(){
	$("#profileForm").validate({
		rules : {
			
			headerNewPassword : {
				required : true
			},
			headerConfirmPassword : {
				required : true
			}
		}
	});
}
	
function bindToastr(){

	toastr.options = {
	  "closeButton": false,
	  "debug": false,
	  "newestOnTop": false,
	  "progressBar": true,
	  "positionClass": "toast-bottom-right",
	  "preventDuplicates": false,
	  "onclick": null,
	  "showDuration": "300",
	  "hideDuration": "1000",
	  "timeOut": "3000",
	  "extendedTimeOut": "1000",
	  "showEasing": "swing",
	  "hideEasing": "linear",
	  "showMethod": "fadeIn",
	  "hideMethod": "fadeOut"
	}
	
	if($("#success").val() != ""){
		toastr.success($("#success").val());
	}
	
	if($("#info").val() != ""){
		toastr.info($("#info").val());
	}
	
	if($("#warning").val() != ""){
		toastr.warning($("#warning").val());
	}
	
	if($("#error").val() != ""){
		toastr.error($("#error").val());
	}
	
}

function getUrlParameter(sParam) {
    var sPageURL = window.location.search.substring(1),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
        }
    }
    return false;
};

function getNotificationData(){
	$.ajax({
		url : getPathName() + "/dashboard/api/notification",
		dataType: "json",
		success : function(data) {
			$("#restockNotification").text(data.restockCount);
			$("#stockRequestNotification").text(data.stockRequest);
			$("#expiringNotification").text(data.expiringCount);
			$("#expiringCount").val(data.expiringCount);
			$("#creditNotification").text(data.creditCount);
			$("#totalNotification").text(data.restockCount + + data.stockRequest + data.expiringCount + data.creditCount);
		},
		complete : function(){
		}
	});
    
}