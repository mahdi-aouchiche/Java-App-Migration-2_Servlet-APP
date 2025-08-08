package com.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class OptionMenu
 */
@WebServlet("/OptionMenu")
public class OptionMenu extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/** This method handles displaying the form
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
    	        
		optionMenu(request, response);
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{		
		/* Check if the user is logged in to be able to have access*/
		HttpSession session = request.getSession(false);
		
		if (session == null || session.getAttribute("username") == null) {
			response.sendRedirect("LoginPage");
			return;
		}	
		
		optionMenu(request, response);
	}
	
	/**
	 * Display the options menu 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void optionMenu( HttpServletRequest request, HttpServletResponse response)
			throws IOException 
	{	    	
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		
		out.println("<!DOCTYPE html>");
		out.println("<html lang='en'>");
		out.println("<head>");
		out.println("	<meta charset='UTF-8'>");
		out.println("	<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
		out.println("   <title>Employee Management Menu</title>");
					//	<!-- Tailwind CSS for styling -->
		out.println("   <script src='https://cdn.tailwindcss.com'></script>");
		    		// <!-- Google Fonts: Inter -->
		out.println("   <link rel='preconnect' href='https://fonts.googleapis.com'>");
		out.println("   <link rel='preconnect' href='https://fonts.gstatic.com' crossorigin>");
		out.println("   <link href='https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap' rel='stylesheet'>");
		out.println("   <style>");
		        	/* Use the Inter font family */
		out.println("		body {");
		out.println("       	font-family: 'Inter', sans-serif;");
		out.println("        }");
		out.println("    </style>");
		
		out.println("</head>");
		out.println("<body class='bg-gray-100 flex items-center justify-center min-h-screen'>");

		    		// <!-- Main Menu Container -->
		out.println("    <div class='bg-white rounded-xl shadow-2xl p-8 max-w-4xl w-full m-4'>");
		out.println("        <h1 class='text-3xl font-bold text-gray-800 mb-4 text-center'>Employee Management System</h1>");
		out.println("        <h2 class='text-xl font-medium text-gray-600 mb-8 text-center'>Select an option:</h2>");
		        
		        	// <!-- Menu Buttons Grid -->
		out.println("        <nav>");
		out.println("            <div class='grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4'>");
		            
					//  <!-- View Actions -->
		out.println("               <a href='ViewEmployeesDetailsAffiliatedToADepartment' class='text-center p-4 rounded-lg bg-blue-500 text-white font-semibold shadow-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:ring-opacity-75 transition-all duration-200 transform hover:-translate-y-1'>View Affiliated Employees</a>");
		out.println("               <a href='ViewEmployeesDetailsNotAffiliatedToADepartment' class='text-center p-4 rounded-lg bg-blue-500 text-white font-semibold shadow-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:ring-opacity-75 transition-all duration-200 transform hover:-translate-y-1'>View Unaffiliated Employees</a>");
		out.println("               <a href='ViewNumberOfEmployeesInEachDepartment' class='text-center p-4 rounded-lg bg-blue-500 text-white font-semibold shadow-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:ring-opacity-75 transition-all duration-200 transform hover:-translate-y-1'>View Employee Counts</a>");
		out.println("               <a href='ViewAverageSalaryOfAllEmployees' class='text-center p-4 rounded-lg bg-blue-500 text-white font-semibold shadow-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:ring-opacity-75 transition-all duration-200 transform hover:-translate-y-1'>View Average Salary</a>");
		out.println("               <a href='ViewMaximumAndMinimumSalaries' class='text-center p-4 rounded-lg bg-blue-500 text-white font-semibold shadow-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:ring-opacity-75 transition-all duration-200 transform hover:-translate-y-1'>View Min/Max Salaries</a>");
		out.println("               <a href='ViewSecondMaximumSalary' class='text-center p-4 rounded-lg bg-blue-500 text-white font-semibold shadow-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:ring-opacity-75 transition-all duration-200 transform hover:-translate-y-1'>View 2nd Highest Salary</a>");
		out.println("               <a href='ViewAverageEmployeesSalaryPerDepartment' class='text-center p-4 rounded-lg bg-blue-500 text-white font-semibold shadow-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:ring-opacity-75 transition-all duration-200 transform hover:-translate-y-1'>View Avg Salary by Dept</a>");
		out.println("               <a href='ViewEmployeesEarningSecondMaximumSalary' class='text-center p-4 rounded-lg bg-blue-500 text-white font-semibold shadow-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:ring-opacity-75 transition-all duration-200 transform hover:-translate-y-1'>View Earners of 2nd Highest Salary</a>");
		out.println("               <a href='ViewDepartmentsWithAtLeastAGivenNumberOfEmployees' class='text-center p-4 rounded-lg bg-blue-500 text-white font-semibold shadow-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:ring-opacity-75 transition-all duration-200 transform hover:-translate-y-1'>View Depts by Employee Count</a>");    
		            
					// <!-- Create Actions -->
		out.println("               <a href='AddNewEmployeeServlet' class='text-center p-4 rounded-lg bg-green-500 text-white font-semibold shadow-md hover:bg-green-600 focus:outline-none focus:ring-2 focus:ring-green-400 focus:ring-opacity-75 transition-all duration-200 transform hover:-translate-y-1'>Add New Employee</a>");
		out.println("               <a href='CreateNewDepartmentServlet' class='text-center p-4 rounded-lg bg-green-500 text-white font-semibold shadow-md hover:bg-green-600 focus:outline-none focus:ring-2 focus:ring-green-400 focus:ring-opacity-75 transition-all duration-200 transform hover:-translate-y-1'>Create New Department</a>");              
		            
					//  <!-- Modify Actions -->
		out.println("               <a href='AddEmployeeToDepartmentServlet' class='text-center p-4 rounded-lg bg-yellow-500 text-white font-semibold shadow-md hover:bg-yellow-600 focus:outline-none focus:ring-2 focus:ring-yellow-400 focus:ring-opacity-75 transition-all duration-200 transform hover:-translate-y-1'>Assign Employee to Dept</a>");
					
					//  <!-- Logout -->
		out.println("	 			<br>");
		out.println("    			<a href='Logout' class='text-center p-4 rounded-lg bg-red-500 text-white font-semibold shadow-md hover:bg-yellow-600 focus:outline-none focus:ring-2 focus:ring-yellow-400 focus:ring-opacity-75 transition-all duration-200 transform hover:-translate-y-1'>Logout</a>");
		
		out.println("            </div>");
		out.println("        </nav>");
		out.println("    </div>");		
		out.println("</body>");
		out.println("</html>");
	}
}
