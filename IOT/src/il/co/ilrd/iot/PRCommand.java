package il.co.ilrd.iot;

import com.google.gson.JsonObject;

public class PRCommand implements Command {
	JsonObject data;
	
	public PRCommand(JsonObject data) {
		this.data = data;
	}
	@Override
	public Response execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
