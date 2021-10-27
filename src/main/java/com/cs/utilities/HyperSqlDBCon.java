package com.cs.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.cs.pojo.EventsPojo;

public class HyperSqlDBCon {

	private static Connection connection;

	static String connectionString = "jdbc:hsqldb:file:db-data/sampledatabase;hsqldb.lock_file=false";

	private static final String tableName = "LogEvents";


	public HyperSqlDBCon() throws SQLException, ClassNotFoundException{
		Log.debug("Connecting to HyperSQL Database");
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
		} catch (ClassNotFoundException exception) {
			throw exception;
		}
		connection = DriverManager.getConnection(connectionString, "SA", "");
	}

	public void createHyperSQLDBTable() throws SQLException {

		try{
			String createTable = "CREATE TABLE IF NOT EXISTS " +  tableName + " (Event_Id VARCHAR(50) NOT NULL, Event_Duration INT NOT NULL, " +
					"Event_Type VARCHAR(50), Host VARCHAR(50), Alert BOOLEAN DEFAULT FALSE NOT NULL)";
			System.out.println("SQL Query: " + createTable);
			Log.info("Creating LogEvents table");
			connection.createStatement().executeUpdate(createTable);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{

			//do nothing
		}

	}

	void writeEvent(EventsPojo event) throws SQLException {

		try{
			String addEvent = "INSERT INTO " + tableName + " (Event_Id, Event_Duration, Event_Type, Host, Alert)  VALUES (?, ?, ?, ?, ?)";

			PreparedStatement preparedStatement = connection.prepareStatement(addEvent);
			preparedStatement.setString(1, event.getId());
			preparedStatement.setLong(2, event.getDuration());
			preparedStatement.setString(3, event.getType());
			preparedStatement.setString(4, event.getHost());
			preparedStatement.setBoolean(5, event.getAlert());

			preparedStatement.executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{

			//do nothing
		}

	}

	public void closeDatabase() throws SQLException {
		Log.info("Closing DB connection.");
		connection.close();
	}

	public void getAllRecordsFromDB() throws SQLException {

		try{
			String getAllRec = "SELECT * FROM " + tableName;

			Log.debug("Retrieving all DB entries in < " + tableName + " > table.");
			ResultSet resultSet = connection.createStatement().executeQuery(getAllRec);
			ResultSetMetaData rsMetaData = resultSet.getMetaData();
			while (resultSet.next()) {
			       for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
			           if (i > 1) System.out.print(",  ");
			           String columnValue = resultSet.getString(i);
			           System.out.print(rsMetaData.getColumnName(i) + " " + columnValue );
			       }
			       System.out.println("");
			   }

		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{

			//do nothing

		}

	}

	public void deleteAllRecordsFromDB() throws SQLException {
		try{
			
			String deleteAllRec = "DELETE FROM " + tableName;

			Log.warn("Deleting all entries in DB table : " + tableName);
			connection.createStatement().executeUpdate(deleteAllRec);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			
			//do nothing
		}
		
	}
}