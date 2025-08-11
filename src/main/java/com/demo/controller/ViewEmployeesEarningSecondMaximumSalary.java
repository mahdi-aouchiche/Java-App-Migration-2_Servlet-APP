package com.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.demo.model.Employee;
import com.demo.service.EmployeeService;

/**
 * Servlet implementation class ViewEmployeesEarningSecondMaximumSalary
 */
@WebServlet("/ViewEmployeesEarningSecondMaximumSalary")
public class ViewEmployeesEarningSecondMaximumSalary extends HttpServlet {
	private static final long serialVersionUID = 1L;

	EmployeeService employeeService = null;
	
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		this.employeeService = new EmployeeService();
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{    	
		/* Check if the user is logged in to be able to have access*/
		HttpSession session = request.getSession(false);
		
		if (session == null || session.getAttribute("username") == null) {
			response.sendRedirect("LoginPage");
			return;
		}	
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		/* HTML Head */
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("	<meta charset=\"UTF-8\">");
		out.println("	<title>Employees Earning Second Highest Salary</title>");
		
		out.println("	<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css'>");
		out.println("	<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script>");
		out.println("	<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js'></script>");
		out.println("	<style>");
		out.println("		table th, td { text-align: center; }");
		out.println("	</style>");
		out.println("</head>");
		
		/* HTML Body*/
		out.println("<body>");
		out.println("<div class='container'>");
		out.println("<br>");
		
		// Print the results in an HTML table using the style 
		out.println("<table class='table table-bordered table-striped table-hover'>");
		
		List<Employee> employeesEarningSecondMaximumSalary = new ArrayList<Employee>();
		List<String> columnLabels = new ArrayList<String>();
		this.employeeService.employeesEarningSecondMaximumSalary(columnLabels,employeesEarningSecondMaximumSalary);
		
		// print the table header
		out.println("<tr>");
		for (String label : columnLabels) {
			out.printf("<th>%s</th>", label);
		}
		out.println("</tr>");
		
		// print the result in the table rows
		for (Employee employee : employeesEarningSecondMaximumSalary) {
			out.println("<tr>");
			out.printf("<td>%d</td>",employee.getEmployeeId());
			out.printf("<td>%s</td>",employee.getEmployeeName());
			out.printf("<td>%d</td>",employee.getAge());
			out.printf("<td>$%,.2f</td>", employee.getSalary());
			out.println("</tr>");
		}		
		out.println("</table>");
		// <%-- Go Back To Menu Link --%>
		out.println("<br>");
		out.println("<a href='OptionMenu' class='btn btn-primary'>Go Back to Menu</a>");
		out.println("</div>");
		out.println("</body>");
	}
}
