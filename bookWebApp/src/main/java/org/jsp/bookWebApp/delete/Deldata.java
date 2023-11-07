package org.jsp.bookWebApp.delete;

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

@WebServlet("/deleteScreen")
public class Deldata extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		String qry = "delete from book where bId=?";

		RequestDispatcher rd = null;
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookdata", "root", "admin");
			ps = con.prepareStatement(qry);
			ps.setInt(1, id);

			int r = ps.executeUpdate();
			if (r > 0) {

				out.println("<h1 style='color:green'>Recourd is successfully Deleted</h1>");
				rd = req.getRequestDispatcher("list");
				rd.include(req, resp);
			} else {
				out.print("<h1 style='color:green'>Recourd is not successfully Deleted</h1>");
			}

			
//			out.println("<a href='list'>BookList</a>");
//			out.println("<a href='home.html'>Home</a>");

		}

		catch (Exception e) {
			// TODO: handle exception
			out.print("<h1 style='color:red'>" + e.getMessage() + "</h1>");
		} finally {
			try {
				if (con != null && ps != null && out != null) {
					con.close();
					ps.close();
					out.close();
				}
			} catch (Exception e) {
				out.print("<h2 style='color:red'>exception occure " + e.getMessage() + "</h2>");
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

}
