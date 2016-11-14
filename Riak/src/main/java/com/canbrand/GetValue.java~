package com.canbrand;

import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.kv.FetchValue;
import com.basho.riak.client.core.query.Location;
import com.basho.riak.client.core.query.Namespace;
import com.basho.riak.client.core.query.RiakObject;

public class GetValue {



	public static void main(String[] args) throws UnknownHostException, ExecutionException, InterruptedException {
		RiakClient client = RiakClient.newClient("localhost");
		System.out.println("connected to localhost");
		Namespace ns = new Namespace("default", "users");
		Location location = new Location(ns, "john_smith");
		System.out.println("location "+location);
		
		FetchValue fv = new FetchValue.Builder(location).build();
		FetchValue.Response response = client.execute(fv);
		RiakObject obj = response.getValue(RiakObject.class);
		System.out.println(obj.getValue());
		
		
//		FetchValue fv1 = new FetchValue.Builder(location).build();		
//		RiakObject fetchedObject = client.execute(fv1).getValue(RiakObject.class);
//		System.out.println(fetchedObject.getValue());
		System.out.println("successfully fetched");
		
		
		
	}

}
