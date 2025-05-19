let userDatatable = {}; 
let userList = [];
let userAddValidator;
let userUpdateValidator;

$(init);

function init() {
	
	bindValidator();
	bindModal();
	initDatatable();
	bindSearch();
}

function bindModal(){
	
	bindUserAddButtonClick();
	
	bindUserAddApi();
	bindUserUpdateApi();
	bindUserDeleteApi();
	
	bindModalCloseButtonClick();
	
}


function bindSearch(){
	
	$("#userDatatable_filter label").addClass("d-none");
	
	$('#searchRole').select2({
		theme: 'bootstrap4',
    	placeholder: "Search Role.",
    	allowClear: false
	});
	
	$("#searchRole").on('change', function() {
		userDatatable.column(2).search( $('#searchRole').val()).draw();
	});
	
	$("#searchBox").on('input', function() {
		userDatatable.search($("#searchBox").val()).draw();
	});
	
	$("#clearFilters").on('click', function() {	
		$("#searchBox").val("");
		$("#searchRole").val("");
		$("#searchRole").trigger("change");
		userDatatable.search("").draw();
	});

}

function bindModalCloseButtonClick(){
	$(".cancel, .close").click(function () {
		resetUserForm();
	});
}

function bindUserAddButtonClick(){
	$("#addUser").click(function () {
		resetUserForm();
		$("#userModal").modal("show");
	});
}

function bindUserAddApi(){
	
	$("#saveUser").click(function () {
		if($("#userForm").valid()){
			
			showLoadingOverlay();
			
			userBean = {
				name : $("#name").val(),
				username : $( "#username" ).val(),
				role : $("#role").val()
			};
			
			if($("#password").val() == $("#confirmPassword").val()){
				userBean.password = $("#password").val();	
			}
			
			$.ajax({
				url : getPathName() + "/user/api/add",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify(userBean),
				dataType: 'json',
				success : function(data) {
					if(data.httpStatus == "OK"){
						userDatatable.ajax.reload();
						$("#userModal").modal("hide");
		
						resetUserForm();
						
						$("#userModal").modal("hide");
						
						toastr.success('User added successfully.');
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


function bindUserUpdateApi(){
	$( "#updateUser" ).on( "click", function()  {
		
		if($("#updateUserForm").valid()){
			
			showLoadingOverlay();
			
			userBean = {
				id : parseInt($("#userId").val()),
				name : $("#updateName").val(),
				username : $( "#updateUsername" ).val(),
				role : $("#updateRole").val(),
				status : $("#updateStatus").val() == 0 ? false : true
			};
			
			if(!isEmpty($("#updatePassword").val()) && ($("#updatePassword").val()== $("#updateConfirmPassword").val())){
				userBean.password = $("#updatePassword").val();	
			}
			
			$.ajax({
				url : getPathName() + "/user/api/edit",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify(userBean),
				dataType: 'json',
				success : function(data) {
					if(data.httpStatus == "OK"){
						userDatatable.ajax.reload();
						$("#updateUserModal").modal("hide");
		
						resetUserForm();
						
						toastr.success('User edited successfully.');
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

function resetUserForm(){
	$("#name, #username, #role, #password, #confirmPassword").val("");
	$("#updateName, #updateUsername, #updatePassword, #updateConfirmPassword").val("");
	$("#role, #updateRole").val("");
	 userAddValidator.resetForm();
	 userUpdateValidator.resetForm();
}

function initDatatable() {
	
	userDatatable = $('#userDatatable').DataTable({
		lengthChange: false, 
		autoWidth: false,
      	dom: 'Bfrtip',
		ajax: {
	        url:  getPathName() + '/user/api/datatable',
	        type: "GET",
	         data : function(d) {
				
			},
	        dataSrc: 'responseData',
	        dataType: "json"
	    },
	    "order": [1],
	    scrollX:        true,
        scrollCollapse: true,
	    columns: [
		{ mData : function(data, type, full, meta) {
				return data.name;
			},
		    sClass: "text-center"}, 
	     { mData : function(data, type, full, meta) {
				userList.push(data);
				return data.username;
			},
		    sClass: "text-center"}, 
	      
	      { mData : function(data, type, full, meta) {
	
				return getRoleText(data.role);
			},
		    sClass: "text-center"}, 
	    { mData : function(data, type, full, meta) {
	
				return isEmpty(data.lastLoggedIn) ? "-" : data.lastLoggedIn;
			},
		    sClass: "text-center"},
	    { mData : function(data, type, full, meta) {
	
				return data.status == 1 ? "<span class='text-success'>ACTIVE</span>" : "<span class='text-muted'>IN-ACTIVE<span>";
			},
		    sClass: "text-center" },
	    { mData : function(data, type, full, meta) {
				
				var returnButtons = "";
				
				if(data.id != $("#loggedInUser").val()){
					returnButtons += '<button type="button" class="btn btn-outline-danger btn-sm delete-user mr-1" data-id="' + data.id + '" title="Delete User">Delete <i class="fas fa-trash"></i></button>';
				}
				
				returnButtons += '<button type="button" class="btn btn-outline-primary btn-sm update-user mr-1" data-id="' + data.id + '" title="Edit User">Edit <i class="fas fa-edit"></i></button>'; 	
				
				return returnButtons;
			},
		    sClass: "text-center",
	    	bSortable: false }
	    ],
		"fnDrawCallback": function () {
			$( ".update-user" ).on( "click", function() {
				var userId = $(this).attr("data-id");
				var userBean = getBeanFromListById(userList, userId);
				$("#userId").val(userId);
				$("#updateName").val(userBean.name);
				$("#updateUsername").val(userBean.username);
				$("#updateRole").val(userBean.role);
				$("#updateStatus").val(userBean.status);
				if(userId == $("#loggedInUser").val()){
					$(".update-status").addClass("d-none");
				}else{
					$(".update-status").removeClass("d-none"); 
				}
				$("#updateUserModal").modal();
			});
			
			$( ".delete-user" ).on( "click", function() {
				var userId = $(this).attr("data-id");
				var userBean = getBeanFromListById(userList, userId);
				$("#delUserId").val(userId);
				$("#delName").text(userBean.name + " [" + userBean.username+ "] ");
				$("#deleteModal").modal();
			});
	   } 
	})
	
}

function bindUserDeleteApi(){
		
		$( "#deleteUser" ).on( "click", function()  {

			showLoadingOverlay();
			
			$.ajax({
				url : getPathName() + "/user/api/delete",
				type : "POST",
				contentType: "application/json",
				data :JSON.stringify($("#delUserId").val()),
				dataType: 'json',
				success : function(data) {
					if(data.httpStatus == "OK"){
						
						userDatatable.ajax.reload();
						
						$("#deleteModal").modal("hide");
						
						toastr.success('User deleted successfully.');
					
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

function bindValidator(){
	userAddValidator = $("#userForm").validate({
		rules : {
			name : {
				required : true,
				maxlength : 200
			},
			username : {
				required : true,
				maxlength : 100
			},
			role : {
				required : true
			},
			password : {
				required : true,
				maxlength : 200,
				minlength: 6,
				isAddPasswordSame : true,
				checkPasswordStrength: true
			},
			confirmPassword : {
				required : true,
				maxlength : 200,
				minlength: 6,
				isAddPasswordSame : true,
				checkPasswordStrength: true
			}
		},
		messages: {
			password : {
				isAddPasswordSame : "Password and Confirm Password should be the same.",
				checkPasswordStrength : "Password should contain at least one alphabet, one number and one special character."
			},
			confirmPassword : {
				isAddPasswordSame : "Password and Confirm Password should be the same.",
				checkPasswordStrength : "Password should contain at least one alphabet, one number and one special character."
			}
		}
	});
	
	userUpdateValidator = $("#updateUserForm").validate({
		rules : {
			updateName : {
				required : true,
				maxlength : 200
			},
			updateUsername : {
				required : true,
				maxlength : 100
			},
			updateRole : {
				required : true
			},
			updateStatus : {
				required : true
			},
			updatePassword : {
				maxlength : 200,
				minlength: 6,
				isUpdatePasswordSame : true,
				checkPasswordStrength: true
			},
			updateConfirmPassword : {
				maxlength : 200,
				minlength: 6,
				isUpdatePasswordSame : true,
				checkPasswordStrength: true
			}
		},
		messages: {
			updatePassword : {
				isUpdatePasswordSame : "Password and Confirm Password should be the same.",
				checkPasswordStrength : "Password should contain at least one alphabet, one number and one special character."
			},
			updateConfirmPassword : {
				isUpdatePasswordSame : "Password and Confirm Password should be the same.",
				checkPasswordStrength : "Password should contain at least one alphabet, one number and one special character."
			}
		}
	});
}

