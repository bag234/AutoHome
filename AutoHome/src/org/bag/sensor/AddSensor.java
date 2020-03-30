package org.bag.sensor;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bag.sqlite.DBTools;

/**
 * Servlet implementation class AddSensor
 */
@WebServlet("/AddSensor")
public class AddSensor extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private DBTools dbTools;
	
    public AddSensor() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String Login = (String) request.getParameter("Login");
		
		String Name =request.getParameter("Name");
		
		String Type = request.getParameter("Type");
		
		dbTools = (DBTools) request.getAttribute("UsingDB");
		
		if (Login != null && Name != null && Type != null) {
			
			
			try {
				if (!dbTools.getTableSensor(Login).equals("NULL")) {
					response.getWriter().println(org.bag.json.Coding.getError("Warning", 0, "Table is Created".toString()).toString());
					System.out.println("[AddSensor:doGet]{Warning}: Table is Created");
					return;
				}
				dbTools.addNodeOfSensor(Login, Name, Type);
				dbTools.creatTableSensor("ts_" + Login);
				response.getWriter().append(org.bag.json.Coding.getDenie("OK", 0, "Add node & Create Table").toString());
			} catch (SQLException e) {
				response.getWriter().println(org.bag.json.Coding.getError("Error", 2, "Crytical error db".toString()).toString());
				System.out.println("[AddSensor:doGet]{Error}: Crytical db error");
				e.printStackTrace();
			}
		}
		else {
			response.getWriter().println(org.bag.json.Coding.getError("Error", 3, "Parametrs error".toString()).toString());
			System.out.println("[AddSensor:doGet]{Error}: Parametrs error...");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
