package com.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.demo.service.EmployeeService;

/**
 * Servlet implementation class AddNewEmployeeServlet
 */
@WebServlet("/AddNewEmployeeServlet")
public class AddNewEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * This method handles displaying the form
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
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
		
		// Since doPost will now be able to display the form too,
		// we can just call our new helper method.
		showForm(request, response, null);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		int numEmployeesAdded = 0;
		String firstname;
		String lastname;
		String fullname = "";
		int age;
		double salary;

		try {
			// Get employee details
			firstname = request.getParameter("firstname");
			lastname = request.getParameter("lastname");
			fullname = firstname + " " + lastname;
			age = Integer.parseInt(request.getParameter("age"));
			salary = Double.parseDouble(request.getParameter("salary"));
		
			// Send info to service layer
			EmployeeService employeeService = new EmployeeService();
			
			numEmployeesAdded = employeeService.addNewEmployee(fullname, age, salary);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		String message = "";
		switch (numEmployeesAdded) {
		case 2:	// Employee Exists based on full name and age
			message = "<p style='color:blue; font-weight:bold;'>Empoyee \"" + fullname
				+ "\" exists already!<br> Please go to update employee if necessary.</p>";
			break;
		case 1:	// Employee Added Successfully
			message = "<p style='color:green; font-weight:bold;'>Empoyee \"" + fullname
				+ "\" is added sucessfully!</p>";
			break;
		case 0:
			message = "<p style='color:red; font-weight:bold;'>Unsuccessful! Empoyee is NOT added!</p>";
		break;

		default:
			message = "<p style='color:orange; font-weight:bold;'>Warning!!! <br>An unexpected number of records were updated ("
					+ numEmployeesAdded + ").<br>Please check the database.</p>";
			break;
		}
			
		// Display the form again along with the success or failure message.
		showForm(request, response, message);
	}
		
	/**
	 * A helper method to display the HTML form. This avoids code duplication
	 * between doGet and doPost.
	 * 
	 * @param request  The servlet request
	 * @param response The servlet response
	 * @param message  An optional message to display above the form (can be null).
	 * @throws IOException
	 */
	private void showForm(HttpServletRequest request, HttpServletResponse response, String message)
			throws IOException
	{
		/* Check if the user is logged in to be able to have access*/
		HttpSession session = request.getSession(false);
		
		if (session == null || session.getAttribute("username") == null) {
			response.sendRedirect("LoginPage");
			return;
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// Start writing the HTML
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\">");
		out.println("<title>Add New Employee Servlet</title>");
		
		out.println("	<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css'>");
		out.println("	<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script>");
		out.println("	<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js'></script>");
		
		out.println("	<style>");
		out.println("		table { border: 2px solid #ccc; width: 100%; height: 50%;}");
		out.println("		th { border: 1px solid black; padding: 8px;	text-align: center;}");
		out.println("		td { border: 1px solid black; padding: 8px;	text-align: left;}");
		out.println("		table input { width: 100%; box-sizing: border-box; padding: 5px;}");
		out.println("		body { display: grid; place-items: center; min-height: 100vh; margin: 0;}");
		out.println("		form { width: 600px; margin: 0 auto; padding: 20px;}");
		out.println("	</style>");	
		out.println("</head>");
		
		out.println("<body>");
		out.println("	<form method='post' action='AddNewEmployeeServlet'>");
		// --- Display the message if it exists ---
		if (message != null && !message.isEmpty()) {
			out.println(message);
		}
		out.println("		<table>");
		out.println("			<thead>");
		out.println("				<tr>");
		out.println("					<th colspan='2'>Enter new employee details</th>");
		out.println("				</tr>");
		out.println("			</thead>");
		// --- Get Employee Details From User ---
		out.println("			<tbody>");
		out.println("				<tr>");
		out.println("					<td>First Name:</td>");
		out.println("					<td><input type='text' name='firstname' placeholder='John' required></td>");
		out.println("				</tr>");
		out.println("				<tr>");
		out.println("					<td>Last Name:</td>");
		out.println("					<td><input type='text' name='lastname' placeholder='Smith' required></td>");
		out.println("				</tr>");
		out.println("				<tr>");
		out.println("					<td>Age (between 18 and 100):</td>");
		out.println("					<td><input type='number' name='age' min='18' max='100' required></td>");
		out.println("				</tr>");
		out.println("				<tr>");
		out.println("					<td>Salary:</td>");
		out.println("					<td><input type='number' name='salary' oninput='doubleValue()' step='0.01' min='0' required></td>");
		out.println("				</tr>");
		out.println("			</tbody>");
		// --- Submit Buttons ---
		out.println("			<tfoot>");
		out.println("				<tr>");
		out.println("					<th colspan='2'> <input type='submit' value='Add Employee'></th>");
		out.println("				</tr>");
		out.println("			</tfoot>");
		out.println("		</table>");
		// <%-- Go Back To Menu Link --%>
		out.println("<br>");
		out.println("<a href='OptionMenu' class='btn btn-primary'>Go Back to Menu</a>");
		out.println("	</form>");
		out.println("</body>");
		out.println("</html>");
	}
}
