package com.live;

import java.util.Arrays;
import java.util.List;

import com.basho.riak.client.api.RiakClient;

public class A {

	public static void main(String[] args) throws Exception {
		RiakClient client = RiakClient.newClient("localhost");
//		 FOR DELETE
//		 final List<Cell> keyCells = Arrays.asList(
//		 new Cell("South Atlantic"), new Cell("South Carolina"),
//		 Cell.newTimestamp(1420113600000));
//		
//		 Delete delete = new Delete.Builder("GeoCheckins", keyCells).build();
//		
//		 client.execute(delete);
//		 Riak Client with supplied IP and Port
//		RiakClient client = RiakClient.newClient(10017, "myriakdb.host");
//		String queryText = "select * from GeoCheckin " + "where time >= 1234567 and time <= 1234567 and "
//				+ "region = 'South Atlantic' and state = 'South Carolina' ";
//		Query query = new Query.Builder(queryText).build();
//		QueryResult queryResult = client.execute(query);
//		List<Row> rows = queryResult.getRowsCopy();
//		client.shutdown();
		
	}

}
