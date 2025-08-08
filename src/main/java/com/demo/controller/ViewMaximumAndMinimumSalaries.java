package com.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.demo.service.EmployeeService;

/**
 * Servlet implementation class ViewMaximumAndMinimumSalaries
 */
@WebServlet("/ViewMaximumAndMinimumSalaries")
public class ViewMaximumAndMinimumSalaries extends HttpServlet {
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
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\"></script>");
		out.println("<head>");
		out.println("</head>");
		
		/*HTML Body*/
		out.println("<body>");
		out.println("<div class='container'>");
		out.println("<br>");
		
		// Print the results in an HTML table using the style 
		out.println("<table class='table table-bordered table-striped table-hover'>");
		
		try {
			// Send info to service layer					
			ResultSet rs = this.employeeService.maxAndMinSalaries();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			
			// print the table header
			out.println("<tr>");
			for(int i = 1; i <= columnCount; i++) {
				out.println("<th>");
				out.print( rsmd.getColumnLabel(i) );
				out.println("</th>");
			}
			out.println("</tr>");
			
			// print the results
			while(rs.next()) {
					out.println("<tr>");
					for(int i = 1; i <= columnCount; i++) {
						out.println("<td>");
						out.printf("$%,.2f", rs.getDouble(i));
						out.println("</td>");
					}
					out.println("</tr>");		
			}
		} catch(Exception e) {
			e.printStackTrace();
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
		out.println("</body>");
	}
}
