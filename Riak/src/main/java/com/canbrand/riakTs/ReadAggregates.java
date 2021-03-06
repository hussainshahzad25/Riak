package com.canbrand.riakTs;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.timeseries.Query;
import com.basho.riak.client.core.query.timeseries.Cell;
import com.basho.riak.client.core.query.timeseries.QueryResult;
import com.basho.riak.client.core.query.timeseries.Row;

public class ReadAggregates {
	public static void main(String[] args) throws UnknownHostException, ExecutionException, InterruptedException, ParseException {
		// Create the Riak TS client to use to write data to
		// Update the IP and Port if needed to connect to your cluster
	    RiakClient client = RiakClient.newClient(8087, "127.0.0.1");
	    
		// Start and end date to search on
		String startDateStr = "19/01/2016 12:30:00";
		String endDateStr = "19/01/2016 12:45:00";
		
		// Convert string formats to epoch for TS
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = sdf.parse(startDateStr);
		long startDate = date.getTime();
		date = sdf.parse(endDateStr);
		long endDate = date.getTime();
		 
	    // TS SQL Query - Aggregate count on rows matching WHERE clause
		// See http://docs.basho.com/riakts/latest/using/aggregate-functions/ for more
		// information on supported aggregate functions
		String queryText = "select COUNT(*) from WeatherStationData " +
				"where time > " + startDate + " and time < " + endDate + " and " +
				"device = 'Weather Station 0001' and deviceId = 'abc-xxx-001-001'";
		System.out.println(queryText);
		
		// Send the query to Riak TS
		Query query = new Query.Builder(queryText).build();
		QueryResult queryResult = client.execute(query);
		
		// Iterate over the returned rows and print them out to the console
		Iterator<Row> rows = queryResult.iterator();
		while (rows.hasNext()) {
			Row row = (Row) rows.next();
			
			Iterator<Cell> cells = row.iterator();
			String rowOut = "";
			while (cells.hasNext()) {
				Cell cell = (Cell) cells.next();
				rowOut += Utility.getCellStringVal( cell );
			}
			System.out.println("COUNT(*) Returns: " + rowOut + "\n");
		}
		
		// TS SQL Query - Get the min, avg, max temperature and average humidity readings 
		// for the same selection of records. Multiply humidity by 0.25 to demonstrate mixing
		// aggregate and arithmetic functions in the same query.
		// See http://docs.basho.com/riakts/latest/using/aggregate-functions/ for more
		// information on supported aggregate functions
		queryText = "select MIN(temperature), AVG(temperature), MAX(temperature), AVG(humidity) * 0.25 from WeatherStationData " +
				"where time > " + startDate + " and time < " + endDate + " and " +
				"device = 'Weather Station 0001' and deviceId = 'abc-xxx-001-001'";
		System.out.println(queryText);
		query = new Query.Builder(queryText).build();
		queryResult = client.execute(query);
		
		// Iterate over the returned rows and print them out to the console
		rows = queryResult.iterator();
		while (rows.hasNext()) {
			Row row = (Row) rows.next();
			
			Iterator<Cell> cells = row.iterator();
			int cellNo = 0;
			while (cells.hasNext()) {
				Cell cell = (Cell) cells.next();
				if (cellNo==0) {
					System.out.println("Miniumum Temp: " + Utility.getCellStringVal(cell));
				}
				else if (cellNo==1) {
					System.out.println("Average Temp: " + Utility.getCellStringVal(cell));
				}
				else if (cellNo==2) {
					System.out.println("Maximum Temp: " + Utility.getCellStringVal(cell));
				}
				else if (cellNo==3) {
					System.out.println("Average Humidity: " + Utility.getCellStringVal(cell) + "%");
				}
				cellNo++;
			}
		}
	    client.shutdown();
	}
	
}