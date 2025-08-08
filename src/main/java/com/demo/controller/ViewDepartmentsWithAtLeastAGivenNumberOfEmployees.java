package com.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.demo.model.Department;
import com.demo.service.DepartmentService;

/**
 * Servlet implementation class
 * ViewDepartmentsWithAtLeastAGivenNumberOfEmployees
 */
@WebServlet("/ViewDepartmentsWithAtLeastAGivenNumberOfEmployees")
public class ViewDepartmentsWithAtLeastAGivenNumberOfEmployees extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DepartmentService departmentService = null;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		this.departmentService = new DepartmentService();
	}

	/**
	 * This method handles displaying the form to get the minimum number of employees per department
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
		this.getNumberOfEmployees(request, response);
	}

	/**
	 * This method handles processing the form submission
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
		int numEmployees = 0;
		List<String> columnLabel = new ArrayList<String>();
		LinkedHashMap<Department, Integer> records = new LinkedHashMap<>();

		response.setContentType("text/html");
		/* HTML Head */
		out.println("<head>");
		out.println(
				"<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>");
		out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>");
		out.println("<style>");
		out.println(".edit-form-row { display: none; }");
		out.println(".edit-form-row td { padding: 0; }");
		out.println(".edit-form-row form { margin: 0; padding: 8px; border: none; }");
		out.println(".edit-form-row input { display: inline-block; width: auto; margin-right: 5px; }");
		out.println(".edit-form-row button { padding: 5px 10px; }");

		out.println(".action-icons {display: flex; justify-content: space-around; align-items: center;}");
		out.println(".action-icons img {margin: 0 5px; cursor: pointer;}");
		out.println("</style>");
		out.println("</head>");

		/* HTML Body */
		out.println("<body>");
		out.println("<div class='container'>");
		out.println("<br>");
		
		/* Print the results in an HTML table using the style */
		out.println("<table class='table table-bordered table-striped table-hover'>");

		/* Get the least number of employees */
		numEmployees = Integer.parseInt(request.getParameter("numEmployees"));

		/* Send info to service layer */
		this.departmentService.listOfDepartmentsWithAtLeastAcertainNumberOFEmployees(columnLabel, records, numEmployees);

		// print the table header
		out.println("<tr>");
		for (String columnName : columnLabel) {
			out.printf("<th>%s</th>", columnName);
		}
		out.println("</tr>");

		// print the results
		for (Map.Entry<Department, Integer> entry : records.entrySet()) {
			out.println("<tr>");
			out.printf("<td>%d</td>", entry.getKey().getDepartmentId());
			out.printf("<td>%s</td>", entry.getKey().getDepartmentName());
			out.printf("<td>%d</td>", entry.getValue());
			out.println("</tr>");
		}

		out.println("</table>");
		out.println("<br>");
		//	<!-- Go Back To Menu -->
		out.println("	<a href='OptionMenu'"); 
		out.println("   	class='w-full sm:w-auto text-center px-6 py-3 bg-slate-200 text-slate-800 font-semibold"
							+ " rounded-lg hover:bg-slate-300 focus:outline-none focus:ring-2 "
							+ "focus:ring-slate-400 focus:ring-opacity-75 transition-all duration-200'>");
		out.println("    	Go Back to Menu");
		out.println("	</a>");

		out.println("</div>");
		out.println("<hr>");
		out.println("</body>");
	}

	/**
	 * A helper method to get the departments with at least a certain number of employees.
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void getNumberOfEmployees(HttpServletRequest request, HttpServletResponse response)
			throws IOException
	{
		/* Disable browser back button cache for sensitive information */
    	response.setHeader("cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    	response.setHeader("Pragma", "no-cache");	// HTTP 1.0
    	response.setHeader("Expires", "0");		// Proxy
    	
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\">");
		out.println("<title>Get Minimum Number Of Employee</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("	<form method=\"post\" action=\"ViewDepartmentsWithAtLeastAGivenNumberOfEmployees\">");
		out.println("		<h3>Enter the least number of employees per department:</h3>");
		out.println("		<table border='1'>");
		out.println("			<tr>");
		out.println("				<td>Number of employees</td>");
		out.println("				<td><input type=\"number\" name=\"numEmployees\" min=\"0\" required></td>");
		out.println("			</tr>");
		out.println("			<tr>");
		out.println("				<td><input type=\"submit\" value=\"Enter\" required></td>");
		out.println("			</tr>");
		out.println("		</table>");
		out.println("	</form>");
		out.println("<br>");
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
