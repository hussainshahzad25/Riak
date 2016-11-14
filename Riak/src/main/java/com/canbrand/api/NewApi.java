package com.canbrand.api;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.timeseries.Query;
import com.basho.riak.client.api.commands.timeseries.Store;
import com.basho.riak.client.core.query.timeseries.Cell;
import com.basho.riak.client.core.query.timeseries.QueryResult;
import com.basho.riak.client.core.query.timeseries.Row;

public class NewApi {
	public static void main(String[] args) throws Exception {

		createTable1();
		// createTable2();
		insertData();
		// insertDataToTable2();
	}

	// ##################################################
	// CREATING TABLE
	private static void createTable1() throws Exception {

		RiakClient client = RiakClient.newClient("localhost");
		String queryText = "CREATE TABLE Em1 (memberId SINT64 NOT NULL, operation VARCHAR NOT NULL"
				+ ",deviceId VARCHAR NOT NULL,userName VARCHAR NOT NULL"
				+ ",start TIMESTAMP NOT NULL,end TIMESTAMP NOT NULL, steps SINT64 NOT NULL"
				+ ",PRIMARY KEY ("
				+ "(memberId,end,QUANTUM(start, 1, 'd')),memberId,end,start)"
				+")";
		Query query = new Query.Builder(queryText).build();
		try {
			// QueryResult queryResult = client.execute(query);
			client.execute(query);
			System.out.println("Table Created Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			
			System.out.println("Table Creation Failed: " + e.getMessage());
		}

	}

	// ##################################################
	// INSERTING RECORD TO TABLE
	private static void insertData()
			throws ParseException, UnknownHostException, ExecutionException, InterruptedException {

		Thread.sleep(1000);
		System.out.println("Now Data is ready to insert...........");
		Thread.sleep(1000);
		RiakClient client = RiakClient.newClient("localhost");
		// #############################
		String jsonData = "";
		BufferedReader br = null;
		try {
			String line;
			br = new BufferedReader(new FileReader("xyz.json"));
			while ((line = br.readLine()) != null) {
				jsonData += line + "\n";
			}
			// System.out.println("File Content: \n" + jsonData);
			JSONObject obj = new JSONObject(jsonData);
			System.out.println("memberId: " + obj.getString("memberId"));
			System.out.println("operation: " + obj.getString("operation"));
			System.out.println("deviceId: " + obj.getString("deviceId"));
			System.out.println("userName: " + obj.getString("userName"));

			JSONArray array = obj.getJSONArray("stepCount");

			long endDate = 0, startDate = 0;
			int n;
			System.out.println("Length = " + array.length());
			for (n = 0; n < array.length(); n++) {

				JSONObject object = array.getJSONObject(n);

				System.out.println("object=" + object);
				System.out.println(object.getString("start"));
				System.out.println(object.getString("end"));
				System.out.println(object.getInt("steps"));

				String startDateStr = object.getString("start");
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				Date date = sdf.parse(startDateStr);
				startDate = date.getTime();
				System.out.println(startDate);

				String endDateStr = object.getString("end");
				SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				Date date1 = sdf1.parse(endDateStr);
				endDate = date1.getTime();
				System.out.println(endDate);
				List<Row> rows = Arrays.asList(new Row(new Cell(obj.getInt("memberId")),
						new Cell(obj.getString("operation")), new Cell(obj.getString("deviceId")),
						new Cell(obj.getString("userName")), Cell.newTimestamp(startDate), Cell.newTimestamp(endDate),
						new Cell(object.getInt("steps"))));
				System.out.println("process....");

				try {
					System.out.println("processing....");
					Store storeCmd = new Store.Builder("Em1").withRows(rows).build();
					client.execute(storeCmd);
					System.out.println("Insert Record Successfully..........");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	// ##################################################
	private static void createTable2() throws Exception {

		RiakClient client = RiakClient.newClient("localhost");
		String queryText = "CREATE TABLE health (memberId SINT64 NOT NULL, start TIMESTAMP NOT NULL, end TIMESTAMP NOT NULL,steps SINT64 NOT NULL, PRIMARY KEY (memberId))";
		Query query = new Query.Builder(queryText).build();
		try {
			QueryResult queryResult = client.execute(query);
			System.out.println("Table Created Successfully");
		} catch (Exception e) {
			System.out.println("Table Creation Failed: " + e.getMessage());
		}

	}

	// ###################################################

	private static void insertDataToTable2() throws Exception {

		RiakClient client = RiakClient.newClient("localhost");
		Thread.sleep(1000);
		System.out.println("Now Data is ready to insert...........");
		Thread.sleep(1000);
		// #####################INSERTION START ########
		String jsonData = "";
		BufferedReader br = null;
		try {
			String line;
			br = new BufferedReader(new FileReader("xyz.json"));
			while ((line = br.readLine()) != null) {
				jsonData += line + "\n";
			}
			// System.out.println("File Content: \n" + jsonData);
			JSONObject obj = new JSONObject(jsonData);
			System.out.println("memberId: " + obj.getString("memberId"));
			// System.out.println("operation: " + obj.getString("operation"));
			// System.out.println("deviceId: " + obj.getString("deviceId"));
			// System.out.println("userName: " + obj.getString("userName"));
			System.out.println("stepCount:" + obj.getJSONArray("stepCount"));

			JSONArray array = obj.getJSONArray("stepCount");
			for (int n = 0; n < array.length(); n++) {
				JSONObject object = array.getJSONObject(n);
				object.put("memberId", obj.getInt("memberId"));
				System.out.println(object.getString("start"));
				System.out.println(object.getString("end"));
				System.out.println(object.getInt("steps"));
				System.out.println(object.getInt("memberId"));

				String startDateStr = object.getString("start");
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				Date date = sdf.parse(startDateStr);
				long startDate = date.getTime();

				String endDateStr = object.getString("end");
				SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				Date date1 = sdf1.parse(endDateStr);
				long endDate = date1.getTime();

				List<Row> rows = Arrays.asList(new Row(new Cell(object.getInt("memberId")),
						Cell.newTimestamp(startDate), Cell.newTimestamp(endDate), new Cell(object.getInt("steps"))));
				Store storeCmd = new Store.Builder("health").withRows(rows).build();
				try {
					client.execute(storeCmd);
					System.out.println("Insert Record Successfully..........");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}