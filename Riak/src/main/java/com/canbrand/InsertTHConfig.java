package com.canbrand;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.RiakException;
import com.basho.riak.client.api.cap.Quorum;
import com.basho.riak.client.api.commands.kv.StoreValue;
import com.basho.riak.client.api.commands.kv.StoreValue.Option;
import com.basho.riak.client.core.query.Location;
import com.basho.riak.client.core.query.Namespace;
import com.basho.riak.client.core.query.RiakObject;
import com.basho.riak.client.core.util.BinaryValue;

public class InsertTHConfig {

	private static String Search_Host;
	private static String Bucket;
	private static String key;
	private static String QUERY;

	private static Properties prop = new Properties();

	static {
		try {

			prop.load(new FileInputStream("config.properties"));

			Search_Host = prop.getProperty("searchHost");
			Bucket = prop.getProperty("bucket");
			key = prop.getProperty("key");
			QUERY = prop.getProperty("Query");

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args)
			throws RiakException, UnknownHostException, ExecutionException, InterruptedException {
		RiakClient client = RiakClient.newClient(Search_Host);
		Namespace ns = new Namespace("default", Bucket);
		Location location = new Location(ns, key);

		System.out.println("done");
		RiakObject riakObject = new RiakObject();
		riakObject.setContentType("application/json").setValue(BinaryValue.create(QUERY));

		StoreValue store = new StoreValue.Builder(riakObject)
				.withLocation(location).withOption(Option.W, new Quorum(3)).build();
		System.out.println("values is STordred");
		try {
			client.execute(store);
			System.out.println("Insertion Sucessfully");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
