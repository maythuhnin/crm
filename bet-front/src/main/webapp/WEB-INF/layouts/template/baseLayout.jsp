<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html dir="ltr" lang="en">

	<head>
	
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  		<meta name="viewport" content="width=device-width, initial-scale=1">
		
		 <!-- Google Font: Source Sans Pro -->
		 <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
		 <!-- Font Awesome -->
		 <link rel="stylesheet" href="<c:url value="/resources/plugins/fontawesome-free/css/all.min.css"/>">
		  <!-- SweetAlert2 -->
  		 <link rel="stylesheet" href="<c:url value="/resources/plugins/toastr/toastr.min.css"/>">
		 <!-- Theme style -->
		 <link rel="stylesheet" href="<c:url value="/resources/adminlte/adminlte.css"/>">
		 <!-- Theme style -->
		 <link rel="stylesheet" href="<c:url value="/resources/adminlte/custom.css"/>">
		<!-- Loading Overlay -->
   		 <link rel="stylesheet" href="<c:url value="/resources/plugins/minimal-custom-overlay/loading-overlay.jquery.css"/>">
		  <!-- jQuery -->
		<script src="<c:url value="/resources/plugins/jquery/jquery.min.js"/>"></script>
		<!-- Bootstrap 4 -->
		<script src="<c:url value="/resources/plugins/bootstrap/js/bootstrap.bundle.min.js"/>"></script>
		 <!-- SweetAlert2 -->
		<script src="<c:url value="/resources/plugins/toastr/toastr.min.js"/>"></script>   
		 <!-- Loading Overlay -->
		<script src="<c:url value="/resources/plugins/minimal-custom-overlay/loading-overlay.jquery.js"/>"></script>   
		<script src="<c:url value="/resources/plugins/jquery-validation/dist/jquery.validate.min.js"/>"></script>
		<script src="<c:url value="/resources/js/validator.js"/>"></script>
		<!-- AdminLTE App -->
		<script src="<c:url value="/resources/adminlte/adminlte.js"/>"></script>
		<script src="<c:url value="/resources/js/common.js"/>"></script>
		<script src="<c:url value="/resources/js/base.js"/>"></script>
		 <link rel="icon" type="image/png" href="<c:url value="/resources/images/favicon.png"/>"/>
		<title> Expense Tracker | <tiles:insertAttribute name="title" /></title>
	</head>

	<body class="hold-transition sidebar-mini layout-fixed"> 
	
		<input type="hidden" id="success" value="${success}"/>
		<input type="hidden" id="info" value="${info}"/>
		<input type="hidden" id="warning" value="${warning}"/>
		<input type="hidden" id="error" value="${error}"/>
	
		<div class="wrapper">
			<tiles:insertAttribute name="header" />
			
			<tiles:insertAttribute name="sideBar" />
				
			<tiles:insertAttribute name="content" />
			
			<tiles:insertAttribute name="footer" />
		</div>
	</body>
</html>