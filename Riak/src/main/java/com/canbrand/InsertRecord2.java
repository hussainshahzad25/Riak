package com.canbrand;

import java.net.UnknownHostException;
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

public class InsertRecord2 {

	public static void main(String[] args)
			throws RiakException, UnknownHostException, ExecutionException, InterruptedException {
		RiakClient client = RiakClient.newClient("localhost");

		// Namespace ns = new Namespace("users", "data");
		// Location location = new Location(ns, "record1");
		//
		// System.out.println("done");
		// RiakObject riakObject = new RiakObject();
		// riakObject.setContentType("application/json").setValue(
		// BinaryValue.create("{'firstName':'Shahzad','lastName':'Hussain','Company':'Can
		// Brans Services'}"));
		//
		// StoreValue store = new StoreValue.Builder(riakObject)
		//
		// .withLocation(location).withOption(Option.W, new Quorum(3)).build();
		// System.out.println("values is STordred");
		// try {
		// client.execute(store);
		// System.out.println("Insertion Sucessfully");
		// } catch (Exception e) {
		// e.printStackTrace();
		//
		// }
		Namespace locationWithoutKey = new Namespace("users", "random_user_keys");

		Location location = new Location(locationWithoutKey, "r2");
		BinaryValue text = BinaryValue.create("{'user':'data'}");
		RiakObject obj = new RiakObject().setContentType("application/json").setValue(text);
		StoreValue store = new StoreValue.Builder(obj).withLocation(location).withOption(Option.W, new Quorum(5)).build();
		try {
			client.execute(store);
			System.out.println("Data Inserted Success");
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
