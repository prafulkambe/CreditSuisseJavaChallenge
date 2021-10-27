package com.sc.test;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.cs.utilities.HyperSqlDBCon;
import com.cs.utilities.Log;

import java.sql.SQLException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class HyperSqlDBConnectionTest {

	HyperSqlDBCon con;
	SoftAssert softAssert = new SoftAssert();

	@BeforeClass
	public void beforeTest() {

		Log.info("*****************************************************************************************");
		Log.info("****** Initializing Test Execution for "+ HyperSqlDBConnectionTest.class + " *******");
		Log.info("*****************************************************************************************");
		
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extent.html");

		// create ExtentReports and attach reporter(s)
		ExtentReports extent = new ExtentReports();

		extent.attachReporter(htmlReporter);

	
	}


	@Test(priority = 1)
	public void checkDBConnectionTest() throws SQLException {
			
		try {
			Log.debug("**** Start: Test to check DB Connectivity ******");
			con = new HyperSqlDBCon();
			if(con != null){
				Log.info("DB Connection is successful");
				softAssert.assertTrue(true);
			}
		} catch (ClassNotFoundException e) {
			softAssert.assertTrue(false);
			e.printStackTrace();
			Log.error("Found issue connecting to DB: " + e.toString());
		}finally{
			con.closeDatabase();
			Log.debug("**** End: Test to check DB Connectivity ******");
			softAssert.assertAll();
			
		}

	}
	
	@Test(priority = 2)
	public void createTableTest() throws SQLException {
			
		try {
			Log.debug("**** Start: Test to check table creation ******");
			con = new HyperSqlDBCon();
			if(con != null){
				Log.info("DB Connection is successful");
				con.createHyperSQLDBTable();
				softAssert.assertTrue(true);
			}
		} catch (ClassNotFoundException e) {
			softAssert.assertTrue(false);
			e.printStackTrace();
			Log.error("Found issue connecting to DB: " + e.toString());
		}finally{
			con.closeDatabase();
			Log.debug("**** End: Test to check table creation ******");
			softAssert.assertAll();
		}

	}
	
	@Test(priority= 3)
	public void readRecordsFromDBTest() throws SQLException{
		
		try {
			Log.debug("**** Start: Test to retrieve data from DB ******");
			con = new HyperSqlDBCon();
			if(con != null){
				Log.info("DB Connection is successful");
				System.out.println("******** Printing DB Records *******");
				con.getAllRecordsFromDB();
				System.out.println("********** END ***********");
				softAssert.assertTrue(true);
			}
		} catch (ClassNotFoundException e) {
			softAssert.assertTrue(false);
			e.printStackTrace();
			Log.error("Found issue connecting to DB: " + e.toString());
		}finally{
			con.closeDatabase();
			Log.debug("**** End: Test to retrieve data from DB ******");
			softAssert.assertAll();
		}
		
	}
	


	@AfterClass
	public void afterTest() {

		Log.info("*****************************************************************************************");
		Log.info("****** Finished Test Execution for "+ HyperSqlDBConnectionTest.class + " *******");
		Log.info("*****************************************************************************************");
	}

}
