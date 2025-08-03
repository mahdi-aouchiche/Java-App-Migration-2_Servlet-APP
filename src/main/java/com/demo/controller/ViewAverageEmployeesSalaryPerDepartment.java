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

import com.demo.model.Department;
import com.demo.service.DepartmentService;

/**
 * Servlet implementation class AverageEmployeesSalaryPerDepartment
 */
@WebServlet("/ViewAverageEmployeesSalaryPerDepartment")
public class ViewAverageEmployeesSalaryPerDepartment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DepartmentService departmentService = null;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		this.departmentService = new DepartmentService();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		/* HTML Head */
		out.println("<head>");
		out.println(
				"<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>");
		out.println("<head>");
		out.println("</head>");

		/* HTML Body */
		out.println("<body>");
		out.println("<div class='container'>");
		out.println("<br>");

		// Print the results in an HTML table using the style
		out.println("<table class='table table-bordered table-striped table-hover'>");

		// Send info to service layer
		List<String> columnLabel = new ArrayList<String>();
		LinkedHashMap<Department, Double> records = new LinkedHashMap<>();

		this.departmentService.averageSalaryByDepartment(columnLabel, records);

		// print the table header
		out.println("<tr>");
		for (String columnName : columnLabel) {
			out.printf("<th>%s</th>", columnName);
		}
		out.println("</tr>");

		// print the results
		for (Map.Entry<Department, Double> entry : records.entrySet()) {
			out.println("<tr>");
			out.printf("<td>%d</td>", entry.getKey().getDepartmentId());
			out.printf("<td>%s</td>", entry.getKey().getDepartmentName());
			out.printf("<td>$%,.2f</td>", entry.getValue());
			out.println("</tr>");
		}

		out.println("</table>");
		out.println("<br>");
		out.println("To go back to option list: ");
		out.println("<a href='Menu.html'>Click here.</a>");
		out.println("</div>");
		out.println("</body>");
	}
}
