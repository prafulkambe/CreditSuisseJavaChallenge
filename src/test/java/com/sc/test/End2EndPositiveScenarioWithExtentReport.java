package com.sc.test;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.cs.utilities.HyperSqlDBCon;
import com.cs.utilities.Log;
import com.cs.utilities.ProcessFileData;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class End2EndPositiveScenarioWithExtentReport {

	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest test;

	//Setting up by default value
	String filepath = System.setProperty("filepath", "logfile.txt");
	
	
	// In case need to use logfile.txt at root for testing purpose
	//String filepath = "logfile.txt";

	@BeforeClass
	public void getInputArgument() {
		
		if(filepath == null){
			System.out.println("Input Argument is expected for code to run. Please check");
			Assert.assertNotEquals(filepath, null);
		}
		
		Log.info("****** ***********************************************************");
		Log.info("***** Initializing Test Execution for End2EndTest ****************");
		Log.info("******************************************************************");

		Log.debug("**** Setting up Extent Reports  ******");
		htmlReporter = new ExtentHtmlReporter("extent.html");

		// create ExtentReports and attach reporter(s)
		extent = new ExtentReports();

		extent.attachReporter(htmlReporter);

		System.out.println(filepath);

		if(filepath != null){

			System.out.println("Proceeding with Test");
		}


	}

	@Test
	public void logProcessingTest(){
			
		boolean isTestComplete = false;
		
		Log.debug("**** Start: End2End Log processing Test ******");

		try{

			
			// creates a toggle for the given test, adds all log events under it  
			Log.debug("**** Writing Test details to Extent Report  ******");
			test = extent.createTest("LogProcessingTest", "This test will flag any longer duration events");


			// get object of ProcessFileData class

			ProcessFileData pf = new ProcessFileData(filepath);
			pf.createStartAndFinishMap();
			

			// Create a connection of SQL DB
			test.info("Connecting to Database");
			HyperSqlDBCon con = new  HyperSqlDBCon();
			

			// Delete all previous records
			test.info("Deleting all records in database pre file processing");
			con.deleteAllRecordsFromDB();
			

			// Create a table if does not exists
			test.info("Creating LogEvents table if does not exists");
			con.createHyperSQLDBTable();
			

			// Flag events with longer duration 
			test.info("Processing events to mark events with longer duration");
			isTestComplete = pf.flaglongerDurationEventMessages(con);
			

			// Close DB connection post processing file
			test.info("Closing DB connection");
			con.closeDatabase();

			//Hard Assertion
			Assert.assertEquals(true, isTestComplete);
			
			Log.debug("**** Writing Test Results in Extent Report  ******");
			test.pass("Test completed successfully");

			
		}
		catch(Exception e){
			
			e.printStackTrace();
			test.fail("Exception occured during test execution");
			Log.error("Exception occured during test Execution : " + e.toString());

		}
		finally{
			
			Log.debug("**** End: End2End Log processing Test ******");
			extent.flush();
		}

	}
	
	@AfterClass
	public void afterTest() {

		Log.info("******************************************************");
		Log.info("****** Finished Test Execution for End2EndTest *******");
		Log.info("******************************************************");
	}


}
