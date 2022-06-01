package com.login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Servlet implementation class PatientLogin
 */
@WebServlet(urlPatterns="/Plogin")
public class PatientLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PatientLogin() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		String driverName = "com.mysql.jdbc.Driver";
		String connectionUrl = "jdbc:mysql://localhost:3306/";
		String dbName = "hospital_database";
		String userId = "root";
		String password = "krishna@123";
		String pwdd=null;
		String name=request.getParameter("uname");
		System.out.println(name);
	
		String psswd=request.getParameter("pwd");
		System.out.println(psswd);
		

			Connection connection = null;
			Statement statement = null;
			ResultSet resultSet = null;
			try{ 
				connection = DriverManager.getConnection(connectionUrl+dbName, userId, password);
				System.out.println("conn");
				statement=connection.createStatement();
				String sql ="SELECT password FROM patients_info where name="+name;

				resultSet = statement.executeQuery(sql);
				System.out.println("query");
				while(resultSet.next()) {
				pwdd=resultSet.getString("password");
				}
		if(psswd.equalsIgnoreCase(pwdd)) {
			session.setAttribute("Name",name);
			request.getRequestDispatcher("Patient_Page.jsp").forward(request, response);
		}
	
		else {
			request.setAttribute("error", "Invalid username/password.");
			request.getRequestDispatcher("Patient_Login.jsp").forward(request, response);
	}
	}catch(SQLException e) { 
		System.out.println("kk");
		e.printStackTrace();
	}
			}}
