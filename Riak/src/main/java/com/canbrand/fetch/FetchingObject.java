package com.canbrand.fetch;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.kv.FetchValue;
import com.basho.riak.client.core.query.Location;
import com.basho.riak.client.core.query.Namespace;
import com.basho.riak.client.core.query.RiakObject;

public class FetchingObject{

	public static void main(String[] args) throws Exception {
		RiakClient client = RiakClient.newClient("localhost");
		Location location = new Location(new Namespace("information"), "info1");
		FetchValue fetchOp = new FetchValue.Builder(location).build();
		RiakObject fetchedObject = client.execute(fetchOp).getValue(RiakObject.class);
		System.out.println(fetchedObject.getValue());
	}

}
