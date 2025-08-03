package com.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.model.Department;
import com.demo.model.Employee;
import com.demo.service.DepartmentService;
import com.demo.service.EmployeeService;

/**
 * Servlet implementation class AddEmployeeToDepartmentServlet
 */
@WebServlet("/AddEmployeeToDepartmentServlet")
public class AddEmployeeToDepartmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DepartmentService departmentService = null;
	EmployeeService employeeService = null;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		this.departmentService = new DepartmentService();
		this.employeeService = new EmployeeService();
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
		addEmployeeToDepartmentForm(request, response, null);
	}

	/**
	 * This method handles processing the form submission
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve the selected IDs from the form
		String departmentId = request.getParameter("departmentId");
		String employeeId = request.getParameter("employeeId");

		// The service method returns the number of records updated.
		// Typically, 1 means success and 0 means failure.
		int numUpdatedRecords = departmentService.addEmployeeToDepartment(departmentId, employeeId);

		String message;

		switch (numUpdatedRecords) {
		case 2: // SUCCESS: Record exists already.
			message = "<p style='color:blue; font-weight:bold;'>Attention! The employee is already associated with the department.</p>";
			break;
		case 1: // SUCCESS: One record was updated.
			message = "<p style='color:green; font-weight:bold;'>Success! The employee was added to the department.</p>";
			break;
		case 0: // FAILURE: No records were updated.
			message = "<p style='color:red; font-weight:bold;'>Error! The employee could not be added. Innput is invalid.</p>";
			break;
		default:
			message = "<p style='color:orange; font-weight:bold;'>Warning: An unexpected number of records were updated ("
					+ numUpdatedRecords + "). Please check the database.</p>";
			break;
		}

		// Display the form again along with the success or failure message.
		addEmployeeToDepartmentForm(request, response, message);
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
	private void addEmployeeToDepartmentForm(HttpServletRequest request, HttpServletResponse response, String message)
			throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// Start writing the HTML
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\">");
		out.println("<title>Add An Existing Employee to a Department</title>");
		out.println("</head>");
		out.println("<body>");

		// --- Display the message if it exists ---
		if (message != null && !message.isEmpty()) {
			out.println(message);
		}

		out.println("	<form method=\"post\" action=\"AddEmployeeToDepartmentServlet\">");
		out.println("		<h3>Select Employee and Department:</h3>");
		out.println("		<table border='1'>");

		// --- Department List Drop down ---
		out.println("			<tr>");
		out.println("				<td>Department Name:</td>");
		out.println("				<td>");
		out.println("					<select name=\"departmentId\" required>");
		out.println("						<option value=\"\" disabled selected>-- Select a Department --</option>");

		// Fetch and display departments
		List<Department> departments = this.departmentService.getDepartmentList();
		for (Department dept : departments) {
			out.println("						<option value=\"" + dept.getDepartmentId() + "\">"
					+ dept.getDepartmentName() + "</option>");
		}
		out.println("					</select>");
		out.println("				</td>");
		out.println("			</tr>");

		// --- Employee List Drop down ---
		out.println("			<tr>");
		out.println("				<td>Employee Name:</td>");
		out.println("				<td>");
		out.println("					<select name=\"employeeId\" required>");
		out.println("						<option value=\"\" disabled selected>-- Select an Employee --</option>");

		// Fetch and display employees
		List<Employee> employees = this.employeeService.getEmployeeList();
		for (Employee emp : employees) {
			out.println("						<option value=\"" + emp.getEmployeeId() + "\">" + emp.getEmployeeName()
					+ "</option>");
		}
		out.println("					</select>");
		out.println("				</td>");
		out.println("			</tr>");

		out.println("			<tr>");
		out.println("				<td colspan='2'><input type=\"submit\" value=\"Add Employee to Department\"></td>");
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
