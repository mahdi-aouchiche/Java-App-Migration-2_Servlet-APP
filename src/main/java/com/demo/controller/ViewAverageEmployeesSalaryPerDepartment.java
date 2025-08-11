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
			throws ServletException, IOException 
	{	
		/* Check if the user is logged in to be able to have access*/
		HttpSession session = request.getSession(false);
		
		if (session == null || session.getAttribute("username") == null) {
			response.sendRedirect("LoginPage");
			return;
		}	
		
		// get info from service layer
		List<String> columnLabel = new ArrayList<String>();
		LinkedHashMap<Department, Double> records = new LinkedHashMap<>();
		this.departmentService.averageSalaryByDepartment(columnLabel, records);
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		/* HTML Head */
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		
		out.println("<head>");
		out.println("	<meta charset=\"UTF-8\">");
		out.println("	<title>Average Salary per Department</title>");
		
		out.println("	<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css'>");
		out.println("	<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script>");
		out.println("	<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js'></script>");
		out.println("	<style>");
		out.println("		table th, td { text-align: center; }");
		out.println("	</style>");
		out.println("</head>");

		/* HTML Body */
		out.println("<body>");
		out.println("	<div class='container'>");
		out.println("	<br>");
		out.println("	<table class='table table-bordered table-striped table-hover'>");
		out.println("		<thead>");
		out.println("			<tr>");
		for (String columnName : columnLabel) {
			out.printf("			<th>%s</th>", columnName);
		}
		out.println("			</tr>");
		out.println("		</thead>");
		out.println("		<tbody>");
		for (Map.Entry<Department, Double> entry : records.entrySet()) {
			out.println("		<tr>");
			out.printf("			<td>%d</td>", entry.getKey().getDepartmentId());
			out.printf("			<td>%s</td>", entry.getKey().getDepartmentName());
			out.printf("			<td>$%,.2f</td>", entry.getValue());
			out.println("		</tr>");
		}
		out.println("		</tbody>");
		out.println("	</table>");
		
		//Go Back To Menu Link
		out.println("	<br>");
		out.println("	<a href='OptionMenu' class='btn btn-primary'>Go Back to Menu</a>");
		out.println("	</div>");
		out.println("	<hr>");
		out.println("</body>");
	}
}
