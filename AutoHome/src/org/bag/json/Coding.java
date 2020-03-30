package org.bag.json;

import org.json.*;

public class Coding {
	
	public static JSONObject getError(String Type, int Code, String About) {
		JSONObject jsono = new JSONObject();
		jsono.put("Type:", Type);
		jsono.put("Code", Code);
		jsono.put("About", About);
		return jsono;
	}
	
	public static JSONObject getDenie(String Type, int Code, String About) {
		JSONObject jsono = new JSONObject();
		jsono.put("Type:", Type);
		jsono.put("Code", Code);
		jsono.put("About", About);
		return jsono;
	}

}
