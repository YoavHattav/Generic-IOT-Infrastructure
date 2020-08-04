package il.co.ilrd.iot;

import com.google.gson.JsonObject;

public class IOTCommand implements Command{
	JsonObject data;
	
	public IOTCommand(JsonObject data) {
		this.data = data;
	}

	@Override
	public Response execute() {
		System.out.println("IOT Operation");
		return new Response("IOT Operation on "+ 
		data.get("Company").getAsString() + " " + 
		data.get("Data").getAsString(), 200);
	}
}
