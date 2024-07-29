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
	     <!-- daterange picker -->
	     
 		 <link rel="stylesheet" href="<c:url value="/resources/plugins/daterangepicker/daterangepicker.css"/>">
	    <!-- Tempusdominus Bootstrap 4 -->
	  	<link rel="stylesheet" href="<c:url value="/resources/plugins/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css"/>">
	   
	     <link rel="stylesheet" href="<c:url value="/resources/css/daily.css"/>"> 
	</head>
	<!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">

    <!-- Main content -->
    <section class="content pt-2 pb-1">

      <!-- Default box -->
      <div class="card">
    
      <div class="card-body">
      <form class="form-horizontal" id="addDailyForm">
		<div class="form-group row">
              <label for="bus" class="col-sm-2 col-form-label text-right">Bus <span class="required">[required]</span> </label>
               <div class="col-sm-2">
                    <select class="form-control" style="width: 100%;" id="bus" name="bus">
			           
			   		</select>
	              </div>
	              <label for="dateRange" class="col-sm-1 col-form-label text-right exchange-col">Date <span class="required">[required]</span> </label>
              
	              <div class="col-sm-3">
	               <div class="input-group date-group"> 
                    <div class="input-group-prepend">
                      <span class="input-group-text">
                        <i class="far fa-calendar-alt"></i>
                      </span>
                    </div>
                    <input type="text" class="form-control float-right" id="dateRange" name="dateRange" placeholder="Select Date.">
                  </div>
                  </div>
                   <div class="col-sm-1 exchange-col"></div>
               
               <div class="col-sm-2">
                   <div class="form-check">
	                 <input type="checkbox" class="form-check-input" id="restDay">
	                <label class="form-check-label" for="restDay">Rest Day</label>
	              </div>
	              </div>
            </div>
            <div class="form-group row no-rest">
            	  <label for="reservation" class="col-sm-2 col-form-label text-right">Path <span class="required">[required]</span> </label>
              <div class="col-sm-2">
                <select style="width: 100%;" class="form-control path" id="path1" name="path1"></select>
               
              </div>
               <div class="col-sm-1 exchange-col"><i class="fas fa-exchange-alt"></i></div>
                <div class="col-sm-2">
                <select style="width: 100%;" class="form-control path" id="path2" name="path2"></select>
               
              </div>
            <div class="col-sm-1 exchange-col"><i class="fas fa-exchange-alt"></i></div>
               <div class="col-sm-2">
                <select style="width: 100%;" class="form-control path" id="path3" name="path3"></select>
               
              </div>
            </div>
            <div class="form-group row no-rest">
              <label for="receivedDate" class="col-sm-2 col-form-label text-right">On Paper Income (Leave)<span class="required">[required]</span> </label>
               <div class="col-sm-2">
                    <input type="number" class="form-control" style="width: 100%;" id="onPaperIncomeLeave" name="onPaperIncomeLeave" min="0"/>
	              </div>
	              <label for="receivedDate" class="col-sm-2 col-form-label text-right exchange-col">On Paper Income (Return)<span class="required">[required]</span> </label>
               <div class="col-sm-2">
                    <input type="number" class="form-control" style="width: 100%;" id="onPaperIncomeReturn" name="onPaperIncomeReturn" min="0"/>
	              </div>
	            <label for="receivedDate" class="col-sm-2 col-form-label text-right exchange-col">In Hand Cash <span class="required">[required]</span> </label>
               <div class="col-sm-2">
                    <input type="number" class="form-control" style="width: 100%;" id="inHandCash" name="inHandCash" min="0"/>
	              </div> 
	               
	              
            </div>
             <div class="form-group row no-rest">
              <label for="receivedDate" class="col-sm-2 col-form-label text-right">Extra Income From Hitchhikers </label>
               <div class="col-sm-2">
                    <input type="number" class="form-control" style="width: 100%;" id="extraIncome" name="extraIncome" min="0"/>
	              </div>
	               <label for="receivedDate" class="col-sm-2 col-form-label text-right exchange-col">Adjustment : </label>
               <div class="col-sm-2">
                   <label class="col-form-label text-center" id="adjustment"> - </label>
	              </div>
	            <label for="receivedDate" class="col-sm-2 col-form-label text-right exchange-col total">Total : </label>
               <div class="col-sm-2">
                   <label class="col-form-label text-center" id="total"> - </label>
	              </div>  
	              
            </div>
             </form>
            
            <div class="form-group row mt-3">
             <div class="col-sm-12"><hr>
             </div>
             </div>
             <form class="form-horizontal" id="expenseForm">
             <div class="form-group row mt-3">
             	<div class="col-sm-1"></div>
	              <div class="col-sm-5">
	              	 <select class="form-control" style="width: 100%;" id="expenseType" name="expenseType">
			           
			   		</select>
	              </div>
	                <div class="col-sm-4">
	                <input type="number" class="form-control" id="amount" placeholder="Amount" name="amount" min="1"/>
	               
	              </div>
	            <div class="col-sm-1"><button type="button" class="btn btn-outline-primary" id="addExpense"><i class="fas fa-plus"></i></button></div>
	            </div>
	            </form>
            <div class="form-group row mt-3">
            <div class="col-sm-1"></div>
             <div class="col-sm-10">
                <table id="expenseDatatable" class="table table-bordered table-condensed" style="width:100%;">
		            <thead> 
		            <tr>
		              <th>Expense Type</th>
		              <th>Amount</th>
		              <th></th>
		            </tr>
		            </thead>
		            <tbody>
		            
		            </tbody>
		            <tfoot>
		            <tr>
		              <th class="text-right">Total:</th>
		              <th></th>
		              <th></th>
		            </tr>
		        </tfoot>
	          </table>
              </div>
                </div>
   </div> 
   <div class="card-footer">
   		<button type="button" class="btn btn-outline-primary float-right ml-1" id="saveDaily">Save Changes</button>
   		<a href="<c:url value="/daily"/>">
   		<button type="button" class="btn btn-default cancel float-right">Cancel</button>
   		</a>
   </div>
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
	
	<script src="<c:url value="/resources/plugins/jquery-validation/dist/jquery.validate.min.js"/>"></script>
	<script src="<c:url value="/resources/js/validator.js"/>"></script>
	<!-- Select2 -->
	<script src="<c:url value="/resources/plugins/select2/js/select2.full.min.js"/>"></script>
	
	<!-- date-range-picker -->
	<script src="<c:url value="/resources/plugins/moment/moment.min.js"/>"></script>
	
	<script src="<c:url value="/resources/plugins/daterangepicker/daterangepicker.js"/>"></script>
	<!-- Tempusdominus Bootstrap 4 -->
	<script src="<c:url value="/resources/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"/>"></script>
	
	<script src="<c:url value="/resources/js/daily.js"/>"></script>

</html>