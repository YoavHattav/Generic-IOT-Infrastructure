package il.co.ilrd.iot;

import java.util.List;
import java.util.function.Function;

import com.google.gson.JsonObject;

import il.co.ilrd.iot.Dispatcher.Callback;

public class JarLoader {
	SingletonCommandFactory factory;
	Callback<String> cb = new Callback<String>(this::load, null);
	Dispatcher<String> dispacher = null;
	
	public JarLoader(Dispatcher<String> dispacher) {
		factory = SingletonCommandFactory.getInstance();
		this.dispacher = dispacher;
		dispacher.register(cb);
	}

	public void load(String jarPath) {
		List<Class<?>> commandClasses = getCommandClasses(jarPath);
		for (Class<?> commandClass : commandClasses) {
			String key ;
			Function<JsonObject, Command> cTor;
			factory.Add(key, cTor);
		}
	}

	private List<Class<?>> getCommandClasses(String jarPath) {
		// TODO Auto-generated method stub
		return null;
	}
	 
}
