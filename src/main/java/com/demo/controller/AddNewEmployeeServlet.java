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
				+ "\" exists already! Please go to update employee if necessary.</p>";
			break;
		case 1:	// Employee Added Successfully
			message = "<p style='color:green; font-weight:bold;'>Empoyee \"" + fullname
				+ "\" is added sucessfully!</p>";
			break;
		case 0:
			message = "<p style='color:red; font-weight:bold;'>Unsuccessful! Empoyee is NOT added!</p>";
		break;

		default:
			message = "<p style='color:orange; font-weight:bold;'>Warning: An unexpected number of records were updated ("
					+ numEmployeesAdded + "). Please check the database.</p>";
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
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// Start writing the HTML
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\">");
		out.println("<title>Add New Employee Servlet</title>");
		out.println("</head>");
		out.println("<body>");

		// --- Display the message if it exists ---
		if (message != null && !message.isEmpty()) {
			out.println(message);
		}
		
		out.println("	<form method=\"post\" action=\"AddNewEmployeeServlet\">");
		out.println("		<h3>Enter new employee details:</h3>");
		out.println("		<table border='1'>");
		
		// --- Get Employee Details From User ---
		out.println("			<tr>");
		out.println("				<td>First Name:</td>");
		out.println("				<td><input type=\"text\" name=\"firstname\" required></td>");
		out.println("			</tr>");
		out.println("			<tr>");
		out.println("				<td>Last Name:</td>");
		out.println("				<td><input type=\"text\" name=\"lastname\" required></td>");
		out.println("			</tr>");
		out.println("			<tr>");
		out.println("				<td>Age (between 18 and 100):</td>");
		out.println("				<td><input type=\"number\" name=\"age\" min=\"18\" max=\"100\" required></td>");
		out.println("			</tr>");
		out.println("			<tr>");
		out.println("				<td>Salary:</td>");
		out.println("				<td><input type=\"number\" name=\"salary\" oninput=\"doubleValue()\" step=\"0.01\" required></td>");
		out.println("			</tr>");
		
		// --- Submit Buttons ---
		out.println("			<tr>");
		out.println("				<td><input type=\"submit\" value=\"Add Employee\"></td>");
		out.println("			</tr>");
		
		out.println("		</table>");
		out.println("	</form>");
		out.println("	<br>");
		//	<!-- Go Back To Menu -->
		out.println("	<a href='OptionMenu'"); 
		out.println("   	class='w-full sm:w-auto text-center px-6 py-3 bg-slate-200 text-slate-800 font-semibold"
							+ " rounded-lg hover:bg-slate-300 focus:outline-none focus:ring-2 "
							+ "focus:ring-slate-400 focus:ring-opacity-75 transition-all duration-200'>");
		out.println("    	Go Back to Menu");
		out.println("	</a>");

		out.println("</body>");
		out.println("</html>");
	}
}
