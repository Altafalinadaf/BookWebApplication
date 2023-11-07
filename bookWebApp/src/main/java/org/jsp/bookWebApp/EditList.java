package org.jsp.bookWebApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editScreen")
public class EditList extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id")); // this is parameter id (book list)
		String qry = "select bname,bedit,bprice from book where bId=?";

		PrintWriter out = resp.getWriter();
		resp.setContentType("text/html");

		Connection con = null;
		PreparedStatement ps = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookdata", "root", "admin");
			ps = con.prepareStatement(qry);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			rs.next(); // ?id=

		//	out.println("<h1 style='color:red' align='center'> Book List</h1>");
			out.println("<form action='editUrl?id="+id+"' method='post'>");
			out.println("<h1 class=\'bg-danger text-center text-white\'> Edit Book Details </h1>");
			out.println("<link rel='stylesheet' href='css/bootstrap.css'>");
			out.println("<table align='center'>");
			out.println("<tr>");
			out.println("<td>Book Name</td>");
			out.println("<td><input type='text' name='BookName' value='"+rs.getString(1)+"'</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td>BookEdition</td>");
			out.println("<td> <input type='text' name='BookEdition' value='"+rs.getString(2)+"'</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td>BookPrice</td>");
			out.println("<td> <input type='text' name='BookPrice' value='"+rs.getString(3)+"'</td>");

			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td><input type='submit' value='edit' /></td>");
			out.println("<td><input type='reset' value='cancel'/></td>");
			out.println("</tr>");
			out.println("</from>");
			
			

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			out.print("<h2 style='color:red'>exception occure "+e.getMessage()+"</h2>");
		}
		finally {
			try {
				if (con!=null && ps!=null && out!=null ) {
					con.close();
					ps.close();
					out.close();	
				}
			}
			catch (Exception e) {
				out.print("<h2 style='color:red'>exception occure "+e.getMessage()+"</h2>");
			}
		}
	}

}
