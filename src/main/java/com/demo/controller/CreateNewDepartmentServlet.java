package com.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.service.DepartmentService;

/**
 * Servlet implementation class CreateNewDepartmentServlet
 */
@WebServlet("/CreateNewDepartmentServlet")
public class CreateNewDepartmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DepartmentService departmentService = null;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		this.departmentService = new DepartmentService();
	}

	/**
	 * This method handles displaying the form
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Since doPost will now be able to display the form too,
		// we can just call our new helper method.
		getDepartmentNameHTMLForm(request, response, null);
	}

	/**
	 * This method handles processing the form submission
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve the department name from the form
		String departmentName = request.getParameter("departmentName");

		// The service method returns the number of records updated.
		// Typically, 1 means success and 0 means failure, 2 means exists already
		int numDepartmentsAdded = this.departmentService.CreateNewDepartment(departmentName);

		String message;

		switch (numDepartmentsAdded) {
		case 2: // Department Exists Already
			message = "<p style='color:blue; font-weight:bold;'>The department \"" + 
					departmentName + "\" exists in the department list.</p>";
			break;
		case 1: // SUCCESS: One record was updated.
			message = "<p style='color:green; font-weight:bold;'>Success! " +
					  "The department was added to the department list.</p>";
			break;
		case 0: // FAILURE: No records were updated.
			message = "<p style='color:red; font-weight:bold;'>Error! " +
					  "The department could not be added Or the input was invalid.</p>";
			break;
		default:
			message = "<p style='color:orange; font-weight:bold;'>Warning: " +
					  "An unexpected number of records were updated (" +
					  numDepartmentsAdded + "). Please check the database.</p>";
			break;
		}

		// Display the form again along with the success or failure message.
		getDepartmentNameHTMLForm(request, response, message);
	}

	/**
	 * A helper method to display the HTML form. This avoids code duplication
	 * between doGet and doPost.
	 * 
	 * @param The servlet request
	 * @param The servlet response
	 * @param An optional message to display above the HTML form (can be null).
	 * @throws IOException
	 */
	private void getDepartmentNameHTMLForm(
			HttpServletRequest request,
			HttpServletResponse response,
			String message)
			throws IOException 
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// Start writing the HTML
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\">");
		out.println("<title>Create A New Department</title>");
		out.println("</head>");
		out.println("<body>");

		// --- Display the message if it exists ---
		if (message != null && !message.isEmpty()) {
			out.println(message);
		}

		out.println("	<form method=\"post\" action=\"CreateNewDepartmentServlet\">");
		out.println("		<h3>Create A New Department:</h3>");
		out.println("		<table border='1'>");
		// --- Get Department Name From User ---
		out.println("			<tr>");
		out.println("				<td>Department Name:</td>");
		out.println("				<td><input type=\"text\" name=\"departmentName\" required></td>");
		out.println("			</tr>");
		// --- Submit Button ---
		out.println("			<tr>");
		out.println("				<td><input type=\"submit\" value=\"Add Department\"></td>");
		out.println("			</tr>");
		out.println("		</table>");
		out.println("	</form>");
		out.println("	<br>");
		out.println("	<br> To go back to option list:");
		out.println("	<a href='Menu.html'> Click here.</a>");
		out.println("</body>");
		out.println("</html>");
	}
}