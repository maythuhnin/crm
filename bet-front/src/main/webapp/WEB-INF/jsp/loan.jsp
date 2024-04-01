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
	    <link rel="stylesheet" href="<c:url value="/resources/css/loan.css"/>"> 
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
			     <div class="col-sm-7"></div>
			</div>
        </div>
        <div class="card-body">
          <table id="loanDatatable" class="table table-bordered">
            <thead>
            <tr >
              <th>Driver</th>
              <th>Loan Amount</th>
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
	<div class="modal fade" id="loanHistoryModal">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title bus-title">Loan History</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
              <!-- form start -->
        <form class="form-horizontal" id="loanHistoryForm">
          <div class="card-body">
				<table id="loanHistoryDatatable" class="table table-bordered table-condensed">
		            <thead>
		            <tr>
		              <th>Date</th>
		              <th>Remark</th>
		              <th>Type</th>
		              <th>Amount</th>
		              <th></th>
		            </tr>
		            </thead>
		            <tbody>
		            
		            </tbody>
		             <tfoot>
		            <tr>
		              <th class="text-right" colspan="3">Total:</th>
		              <th></th>
		              <th></th>
		            </tr>
		        </tfoot>
          		</table>        
          </div> 
        
          <!-- /.card-footer -->
        </form>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default cancel" data-dismiss="modal">Cancel</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      
            <!-- /.card -->
	<div class="modal fade" id="addLoanModal">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title bus-title">Add Loan</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
              <!-- form start -->
        <form class="form-horizontal" id="loanForm">
          <div class="card-body">
            
           
            
            <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="updatePrimaryDriver" class="col-sm-4 col-form-label">Date <span class="required">[required]</span></label>
                <div class="col-sm-6">
                    <div class="input-group date" id="loanDate" data-target-input="nearest">
	                <input type="text" class="form-control datetimepicker-input" data-target="#loanDate"/>
	                <div class="input-group-append" data-target="#loanDate" data-toggle="datetimepicker">
	                    <div class="input-group-text"><i class="fa fa-calendar"></i></div>
	                </div>
				       </div>
	              </div>
            </div>
            
             
           <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="licensePlate" class="col-sm-4 col-form-label">Amount <span class="required">[required]</span></label>
              <div class="col-sm-6">
                <input type="number" name="amount" class="form-control" id="amount" min="1">
              </div>
            </div>
            
            <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="licensePlate" class="col-sm-4 col-form-label">Remark</label>
              <div class="col-sm-6">
              	<textarea class="form-control" name="remark" id="remark"></textarea>
              </div>
            </div>
            
             <div class="form-group row">
              <div class="col-sm-5"></div>
              <div class="col-sm-2">
                   <div class="form-group">
                        <div class="custom-control custom-radio">
                          <input class="custom-control-input" type="radio" id="typeLoan" name="type" checked value="LOAN">
                          <label for="typeLoan" class="custom-control-label">Loan</label>
                        </div>
                       
                      </div>
              </div>
              <div class="col-sm-2">
              <div class="form-group">
                       <div class="custom-control custom-radio">
                          <input class="custom-control-input" type="radio" id="typeReturn" name="type" value="RETURN">
                          <label for="typeReturn" class="custom-control-label">Return</label>
                        </div>
                      </div>
              </div>
            </div>
   
          </div> 
        
          <!-- /.card-footer -->
        </form>
            </div>
            <div class="modal-footer">
            	<input type="hidden" id="driverId"/>
              <button type="button" class="btn btn-default cancel" data-dismiss="modal">Cancel</button>
              <button type="button" class="btn btn-outline-primary" id="saveLoan">Save Changes</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      
        <!-- /.card -->
	<div class="modal fade" id="deleteModal">
        <div class="modal-dialog modal-xs">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">Delete Loan </h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
                  <!-- form start -->
        
            <div class="modal-body">
          		<div class="card-body del-card">
            		<div class="form-group row">
              			<label class="col-sm-12 col-form-label">
              				Are you sure you want to delete loan : <span id="delName"></span>?
              				<br>
             				<span class="required">This process cannot be undone.</span>
              			</label>
            		
            		</div>                       
      	  		</div>
        	</div>
        	 <input type="hidden" id="delLoanId"/>
        	 <input type="hidden" id="delDriverId"/>
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">NO</button>
              <button type="button" class="btn btn-outline-primary" id="deleteLoan">YES</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      
      
      <div class="modal fade" id="driverModal">
        <div class="modal-dialog modal-md">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title bus-title">Add Driver</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
              <!-- form start -->
        <form class="form-horizontal" id="driverForm">
          <div class="card-body">
            <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="licensePlate" class="col-sm-4 col-form-label">Name <span class="required">[required]</span></label>
              <div class="col-sm-6">
                <input type="text" name="name" class="form-control" id="name">
              </div>
            </div>
            <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="phone" class="col-sm-4 col-form-label">Phone </label>
              <div class="col-sm-6">
                <input type="text" name="driverPhone" class="form-control" id="driverPhone">
              </div>
            </div>
          </div> 
        
          <!-- /.card-footer -->
        </form>
            </div>
            <div class="modal-footer">
            <input type="hidden" id="driverType"/>
              <button type="button" class="btn btn-default cancel" data-dismiss="modal">Cancel</button>
              <button type="button" class="btn btn-outline-primary" id="saveDriver">Save Changes</button>
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
	<!-- date-range-picker -->
	<script src="<c:url value="/resources/plugins/moment/moment.min.js"/>"></script>
	<script src="<c:url value="/resources/plugins/daterangepicker/daterangepicker.js"/>"></script>
	<!-- Tempusdominus Bootstrap 4 -->
	<script src="<c:url value="/resources/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"/>"></script>
	<!-- Select2 -->
	<script src="<c:url value="/resources/plugins/select2/js/select2.full.min.js"/>"></script>
	
	<script src="<c:url value="/resources/plugins/jquery-validation/dist/jquery.validate.min.js"/>"></script>
	<script src="<c:url value="/resources/js/validator.js"/>"></script>
	<script src="<c:url value="/resources/js/loan.js"/>"></script>

</html>