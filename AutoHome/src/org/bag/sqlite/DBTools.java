package org.bag.sqlite;

import java.sql.*;
import java.util.ArrayList;
import org.bag.sensor.*;

class sqlQ{ // this class for save paterns sql
	
	public static String SelecTables = "SELECT name FROM sqlite_master WHERE type ='table' AND name NOT LIKE 'sqlite_%';";
	
	public static String CreateTableSernsor(String name) { return "CREATE TABLE " + name +" (\"ID_Sension\" INTEGER PRIMARY KEY AUTOINCREMENT, \"Date\" TEXT, \"Count\" REAL);";}
	
	public static String AddNodeSensor(String Name, String Date, double Count) { return "INSERT INTO " + Name + " (Date, Count) VALUES (\"" + Date + "\"," + Count + ");"; }
	
	public static String SelectLastNode(String Table, int Count, String byOrder) {return "SELECT * FROM (SELECT * FROM " + Table + " ORDER BY " + byOrder + " DESC LIMIT " + Count + ") AS T ORDER BY " + byOrder + " ASC";}
	
	public static String AddNodeOfSensor(String Login, String Name, String Type) { return "INSERT INTO Sensors ('Login', 'Table', 'Type', 'Name') VALUES ('"+ Login +"','ts_" + Login + "','"+Type+"','" +Name+ "');";}

	public static String GetFromSensor(String Login) {return "SELECT \"Table\" FROM Sensors WHERE Login = '" + Login + "';";}
	
	public static String GetFromSensorName(String Login) {return "SELECT \"Name\" FROM Sensors WHERE Login = '" + Login + "';";}
	
	public static String RenameNodeOfSensor(String NewName, String Login) {return "UPDATE \"Sensors\" SET \"Name\" = \"" + NewName + "\" WHERE \"Login\" = \"" + Login + "\";";}
}
/*
 * Name - using how name of table!
 */
public class DBTools {
	/*static {
	    try {
	        java.sql.DriverManager.registerDriver(new Driver());
	    } catch (SQLException E) {
	        throw new RuntimeException("Can't register driver!");
	    } //who is it?
	}*/
	
	/*
	 * PERIPASATI NAME != TABLE !!!!!
	 * 
	 */
	final String OrderBySensor = "ID_Sension";
	
	String Patch;
	
	final String Driver = "jdbc:sqlite:";
	
	public Connection connection;
	
	public DBTools(String Patch) throws SQLException {
		this.Patch = Patch;
		connection = DriverManager.getConnection(Driver + Patch);
	}
	
	public boolean isWork() {
		if(connection != null) {
			return true;
		}
		return false;
	}
	
	public boolean isFindTable(String name) throws SQLException {
		Statement statement = this.connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sqlQ.SelecTables);
		while(resultSet.next()) {
			//only on test: System.out.println("[DB]:"resultSet.getString("name") + ":" + name);
			if(resultSet.getString("name").equals(name)) {
				resultSet.close();
				statement.close();
				return true;
			}
		}
		resultSet.close();
		statement.close();
		return false;
	}
	
	public void creatTableSensor(String Name) throws SQLException {
		Statement statement = connection.createStatement();
		statement.execute(sqlQ.CreateTableSernsor(Name));
		statement.close();
	}
	
	public void addNoteSensor(String Name, String Date, double Count) throws SQLException {
		Statement statement = connection.createStatement();
		statement.execute(sqlQ.AddNodeSensor(Name, Date, Count));
		statement.close();
	}
	
	public String getTableSensor(String Login) throws SQLException {
		String str;
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sqlQ.GetFromSensor(Login));
		if(!resultSet.isClosed())
			str = resultSet.getString("Table");	
		else 
			str = "NULL";
		return str;
	}
	
	public String getNameSensor(String Login) throws SQLException {
		String str;
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sqlQ.GetFromSensorName(Login));
		if(!resultSet.isClosed())
			str = resultSet.getString("Name");	
		else 
			str = "NULL";
		return str;
	}
	
	public ArrayList<SensorCount> getSensorNode(String Table, int Count) throws SQLException{
		ArrayList<SensorCount> list = new ArrayList<SensorCount>();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sqlQ.SelectLastNode(Table, Count, OrderBySensor));
		//resultSet.first();
		while(resultSet.next()) {
			list.add(new SensorCount(Table,resultSet.getString("Date"),resultSet.getDouble("Count")));
		} 
		resultSet.close();
		statement.close();
		return list;
	}
	
	public void addNodeOfSensor(String Login, String Name, String Type) throws SQLException {
		Statement statement = connection.createStatement();
		statement.execute(sqlQ.AddNodeOfSensor(Login, Name, Type));
		statement.close();
	}
	
	public void renameNodeOfSensor(String NewName, String Login) throws SQLException {
		Statement statement = connection.createStatement();
		statement.execute(sqlQ.RenameNodeOfSensor(NewName, Login));
		statement.close();
	}
	
	@Override
	protected void finalize() throws Throwable {
		connection.close();
	}
}
