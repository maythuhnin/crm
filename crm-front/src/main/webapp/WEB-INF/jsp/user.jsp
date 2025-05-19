<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<!-- Select2 -->
   		<link rel="stylesheet" href="<c:url value="/resources/plugins/select2/css/select2.min.css"/>">
   		<link rel="stylesheet" href="<c:url value="/resources/plugins/select2-bootstrap4-theme/select2-bootstrap4.min.css"/>">
 
    	<!-- DataTables -->
	    <link rel="stylesheet" href="<c:url value="/resources/plugins/datatables-bs4/css/dataTables.bootstrap4.min.css"/>">
	    <link rel="stylesheet" href="<c:url value="/resources/plugins/datatables-responsive/css/responsive.bootstrap4.min.css"/>">
	    <link rel="stylesheet" href="<c:url value="/resources/css/user.css"/>"> 
	</head>
	<!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
	<input type="hidden" id="loggedInUser" value="${loggedInUserId}"/>
    <!-- Main content -->
    <section class="content pt-2 pb-1">

      <!-- Default box -->
      <div class="card">
        <div class="card-header">
          <div class="row">
          	<div class="col-sm-2">
			        <select class="form-control select2 select-filter" style="width: 100%;" id="searchRole">
			        	<option></option>
			            <option value="ADMIN">ADMIN</option>
				        <option value="USER">USER</option>
			        </select>
			    </div>
			    <div class="col-sm-3">
					<div class="input-group">
		            	<input id="searchBox" class="form-control form-control-navbar" type="search" placeholder="Search" aria-label="Search">
		              	<div class="input-group-append">
		                	<button class="btn btn-navbar" type="button">
		                  		<i class="fas fa-search"></i>
		                	</button>
		              	</div>
		            </div>
			     </div>
			     <div class="col-sm-2">
	             	 <button type="button" class="btn btn-default" id="clearFilters">
	             	 	Clear  <i class="fas fa-times"></i>
	                  </button>
	            </div>
			     <div class="col-sm-3"></div>
          	<div class="col-sm-2">
	             	<button type="button" class="btn btn-outline-primary float-right" id="addUser">
	                   Add <i class="fas fa-plus-circle"></i>
	                  </button>
	            </div>
			    
			    
			</div>
        </div>
        <div class="card-body">
          <table id="userDatatable" class="table table-bordered">
            <thead>
            <tr>
              <th>Name</th>
              <th>Username</th>
              <th>Role</th>
              <th>Last Logged In</th>
              <th>Status</th>
              <th></th>
            </tr>
            </thead>
            <tbody>
            
            </tbody>
          </table>
        </div>
        <!-- /.card-body -->
      </div>
      <!-- /.card -->
    </section>
    
            <!-- /.card -->
	<div class="modal fade" id="userModal">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title user-title">Add User</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
              <!-- form start -->
        <form class="form-horizontal" id="userForm">
          <div class="card-body">
            <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="name" class="col-sm-4 col-form-label">Name <span class="required">[required]</span></label>
              <div class="col-sm-6">
                <input type="text" name="name" class="form-control" id="name">
              </div>
            </div>
            
            <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="username" class="col-sm-4 col-form-label">Username <span class="required">[required]</span></label>
              <div class="col-sm-6">
                <input type="text" name="username" class="form-control" id="username">
                </div>
            </div>
            
             <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="inputEmail3" class="col-sm-4 col-form-label">Role <span class="required">[required]</span></label>
              <div class="col-sm-6">
                   <select class="form-control" style="width: 100%;" id="role" name="role">
			            <option value="ROLE_ADMIN">ADMIN</option>
				        <option value="ROLE_USER">USER</option>
			   		</select>
              </div>
            </div>
            
            <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="password" class="col-sm-4 col-form-label">Password <span class="required">[required]</span></label>
              <div class="col-sm-6">
                <input type="password" name="password" class="form-control" id="password">
              </div>
            </div>
            
            <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="confirmPassword" class="col-sm-4 col-form-label">Confirm Password <span class="required">[required]</span></label>
              <div class="col-sm-6">
                <input type="password"  name="confirmPassword" class="form-control" id="confirmPassword">
              </div>
            </div>
         
           
          </div> 
        
          <!-- /.card-footer -->
        </form>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default cancel" data-dismiss="modal">Cancel</button>
              <button type="button" class="btn btn-outline-primary" id="saveUser">Save Changes</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      
            <!-- /.card -->
	<div class="modal fade" id="updateUserModal">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title user-title">Edit User</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
              <!-- form start -->
        <form class="form-horizontal" id="updateUserForm">
          <div class="card-body">
            <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="name" class="col-sm-3 col-form-label">Name <span class="required">[required]</span></label>
              <div class="col-sm-6">
                <input type="text" name="updateName" class="form-control" id="updateName">
              </div>
            </div>
            
            <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="username" class="col-sm-3 col-form-label">Username <span class="required">[required]</span></label>
              <div class="col-sm-6">
                <input type="text" name="updateUsername" class="form-control" id="updateUsername">
                </div>
            </div>
            
             <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="updateRole" class="col-sm-3 col-form-label">Role <span class="required">[required]</span></label>
              <div class="col-sm-6">
                   <select class="form-control select2 select-filter" style="width: 100%;" id="updateRole">
			            <option value="ROLE_ADMIN">ADMIN</option>
				        <option value="ROLE_USER">USER</option>
			   		</select>
              </div>
            </div>
            
             <div class="form-group row update-status">
              <div class="col-sm-1"></div>
              <label for="updateRole" class="col-sm-3 col-form-label">Status <span class="required">[required]</span></label>
              <div class="col-sm-6">
                   <select class="form-control select2 select-filter" style="width: 100%;" id="updateStatus">
			            <option value="1">ACTIVE</option>
				        <option value="0">IN-ACTIVE</option>
			   		</select>
              </div>
            </div>
            
            
            <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="password" class="col-sm-3 col-form-label">Password</label>
              <div class="col-sm-6">
                <input type="password" name="updatePassword" class="form-control" id="updatePassword">
              </div>
            </div>
            
            <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="confirmPassword" class="col-sm-3 col-form-label">Confirm Password</label>
              <div class="col-sm-6">
                <input type="password"  name="updateConfirmPassword" class="form-control" id="updateConfirmPassword">
              </div>
            </div>
         
           
          </div> 
        
          <!-- /.card-footer -->
        </form>
            </div>
            <div class="modal-footer">
            	<input type="hidden" id="userId"/>
              <button type="button" class="btn btn-default cancel" data-dismiss="modal">Cancel</button>
              <button type="button" class="btn btn-outline-primary" id="updateUser">Save Changes</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      
        <!-- /.card -->
	<div class="modal fade" id="deleteModal">
        <div class="modal-dialog modal-md">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">Delete User </h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
                  <!-- form start -->
        
            <div class="modal-body">
          		<div class="card-body del-card">
            		<div class="form-group row">
              			<label class="col-sm-12 col-form-label">
              				Are you sure you want to delete user : <span id="delName"></span>?
              				<br>
             				<span class="required">This process cannot be undone.</span>
              			</label>
            		
            		</div>                       
      	  		</div>
        	</div>
        	 <input type="hidden" id="delUserId"/>
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">NO</button>
              <button type="button" class="btn btn-outline-danger" id="deleteUser">YES</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  <!-- DataTables  & Plugins -->
<script src="<c:url value="/resources/plugins/datatables/jquery.dataTables.min.js"/>"></script>
<script src="<c:url value="/resources/plugins/datatables-bs4/js/dataTables.bootstrap4.min.js"/>"></script>
<script src="<c:url value="/resources/plugins/datatables-responsive/js/dataTables.responsive.min.js"/>"></script>
<script src="<c:url value="/resources/plugins/datatables-responsive/js/responsive.bootstrap4.min.js"/>"></script>

<!-- Select2 -->
<script src="<c:url value="/resources/plugins/select2/js/select2.full.min.js"/>"></script>
<script src="<c:url value="/resources/plugins/jquery-validation/dist/jquery.validate.min.js"/>"></script>
<script src="<c:url value="/resources/js/validator.js"/>"></script>
<script src="<c:url value="/resources/js/user.js"/>"></script>

</html>