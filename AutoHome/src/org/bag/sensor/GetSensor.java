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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class GetSensor
 */
@WebServlet("/GetSensor")
public class GetSensor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private DBTools dbTools;
    
    int MaximalDeap;

    public GetSensor() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		System.out.println("[GetSensor:init]{Config:MximalDeap} " +config.getInitParameter("MaximalDeap"));
        MaximalDeap = Integer.parseInt(config.getInitParameter("MaximalDeap"));
	}

	private JSONArray getListSensor(int Count, String Table) throws SQLException {
		JSONArray jsona = new JSONArray();
		for (SensorCount sc : dbTools.getSensorNode(Table, Count)) {
			jsona.put(sc.toJSONO());
		}
		return jsona;
	}
	
	private int tryParseInt(String str) {
		try {
	        return Integer.parseInt(str);
	    } catch (NumberFormatException e) {
	        return 0;
	    }
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dbTools = (DBTools) request.getAttribute("UsingDB");

        String Login = request.getParameter("Login");

        String Count = request.getParameter("Count"); //NEW, ALL, Null, (Number)
        System.out.println("[GetSensor:doGet]{DEBUG}: Debug:" + Login);
        System.out.println("[GetSensor:doGet]{DEBUG}: Debug:" + request.getServletPath());
        try {
                if(Login != null && !dbTools.getTableSensor(Login).equals("NULL")) {
                	JSONObject jsono = new JSONObject();
                	
                	jsono.put("Name", dbTools.getNameSensor(Login));
                	System.out.println("[GetSensor:doGet]{DEBUG}: Debug");
                        if(Count == null) {
                        		jsono.put("Data", getListSensor(10, dbTools.getTableSensor(Login)));
                                response.getWriter().println(jsono.toString());
                                return;
                        }
                        else if(Count.equals("NEW")) {
                        		jsono.put("Data", getListSensor(1, dbTools.getTableSensor(Login)));
                                response.getWriter().println(jsono.toString());
                                return;
                        }
                        else if(Count.equals("ALL")) {
                        		jsono.put("Data", getListSensor(MaximalDeap, dbTools.getTableSensor(Login)));
                                response.getWriter().println(jsono.toString());
                                return;
                        }
                        else if(tryParseInt(Count) != 0) {
                        	jsono.put("Data", getListSensor(tryParseInt(Count), dbTools.getTableSensor(Login)));
                        }
                        else {
                        	response.getWriter().println(org.bag.json.Coding.getError("Error", 2, "Error Parametrs".toString()).toString());
                        }

                }
        } catch (SQLException e) {
                response.getWriter().println(org.bag.json.Coding.getError("Error", 2, "Crytical error db".toString()).toString());
                System.out.println("[GetSensor:doGet]{Error}: Crytical db error");
                e.printStackTrace();
        }


		response.getWriter().append(org.bag.json.Coding.getError("Error", 1, "Error Parametrs").toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
