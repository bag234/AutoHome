package org.bag.sensor;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bag.sqlite.*;

@WebServlet("/Sensor/Set")
public class SetSensor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private boolean AutoCreat;
	
    public SetSensor() {
        super();
        // TODO Auto-generated constructor stub
    }


	public void init(ServletConfig config) throws ServletException {
		System.out.println("[GetSensor:init]{Config:AutoCreat} " +config.getInitParameter("AutoCreat"));
		AutoCreat = Boolean.parseBoolean(config.getInitParameter("AutoCreat"));
	}

	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			DBTools dbTools = (DBTools)request.getAttribute("UsingDB");
			String Login = request.getParameter("Login");
			int Count = Integer.parseInt(request.getParameter("Count"));
			System.out.println("[GetSensor:doGet]{DEBUG}: DEBUG");
			if(Login != null && !dbTools.getTableSensor(Login).equals("NULL")) {
				if(dbTools.isFindTable(dbTools.getTableSensor(Login)))
				{
					System.out.println("[GetSensor:doGet]{DEBUG}: DEBUG");
					response.getWriter().append(org.bag.json.Coding.getDenie("OK", 0, "Add node").toString());
					dbTools.addNoteSensor(dbTools.getTableSensor(Login), LocalDate.now().toString(), Count);
				}
				else {
					if(AutoCreat) {
						response.getWriter().append(org.bag.json.Coding.getDenie("OK", 1, "Add node & Create Table").toString());
						dbTools.creatTableSensor(dbTools.getTableSensor(Login));
						dbTools.addNoteSensor(dbTools.getTableSensor(Login), LocalDate.now().toString(), Count);
					}
					else{
						response.getWriter().println(org.bag.json.Coding.getError("Error", 3, "Parametrs error(NoSuchName)".toString()).toString());
						System.out.println("[GetSensor:doGet]{Error:Name}: Parametrs error...");
					}
				}
				
				
			}
			else {
				System.out.println("[GetSensor:doGet]{DEBUG}: DEBUG:" + Login);
				throw new NullPointerException();
			}
		} catch (Exception e) {
			System.out.println("[GetSensor:doGet]{Error}: Crytical parametrs error...");
			response.getWriter().println(org.bag.json.Coding.getError("Error", 2, "Cryticle parametrs error".toString()).toString());
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
