package com.ram.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.*; 
import javax.servlet.http.*; 

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uemail=request.getParameter("username");
		String upwd=request.getParameter("password");
		HttpSession session=request.getSession();  
	    RequestDispatcher dispatcher=null;
	    
	    if(uemail==null|| uemail.equals(" "))
	    {
	    	request.setAttribute("status","invalidEmail");
			dispatcher=request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
	    }
	    
	    if(upwd==null|| upwd.equals(" "))
	    {
	    	request.setAttribute("status","invalidUpwd");
			dispatcher=request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
	    }
	    
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/sample?useSSL=false","root","2001");
			PreparedStatement pst=con.prepareStatement("select * from users where uemail=? and upwd=?");
			pst.setString(1, uemail);
			pst.setString(2, upwd);
            ResultSet rs=pst.executeQuery();
			if(rs.next()) {
				session.setAttribute("name",rs.getString("uname"));
				dispatcher=request.getRequestDispatcher("index.jsp");
				
			}else {
				request.setAttribute("status", "failed");
				dispatcher=request.getRequestDispatcher("login.jsp");
			}
			dispatcher.forward(request, response);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}

