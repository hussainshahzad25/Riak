package com.canbrand.index;

import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.api.commands.search.StoreIndex;
import com.basho.riak.client.core.query.search.YokozunaIndex;

public class CreateIndex {

	public static void main(String[] args) throws UnknownHostException, ExecutionException, InterruptedException {
		RiakClient client = RiakClient.newClient("localhost");
		YokozunaIndex famousIndex = new YokozunaIndex("famous");
		StoreIndex storeIndex = new StoreIndex.Builder(famousIndex).build();
		try{
			client.execute(storeIndex);
			System.out.println("IndexCreated Sucsessfully");
		}catch (Exception e) {
		}
	}

}
