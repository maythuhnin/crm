<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


  <nav class="main-header navbar navbar-expand navbar-white navbar-light">
    <!-- Left navbar links -->
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
      </li>
      <li class="nav-item d-none d-sm-inline-block ml-2 mt-1">
        <h4>${title}</h4>
      </li>
    </ul>

    <!-- Right navbar links -->
    <ul class="navbar-nav ml-auto">
      <li class="nav-item">
        <a class="nav-link" data-widget="fullscreen" href="#" role="button" title="Full Screen">
          <i class="fas fa-expand-arrows-alt"></i>
        </a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="<c:url value="/logout"/>" role="button" title="Log Out">
          <i class="fas fa-sign-out-alt"></i>
        </a>
      </li>
    </ul>
  </nav>

<div class="modal fade" id="profileModal">
        <div class="modal-dialog modal-xs">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">Profile</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
                  <!-- form start -->
        <form class="form-horizontal" id="profileForm" >
            <div class="modal-body">
          		      <!-- form start -->
         		<div class="card-body">
        
        		<div class="form-group row">
			        <label for="username" class="col-sm-6 col-form-label">Username :</label>
			        <div class="col-sm-6">
			          <div class="form-group">
			            <label for="username" class="col-sm-12 col-form-label">${username}</label>
			          </div>
			        </div>
		      </div>
            <hr>
            <div class="form-group row">
            	<h5>Change Password</h5> 
            </div>
            <div class="form-group row mt-4">
              <label for="newPassword" class="col-sm-6 col-form-label">Old Password *</label>
              <div class="col-sm-6">
                <div class="form-group">
                  <input type="password" class="form-control" id="headerOldPassword" placeholder="Old Password" name="headerOldPassword"/>
                </div>
              </div>
            </div>
            <div class="form-group row">
              <label for="newPassword" class="col-sm-6 col-form-label">New Password *</label>
              <div class="col-sm-6">
                <div class="form-group">
                  <input type="password" class="form-control" id="headerNewPassword" placeholder="New Password" name="headerNewPassword"/>
                </div>
              </div>
            </div>
            
            <div class="form-group row">
              <label for="debitExpenseDescription" class="col-sm-6 col-form-label">Confirm Password *</label>
              <div class="col-sm-6">
                <div class="form-group">
                   <input type="password" class="form-control" id="headerConfirmPassword" placeholder="Confirm Password" name="headerConfirmPassword"/>
                </div>
              </div>
            </div>
      		</div>
        
          <!-- /.card-footer -->
        	
        	</div>
        	</form>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="submit" class="btn btn-primary" id="changePassword">Change Password</button>
            </div>
         
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>