package org.bag.sqlite;

import java.sql.SQLException;
import java.util.ArrayList;

import org.json.*;
import org.bag.sensor.*;

public class Index {

	public static void main(String[] args) { 
		try {
			DBTools db = new DBTools("DataBases/sqlitedb");
			System.out.println("_____________________d:" + db.isWork());
			System.out.println("isFind:" + db.isFindTable("test001"));
			ArrayList<SensorCount> list = new ArrayList<SensorCount>();
			list = db.getSensorNode("test001", 5554           );
			for(SensorCount sCount : list) {
				System.out.println("[DB JSON]"+ sCount.toJSONO().toString());
			}
			System.out.println("Info: " + db.getTableSensor("test"));
			System.out.println(list.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
