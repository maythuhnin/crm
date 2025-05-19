<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
		<!-- Select2 -->
   		<link rel="stylesheet" href="<c:url value="/resources/plugins/select2/css/select2.min.css"/>">
   		<link rel="stylesheet" href="<c:url value="/resources/plugins/select2-bootstrap4-theme/select2-bootstrap4.min.css"/>">
  		<!-- Tagsinput -->
	    <link rel="stylesheet" href="<c:url value="/resources/plugins/tagsinput/tagsinput.css"/>">	
    	 <!-- daterange picker -->
	     
 		 <link rel="stylesheet" href="<c:url value="/resources/plugins/daterangepicker/daterangepicker.css"/>">
	    <!-- Tempusdominus Bootstrap 4 -->
	  	<link rel="stylesheet" href="<c:url value="/resources/plugins/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css"/>">
	    <!-- Leaflet Map -->
	  	<link rel="stylesheet" href="<c:url value="/resources/plugins/leaflet/leaflet.css"/>">
    	<!-- DataTables -->
	    <link rel="stylesheet" href="<c:url value="/resources/plugins/datatables-bs4/css/dataTables.bootstrap4.min.css"/>">
	    <link rel="stylesheet" href="<c:url value="/resources/plugins/datatables-responsive/css/responsive.bootstrap4.min.css"/>">
	    <link rel="stylesheet" href="<c:url value="/resources/css/sales.css"/>"> 
	</head>
 <div class="content-wrapper">
   

        <!-- Main content -->
    <section class="content pt-2 pb-1">

      <!-- Default box -->
      <div class="card">
        <div class="card-header">
          <div class="row">
          		<div class="col-sm-2">
			        <select class="form-control select2 select-filter" style="width: 100%;" id="searchStatus">
			        	<option value="TYPE">BY TYPE</option>
			            <option value="GENDER">BY GENDER</option>
				        <option value="COLLECTION">BY COLLECTION</option>
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
			     <div class="col-sm-2">
	             	 <button type="button" class="btn btn-default" id="clearFilters">
	             	 	
	                   Clear  <i class="fas fa-times"></i>
	                  </button>
	            </div>
			
          		<div class="col-sm-2">
	                  
	            </div>
			    
			    
			</div>
        </div>
        <div class="card-body">
         <div class="row">
          <div class="col-md-6">
           <!-- PIE CHART -->
            <div class="card">
              <div class="card-header">
                <h3 class="card-title">Pie Chart</h3>

                <div class="card-tools">
                  <button type="button" class="btn btn-tool" data-card-widget="collapse">
                    <i class="fas fa-minus"></i>
                  </button>
                  <button type="button" class="btn btn-tool" data-card-widget="remove">
                    <i class="fas fa-times"></i>
                  </button>
                </div>
              </div>
              <div class="card-body">
                <canvas id="pieChart" style="min-height: 250px; height: 250px; max-height: 250px; max-width: 100%;"></canvas>
              </div>
              <!-- /.card-body -->
            </div>
            <!-- /.card -->
            </div>
            <div class="col-md-6">
               <!-- STACKED BAR CHART -->
            <div class="card">
              <div class="card-header">
                <h3 class="card-title">Stacked Bar Chart</h3>

                <div class="card-tools">
                  <button type="button" class="btn btn-tool" data-card-widget="collapse">
                    <i class="fas fa-minus"></i>
                  </button>
                  <button type="button" class="btn btn-tool" data-card-widget="remove">
                    <i class="fas fa-times"></i>
                  </button>
                </div>
              </div>
              <div class="card-body">
                <div class="chart">
                  <canvas id="stackedBarChart" style="min-height: 250px; height: 250px; max-height: 250px; max-width: 100%;"></canvas>
                </div>
              </div>
              <!-- /.card-body -->
            </div>
            </div>
            <!-- /.card -->
            </div>
            <div class="row">
	          <div class="col-md-12">
	          	 <div id="map"></div>
	          </div>
         	</div>
        </div>
        <!-- /.card-body -->
      </div>
      <!-- /.card -->
    </section>
</div>

<!-- DataTables  & Plugins -->
<script src="<c:url value="/resources/plugins/datatables/jquery.dataTables.min.js"/>"></script>
<script src="<c:url value="/resources/plugins/datatables-bs4/js/dataTables.bootstrap4.min.js"/>"></script>
<script src="<c:url value="/resources/plugins/datatables-responsive/js/dataTables.responsive.min.js"/>"></script>
<script src="<c:url value="/resources/plugins/datatables-responsive/js/responsive.bootstrap4.min.js"/>"></script>
<!-- ChartJS -->
<script src="<c:url value="/resources/plugins/chart.js/Chart.min.js"/>"></script>
<!-- Select2 -->
<script src="<c:url value="/resources/plugins/select2/js/select2.full.min.js"/>"></script>

<script src="<c:url value="/resources/plugins/inputmask/jquery.inputmask.min.js"/>"></script>
<!-- TagsInput -->
<script src="<c:url value="/resources/plugins/tagsinput/tagsinput.js"/>"></script>
<!-- date-range-picker -->
<script src="<c:url value="/resources/plugins/moment/moment.min.js"/>"></script>
	
<script src="<c:url value="/resources/plugins/daterangepicker/daterangepicker.js"/>"></script>
<!-- Leaflet -->
<script src="<c:url value="/resources/plugins/leaflet/leaflet.js"/>"></script>
	

<script src="<c:url value="/resources/js/sales.js"/>"></script>
