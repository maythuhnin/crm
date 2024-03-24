let userDatatable = {}; 
let userList = [];
let userAddValidator;
let userUpdateValidator;

$(init);

function init() {
	
	bindValidator();
	bindModal();
	bindDropDown();
	initDatatable();
}

function bindModal(){
	
	bindUserAddButtonClick();
	
	bindUserAddApi();
	bindUserUpdateApi();
	bindUserDeleteApi();
	
	bindModalCloseButtonClick();
	
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
		//$("#userForm").valid()
		if(true){
			
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
				role : $("#updateRole").val()
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

function bindDropDown(){

	$('#searchRole').select2({
		theme: 'bootstrap4',
    	placeholder: "Search Role.",
    	allowClear: true
	});
	
	$(".select-filter").on('change', function() {
		userDatatable.column(2).search( $('#searchRole').val()).draw();
	});
	
	$(".select2-selection__clear").on('click', function() {	
		userDatatable.column(2).search("").draw();
	});
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
		{ render : function(data, type, full, meta) {
				return full.name;
			},
		    sClass: "text-center"}, 
	     { render : function(data, type, full, meta) {
				userList.push(full);
				return full.username;
			},
		    sClass: "text-center"}, 
	      
	      { render : function(data, type, full, meta) {
	
				return getRoleText(full.role);
			},
		    sClass: "text-center"}, 
	    { render : function(data, type, full, meta) {
	
				return isEmpty(full.lastLoggedIn) ? "-" : full.lastLoggedIn;
			},
		    sClass: "text-center"},
	    { render : function(data, type, full, meta) {
	
				return full.status == 1 ? "<span class='text-success'>ACTIVE</span>" : "<span class='text-muted'>IN-ACTIVE<span>";
			},
		    sClass: "text-center" },
	    { render : function(data, type, full, meta) {
	
				return '<div><i class="fas fa-edit update-user mt-1 mr-1" data-id="' + full.id + '" title="Edit"></i><i class="fas fa-trash delete-user mt-1" data-id="' + full.id + '" title="Delete"></i></div>';
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
				maxlength : 50
			},
			username : {
				required : true
			},
			role : {
				required : true
			},
			password : {
				required : true
			},
			confirmPassword : {
				required : true
			}
		}
	});
	
	userUpdateValidator = $("#updateUserForm").validate({
		rules : {
			updateName : {
				required : true,
				maxlength : 50
			},
			updateUsername : {
				required : true
			},
			updateRole : {
				required : true
			}
		}
	});
}

