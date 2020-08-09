package il.co.ilrd.iot;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.JsonObject;

public class TaskManagment {
	ExecutorService tp; 
	
	public TaskManagment(int numOfThreads) {
		tp = Executors.newFixedThreadPool(numOfThreads);
	}
	public void submitTask(Peer peer, String key, JsonObject data) {
		tp.submit(createTask(peer, key, data));
	}
	private Task createTask(Peer peer, String key, JsonObject data) {
		return new Task(peer, key, data);  
	}
}
