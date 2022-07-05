package com.revature.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FrontController
 */

public class FrontController extends HttpServlet {
       

	/**
	 * This method will be responsible for determining what resource the client is requesting
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1. URI rewriting
		// http://localhost:8080/employee-servlet-app/login -- we want to capture login
		// http://localhost:8080/employee-servlet-app/employees -- if they go here it returns all employees in the DB
		final String URI = request.getRequestURI().replace("/employee-servlet-app/", "");
		// we're capturing the very last part of the URI
		
		// set up a switch case statement in which we call the appropriate functionality based on the URI returned
		switch(URI) {
		
		case "login":
			// invoke some function from the RequestHelper
			RequestHelper.processLogin(request, response);
			break;
		case "employees":
			RequestHelper.processEmployees(request, response);
			// invoke some functionality from the request helper which would return all employees
			break;
		case "register":
			RequestHelper.processRegistration(request, response);
			break;
		case "request":
			RequestHelper.processReimbursementRequest(request, response);
			break;
		case "find-requests":
			RequestHelper.findEmployeesReimbursements(request, response);
			break;
		case "get-user":
			RequestHelper.getUserFromSession(request, response);
			break;
		case "update-account":
			RequestHelper.updateUserInfo(request, response);
			break;
		case "find-requests-mgnr":
			RequestHelper.findEmployeesReimbursementsMngr(request, response);
			break;
		case "find-pending-requests-mgnr":
			RequestHelper.findEmployeesPendingReimbursementsMngr(request, response);
			break;
		case "find-resolved-requests-mgnr":
			RequestHelper.findEmployeesResolvedReimbursementsMngr(request, response);
			break;
		case "find-all-emp-mgnr":
			RequestHelper.getAllEmp(request, response);
			break;
		
		case "update-reimbs-status":
			RequestHelper.updateRembsStatus(request, response);
			break;
		case "logout":
			RequestHelper.processLogout(request, response);
			break;
		default:
			RequestHelper.processLogout(request, response);
			break;
		}
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
