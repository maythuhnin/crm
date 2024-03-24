
$(init);

function init() {

	if(!isEmpty(getUrlParameter("errorType"))){
		bindToastr(getUrlParameter("errorType"));
	}
	

}

	
function bindToastr(errorType){

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

	if(errorType == "subStart"){
		toastr.error("Subscription has not yet started. Please contact customer service at 0900000.");
	}else if(errorType == "noSub"){
		toastr.error("There is no subscription for this shop. Please contact customer service at 0900000.");
	}else if(errorType == "subEnd"){
		toastr.error("Subscription has ended. Please contact customer service to renew at 0900000.");
	}else{
		toastr.error("Incorrect username or password.");
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

function isEmpty(data){
	var isEmpty = false;
	
	if(undefined == data || null == data || "" == data){
		isEmpty = true;
	}
	
	return isEmpty;
}