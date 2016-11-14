package com.canbrand.fetch;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.kv.DeleteValue;
import com.basho.riak.client.api.commands.kv.FetchValue;
import com.basho.riak.client.core.query.Location;
import com.basho.riak.client.core.query.Namespace;
import com.basho.riak.client.core.query.RiakObject;

public class DeleteObject {

	public static void main(String[] args) throws Exception {

		RiakClient client = RiakClient.newClient("localhost");
		Location geniusQuote = new Location(new Namespace("animals"), "lion");
		System.out.println(geniusQuote);
		FetchValue fetchOp = new FetchValue.Builder(geniusQuote).build();
		RiakObject fetchedObject = client.execute(fetchOp).getValue(RiakObject.class);

		if (fetchedObject != null) {
			System.out.println(fetchedObject.getValue());
			DeleteValue delete = new DeleteValue.Builder(geniusQuote).build();
			client.execute(delete);
			System.out.println("DELETED");
		} else {
			System.out.println("Either is not available/Data Already Deletd");
		}

	}

}
