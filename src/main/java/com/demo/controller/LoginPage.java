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
 * Servlet implementation class LoginPage
 */
@WebServlet("/LoginPage")
public class LoginPage extends HttpServlet {
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
		showLoginForm(request, response, null);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		String uname = request.getParameter("username");
		String pword = request.getParameter("password");

		String message;
		// TODO: Disable browser caching to avoid accessing visited pages after logout using the back button.
		// TODO: later this needs to be checked in the database
		if ("Admin".equals(uname) && "123".equals(pword)) {

			// Get a session , user name is unique
			HttpSession session = request.getSession(true);
			session.setAttribute("username", uname);

			response.sendRedirect("OptionMenu");
		} else {
			message = "<p style='color:red; font-weight:bold; text-align:center;'>Invalid username or password. Please try again.</p>";
			showLoginForm(request, response, message);
		}
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
	private void showLoginForm(HttpServletRequest request, HttpServletResponse response, String message)
			throws IOException 
	{	
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// Start writing the HTML
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset=\"UTF-8\">");
		out.println("<title>Login Page</title>");
		
		out.println("	<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css'>");
		out.println("	<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script>");
		out.println("	<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js'></script>");
		
		out.println("	<style>");
		out.println("		table { border: 2px solid #ccc; width: 100%; height: 50%;}");
		out.println("		th { width: 100%; border: 1px solid black; padding: 8px; text-align: center;}");
		out.println("		td { border: 1px solid black; padding: 8px;	text-align: left;}");
		out.println("		table td input { width: 100%; box-sizing: border-box; padding: 5px;}");
		out.println("		body { display: grid; place-items: center; min-height: 100vh; margin: 0;}");
		out.println("		form { width: 600px; margin: 0 auto; padding: 20px;}");
		out.println("		input { width: 100%;}");
		out.println("	</style>");	
		
		out.println("</head>");
		out.println("<body class='bg-gray-100 flex items-center justify-center min-h-screen'>");
		out.println("<form method='post' action='LoginPage'>");
		out.println("	<table");
		out.println("		<thead>");
		out.println("			<tr>");
		out.println("				<th colspan='2'><h2>Enter your login details</h2></th>");
		out.println("			</tr>");
		out.println("		</thead>");
		out.println("		<tbody>");
		out.println("			<tr>");
		out.println("				<td>User Name</td>");
		out.println("				<td><input type='text' name='username' placeholder='Enter User Name' required></td>");
		out.println("			</tr>");
		out.println("			<tr>");
		out.println("				<td>Password</td>");
		out.println("				<td><input type='password' name='password' placeholder='Enter Password' required></td>");
		out.println("			</tr>");
		out.println("		</tbody>");
		out.println("		</tfoot>");
		out.println("			<tr>");
		out.println("				<th colspan='2'><input type='submit' value='Login' required></th>");
		out.println("			</tr>");
		out.println("		</tfoot>");
		out.println("	</table>");
		
		// --- Display the message if it exists ---
		if (message != null && !message.isEmpty()) {
			out.println(message);
		}
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
		
		/* Disable browser back button cache for sensitive information */
    	response.setHeader("cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    	response.setHeader("Pragma", "no-cache");	// HTTP 1.0
    	response.setHeader("Expires", "0");		// Proxy
	}
}
