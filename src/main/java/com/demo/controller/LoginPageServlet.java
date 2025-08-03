package com.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginPageServlet
 */
@WebServlet("/LoginPageServlet")
public class LoginPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/** This method handles displaying the form
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Since doPost will now be able to display the form too,
        // we can just call our new helper method.
        showLoginForm(request, response, null);
    }
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {			
		String uname = request.getParameter("username");
		String pword = request.getParameter("password"); 
			
		String message;
		
		// TODO: later this needs to be checked in the database 
		if("Admin".equals(uname) && "123".equals(pword)) {
			RequestDispatcher rd = request.getRequestDispatcher("/Menu.html");
			rd.forward(request, response);
				
		} else {
			message = "<p style='color:red; font-weight:bold;'>Invalid User Name or Password.</p>";
			showLoginForm(request, response, message);
		}	
	}
	
	  /**
     * A helper method to display the HTML form. This avoids code duplication
     * between doGet and doPost.
     * @param request The servlet request
     * @param response The servlet response
     * @param message An optional message to display above the form (can be null).
     * @throws IOException
     */
    private void showLoginForm(HttpServletRequest request, HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Start writing the HTML
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<title>Login Page</title>");
        out.println("</head>");
        out.println("<body>");

        // --- Display the message if it exists ---
        if (message != null && !message.isEmpty()) {
            out.println(message);
        }
        
        out.println("<form method=\"post\" action=\"LoginPageServlet\">");

    	out.println("	<h2>Enter your login details</h2>");
    	out.println("	<table border='1'>");
    	out.println("		<tr>");
    	out.println("			<td>User Name:</td>");
    	out.println("			<td><input type=\"text\" name=\"username\" required></td>");
    	out.println("		</tr>");
    	out.println("		<tr>");
    	out.println("			<td>Password</td>");
    	out.println("			<td><input type=\"password\" name=\"password\" required></td>");
    	out.println("		</tr>");
    	out.println("		<tr>");
    	out.println("			<td><input type=\"submit\" value=\"Login\" required></td>");
    	out.println("		</tr>");
    	out.println("	</table>");
    	out.println("</form>");
    	out.println("</body>");
    	out.println("</html>");
    }
}
