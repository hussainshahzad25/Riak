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

public class InsertRecord {

	public static void main(String[] args)
			throws RiakException, UnknownHostException, ExecutionException, InterruptedException {
		RiakClient client = RiakClient.newClient("localhost");

		// Location johnSmithKey = new Location(new Namespace("default",
		// "users"), "john_smith");
		// Location johnSmithKey1 = new Location(new Namespace("users"),
		// "john_smith");
		//
		// RiakObject obj = new RiakObject().setContentType("application/json")
		// .setValue(BinaryValue.create("{'user_data':{ ... }}"));
		//
		// obj.getIndexes().getIndex(StringBinIndex.named("twitter")).add("jsmith123");
		// obj.getIndexes().getIndex(StringBinIndex.named("email")).add("jsmith@basho.com");
		//
		// StoreValue store = new
		// StoreValue.Builder(obj).withLocation(johnSmithKey).build();
		// client.execute(store);
		Namespace ns = new Namespace("default", "information");
		Location location = new Location(ns, "info1");

		System.out.println("done");
		RiakObject riakObject = new RiakObject();
		riakObject.setContentType("application/json").setValue(
				BinaryValue.create("{'firstName':'Shahzad','lastName':'Hussain','Company':'Can Tech Services'}"));
		// riakObject.getIndexes().getIndex(StringBinIndex.named("twitter")).add("jsmith123");
		// riakObject.setValue(BinaryValue.create("my_value"));
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
