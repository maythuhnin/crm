
let baseUOMList = [
	'BOX', 'STRIP', 'EACH', 'BOTTLE', 'SATCHET', 'TUBE', 'PACK'
]

let countryList = [
	'Myanmar',
	'Germany',
	'Switzerland',
	'United States',
	'Ireland',
	'Belgium',
	'France',
	'Italy',
	'Netherlands',
	'United Kingdom',
	'Denmark',
	'China',
	'Spain',
	'Austria',
	'Sweden',
	'Singapore',
	'Canada',
	'Japan',
	'Hungary',
	'Slovenia',
	'Australia',
	'South Korea',
	'Poland',
	'Czech Republic',
	'Israel',
	'Greece',
	'Mexico',
	'Turkey',
	'Portugal',
	'Romania',
	'Lithuania',
	'Finland',
	'Russia',
	'Norway',
	'Argentina',
	'Taiwan',
	'Latvia',
	'Slovakia',
	'South Africa',
	'Colombia',
	'New Zealand',
	'Chile',
	'Luxembourg',
	'Estonia',
	'Iceland'
]

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
	  case "ROLE_SALES":
		  return "SALES";
	  case "ROLE_WAREHOUSE":
		  return "WAREHOUSE";
	  case "ROLE_SELLER":
		  return "SELLER";
	  default:
	} 
}

function getUOMText(uom){
	switch(uom) {
	  case 0:
		  return "BOX";
	  case 1:
		  return "STRIP";
	  case 2:
		  return "EACH";
	  case 3:
		  return "BOTTLE";
	  case 4:
		  return "SATCHET";
	  case 5:
		  return "TUBE";
	  case 6:
		  return "PACK";
	  default:
	} 
}

function getMonthText(month){
	switch(month) {
	  case 1:
		  return "JANUARY";
	  case 2:
		  return "FEBRUARY";
	  case 3:
		  return "MARCH";
	  case 4:
		  return "APRIL";
	  case 5:
		  return "MAY";
	  case 6:
		  return "JUNE";
	  case 7:
		  return "JULY";
	  case 8:
		  return "AUGUST";
	  case 9:
		  return "SEPTEMBER";
	  case 10:
		  return "OCTOBER";
	  case 11:
		  return "NOVEMBER";
	  case 12:
		  return "DECEMBER";
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
