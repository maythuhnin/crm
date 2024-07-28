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
	 <!-- daterange picker -->
	     
 		 <link rel="stylesheet" href="<c:url value="/resources/plugins/daterangepicker/daterangepicker.css"/>">
	    <!-- Tempusdominus Bootstrap 4 -->
	  	<link rel="stylesheet" href="<c:url value="/resources/plugins/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css"/>">
	   
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
			     <div class="col-sm-2">
					<select class="form-control select2 select-filter" style="width: 100%;" id="searchOrder">
			        	<option value=""></option>
			        	<option value="1">Is Order</option>
			        	<option value="0">Not Order</option>
			        </select>
			     </div>
			      <div class="col-sm-3">
	               <div class="input-group"> 
                    <div class="input-group-prepend">
                      <span class="input-group-text">
                        <i class="far fa-calendar-alt"></i>
                      </span>
                    </div>
                    <input type="text" class="form-control float-right" id="dateRange" placeholder="Filter Date">
                  </div>
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
	<div class="modal fade" id="deleteModal">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">Delete Daily Income/Expense</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
                  <!-- form start -->
        
            <div class="modal-body">
          		<div class="card-body del-card">
            		<div class="form-group row">
              			<label class="col-sm-12 col-form-label">
              				Are you sure you want to delete Daily Income/Expense: <span id="delName"></span>?
              				<br>
              				<span class="fixed-expense text-danger">Inventory from this record will be added back as well.</span>
             				<br>
             				<br>
             				<span class="required">This process cannot be undone.</span>
              			</label>
            		
            		</div>                       
      	  		</div>
        	</div>
        	 <input type="hidden" id="delExpenseId"/>
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">NO</button>
              <button type="button" class="btn btn-outline-primary" id="deleteExpense">YES</button>
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
	<!-- date-range-picker -->
	<script src="<c:url value="/resources/plugins/moment/moment.min.js"/>"></script>
	
	<script src="<c:url value="/resources/plugins/daterangepicker/daterangepicker.js"/>"></script>
	<!-- Tempusdominus Bootstrap 4 -->
	<script src="<c:url value="/resources/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"/>"></script>
	
	<script src="<c:url value="/resources/js/expense-report.js"/>"></script>

</html>