package com.cs.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;
import com.cs.pojo.EventsPojo;
import com.cs.pojo.LogsPojo;
import com.google.gson.Gson;


public class ProcessFileData {

	private File file;
	private Map<String, LogsPojo> startMap;
	private Map<String, LogsPojo> finishMap;

	public ProcessFileData(String filePath){

		this.file = new File(filePath);
	}



	public void createStartAndFinishMap() throws IOException{
		// Read File and Create 2 different maps to store data
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		startMap = new TreeMap<String,LogsPojo>();
		finishMap = new TreeMap<String,LogsPojo>();

		String line; 
		Gson gson = new Gson();
		while((line = br.readLine()) != null){
			LogsPojo log = gson.fromJson(line, LogsPojo.class);

			if(log.getState().equals("STARTED")){
				startMap.put(log.getId(), log);
			}
			else if(log.getState().equals("FINISHED"))
			{
				finishMap.put(log.getId(), log);
			}
		}
		br.close();
	}

	public boolean flaglongerDurationEventMessages(HyperSqlDBCon con) throws SQLException{

		boolean isProcessComplete = false; 
		for(String id : finishMap.keySet()){
			LogsPojo finishObj = finishMap.get(id);
			LogsPojo startObj = startMap.get(id);

			String eventId = finishObj.getId();
			long finishTimestamp = finishObj.getTimestamp();
			long startTimestamp = startObj.getTimestamp();

			long durationdiff = Math.abs(finishTimestamp - startTimestamp);
			boolean alert = false;
			if(durationdiff > 4){
				alert = true; 
			}
			EventsPojo event = new EventsPojo.EventsPojoBuilder(eventId, durationdiff, alert)
					.setType(finishObj.getType())
					.setHost(finishObj.getHost())
					.build();
			con.writeEvent(event);
			
			isProcessComplete = true;

		}
		return isProcessComplete;


	}
}
