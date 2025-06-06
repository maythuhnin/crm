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
	    <!-- Tagsinput -->
	    <link rel="stylesheet" href="<c:url value="/resources/plugins/tagsinput/tagsinput.css"/>">	
    	<!-- DataTables -->
	    <link rel="stylesheet" href="<c:url value="/resources/plugins/datatables-bs4/css/dataTables.bootstrap4.min.css"/>">
	    <link rel="stylesheet" href="<c:url value="/resources/plugins/datatables-responsive/css/responsive.bootstrap4.min.css"/>">
	    <link rel="stylesheet" href="<c:url value="/resources/css/driver.css"/>"> 
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
	             	 <button type="button" class="btn btn-outline-primary float-right" id="addDriver">
	                   Add <i class="fas fa-plus-circle"></i>
	                  </button>
	            </div>
			    
			    
			</div>
        </div>
        <div class="card-body">
          <table id="driverDatatable" class="table table-bordered">
            <thead>
            <tr >
              <th>Driver</th>
              <th>Phone</th>
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
	<div class="modal fade" id="deleteDriverModal">
        <div class="modal-dialog modal-xs">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">Delete Driver </h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
                  <!-- form start -->
        
            <div class="modal-body">
          		<div class="card-body del-card">
            		<div class="form-group row">
              			<label class="col-sm-12 col-form-label">
              				Are you sure you want to delete driver : <span id="delName"></span>?
              				<br>
              				<span class="delete-text text-danger">This driver will be removed in all added Buses as well.</span>
             				<br><br>
             				<span class="required">This process cannot be undone.</span>
              			</label>
            		
            		</div>                       
      	  		</div>
        	</div>
        	 <input type="hidden" id="delDriverId"/>
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">NO</button>
              <button type="button" class="btn btn-outline-danger" id="deleteDriver">YES</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      
      
      <div class="modal fade" id="addDriverModal">
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
        <form class="form-horizontal" id="addDriverForm">
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
              <label for="phone" class="col-sm-4 col-form-label">Phone </label>
              <div class="col-sm-6">
                <input type="text" data-role="tagsinput" name="phone" class="form-control" id="phone">
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
    
     <div class="modal fade" id="editDriverModal">
        <div class="modal-dialog modal-md">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title bus-title">Edit Driver</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
              <!-- form start -->
        <form class="form-horizontal" id="editDriverForm">
          <div class="card-body">
            <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="name" class="col-sm-4 col-form-label">Name <span class="required">[required]</span></label>
              <div class="col-sm-6">
                <input type="text" name="editName" class="form-control" id="editName">
              </div>
            </div>
            <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="phone" class="col-sm-4 col-form-label">Phone </label>
              <div class="col-sm-6">
                <input type="text" data-role="tagsinput" name="editPhone" class="form-control" id="editPhone">
              </div>
            </div>
          </div> 
        
          <!-- /.card-footer -->
        </form>
            </div>
            <div class="modal-footer">
            <input type="hidden" id="editDriverId"/>
              <button type="button" class="btn btn-default cancel" data-dismiss="modal">Cancel</button>
              <button type="button" class="btn btn-outline-primary" id="editDriver">Save Changes</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
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
	<!-- TagsInput -->
	<script src="<c:url value="/resources/plugins/tagsinput/tagsinput.js"/>"></script>
	<script src="<c:url value="/resources/plugins/jquery-validation/dist/jquery.validate.min.js"/>"></script>
	<script src="<c:url value="/resources/js/validator.js"/>"></script>
	<script src="<c:url value="/resources/js/driver.js"/>"></script>

</html>