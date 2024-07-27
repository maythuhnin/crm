
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


jQuery.validator.addMethod("isAddPasswordSame", function(value, element) {
	var flag = true;
	
	if (!isEmpty($("#password").val()) && !isEmpty($("#confirmPassword").val())) {
		if($("#password").val() != $("#confirmPassword").val()){
			flag = false;
			return;	
		}
		
	}
	return flag;
});


jQuery.validator.addMethod("isUpdatePasswordSame", function(value, element) {
	var flag = true;
	
	if (!isEmpty($("#updatePassword").val()) && !isEmpty($("#updateConfirmPassword").val())) {
		if($("#updatePassword").val() != $("#updateConfirmPassword").val()){
			flag = false;
			return;	
		}
		
	}
	return flag;
});

jQuery.validator.addMethod("checkPasswordStrength", function(value, element) {
	var flag = true;
	if (!isEmpty(value)) {
		
		if(!checkPasswordStrength(value)){
			flag = false;
			return;	
		}

		
	}
	return flag;
});

function checkPasswordStrength(value) {
	var flag = false
	var number = /([0-9])/;
	var alphabets = /([a-zA-Z])/;
	var special_characters = /([~,!,@,#,$,%,^,&,*,-,_,+,=,?,>,<])/;
	var password = value.trim();
	
	if (password.match(number) && password.match(alphabets) && password.match(special_characters)) {
		flag = true;
	}
		
	return flag;
}
