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
	    <link rel="stylesheet" href="<c:url value="/resources/css/expense-report.css"/>"> 
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
					<select class="form-control select2 select-filter" style="width: 100%;" id="searchBus">
			        	
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
          <table id="expenseReportDatatable" class="table table-bordered" >
            <thead>
            <tr >
              <th width="50px" class="dt-control"></th> 
              <th>Date</th>
              <th>Path</th>
              <th>On Paper Income(Leave)</th>
              <th>On Paper Income(Return)</th>
              <th>In Hand Cash</th>
              <th>Adjustment</th>
              <th>Extra Income From Hitchhikers</th>
              <th>Expense Total</th>
              <th>Total</th>
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
		<!-- Select2 -->
<script src="<c:url value="/resources/plugins/select2/js/select2.full.min.js"/>"></script>
	
	<script src="<c:url value="/resources/js/expense-report.js"/>"></script>

</html>