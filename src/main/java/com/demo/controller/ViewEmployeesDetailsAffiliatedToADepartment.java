package com.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.demo.model.Employee;
import com.demo.service.EmployeeService;

@WebServlet("/ViewEmployeesDetailsAffiliatedToADepartment")
public class ViewEmployeesDetailsAffiliatedToADepartment extends HttpServlet {
	private static final long serialVersionUID = 1L;
	EmployeeService employeeService = null;

	public void init(ServletConfig config) throws ServletException {
		this.employeeService = new EmployeeService();
	}

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
		out.println("	<title>Employees Associated To A Department</title>");
		
		out.println("	<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css'>");
		out.println("	<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script>");
		out.println("	<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js'></script>");
		out.println("	<style>");
		out.println("		.edit-form-row { display: none; }");
		out.println("		.edit-form-row td { padding: 0 !important; }");
		out.println("		.edit-form-row form { margin: 0; padding: 8px; border: none; }");
		out.println("		.edit-form-row input { display: inline-block; width: 100%; margin-right: 5px; }");
		out.println("		.edit-form-row button { padding: 5px 10px; }");
		out.println("		.action-icons { display: flex; justify-content: space-around; align-items: center; }");
		out.println("		.action-icons img { margin: 0 5px; cursor: pointer; }");
		out.println("	</style>");
		// JavaScript function to toggle the form visibility
		out.println("	<script>");
		out.println("		function showUpdateForm(id) {");
		out.println("  			var element = document.getElementById('edit-row-' + id);");
		out.println("  			if (element.style.display === 'none' || element.style.display === '') {");
		out.println("    			element.style.display = 'table-row';");
		out.println("  			} else {");
		out.println("    			element.style.display = 'none';");
		out.println("  			}");
		out.println("		}");
		out.println("	</script>");
		out.println("</head>");

		/* HTML Body */
		out.println("<body>");
		out.println("<div class='container'>");
		out.println("<br>");
		out.println("<table class='table table-bordered table-striped table-hover'>");  // start table
		
		List<Employee> employeesAssociatedToDepartment = new ArrayList<Employee>();
		List<String> columnLabels = new ArrayList<String>();
		this.employeeService.listOfEmployeesAssociatedToDepartment(columnLabels,employeesAssociatedToDepartment);
		
		/* Print the table header */
		columnLabels.add("Action");
				
		out.println("<thead>");
		out.println("<tr>");
		for (String label : columnLabels) {
			out.printf("<th style='text-align:center'>%s</th>", label);
		}
		out.println("</tr>");
		out.println("</thead>");
		
		/* Print the table body */
		out.println("<tbody>");
		for (Employee employee : employeesAssociatedToDepartment) {
			
			int id 			= employee.getEmployeeId();
			String name 	= employee.getEmployeeName();
			int age 		= employee.getAge();
			double salary 	= employee.getSalary();
			
			out.println("<tr>");  // begin table row
			// Employee data
			out.printf("<td style='text-align:center'>%d</td>", id);
			out.printf("<td>%s</td>", name);
			out.printf("<td style='text-align:center'>%d</td>", age);
			out.printf("<td>$%,.2f</td>", salary);
			
			// Action column
			out.println("<td>");
			out.println("	<div class='action-icons'>");
			
			// Delete employee
			out.println("		<img src='images/delete.png' title='delete' width='25' height='25'" + 
								" onclick=\"window.location.href='DeleteEmployeeServlet?id=" + 
								id + "&returnURL=" + request.getServletPath() + "';\">");
			// Edit employee
			out.println(		"<img src='images/edit.png' title='edit' width='25' height='25'" +
								" onclick=\"showUpdateForm(" + id + ")\">");
			out.println("	</div>");
			out.println("</td>");
			out.println("</tr>");	// end table row
			
			// Hidden edit form row
			out.println("<tr class='edit-form-row' id='edit-row-" + id + "'>");
			// Use the UpdateEmployeeServlet with id and returnURL to update the Employee and return to the page
			out.println(" <form action='UpdateEmployeeServlet?id=" + id + "&returnURL="
								+ request.getServletPath() + "' method='post'>");

			out.printf("	<td> <input type='hidden' name='id' value='%d'> </td>", id);
			out.printf("	<td> <input type='text' name='name' value='%s' placeholder='Full Name' required> </td>", name);
			out.printf("	<td> <input type='number' name='age' value='%d' placeholder='Age' min='18' required style='text-align:center;'> </td>", age);
			out.printf("	<td> <input type='number' name='salary' value='%.2f' placeholder='Salary' oninput='doubleValue()' step='0.01' min='0' required> </td>", salary);

			out.println("	<td style = 'display: flex; justify-content: space-around; align-items: center;'>");
			out.println("		<button type='submit' class='btn btn-success'>Update</button>");
			out.println("		<button type='button' class='btn btn-default'" + 
								"onclick=\"showUpdateForm(" + id + ")\">Cancel</button>");
			out.println("	</td>");
			out.println(" </form>");
			out.println("</tr>");	
		}
		out.println("</tbody>");	// end of table body
		out.println("</table>");		
		// <%-- Go Back To Menu Link --%>
		out.println("<br>");
		out.println("<a href='OptionMenu' class='btn btn-primary'>Go Back to Menu</a>");
		out.println("</div>"); // close container
		out.println("<hr>");
		out.println("</body>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		doGet(request, response);
	}
}