
var PHONE_PATTERN = /^(09|01)+(\d{7,9})$/;

jQuery.validator.addMethod("isPhoneLengthValid", function(value, element) {
	var flag = true;
	if (null != $("#distributorPhone").val() && $("#distributorPhone").val() != "") {
		if ($("#distributorPhone").val().length > 50) {		
			flag = false;
			return;
		}
	}
	return flag;
});

jQuery.validator.addMethod("isPhonePatternValid", function(value, element) {
	var flag = true;
	
	if (null != $("#distributorPhone").val() && $("#distributorPhone").val() != "") {
		var phoneNumber = $("#distributorPhone").val().split(",");
		$.each(phoneNumber,function(index, value) {
			if (!testRegex(value, PHONE_PATTERN)) {			
				flag = false;
				return;
			}
		});
	}
	return flag;
});

jQuery.validator.addMethod("isContactPhoneLengthValid", function(value, element) {
	var flag = true;
	if (null != $("#contactPersonPhone").val() && $("#contactPersonPhone").val() != "") {
		if ($("#contactPersonPhone").val().length > 50) {		
			flag = false;
			return;
		}
	}
	return flag;
});

jQuery.validator.addMethod("isContactPhonePatternValid", function(value, element) {
	var flag = true;
	
	if (null != $("#contactPersonPhone").val() && $("#contactPersonPhone").val() != "") {
		var phoneNumber = $("#contactPersonPhone").val().split(",");
		$.each(phoneNumber,function(index, value) {
			if (!testRegex(value, PHONE_PATTERN)) {			
				flag = false;
				return;
			}
		});
	}
	return flag;
});


