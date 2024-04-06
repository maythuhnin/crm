<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
  <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>ExpenseTracker | Log in</title>
  <!-- Google Font: Source Sans Pro -->
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="<c:url value="/resources/plugins/fontawesome-free/css/all.min.css"/>">
  <!-- Theme style -->
  <link rel="stylesheet" href="<c:url value="/resources/adminlte/adminlte.css"/>">
  		  <!-- SweetAlert2 -->
  <link rel="stylesheet" href="<c:url value="/resources/plugins/toastr/toastr.min.css"/>">
  <link rel="stylesheet" href="<c:url value="/resources/template/custom.css"/>"> 
  <link rel="stylesheet" href="<c:url value="/resources/css/login.css"/>"> 
   <link rel="icon" type="image/png" href="<c:url value="/resources/images/favicon.png"/>"/>
		
</head>
<body class="hold-transition login-page">
		<div class="login-box">
            <div class="login-logo">
			   <img src="<c:url value="/resources/images/login.png"/>" alt="Logo" class="float-left" style="opacity: .8">
			   <p class="logo-text">ExpenseTracker</p>
			  </div>
  <!-- /.login-logo -->
  <div class="card">
    <div class="card-body login-card-body">
      <p class="login-box-msg">Sign in to start your session</p>

     <form:form method="POST">
        <div class="input-group mb-3">
          <input type="text" name="userId" class="form-control" placeholder="Username">
          <div class="input-group-append">
            <div class="input-group-text">
              <span class="fas fa-envelope"></span>
            </div>
          </div>
        </div>
        <div class="input-group mb-3">
          <input type="password" name="password" class="form-control" placeholder="Password">
          <div class="input-group-append">
            <div class="input-group-text">
              <span class="fas fa-lock"></span>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-8">
            
          </div>
          <!-- /.col -->
          <div class="col-4">
            <button type="submit" class="btn btn-primary btn-block">Sign In</button>
          </div>
          <!-- /.col -->
        </div>
      </form:form>

      
      <!-- /.social-auth-links -->

    
    </div>
    <!-- /.login-card-body -->
  </div>
</div>
<!-- /.login-box -->

<!-- jQuery -->
<script src="<c:url value="/resources/plugins/jquery/jquery.min.js"/>"></script>
<!-- Bootstrap 4 -->
<script src="<c:url value="/resources/plugins/bootstrap/js/bootstrap.bundle.min.js"/>"></script>
<!-- AdminLTE App -->
<script src="<c:url value="/resources/adminlte/adminlte.js"/>"></script>
 <!-- SweetAlert2 -->
		<script src="<c:url value="/resources/plugins/toastr/toastr.min.js"/>"></script>   
<script src="<c:url value="/resources/js/login.js"/>"></script>
</body>
</html>
