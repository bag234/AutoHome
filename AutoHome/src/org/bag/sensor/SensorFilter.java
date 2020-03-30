package org.bag.sensor;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.bag.sqlite.*;


@WebFilter("/SensorFilter")
public class SensorFilter implements Filter {

    private String patchToDB = "DataBases/sqlitedb";
    
    public DBTools dbTools;
    
    final String[] ResursPatch = {"Get","Add","Set", "Viev"};
    
    public SensorFilter() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		//response.setCharacterEncoding("HTML/Text");
		
		HttpServletRequest req = (HttpServletRequest) request;
		
		PrintWriter pwr = response.getWriter();
		if(dbTools.isWork()) {
			request.setAttribute("UsingDB", dbTools);
			System.out.println("[SensorFilter:doFilter]: Filter ok");
			for(String str : ResursPatch) {
				if(req.getServletPath().split("/").length > 2) {
					
					if(req.getServletPath().split("/")[2].equals(str)) {
						
						System.out.println("[SensorFilter:doFilter]: Filter ok() " + req.getServletPath());
						req.getRequestDispatcher("/sensor/"+str).forward(request, response);
					}
				}
			}
			System.out.println("[SensorFilter:doFilter]: Fiter don't accept");
			pwr.println(org.bag.json.Coding.getError("Error", 0, "Invalid Patch".toString()).toString());
		}
		else {
			System.out.println("[SensorFilter:doFilter]{Error}: Crytical error BD(Patch:" + patchToDB + ")");
			pwr.println(org.bag.json.Coding.getError("Error BD", 0, "Cryticle error".toString()).toString());
		}
		
		pwr.println("HUI");
		pwr.println("DB:" + patchToDB);
		
		pwr.close();
		//chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		/*patchToDB = fConfig.getInitParameter(fConfig.getInitParameterNames().nextElement());
		System.out.print("[SensorFilter]{Error}: Crytical error BD(Patch:");
		System.out.print(fConfig.getInitParameterNames().nextElement());
		System.out.println(")");*/
		
		try {
			Class.forName("org.sqlite.JDBC");
			dbTools = new DBTools("/home/bag234/eclipse-workspace/AutoHome/DataBases/sqlitedb");
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("[SensorFilter:init]{Error}: Crytical error");
			e.printStackTrace();
		}
	}

}
