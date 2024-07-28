<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<!-- Select2 -->
   		<link rel="stylesheet" href="<c:url value="/resources/plugins/select2/css/select2.min.css"/>">
   		<link rel="stylesheet" href="<c:url value="/resources/plugins/select2-bootstrap4-theme/select2-bootstrap4.min.css"/>">
 		<!-- daterange picker -->
   		<link rel="stylesheet" href="<c:url value="/resources/plugins/daterangepicker/daterangepicker.css"/>">
	    <!-- Tempusdominus Bootstrap 4 -->
	  	<link rel="stylesheet" href="<c:url value="/resources/plugins/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css"/>">
	  
 		<!-- DataTables -->
	    <link rel="stylesheet" href="<c:url value="/resources/plugins/datatables-bs4/css/dataTables.bootstrap4.min.css"/>">
	    <link rel="stylesheet" href="<c:url value="/resources/plugins/datatables-responsive/css/responsive.bootstrap4.min.css"/>">
	    <link rel="stylesheet" href="<c:url value="/resources/css/income-report.css"/>"> 
	</head>
	<!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">

    <!-- Main content -->
    <section class="content pt-2 pb-1">
<!-- Default box -->
      <div class="card">
       	 <div class="card-header">
          <div class="row">	
          
          <div class="col-sm-2">
					<div class="input-group date" id="searchMonth" data-target-input="nearest">
	                <input type="text" class="form-control datetimepicker-input" data-target="#searchMonth"/>
	                <div class="input-group-append" data-target="#searchMonth" data-toggle="datetimepicker">
	                    <div class="input-group-text"><i class="fa fa-calendar"></i></div>
	                </div>
				       </div>
			     </div>
			     <div class="col-sm-2">
					<select class="form-control select2 select-filter" style="width: 100%;" id="searchBus">
			        	
			        </select>
			     </div>
			     <div class="col-sm-2">
					<select class="form-control select2 select-filter" style="width: 100%;" id="searchOrder">
			        	<option value=""></option>
			        	<option value="1">Is Order</option>
			        	<option value="0">Not Order</option>
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
	             	 
	            </div>
			    
			    
			</div>
        </div>
        <div class="card-body"> 
          <table id="incomeReportDatatable" class="table table-bordered" >
            <thead>
            <tr >
              <th>Bus</th>
              <th>Total Income</th>
              <th>Total Expense</th>
              <th>Total Balance</th>
              <th>Total Income From Hitchhikers</th>
              <th>Round Trips</th>
              <th>Rest Day</th>
              <th>Sum</th>
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
    
  
 
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  	<!-- DataTables  & Plugins -->
	<script src="<c:url value="/resources/plugins/datatables/jquery.dataTables.min.js"/>"></script>
	<script src="<c:url value="/resources/plugins/datatables-bs4/js/dataTables.bootstrap4.min.js"/>"></script>
	<script src="<c:url value="/resources/plugins/datatables-responsive/js/dataTables.responsive.min.js"/>"></script>
	<script src="<c:url value="/resources/plugins/datatables-responsive/js/responsive.bootstrap4.min.js"/>"></script>
	<!-- date-range-picker -->
	<script src="<c:url value="/resources/plugins/moment/moment.min.js"/>"></script>
	<script src="<c:url value="/resources/plugins/daterangepicker/daterangepicker.js"/>"></script>
	<!-- Tempusdominus Bootstrap 4 -->
	<script src="<c:url value="/resources/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"/>"></script>
	 <!-- Select2 -->
	<script src="<c:url value="/resources/plugins/select2/js/select2.full.min.js"/>"></script>
	
	<script src="<c:url value="/resources/js/income-report.js"/>"></script>

</html>