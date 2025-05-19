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
	    <link rel="stylesheet" href="<c:url value="/resources/css/fixed-expense.css"/>"> 
	</head>
	<!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">

    <!-- Main content -->
    <section class="content pt-2 pb-1">

      <!-- Default box -->
      <div class="card">
      	 <div class="card-header">
          <div class="row">	
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
	        <div class="col-sm-5"></div>
          	<div class="col-sm-2">
	             	 <button type="button" class="btn btn-outline-primary float-right" id="addFixedExpense">
	                   Add <i class="fas fa-plus-circle"></i>
	                  </button>
	            </div>
			    
			    
			</div>
        </div>
        <div class="card-body">
          <table id="fixedExpenseDatatable" class="table table-bordered table-condensed">
            <thead>
            <tr>
           	  <th width="50px" class="dt-control"></th> 
              <th>Path</th>
              <th>Bus</th>
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
	<div class="modal fade" id="fixedExpenseModal">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title user-title add-title">Add Fixed Expense</h4>
              <h4 class="modal-title user-title d-none edit-title">Edit Fixed Expense</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
              <!-- form start -->
       
          <div class="card-body">
          	 <form class="form-horizontal" id="addFixedExpenseForm">
            <div class="form-group row">
              <div class="col-sm-3">
                <select class="form-control path" id="path1" name="path1"></select>
               
              </div>
               <div class="col-sm-1 exchange-col"><i class="fas fa-exchange-alt"></i></div>
                <div class="col-sm-3">
                <select class="form-control path" id="path2" name="path2"></select>
               
              </div>
            <div class="col-sm-1 exchange-col"><i class="fas fa-exchange-alt"></i></div>
               <div class="col-sm-3">
                <select class="form-control path" id="path3" name="path3"></select>
               
              </div>
            </div>
             <div class="form-group row">
             
              <label for="licensePlate" class="col-sm-3 col-form-label text-right">Bus :</label>
               <div class="col-sm-1 exchange-col"></div>
              <div class="col-sm-7 col-bus">
                 <select class="select2" id="bus" multiple="multiple" data-placeholder="Select Bus" style="width: 100%;">
                   
                  </select>
              </div>
              </div>
            </form>
            <div class="row"><div class="col-sm-12"><hr></div></div>
            <form class="form-horizontal" id="expenseForm">
	            <div class="form-group row mt-3">
	              <div class="col-sm-5">
	                <select class="form-control" id="expenseType" name="expenseType"></select>
	               
	              </div>
	                <div class="col-sm-5">
	                <input type="number" class="form-control" id="amount" placeholder="Amount" name="amount"/>
	               
	              </div>
	            <div class="col-sm-1"><button type="button" class="btn btn-outline-primary" id="addExpense"><i class="fas fa-plus"></i></button></div>
	            </div>
            </form>
            <div class="form-group row">
             <div class="col-sm-12">
                <table id="expenseDatatable" class="table table-bordered table-condensed">
            <thead>
            <tr>
              <th>Expense Type</th>
              <th>Amount</th>
              <th></th>
            </tr>
            </thead>
            <tbody>
            
            </tbody>
          </table>
              </div>
                </div>
            </div>
          </div> 
        
          <!-- /.card-footer -->
        
            
            <div class="modal-footer">
            <input type="hidden" id="editFixedExpenseId"/>
              <button type="button" class="btn btn-default cancel" data-dismiss="modal">Cancel</button>
              <button type="button" class="btn btn-outline-primary" id="saveFixedExpense">Save Changes</button>
            </div>
          </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      

      
        <!-- /.card -->
	<div class="modal fade" id="deleteFixedExpenseModal">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">Delete Fixed Expense </h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
                  <!-- form start -->
        
            <div class="modal-body">
          		<div class="card-body del-card">
            		<div class="form-group row">
              			<label class="col-sm-12 col-form-label">
              				Are you sure you want to delete Fixed Expense : <span id="delName"></span>?
              				<br>
             				<span class="required">This process cannot be undone.</span>
              			</label>
            		
            		</div>                       
      	  		</div>
        	</div>
        	 <input type="hidden" id="delFixedExpenseId"/>
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">NO</button>
              <button type="button" class="btn btn-outline-primary" id="deleteFixedExpense">YES</button>
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
	<script src="<c:url value="/resources/js/fixed-expense.js"/>"></script>

</html>