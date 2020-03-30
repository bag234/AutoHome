package org.bag.sensor;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bag.sqlite.DBTools;

/**
 * Servlet implementation class VievSensor
 */
@WebServlet("/VievSensor")
public class VievSensor extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private String patchReDirect = "../GraphicViev.html";
    
    public VievSensor() {
        super();
        // TODO Auto-generated constructor stub
    }


	public void init(ServletConfig config) throws ServletException {
		
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DBTools dbTools = (DBTools) request.getAttribute("UsingDB");
		HttpServletRequest req = (HttpServletRequest) request;
        String Login = request.getParameter("Login");
        try {
			if(!dbTools.getNameSensor(Login).equals("NULL")) {        
				System.out.println("[VievSensor:doGet]: State ok");
				req.getRequestDispatcher(patchReDirect).forward(request, response);
			}
			else {
				response.getWriter().println("<h1>Error(Login)</h1>");
			}
		} catch (SQLException e) {
			response.getWriter().println(org.bag.json.Coding.getError("Error", 2, "Crytical error db".toString()).toString());
			System.out.println("[VievSensor:doGet]{Error}: Crytical db error");
			e.printStackTrace();
		} 
        
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
