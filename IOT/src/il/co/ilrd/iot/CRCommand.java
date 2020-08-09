package il.co.ilrd.iot;

import java.util.function.Function;

import com.google.gson.JsonObject;

public class CRCommand implements Command {
	JsonObject data;
	private static final String key = "sdfg";
	
	public CRCommand(JsonObject data) {
		this.data = data;
	}
	@Override
	public Response execute() {
		System.out.println("CR exe");
		return null;
	}
	public static String getKey() {
		return key;
	}
	public static Function<JsonObject, Command> getCommandInstance() {
		return CRCommand::new;
	}
}
