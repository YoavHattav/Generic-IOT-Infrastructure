package il.co.ilrd.iot;

import com.google.gson.JsonObject;

public class SingletonCommandFactory extends Factory<String, Command, JsonObject>{
	private static final SingletonCommandFactory factory = new SingletonCommandFactory();
	
	private SingletonCommandFactory() {}
	public static SingletonCommandFactory getInstance() {
		return factory;
	}
}
