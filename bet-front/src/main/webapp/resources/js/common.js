

function getUrl(){
	return window.location.href;
}

function getPathName(){
	return window.location.origin + applicationContextPath();
}

function applicationContextPath() {
    if (location.pathname.split('/').length > 1)
        return "/" + location.pathname.split('/')[1];
    else
        return "/";
}

function getLocationOrigin(){
	return window.location.origin;
}

function getRoleText(role){
	switch(role) {
	  case "ROLE_ADMIN":
		  return "ADMIN";
	  case "ROLE_USER":
		  return "USER";
	  default:
	} 
}

function isEmpty(data){
	var isEmpty = false;
	
	if(undefined == data || null == data || "" == data){
		isEmpty = true;
	}
	
	return isEmpty;
}

function isNotEmptyOrZero(data){
	var isEmpty = false;
	
	if(undefined == data || null == data || "" == data || 0 == data){
		isEmpty = true;
	}
	
	return isEmpty;
}

function unique(array){
    return $.grep(array,function(el,index){
        return index == $.inArray(el,array);
    });
}

function exists(arr,prop,val){
    var threads = arr.filter(function(e){
        return e[prop] == val;
    });
    return !!threads.length;
}

function getBeanFromListById(list, id){
	var returnBean = {};
	$.each(list, function(key, value) {
		if(value.id == id){
			returnBean = value;
		}
	});
	
	return returnBean;
}

function getIndexFromListById(list, id){
	var returnIndex = {};
	$.each(list, function(key, value) {
		if(value.id == id){
			returnIndex = key;
		}
	});
	
	return returnIndex;
}

function showLoadingOverlay(){
	$("body").loadingOverlay(true, {
 		backgroundColor: 'rgba(0,0,0,0.65)',
	});
}

function hideLoadingOverlay(){
	$("body").loadingOverlay(false, {
	});
}


function testRegex(value, pattern){
	
	var regex = new RegExp(pattern);

	if (regex.test(value)) {
        return true;
    }
	
}
