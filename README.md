# CreditSuisseJavaChallenge

Purpose of this document is to demonstrate how to run the code. 


**Technologies used in this project: **

1. TestNG – Provides parallel test run capabilities using testing.xml
2. Log4J2 – For general logging purpose
3. Maven – Build tool used for project
4. HSQLDB – Relation database engine. More info could be found at http://hsqldb.org/
5. Extent Report – Integrated in End2End test for the purpose of generating test repot


**Details on Project Structure:** 
1.	Current project structure includes 2 POJO classes for handling json messages from input txt file and creating events out of them. 
2.	All required utilities could be found under com.cs.utilities package
3.	There are 2 TestNG tests under the package com.sc.test which lies under src/test/java structure of the maven project where we usually write unit tests/automation tests.
    a.	End2EndPositiveScenarioWithExtentReport – This TestNG test is an end to end scenario to accept .txt file as an input argument and process the data to flag long  
        duration events with alert column value as true in HyperSQLDB. 
    b.	HyperSqlDBConnectionTest – This includes simple tests to check DB connectivity, retrieving data, createTableTest etc. 


**How to Run?** : 
Both tests mentioned above can be run individually as well but main goal was to pass .txt file as an argument. 
If using an IDE like Eclipse then right click on project root folder and select Run As  Maven install option
 
**o/p** : 
**End2End test will fail with message**: 
**Input Argument is expected for code to run. Please check**
 
Please note 2nd test does not require any parameter and will still be executed. 


**To run End2EndTest with valid input argument:** 

**1.	Using eclipse IDE: **

Right click on project root folder and select Run As  Maven Build
In Edit Configuration window under Goals section pass .txt file path in the format mentioned below: 
	install –Dfilepath={your filepath}

**o/p: **

We will see a successful build with 4 tests passed and an extent report is integrated with End2End test so we could refer to test o/p in extent report as well.
 

**Extent Report path: **

Report will be available at the root folder path of the project. 

 
**Checking if events are actually logged properly in HyperSQL DB: **

Go to RunGUI.java file and run the code. It will open up HyperSQLDB Graphical user interface.

In the HSQL Database Manager GUI, we can query the table to check output processed by End2End test.

**Sample Data which was provided to program through an argument: **

{"id":"scsmbstgrx", "state":"STARTED", "type":"APPLICATION_LOG", "host":"12345", "timestamp":1491377495212}
{"id":"scsmbstgry", "state":"STARTED", "timestamp":1491377495213}
{"id":"scsmbstgrz", "state":"FINISHED", "timestamp":1491377495218}
{"id":"scsmbstgrx", "state":"FINISHED", "type":"APPLICATION_LOG", "host":"12345", "timestamp":1491377495219}
{"id":"scsmbstgrz", "state":"STARTED", "timestamp":1491377495210}
{"id":"scsmbstgry", "state":"FINISHED", "timestamp":1491377495214}

 
**2.	Run the program using command prompt. **

Note: - Please make sure maven is installed and configured properly. 
1.	Open cmd
2.	Change directory to project root folder. 
3.	Type command mvn install –Dfilepath={provide your file path here}
 
 
 
