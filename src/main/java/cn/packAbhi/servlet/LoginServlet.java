package cn.packAbhi.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import cn.packAbhi.connection.DbCon;
import cn.packAbhi.dao.UserDao;
import cn.packAbhi.model.User;

@WebServlet("/user-login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("login.jsp");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		try(PrintWriter out=response.getWriter()){
			String email=request.getParameter("login-email");
			String password=request.getParameter("login-password");
			
			try {
				UserDao udao=new UserDao(DbCon.getConnection());
				User user=udao.userLogin(email,password);
				if(user!=null)
				{
					request.getSession().setAttribute("auth", user);
					response.sendRedirect("index.jsp");
				}
				else {
					out.print("user login failed");
				}
			}
			catch(ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
