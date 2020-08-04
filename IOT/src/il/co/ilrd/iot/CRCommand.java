package il.co.ilrd.iot;

import com.google.gson.JsonObject;

public class CRCommand implements Command {
	JsonObject data;
	
	public CRCommand(JsonObject data) {
		this.data = data;
	}
	@Override
	public Response execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
