package il.co.ilrd.iot;

import java.util.function.Function;

import com.google.gson.JsonObject;

public class CRCommand1 implements Command {
	JsonObject data;
	private static final String key = "CR";
	
	public CRCommand1(JsonObject data) {
		this.data = data;
	}
	@Override
	public Response execute() {
		System.out.println("CR exe");
		return new Response("CR working (msg)", 200);
	}
	public static String getKey() {
		return key;
	}
	public static Function<JsonObject, Command> getCommandInstance() {
		return CRCommand1::new;
	}
}
