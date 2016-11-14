package com.canbrand.fetch;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.kv.FetchValue;
import com.basho.riak.client.api.commands.kv.StoreValue;
import com.basho.riak.client.core.query.Location;
import com.basho.riak.client.core.query.Namespace;
import com.basho.riak.client.core.query.RiakObject;

import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

public class FetchObject {

    // { "foo":"Some String", "bar":"some other string","foobar":5 }
    static class Pojo {
        public String foo;
        public String bar;
        public int foobar;
    }

    public static void main(String [] args) throws UnknownHostException, ExecutionException, InterruptedException {

//        RiakClient client = RiakClient.newClient(10017, "127.0.0.1");
    	RiakClient client = RiakClient.newClient("localhost");
    	System.out.println("Connection success");
        Location location = new Location(new Namespace("information"), "info");
        System.out.println(location);

        FetchValue fv = new FetchValue.Builder(location).build();
        FetchValue.Response response = client.execute(fv);
        RiakObject obj = response.getValue(RiakObject.class);
		System.out.println(obj.getValue());
		System.out.println("getting Data From Server is completed");
        // Fetch object as Pojo class (map json to object)
        
        Pojo myObject;
        try{
        	myObject = response.getValue(Pojo.class);
        	 System.out.println(myObject.foobar);
        	 System.out.println("done");
        }catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
       

        client.shutdown();
    }
}