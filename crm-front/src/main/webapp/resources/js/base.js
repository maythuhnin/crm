
$(init);

var toastr;

function init() {
	
	$(document).ajaxStart(function () {
     	showLoadingOverlay();
	});
	
	$( document ).on( "ajaxStop", function() {
  		hideLoadingOverlay();
	} );
	
	bindToastr();
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