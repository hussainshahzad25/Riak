package com.canbrand;

import java.net.UnknownHostException;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.cap.Quorum;
import com.basho.riak.client.api.commands.kv.StoreValue;
import com.basho.riak.client.api.commands.kv.StoreValue.Option;
import com.basho.riak.client.core.query.Location;
import com.basho.riak.client.core.query.Namespace;
import com.basho.riak.client.core.query.RiakObject;
import com.basho.riak.client.core.util.BinaryValue;

public class SavingPlainText {

	public static void main(String[] args) throws UnknownHostException {

		RiakClient client = RiakClient.newClient("localhost");

		Namespace ns = new Namespace("default", "info");
		Location location = new Location(ns, "info2");

		System.out.println("done");
		RiakObject riakObject = new RiakObject();
		riakObject.setContentType("text/plain").setValue(BinaryValue.create("Hello Can Brand"));

		StoreValue store = new StoreValue.Builder(riakObject).withLocation(location).withOption(Option.W, new Quorum(3))
				.build();
		System.out.println("values is STordred");
		try {
			client.execute(store);
			System.out.println("Insertion Sucessfully");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
