package il.co.ilrd.iot;

import com.google.gson.JsonObject;

public class Task implements Runnable{
	Peer peer;
	String Key;
	String company;
	JsonObject data;
	
	public Task(Peer peer, String key, JsonObject data) {
		this.peer = peer;
		Key = key;
		this.data = data;
		System.out.println(key + " hhhhhh " + data);
	}
	public String getKey() {
		return Key;
	}
	public String getCompany() {
		return company;
	}
	public JsonObject getData() {
		return data;
	}

	@Override
	public void run() {
		SingletonCommandFactory factory = SingletonCommandFactory.getInstance();
		Command command = factory.Create(Key, data);
		System.out.println("in run task " + command.toString());
		Response response = command.execute();
		
		peer.send(response);
	}
}
