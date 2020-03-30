package org.bag.sensor;

import org.json.*;

//links to: DBTools, 



public class SensorCount {

	public String Name;
	
	public String Date;
	
	public double Count;
	
	public SensorCount() {
		
	}
	
	public SensorCount(String Name, String Data, double Count) {
		this.Name = Name;
		this.Date = Data;
		this.Count = Count;
	}
	
	public JSONObject toJSONO() {
		JSONObject jsono = new JSONObject();
		jsono.put("Date", Date);
		jsono.put("Count", Count);
		
		return jsono;
	}
	
}
