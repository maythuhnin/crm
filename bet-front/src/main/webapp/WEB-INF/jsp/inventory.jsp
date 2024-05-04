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
	    <link rel="stylesheet" href="<c:url value="/resources/css/inventory.css"/>"> 
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
	             	 <button type="button" class="btn btn-outline-primary float-right" id="addInventory">
	                   Add <i class="fas fa-plus-circle"></i>
	                  </button>
	            </div>
			    
			    
			</div>
        </div>
        <div class="card-body">
          <table id="inventoryDatatable" class="table table-bordered">
            <thead>
            <tr >
              <th>Item</th>
              <th>Goods Received Date</th>
              <th>Price</th>
              <th>Quantity</th>
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
	<div class="modal fade" id="deleteInventoryModal">
        <div class="modal-dialog modal-xs">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">Delete Inventory </h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
                  <!-- form start -->
        
            <div class="modal-body">
          		<div class="card-body del-card">
            		<div class="form-group row">
              			<label class="col-sm-12 col-form-label">
              				Are you sure you want to delete inventory : <span id="delName"></span>?
              				<br>
             				<span class="required">This process cannot be undone.</span>
              			</label>
            		
            		</div>                       
      	  		</div>
        	</div>
        	 <input type="hidden" id="delInventoryId"/>
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">NO</button>
              <button type="button" class="btn btn-outline-danger" id="deleteInventory">YES</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      
      
      <div class="modal fade" id="addInventoryModal">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title bus-title">Add Inventory</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
              <!-- form start -->
        <form class="form-horizontal" id="addInventoryForm">
          <div class="card-body">
            <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="name" class="col-sm-4 col-form-label">Item <span class="required">[required]</span></label>
              <div class="col-sm-6">
                <input type="text" name="item" class="form-control" id="item">
              </div>
            </div>
            <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="receivedDate" class="col-sm-4 col-form-label">Goods Received Date <span class="required">[required]</span></label>
               <div class="col-sm-6">
                    <div class="input-group date" id="receivedDate" data-target-input="nearest">
	                <input type="text" class="form-control datetimepicker-input" data-target="#receivedDate"/>
	                <div class="input-group-append" data-target="#receivedDate" data-toggle="datetimepicker">
	                    <div class="input-group-text"><i class="fa fa-calendar"></i></div>
	                </div>
				       </div>
	              </div>
            </div>
            <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="price" class="col-sm-4 col-form-label">Price <span class="required">[required]</span></label>
              <div class="col-sm-6">
                <input type="number" name="price" class="form-control" id="price" min="0">
              </div>
            </div>
            
            <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="quantity" class="col-sm-4 col-form-label">Quantity <span class="required">[required]</span></label>
              <div class="col-sm-6">
                <input type="number" name="quantity" class="form-control" id="quantity" min="1">
              </div>
            </div>
          </div> 
        
          <!-- /.card-footer -->
        </form>
            </div>
            <div class="modal-footer">
            <input type="hidden" id="inventoryType"/>
              <button type="button" class="btn btn-default cancel" data-dismiss="modal">Cancel</button>
              <button type="button" class="btn btn-outline-primary" id="saveInventory">Save Changes</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
    <!-- /.content -->
    
     <div class="modal fade" id="editInventoryModal">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title bus-title">Edit Inventory</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
              <!-- form start -->
        <form class="form-horizontal" id="editInventoryForm">
          <div class="card-body">
            <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="name" class="col-sm-4 col-form-label">Item <span class="required">[required]</span></label>
              <div class="col-sm-6">
                <input type="text" name="editItem" class="form-control" id="editItem">
              </div>
            </div>
            <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="receivedDate" class="col-sm-4 col-form-label">Goods Received Date <span class="required">[required]</span> </label>
               <div class="col-sm-6">
                    <div class="input-group date" id="editReceivedDate" data-target-input="nearest">
	                <input type="text" class="form-control datetimepicker-input" data-target="#editReceivedDate"/>
	                <div class="input-group-append" data-target="#editReceivedDate" data-toggle="datetimepicker">
	                    <div class="input-group-text"><i class="fa fa-calendar"></i></div>
	                </div>
				       </div>
	              </div>
            </div>
            <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="editPrice" class="col-sm-4 col-form-label">Price <span class="required">[required]</span> </label>
              <div class="col-sm-6">
                <input type="number" name="editPrice" class="form-control" id="editPrice">
              </div>
            </div>
            
            <div class="form-group row">
              <div class="col-sm-1"></div>
              <label for="quantity" class="col-sm-4 col-form-label">Quantity <span class="required">[required]</span> </label>
              <div class="col-sm-6">
                <input type="number" name="editQuantity" class="form-control" id="editQuantity">
              </div>
            </div>
          </div> 
        
          <!-- /.card-footer -->
        </form>
            </div>
            <div class="modal-footer">
            <input type="hidden" id="editInventoryId"/>
              <button type="button" class="btn btn-default cancel" data-dismiss="modal">Cancel</button>
              <button type="button" class="btn btn-outline-primary" id="editInventory">Save Changes</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      
                  <!-- /.card -->
	<div class="modal fade" id="stockHistoryModal">
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title bus-title">Stock History</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
              <!-- form start -->
        <form class="form-horizontal" id="stockHistoryForm">
          <div class="card-body">
				<table id="stockHistoryDatatable" class="table table-bordered table-condensed">
		            <thead>
		            <tr>
		              <th>Type</th>
		              <th>Quantity</th>
		              <th>Transaction Date</th>
		              <th>Transaction Reference</th>
		            </tr>
		            </thead>
		            <tbody>
		            
		            </tbody>
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
	<script src="<c:url value="/resources/js/inventory.js"/>"></script>

</html>