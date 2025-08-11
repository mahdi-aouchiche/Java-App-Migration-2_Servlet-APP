package com.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.demo.service.EmployeeService;

/**
 * Servlet implementation class ViewSecondMaximumSalary
 */
@WebServlet("/ViewSecondMaximumSalary")
public class ViewSecondMaximumSalary extends HttpServlet {
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
		out.println("	<title>Second Highest Salary</title>");
		
		out.println("	<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css'>");
		out.println("	<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script>");
		out.println("	<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js'></script>");
		out.println("</head>");
		
		/* HTML Body */
		out.println("<body>");
		out.println("	<div class='container'>");
		out.println("	<br>");
		
		// Print the results in an HTML table using the style 
		out.println("		<table class='table table-bordered table-striped table-hover'>");
		out.printf("			<th style='text-align:center'>%s</th>", "Employees Second Highest Salary");
		out.println("			<tr>");
		out.printf("				<td style='text-align:center'>$%,.2f</td>", this.employeeService.secondMaxEmployeesSalary());
		out.println("			</tr>");	
		out.println("		</table>");
		
		// <%-- Go Back To Menu Link --%>
		out.println("		<br>");
		out.println("		<a href='OptionMenu' class='btn btn-primary'>Go Back to Menu</a>");
		out.println("	</div>");
		out.println("</body>");
	}
}