package com.canbrand.fetch;

import java.util.ArrayList;
import java.util.List;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.kv.StoreValue;
import com.basho.riak.client.core.query.Location;
import com.basho.riak.client.core.query.Namespace;
import com.basho.riak.client.core.query.RiakObject;
import com.basho.riak.client.core.util.BinaryValue;

public class InsertingMultipleRecords {

	public static void main(String[] args) throws Exception {
		
		RiakClient client = RiakClient.newClient("localhost");
		
		Namespace animalsBucket = new Namespace("animals");
		String json = "application/json";

		RiakObject liono = new RiakObject()
		        .setContentType(json)
		        .setValue(BinaryValue.create("{\"name_s\":\"Lion-o\",\"age_i\":30,\"leader_b\":true}"));
		RiakObject cheetara = new RiakObject()
		        .setContentType(json)
		        .setValue(BinaryValue.create("{\"name_s\":\"Cheetara\",\"age_i\":30,\"leader_b\":false}"));
		RiakObject snarf = new RiakObject()
		        .setContentType(json)
		        .setValue(BinaryValue.create("{\"name_s\":\"Snarf\",\"age_i\":43,\"leader_b\":false}"));
		RiakObject panthro = new RiakObject()
		        .setContentType(json)
		        .setValue(BinaryValue.create("{\"name_s\":\"Panthro\",\"age_i\":36,\"leader_b\":false}"));
		
		List<RiakObject> objects = new ArrayList<RiakObject>();
		objects.add(liono);
		objects.add(cheetara);
		objects.add(snarf);
		objects.add(panthro);
		Location lionoLoc = new Location(animalsBucket, "lion");
		Location cheetaraLoc = new Location(animalsBucket, "cheetara");
		Location snarfLoc = new Location(animalsBucket, "snarf");
		Location panthroLoc = new Location(animalsBucket, "panthro");

		StoreValue lionoStore = new StoreValue.Builder(objects).withLocation(lionoLoc).build();
		// The other StoreValue operations can be built the same way

		client.execute(lionoStore);
		System.out.println("done");
		// The other storage operations can be performed the same way
	}

}
