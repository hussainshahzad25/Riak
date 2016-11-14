package com.canbrand.fetch;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.kv.FetchValue;
import com.basho.riak.client.api.commands.kv.StoreValue;
import com.basho.riak.client.core.query.Location;
import com.basho.riak.client.core.query.Namespace;
import com.basho.riak.client.core.query.RiakObject;
import com.basho.riak.client.core.util.BinaryValue;

public class UpdatinngRecords {

	public static void main(String[] args) throws Exception {

		RiakClient client = RiakClient.newClient("localhost");
		Location currentChampion = new Location(new Namespace("new_bucket"), "new_key");
		FetchValue fetchValue = new FetchValue.Builder(currentChampion).build();
		FetchValue.Response response = client.execute(fetchValue);
		RiakObject obj = response.getValue(RiakObject.class);
		if (fetchValue != null) {
			System.out.println("Original Object = " + obj.getValue());
			obj.setValue(BinaryValue.create("Harlem Globetrotters"));
			try {
				StoreValue lionoStore = new StoreValue.Builder(obj).withLocation(currentChampion).build();
				client.execute(lionoStore);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Updated Object = " + obj.getValue());
			System.out.println("Record Update Successfully......");
		} else {
			System.out.println("Object is not available to update/Update can't be performed.");
		}
	}

}
