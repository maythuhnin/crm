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
	    <link rel="stylesheet" href="<c:url value="/resources/css/bus.css"/>"> 
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
			        <select class="form-control select2 select-filter" style="width: 100%;" id="searchStatus">
			        	<option></option>
			            <option value="OK">OK</option>
				        <option value="REPAIRING">REPAIRING</option>
			        </select>
			    </div>
			    <div class="col-sm-3">
			        <select class="form-control select2 select-filter" style="width: 100%;" id="searchDriver">
			        	<option></option>
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
			
          		<div class="col-sm-2">
	                  <button type="button" class="btn btn-outline-primary btn-sm float-right" id="addBus">
	                   Add <i class="fas fa-plus-circle"></i>
	                  </button>
	            </div>
			    
			    
			</div>
        </div>
        <div class="card-body">
          <table id="busDatatable" class="table table-bordered">
            <thead>
            <tr>
              <th>License Plate</th>
              <th>Primary Driver</th>
              <th>Secondary Driver</th>
              <th>Primary Driver Phone</th>
              <th>Status</th>
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
	<div class="modal fade" id="addBusModal">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title bus-title">Add Bus</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
              <!-- form start -->
        <form class="form-horizontal" id="addBusForm">
          <div class="card-body">
            <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="licensePlate" class="col-sm-4 col-form-label">License Plate <span class="required">[required]</span></label>
              <div class="col-sm-6">
                <input type="text" name="licensePlate" class="form-control" id="licensePlate" data-inputmask='"mask": "9A-9999"' data-mask>
              </div>
            </div>
            
            <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="primaryDriver" class="col-sm-4 col-form-label">Primary Driver <span class="required">[required]</span></label>
                <div class="col-sm-6">
                    <div class="input-group add-input-group">
						  <select class="custom-select" id="primaryDriver" name="primaryDriver">
						    <option selected></option>
						
						  </select>
						  <div class="input-group-append">
						    <button class="btn btn-default add-driver" data-type="primaryDriver" type="button"><i class="nav-icon fas fa-plus-square"></i></button>
						  </div>
					</div>
              </div>
            </div>
            
             <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="secondaryDriver" class="col-sm-4 col-form-label">Secondary Driver</span></label>
              <div class="col-sm-6">
                    <div class="input-group">
						  <select class="custom-select" id="secondaryDriver">
						    <option selected></option>
						  
						  </select>
						  <div class="input-group-append">
						    <button class="btn btn-default add-driver" data-type="secondaryDriver" type="button"><i class="nav-icon fas fa-plus-square"></i></button>
						  </div>
					</div>
              </div>
            </div>
            
            <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="phone" class="col-sm-4 col-form-label">Primary Driver Phone</label>
              <div class="col-sm-6">
                <input type="text" name="phone" class="form-control" id="phone" disabled="true">
              </div>
            </div>
            
            <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="status" class="col-sm-4 col-form-label">Status <span class="required">[required]</span></label>
              <div class="col-sm-6">
                 <select class="form-control" style="width: 100%;" id="status" name="status">
			            <option value="OK">OK</option>
			            <option value="REPAIRING">REPAIRING</option>
			   		</select>
              </div>
            </div>
         
           
          </div> 
        
          <!-- /.card-footer -->
        </form>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default cancel" data-dismiss="modal">Cancel</button>
              <button type="button" class="btn btn-outline-primary" id="saveBus">Save Changes</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      
            <!-- /.card -->
	<div class="modal fade" id="editBusModal">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title bus-title">Edit Bus</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
              <!-- form start -->
        <form class="form-horizontal" id="editBusForm">
          <div class="card-body">
            
            
           <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="licensePlate" class="col-sm-4 col-form-label">License Plate <span class="required">[required]</span></label>
              <div class="col-sm-6">
                <input type="text" name="editLicensePlate" class="form-control" id="editLicensePlate" data-inputmask='"mask": "9A-9999"' data-mask>
              </div>
            </div>
            
            <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="editPrimaryDriver" class="col-sm-4 col-form-label">Primary Driver <span class="required">[required]</span></label>
                <div class="col-sm-6">
                    <div class="input-group edit-input-group">
						  <select class="custom-select" id="editPrimaryDriver" name="editPrimaryDriver">
						    <option selected></option>
						
						  </select>
						  <div class="input-group-append">
						    <button class="btn btn-default add-driver" data-type="primaryDriver" type="button"><i class="nav-icon fas fa-plus-square"></i></button>
						  </div>
					</div>
              </div>
            </div>
            
             <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="editSecondaryDriver" class="col-sm-4 col-form-label">Secondary Driver </label>
              <div class="col-sm-6">
                    <div class="input-group">
						  <select class="custom-select" id="editSecondaryDriver">
						    <option selected></option>
						  
						  </select>
						  <div class="input-group-append">
						    <button class="btn btn-default add-driver" data-type="editSecondaryDriver" type="button"><i class="nav-icon fas fa-plus-square"></i></button>
						  </div>
					</div>
              </div>
            </div>
            
            <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="editPhone" class="col-sm-4 col-form-label">Primary Driver Phone</label>
              <div class="col-sm-6">
                <input type="text" name="editPhone" class="form-control" id="editPhone" disabled="true">
              </div>
            </div>
            
            <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="editStatus" class="col-sm-4 col-form-label">Status <span class="required">[required]</span></label>
              <div class="col-sm-6">
                 <select class="form-control" style="width: 100%;" id="editStatus" name="editStatus">
			            <option value="OK">OK</option>
			            <option value="REPAIRING">REPAIRING</option>
			   		</select>
              </div>
            </div>
         
         
           
          </div> 
        
          <!-- /.card-footer -->
        </form>
            </div>
            <div class="modal-footer">
            	<input type="hidden" id="busId"/>
              <button type="button" class="btn btn-default cancel" data-dismiss="modal">Cancel</button>
              <button type="button" class="btn btn-outline-primary" id="editBus">Save Changes</button>
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
              <h4 class="modal-title">Delete Bus </h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
                  <!-- form start -->
        
            <div class="modal-body">
          		<div class="card-body del-card">
            		<div class="form-group row">
              			<label class="col-sm-12 col-form-label">
              				Are you sure you want to delete bus : <span id="delName"></span>?
              				<br>
             				<span class="required">This process cannot be undone.</span>
              			</label>
            		
            		</div>                       
      	  		</div>
        	</div>
        	 <input type="hidden" id="delBusId"/>
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">NO</button>
              <button type="button" class="btn btn-outline-danger" id="deleteBus">YES</button>
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

<!-- Select2 -->
<script src="<c:url value="/resources/plugins/select2/js/select2.full.min.js"/>"></script>

<script src="<c:url value="/resources/plugins/inputmask/jquery.inputmask.min.js"/>"></script>

<script src="<c:url value="/resources/plugins/jquery-validation/dist/jquery.validate.min.js"/>"></script>
<script src="<c:url value="/resources/js/validator.js"/>"></script>
<script src="<c:url value="/resources/js/bus.js"/>"></script>

</html>