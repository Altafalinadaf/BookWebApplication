package org.jsp.bookWebApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editUrl")

public class EditData extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//String qry="update book set bname=?,bedit=?,bprice=? where bId=?";
		
		int id = Integer.parseInt(req.getParameter("id"));
		String bname=req.getParameter("BookName");
		String bedit=req.getParameter("BookEdition");
		float bprice=Float.parseFloat(req.getParameter("BookPrice"));
	
		PrintWriter out=resp.getWriter();
		Connection con=null;
		PreparedStatement ps=null;
		
		resp.setContentType("text/html");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bookdata","root","admin");
			ps=con.prepareStatement("update book set bname=?,bedit=?,bprice=? where bId=?");
			ps.setString(1, bname);
			ps.setString(2, bedit);
			ps.setFloat(3, bprice);
			ps.setInt(4, id);
			int r=ps.executeUpdate();
			RequestDispatcher rd=null;
			resp.setContentType("text/html");

			
			if(r>0) {
				
				out.print("<h1 style='color:green'>Value Successfully modified </h1>");
				rd=req.getRequestDispatcher("list");
				rd.include(req, resp);
			}
			else {
				
				out.print("<h1 style='color:red'>not updated </h1>");
				rd=req.getRequestDispatcher("list");
				rd.include(req, resp);
			}
		}
		catch (Exception e) {
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
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doGet(req, resp);
	}
}
