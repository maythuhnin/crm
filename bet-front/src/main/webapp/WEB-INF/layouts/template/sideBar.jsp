<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

  <aside class="main-sidebar sidebar-dark-primary elevation-4">
    <!-- Brand Logo -->
    <a href="../../index3.html" class="brand-link">
      <img src="<c:url value="/resources/images/logo-circle.png"/>" alt="Logo" class="brand-image img-circle elevation-3" style="opacity: .8">
      <span class="brand-text font-weight-light">ExpenseTracker</span>
    </a>

    <!-- Sidebar -->
    <div class="sidebar">
     

      <!-- Sidebar Menu -->
      <nav class="mt-4">
        <ul class="nav nav-pills nav-sidebar flex-column nav-legacy" data-widget="treeview" role="menu" data-accordion="false">
          <!-- Add icons to the links using the .nav-icon class
               with font-awesome or any other icon font library -->

               <li class="nav-item">
                <a href="<c:url value="/dashboard"/>" class="nav-link ${page eq 'dashboard' ? 'active' : ''}">
                  <i class="nav-icon fas fa-tachometer-alt"></i>
                  <p>
                    Dashboard
                  </p>
                </a>
              </li>
              <li class="nav-item">
                <a href="<c:url value="/daily"/>" class="nav-link ${page eq 'daily' ? 'active' : ''}">
                  <i class="nav-icon fas fa-road"></i>
                  <p>
                    Daily Income/Expense
                  </p>
                </a>
              </li>
              <li class="nav-item">
                <a href="<c:url value="/inventory"/>" class="nav-link ${page eq 'inventory' ? 'active' : ''}">
                  <i class="nav-icon fas fa-warehouse"></i>
                  <p>
                   Inventory
                  </p>
                </a>
              </li>
              <li class="nav-item">
                <a href="<c:url value="/loan"/>" class="nav-link ${page eq 'loan' ? 'active' : ''}">
                  <i class="nav-icon fas fa-hand-holding-usd"></i>
                  <p>
                   Driver Loan
                  </p>
                </a>
              </li>
              <li class="nav-header">REPORTS</li>
              <li class="nav-item">
                <a href="<c:url value="/expense-report"/>" class="nav-link ${page eq 'expenseReport' ? 'active' : ''}">
                  <i class="nav-icon far fa-file"></i>
                  <p>
                   Expense Report
                  </p>
                </a>
              </li>
              <li class="nav-item">
                <a href="<c:url value="/income-report"/>" class="nav-link ${page eq 'incomeReport' ? 'active' : ''}">
                  <i class="nav-icon far fa-file"></i>
                  <p>
                    Income Report
                  </p>
                </a>
              </li>
       
          <li class="nav-header">MASTER DATA</li>
           <li class="nav-item">
            <a href="<c:url value="/driver"/>" class="nav-link ${page eq 'driver' ? 'active' : ''}">
              <i class="nav-icon far fa-address-card"></i>
              <p>
                DRIVER
              </p>
            </a>
          </li>
          <li class="nav-item">
            <a href="<c:url value="/bus"/>" class="nav-link ${page eq 'bus' ? 'active' : ''}">
              <i class="nav-icon fas fa-bus"></i>
              <p>
                BUS
              </p>
            </a>
          </li>
          <li class="nav-item">
            <a href="<c:url value="/destination"/>" class="nav-link ${page eq 'destination' ? 'active' : ''}">
              <i class="nav-icon fas fa-route"></i>
              <p>
                DESTINATION
              </p>
            </a>
          </li>
           <li class="nav-item">
            <a href="<c:url value="/fixed-expense"/>" class="nav-link ${page eq 'fixedExpense' ? 'active' : ''}">
              <i class="nav-icon fas fa-coins"></i>
              <p>
                FIXED EXPENSE
              </p>
            </a>
          </li>
          
          <li class="nav-header">SETTING</li>
          <li class="nav-item">
            <a href="<c:url value="/user"/>" class="nav-link ${page eq 'user' ? 'active' : ''}">
              <i class="nav-icon fas fa-users"></i>
              <p>
                User
              </p>
            </a>
          </li>
        </ul>
      </nav>
      <!-- /.sidebar-menu -->
    </div>
    <!-- /.sidebar -->
  </aside>