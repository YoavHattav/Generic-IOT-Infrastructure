package il.co.ilrd.iot;

import com.google.gson.JsonObject;

public class EUCommand implements Command {
	JsonObject data;
	
	public EUCommand(JsonObject data) {
		this.data = data;
	}
	@Override
	public Response execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
